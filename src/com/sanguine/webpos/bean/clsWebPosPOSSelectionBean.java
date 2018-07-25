package com.sanguine.webpos.bean;

import java.io.Serializable;

public class clsWebPosPOSSelectionBean implements Serializable {

	private String strPosCode;

	private String strPosName;

	public String getStrPosName() {
		return strPosName;
	}

	public void setStrPosName(String strPosName) {
		this.strPosName = strPosName;
	}

	public String getStrPosCode() {
		return strPosCode;
	}

	public void setStrPosCode(String strPosCode) {
		this.strPosCode = strPosCode;
	}

}
