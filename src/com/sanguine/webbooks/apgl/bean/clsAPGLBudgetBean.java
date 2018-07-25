package com.sanguine.webbooks.apgl.bean;

import java.util.List;

import com.sanguine.webbooks.apgl.bean.clsBudgetDtlBean;

public class clsAPGLBudgetBean {

	// Variable Declaration
	private String strAccCode;

	private String strAccName;

	private String strMonth;

	private String strYear;

	private double dblBudgetAmt;

	private String strClientCode;

	private List<clsBudgetDtlBean> listBudgetDtlModel;

	// Getter and Setter
	public String getStrAccCode() {
		return strAccCode;
	}

	public void setStrAccCode(String strAccCode) {
		this.strAccCode = strAccCode;
	}

	public String getStrAccName() {
		return strAccName;
	}

	public void setStrAccName(String strAccName) {
		this.strAccName = strAccName;
	}

	public double getDblBudgetAmt() {
		return dblBudgetAmt;
	}

	public void setDblBudgetAmt(double dblBudgetAmt) {
		this.dblBudgetAmt = dblBudgetAmt;
	}

	public String getStrMonth() {
		return strMonth;
	}

	public void setStrMonth(String strMonth) {
		this.strMonth = strMonth;
	}

	public String getStrYear() {
		return strYear;
	}

	public void setStrYear(String strYear) {
		this.strYear = strYear;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsBudgetDtlBean> getListBudgetDtlModel() {
		return listBudgetDtlModel;
	}

	public void setListBudgetDtlModel(List<clsBudgetDtlBean> listBudgetDtlModel) {
		this.listBudgetDtlModel = listBudgetDtlModel;
	}

}
