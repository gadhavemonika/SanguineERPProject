package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsProductMasterModel;

public class clsPOSItemMasterImportBean {

	private String strIPAddress;

	private String strPortNo;

	private String strDBName;

	private String strUserName;

	private String strPass;

	private String strSGName;

	private List<clsProductMasterModel> listPOSRecipe;

	private String strShowLinked;

	private String strLocName;

	public String getStrIPAddress() {
		return strIPAddress;
	}

	public void setStrIPAddress(String strIPAddress) {
		this.strIPAddress = strIPAddress;
	}

	public String getStrPortNo() {
		return strPortNo;
	}

	public void setStrPortNo(String strPortNo) {
		this.strPortNo = strPortNo;
	}

	public String getStrDBName() {
		return strDBName;
	}

	public void setStrDBName(String strDBName) {
		this.strDBName = strDBName;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrPass() {
		return strPass;
	}

	public void setStrPass(String strPass) {
		this.strPass = strPass;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public List<clsProductMasterModel> getListPOSRecipe() {
		return listPOSRecipe;
	}

	public void setListPOSRecipe(List<clsProductMasterModel> listPOSRecipe) {
		this.listPOSRecipe = listPOSRecipe;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrShowLinked() {
		return strShowLinked;
	}

	public void setStrShowLinked(String strShowLinked) {
		this.strShowLinked = strShowLinked;
	}

}
