package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblexciseposlinkup")
@IdClass(clsExcisePOSLinkUpModel_ID.class)
public class clsExcisePOSLinkUpModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExcisePOSLinkUpModel() {
	}

	public clsExcisePOSLinkUpModel(clsExcisePOSLinkUpModel_ID objModelID) {
		strPOSItemCode = objModelID.getStrPOSItemCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strPOSItemCode", column = @Column(name = "strPOSItemCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strPOSItemCode")
	private String strPOSItemCode;

	@Column(name = "strPOSItemName")
	private String strPOSItemName;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Column(name = "strBrandName")
	private String strBrandName;

	@Column(name = "intConversionFactor", columnDefinition = "INT(10) NOT NULL default '1'")
	private Integer intConversionFactor;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public String getStrPOSItemCode() {
		return strPOSItemCode;
	}

	public void setStrPOSItemCode(String strPOSItemCode) {
		this.strPOSItemCode = strPOSItemCode;
	}

	public String getStrPOSItemName() {
		return strPOSItemName;
	}

	public void setStrPOSItemName(String strPOSItemName) {
		this.strPOSItemName = strPOSItemName;
	}

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public Integer getIntConversionFactor() {
		return intConversionFactor;
	}

	public void setIntConversionFactor(Integer intConversionFactor) {
		this.intConversionFactor = intConversionFactor;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
