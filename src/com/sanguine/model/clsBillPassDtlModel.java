package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblbillpassdtl")
public class clsBillPassDtlModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId")
	private long intId;

	@Column(name = "strBillPassNo", columnDefinition = "VARCHAR(255) NOT NULL default '' ")
	private String strBillPassNo;

	@Column(name = "strChallanNo", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strChallanNo;

	@Column(name = "strGRNCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strGRNCode;

	@Column(name = "dtGRNDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtGRNDate;

	@Column(name = "dblGRNAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblGRNAmt;

	@Column(name = "dblAdjustAmt", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblAdjustAmt;

	@Column(name = "dblSurcharge", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblSurcharge;

	@Column(name = "dblFreight", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblFreight;

	@Column(name = "dblTotal", columnDefinition = "Decimal(18,4) NOT NULL default '0.00'")
	private double dblTotal;

	@Column(name = "strClientCode")
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrChallanNo() {
		return strChallanNo;
	}

	public void setStrChallanNo(String strChallanNo) {
		this.strChallanNo = (String) setDefaultValue(strChallanNo, "");
	}

	public String getStrGRNCode() {
		return strGRNCode;
	}

	public void setStrGRNCode(String strGRNCode) {
		this.strGRNCode = (String) setDefaultValue(strGRNCode, "");
	}

	public String getDtGRNDate() {
		return dtGRNDate;
	}

	public void setDtGRNDate(String dtGRNDate) {
		this.dtGRNDate = (String) setDefaultValue(dtGRNDate, "");
	}

	public double getDblGRNAmt() {
		return dblGRNAmt;
	}

	public void setDblGRNAmt(double dblGRNAmt) {
		this.dblGRNAmt = (double) setDefaultValue(dblGRNAmt, 0.00);
	}

	public double getDblAdjustAmt() {
		return dblAdjustAmt;
	}

	public void setDblAdjustAmt(double dblAdjustAmt) {
		this.dblAdjustAmt = (double) setDefaultValue(dblAdjustAmt, 0.00);
	}

	public double getDblSurcharge() {
		return dblSurcharge;
	}

	public void setDblSurcharge(double dblSurcharge) {
		this.dblSurcharge = (double) setDefaultValue(dblSurcharge, 0.00);
	}

	public double getDblFreight() {
		return dblFreight;
	}

	public void setDblFreight(double dblFreight) {
		this.dblFreight = (double) setDefaultValue(dblFreight, 0.00);
	}

	public double getDblTotal() {
		return dblTotal;
	}

	public void setDblTotal(double dblTotal) {
		this.dblTotal = (double) setDefaultValue(dblTotal, 0.00);
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrBillPassNo() {
		return strBillPassNo;
	}

	public void setStrBillPassNo(String strBillPassNo) {
		this.strBillPassNo = (String) setDefaultValue(strBillPassNo, "");
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
}
