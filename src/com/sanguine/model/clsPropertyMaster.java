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
@Table(name = "tblpropertymaster")
@IdClass(clsPropertyMaster_ID.class)
public class clsPropertyMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPropertyMaster() {
	}

	public clsPropertyMaster(clsPropertyMaster_ID clsPropertyMaster_ID) {
		strPropertyCode = clsPropertyMaster_ID.getPropertyCode();
		strClientCode = clsPropertyMaster_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strPropertyName")
	private String propertyName;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "intId", updatable = false)
	private long intId;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getPropertyCode() {
		return strPropertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.strPropertyCode = propertyCode;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
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

	public String getClientCode() {
		return strClientCode;
	}

	public void setClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
