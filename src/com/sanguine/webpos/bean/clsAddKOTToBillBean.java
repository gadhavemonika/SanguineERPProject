package com.sanguine.webpos.bean;

import java.util.ArrayList;
import java.util.List;

public class clsAddKOTToBillBean {
	private String strTableName;
	private List<clsPOSTableMasterBean> listTableDtl;
	private List<clsKOTItemDtl> listKOTDtl = new ArrayList<clsKOTItemDtl>();
	private List<clsBillItemDtlBean> listBillDtl = new ArrayList<clsBillItemDtlBean>();

	public List<clsBillItemDtlBean> getListBillDtl() {
		return listBillDtl;
	}

	public void setListBillDtl(List<clsBillItemDtlBean> listBillDtl) {
		this.listBillDtl = listBillDtl;
	}

	public List<clsKOTItemDtl> getListKOTDtl() {
		return listKOTDtl;
	}

	public void setListKOTDtl(List<clsKOTItemDtl> listKOTDtl) {
		this.listKOTDtl = listKOTDtl;
	}

	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public List<clsPOSTableMasterBean> getListTableDtl() {
		return listTableDtl;
	}

	public void setListTableDtl(List<clsPOSTableMasterBean> listTableDtl) {
		this.listTableDtl = listTableDtl;
	}

}
