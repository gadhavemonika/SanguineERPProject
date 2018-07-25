package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class clsWorkOrderDtlModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// @Column(name="strWOCode")
	@Transient
	private String strWOCode;

	@Column(name = "strProcessCode")
	private String strProcessCode;

	@Transient
	private String strProcessName;

	@Transient
	private String strProdName;

	@Transient
	private String strProdCode;

	@Column(name = "strStatus")
	private String strStatus;

	@Transient
	private String strUOM;

	@Transient
	private double dblQty;

	@Transient
	private String strStage;

	// private String strClientCode;

	@Transient
	private double dblPendingQty;

	@Transient
	private String strSelectedWO;

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public double getDblPendingQty() {
		return dblPendingQty;
	}

	public void setDblPendingQty(double dblPendingQty) {
		this.dblPendingQty = dblPendingQty;
	}

	// @Column(name="strWOCode")
	// public String getStrWOCode() {
	// return strWOCode;
	// }
	// public void setStrWOCode(String strWOCode) {
	// this.strWOCode = strWOCode;
	// }

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	// @Column(name="strClientCode")
	// public String getStrClientCode() {
	// return strClientCode;
	// }
	// public void setStrClientCode(String strClientCode) {
	// this.strClientCode = strClientCode;
	// }
	public String getStrSelectedWO() {
		return strSelectedWO;
	}

	public void setStrSelectedWO(String strSelectedWO) {
		this.strSelectedWO = strSelectedWO;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrStage() {
		return strStage;
	}

	public void setStrStage(String strStage) {
		this.strStage = strStage;
	}

	public String getStrWOCode() {
		return strWOCode;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

}
