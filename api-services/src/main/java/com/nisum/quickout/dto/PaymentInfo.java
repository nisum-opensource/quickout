/**
 * Copyright (C) Nisum Technologies
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 * 
*/


package com.nisum.quickout.dto;

import com.nisum.quickout.persistence.domain.Address;

/**
 * It contains the amount, Country of Billing, Billing Address, Payment Method and Payment Card Info
 */
public class PaymentInfo {
    
    public PaymentInfo() {
    }

	public PaymentInfo(String countryOfBilling, String firstName, String lastName, String areaCode, String primaryPhone,
			String alternateAreaCode, String alternatePrimaryPhone, String email, String companyName,
			Address addressInfo, String paymentMethod, CreditCard creditCardInfo) {
		super();
		this.countryOfBilling = countryOfBilling;
		this.firstName = firstName;
		this.lastName = lastName;
		this.areaCode = areaCode;
		this.primaryPhone = primaryPhone;
		this.alternateAreaCode = alternateAreaCode;
		this.alternatePrimaryPhone = alternatePrimaryPhone;
		this.email = email;
		this.companyName = companyName;
		this.addressInfo = addressInfo;
		this.paymentMethod = paymentMethod;
		this.creditCardInfo = creditCardInfo;
	}

	private String countryOfBilling;
    private String firstName;
    private String lastName;
    
    private String areaCode;
    private String primaryPhone;
    
    private String alternateAreaCode;
    private String alternatePrimaryPhone;
    
    private String email;
    
    private String companyName;
    
    private Address addressInfo;
    private String paymentMethod;
    
    private CreditCard creditCardInfo;

	public String getCountryOfBilling() {
        return countryOfBilling;
    }

    public void setCountryOfBilling(String countryOfBilling) {
        this.countryOfBilling = countryOfBilling;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getAlternateAreaCode() {
        return alternateAreaCode;
    }

    public void setAlternateAreaCode(String alternateAreaCode) {
        this.alternateAreaCode = alternateAreaCode;
    }

    public String getAlternatePrimaryPhone() {
        return alternatePrimaryPhone;
    }

    public void setAlternatePrimaryPhone(String alternatePrimaryPhone) {
        this.alternatePrimaryPhone = alternatePrimaryPhone;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Address getAddress() {
        return addressInfo;
    }

    public void setAddress(Address address) {
        this.addressInfo = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CreditCard getCreditCard() {
        return creditCardInfo;
    }

}
