package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.IdClass;

@Entity
@Table(name = "tbldeletedetails")
@IdClass(clsDeleteTransactionModel_ID.class)
public class clsDeleteTransactionModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsDeleteTransactionModel() {
	}

	public clsDeleteTransactionModel(clsDeleteTransactionModel_ID objModelID) {
		strTransCode = objModelID.getStrTransCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransCode", column = @Column(name = "strTransCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strFormName")
	private String strFormName;

	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "strUserCode")
	private String strUserCode;

	@Column(name = "dteDeleteDate")
	private String dteDeleteDate;

	@Column(name = "strReasonCode")
	private String strReasonCode;

	@Column(name = "strNarration")
	private String strNarration;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strDataPostFlag")
	private String strDataPostFlag;

	// Setter-Getter Methods
	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = (String) setDefaultValue(strFormName, "NA");
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = (String) setDefaultValue(strTransCode, "NA");
	}

	public String getStrUserCode() {
		return strUserCode;
	}

	public void setStrUserCode(String strUserCode) {
		this.strUserCode = (String) setDefaultValue(strUserCode, "NA");
	}

	public String getDteDeleteDate() {
		return dteDeleteDate;
	}

	public void setDteDeleteDate(String dteDeleteDate) {
		this.dteDeleteDate = dteDeleteDate;
	}

	public String getStrReasonCode() {
		return strReasonCode;
	}

	public void setStrReasonCode(String strReasonCode) {
		this.strReasonCode = (String) setDefaultValue(strReasonCode, "NA");
	}

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = (String) setDefaultValue(strNarration, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = (String) setDefaultValue(strDataPostFlag, "NA");
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
