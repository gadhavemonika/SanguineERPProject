package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbluserdesktop")
public class clsUserDesktopModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "strusercode")
	private String strusercode;
	@Column(name = "strformname")
	private String strformname;

	@Transient
	private boolean desktopForm;

	@Transient
	private String formDesc;

	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

	public String getStrusercode() {
		return strusercode;
	}

	public void setStrusercode(String strusercode) {
		this.strusercode = strusercode;
	}

	public String getStrformname() {
		return strformname;
	}

	public void setStrformname(String strformname) {
		this.strformname = strformname;
	}

	public boolean isDesktopForm() {
		return desktopForm;
	}

	public void setDesktopForm(boolean desktopForm) {
		this.desktopForm = desktopForm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
