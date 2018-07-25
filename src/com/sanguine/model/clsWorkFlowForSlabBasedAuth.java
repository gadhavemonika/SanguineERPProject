package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblworkflowforslabbasedauth")
public class clsWorkFlowForSlabBasedAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intid;
	private String strCompanyCode;
	private String strPropertyCode;
	private String strFormName;
	private String strCriteria;
	private long intVal1;
	private long intVal2;
	private int intLevel;
	private String strUser1;
	private String strUser2;
	private String strUser3;
	private String strUser4;
	private String strUser5;
	private String strUserCreated;
	private String dtDateCreated;
	private String strUserModified;
	private String dtLastModified;
	private String strClientCode;

	public long getIntid() {
		return intid;
	}

	public void setIntid(long intid) {
		this.intid = intid;
	}

	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	public String getStrCriteria() {
		return strCriteria;
	}

	public void setStrCriteria(String strCriteria) {
		this.strCriteria = strCriteria;
	}

	public long getIntVal1() {
		return intVal1;
	}

	public void setIntVal1(long intVal1) {
		this.intVal1 = intVal1;
	}

	public long getIntVal2() {
		return intVal2;
	}

	public void setIntVal2(long intVal2) {
		this.intVal2 = intVal2;
	}

	public int getIntLevel() {
		return intLevel;
	}

	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
	}

	public String getStrUser1() {
		return strUser1;
	}

	public void setStrUser1(String strUser1) {
		this.strUser1 = strUser1;
	}

	public String getStrUser2() {
		return strUser2;
	}

	public void setStrUser2(String strUser2) {
		this.strUser2 = strUser2;
	}

	public String getStrUser3() {
		return strUser3;
	}

	public void setStrUser3(String strUser3) {
		this.strUser3 = strUser3;
	}

	public String getStrUser4() {
		return strUser4;
	}

	public void setStrUser4(String strUser4) {
		this.strUser4 = strUser4;
	}

	public String getStrUser5() {
		return strUser5;
	}

	public void setStrUser5(String strUser5) {
		this.strUser5 = strUser5;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDtDateCreated() {
		return dtDateCreated;
	}

	public void setDtDateCreated(String dtDateCreated) {
		this.dtDateCreated = dtDateCreated;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDtLastModified() {
		return dtLastModified;
	}

	public void setDtLastModified(String dtLastModified) {
		this.dtLastModified = dtLastModified;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
