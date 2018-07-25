package com.sanguine.bean;

import java.util.List;

import com.sanguine.webbooks.bean.clsJVDetailsBean;

public class clsUserMasterBean {

	private String strUserCode1, strUserName1, strPassword1, strSuperUser, strType, strRetire, strProperty, strLoginStatus;
	private String strUserCreated, dtCreatedDate, strUserModified, dtLastModified, strSignatureImg, strLocation, strModule, strShowDashBoard;
	private String strReorderLevel;
	private long intid;
	private List<clsUserLocDtlBean> listUserLocDtlBean;

	public String getStrSuperUser() {
		return strSuperUser;
	}

	public void setStrSuperUser(String strSuperUser) {
		this.strSuperUser = strSuperUser;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrRetire() {
		return strRetire;
	}

	public void setStrRetire(String strRetire) {
		this.strRetire = strRetire;
	}

	public String getStrProperty() {
		return strProperty;
	}

	public void setStrProperty(String strProperty) {
		this.strProperty = strProperty;
	}

	public String getStrLoginStatus() {
		return strLoginStatus;
	}

	public void setStrLoginStatus(String strLoginStatus) {
		this.strLoginStatus = strLoginStatus;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrSignatureImg() {
		return strSignatureImg;
	}

	public void setStrSignatureImg(String strSignatureImg) {
		this.strSignatureImg = strSignatureImg;
	}

	public String getStrUserCode1() {
		return strUserCode1;
	}

	public void setStrUserCode1(String strUserCode1) {
		this.strUserCode1 = strUserCode1;
	}

	public String getStrUserName1() {
		return strUserName1;
	}

	public void setStrUserName1(String strUserName1) {
		this.strUserName1 = strUserName1;
	}

	public String getStrPassword1() {
		return strPassword1;
	}

	public void setStrPassword1(String strPassword1) {
		this.strPassword1 = strPassword1;
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public List<clsUserLocDtlBean> getListUserLocDtlBean() {
		return listUserLocDtlBean;
	}

	public void setListUserLocDtlBean(List<clsUserLocDtlBean> listUserLocDtlBean) {
		this.listUserLocDtlBean = listUserLocDtlBean;
	}

	public String getStrLocation() {
		return strLocation;
	}

	public void setStrLocation(String strLocation) {
		this.strLocation = strLocation;
	}

	public String getStrModule() {
		return strModule;
	}

	public void setStrModule(String strModule) {
		this.strModule = strModule;
	}

	public String getStrShowDashBoard() {
		return strShowDashBoard;
	}

	public void setStrShowDashBoard(String strShowDashBoard) {
		this.strShowDashBoard = strShowDashBoard;
	}

	public String getStrReorderLevel() {
		return strReorderLevel;
	}

	public void setStrReorderLevel(String strReorderLevel) {
		this.strReorderLevel = strReorderLevel;
	}

	
}
