package com.sanguine.webbooks.apgl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSundaryCrBillModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strVoucherNo")
	private String strVoucherNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSundaryCrBillModel_ID() {
	}

	public clsSundaryCrBillModel_ID(String strVoucherNo, String strClientCode) {
		this.strVoucherNo = strVoucherNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrVoucherNo() {
		return strVoucherNo;
	}

	public void setStrVoucherNo(String strVoucherNo) {
		this.strVoucherNo = strVoucherNo;
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
		clsSundaryCrBillModel_ID objModelId = (clsSundaryCrBillModel_ID) obj;
		if (this.strVoucherNo.equals(objModelId.getStrVoucherNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strVoucherNo.hashCode() + this.strClientCode.hashCode();
	}

}
