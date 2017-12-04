/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class FlowService {

   onClickAuthLink = new Subject<boolean>();
   private isLoggedIn = false;
   constructor() {}

   onContinue1(jQuery: any, next: string) {
      jQuery('#continueButtonSec1').hide('slow');
      jQuery('#selectDeliveyOptionsDiv').hide();
      jQuery('#selectedDeliveyOptionsDiv').show();
      jQuery('#deliveryOptionsEditButton').show();
      // item -> shipping
      if (next === 'shipping') {
         jQuery('#shippingAddressSection').show('slow');
         jQuery('#shippingAddressHeading').addClass('active');
         jQuery('#continueButtonSec2').show('slow');
         jQuery('html,body').animate({
            scrollTop: jQuery('#shippingAddressHeading').offset().top - 40
         }, 'slow');
      } else if (next === 'payment') { // item -> payment
         jQuery('#paymentInfoSection').show('slow');
         jQuery('#paymentInfoHeading').addClass('active');
         jQuery('#continueButtonSec3').show('slow');
         jQuery('html,body').animate({
            scrollTop: jQuery('#paymentInfoHeading').offset().top - 40
         }, 'slow');
      }
   }

   onContinue2(jQuery: any, next: string) {

      // shipping -> payment
      if (next === 'payment') {
         jQuery('#continueButtonSec2').hide('slow');
         jQuery('#shippingAddressSection').hide();
         jQuery('#continueButtonSec3').show('slow');
         jQuery('#shippingFilledInfo').show();
         jQuery('#shippingAddressEditButton').show();
         jQuery('#paymentInfoSection').show('slow');
         jQuery('#paymentInfoHeading').addClass('active');
         jQuery('html,body').animate({
            scrollTop: jQuery('#paymentInfoHeading').offset().top - 40
         }, 'slow');
      } else if (next === 'shipping') { // payment -> shipping
         jQuery('#continueButtonSec3').hide('slow');
         jQuery('#continueButtonSec2').show('slow');
         jQuery('#paymentInfoSection').hide();
         jQuery('#paymentFilledInfo').show();
         jQuery('#paymentInfoEditButton').show();
         jQuery('#shippingAddressSection').show('slow');
         jQuery('#shippingAddressHeading').addClass('active');
         jQuery('html,body').animate({
            scrollTop: jQuery('#shippingAddressHeading').offset().top - 40
         }, 'slow');
      }
   }

   onContinue3(jQuery: any, current: string, next: string) {
         if (current === 'payment') {
         jQuery('#continueButtonSec3').hide('slow');
         jQuery('#shippingAddressSection').hide();
         jQuery('#paymentInfoSection').hide();
         jQuery('#paymentFilledInfo').show();
         jQuery('#paymentInfoEditButton').show();
      } else {
         jQuery('#shippingAddressSection').hide();
         jQuery('#continueButtonSec2').hide('slow');
         jQuery('#shippingFilledInfo').show();
         jQuery('#shippingAddressEditButton').show();
      }
      if (!this.isLoggedIn) {
         jQuery('#createIDSection').show('slow');
         jQuery('#continueButtonSec4').show('slow');
         jQuery('#createAccountHeading').addClass('active');
         jQuery('html,body').animate({
            scrollTop: jQuery('#createAccountHeading').offset().top - 40
          }, 'slow');
      }
   }

   itemEdit(jQuery: any) {
      jQuery('#continueButtonSec1').show('slow');
      jQuery('#selectDeliveyOptionsDiv').show('slow');
      jQuery('#deliveryOptionsEditButton').hide();
      jQuery('#selectedDeliveyOptionsDiv').hide();
      jQuery('#shippingAddressSection').hide();
      jQuery('#continueButtonSec2').hide();
      jQuery('#paymentInfoSection').hide();
      jQuery('#continueButtonSec3').hide();
      jQuery('#createIDSection').hide();
      jQuery('#continueButtonSec4').hide();

      jQuery('html,body').animate({
            scrollTop: jQuery('#selectDeliveyOptionsDiv').offset().top - 40
         }, 'slow'
      );
   }

   shippingEdit(jQuery: any) {
      jQuery('#shippingAddressEditButton').hide();
      jQuery('#shippingFilledInfo').hide();
      jQuery('#paymentInfoSection').hide();
      jQuery('#continueButtonSec3').hide();
      jQuery('#createIDSection').hide();
      jQuery('#continueButtonSec4').hide();
      jQuery('#shippingAddressSection').show('slow');
      jQuery('#continueButtonSec2').show('slow');
      jQuery('#shippingAddressHeading').addClass('active');

      jQuery('html,body').animate({
            scrollTop: jQuery('#shippingAddressHeading').offset().top
         }, 'slow'
      );
   }

   paymentEdit(jQuery: any) {
      jQuery('#paymentInfoEditButton').hide();
      jQuery('#paymentFilledInfo').hide();
      jQuery('#paymentInfoSection').hide();
      jQuery('#continueButtonSec3').hide();
      jQuery('#createIDSection').hide();
      jQuery('#continueButtonSec4').hide();
      jQuery('#paymentInfoSection').show('slow');
      jQuery('#continueButtonSec3').show('slow');
      jQuery('#paymentInfoHeading').addClass('active');

      jQuery('html,body').animate({
            scrollTop: jQuery('#paymentInfoHeading').offset().top
         }, 'slow'
      );
   }

   signIn() {
      this.isLoggedIn = true;
   }
   signOut() {
      this.isLoggedIn = false;
   }

}