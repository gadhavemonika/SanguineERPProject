package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSWiseItemIncentiveBean {

	private String strPOSName;

	private List<clsPOSWiseItemIncentiveDtlBean> listItemIncentive;

	public String getStrPOSName() {
		return strPOSName;
	}

	public void setStrPOSName(String strPOSName) {
		this.strPOSName = strPOSName;
	}

	public List<clsPOSWiseItemIncentiveDtlBean> getListItemIncentive() {
		return listItemIncentive;
	}

	public void setListItemIncentive(List<clsPOSWiseItemIncentiveDtlBean> listItemIncentive) {
		this.listItemIncentive = listItemIncentive;
	}

}
