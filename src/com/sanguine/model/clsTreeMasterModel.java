package com.sanguine.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbltreemast")
public class clsTreeMasterModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strFormName;
	private String strFormDesc;
	private String strRootNode;
	private String intRootIndex;
	private String strType;
	private String intFormKey;
	private String intFormNo;
	private String strImgSrc;
	private String strImgName;
	private String strModule;
	private String strTemp;
	private String strActFile;
	private String strRequestMapping;
	private String strFormAccessYN;

	@Column(nullable = false, updatable = false, insertable = false)
	private String strAutorisationForm;

	@Column(name = "strAuditForm", nullable = true)
	private String strAuditForm;

	@Transient
	private String strAdd;

	@Transient
	private String strEdit;

	@Transient
	private String strPrint;

	@Transient
	private String strView;

	@Transient
	private String strDelete;

	@Transient
	private String strAuthorise;

	@Transient
	private String strGrant;

	/*
	 * for setup from process tab RP
	 */
	@Transient
	private String strSalesOrder;

	@Transient
	private String strProductionOrder;

	@Transient
	private String strMinimumLevel;

	@Transient
	private String strRequisition;

	@Transient
	private String strDirect;

	@Transient
	private String strPurchaseIndent;

	@Transient
	private String strServiceOrder;

	@Transient
	private String strWorkOrder;

	@Transient
	private String strProject;

	@Transient
	private String strPurchaseOrder;

	@Transient
	private String strPurchaseReturn;

	@Transient
	private String strSalesReturn;

	@Transient
	private String strRateContractor;

	@Transient
	private String strGRN;

	@Transient
	private String strMIS;

	@Transient
	private String strDeliveryNote;

	@Transient
	private String strOpeningStock;

	@Transient
	private String strSubContractorGRN;

	@Transient
	private String strSalesProjection;

	@Transient
	private String strInvoice;

	@Transient
	private String strDeliverySchedule;

	/*
	 * End for setup from process tab RP
	 */

	public String getStrAutorisationForm() {
		return strAutorisationForm;
	}

	public void setStrAutorisationForm(String strAutorisationForm) {
		this.strAutorisationForm = strAutorisationForm;
	}

	public String getStrSalesOrder() {
		return strSalesOrder;
	}

	public void setStrSalesOrder(String strSalesOrder) {
		this.strSalesOrder = strSalesOrder;
	}

	public String getStrProductionOrder() {
		return strProductionOrder;
	}

	public void setStrProductionOrder(String strProductionOrder) {
		this.strProductionOrder = strProductionOrder;
	}

	public String getStrMinimumLevel() {
		return strMinimumLevel;
	}

	public void setStrMinimumLevel(String strMinimumLevel) {
		this.strMinimumLevel = strMinimumLevel;
	}

	public String getStrRequisition() {
		return strRequisition;
	}

	public void setStrRequisition(String strRequisition) {
		this.strRequisition = strRequisition;
	}

	public String getStrDirect() {
		return strDirect;
	}

	public void setStrDirect(String strDirect) {
		this.strDirect = strDirect;
	}

	public String getStrPurchaseIndent() {
		return strPurchaseIndent;
	}

	public void setStrPurchaseIndent(String strPurchaseIndent) {
		this.strPurchaseIndent = strPurchaseIndent;
	}

	public String getStrServiceOrder() {
		return strServiceOrder;
	}

	public void setStrServiceOrder(String strServiceOrder) {
		this.strServiceOrder = strServiceOrder;
	}

	public String getStrWorkOrder() {
		return strWorkOrder;
	}

	public void setStrWorkOrder(String strWorkOrder) {
		this.strWorkOrder = strWorkOrder;
	}

	public String getStrProject() {
		return strProject;
	}

	public void setStrProject(String strProject) {
		this.strProject = strProject;
	}

	public String getStrPurchaseOrder() {
		return strPurchaseOrder;
	}

	public void setStrPurchaseOrder(String strPurchaseOrder) {
		this.strPurchaseOrder = strPurchaseOrder;
	}

	public String getStrPurchaseReturn() {
		return strPurchaseReturn;
	}

	public void setStrPurchaseReturn(String strPurchaseReturn) {
		this.strPurchaseReturn = strPurchaseReturn;
	}

	public String getStrSalesReturn() {
		return strSalesReturn;
	}

	public void setStrSalesReturn(String strSalesReturn) {
		this.strSalesReturn = strSalesReturn;
	}

	public String getStrRateContractor() {
		return strRateContractor;
	}

	public void setStrRateContractor(String strRateContractor) {
		this.strRateContractor = strRateContractor;
	}

	public String getStrGRN() {
		return strGRN;
	}

	public void setStrGRN(String strGRN) {
		this.strGRN = strGRN;
	}

	public String getStrDeliveryNote() {
		return strDeliveryNote;
	}

	public void setStrDeliveryNote(String strDeliveryNote) {
		this.strDeliveryNote = strDeliveryNote;
	}

	public String getStrOpeningStock() {
		return strOpeningStock;
	}

	public void setStrOpeningStock(String strOpeningStock) {
		this.strOpeningStock = strOpeningStock;
	}

	public String getStrSubContractorGRN() {
		return strSubContractorGRN;
	}

	public void setStrSubContractorGRN(String strSubContractorGRN) {
		this.strSubContractorGRN = strSubContractorGRN;
	}

	public String getStrSalesProjection() {
		return strSalesProjection;
	}

	public void setStrSalesProjection(String strSalesProjection) {
		this.strSalesProjection = strSalesProjection;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "strFormName")
	public String getStrFormName() {
		return strFormName;
	}

	public void setStrFormName(String strFormName) {
		this.strFormName = strFormName;
	}

	@Column(name = "strFormDesc")
	public String getStrFormDesc() {
		return strFormDesc;
	}

	public void setStrFormDesc(String strFormDesc) {
		this.strFormDesc = strFormDesc;
	}

	@Column(name = "strRootNode")
	public String getStrRootNode() {
		return strRootNode;
	}

	public void setStrRootNode(String strRootNode) {
		this.strRootNode = strRootNode;
	}

	@Column(name = "intRootIndex")
	public String getIntRootIndex() {
		return intRootIndex;
	}

	public void setIntRootIndex(String intRootIndex) {
		this.intRootIndex = intRootIndex;
	}

	@Column(name = "strType")
	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	@Column(name = "intFormKey")
	public String getIntFormKey() {
		return intFormKey;
	}

	public void setIntFormKey(String intFormKey) {
		this.intFormKey = intFormKey;
	}

	@Column(name = "intFormNo")
	public String getIntFormNo() {
		return intFormNo;
	}

	public void setIntFormNo(String intFormNo) {
		this.intFormNo = intFormNo;
	}

	@Column(name = "strImgSrc")
	public String getStrImgSrc() {
		return strImgSrc;
	}

	public void setStrImgSrc(String strImgSrc) {
		this.strImgSrc = strImgSrc;
	}

	@Column(name = "strImgName")
	public String getStrImgName() {
		return strImgName;
	}

	public void setStrImgName(String strImgName) {
		this.strImgName = strImgName;
	}

	@Column(name = "strModule")
	public String getStrModule() {
		return strModule;
	}

	public void setStrModule(String strModule) {
		this.strModule = strModule;
	}

	@Column(name = "strTemp")
	public String getStrTemp() {
		return strTemp;
	}

	public void setStrTemp(String strTemp) {
		this.strTemp = strTemp;
	}

	@Column(name = "strActFile")
	public String getStrActFile() {
		return strActFile;
	}

	public void setStrActFile(String strActFile) {
		this.strActFile = strActFile;
	}

	@Column(name = "strHelpFile")
	public String getStrHelpFile() {
		return strHelpFile;
	}

	public void setStrHelpFile(String strHelpFile) {
		this.strHelpFile = strHelpFile;
	}

	private String strHelpFile;

	public String getStrAdd() {
		return strAdd;
	}

	public void setStrAdd(String strAdd) {
		this.strAdd = strAdd;
	}

	public String getStrEdit() {
		return strEdit;
	}

	public void setStrEdit(String strEdit) {
		this.strEdit = strEdit;
	}

	public String getStrPrint() {
		return strPrint;
	}

	public void setStrPrint(String strPrint) {
		this.strPrint = strPrint;
	}

	public String getStrView() {
		return strView;
	}

	public void setStrView(String strView) {
		this.strView = strView;
	}

	public String getStrDelete() {
		return strDelete;
	}

	public void setStrDelete(String strDelete) {
		this.strDelete = strDelete;
	}

	public String getStrAuthorise() {
		return strAuthorise;
	}

	public void setStrAuthorise(String strAuthorise) {
		this.strAuthorise = strAuthorise;
	}

	public String getStrGrant() {
		return strGrant;
	}

	public void setStrGrant(String strGrant) {
		this.strGrant = strGrant;
	}

	public String getStrRequestMapping() {
		return strRequestMapping;
	}

	public void setStrRequestMapping(String strRequestMapping) {
		this.strRequestMapping = strRequestMapping;
	}

	public String getStrAuditForm() {
		return strAuditForm;
	}

	public void setStrAuditForm(String strAuditForm) {
		this.strAuditForm = strAuditForm;
	}

	public String getStrMIS() {
		return strMIS;
	}

	public void setStrMIS(String strMIS) {
		this.strMIS = strMIS;
	}

	public String getStrInvoice() {
		return strInvoice;
	}

	public void setStrInvoice(String strInvoice) {
		this.strInvoice = strInvoice;
	}

	public String getStrDeliverySchedule() {
		return strDeliverySchedule;
	}

	public void setStrDeliverySchedule(String strDeliverySchedule) {
		this.strDeliverySchedule = strDeliverySchedule;
	}

	public String getStrFormAccessYN() {
		return strFormAccessYN;
	}

	public void setStrFormAccessYN(String strFormAccessYN) {
		this.strFormAccessYN = strFormAccessYN;
	}
	
	

}
