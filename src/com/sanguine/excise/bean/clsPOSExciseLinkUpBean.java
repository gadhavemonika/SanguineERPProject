package com.sanguine.excise.bean;

import java.util.List;

import org.apache.xpath.operations.String;

import com.sanguine.excise.model.clsExcisePOSLinkUpModel;

public class clsPOSExciseLinkUpBean {

	// Variable Declaration

	private String strClientCode;

	private List<clsExcisePOSLinkUpModel> listExcisePOSLinkUp;

	// Setter-Getter Methods

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsExcisePOSLinkUpModel> getListExcisePOSLinkUp() {
		return listExcisePOSLinkUp;
	}

	public void setListExcisePOSLinkUp(List<clsExcisePOSLinkUpModel> listExcisePOSLinkUp) {
		this.listExcisePOSLinkUp = listExcisePOSLinkUp;
	}

}
