/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, OnInit, Inject} from '@angular/core';

import {AppService} from '../../../services/app.service';

import {Step} from '../../../models/model';
import {FlowService} from '../../../services/flow.service';

@Component({
    moduleId: module.id,
    selector: 'app-layout2',
    templateUrl: './layout2.component.html',
    styleUrls: ['./layout2.component.css']
})

export class Layout2Component implements OnInit {

    step1: Step;
    step2: Step;
    step3: Step;
    step4: Step;
    step5: Step;
    showPlaceOrderButton: boolean = false;
    isLoggedIn: boolean = false;

    constructor(private appService: AppService,
                private flowService: FlowService) {
        this.step1 = {
            title: 'Delivery & Pickup Options',
            index: 1,
            stepNumber: 1,
            buttonLabel: 'Continue',
            componentName: 'items',
            next: 'payment',
            layout: 'layout-2'
        }
        this.step2 = {
            title: 'Payment',
            index: 2,
            stepNumber: 2,
            buttonLabel: 'Continue',
            componentName: 'payment',
            next: 'shipping',
            layout: 'layout-2'
        }
        this.step3 = {
            title: 'ShippingAddress',
            index: 3,
            stepNumber: 3,
            buttonLabel: 'Continue',
            componentName: 'shipping',
            next: 'account',
            layout: 'layout-2'
        }
        this.step4 = {
            title: 'Account',
            index: 4,
            stepNumber: 4,
            buttonLabel: 'Continue as Guest',
            componentName: 'account',
            next: 'summary',
            layout: 'layout-2'
        }
        this.step5 = {
            title: 'Summary',
            index: 5,
            stepNumber: 5,
            buttonLabel: 'Place Order Now',
            componentName: 'placeorder',
            next: 'finish',
            layout: 'layout-2'
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
}
