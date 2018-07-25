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
@Table(name = "tblexcisestockpostingdtl")
public class clsExciseStkPostingDtlModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsExciseStkPostingDtlModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "intId")
	private Long intId;

	@Column(name = "strPSPCode")
	private String strPSPCode;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private String strBrandName;

	@Transient
	private Double dblBrandMRP;

	@Column(name = "intSysBtl")
	private Long intSysBtl;

	@Column(name = "intSysML")
	private Long intSysML;

	@Column(name = "intSysPeg")
	private Long intSysPeg;

	@Column(name = "intPhyBtl")
	private Long intPhyBtl;

	@Column(name = "intPhyML")
	private Long intPhyML;

	@Column(name = "intPhyPeg")
	private Long intPhyPeg;

	@Transient
	private Long intVarianceInML;

	@Transient
	private Long intBrandSize;

	@Transient
	private Long intPegSize;

	@Transient
	private String strOpStk;

	@Column(name = "strClientCode")
	private String strClientCode;

	// Setter-Getter Methods

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrPSPCode() {
		return strPSPCode;
	}

	public void setStrPSPCode(String strPSPCode) {
		this.strPSPCode = strPSPCode;
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

	public Double getDblBrandMRP() {
		return dblBrandMRP;
	}

	public void setDblBrandMRP(Double dblBrandMRP) {
		this.dblBrandMRP = dblBrandMRP;
	}

	public Long getIntSysBtl() {
		return intSysBtl;
	}

	public void setIntSysBtl(Long intSysBtl) {
		this.intSysBtl = intSysBtl;
	}

	public Long getIntSysML() {
		return intSysML;
	}

	public void setIntSysML(Long intSysML) {
		this.intSysML = intSysML;
	}

	public Long getIntSysPeg() {
		return intSysPeg;
	}

	public void setIntSysPeg(Long intSysPeg) {
		this.intSysPeg = intSysPeg;
	}

	public Long getIntPhyBtl() {
		return intPhyBtl;
	}

	public void setIntPhyBtl(Long intPhyBtl) {
		this.intPhyBtl = intPhyBtl;
	}

	public Long getIntPhyML() {
		return intPhyML;
	}

	public void setIntPhyML(Long intPhyML) {
		this.intPhyML = intPhyML;
	}

	public Long getIntPhyPeg() {
		return intPhyPeg;
	}

	public void setIntPhyPeg(Long intPhyPeg) {
		this.intPhyPeg = intPhyPeg;
	}

	public Long getIntVarianceInML() {
		return intVarianceInML;
	}

	public void setIntVarianceInML(Long intVarianceInML) {
		this.intVarianceInML = intVarianceInML;
	}

	public Long getIntBrandSize() {
		return intBrandSize;
	}

	public void setIntBrandSize(Long intBrandSize) {
		this.intBrandSize = intBrandSize;
	}

	public Long getIntPegSize() {
		return intPegSize;
	}

	public void setIntPegSize(Long intPegSize) {
		this.intPegSize = intPegSize;
	}

	public String getStrOpStk() {
		return strOpStk;
	}

	public void setStrOpStk(String strOpStk) {
		this.strOpStk = strOpStk;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
