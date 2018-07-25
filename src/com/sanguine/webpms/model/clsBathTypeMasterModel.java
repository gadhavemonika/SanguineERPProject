package com.sanguine.webpms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblbathtypemaster")
public class clsBathTypeMasterModel {
	@Id
	@Column(name = "strBathTypeCode")
	private String strBathTypeCode;

	@Column(name = "strBathTypeDesc")
	private String strBathTypeDesc;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrBathTypeCode() {
		return strBathTypeCode;
	}

	public void setStrBathTypeCode(String strBathTypeCode) {
		this.strBathTypeCode = strBathTypeCode;
	}

	public String getStrBathTypeDesc() {
		return strBathTypeDesc;
	}

	public void setStrBathTypeDesc(String strBathTypeDesc) {
		this.strBathTypeDesc = strBathTypeDesc;
	}

}
