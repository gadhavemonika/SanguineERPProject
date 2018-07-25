package com.sanguine.bean;

import java.util.List;

public class clsBudgetMasterBean {

	private String strPropertyCode;

	private String strFinYear;

	private String strBudgetCode;

	private List<clsBudgetMasterMonthBean> listBudgetMonth;

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrFinYear() {
		return strFinYear;
	}

	public void setStrFinYear(String strFinYear) {
		this.strFinYear = strFinYear;
	}

	public List<clsBudgetMasterMonthBean> getListBudgetMonth() {
		return listBudgetMonth;
	}

	public void setListBudgetMonth(List<clsBudgetMasterMonthBean> listBudgetMonth) {
		this.listBudgetMonth = listBudgetMonth;
	}

	public String getStrBudgetCode() {
		return strBudgetCode;
	}

	public void setStrBudgetCode(String strBudgetCode) {
		this.strBudgetCode = strBudgetCode;
	}

}
