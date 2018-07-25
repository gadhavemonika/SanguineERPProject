package com.sanguine.bean;

import org.hibernate.validator.constraints.NotEmpty;

public class clsPropertyMasterBean {
	private String propertyCode;
	@NotEmpty
	/* @Length(min=3, max=10) */
	private String propertyName;

	/*
	 * private String addressLine1; private String addressLine2; private String
	 * city; private String state; private String country; private String pin;
	 * private String phone; private String mobile; private String fax; private
	 * String contact; private String email;
	 */
	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	/*
	 * public String getAddressLine1() { return addressLine1; } public void
	 * setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1;
	 * } public String getAddressLine2() { return addressLine2; } public void
	 * setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2;
	 * } public String getCity() { return city; } public void setCity(String
	 * city) { this.city = city; } public String getState() { return state; }
	 * public void setState(String state) { this.state = state; } public String
	 * getCountry() { return country; } public void setCountry(String country) {
	 * this.country = country; }
	 * 
	 * public String getEmail() { return email; } public void setEmail(String
	 * email) { this.email = email; } public String getPin() { return pin; }
	 * public void setPin(String pin) { this.pin = pin; } public String
	 * getPhone() { return phone; } public void setPhone(String phone) {
	 * this.phone = phone; } public String getMobile() { return mobile; } public
	 * void setMobile(String mobile) { this.mobile = mobile; } public String
	 * getFax() { return fax; } public void setFax(String fax) { this.fax = fax;
	 * } public String getContact() { return contact; } public void
	 * setContact(String contact) { this.contact = contact; }
	 */
}
