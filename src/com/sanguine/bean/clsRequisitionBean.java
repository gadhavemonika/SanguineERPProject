package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.model.clsRequisitionDtlModel;

public class clsRequisitionBean {
	private String strReqCode, dtReqDate, dtReqiredDate, strLocBy, strLocOn, strNarration, strAuthorise;
	private String strUserCreated, dtCreatedDate, strUserModified, dtLastModified, strWoCode;
	private String strCloseReq, strAgainst, strClientCode, strSaveSatandard;
	private long intid;
	private double dblWOQty, dblSubTotal;
	private List<clsRequisitionDtlModel> listReqDtl = new ArrayList<clsRequisitionDtlModel>();
	private String strUOM, strReqFrom;;
	private String strSessionCode;

	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public List<clsRequisitionDtlModel> getListReqDtl() {
		return listReqDtl;
	}

	public void setListReqDtl(List<clsRequisitionDtlModel> listReqDtl) {
		this.listReqDtl = listReqDtl;
	}

	public String getDtReqDate() {
		return dtReqDate;
	}

	public void setDtReqDate(String dtReqDate) {
		this.dtReqDate = dtReqDate;
	}

	public String getStrLocBy() {
		return strLocBy;
	}

	public void setStrLocBy(String strLocBy) {
		this.strLocBy = strLocBy;
	}

	public String getStrLocOn() {
		return strLocOn;
	}

	public void setStrLocOn(String strLocOn) {
		this.strLocOn = strLocOn;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrWoCode() {
		return strWoCode;
	}

	public void setStrWoCode(String strWoCode) {
		this.strWoCode = strWoCode;
	}

	public String getStrCloseReq() {
		return strCloseReq;
	}

	public void setStrCloseReq(String strCloseReq) {
		this.strCloseReq = strCloseReq;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public double getDblWOQty() {
		return dblWOQty;
	}

	public void setDblWOQty(double dblWOQty) {
		this.dblWOQty = dblWOQty;
	}

	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
	}

	public String getDtReqiredDate() {
		return dtReqiredDate;
	}

	public void setDtReqiredDate(String dtReqiredDate) {
		this.dtReqiredDate = dtReqiredDate;
	}

	public String getStrSaveSatandard() {
		return strSaveSatandard;
	}

	public void setStrSaveSatandard(String strSaveSatandard) {
		this.strSaveSatandard = strSaveSatandard;
	}

	public String getStrReqFrom() {
		return strReqFrom;
	}

	public void setStrReqFrom(String strReqFrom) {
		this.strReqFrom = strReqFrom;
	}

	public String getStrSessionCode() {
		return strSessionCode;
	}

	public void setStrSessionCode(String strSessionCode) {
		this.strSessionCode = strSessionCode;
	}

}
