package com.sanguine.webbooks.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsDeleteTransactionModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsDeleteTransactionModel_ID() {
	}

	public clsDeleteTransactionModel_ID(String strTransCode, String strClientCode) {
		this.strTransCode = strTransCode;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
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

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsDeleteTransactionModel_ID objModelId = (clsDeleteTransactionModel_ID) obj;
		if (this.strTransCode.equals(objModelId.getStrTransCode()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
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
