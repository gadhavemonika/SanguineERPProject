package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblopeningstock")
public class clsOpeningStockModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsOpeningStockModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Column(name = "strLicenceCode")
	private String strLicenceCode;

	@Transient
	private String strBrandName;

	@Column(name = "intOpBtls")
	private Long intOpBtls;

	@Column(name = "intOpPeg")
	private Long intOpPeg;

	@Column(name = "intOpML")
	private Long intOpML;

	/*
	 * @Column(name="intPegSize") private Long intPegSize;
	 * 
	 * @Column(name="dblStrength") private Double dblStrength;
	 * 
	 * @Column(name="dblMRP") private Double dblMRP;
	 * 
	 * @Column(name="dblPegPrice") private Double dblPegPrice;
	 */

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

	// Setter-Getter Methods

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
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

	public String getStrLicenceCode() {
		return strLicenceCode;
	}

	public void setStrLicenceCode(String strLicenceCode) {
		this.strLicenceCode = strLicenceCode;
	}

}