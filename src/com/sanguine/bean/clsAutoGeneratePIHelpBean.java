package com.sanguine.bean;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsPurchaseIndentDtlModel;

public class clsAutoGeneratePIHelpBean {
	Map<String, String> group;
	Map<String, String> subGroup;
	private String strGCode;
	private String strSGCode;
	private List<clsPurchaseIndentDtlModel> listPIDtl;

	public Map<String, String> getGroup() {
		return group;
	}

	public Map<String, String> getSubGroup() {
		return subGroup;
	}

	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	public void setSubGroup(Map<String, String> subGroup) {
		this.subGroup = subGroup;
	}

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public List<clsPurchaseIndentDtlModel> getListPIDtl() {
		return listPIDtl;
	}

	public void setListPIDtl(List<clsPurchaseIndentDtlModel> listPIDtl) {
		this.listPIDtl = listPIDtl;
	}

}
