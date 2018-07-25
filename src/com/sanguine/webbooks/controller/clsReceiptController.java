package com.sanguine.webbooks.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsReceiptBean;
import com.sanguine.webbooks.bean.clsReceiptDetailBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsEmployeeMasterModel;
import com.sanguine.webbooks.model.clsReceiptDebtorDtlModel;
import com.sanguine.webbooks.model.clsReceiptDtlModel;
import com.sanguine.webbooks.model.clsReceiptHdModel;
import com.sanguine.webbooks.model.clsReceiptInvDtlModel;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.service.clsBankMasterService;
import com.sanguine.webbooks.service.clsReceiptService;
import com.sanguine.webbooks.service.clsSundryCreditorMasterService;
import com.sanguine.webbooks.service.clsSundryDebtorMasterService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsReceiptController {

	@Autowired
	private clsReceiptService objReceiptService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;

	@Autowired
	private clsBankMasterService objBankMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSundryDebtorMasterService objSundryDebtorMasterService;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	intfBaseService objBaseService;
	@Autowired
	clsEmployeeMasterController objEmployeeMasterController;
	
	@Autowired
	private clsSundryCreditorMasterService objSundryCreditorMasterService;
	
	// Open Receipt
	@RequestMapping(value = "/frmReceipt", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReceipt_1", "command", new clsReceiptHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReceipt", "command", new clsReceiptHdModel());
		} else {
			return null;
		}
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadReceipt", method = RequestMethod.GET)
	public @ResponseBody clsReceiptBean funLoadHdData(HttpServletRequest request) {

		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsReceiptBean objBean = new clsReceiptBean();
		String docCode = request.getParameter("docCode").toString();
		String debtorCode = "";
		String debtorName = "";
//		String strCurr = request.getSession().getAttribute("currValue").toString();
//		double currValue = Double.parseDouble(strCurr);
		
		clsReceiptHdModel objReceipt = objReceiptService.funGetReceiptList(docCode, clientCode, propCode);

//		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		double currConversion = objReceipt.getDblConversion();
		if(currConversion==0)
		{
			currConversion=1.0;
		}
//		if (objSetup != null) {
//			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objReceipt.getStrCurrency(), clientCode);
//			if (objCurrModel != null) {
//				currConversion = objCurrModel.getDblConvToBaseCurr();
//
//			}
//
//		}
		
		clsReceiptBean objReceiptBean = null;
		if (null == objReceipt) {
			objReceiptBean = new clsReceiptBean();
			objReceiptBean.setStrVouchNo("Invalid");
		} else {
			Map<String, clsReceiptDetailBean> hmReceiptDtlBean = new HashMap<String, clsReceiptDetailBean>();

			objReceiptBean = new clsReceiptBean();
			objReceiptBean.setStrVouchNo(objReceipt.getStrVouchNo());
			objReceiptBean.setStrCFCode(objReceipt.getStrCFCode());
			String CFAccDesc = objWebBooksAccountMasterService.funGetAccountCodeAndName(objReceipt.getStrCFCode(), clientCode).getStrAccountName();
			objReceiptBean.setStrCFDesc(CFAccDesc);
			objReceiptBean.setDteVouchDate(objGlobal.funGetDate("yyyy/MM/dd", objReceipt.getDteVouchDate()));
			objReceiptBean.setDteChequeDate(objGlobal.funGetDate("yyyy/MM/dd", objReceipt.getDteChequeDate()));
			objReceiptBean.setStrType(objReceipt.getStrType());
			objReceiptBean.setStrChequeNo(objReceipt.getStrChequeNo());
			objReceiptBean.setDblAmt(objReceipt.getDblAmt() / currConversion);
			objReceiptBean.setStrDrawnOn(objReceipt.getStrDrawnOn());
			clsBankMasterModel objBank = objBankMasterService.funGetBankMaster(objReceipt.getStrDrawnOn(), clientCode);
			String bankName = "";
			if (objBank != null) {
				bankName = objBank.getStrBankName();
			}
			objReceiptBean.setStrBankName(bankName);
			objReceiptBean.setStrBranch(objReceipt.getStrBranch());
			objReceiptBean.setStrDebtorCode(objReceipt.getStrDebtorCode());
			if(objReceipt.getStrDebtorCode().startsWith("D"))
			{
				clsSundryDebtorMasterModel objSunDebtor = objSundryDebtorMasterService.funGetSundryDebtorMaster(objReceipt.getStrDebtorCode(), clientCode);
				if (objSunDebtor != null) {
					debtorCode = objSunDebtor.getStrDebtorCode();
					debtorName = objSunDebtor.getStrDebtorFullName();
					objReceiptBean.setStrDebtorName(objSunDebtor.getStrDebtorFullName());
				} 
			}else if(objReceipt.getStrDebtorCode().startsWith("C")){
				clsSundaryCreditorMasterModel objSunCrModel= objSundryCreditorMasterService.funGetSundryCreditorMaster(objReceipt.getStrDebtorCode(), clientCode);
				if (objSunCrModel != null) {
					debtorCode = objSunCrModel.getStrCreditorCode();
					debtorName = objSunCrModel.getStrCreditorFullName();
					objReceiptBean.setStrDebtorName(objSunCrModel.getStrCreditorFullName());
				}
				
			}if(objReceipt.getStrDebtorCode().startsWith("E")){
				clsEmployeeMasterModel objclsEmployeeMasterModel=objEmployeeMasterController.funAssignFields(objReceipt.getStrDebtorCode(),request);
				debtorCode = objclsEmployeeMasterModel.getStrEmployeeCode();
				debtorName = objclsEmployeeMasterModel.getStrEmployeeName();
				objReceiptBean.setStrDebtorName(objclsEmployeeMasterModel.getStrEmployeeName());
			
			}
			
			
			clsSundryDebtorMasterModel objSunDebtor = objSundryDebtorMasterService.funGetSundryDebtorMaster(objReceipt.getStrDebtorCode(), clientCode);
			if (objSunDebtor != null) {
				debtorCode = objSunDebtor.getStrDebtorCode();
				debtorName = objSunDebtor.getStrDebtorFullName();
				objReceiptBean.setStrDebtorName(objSunDebtor.getStrDebtorFullName());
			} else {
				objReceiptBean.setStrDebtorName("");
			}

			objReceiptBean.setStrReceivedFrom(objReceipt.getStrReceivedFrom());
			objReceiptBean.setStrNarration(objReceipt.getStrNarration());
			objReceiptBean.setStrUserCreated(objReceipt.getStrUserCreated());
			objReceiptBean.setStrUserEdited(objReceipt.getStrUserEdited());
			objReceiptBean.setDteDateCreated(objGlobal.funGetDate("yyyy/MM/dd", objReceipt.getDteDateCreated()));
			objReceiptBean.setDteDateEdited(objGlobal.funGetDate("yyyy/MM/dd", objReceipt.getDteDateEdited()));

			List<clsReceiptDetailBean> listReceiptDtlBean = new ArrayList<clsReceiptDetailBean>();

			for (clsReceiptDebtorDtlModel objRecDebtorDtl : objReceipt.getListReceiptDebtorDtlModel()) {
				clsReceiptDetailBean objRecDtlNew = new clsReceiptDetailBean();
				objRecDtlNew.setStrAccountCode(objRecDebtorDtl.getStrAccountCode());
				objRecDtlNew.setStrDebtorCode(debtorCode);
				objRecDtlNew.setStrDebtorName(debtorName);

				if (objRecDebtorDtl.getStrCrDr().equalsIgnoreCase("Cr")) {
					objRecDtlNew.setDblCreditAmt(objRecDebtorDtl.getDblAmt() / currConversion);
					objRecDtlNew.setDblDebitAmt(0.0000);
				} else {
					objRecDtlNew.setDblCreditAmt(0.0000);
					objRecDtlNew.setDblDebitAmt(objRecDebtorDtl.getDblAmt() / currConversion);
				}
				hmReceiptDtlBean.put(objRecDebtorDtl.getStrAccountCode(), objRecDtlNew);
			}

			for (clsReceiptDtlModel objRecDtlModel : objReceipt.getListReceiptDtlModel()) {
				clsReceiptDetailBean objRecDtlBean = hmReceiptDtlBean.get(objRecDtlModel.getStrAccountCode());
				if (null == objRecDtlBean) {
					objRecDtlBean = new clsReceiptDetailBean();
					objRecDtlBean.setStrDebtorCode("");
					objRecDtlBean.setStrDebtorName("");
				} else {
					if(objReceipt.getStrDebtorCode().startsWith("D"))
					{
						clsSundryDebtorMasterModel objSunDr = objSundryDebtorMasterService.funGetSundryDebtorMaster(objRecDtlBean.getStrDebtorCode(), clientCode);
						objRecDtlBean.setStrDebtorName(objSunDr.getStrFirstName());
					}else if(objReceipt.getStrDebtorCode().startsWith("C")){
					
					clsSundaryCreditorMasterModel objSunCrModel= objSundryCreditorMasterService.funGetSundryCreditorMaster(objReceipt.getStrDebtorCode(), clientCode);
					objReceiptBean.setStrDebtorCode(objReceipt.getStrDebtorCode()) ;
					objReceiptBean.setStrDebtorName(objSunCrModel.getStrFirstName());
					
				}if(objReceipt.getStrDebtorCode().startsWith("E")){
					clsEmployeeMasterModel objclsEmployeeMasterModel=objEmployeeMasterController.funAssignFields(objReceipt.getStrDebtorCode(),request);
					debtorCode = objclsEmployeeMasterModel.getStrEmployeeCode();
					debtorName = objclsEmployeeMasterModel.getStrEmployeeName();
					objReceiptBean.setStrDebtorName(objclsEmployeeMasterModel.getStrEmployeeName());
				
				}
				}
				objRecDtlBean.setStrAccountCode(objRecDtlModel.getStrAccountCode());
				objRecDtlBean.setStrDescription(objRecDtlModel.getStrAccountName());
				objRecDtlBean.setStrDC(objRecDtlModel.getStrCrDr());
				objRecDtlBean.setDblCreditAmt(objRecDtlModel.getDblCrAmt() / currConversion);
				objRecDtlBean.setDblDebitAmt(objRecDtlModel.getDblDrAmt() / currConversion);
				objRecDtlBean.setStrDimension("");
				listReceiptDtlBean.add(objRecDtlBean);
			}
			objReceiptBean.setListReceiptBeanDtl(listReceiptDtlBean);
			hmReceiptDtlBean = null;

			List<clsReceiptInvDtlModel> listRecInvDtlModel = new ArrayList<clsReceiptInvDtlModel>();
			for (clsReceiptInvDtlModel objRecInvDtlModel : objReceipt.getListReceiptInvDtlModel()) {
				objRecInvDtlModel.setStrSelected("Tick");
				objRecInvDtlModel.setStrPropertyCode(propCode);
				objRecInvDtlModel.setDblInvAmt(objRecInvDtlModel.getDblInvAmt() / currConversion);
				objRecInvDtlModel.setDblPayedAmt(objRecInvDtlModel.getDblPayedAmt() / currConversion);
				listRecInvDtlModel.add(objRecInvDtlModel);

			}
			objReceiptBean.setListReceiptInvDtlModel(listRecInvDtlModel);
			objReceiptBean.setStrCurrency(objReceipt.getStrCurrency());
			objReceiptBean.setDblConversion(currConversion); 
		}
		return objReceiptBean;
	}

	// Save or Update Receipt
	@RequestMapping(value = "/saveReceipt", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsReceiptBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsReceiptHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, propCode, req);
			objReceiptService.funAddUpdateReceiptHd(objHdModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Receipt No : ".concat(objHdModel.getStrVouchNo()));
			req.getSession().setAttribute("rptVoucherNo", objHdModel.getStrVouchNo());
			return new ModelAndView("redirect:/frmReceipt.html");
		} else {
			return new ModelAndView("frmReceipt");
		}
	}

	// Convert bean to model function
	public clsReceiptHdModel funPrepareHdModel(clsReceiptBean objBean, String userCode, String clientCode, String propertyCode, HttpServletRequest request) {

		clsReceiptHdModel objModel = new clsReceiptHdModel();
//		String strCurr = request.getSession().getAttribute("currValue").toString();
		
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
//		double currConversion = 1.0;
//		if (objSetup != null) {
//			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrency(), clientCode);
//			if (objCurrModel != null) {
//				currConversion = objCurrModel.getDblConvToBaseCurr();
//
//			}
//
//		}
		double currConversion = 1.0;
		if(objBean.getDblConversion()>0)
		{
			currConversion=objBean.getDblConversion();
		}

		
		Map<String, clsReceiptDtlModel> hmReceiptDtl = new HashMap<String, clsReceiptDtlModel>();
		if (objBean.getStrVouchNo().isEmpty()) // New Entry
		{
			String documentNo = objGlobal.funGenerateDocumentCodeWebBook("frmReceipt", objBean.getDteVouchDate(), request);
			objModel.setStrVouchNo(documentNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else // Update
		{
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrVouchNo(objBean.getStrVouchNo());
		}

		objModel.setStrCFCode(objBean.getStrCFCode());
		objModel.setStrType(objGlobal.funIfNull(objBean.getStrType(), "None", objBean.getStrType()));
		objModel.setStrDebtorCode(objGlobal.funIfNull(objBean.getStrDebtorCode(), "", objBean.getStrDebtorCode()));
		objModel.setStrReceivedFrom(objGlobal.funIfNull(objBean.getStrReceivedFrom(), "", objBean.getStrReceivedFrom()));
		objModel.setStrChequeNo(objBean.getStrChequeNo());
		objModel.setStrDrawnOn(objGlobal.funIfNull(objBean.getStrDrawnOn(), "", objBean.getStrDrawnOn()));
		objModel.setStrBranch(objGlobal.funIfNull(objBean.getStrBranch(), "", objBean.getStrBranch()));
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "NA", objBean.getStrNarration()));
		objModel.setStrSancCode(objGlobal.funIfNull(objBean.getStrSancCode(), "NA", objBean.getStrSancCode()));
		objModel.setDblAmt(objBean.getDblAmt() * currConversion);
		objModel.setStrCrDr(objBean.getStrCrDr());
		objModel.setDteVouchDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteVouchDate()));
		objModel.setDteChequeDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteChequeDate()));
		objModel.setIntVouchMonth(objGlobal.funGetMonth());
		objModel.setIntVouchNum(0);
		objModel.setStrTransMode("R");
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		if (objBean.getStrType().equals("NEFT")) {
			objModel.setStrReceiptType("MR");
		} else {
			objModel.setStrReceiptType("Cr");
		}
		if (request.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			objModel.setStrModuleType("APGL");

		} else {
			objModel.setStrModuleType("AP");
		}

		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrClientCode(clientCode);
		if (objBean.getStrType().equals("NEFT")) {
			objModel.setDteClearence("0000-00-00 00:00:00");
		}if (objBean.getStrType().equals("Cheque")){
			objModel.setDteClearence("1990-01-01 00:00:00");
		}
		else {
			objModel.setDteClearence(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		objModel.setStrReceivedFrom(objGlobal.funIfNull(objBean.getStrReceivedFrom(), "", objBean.getStrReceivedFrom()));
		objModel.setIntOnHold(0);
	
		for (clsReceiptDetailBean objReceiptDetails : objBean.getListReceiptBeanDtl()) {
			if (null != hmReceiptDtl.get(objReceiptDetails.getStrAccountCode())) {
				clsReceiptDtlModel objReceiptDtlModel = hmReceiptDtl.get(objReceiptDetails.getStrAccountCode());
				objReceiptDtlModel.setDblCrAmt((objReceiptDetails.getDblCreditAmt() + objReceiptDetails.getDblCreditAmt()) * currConversion);
				objReceiptDtlModel.setDblDrAmt((objReceiptDetails.getDblDebitAmt() + objReceiptDetails.getDblDebitAmt()) * currConversion);
				hmReceiptDtl.put(objReceiptDetails.getStrAccountCode(), objReceiptDtlModel);
			} else {
				clsReceiptDtlModel objReceiptDtlModel = new clsReceiptDtlModel();
				objReceiptDtlModel.setStrAccountCode(objReceiptDetails.getStrAccountCode());
				objReceiptDtlModel.setStrAccountName(objReceiptDetails.getStrDescription());
				objReceiptDtlModel.setStrCrDr(objReceiptDetails.getStrDC());
				objReceiptDtlModel.setStrNarration("");
				objReceiptDtlModel.setDblCrAmt(objReceiptDetails.getDblCreditAmt() * currConversion);
				objReceiptDtlModel.setDblDrAmt(objReceiptDetails.getDblDebitAmt() * currConversion);
				objReceiptDtlModel.setStrPropertyCode(propertyCode);
				hmReceiptDtl.put(objReceiptDetails.getStrAccountCode(), objReceiptDtlModel);
			}
		}

		List<clsReceiptDtlModel> listReceiptDtlModel = new ArrayList<clsReceiptDtlModel>();
		for (Map.Entry<String, clsReceiptDtlModel> entry : hmReceiptDtl.entrySet()) {
			listReceiptDtlModel.add(entry.getValue());
		}
		objModel.setListReceiptDtlModel(listReceiptDtlModel);
		String debtorCode = "";
		String debtorName = "";
		List<clsReceiptDebtorDtlModel> listReceiptDebtorDtlModel = new ArrayList<clsReceiptDebtorDtlModel>();
		for (clsReceiptDetailBean objReceiptDetails : objBean.getListReceiptBeanDtl()) {
			clsReceiptDebtorDtlModel objReceiptDebtorDtlModel = new clsReceiptDebtorDtlModel();

			if (objReceiptDetails.getStrDC().equals("Cr")) {
				debtorCode = objReceiptDetails.getStrDebtorCode();
				clsSundryDebtorMasterModel objSunDrModel = objSundryDebtorMasterService.funGetSundryDebtorMaster(debtorCode, clientCode);
				if (objSunDrModel != null) {
					objReceiptDebtorDtlModel.setStrDebtorName(objSunDrModel.getStrFirstName());
					debtorName = objSunDrModel.getStrDebtorFullName();
				} else {
					objReceiptDebtorDtlModel.setStrDebtorName("");
				}
				objReceiptDebtorDtlModel.setStrDebtorCode(objReceiptDetails.getStrDebtorCode());

				objReceiptDebtorDtlModel.setStrAccountCode(objReceiptDetails.getStrAccountCode());
				objReceiptDebtorDtlModel.setStrNarration("");
				objReceiptDebtorDtlModel.setStrPropertyCode(propertyCode);
				objReceiptDebtorDtlModel.setStrCrDr(objReceiptDetails.getStrDC());
				objReceiptDebtorDtlModel.setDblAmt(objReceiptDetails.getDblCreditAmt() * currConversion);

				objReceiptDebtorDtlModel.setStrBillNo("");
				objReceiptDebtorDtlModel.setStrInvoiceNo("");
				objReceiptDebtorDtlModel.setStrGuest("");
				objReceiptDebtorDtlModel.setStrCreditNo("");
				objReceiptDebtorDtlModel.setDteBillDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objReceiptDebtorDtlModel.setDteInvoiceDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objReceiptDebtorDtlModel.setDteDueDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

				listReceiptDebtorDtlModel.add(objReceiptDebtorDtlModel);
			}
		}
		objModel.setStrDebtorCode(debtorCode);
		objModel.setStrDebtorName(debtorName);
		objModel.setListReceiptDebtorDtlModel(listReceiptDebtorDtlModel);

		List<clsReceiptInvDtlModel> listReceiptInvDtl = new ArrayList<clsReceiptInvDtlModel>();
		for (clsReceiptInvDtlModel objReceitInvdtl : objBean.getListReceiptInvDtlModel()) {
			if (objReceitInvdtl.getStrSelected() != null && objReceitInvdtl.getStrSelected().equalsIgnoreCase("Tick") && objReceitInvdtl.getDblPayedAmt() > 0) {
				objReceitInvdtl.setDblInvAmt(objReceitInvdtl.getDblInvAmt() * currConversion);
				objReceitInvdtl.setDblPayedAmt(objReceitInvdtl.getDblPayedAmt() * currConversion);
				objReceitInvdtl.setStrPropertyCode(propertyCode);
				listReceiptInvDtl.add(objReceitInvdtl);
			}

		}
		objModel.setListReceiptInvDtlModel(listReceiptInvDtl);
		objModel.setStrCurrency(objBean.getStrCurrency());
		objModel.setDblConversion(currConversion);
		
		
		return objModel;
	}

	@RequestMapping(value = "/frmReciptReport", method = RequestMethod.GET)
	public ModelAndView funOpenReportForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);


		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReciptReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReciptReport", "command", new clsReportBean());
		} else {
			return null;
		}
		// return new ModelAndView("frmJV","command", new clsJVHdModel());
	}

	@RequestMapping(value = "/rptReciptReport", method = RequestMethod.GET)
	private void funReciptReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String currencyCode=objBean.getStrCurrency();
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		double conversionRate=1;
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currencyCode+"' and strClientCode='"+clientCode+"' ");
		try
		{
			List list = objBaseService.funGetList(sbSql,"sql");
			conversionRate=Double.parseDouble(list.get(0).toString());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		funCallReciptdtlReport(VoucherNo, type, resp, req, currValue,conversionRate);
	}

	@RequestMapping(value = "/openRptReciptReport", method = RequestMethod.GET)
	private void funReciptReport(HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = req.getParameter("docCode").toString();
		String type = "pdf";
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		double conversionRate=1;
		funCallReciptdtlReport(VoucherNo, type, resp, req, currValue,conversionRate);
		// funCallReciptdtlReport(VoucherNo,type,resp,req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReciptdtlReport(String VoucherNo, String type, HttpServletResponse resp, HttpServletRequest req, double currValue,double conversionRate) {
		try {

			String strVouchNo = "";
			String dteVouchDate = "";
			String strNarration = "",strCurrency="",currency="";

			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
//			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
//			if (objSetup == null) {
//				objSetup = new clsPropertySetupModel();
//			}
			double currConversion = 1.0;
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptReciptReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String strPaymentMode="",strChequeNo="",strBankName="";
			String sqlHd = "select strVouchNo ,Date(dteVouchDate),strNarration,strType,strChequeNo,ifnull(b.strBankName,''),a.strCurrency,a.dblConversion "
					+ "from tblreceipthd a left outer join tblbankmaster b on  a.strDrawnOn = b.strBankCode"
					+ " where strVouchNo='" + VoucherNo + "'";

			List list = objGlobalFunctionsService.funGetListModuleWise(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strVouchNo = arrObj[0].toString();
				dteVouchDate = arrObj[1].toString();
				strNarration = arrObj[2].toString();
				strPaymentMode= arrObj[3].toString();
				strChequeNo= arrObj[4].toString();
				strBankName= arrObj[5].toString();
				strCurrency = arrObj[6].toString();
				currConversion = Double.parseDouble(arrObj[7].toString());
			}
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurrency, clientCode);
				if (objCurrModel != null) {
					
					currency = objCurrModel.getStrCurrencyName();
				}

			
			//String sqlDtl = "select strAccountCode,strAccountName ,dblCrAmt/" + currValue + " as dblCrAmt ,dblDrAmt/" + currValue + " as dblDrAmt from tblreceiptdtl where strVouchNo='" + VoucherNo + "' order by strAccountCode;";
			String sqlDtl = "select strAccountCode,strAccountName ,dblCrAmt /" + currConversion + " as dblCrAmt ,dblDrAmt /" + currConversion + " as dblDrAmt from tblreceiptdtl where strVouchNo='" + VoucherNo + "' order by strAccountCode;";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsReciptdtl");
			subDataset.setQuery(subQuery);
			//String sqldetordtl = "select strDebtorCode,strDebtorName,sum(dblAmt)/" + currValue + " as dblAmt,strCrDr,strBillNo,date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration " + "from tblreceiptdebtordtl where strVouchNo='" + VoucherNo + "' group by strDebtorCode ,strCrDr  order by strDebtorCode ";

			String sqldetordtl = "select strDebtorCode,strDebtorName,sum(dblAmt) /" + currConversion + " as dblAmt,strCrDr,strBillNo,date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration " + "from tblreceiptdebtordtl where strVouchNo='" + VoucherNo + "' group by strDebtorCode ,strCrDr  order by strDebtorCode ";

			JRDesignQuery detorDtl = new JRDesignQuery();
			detorDtl.setText(sqldetordtl);
			JRDesignDataset detorDataset = (JRDesignDataset) datasetMap.get("dsRecipedetor");
			detorDataset.setQuery(detorDtl);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strVouchNo", strVouchNo);
			hm.put("dteVouchDate", objGlobal.funGetDate("dd-MM-yyyy", dteVouchDate));
			hm.put("strNarration", strNarration);
			hm.put("strCurrency", currency);
			
			if(strPaymentMode.equalsIgnoreCase("Cheque"))
			{
				hm.put("strChequeNo",strChequeNo);
				hm.put("strBankName",strBankName);
				hm.put("strtxt","Cheque No");
				hm.put("strtxt1","Drawn On");
			}
			else if(strPaymentMode.equalsIgnoreCase("Credit Card"))
			{
				hm.put("strChequeNo",strChequeNo);
				hm.put("strtxt","Card No");
			}
			else if(strPaymentMode.equalsIgnoreCase("NEFT"))
			{
				hm.put("strChequeNo",strChequeNo);
				hm.put("strtxt","Account No");
			}
			
			hm.put("strPaymentMode", strPaymentMode);
			
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);

			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptReciptReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/loadUnPayedInv", method = RequestMethod.GET)
	public @ResponseBody List<clsReceiptInvDtlModel> funloadUnPayedInv(HttpServletRequest request) {

		String sql = "";
		List<clsReceiptInvDtlModel> listUnBillInv = new ArrayList<clsReceiptInvDtlModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String strDebtorCode = request.getParameter("strDebtorCode").toString();
		String strCurr = request.getParameter("strCurrency").toString();
		double currValue=1.0;
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurr, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
				
			}

		listUnBillInv = funUnPayedInvList(strDebtorCode, clientCode, propCode, currValue, request);
		return listUnBillInv;
	}


	private List<clsReceiptInvDtlModel> funUnPayedInvList(String strDebtorCode, String clientCode, String propCode
			, double currConversion,HttpServletRequest request) {

		List<clsReceiptInvDtlModel> listUnBillInv = new ArrayList<clsReceiptInvDtlModel>();
		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("strDebtorCode", strDebtorCode);
		jObjData.put("currConversion", currConversion);
		//JSONObject jObjRet = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadUnPayedInv", jObjData);
		JSONObject jObjRet = funLoadUnPayedInvJSON(jObjData,request);
		if (jObjRet.size() > 0) {
			JSONArray jsonArr = (JSONArray) jObjRet.get("unPayedInv");

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jObj = (JSONObject) jsonArr.get(i);
				clsReceiptInvDtlModel objRptInvDtl = new clsReceiptInvDtlModel();
				objRptInvDtl.setStrInvCode(jObj.get("strInvCode").toString());
				objRptInvDtl.setStrInvBIllNo(jObj.get("strBillNo").toString());
				objRptInvDtl.setDteBillDate(jObj.get("dteBillDate").toString());
				double saleReturn = 0.0;
				double paymentAmt = 0.0;
				// String
				// sql=" select ifnull(a.dblInvAmt-sum(a.dblPayedAmt),0) from tblreceiptinvdtl a  "
				// +
				// "where a.strInvCode='"+jObj.get("strInvCode").toString()+"' and a.strClientCode='"+clientCode+"';  ";

				String sql = " select ifnull(sum(a.dblPayedAmt),0) from tblreceiptinvdtl a  " + "where a.strInvCode='" + jObj.get("strInvCode").toString() + "' and a.strClientCode='" + clientCode + "';  ";
				System.out.println(sql);
				List list = objGlobalFunctionsService.funGetDataList(sql, "sql");

				String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
				String sqlSalesRetrn = "select ifnull(sum(a.dblTotalAmt),0) from "+webStockDB+".tblsalesreturnhd a where a.strAgainst='Invoice' and a.strDCCode='" + jObj.get("strInvCode").toString() + "' " + " and a.strClientCode='" + clientCode + "' ";

				List listSalesReturn = objGlobalFunctionsService.funGetDataList(sqlSalesRetrn, "sql");
				if (listSalesReturn.size() > 0) {
					saleReturn = Double.parseDouble(listSalesReturn.get(0).toString());
					saleReturn = saleReturn;
				}

				if (Double.parseDouble(list.get(0).toString()) > 0) {
					paymentAmt = Double.parseDouble(list.get(0).toString());
				}

				objRptInvDtl.setDblInvAmt((Double.parseDouble(jObj.get("dblGrandTotal").toString()) - saleReturn - paymentAmt) / currConversion);

				objRptInvDtl.setDteInvDate(jObj.get("dteInvDate").toString());
				objRptInvDtl.setDteInvDueDate(jObj.get("dteDueDate").toString());
				objRptInvDtl.setStrPropertyCode(propCode);
				listUnBillInv.add(objRptInvDtl);
				System.out.println(listUnBillInv);
			}

		}

		return listUnBillInv;
	}
	

	public  JSONObject funLoadUnPayedInvJSON(JSONObject objCLData,HttpServletRequest req)
    {
	JSONObject jObjData = new JSONObject();
	JSONArray jArrData = new JSONArray();
   
	try
		{
			String clientCode = objCLData.get("clientCode").toString();
			String strDebtorCode = objCLData.get("strDebtorCode").toString();
	
			DecimalFormat df = new DecimalFormat("#.00");
			double currConversion  = Double.parseDouble(objCLData.get("currConversion").toString());
			String dbmms=req.getSession().getAttribute("WebStockDB").toString();
			String dbBooks=req.getSession().getAttribute("WebBooksDB").toString();
	       
	        StringBuilder sql =new StringBuilder("  SELECT a.strInvCode,a.strPackNo,a.dblGrandTotal,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),DATE_FORMAT(a.dteInvDate,'%d-%m-%Y') "
	        		+ " FROM "+dbmms+".tblinvoicehd a,"+dbmms+".tbllinkup b "
	        		+ " WHERE a.strInvCode NOT IN "
	        		+ " ( SELECT b.strInvCode FROM "+dbBooks+".tblreceiptinvdtl b "
	        		+ " WHERE b.dblInvAmt=b.dblPayedAmt AND b.strClientCode='"+clientCode+"') "
	        		+ " AND a.strClientCode='"+clientCode+"' AND a.strCustCode=b.strMasterCode "
	        		+ " AND b.strAccountCode='"+strDebtorCode+"'  "); 
	        System.out.print(sql);
	        List list =objBaseService.funGetList(sql, "sql");
	        if(null!=list){
	        	if(list.size()>0){
	        		for(int i=0;i<list.size();i++){
	        			Object ob[]=(Object[])list.get(i);
	        			
	        			double totAmt =Double.parseDouble(ob[2].toString());
	    		    	JSONObject jsonObj =new JSONObject();
	    		    	jsonObj.put("strInvCode",ob[0].toString());
	    		    	jsonObj.put("strBillNo",ob[1].toString());
	    		    	jsonObj.put("dblGrandTotal",df.format(totAmt));
	    		    	jsonObj.put("dteInvDate",ob[3].toString());
	    		    	jsonObj.put("dteBillDate",ob[4].toString());
	    		    	jsonObj.put("dteDueDate",ob[5].toString());
	    		    	jArrData.add(jsonObj);
	        		}
	        	}
	        }
	        jObjData.put("unPayedInv", jArrData);
		    
		}
	catch (Exception e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
		return jObjData;
	
    }


}
