package com.sanguine.bean;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import com.sanguine.model.clsBomDtlModel;

public class clsRecipeMasterBean {
	private String strBOMCode;

	@NotEmpty
	private String strParentCode;

	private String strParentName;

	private String strProcessCode;

	private String dtValidFrom;

	private String dtValidTo;

	private String strPartNo;

	private String strProdType;

	private String strSGCode;

	private String strSGName;

	private String strProcessName;

	private String strPOSItemCode;

	private String strType;

	private String strUOM;

	private double dblQty;

	private String strMethod;

	private List<clsBomDtlModel> listBomDtlModel;

	public String getStrBOMCode() {
		return strBOMCode;
	}

	public void setStrBOMCode(String strBOMCode) {
		this.strBOMCode = strBOMCode;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getDtValidFrom() {
		return dtValidFrom;
	}

	public void setDtValidFrom(String dtValidFrom) {
		this.dtValidFrom = dtValidFrom;
	}

	public String getDtValidTo() {
		return dtValidTo;
	}

	public void setDtValidTo(String dtValidTo) {
		this.dtValidTo = dtValidTo;
	}

	public String getStrPartNo() {
		return strPartNo;
	}

	public void setStrPartNo(String strPartNo) {
		this.strPartNo = strPartNo;
	}

	public String getStrProdType() {
		return strProdType;
	}

	public void setStrProdType(String strProdType) {
		this.strProdType = strProdType;
	}

	public String getStrSGCode() {
		return strSGCode;
	}

	public void setStrSGCode(String strSGCode) {
		this.strSGCode = strSGCode;
	}

	public String getStrSGName() {
		return strSGName;
	}

	public void setStrSGName(String strSGName) {
		this.strSGName = strSGName;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public List<clsBomDtlModel> getListBomDtlModel() {
		return listBomDtlModel;
	}

	public void setListBomDtlModel(List<clsBomDtlModel> listBomDtlModel) {
		this.listBomDtlModel = listBomDtlModel;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrMethod() {
		return strMethod;
	}

	public void setStrMethod(String strMethod) {
		this.strMethod = strMethod;
	}

}
