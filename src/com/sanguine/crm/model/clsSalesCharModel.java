package com.sanguine.crm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

@Entity
@Table(name = "tblsaleschar")
@IdClass(clsSalesCharModel_ID.class)
public class clsSalesCharModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSalesCharModel() {
	}

	public clsSalesCharModel(clsSalesCharModel_ID objModelID) {
		strSOCode = objModelID.getStrSOCode();
		strProdCode = objModelID.getStrProdCode();
		strCharCode = objModelID.getStrCharCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSOCode", column = @Column(name = "strSOCode")), @AttributeOverride(name = "strProdCode", column = @Column(name = "strProdCode")), @AttributeOverride(name = "strCharCode", column = @Column(name = "strCharCode")) })
	// Variable Declaration
	@Column(name = "strSOCode")
	private String strSOCode;

	@Column(name = "strProdCode")
	private String strProdCode;

	@Column(name = "strCharCode")
	private String strCharCode;

	@Column(name = "strCharValue")
	private String strCharValue;

	@Column(name = "strAdvOrderNo")
	private String strAdvOrderNo;

	// Setter-Getter Methods
	public String getStrSOCode() {
		return strSOCode;
	}

	public void setStrSOCode(String strSOCode) {
		this.strSOCode = (String) setDefaultValue(strSOCode, "NA");
	}

	public String getStrProdCode() {
		return strProdCode;
	}

	public void setStrProdCode(String strProdCode) {
		this.strProdCode = (String) setDefaultValue(strProdCode, "NA");
	}

	public String getStrCharCode() {
		return strCharCode;
	}

	public void setStrCharCode(String strCharCode) {
		this.strCharCode = (String) setDefaultValue(strCharCode, "NA");
	}

	public String getStrCharValue() {
		return strCharValue;
	}

	public void setStrCharValue(String strCharValue) {
		this.strCharValue = (String) setDefaultValue(strCharValue, "NA");
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

	public String getStrAdvOrderNo() {
		return strAdvOrderNo;
	}

	public void setStrAdvOrderNo(String strAdvOrderNo) {
		this.strAdvOrderNo = (String) setDefaultValue(strAdvOrderNo, "NA");
	}

}
