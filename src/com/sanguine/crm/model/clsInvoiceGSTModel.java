package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblinvtaxgst")
@IdClass(clsInvoiceGSTModel_ID.class)
public class clsInvoiceGSTModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsInvoiceGSTModel() {
	}

	public clsInvoiceGSTModel(clsInvoiceGSTModel_ID clsInvoiceGSTModel_ID) {
		strInvCode = clsInvoiceGSTModel_ID.getStrInvCode();
		strTaxCode = clsInvoiceGSTModel_ID.getStrTaxCode();
		strProdCode = clsInvoiceGSTModel_ID.getStrProdCode();
		strClientCode = clsInvoiceGSTModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strInvCode", column = @Column(name = "strInvCode")), @AttributeOverride(name = "strTaxCode", column = @Column(name = "strTaxCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strInvCode")
	private String strInvCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "dblTaxableAmt")
	private double dblTaxableAmt;

	@Column(name = "dblCGSTPer")
	private double dblCGSTPer;

	@Column(name = "dblCGSTAmt")
	private double dblCGSTAmt;

	@Column(name = "dblSGSTPer")
	private double dblSGSTPer;

	@Column(name = "dblSGSTAmt")
	private double dblSGSTAmt;

	@Column(name = "dblIGSTCGSTPer")
	private double dblIGSTCGSTPer;

	@Column(name = "dblIGSTCGSTAmt")
	private double dblIGSTCGSTAmt;

	@Column(name = "dblIGSTSGSTPer")
	private double dblIGSTSGSTPer;

	@Column(name = "dblIGSTSGSTAmt")
	private double dblIGSTSGSTAmt;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrInvCode() {
		return strInvCode;
	}

	public void setStrInvCode(String strInvCode) {
		this.strInvCode = strInvCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblTaxableAmt() {
		return dblTaxableAmt;
	}

	public void setDblTaxableAmt(double dblTaxableAmt) {
		this.dblTaxableAmt = dblTaxableAmt;
	}

	public double getDblCGSTPer() {
		return dblCGSTPer;
	}

	public void setDblCGSTPer(double dblCGSTPer) {
		this.dblCGSTPer = dblCGSTPer;
	}

	public double getDblCGSTAmt() {
		return dblCGSTAmt;
	}

	public void setDblCGSTAmt(double dblCGSTAmt) {
		this.dblCGSTAmt = dblCGSTAmt;
	}

	public double getDblSGSTPer() {
		return dblSGSTPer;
	}

	public void setDblSGSTPer(double dblSGSTPer) {
		this.dblSGSTPer = dblSGSTPer;
	}

	public double getDblSGSTAmt() {
		return dblSGSTAmt;
	}

	public void setDblSGSTAmt(double dblSGSTAmt) {
		this.dblSGSTAmt = dblSGSTAmt;
	}

	public double getDblIGSTCGSTPer() {
		return dblIGSTCGSTPer;
	}

	public void setDblIGSTCGSTPer(double dblIGSTCGSTPer) {
		this.dblIGSTCGSTPer = dblIGSTCGSTPer;
	}

	public double getDblIGSTCGSTAmt() {
		return dblIGSTCGSTAmt;
	}

	public void setDblIGSTCGSTAmt(double dblIGSTCGSTAmt) {
		this.dblIGSTCGSTAmt = dblIGSTCGSTAmt;
	}

	public double getDblIGSTSGSTPer() {
		return dblIGSTSGSTPer;
	}

	public void setDblIGSTSGSTPer(double dblIGSTSGSTPer) {
		this.dblIGSTSGSTPer = dblIGSTSGSTPer;
	}

	public double getDblIGSTSGSTAmt() {
		return dblIGSTSGSTAmt;
	}

	public void setDblIGSTSGSTAmt(double dblIGSTSGSTAmt) {
		this.dblIGSTSGSTAmt = dblIGSTSGSTAmt;
	}

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

}
