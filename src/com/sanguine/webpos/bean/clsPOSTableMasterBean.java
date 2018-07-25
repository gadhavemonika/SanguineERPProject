package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSTableMasterBean {
	// Variable Declaration
	private String strTableNo;

	private String strTableName;

	private String strAreaName;

	private String strWaiterName;

	private long intPaxCapacity;

	private long intSequence;

	private String strOperational;

	private String strPOSCode;

	private String strStatus;

	private List<clsPOSTableMasterBean> listTableDtl;

	public List<clsPOSTableMasterBean> getListTableDtl() {
		return listTableDtl;
	}

	public void setListTableDtl(List<clsPOSTableMasterBean> listTableDtl) {
		this.listTableDtl = listTableDtl;
	}

	// Setter-Getter Methods
	public String getStrTableNo() {
		return strTableNo;
	}

	public void setStrTableNo(String strTableNo) {
		this.strTableNo = strTableNo;
	}

	public String getStrTableName() {
		return strTableName;
	}

	public void setStrTableName(String strTableName) {
		this.strTableName = strTableName;
	}

	public long getIntSequence() {
		return intSequence;
	}

	public void setIntSequence(long intSequence) {
		this.intSequence = intSequence;
	}

	public String getStrAreaName() {
		return strAreaName;
	}

	public void setStrAreaName(String strAreaName) {
		this.strAreaName = strAreaName;
	}

	public String getStrWaiterName() {
		return strWaiterName;
	}

	public void setStrWaiterName(String strWaiterName) {
		this.strWaiterName = strWaiterName;
	}

	public long getIntPaxCapacity() {
		return intPaxCapacity;
	}

	public void setIntPaxCapacity(long intPaxCapacity) {
		this.intPaxCapacity = intPaxCapacity;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public String getStrPOSCode() {
		return strPOSCode;
	}

	public void setStrPOSCode(String strPOSCode) {
		this.strPOSCode = strPOSCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

}
