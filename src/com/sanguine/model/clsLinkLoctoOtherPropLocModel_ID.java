package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsLinkLoctoOtherPropLocModel_ID implements Serializable {

	private String strPropertyCode;
	private String strLocCode;
	private String strToLoc;

	public clsLinkLoctoOtherPropLocModel_ID() {
	}

	public clsLinkLoctoOtherPropLocModel_ID(String strPropertyCode, String strLocCode, String strToLoc) {
		this.strPropertyCode = strPropertyCode;
		this.strLocCode = strLocCode;
		this.strLocCode = strToLoc;
	}

	@Override
	public boolean equals(Object obj) {
		clsLinkLoctoOtherPropLocModel_ID Batchobj = (clsLinkLoctoOtherPropLocModel_ID) obj;
		if (this.strPropertyCode.equals(Batchobj.getStrPropertyCode()) && this.strLocCode.equals(Batchobj.getStrLocCode()) && this.strToLoc.equals(Batchobj.getStrToLoc())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPropertyCode.hashCode() + this.strLocCode.hashCode() + this.strToLoc.hashCode();
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrToLoc() {
		return strToLoc;
	}

	public void setStrToLoc(String strToLoc) {
		this.strToLoc = strToLoc;
	}
}
