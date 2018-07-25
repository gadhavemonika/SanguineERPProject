package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

@Entity
@Table(name = "tblbookermaster")
@IdClass(clsBookerMasterModel_ID.class)
public class clsBookerMasterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBookerMasterHdModel() {
	}

	public clsBookerMasterHdModel(clsBookerMasterModel_ID objModelID) {
		strBookerCode = objModelID.getStrBookerCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBookerCode", column = @Column(name = "strBookerCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBookerCode")
	private String strBookerCode;

	@Column(name = "strBookerName")
	private String strBookerName;

	@Column(name = "strAddress")
	private String strAddress;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strState")
	private String strState;

	@Column(name = "strCountry")
	private String strCountry;

	@Column(name = "strEmailId")
	private String strEmailId;

	@Column(name = "strBlackList")
	private String strBlackList;

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

	@Column(name = "lngMobileNo")
	private String lngMobileNo;

	@Column(name = "lngTelephoneNo")
	private String lngTelephoneNo;

	// Setter-Getter Methods
	public String getStrBookerCode() {
		return strBookerCode;
	}

	public void setStrBookerCode(String strBookerCode) {
		this.strBookerCode = (String) setDefaultValue(strBookerCode, "NA");
	}

	public String getStrBookerName() {
		return strBookerName;
	}

	public void setStrBookerName(String strBookerName) {
		this.strBookerName = (String) setDefaultValue(strBookerName, "NA");
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = (String) setDefaultValue(strAddress, "NA");
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = (String) setDefaultValue(strCity, "NA");
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = (String) setDefaultValue(strState, "NA");
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = (String) setDefaultValue(strCountry, "NA");
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = (String) setDefaultValue(strEmailId, "NA");
	}

	public String getStrBlackList() {
		return strBlackList;
	}

	public void setStrBlackList(String strBlackList) {
		this.strBlackList = (String) setDefaultValue(strBlackList, "NA");
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

	public String getLngMobileNo() {
		return lngMobileNo;
	}

	public void setLngMobileNo(String lngMobileNo) {
		this.lngMobileNo = lngMobileNo;
	}

	public String getLngTelephoneNo() {
		return lngTelephoneNo;
	}

	public void setLngTelephoneNo(String lngTelephoneNo) {
		this.lngTelephoneNo = lngTelephoneNo;
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
