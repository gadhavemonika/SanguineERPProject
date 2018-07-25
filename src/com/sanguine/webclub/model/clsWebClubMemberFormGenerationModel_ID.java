package com.sanguine.webclub.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsWebClubMemberFormGenerationModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "intFormNo")
	private String strFormNo;

	@Column(name = "strClientCode")
	private String strClientCode;

	public clsWebClubMemberFormGenerationModel_ID() {
	}

	public clsWebClubMemberFormGenerationModel_ID(String strFormNo, String strClientCode) {
		this.strFormNo = strFormNo;
		this.strClientCode = strClientCode;
	}

	// Setter-Getter Methods

	public String getStrClientCode() {
		return strClientCode;
	}

	public String getStrFormNo() {
		return strFormNo;
	}

	public void setStrFormNo(String intFormNo) {
		this.strFormNo = intFormNo;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	// HashCode and Equals Funtions
	// @Override
	// public boolean equals(Object obj) {
	// clsWebClubMemberFormGenerationModel_ID objModelId =
	// (clsWebClubMemberFormGenerationModel_ID)obj;
	// if(this.intFormNo.equals(objModelId.getIntFormNo())&&
	// this.strClientCode.equals(objModelId.getStrClientCode())){
	// return true;
	// }
	// else{
	// return false;
	// }
	// }
	//
	// @Override
	// public int hashCode() {
	// return this.intFormNo.hashCode()+this.strClientCode.hashCode();
	// }

}
