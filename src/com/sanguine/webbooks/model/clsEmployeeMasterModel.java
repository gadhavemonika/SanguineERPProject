package com.sanguine.webbooks.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.base.model.clsBaseModel;

@Entity
@Table(name = "tblemployeemaster")
@IdClass(clsEmployeeMasterModel_ID.class)
public class clsEmployeeMasterModel extends clsBaseModel implements Serializable
{
	private static final long serialVersionUID = 1L;

	public clsEmployeeMasterModel()
	{

	}

	public clsEmployeeMasterModel(clsEmployeeMasterModel_ID objEmlpoyeeModelID)
	{
		strEmployeeCode = objEmlpoyeeModelID.getStrEmployeeCode();
		strClientCode = objEmlpoyeeModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblemployeeopeningbalance", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strEmployeeCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strEmployeeCode", column = @Column(name = "strEmployeeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	List<clsEmployeeOpeningBalModel> listEmployeeOpenongBalModel = new ArrayList<clsEmployeeOpeningBalModel>();

	@Column(name = "strEmployeeCode")
	private String strEmployeeCode;

	@Column(name = "strEmployeeName")
	private String strEmployeeName;

	@Column(name = "intID", nullable = false, updatable = false)
	private long intID;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "strClientCode")
	private String strClientCode;
	
	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	public String getStrEmployeeCode()
	{
		return strEmployeeCode;
	}

	public void setStrEmployeeCode(String strEmployeeCode)
	{
		this.strEmployeeCode = strEmployeeCode;
	}

	public String getStrEmployeeName()
	{
		return strEmployeeName;
	}

	public void setStrEmployeeName(String strEmployeeName)
	{
		this.strEmployeeName = strEmployeeName;
	}

	public String getDteCreatedDate()
	{
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate)
	{
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getDteLastModified()
	{
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified)
	{
		this.dteLastModified = dteLastModified;
	}

	public String getStrUserCreated()
	{
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated)
	{
		this.strUserCreated = strUserCreated;
	}

	public String getStrUserModified()
	{
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified)
	{
		this.strUserModified = strUserModified;
	}

	public String getStrClientCode()
	{
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode)
	{
		this.strClientCode = strClientCode;
	}

	public long getIntID()
	{
		return intID;
	}

	public void setIntID(long intID)
	{
		this.intID = intID;
	}

	public List<clsEmployeeOpeningBalModel> getListEmployeeOpenongBalModel()
	{
		return listEmployeeOpenongBalModel;
	}

	public void setListEmployeeOpenongBalModel(List<clsEmployeeOpeningBalModel> listEmployeeOpenongBalModel)
	{
		this.listEmployeeOpenongBalModel = listEmployeeOpenongBalModel;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

}
