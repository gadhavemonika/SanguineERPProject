package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblscreturndtl")
public class clsSubContractorGRNModelDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSubContractorGRNModelDtl() {
	}

	// Variable Declaration
	@Column(name = "strSRCode")
	private String strSRCode;

	@GeneratedValue
	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblAcceptQty")
	private double dblAcceptQty;

	@Column(name = "dblQtyRej")
	private double dblQtyRej;

	@Column(name = "dblWeight")
	private double dblWeight;

	@Column(name = "dblPrice")
	private double dblPrice;

	@Column(name = "strProdChar")
	private String strProdChar;

	@Column(name = "strDNProdCode")
	private String strDNProdCode;

	@Column(name = "strDNProdName")
	private String strDNProdName;

	@Column(name = "strDNProcess")
	private String strDNProcess;

	@Column(name = "dblDNQty")
	private double dblDNQty;

	@Column(name = "dblDNWeight")
	private double dblDNWeight;

	@Column(name = "strDNProdChar")
	private String strDNProdChar;

	@Column(name = "dblScrap")
	private double dblScrap;

	@Column(name = "strDNCode")
	private String strDNCode;

	@Column(name = "dblDCQty")
	private double dblDCQty;

	@Column(name = "dblExpQty")
	private double dblExpQty;

	@Column(name = "intExpIndex")
	private long intExpIndex;

	@Column(name = "dblDCWt")
	private double dblDCWt;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "dblQtyRbl")
	private double dblQtyRbl;

	@Column(name = "dblRblWt")
	private double dblRblWt;

	@Column(name = "intIndex")
	private long intIndex;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Transient
	private String strProdName;

	@Transient
	private double dblQtyReceived;

	@Transient
	private double dblTotWt;

	@Transient
	private double dblTotPrice;

	// @Transient
	// private double dblQtyAccept;

	@Transient
	private double dblDiff;

	@Transient
	private double dblDiffPer;

	@Transient
	private double dblWtReceivable;

	// Setter-Getter Methods
	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = (String) setDefaultValue(strSRCode, "NA");
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = (Long) setDefaultValue(intId, "NA");
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = (String) setDefaultValue(strProdCode, "NA");
	}

	// public double getDblAcceptQty(){
	// return dblAcceptQty;
	// }
	// public void setDblQty(double dblAcceptQty){
	// this. dblAcceptQty = (Double) setDefaultValue( dblAcceptQty, 0);
	// }

	public double getDblQtyRej() {
		return dblQtyRej;
	}

	public void setDblQtyRej(double dblQtyRej) {
		this.dblQtyRej = (Double) setDefaultValue(dblQtyRej, 0);
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = (Double) setDefaultValue(dblWeight, 0);
	}

	public double getDblPrice() {
		return dblPrice;
	}

	public void setDblPrice(double dblPrice) {
		this.dblPrice = (Double) setDefaultValue(dblPrice, 0);
	}

	public String getStrProdChar() {
		return strProdChar;
	}

	public void setStrProdChar(String strProdChar) {
		this.strProdChar = (String) setDefaultValue(strProdChar, "NA");
	}

	public String getStrDNProdCode() {
		return strDNProdCode;
	}

	public void setStrDNProdCode(String strDNProdCode) {
		this.strDNProdCode = (String) setDefaultValue(strDNProdCode, "NA");
	}

	public String getStrDNProdName() {
		return strDNProdName;
	}

	public void setStrDNProdName(String strDNProdName) {
		this.strDNProdName = (String) setDefaultValue(strDNProdName, "NA");
	}

	public String getStrDNProcess() {
		return strDNProcess;
	}

	public void setStrDNProcess(String strDNProcess) {
		this.strDNProcess = (String) setDefaultValue(strDNProcess, "NA");
	}

	public double getDblDNQty() {
		return dblDNQty;
	}

	public void setDblDNQty(double dblDNQty) {
		this.dblDNQty = (Double) setDefaultValue(dblDNQty, 0);
	}

	public double getDblDNWeight() {
		return dblDNWeight;
	}

	public void setDblDNWeight(double dblDNWeight) {
		this.dblDNWeight = (Double) setDefaultValue(dblDNWeight, 0);
	}

	public String getStrDNProdChar() {
		return strDNProdChar;
	}

	public void setStrDNProdChar(String strDNProdChar) {
		this.strDNProdChar = (String) setDefaultValue(strDNProdChar, "NA");
	}

	public double getDblScrap() {
		return dblScrap;
	}

	public void setDblScrap(double dblScrap) {
		this.dblScrap = (Double) setDefaultValue(dblScrap, 0);
	}

	public String getStrDNCode() {
		return strDNCode;
	}

	public void setStrDNCode(String strDNCode) {
		this.strDNCode = (String) setDefaultValue(strDNCode, "NA");
	}

	public double getDblDCQty() {
		return dblDCQty;
	}

	public void setDblDCQty(double dblDCQty) {
		this.dblDCQty = (Double) setDefaultValue(dblDCQty, 0);
	}

	public double getDblExpQty() {
		return dblExpQty;
	}

	public void setDblExpQty(double dblExpQty) {
		this.dblExpQty = (Double) setDefaultValue(dblExpQty, 0);
	}

	public long getIntExpIndex() {
		return intExpIndex;
	}

	public void setIntExpIndex(long intExpIndex) {
		this.intExpIndex = (Long) setDefaultValue(intExpIndex, "0");
	}

	public double getDblDCWt() {
		return dblDCWt;
	}

	public void setDblDCWt(double dblDCWt) {
		this.dblDCWt = (Double) setDefaultValue(dblDCWt, 0);
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "NA");
	}

	public double getDblQtyRbl() {
		return dblQtyRbl;
	}

	public void setDblQtyRbl(double dblQtyRbl) {
		this.dblQtyRbl = (Double) setDefaultValue(dblQtyRbl, 0);
	}

	public double getDblRblWt() {
		return dblRblWt;
	}

	public void setDblRblWt(double dblRblWt) {
		this.dblRblWt = (Double) setDefaultValue(dblRblWt, 0);
	}

	public long getIntIndex() {
		return intIndex;
	}

	public void setIntIndex(long intIndex) {
		this.intIndex = (Long) setDefaultValue(intIndex, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
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

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblQtyReceived() {
		return dblQtyReceived;
	}

	public void setDblQtyReceived(double dblQtyReceived) {
		this.dblQtyReceived = dblQtyReceived;
	}

	public double getDblTotWt() {
		return dblTotWt;
	}

	public void setDblTotWt(double dblTotWt) {
		this.dblTotWt = dblTotWt;
	}

	public double getDblTotPrice() {
		return dblTotPrice;
	}

	public void setDblTotPrice(double dblTotPrice) {
		this.dblTotPrice = dblTotPrice;
	}

	// public double getDblQtyAccept() {
	// return dblQtyAccept;
	// }
	// public void setDblQtyAccept(double dblQtyAccept) {
	// this.dblQtyAccept = dblQtyAccept;
	// }
	public double getDblDiff() {
		return dblDiff;
	}

	public void setDblDiff(double dblDiff) {
		this.dblDiff = dblDiff;
	}

	public double getDblDiffPer() {
		return dblDiffPer;
	}

	public void setDblDiffPer(double dblDiffPer) {
		this.dblDiffPer = dblDiffPer;
	}

	public double getDblWtReceivable() {
		return dblWtReceivable;
	}

	public void setDblWtReceivable(double dblWtReceivable) {
		this.dblWtReceivable = dblWtReceivable;
	}

	public double getDblAcceptQty() {
		return dblAcceptQty;
	}

	public void setDblAcceptQty(double dblAcceptQty) {
		this.dblAcceptQty = (Double) setDefaultValue(dblAcceptQty, 0);
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = (Double) setDefaultValue(dblQty, 0);
	}

}
