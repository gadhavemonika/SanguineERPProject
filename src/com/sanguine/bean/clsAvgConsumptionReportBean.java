package com.sanguine.bean;

import java.util.Comparator;

public class clsAvgConsumptionReportBean {

	private String strProdCode;

	private String strProdName;

	private String strUom;

	private double dblRecConverstion;

	private double dblOpeningStk;

	private double dblConsump;

	private double dblAvgConsump;

	private double dblCurrentStock;

	private double dblMinOrderQty;

	private String strSuppName;

	private String strSuppCode;

	private double dblRecipts;

	private double dblIssue;

	public clsAvgConsumptionReportBean() {

	}

	public clsAvgConsumptionReportBean(String strProdCode, String strProdName, String strUom, double dblRecConverstion, double dblOpeningStk, double dblConsump, double dblAvgConsump, double dblCurrentStock, double dblMinOrderQty, String strSuppName, String strSuppCode, double dblRecipts, double dblIssue) {
		this.strProdCode = strProdCode;
		this.strProdName = strProdName;
		this.strUom = strUom;
		this.dblRecConverstion = dblRecConverstion;
		this.dblOpeningStk = dblOpeningStk;
		this.dblConsump = dblConsump;
		this.dblAvgConsump = dblAvgConsump;
		this.dblCurrentStock = dblCurrentStock;
		this.dblMinOrderQty = dblMinOrderQty;
		this.strSuppName = strSuppName;
		this.strSuppCode = strSuppCode;
		this.dblRecipts = dblRecipts;
		this.dblIssue = dblIssue;
	}

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

	public String getStrUom() {
		return strUom;
	}

	public void setStrUom(String strUom) {
		this.strUom = strUom;
	}

	public double getDblRecConverstion() {
		return dblRecConverstion;
	}

	public void setDblRecConverstion(double dblRecConverstion) {
		this.dblRecConverstion = dblRecConverstion;
	}

	public double getDblOpeningStk() {
		return dblOpeningStk;
	}

	public void setDblOpeningStk(double dblOpeningStk) {
		this.dblOpeningStk = dblOpeningStk;
	}

	public double getDblAvgConsump() {
		return dblAvgConsump;
	}

	public void setDblAvgConsump(double dblAvgConsump) {
		this.dblAvgConsump = dblAvgConsump;
	}

	public double getDblCurrentStock() {
		return dblCurrentStock;
	}

	public void setDblCurrentStock(double dblCurrentStock) {
		this.dblCurrentStock = dblCurrentStock;
	}

	public double getDblMinOrderQty() {
		return dblMinOrderQty;
	}

	public void setDblMinOrderQty(double dblMinOrderQty) {
		this.dblMinOrderQty = dblMinOrderQty;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public double getDblConsump() {
		return dblConsump;
	}

	public void setDblConsump(double dblConsump) {
		this.dblConsump = dblConsump;
	}

	public double getDblRecipts() {
		return dblRecipts;
	}

	public void setDblRecipts(double dblRecipts) {
		this.dblRecipts = dblRecipts;
	}

	public double getDblIssue() {
		return dblIssue;
	}

	public void setDblIssue(double dblIssue) {
		this.dblIssue = dblIssue;
	}

	public static Comparator<clsAvgConsumptionReportBean> suppNameComparator = new Comparator<clsAvgConsumptionReportBean>() {

		public int compare(clsAvgConsumptionReportBean AvgObj1, clsAvgConsumptionReportBean AvgObj2) {
			String strSuppName1 = AvgObj1.getStrSuppName().toUpperCase();
			String strSuppName2 = AvgObj2.getStrSuppName().toUpperCase();

			// ascending order
			return strSuppName1.compareTo(strSuppName2);

			// descending order
			// return strSuppName2.compareTo(strSuppName1);
		}

	};

}
