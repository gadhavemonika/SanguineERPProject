package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblexcisepossale")
public class clsExcisePOSSaleModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsExcisePOSSaleModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strPOSItemCode")
	private String strPOSItemCode;

	@Column(name = "strPOSItemName")
	private String strPOSItemName;

	@Column(name = "intQuantity")
	private Long intQuantity;

	@Column(name = "dblRate")
	private Double dblRate;

	@Column(name = "strPOSCode")
	private String strPOSCode;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private String strBrandName;

	@Column(name = "strBillNo")
	private String strBillNo;

	// Setter-Getter Methods

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = strPOSItemName;
	}

	public Long getIntQuantity() {
		return intQuantity;
	}

	public void setIntQuantity(Long intQuantity) {
		this.intQuantity = intQuantity;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

}
