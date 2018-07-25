package com.sanguine.util;

import java.io.Serializable;

@SuppressWarnings("serial")
public class clsUserDesktopUtil implements Serializable {

	private String strFormName;
	private String strFormDesc;
	private String strImgName;
	private String strDesktop;
	private String userCode;
	private String clientCode;
	private String strRequestMapping;

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrFormDesc() {
		return strFormDesc;
	}

	public void setStrFormDesc(String strFormDesc) {
		this.strFormDesc = strFormDesc;
	}

	public String getStrImgName() {
		return strImgName;
	}

	public void setStrImgName(String strImgName) {
		this.strImgName = strImgName;
	}

	public String getStrDesktop() {
		return strDesktop;
	}

	public void setStrDesktop(String strDesktop) {
		this.strDesktop = strDesktop;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getStrRequestMapping() {
		return strRequestMapping;
	}

	public void setStrRequestMapping(String strRequestMapping) {
		this.strRequestMapping = strRequestMapping;
	}
}
