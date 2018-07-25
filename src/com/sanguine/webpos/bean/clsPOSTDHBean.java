package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSTDHBean {

	private String strTDHOn;

	private String strTDHCode;

	private String strTDHOnMenuHead;

	private Double strFreeQuantity;

	private String strDescription;

	private String strTDHOnItem;

	private String strchkApplicable;

	private String strMenuHead;

	private long strMaxItemQuantity;

	List<clsTDHDtlBean> listTDHDtl;

	public String getStrTDHOn() {
		return strTDHOn;
	}

	public void setStrTDHOn(String strTDHOn) {
		this.strTDHOn = strTDHOn;
	}

	public String getStrTDHCode() {
		return strTDHCode;
	}

	public void setStrTDHCode(String strTDHCode) {
		this.strTDHCode = strTDHCode;
	}

	public String getStrTDHOnMenuHead() {
		return strTDHOnMenuHead;
	}

	public void setStrTDHOnMenuHead(String strTDHOnMenuHead) {
		this.strTDHOnMenuHead = strTDHOnMenuHead;
	}

	public Double getStrFreeQuantity() {
		return strFreeQuantity;
	}

	public void setStrFreeQuantity(Double strFreeQuantity) {
		this.strFreeQuantity = strFreeQuantity;
	}

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public String getStrTDHOnItem() {
		return strTDHOnItem;
	}

	public void setStrTDHOnItem(String strTDHOnItem) {
		this.strTDHOnItem = strTDHOnItem;
	}

	public String getStrchkApplicable() {
		return strchkApplicable;
	}

	public void setStrchkApplicable(String strchkApplicable) {
		this.strchkApplicable = strchkApplicable;
	}

	public String getStrMenuHead() {
		return strMenuHead;
	}

	public void setStrMenuHead(String strMenuHead) {
		this.strMenuHead = strMenuHead;
	}

	public long getStrMaxItemQuantity() {
		return strMaxItemQuantity;
	}

	public void setStrMaxItemQuantity(long strMaxItemQuantity) {
		this.strMaxItemQuantity = strMaxItemQuantity;
	}

	public List<clsTDHDtlBean> getListTDHDtl() {
		return listTDHDtl;
	}

	public void setListTDHDtl(List<clsTDHDtlBean> listTDHDtl) {
		this.listTDHDtl = listTDHDtl;
	}

}
