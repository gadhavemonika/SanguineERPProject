package com.sanguine.excise.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class clsFLR6Summary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId")
	private long intId;

	@Column(name = "strSumRow0", columnDefinition = "VARCHAR(50)")
	private String strSumRow0;

	@Column(name = "strSumRow1", columnDefinition = "VARCHAR(50)")
	private String strSumRow1;

	@Column(name = "strSumRow2", columnDefinition = "VARCHAR(50)")
	private String strSumRow2;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrSumRow0() {
		return strSumRow0;
	}

	public void setStrSumRow0(String strSumRow0) {
		this.strSumRow0 = strSumRow0;
	}

	public String getStrSumRow1() {
		return strSumRow1;
	}

	public void setStrSumRow1(String strSumRow1) {
		this.strSumRow1 = strSumRow1;
	}

	public String getStrSumRow2() {
		return strSumRow2;
	}

	public void setStrSumRow2(String strSumRow2) {
		this.strSumRow2 = strSumRow2;
	}

}
