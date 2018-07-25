package com.sanguine.webclub.model;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "tblmemberphoto")
@IdClass(clsWebClubMemberPhotoModel_ID.class)
public class clsWebClubMemberPhotoModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWebClubMemberPhotoModel() {
	}

	public clsWebClubMemberPhotoModel(clsWebClubMemberPhotoModel_ID objModelID) {
		strMemberCode = objModelID.getStrMemberCode();
		strClientCode = objModelID.getStrClientCode();
	}

	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strMemberCode", column = @Column(name = "strMemberCode")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	// Variable Declaration
	@Column(name = "strMemberCode")
	private String strMemberCode;

	@Column(name = "strMemberName")
	private String strMemberName;

	@Column(name = "strUserCreated")
	private String strUserCreated;

	@Column(name = "strMemberImage", length = 1000000000, nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@JsonIgnore
	private Blob strMemberImage;

	@Column(name = "dteCreatedDate")
	private String dteCreatedDate;

	@Column(name = "strUserModified")
	private String strUserModified;

	@Column(name = "dteLastModified")
	private String dteLastModified;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strPropertyCode")
	private String strPropertyCode;

	// Setter-Getter Methods
	public String getStrMemberCode() {
		return strMemberCode;
	}

	public void setStrMemberCode(String strMemberCode) {
		this.strMemberCode = (String) setDefaultValue(strMemberCode, "NA");
	}

	public String getStrMemberName() {
		return strMemberName;
	}

	public void setStrMemberName(String strMemberName) {
		this.strMemberName = (String) setDefaultValue(strMemberName, "NA");
	}

	public String getStrUserCreated() {
		return strUserCreated;
	}

	public void setStrUserCreated(String strUserCreated) {
		this.strUserCreated = (String) setDefaultValue(strUserCreated, "NA");
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
		this.strUserModified = (String) setDefaultValue(strUserModified, "NA");
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
		this.strClientCode = (String) setDefaultValue(strClientCode, "NA");
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = (String) setDefaultValue(strPropertyCode, "NA");
	}

	// Function to Set Default Values
	private Object setDefaultValue(Object value, Object defaultValue) {
		if (value != null && (value instanceof String && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Double && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Integer && value.toString().length() > 0)) {
			return value;
		} else if (value != null && (value instanceof Long && value.toString().length() > 0)) {
			return value;
		} else {
			return defaultValue;
		}
	}

	public Blob getStrMemberImage() {
		return strMemberImage;
	}

	public void setStrMemberImage(Blob strMemberImage) {
		this.strMemberImage = strMemberImage;
	}

}
