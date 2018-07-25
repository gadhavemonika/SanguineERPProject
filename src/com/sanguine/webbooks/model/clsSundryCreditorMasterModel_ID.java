package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsSundryCreditorMasterModel_ID implements Serializable {

	@Column(name = "strCreditorCode")
	private String strCreditorCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsSundryCreditorMasterModel_ID() {
	}

	public clsSundryCreditorMasterModel_ID(String strCreditorCode, String strClientCode) {
		this.strCreditorCode = strCreditorCode;
		this.strClientCode = strClientCode;
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = strCreditorCode;
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
		result = prime * result + ((strCreditorCode == null) ? 0 : strCreditorCode.hashCode());
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
		clsSundryCreditorMasterModel_ID other = (clsSundryCreditorMasterModel_ID) obj;
		if (strClientCode == null) {
			if (other.strClientCode != null)
				return false;
		} else if (!strClientCode.equals(other.strClientCode))
			return false;
		if (strCreditorCode == null) {
			if (other.strCreditorCode != null)
				return false;
		} else if (!strCreditorCode.equals(other.strCreditorCode))
			return false;
		return true;
	}

}
