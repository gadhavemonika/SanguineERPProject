package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "tblmrpidtl")
@IdClass(clsMRPIDtl_ID.class)
public class clsMRPIDtl implements Serializable {

	@Column(name = "strReqCode")
	private String strReqCode;
	@Column(name = "strPICode")
	private String strPICode;
	@Column(name = "strClientCode")
	private String strClientCode;

	public clsMRPIDtl() {
	}

	public clsMRPIDtl(clsMRPIDtl_ID MRPIDtl_ID) {
		strReqCode = MRPIDtl_ID.getStrReqCode();
		strPICode = MRPIDtl_ID.getStrPICode();
		strClientCode = MRPIDtl_ID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strReqCode", column = @Column(name = "strReqCode")), @AttributeOverride(name = "strPICode", column = @Column(name = "strPICode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	public String getStrReqCode() {
		return strReqCode;
	}

	public void setStrReqCode(String strReqCode) {
		this.strReqCode = strReqCode;
	}

	public String getStrPICode() {
		return strPICode;
	}

	public void setStrPICode(String strPICode) {
		this.strPICode = strPICode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

}
