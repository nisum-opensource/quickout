/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, OnInit, Inject, Injectable, AfterViewInit, ElementRef} from '@angular/core';

import {AppService} from '../../../services/app.service';

import {Step} from '../../../models/model';
import {FlowService} from '../../../services/flow.service';

declare var jQuery: any;

@Component({
    moduleId: module.id,
    selector: 'app-layout3',
    templateUrl: './layout3.component.html',
    styleUrls: ['./layout3.component.css']
})
export class Layout3Component implements OnInit, AfterViewInit {

    step1: Step;
    step2: Step;
    step3: Step;
    step4: Step;
    step5: Step;
    showPlaceOrderButton :boolean= false;
    isLoggedIn:boolean = false;

    constructor(private appService: AppService,
                private _elRef: ElementRef,
                private flowService: FlowService) {
        this.step1 = {
            title: 'Delivery & Pickup Options',
            index: 0,
            stepNumber: 1,
            buttonLabel: 'Continue',
            componentName: 'items',
            next: 'shipping',
            layout: 'layout-3'
        }
        this.step2 = {
            title: 'Shipping Address',
            index: 0,
            stepNumber: 2,
            buttonLabel: 'Continue',
            componentName: 'shipping',
            next: 'payment',
            layout: 'layout-3'
        }
        this.step3 = {
            title: 'Payment',
            index: 0,
            stepNumber: 3,
            buttonLabel: 'Continue',
            componentName: 'payment',
            next: 'account',
            layout: 'layout-3'
        }
        this.step4 = {
            title: 'Account',
            index: 0,
            stepNumber: 4,
            buttonLabel: 'Continue as Guest',
            componentName: 'account',
            next: 'summary',
            layout: 'layout-3'
        }
        this.step5 = {
            title: 'Summary',
            index: 0,
            stepNumber: 5,
            buttonLabel: 'Place Order Now',
            componentName: 'placeorder',
            next: 'finish',
            layout: 'layout-3'
        }
    }

    ngOnInit() {
        this.appService.showPlaceOrderButton
            .subscribe((flag: boolean) => {
                this.showPlaceOrderButton = flag;
            });
        this.flowService.onClickAuthLink
            .subscribe ((isLoggedIn: boolean) => {
              this.isLoggedIn = isLoggedIn;
            })
    }

    ngAfterViewInit() {
        // const s = document.createElement('script');
        // s.type = 'text/javascript';
        // s.src = 'assets/js/app.js';
        // this._elRef.nativeElement.appendChild(s);
    }

}
