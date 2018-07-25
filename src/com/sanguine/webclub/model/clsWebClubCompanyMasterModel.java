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
@Table(name = "tblcompanymaster")
@IdClass(clsWebClubCompanyMasterModel_ID.class)
public class clsWebClubCompanyMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubCompanyMasterModel() {
	}

	public clsWebClubCompanyMasterModel(clsWebClubCompanyMasterModel_ID objModelID) {
		strCompanyCode = objModelID.getStrCompanyCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCompanyCode", column = @Column(name = "strCompanyCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCompanyCode")
	private String strCompanyCode;

	@Column(name = "strCompanyName")
	private String strCompanyName;

	@Column(name = "strAddress1")
	private String strAddress1;

	@Column(name = "strAddress2")
	private String strAddress2;

	@Column(name = "strAddress3")
	private String strAddress3;

	@Column(name = "strLandmark")
	private String strLandmark;

	@Column(name = "strAreaCode")
	private String strAreaCode;

	@Column(name = "strCityCode")
	private String strCityCode;

	@Column(name = "strStateCode")
	private String strStateCode;

	@Column(name = "strRegionCode")
	private String strRegionCode;

	@Column(name = "strCountryCode")
	private String strCountryCode;

	@Column(name = "strPin")
	private String strPin;

	@Column(name = "strTelephone1")
	private String strTelephone1;

	@Column(name = "strTelephone2")
	private String strTelephone2;

	@Column(name = "strFax1")
	private String strFax1;

	@Column(name = "strFax2")
	private String strFax2;

	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strActiveNominee")
	private String strActiveNominee;

	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Column(name = "strCompanyType")
	private String strCompanyType;

	@Column(name = "dblAnnualTrunover")
	private double dblAnnualTrunover;

	@Column(name = "dblCapital")
	private double dblCapital;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "intGId", updatable = false)
	private long intGId;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	// Setter-Getter Methods
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = (String) setDefaultValue(strCompanyCode, "NA");
	}

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = (String) setDefaultValue(strCompanyName, "NA");
	}

	public String getStrAddress1() {
		return strAddress1;
	}

	public void setStrAddress1(String strAddress1) {
		this.strAddress1 = (String) setDefaultValue(strAddress1, "NA");
	}

	public String getStrAddress2() {
		return strAddress2;
	}

	public void setStrAddress2(String strAddress2) {
		this.strAddress2 = (String) setDefaultValue(strAddress2, "NA");
	}

	public String getStrAddress3() {
		return strAddress3;
	}

	public void setStrAddress3(String strAddress3) {
		this.strAddress3 = (String) setDefaultValue(strAddress3, "NA");
	}

	public String getStrLandmark() {
		return strLandmark;
	}

	public void setStrLandmark(String strLandmark) {
		this.strLandmark = (String) setDefaultValue(strLandmark, "NA");
	}

	public String getStrAreaCode() {
		return strAreaCode;
	}

	public void setStrAreaCode(String strAreaCode) {
		this.strAreaCode = (String) setDefaultValue(strAreaCode, "NA");
	}

	public String getStrCityCode() {
		return strCityCode;
	}

	public void setStrCityCode(String strCityCode) {
		this.strCityCode = (String) setDefaultValue(strCityCode, "NA");
	}

	public String getStrStateCode() {
		return strStateCode;
	}

	public void setStrStateCode(String strStateCode) {
		this.strStateCode = (String) setDefaultValue(strStateCode, "NA");
	}

	public String getStrRegionCode() {
		return strRegionCode;
	}

	public void setStrRegionCode(String strRegionCode) {
		this.strRegionCode = (String) setDefaultValue(strRegionCode, "NA");
	}

	public String getStrCountryCode() {
		return strCountryCode;
	}

	public void setStrCountryCode(String strCountryCode) {
		this.strCountryCode = (String) setDefaultValue(strCountryCode, "NA");
	}

	public String getStrPin() {
		return strPin;
	}

	public void setStrPin(String strPin) {
		this.strPin = (String) setDefaultValue(strPin, "NA");
	}

	public String getStrTelephone1() {
		return strTelephone1;
	}

	public void setStrTelephone1(String strTelephone1) {
		this.strTelephone1 = (String) setDefaultValue(strTelephone1, "NA");
	}

	public String getStrTelephone2() {
		return strTelephone2;
	}

	public void setStrTelephone2(String strTelephone2) {
		this.strTelephone2 = (String) setDefaultValue(strTelephone2, "NA");
	}

	public String getStrFax1() {
		return strFax1;
	}

	public void setStrFax1(String strFax1) {
		this.strFax1 = (String) setDefaultValue(strFax1, "NA");
	}

	public String getStrFax2() {
		return strFax2;
	}

	public void setStrFax2(String strFax2) {
		this.strFax2 = (String) setDefaultValue(strFax2, "NA");
	}

	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getStrActiveNominee() {
		return strActiveNominee;
	}

	public void setStrActiveNominee(String strActiveNominee) {
		this.strActiveNominee = (String) setDefaultValue(strActiveNominee, "NA");
	}

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = (String) setDefaultValue(strCategoryCode, "NA");
	}

	public String getStrCompanyType() {
		return strCompanyType;
	}

	public void setStrCompanyType(String strCompanyType) {
		this.strCompanyType = (String) setDefaultValue(strCompanyType, "NA");
	}

	public double getDblAnnualTrunover() {
		return dblAnnualTrunover;
	}

	public void setDblAnnualTrunover(double dblAnnualTrunover) {
		this.dblAnnualTrunover = (Double) setDefaultValue(dblAnnualTrunover, "NA");
	}

	public double getDblCapital() {
		return dblCapital;
	}

	public void setDblCapital(double dblCapital) {
		this.dblCapital = (Double) setDefaultValue(dblCapital, "NA");
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

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
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
