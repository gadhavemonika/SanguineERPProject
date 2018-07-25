package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

import com.sanguine.model.clsGroupMasterModel_ID;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubGroupMasterModel_ID implements Serializable {

	private String strGroupCode;

	private String strClientCode;

	public clsWebClubGroupMasterModel_ID() {
	}

	public clsWebClubGroupMasterModel_ID(String strGroupCode, String strClientCode) {
		this.strGroupCode = strGroupCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsWebClubGroupMasterModel_ID cp = (clsWebClubGroupMasterModel_ID) obj;
		if (this.strGroupCode.equals(cp.getStrGroupCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strGroupCode.hashCode() + this.strClientCode.hashCode();
	}

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = strGroupCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
