package com.sanguine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "tblreqhd")
@IdClass(clsRequisitionHdModel_ID.class)
public class clsRequisitionHdModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String strReqCode;
	private String dtReqDate;
	private String strLocBy;
	private String strLocOn;
	private String strNarration;
	private String strAuthorise;
	private String strUserCreated;
	private String dtCreatedDate;
	private String strUserModified;
	private String dtLastModified;
	private String strWoCode;
	private String strCloseReq;
	private String strAgainst;
	private String strClientCode;
	private String strLocOnName;
	private String strLocByName;
	private long intId;
	private double dblWOQty;
	private double dblSubTotal;
	private String dtReqiredDate;
	private String strReqFrom;
	private String strSessionCode;

	private int intLevel = 0;

	public clsRequisitionHdModel() {
	}

	public clsRequisitionHdModel(clsRequisitionHdModel_ID clsRequisitionHdModel_ID) {
		strReqCode = clsRequisitionHdModel_ID.getStrReqCode();
		strClientCode = clsRequisitionHdModel_ID.getStrClientCode();
	}

	@Autowired(required = false)
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReqCode", column = @Column(name = "strReqCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblreqdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strReqCode") })
	private List<clsRequisitionDtlModel> clsRequisitionDtlModel = new ArrayList<clsRequisitionDtlModel>();

	// private List<clsProductStandardModel> objListReqStandardModel= new
	// ArrayList<clsProductStandardModel>();

	@Column(name = "strReqCode")
	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	@Column(name = "dtReqDate")
	public String getDtReqDate() {
		return dtReqDate;
	}

	public void setDtReqDate(String dtReqDate) {
		this.dtReqDate = dtReqDate;
	}

	@Column(name = "strLocBy")
	public String getStrLocBy() {
		return strLocBy;
	}

	public void setStrLocBy(String strLocBy) {
		this.strLocBy = strLocBy;
	}

	@Column(name = "strLocOn")
	public String getStrLocOn() {
		return strLocOn;
	}

	public void setStrLocOn(String strLocOn) {
		this.strLocOn = strLocOn;
	}

	@Column(name = "strNarration", columnDefinition = "VARCHAR(200) default '' ")
	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	@Column(name = "strAuthorise", updatable = false, columnDefinition = "VARCHAR(4) default 'No' ")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "dtCreatedDate", nullable = false, updatable = false)
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

	@Column(name = "dtLastModified")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "strWoCode")
	public String getStrWoCode() {
		return strWoCode;
	}

	public void setStrWoCode(String strWoCode) {
		this.strWoCode = strWoCode;
	}

	@Column(name = "strCloseReq")
	public String getStrCloseReq() {
		return strCloseReq;
	}

	public void setStrCloseReq(String strCloseReq) {
		this.strCloseReq = strCloseReq;
	}

	@Column(name = "strAgainst")
	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	@Column(name = "strClientCode")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "intId", nullable = false, updatable = false)
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	@Column(name = "dblWOQty")
	public double getDblWOQty() {
		return dblWOQty;
	}

	public void setDblWOQty(double dblWOQty) {
		this.dblWOQty = dblWOQty;
	}

	@Column(name = "dblSubTotal")
	public double getDblSubTotal() {
		return dblSubTotal;
	}

	public void setDblSubTotal(double dblSubTotal) {
		this.dblSubTotal = dblSubTotal;
	}

	@Transient
	public String getStrLocOnName() {
		return strLocOnName;
	}

	public void setStrLocOnName(String strLocOnName) {
		this.strLocOnName = strLocOnName;
	}

	@Transient
	public String getStrLocByName() {
		return strLocByName;
	}

	public void setStrLocByName(String strLocByName) {
		this.strLocByName = strLocByName;
	}

	public List<clsRequisitionDtlModel> getClsRequisitionDtlModel() {
		return clsRequisitionDtlModel;
	}

	public void setClsRequisitionDtlModel(List<clsRequisitionDtlModel> clsRequisitionDtlModel) {
		this.clsRequisitionDtlModel = clsRequisitionDtlModel;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name = "intLevel", nullable = false, columnDefinition = "Int(8) default '0'")
	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}

	@Column(name = "dtReqiredDate")
	public String getDtReqiredDate() {
		return dtReqiredDate;
	}

	public void setDtReqiredDate(String dtReqiredDate) {
		this.dtReqiredDate = dtReqiredDate;
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	@Column(name = "strReqFrom", updatable = false, columnDefinition = "VARCHAR(10) default 'System' ")
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
