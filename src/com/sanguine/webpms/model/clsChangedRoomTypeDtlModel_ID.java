package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
public class clsChangedRoomTypeDtlModel_ID implements Serializable {

	// Variable Declaration
	@Column(name = "strDocNo")
	private String strDocNo;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	@Column(name = "strRoomType")
	private String strRoomType;

	public clsChangedRoomTypeDtlModel_ID() {
	}

	public clsChangedRoomTypeDtlModel_ID(String strDocNo,String strRoomType,String strClientCode) {
		this.strDocNo = strDocNo;
		this.strRoomType= strRoomType;
		this.strClientCode = strClientCode;
		
	}

	// Setter-Getter Methods
	
	public String getStrDocNo() {
		return strDocNo;
	}

	public void setStrDocNo(String strDocNo) {
		this.strDocNo = strDocNo;
	}
	
	
	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}
	
	

	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}

	// HashCode and Equals Funtions
	@Override
	public boolean equals(Object obj) {
		clsChangedRoomTypeDtlModel_ID objModelId = (clsChangedRoomTypeDtlModel_ID) obj;
		if (this.strDocNo.equals(objModelId.getStrDocNo())  && this.strRoomType.equals(objModelId.getStrRoomType()) && this.strClientCode.equals(objModelId.getStrClientCode())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.strDocNo.hashCode() +  this.strRoomType.hashCode() +this.strClientCode.hashCode();
	}



}