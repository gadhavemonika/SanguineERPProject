package com.sanguine.webbooks.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsWebBooksAuditModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strTransNo")
	private String strTransNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebBooksAuditModel_ID() {
	}

	public clsWebBooksAuditModel_ID(String strTransNo, String strClientCode) {
		this.strTransNo = strTransNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrTransNo() {
		return strTransNo;
	}

	public void setStrTransNo(String strTransNo) {
		this.strTransNo = strTransNo;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsWebBooksAuditModel_ID objModelId = (clsWebBooksAuditModel_ID) obj;
		if (this.strTransNo.equals(objModelId.getStrTransNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strTransNo.hashCode() + this.strClientCode.hashCode();
	}

}
