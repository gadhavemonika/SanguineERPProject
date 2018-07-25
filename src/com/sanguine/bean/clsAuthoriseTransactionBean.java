package com.sanguine.bean;

import java.util.ArrayList;

public class clsAuthoriseTransactionBean {
	private String strFormName;

	private String strTransCode;

	private int intLevel;

	private ArrayList<String> chkTransCodes;

	private ArrayList<String> txtComments;

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
	}

	public ArrayList<String> getTxtComments() {
		return txtComments;
	}

	public void setTxtComments(ArrayList<String> txtComments) {
		this.txtComments = txtComments;
	}

	public ArrayList<String> getChkTransCodes() {
		return chkTransCodes;
	}

	public void setChkTransCodes(ArrayList<String> chkTransCodes) {
		this.chkTransCodes = chkTransCodes;
	}

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}
}
