package com.sanguine.webbooks.model;

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
@Table(name = "tblchargemaster")
@IdClass(clsChargeMasterModel_ID.class)
public class clsChargeMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsChargeMasterModel() {
	}

	public clsChargeMasterModel(clsChargeMasterModel_ID objModelID) {
		strChargeCode = objModelID.getStrChargeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strChargeCode", column = @Column(name = "strChargeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGid", updatable = false, nullable = false)
	private long intGid;

	@Column(name = "strChargeCode")
	private String strChargeCode;

	@Column(name = "strChargeName")
	private String strChargeName;

	@Column(name = "strAcctCode")
	private String strAcctCode;

	@Transient
	private String strAccountName;
	
	@Column(name = "strCriteria")
	private String strCriteria;
	
	@Column(name = "strCondition")
	private String strCondition;
	
	@Column(name = "dblConditionValue")
	private double dblConditionValue;

	@Column(name = "strDeptCode")
	private String strDeptCode;

	@Column(name = "strRemark")
	private String strRemark;

	@Column(name = "strStopSupply")
	private String strStopSupply;

	@Column(name = "strActive")
	private String strActive;

	@Column(name = "strOpenCharge")
	private String strOpenCharge;

	@Column(name = "strOutstandInvoise")
	private String strOutstandInvoise;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strAmtUnit")
	private String strAmtUnit;

	@Column(name = "dblAmt")
	private double dblAmt;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "strFreq")
	private String strFreq;

	@Column(name = "strCharge")
	private String strCharge;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "intRecAffected")
	private long intRecAffected;

	@Column(name = "strSql")
	private String strSql;

	@Column(name = "strCriteriaType")
	private String strCriteriaType;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strAllowEditing")
	private String strAllowEditing;

	@Column(name = "strTaxIndicator")
	private String strTaxIndicator;

	@Column(name = "strDimension")
	private String strDimension;

	@Column(name = "strDimensionCode")
	private String strDimensionCode;

	@Column(name = "strDimensionValue")
	private String strDimensionValue;

	@Column(name = "strDimensionValue2")
	private String strDimensionValue2;

	// Setter-Getter Methods
	public long getIntGid() {
		return intGid;
	}

	public void setIntGid(long intGid) {
		this.intGid = (Long) setDefaultValue(intGid, "NA");
	}

	public String getStrChargeCode() {
		return strChargeCode;
	}

	public void setStrChargeCode(String strChargeCode) {
		this.strChargeCode = (String) setDefaultValue(strChargeCode, "NA");
	}

	public String getStrChargeName() {
		return strChargeName;
	}

	public void setStrChargeName(String strChargeName) {
		this.strChargeName = (String) setDefaultValue(strChargeName, "NA");
	}

	public String getStrAcctCode() {
		return strAcctCode;
	}

	public void setStrAcctCode(String strAcctCode) {
		this.strAcctCode = (String) setDefaultValue(strAcctCode, "NA");
	}

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = (String) setDefaultValue(strDeptCode, "NA");
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		this.strRemark = (String) setDefaultValue(strRemark, "NA");
	}

	public String getStrStopSupply() {
		return strStopSupply;
	}

	public void setStrStopSupply(String strStopSupply) {
		this.strStopSupply = (String) setDefaultValue(strStopSupply, "NA");
	}

	public String getStrActive() {
		return strActive;
	}

	public void setStrActive(String strActive) {
		this.strActive = (String) setDefaultValue(strActive, "NA");
	}

	public String getStrOpenCharge() {
		return strOpenCharge;
	}

	public void setStrOpenCharge(String strOpenCharge) {
		this.strOpenCharge = (String) setDefaultValue(strOpenCharge, "NA");
	}

	public String getStrOutstandInvoise() {
		return strOutstandInvoise;
	}

	public void setStrOutstandInvoise(String strOutstandInvoise) {
		this.strOutstandInvoise = (String) setDefaultValue(strOutstandInvoise, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = (Double) setDefaultValue(dblAmt, "NA");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "NA");
	}

	public String getStrFreq() {
		return strFreq;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = (String) setDefaultValue(strFreq, "NA");
	}

	public String getStrCharge() {
		return strCharge;
	}

	public void setStrCharge(String strCharge) {
		this.strCharge = (String) setDefaultValue(strCharge, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
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
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public long getIntRecAffected() {
		return intRecAffected;
	}

	public void setIntRecAffected(long intRecAffected) {
		this.intRecAffected = (Long) setDefaultValue(intRecAffected, "NA");
	}

	public String getStrSql() {
		return strSql;
	}

	public void setStrSql(String strSql) {
		this.strSql = (String) setDefaultValue(strSql, "NA");
	}

	public String getStrCriteriaType() {
		return strCriteriaType;
	}

	public void setStrCriteriaType(String strCriteriaType) {
		this.strCriteriaType = (String) setDefaultValue(strCriteriaType, "NA");
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

	public String getStrAllowEditing() {
		return strAllowEditing;
	}

	public void setStrAllowEditing(String strAllowEditing) {
		this.strAllowEditing = (String) setDefaultValue(strAllowEditing, "NA");
	}

	public String getStrTaxIndicator() {
		return strTaxIndicator;
	}

	public void setStrTaxIndicator(String strTaxIndicator) {
		this.strTaxIndicator = strTaxIndicator;
	}

	public String getStrDimension() {
		return strDimension;
	}

	public void setStrDimension(String strDimension) {
		this.strDimension = (String) setDefaultValue(strDimension, "NA");
	}

	public String getStrDimensionValue() {
		return strDimensionValue;
	}

	public void setStrDimensionValue(String strDimensionValue) {
		this.strDimensionValue = (String) setDefaultValue(strDimensionValue, "NA");
	}

	public String getStrDimensionValue2() {
		return strDimensionValue2;
	}

	public void setStrDimensionValue2(String strDimensionValue2) {
		this.strDimensionValue2 = (String) setDefaultValue(strDimensionValue2, "NA");
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

	public String getStrAmtUnit() {
		return strAmtUnit;
	}

	public void setStrAmtUnit(String strAmtUnit) {
		this.strAmtUnit = strAmtUnit;
	}

	public String getStrDimensionCode() {
		return strDimensionCode;
	}

	public void setStrDimensionCode(String strDimensionCode) {
		this.strDimensionCode = strDimensionCode;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public String getStrCondition() {
		return strCondition;
	}

	public void setStrCondition(String strCondition) {
		this.strCondition = strCondition;
	}

	public double getDblConditionValue() {
		return dblConditionValue;
	}

	public void setDblConditionValue(double dblConditionValue) {
		this.dblConditionValue = dblConditionValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrCriteria() {
		return strCriteria;
	}

	public void setStrCriteria(String strCriteria) {
		this.strCriteria = strCriteria;
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
