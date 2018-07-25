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

import com.sanguine.crm.model.clsInvoiceModelDtl;

@Entity
@Table(name = "tbllocationmaster")
@IdClass(clsLocationMasterModel_ID.class)
public class clsLocationMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsLocationMasterModel() {
	}

	public clsLocationMasterModel(clsLocationMasterModel_ID clsLocationMasterModel_ID) {
		strLocCode = clsLocationMasterModel_ID.getStrLocCode();
		strClientCode = clsLocationMasterModel_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// @CollectionOfElements(fetch=FetchType.LAZY)
	// @JoinTable(name="tbluserlocdtl" ,
	// joinColumns={@JoinColumn(name="strClientCode"),@JoinColumn(name="strLocCode")})
	// @Id
	// @AttributeOverrides({
	// @AttributeOverride(name="strLocCode",column=@Column(name="strLocCode")),
	// @AttributeOverride(name="strClientCode",column=@Column(name="strClientCode"))
	// })
	// private List<clsUserLocationDtl> listLocUserDtlModel= new
	// ArrayList<clsUserLocationDtl>();
	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strLocName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocName;

	@Column(name = "strLocDesc", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strLocDesc;

	@Column(name = "strUserCreated", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCreated;

	@Column(name = "strUserModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserModified;

	@Column(name = "dtCreatedDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtCreatedDate;

	@Column(name = "dtLastModified", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtLastModified;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "intid", nullable = false, updatable = false)
	private long intId;

	@Column(name = "strAvlForSale")
	private String strAvlForSale;

	@Column(name = "strActive")
	private String strActive;

	@Column(name = "strPickable")
	private String strPickable;

	@Column(name = "strReceivable")
	private String strReceivable;

	@Column(name = "strExciseNo")
	private String strExciseNo;

	@Column(name = "strType")
	private String strType;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strMonthEnd")
	private String strMonthEnd;

	@Column(name = "strExternalCode")
	private String strExternalCode;

	private String strLocPropertyCode;

	@Transient
	private String strAcCode;

	@Transient
	private String strAcName;

	// @Column(name = "strDayEnd")
	// private String strDayEnd;

	@Column(name = "strUnderLocCode")
	private String strUnderLocCode;

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

	public String getStrLocDesc() {
		return strLocDesc;
	}

	public void setStrLocDesc(String strLocDesc) {
		this.strLocDesc = strLocDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtCreatedDate() {
		return dtCreatedDate;
	}

	public void setDtCreatedDate(String dtCreatedDate) {
		this.dtCreatedDate = dtCreatedDate;
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

	public long getIntid() {
		return intId;
	}

	public void setIntid(long intId) {
		this.intId = intId;
	}

	public String getStrAvlForSale() {
		return strAvlForSale;
	}

	public void setStrAvlForSale(String strAvlForSale) {
		this.strAvlForSale = strAvlForSale;
	}

	public String getStrActive() {
		return strActive;
	}

	public void setStrActive(String strActive) {
		this.strActive = strActive;
	}

	public String getStrPickable() {
		return strPickable;
	}

	public void setStrPickable(String strPickable) {
		this.strPickable = strPickable;
	}

	public String getStrReceivable() {
		return strReceivable;
	}

	public void setStrReceivable(String strReceivable) {
		this.strReceivable = strReceivable;
	}

	public String getStrExciseNo() {
		return strExciseNo;
	}

	public void setStrExciseNo(String strExciseNo) {
		this.strExciseNo = strExciseNo;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrMonthEnd() {
		return strMonthEnd;
	}

	public void setStrMonthEnd(String strMonthEnd) {
		this.strMonthEnd = strMonthEnd;
	}

	public String getStrExternalCode() {
		return strExternalCode;
	}

	public void setStrExternalCode(String strExternalCode) {
		this.strExternalCode = strExternalCode;
	}

	public String getStrLocPropertyCode() {
		return strLocPropertyCode;
	}

	public void setStrLocPropertyCode(String strLocPropertyCode) {
		this.strLocPropertyCode = strLocPropertyCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStrAcCode() {
		return strAcCode;
	}

	public void setStrAcCode(String strAcCode) {
		this.strAcCode = strAcCode;
	}

	public String getStrAcName() {
		return strAcName;
	}

	public void setStrAcName(String strAcName) {
		this.strAcName = strAcName;
	}

	public String getStrUnderLocCode() {
		return strUnderLocCode;
	}

	public void setStrUnderLocCode(String strUnderLocCode) {
		this.strUnderLocCode = strUnderLocCode;
	}

	// public String getStrDayEnd() {
	// return strDayEnd;
	// }
	//
	// public void setStrDayEnd(String strDayEnd) {
	// this.strDayEnd = strDayEnd;
	// }

	// public List<clsUserLocationDtl> getListLocUserDtlModel() {
	// return listLocUserDtlModel;
	// }
	//
	// public void setListLocUserDtlModel(List<clsUserLocationDtl>
	// listLocUserDtlModel) {
	// this.listLocUserDtlModel = listLocUserDtlModel;
	// }

}
