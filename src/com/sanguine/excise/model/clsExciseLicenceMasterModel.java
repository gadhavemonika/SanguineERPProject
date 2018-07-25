package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbllicencemaster")
@IdClass(clsExciseLicenceMasterModel_ID.class)
public class clsExciseLicenceMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseLicenceMasterModel() {
	}

	public clsExciseLicenceMasterModel(clsExciseLicenceMasterModel_ID objModelID) {
		strLicenceCode = objModelID.getStrLicenceCode();
		strClientCode = objModelID.getStrClientCode();
		strPropertyCode = objModelID.getStrPropertyCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strLicenceCode", column = @Column(name = "strLicenceCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")) })
	// Variable Declaration
	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Column(name = "intId")
	private Long intId;

	@Column(name = "strLicenceNo")
	private String strLicenceNo;

	@Column(name = "strLicenceName")
	private String strLicenceName;

	@Column(name = "strAddress1")
	private String strAddress1;

	@Column(name = "strAddress2")
	private String strAddress2;

	@Column(name = "strAddress3")
	private String strAddress3;

	@Column(name = "strExternalCode")
	private String strExternalCode;

	@Column(name = "strEmailId")
	private String strEmailId;

	@Column(name = "strBusinessCode")
	private String strBusinessCode;

	@Column(name = "strVATNo")
	private String strVATNo;

	@Column(name = "strTINNo")
	private String strTINNo;

	@Column(name = "strCity")
	private String strCity;

	@Column(name = "strContactPerson")
	private String strContactPerson;

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

	@Column(name = "longMobileNo")
	private Long longMobileNo;

	@Column(name = "strPINCode")
	private String strPINCode;

	@Column(name = "longTelephoneNo")
	private Long longTelephoneNo;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strMonthEnd")
	private String strMonthEnd;

	// @Column(name="strUserDetail")
	// private String strUserDetail;

	// Setter-Getter Methods

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrLicenceNo() {
		return strLicenceNo;
	}

	public void setStrLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}

	public String getStrLicenceName() {
		return strLicenceName;
	}

	public void setStrLicenceName(String strLicenceName) {
		this.strLicenceName = strLicenceName;
	}

	public String getStrAddress1() {
		return strAddress1;
	}

	public void setStrAddress1(String strAddress1) {
		this.strAddress1 = strAddress1;
	}

	public String getStrAddress2() {
		return strAddress2;
	}

	public void setStrAddress2(String strAddress2) {
		this.strAddress2 = strAddress2;
	}

	public String getStrAddress3() {
		return strAddress3;
	}

	public void setStrAddress3(String strAddress3) {
		this.strAddress3 = strAddress3;
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	public String getStrEmailId() {
		return strEmailId;
	}

	public void setStrEmailId(String strEmailId) {
		this.strEmailId = strEmailId;
	}

	public String getStrBusinessCode() {
		return strBusinessCode;
	}

	public void setStrBusinessCode(String strBusinessCode) {
		this.strBusinessCode = strBusinessCode;
	}

	public String getStrVATNo() {
		return strVATNo;
	}

	public void setStrVATNo(String strVATNo) {
		this.strVATNo = strVATNo;
	}

	public String getStrTINNo() {
		return strTINNo;
	}

	public void setStrTINNo(String strTINNo) {
		this.strTINNo = strTINNo;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public String getStrContactPerson() {
		return strContactPerson;
	}

	public void setStrContactPerson(String strContactPerson) {
		this.strContactPerson = strContactPerson;
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

	public Long getLongMobileNo() {
		return longMobileNo;
	}

	public void setLongMobileNo(Long longMobileNo) {
		this.longMobileNo = longMobileNo;
	}

	public String getStrPINCode() {
		return strPINCode;
	}

	public void setStrPINCode(String strPINCode) {
		this.strPINCode = strPINCode;
	}

	public Long getLongTelephoneNo() {
		return longTelephoneNo;
	}

	public void setLongTelephoneNo(Long longTelephoneNo) {
		this.longTelephoneNo = longTelephoneNo;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
	}

	// public String getStrUserDetail() {
	// return strUserDetail;
	// }
	//
	// public void setStrUserDetail(String strUserDetail) {
	// this.strUserDetail = strUserDetail;
	// }

}
