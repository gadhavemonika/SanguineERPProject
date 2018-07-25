package com.sanguine.webbooks.apgl.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.apgl.model.clsSundaryCrBillDtlModel;
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillGRNDtlModel;

public class clsSuppGRNBillBean {
	// Variable Declaration
	private String strVoucherNo;

	private String strSuppCode;

	private String strBillNo;

	private String dteBillDate;

	private String dteDueDate;

	private double dblTotalAmount;

	private String strModuleType;

	private String dteVoucherDate;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strPropertyCode;

	private String strNarration;

	private long intVouchNum;

	private long intVoucherMonth;

	private String strSuppName;

	private String strAcCode;

	private String strAcName;

	private String strCreditorCode;

	private String strCreditorName;

	private List<clsSundaryCrBillDtlModel> listSGBillAccDtl = new ArrayList<clsSundaryCrBillDtlModel>();

	private List<clsSundaryCrBillGRNDtlModel> listSGBillGRNDtl = new ArrayList<clsSundaryCrBillGRNDtlModel>();

	// Setter-Getter Methods
	public String getStrVoucherNo() {
		return strVoucherNo;
	}

	public void setStrVoucherNo(String strVoucherNo) {
		this.strVoucherNo = strVoucherNo;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getDteDueDate() {
		return dteDueDate;
	}

	public void setDteDueDate(String dteDueDate) {
		this.dteDueDate = dteDueDate;
	}

	public double getDblTotalAmount() {
		return dblTotalAmount;
	}

	public void setDblTotalAmount(double dblTotalAmount) {
		this.dblTotalAmount = dblTotalAmount;
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

	public String getDteVoucherDate() {
		return dteVoucherDate;
	}

	public void setDteVoucherDate(String dteVoucherDate) {
		this.dteVoucherDate = dteVoucherDate;
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

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public long getIntVouchNum() {
		return intVouchNum;
	}

	public void setIntVouchNum(long intVouchNum) {
		this.intVouchNum = intVouchNum;
	}

	public long getIntVoucherMonth() {
		return intVoucherMonth;
	}

	public void setIntVoucherMonth(long intVoucherMonth) {
		this.intVoucherMonth = intVoucherMonth;
	}

	public List<clsSundaryCrBillGRNDtlModel> getListSGBillGRNDtl() {
		return listSGBillGRNDtl;
	}

	public void setListSGBillGRNDtl(List<clsSundaryCrBillGRNDtlModel> listSGBillGRNDtl) {
		this.listSGBillGRNDtl = listSGBillGRNDtl;
	}

	public List<clsSundaryCrBillDtlModel> getListSGBillAccDtl() {
		return listSGBillAccDtl;
	}

	public void setListSGBillAccDtl(List<clsSundaryCrBillDtlModel> listSGBillAccDtl) {
		this.listSGBillAccDtl = listSGBillAccDtl;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getStrAcCode() {
		return strAcCode;
	}

	public void setStrAcCode(String strAcCode) {
		this.strAcCode = strAcCode;
	}

	public String getStrAcName() {
		return strAcName;
	}

	public void setStrAcName(String strAcName) {
		this.strAcName = strAcName;
	}

	public String getStrCreditorCode() {
		return strCreditorCode;
	}

	public void setStrCreditorCode(String strCreditorCode) {
		this.strCreditorCode = strCreditorCode;
	}

	public String getStrCreditorName() {
		return strCreditorName;
	}

	public void setStrCreditorName(String strCreditorName) {
		this.strCreditorName = strCreditorName;
	}

}
