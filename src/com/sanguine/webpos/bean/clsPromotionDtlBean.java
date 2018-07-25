package com.sanguine.webpos.bean;

public class clsPromotionDtlBean {

	private String strItemCode;

	private String strItemName;

	private long strRate;

	private Boolean strApplicableYN;

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrItemName() {
		return strItemName;
	}

	public void setStrItemName(String strItemName) {
		this.strItemName = strItemName;
	}

	public Boolean getStrApplicableYN() {
		return strApplicableYN;
	}

	public void setStrApplicableYN(Boolean strApplicableYN) {
		this.strApplicableYN = strApplicableYN;
	}

	public long getStrRate() {
		return strRate;
	}

	public void setStrRate(long strRate) {
		this.strRate = strRate;
	}

}
