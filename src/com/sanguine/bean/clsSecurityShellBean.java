package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsTreeMasterModel;

public class clsSecurityShellBean {

	private String strUserCode;
	private String strFormType;
	private String intFormKey;
	private String intFormNo;
	private String strUserCreated;
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;
	private String strDesktop;
	private String strUserName;
	private String strLikeUserCode;
	private String strLikeUserName;
	List<clsTreeMasterModel> listMasterForms;// M
	List<clsTreeMasterModel> listTransactionForms;// T
	List<clsTreeMasterModel> listReportForms;// R
	List<clsTreeMasterModel> listUtilityForms;// L

	public clsSecurityShellBean() {
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrFormType() {
		return strFormType;
	}

	public void setStrFormType(String strFormType) {
		this.strFormType = strFormType;
	}

	public String getIntFormKey() {
		return intFormKey;
	}

	public void setIntFormKey(String intFormKey) {
		this.intFormKey = intFormKey;
	}

	public String getIntFormNo() {
		return intFormNo;
	}

	public void setIntFormNo(String intFormNo) {
		this.intFormNo = intFormNo;
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

	public String getStrDesktop() {
		return strDesktop;
	}

	public void setStrDesktop(String strDesktop) {
		this.strDesktop = strDesktop;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public List<clsTreeMasterModel> getListMasterForms() {
		return listMasterForms;
	}

	public void setListMasterForms(List<clsTreeMasterModel> listMasterForms) {
		this.listMasterForms = listMasterForms;
	}

	public List<clsTreeMasterModel> getListTransactionForms() {
		return listTransactionForms;
	}

	public void setListTransactionForms(List<clsTreeMasterModel> listTransactionForms) {
		this.listTransactionForms = listTransactionForms;
	}

	public List<clsTreeMasterModel> getListReportForms() {
		return listReportForms;
	}

	public void setListReportForms(List<clsTreeMasterModel> listReportForms) {
		this.listReportForms = listReportForms;
	}

	public List<clsTreeMasterModel> getListUtilityForms() {
		return listUtilityForms;
	}

	public void setListUtilityForms(List<clsTreeMasterModel> listUtilityForms) {
		this.listUtilityForms = listUtilityForms;
	}

	public String getStrLikeUserCode() {
		return strLikeUserCode;
	}

	public void setStrLikeUserCode(String strLikeUserCode) {
		this.strLikeUserCode = strLikeUserCode;
	}

	public String getStrLikeUserName() {
		return strLikeUserName;
	}

	public void setStrLikeUserName(String strLikeUserName) {
		this.strLikeUserName = strLikeUserName;
	}

}
