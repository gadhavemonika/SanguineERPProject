package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbldeletedetails")
@Entity
public class clsDeleteTransModel {
	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;

	@Column(name = "strFormName", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strFormName;

	@Column(name = "strTransCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strTransCode;

	@Column(name = "strUserCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strUserCode;

	@Column(name = "dtDeleteDate", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String dtDeleteDate;

	@Column(name = "strReasonCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strReasonCode;

	@Column(name = "strNarration", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strNarration;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strClientCode;

	@Column(name = "strDataPostFlag", columnDefinition = "VARCHAR(255) NOT NULL default ''")
	private String strDataPostFlag;

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getDtDeleteDate() {
		return dtDeleteDate;
	}

	public void setDtDeleteDate(String dtDeleteDate) {
		this.dtDeleteDate = dtDeleteDate;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = strReasonCode;
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = strDataPostFlag;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
