package com.sanguine.webbooks.apgl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.sanguine.webbooks.model.clsACGroupMasterModel_ID;

@Embeddable
@SuppressWarnings("serial")
public class clsAPGLBudgetModel_ID implements Serializable {

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "intId")
	private long intId;

	public clsAPGLBudgetModel_ID() {
	}

	public clsAPGLBudgetModel_ID(long intId, String strClientCode) {
		this.intId = intId;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// //HashCode and Equals Funtions
	// @Override
	// public boolean equals(Object obj) {
	// clsAPGLBudgetModel_ID objModelId = (clsAPGLBudgetModel_ID)obj;
	// if(this.strClientCode.equals(objModelId.getStrClientCode())&&
	// this.intId.equals(objModelId.getIntId())){
	// return true;
	// }
	// else{
	// return false;
	// }
	// }
	//
	// @Override
	// public int hashCode() {
	// return this.strClientCode.hashCode();
	// }
	public long getIntId() {
		return intId;
	}

	public void setIntId(long intId) {
		this.intId = intId;
	}

}
