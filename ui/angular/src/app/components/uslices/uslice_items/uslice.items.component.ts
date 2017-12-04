/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {
    Component,
    Input,
    ViewChild,
    ElementRef,
    AfterViewInit,
    Inject,
    OnInit,
    OnDestroy
} from '@angular/core';
import { Item, Step, NegotiateCartServerRequest, CartItem, NegotiateCartServerResponse, ResponseCartItem, PaymentSummary } from 'app/models/model';
import { AppService } from '../../../services/app.service';
import { Subscription } from 'rxjs/Subscription';
import { FlowService } from '../../../services/flow.service';
import { ActivatedRoute, Params } from "@angular/router";
import { NgForm } from "@angular/forms";
import { UUID } from 'angular2-uuid';
import { Subject } from "rxjs/Subject";

declare var jQuery: any;

@Component({
    moduleId: module.id,
    selector: 'app-uslice-shopping-item',
    templateUrl: 'uslice.items.component.html',
    styleUrls: ['./uslice.items.component.css']
})

export class ItemsComponent implements OnInit, OnDestroy {
    @Input() items: Item[] = [];
    @Input() step: Step;
    askingPrice: number;
    cartItems: CartItem[] = [];

    cartId: string;
    accountCode: string;
    @Input() responseItems: ResponseCartItem[] = [];
    responseItemsSubscription: Subscription;

    itemSubscription: Subscription;
    responsePrice: string;
    requestId: string;
    id: string;
    rppId: string;
    note: string;

    negotiate: boolean;
    promoCode: boolean;
    validResponse: boolean;
    lastAttempt: boolean;
    showPlaceOrderButton: boolean;
    accepted: boolean;

    negotiator: Subscription;
    paymentSummary: PaymentSummary = {
        sub_total: 0,
        shipping: 0,
        estimated_tax: 0,
        order_total: 0
    };

    constructor(private _elRef: ElementRef,
        private flowService: FlowService,
        private appService: AppService,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.appService.getItems(this.accountCode, this.cartId).subscribe(
            (data: Item[]) => {
                let items: CartItem[] = [];
                data.forEach((item) => {
                    let cartItem: CartItem = new CartItem();
                    cartItem.sku = item.sku;
                    cartItem.quantity = item.quantity;
                    cartItem.price = item.sellingPrice;
                    items.push(cartItem);
                })
                this.appService.cartItemsSubject.next(items);

                const sub_total = +data.reduce((previousValue: number, currentValue: Item) => {
                    return this.appService.getSubTotal(previousValue, currentValue.sellingPrice * currentValue.quantity);
                    // return previousValue + (currentValue.sellingPrice * currentValue.quantity);
                }, 0).toFixed(2);

                const estimated_tax = this.appService.getTax(sub_total);
                const order_total = this.appService.getTotal(sub_total, estimated_tax);
                this.paymentSummary = {
                    sub_total,
                    shipping: 0,
                    estimated_tax,
                    order_total
                }
                console.log("CartItems are ready", this.paymentSummary)
                this.appService.defaultPmtSummarySubject.next(this.paymentSummary);
            },
            (error) => { console.log("Uslice getItems error", error) }
        )
        this.note = "Enter a price to negotiate."
        this.route.queryParams.subscribe(
            (params: Params) => {
                this.cartId = params['cartId'];
                this.accountCode = params['accountCode'];
            }
        )
        this.itemSubscription = this.appService
            .getItems(this.accountCode, this.cartId).subscribe(
            (data: Item[]) => { this.items = data },
            (error) => { console.log(error) }
            )

        this.appService.negotiationComplete.subscribe(
            (flag: boolean) => { this.negotiate = flag }
        )

        this.responseItemsSubscription = this.appService.getNegotiationResponseItems().subscribe(
            (data: ResponseCartItem[]) => {
                const sub_total = data.reduce((total: number, item: ResponseCartItem) => {
                    return this.appService.getSubTotal(total, item.reponsePrice);
                }, 0);
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
        this.itemSubscription.unsubscribe();
        this.responseItemsSubscription.unsubscribe();
    }

    onContinueClick() {
        console.log(`${this.step.stepNumber} - continue clicked from ${this.step.componentName}`)
        this.flowService.onContinue1(jQuery, this.step.next);
    }
    onEditClick() {
        console.log(`${this.step.index} - Edit clicked from ${this.step.componentName}`)
        this.flowService.itemEdit(jQuery);
    }

    onApplyPromoCode() {
        this.negotiate = true;
    }

    onEnterPromoCode() {
        this.promoCode = true;
    }
}