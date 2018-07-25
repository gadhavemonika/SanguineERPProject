package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

    @Entity
	@Table(name = "tblchangedroomtypedtl")
	//@IdClass(clsChangedRoomTypeDtlModel_ID.class)
	public class clsChangedRoomTypeDtlModel implements Serializable {
		private static final long serialVersionUID = 1L;

		public clsChangedRoomTypeDtlModel() {
		}

		public clsChangedRoomTypeDtlModel(clsChangedRoomTypeDtlModel_ID objModelID) {
			strDocNo = objModelID.getStrDocNo();
			strRoomType = objModelID.getStrRoomType();
			strClientCode = objModelID.getStrClientCode();
		}
		

		@Id
		@AttributeOverrides({ @AttributeOverride(name = "strDocNo", column = @Column(name = "strDocNo")), @AttributeOverride(name = "strRoomType", column = @Column(name = "strRoomType")),@AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
		// Variable Declaration
		
		@Column(name = "strDocNo")
		private String strDocNo;
		
		@Column(name = "strType")
		private String strType;
		
		@Column(name = "strRoomType")
		private String strRoomType;
		
		@Column(name = "strRoomNo")
		private String strRoomNo;
		
		@Column(name = "strGuestCode")
		private String strGuestCode;
		
		public String getStrGuestCode() {
			return strGuestCode;
		}

		public void setStrGuestCode(String strGuestCode) {
			this.strGuestCode = strGuestCode;
		}

		@Column(name = "dteFromDate", updatable = false)
		private String dteFromDate;
		
		@Column(name = "dteToDate")
		private String dteToDate;
		
		@Column(name = "strClientCode")
		private String strClientCode;

		
		
		// Setter-Getter Methods
	
		public String getStrDocNo() {
			return strDocNo;
		}

		public void setStrDocNo(String strDocNo) {
			this.strDocNo = strDocNo;
		}

		public String getStrType() {
			return strType;
		}

		public void setStrType(String strType) {
			this.strType = strType;
		}

		public String getStrRoomType() {
			return strRoomType;
		}

		public void setStrRoomType(String strRoomType) {
			this.strRoomType = strRoomType;
		}

		public String getStrRoomNo() {
			return strRoomNo;
		}

		public void setStrRoomNo(String strRoomNo) {
			this.strRoomNo = strRoomNo;
		}

		public String getDteFromDate() {
			return dteFromDate;
		}

		public void setDteFromDate(String dteFromDate) {
			this.dteFromDate = dteFromDate;
		}

		public String getDteToDate() {
			return dteToDate;
		}

		public void setDteToDate(String dteToDate) {
			this.dteToDate = dteToDate;
		}

		public String getStrClientCode() {
			return strClientCode;
		}

		public void setStrClientCode(String strClientCode) {
			this.strClientCode = strClientCode;
		}
		
}
