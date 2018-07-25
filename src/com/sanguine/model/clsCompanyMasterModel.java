package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblcompanymaster")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class clsCompanyMasterModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "strCompanyCode")
	private String strCompanyCode;

	@Id
	@Column(name = "intId")
	private int intId;

	@Column(name = "strCompanyName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCompanyName;

	@Column(name = "strFinYear", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strFinYear;

	@Column(name = "dtStart", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtStart;

	@Column(name = "dtEnd", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtEnd;

	@Column(name = "dtLastTransDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastTransDate;

	@Column(name = "strDbName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strDbName;

	@Column(name = "intUserLicence", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String intUserLicence;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "strPassword", columnDefinition = "VARCHAR(100) NOT NULL default ''")
	private String strPassword;

	@Column(name = "strIndustryType", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strIndustryType;

	@Column(name = "strWebStockModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebStockModule;

	@Column(name = "strWebExciseModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebExciseModule;

	@Column(name = "strWebPMSModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebPMSModule;

	@Column(name = "strWebClubModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebClubModule;

	@Column(name = "strWebBookModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebBookModule;

	@Column(name = "strCRMModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strCRMModule;

	@Column(name = "strWebPOSModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebPOSModule;

	@Column(name = "strWebBookAPGLModule", columnDefinition = "VARCHAR(3) default 'No'")
	private String strWebBookAPGLModule;
	
	@Column(name = "strYear", columnDefinition = "VARCHAR(1) NOT NULL DEFAULT 'A' ")
	private String strYear;

	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

	public int getIntId() {
		return intId;
	}

	public void setIntId(int intId) {
		this.intId = intId;
	}

	public String getStrIndustryType() {
		return strIndustryType;
	}

	public void setStrIndustryType(String strIndustryType) {
		this.strIndustryType = strIndustryType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrCompanyName() {
		return strCompanyName;
	}

	public void setStrCompanyName(String strCompanyName) {
		this.strCompanyName = strCompanyName;
	}

	public String getStrFinYear() {
		return strFinYear;
	}

	public void setStrFinYear(String strFinYear) {
		this.strFinYear = strFinYear;
	}

	public String getDtStart() {
		return dtStart;
	}

	public void setDtStart(String dtStart) {
		this.dtStart = dtStart;
	}

	public String getDtEnd() {
		return dtEnd;
	}

	public void setDtEnd(String dtEnd) {
		this.dtEnd = dtEnd;
	}

	public String getDtLastTransDate() {
		return dtLastTransDate;
	}

	public void setDtLastTransDate(String dtLastTransDate) {
		this.dtLastTransDate = dtLastTransDate;
	}

	public String getStrDbName() {
		return strDbName;
	}

	public void setStrDbName(String strDbName) {
		this.strDbName = strDbName;
	}

	public String getIntUserLicence() {
		return intUserLicence;
	}

	public void setIntUserLicence(String intUserLicence) {
		this.intUserLicence = intUserLicence;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public String getStrWebStockModule() {
		return strWebStockModule;
	}

	public void setStrWebStockModule(String strWebStockModule) {
		this.strWebStockModule = (String) setDefaultValue(strWebStockModule, "No");
	}

	public String getStrWebExciseModule() {
		return strWebExciseModule;
	}

	public void setStrWebExciseModule(String strWebExciseModule) {
		this.strWebExciseModule = (String) setDefaultValue(strWebExciseModule, "No");
	}

	public String getStrWebPMSModule() {
		return strWebPMSModule;
	}

	public void setStrWebPMSModule(String strWebPMSModule) {
		this.strWebPMSModule = (String) setDefaultValue(strWebPMSModule, "No");
	}

	public String getStrWebClubModule() {
		return strWebClubModule;
	}

	public void setStrWebClubModule(String strWebClubModule) {
		this.strWebClubModule = (String) setDefaultValue(strWebClubModule, "No");
	}

	public String getStrWebBookModule() {
		return strWebBookModule;
	}

	public void setStrWebBookModule(String strWebBookModule) {
		this.strWebBookModule = (String) setDefaultValue(strWebBookModule, "No");
	}

	public String getStrCRMModule() {
		return strCRMModule;
	}

	public void setStrCRMModule(String strCRMModule) {
		this.strCRMModule = (String) setDefaultValue(strCRMModule, "No");
	}

	public String getStrWebPOSModule() {
		return strWebPOSModule;
	}

	public void setStrWebPOSModule(String strWebPOSModule) {
		this.strWebPOSModule = (String) setDefaultValue(strWebPOSModule, "No");
	}

	public String getStrWebBookAPGLModule() {
		return strWebBookAPGLModule;
	}

	public void setStrWebBookAPGLModule(String strWebBookAPGLModule) {
		this.strWebBookAPGLModule = (String) setDefaultValue(strWebBookAPGLModule, "No");
	}

	public String getStrYear() {
		return strYear;
	}

	public void setStrYear(String strYear) {
		this.strYear = (String) setDefaultValue(strYear, "A");
	}
	
}
