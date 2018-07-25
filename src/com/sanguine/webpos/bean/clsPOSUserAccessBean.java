package com.sanguine.webpos.bean;

import javax.persistence.Column;

public class clsPOSUserAccessBean {
	private String strUserCode;
	private String strFormName;
	private String strButtonName;
	private long intSequence;
	private String strAdd;
	private String strEdit;
	private String strDelete;
	private String strView;
	private String strPrint;
	private String strSave;
	private String strGrant;
	private String strTLA;
	private String strAuditing;

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrButtonName() {
		return strButtonName;
	}

	public void setStrButtonName(String strButtonName) {
		this.strButtonName = strButtonName;
	}

	public long getIntSequence() {
		return intSequence;
	}

	public void setIntSequence(long intSequence) {
		this.intSequence = intSequence;
	}

	public String getStrAdd() {
		return strAdd;
	}

	public void setStrAdd(String strAdd) {
		this.strAdd = strAdd;
	}

	public String getStrEdit() {
		return strEdit;
	}

	public void setStrEdit(String strEdit) {
		this.strEdit = strEdit;
	}

	public String getStrDelete() {
		return strDelete;
	}

	public void setStrDelete(String strDelete) {
		this.strDelete = strDelete;
	}

	public String getStrView() {
		return strView;
	}

	public void setStrView(String strView) {
		this.strView = strView;
	}

	public String getStrPrint() {
		return strPrint;
	}

	public void setStrPrint(String strPrint) {
		this.strPrint = strPrint;
	}

	public String getStrSave() {
		return strSave;
	}

	public void setStrSave(String strSave) {
		this.strSave = strSave;
	}

	public String getStrGrant() {
		return strGrant;
	}

	public void setStrGrant(String strGrant) {
		this.strGrant = strGrant;
	}

	public String getStrTLA() {
		return strTLA;
	}

	public void setStrTLA(String strTLA) {
		this.strTLA = strTLA;
	}

	public String getStrAuditing() {
		return strAuditing;
	}

	public void setStrAuditing(String strAuditing) {
		this.strAuditing = strAuditing;
	}
}
