package com.sanguine.excise.bean;

import java.util.List;

import com.sanguine.excise.model.clsExciseManualSaleHdModel;

public class clsExciseBillGenerateBean {
	// Variable Declaration
	private String strFromDate;

	private String strToDate;

	private String strlicenceCode;

	private List<clsExciseManualSaleHdModel> saleHdList;

	public String getStrFromDate() {
		return strFromDate;
	}

	public void setStrFromDate(String strFromDate) {
		this.strFromDate = strFromDate;
	}

	public String getStrToDate() {
		return strToDate;
	}

	public void setStrToDate(String strToDate) {
		this.strToDate = strToDate;
	}

	public List<clsExciseManualSaleHdModel> getSaleHdList() {
		return saleHdList;
	}

	public void setSaleHdList(List<clsExciseManualSaleHdModel> saleHdList) {
		this.saleHdList = saleHdList;
	}

	public String getStrlicenceCode() {
		return strlicenceCode;
	}

	public void setStrlicenceCode(String strlicenceCode) {
		this.strlicenceCode = strlicenceCode;
	}

}
