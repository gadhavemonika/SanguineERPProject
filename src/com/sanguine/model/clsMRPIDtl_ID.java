package com.sanguine.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class clsMRPIDtl_ID implements Serializable {

	private String strReqCode;
	private String strPICode;
	private String strClientCode;

	public clsMRPIDtl_ID() {
	}

	public clsMRPIDtl_ID(String strReqCode, String strPICode, String strClientCode) {
		this.strReqCode = strReqCode;
		this.strPICode = strPICode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsMRPIDtl_ID cp = (clsMRPIDtl_ID) obj;
		if (this.strReqCode.equals(cp.getStrReqCode()) && this.strPICode.equals(cp.getStrPICode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strReqCode.hashCode() + this.strPICode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrPICode() {
		return strPICode;
	}

	public void setStrPICode(String strPICode) {
		this.strPICode = strPICode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
