package com.sanguine.webpms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbldepartmentmaster")
public class clsDepartmentMasterModel {

	@Id
	@Column(name = "strDeptCode")
	private String strDeptCode;

	@Column(name = "strDeptDesc")
	private String strDeptDesc;

	@Column(name = "strOperational")
	private String strOperational;

	@Column(name = "strRevenueProducing")
	private String strRevenueProducing;

	@Column(name = "strDiscount")
	private String strDiscount;

	@Column(name = "strDeactivate")
	private String strDeactivate;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = strDeptCode;
	}

	public String getStrDeptDesc() {
		return strDeptDesc;
	}

	public void setStrDeptDesc(String strDeptDesc) {
		this.strDeptDesc = strDeptDesc;
	}

	public String getStrRevenueProducing() {
		return strRevenueProducing;
	}

	public void setStrRevenueProducing(String strRevenueProducing) {
		this.strRevenueProducing = strRevenueProducing;
	}

	public String getStrDiscount() {
		return strDiscount;
	}

	public void setStrDiscount(String strDiscount) {
		this.strDiscount = strDiscount;
	}

	public String getStrDeactivate() {
		return strDeactivate;
	}

	public void setStrDeactivate(String strDeactivate) {
		this.strDeactivate = strDeactivate;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
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

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

}
