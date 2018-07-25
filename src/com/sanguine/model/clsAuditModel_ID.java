package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsAuditModel_ID implements Serializable {
	private String strTransCode;
	private String strClientCode;

	public clsAuditModel_ID() {
	}

	public clsAuditModel_ID(String strTransCode, String strClientCode) {
		this.strTransCode = strTransCode;
		this.strClientCode = strClientCode;
	}

	@Override
	public boolean equals(Object obj) {
		clsAuditModel_ID Auditobj = (clsAuditModel_ID) obj;
		if (this.strTransCode.equals(Auditobj.getStrTransCode()) && this.strClientCode.equals(Auditobj.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTransCode.hashCode() + this.strClientCode.hashCode();
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

}
