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

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.crm.model.clsInvoiceHdModel_ID;
import com.sanguine.crm.model.clsInvoiceModelDtl;

@Entity
@Table(name = "tblbudgetmasterhd")
@IdClass(clsBudgetMasterHdModel_ID.class)
public class clsBudgetMasterHdModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsBudgetMasterHdModel() {
	}

	public clsBudgetMasterHdModel(clsBudgetMasterHdModel_ID objModelID) {
		strBudgetCode = objModelID.getStrBudgetCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblbudgetmasterdtl", joinColumns = { @JoinColumn(name = "strBudgetCode"), @JoinColumn(name = "strClientCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBudgetCode", column = @Column(name = "strBudgetCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsBudgetMasterDtlModel> listBudgetDtlModel = new ArrayList<clsBudgetMasterDtlModel>();

	@Column(name = "strBudgetCode")
	private String strBudgetCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strFinYear")
	private String strFinYear;

	@Column(name = "strStartMonth")
	private String strStartMonth;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated")
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "intBId")
	private long intBId;

	public String getStrBudgetCode() {
		return strBudgetCode;
	}

	public void setStrBudgetCode(String strBudgetCode) {
		this.strBudgetCode = strBudgetCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrFinYear() {
		return strFinYear;
	}

	public void setStrFinYear(String strFinYear) {
		this.strFinYear = strFinYear;
	}

	public String getStrStartMonth() {
		return strStartMonth;
	}

	public void setStrStartMonth(String strStartMonth) {
		this.strStartMonth = strStartMonth;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
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

	public long getIntBId() {
		return intBId;
	}

	public void setIntBId(long intBId) {
		this.intBId = intBId;
	}

	public List<clsBudgetMasterDtlModel> getListBudgetDtlModel() {
		return listBudgetDtlModel;
	}

	public void setListBudgetDtlModel(List<clsBudgetMasterDtlModel> listBudgetDtlModel) {
		this.listBudgetDtlModel = listBudgetDtlModel;
	}

}
