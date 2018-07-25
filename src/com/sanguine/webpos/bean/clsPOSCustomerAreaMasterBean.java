package com.sanguine.webpos.bean;

import java.util.ArrayList;
import java.util.List;

public class clsPOSCustomerAreaMasterBean {

	private String strCustomerAreaCode;

	private String strCustomerAreaName;

	private String strAddress;

	private double strHomeDeliveryCharges;

	private String strZone;

	private double dblDeliveryBoyPayOut;

	private double strHelperPayOut;

	private double dblAmount;

	private double dblAmount1;

	private double dblDeliveryCharges;

	private String strCustomerType;

	private List<clsPOSCustomerAreaMasterAmountBean> listCustAreaAmount;

	public String getStrCustomerAreaCode() {
		return strCustomerAreaCode;
	}

	public void setStrCustomerAreaCode(String strAreaCode) {
		this.strCustomerAreaCode = strAreaCode;
	}

	public String getStrCustomerAreaName() {
		return strCustomerAreaName;
	}

	public void setStrCustomerAreaName(String strAreaName) {
		this.strCustomerAreaName = strAreaName;
	}

	public String getStrAddress() {
		return strAddress;
	}

	public void setStrAddress(String strAddress) {
		this.strAddress = strAddress;
	}

	public Double getStrHomeDeliveryCharges() {
		return strHomeDeliveryCharges;
	}

	public void setStrHomeDeliveryCharges(Double strHomeDeliveryCharges) {
		this.strHomeDeliveryCharges = strHomeDeliveryCharges;
	}

	public String getStrZone() {
		return strZone;
	}

	public void setStrZone(String strZone) {
		this.strZone = strZone;
	}

	public Double getDblDeliveryBoyPayOut() {
		return dblDeliveryBoyPayOut;
	}

	public void setDblDeliveryBoyPayOut(Double dblDeliveryBoyPayOut) {
		this.dblDeliveryBoyPayOut = dblDeliveryBoyPayOut;
	}

	public Double getStrHelperPayOut() {
		return strHelperPayOut;
	}

	public void setStrHelperPayOut(Double strHelperPayOut) {
		this.strHelperPayOut = strHelperPayOut;
	}

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

	public List<clsPOSCustomerAreaMasterAmountBean> getListCustAreaAmount() {
		return listCustAreaAmount;
	}

	public void setListCustAreaAmount(List<clsPOSCustomerAreaMasterAmountBean> listCustAreaAmount) {
		this.listCustAreaAmount = listCustAreaAmount;
	}

}
