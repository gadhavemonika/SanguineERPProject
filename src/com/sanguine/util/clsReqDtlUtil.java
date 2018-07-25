package com.sanguine.util;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class clsReqDtlUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5682253014511074616L;
	private String strReqCode;
	private String strProdCode;
	private String strClientCode;

	public clsReqDtlUtil() {
	}

	public clsReqDtlUtil(String strReqCode, String strProdCode, String strClientCode) {
		this.strReqCode = strReqCode;
		this.strProdCode = strProdCode;
		this.strClientCode = strClientCode;
	}

	// Write equals condition for all properties
	@Override
	public boolean equals(Object obj) {
		clsReqDtlUtil cp = (clsReqDtlUtil) obj;
		if (this.strReqCode.equals(cp.getStrReqCode()) && this.strProdCode.equals(cp.getStrProdCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
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
	public int hashCode() {
		return this.strReqCode.hashCode() + this.strProdCode.hashCode() + this.strClientCode.hashCode();
	}

}