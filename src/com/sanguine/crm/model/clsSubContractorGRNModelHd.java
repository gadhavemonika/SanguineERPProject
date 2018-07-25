package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblscreturnhd")
@IdClass(clsSubContractorGRNModelHd_ID.class)
public class clsSubContractorGRNModelHd implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSubContractorGRNModelHd() {
	}

	public clsSubContractorGRNModelHd(clsSubContractorGRNModelHd_ID objModelID) {
		strSRCode = objModelID.getStrSRCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSRCode", column = @Column(name = "strSRCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSRCode")
	private String strSRCode;

	@Column(name = "intid")
	private long intid;

	@Column(name = "strSRNo")
	private String strSRNo;

	@Column(name = "dteSRDate")
	private String dteSRDate;

	@Column(name = "strSCCode")
	private String strSCCode;

	@Column(name = "strSCDNCode")
	private String strSCDNCode;

	@Column(name = "strVerRemark")
	private String strVerRemark;

	@Column(name = "strDispAction")
	private String strDispAction;

	@Column(name = "strPartDel")
	private String strPartDel;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strJWCode")
	private String strJWCode;

	@Column(name = "strVehNo")
	private String strVehNo;

	@Column(name = "strMInBy")
	private String strMInBy;

	@Column(name = "strTimeInOut")
	private String strTimeInOut;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strNo")
	private String strNo;

	@Column(name = "strInRefNo")
	private String strInRefNo;

	@Column(name = "dteInRefDate")
	private String dteInRefDate;

	@Column(name = "dteSCDCDate")
	private String dteSCDCDate;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "dblTotQty")
	private String dblTotQty;

	@Column(name = "dblTotWt")
	private String dblTotWt;

	@Column(name = "dblTotAmt")
	private String dblTotAmt;

	// Setter-Getter Methods
	public String getStrSRCode() {
		return strSRCode;
	}

	public void setStrSRCode(String strSRCode) {
		this.strSRCode = (String) setDefaultValue(strSRCode, "NA");
	}

	public String getStrSRNo() {
		return strSRNo;
	}

	public void setStrSRNo(String strSRNo) {
		this.strSRNo = strSRNo;
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = (Long) setDefaultValue(intid, "NA");
	}

	public String getDteSRDate() {
		return dteSRDate;
	}

	public void setDteSRDate(String dteSRDate) {
		this.dteSRDate = dteSRDate;
	}

	public String getStrSCCode() {
		return strSCCode;
	}

	public void setStrSCCode(String strSCCode) {
		this.strSCCode = (String) setDefaultValue(strSCCode, "NA");
	}

	public String getStrVerRemark() {
		return strVerRemark;
	}

	public void setStrVerRemark(String strVerRemark) {
		this.strVerRemark = (String) setDefaultValue(strVerRemark, "NA");
	}

	public String getStrDispAction() {
		return strDispAction;
	}

	public void setStrDispAction(String strDispAction) {
		this.strDispAction = (String) setDefaultValue(strDispAction, "NA");
	}

	public String getStrPartDel() {
		return strPartDel;
	}

	public void setStrPartDel(String strPartDel) {
		this.strPartDel = (String) setDefaultValue(strPartDel, "NA");
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = (String) setDefaultValue(strLocCode, "NA");
	}

	public String getStrJWCode() {
		return strJWCode;
	}

	public void setStrJWCode(String strJWCode) {
		this.strJWCode = (String) setDefaultValue(strJWCode, "NA");
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = (String) setDefaultValue(strVehNo, "NA");
	}

	public String getStrMInBy() {
		return strMInBy;
	}

	public void setStrMInBy(String strMInBy) {
		this.strMInBy = (String) setDefaultValue(strMInBy, "NA");
	}

	public String getStrTimeInOut() {
		return strTimeInOut;
	}

	public void setStrTimeInOut(String strTimeInOut) {
		this.strTimeInOut = (String) setDefaultValue(strTimeInOut, "NA");
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = (String) setDefaultValue(strAgainst, "NA");
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = (String) setDefaultValue(strNo, "NA");
	}

	public String getStrInRefNo() {
		return strInRefNo;
	}

	public void setStrInRefNo(String strInRefNo) {
		this.strInRefNo = (String) setDefaultValue(strInRefNo, "NA");
	}

	public String getDteInRefDate() {
		return dteInRefDate;
	}

	public void setDteInRefDate(String dteInRefDate) {
		this.dteInRefDate = dteInRefDate;
	}

	public String getDteSCDCDate() {
		return dteSCDCDate;
	}

	public void setDteSCDCDate(String dteSCDCDate) {
		this.dteSCDCDate = dteSCDCDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = (String) setDefaultValue(strAuthorise, "N");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

	public String getDblTotQty() {
		return dblTotQty;
	}

	public void setDblTotQty(String dblTotQty) {
		this.dblTotQty = dblTotQty;
	}

	public String getDblTotWt() {
		return dblTotWt;
	}

	public void setDblTotWt(String dblTotWt) {
		this.dblTotWt = dblTotWt;
	}

	public String getDblTotAmt() {
		return dblTotAmt;
	}

	public void setDblTotAmt(String dblTotAmt) {
		this.dblTotAmt = dblTotAmt;
	}

	public String getStrSCDNCode() {
		return strSCDNCode;
	}

	public void setStrSCDNCode(String strSCDNCode) {
		this.strSCDNCode = strSCDNCode;
	}

}
