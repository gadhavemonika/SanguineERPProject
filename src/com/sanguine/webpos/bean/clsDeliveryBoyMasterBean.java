package com.sanguine.webpos.bean;

import java.util.List;

public class clsDeliveryBoyMasterBean {
	// Variable Declaration
	private String strDPCode;

	private String strDPName;

	private String strOperational;

	private List<clsDeliveryBoyChargesBean> listDeliveryBoyCharges;

	// Setter-Getter Methods
	public String getStrDPCode() {
		return strDPCode;
	}

	public void setStrDPCode(String strDPCode) {
		this.strDPCode = strDPCode;
	}

	public String getStrDPName() {
		return strDPName;
	}

	public void setStrDPName(String strDPName) {
		this.strDPName = strDPName;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public List<clsDeliveryBoyChargesBean> getListDeliveryBoyCharges() {
		return listDeliveryBoyCharges;
	}

	public void setListDeliveryBoyCharges(List<clsDeliveryBoyChargesBean> listDeliveryBoyCharges) {
		this.listDeliveryBoyCharges = listDeliveryBoyCharges;
	}

}
