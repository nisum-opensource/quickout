/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UslicePaymentSuccessfulComponent } from './uslice-payment-successful.component';

describe('UslicePaymentSuccessfulComponent', () => {
  let component: UslicePaymentSuccessfulComponent;
  let fixture: ComponentFixture<UslicePaymentSuccessfulComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UslicePaymentSuccessfulComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UslicePaymentSuccessfulComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
