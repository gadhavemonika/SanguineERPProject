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

@Entity
@Table(name = "tblmishd")
@IdClass(clsMISHdModel_ID.class)
public class clsMISHdModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strMISCode, strAgainst, strReqCode, strLocFrom, strLocTo, strNarration, strAuthorise, strUserModified, strUserCreated, strClientCode;
	private long intid;
	private String strLocFromName, strLocToName;
	private String dtMISDate, dtCreatedDate, dtLastModified;
	private double dblTotalAmt;

	private int intLevel = 0;

	public clsMISHdModel() {

	}

	public clsMISHdModel(clsMISHdModel_ID clsMISHdModel_ID) {
		strMISCode = clsMISHdModel_ID.getStrMISCode();
		strClientCode = clsMISHdModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strMISCode", column = @Column(name = "strMISCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsMISDtlModel> clsMISDtlModel = new ArrayList<clsMISDtlModel>();;

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblmisdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strMISCode") })
	public List<clsMISDtlModel> getClsMISDtlModel() {
		return clsMISDtlModel;
	}

	public void setClsMISDtlModel(List<clsMISDtlModel> clsMISDtlModel) {
		this.clsMISDtlModel = clsMISDtlModel;
	}

	@Column(name = "intid", updatable = false)
	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	@Id
	@Column(name = "strMISCode")
	public String getStrMISCode() {
		return strMISCode;
	}

	public void setStrMISCode(String strMISCode) {
		this.strMISCode = strMISCode;
	}

	@Column(name = "dblTotalAmt", columnDefinition = "DECIMAL(18,4) NOT NULL default 0.00")
	public double getDblTotalAmt() {
		return dblTotalAmt;
	}

	public void setDblTotalAmt(double dblTotalAmt) {
		this.dblTotalAmt = dblTotalAmt;
	}

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	@Column(name = "strReqCode", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	@Column(name = "strLocFrom", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrLocFrom() {
		return strLocFrom;
	}

	public void setStrLocFrom(String strLocFrom) {
		this.strLocFrom = strLocFrom;
	}

	@Column(name = "strLocTo", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrLocTo() {
		return strLocTo;
	}

	public void setStrLocTo(String strLocTo) {
		this.strLocTo = strLocTo;
	}

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "strUserCreated", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	@Column(name = "dtMISDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtMISDate() {
		return dtMISDate;
	}

	public void setDtMISDate(String dtMISDate) {
		this.dtMISDate = dtMISDate;
	}

	@Column(name = "dtCreatedDate", nullable = false, updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
	}

	@Column(name = "dtLastModified")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Transient
	public String getStrLocFromName() {
		return strLocFromName;
	}

	public void setStrLocFromName(String strLocFromName) {
		this.strLocFromName = strLocFromName;
	}

	@Transient
	public String getStrLocToName() {
		return strLocToName;
	}

	public void setStrLocToName(String strLocToName) {
		this.strLocToName = strLocToName;
	}

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = (int) setDefaultValue(intLevel, "0");
	}

	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && !(value instanceof String)) {
			return value;

		} else {
			return defaultValue;
		}

	}

}
