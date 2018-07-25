package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblcurrentstock")
public class clsCurrentStockModel {
	@Id
	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strProdName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strProdName;

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocCode;

	@Column(name = "dblOpeningStk", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblOpeningStk;

	@Column(name = "dblGRN", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblGRN;

	@Column(name = "dblSCGRN", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblSCGRN;

	@Column(name = "dblStkTransIn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblStkTransIn;

	@Column(name = "dblStkAdjIn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblStkAdjIn;

	@Column(name = "dblMISIn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblMISIn;

	@Column(name = "dblQtyProduced", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQtyProduced;

	@Column(name = "dblSalesReturn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblSalesReturn;

	@Column(name = "dblPurchaseReturn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblPurchaseReturn;

	@Column(name = "dblDeliveryNote", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblDeliveryNote;

	@Column(name = "dblStkTransOut", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblStkTransOut;

	@Column(name = "dblStkAdjOut", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblStkAdjOut;

	@Column(name = "dblMISOut", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblMISOut;

	@Column(name = "dblQtyConsumed", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblQtyConsumed;

	@Column(name = "dblSales", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblSales;

	@Column(name = "dblMaterialReturnOut", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblMaterialReturnOut;

	@Column(name = "dblMaterialReturnIn", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblMaterialReturnIn;

	@Column(name = "dblClosingStk", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblClosingStk;

	@Column(name = "dblValue", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private double dblValue;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "strUserCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCode;

	@Column(name = "strLastGRNDate_Qty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private String strLastGRNDate_Qty;

	@Column(name = "strLastISDate_Qty", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private String strLastISDate_Qty;

	@Column(name = "dblPercentage", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.0000'")
	private String dblPercentage;

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblOpeningStk() {
		return dblOpeningStk;
	}

	public void setDblOpeningStk(double dblOpeningStk) {
		this.dblOpeningStk = dblOpeningStk;
	}

	public double getDblGRN() {
		return dblGRN;
	}

	public void setDblGRN(double dblGRN) {
		this.dblGRN = dblGRN;
	}

	public double getDblSCGRN() {
		return dblSCGRN;
	}

	public void setDblSCGRN(double dblSCGRN) {
		this.dblSCGRN = dblSCGRN;
	}

	public double getDblStkTransIn() {
		return dblStkTransIn;
	}

	public void setDblStkTransIn(double dblStkTransIn) {
		this.dblStkTransIn = dblStkTransIn;
	}

	public double getDblStkAdjIn() {
		return dblStkAdjIn;
	}

	public void setDblStkAdjIn(double dblStkAdjIn) {
		this.dblStkAdjIn = dblStkAdjIn;
	}

	public double getDblMISIn() {
		return dblMISIn;
	}

	public void setDblMISIn(double dblMISIn) {
		this.dblMISIn = dblMISIn;
	}

	public double getDblQtyProduced() {
		return dblQtyProduced;
	}

	public void setDblQtyProduced(double dblQtyProduced) {
		this.dblQtyProduced = dblQtyProduced;
	}

	public double getDblSalesReturn() {
		return dblSalesReturn;
	}

	public void setDblSalesReturn(double dblSalesReturn) {
		this.dblSalesReturn = dblSalesReturn;
	}

	public double getDblPurchaseReturn() {
		return dblPurchaseReturn;
	}

	public void setDblPurchaseReturn(double dblPurchaseReturn) {
		this.dblPurchaseReturn = dblPurchaseReturn;
	}

	public double getDblDeliveryNote() {
		return dblDeliveryNote;
	}

	public void setDblDeliveryNote(double dblDeliveryNote) {
		this.dblDeliveryNote = dblDeliveryNote;
	}

	public double getDblStkTransOut() {
		return dblStkTransOut;
	}

	public void setDblStkTransOut(double dblStkTransOut) {
		this.dblStkTransOut = dblStkTransOut;
	}

	public double getDblStkAdjOut() {
		return dblStkAdjOut;
	}

	public void setDblStkAdjOut(double dblStkAdjOut) {
		this.dblStkAdjOut = dblStkAdjOut;
	}

	public double getDblMISOut() {
		return dblMISOut;
	}

	public void setDblMISOut(double dblMISOut) {
		this.dblMISOut = dblMISOut;
	}

	public double getDblQtyConsumed() {
		return dblQtyConsumed;
	}

	public void setDblQtyConsumed(double dblQtyConsumed) {
		this.dblQtyConsumed = dblQtyConsumed;
	}

	public double getDblSales() {
		return dblSales;
	}

	public void setDblSales(double dblSales) {
		this.dblSales = dblSales;
	}

	public double getDblClosingStk() {
		return dblClosingStk;
	}

	public void setDblClosingStk(double dblClosingStk) {
		this.dblClosingStk = dblClosingStk;
	}

	public double getDblValue() {
		return dblValue;
	}

	public void setDblValue(double dblValue) {
		this.dblValue = dblValue;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public double getDblMaterialReturnOut() {
		return dblMaterialReturnOut;
	}

	public void setDblMaterialReturnOut(double dblMaterialReturnOut) {
		this.dblMaterialReturnOut = dblMaterialReturnOut;
	}

	public double getDblMaterialReturnIn() {
		return dblMaterialReturnIn;
	}

	public void setDblMaterialReturnIn(double dblMaterialReturnIn) {
		this.dblMaterialReturnIn = dblMaterialReturnIn;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrLastGRNDate_Qty() {
		return strLastGRNDate_Qty;
	}

	public void setStrLastGRNDate_Qty(String strLastGRNDate_Qty) {
		this.strLastGRNDate_Qty = strLastGRNDate_Qty;
	}

	public String getStrLastISDate_Qty() {
		return strLastISDate_Qty;
	}

	public void setStrLastISDate_Qty(String strLastISDate_Qty) {
		this.strLastISDate_Qty = strLastISDate_Qty;
	}

	public String getDblPercentage() {
		return dblPercentage;
	}

	public void setDblPercentage(String dblPercentage) {
		this.dblPercentage = dblPercentage;
	}
}
