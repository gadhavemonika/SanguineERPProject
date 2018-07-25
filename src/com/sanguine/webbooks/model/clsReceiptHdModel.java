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
@Table(name = "tblreceipthd")
@IdClass(clsReceiptModel_ID.class)
public class clsReceiptHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsReceiptHdModel() {
	}

	public clsReceiptHdModel(clsReceiptModel_ID objModelID) {
		strVouchNo = objModelID.getStrVouchNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblreceiptdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsReceiptDtlModel> listReceiptDtlModel = new ArrayList<clsReceiptDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblreceiptdebtordtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsReceiptDebtorDtlModel> listReceiptDebtorDtlModel = new ArrayList<clsReceiptDebtorDtlModel>();

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblreceiptinvdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strVouchNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVouchNo", column = @Column(name = "strVouchNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsReceiptInvDtlModel> listReceiptInvDtlModel = new ArrayList<clsReceiptInvDtlModel>();

	// Variable Declaration
	@Column(name = "strVouchNo")
	private String strVouchNo;

	@Column(name = "strCFCode")
	private String strCFCode;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strDebtorCode")
	private String strDebtorCode;

	@Column(name = "strChequeNo")
	private String strChequeNo;

	@Column(name = "strDrawnOn")
	private String strDrawnOn;

	@Column(name = "strBranch")
	private String strBranch;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strSancCode")
	private String strSancCode;

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

	@Column(name = "strReceiptType")
	private String strReceiptType;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strModuleType")
	private String strModuleType;

	@Column(name = "dteClearence")
	private String dteClearence;

	@Column(name = "strReceivedFrom")
	private String strReceivedFrom;

	@Column(name = "intOnHold")
	private long intOnHold;


	@Column(name = "strCurrency",columnDefinition = "varchar(10) NOT NULL default ''")
	private String strCurrency;
	
	@Column(name = "dblConversion", nullable = false,columnDefinition = "double(18,2) NOT NULL default '1'")
	private double dblConversion;

	
	@Transient
	private String strDebtorName;

	// Setter-Getter Methods
	public String getStrVouchNo() {
		return strVouchNo;
	}

	public void setStrVouchNo(String strVouchNo) {
		this.strVouchNo = (String) setDefaultValue(strVouchNo, "");
	}

	public String getStrCFCode() {
		return strCFCode;
	}

	public void setStrCFCode(String strCFCode) {
		this.strCFCode = (String) setDefaultValue(strCFCode, "");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "");
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "");
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = (String) setDefaultValue(strChequeNo, "");
	}

	public String getStrDrawnOn() {
		return strDrawnOn;
	}

	public void setStrDrawnOn(String strDrawnOn) {
		this.strDrawnOn = (String) setDefaultValue(strDrawnOn, "");
	}

	public String getStrBranch() {
		return strBranch;
	}

	public void setStrBranch(String strBranch) {
		this.strBranch = (String) setDefaultValue(strBranch, "");
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
		this.strSancCode = (String) setDefaultValue(strSancCode, "");
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
		this.strCrDr = (String) setDefaultValue(strCrDr, "");
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
		this.strTransMode = (String) setDefaultValue(strTransMode, "");
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

	public String getStrReceiptType() {
		return strReceiptType;
	}

	public void setStrReceiptType(String strReceiptType) {
		this.strReceiptType = (String) setDefaultValue(strReceiptType, "");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "");
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = (String) setDefaultValue(strModuleType, "");
	}

	public String getDteClearence() {
		return dteClearence;
	}

	public void setDteClearence(String dteClearence) {
		this.dteClearence = dteClearence;
	}

	public String getStrReceivedFrom() {
		return strReceivedFrom;
	}

	public void setStrReceivedFrom(String strReceivedFrom) {
		this.strReceivedFrom = (String) setDefaultValue(strReceivedFrom, "");
	}

	public long getIntOnHold() {
		return intOnHold;
	}

	public void setIntOnHold(long intOnHold) {
		this.intOnHold = (Long) setDefaultValue(intOnHold, "0");
	}

	public List<clsReceiptDtlModel> getListReceiptDtlModel() {
		return listReceiptDtlModel;
	}

	public void setListReceiptDtlModel(List<clsReceiptDtlModel> listReceiptDtlModel) {
		this.listReceiptDtlModel = listReceiptDtlModel;
	}

	public List<clsReceiptDebtorDtlModel> getListReceiptDebtorDtlModel() {
		return listReceiptDebtorDtlModel;
	}

	public void setListReceiptDebtorDtlModel(List<clsReceiptDebtorDtlModel> listReceiptDebtorDtlModel) {
		this.listReceiptDebtorDtlModel = listReceiptDebtorDtlModel;
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

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = strDebtorName;
	}

	public List<clsReceiptInvDtlModel> getListReceiptInvDtlModel() {
		return listReceiptInvDtlModel;
	}

	public void setListReceiptInvDtlModel(List<clsReceiptInvDtlModel> listReceiptInvDtlModel) {
		this.listReceiptInvDtlModel = listReceiptInvDtlModel;
	}


	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = (String) setDefaultValue(strCurrency, "");;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = (double) setDefaultValue(dblConversion,1.0);
	}

}
