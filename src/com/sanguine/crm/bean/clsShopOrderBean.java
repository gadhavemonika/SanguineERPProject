package com.sanguine.crm.bean;

import java.util.Comparator;

import com.sanguine.bean.clsAvgConsumptionReportBean;

public class clsShopOrderBean {

	private String strPName;
	private String strGName;
	private String strSGName;
	private String strProdName;
	private double dblRequiredQty;
	private int count;
	private int intSrNo;
	private int intSortingNo;

	public clsShopOrderBean() {
	}

	public clsShopOrderBean(String strPName, String strGName, String strSGName, String strProdName, double dblRequiredQty, int count, int intSrNo, int intSortingNo) {
		this.strPName = strPName;
		this.strGName = strGName;
		this.strSGName = strSGName;
		this.strProdName = strProdName;
		this.dblRequiredQty = dblRequiredQty;
		this.count = count;
		this.intSrNo = intSrNo;
		this.intSortingNo = intSortingNo;
	}

	public String getStrPName() {
		return strPName;
	}

	public void setStrPName(String strPName) {
		this.strPName = strPName;
	}

	public String getStrGName() {
		return strGName;
	}

	public void setStrGName(String strGName) {
		this.strGName = strGName;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public double getDblRequiredQty() {
		return dblRequiredQty;
	}

	public void setDblRequiredQty(double dblRequiredQty) {
		this.dblRequiredQty = dblRequiredQty;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIntSrNo() {
		return intSrNo;
	}

	public void setIntSrNo(int intSrNo) {
		this.intSrNo = intSrNo;
	}

	public int getIntSortingNo() {
		return intSortingNo;
	}

	public void setIntSortingNo(int intSortingNo) {
		this.intSortingNo = intSortingNo;
	}

	public static Comparator<clsShopOrderBean> intSortingNoComparator = new Comparator<clsShopOrderBean>() {

		@Override
		public int compare(clsShopOrderBean intSortObj1, clsShopOrderBean intSortObj2) {
			int intSortingNo1 = intSortObj1.getIntSortingNo();
			int intSortingNo2 = intSortObj2.getIntSortingNo();

			// ascending order
			return intSortingNo1 - intSortingNo2;

			// descending order
			// return strSuppName2.compareTo(strSuppName1);
		}

	};

}
