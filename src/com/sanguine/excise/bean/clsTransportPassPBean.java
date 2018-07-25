package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsTransportPassDtlModel;

public class clsTransportPassPBean {

	// Variable Declaration

	private String strLicenceCode;

	private String strLicenceNo;

	private String strTPCode;

	private String strTPNo;

	private Long intId;

	private String strInvoiceNo;

	private String strTpDate;

	private String strSupplierCode;

	private String strSupplierName;

	private Double dblTotalPurchase;

	private Double dblTotalTax;

	private Double dblTotalFees;

	private Double dblTotalBill;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private List<clsTransportPassDtlModel> objTPMasterdtlModel;

	// Setter-Getter Methods

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrLicenceNo() {
		return strLicenceNo;
	}

	public void setStrLicenceNo(String strLicenceNo) {
		this.strLicenceNo = strLicenceNo;
	}

	public String getStrTPCode() {
		return strTPCode;
	}

	public void setStrTPCode(String strTPCode) {
		this.strTPCode = strTPCode;
	}

	public String getStrTPNo() {
		return strTPNo;
	}

	public void setStrTPNo(String strTPNo) {
		this.strTPNo = strTPNo;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrInvoiceNo() {
		return strInvoiceNo;
	}

	public void setStrInvoiceNo(String strInvoiceNo) {
		this.strInvoiceNo = strInvoiceNo;
	}

	public String getStrTpDate() {
		return strTpDate;
	}

	public void setStrTpDate(String strTpDate) {
		this.strTpDate = strTpDate;
	}

	public String getStrSupplierCode() {
		return strSupplierCode;
	}

	public void setStrSupplierCode(String strSupplierCode) {
		this.strSupplierCode = strSupplierCode;
	}

	public String getStrSupplierName() {
		return strSupplierName;
	}

	public void setStrSupplierName(String strSupplierName) {
		this.strSupplierName = strSupplierName;
	}

	public Double getDblTotalPurchase() {
		return dblTotalPurchase;
	}

	public void setDblTotalPurchase(Double dblTotalPurchase) {
		this.dblTotalPurchase = dblTotalPurchase;
	}

	public Double getDblTotalTax() {
		return dblTotalTax;
	}

	public void setDblTotalTax(Double dblTotalTax) {
		this.dblTotalTax = dblTotalTax;
	}

	public Double getDblTotalFees() {
		return dblTotalFees;
	}

	public void setDblTotalFees(Double dblTotalFees) {
		this.dblTotalFees = dblTotalFees;
	}

	public Double getDblTotalBill() {
		return dblTotalBill;
	}

	public void setDblTotalBill(Double dblTotalBill) {
		this.dblTotalBill = dblTotalBill;
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

	public List<clsTransportPassDtlModel> getObjTPMasterdtlModel() {
		return objTPMasterdtlModel;
	}

	public void setObjTPMasterdtlModel(List<clsTransportPassDtlModel> objTPMasterdtlModel) {
		this.objTPMasterdtlModel = objTPMasterdtlModel;
	}

}
