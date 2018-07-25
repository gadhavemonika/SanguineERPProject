package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSCounterMasterBean {
	// Variable Declaration
	private List<clsPOSMenuHeadBean> listMenuHeadDtl;

	private String strCounterCode;

	private String strCounterName;

	private String strPOSCode;

	private String strUserCreated;

	private String strUserEdited;

	private String dteDateCreated;

	private String dteDateEdited;

	private String strClientCode;

	private String strDataPostFlag;

	private String strOperational;

	private String strUserCode;

	// Setter-Getter Methods
	public String getStrCounterCode() {
		return strCounterCode;
	}

	public void setStrCounterCode(String strCounterCode) {
		this.strCounterCode = strCounterCode;
	}

	public String getStrCounterName() {
		return strCounterName;
	}

	public void setStrCounterName(String strCounterName) {
		this.strCounterName = strCounterName;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
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

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = strDataPostFlag;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public List<clsPOSMenuHeadBean> getListMenuHeadDtl() {
		return listMenuHeadDtl;
	}

	public void setListMenuHeadDtl(List<clsPOSMenuHeadBean> listMenuHeadDtl) {
		this.listMenuHeadDtl = listMenuHeadDtl;
	}

}
