package com.sanguine.bean;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsTreeMasterModel;

public class clsTreeMenuBean {

	List<clsTreeMasterModel> menu;

	Map<String, String> menu1;

	List<clsTreeMasterModel> listMasterForms;// M
	List<clsTreeMasterModel> listTransactionForms;// T
	List<clsTreeMasterModel> listReportForms;// R
	List<clsTreeMasterModel> listUtilityForms;// L

	public List<clsTreeMasterModel> getMenu() {
		return menu;
	}

	public void setMenu(List<clsTreeMasterModel> menu) {
		this.menu = menu;
	}

	public Map<String, String> getMenu1() {
		return menu1;
	}

	public void setMenu1(Map<String, String> menu1) {
		this.menu1 = menu1;
	}

	public List<clsTreeMasterModel> getListMasterForms() {
		return listMasterForms;
	}

	public void setListMasterForms(List<clsTreeMasterModel> listMasterForms) {
		this.listMasterForms = listMasterForms;
	}

	public List<clsTreeMasterModel> getListTransactionForms() {
		return listTransactionForms;
	}

	public void setListTransactionForms(List<clsTreeMasterModel> listTransactionForms) {
		this.listTransactionForms = listTransactionForms;
	}

	public List<clsTreeMasterModel> getListReportForms() {
		return listReportForms;
	}

	public void setListReportForms(List<clsTreeMasterModel> listReportForms) {
		this.listReportForms = listReportForms;
	}

	public List<clsTreeMasterModel> getListUtilityForms() {
		return listUtilityForms;
	}

	public void setListUtilityForms(List<clsTreeMasterModel> listUtilityForms) {
		this.listUtilityForms = listUtilityForms;
	}

}
