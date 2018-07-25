package com.sanguine.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblreasonmaster")
@IdClass(clsReasonMaster_ID.class)
public class clsReasonMaster {
	public clsReasonMaster() {
	}

	public clsReasonMaster(clsReasonMaster_ID clsReasonMaster_ID) {
		strReasonCode = clsReasonMaster_ID.getStrReasonCode();
		strClientCode = clsReasonMaster_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReasonCode", column = @Column(name = "strReasonCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strReasonCode")
	private String strReasonCode;

	@Column(name = "strReasonName")
	private String strReasonName;

	@Column(name = "strReasonDesc")
	private String strReasonDesc;

	@Column(name = "intid", updatable = false)
	private long intid;

	@Column(name = "strStockAdj")
	private String strStockAdj;

	@Column(name = "strNonConf")
	private String strNonConf;

	@Column(name = "strCorract")
	private String strCorract;

	@Column(name = "strPrevAct")
	private String strPrevAct;

	@Column(name = "strStocktra")
	private String strStocktra;

	@Column(name = "strFollowUps")
	private String strFollowUps;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dtCreatedDate", updatable = false)
	private String dtCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strResAlloc")
	private String strResAlloc;

	@Column(name = "strDelcha")
	private String strDelcha;

	@Column(name = "strLeadMaster")
	private String strLeadMaster;

	@Column(name = "strClientCode", updatable = false)
	private String strClientCode;

	@Column(name = "dtExpiryDate")
	private String dtExpiryDate;

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

	public String getStrReasonName() {
		return strReasonName;
	}

	public void setStrReasonName(String strReasonName) {
		this.strReasonName = strReasonName;
	}

	public String getStrReasonDesc() {
		return strReasonDesc;
	}

	public void setStrReasonDesc(String strReasonDesc) {
		this.strReasonDesc = strReasonDesc;
	}

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public String getStrStockAdj() {
		return strStockAdj;
	}

	public void setStrStockAdj(String strStockAdj) {
		this.strStockAdj = strStockAdj;
	}

	public String getStrNonConf() {
		return strNonConf;
	}

	public void setStrNonConf(String strNonConf) {
		this.strNonConf = strNonConf;
	}

	public String getStrCorract() {
		return strCorract;
	}

	public void setStrCorract(String strCorract) {
		this.strCorract = strCorract;
	}

	public String getStrPrevAct() {
		return strPrevAct;
	}

	public void setStrPrevAct(String strPrevAct) {
		this.strPrevAct = strPrevAct;
	}

	public String getStrStocktra() {
		return strStocktra;
	}

	public void setStrStocktra(String strStocktra) {
		this.strStocktra = strStocktra;
	}

	public String getStrFollowUps() {
		return strFollowUps;
	}

	public void setStrFollowUps(String strFollowUps) {
		this.strFollowUps = strFollowUps;
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

	public String getStrResAlloc() {
		return strResAlloc;
	}

	public void setStrResAlloc(String strResAlloc) {
		this.strResAlloc = strResAlloc;
	}

	public String getStrDelcha() {
		return strDelcha;
	}

	public void setStrDelcha(String strDelcha) {
		this.strDelcha = strDelcha;
	}

	public String getStrLeadMaster() {
		return strLeadMaster;
	}

	public void setStrLeadMaster(String strLeadMaster) {
		this.strLeadMaster = strLeadMaster;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getDtExpiryDate() {
		return dtExpiryDate;
	}

	public void setDtExpiryDate(String dtExpiryDate) {
		this.dtExpiryDate = dtExpiryDate;
	}

}
