package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsRateContractDtlModel;
import com.sanguine.model.clsTCMasterModel;

public class clsRateContractBean {
	private String strRateContNo;

	private String dtRateContDate;

	private String strSuppCode;

	private String strSuppName;

	private String dtFromDate;

	private String dtToDate;

	private String strDateChg;

	private String strAuthorise;

	private String strCurrency;

	private String strProdFlag;

	private double dblConversion;

	private String strTC1, strTC2, strTC3, strTC4, strTC5, strTC6, strTC7, strTC8, strTC9, strTC10, strTC11, strTC12;

	private List<clsRateContractDtlModel> listRateContDtl;

	private List<clsTCMasterModel> listTCMaster;

	public List<clsRateContractDtlModel> getListRateContDtl() {
		return listRateContDtl;
	}

	public void setListRateContDtl(List<clsRateContractDtlModel> listRateContDtl) {
		this.listRateContDtl = listRateContDtl;
	}

	public String getStrRateContNo() {
		return strRateContNo;
	}

	public void setStrRateContNo(String strRateContNo) {
		this.strRateContNo = strRateContNo;
	}

	public String getDtRateContDate() {
		return dtRateContDate;
	}

	public void setDtRateContDate(String dtRateContDate) {
		this.dtRateContDate = dtRateContDate;
	}

	public String getStrSuppCode() {
		return strSuppCode;
	}

	public void setStrSuppCode(String strSuppCode) {
		this.strSuppCode = strSuppCode;
	}

	public String getStrSuppName() {
		return strSuppName;
	}

	public void setStrSuppName(String strSuppName) {
		this.strSuppName = strSuppName;
	}

	public String getDtFromDate() {
		return dtFromDate;
	}

	public void setDtFromDate(String dtFromDate) {
		this.dtFromDate = dtFromDate;
	}

	public String getDtToDate() {
		return dtToDate;
	}

	public void setDtToDate(String dtToDate) {
		this.dtToDate = dtToDate;
	}

	public String getStrDateChg() {
		return strDateChg;
	}

	public void setStrDateChg(String strDateChg) {
		this.strDateChg = strDateChg;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public String getStrProdFlag() {
		return strProdFlag;
	}

	public void setStrProdFlag(String strProdFlag) {
		this.strProdFlag = strProdFlag;
	}

	public double getDblConversion() {
		return dblConversion;
	}

	public void setDblConversion(double dblConversion) {
		this.dblConversion = dblConversion;
	}

	public String getStrTC1() {
		return strTC1;
	}

	public void setStrTC1(String strTC1) {
		this.strTC1 = strTC1;
	}

	public String getStrTC2() {
		return strTC2;
	}

	public void setStrTC2(String strTC2) {
		this.strTC2 = strTC2;
	}

	public String getStrTC3() {
		return strTC3;
	}

	public void setStrTC3(String strTC3) {
		this.strTC3 = strTC3;
	}

	public String getStrTC4() {
		return strTC4;
	}

	public void setStrTC4(String strTC4) {
		this.strTC4 = strTC4;
	}

	public String getStrTC5() {
		return strTC5;
	}

	public void setStrTC5(String strTC5) {
		this.strTC5 = strTC5;
	}

	public String getStrTC6() {
		return strTC6;
	}

	public void setStrTC6(String strTC6) {
		this.strTC6 = strTC6;
	}

	public String getStrTC7() {
		return strTC7;
	}

	public void setStrTC7(String strTC7) {
		this.strTC7 = strTC7;
	}

	public String getStrTC8() {
		return strTC8;
	}

	public void setStrTC8(String strTC8) {
		this.strTC8 = strTC8;
	}

	public String getStrTC9() {
		return strTC9;
	}

	public void setStrTC9(String strTC9) {
		this.strTC9 = strTC9;
	}

	public String getStrTC10() {
		return strTC10;
	}

	public void setStrTC10(String strTC10) {
		this.strTC10 = strTC10;
	}

	public String getStrTC11() {
		return strTC11;
	}

	public void setStrTC11(String strTC11) {
		this.strTC11 = strTC11;
	}

	public String getStrTC12() {
		return strTC12;
	}

	public void setStrTC12(String strTC12) {
		this.strTC12 = strTC12;
	}

	public List<clsTCMasterModel> getListTCMaster() {
		return listTCMaster;
	}

	public void setListTCMaster(List<clsTCMasterModel> listTCMaster) {
		this.listTCMaster = listTCMaster;
	}

}
