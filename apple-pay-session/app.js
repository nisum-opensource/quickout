const express = require('express');
const bodyParser =  require('body-parser');
const fs = require('fs');
const http = require('http');
const request = require('request');

/**
* IMPORTANT
* Change these paths to your own SSL and Apple Pay certificates,
* with the appropriate merchant identifier and domain
* See the README for more information.
*/
const BASE_PATH = "/home/ec2-user/apple-pay-session/";
const APPLE_PAY_CERTIFICATE_PATH = BASE_PATH + "certificates/ApplePay.pem";
const APPLE_PAY_CERTIFICATE_KEY_PATH = BASE_PATH + "certificates/ApplePayKey.pem";
const MERCHANT_IDENTIFIER = "merchant.com.mynisum.quickout";
const MERCHANT_DOMAIN = "quickout.mynisum.com";

try {
  fs.accessSync(APPLE_PAY_CERTIFICATE_PATH);
  fs.accessSync(APPLE_PAY_CERTIFICATE_KEY_PATH);
} catch (e) {
  throw new Error('You must generate your Merchant Identity Certificate and Key before running this example.');
}

const applePayCert = fs.readFileSync(APPLE_PAY_CERTIFICATE_PATH);
const applePayCertKey = fs.readFileSync(APPLE_PAY_CERTIFICATE_KEY_PATH);

const app = express();
app.use(bodyParser.json());


// Add headers
app.use(function (req, res, next) {

    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', '*');

    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST');

    // Request headers you wish to allow
    //res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With, CONTENT-TYPE');
    res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

    // Pass to next layer of middleware
    next();
});

/**
* A POST endpoint to obtain a merchant session for Apple Pay.
* The client provides the URL to call in its body.
* Merchant validation is always carried out server side rather than on
* the client for security reasons.
*/
app.post('/getApplePaySession', function (req, res) {

	console.log('Got getApplePaySession request');
	console.log('Request url: '+ req.body.url);
	// We need a URL from the client to call
	if (!req.body.url) return res.sendStatus(400);

	// We must provide our Apple Pay certificate, merchant ID, domain name, and display name
	const options = {
		url: req.body.url,
		cert: applePayCert,
		key: applePayCertKey,
		method: 'post',
		body: {
			merchantIdentifier: MERCHANT_IDENTIFIER,
			domainName: MERCHANT_DOMAIN,
			displayName: 'My Store',
		},
		json: true,
	}

	// Send the request to the Apple Pay server and return the response to the client
	request(options, function(err, response, body) {
		if (err) {
			console.log('Error generating Apple Pay session!');
			console.log(err, response, body);
			res.status(500).send(body);
		}
		res.send(body);
	});
});

http.createServer(app).listen(8443);
