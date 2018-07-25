package com.sanguine.bean;

public class clsParentDataForBOM {
	private String strParentCode, strPartNo, strProdType, strSGCode, strSGName, strProcessCode, strParentName, strProcessName;
	private String strUOM, strBOMCode;

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	/*
	 * public String getStrBOMCreated() { return strBOMCreated; }
	 * 
	 * public void setStrBOMCreated(String strBOMCreated) { this.strBOMCreated =
	 * (String) setDefaultValue(strBOMCreated,"N"); }
	 */

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
	}

	public String getStrBOMCode() {
		return strBOMCode;
	}

	public void setStrBOMCode(String strBOMCode) {
		this.strBOMCode = strBOMCode;
	}
}
