package com.sanguine.crm.bean;

import java.util.List;

import com.mysql.jdbc.Blob;
import com.sanguine.crm.model.clsSalesOrderBOMModel;

public class clsSalesOrderBOMBean {

	// Variable Declaration

	private String strAgainst;

	private String strSOCode;

	private String dteSODate;

	private Long intId;

	private String strProdCode;

	private String strProdName;

	private Blob strProdImage;

	private String strProcessCode;

	private String strProcessName;

	private String strParentCode;

	private String strParentName;

	private String strParentProcessCode;

	private String strParentProcessName;

	private String strChildCode;

	private String strChildName;

	private Double dblQty;

	private Double dblWeight;

	private Long intindex;

	private String strRemarks;

	private String strUserCreated;

	private String strUserModified;

	private String strClientCode;

	private List<clsSalesOrderBOMModel> listChildProduct;

	// Setter-Getter Methods

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getDteSODate() {
		return dteSODate;
	}

	public void setDteSODate(String dteSODate) {
		this.dteSODate = dteSODate;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public Blob getStrProdImage() {
		return strProdImage;
	}

	public void setStrProdImage(Blob strProdImage) {
		this.strProdImage = strProdImage;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrParentCode() {
		return strParentCode;
	}

	public void setStrParentCode(String strParentCode) {
		this.strParentCode = strParentCode;
	}

	public String getStrParentName() {
		return strParentName;
	}

	public void setStrParentName(String strParentName) {
		this.strParentName = strParentName;
	}

	public String getStrParentProcessCode() {
		return strParentProcessCode;
	}

	public void setStrParentProcessCode(String strParentProcessCode) {
		this.strParentProcessCode = strParentProcessCode;
	}

	public String getStrParentProcessName() {
		return strParentProcessName;
	}

	public void setStrParentProcessName(String strParentProcessName) {
		this.strParentProcessName = strParentProcessName;
	}

	public String getStrChildCode() {
		return strChildCode;
	}

	public void setStrChildCode(String strChildCode) {
		this.strChildCode = strChildCode;
	}

	public String getStrChildName() {
		return strChildName;
	}

	public void setStrChildName(String strChildName) {
		this.strChildName = strChildName;
	}

	public Double getDblQty() {
		return dblQty;
	}

	public void setDblQty(Double dblQty) {
		this.dblQty = dblQty;
	}

	public Double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(Double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public Long getIntindex() {
		return intindex;
	}

	public void setIntindex(Long intindex) {
		this.intindex = intindex;
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = strRemarks;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public List<clsSalesOrderBOMModel> getListChildProduct() {
		return listChildProduct;
	}

	public void setListChildProduct(List<clsSalesOrderBOMModel> listChildProduct) {
		this.listChildProduct = listChildProduct;
	}

}
