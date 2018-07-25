package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Embeddable
public class clsRequisitionDtlModel implements Serializable {

	@Transient
	private String strPartNo;

	@Transient
	private String strProdName;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "dblUnitPrice")
	private double dblUnitPrice;

	@Column(name = "dblTotalPrice")
	private double dblTotalPrice;

	@Transient
	private double dblPrice;
	@Transient
	private double dblWeight;
	@Transient
	private double AvailStock;
	@Transient
	private double dblReOrderLevel;
	@Transient
	private double dblReOderQty;
	@Transient
	private double dblOrderQty;
	@Transient
	private double OpenReq;

	@Transient
	private String strChecked;

	@Transient
	private String strNonStockableItem;

	@Transient
	private String strUOM;

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblTotalPrice() {
		return dblTotalPrice;
	}

	public void setDblTotalPrice(double dblTotalPrice) {
		this.dblTotalPrice = dblTotalPrice;
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = dblPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public double getAvailStock() {
		return AvailStock;
	}

	public void setAvailStock(double availStock) {
		AvailStock = availStock;
	}

	public double getDblReOrderLevel() {
		return dblReOrderLevel;
	}

	public void setDblReOrderLevel(double dblReOrderLevel) {
		this.dblReOrderLevel = dblReOrderLevel;
	}

	public double getDblReOderQty() {
		return dblReOderQty;
	}

	public void setDblReOderQty(double dblReOderQty) {
		this.dblReOderQty = dblReOderQty;
	}

	public double getDblOrderQty() {
		return dblOrderQty;
	}

	public void setDblOrderQty(double dblOrderQty) {
		this.dblOrderQty = dblOrderQty;
	}

	public double getOpenReq() {
		return OpenReq;
	}

	public void setOpenReq(double OpenReq) {
		this.OpenReq = OpenReq;
	}

	public String getStrChecked() {
		return strChecked;
	}

	public void setStrChecked(String strChecked) {
		this.strChecked = strChecked;
	}

	public String getStrNonStockableItem() {
		return strNonStockableItem;
	}

	public void setStrNonStockableItem(String strNonStockableItem) {
		this.strNonStockableItem = strNonStockableItem;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

}
