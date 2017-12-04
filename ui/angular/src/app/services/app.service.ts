/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
import { Injectable } from '@angular/core';
import { Http, HttpModule, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { Item, ShippingInfoServerRequest, PaymentInfoServerRequest, NegotiateCartServerRequest, NegotiateCartServerResponse, CartItem, ResponseCartItem, PaymentSummary } from '../models/model';

@Injectable()
export class AppService {

    showPlaceOrderButton = new Subject<boolean>();
    rppIdSubscription = new Subject<string>();
    cartItemsSubject = new Subject<CartItem[]>();
    defaultPmtSummarySubject = new Subject<PaymentSummary>();
    acceptedPmtSummarySubject = new Subject<PaymentSummary>();
    private responseItems = new Subject<ResponseCartItem[]>();
    orderTotalSubject = new Subject<number>();
    negotiationComplete = new Subject<boolean>();

    requestOptions: RequestOptions;

    url: string;
    pricePongUrl: string;
    counterOffer: string;
    zipState = '';
    cartId = '2ef0ea3c-6306-4faf-8543-2966d54928e9';
    accountCode = 'fixed';

    constructor(private http: Http) {
        this.url = environment.services_addr + '/quickout/api/'
        this.pricePongUrl = environment.pricepong_addr;

        const headers = new Headers({ 'Content-Type': 'application/json', 'AUTH-TOKEN': environment.pricepong_auth_token });
        this.requestOptions = new RequestOptions({
            headers: headers
        });
    }

    getItems(accountCode: string, cartId: string): Observable<Item[]> {
        if (accountCode != null)
            this.accountCode = accountCode;
        if (cartId != null)
            this.cartId = cartId;

        const observableItems = this.http.get(this.url + 'cart?accountCode=' + this.accountCode + '&cartId=' + this.cartId)
            .map((res: Response) => {
                return res.json().cartDetails
            });
        return observableItems;
    }

    getZipState(code: string): Observable<Response> {
        return this.http.get(this.url + 'ziptostate?zipcode=' + code);
    }

    updateShippingAddress(shippingInfo: ShippingInfoServerRequest) {
        this.http.put(this.url + 'shipment?accountCode=' + this.accountCode + '&cartId=' + this.cartId,
            JSON.stringify(shippingInfo),
            this.requestOptions)
            .subscribe(
            data => console.log('Shipping call Response:' + data),
            error => console.log('Shipping call error' + error)
            )
    }

    updatePaymentInfo(paymentInfo: PaymentInfoServerRequest) {
        this.http.post(this.url + 'payment?accountCode=' + this.accountCode + '&cartId=' + this.cartId,
            JSON.stringify(paymentInfo),
            this.requestOptions).subscribe(
            data => console.log('Payment call response' + data),
            error => console.log('Payment call error' + error)
            )
    }

    negotiateCart(cartInfo: NegotiateCartServerRequest): Observable<NegotiateCartServerResponse> {
        this.rppIdSubscription.next(cartInfo.customerInfo.rppId);
        return this.http.post(this.pricePongUrl + '/api/negotiator/cart/negotiate',
            JSON.stringify(cartInfo),
            this.requestOptions).map(
            data => {
                console.log('negotiate cart response', data.json())
                let response: NegotiateCartServerResponse = new NegotiateCartServerResponse();
                let apiResponse: any = data.json().apiResponse;
                response.responsePrice = apiResponse.result.responsePrice;
                response.rejected = false;
              
                if (apiResponse.result.status === 'rejected') {
                    response.status = apiResponse.result.offerTooLowMessage;
                    response.rejected = true;
                }
                else
                    response.status = apiResponse.result.negotiationMessage;
              
                if (apiResponse.lastAttempt)
                    response.status += " " + apiResponse.result.lastChanceMessage;
                response.id = apiResponse.id;
                response.lastAttempt = apiResponse.noMoreAttemtps;
                if(apiResponse.lastAttempt)
                    response.status += " "+apiResponse.result.lastChanceMessage;
                response.id = apiResponse.id;
                response.lastAttempt = apiResponse.noMoreAttemtps;
                if(apiResponse.cartSkuResponseList != null)
                    this.responseItems.next(apiResponse.cartSkuResponseList);
                return response;
            },
            error => {
                console.log('negotiate cart error', error);
                return "0";
            })
    }

    clearNegotiatedResponseItems() {
        this.responseItems.next([]);
    }

    getNegotiationResponseItems() : Observable<ResponseCartItem[]> {
        return this.responseItems.asObservable();
    }
  
    acceptOffer(requestId: string){
        this.http.put(this.pricePongUrl + '/api/negotiator/'+requestId+'/accept', "",
        this.requestOptions).subscribe(
            (data) => {
                console.log("Accept success", data.json());
            },
            error => {
                console.log("Error", error.json())
            }
        )
    }

    placeOrder(price: number, rppId: string) {
        let url = this.pricePongUrl + '/api/negotiator/pixel/checkout?s1=CART001&p1=' + price + '&rppId=' + rppId;
        console.log("placeOrder", url);
        this.http
            .get(url, this.requestOptions)
            .subscribe(
              (data) => {
                  console.log("placeOrder success", data.json());
              },
              error => {
                  console.log("placeOrder failure", error.json());
              }
            )
    }

    getSubTotal(total: number, newValue: number): number {
        return total + newValue;
    }

    getTotal(sub_total: number, tax: number): number {
        return Math.floor((sub_total + tax) * 100) / 100;
    }

    getTax(sub_total: number): number {
        return Math.floor((sub_total * 0.09) * 100) / 100;;
    }
}