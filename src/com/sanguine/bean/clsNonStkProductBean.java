package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.util.clsNonStkProductModel;

public class clsNonStkProductBean {

	private String strNarration;
	private String strGRNCode;
	private Double dblTotalAmt;
	private List<clsNonStkProductModel> listItems = new ArrayList<clsNonStkProductModel>();

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public Double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(Double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	public List<clsNonStkProductModel> getListItems() {
		return listItems;
	}

	public void setListItems(List<clsNonStkProductModel> listItems) {
		this.listItems = listItems;
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = strGRNCode;
	}

}
