package com.sanguine.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.sanguine.model.clsOpeningStkDtl;

public class clsOpeningStkBean {
	@NotEmpty
	private String strLocCode;

	private String strOpStkCode;

	private String dtExpDate;

	private String strConversionUOM;

	private List<clsOpeningStkDtl> listOpStkDtl;

	public String getStrOpStkCode() {
		return strOpStkCode;
	}

	public void setStrOpStkCode(String strOpStkCode) {
		this.strOpStkCode = strOpStkCode;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getDtExpDate() {
		return dtExpDate;
	}

	public void setDtExpDate(String dtExpDate) {
		this.dtExpDate = dtExpDate;
	}

	public List<clsOpeningStkDtl> getListOpStkDtl() {
		return listOpStkDtl;
	}

	public void setListOpStkDtl(List<clsOpeningStkDtl> listOpStkDtl) {
		this.listOpStkDtl = listOpStkDtl;
	}

	public String getStrConversionUOM() {
		return strConversionUOM;
	}

	public void setStrConversionUOM(String strConversionUOM) {
		this.strConversionUOM = strConversionUOM;
	}

}
