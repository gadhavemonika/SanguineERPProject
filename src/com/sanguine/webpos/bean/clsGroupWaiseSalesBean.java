package com.sanguine.webpos.bean;

public class clsGroupWaiseSalesBean {

	private String groupName;
	private String posName;
	private double qty;
	private double salesAmt;
	private double discAmt;
	private double subTotal;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(double salesAmt) {
		this.salesAmt = salesAmt;
	}

	public double getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(double discAmt) {
		this.discAmt = discAmt;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

}
