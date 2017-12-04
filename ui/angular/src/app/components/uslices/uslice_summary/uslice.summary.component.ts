/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { Component, Input, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { AppService } from '../../../services/app.service';
import { Item, NegotiateCartServerRequest, CartItem, NegotiateCartServerResponse, ResponseCartItem, PaymentSummary } from 'app/models/model';
import { NgForm } from "@angular/forms";
import { Subscription } from "rxjs/Subscription";
import { UUID } from 'angular2-uuid';
import { Subject } from "rxjs/Subject";

declare var jQuery: any;

@Component({
    moduleId: module.id,
    selector: 'app-uslice-summary-info',
    templateUrl: './uslice.summary.component.html',
    styleUrls: ['./uslice.summary.component.css']
})
export class OrderSummaryComponent implements OnInit {

    @Input() layout;
    @ViewChild('f') negotiateForm: NgForm;
    cartItems = new Subject<CartItem[]>();

    cartId: string;
    accountCode: string;
    requestId: string;

    showPlaceOrderButton: boolean;

    paymentSummary: PaymentSummary = {
        sub_total: 0,
        shipping: 0,
        estimated_tax: 0,
        order_total: 0
    };
    defaultPaymentSummary: PaymentSummary;

    ngOnInit(): void {
        this.appService.defaultPmtSummarySubject.subscribe(
            (data: PaymentSummary) => { 
                this.defaultPaymentSummary = data;
                this.paymentSummary = this.defaultPaymentSummary;
            }
        )
        this.appService.acceptedPmtSummarySubject.subscribe(
            (data: PaymentSummary) => { 
                this.paymentSummary = data;
            }
        )
        this.appService.showPlaceOrderButton
            .subscribe((flag: boolean) => {
                console.log('Summary Show/Hide order place button', flag);
                this.showPlaceOrderButton = flag;
            });
    }

    constructor(private appService: AppService) { }
}