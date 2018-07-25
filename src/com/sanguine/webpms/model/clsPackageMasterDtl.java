package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class clsPackageMasterDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsPackageMasterDtl() 
	{
	}
	
	// Variable Declaration
	    @Column(name = "strIncomeHeadCode")
		private String strIncomeHeadCode;
	    
	    @Column(name = "dblAmt")
	    private double dblAmt;
		
	    public String getStrIncomeHeadCode() {
			return strIncomeHeadCode;
		}

		public void setStrIncomeHeadCode(String strIncomeHeadCode) {
			this.strIncomeHeadCode = strIncomeHeadCode;
		}

		public double getDblAmt() {
			return dblAmt;
		}

		public void setDblAmt(double dblAmt) {
			this.dblAmt = dblAmt;
		}

		
		// Function to Set Default Values
		private Object setDefaultValue(Object value, Object defaultValue) {
			if (value != null && (value instanceof String && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
				return value;
			} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
				return value;
			} else {
				return defaultValue;
			}
		}

}
