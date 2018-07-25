package com.sanguine.bean;

import java.util.List;

import javax.persistence.Column;

import com.sanguine.model.clsDeliveryScheduleModuledtl;

public class clsDeliveryScheduleBean {

	private String strDSCode;

	private String strPOCode;

	private String dteDSDate;

	private String strAgainst;

	private String strNarration;

	private String strLocCode;

	private String strCloseDS;

	private List<clsDeliveryScheduleModuledtl> listObjDeliveryScheduleModuledtl;

	public String getStrDSCode() {
		return strDSCode;
	}

	public void setStrDSCode(String strDSCode) {
		this.strDSCode = strDSCode;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
	}

	public String getDteDSDate() {
		return dteDSDate;
	}

	public void setDteDSDate(String dteDSDate) {
		this.dteDSDate = dteDSDate;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public List<clsDeliveryScheduleModuledtl> getListObjDeliveryScheduleModuledtl() {
		return listObjDeliveryScheduleModuledtl;
	}

	public void setListObjDeliveryScheduleModuledtl(List<clsDeliveryScheduleModuledtl> listObjDeliveryScheduleModuledtl) {
		this.listObjDeliveryScheduleModuledtl = listObjDeliveryScheduleModuledtl;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrCloseDS() {
		return strCloseDS;
	}

	public void setStrCloseDS(String strCloseDS) {
		this.strCloseDS = strCloseDS;
	}

}
