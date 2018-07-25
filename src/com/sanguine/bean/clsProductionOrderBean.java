package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsProductionOrderDtlModel;

public class clsProductionOrderBean {

	private String strOPCode;
	private String dtOPDate;
	private String strStatus;
	private String dtFulmtDate;
	private String dtfulfilled;
	private String strLocCode;
	private String strAgainst;
	private String strNarration;
	private String strDocCode;
	private List<clsProductionOrderDtlModel> listclsProductionOrderDtlModel;

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrOPCode() {
		return strOPCode;
	}

	public void setStrOPCode(String strOPCode) {
		this.strOPCode = strOPCode;
	}

	public List<clsProductionOrderDtlModel> getListclsProductionOrderDtlModel() {
		return listclsProductionOrderDtlModel;
	}

	public void setListclsProductionOrderDtlModel(List<clsProductionOrderDtlModel> listclsProductionOrderDtlModel) {
		this.listclsProductionOrderDtlModel = listclsProductionOrderDtlModel;
	}

	public String getDtOPDate() {
		return dtOPDate;
	}

	public void setDtOPDate(String dtOPDate) {
		this.dtOPDate = dtOPDate;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getDtFulmtDate() {
		return dtFulmtDate;
	}

	public void setDtFulmtDate(String dtFulmtDate) {
		this.dtFulmtDate = dtFulmtDate;
	}

	public String getDtfulfilled() {
		return dtfulfilled;
	}

	public void setDtfulfilled(String dtfulfilled) {
		this.dtfulfilled = dtfulfilled;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrDocCode() {
		return strDocCode;
	}

	public void setStrDocCode(String strDocCode) {
		this.strDocCode = strDocCode;
	}

}
