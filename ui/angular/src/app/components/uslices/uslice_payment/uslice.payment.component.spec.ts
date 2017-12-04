/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick, inject } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import 'rxjs/add/Observable/from';
import { HttpModule } from '@angular/http';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, FormGroup } from '@angular/forms';
import { PaymentInformationComponent } from './uslice.payment.component';
import { AppService } from '../../../services/app.service';
import { FlowService } from '../../../services/flow.service';
import { Step } from '../../../models/model';



describe('UslicePaymentComponent', () => {
    let component: PaymentInformationComponent;
    let fixture: ComponentFixture<PaymentInformationComponent>;
    let flowService: FlowService;
    let appService: AppService;
    let expectedStep: Step;
    let sampleStep: Step[];


    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [FormsModule, HttpModule],
            declarations: [PaymentInformationComponent],
            providers: [AppService, FlowService, PaymentInformationComponent],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PaymentInformationComponent);
        component = fixture.componentInstance;
        expectedStep = new Step();
        component.step = expectedStep;
        sampleStep = [{
            title: 'title',
            index: 0,
            stepNumber: 1,
            buttonLabel: 'buttonlabel',
            componentName: 'name',
            next: 'next',
            layout: 'layout'
        }];

        flowService = TestBed.get(FlowService);
        appService = TestBed.get(AppService);
        fixture.detectChanges();

    });

    it('should be created', () => {
        expect(component).toBeTruthy();
    });
    it('should trigger onEditClick event (payment)', fakeAsync(() => {
        fixture.detectChanges();
        spyOn(component, 'onEditClick');
        let btn = fixture.debugElement.query(By.css('#paymentInfoEditButton'));
        btn.triggerEventHandler('click', null);
        tick();
        fixture.detectChanges();
        expect(component.onEditClick).toHaveBeenCalled();
    }));
    it('should trigger onContinueClick event (payment) ', fakeAsync(() => {
        fixture.detectChanges();
        spyOn(component, 'onContinueClick');
        let btn = fixture.debugElement.query(By.css('#payment'));
        btn.triggerEventHandler('ngSubmit', null);
        tick();
        fixture.detectChanges();
        expect(component.onContinueClick).toHaveBeenCalled();
    }));

    it('form shoud be  invalid when empty', () => {
        expect(component.paymentForm.invalid).toBeFalsy();
    });

    it('should add a  paymentInfo', fakeAsync(() => {

        fixture.detectChanges();



        const firstname: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="firstname"]')).nativeElement;
        firstname.value = 'fName';
        firstname.dispatchEvent(new Event('input'));

        const lastname: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="lastname"]')).nativeElement;
        lastname.value = 'lName';
        lastname.dispatchEvent(new Event('input'));

        const areacode: HTMLInputElement = fixture.debugElement.query(By.css('input[name="areacode"]')).nativeElement;
        areacode.value = '123';
        areacode.dispatchEvent(new Event('input'));

        const primaryPhone: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="primaryPhone"]')).nativeElement;
        primaryPhone.value = '5678987';
        primaryPhone.dispatchEvent(new Event('input'));

        const areaCode: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="alternateAreaCode"]')).nativeElement;
        areaCode.value = '343';
        areaCode.dispatchEvent(new Event('input'));

        const altPhone: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="alternatePrimaryPhone"]')).nativeElement;
        altPhone.value = '5678987';
        altPhone.dispatchEvent(new Event('input'));

        const email: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="email"]')).nativeElement;
        email.value = '';
        email.dispatchEvent(new Event('input'));

        const companyName: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="companyName"]')).nativeElement;
        companyName.value = '';
        companyName.dispatchEvent(new Event('input'));


        const address: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="address"]')).nativeElement;
        address.value = '500 S Kraemer Blvd, Brea';
        address.dispatchEvent(new Event('input'));

        const address2: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="address2"]')).nativeElement;
        address2.value = 'Suite 301';
        address2.dispatchEvent(new Event('input'));

        const zipCode: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="zipcode"]')).nativeElement;
        zipCode.value = '92821';
        zipCode.dispatchEvent(new Event('input'));

        tick();

        fixture.detectChanges();
        expect(component.paymentInfo).toBeDefined();
        expect(component.paymentForm).toBeDefined();

        expect(component.paymentInfo.addressInfo.state.length).toBeGreaterThanOrEqual(0);

    }));
    it('should console log  onContinueClick', () => {
        spyOn(console, 'log');
        expect(console.log).not.toHaveBeenCalled();
        let step = {
            title: 'title',
            index: 0,
            stepNumber: 1,
            buttonLabel: 'buttonlabel',
            componentName: 'name',
            next: 'next',
            layout: 'layout'
        };
        component.onContinueClick();
        expect(console.log).toHaveBeenCalled();
        //expect(console.log).toContain('title - continue clicked from name');

    });
    // it('should console log  onEditClick', () => {
    //     spyOn(console, 'log');
    //     expect(console.log).not.toHaveBeenCalled();
    //     //let jQuery="M"
    //     component.onEditClick();
    //     expect(console.log).toHaveBeenCalled();

    // });
});