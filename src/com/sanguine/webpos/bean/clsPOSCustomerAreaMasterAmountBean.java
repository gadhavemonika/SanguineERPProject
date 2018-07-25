package com.sanguine.webpos.bean;

import java.util.ArrayList;
import java.util.List;

public class clsPOSCustomerAreaMasterAmountBean {

	private double dblAmount;

	private double dblAmount1;

	private double dblDeliveryCharges;

	private String strCustomerType;

	// private List<clsPOSCustomerAreaMasterAmountBean> listCustAreaAmount=new
	// ArrayList<clsPOSCustomerAreaMasterAmountBean>();

	public Double getDblAmount() {
		return dblAmount;
	}

	public void setDblAmount(Double dblAmount) {
		this.dblAmount = dblAmount;
	}

	public Double getDblAmount1() {
		return dblAmount1;
	}

	public void setDblAmount1(Double dblAmount1) {
		this.dblAmount1 = dblAmount1;
	}

	public Double getDblDeliveryCharges() {
		return dblDeliveryCharges;
	}

	public void setDblDeliveryCharges(Double dblDeliveryCharges) {
		this.dblDeliveryCharges = dblDeliveryCharges;
	}

	public String getStrCustomerType() {
		return strCustomerType;
	}

	public void setStrCustomerType(String strCustomerType) {
		this.strCustomerType = strCustomerType;
	}

	/*
	 * public List<clsPOSCustomerAreaMasterAmountBean> getListCustAreaAmount() {
	 * return listCustAreaAmount; }
	 * 
	 * public void setListCustAreaAmount(
	 * List<clsPOSCustomerAreaMasterAmountBean> listCustAreaAmount) {
	 * this.listCustAreaAmount = listCustAreaAmount; }
	 */

}
