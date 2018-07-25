package com.sanguine.bean;

import java.util.List;

import org.apache.xpath.operations.String;

import com.sanguine.model.clsPOSLinkUpModel;

public class clsPOSLinkUpBean {

	// Variable Declaration
	private String strPOSItemCode;

	private String strPOSItemName;

	private String strWSItemCode;

	private String strWSItemName;

	private String strClientCode;

	private List<clsPOSLinkUpModel> listPOSLinkUp;

	// Setter-Getter Methods
	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = strPOSItemName;
	}

	public String getStrWSItemCode() {
		return strWSItemCode;
	}

	public void setStrWSItemCode(String strWSItemCode) {
		this.strWSItemCode = strWSItemCode;
	}

	public String getStrWSItemName() {
		return strWSItemName;
	}

	public void setStrWSItemName(String strWSItemName) {
		this.strWSItemName = strWSItemName;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsPOSLinkUpModel> getListPOSLinkUp() {
		return listPOSLinkUp;
	}

	public void setListPOSLinkUp(List<clsPOSLinkUpModel> listPOSLinkUp) {
		this.listPOSLinkUp = listPOSLinkUp;
	}

}
