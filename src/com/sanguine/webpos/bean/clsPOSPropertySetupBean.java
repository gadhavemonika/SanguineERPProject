package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSPropertySetupBean {

	private String strPosCode;

	private String strClientCode;

	private String strClientName;

	private String strAddrLine1;

	private String strAddrLine2;

	private String strAddrLine3;

	private String strCity;

	private long strPinCode;

	private String strState;

	private String strCountry;

	private long strTelephone;

	private String strEmail;

	private String strNatureOfBussness;

	private String strBillFooter;

	private long intBiilPaperSize;

	private String strBillPrintMode;

	private long intColumnSize;

	private String strPrintingType;

	private String strBillFormat;

	private String chkShowBills;

	private String chkNegBilling;

	private String chkPrintKotForDirectBiller;

	private String chkEnableKOT;

	private String chkMultiBillPrint;

	private String chkDayEnd;

	private String chkPrintShortNameOnKOT;

	private String chkMultiKOTPrint;

	private String chkPrintInvoiceOnBill;

	private String chkPrintTDHItemsInBill;

	private String chkManualBillNo;

	private String chkPrintInclusiveOfAllTaxesOnBill;

	private String chkEffectOnPSP;

	private String chkPrintVatNo;

	private String strVatNo;

	private long intNoOfLinesInKOTPrint;

	private String chkServiceTaxNo;

	private String strServiceTaxNo;

	private String strShowBillsDtlType;

	private long strAdvRecPrintCount;

	private String strPOSType;

	private String strDataSendFrequency;

	private String strWebServiceLink;

	private String dteHOServerDate;

	private long dblMaxDiscount;

	private String strChangeTheme;

	private String strDirectArea;

	private String chkAreaWisePricing;

	private String strCustSeries;

	private String chkSlabBasedHomeDelCharges;

	private String chkEditHomeDelivery;

	private String chkPrintForVoidBill;

	private String chkDirectKOTPrintMakeKOT;

	private String chkSkipWaiterSelection;

	private String chkSkipPaxSelection;

	private String chkAreaMasterCompulsory;

	private String chkPostSalesDataToMMS;

	private String strItemType;

	private String chkPrinterErrorMessage;

	private String chkActivePromotions;

	private String chkPrintKOTYN;

	private String chkChangeQtyForExternalCode;

	private String strStockInOption;

	private String chkShowItemStkColumnInDB;

	private String strPriceFrom;

	private String chkPrintBill;

	private String strApplyDiscountOn;

	private String chkUseVatAndServiceNoFromPos;

	private String chkManualAdvOrderCompulsory;

	private String chkPrintManualAdvOrderOnBill;

	private String chkPrintModifierQtyOnKOT;

	private String chkBoxAllowNewAreaMasterFromCustMaster;

	private String strMenuItemSortingOn;

	private String chkAllowToCalculateItemWeight;

	private String strMenuItemDisSeq;

	private String chkItemWiseKOTPrintYN;

	private String chkItemQtyNumpad;

	private String chkSlipNoForCreditCardBillYN;

	private String chkPrintKOTToLocalPrinter;

	private String chkExpDateForCreditCardBillYN;

	private String chkDelBoyCompulsoryOnDirectBiller;

	private String chkSelectWaiterFromCardSwipe;

	private String chkEnableSettleBtnForDirectBillerBill;

	private String chkMultipleWaiterSelectionOnMakeKOT;

	private String chkDontShowAdvOrderInOtherPOS;

	private String chkMoveTableToOtherPOS;

	private String chkPrintZeroAmtModifierInBill;

	private String chkMoveKOTToOtherPOS;

	private String chkPointsOnBillPrint;

	private String chkCalculateTaxOnMakeKOT;

	private String chkCalculateDiscItemWise;

	private String chkTakewayCustomerSelection;

	private String chkSelectCustAddressForBill;

	private String chkGenrateMI;

	private String chkPopUpToApplyPromotionsOnBill;

	private String strWSClientCode;

	private String chkCheckDebitCardBalOnTrans;

	private long intDaysBeforeOrderToCancel;

	private String chkSettlementsFromPOSMaster;

	private long intNoOfDelDaysForAdvOrder;

	private String chkShiftWiseDayEnd;

	private long intNoOfDelDaysForUrgentOrder;

	private String chkProductionLinkup;

	private String chkLockDataOnShift;

	private String chkSetUpToTimeForAdvOrder;

	private String chkEnableBillSeries;

	private String strHours;

	private String strMinutes;

	private String strAMPM;

	private String chkEnablePMSIntegration;

	private String chkSetUpToTimeForUrgentOrder;

	private String chkPrintTimeOnBill;

	private String strHoursUrgentOrder;

	private String strMinutesUrgentOrder;

	private String strAMPMUrgent;

	private String chkPrintRemarkAndReasonForReprint;

	private String chkEnableBothPrintAndSettleBtnForDB;

	private String chkCarryForwardFloatAmtToNextDay;

	private String chkOpenCashDrawerAfterBillPrint;

	private String chkShowItemDtlsForChangeCustomerOnBill;

	private String chkPropertyWiseSalesOrder;

	private String chkShowPopUpForNextItemQuantity;

	private String strSenderEmailId;

	private String strEmailPassword;

	private String strEmailServerName;

	private String strReceiverEmailId;

	private String strBodyPart;

	private String strCardIntfType;

	private String strRFIDSetup;

	private String strRFIDServerName;

	private String strRFIDUserName;

	private String strRFIDPassword;

	private String strRFIDDatabaseName;

	private String strCRM;

	private String strGetWebservice;

	private String strPostWebservice;

	private String strOutletUID;

	private String strPOSID;

	private String strSMSType;

	private String strAreaSMSApi;

	private String chkHomeDelSMS;

	private String strAreaSendHomeDeliverySMS;

	private String chkBillSettlementSMS;

	private String strBillSeriesType;

	private String strAreaBillSettlementSMS;

	private String strFTPAddress;

	private String strFTPServerUserName;

	private String strFTPServerPass;

	private String strCMSIntegrationYN;

	private String strCMSWesServiceURL;

	private String chkMemberAsTable;

	private String chkMemberCodeForKOTJPOS;

	private String chkMemberCodeForKotInMposByCardSwipe;

	private String chkMemberCodeForMakeBillInMPOS;

	private String chkMemberCodeForKOTMPOS;

	private String chkSelectCustomerCodeFromCardSwipe;

	private String strCMSPostingType;

	private String strPOSForDayEnd;

	private String strInrestoPOSIntegrationYN;

	private String strInrestoPOSWesServiceURL;

	private String strInrestoPOSId;

	private String strInrestoPOSKey;

	private String strJioPOSIntegrationYN;

	private String strJioPOSWesServiceURL;

	private String strJioMID;

	private String strJioTID;

	private String strJioActivationCode;

	private String strJioDeviceID;

	private String chkNewBillSeriesForNewDay;

	private String chkShowReportsPOSWise;

	private String chkEnableDineIn;

	private String chkAutoAreaSelectionInMakeKOT;

	private List<clsBillSeriesDtlBean> listBillSeriesDtl;

	private List<clsPrinterSetupBean> listPrinterDtl;

	public String getStrPosCode() {
		return strPosCode;
	}

	public void setStrPosCode(String strPosCode) {
		this.strPosCode = strPosCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrClientName() {
		return strClientName;
	}

	public void setStrClientName(String strClientName) {
		this.strClientName = strClientName;
	}

	public String getStrAddrLine1() {
		return strAddrLine1;
	}

	public void setStrAddrLine1(String strAddrLine1) {
		this.strAddrLine1 = strAddrLine1;
	}

	public String getStrAddrLine2() {
		return strAddrLine2;
	}

	public void setStrAddrLine2(String strAddrLine2) {
		this.strAddrLine2 = strAddrLine2;
	}

	public String getStrAddrLine3() {
		return strAddrLine3;
	}

	public void setStrAddrLine3(String strAddrLine3) {
		this.strAddrLine3 = strAddrLine3;
	}

	public String getStrCity() {
		return strCity;
	}

	public void setStrCity(String strCity) {
		this.strCity = strCity;
	}

	public long getStrPinCode() {
		return strPinCode;
	}

	public void setStrPinCode(long strPinCode) {
		this.strPinCode = strPinCode;
	}

	public String getStrState() {
		return strState;
	}

	public void setStrState(String strState) {
		this.strState = strState;
	}

	public String getStrCountry() {
		return strCountry;
	}

	public void setStrCountry(String strCountry) {
		this.strCountry = strCountry;
	}

	public long getStrTelephone() {
		return strTelephone;
	}

	public void setStrTelephone(long strTelephone) {
		this.strTelephone = strTelephone;
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	public String getStrNatureOfBussness() {
		return strNatureOfBussness;
	}

	public void setStrNatureOfBussness(String strNatureOfBussness) {
		this.strNatureOfBussness = strNatureOfBussness;
	}

	public String getStrBillFooter() {
		return strBillFooter;
	}

	public void setStrBillFooter(String strBillFooter) {
		this.strBillFooter = strBillFooter;
	}

	public long getStrAdvRecPrintCount() {
		return strAdvRecPrintCount;
	}

	public void setStrAdvRecPrintCount(long strAdvRecPrintCount) {
		this.strAdvRecPrintCount = strAdvRecPrintCount;
	}

	public String getStrBillPrintMode() {
		return strBillPrintMode;
	}

	public void setStrBillPrintMode(String strBillPrintMode) {
		this.strBillPrintMode = strBillPrintMode;
	}

	public String getStrPrintingType() {
		return strPrintingType;
	}

	public void setStrPrintingType(String strPrintingType) {
		this.strPrintingType = strPrintingType;
	}

	public String getStrBillFormat() {
		return strBillFormat;
	}

	public void setStrBillFormat(String strBillFormat) {
		this.strBillFormat = strBillFormat;
	}

	public String getChkShowBills() {
		return chkShowBills;
	}

	public void setChkShowBills(String chkShowBills) {
		this.chkShowBills = chkShowBills;
	}

	public String getChkNegBilling() {
		return chkNegBilling;
	}

	public void setChkNegBilling(String chkNegBilling) {
		this.chkNegBilling = chkNegBilling;
	}

	public String getChkPrintKotForDirectBiller() {
		return chkPrintKotForDirectBiller;
	}

	public void setChkPrintKotForDirectBiller(String chkPrintKotForDirectBiller) {
		this.chkPrintKotForDirectBiller = chkPrintKotForDirectBiller;
	}

	public String getChkEnableKOT() {
		return chkEnableKOT;
	}

	public void setChkEnableKOT(String chkEnableKOT) {
		this.chkEnableKOT = chkEnableKOT;
	}

	public String getChkMultiBillPrint() {
		return chkMultiBillPrint;
	}

	public void setChkMultiBillPrint(String chkMultiBillPrint) {
		this.chkMultiBillPrint = chkMultiBillPrint;
	}

	public String getChkDayEnd() {
		return chkDayEnd;
	}

	public void setChkDayEnd(String chkDayEnd) {
		this.chkDayEnd = chkDayEnd;
	}

	public String getChkPrintShortNameOnKOT() {
		return chkPrintShortNameOnKOT;
	}

	public void setChkPrintShortNameOnKOT(String chkPrintShortNameOnKOT) {
		this.chkPrintShortNameOnKOT = chkPrintShortNameOnKOT;
	}

	public String getChkMultiKOTPrint() {
		return chkMultiKOTPrint;
	}

	public void setChkMultiKOTPrint(String chkMultiKOTPrint) {
		this.chkMultiKOTPrint = chkMultiKOTPrint;
	}

	public String getStrCustSeries() {
		return strCustSeries;
	}

	public void setStrCustSeries(String strCustSeries) {
		this.strCustSeries = strCustSeries;
	}

	public String getChkPrintInvoiceOnBill() {
		return chkPrintInvoiceOnBill;
	}

	public void setChkPrintInvoiceOnBill(String chkPrintInvoiceOnBill) {
		this.chkPrintInvoiceOnBill = chkPrintInvoiceOnBill;
	}

	public String getChkPrintTDHItemsInBill() {
		return chkPrintTDHItemsInBill;
	}

	public void setChkPrintTDHItemsInBill(String chkPrintTDHItemsInBill) {
		this.chkPrintTDHItemsInBill = chkPrintTDHItemsInBill;
	}

	public String getChkManualBillNo() {
		return chkManualBillNo;
	}

	public void setChkManualBillNo(String chkManualBillNo) {
		this.chkManualBillNo = chkManualBillNo;
	}

	public String getChkPrintInclusiveOfAllTaxesOnBill() {
		return chkPrintInclusiveOfAllTaxesOnBill;
	}

	public void setChkPrintInclusiveOfAllTaxesOnBill(String chkPrintInclusiveOfAllTaxesOnBill) {
		this.chkPrintInclusiveOfAllTaxesOnBill = chkPrintInclusiveOfAllTaxesOnBill;
	}

	public String getChkEffectOnPSP() {
		return chkEffectOnPSP;
	}

	public void setChkEffectOnPSP(String chkEffectOnPSP) {
		this.chkEffectOnPSP = chkEffectOnPSP;
	}

	public String getChkPrintVatNo() {
		return chkPrintVatNo;
	}

	public void setChkPrintVatNo(String chkPrintVatNo) {
		this.chkPrintVatNo = chkPrintVatNo;
	}

	public String getStrVatNo() {
		return strVatNo;
	}

	public void setStrVatNo(String strVatNo) {
		this.strVatNo = strVatNo;
	}

	public long getIntBiilPaperSize() {
		return intBiilPaperSize;
	}

	public void setIntBiilPaperSize(long intBiilPaperSize) {
		this.intBiilPaperSize = intBiilPaperSize;
	}

	public long getIntColumnSize() {
		return intColumnSize;
	}

	public void setIntColumnSize(long intColumnSize) {
		this.intColumnSize = intColumnSize;
	}

	public long getIntNoOfLinesInKOTPrint() {
		return intNoOfLinesInKOTPrint;
	}

	public void setIntNoOfLinesInKOTPrint(long intNoOfLinesInKOTPrint) {
		this.intNoOfLinesInKOTPrint = intNoOfLinesInKOTPrint;
	}

	public String getChkServiceTaxNo() {
		return chkServiceTaxNo;
	}

	public void setChkServiceTaxNo(String chkServiceTaxNo) {
		this.chkServiceTaxNo = chkServiceTaxNo;
	}

	public String getStrServiceTaxNo() {
		return strServiceTaxNo;
	}

	public long getDblMaxDiscount() {
		return dblMaxDiscount;
	}

	public void setDblMaxDiscount(long dblMaxDiscount) {
		this.dblMaxDiscount = dblMaxDiscount;
	}

	public void setStrServiceTaxNo(String strServiceTaxNo) {
		this.strServiceTaxNo = strServiceTaxNo;
	}

	public String getStrShowBillsDtlType() {
		return strShowBillsDtlType;
	}

	public void setStrShowBillsDtlType(String strShowBillsDtlType) {
		this.strShowBillsDtlType = strShowBillsDtlType;
	}

	public String getStrPOSType() {
		return strPOSType;
	}

	public void setStrPOSType(String strPOSType) {
		this.strPOSType = strPOSType;
	}

	public String getStrDataSendFrequency() {
		return strDataSendFrequency;
	}

	public void setStrDataSendFrequency(String strDataSendFrequency) {
		this.strDataSendFrequency = strDataSendFrequency;
	}

	public String getStrWebServiceLink() {
		return strWebServiceLink;
	}

	public void setStrWebServiceLink(String strWebServiceLink) {
		this.strWebServiceLink = strWebServiceLink;
	}

	public String getDteHOServerDate() {
		return dteHOServerDate;
	}

	public void setDteHOServerDate(String dteHOServerDate) {
		this.dteHOServerDate = dteHOServerDate;
	}

	public String getStrChangeTheme() {
		return strChangeTheme;
	}

	public void setStrChangeTheme(String strChangeTheme) {
		this.strChangeTheme = strChangeTheme;
	}

	public String getStrDirectArea() {
		return strDirectArea;
	}

	public void setStrDirectArea(String strDirectArea) {
		this.strDirectArea = strDirectArea;
	}

	public String getChkAreaWisePricing() {
		return chkAreaWisePricing;
	}

	public void setChkAreaWisePricing(String chkAreaWisePricing) {
		this.chkAreaWisePricing = chkAreaWisePricing;
	}

	public String getChkSlabBasedHomeDelCharges() {
		return chkSlabBasedHomeDelCharges;
	}

	public void setChkSlabBasedHomeDelCharges(String chkSlabBasedHomeDelCharges) {
		this.chkSlabBasedHomeDelCharges = chkSlabBasedHomeDelCharges;
	}

	public String getChkEditHomeDelivery() {
		return chkEditHomeDelivery;
	}

	public void setChkEditHomeDelivery(String chkEditHomeDelivery) {
		this.chkEditHomeDelivery = chkEditHomeDelivery;
	}

	public String getChkPrintForVoidBill() {
		return chkPrintForVoidBill;
	}

	public void setChkPrintForVoidBill(String chkPrintForVoidBill) {
		this.chkPrintForVoidBill = chkPrintForVoidBill;
	}

	public String getChkDirectKOTPrintMakeKOT() {
		return chkDirectKOTPrintMakeKOT;
	}

	public void setChkDirectKOTPrintMakeKOT(String chkDirectKOTPrintMakeKOT) {
		this.chkDirectKOTPrintMakeKOT = chkDirectKOTPrintMakeKOT;
	}

	public String getChkSkipWaiterSelection() {
		return chkSkipWaiterSelection;
	}

	public void setChkSkipWaiterSelection(String chkSkipWaiterSelection) {
		this.chkSkipWaiterSelection = chkSkipWaiterSelection;
	}

	public String getChkSkipPaxSelection() {
		return chkSkipPaxSelection;
	}

	public void setChkSkipPaxSelection(String chkSkipPaxSelection) {
		this.chkSkipPaxSelection = chkSkipPaxSelection;
	}

	public String getChkAreaMasterCompulsory() {
		return chkAreaMasterCompulsory;
	}

	public void setChkAreaMasterCompulsory(String chkAreaMasterCompulsory) {
		this.chkAreaMasterCompulsory = chkAreaMasterCompulsory;
	}

	public String getChkPostSalesDataToMMS() {
		return chkPostSalesDataToMMS;
	}

	public void setChkPostSalesDataToMMS(String chkPostSalesDataToMMS) {
		this.chkPostSalesDataToMMS = chkPostSalesDataToMMS;
	}

	public String getStrItemType() {
		return strItemType;
	}

	public void setStrItemType(String strItemType) {
		this.strItemType = strItemType;
	}

	public String getChkPrinterErrorMessage() {
		return chkPrinterErrorMessage;
	}

	public void setChkPrinterErrorMessage(String chkPrinterErrorMessage) {
		this.chkPrinterErrorMessage = chkPrinterErrorMessage;
	}

	public String getChkActivePromotions() {
		return chkActivePromotions;
	}

	public void setChkActivePromotions(String chkActivePromotions) {
		this.chkActivePromotions = chkActivePromotions;
	}

	public String getChkPrintKOTYN() {
		return chkPrintKOTYN;
	}

	public void setChkPrintKOTYN(String chkPrintKOTYN) {
		this.chkPrintKOTYN = chkPrintKOTYN;
	}

	public String getChkChangeQtyForExternalCode() {
		return chkChangeQtyForExternalCode;
	}

	public void setChkChangeQtyForExternalCode(String chkChangeQtyForExternalCode) {
		this.chkChangeQtyForExternalCode = chkChangeQtyForExternalCode;
	}

	public String getStrStockInOption() {
		return strStockInOption;
	}

	public void setStrStockInOption(String strStockInOption) {
		this.strStockInOption = strStockInOption;
	}

	public String getChkShowItemStkColumnInDB() {
		return chkShowItemStkColumnInDB;
	}

	public void setChkShowItemStkColumnInDB(String chkShowItemStkColumnInDB) {
		this.chkShowItemStkColumnInDB = chkShowItemStkColumnInDB;
	}

	public String getStrPriceFrom() {
		return strPriceFrom;
	}

	public void setStrPriceFrom(String strPriceFrom) {
		this.strPriceFrom = strPriceFrom;
	}

	public String getChkPrintBill() {
		return chkPrintBill;
	}

	public void setChkPrintBill(String chkPrintBill) {
		this.chkPrintBill = chkPrintBill;
	}

	public String getStrApplyDiscountOn() {
		return strApplyDiscountOn;
	}

	public void setStrApplyDiscountOn(String strApplyDiscountOn) {
		this.strApplyDiscountOn = strApplyDiscountOn;
	}

	public String getChkUseVatAndServiceNoFromPos() {
		return chkUseVatAndServiceNoFromPos;
	}

	public void setChkUseVatAndServiceNoFromPos(String chkUseVatAndServiceNoFromPos) {
		this.chkUseVatAndServiceNoFromPos = chkUseVatAndServiceNoFromPos;
	}

	public String getChkManualAdvOrderCompulsory() {
		return chkManualAdvOrderCompulsory;
	}

	public void setChkManualAdvOrderCompulsory(String chkManualAdvOrderCompulsory) {
		this.chkManualAdvOrderCompulsory = chkManualAdvOrderCompulsory;
	}

	public String getChkPrintManualAdvOrderOnBill() {
		return chkPrintManualAdvOrderOnBill;
	}

	public void setChkPrintManualAdvOrderOnBill(String chkPrintManualAdvOrderOnBill) {
		this.chkPrintManualAdvOrderOnBill = chkPrintManualAdvOrderOnBill;
	}

	public String getChkPrintModifierQtyOnKOT() {
		return chkPrintModifierQtyOnKOT;
	}

	public void setChkPrintModifierQtyOnKOT(String chkPrintModifierQtyOnKOT) {
		this.chkPrintModifierQtyOnKOT = chkPrintModifierQtyOnKOT;
	}

	public String getChkBoxAllowNewAreaMasterFromCustMaster() {
		return chkBoxAllowNewAreaMasterFromCustMaster;
	}

	public void setChkBoxAllowNewAreaMasterFromCustMaster(String chkBoxAllowNewAreaMasterFromCustMaster) {
		this.chkBoxAllowNewAreaMasterFromCustMaster = chkBoxAllowNewAreaMasterFromCustMaster;
	}

	public String getStrMenuItemSortingOn() {
		return strMenuItemSortingOn;
	}

	public void setStrMenuItemSortingOn(String strMenuItemSortingOn) {
		this.strMenuItemSortingOn = strMenuItemSortingOn;
	}

	public String getChkAllowToCalculateItemWeight() {
		return chkAllowToCalculateItemWeight;
	}

	public void setChkAllowToCalculateItemWeight(String chkAllowToCalculateItemWeight) {
		this.chkAllowToCalculateItemWeight = chkAllowToCalculateItemWeight;
	}

	public String getStrMenuItemDisSeq() {
		return strMenuItemDisSeq;
	}

	public void setStrMenuItemDisSeq(String strMenuItemDisSeq) {
		this.strMenuItemDisSeq = strMenuItemDisSeq;
	}

	public String getChkItemWiseKOTPrintYN() {
		return chkItemWiseKOTPrintYN;
	}

	public void setChkItemWiseKOTPrintYN(String chkItemWiseKOTPrintYN) {
		this.chkItemWiseKOTPrintYN = chkItemWiseKOTPrintYN;
	}

	public String getChkItemQtyNumpad() {
		return chkItemQtyNumpad;
	}

	public void setChkItemQtyNumpad(String chkItemQtyNumpad) {
		this.chkItemQtyNumpad = chkItemQtyNumpad;
	}

	public String getChkSlipNoForCreditCardBillYN() {
		return chkSlipNoForCreditCardBillYN;
	}

	public void setChkSlipNoForCreditCardBillYN(String chkSlipNoForCreditCardBillYN) {
		this.chkSlipNoForCreditCardBillYN = chkSlipNoForCreditCardBillYN;
	}

	public String getChkPrintKOTToLocalPrinter() {
		return chkPrintKOTToLocalPrinter;
	}

	public void setChkPrintKOTToLocalPrinter(String chkPrintKOTToLocalPrinter) {
		this.chkPrintKOTToLocalPrinter = chkPrintKOTToLocalPrinter;
	}

	public String getChkExpDateForCreditCardBillYN() {
		return chkExpDateForCreditCardBillYN;
	}

	public void setChkExpDateForCreditCardBillYN(String chkExpDateForCreditCardBillYN) {
		this.chkExpDateForCreditCardBillYN = chkExpDateForCreditCardBillYN;
	}

	public String getChkDelBoyCompulsoryOnDirectBiller() {
		return chkDelBoyCompulsoryOnDirectBiller;
	}

	public void setChkDelBoyCompulsoryOnDirectBiller(String chkDelBoyCompulsoryOnDirectBiller) {
		this.chkDelBoyCompulsoryOnDirectBiller = chkDelBoyCompulsoryOnDirectBiller;
	}

	public String getChkSelectWaiterFromCardSwipe() {
		return chkSelectWaiterFromCardSwipe;
	}

	public void setChkSelectWaiterFromCardSwipe(String chkSelectWaiterFromCardSwipe) {
		this.chkSelectWaiterFromCardSwipe = chkSelectWaiterFromCardSwipe;
	}

	public String getChkEnableSettleBtnForDirectBillerBill() {
		return chkEnableSettleBtnForDirectBillerBill;
	}

	public void setChkEnableSettleBtnForDirectBillerBill(String chkEnableSettleBtnForDirectBillerBill) {
		this.chkEnableSettleBtnForDirectBillerBill = chkEnableSettleBtnForDirectBillerBill;
	}

	public String getChkMultipleWaiterSelectionOnMakeKOT() {
		return chkMultipleWaiterSelectionOnMakeKOT;
	}

	public void setChkMultipleWaiterSelectionOnMakeKOT(String chkMultipleWaiterSelectionOnMakeKOT) {
		this.chkMultipleWaiterSelectionOnMakeKOT = chkMultipleWaiterSelectionOnMakeKOT;
	}

	public String getChkDontShowAdvOrderInOtherPOS() {
		return chkDontShowAdvOrderInOtherPOS;
	}

	public void setChkDontShowAdvOrderInOtherPOS(String chkDontShowAdvOrderInOtherPOS) {
		this.chkDontShowAdvOrderInOtherPOS = chkDontShowAdvOrderInOtherPOS;
	}

	public String getChkMoveTableToOtherPOS() {
		return chkMoveTableToOtherPOS;
	}

	public void setChkMoveTableToOtherPOS(String chkMoveTableToOtherPOS) {
		this.chkMoveTableToOtherPOS = chkMoveTableToOtherPOS;
	}

	public String getChkPrintZeroAmtModifierInBill() {
		return chkPrintZeroAmtModifierInBill;
	}

	public void setChkPrintZeroAmtModifierInBill(String chkPrintZeroAmtModifierInBill) {
		this.chkPrintZeroAmtModifierInBill = chkPrintZeroAmtModifierInBill;
	}

	public String getChkMoveKOTToOtherPOS() {
		return chkMoveKOTToOtherPOS;
	}

	public void setChkMoveKOTToOtherPOS(String chkMoveKOTToOtherPOS) {
		this.chkMoveKOTToOtherPOS = chkMoveKOTToOtherPOS;
	}

	public String getChkPointsOnBillPrint() {
		return chkPointsOnBillPrint;
	}

	public void setChkPointsOnBillPrint(String chkPointsOnBillPrint) {
		this.chkPointsOnBillPrint = chkPointsOnBillPrint;
	}

	public String getChkCalculateTaxOnMakeKOT() {
		return chkCalculateTaxOnMakeKOT;
	}

	public void setChkCalculateTaxOnMakeKOT(String chkCalculateTaxOnMakeKOT) {
		this.chkCalculateTaxOnMakeKOT = chkCalculateTaxOnMakeKOT;
	}

	public String getChkCalculateDiscItemWise() {
		return chkCalculateDiscItemWise;
	}

	public void setChkCalculateDiscItemWise(String chkCalculateDiscItemWise) {
		this.chkCalculateDiscItemWise = chkCalculateDiscItemWise;
	}

	public String getChkTakewayCustomerSelection() {
		return chkTakewayCustomerSelection;
	}

	public void setChkTakewayCustomerSelection(String chkTakewayCustomerSelection) {
		this.chkTakewayCustomerSelection = chkTakewayCustomerSelection;
	}

	public String getChkSelectCustAddressForBill() {
		return chkSelectCustAddressForBill;
	}

	public void setChkSelectCustAddressForBill(String chkSelectCustAddressForBill) {
		this.chkSelectCustAddressForBill = chkSelectCustAddressForBill;
	}

	public String getChkGenrateMI() {
		return chkGenrateMI;
	}

	public void setChkGenrateMI(String chkGenrateMI) {
		this.chkGenrateMI = chkGenrateMI;
	}

	public String getChkPopUpToApplyPromotionsOnBill() {
		return chkPopUpToApplyPromotionsOnBill;
	}

	public void setChkPopUpToApplyPromotionsOnBill(String chkPopUpToApplyPromotionsOnBill) {
		this.chkPopUpToApplyPromotionsOnBill = chkPopUpToApplyPromotionsOnBill;
	}

	public String getStrWSClientCode() {
		return strWSClientCode;
	}

	public void setStrWSClientCode(String strWSClientCode) {
		this.strWSClientCode = strWSClientCode;
	}

	public String getChkCheckDebitCardBalOnTrans() {
		return chkCheckDebitCardBalOnTrans;
	}

	public void setChkCheckDebitCardBalOnTrans(String chkCheckDebitCardBalOnTrans) {
		this.chkCheckDebitCardBalOnTrans = chkCheckDebitCardBalOnTrans;
	}

	public long getIntDaysBeforeOrderToCancel() {
		return intDaysBeforeOrderToCancel;
	}

	public void setIntDaysBeforeOrderToCancel(long intDaysBeforeOrderToCancel) {
		this.intDaysBeforeOrderToCancel = intDaysBeforeOrderToCancel;
	}

	public String getChkSettlementsFromPOSMaster() {
		return chkSettlementsFromPOSMaster;
	}

	public void setChkSettlementsFromPOSMaster(String chkSettlementsFromPOSMaster) {
		this.chkSettlementsFromPOSMaster = chkSettlementsFromPOSMaster;
	}

	public long getIntNoOfDelDaysForAdvOrder() {
		return intNoOfDelDaysForAdvOrder;
	}

	public void setIntNoOfDelDaysForAdvOrder(long intNoOfDelDaysForAdvOrder) {
		this.intNoOfDelDaysForAdvOrder = intNoOfDelDaysForAdvOrder;
	}

	public String getChkShiftWiseDayEnd() {
		return chkShiftWiseDayEnd;
	}

	public void setChkShiftWiseDayEnd(String chkShiftWiseDayEnd) {
		this.chkShiftWiseDayEnd = chkShiftWiseDayEnd;
	}

	public long getIntNoOfDelDaysForUrgentOrder() {
		return intNoOfDelDaysForUrgentOrder;
	}

	public void setIntNoOfDelDaysForUrgentOrder(long intNoOfDelDaysForUrgentOrder) {
		this.intNoOfDelDaysForUrgentOrder = intNoOfDelDaysForUrgentOrder;
	}

	public String getChkProductionLinkup() {
		return chkProductionLinkup;
	}

	public void setChkProductionLinkup(String chkProductionLinkup) {
		this.chkProductionLinkup = chkProductionLinkup;
	}

	public String getChkLockDataOnShift() {
		return chkLockDataOnShift;
	}

	public void setChkLockDataOnShift(String chkLockDataOnShift) {
		this.chkLockDataOnShift = chkLockDataOnShift;
	}

	public String getChkSetUpToTimeForAdvOrder() {
		return chkSetUpToTimeForAdvOrder;
	}

	public void setChkSetUpToTimeForAdvOrder(String chkSetUpToTimeForAdvOrder) {
		this.chkSetUpToTimeForAdvOrder = chkSetUpToTimeForAdvOrder;
	}

	public String getChkEnableBillSeries() {
		return chkEnableBillSeries;
	}

	public void setChkEnableBillSeries(String chkEnableBillSeries) {
		this.chkEnableBillSeries = chkEnableBillSeries;
	}

	public String getStrHours() {
		return strHours;
	}

	public void setStrHours(String strHours) {
		this.strHours = strHours;
	}

	public String getStrMinutes() {
		return strMinutes;
	}

	public void setStrMinutes(String strMinutes) {
		this.strMinutes = strMinutes;
	}

	public String getStrAMPM() {
		return strAMPM;
	}

	public void setStrAMPM(String strAMPM) {
		this.strAMPM = strAMPM;
	}

	public String getChkEnablePMSIntegration() {
		return chkEnablePMSIntegration;
	}

	public void setChkEnablePMSIntegration(String chkEnablePMSIntegration) {
		this.chkEnablePMSIntegration = chkEnablePMSIntegration;
	}

	public String getChkSetUpToTimeForUrgentOrder() {
		return chkSetUpToTimeForUrgentOrder;
	}

	public void setChkSetUpToTimeForUrgentOrder(String chkSetUpToTimeForUrgentOrder) {
		this.chkSetUpToTimeForUrgentOrder = chkSetUpToTimeForUrgentOrder;
	}

	public String getChkPrintTimeOnBill() {
		return chkPrintTimeOnBill;
	}

	public void setChkPrintTimeOnBill(String chkPrintTimeOnBill) {
		this.chkPrintTimeOnBill = chkPrintTimeOnBill;
	}

	public String getStrHoursUrgentOrder() {
		return strHoursUrgentOrder;
	}

	public void setStrHoursUrgentOrder(String strHoursUrgentOrder) {
		this.strHoursUrgentOrder = strHoursUrgentOrder;
	}

	public String getStrMinutesUrgentOrder() {
		return strMinutesUrgentOrder;
	}

	public void setStrMinutesUrgentOrder(String strMinutesUrgentOrder) {
		this.strMinutesUrgentOrder = strMinutesUrgentOrder;
	}

	public String getStrAMPMUrgent() {
		return strAMPMUrgent;
	}

	public void setStrAMPMUrgent(String strAMPMUrgent) {
		this.strAMPMUrgent = strAMPMUrgent;
	}

	public String getChkPrintRemarkAndReasonForReprint() {
		return chkPrintRemarkAndReasonForReprint;
	}

	public void setChkPrintRemarkAndReasonForReprint(String chkPrintRemarkAndReasonForReprint) {
		this.chkPrintRemarkAndReasonForReprint = chkPrintRemarkAndReasonForReprint;
	}

	public String getChkEnableBothPrintAndSettleBtnForDB() {
		return chkEnableBothPrintAndSettleBtnForDB;
	}

	public void setChkEnableBothPrintAndSettleBtnForDB(String chkEnableBothPrintAndSettleBtnForDB) {
		this.chkEnableBothPrintAndSettleBtnForDB = chkEnableBothPrintAndSettleBtnForDB;
	}

	public String getChkCarryForwardFloatAmtToNextDay() {
		return chkCarryForwardFloatAmtToNextDay;
	}

	public void setChkCarryForwardFloatAmtToNextDay(String chkCarryForwardFloatAmtToNextDay) {
		this.chkCarryForwardFloatAmtToNextDay = chkCarryForwardFloatAmtToNextDay;
	}

	public String getChkOpenCashDrawerAfterBillPrint() {
		return chkOpenCashDrawerAfterBillPrint;
	}

	public void setChkOpenCashDrawerAfterBillPrint(String chkOpenCashDrawerAfterBillPrint) {
		this.chkOpenCashDrawerAfterBillPrint = chkOpenCashDrawerAfterBillPrint;
	}

	public String getChkShowItemDtlsForChangeCustomerOnBill() {
		return chkShowItemDtlsForChangeCustomerOnBill;
	}

	public void setChkShowItemDtlsForChangeCustomerOnBill(String chkShowItemDtlsForChangeCustomerOnBill) {
		this.chkShowItemDtlsForChangeCustomerOnBill = chkShowItemDtlsForChangeCustomerOnBill;
	}

	public String getChkPropertyWiseSalesOrder() {
		return chkPropertyWiseSalesOrder;
	}

	public void setChkPropertyWiseSalesOrder(String chkPropertyWiseSalesOrder) {
		this.chkPropertyWiseSalesOrder = chkPropertyWiseSalesOrder;
	}

	public String getChkShowPopUpForNextItemQuantity() {
		return chkShowPopUpForNextItemQuantity;
	}

	public void setChkShowPopUpForNextItemQuantity(String chkShowPopUpForNextItemQuantity) {
		this.chkShowPopUpForNextItemQuantity = chkShowPopUpForNextItemQuantity;
	}

	public String getStrSenderEmailId() {
		return strSenderEmailId;
	}

	public void setStrSenderEmailId(String strSenderEmailId) {
		this.strSenderEmailId = strSenderEmailId;
	}

	public String getStrEmailPassword() {
		return strEmailPassword;
	}

	public void setStrEmailPassword(String strEmailPassword) {
		this.strEmailPassword = strEmailPassword;
	}

	public String getStrEmailServerName() {
		return strEmailServerName;
	}

	public void setStrEmailServerName(String strEmailServerName) {
		this.strEmailServerName = strEmailServerName;
	}

	public String getStrReceiverEmailId() {
		return strReceiverEmailId;
	}

	public void setStrReceiverEmailId(String strReceiverEmailId) {
		this.strReceiverEmailId = strReceiverEmailId;
	}

	public String getStrBodyPart() {
		return strBodyPart;
	}

	public void setStrBodyPart(String strBodyPart) {
		this.strBodyPart = strBodyPart;
	}

	public String getStrCardIntfType() {
		return strCardIntfType;
	}

	public void setStrCardIntfType(String strCardIntfType) {
		this.strCardIntfType = strCardIntfType;
	}

	public String getStrRFIDSetup() {
		return strRFIDSetup;
	}

	public void setStrRFIDSetup(String strRFIDSetup) {
		this.strRFIDSetup = strRFIDSetup;
	}

	public String getStrRFIDServerName() {
		return strRFIDServerName;
	}

	public void setStrRFIDServerName(String strRFIDServerName) {
		this.strRFIDServerName = strRFIDServerName;
	}

	public String getStrRFIDUserName() {
		return strRFIDUserName;
	}

	public void setStrRFIDUserName(String strRFIDUserName) {
		this.strRFIDUserName = strRFIDUserName;
	}

	public String getStrRFIDPassword() {
		return strRFIDPassword;
	}

	public void setStrRFIDPassword(String strRFIDPassword) {
		this.strRFIDPassword = strRFIDPassword;
	}

	public String getStrRFIDDatabaseName() {
		return strRFIDDatabaseName;
	}

	public void setStrRFIDDatabaseName(String strRFIDDatabaseName) {
		this.strRFIDDatabaseName = strRFIDDatabaseName;
	}

	public String getStrCRM() {
		return strCRM;
	}

	public void setStrCRM(String strCRM) {
		this.strCRM = strCRM;
	}

	public String getStrGetWebservice() {
		return strGetWebservice;
	}

	public void setStrGetWebservice(String strGetWebservice) {
		this.strGetWebservice = strGetWebservice;
	}

	public String getStrPostWebservice() {
		return strPostWebservice;
	}

	public void setStrPostWebservice(String strPostWebservice) {
		this.strPostWebservice = strPostWebservice;
	}

	public String getStrOutletUID() {
		return strOutletUID;
	}

	public void setStrOutletUID(String strOutletUID) {
		this.strOutletUID = strOutletUID;
	}

	public String getStrPOSID() {
		return strPOSID;
	}

	public void setStrPOSID(String strPOSID) {
		this.strPOSID = strPOSID;
	}

	public String getStrSMSType() {
		return strSMSType;
	}

	public void setStrSMSType(String strSMSType) {
		this.strSMSType = strSMSType;
	}

	public String getStrAreaSMSApi() {
		return strAreaSMSApi;
	}

	public void setStrAreaSMSApi(String strAreaSMSApi) {
		this.strAreaSMSApi = strAreaSMSApi;
	}

	public String getChkHomeDelSMS() {
		return chkHomeDelSMS;
	}

	public void setChkHomeDelSMS(String chkHomeDelSMS) {
		this.chkHomeDelSMS = chkHomeDelSMS;
	}

	public String getStrAreaSendHomeDeliverySMS() {
		return strAreaSendHomeDeliverySMS;
	}

	public void setStrAreaSendHomeDeliverySMS(String strAreaSendHomeDeliverySMS) {
		this.strAreaSendHomeDeliverySMS = strAreaSendHomeDeliverySMS;
	}

	public String getChkBillSettlementSMS() {
		return chkBillSettlementSMS;
	}

	public void setChkBillSettlementSMS(String chkBillSettlementSMS) {
		this.chkBillSettlementSMS = chkBillSettlementSMS;
	}

	public String getStrBillSeriesType() {
		return strBillSeriesType;
	}

	public void setStrBillSeriesType(String strBillSeriesType) {
		this.strBillSeriesType = strBillSeriesType;
	}

	public String getStrAreaBillSettlementSMS() {
		return strAreaBillSettlementSMS;
	}

	public void setStrAreaBillSettlementSMS(String strAreaBillSettlementSMS) {
		this.strAreaBillSettlementSMS = strAreaBillSettlementSMS;
	}

	public String getStrFTPAddress() {
		return strFTPAddress;
	}

	public void setStrFTPAddress(String strFTPAddress) {
		this.strFTPAddress = strFTPAddress;
	}

	public String getStrFTPServerUserName() {
		return strFTPServerUserName;
	}

	public void setStrFTPServerUserName(String strFTPServerUserName) {
		this.strFTPServerUserName = strFTPServerUserName;
	}

	public String getStrFTPServerPass() {
		return strFTPServerPass;
	}

	public void setStrFTPServerPass(String strFTPServerPass) {
		this.strFTPServerPass = strFTPServerPass;
	}

	public String getStrCMSIntegrationYN() {
		return strCMSIntegrationYN;
	}

	public void setStrCMSIntegrationYN(String strCMSIntegrationYN) {
		this.strCMSIntegrationYN = strCMSIntegrationYN;
	}

	public String getStrCMSWesServiceURL() {
		return strCMSWesServiceURL;
	}

	public void setStrCMSWesServiceURL(String strCMSWesServiceURL) {
		this.strCMSWesServiceURL = strCMSWesServiceURL;
	}

	public String getChkMemberAsTable() {
		return chkMemberAsTable;
	}

	public void setChkMemberAsTable(String chkMemberAsTable) {
		this.chkMemberAsTable = chkMemberAsTable;
	}

	public String getChkMemberCodeForKOTJPOS() {
		return chkMemberCodeForKOTJPOS;
	}

	public void setChkMemberCodeForKOTJPOS(String chkMemberCodeForKOTJPOS) {
		this.chkMemberCodeForKOTJPOS = chkMemberCodeForKOTJPOS;
	}

	public String getChkMemberCodeForKotInMposByCardSwipe() {
		return chkMemberCodeForKotInMposByCardSwipe;
	}

	public void setChkMemberCodeForKotInMposByCardSwipe(String chkMemberCodeForKotInMposByCardSwipe) {
		this.chkMemberCodeForKotInMposByCardSwipe = chkMemberCodeForKotInMposByCardSwipe;
	}

	public String getChkMemberCodeForMakeBillInMPOS() {
		return chkMemberCodeForMakeBillInMPOS;
	}

	public void setChkMemberCodeForMakeBillInMPOS(String chkMemberCodeForMakeBillInMPOS) {
		this.chkMemberCodeForMakeBillInMPOS = chkMemberCodeForMakeBillInMPOS;
	}

	public String getChkMemberCodeForKOTMPOS() {
		return chkMemberCodeForKOTMPOS;
	}

	public void setChkMemberCodeForKOTMPOS(String chkMemberCodeForKOTMPOS) {
		this.chkMemberCodeForKOTMPOS = chkMemberCodeForKOTMPOS;
	}

	public String getChkSelectCustomerCodeFromCardSwipe() {
		return chkSelectCustomerCodeFromCardSwipe;
	}

	public void setChkSelectCustomerCodeFromCardSwipe(String chkSelectCustomerCodeFromCardSwipe) {
		this.chkSelectCustomerCodeFromCardSwipe = chkSelectCustomerCodeFromCardSwipe;
	}

	public String getStrCMSPostingType() {
		return strCMSPostingType;
	}

	public void setStrCMSPostingType(String strCMSPostingType) {
		this.strCMSPostingType = strCMSPostingType;
	}

	public String getStrPOSForDayEnd() {
		return strPOSForDayEnd;
	}

	public void setStrPOSForDayEnd(String strPOSForDayEnd) {
		this.strPOSForDayEnd = strPOSForDayEnd;
	}

	public String getStrInrestoPOSIntegrationYN() {
		return strInrestoPOSIntegrationYN;
	}

	public void setStrInrestoPOSIntegrationYN(String strInrestoPOSIntegrationYN) {
		this.strInrestoPOSIntegrationYN = strInrestoPOSIntegrationYN;
	}

	public String getStrInrestoPOSWesServiceURL() {
		return strInrestoPOSWesServiceURL;
	}

	public void setStrInrestoPOSWesServiceURL(String strInrestoPOSWesServiceURL) {
		this.strInrestoPOSWesServiceURL = strInrestoPOSWesServiceURL;
	}

	public String getStrInrestoPOSId() {
		return strInrestoPOSId;
	}

	public void setStrInrestoPOSId(String strInrestoPOSId) {
		this.strInrestoPOSId = strInrestoPOSId;
	}

	public String getStrInrestoPOSKey() {
		return strInrestoPOSKey;
	}

	public void setStrInrestoPOSKey(String strInrestoPOSKey) {
		this.strInrestoPOSKey = strInrestoPOSKey;
	}

	public String getStrJioPOSIntegrationYN() {
		return strJioPOSIntegrationYN;
	}

	public void setStrJioPOSIntegrationYN(String strJioPOSIntegrationYN) {
		this.strJioPOSIntegrationYN = strJioPOSIntegrationYN;
	}

	public String getStrJioPOSWesServiceURL() {
		return strJioPOSWesServiceURL;
	}

	public void setStrJioPOSWesServiceURL(String strJioPOSWesServiceURL) {
		this.strJioPOSWesServiceURL = strJioPOSWesServiceURL;
	}

	public List<clsBillSeriesDtlBean> getListBillSeriesDtl() {
		return listBillSeriesDtl;
	}

	public void setListBillSeriesDtl(List<clsBillSeriesDtlBean> listBillSeriesDtl) {
		this.listBillSeriesDtl = listBillSeriesDtl;
	}

	public String getStrJioMID() {
		return strJioMID;
	}

	public void setStrJioMID(String strJioMID) {
		this.strJioMID = strJioMID;
	}

	public String getStrJioTID() {
		return strJioTID;
	}

	public void setStrJioTID(String strJioTID) {
		this.strJioTID = strJioTID;
	}

	public String getStrJioActivationCode() {
		return strJioActivationCode;
	}

	public void setStrJioActivationCode(String strJioActivationCode) {
		this.strJioActivationCode = strJioActivationCode;
	}

	public String getStrJioDeviceID() {
		return strJioDeviceID;
	}

	public void setStrJioDeviceID(String strJioDeviceID) {
		this.strJioDeviceID = strJioDeviceID;
	}

	public String getChkNewBillSeriesForNewDay() {
		return chkNewBillSeriesForNewDay;
	}

	public void setChkNewBillSeriesForNewDay(String chkNewBillSeriesForNewDay) {
		this.chkNewBillSeriesForNewDay = chkNewBillSeriesForNewDay;
	}

	public String getChkShowReportsPOSWise() {
		return chkShowReportsPOSWise;
	}

	public void setChkShowReportsPOSWise(String chkShowReportsPOSWise) {
		this.chkShowReportsPOSWise = chkShowReportsPOSWise;
	}

	public List<clsPrinterSetupBean> getListPrinterDtl() {
		return listPrinterDtl;
	}

	public void setListPrinterDtl(List<clsPrinterSetupBean> listPrinterDtl) {
		this.listPrinterDtl = listPrinterDtl;
	}

	public String getChkEnableDineIn() {
		return chkEnableDineIn;
	}

	public void setChkEnableDineIn(String chkEnableDineIn) {
		this.chkEnableDineIn = chkEnableDineIn;
	}

	public String getChkAutoAreaSelectionInMakeKOT() {
		return chkAutoAreaSelectionInMakeKOT;
	}

	public void setChkAutoAreaSelectionInMakeKOT(String chkAutoAreaSelectionInMakeKOT) {
		this.chkAutoAreaSelectionInMakeKOT = chkAutoAreaSelectionInMakeKOT;
	}

}
