package com.sanguine.webpms.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

@Entity
@Table(name = "tbldayendprocess")
@IdClass(clsDayEndModel_ID.class)
public class clsDayEndHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsDayEndHdModel() {
	}

	public clsDayEndHdModel(clsDayEndModel_ID objModelID) {
		strPropertyCode = objModelID.getStrPropertyCode();
		dtePMSDate = objModelID.getDtePMSDate();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")), @AttributeOverride(name = "dtePMSDate", column = @Column(name = "dtePMSDate")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "dtePMSDate")
	private String dtePMSDate;

	@Column(name = "strStartDay")
	private String strStartDay;

	@Column(name = "strDayEnd")
	private String strDayEnd;

	@Column(name = "dblDayEndAmt")
	private double dblDayEndAmt;

	@Column(name = "strUserCode")
	private String strUserCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods
	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	public String getDtePMSDate() {
		return dtePMSDate;
	}

	public void setDtePMSDate(String dtePMSDate) {
		this.dtePMSDate = dtePMSDate;
	}

	public String getStrDayEnd() {
		return strDayEnd;
	}

	public void setStrDayEnd(String strDayEnd) {
		this.strDayEnd = (String) setDefaultValue(strDayEnd, "NA");
	}

	public double getDblDayEndAmt() {
		return dblDayEndAmt;
	}

	public void setDblDayEndAmt(double dblDayEndAmt) {
		this.dblDayEndAmt = (Double) setDefaultValue(dblDayEndAmt, "0.0000");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrStartDay() {
		return strStartDay;
	}

	public void setStrStartDay(String strStartDay) {
		this.strStartDay = strStartDay;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
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
