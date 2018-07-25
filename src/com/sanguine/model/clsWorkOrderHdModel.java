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
@Table(name = "tblworkorderhd")
@IdClass(clsWorkOrderHdModel_ID.class)
public class clsWorkOrderHdModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public clsWorkOrderHdModel() {
	}

	public clsWorkOrderHdModel(clsWorkOrderHdModel_ID clsWorkOrderHdModel_ID) {
		strWOCode = clsWorkOrderHdModel_ID.getStrWOCode();
		strClientCode = clsWorkOrderHdModel_ID.getStrClientCode();
	}

	@Column(name = "intid", nullable = false, updatable = false)
	private long intid;
	// @Id
	// @AttributeOverrides({
	// @AttributeOverride(name = "strWOCode",column =
	// @Column(name="strWOCode")),
	// @AttributeOverride(name = "strClientCode",column =
	// @Column(name="strClientCode"))
	// })

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblworkorderdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strWOCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strWOCode", column = @Column(name = "strWOCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsWorkOrderDtlModel> listWorkOrderDtlModel = new ArrayList<clsWorkOrderDtlModel>();

	@Column(name = "strWOCode")
	private String strWOCode;

	@Column(name = "dtWODate")
	private String dtWODate;

	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "dblQty")
	private double dblQty;

	@Column(name = "strParentWOCode")
	private String strParentWOCode;

	@Column(name = "strStatus")
	private String strStatus;

	@Column(name = "strUserCreated", nullable = false, updatable = false)
	private String strUserCreated;

	@Column(name = "dtDateCreated", nullable = false, updatable = false)
	private String dtDateCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dtLastModified")
	private String dtLastModified;

	@Column(name = "strAuthorise")
	private String strAuthorise;

	@Column(name = "strJOCode")
	private String strJOCode;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "strClientCode")
	private String strClientCode;

	// private String strPartNo;
	@Transient
	private String strProdName;

	@Column(name = "strFromLocCode")
	private String strFromLocCode;

	@Column(name = "strToLocCode")
	private String strToLocCode;

	public long getIntId() {
		return intid;
	}

	public void setIntId(long intid) {
		this.intid = intid;
	}

	public String getStrWOCode() {
		return strWOCode;
	}

	public void setStrWOCode(String strWOCode) {
		this.strWOCode = strWOCode;
	}

	// @Transient
	// public String getStrPartNo() {
	// return strPartNo;
	// }
	// public void setStrPartNo(String strPartNo) {
	// this.strPartNo = strPartNo;
	// }

	public String getStrProdName() {
		return strProdName;
	}

	public void setStrProdName(String strProdName) {
		this.strProdName = strProdName;
	}

	public String getDtWODate() {
		return dtWODate;
	}

	public void setDtWODate(String dtWODate) {
		this.dtWODate = dtWODate;
	}

	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = strSOCode;
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = strProdCode;
	}

	public double getDblQty() {
		return dblQty;
	}

	public void setDblQty(double dblQty) {
		this.dblQty = dblQty;
	}

	public String getStrParentWOCode() {
		return strParentWOCode;
	}

	public void setStrParentWOCode(String strParentWOCode) {
		this.strParentWOCode = strParentWOCode;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
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

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrJOCode() {
		return strJOCode;
	}

	public void setStrJOCode(String strJOCode) {
		this.strJOCode = strJOCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
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

	}

	public List<clsWorkOrderDtlModel> getListWorkOrderDtlModel() {
		return listWorkOrderDtlModel;
	}

	public void setListWorkOrderDtlModel(List<clsWorkOrderDtlModel> listWorkOrderDtlModel) {
		this.listWorkOrderDtlModel = listWorkOrderDtlModel;
	}

	public String getStrFromLocCode() {
		return strFromLocCode;
	}

	public void setStrFromLocCode(String strFromLocCode) {
		this.strFromLocCode = strFromLocCode;
	}

	public String getStrToLocCode() {
		return strToLocCode;
	}

	public void setStrToLocCode(String strToLocCode) {
		this.strToLocCode = strToLocCode;
	}
}
