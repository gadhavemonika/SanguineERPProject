package com.sanguine.webpos.bean;

import java.util.List;

public class clsFormMasterBean {
	private String strFormName;

	private String strModuleName;

	private String strModuleType;

	private String strImageName;

	private String strColorImageName;

	private int intSequence;

	private String strRequestMapping;

	private List<clsFormMasterBean> listMastersDtl;

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
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

	public String getStrImageName() {
		return strImageName;
	}

	public void setStrImageName(String strImageName) {
		this.strImageName = strImageName;
	}

	public String getStrColorImageName() {
		return strColorImageName;
	}

	public void setStrColorImageName(String strColorImageName) {
		this.strColorImageName = strColorImageName;
	}

	public int getIntSequence() {
		return intSequence;
	}

	public void setIntSequence(int intSequence) {
		this.intSequence = intSequence;
	}

	public String getStrRequestMapping() {
		return strRequestMapping;
	}

	public void setStrRequestMapping(String strRequestMapping) {
		this.strRequestMapping = strRequestMapping;
	}

	public List<clsFormMasterBean> getListMastersDtl() {
		return listMastersDtl;
	}

	public void setListMastersDtl(List<clsFormMasterBean> listMastersDtl) {
		this.listMastersDtl = listMastersDtl;
	}

}
