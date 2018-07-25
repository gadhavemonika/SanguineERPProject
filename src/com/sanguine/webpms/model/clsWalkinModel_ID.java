package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Column;

@Embeddable
@SuppressWarnings("serial")
public class clsWalkinModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strWalkinNo")
	private String strWalkinNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWalkinModel_ID() {
	}

	public clsWalkinModel_ID(String strWalkinNo, String strClientCode) {
		this.strWalkinNo = strWalkinNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrWalkinNo() {
		return strWalkinNo;
	}

	public void setStrWalkinNo(String strWalkinNo) {
		this.strWalkinNo = strWalkinNo;
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
		clsWalkinModel_ID objModelId = (clsWalkinModel_ID) obj;
		if (this.strWalkinNo.equals(objModelId.getStrWalkinNo()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strWalkinNo.hashCode() + this.strClientCode.hashCode();
	}

}
