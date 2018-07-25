package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSSendBulkSMSBean {

	private String strCustomerType;

	private String strArea;

	private String strDob;

	private String strTestMobileNo;

	private String strSms;

	private String strSelectFile;

	private List<clsPOSCustomerSMSSendDtl> listSmsDtl;

	public String getStrCustomerType() {
		return strCustomerType;
	}

	public void setStrCustomerType(String strCustomerType) {
		this.strCustomerType = strCustomerType;
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = strArea;
	}

	public String getStrDob() {
		return strDob;
	}

	public void setStrDob(String strDob) {
		this.strDob = strDob;
	}

	public String getStrTestMobileNo() {
		return strTestMobileNo;
	}

	public void setStrTestMobileNo(String strTestMobileNo) {
		this.strTestMobileNo = strTestMobileNo;
	}

	public String getStrSms() {
		return strSms;
	}

	public void setStrSms(String strSms) {
		this.strSms = strSms;
	}

	public String getStrSelectFile() {
		return strSelectFile;
	}

	public void setStrSelectFile(String strSelectFile) {
		this.strSelectFile = strSelectFile;
	}

	public List<clsPOSCustomerSMSSendDtl> getListSmsDtl() {
		return listSmsDtl;
	}

	public void setListSmsDtl(List<clsPOSCustomerSMSSendDtl> listSmsDtl) {
		this.listSmsDtl = listSmsDtl;
	}

}
