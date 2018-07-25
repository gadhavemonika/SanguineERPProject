package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tbltphd")
@IdClass(clsTransportPassModel_ID.class)
public class clsTransportPassModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsTransportPassModel() {
	}

	public clsTransportPassModel(clsTransportPassModel_ID objModelID) {
		strTPCode = objModelID.getstrTPCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTPCode", column = @Column(name = "strTPCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Column(name = "strTPCode")
	private String strTPCode;

	@Column(name = "strTPNo")
	private String strTPNo;

	@Column(name = "intId")
	private Long intId;

	@Column(name = "strInvoiceNo")
	private String strInvoiceNo;

	@Column(name = "strTpDate")
	private String strTpDate;

	@Column(name = "strSupplierCode")
	private String strSupplierCode;

	@Column(name = "dblTotalPurchase")
	private Double dblTotalPurchase;

	@Column(name = "dblTotalTax")
	private Double dblTotalTax;

	@Column(name = "dblTotalFees")
	private Double dblTotalFees;

	@Column(name = "dblTotalBill")
	private Double dblTotalBill;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
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

}
