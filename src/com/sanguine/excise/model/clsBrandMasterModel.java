package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblbrandmaster")
@IdClass(clsBrandMasterModel_ID.class)
public class clsBrandMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsBrandMasterModel() {
	}

	public clsBrandMasterModel(clsBrandMasterModel_ID objModelID) {
		strBrandCode = objModelID.getStrBrandCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strBrandCode", column = @Column(name = "strBrandCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Column(name = "intId")
	private long intId;

	@Column(name = "strBrandName")
	private String strBrandName;

	@Column(name = "strShortName")
	private String strShortName;

	@Column(name = "strSubCategoryCode")
	private String strSubCategoryCode;

	@Transient
	private String strSubCategoryName;

	@Transient
	private String strCategoryName;

	@Transient
	private Double dblRate;

	@Column(name = "strSizeCode")
	private String strSizeCode;

	@Column(name = "intPegSize")
	private Long intPegSize;

	@Column(name = "dblStrength")
	private Double dblStrength;

	@Transient
	private String strSizeName;

	@Transient
	private Long intSizeQty;

	@Transient
	private Long intOpBtls;

	@Transient
	private Long intOpPeg;

	@Transient
	private Long intOpML;

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

	@Transient
	private String strAvailableStk;

	// Setter-Getter Methods

	public String getStrBrandCode() {
		return strBrandCode;
	}

	public void setStrBrandCode(String strBrandCode) {
		this.strBrandCode = strBrandCode;
	}

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrShortName() {
		return strShortName;
	}

	public void setStrShortName(String strShortName) {
		this.strShortName = strShortName;
	}

	public String getStrSubCategoryCode() {
		return strSubCategoryCode;
	}

	public void setStrSubCategoryCode(String strSubCategoryCode) {
		this.strSubCategoryCode = strSubCategoryCode;
	}

	public String getStrSizeCode() {
		return strSizeCode;
	}

	public void setStrSizeCode(String strSizeCode) {
		this.strSizeCode = strSizeCode;
	}

	public Long getIntSizeQty() {
		return intSizeQty;
	}

	public void setIntSizeQty(Long intSizeQty) {
		this.intSizeQty = intSizeQty;
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

	public String getStrSubCategoryName() {
		return strSubCategoryName;
	}

	public void setStrSubCategoryName(String strSubCategoryName) {
		this.strSubCategoryName = strSubCategoryName;
	}

	public String getStrCategoryName() {
		return strCategoryName;
	}

	public void setStrCategoryName(String strCategoryName) {
		this.strCategoryName = strCategoryName;
	}

	public String getStrSizeName() {
		return strSizeName;
	}

	public void setStrSizeName(String strSizeName) {
		this.strSizeName = strSizeName;
	}

	public String getStrAvailableStk() {
		return strAvailableStk;
	}

	public void setStrAvailableStk(String strAvailableStk) {
		this.strAvailableStk = strAvailableStk;
	}

	public Long getIntPegSize() {
		return intPegSize;
	}

	public void setIntPegSize(Long intPegSize) {
		this.intPegSize = intPegSize;
	}

	public Long getIntOpBtls() {
		return intOpBtls;
	}

	public void setIntOpBtls(Long intOpBtls) {
		this.intOpBtls = intOpBtls;
	}

	public Long getIntOpPeg() {
		return intOpPeg;
	}

	public void setIntOpPeg(Long intOpPeg) {
		this.intOpPeg = intOpPeg;
	}

	public Long getIntOpML() {
		return intOpML;
	}

	public void setIntOpML(Long intOpML) {
		this.intOpML = intOpML;
	}

	public Double getDblStrength() {
		return dblStrength;
	}

	public void setDblStrength(Double dblStrength) {
		this.dblStrength = dblStrength;
	}

	public Double getDblRate() {
		return dblRate;
	}

	public void setDblRate(Double dblRate) {
		this.dblRate = dblRate;
	}

}
