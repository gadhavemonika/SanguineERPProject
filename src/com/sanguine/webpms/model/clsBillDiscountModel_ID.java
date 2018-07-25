package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsBillDiscountModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strBillNo")
	private String strBillNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsBillDiscountModel_ID() {
	}

	public clsBillDiscountModel_ID(String strBillNo, String strClientCode) {
		this.strBillNo = strBillNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
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
		clsBillDiscountModel_ID objModelId = (clsBillDiscountModel_ID) obj;
		if (this.strBillNo.equals(objModelId.getStrBillNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBillNo.hashCode() + this.strClientCode.hashCode();
	}

}
