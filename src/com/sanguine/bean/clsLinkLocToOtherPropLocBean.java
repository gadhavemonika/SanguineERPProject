package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.model.clsLinkLoctoOtherPropLocModel;

public class clsLinkLocToOtherPropLocBean {

	private String strPropertyCode;
	private String strLocCode;
	private String strToLoc;

	List<clsLinkLoctoOtherPropLocModel> listLinkLocationModel = new ArrayList<clsLinkLoctoOtherPropLocModel>();

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrToLoc() {
		return strToLoc;
	}

	public void setStrToLoc(String strToLoc) {
		this.strToLoc = strToLoc;
	}

	public List<clsLinkLoctoOtherPropLocModel> getListLinkLocationModel() {
		return listLinkLocationModel;
	}

	public void setListLinkLocationModel(List<clsLinkLoctoOtherPropLocModel> listLinkLocationModel) {
		this.listLinkLocationModel = listLinkLocationModel;
	}
}
