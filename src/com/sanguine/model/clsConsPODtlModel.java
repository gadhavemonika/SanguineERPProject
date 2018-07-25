package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblconspodtl")
public class clsConsPODtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strConsPOCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strConsPOCode;

	@Column(name = "strPOCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strPOCode;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrConsPOCode() {
		return strConsPOCode;
	}

	public void setStrConsPOCode(String strConsPOCode) {
		this.strConsPOCode = strConsPOCode;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
