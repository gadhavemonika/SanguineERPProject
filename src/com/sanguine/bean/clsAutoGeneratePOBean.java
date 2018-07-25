package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

public class clsAutoGeneratePOBean {

	private String strProdCode;

	private String strProdName;

	private String strUOM;

	private double dblOrderQty;

	private double dblCurrentStk;

	private double dblOpenPOQty;

	private double dblTotalQty;

	private String dtFromInvDate;

	private String dtToInvDate;

	private int intNoDays;

	private double dblUnitPrice;

	private double dblWeight;

	private String strSuppCode;

	private String strSuppName;

	List<clsAutoGeneratePOBean> listAutoGenPOBean = new ArrayList<clsAutoGeneratePOBean>();

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblOrderQty() {
		return dblOrderQty;
	}

	public void setDblOrderQty(double dblOrderQty) {
		this.dblOrderQty = dblOrderQty;
	}

	public double getDblCurrentStk() {
		return dblCurrentStk;
	}

	public void setDblCurrentStk(double dblCurrentStk) {
		this.dblCurrentStk = dblCurrentStk;
	}

	public double getDblOpenPOQty() {
		return dblOpenPOQty;
	}

	public void setDblOpenPOQty(double dblOpenPOQty) {
		this.dblOpenPOQty = dblOpenPOQty;
	}

	public double getDblTotalQty() {
		return dblTotalQty;
	}

	public void setDblTotalQty(double dblTotalQty) {
		this.dblTotalQty = dblTotalQty;
	}

	public String getDtFromInvDate() {
		return dtFromInvDate;
	}

	public void setDtFromInvDate(String dtFromInvDate) {
		this.dtFromInvDate = dtFromInvDate;
	}

	public String getDtToInvDate() {
		return dtToInvDate;
	}

	public void setDtToInvDate(String dtToInvDate) {
		this.dtToInvDate = dtToInvDate;
	}

	public int getIntNoDays() {
		return intNoDays;
	}

	public void setIntNoDays(int intNoDays) {
		this.intNoDays = intNoDays;
	}

	public double getDblUnitPrice() {
		return dblUnitPrice;
	}

	public void setDblUnitPrice(double dblUnitPrice) {
		this.dblUnitPrice = dblUnitPrice;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public List<clsAutoGeneratePOBean> getListAutoGenPOBean() {
		return listAutoGenPOBean;
	}

	public void setListAutoGenPOBean(List<clsAutoGeneratePOBean> listAutoGenPOBean) {
		this.listAutoGenPOBean = listAutoGenPOBean;
	}

}
