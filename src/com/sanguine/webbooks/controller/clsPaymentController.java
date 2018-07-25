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
import org.springframework.web.bind.annotation.RequestParam;
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
import com.sanguine.webbooks.apgl.model.clsSundaryCrBillGRNDtlModel;
import com.sanguine.webbooks.bean.clsPaymentBean;
import com.sanguine.webbooks.bean.clsPaymentDetailsBean;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsEmployeeMasterModel;
import com.sanguine.webbooks.model.clsPaymentDebtorDtlModel;
import com.sanguine.webbooks.model.clsPaymentDtl;
import com.sanguine.webbooks.model.clsPaymentGRNDtlModel;
import com.sanguine.webbooks.model.clsPaymentHdModel;
import com.sanguine.webbooks.model.clsPaymentScBillDtlModel;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;
import com.sanguine.webbooks.service.clsBankMasterService;
import com.sanguine.webbooks.service.clsPaymentService;
import com.sanguine.webbooks.service.clsSundryCreditorMasterService;
import com.sanguine.webbooks.service.clsSundryDebtorMasterService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsPaymentController {

	@Autowired
	private clsPaymentService objPaymentService;

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
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsSundryCreditorMasterService objSundryCreditorMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;


	@Autowired
	intfBaseService objBaseService;
	
	@Autowired
	private clsSundryDebtorMasterService objSundryDebtorMasterService;
	
	@Autowired
	clsEmployeeMasterController objEmployeeMasterController;
	
	// Open Payment
	@RequestMapping(value = "/frmPayment", method = RequestMethod.GET)
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
			return new ModelAndView("frmPayment_1", "command", new clsPaymentHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPayment", "command", new clsPaymentHdModel());
		} else {
			return null;
		}
	}

	/*
	 * @ModelAttribute("Months") public Map<String,Integer>
	 * funGetMonths(HttpServletRequest req) { Map<String,Integer> hmMonths=new
	 * HashMap<String, Integer>(); hmMonths.put("January", 1);
	 * hmMonths.put("February", 2); hmMonths.put("March", 3);
	 * hmMonths.put("April", 4); hmMonths.put("May", 5); hmMonths.put("June",
	 * 6); hmMonths.put("July", 7); hmMonths.put("August", 8);
	 * hmMonths.put("September", 9); hmMonths.put("October", 10);
	 * hmMonths.put("November", 11); hmMonths.put("December", 12);
	 * 
	 * return hmMonths; }
	 */

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadPayment", method = RequestMethod.GET)
	public @ResponseBody clsPaymentBean funLoadPaymentRecord(HttpServletRequest request) {

		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPaymentBean objPaymentBean = null;
		String docCode = request.getParameter("docCode").toString();
//		double currValue = Double.parseDouble(request.getSession().getAttribute("currValue").toString());
		clsPaymentHdModel objPaymentHd = objPaymentService.funGetPaymentList(docCode, clientCode, propCode);

//		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		double currConversion = objPaymentHd.getDblConversion();
		if(currConversion==0){
			currConversion=1;
		}
//		if (objSetup != null) {
//			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objPaymentHd.getStrCurrency(), clientCode);
//			if (objCurrModel != null) {
//				currConversion = objCurrModel.getDblConvToBaseCurr();
//
//			}
//
//		}
		
		if (null == objPaymentHd) {
			objPaymentBean = new clsPaymentBean();
			objPaymentBean.setStrVouchNo("Invalid");
		} else {
			Map<String, clsPaymentDetailsBean> hmPaymentDtlBean = new HashMap<String, clsPaymentDetailsBean>();

			objPaymentBean = new clsPaymentBean();
			objPaymentBean.setStrVouchNo(objPaymentHd.getStrVouchNo());
			objPaymentBean.setStrBankCode(objPaymentHd.getStrBankCode());
			String bankAccDesc = objWebBooksAccountMasterService.funGetAccountCodeAndName(objPaymentHd.getStrBankCode(), clientCode).getStrAccountName();
			objPaymentBean.setStrBankAccDesc(bankAccDesc);
			objPaymentBean.setDteVouchDate(objGlobal.funGetDate("yyyy/MM/dd", objPaymentHd.getDteVouchDate()));
			objPaymentBean.setIntVouchMonth(objPaymentHd.getIntVouchMonth());
			objPaymentBean.setIntMonth(objPaymentHd.getIntMonth());
			objPaymentBean.setDteChequeDate(objGlobal.funGetDate("yyyy/MM/dd", objPaymentHd.getDteChequeDate()));
			objPaymentBean.setStrType(objPaymentHd.getStrType());
			objPaymentBean.setStrChequeNo(objPaymentHd.getStrChequeNo());
			objPaymentBean.setDblAmt(objPaymentHd.getDblAmt() / currConversion);
			objPaymentBean.setStrDrawnOn(objPaymentHd.getStrDrawnOn());
			clsBankMasterModel objBankModel = objBankMasterService.funGetBankMaster(objPaymentHd.getStrDrawnOn(), clientCode);
			String bankName = "";
			if (objBankModel != null) {
				bankName = objBankModel.getStrBankName();
			}
			objPaymentBean.setStrDrawnDesc(bankName);
			objPaymentBean.setStrBranch(objPaymentHd.getStrBranch());
			objPaymentBean.setStrNarration(objPaymentHd.getStrNarration());
			objPaymentBean.setStrUserCreated(objPaymentHd.getStrUserCreated());
			objPaymentBean.setStrUserEdited(objPaymentHd.getStrUserEdited());
			objPaymentBean.setDteDateCreated(objGlobal.funGetDate("yyyy/MM/dd", objPaymentHd.getDteDateCreated()));
			objPaymentBean.setDteDateEdited(objGlobal.funGetDate("yyyy/MM/dd", objPaymentHd.getDteDateEdited()));

			List<clsPaymentDetailsBean> listPaymentDtlBean = new ArrayList<clsPaymentDetailsBean>();
			for (clsPaymentDebtorDtlModel objPaymentDebtorDtl : objPaymentHd.getListPaymentDebtorDtlModel()) {
				clsPaymentDetailsBean objPaymentDtlNew = new clsPaymentDetailsBean();
				objPaymentDtlNew.setStrAccountCode(objPaymentDebtorDtl.getStrAccountCode());
				objPaymentDtlNew.setStrDebtorCode(objPaymentDebtorDtl.getStrDebtorCode());
				objPaymentDtlNew.setStrDebtorName(objPaymentDebtorDtl.getStrDebtorName());

				if (objPaymentDebtorDtl.getStrCrDr().equalsIgnoreCase("Cr")) {
					objPaymentDtlNew.setDblCreditAmt(objPaymentDebtorDtl.getDblAmt() / currConversion);
					objPaymentDtlNew.setDblDebitAmt(0.0000);
				} else {
					objPaymentDtlNew.setDblCreditAmt(0.0000);
					objPaymentDtlNew.setDblDebitAmt(objPaymentDebtorDtl.getDblAmt() / currConversion);
				}
				hmPaymentDtlBean.put(objPaymentDebtorDtl.getStrAccountCode(), objPaymentDtlNew);
			}

			for (clsPaymentDtl objPaymentDtlModel : objPaymentHd.getListPaymentDtlModel()) {
				clsPaymentDetailsBean objPaymentDtlBean = hmPaymentDtlBean.get(objPaymentDtlModel.getStrAccountCode());
				if (null != objPaymentDtlBean) {
					objPaymentDtlBean = new clsPaymentDetailsBean();
					
					if(objPaymentDtlModel.getStrDebtorCode().startsWith("D"))
					{
						clsSundryDebtorMasterModel objSunDebtor = objSundryDebtorMasterService.funGetSundryDebtorMaster(objPaymentDtlModel.getStrDebtorCode(), clientCode);
						String debtorName="";
						if (objSunDebtor != null) {
							debtorName = objSunDebtor.getStrDebtorFullName();
						}
						objPaymentDtlBean.setStrDebtorCode(objPaymentDtlModel.getStrDebtorCode());
						objPaymentDtlBean.setStrDebtorName(debtorName);
					}else if(objPaymentDtlModel.getStrDebtorCode().startsWith("C")){
					
					clsSundaryCreditorMasterModel objSunCrModel= objSundryCreditorMasterService.funGetSundryCreditorMaster(objPaymentDtlModel.getStrDebtorCode(), clientCode);
					objPaymentDtlBean.setStrDebtorCode(objPaymentDtlModel.getStrDebtorCode()) ;
					objPaymentDtlBean.setStrDebtorName(objSunCrModel.getStrFirstName());
				}if(objPaymentDtlModel.getStrDebtorCode().startsWith("E")){
					clsEmployeeMasterModel objclsEmployeeMasterModel=objEmployeeMasterController.funAssignFields(objPaymentDtlModel.getStrDebtorCode(),request);
					
					objPaymentDtlBean.setStrDebtorCode(objclsEmployeeMasterModel.getStrEmployeeCode()) ;
					objPaymentDtlBean.setStrDebtorName(objclsEmployeeMasterModel.getStrEmployeeName());
					
				
				} 
				}else {
					objPaymentDtlBean = new clsPaymentDetailsBean();
					objPaymentDtlBean.setStrDebtorCode("");
					objPaymentDtlBean.setStrDebtorName("");
				}
				objPaymentDtlBean.setStrAccountCode(objPaymentDtlModel.getStrAccountCode());
				objPaymentDtlBean.setStrDescription(objPaymentDtlModel.getStrAccountName());
				objPaymentDtlBean.setStrDC(objPaymentDtlModel.getStrCrDr());
				objPaymentDtlBean.setDblCreditAmt(objPaymentDtlModel.getDblCrAmt() / currConversion);
				objPaymentDtlBean.setDblDebitAmt(objPaymentDtlModel.getDblDrAmt() / currConversion);
				objPaymentDtlBean.setStrDimension("");
				listPaymentDtlBean.add(objPaymentDtlBean);
			}
			objPaymentBean.setListPaymentDetailsBean(listPaymentDtlBean);
			hmPaymentDtlBean = null;
			List<clsPaymentGRNDtlModel> listPaymentGRNDtl = new ArrayList<clsPaymentGRNDtlModel>();
			for (clsPaymentGRNDtlModel objPaymentGrndtl : objPaymentHd.getListPaymentGRNDtlModel()) {
				objPaymentGrndtl.setDblGRNAmt(objPaymentGrndtl.getDblGRNAmt() / currConversion);
				objPaymentGrndtl.setDblPayedAmt(objPaymentGrndtl.getDblPayedAmt() / currConversion);
				objPaymentGrndtl.setStrSelected("Tick");
				objPaymentGrndtl.setStrPropertyCode(propCode);
				listPaymentGRNDtl.add(objPaymentGrndtl);
			}
			objPaymentBean.setListPaymentGRNDtl(listPaymentGRNDtl);
			objPaymentBean.setStrCurrency(objPaymentHd.getStrCurrency());
			objPaymentBean.setDblConversion(currConversion); 
		}
		return objPaymentBean;
	}

	// Save or Update Payment
	@RequestMapping(value = "/savePayment", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPaymentBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
//			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
//			double currConversion = 1.0;
//			if (objSetup != null) {
//				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrency(), clientCode);
//				if (objCurrModel != null) {
//					currConversion = objCurrModel.getDblConvToBaseCurr();
//				}
//
//			}
			double currConversion = 1.0;
			if(objBean.getDblConversion()>0)
			{
				currConversion=objBean.getDblConversion();
			}

			clsPaymentHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req, propCode, currConversion);
			objPaymentService.funAddUpdatePaymentHd(objHdModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Payment No : ".concat(objHdModel.getStrVouchNo()));
			req.getSession().setAttribute("rptVoucherNo", objHdModel.getStrVouchNo());

			return new ModelAndView("redirect:/frmPayment.html");
		} else {
			return new ModelAndView("frmPayment");
		}
	}

	// Convert bean to model function
	private clsPaymentHdModel funPrepareHdModel(clsPaymentBean objBean, String userCode, String clientCode, HttpServletRequest request, String propertyCode, double currValue) {

		clsPaymentHdModel objModel = new clsPaymentHdModel();
		Map<String, clsPaymentDtl> hmPaymentDtl = new HashMap<String, clsPaymentDtl>();

		if (objBean.getStrVouchNo().isEmpty()) // New Entry
		{
			String documentNo = objGlobal.funGenerateDocumentCodeWebBook("frmPayment", objBean.getDteVouchDate(), request);
			objModel.setStrVouchNo(documentNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else // Update
		{
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrVouchNo(objBean.getStrVouchNo());
		}

		objModel.setStrBankCode(objBean.getStrBankCode());
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "NA", objBean.getStrNarration()));
		objModel.setStrSancCode(objGlobal.funIfNull(objBean.getStrSancCode(), "NA", objBean.getStrSancCode()));
		objModel.setStrChequeNo(objGlobal.funIfNull(objBean.getStrChequeNo(), "", objBean.getStrChequeNo()));
		objModel.setDblAmt(objBean.getDblAmt() * currValue);
		objModel.setStrCrDr(objBean.getStrCrDr());
		objModel.setDteVouchDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteVouchDate()));
		objModel.setDteChequeDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDteChequeDate()));
		objModel.setIntVouchMonth(objGlobal.funGetMonth());
		objModel.setStrTransMode("R");
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		// System.out.print(request.getSession().getAttribute("selectedModuleName").toString());
		if (request.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			objModel.setStrModuleType("APGL");
		} else {
			objModel.setStrModuleType("AR");
		}

		if (objBean.getStrType().equals("NEFT")) {
			objModel.setDteClearence("0000-00-00 00:00:00");
		}
		if (objBean.getStrType().equals("Cheque")){
			objModel.setDteClearence("1990-01-01 00:00:00");
		}
		else {
			objModel.setDteClearence(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		objModel.setIntMonth(1);// Set Month Later
		objModel.setStrType(objGlobal.funIfNull(objBean.getStrType(), "None", objBean.getStrType()));
		objModel.setStrDrawnOn(objGlobal.funIfNull(objBean.getStrDrawnOn(), "", objBean.getStrDrawnOn()));
		objModel.setStrBranch(objGlobal.funIfNull(objBean.getStrBranch(), "", objBean.getStrBranch()));
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrClientCode(clientCode);
		String strCrCode = "";
		String strCrNameCode = "";
		for (clsPaymentDetailsBean objPaymentDetails : objBean.getListPaymentDetailsBean()) {
			if (null != hmPaymentDtl.get(objPaymentDetails.getStrAccountCode())) {
				clsPaymentDtl objPaymentDtlModel = hmPaymentDtl.get(objPaymentDetails.getStrAccountCode());
				objPaymentDtlModel.setDblCrAmt((objPaymentDetails.getDblCreditAmt() + objPaymentDetails.getDblCreditAmt()) * currValue);
				objPaymentDtlModel.setDblDrAmt((objPaymentDetails.getDblDebitAmt() + objPaymentDetails.getDblDebitAmt()) * currValue);
				hmPaymentDtl.put(objPaymentDetails.getStrAccountCode(), objPaymentDtlModel);
			} else {
				clsPaymentDtl objPaymentDtlModel = new clsPaymentDtl();
				objPaymentDtlModel.setStrAccountCode(objPaymentDetails.getStrAccountCode());
				objPaymentDtlModel.setStrAccountName(objPaymentDetails.getStrDescription());
				objPaymentDtlModel.setStrCrDr(objPaymentDetails.getStrDC());
				objPaymentDtlModel.setStrNarration("");
				objPaymentDtlModel.setDblCrAmt(objPaymentDetails.getDblCreditAmt() * currValue);
				objPaymentDtlModel.setDblDrAmt(objPaymentDetails.getDblDebitAmt() * currValue);
				objPaymentDtlModel.setStrPropertyCode(propertyCode);
				objPaymentDtlModel.setStrDebtorCode(objPaymentDetails.getStrDebtorCode());
				hmPaymentDtl.put(objPaymentDetails.getStrAccountCode(), objPaymentDtlModel);
				if (strCrCode.equals("")) {
					strCrCode = objPaymentDetails.getStrDebtorCode();
					clsSundaryCreditorMasterModel objSunCrModel = objSundryCreditorMasterService.funGetSundryCreditorMaster(strCrCode, clientCode);
					if (objSunCrModel != null) {
						strCrNameCode = objSunCrModel.getStrFirstName();
					}
				}
			}
		}
		objModel.setStrSancCode(strCrCode);

		List<clsPaymentDtl> listPaymentDtlModel = new ArrayList<clsPaymentDtl>();
		for (Map.Entry<String, clsPaymentDtl> entry : hmPaymentDtl.entrySet()) {
			listPaymentDtlModel.add(entry.getValue());
		}
		objModel.setListPaymentDtlModel(listPaymentDtlModel);

		List<clsPaymentDebtorDtlModel> listPaymentDebtorDtlModel = new ArrayList<clsPaymentDebtorDtlModel>();
		for (clsPaymentDetailsBean objPaymentBeanDetails : objBean.getListPaymentDetailsBean()) {
			clsPaymentDebtorDtlModel objPaymentDebtorDtlModel = new clsPaymentDebtorDtlModel();

			if (objPaymentBeanDetails.getStrDC().equals("Dr")) {
				objPaymentDebtorDtlModel.setStrDebtorCode(strCrCode);
				objPaymentDebtorDtlModel.setStrDebtorName(strCrNameCode);
				objPaymentDebtorDtlModel.setStrAccountCode(objPaymentBeanDetails.getStrAccountCode());
				objPaymentDebtorDtlModel.setStrNarration("");
				objPaymentDebtorDtlModel.setStrPropertyCode(propertyCode);
				objPaymentDebtorDtlModel.setStrCrDr(objPaymentBeanDetails.getStrDC());
				objPaymentDebtorDtlModel.setDblAmt(objPaymentBeanDetails.getDblDebitAmt() * currValue);

				objPaymentDebtorDtlModel.setStrBillNo("");
				objPaymentDebtorDtlModel.setStrInvoiceNo("");
				objPaymentDebtorDtlModel.setStrGuest("");
				objPaymentDebtorDtlModel.setStrCreditNo("");
				objPaymentDebtorDtlModel.setDteBillDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objPaymentDebtorDtlModel.setDteInvoiceDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objPaymentDebtorDtlModel.setDteDueDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

				listPaymentDebtorDtlModel.add(objPaymentDebtorDtlModel);
			}
		}
		objModel.setListPaymentDebtorDtlModel(listPaymentDebtorDtlModel);
		List<clsPaymentGRNDtlModel> listPaymentGRNDtl = new ArrayList<clsPaymentGRNDtlModel>();
		for (clsPaymentGRNDtlModel objPaymentGrndtl : objBean.getListPaymentGRNDtl()) {
			if (objPaymentGrndtl.getStrSelected() != null && objPaymentGrndtl.getStrSelected().equalsIgnoreCase("Tick") && objPaymentGrndtl.getDblPayedAmt() > 0) {
				// objPaymentGrndtl.setStrVouchNo(objModel.getStrVouchNo());
				// objPaymentGrndtl.setStrClientCode(objModel.getStrClientCode());
				objPaymentGrndtl.setDblGRNAmt(objPaymentGrndtl.getDblGRNAmt() * currValue);
				objPaymentGrndtl.setDblPayedAmt(objPaymentGrndtl.getDblPayedAmt() * currValue);
				objPaymentGrndtl.setStrPropertyCode(propertyCode);
				listPaymentGRNDtl.add(objPaymentGrndtl);
			}

		}
		objModel.setListPaymentGRNDtlModel(listPaymentGRNDtl);

		List<clsPaymentScBillDtlModel> listPaymentSCDtl = new ArrayList<clsPaymentScBillDtlModel>();
		for (clsPaymentScBillDtlModel objPaymentSCdtl : objBean.getListPaymentSCDtl()) {
			if (objPaymentSCdtl.getStrSelected() != null && objPaymentSCdtl.getStrSelected().equalsIgnoreCase("Tick") && objPaymentSCdtl.getDblPayedAmt() > 0) {
				// objPaymentGrndtl.setStrVouchNo(objModel.getStrVouchNo());
				// objPaymentGrndtl.setStrClientCode(objModel.getStrClientCode());
				objPaymentSCdtl.setDblScBillAmt(objPaymentSCdtl.getDblScBillAmt() * currValue);
				objPaymentSCdtl.setDblPayedAmt(objPaymentSCdtl.getDblPayedAmt() * currValue);
				objPaymentSCdtl.setStrPropertyCode(propertyCode);
				listPaymentSCDtl.add(objPaymentSCdtl);
			}

		}
		objModel.setListPaymentSCDtlModel(listPaymentSCDtl);
		objModel.setStrCurrency(objBean.getStrCurrency());
		objModel.setDblConversion(currValue);

		return objModel;

	}

	@RequestMapping(value = "/frmPaymentReport", method = RequestMethod.GET)
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
			return new ModelAndView("frmPaymentReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPaymentReport", "command", new clsReportBean());
		} else {
			return null;
		}
		// return new ModelAndView("frmJV","command", new clsJVHdModel());
	}

	@RequestMapping(value = "/rptPaymentReport", method = RequestMethod.GET)
	private void funPaymentReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String currencyCode=objBean.getStrCurrency();
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
		
		double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		funCallPaymentdtlReport(VoucherNo, type, resp, req, currValue,conversionRate);
	}

	@RequestMapping(value = "/openRptPaymentReport", method = RequestMethod.GET)
	private void funOpenPaymentReport(HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = req.getParameter("docCode").toString();
		String type = "pdf";
		double conversionRate=1;
		double currValue=0.0;
//		double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		funCallPaymentdtlReport(VoucherNo, type, resp, req, currValue,conversionRate);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallPaymentdtlReport(String VoucherNo, String type, HttpServletResponse resp, HttpServletRequest req, double currValue,double conversionRate) {
		try {

			String strVouchNo = "",strCurrency="",currency="";
			String dteVouchDate = "";
			String strNarration = "",strPayment="",strChequeNo="",strDrawnOn="";
			// objGlobal=new clsGlobalFunctions();
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptPaymentReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select a.strVouchNo ,Date(a.dteVouchDate),a.strNarration,a.strType,ifNull(a.strChequeNo,''),ifnull(b.strBankName,''),a.strCurrency,a.dblConversion "
					+ " from tblpaymenthd a left outer join  tblbankmaster b  on a.strDrawnOn=b.strBankCode where    a.strVouchNo='" + VoucherNo + "'";

			List list = objGlobalFunctionsService.funGetListModuleWise(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strVouchNo = arrObj[0].toString();
				dteVouchDate = arrObj[1].toString();
				strNarration = arrObj[2].toString();
				strPayment = arrObj[3].toString();
				strChequeNo= arrObj[4].toString();
				strDrawnOn= arrObj[5].toString();
				strCurrency = arrObj[6].toString();
				currConversion= Double.parseDouble(arrObj[7].toString());
			}
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			
			
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurrency, clientCode);
				if (objCurrModel != null) {
					
					currency = objCurrModel.getStrCurrencyName();
				}

			
			//String sqlDtl = "select strAccountCode,strAccountName ,dblCrAmt/" + currValue + " as dblCrAmt,dblDrAmt/" + currValue + " as dblDrAmt,strNarration from tblpaymentdtl  where strVouchNo='" + VoucherNo + "' order by strAccountCode;";
			String sqlDtl = "select strAccountCode,strAccountName ,dblCrAmt /" + currConversion + " as dblCrAmt,dblDrAmt /" + currConversion + " as dblDrAmt,strNarration from tblpaymentdtl  where strVouchNo='" + VoucherNo + "' order by strAccountCode;";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPaymentReport");
			subDataset.setQuery(subQuery);

			//String sqldetordtl = "select strDebtorCode,strDebtorName,sum(dblAmt)/" + currValue + " as totdblAmt ,strCrDr,strBillNo,date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration " + "from tblpaymentdebtordtl where strVouchNo='" + VoucherNo + "'  group by strDebtorCode ,strCrDr order by strDebtorCode;";
			String sqldetordtl = "select strDebtorCode,strDebtorName,sum(dblAmt) /" + currConversion + " as totdblAmt ,strCrDr,strBillNo,date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration " + "from tblpaymentdebtordtl where strVouchNo='" + VoucherNo + "'  group by strDebtorCode ,strCrDr order by strDebtorCode;";
			JRDesignQuery detorDtl = new JRDesignQuery();
			detorDtl.setText(sqldetordtl);
			JRDesignDataset detorDataset = (JRDesignDataset) datasetMap.get("dsPaymentDetor");
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
			hm.put("strPaymentType", strPayment);
			hm.put("lblCardNo", "");
			hm.put("strCardNo", "");
			hm.put("lblDrawnOn", "");
			hm.put("strDrawnOn", "");
			hm.put("strCurrency", currency);
			
			if(strPayment.equalsIgnoreCase("Cheque")){
				hm.put("lblCardNo", "Cheque No :");
				hm.put("strCardNo", strChequeNo);
				hm.put("lblDrawnOn", "Drawn On");
				hm.put("strDrawnOn", strDrawnOn);
			}else if(strPayment.equalsIgnoreCase("Credit Card")){
				hm.put("lblCardNo", "Card No :");
				hm.put("strCardNo", strChequeNo);
				
			}
			else if(strPayment.equalsIgnoreCase("NEFT")){
				hm.put("lblCardNo", "Account No :");
				hm.put("strCardNo", strChequeNo);
				
			}
			
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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPaymentReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/loadUnPayedGrn", method = RequestMethod.GET)
	public @ResponseBody List<clsSundaryCrBillGRNDtlModel> funloadUnPayedGrn(HttpServletRequest request) {

		String sql = "";
		List<clsSundaryCrBillGRNDtlModel> listUnBillGRN = new ArrayList<clsSundaryCrBillGRNDtlModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String strDebtorCode = request.getParameter("strDebtorCode").toString();
		String strCurr = request.getParameter("strCurrency").toString();
		double currValue=1.0;
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurr, clientCode);
		if (objCurrModel != null) 
		{
			currValue = objCurrModel.getDblConvToBaseCurr();
		}

		listUnBillGRN = funUnPayedGRNList(strDebtorCode, clientCode, propCode, currValue,request);
		return listUnBillGRN;
	}


	@RequestMapping(value = "/loadUnPayedSCBill", method = RequestMethod.GET)
	public @ResponseBody List<clsPaymentScBillDtlModel> funloadUnPayedScBill(HttpServletRequest request) {

		String sql = "";
		List<clsPaymentScBillDtlModel> listUnBillGRN = new ArrayList<clsPaymentScBillDtlModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String debtorCode = request.getParameter("debtorCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		double currConversion = 1.0;
		if (objSetup != null) {
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
			if (objCurrModel != null) {
				currConversion = objCurrModel.getDblConvToBaseCurr();

			}

		}

		listUnBillGRN = funUnPayedSCList(fromDate, toDate, clientCode, propCode, currConversion, debtorCode);
		return listUnBillGRN;
	}

	private List<clsSundaryCrBillGRNDtlModel> funUnPayedGRNList(String strDebtorCode, String clientCode, String propCode
			, double currConversion,HttpServletRequest request) {

		List<clsSundaryCrBillGRNDtlModel> listUnBillGRN = new ArrayList<clsSundaryCrBillGRNDtlModel>();
		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("strDebtorCode", strDebtorCode);
		jObjData.put("currConversion", currConversion);
		jObjData.put("propCode", propCode);

		//JSONObject jObjRet = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadUnPayedGRN", jObjData);
		JSONObject jObjRet = funLoadUnPayedGRNJSON(jObjData,request);
		if (jObjRet.size() > 0) {
			JSONArray jsonArr = (JSONArray) jObjRet.get("unPayedGRN");

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jObj = (JSONObject) jsonArr.get(i);
				clsSundaryCrBillGRNDtlModel objSundaryGRN = new clsSundaryCrBillGRNDtlModel();
				objSundaryGRN.setStrGRNCode(jObj.get("strGRNCode").toString());
				objSundaryGRN.setStrGRNBIllNo(jObj.get("strBillNo").toString());
				objSundaryGRN.setDteBillDate(jObj.get("dtBillDate").toString());
				double purReturn = 0.0;
				double dblPayedAmt = 0.0;
				String sql = " select ifnull(sum(a.dblPayedAmt),0) from tblpaymentgrndtl a " + "where a.strGRNCode='" + jObj.get("strGRNCode").toString() + "' and a.strClientCode='" + clientCode + "'  ";
				List list = objGlobalFunctionsService.funGetDataList(sql, "sql");

				if (Double.parseDouble(list.get(0).toString()) > 0) {
					dblPayedAmt = Double.parseDouble(list.get(0).toString());
				}

				String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
				String sqlSalesRetrn = "select ifnull(sum(a.dblTotal),0) FROM "+webStockDB+".tblpurchasereturnhd a " + " WHERE a.strAgainst='GRN' AND a.strGRNCode='" + jObj.get("strGRNCode").toString() + "' " + " and a.strClientCode='" + clientCode + "' ";
				List listPurRet = objGlobalFunctionsService.funGetDataList(sqlSalesRetrn, "sql");
				if (listPurRet.size() > 0) {
					purReturn = Double.parseDouble(listPurRet.get(0).toString());
					purReturn = purReturn;
				}

				objSundaryGRN.setDblGRNAmt((Double.parseDouble(jObj.get("dblTotal").toString()) - purReturn - dblPayedAmt) / currConversion);

				objSundaryGRN.setDteGRNDate(jObj.get("dtGRNDate").toString());
				objSundaryGRN.setDteGRNDueDate(jObj.get("dtDueDate").toString());
				objSundaryGRN.setStrPropertyCode(propCode);
				listUnBillGRN.add(objSundaryGRN);
			}

		}

		return listUnBillGRN;
	}

	private List<clsPaymentScBillDtlModel> funUnPayedSCList(String fromDate, String toDate, String clientCode, String propCode, double currConversion, String debtorCode) {

		List<clsPaymentScBillDtlModel> listUnBillGRN = new ArrayList<clsPaymentScBillDtlModel>();
		JSONObject jObjData = new JSONObject();
		jObjData.put("clientCode", clientCode);
		jObjData.put("fromDate", fromDate);
		jObjData.put("toDate", toDate);
		jObjData.put("currConversion", currConversion);
		jObjData.put("debtorCode", debtorCode);

		JSONObject jObjRet = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funLoadUnPayedScBill", jObjData);
		if (jObjRet.size() > 0) {
			JSONArray jsonArr = (JSONArray) jObjRet.get("unPayedGRN");

			for (int i = 0; i < jsonArr.size(); i++) {
				JSONObject jObj = (JSONObject) jsonArr.get(i);
				clsPaymentScBillDtlModel objSundaryGRN = new clsPaymentScBillDtlModel();
				objSundaryGRN.setStrScCode(jObj.get("strGRNCode").toString());
				objSundaryGRN.setStrScBillNo(jObj.get("strBillNo").toString());
				objSundaryGRN.setDteBillDate(jObj.get("dtBillDate").toString());
				objSundaryGRN.setDblScBillAmt(Double.parseDouble(jObj.get("dblTotal").toString()));
				objSundaryGRN.setDteSCBillDate(jObj.get("dtGRNDate").toString());
				objSundaryGRN.setDteScBillDueDate(jObj.get("dtDueDate").toString());
				objSundaryGRN.setStrPropertyCode(propCode);
				listUnBillGRN.add(objSundaryGRN);
			}

		}

		return listUnBillGRN;
	}
	
	//Open Payment form  via Creditor Ledger
	
	@RequestMapping(value = "/frmPaymentCreditorLedger", method = RequestMethod.GET)
	public ModelAndView funOpenFormViaCreditorLedger(Map<String, Object> model,@RequestParam("glCode") String glCode,@RequestParam("creditorCode") String creditorCode,@RequestParam("closingAmt") String closingAmt, HttpServletRequest request) {
		
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		
		clsPaymentHdModel objModel=new clsPaymentHdModel();
		objModel.setCreditorCode(creditorCode);
		objModel.setAccountCode(glCode);
		objModel.setClosingAmt(Double.parseDouble(closingAmt));
		
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPayment_1", "command", objModel);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPayment", "command", objModel);
		} else {
			return null;
		}
	}

	
	
	 public JSONObject funLoadUnPayedGRNJSON(JSONObject objCLData,HttpServletRequest req)
	    {
		
		JSONObject jObjData = new JSONObject();
		
		JSONArray jArrData = new JSONArray();
	    
		String sql="";
		try
		{
			DecimalFormat df = new DecimalFormat("#.00");
			String clientCode = objCLData.get("clientCode").toString();
			String strDebtorCode = objCLData.get("strDebtorCode").toString();
			double currConversion  = Double.parseDouble(objCLData.get("currConversion").toString());
			String propCode = objCLData.get("propCode").toString();
			
			String grnCodes="";
			String querygrnCodes="";
			StringBuilder sbSql=new StringBuilder();
			sbSql.append(" select a.strNarration from tbljvhd a where a.strNarration like '%JV Generated by GRN%' and a.strClientCode='"+clientCode+"' and a.strPropertyCode='"+propCode+"' ");

			List list =objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
			if(null!=list){
				if(list.size()>0){
					for(int i=0;i<list.size();i++)
					{
					grnCodes = list.get(i).toString().split(":")[1].toString();
			    	querygrnCodes += " a.strGRNCode = '"+grnCodes+"' or";
					}
				}
			}
			
		    querygrnCodes = querygrnCodes.substring(0, querygrnCodes.length()-2);
		    String dbmms=req.getSession().getAttribute("WebStockDB").toString();
			String dbBooks=req.getSession().getAttribute("WebBooksDB").toString();
	       // st = webmms.createStatement();
			sbSql.setLength(0);
			sbSql.append(" select a.strGRNCode,a.strBillNo,a.dblTotal,DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y'),DATE_FORMAT(a.dtBillDate,'%d-%m-%Y'),DATE_FORMAT(a.dtDueDate,'%d-%m-%Y') "
	        		+ " from "+dbmms+".tblgrnhd a,"+dbmms+".tbllinkup b where a.strGRNCode not in "
	        		+ " (select b.strGRNCode from  "+dbBooks+".tblpaymentgrndtl b where b.dblGRNAmt=b.dblPayedAmt and b.strClientCode='"+clientCode+"' )   and a.strClientCode='"+clientCode+"' "
	        		+ " and a.strSuppCode=b.strMasterCode and b.strAccountCode='"+strDebtorCode+"' and ( "+querygrnCodes+" )  ");
	        
	         list =objBaseService.funGetList(sbSql, "sql");
	        if(null!=list){
	        	if(list.size()>0){
	        		for(int i=0;i<list.size();i++){
	        			Object ob[]=(Object[])list.get(i);
	        			
	        			double totAmt =Double.parseDouble(ob[2].toString());
	    		    	JSONObject jsonObj =new JSONObject();
	    		    	jsonObj.put("strGRNCode",ob[0].toString());
	    		    	jsonObj.put("strBillNo",ob[1].toString());
	    		    	jsonObj.put("dblTotal",df.format(totAmt));
	    		    	jsonObj.put("dtGRNDate",ob[3].toString());
	    		    	jsonObj.put("dtBillDate",ob[4].toString());
	    		    	jsonObj.put("dtDueDate",ob[5].toString());
	    		    	jArrData.add(jsonObj);
	        		}
	        	}
	        }
	        jObjData.put("unPayedGRN", jArrData);
	       
	        sbSql.setLength(0);
			sbSql.append(" select strSACode  docNo,strSACode  BillNo,dblTotalAmt  TotalAmt,  DATE_FORMAT(dtSADate,'%d-%m-%Y')  docDate, "
	        		+ " DATE_FORMAT(dtSADate,'%d-%m-%Y')  BillDate, DATE_FORMAT(dtSADate,'%d-%m-%Y')  dueDate "
	        		+ " from "+dbmms+".tblstockadjustmenthd  where strClientCode='"+clientCode+"' "
	        		+ " and strNarration like '%SCCode:"+strDebtorCode+"%' and  strSACode NOT IN ( "
	        		+ " SELECT pp.strGRNCode  FROM "+dbBooks+".tblpaymentgrndtl pp "
	        		+ " WHERE pp.dblGRNAmt=pp.dblPayedAmt AND pp.strClientCode='"+clientCode+"') " );    
	        
	        
	        list =objBaseService.funGetList(sbSql, "sql");
	        if(null!=list){
	        	if(list.size()>0){
	        		for(int i=0;i<list.size();i++){
	        			Object ob[]=(Object[])list.get(i);
	        			
	        			double totAmt =Double.parseDouble(ob[2].toString());
	    		    	JSONObject jsonObj =new JSONObject();
	    		    	jsonObj.put("strGRNCode",ob[0].toString());
	    		    	jsonObj.put("strBillNo",ob[1].toString());
	    		    	jsonObj.put("dblTotal",df.format(totAmt));
	    		    	jsonObj.put("dtGRNDate",ob[3].toString());
	    		    	jsonObj.put("dtBillDate",ob[4].toString());
	    		    	jsonObj.put("dtDueDate",ob[5].toString());
	    		    	jArrData.add(jsonObj);
	        		}
	        	}
	        }
	        jObjData.put("unPayedGRN", jArrData);
		    
	     
	        
	        
		}
		catch (Exception e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
			return  jObjData;
		
	    }
	
}
