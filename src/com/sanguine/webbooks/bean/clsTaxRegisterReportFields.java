package com.sanguine.webbooks.bean;

public class clsTaxRegisterReportFields {

	private String transDocCode;
	
	private String transDocDate;
	
	private String taxDesc;
	
	private double taxAmount;
	
	private double taxableAmount;
	
	private double totalTaxAmount;

	
	public String getTransDocCode() {
		return transDocCode;
	}

	public void setTransDocCode(String transDocCode) {
		this.transDocCode = transDocCode;
	}

	public String getTransDocDate() {
		return transDocDate;
	}

	public void setTransDocDate(String transDocDate) {
		this.transDocDate = transDocDate;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public double getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(double totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}
}
