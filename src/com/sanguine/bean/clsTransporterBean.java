package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.model.clsTransporterModelDtl;

public class clsTransporterBean {

	private String strTransCode;

	private String strTransName;

	private String strDesc;

	private String strVehCode;

	List<clsTransporterModelDtl> listclsTransporterModelDtl = new ArrayList<clsTransporterModelDtl>();

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrTransName() {
		return strTransName;
	}

	public void setStrTransName(String strTransName) {
		this.strTransName = strTransName;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrVehCode() {
		return strVehCode;
	}

	public void setStrVehCode(String strVehCode) {
		this.strVehCode = strVehCode;
	}

	public List<clsTransporterModelDtl> getListclsTransporterModelDtl() {
		return listclsTransporterModelDtl;
	}

	public void setListclsTransporterModelDtl(List<clsTransporterModelDtl> listclsTransporterModelDtl) {
		this.listclsTransporterModelDtl = listclsTransporterModelDtl;
	}

}
