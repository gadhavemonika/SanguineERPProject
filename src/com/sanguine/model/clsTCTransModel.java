package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbltctransdtl")
public class clsTCTransModel {
	@Id
	@Column(name = "intId")
	@GeneratedValue
	private long intId;

	@Column(name = "strTCCode")
	private String strTCCode;

	@Column(name = "strTCDesc")
	private String strTCDesc;

	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "strTransType")
	private String strTransType;

	@Column(name = "strClientCode")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrTCCode() {
		return strTCCode;
	}

	public void setStrTCCode(String strTCCode) {
		this.strTCCode = strTCCode;
	}

	public String getStrTCDesc() {
		return strTCDesc;
	}

	public void setStrTCDesc(String strTCDesc) {
		this.strTCDesc = strTCDesc;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
