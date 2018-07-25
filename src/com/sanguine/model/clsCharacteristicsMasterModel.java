package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblcharacteristics")
@IdClass(clsCharacteristicsMasterModel_ID.class)
public class clsCharacteristicsMasterModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsCharacteristicsMasterModel() {
	}

	public clsCharacteristicsMasterModel(clsCharacteristicsMasterModel_ID clsCharacteristicsMaster_ID) {
		strCharCode = clsCharacteristicsMaster_ID.getStrCharCode();
		strClientCode = clsCharacteristicsMaster_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCharCode", column = @Column(name = "strCharCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strCharCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCharCode;

	@Column(name = "strCharName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCharName;

	@Column(name = "strCharType", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCharType;

	@Column(name = "strCharDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strCharDesc;

	@Column(name = "strUserCreated", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "dtCreatedDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = strCharCode;
	}

	public String getStrCharName() {
		return strCharName;
	}

	public void setStrCharName(String strCharName) {
		this.strCharName = strCharName;
	}

	public String getStrCharType() {
		return strCharType;
	}

	public void setStrCharType(String strCharType) {
		this.strCharType = strCharType;
	}

	public String getStrCharDesc() {
		return strCharDesc;
	}

	public void setStrCharDesc(String strCharDesc) {
		this.strCharDesc = strCharDesc;
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

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
