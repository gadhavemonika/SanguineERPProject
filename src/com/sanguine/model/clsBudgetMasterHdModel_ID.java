package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsBudgetMasterHdModel_ID implements Serializable {

	private String strBudgetCode;

	private String strClientCode;

	public clsBudgetMasterHdModel_ID() {
	}

	public clsBudgetMasterHdModel_ID(String strBudgetCode, String strClientCode) {
		this.strBudgetCode = strBudgetCode;
		this.strClientCode = strClientCode;
	}

	public String getStrBudgetCode() {
		return strBudgetCode;
	}

	public void setStrBudgetCode(String strBudgetCode) {
		this.strBudgetCode = strBudgetCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsBudgetMasterHdModel_ID cp = (clsBudgetMasterHdModel_ID) obj;
		if (this.strBudgetCode.equals(cp.getStrBudgetCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strBudgetCode.hashCode() + this.strClientCode.hashCode();
	}

}
