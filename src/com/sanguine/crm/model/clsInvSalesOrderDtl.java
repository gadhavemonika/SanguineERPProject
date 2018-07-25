package com.sanguine.crm.model;

import javax.persistence.Embeddable;

@Embeddable
public class clsInvSalesOrderDtl {

	private String strSOCode;

	private String dteInvDate;

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getDteInvDate() {
		return dteInvDate;
	}

	public void setDteInvDate(String dteInvDate) {
		this.dteInvDate = dteInvDate;
	}

}
