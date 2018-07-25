package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class clsProductionOrderHdModel_ID implements Serializable {

	private String strOPCode;

	private String strClientCode;

	public clsProductionOrderHdModel_ID() {
	}

	public clsProductionOrderHdModel_ID(String strOPCode, String strClientCode) {
		this.strOPCode = strOPCode;
		this.strClientCode = strClientCode;
	}

	public String getStrOPCode() {
		return strOPCode;
	}

	public void setStrOPCode(String strOPCode) {
		this.strOPCode = strOPCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProductionOrderHdModel_ID cp = (clsProductionOrderHdModel_ID) obj;
		if (this.strOPCode.equals(cp.getStrOPCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strOPCode.hashCode() + this.strClientCode.hashCode();
	}

}
