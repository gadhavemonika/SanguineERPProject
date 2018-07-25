package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

public class clsChargeProcessingDtlModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsChargeProcessingDtlModel() {

	}

	private String isProcessYN;

	private String strChargeCode;

	private String strAccountCode;

	private double dblAmount;

	private String strType;

	private String strCrDr;

	private String strNarration;

	public String getStrChargeCode() {
		return strChargeCode;
	}

	public void setStrChargeCode(String strChargeCode) {
		this.strChargeCode = strChargeCode;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getIsProcessYN() {
		return isProcessYN;
	}

	public void setIsProcessYN(String isProcessYN) {
		this.isProcessYN = isProcessYN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
