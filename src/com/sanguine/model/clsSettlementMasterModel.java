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
@Table(name = "tblsettlementmaster")
@IdClass(clsSettlementMasterModel_ID.class)
public class clsSettlementMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSettlementMasterModel() {
	}

	public clsSettlementMasterModel(clsSettlementMasterModel_ID objModelID) {
		strSettlementCode = objModelID.getStrSettlementCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSettlementCode", column = @Column(name = "strSettlementCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	@Column(name = "strSettlementDesc")
	private String strSettlementDesc;

	@Column(name = "strSettlementType")
	private String strSettlementType;

	@Column(name = "strApplicable")
	private String strApplicable;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "dtCreatedDate")
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "intId")
	private long intId;

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}

	public String getStrSettlementDesc() {
		return strSettlementDesc;
	}

	public void setStrSettlementDesc(String strSettlementDesc) {
		this.strSettlementDesc = strSettlementDesc;
	}

	public String getStrSettlementType() {
		return strSettlementType;
	}

	public void setStrSettlementType(String strSettlementType) {
		this.strSettlementType = strSettlementType;
	}

	public String getStrApplicable() {
		return strApplicable;
	}

	public void setStrApplicable(String strApplicable) {
		this.strApplicable = strApplicable;
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
}
