package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsPropertyMaster_ID implements Serializable {
	private String strPropertyCode;
	private String strClientCode;

	public clsPropertyMaster_ID() {
	}

	public clsPropertyMaster_ID(String propertyCode, String strClientCode) {
		this.strPropertyCode = propertyCode;
		this.strClientCode = strClientCode;
	}

	public String getPropertyCode() {
		return strPropertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.strPropertyCode = propertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsPropertyMaster_ID cp = (clsPropertyMaster_ID) obj;
		if (this.strPropertyCode.equals(cp.getPropertyCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strPropertyCode.hashCode() + this.strClientCode.hashCode();
	}

}
