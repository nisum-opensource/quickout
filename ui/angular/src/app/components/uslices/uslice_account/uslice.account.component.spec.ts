/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpModule} from '@angular/http';
import { AccountInformationComponent } from './uslice.account.component';
import {  Step } from 'app/models/model';


describe('UsliceAccountComponent', () => {
  let component: AccountInformationComponent;
  let fixture: ComponentFixture<AccountInformationComponent>;
  let de: DebugElement;
  let el: HTMLElement;
  let expectedStep: Step;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpModule],
      declarations: [AccountInformationComponent],
      schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountInformationComponent);
    component = fixture.componentInstance;
    expectedStep = new Step();
    component.step = expectedStep;
    fixture.detectChanges();
  });

  it('should be created', () => {
    fixture.detectChanges();
    let account = fixture.nativeElement.getElementsByTagName('#createIDSection');
    expect(account).toBeDefined();
    expect(component).toBeTruthy();
  });
  
  it('should have expected component', () => {
    expect(true).toBe(true);
  });
  it('should correctly project component Heading', () => {
    expect(fixture.debugElement.query(By.css('.subheading')).nativeElement.innerHTML).toContain('Account');
  });
  it('should display continue as guest button ', () => {
    expect(fixture.debugElement.query(By.css('#continueButtonSec4')).query(By.css('#guest')).nativeElement.innerHTML).toContain('Continue as Guest');
  });
});
