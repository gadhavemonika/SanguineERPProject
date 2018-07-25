package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblsalesperson")
@IdClass(clsSalesPersonMasterModel_ID.class)
public class clsSalesPersonMasterModel implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public clsSalesPersonMasterModel(){
	}
	
	public clsSalesPersonMasterModel(clsSalesPersonMasterModel_ID objModelID) {
		strSalesPersonCode = objModelID.getStrSalesPersonCode();
		strClientCode = objModelID.getStrClientCode();
	}
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSalesPersonCode", column = @Column(name = "strSalesPersonCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	
	@Column(name="strSalesPersonCode")
	private String strSalesPersonCode;

	@Column(name="strSalesPersonName")
	private String strSalesPersonName;

	@Column(name="strClientCode")
	private String strClientCode;
	
	
	@Column(name="intID")
	private Long intID;

	public String getStrSalesPersonCode(){
		return strSalesPersonCode;
	}
	public void setStrSalesPersonCode(String strSalesPersonCode){
		this. strSalesPersonCode = (String) setDefaultValue( strSalesPersonCode, "NA");
	}

	public String getStrSalesPersonName(){
		return strSalesPersonName;
	}
	public void setStrSalesPersonName(String strSalesPersonName){
		this. strSalesPersonName = (String) setDefaultValue( strSalesPersonName, "NA");
	}

	public String getStrClientCode(){
		return strClientCode;
	}
	public void setStrClientCode(String strClientCode){
		this. strClientCode = (String) setDefaultValue( strClientCode, "NA");
	}


	public Long getIntID() {
		return intID;
	}

	public void setIntID(Long intID) {
		this.intID = intID;
	}

	//Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue){
		if(value !=null && (value instanceof String && value.toString().length()>0)){
			return value;
		}
		else if(value !=null && (value instanceof Double && value.toString().length()>0)){
			return value;
		}
		else if(value !=null && (value instanceof Integer && value.toString().length()>0)){
			return value;
		}
		else if(value !=null && (value instanceof Long && value.toString().length()>0)){
			return value;
		}
		else{
			return defaultValue;
		}
	}
	
}
