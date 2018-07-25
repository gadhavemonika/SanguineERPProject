package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblprocesssetup")
public class clsProcessSetupModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long intId;
	private String strForm;
	private String strFormDesc;
	private String strProcess;
	private String strPropertyCode;
	private String strClientCode;

	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

	public String getStrForm() {
		return strForm;
	}

	public void setStrForm(String strForm) {
		this.strForm = strForm;
	}

	public String getStrProcess() {
		return strProcess;
	}

	public void setStrProcess(String strProcess) {
		this.strProcess = strProcess;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
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

	public String getStrFormDesc() {
		return strFormDesc;
	}

	public void setStrFormDesc(String strFormDesc) {
		this.strFormDesc = strFormDesc;
	}

}
