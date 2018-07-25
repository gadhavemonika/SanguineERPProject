package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbltallylinkup")
@IdClass(clsTallyLinkUpModel_ID.class)
public class clsTallyLinkUpModel {

	private static final long serialVersionUID = 1L;

	public clsTallyLinkUpModel() {
	}

	public clsTallyLinkUpModel(clsTallyLinkUpModel_ID objModelID) {
		strGroupCode = objModelID.getStrGroupCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strGroupCode", column = @Column(name = "strGroupCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strGroupCode")
	public String strGroupCode;

	@Column(name = "strGroupName")
	public String strGroupName;

	@Column(name = "strGDes")
	public String strGDes;

	@Column(name = "strTallyCode")
	public String strTallyCode;

	@Column(name = "strClientCode")
	public String strClientCode;

	@Column(name = "strUserCreated")
	public String strUserCreated;

	@Column(name = "strUserEdited")
	public String strUserEdited;

	@Column(name = "dteCreatedDate")
	public String dteCreatedDate;

	@Column(name = "dteLastModified")
	public String dteLastModified;

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

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = strGroupCode;
	}

	public String getStrGroupName() {
		return strGroupName;
	}

	public void setStrGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}

	public String getStrGDes() {
		return strGDes;
	}

	public void setStrGDes(String strGDes) {
		this.strGDes = strGDes;
	}

	public String getStrTallyCode() {
		return strTallyCode;
	}

	public void setStrTallyCode(String strTallyCode) {
		this.strTallyCode = strTallyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

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

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

}
