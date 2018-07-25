package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblpaymentgrndtl")
public class clsPaymentGRNDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsPaymentGRNDtlModel() {
	}

	/*
	 * @Id
	 * 
	 * @AttributeOverrides({
	 * 
	 * @AttributeOverride(name="strVouchNo",column=@Column(name="strVouchNo")),
	 * 
	 * @AttributeOverride(name="strClientCode",column=@Column(name="strClientCode"
	 * )) })
	 * 
	 * 
	 * @Column(name="strVouchNo") private String strVouchNo;
	 * 
	 * @Column(name="strClientCode") private String strClientCode;
	 */

	@Column(name = "strGRNCode")
	private String strGRNCode;

	@Column(name = "dteGRNDate")
	private String dteGRNDate;

	@Column(name = "dblGRNAmt")
	private double dblGRNAmt;

	@Column(name = "dteGRNDueDate")
	private String dteGRNDueDate;

	@Column(name = "strGRNBIllNo")
	private String strGRNBIllNo;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Transient
	private String strSelected;

	private double dblPayedAmt;

	// Setter-Getter Methods

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = (String) setDefaultValue(strGRNCode, "");
	}

	public String getDteGRNDate() {
		return dteGRNDate;
	}

	public void setDteGRNDate(String dteGRNDate) {
		this.dteGRNDate = dteGRNDate;
	}

	public double getDblGRNAmt() {
		return dblGRNAmt;
	}

	public void setDblGRNAmt(double dblGRNAmt) {
		this.dblGRNAmt = (Double) setDefaultValue(dblGRNAmt, "");
	}

	public String getDteGRNDueDate() {
		return dteGRNDueDate;
	}

	public void setDteGRNDueDate(String dteGRNDueDate) {
		this.dteGRNDueDate = dteGRNDueDate;
	}

	public String getStrGRNBIllNo() {
		return strGRNBIllNo;
	}

	public void setStrGRNBIllNo(String strGRNBIllNo) {
		this.strGRNBIllNo = (String) setDefaultValue(strGRNBIllNo, "");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "");
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

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrSelected() {
		return strSelected;
	}

	public void setStrSelected(String strSelected) {
		this.strSelected = strSelected;
	}

	public double getDblPayedAmt() {
		return dblPayedAmt;
	}

	public void setDblPayedAmt(double dblPayedAmt) {
		this.dblPayedAmt = dblPayedAmt;
	}
	/*
	 * public String getStrVouchNo() { return strVouchNo; } public void
	 * setStrVouchNo(String strVouchNo) { this.strVouchNo = strVouchNo; } public
	 * String getStrClientCode() { return strClientCode; } public void
	 * setStrClientCode(String strClientCode) { this.strClientCode =
	 * strClientCode; }
	 */

}
