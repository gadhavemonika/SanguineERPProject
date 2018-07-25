package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblfloormaster")
@IdClass(clsFloorMasterModel_ID.class)
public class clsFloorMasterModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public clsFloorMasterModel(){
		
	}
	
	public clsFloorMasterModel(clsFloorMasterModel_ID objModelID){
		strFloorCode = objModelID.getStrFloorCode();
		strClientCode = objModelID.getStrClientCode();
	}
	
	@Id
	@AttributeOverrides({
		@AttributeOverride(name = "strFloorCode", column = @Column(name = "strFloorCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode"))
	})
	

@Column(name = "strFloorCode")
private String strFloorCode;

@Column(name = "strFloorName")
private String strFloorName;

@Column(name = "dblFloorAmt")
private double dblFloorAmt;

@Column(name = "strUserCreated", updatable = false)
private String strUserCreated;

@Column(name = "strUserEdited")
private String strUserEdited;

@Column(name = "dteDateCreated", updatable = false)
private String dteDateCreated;

@Column(name = "dteDateEdited")
private String dteDateEdited;

@Column(name = "strClientCode")
private String strClientCode;

public String getStrFloorCode() {
	return strFloorCode;
}

public void setStrFloorCode(String strFloorCode) {
	this.strFloorCode = strFloorCode;
}

public String getStrFloorName() {
	return strFloorName;
}

public void setStrFloorName(String strFloorName) {
	this.strFloorName = strFloorName;
}

public double getDblFloorAmt() {
	return dblFloorAmt;
}

public void setDblFloorAmt(double dblFloorAmt) {
	this.dblFloorAmt = dblFloorAmt;
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



}
