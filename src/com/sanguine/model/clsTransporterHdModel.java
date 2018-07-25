package com.sanguine.model;

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

@Entity
@Table(name = "tbltransportermaster")
@IdClass(clsTransporterHdModel_ID.class)
public class clsTransporterHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsTransporterHdModel() {
	}

	public clsTransporterHdModel(clsTransporterHdModel_ID objModelID) {
		strTransCode = objModelID.getStrTransCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tbltransportermasterdtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strTransCode") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strTransCode", column = @Column(name = "strTransCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsTransporterModelDtl> listTransportDtlModel = new ArrayList<clsTransporterModelDtl>();

	@Column(name = "strTransCode")
	private String strTransCode;

	@Column(name = "strTransName")
	private String strTransName;

	@Column(name = "strDesc")
	private String strDesc;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "dteCreatedDate", updatable = false)
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "intId", nullable = false, updatable = false)
	private long intId;

	public List<clsTransporterModelDtl> getListTransportDtlModel() {
		return listTransportDtlModel;
	}

	public void setListTransportDtlModel(List<clsTransporterModelDtl> listTransportDtlModel) {
		this.listTransportDtlModel = listTransportDtlModel;
	}

	public String getStrTransCode() {
		return strTransCode;
	}

	public void setStrTransCode(String strTransCode) {
		this.strTransCode = strTransCode;
	}

	public String getStrTransName() {
		return strTransName;
	}

	public void setStrTransName(String strTransName) {
		this.strTransName = strTransName;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = strUserCreated;
	}

	public String getDteCreatedDate() {
		return dteCreatedDate;
	}

	public void setDteCreatedDate(String dteCreatedDate) {
		this.dteCreatedDate = dteCreatedDate;
	}

	public String getStrUserModified() {
		return strUserModified;
	}

	public void setStrUserModified(String strUserModified) {
		this.strUserModified = strUserModified;
	}

	public String getDteLastModified() {
		return dteLastModified;
	}

	public void setDteLastModified(String dteLastModified) {
		this.dteLastModified = dteLastModified;
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
