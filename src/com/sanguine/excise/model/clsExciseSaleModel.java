package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblexcisesaledata")
@IdClass(clsExciseSalesHdModel_ID.class)
public class clsExciseSaleModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseSaleModel() {
	}

	public clsExciseSaleModel(clsExciseSalesHdModel_ID objModelID) {
		intBillNo = objModelID.getIntBillNo();
		strItemCode = objModelID.getStrItemCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "intBillNo", column = @Column(name = "intBillNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strItemCode", column = @Column(name = "strItemCode")) })
	// Variable Declaration
	@Column(name = "intBillNo")
	private Long intBillNo;

	@Column(name = "dteBillDate")
	private String dteBillDate;

	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Column(name = "strSalesCode")
	private String strSalesCode;

	@Column(name = "strItemCode")
	private String strItemCode;

	@Column(name = "intTotalPeg")
	private Integer intTotalPeg;

	@Column(name = "intQty")
	private Integer intQty;

	@Column(name = "strPermitCode")
	private String strPermitCode;

	@Column(name = "dblTotalAmt")
	private Double dblTotalAmt;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strSourceEntry")
	private String strSourceEntry;

	@Transient
	// @Column(name = "strBillNo")
	private String strBillNo;

	// Setter-Getter Methods

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public Long getIntBillNo() {
		return intBillNo;
	}

	public void setIntBillNo(Long intBillNo) {
		this.intBillNo = intBillNo;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

	public String getStrSalesCode() {
		return strSalesCode;
	}

	public void setStrSalesCode(String strSalesCode) {
		this.strSalesCode = strSalesCode;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public Integer getIntTotalPeg() {
		return intTotalPeg;
	}

	public void setIntTotalPeg(Integer intTotalPeg) {
		this.intTotalPeg = intTotalPeg;
	}

	public Integer getIntQty() {
		return intQty;
	}

	public void setIntQty(Integer intQty) {
		this.intQty = intQty;
	}

	public String getStrPermitCode() {
		return strPermitCode;
	}

	public void setStrPermitCode(String strPermitCode) {
		this.strPermitCode = strPermitCode;
	}

	public Double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(Double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrSourceEntry() {
		return strSourceEntry;
	}

	public void setStrSourceEntry(String strSourceEntry) {
		this.strSourceEntry = strSourceEntry;
	}

}
