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
@Table(name = "tblmanufacturemaster")
@IdClass(clsManufactureMasterModel_ID.class)
public class clsManufactureMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsManufactureMasterModel() {
	}

	public clsManufactureMasterModel(clsManufactureMasterModel_ID objModelID) {
		strManufacturerCode = objModelID.getStrManufacturerCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strManufacturerCode", column = @Column(name = "strManufacturerCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strManufacturerCode")
	private String strManufacturerCode;

	@Column(name = "strManufacturerName")
	private String strManufacturerName;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "intGId")
	private long intGId;

	public String getStrManufacturerCode() {
		return strManufacturerCode;
	}

	public void setStrManufacturerCode(String strManufacturerCode) {
		this.strManufacturerCode = strManufacturerCode;
	}

	public String getStrManufacturerName() {
		return strManufacturerName;
	}

	public void setStrManufacturerName(String strManufacturerName) {
		this.strManufacturerName = strManufacturerName;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public long getIntGId() {
		return intGId;
	}

	public void setIntGId(long intGId) {
		this.intGId = intGId;
	}

}
