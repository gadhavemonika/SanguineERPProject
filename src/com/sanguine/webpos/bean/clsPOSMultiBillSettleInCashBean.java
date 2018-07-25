package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSMultiBillSettleInCashBean {
	private String strBillNo;
	private String strTableName, strCustomerName, strBuildingName;
	// private double dblGrandTotal;
	private String strDPName, dteBillDate, strWShortName;
	private double dblGrandTotal;
	private List<clsPOSMultiBillSettleInCashBean> listUnsettleBillDtl;
	private String strSelectedData;

	public String getStrBillNo() {
		return strBillNo;
	}

	public List<clsPOSMultiBillSettleInCashBean> getListUnsettleBillDtl() {
		return listUnsettleBillDtl;
	}

	public void setListUnsettleBillDtl(List<clsPOSMultiBillSettleInCashBean> listUnsettleBillDtl) {
		this.listUnsettleBillDtl = listUnsettleBillDtl;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public String getStrCustomerName() {
		return strCustomerName;
	}

	public void setStrCustomerName(String strCustomerName) {
		this.strCustomerName = strCustomerName;
	}

	public String getStrBuildingName() {
		return strBuildingName;
	}

	public void setStrBuildingName(String strBuildingName) {
		this.strBuildingName = strBuildingName;
	}

	public String getStrDPName() {
		return strDPName;
	}

	public void setStrDPName(String strDPName) {
		this.strDPName = strDPName;
	}

	public String getDteBillDate() {
		return dteBillDate;
	}

	public void setDteBillDate(String dteBillDate) {
		this.dteBillDate = dteBillDate;
	}

	public double getDblGrandTotal() {
		return dblGrandTotal;
	}

	public void setDblGrandTotal(double dblGrandTotal) {
		this.dblGrandTotal = dblGrandTotal;
	}

	public String getStrWShortName() {
		return strWShortName;
	}

	public void setStrWShortName(String strWShortName) {
		this.strWShortName = strWShortName;
	}

	public String getStrSelectedData() {
		return strSelectedData;
	}

	public void setStrSelectedData(String strSelectedData) {
		this.strSelectedData = strSelectedData;
	}

}
