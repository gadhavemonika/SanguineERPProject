package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblprofilesource")
@IdClass(clsWebClubProfileMasterModel_ID.class)
public class clsWebClubProfileMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsWebClubProfileMasterModel() {
	}

	public clsWebClubProfileMasterModel(clsWebClubProfileMasterModel_ID objModelID) {
		strProfileCode = objModelID.getStrProfileCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strProfileCode", column = @Column(name = "strProfileCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strProfileCode")
	private String strProfileCode;

	@Column(name = "intId")
	public long intId;

	@Column(name = "strProfileDesc")
	private String strProfileDesc;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModifiedDate")
	private String dteLastModifiedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrProfileCode() {
		return strProfileCode;
	}

	public void setStrProfileCode(String strProfileCode) {
		this.strProfileCode = strProfileCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrProfileDesc() {
		return strProfileDesc;
	}

	public void setStrProfileDesc(String strProfileDesc) {
		this.strProfileDesc = strProfileDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModifiedDate() {
		return dteLastModifiedDate;
	}

	public void setDteLastModifiedDate(String dteLastModifiedDate) {
		this.dteLastModifiedDate = dteLastModifiedDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}
}
