package com.sanguine.crm.model;

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
@Table(name = "tblcategorymaster")
@IdClass(clsCategoryMasterModel_ID.class)
public class clsCategoryMasterModel extends clsBaseModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public clsCategoryMasterModel() {
	}

	public clsCategoryMasterModel(clsCategoryMasterModel_ID objModelID) 
	{
		strCategoryCode = objModelID.getStrCategoryCode();
		strClientCode = objModelID.getStrClientCode();
	}
	
	
	// Variable Declaration
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strCategoryCode", column = @Column(name = "strCategoryCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	@Column(name = "strCategoryCode")
	private String strCategoryCode;

	@Column(name = "strCategoryDesc")
	private String strCategoryDesc;


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

	public String getStrCategoryCode() {
		return strCategoryCode;
	}

	public void setStrCategoryCode(String strCategoryCode) {
		this.strCategoryCode = strCategoryCode;
	}

	public String getStrCategoryDesc() {
		return strCategoryDesc;
	}

	public void setStrCategoryDesc(String strCategoryDesc) {
		this.strCategoryDesc = strCategoryDesc;
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