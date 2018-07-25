package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbltaxsettlementmaster")
public class clsTaxSettlementMasterModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "intId")
	private long intId;

	@Column(name = "strTaxCode")
	private String strTaxCode;

	@Column(name = "strSettlementCode")
	private String strSettlementCode;

	public String getStrTaxCode() {
		return strTaxCode;
	}

	public void setStrTaxCode(String strTaxCode) {
		this.strTaxCode = strTaxCode;
	}

	public String getStrSettlementCode() {
		return strSettlementCode;
	}

	public void setStrSettlementCode(String strSettlementCode) {
		this.strSettlementCode = strSettlementCode;
	}
}
