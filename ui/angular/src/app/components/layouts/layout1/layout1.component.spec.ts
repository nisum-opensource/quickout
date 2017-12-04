/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement, CUSTOM_ELEMENTS_SCHEMA  } from '@angular/core';
import { HttpModule } from '@angular/http';
import { Layout1Component} from './layout1.component';
import { AppService } from '../../../services/app.service';
import { FlowService } from '../../../services/flow.service';

describe('layout1Component', () => {
    let component: Layout1Component;
    let fixture: ComponentFixture<Layout1Component>;
    let flowService: FlowService;
    let appService:AppService;
    let de: DebugElement;
    let el: HTMLElement;
   
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [HttpModule],
            declarations: [Layout1Component],
            providers: [AppService, FlowService],
            schemas: [CUSTOM_ELEMENTS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(Layout1Component);
        component = fixture.componentInstance;
        fixture.detectChanges();
        flowService = TestBed.get(FlowService);
        appService = TestBed.get(AppService);
        de = fixture.debugElement;
    });

    it('should be created', () => {
        expect(component).toBeTruthy();
    });
    it('should  have Account component if  isLoggedIn is set to false', () => {
        let containerElement = fixture.debugElement.query(By.css('.col-md-6 delivery-options-box'));
        expect(containerElement).toBeNull();
    });
    it('should not  have Account component if  isLoggedIn is set to true', () => {
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
    it('should call showplaceorder from AppService ', () => {
        component.ngOnInit();
        spyOn(appService, 'showPlaceOrderButton').and.callThrough();
        fixture.detectChanges();
        //this.showPlaceOrderButton = false;
        expect(component.showPlaceOrderButton).toBeDefined();
        expect(component.showPlaceOrderButton).toBe(false);
    });
    it('should call onClickAuthLink from service ', () => {
        component.ngOnInit();
        spyOn(flowService, 'onClickAuthLink').and.callThrough();
        
        fixture.detectChanges();
        
        expect(component.isLoggedIn).toBeDefined();
        expect(component.isLoggedIn).toBe(false);
    });

    // it('should log ngOnInit', () => {
    //     spyOn(console, 'log').and.callThrough();
    //     expect(console.log).not.toHaveBeenCalled();

    //     component.ngOnInit();
    //     expect(console.log).toHaveBeenCalled();
    // });
   
    // it('should call onClickAuthLink from flowservice', () => {

    //     spyOn(flowService, 'onClickAuthLink').and.callThrough();
    //     //this.list.length = 0;
    //     fixture.detectChanges();
    //     expect(flowService.onClickAuthLink).toHaveBeenCalled();
    //     expect(component.isLoggedIn).toBe(false);
    // });
});