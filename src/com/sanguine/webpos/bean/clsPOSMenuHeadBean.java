package com.sanguine.webpos.bean;

import java.util.ArrayList;
import java.util.List;

public class clsPOSMenuHeadBean {

	private String strMenuHeadCode;

	private String strMenuHeadName;

	private String strOperational;

	private String strOperationType;

	private String strSubMenuHeadCode = "";
	private String strSubMenuHeadName;
	private String strSubMenuHeadShortName;
	private String strMenuHeadCodeInSub;
	private String strSubMenuOperational;

	private String sequenceNo;

	private List<clsPOSMenuHeadBean> listMenuMasterDtl = new ArrayList<clsPOSMenuHeadBean>();

	public String getStrMenuHeadCode() {
		return strMenuHeadCode;
	}

	public void setStrMenuHeadCode(String strMenuHeadCode) {
		this.strMenuHeadCode = strMenuHeadCode;
	}

	public String getStrMenuHeadName() {
		return strMenuHeadName;
	}

	public void setStrMenuHeadName(String strMenuHeadName) {
		this.strMenuHeadName = strMenuHeadName;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public String getStrSubMenuHeadCode() {
		return strSubMenuHeadCode;
	}

	public void setStrSubMenuHeadCode(String strSubMenuHeadCode) {
		this.strSubMenuHeadCode = strSubMenuHeadCode;
	}

	public String getStrSubMenuHeadName() {
		return strSubMenuHeadName;
	}

	public void setStrSubMenuHeadName(String strSubMenuHeadName) {
		this.strSubMenuHeadName = strSubMenuHeadName;
	}

	public String getStrSubMenuHeadShortName() {
		return strSubMenuHeadShortName;
	}

	public void setStrSubMenuHeadShortName(String strSubMenuHeadShortName) {
		this.strSubMenuHeadShortName = strSubMenuHeadShortName;
	}

	public String getStrMenuHeadCodeInSub() {
		return strMenuHeadCodeInSub;
	}

	public void setStrMenuHeadCodeInSub(String strMenuHeadCodeInSub) {
		this.strMenuHeadCodeInSub = strMenuHeadCodeInSub;
	}

	public String getStrSubMenuOperational() {
		return strSubMenuOperational;
	}

	public void setStrSubMenuOperational(String strSubMenuOperational) {
		this.strSubMenuOperational = strSubMenuOperational;
	}

	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public List<clsPOSMenuHeadBean> getListMenuMasterDtl() {
		return listMenuMasterDtl;
	}

	public void setListMenuMasterDtl(List<clsPOSMenuHeadBean> listMenuMasterDtl) {
		this.listMenuMasterDtl = listMenuMasterDtl;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
