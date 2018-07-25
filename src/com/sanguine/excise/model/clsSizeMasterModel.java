package com.sanguine.excise.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblsizemaster")
@IdClass(clsSizeMasterModel_ID.class)
public class clsSizeMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsSizeMasterModel() {
	}

	public clsSizeMasterModel(clsSizeMasterModel_ID objModelID) {
		strSizeCode = objModelID.getStrSizeCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strSizeCode", column = @Column(name = "strSizeCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strSizeCode")
	private String strSizeCode;

	@Column(name = "intId")
	private Long intId;

	@Column(name = "strSizeName")
	private String strSizeName;

	@Column(name = "intQty")
	private Long intQty;

	@Column(name = "strUOM")
	private String strUOM;

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

	@Column(name = "strNarration")
	private String strNarration;

	// Setter-Getter Methods

	public String getStrSizeCode() {
		return strSizeCode;
	}

	public void setStrSizeCode(String strSizeCode) {
		this.strSizeCode = strSizeCode;
	}

	public Long getIntId() {
		return intId;
	}

	public void setIntId(Long intId) {
		this.intId = intId;
	}

	public String getStrSizeName() {
		return strSizeName;
	}

	public void setStrSizeName(String strSizeName) {
		this.strSizeName = strSizeName;
	}

	public Long getIntQty() {
		return intQty;
	}

	public void setIntQty(Long intQty) {
		this.intQty = intQty;
	}

	public String getStrUOM() {
		return strUOM;
	}

	public void setStrUOM(String strUOM) {
		this.strUOM = strUOM;
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

	public String getStrNarration() {
		return strNarration;
	}

	public void setStrNarration(String strNarration) {
		this.strNarration = strNarration;
	}

}
