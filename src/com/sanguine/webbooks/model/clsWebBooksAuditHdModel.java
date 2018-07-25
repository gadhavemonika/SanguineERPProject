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

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.model.clsAuditDtlModel;

@Entity
@Table(name = "tblaudithd")
@IdClass(clsWebBooksAuditModel_ID.class)
public class clsWebBooksAuditHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebBooksAuditHdModel() {
	}

	public clsWebBooksAuditHdModel(clsWebBooksAuditModel_ID objModelID) {
		strTransNo = objModelID.getStrTransNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblauditdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strTransNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransNo", column = @Column(name = "strTransNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsWebBooksAuditDtlModel> listAuditDtlModel = new ArrayList<clsWebBooksAuditDtlModel>();

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblauditdebtordtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strTransNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransNo", column = @Column(name = "strTransNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsWebBooksAuditDebtorDtlModel> listAuditDebtorDtlModel = new ArrayList<clsWebBooksAuditDebtorDtlModel>();

	// Variable Declaration
	@Column(name = "strTransNo")
	private String strTransNo;

	@Column(name = "strAccCode")
	private String strAccCode;

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

	@Column(name = "strMasterPOS")
	private String strMasterPOS;

	@Column(name = "strTransType")
	private String strTransType;

	// Setter-Getter Methods
	public String getStrTransNo() {
		return strTransNo;
	}

	public void setStrTransNo(String strTransNo) {
		this.strTransNo = (String) setDefaultValue(strTransNo, "NA");
	}

	public String getStrAccCode() {
		return strAccCode;
	}

	public void setStrAccCode(String strAccCode) {
		this.strAccCode = (String) setDefaultValue(strAccCode, "NA");
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = (String) setDefaultValue(strType, "NA");
	}

	public String getStrDebtorCode() {
		return strDebtorCode;
	}

	public void setStrDebtorCode(String strDebtorCode) {
		this.strDebtorCode = (String) setDefaultValue(strDebtorCode, "NA");
	}

	public String getStrChequeNo() {
		return strChequeNo;
	}

	public void setStrChequeNo(String strChequeNo) {
		this.strChequeNo = (String) setDefaultValue(strChequeNo, "NA");
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

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public String getStrSancCode() {
		return strSancCode;
	}

	public void setStrSancCode(String strSancCode) {
		this.strSancCode = (String) setDefaultValue(strSancCode, "NA");
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

	public String getStrReceiptType() {
		return strReceiptType;
	}

	public void setStrReceiptType(String strReceiptType) {
		this.strReceiptType = (String) setDefaultValue(strReceiptType, "NA");
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

	public String getStrReceivedFrom() {
		return strReceivedFrom;
	}

	public void setStrReceivedFrom(String strReceivedFrom) {
		this.strReceivedFrom = (String) setDefaultValue(strReceivedFrom, "NA");
	}

	public long getIntOnHold() {
		return intOnHold;
	}

	public void setIntOnHold(long intOnHold) {
		this.intOnHold = (Long) setDefaultValue(intOnHold, "0");
	}

	public String getStrMasterPOS() {
		return strMasterPOS;
	}

	public void setStrMasterPOS(String strMasterPOS) {
		this.strMasterPOS = strMasterPOS;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public List<clsWebBooksAuditDtlModel> getListAuditDtlModel() {
		return listAuditDtlModel;
	}

	public void setListAuditDtlModel(List<clsWebBooksAuditDtlModel> listAuditDtlModel) {
		this.listAuditDtlModel = listAuditDtlModel;
	}

	public List<clsWebBooksAuditDebtorDtlModel> getListAuditDebtorDtlModel() {
		return listAuditDebtorDtlModel;
	}

	public void setListAuditDebtorDtlModel(List<clsWebBooksAuditDebtorDtlModel> listAuditDebtorDtlModel) {
		this.listAuditDebtorDtlModel = listAuditDebtorDtlModel;
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
