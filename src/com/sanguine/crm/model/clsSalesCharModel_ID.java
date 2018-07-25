package com.sanguine.crm.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsSalesCharModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strCharCode")
	private String strCharCode;

	public clsSalesCharModel_ID() {
	}

	public clsSalesCharModel_ID(String strSOCode, String strProdCode, String strCharCode) {
		this.strSOCode = strSOCode;
		this.strProdCode = strProdCode;
		this.strCharCode = strCharCode;
	}

	// Setter-Getter Methods
	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = strCharCode;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsSalesCharModel_ID objModelId = (clsSalesCharModel_ID) obj;
		if (this.strSOCode.equals(objModelId.getStrSOCode()) && this.strProdCode.equals(objModelId.getStrProdCode()) && this.strCharCode.equals(objModelId.getStrCharCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strSOCode.hashCode() + this.strProdCode.hashCode() + this.strCharCode.hashCode();
	}

}
