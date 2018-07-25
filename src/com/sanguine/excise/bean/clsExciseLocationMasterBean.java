package com.sanguine.excise.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.sanguine.model.clsProductReOrderLevelModel;

public class clsExciseLocationMasterBean {
	private String strLocCode;
	@NotEmpty
	/* @Length(min=3, max=10) */
	private String strLocName;
	private String strLocDesc;
	private String strUserCreated, strUserModified, dtCreatedDate, dtLastModified;
	private String strClientCode;
	private long intId;
	private String strAvlForSale, strActive, strPickable, strReceivable, strExciseNo, strType, strPropertyCode;
	private String strMonthEnd, strExternalCode, strLocPropertyCode;
	private List<clsProductReOrderLevelModel> listReorderLvl;

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrLocDesc() {
		return strLocDesc;
	}

	public void setStrLocDesc(String strLocDesc) {
		this.strLocDesc = strLocDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntid() {
		return intId;
	}

	public void setIntid(long intId) {
		this.intId = intId;
	}

	public String getStrAvlForSale() {
		return strAvlForSale;
	}

	public void setStrAvlForSale(String strAvlForSale) {
		this.strAvlForSale = strAvlForSale;
	}

	public String getStrActive() {
		return strActive;
	}

	public void setStrActive(String strActive) {
		this.strActive = strActive;
	}

	public String getStrPickable() {
		return strPickable;
	}

	public void setStrPickable(String strPickable) {
		this.strPickable = strPickable;
	}

	public String getStrReceivable() {
		return strReceivable;
	}

	public void setStrReceivable(String strReceivable) {
		this.strReceivable = strReceivable;
	}

	public String getStrExciseNo() {
		return strExciseNo;
	}

	public void setStrExciseNo(String strExciseNo) {
		this.strExciseNo = strExciseNo;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	public String getStrLocPropertyCode() {
		return strLocPropertyCode;
	}

	public void setStrLocPropertyCode(String strLocPropertyCode) {
		this.strLocPropertyCode = strLocPropertyCode;
	}

	public List<clsProductReOrderLevelModel> getListReorderLvl() {
		return listReorderLvl;
	}

	public void setListReorderLvl(List<clsProductReOrderLevelModel> listReorderLvl) {
		this.listReorderLvl = listReorderLvl;
	}

}
