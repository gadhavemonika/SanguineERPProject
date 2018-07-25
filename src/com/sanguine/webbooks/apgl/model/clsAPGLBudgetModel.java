package com.sanguine.webbooks.apgl.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sanguine.excise.model.clsExciseManualSaleHdModel_ID;
import com.sanguine.webbooks.model.clsAccountHolderMasterModel_ID;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;

@Entity
@Table(name = "tblbudget")
@IdClass(clsAPGLBudgetModel_ID.class)
public class clsAPGLBudgetModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsAPGLBudgetModel() {
	}

	public clsAPGLBudgetModel(clsAPGLBudgetModel_ID objModelID) {
		intId = objModelID.getIntId();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "intId", column = @Column(name = "intId")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private Long intId;

	@Column(name = "strAccCode")
	private String strAccCode;

	@Column(name = "strAccName")
	private String strAccName;

	@Column(name = "strMonth")
	private String strMonth;

	@Column(name = "strYear")
	private String strYear;

	@Column(name = "dblBudgetAmt")
	private double dblBudgetAmt;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Getter and Setter
	public String getStrAccCode() {
		return strAccCode;
	}

	public void setStrAccCode(String strAccCode) {
		this.strAccCode = strAccCode;
	}

	public String getStrAccName() {
		return strAccName;
	}

	public void setStrAccName(String strAccName) {
		this.strAccName = strAccName;
	}

	public String getStrMonth() {
		return strMonth;
	}

	public void setStrMonth(String strMonth) {
		this.strMonth = strMonth;
	}

	public String getStrYear() {
		return strYear;
	}

	public void setStrYear(String strYear) {
		this.strYear = strYear;
	}

	public double getDblBudgetAmt() {
		return dblBudgetAmt;
	}

	public void setDblBudgetAmt(double dblBudgetAmt) {
		this.dblBudgetAmt = dblBudgetAmt;
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
		this.intId = (long) setDefaultValue(intId, "");
		;
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

}
