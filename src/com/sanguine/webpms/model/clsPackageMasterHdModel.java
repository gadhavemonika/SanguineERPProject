package com.sanguine.webpms.model;

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
@Table(name = "tblpackagemasterhd")
@IdClass(clsPackageMasterModel_ID.class)
public class clsPackageMasterHdModel extends clsBaseModel implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public clsPackageMasterHdModel() {
	}

	public clsPackageMasterHdModel(clsPackageMasterModel_ID objModelID) 
	{
		strPackageCode = objModelID.getStrPackageCode();
		strClientCode = objModelID.getStrClientCode();
	}
	
	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblpackagemasterdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strPackageCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strPackageCode", column = @Column(name = "strPackageCode")) })
	List<clsPackageMasterDtl> listPackageDtl = new ArrayList<clsPackageMasterDtl>();

	// Variable Declaration
	@Column(name = "strPackageCode")
	private String strPackageCode;
	
	@Column(name = "strPackageName")
	private String strPackageName;
	
	@Column(name = "dblPackageAmt")
	private double dblPackageAmt;
	
	@Column(name = "strUserCreated")
	private String strUserCreated;
	
	@Column(name = "strUserEdited")
	private String strUserEdited;
	
	@Column(name = "dteDateCreated")
	private String dteDateCreated;
	
	@Column(name = "dteDateEdited")
	private String dteDateEdited;
	
	@Column(name = "strClientCode")
	private String strClientCode;

	public List<clsPackageMasterDtl> getListPackageDtl() {
		return listPackageDtl;
	}

	public void setListPackageDtl(List<clsPackageMasterDtl> listPackageDtl) {
		this.listPackageDtl = listPackageDtl;
	}

	public String getStrPackageCode() {
		return strPackageCode;
	}

	public void setStrPackageCode(String strPackageCode) {
		this.strPackageCode = strPackageCode;
	}

	public String getStrPackageName() {
		return strPackageName;
	}

	public void setStrPackageName(String strPackageName) {
		this.strPackageName = strPackageName;
	}
	
	public double getDblPackageAmt() {
		return dblPackageAmt;
	}

	public void setDblPackageAmt(double dblPackageAmt) {
		this.dblPackageAmt = dblPackageAmt;
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
