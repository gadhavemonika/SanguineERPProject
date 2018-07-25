package com.sanguine.model;

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
@Table(name = "tblpossalesdtl")
@IdClass(clsPOSSalesDtlModel_ID.class)
public class clsPOSSalesDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPOSSalesDtlModel() {
	}

	public clsPOSSalesDtlModel(clsPOSSalesDtlModel_ID objModelID) {
		strPOSItemCode = objModelID.getStrPOSItemCode();
		strClientCode = objModelID.getStrClientCode();
		
		strCostCenterCode = objModelID.getStrCostCenterCode();
		strLocationCode = objModelID.getStrLocationCode();
		strWSItemCode = objModelID.getStrWSItemCode();
		strSACode = objModelID.getStrSACode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPOSItemCode", column = @Column(name = "strPOSItemCode")), 
		@AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), 
		@AttributeOverride(name = "strCostCenterCode", column = @Column(name = "strCostCenterCode")),
		@AttributeOverride(name = "strLocationCode", column = @Column(name = "strLocationCode")),
		@AttributeOverride(name = "strWSItemCode", column = @Column(name = "strWSItemCode")),
		@AttributeOverride(name = "strSACode", column = @Column(name = "strSACode")) })
	// Variable Declaration
	@Column(name = "strPOSItemCode")
	private String strPOSItemCode;

	@Column(name = "strPOSItemName", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strPOSItemName;

	@Column(name = "dblQuantity", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	private double dblQuantity;

	@Column(name = "dblRate", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	private double dblRate;

	@Column(name = "strPOSCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strPOSCode;

	@Column(name = "dteBillDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String dteBillDate;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strClientCode;

	@Column(name = "strWSItemCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strWSItemCode;

	@Column(name = "strSACode")
	private String strSACode;
	
	@Column(name = "strCostCenterCode")
	private String strCostCenterCode;
	
	@Column(name = "strLocationCode")
	private String strLocationCode;
	
	@Column(name = "strCostCenterName")
	private String strCostCenterName;
	
	@Column(name = "dblAmount")
	private double dblAmount;
	
	@Column(name = "dblPercent")
	private double dblPercent;
	
	@Column(name = "dblPercentAmt")
	private double dblPercentAmt;
	
	
	@Transient
	private String strWSItemName;

	@Transient
	private String strProdType;

	// Setter-Getter Methods
	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = (String) setDefaultValue(strPOSItemCode, "NA");
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = (String) setDefaultValue(strPOSItemName, "NA");
	}

	public double getDblQuantity() {
		return dblQuantity;
	}

	public void setDblQuantity(double dblQuantity) {
		this.dblQuantity = (Double) setDefaultValue(dblQuantity, "NA");
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = (Double) setDefaultValue(dblRate, "NA");
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = (String) setDefaultValue(strPOSCode, "NA");
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = (String) setDefaultValue(strWSItemCode, "");
	}

	public String getStrSACode() {
		return strSACode;
	}

	public void setStrSACode(String strSACode) {
		this.strSACode = (String) setDefaultValue(strSACode, "");
	}

	public String getStrWSItemName() {
		return strWSItemName;
	}

	public void setStrWSItemName(String strWSItemName) {
		this.strWSItemName = strWSItemName;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
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

	public String getStrCostCenterCode() {
		return strCostCenterCode;
	}

	public void setStrCostCenterCode(String strCostCenterCode) {
		this.strCostCenterCode = strCostCenterCode;
	}

	public String getStrLocationCode() {
		return strLocationCode;
	}

	public void setStrLocationCode(String strLocationCode) {
		this.strLocationCode = strLocationCode;
	}

	public String getStrCostCenterName() {
		return strCostCenterName;
	}

	public void setStrCostCenterName(String strCostCenterName) {
		this.strCostCenterName = strCostCenterName;
	}

	public double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public double getDblPercent() {
		return dblPercent;
	}

	public void setDblPercent(double dblPercent) {
		this.dblPercent = dblPercent;
	}

	public double getDblPercentAmt() {
		return dblPercentAmt;
	}

	public void setDblPercentAmt(double dblPercentAmt) {
		this.dblPercentAmt = dblPercentAmt;
	}

}
