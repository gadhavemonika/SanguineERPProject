package com.sanguine.webpos.bean;

public class clsPhysicalStockFlashBean {
	private String strPSPCode;

	private String strItemName;

	private String dteDate;

	private Double dblCompStk;

	private Double dblPhyStk;

	private Double dblVariance;

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public String getDteDate() {
		return dteDate;
	}

	public void setDteDate(String dteDate) {
		this.dteDate = dteDate;
	}

	public Double getDblCompStk() {
		return dblCompStk;
	}

	public void setDblCompStk(Double dblCompStk) {
		this.dblCompStk = dblCompStk;
	}

	public Double getDblPhyStk() {
		return dblPhyStk;
	}

	public void setDblPhyStk(Double dblPhyStk) {
		this.dblPhyStk = dblPhyStk;
	}

	public Double getDblVariance() {
		return dblVariance;
	}

	public void setDblVariance(Double dblVariance) {
		this.dblVariance = dblVariance;
	}

}
