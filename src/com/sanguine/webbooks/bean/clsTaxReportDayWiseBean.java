package com.sanguine.webbooks.bean;

import java.util.Comparator;

import com.sanguine.bean.clsAvgConsumptionReportBean;

public class clsTaxReportDayWiseBean {

	private String strTaxCode;

	private String strTaxDesc;
	
	private String strTaxDate;

	private double taxAmount;
	
	private double taxableAmount;
	
	private String strTaxFrom;
	
	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrTaxDesc() {
		return strTaxDesc;
	}

	public void setStrTaxDesc(String strTaxDesc) {
		this.strTaxDesc = strTaxDesc;
	}

	public String getStrTaxDate() {
		return strTaxDate;
	}

	public void setStrTaxDate(String strTaxDate) {
		this.strTaxDate = strTaxDate;
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
	
	
	
	public String getStrTaxFrom() {
		return strTaxFrom;
	}

	public void setStrTaxFrom(String strTaxFrom) {
		this.strTaxFrom = strTaxFrom;
	}



	public static Comparator<clsTaxReportDayWiseBean> TaxRtpDateComparator = new Comparator<clsTaxReportDayWiseBean>() {

		public int compare(clsTaxReportDayWiseBean taxObj1, clsTaxReportDayWiseBean taxObj2) {
			String date1 = taxObj1.getStrTaxDate().toUpperCase();
			String date2 = taxObj2.getStrTaxDate().toUpperCase();

			// ascending order
			return date1.compareTo(date2);

			// descending order
			// return strSuppName2.compareTo(strSuppName1);
		}

	};
	
	public static Comparator<clsTaxReportDayWiseBean> TaxRtpTaxComparator = new Comparator<clsTaxReportDayWiseBean>() {

		public int compare(clsTaxReportDayWiseBean taxObj1, clsTaxReportDayWiseBean taxObj2) {
			String txCode = taxObj1.getStrTaxCode().toUpperCase();
			String txCode2 = taxObj2.getStrTaxCode().toUpperCase();

			// ascending order
			return txCode.compareTo(txCode2);

			// descending order
			// return strSuppName2.compareTo(strSuppName1);
		}

	};
	
}
