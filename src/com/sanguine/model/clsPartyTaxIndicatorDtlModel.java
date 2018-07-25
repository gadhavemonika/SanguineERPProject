package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@SuppressWarnings("serial")
// @Entity
// @Table(name="tblpartytaxdtl")
public class clsPartyTaxIndicatorDtlModel implements Serializable {
	// @GeneratedValue
	// @Id
	@Column(name = "intId")
	private long intId;

	// @Id

	/*
	 * @Column(name="strPCode",columnDefinition="VARCHAR(20) NOT NULL default ''"
	 * ) private String strPCode;
	 * 
	 * @Column(name="strClientCode",columnDefinition=
	 * "VARCHAR(20) NOT NULL default ''") private String strClientCode;
	 */

	@Column(name = "strTaxCode", columnDefinition = "VARCHAR(20) NOT NULL default ''")
	private String strTaxCode;

	/*
	 * public String getStrPCode() { return strPCode; } public void
	 * setStrPCode(String strPCode) { this.strPCode = strPCode; }
	 */

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	/*
	 * public String getStrClientCode() { return strClientCode; } public void
	 * setStrClientCode(String strClientCode) { this.strClientCode =
	 * strClientCode; }
	 */
}
