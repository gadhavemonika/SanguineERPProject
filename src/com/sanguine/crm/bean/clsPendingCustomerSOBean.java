package com.sanguine.crm.bean;

import java.util.List;

public class clsPendingCustomerSOBean {

	private String strSuppCode;

	private String strProdCode;

	private String dteSODate;

	private String dteFulmtDate;

	private List<clsPendingCustomerSOProductDtlBean> listPendingCustomerSOProductDtlBean;

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public List<clsPendingCustomerSOProductDtlBean> getListPendingCustomerSOProductDtlBean() {
		return listPendingCustomerSOProductDtlBean;
	}

	public void setListPendingCustomerSOProductDtlBean(List<clsPendingCustomerSOProductDtlBean> listPendingCustomerSOProductDtlBean) {
		this.listPendingCustomerSOProductDtlBean = listPendingCustomerSOProductDtlBean;
	}

	public String getDteSODate() {
		return dteSODate;
	}

	public void setDteSODate(String dteSODate) {
		this.dteSODate = dteSODate;
	}

	public String getDteFulmtDate() {
		return dteFulmtDate;
	}

	public void setDteFulmtDate(String dteFulmtDate) {
		this.dteFulmtDate = dteFulmtDate;
	}

}
