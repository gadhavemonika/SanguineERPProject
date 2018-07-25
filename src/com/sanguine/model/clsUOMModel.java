package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tbluommaster")
public class clsUOMModel implements Serializable {

	@Id
	@Column(name = "strUOMName")
	String strUOMName;

	@Column(name = "StrClientCode")
	private String strClientCode;

	public String getStrUOMName() {
		return strUOMName;
	}

	public void setStrUOMName(String strUOMName) {
		this.strUOMName = strUOMName;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
