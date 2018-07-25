package com.sanguine.webpms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;

import org.hibernate.annotations.CollectionOfElements;

import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsJVModel_ID;

@Entity
@Table(name = "tblwalkinhd")
@IdClass(clsWalkinModel_ID.class)
public class clsWalkinHdModel implements Serializable {
	private static final long serialVersionUID = 1L;

	public clsWalkinHdModel() {
	}

	public clsWalkinHdModel(clsWalkinModel_ID objModelID) {
		strWalkinNo = objModelID.getStrWalkinNo();
		strClientCode = objModelID.getStrClientCode();
	}

	@CollectionOfElements(fetch = FetchType.LAZY)
	@JoinTable(name = "tblwalkindtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strWalkinNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strWalkinNo", column = @Column(name = "strWalkinNo")), @AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsWalkinDtl> listWalkinDtlModel = new ArrayList<clsWalkinDtl>();
	
	@CollectionOfElements(fetch = FetchType.EAGER)
	@JoinTable(name = "tblwalkinroomratedtl", joinColumns = { @JoinColumn(name = "strClientCode"), @JoinColumn(name = "strWalkinNo") })
	@Id
	@AttributeOverrides({ @AttributeOverride(name = "strWalkinNo", column = @Column(name = "strWalkinNo")), 
		@AttributeOverride(name = "strClientCode", column = @Column(name = "strClientCode")) })
	private List<clsWalkinRoomRateDtlModel> listWalkinRoomRateDtlModel = new ArrayList<clsWalkinRoomRateDtlModel>();
	
	

	// Variable Declaration

	@Column(name = "strWalkinNo")
	private String strWalkinNo;

	@Column(name = "dteWalkinDate")
	private String dteWalkinDate;

	@Column(name = "dteCheckOutDate")
	private String dteCheckOutDate;

	@Column(name = "tmeWalkinTime")
	private String tmeWalkinTime;

	@Column(name = "tmeCheckOutTime")
	private String tmeCheckOutTime;

	@Column(name = "strCorporateCode")
	private String strCorporateCode;

	@Column(name = "strBookerCode")
	private String strBookerCode;

	@Column(name = "strAgentCode")
	private String strAgentCode;

	@Column(name = "intNoOfNights")
	private int intNoOfNights;

	@Column(name = "strRemarks")
	private String strRemarks;

	@Column(name = "strUserCreated", updatable = false)
	private String strUserCreated;

	@Column(name = "strUserEdited")
	private String strUserEdited;

	@Column(name = "dteDateCreated", updatable = false)
	private String dteDateCreated;

	@Column(name = "dteDateEdited")
	private String dteDateEdited;

	@Column(name = "strClientCode")
	private String strClientCode;

	@Column(name = "strRoomNo")
	private String strRoomNo;

	@Column(name = "strExtraBedCode")
	private String strExtraBedCode;

	@Column(name = "intNoOfAdults")
	private int intNoOfAdults;

	@Column(name = "intNoOfChild")
	private int intNoOfChild;
	
	@Column(name = "strIncomeHeadCode")
	private String strIncomeHeadCode;

	// Setter-Getter Methods
	public String getStrWalkinNo() {
		return strWalkinNo;
	}

	public void setStrWalkinNo(String strWalkinNo) {
		this.strWalkinNo = strWalkinNo;
	}

	public String getDteWalkinDate() {
		return dteWalkinDate;
	}

	public void setDteWalkinDate(String dteWalkinDate) {
		this.dteWalkinDate = dteWalkinDate;
	}

	public String getDteCheckOutDate() {
		return dteCheckOutDate;
	}

	public void setDteCheckOutDate(String dteCheckOutDate) {
		this.dteCheckOutDate = dteCheckOutDate;
	}

	public String getStrCorporateCode() {
		return strCorporateCode;
	}

	public void setStrCorporateCode(String strCorporateCode) {
		this.strCorporateCode = (String) setDefaultValue(strCorporateCode, "NA");
	}

	public String getStrBookerCode() {
		return strBookerCode;
	}

	public void setStrBookerCode(String strBookerCode) {
		this.strBookerCode = (String) setDefaultValue(strBookerCode, "NA");
	}

	public String getStrAgentCode() {
		return strAgentCode;
	}

	public void setStrAgentCode(String strAgentCode) {
		this.strAgentCode = (String) setDefaultValue(strAgentCode, "NA");
	}

	public int getIntNoOfNights() {
		return intNoOfNights;
	}

	public void setIntNoOfNights(int intNoOfNights) {
		this.intNoOfNights = (Integer) setDefaultValue(intNoOfNights, "0");
	}

	public String getStrRemarks() {
		return strRemarks;
	}

	public void setStrRemarks(String strRemarks) {
		this.strRemarks = (String) setDefaultValue(strRemarks, "NA");
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
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

	public List<clsWalkinDtl> getListWalkinDtlModel() {
		return listWalkinDtlModel;
	}

	public void setListWalkinDtlModel(List<clsWalkinDtl> listWalkinDtlModel) {
		this.listWalkinDtlModel = listWalkinDtlModel;
	}

	public String getTmeWalkinTime() {
		return tmeWalkinTime;
	}

	public void setTmeWalkinTime(String tmeWalkinTime) {
		this.tmeWalkinTime = tmeWalkinTime;
	}

	public String getTmeCheckOutTime() {
		return tmeCheckOutTime;
	}

	public void setTmeCheckOutTime(String tmeCheckOutTime) {
		this.tmeCheckOutTime = tmeCheckOutTime;
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

	public String getStrRoomNo() {
		return strRoomNo;
	}

	public void setStrRoomNo(String strRoomNo) {
		this.strRoomNo = strRoomNo;
	}

	public String getStrExtraBedCode() {
		return strExtraBedCode;
	}

	public void setStrExtraBedCode(String strExtraBedCode) {
		this.strExtraBedCode = strExtraBedCode;
	}

	public int getIntNoOfAdults() {
		return intNoOfAdults;
	}

	public void setIntNoOfAdults(int intNoOfAdults) {
		this.intNoOfAdults = intNoOfAdults;
	}

	public int getIntNoOfChild() {
		return intNoOfChild;
	}

	public void setIntNoOfChild(int intNoOfChild) {
		this.intNoOfChild = intNoOfChild;
	}

	public String getStrIncomeHeadCode() {
		return strIncomeHeadCode;
	}

	public void setStrIncomeHeadCode(String strIncomeHeadCode) {
		this.strIncomeHeadCode = strIncomeHeadCode;
	}

	public List<clsWalkinRoomRateDtlModel> getListWalkinRoomRateDtlModel() {
		return listWalkinRoomRateDtlModel;
	}

	public void setListWalkinRoomRateDtlModel(List<clsWalkinRoomRateDtlModel> listWalkinRoomRateDtlModel) {
		this.listWalkinRoomRateDtlModel = listWalkinRoomRateDtlModel;
	}

}
