package com.sanguine.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class clsProdSuppMasterModel_ID implements Serializable {
	private String strSuppCode;
	private String strProdCode;
	private String strClientCode;

	public clsProdSuppMasterModel_ID() {
	}

	public clsProdSuppMasterModel_ID(String strSuppCode, String strProdCode, String strClientCode) {
		this.strSuppCode = strSuppCode;
		this.strProdCode = strProdCode;
		this.strClientCode = strClientCode;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsProdSuppMasterModel_ID cp = (clsProdSuppMasterModel_ID) obj;
		if (this.strSuppCode.equals(cp.getStrSuppCode()) && this.strProdCode.equals(cp.strProdCode) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSuppCode.hashCode() + this.strProdCode.hashCode() + this.strClientCode.hashCode();
	}

}
