package com.sanguine.webbooks.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblcurrentaccountbal")
@IdClass(clsCurrentAccountBalMaodel_ID.class)
public class clsCurrentAccountBalMaodel {

	public clsCurrentAccountBalMaodel() {
	}

	public clsCurrentAccountBalMaodel(clsCurrentAccountBalMaodel_ID objModelID) {
		strAccountCode = objModelID.getStrAccountCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strAccountCode", column = @Column(name = "strAccountCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strAccountCode")
	private String strAccountCode;

	@Column(name = "strAccountName")
	private String strAccountName;

	@Column(name = "strDrCrCode")
	private String strDrCrCode;

	@Column(name = "dteBalDate")
	private String dteBalDate;

	@Column(name = "dblDrAmt")
	private double dblDrAmt;

	@Column(name = "dblCrAmt")
	private double dblCrAmt;

	@Column(name = "dblBalAmt")
	private double dblBalAmt;

	@Column(name = "strTransecType")
	private String strTransecType;

	@Column(name = "strUserCode")
	private String strUserCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	public String getStrAccountCode() {
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode) {
		this.strAccountCode = strAccountCode;
	}

	public String getStrDrCrCode() {
		return strDrCrCode;
	}

	public void setStrDrCrCode(String strDrCrCode) {
		this.strDrCrCode = strDrCrCode;
	}

	public String getDteBalDate() {
		return dteBalDate;
	}

	public void setDteBalDate(String dteBalDate) {
		this.dteBalDate = dteBalDate;
	}

	public double getDblDrAmt() {
		return dblDrAmt;
	}

	public void setDblDrAmt(double dblDrAmt) {
		this.dblDrAmt = dblDrAmt;
	}

	public double getDblCrAmt() {
		return dblCrAmt;
	}

	public void setDblCrAmt(double dblCrAmt) {
		this.dblCrAmt = dblCrAmt;
	}

	public double getDblBalAmt() {
		return dblBalAmt;
	}

	public void setDblBalAmt(double dblBalAmt) {
		this.dblBalAmt = dblBalAmt;
	}

	public String getStrTransecType() {
		return strTransecType;
	}

	public void setStrTransecType(String strTransecType) {
		this.strTransecType = strTransecType;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrAccountName() {
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName) {
		this.strAccountName = strAccountName;
	}

}
