/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { Component, OnInit, OnDestroy } from '@angular/core';
import { AppService } from "app/services/app.service";
import { Subscription } from "rxjs/Subscription";
import { NegotiateCartServerRequest, PaymentSummary, NegotiateCartServerResponse, CartItem, ResponseCartItem } from "app/models/model";
import { Subject } from "rxjs/Subject";

declare var jQuery: any;

@Component({
  selector: 'app-uslice-negotiation-modal',
  templateUrl: './uslice-negotiation-modal.component.html',
  styleUrls: ['./uslice-negotiation-modal.component.css']
})
export class UsliceNegotiationModalComponent implements OnInit, OnDestroy {

  askingPrice: number;
  accepted: boolean;
  validResponse: boolean;
  lastAttempt: boolean;
  note: string;
  id: string;
  rppId: string;
  responsePrice: string;

  negotiator: Subscription;
  cartItemsSubscription: Subscription;
  responseItemsSubscription: Subscription;

  paymentSummary: PaymentSummary;
  defaultPmtSummary: PaymentSummary;
  cartItems: CartItem[];

  constructor(private appService: AppService) { }

  ngOnInit() {
    //get cart items from Items component
    this.appService.cartItemsSubject.subscribe(
      (data: CartItem[]) => { this.cartItems = data }
    )

    //get paymentSummary from Items Component
    this.appService.defaultPmtSummarySubject.subscribe(
      (data: PaymentSummary) => {
        this.paymentSummary = data;
        this.defaultPmtSummary = data;
        console.log("NegotiationModal defaultPaymentSummary", this.paymentSummary)
      }
    )

    this.responseItemsSubscription = this.appService.getNegotiationResponseItems().subscribe(
      (data: ResponseCartItem[]) => {
        const sub_total = +data.reduce((total: number, item: ResponseCartItem) => {
          return this.appService.getSubTotal(total, item.reponsePrice * item.quantity);
        }, 0).toFixed(2);
        const estimated_tax = this.appService.getTax(sub_total);
        const order_total = this.appService.getTotal(sub_total, estimated_tax);

        this.paymentSummary = {
          sub_total,
          shipping: 0,
          estimated_tax,
          order_total
        }
      }
    );
  }

  ngOnDestroy() {
    if (this.negotiator != null)
      this.negotiator.unsubscribe();
    this.responseItemsSubscription.unsubscribe();
  }

  onCancelNegotiate() {
    this.initNegotiation();
  }

  initNegotiation() {
    this.appService.negotiationComplete.next(false);
    this.appService.clearNegotiatedResponseItems();
    this.appService.defaultPmtSummarySubject.next(this.defaultPmtSummary);
    this.note = "Enter a price to negotiate."
    this.lastAttempt = false;
    this.askingPrice = null;
    this.accepted = false;
    this.validResponse = false;
    this.id = null;
  }

  onNegotiateClick() {
    jQuery('#invalidCodeNote').hide('slow');
    if (!this.askingPrice) {
      console.log("Invalid asking price");
      this.note = "Enter a valid negotiation price."
      return;
    }
    if (this.askingPrice > this.paymentSummary.sub_total) {
      this.note = "Your offer must be below $" + this.paymentSummary.sub_total;
      return;
    }
    console.log("Asking price", this.askingPrice, this.paymentSummary.sub_total, this.id);
    const nReq = new NegotiateCartServerRequest();
    nReq.askingPriceByCart = +this.askingPrice;
    nReq.cartTotal = +this.paymentSummary.sub_total.toFixed(2);
    nReq.skus = this.cartItems;
    nReq.id = this.id;
    // this.rppId = (this.rppId == null) ? '10001_' + UUID.UUID() : this.rppId
    this.rppId = '10001_b64b4450-5c29-11e7-8f64-b916bc861321';
    nReq.customerInfo = { 'rppId': this.rppId };
    this.validResponse = false;

    console.log("Negotiation request", JSON.stringify(nReq));

    this.negotiator = this.appService.negotiateCart(nReq).subscribe(
      (data: NegotiateCartServerResponse) => {
        this.validResponse = !data.rejected;
        this.responsePrice = data.responsePrice;
        this.appService.orderTotalSubject.next(+data.responsePrice);
        this.lastAttempt = data.lastAttempt;
        this.id = data.id;
        this.note = data.status;
      },
      (error) => {
        console.log("Negotiation request error", error);
      }
    );
  }

  onAcceptClick() {
    this.appService.acceptOffer(this.id);
    this.appService.negotiationComplete.next(true);
    this.accepted = true;

    //update summary component
    this.appService.acceptedPmtSummarySubject.next(this.paymentSummary);

    jQuery('#pricepong').modal('hide');
  }
}
