/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/
import { NgModule , enableProdMode} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';

import { environment } from '../environments/environment';
import { AppComponent } from './app.component';

import { GalleryComponent } from './components/layouts/gallery/gallery.component';
import { Layout1Component } from './components/layouts/layout1/layout1.component';
import { Layout2Component } from './components/layouts/layout2/layout2.component';
import { Layout3Component } from './components/layouts/layout3/layout3.component';

import { NavComponent } from './components/uslices/uslice_nav/uslice.nav.component';
import { ItemsComponent } from './components/uslices/uslice_items/uslice.items.component';
import { ShippingComponent } from './components/uslices/uslice_shipping/uslice.shipping.component';
import { AccountInformationComponent} from './components/uslices/uslice_account/uslice.account.component';
import { PaymentInformationComponent } from './components/uslices/uslice_payment/uslice.payment.component';
import { OrderSummaryComponent } from './components/uslices/uslice_summary/uslice.summary.component';

import { AppService } from './services/app.service';
import { ModalComponent } from './components/uslices/uslice_modal/uslice.modal.component';
import { FlowService } from './services/flow.service';
import { UslicePaymentSuccessfulComponent } from './components/uslices/uslice-payment-successful/uslice-payment-successful.component';
import { HomeComponent } from './components/layouts/home/home.component';
import { UsliceApplePayComponent } from './components/uslices/uslice-apple-pay/uslice-apple-pay.component';
import { UsliceNegotiationModalComponent } from './components/uslices/uslice-negotiation-modal/uslice-negotiation-modal.component';

if (environment.production) {
  enableProdMode()
}
@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    Layout1Component,
    Layout2Component,
    Layout3Component,
    ItemsComponent,
    ShippingComponent,
    OrderSummaryComponent,
    AccountInformationComponent,
    PaymentInformationComponent,
    NavComponent,
    GalleryComponent,
    ModalComponent,
    UsliceApplePayComponent,
    UslicePaymentSuccessfulComponent,
    HomeComponent,
    UsliceNegotiationModalComponent
  ],
  bootstrap: [ AppComponent ],
  providers: [AppService, FlowService]
})
export class AppModule { }