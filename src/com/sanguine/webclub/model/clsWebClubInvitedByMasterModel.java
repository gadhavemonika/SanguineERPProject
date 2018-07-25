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
@Table(name = "tblinvitedby")
@IdClass(clsWebClubInvitedByMasterModel_ID.class)
public class clsWebClubInvitedByMasterModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsWebClubInvitedByMasterModel() {
	}

	public clsWebClubInvitedByMasterModel(clsWebClubInvitedByMasterModel_ID objModelID) {
		strInvCode = objModelID.getStrInvCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "intId")
	public long intId;

	@Column(name = "strInvName")
	private String strInvName;

	@Column(name = "strMecompCode")
	private String strMecompCode;

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

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrInvName() {
		return strInvName;
	}

	public void setStrInvName(String strInvName) {
		this.strInvName = strInvName;
	}

	public String getStrMecompCode() {
		return strMecompCode;
	}

	public void setStrMecompCode(String strMecompCode) {
		this.strMecompCode = strMecompCode;
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
