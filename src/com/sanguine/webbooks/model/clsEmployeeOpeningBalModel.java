package com.sanguine.webbooks.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Embeddable
public class clsEmployeeOpeningBalModel implements Serializable
{

	private static final long serialVersionUID = 1L;

	public clsEmployeeOpeningBalModel()
	{
	}

	// Variable Declaration

	private String strAccountCode;

	private String strAccountName;

	private String dblOpeningbal;

	private String strCrDr;

	// Setter-Getter Methods

	public String getStrAccountCode()
	{
		return strAccountCode;
	}

	public void setStrAccountCode(String strAccountCode)
	{
		this.strAccountCode = strAccountCode;
	}

	public String getStrAccountName()
	{
		return strAccountName;
	}

	public void setStrAccountName(String strAccountName)
	{
		this.strAccountName = strAccountName;
	}

	public String getDblOpeningbal()
	{
		return dblOpeningbal;
	}

	public void setDblOpeningbal(String dblOpeningbal)
	{
		this.dblOpeningbal = dblOpeningbal;
	}

	public String getStrCrDr()
	{
		return strCrDr;
	}

	public void setStrCrDr(String strCrDr)
	{
		this.strCrDr = strCrDr;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue)
	{
		if (value != null && (value instanceof String && value.toString().length() > 0))
		{
			return value;
		}
		else if (value != null && (value instanceof Double && value.toString().length() > 0))
		{
			return value;
		}
		else if (value != null && (value instanceof Integer && value.toString().length() > 0))
		{
			return value;
		}
		else if (value != null && (value instanceof Long && value.toString().length() > 0))
		{
			return value;
		}
		else
		{
			return defaultValue;
		}
	}

}
