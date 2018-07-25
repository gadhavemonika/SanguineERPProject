package com.sanguine.webpms.bean;

import java.util.ArrayList;
import java.util.List;

public class clsRoomStatusDiaryBean {

	private String dteViewDate;

	private String strViewType;

	private List<clsRoomStatusDtlBean> listRoomStatusDtlBean = new ArrayList<clsRoomStatusDtlBean>();

	public List<clsRoomStatusDtlBean> getListRoomStatusDtlBean() {
		return listRoomStatusDtlBean;
	}

	public void setListRoomStatusDtlBean(List<clsRoomStatusDtlBean> listRoomStatusDtlBean) {
		this.listRoomStatusDtlBean = listRoomStatusDtlBean;
	}

	public String getDteViewDate() {
		return dteViewDate;
	}

	public void setDteViewDate(String dteViewDate) {
		this.dteViewDate = dteViewDate;
	}

	public String getStrViewType() {
		return strViewType;
	}

	public void setStrViewType(String strViewType) {
		this.strViewType = strViewType;
	}
}
