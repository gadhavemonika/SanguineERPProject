package com.sanguine.webpos.bean;

public class clsDebitCardDetailBean {

	private String strCardNo;

	private String strCustomerName;

	private double dblRechargeAmt;

	private double dblRefundAmt;

	private double dblRedeemAmt;

	public String getStrCardNo() {
		return strCardNo;
	}

	public void setStrCardNo(String strCardNo) {
		this.strCardNo = strCardNo;
	}

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}

	public double getDblRechargeAmt() {
		return dblRechargeAmt;
	}

	public void setDblRechargeAmt(double dblRechargeAmt) {
		this.dblRechargeAmt = dblRechargeAmt;
	}

	public double getDblRefundAmt() {
		return dblRefundAmt;
	}

	public void setDblRefundAmt(double dblRefundAmt) {
		this.dblRefundAmt = dblRefundAmt;
	}

	public double getDblRedeemAmt() {
		return dblRedeemAmt;
	}

	public void setDblRedeemAmt(double dblRedeemAmt) {
		this.dblRedeemAmt = dblRedeemAmt;
	}

}
