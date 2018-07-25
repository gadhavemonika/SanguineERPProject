package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbltranschardtl")
public class clsTransectionProdCharModel {

	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strCharCode")
	private String strCharCode;

	@Column(name = "strValue")
	private String strSpecf;

	@Id
	@Column(name = "intId")
	@GeneratedValue
	private long intId;

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
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

	public String getStrSpecf() {
		return strSpecf;
	}

	public void setStrSpecf(String strSpecf) {
		this.strSpecf = strSpecf;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
