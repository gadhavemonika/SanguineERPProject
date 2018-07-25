package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsBankMasterBean;
import com.sanguine.webbooks.bean.clsParameterSetupBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.model.clsParameterSetupModel;
import com.sanguine.webbooks.model.clsParameterSetupModel_ID;
import com.sanguine.webbooks.service.clsParameterSetupService;

@Controller
public class clsParameterSetupController {

	@Autowired
	private clsParameterSetupService objParameterSetupService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ParameterSetup
	// Open BankMaster
	@RequestMapping(value = "/frmParameterSetup", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ArrayList<String> listInvoiceBasedOn = new ArrayList<String>();
		listInvoiceBasedOn.add("Voucher Date");
		listInvoiceBasedOn.add("Due Date");

		ArrayList<String> listTAXIndicatorInTrans = new ArrayList<String>();
		listTAXIndicatorInTrans.add("Yes");
		listTAXIndicatorInTrans.add("No");

		ArrayList<String> listSelectParameters = new ArrayList<String>();
		listSelectParameters.add("System Date 10 Characters");
		listSelectParameters.add("Transaction Date 10 Characters");
		listSelectParameters.add("Property Code 10 Characters");
		listSelectParameters.add("User Code 10 Characters");

		model.put("urlHits", urlHits);
		model.put("listInvoiceBasedOn", listInvoiceBasedOn);
		model.put("listTAXIndicatorInTrans", listTAXIndicatorInTrans);
		model.put("listSelectParameters", listSelectParameters);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmParameterSetup", "command", new clsParameterSetupBean());
		} else {
			return new ModelAndView("frmParameterSetup_1", "command", new clsParameterSetupBean());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadParameterSetupData", method = RequestMethod.GET)
	public @ResponseBody clsParameterSetupModel funAssignFields(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();

		clsParameterSetupModel objModel = objParameterSetupService.funGetParameterSetup(clientCode, propCode);
		if (null == objModel) {
			objModel = new clsParameterSetupModel();
		}

		return objModel;
	}

	@RequestMapping(value = "/saveParameterSetup", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsParameterSetupBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsParameterSetupModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objParameterSetupService.funAddUpdateParameterSetup(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Parameter Setup");

			return new ModelAndView("redirect:/frmParameterSetup.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmParameterSetup.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsParameterSetupModel funPrepareModel(clsParameterSetupBean objBean, String userCode, String clientCode, String propCode) {
		objGlobal = new clsGlobalFunctions();
		clsParameterSetupModel objModel = new clsParameterSetupModel(new clsParameterSetupModel_ID(clientCode, propCode));

		objModel.setStrAcctNarrJv(objBean.getStrAcctNarrJv());
		objModel.setStrAcctNarrPay(objBean.getStrAcctNarrPay());
		objModel.setStrAcctNarrRec(objBean.getStrAcctNarrRec());
		objModel.setStrAdAgeLimit(objBean.getStrAdAgeLimit());
		objModel.setStrAdultAgeLimit(objBean.getStrAdultAgeLimit());
		objModel.setStrAdultGuest(objBean.getStrAdultGuest());
		objModel.setStrAdultMember(objBean.getStrAdultMember());
		objModel.setStrAdvanceACCode(objBean.getStrAdvanceACCode());
		objModel.setStrAdvanceAcct(objBean.getStrAdvanceAcct());
		objModel.setStrAIMS(objBean.getStrAIMS());
		objModel.setStrAmadeusInterface(objBean.getStrAmadeusInterface());
		objModel.setStrApDebtorReceiptEntry(objBean.getStrApDebtorReceiptEntry());
		objModel.setStrAPECSBankCode(objBean.getStrAPECSBankCode());
		objModel.setStrAPJVEntry(objBean.getStrAPJVEntry());
		objModel.setStrAPPaymentEntry(objBean.getStrAPPaymentEntry());
		objModel.setStrAPReceiptEntry(objBean.getStrAPReceiptEntry());
		objModel.setStrAutoGenCode(objBean.getStrAutoGenCode());
		objModel.setStrBillableCode(objBean.getStrBillableCode());
		objModel.setStrBillableName(objBean.getStrBillableName());
		objModel.setStrBillPrefix(objBean.getStrBillPrefix());
		objModel.setStrCashFlowCode(objBean.getStrCashFlowCode());
		objModel.setStrCategoryCode(objBean.getStrCategoryCode());
		objModel.setStrChildGuest(objBean.getStrChildGuest());
		objModel.setStrChildMember(objBean.getStrChildMember());
		objModel.setStrclientid(objBean.getStrclientid());
		objModel.setStrControlCode(objBean.getStrControlCode());
		objModel.setStrControlName(objBean.getStrControlName());
		objModel.setStrCreditLimit(objBean.getStrCreditLimit());
		objModel.setStrCreditorControlAccount(objBean.getStrCreditorControlAccount());
		objModel.setStrCreditorLedgerAccount(objBean.getStrCreditorLedgerAccount());
		objModel.setStrCRM(objBean.getStrCRM());
		objModel.setStrCurrencyCode(objBean.getStrCurrencyCode());
		objModel.setStrCurrencyDesc(objBean.getStrCurrencyDesc());
		objModel.setStrCustImgPath(objBean.getStrCustImgPath());
		objModel.setStrDatabase(objBean.getStrDatabase());
		objModel.setStrDbServer(objBean.getStrDbServer());
		objModel.setStrDbtRoomACCode(objBean.getStrDbtRoomACCode());
		objModel.setStrDbtRoomACName(objBean.getStrDbtRoomACName());
		objModel.setStrDbtRoomAdvCode(objBean.getStrDbtRoomAdvCode());
		objModel.setStrDbtRoomAdvName(objBean.getStrDbtRoomAdvName());
		objModel.setStrDbtrSuspAcctCode(objBean.getStrDbtrSuspAcctCode());
		objModel.setStrDbtrSuspAcctName(objBean.getStrDbtrSuspAcctName());
		objModel.setStrDeborLedgerAccount(objBean.getStrDeborLedgerAccount());
		objModel.setStrDebtorLedgerACCode(objBean.getStrDebtorLedgerACCode());
		objModel.setStrDebtorLedgerACName(objBean.getStrDebtorLedgerACName());
		objModel.setStrDebtorAck(objBean.getStrDebtorAck());
		objModel.setStrDebtorNarrJv(objBean.getStrDebtorNarrJv());
		objModel.setStrDebtorNarrPay(objBean.getStrDebtorNarrPay());
		objModel.setStrDebtorNarrRec(objBean.getStrDebtorNarrRec());
		objModel.setStrDebtorPreProfiling(objBean.getStrDebtorPreProfiling());
		objModel.setStrECSBankcode(objBean.getStrECSBankcode());
		objModel.setStrECSBankName(objBean.getStrECSBankName());
		objModel.setStrEcsLetterCode(objBean.getStrEcsLetterCode());
		objModel.setStrEmailBcc(objBean.getStrEmailBcc());
		objModel.setStrEmailCc(objBean.getStrEmailCc());
		objModel.setStrEmailFrom(objBean.getStrEmailFrom());
		objModel.setStrEmailSMTPPort(objBean.getStrEmailSMTPPort());
		objModel.setStrEmailSmtpServer(objBean.getStrEmailSmtpServer());
		objModel.setStrExportType(objBean.getStrExportType());
		objModel.setStrGolfFac(objBean.getStrGolfFac());
		objModel.setStrGroupCode(objBean.getStrGroupCode());
		objModel.setStrIntegrityChk(objBean.getStrIntegrityChk());
		objModel.setStrInvoiceBasedOn(objBean.getStrInvoiceBasedOn());
		objModel.setStrInvoiceNarrRec(objBean.getStrInvoiceNarrRec());
		objModel.setStrInvoicerAdvCode(objBean.getStrInvoicerAdvCode());
		objModel.setStrInvoicerAdvName(objBean.getStrInvoicerAdvName());
		objModel.setStrInvoiceHeader1(objBean.getStrInvoiceHeader1());
		objModel.setStrInvoiceHeader2(objBean.getStrInvoiceHeader2());
		objModel.setStrInvoiceHeader3(objBean.getStrInvoiceHeader3());
		objModel.setStrInvoiceFooter1(objBean.getStrInvoiceFooter1());
		objModel.setStrInvoiceFooter2(objBean.getStrInvoiceFooter2());
		objModel.setStrInvoiceFooter3(objBean.getStrInvoiceFooter3());
		objModel.setStrjventry(objBean.getStrjventry());
		objModel.setStrLabelsetting(objBean.getStrLabelsetting());
		objModel.setStrLastCreated(objBean.getStrLastCreated());
		objModel.setStrLetterCode(objBean.getStrLetterCode());
		objModel.setStrLetterPrefix(objBean.getStrLetterPrefix());
		objModel.setStrLogo(objBean.getStrLogo());
		objModel.setStrMasterDrivenNarration(objBean.getStrMasterDrivenNarration());
		objModel.setStrMemberPreProfiling(objBean.getStrMemberPreProfiling());
		objModel.setStrmembrecp(objBean.getStrmembrecp());
		objModel.setStrNarrActivateInv(objBean.getStrNarrActivateInv());
		objModel.setStrNarrActivateJv(objBean.getStrNarrActivateJv());
		objModel.setStrNarrActivatePay(objBean.getStrNarrActivatePay());
		objModel.setStrNarrActivateRec(objBean.getStrNarrActivateRec());
		objModel.setStrPassword(objBean.getStrPassword());
		objModel.setStrpayentry(objBean.getStrpayentry());
		objModel.setStrPettyCashAccountCode(objBean.getStrPettyCashAccountCode());
		objModel.setStrPMS(objBean.getStrPMS());
		objModel.setStrPOSCommonDB(objBean.getStrPOSCommonDB());
		objModel.setStrPOSMSDNdb(objBean.getStrPOSMSDNdb());
		objModel.setStrPOSQfileDB(objBean.getStrPOSQfileDB());
		objModel.setStrPostDatedChequeACCode(objBean.getStrPostDatedChequeACCode());
		objModel.setStrPostDatedChequeACName(objBean.getStrPostDatedChequeACName());
		objModel.setStrpropertyid(objBean.getStrpropertyid());
		objModel.setStrReceiptBcc(objBean.getStrReceiptBcc());
		objModel.setStrReceiptCc(objBean.getStrReceiptCc());
		objModel.setStrReceiptLetterCode(objBean.getStrReceiptLetterCode());
		objModel.setStrrecpentry(objBean.getStrrecpentry());
		objModel.setStrReserveAccCode(objBean.getStrReserveAccCode());
		objModel.setStrReserveAccName(objBean.getStrReserveAccName());
		objModel.setStrRoundingCode(objBean.getStrRoundingCode());
		objModel.setStrRoundingName(objBean.getStrRoundingName());
		objModel.setStrRoundOffCode(objBean.getStrRoundOffCode());
		objModel.setStrSancCode(objBean.getStrSancCode());
		objModel.setStrSancName(objBean.getStrSancName());
		objModel.setStrServiceTaxAccount(objBean.getStrServiceTaxAccount());
		objModel.setStrSmtpPassword(objBean.getStrSmtpPassword());
		objModel.setStrSmtpUserid(objBean.getStrSmtpUserid());
		objModel.setStrSSLRequiredYN(objBean.getStrSSLRequiredYN());
		objModel.setStrSuspenceCode(objBean.getStrSuspenceCode());
		objModel.setStrSuspenceName(objBean.getStrSuspenceName());
		objModel.setStrTallyAlifTransLockYN(objBean.getStrTallyAlifTransLockYN());
		objModel.setStrTaxCode(objBean.getStrTaxCode());
		objModel.setStrTaxIndicator(objBean.getStrTaxIndicator());
		objModel.setStrTypeOfPOsting(objBean.getStrTypeOfPOsting());
		objModel.setStrUserid(objBean.getStrUserid());
		objModel.setStrVouchNarrJv(objBean.getStrVouchNarrJv());
		objModel.setStrVouchNarrPay(objBean.getStrVouchNarrPay());
		objModel.setStrVouchNarrRec(objBean.getStrVouchNarrRec());
		objModel.setStrVouchNarrInvoice(objBean.getStrVouchNarrInvoice());
		objModel.setStrYeaOutsta(objBean.getStrYeaOutsta());
		objModel.setAllowSingleUserLogin(objBean.getAllowSingleUserLogin());
		objModel.setDteLastAR(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteLastAR()));
		objModel.setDteLastRV(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteLastRV()));
		objModel.setDteRVPosted(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteRVPosted()));
		objModel.setDteMemberDataTransfer(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
		objModel.setEmailViaOutlook(objBean.getEmailViaOutlook());
		objModel.setIncludeBanquetMember(objBean.getIncludeBanquetMember());
		objModel.setIsMSOfficeInstalled(objBean.getIsMSOfficeInstalled());
		objModel.setIsMultipleDebtor(objBean.getIsMultipleDebtor());
		objModel.setNEFTOnlineAccountCode(objBean.getNEFTOnlineAccountCode());
		objModel.setNEFTOnlineAccountName(objBean.getNEFTOnlineAccountName());
		objModel.setPDCAccountCode(objBean.getPDCAccountCode());
		objModel.setPDCAccountDesc(objBean.getPDCAccountDesc());
		objModel.setSML(objBean.getSML());

		/*
		 * objModel.setStrClientCode(clientCode);
		 * objModel.setStrPropertyCode(propCode);
		 */

		return objModel;
	}
}
