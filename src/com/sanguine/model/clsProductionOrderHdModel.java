package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblproductionorderhd")
@IdClass(clsProductionOrderHdModel_ID.class)
public class clsProductionOrderHdModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsProductionOrderHdModel() {
	}

	public clsProductionOrderHdModel(clsProductionOrderHdModel_ID clsProductionOrderHdModel_ID) {
		strOPCode = clsProductionOrderHdModel_ID.getStrOPCode();
		strClientCode = clsProductionOrderHdModel_ID.getStrClientCode();
	}

	private long intid;
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strOPCode", column = @Column(name = "strOPCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private String strOPCode;
	private String dtOPDate;
	private String strStatus;
	private String dtFulmtDate;
	private String strLocCode;
	private String dtfulfilled;
	private String strNarration;
	private String strUserCreated;
	private String dtDateCreated;
	private String strUserModified;
	private String dtLastModified;
	private String strBOMFlag;
	private String strAuthorise;
	private String strImgName;
	private String strAgainst;
	private String strCode;
	private String strClientCode;
	private String strLocName;

	@Column(name = "intid", updatable = false)
	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	@Id
	@Column(name = "strOPCode")
	public String getStrOPCode() {
		return strOPCode;
	}

	public void setStrOPCode(String strOPCode) {
		this.strOPCode = strOPCode;
	}

	@Column(name = "dtOPDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtOPDate() {
		return dtOPDate;
	}

	public void setDtOPDate(String dtOPDate) {
		this.dtOPDate = dtOPDate;
	}

	@Column(name = "strStatus", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	@Column(name = "dtFulmtDate", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtFulmtDate() {
		return dtFulmtDate;
	}

	public void setDtFulmtDate(String dtFulmtDate) {
		this.dtFulmtDate = dtFulmtDate;
	}

	@Column(name = "strLocCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	@Column(name = "dtfulfilled", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtfulfilled() {
		return dtfulfilled;
	}

	public void setDtfulfilled(String dtfulfilled) {
		this.dtfulfilled = dtfulfilled;
	}

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	@Column(name = "strUserCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	@Column(name = "dtDateCreated", updatable = false, columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	@Column(name = "strBOMFlag", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrBOMFlag() {
		return strBOMFlag;
	}

	public void setStrBOMFlag(String strBOMFlag) {
		this.strBOMFlag = strBOMFlag;
	}

	@Column(name = "strAuthorise", columnDefinition = "VARCHAR(5) NOT NULL dafault ''")
	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	@Column(name = "strImgName", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrImgName() {
		return strImgName;
	}

	public void setStrImgName(String strImgName) {
		this.strImgName = strImgName;
	}

	@Column(name = "strAgainst", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	@Column(name = "strCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	private int intLevel = 0;

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
		// return value !=null && (value instanceof String &&
		// value.toString().length()>0) ? value : defaultValue;
	}
}
