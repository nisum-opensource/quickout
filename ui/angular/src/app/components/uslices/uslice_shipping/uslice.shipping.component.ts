/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, Input, OnInit, ElementRef, ViewChild} from '@angular/core';
import {NgForm} from '@angular/forms';

import {ShippingInfo, Address, Step, ShippingInfoServerRequest} from '../../../models/model';

import {FlowService} from '../../../services/flow.service';
import {AppService} from '../../../services/app.service';

declare var jQuery: any;

@Component({
    moduleId: module.id,
    selector: 'app-uslice-shipping-info',
    templateUrl: './uslice.shipping.component.html',
    styleUrls: ['./uslice.shipping.component.css']
})
export class ShippingComponent implements OnInit {

    @Input()step: Step;

    shippingInfo: ShippingInfo;
    @ViewChild('f') shippingForm: NgForm;
   
    constructor(private _elRef: ElementRef,
                private appService: AppService,
                private flowService: FlowService) {}

    ngOnInit(): void {
        this.shippingInfo = {
            firstName: 'Quick',
            lastName: 'Out',
            companyName: 'Nisum',
            areaCode: '714',
            primaryPhone: '579-7979',
            addressInfo: {
                address: '500 S Kraemer Blvd',
                address2: 'Suite 301',
                city: 'Brea',
                state: 'CA',
                zipCode: '92821'
            },
            notifEmail: 'quickout@nisum.com',
            notifAreaCode: '714',
            notifPhone: '579-7979'
        }
       
    }

    onContinueClick() {
        console.log(`${this.step.stepNumber} - continue clicked from ${this.step.componentName}`)
        this.shippingInfo = this.shippingForm.value;
        if (this.step.stepNumber === 2) {
            this.flowService.onContinue2(jQuery, this.step.next);
        } else if (this.step.stepNumber === 3) {
            this.flowService.onContinue3(jQuery, this.step.componentName, this.step.next);
            this.appService.showPlaceOrderButton.next(true);
        }

        const requestObject = new ShippingInfoServerRequest();
        requestObject.firstName = this.shippingInfo.firstName;
        requestObject.lastName = this.shippingInfo.lastName;
        requestObject.email = this.shippingInfo.notifEmail;
        requestObject.phone = this.shippingInfo.primaryPhone;
        requestObject.addresses = [this.shippingInfo.addressInfo]

        this.appService.updateShippingAddress(requestObject);
    }
    onEditClick() {
        console.log(`${this.step.index} - Edit clicked from ${this.step.componentName}`)
        this.flowService.shippingEdit(jQuery);
    }
   
}
