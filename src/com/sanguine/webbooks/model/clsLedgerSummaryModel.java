package com.sanguine.webbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sanguine.base.model.clsBaseModel;

@Entity
@Table(name = "tblledgersummary")
@IdClass(clsLedgerSummaryModel_ID.class)
public class clsLedgerSummaryModel extends clsBaseModel {

	public clsLedgerSummaryModel() {
	}

	public clsLedgerSummaryModel(clsLedgerSummaryModel_ID objModelID) {
		strVoucherNo = objModelID.getStrVoucherNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strVoucherNo", column = @Column(name = "strVoucherNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strBalCrDr", column = @Column(name = "strBalCrDr")) })
	@Column(name = "dteVochDate")
	private String dteVochDate;

	@Column(name = "strVoucherNo")
	private String strVoucherNo;

	@Column(name = "strTransType")
	private String strTransType;

	@Column(name = "strChequeBillNo")
	private String strChequeBillNo;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "dblDebitAmt")
	private double dblDebitAmt;

	@Column(name = "dblCreditAmt")
	private double dblCreditAmt;

	@Column(name = "dblBalanceAmt")
	private double dblBalanceAmt;

	@Column(name = "strBalCrDr")
	private String strBalCrDr;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strTransTypeForOrderBy")
	private String strTransTypeForOrderBy;

	@Column(name = "strUserCode")
	private String strUserCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strDebtorName")
	private String strDebtorName;
	
	public String getDteVochDate() {
		return dteVochDate;
	}

	public void setDteVochDate(String dteVochDate) {
		this.dteVochDate = dteVochDate;
	}

	public String getStrVoucherNo() {
		return strVoucherNo;
	}

	public void setStrVoucherNo(String strVoucherNo) {
		this.strVoucherNo = strVoucherNo;
	}

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrChequeBillNo() {
		return strChequeBillNo;
	}

	public void setStrChequeBillNo(String strChequeBillNo) {
		this.strChequeBillNo = strChequeBillNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public double getDblDebitAmt() {
		return dblDebitAmt;
	}

	public void setDblDebitAmt(double dblDebitAmt) {
		this.dblDebitAmt = dblDebitAmt;
	}

	public double getDblCreditAmt() {
		return dblCreditAmt;
	}

	public void setDblCreditAmt(double dblCreditAmt) {
		this.dblCreditAmt = dblCreditAmt;
	}

	public double getDblBalanceAmt() {
		return dblBalanceAmt;
	}

	public void setDblBalanceAmt(double dblBalanceAmt) {
		this.dblBalanceAmt = dblBalanceAmt;
	}

	public String getStrBalCrDr() {
		return strBalCrDr;
	}

	public void setStrBalCrDr(String strBalCrDr) {
		this.strBalCrDr = strBalCrDr;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrTransTypeForOrderBy() {
		return strTransTypeForOrderBy;
	}

	public void setStrTransTypeForOrderBy(String strTransTypeForOrderBy) {
		this.strTransTypeForOrderBy = strTransTypeForOrderBy;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrDebtorName() {
		return strDebtorName;
	}

	public void setStrDebtorName(String strDebtorName) {
		this.strDebtorName = (String) setDefaultValue(strDebtorName, ""); ;
	}

	
}
