package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSMoveTableBean {
	private List<clsPOSTableMasterBean> listOfAllTable;
	private List<clsPOSTableMasterBean> listOfOccupiedTable;

	public List<clsPOSTableMasterBean> getListOfAllTable() {
		return listOfAllTable;
	}

	public void setListOfAllTable(List<clsPOSTableMasterBean> listOfAllTable) {
		this.listOfAllTable = listOfAllTable;
	}

	public List<clsPOSTableMasterBean> getListOfOccupiedTable() {
		return listOfOccupiedTable;
	}

	public void setListOfOccupiedTable(List<clsPOSTableMasterBean> listOfOccupiedTable) {
		this.listOfOccupiedTable = listOfOccupiedTable;
	}

}
