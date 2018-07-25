package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblacholdermaster")
@IdClass(clsAccountHolderMasterModel_ID.class)
public class clsAccountHolderMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsAccountHolderMasterModel() {
	}

	public clsAccountHolderMasterModel(clsAccountHolderMasterModel_ID objModelID) {
		strACHolderCode = objModelID.getStrACHolderCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strACHolderCode", column = @Column(name = "strACHolderCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strACHolderCode")
	private String strACHolderCode;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	@Column(name = "strACHolderName")
	private String strACHolderName;

	@Column(name = "strDesignation")
	private String strDesignation;

	@Column(name = "intMobileNumber")
	private String intMobileNumber;

	@Column(name = "strEmailId")
	private String strEmailId;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrACHolderCode() {
		return strACHolderCode;
	}

	public void setStrACHolderCode(String strACHolderCode) {
		this.strACHolderCode = (String) setDefaultValue(strACHolderCode, "NA");
	}

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = (Long) setDefaultValue(intGId, "NA");
	}

	public String getStrACHolderName() {
		return strACHolderName;
	}

	public void setStrACHolderName(String strACHolderName) {
		this.strACHolderName = (String) setDefaultValue(strACHolderName, "NA");
	}

	public String getStrDesignation() {
		return strDesignation;
	}

	public void setStrDesignation(String strDesignation) {
		this.strDesignation = (String) setDefaultValue(strDesignation, "NA");
	}

	public String getIntMobileNumber() {
		return intMobileNumber;
	}

	public void setIntMobileNumber(String intMobileNumber) {
		this.intMobileNumber = (String) setDefaultValue(intMobileNumber, "NA");
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = (String) setDefaultValue(strEmailId, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
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
