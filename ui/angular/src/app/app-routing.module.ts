/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/

import { ModuleWithProviders, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { Layout1Component } from './components/layouts/layout1/layout1.component';
import { Layout2Component } from './components/layouts/layout2/layout2.component';
import { Layout3Component } from './components/layouts/layout3/layout3.component';
import { GalleryComponent } from './components/layouts/gallery/gallery.component';
import { UslicePaymentSuccessfulComponent } from './components/uslices/uslice-payment-successful/uslice-payment-successful.component'
import { HomeComponent } from "app/components/layouts/home/home.component";

const routes: Routes = [
  {path: '', pathMatch: 'full', redirectTo: 'home' },
  {path: 'home', component: HomeComponent },
  // {path: 'layouts', component: GalleryComponent},
  {path: '1', component: Layout1Component},
  {path: '2', component: Layout2Component},
  {path: '3', component: Layout3Component},
  {path: 'payment_successfully',  component: UslicePaymentSuccessfulComponent}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {}