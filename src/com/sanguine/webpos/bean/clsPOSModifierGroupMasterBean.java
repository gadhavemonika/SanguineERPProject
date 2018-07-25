package com.sanguine.webpos.bean;

public class clsPOSModifierGroupMasterBean {
	private String strModifierGroupCode;

	private String strModifierGroupName;

	private String strModifierGroupShortName;

	private String strMinModifierSelection;

	private long strMinItemLimit;

	private String strMaxModifierSelection;

	private long strMaxItemLimit;

	private int strSequenceNo;

	private String strModGrpOperational;

	private String strOperationType;

	public String getStrModifierGroupCode() {
		return strModifierGroupCode;
	}

	public void setStrModifierGroupCode(String strModifierGroupCode) {
		this.strModifierGroupCode = strModifierGroupCode;
	}

	public String getStrModifierGroupName() {
		return strModifierGroupName;
	}

	public void setStrModifierGroupName(String strModifierGroupName) {
		this.strModifierGroupName = strModifierGroupName;
	}

	public String getStrModifierGroupShortName() {
		return strModifierGroupShortName;
	}

	public void setStrModifierGroupShortName(String strModifierGroupShortName) {
		this.strModifierGroupShortName = strModifierGroupShortName;
	}

	public String getStrMinModifierSelection() {
		return strMinModifierSelection;
	}

	public void setStrMinModifierSelection(String strMinModifierSelection) {
		this.strMinModifierSelection = strMinModifierSelection;
	}

	public double getStrMinItemLimit() {
		return strMinItemLimit;
	}

	public void setStrMinItemLimit(long strMinItemLimit) {
		this.strMinItemLimit = strMinItemLimit;
	}

	public String getStrMaxModifierSelection() {
		return strMaxModifierSelection;
	}

	public void setStrMaxModifierSelection(String strMaxModifierSelection) {
		this.strMaxModifierSelection = strMaxModifierSelection;
	}

	public double getStrMaxItemLimit() {
		return strMaxItemLimit;
	}

	public void setStrMaxItemLimit(long strMaxItemLimit) {
		this.strMaxItemLimit = strMaxItemLimit;
	}

	public int getStrSequenceNo() {
		return strSequenceNo;
	}

	public void setStrSequenceNo(int strSequenceNo) {
		this.strSequenceNo = strSequenceNo;
	}

	public String getStrModGrpOperational() {
		return strModGrpOperational;
	}

	public void setStrModGrpOperational(String strModGrpOperational) {
		this.strModGrpOperational = strModGrpOperational;
	}

	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

}
