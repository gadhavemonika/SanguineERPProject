package com.sanguine.webbooks.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.webbooks.model.clsEmployeeOpeningBalModel;
import com.sanguine.webbooks.model.clsSundryDebtorOpeningBalMasterModel;

public class clsEmployeeMasterBean
{

	private String strEmployeeCode;

	private String strEmployeeName;

	private long intID;

	private String strUserCreated;

	private String strUserModified;

	private String dteCreatedDate;

	private String dteLastModified;

	private String strClientCode;
	
	
	private List<clsEmployeeOpeningBalModel> listEmployeeOpenongBalModel = new ArrayList<clsEmployeeOpeningBalModel>();
	

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


	
	
	

}
