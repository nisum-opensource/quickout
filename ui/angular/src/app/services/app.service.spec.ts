/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { TestBed, inject, fakeAsync, getTestBed, async, ComponentFixture } from '@angular/core/testing';
import { AppService } from './app.service';
import { MockBackend, MockConnection } from '@angular/http/testing';
import { HttpModule, BaseRequestOptions, Http, XHRBackend, Response, ResponseOptions, RequestMethod } from '@angular/http';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Item, ShippingInfoServerRequest, PaymentInfoServerRequest, Address, CreditCard } from '../models/model'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';



const mockHttpProvider = {
    deps: [MockBackend, BaseRequestOptions],
    useFactory: (backend: MockBackend, defaultOptions: BaseRequestOptions) => {
        return new Http(backend, defaultOptions);
    }
};


describe('AppService  ', () => {
    let mockBackend: MockBackend;
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [{ provide: Http, useValue: mockHttpProvider },
                MockBackend,
                BaseRequestOptions,
                AppService,
            { provide: XHRBackend, useClass: MockBackend },]
        })
    });
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            providers: [
                AppService,
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    deps: [MockBackend, BaseRequestOptions],
                    useFactory:
                    (backend: XHRBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backend, defaultOptions);
                    }
                }
            ],
            imports: [
                HttpModule
            ]
        });
        mockBackend = getTestBed().get(MockBackend);
    }));

    it('should be Defined', inject([AppService], (service: AppService) => {
        expect(service).toBeDefined();
    }));
    it('can instantiate service when inject service',
        inject([AppService], (service: AppService) => {
            expect(service instanceof AppService).toBe(true);
        }));

    it('can instantiate service with "new"', inject([Http], (http: Http) => {
        expect(http).not.toBeNull('http should be provided');
        let service = new AppService(http);
        expect(service instanceof AppService).toBe(true, 'new service should be ok');
    }));


    it('can provide the mockBackend as XHRBackend',
        inject([XHRBackend], (backend: MockBackend) => {
            expect(backend).not.toBeNull('backend should be provided');
        }));


    describe('AppService service methods ', () => {

        it('Checking get Items ', inject([XHRBackend, AppService], (mockBackend, appService) => {
            mockBackend.connections.subscribe(
                (connection: MockConnection) => {
                    connection.mockRespond(new Response(
                        new ResponseOptions({
                            body: [
                                {
                                    imageSource: 'irc',
                                    skuName: 'sku',
                                    listPrice: 10,
                                    sellingPrice: 10,
                                    quantity: 1
                                }]
                        })));
                });

            appService.getItems().subscribe((items: Item[]) => {
                expect(items.length).toBe(1);
                expect(items[0].imageSource).toBe('irc');
            });

        }));
        it('should try to make a GET request to getItems',
            inject([AppService, MockBackend], fakeAsync((service: AppService, backend: MockBackend) => {
                backend.connections.subscribe((connection: MockConnection) => {
                    expect(connection.request.url).toBe('https://quickout.mynisum.com/quickout/api/cart?accountCode=fixed&cartId=2ef0ea3c-6306-4faf-8543-2966d54928e9');
                    expect(connection.request.method).toBe(RequestMethod.Get);
                });

                service.getItems();
            })));

        it('should try to make a GET request to get ZipState',
            inject([AppService, MockBackend], fakeAsync((service: AppService, backend: MockBackend) => {
                let mockAddress: Address = {
                    address: 'california street', address2: 'apt#1', city: 'fremont', state: 'CA', zipCode: '500071'

                };
                backend.connections.subscribe((connection: MockConnection) => {
                    expect(connection.request.url).toBe('https://quickout.mynisum.com/quickout/api/ziptostate?zipcode=500071');
                    expect(connection.request.method).toBe(RequestMethod.Get);

                    //expect(connection.request.text()).toBe(JSON.stringify(mockAddress));
                });

                service.getZipState(mockAddress.zipCode);
            })));


        it('should try to make a POST request to update Payment Info ',
            inject([AppService, MockBackend], fakeAsync((service: AppService, backend: MockBackend) => {

                let mockPayment: PaymentInfoServerRequest = {
                    countryOfBilling: 'Unite States',
                    firstName: 'John',
                    lastName: 'Doe',
                    areaCode: '123',
                    primaryPhone: '3456789',
                    email: 'johndoe@gmail.com',
                    alternateAreaCode: '098',
                    alternatePrimaryPhone: '9876543',
                    companyName: 'nisum',
                    paymentMethod: 'creditcard',
                    address: {
                        address: '500 S Kraemer Blvd, Brea',
                        address2: 'Suite 301',
                        city: 'fremont',
                        state: 'CA',
                        zipCode: '92821'
                    },
                };

                backend.connections.subscribe((connection: MockConnection) => {

                    expect(connection.request.url).toBe('https://quickout.mynisum.com/quickout/api/payment?accountCode=fixed&cartId=2ef0ea3c-6306-4faf-8543-2966d54928e9');
                    expect(connection.request.method).toBe(RequestMethod.Post);
                    expect(connection.request.text()).toBe(JSON.stringify(mockPayment));
                });
                service.updatePaymentInfo(mockPayment);

            }))
        );
        it('should try to make a PUT request to updateShippingAddress ',
            inject([AppService, MockBackend], fakeAsync((service: AppService, backend: MockBackend) => {

                let mockShipping: ShippingInfoServerRequest = {
                    firstName: 'John',
                    lastName: 'Doe',
                    email: 'johndoe@gmail.com',
                    phone: '9876543210',
                    addresses: [{
                        address: '500 S Kraemer Blvd, Brea',
                        address2: 'Suite 301',
                        city: 'fremont',
                        state: 'CA',
                        zipCode: '92821'
                    }]

                };


                backend.connections.subscribe((connection: MockConnection) => {
                    expect(connection.request.url).toBe('https://quickout.mynisum.com/quickout/api/shipment?accountCode=fixed&cartId=2ef0ea3c-6306-4faf-8543-2966d54928e9');
                    expect(connection.request.method).toBe(RequestMethod.Put);
                    expect(connection.request.text()).toBe(JSON.stringify(mockShipping));

                });

                service.updateShippingAddress(mockShipping);

            })));


    });
});
