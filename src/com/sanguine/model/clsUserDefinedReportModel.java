package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbluserdefinedreport")
public class clsUserDefinedReportModel {
	@Id
	@Column(name = "strReportCode")
	private String strReportCode;

	@Column(name = "strReportDesc")
	private String strReportDesc;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strTable")
	private String strTable;

	@Column(name = "strQuery")
	private String strQuery;

	@Column(name = "strSelectedFields")
	private String strSelectedFields;

	@Column(name = "strCriteria")
	private String strCriteria;

	@Column(name = "strLayout")
	private String strLayout;

	@Column(name = "strFieldSize")
	private String strFieldSize;

	@Column(name = "strGroupBy")
	private String strGroupBy;

	@Column(name = "strSortBy")
	private String strSortBy;

	@Column(name = "strSubTotal")
	private String strSubTotal;

	@Column(name = "strGrandTotal")
	private String strGrandTotal;

	@Column(name = "strHeadLine1")
	private String strHeadLine1;

	@Column(name = "strHeadLine2")
	private String strHeadLine2;

	@Column(name = "strImgURL")
	private String strImgURL;

	@Column(name = "strFootLine1")
	private String strFootLine1;

	@Column(name = "strFootLine2")
	private String strFootLine2;

	@Column(name = "strSearchCode")
	private String strSearchCode;

	@Column(name = "strCategory")
	private String strCategory;

	@Column(name = "strOperational")
	private String strOperational;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "strUserCode")
	private String strUserCode;

	public String getStrReportCode() {
		return strReportCode;
	}

	public void setStrReportCode(String strReportCode) {
		this.strReportCode = strReportCode;
	}

	public String getStrReportDesc() {
		return strReportDesc;
	}

	public void setStrReportDesc(String strReportDesc) {
		this.strReportDesc = strReportDesc;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrTable() {
		return strTable;
	}

	public void setStrTable(String strTable) {
		this.strTable = strTable;
	}

	public String getStrQuery() {
		return strQuery;
	}

	public void setStrQuery(String strQuery) {
		this.strQuery = strQuery;
	}

	public String getStrSelectedFields() {
		return strSelectedFields;
	}

	public void setStrSelectedFields(String strSelectedFields) {
		this.strSelectedFields = strSelectedFields;
	}

	public String getStrCriteria() {
		return strCriteria;
	}

	public void setStrCriteria(String strCriteria) {
		this.strCriteria = strCriteria;
	}

	public String getStrLayout() {
		return strLayout;
	}

	public void setStrLayout(String strLayout) {
		this.strLayout = strLayout;
	}

	public String getStrFieldSize() {
		return strFieldSize;
	}

	public void setStrFieldSize(String strFieldSize) {
		this.strFieldSize = strFieldSize;
	}

	public String getStrGroupBy() {
		return strGroupBy;
	}

	public void setStrGroupBy(String strGroupBy) {
		this.strGroupBy = strGroupBy;
	}

	public String getStrSortBy() {
		return strSortBy;
	}

	public void setStrSortBy(String strSortBy) {
		this.strSortBy = strSortBy;
	}

	public String getStrSubTotal() {
		return strSubTotal;
	}

	public void setStrSubTotal(String strSubTotal) {
		this.strSubTotal = strSubTotal;
	}

	public String getStrGrandTotal() {
		return strGrandTotal;
	}

	public void setStrGrandTotal(String strGrandTotal) {
		this.strGrandTotal = strGrandTotal;
	}

	public String getStrHeadLine1() {
		return strHeadLine1;
	}

	public void setStrHeadLine1(String strHeadLine1) {
		this.strHeadLine1 = strHeadLine1;
	}

	public String getStrHeadLine2() {
		return strHeadLine2;
	}

	public void setStrHeadLine2(String strHeadLine2) {
		this.strHeadLine2 = strHeadLine2;
	}

	public String getStrImgURL() {
		return strImgURL;
	}

	public void setStrImgURL(String strImgURL) {
		this.strImgURL = strImgURL;
	}

	public String getStrFootLine1() {
		return strFootLine1;
	}

	public void setStrFootLine1(String strFootLine1) {
		this.strFootLine1 = strFootLine1;
	}

	public String getStrFootLine2() {
		return strFootLine2;
	}

	public void setStrFootLine2(String strFootLine2) {
		this.strFootLine2 = strFootLine2;
	}

	public String getStrSearchCode() {
		return strSearchCode;
	}

	public void setStrSearchCode(String strSearchCode) {
		this.strSearchCode = strSearchCode;
	}

	public String getStrCategory() {
		return strCategory;
	}

	public void setStrCategory(String strCategory) {
		this.strCategory = strCategory;
	}

	public String getStrOperational() {
		return strOperational;
	}

	public void setStrOperational(String strOperational) {
		this.strOperational = strOperational;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}
}
