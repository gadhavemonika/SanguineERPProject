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
@Table(name = "tbltaxmaster")
@IdClass(clsPMSTaxMasterModel_ID.class)
public class clsPMSTaxMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPMSTaxMasterModel() {
	}

	public clsPMSTaxMasterModel(clsPMSTaxMasterModel_ID objModelID) {
		strTaxCode = objModelID.getStrTaxCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTaxCode", column = @Column(name = "strTaxCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strTaxDesc")
	private String strTaxDesc;

	@Column(name = "strDeptCode")
	private String strDeptCode;

	@Column(name = "strIncomeHeadCode")
	private String strIncomeHeadCode;

	@Column(name = "strTaxType")
	private String strTaxType;

	@Column(name = "strTaxOnType")
	private String strTaxOnType;

	@Column(name = "dblTaxValue")
	private double dblTaxValue;

	@Column(name = "strTaxOn")
	private String strTaxOn;

	@Column(name = "strDeplomat")
	private String strDeplomat;

	@Column(name = "strLocalOrForeigner")
	private String strLocalOrForeigner;

	@Column(name = "dteValidFrom")
	private String dteValidFrom;

	@Column(name = "dteValidTo")
	private String dteValidTo;

	@Column(name = "strTaxOnTaxCode")
	private String strTaxOnTaxCode;

	@Column(name = "strTaxOnTaxable")
	private String strTaxOnTaxable;

	@Column(name = "strTaxGroupCode")
	private String strTaxGroupCode;

	@Column(name = "strUserCreated", updatable = false, nullable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false, nullable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strAccountCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strAccountCode;

	// Setter-Getter Methods

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = (String) setDefaultValue(strTaxCode, "");
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = (String) setDefaultValue(strTaxDesc, "");
	}

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = (String) setDefaultValue(strDeptCode, "");
	}

	public String getStrIncomeHeadCode() {
		return strIncomeHeadCode;
	}

	public void setStrIncomeHeadCode(String strIncomeHeadCode) {
		this.strIncomeHeadCode = (String) setDefaultValue(strIncomeHeadCode, "");
	}

	public String getStrTaxType() {
		return strTaxType;
	}

	public void setStrTaxType(String strTaxType) {
		this.strTaxType = (String) setDefaultValue(strTaxType, "");
	}

	public double getDblTaxValue() {
		return dblTaxValue;
	}

	public void setDblTaxValue(double dblTaxValue) {
		this.dblTaxValue = (Double) setDefaultValue(dblTaxValue, "0.0000");
	}

	public String getStrTaxOn() {
		return strTaxOn;
	}

	public void setStrTaxOn(String strTaxOn) {
		this.strTaxOn = (String) setDefaultValue(strTaxOn, "");
	}

	public String getStrDeplomat() {
		return strDeplomat;
	}

	public void setStrDeplomat(String strDeplomat) {
		this.strDeplomat = (String) setDefaultValue(strDeplomat, "N");
	}

	public String getStrLocalOrForeigner() {
		return strLocalOrForeigner;
	}

	public void setStrLocalOrForeigner(String strLocalOrForeigner) {
		this.strLocalOrForeigner = (String) setDefaultValue(strLocalOrForeigner, "");
	}

	public String getDteValidFrom() {
		return dteValidFrom;
	}

	public void setDteValidFrom(String dteValidFrom) {
		this.dteValidFrom = dteValidFrom;
	}

	public String getDteValidTo() {
		return dteValidTo;
	}

	public void setDteValidTo(String dteValidTo) {
		this.dteValidTo = dteValidTo;
	}

	public String getStrTaxOnTaxCode() {
		return strTaxOnTaxCode;
	}

	public void setStrTaxOnTaxCode(String strTaxOnTaxCode) {
		this.strTaxOnTaxCode = (String) setDefaultValue(strTaxOnTaxCode, "");
	}

	public String getStrTaxOnTaxable() {
		return strTaxOnTaxable;
	}

	public void setStrTaxOnTaxable(String strTaxOnTaxable) {
		this.strTaxOnTaxable = (String) setDefaultValue(strTaxOnTaxable, "");
	}

	public String getStrTaxGroupCode() {
		return strTaxGroupCode;
	}

	public void setStrTaxGroupCode(String strTaxGroupCode) {
		this.strTaxGroupCode = (String) setDefaultValue(strTaxGroupCode, "");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "");
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = (String) setDefaultValue(strUserEdited, "");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrTaxOnType() {
		return strTaxOnType;
	}

	public void setStrTaxOnType(String strTaxOnType) {
		this.strTaxOnType = strTaxOnType;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "");
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
