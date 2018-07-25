package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblsalesreturnhd")
@IdClass(clsSalesReturnHdModel_ID.class)
public class clsSalesReturnHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSalesReturnHdModel() {
	}

	public clsSalesReturnHdModel(clsSalesReturnHdModel_ID objModelID) {
		strSRCode = objModelID.getStrSRCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSRCode", column = @Column(name = "strSRCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSRCode")
	private String strSRCode;

	@Column(name = "dteSRDate")
	private String dteSRDate;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strDCCode")
	private String strDCCode;

	@Column(name = "strCustCode")
	private String strCustCode;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "dblTotalAmt")
	private String dblTotalAmt;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblTaxAmt")
	private double dblTaxAmt;
	
	@Column(name="dblDiscAmt")
	private double dblDiscAmt;
	
	@Column(name="dblDiscPer")
	private double dblDiscPer;
	
	
	@Column(name = "strCurrency",columnDefinition = "VARCHAR(50) NOT NULL DEFAULT 'NA'")
	private String strCurrency;
	
	@Column(name = "dblConversion", nullable = false,columnDefinition = "DECIMAL(18,2) NOT NULL DEFAULT '1.00'")
	private double dblConversion;
	

	@Column(name = "strJVNo",columnDefinition = "VARCHAR(50) NOT NULL default ''")
	private String strJVNo;
	
	// Getter and Setter

	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = strSRCode;
	}

	public String getDteSRDate() {
		return dteSRDate;
	}

	public void setDteSRDate(String dteSRDate) {
		this.dteSRDate = dteSRDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrDCCode() {
		return strDCCode;
	}

	public void setStrDCCode(String strDCCode) {
		this.strDCCode = strDCCode;
	}

	public String getStrCustCode() {
		return strCustCode;
	}

	public void setStrCustCode(String strCustCode) {
		this.strCustCode = strCustCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(String dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
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

	public double getDblTaxAmt() {
		return dblTaxAmt;
	}

	public void setDblTaxAmt(double dblTaxAmt) {
		this.dblTaxAmt = dblTaxAmt;
	}

	public double getDblDiscAmt() {
		return dblDiscAmt;
	}

	public void setDblDiscAmt(double dblDiscAmt) {
		this.dblDiscAmt = dblDiscAmt;
	}

	public double getDblDiscPer() {
		return dblDiscPer;
	}

	public void setDblDiscPer(double dblDiscPer) {
		this.dblDiscPer = dblDiscPer;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrJVNo() {
		return strJVNo;
	}

	public void setStrJVNo(String strJVNo) {
		this.strJVNo = strJVNo;
	}
	
	

}
