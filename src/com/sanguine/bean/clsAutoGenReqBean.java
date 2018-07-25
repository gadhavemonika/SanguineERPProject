package com.sanguine.bean;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsRequisitionDtlModel;

public class clsAutoGenReqBean {

	Map<String, String> group;
	Map<String, String> subGroup;
	private String strGCode;
	private String strSGCode;
	private List<clsRequisitionDtlModel> listReqDtl;

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

	public Map<String, String> getGroup() {
		return group;
	}

	public void setGroup(Map<String, String> group) {
		this.group = group;
	}

	public Map<String, String> getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(Map<String, String> subGroup) {
		this.subGroup = subGroup;
	}

	public List<clsRequisitionDtlModel> getListReqDtl() {
		return listReqDtl;
	}

	public void setListReqDtl(List<clsRequisitionDtlModel> listReqDtl) {
		this.listReqDtl = listReqDtl;
	}

}
