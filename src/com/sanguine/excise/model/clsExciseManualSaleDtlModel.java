package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblmanualsalesDtl")
public class clsExciseManualSaleDtlModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsExciseManualSaleDtlModel() {
	}

	// Variable Declaration

	@Id
	@GeneratedValue
	@Column(name = "intId")
	private Long intId;

	@Column(name = "intSalesHd")
	private Long intSalesHd;

	@Column(name = "strBrandCode")
	private String strBrandCode;

	@Transient
	private Long intBrandSize;

	@Transient
	private Long intPegSize;

	@Transient
	private String strOpStk;

	@Transient
	private String strBrandName;

	@Column(name = "intBtl")
	private Long intBtl;

	@Column(name = "intPeg")
	private Long intPeg;

	@Column(name = "intML")
	private Long intML;

	@Column(name = "strBillGenFlag")
	private String strBillGenFlag;

	@Column(name = "strClientCode")
	private String strClientCode;

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public Long getIntSalesHd() {
		return intSalesHd;
	}

	public void setIntSalesHd(Long intSalesHd) {
		this.intSalesHd = intSalesHd;
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

	public Long getIntBtl() {
		return intBtl;
	}

	public void setIntBtl(Long intBtl) {
		this.intBtl = intBtl;
	}

	public Long getIntPeg() {
		return intPeg;
	}

	public void setIntPeg(Long intPeg) {
		this.intPeg = intPeg;
	}

	public Long getIntML() {
		return intML;
	}

	public void setIntML(Long intML) {
		this.intML = intML;
	}

	public String getStrBillGenFlag() {
		return strBillGenFlag;
	}

	public void setStrBillGenFlag(String strBillGenFlag) {
		this.strBillGenFlag = strBillGenFlag;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
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

}
