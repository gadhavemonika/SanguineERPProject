package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblsuppliermaster")
@IdClass(clsExciseSupplierMasterModel_ID.class)
public class clsExciseSupplierMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseSupplierMasterModel() {
	}

	public clsExciseSupplierMasterModel(clsExciseSupplierMasterModel_ID objModelID) {
		strSupplierCode = objModelID.getStrSupplierCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSupplierCode", column = @Column(name = "strSupplierCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSupplierCode")
	private String strSupplierCode;

	@Column(name = "intId")
	private long intId;

	// @Column(name = "strSupplierNo")
	// private String strSupplierNo;

	@Column(name = "strSupplierName")
	private String strSupplierName;

	@Column(name = "strAddress")
	private String strAddress;

	@Column(name = "strVATNo")
	private String strVATNo;

	@Column(name = "strTINNo")
	private String strTINNo;

	@Column(name = "strCityCode")
	private String strCityCode;

	@Transient
	private String strCityName;

	@Column(name = "strPINCode")
	private String strPINCode;

	@Column(name = "strContactPerson")
	private String strContactPerson;

	@Column(name = "longTelephoneNo")
	private long longTelephoneNo;

	@Column(name = "strEmailId")
	private String strEmailId;

	@Column(name = "longMobileNo")
	private long longMobileNo;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public String getStrSupplierCode() {
		return strSupplierCode;
	}

	public void setStrSupplierCode(String strSupplierCode) {
		this.strSupplierCode = (String) setDefaultValue(strSupplierCode, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	// public String getStrSupplierNo() {
	// return strSupplierNo;
	// }
	//
	// public void setStrSupplierNo(String strSupplierNo) {
	// this.strSupplierNo = (String) setDefaultValue(strSupplierNo, "NA");
	// }

	public String getStrSupplierName() {
		return strSupplierName;
	}

	public void setStrSupplierName(String strSupplierName) {
		this.strSupplierName = (String) setDefaultValue(strSupplierName, "NA");
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = (String) setDefaultValue(strAddress, "NA");
	}

	public String getStrVATNo() {
		return strVATNo;
	}

	public void setStrVATNo(String strVATNo) {
		this.strVATNo = (String) setDefaultValue(strVATNo, "NA");
	}

	public String getStrTINNo() {
		return strTINNo;
	}

	public void setStrTINNo(String strTINNo) {
		this.strTINNo = (String) setDefaultValue(strTINNo, "NA");
	}

	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = strCityCode;
	}

	public String getStrCityName() {
		return strCityName;
	}

	public void setStrCityName(String strCityName) {
		this.strCityName = strCityName;
	}

	public String getStrPINCode() {
		return strPINCode;
	}

	public void setStrPINCode(String strPINCode) {
		this.strPINCode = (String) setDefaultValue(strPINCode, "NA");
	}

	public String getStrContactPerson() {
		return strContactPerson;
	}

	public void setStrContactPerson(String strContactPerson) {
		this.strContactPerson = (String) setDefaultValue(strContactPerson, "NA");
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = (String) setDefaultValue(strEmailId, "NA");
	}

	public long getLongTelephoneNo() {
		return longTelephoneNo;
	}

	public void setLongTelephoneNo(long longTelephoneNo) {
		this.longTelephoneNo = longTelephoneNo;
	}

	public long getLongMobileNo() {
		return longMobileNo;
	}

	public void setLongMobileNo(long longMobileNo) {
		this.longMobileNo = longMobileNo;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "NA");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
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
