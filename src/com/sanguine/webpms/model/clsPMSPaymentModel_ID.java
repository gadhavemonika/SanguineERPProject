package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsPMSPaymentModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strReceiptNo")
	private String strReceiptNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsPMSPaymentModel_ID() {
	}

	public clsPMSPaymentModel_ID(String strReceiptNo, String strClientCode) {
		this.strReceiptNo = strReceiptNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrReceiptNo() {
		return strReceiptNo;
	}

	public void setStrReceiptNo(String strReceiptNo) {
		this.strReceiptNo = strReceiptNo;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsPMSPaymentModel_ID objModelId = (clsPMSPaymentModel_ID) obj;
		if (this.strReceiptNo.equals(objModelId.getStrReceiptNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReceiptNo.hashCode() + this.strClientCode.hashCode();
	}

}
