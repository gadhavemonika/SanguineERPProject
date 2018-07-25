package com.sanguine.crm.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sanguine.base.model.clsBaseModel;

@Entity
@Table(name = "tblregionmaster")
@IdClass(clsRegionMasterModel_ID.class)
public class clsRegionMasterModel extends clsBaseModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public clsRegionMasterModel() {
	}

	public clsRegionMasterModel(clsRegionMasterModel_ID objModelID) 
	{
		strRegionCode = objModelID.getStrRegionCode();
		strClientCode = objModelID.getStrClientCode();
	}
	
	
	// Variable Declaration
		@Id
		@AttributeOverrides({ @AttributeOverride(name = "strRegionCode", column = @Column(name = "strRegionCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
		@Column(name = "strRegionCode")
		private String strRegionCode;

		@Column(name = "strRegionDesc")
		private String strRegionDesc;


		@Column(name = "strUserCreated")
		private String strUserCreated;

		@Column(name = "dtCreatedDate")
		private String dtCreatedDate;

		@Column(name = "strUserModified")
		private String strUserModified;

		@Column(name = "dtLastModified")
		private String dtLastModified;

		@Column(name = "strClientCode")
		private String strClientCode;
		
		@Column(name = "intId")
		private long intId;

		public String getStrRegionCode() {
			return strRegionCode;
		}

		public void setStrRegionCode(String strRegionCode) {
			this.strRegionCode = strRegionCode;
		}

		public String getStrRegionDesc() {
			return strRegionDesc;
		}

		public void setStrRegionDesc(String strRegionDesc) {
			this.strRegionDesc = strRegionDesc;
		}

		public String getStrUserCreated() {
			return strUserCreated;
		}

		public void setStrUserCreated(String strUserCreated) {
			this.strUserCreated = strUserCreated;
		}

		public String getDtCreatedDate() {
			return dtCreatedDate;
		}

		public void setDtCreatedDate(String dtCreatedDate) {
			this.dtCreatedDate = dtCreatedDate;
		}

		public String getStrUserModified() {
			return strUserModified;
		}

		public void setStrUserModified(String strUserModified) {
			this.strUserModified = strUserModified;
		}

		public String getDtLastModified() {
			return dtLastModified;
		}

		public void setDtLastModified(String dtLastModified) {
			this.dtLastModified = dtLastModified;
		}

		public String getStrClientCode() {
			return strClientCode;
		}

		public void setStrClientCode(String strClientCode) {
			this.strClientCode = strClientCode;
		}

		public long getIntId() {
			return intId;
		}

		public void setIntId(long intId) {
			this.intId = intId;
		}
}
