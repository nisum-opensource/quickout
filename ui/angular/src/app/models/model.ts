/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
*/
export class ShippingInfo {
    firstName: string;
    lastName: string;
    companyName: string;
    areaCode: string;
    primaryPhone: string;
    addressInfo: Address;
    notifEmail: string;
    notifAreaCode: string;
    notifPhone: string;
}

export class ShippingInfoServerRequest {
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    addresses: Address[];
}

export class Address {
    address: string;
    address2: string;
    city: string;
    state: string;
    zipCode: string;
}

export class Item {
    imageSource: string;
    skuName: string;
    listPrice: number;
    sellingPrice: number;
    quantity: number;
    sku: string
}

export class PaymentInfo {
    countryOfBilling: string
    firstName: string
    lastName: string
    areaCode: string
    primaryPhone: string
    alternateAreaCode: string
    alternatePrimaryPhone: string
    email: string
    companyName: string
    addressInfo: Address
    paymentMethod: string
    creditCardInfo: CreditCard
}
export class PaymentInfoServerRequest {
    alternateAreaCode: string
    alternatePrimaryPhone: string
    areaCode: string
    companyName: string
    countryOfBilling: string
    email: string
    firstName: string
    lastName: string
    paymentMethod: string
    primaryPhone: string
    address: Address
}
export class CreditCard {
    cardNumber: string
    expiryMonth: number
    expiryYear: number
    securityCode: string
}
export class Step {
    title: string;
    index: number;
    stepNumber: number;
    buttonLabel: string;
    componentName: string;
    next: string;
    layout: string;
}
export class NegotiateCartServerRequest {
    id: string;
    cartTotal: number;
    askingPriceByCart: number;
    customerInfo: { rppId: string };
    skus: CartItem[];
}
export class NegotiateCartServerResponse {
    id: string;
    responsePrice: string;
    lastAttempt: boolean;
    rejected: boolean;
    status: string;
    skus: ResponseCartItem[];
}

export class CartItem {
    sku: string;
    price: number;
    quantity: number;
}

export class ResponseCartItem {
    sku: string;
    price: number;
    quantity: number;
    reponsePrice: number;
    responseDiscount: number;
}

export class PaymentSummary {
    sub_total: number;
    shipping: number;
    estimated_tax: number;
    order_total: number;
}