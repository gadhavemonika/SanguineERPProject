package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsTransporterHdModel_ID implements Serializable {

	private String strTransCode;
	private String strClientCode;

	public clsTransporterHdModel_ID() {
	}

	public clsTransporterHdModel_ID(String strTransCode, String strClientCode) {
		this.strTransCode = strTransCode;
		this.strClientCode = strClientCode;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsTransporterHdModel_ID cp = (clsTransporterHdModel_ID) obj;
		if (this.strTransCode.equals(cp.getStrTransCode()) && this.strClientCode.equals(cp.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTransCode.hashCode() + this.strClientCode.hashCode();
	}

}
