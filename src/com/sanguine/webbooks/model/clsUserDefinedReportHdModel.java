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
@Table(name = "tbluserdefinedreporthd")
@IdClass(clsUserDefinedReportHdModel_ID.class)
public class clsUserDefinedReportHdModel extends clsBaseModel implements Serializable
{
   
	private static final long serialVersionUID = 1L;

	public clsUserDefinedReportHdModel() {
	}

	public clsUserDefinedReportHdModel(clsUserDefinedReportHdModel_ID objModelID) {
		strReportId = objModelID.getStrReportId();
		strClientCode = objModelID.getStrClientCode();
	}
	
	
	
	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tbluserdefinedreportdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strReportId") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReportId", column = @Column(name = "strReportId")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsUserDefinedReportDtlModel> listUserDefRptDtlModel = new ArrayList<clsUserDefinedReportDtlModel>();

	
	
	// Variable Declaration
		@Column(name = "strReportId")
		private String strReportId;
		
		@Column(name = "intid")
		private long intid;
		
		@Column(name = "strReportName")
		private String strReportName;
		
		@Column(name = "dteUserDefDate")
		private String dteUserDefDate;
		
		@Column(name = "dteDateCreated", updatable = false)
		private String dteDateCreated;
		
		@Column(name = "dteDateEdited")
		private String dteDateEdited;
		
		@Column(name = "strUserCreated" , updatable = false)
		private String strUserCreated;
		
		@Column(name = "strUserModified")
		private String strUserModified;
		
		@Column(name = "strClientCode")
		private String strClientCode;
		
		
		public static long getSerialversionuid()
		{
			return serialVersionUID;
		}


		public String getStrReportId() {
			return strReportId;
		}

		public void setStrReportId(String strReportId) {
			this.strReportId = strReportId;
		}

		
		public long getIntid() {
			return intid;
		}

		public void setIntid(long intid) {
			this.intid = intid;
		}

		public String getStrReportName() {
			return strReportName;
		}

		public void setStrReportName(String strReportName) {
			this.strReportName = strReportName;
		}

		public String getDteUserDefDate() {
			return dteUserDefDate;
		}

		public void setDteUserDefDate(String dteUserDefDate) {
			this.dteUserDefDate = dteUserDefDate;
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

		public String getStrUserCreated() {
			return strUserCreated;
		}

		public void setStrUserCreated(String strUserCreated) {
			this.strUserCreated = strUserCreated;
		}

		public String getStrUserModified() {
			return strUserModified;
		}

		public void setStrUserModified(String strUserModified) {
			this.strUserModified = strUserModified;
		}

		public String getStrClientCode() {
			return strClientCode;
		}

		public void setStrClientCode(String strClientCode) {
			this.strClientCode = strClientCode;
		}

		public List<clsUserDefinedReportDtlModel> getListUserDefRptDtlModel() {
			return listUserDefRptDtlModel;
		}

		public void setListUserDefRptDtlModel(
				List<clsUserDefinedReportDtlModel> listUserDefRptDtlModel) {
			this.listUserDefRptDtlModel = listUserDefRptDtlModel;
		}
		
		
		
		
	
}
