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
@Table(name = "tblpurchaseindendhd")
@IdClass(clsPurchaseIndentHdModel_ID.class)
public class clsPurchaseIndentHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPurchaseIndentHdModel() {
	}

	public clsPurchaseIndentHdModel(clsPurchaseIndentHdModel_ID clsPurchaseIndentHdModel_ID) {
		strPICode = clsPurchaseIndentHdModel_ID.getStrPICode();
		strClientCode = clsPurchaseIndentHdModel_ID.getStrClientCode();
	}

	@Column(name = "intId", updatable = false)
	private long intId;

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPICode", column = @Column(name = "strPICode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblpurchaseindenddtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strPICode") })
	private List<clsPurchaseIndentDtlModel> clsPurchaseIndentDtlModel = new ArrayList<clsPurchaseIndentDtlModel>();;

	public List<clsPurchaseIndentDtlModel> getClsPurchaseIndentDtlModel() {
		return clsPurchaseIndentDtlModel;
	}

	public void setClsPurchaseIndentDtlModel(List<clsPurchaseIndentDtlModel> clsPurchaseIndentDtlModel) {
		this.clsPurchaseIndentDtlModel = clsPurchaseIndentDtlModel;
	}

	@Column(name = "strPICode")
	private String strPICode;

	@Column(name = "dtPIDate")
	private String dtPIDate;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "dblTotal", columnDefinition = "DECIMAL(18,4) NOT NULL default '0.00'")
	private double dblTotal;

	@Transient
	private String strLocName;

	@Column(name = "strClosePI", columnDefinition = "VARCHAR(3) NOT NULL default 'No'")
	private String strClosePI;

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strAgainst;

	@Column(name = "strDocCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strDocCode;

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrPICode() {
		return strPICode;
	}

	public void setStrPICode(String strPICode) {
		this.strPICode = strPICode;
	}

	public String getDtPIDate() {
		return dtPIDate;
	}

	public void setDtPIDate(String dtPIDate) {
		this.dtPIDate = dtPIDate;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
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

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	@Column(name = "intLevel", columnDefinition = "Int(8) default '0'")
	private int intLevel = 0;

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
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = dblTotal;
	}

	public String getStrClosePI() {
		return strClosePI;
	}

	public void setStrClosePI(String strClosePI) {
		this.strClosePI = strClosePI;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = (String) setDefaultValue(strAgainst, "");
	}

	public String getStrDocCode() {
		return strDocCode;
	}

	public void setStrDocCode(String strDocCode) {
		this.strDocCode = (String) setDefaultValue(strDocCode, "");
	}
}
