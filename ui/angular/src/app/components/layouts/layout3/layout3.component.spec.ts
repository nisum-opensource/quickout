/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { HttpModule } from '@angular/http';
import { Layout3Component } from './layout3.component';
import { AppService } from '../../../services/app.service';
import { FlowService } from '../../../services/flow.service';

describe('Layout3Component', () => {
    let component: Layout3Component;
    let fixture: ComponentFixture<Layout3Component>;
    let appService:AppService;
    let flowService:FlowService;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [HttpModule],
            declarations: [Layout3Component],
            providers: [AppService, FlowService],
            schemas: [CUSTOM_ELEMENTS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(Layout3Component);
        component = fixture.componentInstance;
        appService = TestBed.get(AppService);
        flowService = TestBed.get(FlowService);
        fixture.detectChanges();
    });

    it('should be created', () => {
        expect(component).toBeTruthy();
    });
    it('should  have Account component if  isLoggedIn is set to false', () => {
        let containerElement = fixture.debugElement.query(By.css('.col-md-6 delivery-options-box'));
        expect(containerElement).toBeNull();
    });
    it('should  not have Account component if  isLoggedIn is set to true', () => {
        component.isLoggedIn = true;
       fixture.detectChanges();
       fixture.whenStable().then(() => {
           let containerElement = fixture.debugElement.query(By.css('.col-md-6 delivery-options-box'));
           expect(containerElement).toBeNull();
       });
     });

    it('should not have Place Order button  if  showPlaceOrderButton is set to false', () => {
        let containerElement = fixture.debugElement.query(By.css('.continue-button-section'));
        expect(containerElement).toBeNull();
    });
    it('should have the Place Order button if showPlaceOrderButton is set to true', () => {
        component.showPlaceOrderButton = true;
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            let containerElement = fixture.debugElement.query(By.css('.continue-button-section'));
            expect(containerElement).not.toBeNull();
        });
    });
    it('should call showPlaceorder button from service ', () => {
        component.ngOnInit();
        spyOn(appService, 'showPlaceOrderButton').and.callThrough();
        component.showPlaceOrderButton=false;
        fixture.detectChanges();
        expect(component.showPlaceOrderButton).toBeDefined();
        expect(component.showPlaceOrderButton).toBe(false);
    });
    it('should call onClickAuthLink button from service ', () => {
        component.ngOnInit();
        spyOn(flowService, 'onClickAuthLink').and.callThrough();
        component.isLoggedIn=false;
        fixture.detectChanges();
        expect(component.isLoggedIn).toBe(false);
    });
    // it('should have the Place Order button if showPlaceOrderButton is set to true', () => {
       
    //     spyOn(component, 'showPlaceOrderButton').and.callThrough();
    //     this.showPlaceOrderButton = true;
    //     component.ngOnInit();
    //     fixture.detectChanges();
    //     expect(component.showPlaceOrderButton).toHaveBeenCalledWith(flag);
        
    //     });
    
    
});
