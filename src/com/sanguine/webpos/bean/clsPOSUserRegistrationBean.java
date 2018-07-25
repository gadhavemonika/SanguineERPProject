package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSUserRegistrationBean {
	private String strUserCode;

	private String strUserName;

	private String strUserType;

	private String dteValidDate;

	public String getStrUserType() {
		return strUserType;
	}

	public void setStrUserType(String strUserType) {
		this.strUserType = strUserType;
	}

	private String strPassword;

	private String imgUserIcon;

	private String strFormName;

	private String strModuleName;

	private String strModuleType;

	private List<clsPOSUserAccessBean> listMasterForm;

	public List<clsPOSUserAccessBean> getListMasterForm() {
		return listMasterForm;
	}

	public void setListMasterForm(List<clsPOSUserAccessBean> listMasterForm) {
		this.listMasterForm = listMasterForm;
	}

	public List<clsPOSUserAccessBean> getListTransactionForm() {
		return listTransactionForm;
	}

	public void setListTransactionForm(List<clsPOSUserAccessBean> listTransactionForm) {
		this.listTransactionForm = listTransactionForm;
	}

	public List<clsPOSUserAccessBean> getListReportForm() {
		return listReportForm;
	}

	public void setListReportForm(List<clsPOSUserAccessBean> listReportForm) {
		this.listReportForm = listReportForm;
	}

	public List<clsPOSUserAccessBean> getListUtilitiesForm() {
		return listUtilitiesForm;
	}

	public void setListUtilitiesForm(List<clsPOSUserAccessBean> listUtilitiesForm) {
		this.listUtilitiesForm = listUtilitiesForm;
	}

	private List<clsPOSUserAccessBean> listTransactionForm;
	private List<clsPOSUserAccessBean> listReportForm;
	private List<clsPOSUserAccessBean> listUtilitiesForm;
	private List<clsPOSUserAccessBean> listUsersSelectedForms;

	public List<clsPOSUserAccessBean> getListUsersSelectedForms() {
		return listUsersSelectedForms;
	}

	public void setListUsersSelectedForms(List<clsPOSUserAccessBean> listUsersSelectedForms) {
		this.listUsersSelectedForms = listUsersSelectedForms;
	}

	public String getStrModuleName() {
		return strModuleName;
	}

	public void setStrModuleName(String strModuleName) {
		this.strModuleName = strModuleName;
	}

	public String getStrModuleType() {
		return strModuleType;
	}

	public void setStrModuleType(String strModuleType) {
		this.strModuleType = strModuleType;
	}

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getImgUserIcon() {
		return imgUserIcon;
	}

	public void setImgUserIcon(String imgUserIcon) {
		this.imgUserIcon = imgUserIcon;
	}

	public String getDteValidDate() {
		return dteValidDate;
	}

	public void setDteValidDate(String dteValidDate) {
		this.dteValidDate = dteValidDate;
	}

	public String getStrPassword() {
		return strPassword;
	}

	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

}
