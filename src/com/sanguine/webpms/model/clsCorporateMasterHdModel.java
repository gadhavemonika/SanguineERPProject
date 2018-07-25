package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblcorporatemaster")
@IdClass(clsCorporateMasterModel_ID.class)
public class clsCorporateMasterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsCorporateMasterHdModel() {
	}

	public clsCorporateMasterHdModel(clsCorporateMasterModel_ID objModelID) {
		strCorporateCode = objModelID.getStrCorporateCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCorporateCode", column = @Column(name = "strCorporateCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strCorporateCode")
	private String strCorporateCode;

	@Column(name = "strCorporateDesc")
	private String strCorporateDesc;

	@Column(name = "strPersonIncharge")
	private String strPersonIncharge;

	@Column(name = "strAddress")
	private String strAddress;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strState")
	private String strState;

	@Column(name = "strCountry")
	private String strCountry;

	@Column(name = "strArea")
	private String strArea;

	@Column(name = "intPinCode")
	private long intPinCode;

	@Column(name = "strSegmentCode")
	private String strSegmentCode;

	@Column(name = "strPlanCode")
	private String strPlanCode;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strAgentType")
	private String strAgentType;

	@Column(name = "strCreditAllowed")
	private String strCreditAllowed;

	@Column(name = "dblCreditLimit")
	private double dblCreditLimit;

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

	@Column(name = "lngTelephoneNo")
	private Long lngTelephoneNo;

	@Column(name = "lngMobileNo")
	private Long lngMobileNo;

	@Column(name = "lngFax")
	private Long lngFax;

	@Column(name = "dblDiscountPer")
	private double dblDiscountPer;

	// Setter-Getter Methods
	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = (String) setDefaultValue(strCorporateCode, "NA");
	}

	public String getStrCorporateDesc() {
		return strCorporateDesc;
	}

	public void setStrCorporateDesc(String strCorporateDesc) {
		this.strCorporateDesc = (String) setDefaultValue(strCorporateDesc, "NA");
	}

	public String getStrPersonIncharge() {
		return strPersonIncharge;
	}

	public void setStrPersonIncharge(String strPersonIncharge) {
		this.strPersonIncharge = (String) setDefaultValue(strPersonIncharge, "NA");
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

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = (String) setDefaultValue(strArea, "NA");
	}

	public long getIntPinCode() {
		return intPinCode;
	}

	public void setIntPinCode(long intPinCode) {
		this.intPinCode = (Long) setDefaultValue(intPinCode, "0");
	}

	public String getStrSegmentCode() {
		return strSegmentCode;
	}

	public void setStrSegmentCode(String strSegmentCode) {
		this.strSegmentCode = (String) setDefaultValue(strSegmentCode, "NA");
	}

	public String getStrPlanCode() {
		return strPlanCode;
	}

	public void setStrPlanCode(String strPlanCode) {
		this.strPlanCode = (String) setDefaultValue(strPlanCode, "NA");
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "NA");
	}

	public String getStrAgentType() {
		return strAgentType;
	}

	public void setStrAgentType(String strAgentType) {
		this.strAgentType = (String) setDefaultValue(strAgentType, "NA");
	}

	public String getStrCreditAllowed() {
		return strCreditAllowed;
	}

	public void setStrCreditAllowed(String strCreditAllowed) {
		this.strCreditAllowed = (String) setDefaultValue(strCreditAllowed, "NA");
	}

	public double getDblCreditLimit() {
		return dblCreditLimit;
	}

	public void setDblCreditLimit(double dblCreditLimit) {
		this.dblCreditLimit = (Double) setDefaultValue(dblCreditLimit, "0.0000");
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

	public double getDblDiscountPer() {
		return dblDiscountPer;
	}

	public void setDblDiscountPer(double dblDiscountPer) {
		this.dblDiscountPer = (double) setDefaultValue(dblDiscountPer, "0.00");
	}

	public Long getLngFax() {
		return lngFax;
	}

	public void setLngFax(Long lngFax) {
		this.lngFax = lngFax;
	}

	public Long getLngTelephoneNo() {
		return lngTelephoneNo;
	}

	public void setLngTelephoneNo(Long lngTelephoneNo) {
		this.lngTelephoneNo = lngTelephoneNo;
	}

	public Long getLngMobileNo() {
		return lngMobileNo;
	}

	public void setLngMobileNo(Long lngMobileNo) {
		this.lngMobileNo = lngMobileNo;
	}

}
