package com.sanguine.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblprodprocess")
public class clsProdProcessModel {
	@Id
	@GeneratedValue
	@Column(name = "intId")
	private long intId;

	@Column(name = "strProdCode")
	private String strProdProcessCode;

	@Column(name = "strProcessCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strProcessCode;

	@Column(name = "intLevel", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	private int intLevel;

	@Column(name = "dblWeight", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	private double dblWeight;

	@Column(name = "dblCycleTime", columnDefinition = "DECIMAL(18,4) NOT NULL dafault '0.0000'")
	private double dblCycleTime;

	@Column(name = "strClientCode", columnDefinition = "VARCHAR(255) NOT NULL dafault ''")
	private String strClientCode;

	@Transient
	String strProcessName;

	public String getStrProcessName() {
		return strProcessName;
	}

	public void setStrProcessName(String strProcessName) {
		this.strProcessName = strProcessName;
	}

	public String getStrProdProcessCode() {
		return strProdProcessCode;
	}

	public void setStrProdProcessCode(String strProdProcessCode) {
		this.strProdProcessCode = strProdProcessCode;
	}

	public String getStrProcessCode() {
		return strProcessCode;
	}

	public void setStrProcessCode(String strProcessCode) {
		this.strProcessCode = strProcessCode;
	}

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
	}

	public double getDblWeight() {
		return dblWeight;
	}

	public void setDblWeight(double dblWeight) {
		this.dblWeight = dblWeight;
	}

	public double getDblCycleTime() {
		return dblCycleTime;
	}

	public void setDblCycleTime(double dblCycleTime) {
		this.dblCycleTime = dblCycleTime;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
}
