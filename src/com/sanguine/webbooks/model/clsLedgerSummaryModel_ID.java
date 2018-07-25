package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsLedgerSummaryModel_ID implements Serializable {

	@Column(name = "strVoucherNo")
	private String strVoucherNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strBalCrDr")
	private String strBalCrDr;
	

	public clsLedgerSummaryModel_ID() {
	}

	public clsLedgerSummaryModel_ID(String strVoucherNo, String strClientCode,String strBalCrDr) {
		this.strVoucherNo = strVoucherNo;
		this.strClientCode = strClientCode;
		this.strBalCrDr=strBalCrDr;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strClientCode == null) ? 0 : strClientCode.hashCode());
		result = prime * result + ((strVoucherNo == null) ? 0 : strVoucherNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		clsLedgerSummaryModel_ID other = (clsLedgerSummaryModel_ID) obj;
		if (strClientCode == null) {
			if (other.strClientCode != null)
				return false;
		} else if (!strClientCode.equals(other.strClientCode))
			return false;
		if (strVoucherNo == null) {
			if (other.strVoucherNo != null)
				return false;
		} else if (!strVoucherNo.equals(other.strVoucherNo))
			return false;
		if (strBalCrDr == null) {
			if (other.strBalCrDr != null)
				return false;
		} else if (!strBalCrDr.equals(other.strBalCrDr))
			return false;
		return true;
	}

	public String getStrBalCrDr() {
		return strBalCrDr;
	}

	public void setStrBalCrDr(String strBalCrDr) {
		this.strBalCrDr = strBalCrDr;
	}

}
