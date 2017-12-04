/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Component, Input, OnInit, ElementRef, ViewChild} from '@angular/core';
import {PaymentInfo, Step, PaymentInfoServerRequest} from '../../../models/model';
import {NgForm} from '@angular/forms/';
import {AppService} from '../../../services/app.service';
import 'rxjs/add/operator/map';
import {FlowService} from '../../../services/flow.service';

declare var jQuery: any;

@Component({
    moduleId: module.id,
    selector: 'app-uslice-payment-info',
    templateUrl: './uslice.payment.component.html',
    styleUrls: ['./uslice.payment.component.css']
})
export class PaymentInformationComponent implements OnInit {
  
    @Input()step: Step;
    @ViewChild('f') paymentForm: NgForm;
    paymentInfo: PaymentInfo;
   

    constructor(private appService: AppService,
        private _elRef: ElementRef,
        private flowService: FlowService) {}

    ngOnInit(): void {
        this.paymentInfo = {
            countryOfBilling: 'United States',
            firstName: 'Quick',
            lastName: 'Out',
            areaCode: '714',
            primaryPhone: '579-7979',
            alternateAreaCode: '',
            alternatePrimaryPhone: '',
            email: 'quickout@nisum.com',
            companyName: 'Nisum',
            addressInfo: {
                address: '500 S Kraemer Blvd',
                address2: 'Suite 301',
                city: 'Brea',
                state: 'CA',
                zipCode: '92821'
            },
            paymentMethod: '',
            creditCardInfo: {
                cardNumber: '4111111111111111',
                expiryMonth: 1,
                expiryYear: 20,
                securityCode: '342'
            }
        }
 }
    onContinueClick() {
        console.log(`${this.step.stepNumber} - continue clicked from ${this.step.componentName}`)
        this.paymentInfo = this.paymentForm.value;
        const paymentObject = new PaymentInfoServerRequest();
        paymentObject.alternateAreaCode = this.paymentInfo.alternateAreaCode;
        paymentObject.alternatePrimaryPhone = this.paymentInfo.alternatePrimaryPhone;
        paymentObject.areaCode = this.paymentInfo.areaCode;
        paymentObject.companyName = this.paymentInfo.companyName;
        paymentObject.countryOfBilling = this.paymentInfo.countryOfBilling;
        paymentObject.email = this.paymentInfo.email;
        paymentObject.firstName = this.paymentInfo.firstName;
        paymentObject.lastName = this.paymentInfo.lastName;
        paymentObject.paymentMethod = this.paymentInfo.paymentMethod;
        paymentObject.primaryPhone = this.paymentInfo.primaryPhone;
        paymentObject.address = this.paymentInfo.addressInfo;
        
        if (this.step.stepNumber === 3) {
            console.log("Step", this.step.stepNumber);
            this.flowService.onContinue3(jQuery, this.step.componentName, this.step.next);
            this.appService.showPlaceOrderButton.next(true);
        } else if (this.step.stepNumber === 2) {
            this.flowService.onContinue2(jQuery,  this.step.next);
        }
        this.appService.updatePaymentInfo(paymentObject);
    }
     
    onEditClick() {
      console.log(`${this.step.index} - Edit clicked from ${this.step.componentName}`)
      this.flowService.paymentEdit(jQuery);
    }
}
