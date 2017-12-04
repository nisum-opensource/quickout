/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync, tick, inject } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { DebugElement, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpModule } from '@angular/http';
import { ItemsComponent } from './uslice.items.component';
import { FlowService } from '../../../services/flow.service';
import { AppService } from '../../../services/app.service';
import { Item, Step } from 'app/models/model';

describe('UsliceItemsComponent', () => {
    let component: ItemsComponent;
    let fixture: ComponentFixture<ItemsComponent>;
    let appService: AppService;
    let flowService: FlowService;
    let expectedStep: Step;
    let items: Item[] = [{ imageSource: 'source', skuName: '1234', listPrice: 9, sellingPrice: 10.9, quantity: 1 }];


    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [HttpModule],
            declarations: [ItemsComponent],
            providers: [FlowService, AppService, ItemsComponent],
            schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ItemsComponent);
        component = fixture.componentInstance;
        appService = TestBed.get(AppService);
        flowService = TestBed.get(FlowService);
        expectedStep = new Step();
        component.step = expectedStep;
        fixture.detectChanges();
    });
    it('should have a defined component', () => {
        expect(component).toBeDefined();
    });
    it('should have default state', () => {
        expect(component.items).toBeUndefined;
        expect(component.itemSubscription).toBeUndefined;
    });

    it('should get items from server/service ', () => {
        component.ngOnInit();
        spyOn(appService, 'getItems').and.callThrough();

        fixture.detectChanges();
        expect(component.items).toBeDefined();
        expect(component.items.length).toBeGreaterThanOrEqual(0);
        

    });
    it('should unsubscribe component', () => {
        component.ngOnDestroy();
        expect(component.itemSubscription).toBeUndefined;
    });
    it('should trigger onContinueClick event (shipping) ', fakeAsync(() => {
        fixture.detectChanges();
        spyOn(component, 'onContinueClick');
        let btn = fixture.debugElement.query(By.css('#items'));
        btn.triggerEventHandler('click', null);
        tick();
        fixture.detectChanges();
        expect(component.onContinueClick).toHaveBeenCalled();
    }));




    // it('should log ngOnInit', () => {
    //     spyOn(console, 'log');
    //     expect(console.log).not.toHaveBeenCalled();

    //     component.onContinueClick();
    //     expect(console.log).toHaveBeenCalled();
    // });

});