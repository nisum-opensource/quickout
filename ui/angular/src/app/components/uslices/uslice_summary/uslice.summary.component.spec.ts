/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { async, ComponentFixture, TestBed, fakeAsync,inject, tick} from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import 'rxjs/add/Observable/from';
import { HttpModule } from '@angular/http';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';
import { OrderSummaryComponent} from './uslice.summary.component';
import { AppService } from '../../../services/app.service';
import { Item } from 'app/models/model';
import {MockBackend,MockConnection} from '@angular/http/testing';
import {BaseRequestOptions, Http, Response,ResponseOptions,XHRBackend} from '@angular/http';
import { environment } from '../../../../environments/environment';

function setupConnections(backend: MockBackend, options: any): any {
    backend.connections.subscribe((connection: MockConnection) => {
        const responseOptions: any = new ResponseOptions(options);
        const response: any = new Response(responseOptions);
        console.log(response.ok); 
        connection.mockRespond(response);
    });
}

describe('UsliceSummaryComponent', () => {
    let component: OrderSummaryComponent;
    let fixture: ComponentFixture<OrderSummaryComponent>;
    let appService:AppService;
    let service: AppService;
    let backend: MockBackend;
    
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports:[HttpModule],
            declarations: [OrderSummaryComponent],
            providers: [AppService,MockBackend],
            schemas: [NO_ERRORS_SCHEMA]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(OrderSummaryComponent);
        component = fixture.componentInstance;
       appService = TestBed.get(AppService);
       backend = TestBed.get(MockBackend);
        
    });
    
   
    it('should be created', () => {
      expect(component).toBeTruthy();
    });
    it('should get items from server ',()=>{
        component.ngOnInit();
        spyOn(appService, 'getItems').and.callThrough();

        fixture.detectChanges();
        expect(component.items).toBeDefined();
        expect(component.items.length).toBeGreaterThanOrEqual(0);
    });
    
    it('should log an error to the console on error', async(() => {
        setupConnections(backend, {
            body: { error: `error occured` },
            status: 400
        });
        spyOn(console, 'error');
       
        appService.getItems().subscribe(res => {
            console.log(res); 
           
        }, e => {
            expect(console.error).toHaveBeenCalledWith("400 - error occured");
            //console.log("Make sure an error has been thrown");
        });

        
    }));

});
