package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseRecipeMasterDtlModel;

public class clsExciseRecipeMasterBean {
	// Variable Declaration
	private String strRecipeCode;

	private long intId;

	private String strParentCode;

	private String strParentName;

	private String strParentSize;

	private String dtValidFrom;

	private String dtValidTo;

	private String strUserCreated;

	private String strUserModified;

	private String strClientCode;

	private List<clsExciseRecipeMasterDtlModel> objclsExciseRecipeMasterDtlModel;

	// Setter-Getter Methods
	public String getStrRecipeCode() {
		return strRecipeCode;
	}

	public void setStrRecipeCode(String strRecipeCode) {
		this.strRecipeCode = strRecipeCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrParentSize() {
		return strParentSize;
	}

	public void setStrParentSize(String strParentSize) {
		this.strParentSize = strParentSize;
	}

	public String getDtValidFrom() {
		return dtValidFrom;
	}

	public void setDtValidFrom(String dtValidFrom) {
		this.dtValidFrom = dtValidFrom;
	}

	public String getDtValidTo() {
		return dtValidTo;
	}

	public void setDtValidTo(String dtValidTo) {
		this.dtValidTo = dtValidTo;
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

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsExciseRecipeMasterDtlModel> getObjclsExciseRecipeMasterDtlModel() {
		return objclsExciseRecipeMasterDtlModel;
	}

	public void setObjclsExciseRecipeMasterDtlModel(List<clsExciseRecipeMasterDtlModel> objclsExciseRecipeMasterDtlModel) {
		this.objclsExciseRecipeMasterDtlModel = objclsExciseRecipeMasterDtlModel;
	}

}
