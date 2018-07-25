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
@Table(name = "tblacmaster")
@IdClass(clsWebBooksAccountMasterModel_ID.class)
public class clsWebBooksAccountMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebBooksAccountMasterModel() {
	}

	public clsWebBooksAccountMasterModel(clsWebBooksAccountMasterModel_ID objModelID) {
		strAccountCode = objModelID.getStrAccountCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAccountCode", column = @Column(name = "strAccountCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "intGId", updatable = false, nullable = false)
	private long intGId;

	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strAccountName")
	private String strAccountName;

	@Column(name = "strOperational", columnDefinition = "VARCHAR(5) NOT NULL")
	private String strOperational;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strTaxonPurchase")
	private String strTaxonPurchase;

	@Column(name = "strRRGRN")
	private String strRRGRN;

	@Column(name = "strEmployee")
	private String strEmployee;

	@Column(name = "strTaxonSales")
	private String strTaxonSales;

	@Column(name = "strDebtor")
	private String strDebtor;

	@Column(name = "strGroupCode")
	private String strGroupCode;

	public String getStrGroupName() {
		return strGroupName;
	}

	public void setStrGroupName(String strGroupName) {
		this.strGroupName = strGroupName;
	}

	@Transient
	private String strGroupName;

	@Column(name = "intPreYearGrpCode")
	private String intPreYearGrpCode;

	@Column(name = "intMSGrpCode")
	private String intMSGrpCode;

	@Column(name = "strCashflowCode")
	private String strCashflowCode;

	@Column(name = "strBranch")
	private String strBranch;

	@Column(name = "intOpeningBal")
	private double intOpeningBal;

	

	public double getintOpeningBal() {
		return intOpeningBal;
	}

	public void setintOpeningBal(double intOpeningBal) {
		this.intOpeningBal = intOpeningBal;
	}

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strDeptCode")
	private String strDeptCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strDeduction")
	private String strDeduction;

	@Column(name = "strFBT")
	private String strFBT;

	@Column(name = "strCreditor")
	private String strCreditor;

	@Column(name = "strChequeNo")
	private String strChequeNo;

	@Column(name = "strEmployeeCode")
	private String strEmployeeCode;
	
	@Column(name="strCrDr")
	private String strCrDr;
	@Transient
	private String strEmployeeName;

	
	// Setter-Getter Methods

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = (String) setDefaultValue(strAccountCode, "NA");
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = (String) setDefaultValue(strAccountName, "NA");
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = (String) setDefaultValue(strOperational, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getStrTaxonPurchase() {
		return strTaxonPurchase;
	}

	public void setStrTaxonPurchase(String strTaxonPurchase) {
		this.strTaxonPurchase = (String) setDefaultValue(strTaxonPurchase, "NA");
	}

	public String getStrRRGRN() {
		return strRRGRN;
	}

	public void setStrRRGRN(String strRRGRN) {
		this.strRRGRN = (String) setDefaultValue(strRRGRN, "NA");
	}

	public String getStrEmployee() {
		return strEmployee;
	}

	public void setStrEmployee(String strEmployee) {
		this.strEmployee = (String) setDefaultValue(strEmployee, "NA");
	}

	public String getStrTaxonSales() {
		return strTaxonSales;
	}

	public void setStrTaxonSales(String strTaxonSales) {
		this.strTaxonSales = (String) setDefaultValue(strTaxonSales, "NA");
	}

	public String getStrDebtor() {
		return strDebtor;
	}

	public void setStrDebtor(String strDebtor) {
		this.strDebtor = (String) setDefaultValue(strDebtor, "NA");
	}

	public String getStrGroupCode() {
		return strGroupCode;
	}

	public void setStrGroupCode(String strGroupCode) {
		this.strGroupCode = (String) setDefaultValue(strGroupCode, "NA");
	}

	

	public String getIntPreYearGrpCode() {
		return intPreYearGrpCode;
	}

	public void setIntPreYearGrpCode(String intPreYearGrpCode) {
		this.intPreYearGrpCode = (String) setDefaultValue(intPreYearGrpCode, "NA");
	}

	public String getIntMSGrpCode() {
		return intMSGrpCode;
	}

	public void setIntMSGrpCode(String intMSGrpCode) {
		this.intMSGrpCode = (String) setDefaultValue(intMSGrpCode, "NA");
	}

	public String getStrCashflowCode() {
		return strCashflowCode;
	}

	public void setStrCashflowCode(String strCashflowCode) {
		this.strCashflowCode = (String) setDefaultValue(strCashflowCode, "NA");
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = (String) setDefaultValue(strBranch, "NA");
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

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = (String) setDefaultValue(strDeptCode, "NA");
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

	public String getStrDeduction() {
		return strDeduction;
	}

	public void setStrDeduction(String strDeduction) {
		this.strDeduction = (String) setDefaultValue(strDeduction, "NA");
	}

	public String getStrFBT() {
		return strFBT;
	}

	public void setStrFBT(String strFBT) {
		this.strFBT = (String) setDefaultValue(strFBT, "NA");
	}

	public String getStrCreditor() {
		return strCreditor;
	}

	public void setStrCreditor(String strCreditor) {
		this.strCreditor = (String) setDefaultValue(strCreditor, "NA");
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = (String) setDefaultValue(strChequeNo, "NA");
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

	public String getStrEmployeeCode() {
		return strEmployeeCode;
	}

	public void setStrEmployeeCode(String strEmployeeCode) {
		this.strEmployeeCode = strEmployeeCode;
	}

	public String getStrEmployeeName() {
		return strEmployeeName;
	}

	public void setStrEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = strCrDr;
	}
}
