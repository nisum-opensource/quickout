/**
 * This method is called when the page is loaded.
 * We use it to show the Apple Pay button as appropriate.
 * Here we're using the ApplePaySession.canMakePayments() method,
 * which performs a basic hardware check.
*/
document.addEventListener('DOMContentLoaded', () => {
   if (window.ApplePaySession) {
      // var merchantIdentifier = "merchant.com.mynisum.quickout";
      // var promise = ApplePaySession.canMakePaymentsWithActiveCard(merchantIdentifier);
      // promise.then((canMakePayments) => {
      //    console.log('Inside promise canMakePayments: ' + canMakePayments);
      //    if (canMakePayments) {
      //       console.log('Merchant verified. Show apple pay button.');
      //       showApplePayButton();
      //    }
      // });
      if (ApplePaySession.canMakePayments) {    showApplePayButton(); }
   }
});

function showApplePayButton() {
   // HTMLCollection.prototype[Symbol.iterator] = Array.prototype[Symbol.iterator];
   // const buttons = document.getElementsByClassName("apple-pay-button"); for (let
   // button of buttons) { button.className += " visible"; }
   $('.apple-pay-button').addClass(' visible')
}

/**
 * Apple Pay Logic
 * Our entry point for Apple Pay interactions.
 * Triggered when the Apple Pay button is pressed
*/
function applePayButtonClicked() {
   console.log('User Clicked on Apple Button');
   var paymentRequest = {
      currencyCode: 'USD',
      countryCode: 'US',
      total: {
         label: 'QuickOut Checkout Total',
         amount: '1.00'
      },
      supportedNetworks: [
         'visa', 'masterCard', 'amex', 'discover'
      ],
      merchantCapabilities: ['supports3DS'],
      //requiredShippingContactFields: [ 'postalAddress', 'email' ],
   }
   var session = new ApplePaySession(1, paymentRequest);
   console.log('Session object created. Show Apple pay sheet in safari');

   /**
    * Merchant Validation
    * We call our merchant session endpoint, passing the URL to use
    */
   session.onvalidatemerchant = (event) => {
      const validationURL = event.validationURL;
      console.log("Validate merchant");
      console.log("Validation url is: " + validationURL);
      getApplePaySession(event.validationURL).then(function (response) {
         //console.log(response);
         console.log("Merchant validation completed successfully.")
         session.completeMerchantValidation(response);
      });
   };

   /**
     * Payment Authorization
     * Here you receive the encrypted payment data. You would then send it
     * on to your payment provider for processing, and return an appropriate
     * status in session.completePayment()
     */
   session.onpaymentauthorized = (event) => {
      console.log("onpaymentauthorized");
      // Send payment for processing...
      const payment = event.payment;
      //console.log(JSON.stringify(payment.token));
      
      session.completePayment(ApplePaySession.STATUS_SUCCESS);
      $('.apple-pay-button').hide();
      //alert('Payment processed successfully.');
      console.log('Payment processed successfully.')
      window.location.href = "/payment_successfully";
   };
   session.begin();
}

/*
A helper function that requests an Apple Pay merchant session using a promise.
*/

function getApplePaySession(url) {
   return new Promise(function (resolve, reject) {
      var xhr = new XMLHttpRequest();
      xhr.open('POST', 'https://quickout.mynisum.com/getApplePaySession');
      xhr.onload = function () {
         console.log("getApplePaySession call: status code is " + this.status);
         if (this.status >= 200 && this.status < 300) {
            console.log("getApplePaySession call: response " + JSON.parse(xhr.response));
            resolve(JSON.parse(xhr.response));
         } else {
            reject({status: this.status, statusText: xhr.statusText});
         }
      };
      xhr.onerror = function () {
         reject({status: this.status, statusText: xhr.statusText});
      };
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.send(JSON.stringify({url: url}));
   });
}