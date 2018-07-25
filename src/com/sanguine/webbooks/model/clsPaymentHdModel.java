package com.sanguine.webbooks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "tblpaymenthd")
@IdClass(clsPaymentModel_ID.class)
public class clsPaymentHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPaymentHdModel() {
	}

	public clsPaymentHdModel(clsPaymentModel_ID objModelID) {
		strVouchNo = objModelID.getStrVouchNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblpaymentdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsPaymentDtl> listPaymentDtlModel = new ArrayList<clsPaymentDtl>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblpaymentdebtordtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsPaymentDebtorDtlModel> listPaymentDebtorDtlModel = new ArrayList<clsPaymentDebtorDtlModel>();

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblpaymentgrndtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsPaymentGRNDtlModel> listPaymentGRNDtlModel = new ArrayList<clsPaymentGRNDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblpaymentScBilldtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsPaymentScBillDtlModel> listPaymentSCDtlModel = new ArrayList<clsPaymentScBillDtlModel>();

	// Variable Declaration
	@Column(name = "strVouchNo")
	private String strVouchNo;

	@Column(name = "strBankCode")
	private String strBankCode;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strSancCode")
	private String strSancCode;

	@Column(name = "strChequeNo")
	private String strChequeNo;

	@Column(name = "dblAmt")
	private double dblAmt;

	@Column(name = "strCrDr")
	private String strCrDr;

	@Column(name = "dteVouchDate")
	private String dteVouchDate;

	@Column(name = "dteChequeDate")
	private String dteChequeDate;

	@Column(name = "intVouchMonth")
	private long intVouchMonth;

	@Column(name = "intVouchNum")
	private long intVouchNum;

	@Column(name = "strTransMode")
	private String strTransMode;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strModuleType")
	private String strModuleType;

	@Column(name = "dteClearence")
	private String dteClearence;

	@Column(name = "intMonth")
	private long intMonth;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strDrawnOn")
	private String strDrawnOn;

	@Column(name = "strBranch")
	private String strBranch;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;
	
	@Transient
	private String accountCode;
	
	@Transient
	private String creditorCode;

	@Transient
	private double closingAmt;
	
	@Column(name = "strCurrency",columnDefinition = "varchar(10) NOT NULL default 'NA'")
	private String strCurrency;
	
	@Column(name = "dblConversion", nullable = false,columnDefinition = "double(18,2) NOT NULL default '1'")
	private double dblConversion;
	
	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = (String) setDefaultValue(strVouchNo, "NA");
	}

	public String getStrBankCode() {
		return strBankCode;
	}

	public void setStrBankCode(String strBankCode) {
		this.strBankCode = (String) setDefaultValue(strBankCode, "NA");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "");
	}

	public String getStrSancCode() {
		return strSancCode;
	}

	public void setStrSancCode(String strSancCode) {
		this.strSancCode = (String) setDefaultValue(strSancCode, "NA");
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = (String) setDefaultValue(strChequeNo, "NA");
	}

	public double getDblAmt() {
		return dblAmt;
	}

	public void setDblAmt(double dblAmt) {
		this.dblAmt = (Double) setDefaultValue(dblAmt, "0.0000");
	}

	public String getStrCrDr() {
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr) {
		this.strCrDr = (String) setDefaultValue(strCrDr, "NA");
	}

	public String getDteVouchDate() {
		return dteVouchDate;
	}

	public void setDteVouchDate(String dteVouchDate) {
		this.dteVouchDate = dteVouchDate;
	}

	public String getDteChequeDate() {
		return dteChequeDate;
	}

	public void setDteChequeDate(String dteChequeDate) {
		this.dteChequeDate = dteChequeDate;
	}

	public long getIntVouchMonth() {
		return intVouchMonth;
	}

	public void setIntVouchMonth(long intVouchMonth) {
		this.intVouchMonth = (Long) setDefaultValue(intVouchMonth, "0");
	}

	public long getIntVouchNum() {
		return intVouchNum;
	}

	public void setIntVouchNum(long intVouchNum) {
		this.intVouchNum = (Long) setDefaultValue(intVouchNum, "0");
	}

	public String getStrTransMode() {
		return strTransMode;
	}

	public void setStrTransMode(String strTransMode) {
		this.strTransMode = (String) setDefaultValue(strTransMode, "NA");
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

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = (String) setDefaultValue(strModuleType, "NA");
	}

	public String getDteClearence() {
		return dteClearence;
	}

	public void setDteClearence(String dteClearence) {
		this.dteClearence = dteClearence;
	}

	public long getIntMonth() {
		return intMonth;
	}

	public void setIntMonth(long intMonth) {
		this.intMonth = (Long) setDefaultValue(intMonth, "0");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getStrDrawnOn() {
		return strDrawnOn;
	}

	public void setStrDrawnOn(String strDrawnOn) {
		this.strDrawnOn = (String) setDefaultValue(strDrawnOn, "NA");
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = (String) setDefaultValue(strBranch, "NA");
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

	public List<clsPaymentDtl> getListPaymentDtlModel() {
		return listPaymentDtlModel;
	}

	public void setListPaymentDtlModel(List<clsPaymentDtl> listPaymentDtlModel) {
		this.listPaymentDtlModel = listPaymentDtlModel;
	}

	public List<clsPaymentDebtorDtlModel> getListPaymentDebtorDtlModel() {
		return listPaymentDebtorDtlModel;
	}

	public void setListPaymentDebtorDtlModel(List<clsPaymentDebtorDtlModel> listPaymentDebtorDtlModel) {
		this.listPaymentDebtorDtlModel = listPaymentDebtorDtlModel;
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

	public List<clsPaymentGRNDtlModel> getListPaymentGRNDtlModel() {
		return listPaymentGRNDtlModel;
	}

	public void setListPaymentGRNDtlModel(List<clsPaymentGRNDtlModel> listPaymentGRNDtlModel) {
		this.listPaymentGRNDtlModel = listPaymentGRNDtlModel;
	}

	public List<clsPaymentScBillDtlModel> getListPaymentSCDtlModel() {
		return listPaymentSCDtlModel;
	}

	public void setListPaymentSCDtlModel(List<clsPaymentScBillDtlModel> listPaymentSCDtlModel) {
		this.listPaymentSCDtlModel = listPaymentSCDtlModel;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getCreditorCode() {
		return creditorCode;
	}

	public void setCreditorCode(String creditorCode) {
		this.creditorCode = creditorCode;
	}

	public double getClosingAmt() {
		return closingAmt;
	}

	public void setClosingAmt(double closingAmt) {
		this.closingAmt = closingAmt;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");;;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion =  (double) setDefaultValue(dblConversion,1.0);
	}

}
