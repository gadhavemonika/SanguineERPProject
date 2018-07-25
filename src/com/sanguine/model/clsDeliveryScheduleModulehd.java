package com.sanguine.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.model.clsDeliveryScheduleModulehd_ID;

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

import org.hibernate.annotations.CollectionOfElements;

@Entity
@Table(name = "tbldeliveryschedulehd")
@IdClass(clsDeliveryScheduleModulehd_ID.class)
public class clsDeliveryScheduleModulehd implements Serializable {

	public clsDeliveryScheduleModulehd() {
	}

	public clsDeliveryScheduleModulehd(clsDeliveryScheduleModulehd_ID clsDeliveryScheduleModuleHD_ID) {
		strDSCode = clsDeliveryScheduleModuleHD_ID.getStrDSCode();
		strClientCode = clsDeliveryScheduleModuleHD_ID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tbldeliveryscheduledtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strDSCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strDSCode", column = @Column(name = "strDSCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsDeliveryScheduleModuledtl> listDeliveryModelDtl = new ArrayList<clsDeliveryScheduleModuledtl>();

	@Column(name = "strDSCode")
	private String strDSCode;

	@Column(name = "strPOCode")
	private String strPOCode;

	@Column(name = "strAgainst")
	private String strAgainst;

	@Column(name = "dteDSDate")
	private String dteDSDate;

	@Column(name = "dteScheduleDate")
	private String dteScheduleDate;

	@Column(name = "dblTotalAmount")
	private double dblTotalAmount;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strCloseDS")
	private String strCloseDS;

	public String getStrDSCode() {
		return strDSCode;
	}

	public void setStrDSCode(String strDSCode) {
		this.strDSCode = strDSCode;
	}

	public String getStrPOCode() {
		return strPOCode;
	}

	public void setStrPOCode(String strPOCode) {
		this.strPOCode = strPOCode;
	}

	public String getStrAgainst() {
		return strAgainst;
	}

	public void setStrAgainst(String strAgainst) {
		this.strAgainst = strAgainst;
	}

	public String getDteDSDate() {
		return dteDSDate;
	}

	public void setDteDSDate(String dteDSDate) {
		this.dteDSDate = dteDSDate;
	}

	public String getDteScheduleDate() {
		return dteScheduleDate;
	}

	public void setDteScheduleDate(String dteScheduleDate) {
		this.dteScheduleDate = dteScheduleDate;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserEdited() {
		return strUserEdited;
	}

	public void setStrUserEdited(String strUserEdited) {
		this.strUserEdited = strUserEdited;
	}

	public String getDteDateCreated() {
		return dteDateCreated;
	}

	public void setDteDateCreated(String dteDateCreated) {
		this.dteDateCreated = dteDateCreated;
	}

	public String getDteDateEdited() {
		return dteDateEdited;
	}

	public void setDteDateEdited(String dteDateEdited) {
		this.dteDateEdited = dteDateEdited;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public double getDblTotalAmount() {
		return dblTotalAmount;
	}

	public void setDblTotalAmount(double dblTotalAmount) {
		this.dblTotalAmount = dblTotalAmount;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public List<clsDeliveryScheduleModuledtl> getListDeliveryModelDtl() {
		return listDeliveryModelDtl;
	}

	public void setListDeliveryModelDtl(List<clsDeliveryScheduleModuledtl> listDeliveryModelDtl) {
		this.listDeliveryModelDtl = listDeliveryModelDtl;
	}

	public String getStrCloseDS() {
		return strCloseDS;
	}

	public void setStrCloseDS(String strCloseDS) {
		this.strCloseDS = strCloseDS;
	}

}
