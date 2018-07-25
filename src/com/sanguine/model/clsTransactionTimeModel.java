package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbltransactiontime")
@IdClass(clsTransactionTimeModel_ID.class)
public class clsTransactionTimeModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public clsTransactionTimeModel() {
	}

	public clsTransactionTimeModel(clsTransactionTimeModel_ID clsTransactionTimeModel_ID) {
		strLocCode = clsTransactionTimeModel_ID.getStrLocCode();
		strClientCode = clsTransactionTimeModel_ID.getStrClientCode();
		strPropertyCode = clsTransactionTimeModel_ID.getStrPropertyCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strLocCode", column = @Column(name = "strLocCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")), @AttributeOverride(name = "strPropertyCode", column = @Column(name = "strPropertyCode")) })
	@Column(name = "strLocCode")
	private String strLocCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "tmeFrom")
	private String tmeFrom;

	@Column(name = "tmeTo")
	private String tmeTo;

	@Column(name = "strTransactionName")
	private String strTransactionName;

	@Transient
	private String strLocName;

	public String getStrLocCode() {
		return strLocCode;
	}

	public void setStrLocCode(String strLocCode) {
		this.strLocCode = strLocCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getTmeFrom() {
		return tmeFrom;
	}

	public void setTmeFrom(String tmeFrom) {
		this.tmeFrom = tmeFrom;
	}

	public String getTmeTo() {
		return tmeTo;
	}

	public void setTmeTo(String tmeTo) {
		this.tmeTo = tmeTo;
	}

	public String getStrTransactionName() {
		return strTransactionName;
	}

	public void setStrTransactionName(String strTransactionName) {
		this.strTransactionName = strTransactionName;
	}

	public String getStrLocName() {
		return strLocName;
	}

	public void setStrLocName(String strLocName) {
		this.strLocName = strLocName;
	}

}
