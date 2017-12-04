/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsliceApplePayComponent } from './uslice-apple-pay.component';

describe('UsliceApplePayComponent', () => {
  let component: UsliceApplePayComponent;
  let fixture: ComponentFixture<UsliceApplePayComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsliceApplePayComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsliceApplePayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
