package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsExciseSalesHdModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "intBillNo")
	private Long intBillNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strItemCode")
	private String strItemCode;

	public clsExciseSalesHdModel_ID() {
	}

	public clsExciseSalesHdModel_ID(Long intBillNo, String strClientCode, String strItemCode) {
		this.intBillNo = intBillNo;
		this.strItemCode = strItemCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods

	public Long getIntBillNo() {
		return intBillNo;
	}

	public void setIntBillNo(Long intBillNo) {
		this.intBillNo = intBillNo;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsExciseSalesHdModel_ID objModelId = (clsExciseSalesHdModel_ID) obj;
		if (this.intBillNo == objModelId.getIntBillNo() && this.strClientCode.equals(objModelId.getStrClientCode()) && this.strItemCode.equals(objModelId.getStrItemCode())) {
			return true;
		} else {
			return false;
		}
	}

}
