/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick,inject } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import 'rxjs/add/Observable/from';
import { HttpModule } from '@angular/http';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, FormGroup } from '@angular/forms';
import { ShippingComponent } from './uslice.shipping.component';
import { AppService } from '../../../services/app.service';
import { FlowService } from '../../../services/flow.service';
import { Step } from '../../../models/model';



describe('UsliceShippingComponent', () => {
    let component: ShippingComponent;
    let fixture: ComponentFixture<ShippingComponent>;
    let expectedStep: Step;
    let appService:AppService;
    let flowService:FlowService;
    let sampleStep: Step[];
    //let jquery:jQuery:any;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ShippingComponent],
            imports: [FormsModule, HttpModule],
            providers: [AppService, FlowService],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ShippingComponent);
        component = fixture.componentInstance;
        expectedStep = new Step();
        component.step = expectedStep; 
        appService = TestBed.get(AppService);
        flowService=TestBed.get(FlowService);
        sampleStep = [{
            title: 'title',
            index: 0,
            stepNumber: 1,
            buttonLabel: 'buttonlabel',
            componentName: 'name',
            next: 'next',
            layout: 'layout'
        }];

        fixture.detectChanges();
       
    });

    it('should be created', () => {
      expect(component).toBeTruthy();
    });
    it('should trigger onEditClick event (shipping) ', fakeAsync(() => {
        fixture.detectChanges();
        spyOn(component, 'onEditClick');
        let btn = fixture.debugElement.query(By.css('#shippingAddressEditButton'));
        btn.triggerEventHandler('click', null);
        tick();
        fixture.detectChanges();
        expect(component.onEditClick).toHaveBeenCalled();
      
    }));
    it('should trigger onContinueClick event (shipping) ', fakeAsync(() => {
        fixture.detectChanges();
        spyOn(component, 'onContinueClick');
        let btn = fixture.debugElement.query(By.css('#shipping'));
        btn.triggerEventHandler('ngSubmit', null);
        tick();
        fixture.detectChanges();
        expect(component.onContinueClick).toHaveBeenCalled();
    }));
   
    it('form shoud be  invalid when empty', () => {
        expect(component.shippingForm.invalid).toBeFalsy();
    });

    it('should add a  shipping address', fakeAsync( () => {

        fixture.detectChanges();


        const firstname: HTMLInputElement = fixture.debugElement.query(By.css('input[name="firstName"]')).nativeElement;
        firstname.value = 'John';
        firstname.dispatchEvent(new Event('input'));

        const lastname: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="lastName"]')).nativeElement;
        lastname.value = 'Tech';
        lastname.dispatchEvent(new Event('input'));

        const companyName: HTMLInputElement = fixture.debugElement.query(By.css('input[name="companyName"]')).nativeElement;
        companyName.value = 'Nisum';
        companyName.dispatchEvent(new Event('input'));

        const areaCode: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="areaCode"]')).nativeElement;
        areaCode.value = '343';
        areaCode.dispatchEvent(new Event('input'));

        const primaryPhone: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="primaryPhone"]')).nativeElement;
        primaryPhone.value= '5678987';
        primaryPhone.dispatchEvent(new Event('input'));

        const address: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="address"]')).nativeElement;
        address.value = '';
        address.dispatchEvent(new Event('input'));

        const address2: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="address2"]')).nativeElement;
        address2.value = '';
        address2.dispatchEvent(new Event('input'));

        const zipCode: HTMLSelectElement = fixture.debugElement.query(By.css('input[name="zipCode"]')).nativeElement;
        zipCode.value = '';
        zipCode.dispatchEvent(new Event('input'));

        tick();
       
       
        fixture.detectChanges();
        expect(component.shippingInfo).toBeDefined();
        expect(component.shippingForm).toBeDefined();
       
        expect(component.shippingInfo.addressInfo.state.length).toBeGreaterThanOrEqual(0);
       
       
       
    }));
    it('should console log  onContinueClick',  () => {
        spyOn(console, 'log');
        expect(console.log).not.toHaveBeenCalled();
        let step = {
            title: 'title',
            index: 0,
            stepNumber: 1,
            buttonLabel: 'buttonlabel',
            componentName: 'name',
            next: 'next',
            layout: 'layout'};
      component.onContinueClick();
      expect(console.log).toHaveBeenCalled( );
      //expect(console.log).toContain('title - continue clicked from name');
     
    });
    // it('should console log  onEditClick', () => {
    //     spyOn(console, 'log');
    //     expect(console.log).not.toHaveBeenCalled();
    //     // let step = {
    //     //     title: 'title',
    //     //     index: 0,
    //     //     stepNumber: 1,
    //     //     buttonLabel: 'buttonlabel',
    //     //     componentName: 'name',
    //     //     next: 'next',
    //     //     layout: 'layout'
    //     // };
    //     component.onEditClick();
    //     expect(console.log).toHaveBeenCalled();
       

    // });

});
    


