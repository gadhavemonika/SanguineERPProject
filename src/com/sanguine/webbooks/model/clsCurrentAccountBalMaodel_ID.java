package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsCurrentAccountBalMaodel_ID implements Serializable {

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsCurrentAccountBalMaodel_ID() {
	}

	public clsCurrentAccountBalMaodel_ID(String strAccountCode, String strClientCode) {
		this.strAccountCode = strAccountCode;
		this.strClientCode = strClientCode;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
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
		result = prime * result + ((strAccountCode == null) ? 0 : strAccountCode.hashCode());
		result = prime * result + ((strClientCode == null) ? 0 : strClientCode.hashCode());
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
		clsCurrentAccountBalMaodel_ID other = (clsCurrentAccountBalMaodel_ID) obj;
		if (strAccountCode == null) {
			if (other.strAccountCode != null)
				return false;
		} else if (!strAccountCode.equals(other.strAccountCode))
			return false;
		if (strClientCode == null) {
			if (other.strClientCode != null)
				return false;
		} else if (!strClientCode.equals(other.strClientCode))
			return false;
		return true;
	}

}
