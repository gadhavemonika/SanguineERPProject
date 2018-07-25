package com.sanguine.webbooks.controller;

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
import com.sanguine.bean.clsCurrencyMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.bean.clsJVBean;
import com.sanguine.webbooks.bean.clsJVDetailsBean;
import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsJVDtlModel;
import com.sanguine.webbooks.model.clsJVHdModel;
import com.sanguine.webbooks.model.clsWebBooksAccountMasterModel;
import com.sanguine.webbooks.service.clsJVService;
import com.sanguine.webbooks.service.clsWebBooksAccountMasterService;

@Controller
public class clsJVController {

	@Autowired
	private clsWebBooksAccountMasterService objWebBooksAccountMasterService;

	@Autowired
	private clsJVService objJVService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	intfBaseService objBaseService;
	
	// Open JV
	@RequestMapping(value = "/frmJV", method = RequestMethod.GET)
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
			return new ModelAndView("frmJV_1", "command", new clsJVHdModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJV", "command", new clsJVHdModel());
		} else {
			return null;
		}
		// return new ModelAndView("frmJV","command", new clsJVHdModel());
	}

	// Load Header Table Data On Form
	@RequestMapping(value = "/loadJV", method = RequestMethod.GET)
	public @ResponseBody clsJVBean funLoadHdData(HttpServletRequest request) {

		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String docCode = request.getParameter("docCode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		clsJVHdModel objJV = objJVService.funGetJVList(docCode, clientCode, propertyCode);
//		String strCurr = request.getParameter("strCurrency").toString();
//		double currValue=1.0;
//
//			currValue = objJV.getDblConversion();
//			if(currValue==0)
//			{
//				currValue=1.0;
//			}
		
		clsJVBean objJVBean = null;
		if (null == objJV) {
			objJVBean = new clsJVBean();
			objJVBean.setStrVouchNo("Invalid");
		} else {
			
			double currConversion = objJV.getDblConversion();
			if(currConversion==0){
				currConversion=1;
			}
			
			Map<String, clsJVDetailsBean> hmJVDtlBean = new HashMap<String, clsJVDetailsBean>();

			objJVBean = new clsJVBean();
			objJVBean.setStrVouchNo(objJV.getStrVouchNo());
			objJVBean.setStrNarration(objJV.getStrNarration());
			objJVBean.setDteVouchDate(objGlobal.funGetDate("yyyy/MM/dd", objJV.getDteVouchDate()));
			objJVBean.setStrUserCreated(objJV.getStrUserCreated());
			objJVBean.setStrUserEdited(objJV.getStrUserEdited());
			objJVBean.setDteDateCreated(objGlobal.funGetDate("yyyy/MM/dd", objJV.getDteDateCreated()));
			objJVBean.setDteDateEdited(objGlobal.funGetDate("yyyy/MM/dd", objJV.getDteDateEdited()));

			List<clsJVDetailsBean> listJVDtlBean = new ArrayList<clsJVDetailsBean>();

			for (clsJVDtlModel objJVDtlModel : objJV.getListJVDtlModel()) {
				clsJVDetailsBean objJVDtlBean = new clsJVDetailsBean();
				//Only for Jv Dtl
				if( objJV.getListJVDebtorDtlModel().size()>0 &&  null!=objJV.getListJVDebtorDtlModel())
				{
				// Detail for Debtor Dtl
				
				for (clsJVDebtorDtlModel objJVDebtorDtl : objJV.getListJVDebtorDtlModel()) {
					 objJVDtlBean = new clsJVDetailsBean();
					if (objJVDtlModel.getStrAccountCode().equalsIgnoreCase(objJVDebtorDtl.getStrAccountCode())) {
						if(!(hmJVDtlBean.containsKey(objJVDtlModel.getStrAccountCode()+"##"+objJVDebtorDtl.getStrDebtorCode()))){
							objJVDtlBean.setStrAccountCode(objJVDtlModel.getStrAccountCode());
							objJVDtlBean.setStrDescription(objJVDtlModel.getStrAccountName());
							objJVDtlBean.setStrDC(objJVDebtorDtl.getStrCrDr());
							objJVDtlBean.setStrDimension("");
							objJVDtlBean.setStrNarration(objJVDtlModel.getStrNarration());
							objJVDtlBean.setStrOneLineAcc(objJVDtlModel.getStrOneLine());
							objJVDtlBean.setStrDebtorCode(objJVDebtorDtl.getStrDebtorCode());
							objJVDtlBean.setStrDebtorName(objJVDebtorDtl.getStrDebtorName());
							objJVDtlBean.setStrDebtorYN("Y");
							hmJVDtlBean.put(objJVDtlModel.getStrAccountCode()+"##"+objJVDebtorDtl.getStrDebtorCode(), objJVDtlBean);
							if (objJVDebtorDtl.getStrCrDr().equalsIgnoreCase("Cr")) {
								objJVDtlBean.setDblCreditAmt(objJVDebtorDtl.getDblAmt() / currConversion);
								objJVDtlBean.setDblDebitAmt(0.0000);
							} else {
								objJVDtlBean.setDblCreditAmt(0.0000);
								objJVDtlBean.setDblDebitAmt(objJVDebtorDtl.getDblAmt() / currConversion);
							}
							listJVDtlBean.add(objJVDtlBean);
						}
						} else {
							objJVDtlBean.setStrAccountCode(objJVDtlModel.getStrAccountCode());
							objJVDtlBean.setStrDescription(objJVDtlModel.getStrAccountName());
							objJVDtlBean.setStrDC(objJVDtlModel.getStrCrDr());
							objJVDtlBean.setDblCreditAmt(objJVDtlModel.getDblCrAmt() / currConversion);
							objJVDtlBean.setDblDebitAmt(objJVDtlModel.getDblDrAmt() / currConversion);
							objJVDtlBean.setStrDimension("");
							objJVDtlBean.setStrNarration(objJVDtlModel.getStrNarration());
							objJVDtlBean.setStrOneLineAcc(objJVDtlModel.getStrOneLine());
							objJVDtlBean.setStrDebtorCode("");
							objJVDtlBean.setStrDebtorName("");
							objJVDtlBean.setStrDebtorYN("N");
							hmJVDtlBean.put(objJVDtlModel.getStrAccountCode(), objJVDtlBean);
							listJVDtlBean.add(objJVDtlBean);
						}
						
				    }
					}else{
						 objJVDtlBean = new clsJVDetailsBean();
							objJVDtlBean.setStrAccountCode(objJVDtlModel.getStrAccountCode());
							objJVDtlBean.setStrDescription(objJVDtlModel.getStrAccountName());
							objJVDtlBean.setStrDC(objJVDtlModel.getStrCrDr());
							objJVDtlBean.setDblCreditAmt(objJVDtlModel.getDblCrAmt() / currConversion);
							objJVDtlBean.setDblDebitAmt(objJVDtlModel.getDblDrAmt() / currConversion);
							objJVDtlBean.setStrDimension("");
							objJVDtlBean.setStrNarration(objJVDtlModel.getStrNarration());
							objJVDtlBean.setStrOneLineAcc(objJVDtlModel.getStrOneLine());
							objJVDtlBean.setStrDebtorCode("");
							objJVDtlBean.setStrDebtorName("");
							objJVDtlBean.setStrDebtorYN("N");
							hmJVDtlBean.put(objJVDtlModel.getStrAccountCode(), objJVDtlBean);
							listJVDtlBean.add(objJVDtlBean);
					}

				

			}

			for (clsJVDebtorDtlModel objJVDebtorDtl : objJV.getListJVDebtorDtlModel()) {
				
				if(hmJVDtlBean.containsKey(objJVDebtorDtl.getStrAccountCode()))
				{
					clsJVDetailsBean objJVDtlBean = hmJVDtlBean.get(objJVDebtorDtl.getStrAccountCode()+"##"+objJVDebtorDtl.getStrDebtorCode());
	
					clsJVDetailsBean objJVDtlNew = new clsJVDetailsBean();
					objJVDtlNew.setStrAccountCode(objJVDtlBean.getStrAccountCode());
					objJVDtlNew.setStrDescription(objJVDtlBean.getStrDescription());
					objJVDtlNew.setStrDC(objJVDtlBean.getStrDC());
					objJVDtlNew.setStrDimension("");
					objJVDtlNew.setStrNarration(objJVDtlBean.getStrNarration());
					objJVDtlNew.setStrOneLineAcc(objJVDtlBean.getStrOneLineAcc());
	
					objJVDtlNew.setStrDebtorCode(objJVDebtorDtl.getStrDebtorCode());
					objJVDtlNew.setStrDebtorName(objJVDebtorDtl.getStrDebtorName());
					objJVDtlNew.setStrDebtorYN("Y");
	
					if (objJVDebtorDtl.getStrCrDr().equalsIgnoreCase("Cr")) {
						objJVDtlNew.setDblCreditAmt(objJVDebtorDtl.getDblAmt() / currConversion);
						objJVDtlNew.setDblDebitAmt(0.0000);
					} else {
						objJVDtlNew.setDblCreditAmt(0.0000);
						objJVDtlNew.setDblDebitAmt(objJVDebtorDtl.getDblAmt() / currConversion);
					}
				}
				
//				 listJVDtlBean.add(objJVDtlNew);
				/*
				 * for(clsJVDetailsBean objJVDtlBean:listJVDtlBean) {
				 * if(objJVDtlBean
				 * .getStrAccountCode().equals(objJVDebtorDtl.getStrAccountCode
				 * ())) {
				 * objJVDtlBean.setStrDebtorCode(objJVDebtorDtl.getStrDebtorCode
				 * ());
				 * objJVDtlBean.setStrDebtorName(objJVDebtorDtl.getStrDebtorName
				 * ()); objJVDtlBean.setStrDebtorYN("Y"); } }
				 */
			}
			objJVBean.setListJVDtlBean(listJVDtlBean);
			objJVBean.setDblConversion(currConversion); 
			objJVBean.setStrCurrency(objJV.getStrCurrency());
			objJVBean.setStrSource(objJV.getStrSource());
			hmJVDtlBean = null;
		}
		return objJVBean;
	}

	// Load Dtl Table Data On Form
	@RequestMapping(value = "/loadJVDtl", method = RequestMethod.POST)
	public @ResponseBody clsJVDtlModel funLoadDtlData(HttpServletRequest request) {

		String sql = "";
		clsJVDtlModel objJVDtl = new clsJVDtlModel();
		return objJVDtl;
	}

	// Save or Update JV
	@RequestMapping(value = "/saveJV", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsJVBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String webBooksModuleType="AR";
			
			if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
				webBooksModuleType="APGL";
			}
			
			clsJVHdModel objHdModel=funGenerateJV(objBean, userCode, clientCode, webBooksModuleType, req,"User");
			
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "JV No : ".concat(objHdModel.getStrVouchNo()));
			req.getSession().setAttribute("rptVoucherNo", objHdModel.getStrVouchNo());
			return new ModelAndView("redirect:/frmJV.html");
		} else {
			return new ModelAndView("frmJV");
		}
	}
	
	
	public clsJVHdModel funGenerateJV(clsJVBean objBean,String userCode,String clientCode,String webBooksModuleType,HttpServletRequest req, String source)
	{
		clsJVHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode,webBooksModuleType, req, source);
		objJVService.funAddUpdateJVHd(objHdModel);
		return objHdModel;
	}
	
	

	// Convert bean to model function
	// Convert bean to model function
	private clsJVHdModel funPrepareHdModel(clsJVBean objBean, String userCode, String clientCode, String webBooksModuleType,HttpServletRequest request, String source) {

		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		clsJVHdModel objModel = new clsJVHdModel();
		String strCurr = objBean.getStrCurrency();
	
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurr, clientCode);
//		if(objBean.getDblConversion() ==0) 
//		{
//			currValue =1.0;
//		}else{
//			currValue=objBean.getDblConversion();
//		}
		double currConversion = 1.0;
		double ConversionRate = 1.0;
		if(objBean.getDblConversion()>0)
		{
			currConversion=objBean.getDblConversion();
			ConversionRate=objBean.getDblConversion();
			if((!source.equals("User"))){
				currConversion=1.0;
			}
		}

		Map<String, clsJVDtlModel> hmJVDtl = new HashMap<String, clsJVDtlModel>();

		if (objBean.getStrVouchNo().isEmpty()) // New Entry
		{
			String documentNo = objGlobal.funGenerateDocumentCodeWebBook("frmJV", objBean.getDteVouchDate(), request);
			objModel.setStrVouchNo(documentNo);
			long lastNo = 0;
			objModel.setIntVouchNum(String.valueOf(lastNo));
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} 
		else // Update
		{
			objModel.setStrUserCreated(userCode);
			objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrVouchNo(objBean.getStrVouchNo());
		}

		if(source.equals("User"))
		{
			objModel.setStrSource(source);
			objModel.setStrSourceDocNo(objModel.getStrVouchNo());
		}
		else
		{
			objModel.setStrSource(source);
			objModel.setStrSourceDocNo(objBean.getStrSourceDocNo());
		}
		
		objModel.setIntId(0);
		objModel.setStrNarration(objGlobal.funIfNull(objBean.getStrNarration(), "NA", objBean.getStrNarration()));
		objModel.setStrType(objGlobal.funIfNull(objBean.getStrType(), "None", objBean.getStrType()));
		objModel.setStrSancCode(objGlobal.funIfNull(objBean.getStrSancCode(), "NA", objBean.getStrSancCode()));
		objModel.setDteVouchDate(objGlobal.funGetDateAndTime("yyyy-MM-dd", objBean.getDteVouchDate()));
		objModel.setIntVouchMonth(objGlobal.funGetMonth());
		objModel.setDblAmt(objBean.getDblAmt() * currConversion);
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrMasterPOS("");
		if (request.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			objModel.setStrModuleType("APGL");
		} else {
			objModel.setStrModuleType("AR");
		}
		objModel.setStrTransMode("R");
		objModel.setStrTransType("A");
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrClientCode(clientCode);
		objModel.setIntVouchNum("");
		
		// Comment For Same Account Code debtor to debtor transaction done 
/*		if(source.equalsIgnoreCase("User")){
			List<clsJVDtlModel> listJVDtlModel = new ArrayList<clsJVDtlModel>();
			for (clsJVDetailsBean objJVDetails : objBean.getListJVDtlBean()) {
			
					if (null != hmJVDtl.get(objJVDetails.getStrAccountCode())) {
						clsJVDtlModel objJVDtlModel = hmJVDtl.get(objJVDetails.getStrAccountCode());
						objJVDtlModel.setDblCrAmt((objJVDtlModel.getDblCrAmt() + objJVDetails.getDblCreditAmt()) * currConversion);
						objJVDtlModel.setDblDrAmt((objJVDtlModel.getDblDrAmt() + objJVDetails.getDblDebitAmt()) * currConversion);
						hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
					} else {
						clsJVDtlModel objJVDtlModel = new clsJVDtlModel();
						objJVDtlModel.setStrAccountCode(objJVDetails.getStrAccountCode());
						objJVDtlModel.setStrAccountName(objJVDetails.getStrDescription());
						objJVDtlModel.setStrCrDr(objJVDetails.getStrDC());
						objJVDtlModel.setStrNarration(objJVDetails.getStrNarration());
						objJVDtlModel.setStrOneLine(objJVDetails.getStrOneLineAcc());
						objJVDtlModel.setDblCrAmt(objJVDetails.getDblCreditAmt() * currConversion);
						objJVDtlModel.setDblDrAmt(objJVDetails.getDblDebitAmt() * currConversion);
						objJVDtlModel.setStrPropertyCode(propertyCode);
						listJVDtlModel.add(objJVDtlModel);
		
						hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
//					}
			}

			
		}else{
		for (clsJVDetailsBean objJVDetails : objBean.getListJVDtlBean()) {
			if(null==objJVDetails.getStrDebtorCode() || objJVDetails.getStrDebtorCode().equals(""))
			{
				if (null != hmJVDtl.get(objJVDetails.getStrAccountCode())) {
					clsJVDtlModel objJVDtlModel = hmJVDtl.get(objJVDetails.getStrAccountCode());
					objJVDtlModel.setDblCrAmt((objJVDtlModel.getDblCrAmt() + objJVDetails.getDblCreditAmt()) * currConversion);
					objJVDtlModel.setDblDrAmt((objJVDtlModel.getDblDrAmt() + objJVDetails.getDblDebitAmt()) * currConversion);
					hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
				} else {
					clsJVDtlModel objJVDtlModel = new clsJVDtlModel();
					objJVDtlModel.setStrAccountCode(objJVDetails.getStrAccountCode());
					objJVDtlModel.setStrAccountName(objJVDetails.getStrDescription());
					objJVDtlModel.setStrCrDr(objJVDetails.getStrDC());
					objJVDtlModel.setStrNarration(objJVDetails.getStrNarration());
					objJVDtlModel.setStrOneLine(objJVDetails.getStrOneLineAcc());
					objJVDtlModel.setDblCrAmt(objJVDetails.getDblCreditAmt() * currConversion);
					objJVDtlModel.setDblDrAmt(objJVDetails.getDblDebitAmt() * currConversion);
					objJVDtlModel.setStrPropertyCode(propertyCode);
					// listJVDtlModel.add(objJVDtlModel);
	
					hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
				}
			}
		}
		}

		List<clsJVDtlModel> listJVDtlModel = new ArrayList<clsJVDtlModel>();
		for (Map.Entry<String, clsJVDtlModel> entry : hmJVDtl.entrySet()) {
			listJVDtlModel.add(entry.getValue());
		}*/
		List<clsJVDtlModel> listJVDtlModel = new ArrayList<clsJVDtlModel>();
		
		if(source.equalsIgnoreCase("User")){
			
			for (clsJVDetailsBean objJVDetails : objBean.getListJVDtlBean()) {
						clsJVDtlModel objJVDtlModel = new clsJVDtlModel();
						objJVDtlModel.setStrAccountCode(objJVDetails.getStrAccountCode());
						objJVDtlModel.setStrAccountName(objJVDetails.getStrDescription());
						objJVDtlModel.setStrCrDr(objJVDetails.getStrDC());
						objJVDtlModel.setStrNarration(objJVDetails.getStrNarration());
						objJVDtlModel.setStrOneLine(objJVDetails.getStrOneLineAcc());
						objJVDtlModel.setDblCrAmt(objJVDetails.getDblCreditAmt() * currConversion);
						objJVDtlModel.setDblDrAmt(objJVDetails.getDblDebitAmt() * currConversion);
						objJVDtlModel.setStrPropertyCode(propertyCode);
						listJVDtlModel.add(objJVDtlModel);
		
						hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
//					}
			}

			
		}else{
		for (clsJVDetailsBean objJVDetails : objBean.getListJVDtlBean()) {
			if(null==objJVDetails.getStrDebtorCode() || objJVDetails.getStrDebtorCode().equals(""))
			{
				if (null != hmJVDtl.get(objJVDetails.getStrAccountCode())) {
					clsJVDtlModel objJVDtlModel = hmJVDtl.get(objJVDetails.getStrAccountCode());
					objJVDtlModel.setDblCrAmt((objJVDtlModel.getDblCrAmt() + objJVDetails.getDblCreditAmt()) * currConversion);
					objJVDtlModel.setDblDrAmt((objJVDtlModel.getDblDrAmt() + objJVDetails.getDblDebitAmt()) * currConversion);
					hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
				} else {
					clsJVDtlModel objJVDtlModel = new clsJVDtlModel();
					objJVDtlModel.setStrAccountCode(objJVDetails.getStrAccountCode());
					objJVDtlModel.setStrAccountName(objJVDetails.getStrDescription());
					objJVDtlModel.setStrCrDr(objJVDetails.getStrDC());
					objJVDtlModel.setStrNarration(objJVDetails.getStrNarration());
					objJVDtlModel.setStrOneLine(objJVDetails.getStrOneLineAcc());
					objJVDtlModel.setDblCrAmt(objJVDetails.getDblCreditAmt() * currConversion);
					objJVDtlModel.setDblDrAmt(objJVDetails.getDblDebitAmt() * currConversion);
					objJVDtlModel.setStrPropertyCode(propertyCode);
					// listJVDtlModel.add(objJVDtlModel);
	
					hmJVDtl.put(objJVDetails.getStrAccountCode(), objJVDtlModel);
				}
			}
		}
		
		
		for (Map.Entry<String, clsJVDtlModel> entry : hmJVDtl.entrySet()) {
			listJVDtlModel.add(entry.getValue());
		}
		
		}

		
		objModel.setListJVDtlModel(listJVDtlModel);

		List<clsJVDebtorDtlModel> listJVDebtorDtlModel = new ArrayList<clsJVDebtorDtlModel>();
		for (clsJVDetailsBean objJVDetails : objBean.getListJVDtlBean()) {
			clsJVDebtorDtlModel objJVDebtorDtlModel = new clsJVDebtorDtlModel();
			if(null!=objJVDetails.getStrDebtorCode() && !objJVDetails.getStrDebtorCode().equals(""))
			{
				objJVDebtorDtlModel.setStrDebtorCode(objJVDetails.getStrDebtorCode());
				objJVDebtorDtlModel.setStrDebtorName(objJVDetails.getStrDebtorName());
				objJVDebtorDtlModel.setStrAccountCode(objJVDetails.getStrAccountCode());
				objJVDebtorDtlModel.setStrNarration(objJVDetails.getStrNarration());
				objJVDebtorDtlModel.setStrPropertyCode(propertyCode);
				objJVDebtorDtlModel.setStrCrDr(objJVDetails.getStrDC());
				if (objJVDetails.getStrDC().equals("Cr")) {
					objJVDebtorDtlModel.setDblAmt(objJVDetails.getDblCreditAmt() * currConversion);
				} else {
					objJVDebtorDtlModel.setDblAmt(objJVDetails.getDblDebitAmt() * currConversion);
				}
	
				objJVDebtorDtlModel.setStrBillNo(objGlobal.funIfNull(objJVDetails.getStrBillNo(), "NA", objJVDetails.getStrBillNo()));
				objJVDebtorDtlModel.setStrInvoiceNo(objGlobal.funIfNull(objJVDetails.getStrInvoiceNo(), "NA", objJVDetails.getStrInvoiceNo()));
				objJVDebtorDtlModel.setStrGuest(objGlobal.funIfNull(objJVDetails.getStrGuest(), "NA", objJVDetails.getStrGuest()));
				objJVDebtorDtlModel.setStrPOSCode(objGlobal.funIfNull(objJVDetails.getStrPOSCode(), "NA", objJVDetails.getStrPOSCode()));
				objJVDebtorDtlModel.setStrPOSName(objGlobal.funIfNull(objJVDetails.getStrPOSName(), "NA", objJVDetails.getStrPOSName()));
				objJVDebtorDtlModel.setStrRegistrationNo(objGlobal.funIfNull(objJVDetails.getStrRegistrationNo(), "NA", objJVDetails.getStrRegistrationNo()));
				objJVDebtorDtlModel.setStrCreditNo(objGlobal.funIfNull(objJVDetails.getStrCreditNo(), "NA", objJVDetails.getStrCreditNo()));
				objJVDebtorDtlModel.setDteBillDate(objGlobal.funIfNull(objJVDetails.getDteBillDate(), objGlobal.funGetCurrentDateTime("yyyy-MM-dd"), objJVDetails.getDteBillDate()));
				objJVDebtorDtlModel.setDteInvoiceDate(objGlobal.funIfNull(objJVDetails.getDteInvoiceDate(), objGlobal.funGetCurrentDateTime("yyyy-MM-dd"), objJVDetails.getDteInvoiceDate()));
				objJVDebtorDtlModel.setDteDueDate(objGlobal.funIfNull(objJVDetails.getDteDueDate(), objGlobal.funGetCurrentDateTime("yyyy-MM-dd"), objJVDetails.getDteDueDate()));
	
				listJVDebtorDtlModel.add(objJVDebtorDtlModel);
			}
		}

		objModel.setListJVDebtorDtlModel(listJVDebtorDtlModel);
		objModel.setStrCurrency(objBean.getStrCurrency());
		objModel.setDblConversion(ConversionRate);

		return objModel;
	}

	
	@RequestMapping(value = "/loadMemberDetails", method = RequestMethod.GET)
	public @ResponseBody clsJVDebtorDtlModel funLoadMemberDetails(@RequestParam("debtorCode") String debtorCode, HttpServletRequest req) {
		clsJVDebtorDtlModel objJVDebtorDtlModel = new clsJVDebtorDtlModel();

		String sql = "select strMemberCode,strFirstName,strMiddleName,strLastName " + "from vMemberData where strMemberCode='" + debtorCode + "'";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");

		Object[] arrObj = (Object[]) list.get(0);
		objJVDebtorDtlModel.setStrDebtorCode(arrObj[0].toString());
		objJVDebtorDtlModel.setStrDebtorName(arrObj[1].toString());

		return objJVDebtorDtlModel;
	}

	// Get Non Debtor Account Code And Name
	@RequestMapping(value = "/loadAccForNonDebtor", method = RequestMethod.GET)
	public @ResponseBody clsWebBooksAccountMasterModel funGetAccountCodeAndName(@RequestParam("acCode") String acCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebBooksAccountMasterModel objModel = objWebBooksAccountMasterService.funGetAccountForNonDebtor(acCode, clientCode);
		if (null == objModel) {
			objModel = new clsWebBooksAccountMasterModel();
			objModel.setStrAccountCode("Invalid Code");
		}

		return objModel;
	}

	@RequestMapping(value = "/frmJVReport", method = RequestMethod.GET)
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
			return new ModelAndView("frmJVReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmJVReport", "command", new clsReportBean());
		} else {
			return null;
		}
		// return new ModelAndView("frmJV","command", new clsJVHdModel());
	}

	@RequestMapping(value = "/rptJVReport", method = RequestMethod.GET)
	private void funJobOrderReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		double dblConversion=1.0;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
	
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		StringBuilder sbSql = new StringBuilder();
//		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currencyCode+"' and strClientCode='"+clientCode+"' ");
//		try
//		{
//			List list = objBaseService.funGetList(sbSql,"sql");
//			conversionRate=Double.parseDouble(list.get(0).toString());
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		funCallJVdtlReport(VoucherNo, type, resp, req,dblConversion);
	}
	

	@RequestMapping(value = "/rptJVReportFromTrans", method = RequestMethod.GET)
	private void funJVReportFromTrans( HttpServletResponse resp, HttpServletRequest req) {
		String VoucherNo = req.getParameter("docCode").toString();
		String type = "pdf";
		double dbl=1.0;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
	
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		StringBuilder sbSql = new StringBuilder();
//		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currencyCode+"' and strClientCode='"+clientCode+"' ");
//		try
//		{
//			List list = objBaseService.funGetList(sbSql,"sql");
//			conversionRate=Double.parseDouble(list.get(0).toString());
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		funCallJVdtlReport(VoucherNo, type, resp, req,dbl);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallJVdtlReport(String VoucherNo, String type, HttpServletResponse resp, HttpServletRequest req,double a) {
		try {

			String strVouchNo = "",strCurr="";
			String dteVouchDate = "",currency="";
			String strNarration = "";
			double conversionRate=1.0;
//			String strCurr = req.getSession().getAttribute("currValue").toString();
//			double currValue = Double.parseDouble(strCurr);
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webbooks/rptJVReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select strVouchNo ,Date(dteVouchDate),strNarration,strCurrency,dblConversion from tbljvhd where strVouchNo='" + VoucherNo + "'";

			List list = objGlobalFunctionsService.funGetListModuleWise(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strVouchNo = arrObj[0].toString();
				dteVouchDate = arrObj[1].toString();
				strNarration = arrObj[2].toString();
				strCurr = arrObj[3].toString();
				conversionRate=Double.parseDouble(arrObj[4].toString());
			}
			
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		
		
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(strCurr, clientCode);
				if (objCurrModel != null) {
					
					currency = objCurrModel.getStrCurrencyName();
				}

		
			
			//String sqlDtl = "select a.strVouchNo,a.strAccountCode,a.strAccountName,a.strCrDr,a.dblDrAmt/" + currValue + " as dblDrAmt,a.dblCrAmt/" + currValue + " as dblCrAmt,a.strNarration,a.strNarration, " + " a.strOneLine,a.strClientCode,a.strPropertyCode  from tbljvdtl a where a.strVouchNo='" + VoucherNo + "' order by a.strAccountCode;";
			String sqlDtl = "select a.strVouchNo,a.strAccountCode,a.strAccountName,a.strCrDr,a.dblDrAmt /" + conversionRate + " as dblDrAmt,a.dblCrAmt /" + conversionRate + " as dblCrAmt,a.strNarration,a.strNarration, " + " a.strOneLine,a.strClientCode,a.strPropertyCode  from tbljvdtl a where a.strVouchNo='" + VoucherNo + "' order by a.strAccountCode;";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsJVdtl");
			subDataset.setQuery(subQuery);

			// String trunTemp="truncate table tbltempjvorderdtl";
			//
			// objJVService.funInsertJV(trunTemp);
			//
			// String
			// sqltempDtlDr="insert into tbltempjvorderdtl (strVouchNo,strDebtorCode,strDebtorName,"
			// +
			// "dblAmt,strCrDr,strBillNo,dteBillDate,strInvoiceNo,dteInvoiceDate,strNarration ) "
			// +
			// "select strVouchNo, strDebtorCode,strDebtorName,sum(dblAmt),strCrDr,strBillNo,"
			// +
			// "date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration "
			// +"from tbljvdebtordtl where strVouchNo='"+VoucherNo+"' and strCrDr='Dr'  "
			// +"group by strDebtorCode order by strDebtorCode ";
			//
			//
			// objJVService.funInsertJV(sqltempDtlDr);
			//
			//
			// String
			// sqltempDtlCr="insert into tbltempjvorderdtl (strVouchNo,strDebtorCode,strDebtorName,"
			// +
			// "dblAmt,strCrDr,strBillNo,dteBillDate,strInvoiceNo,dteInvoiceDate,strNarration ) "
			// +
			// "select strVouchNo, strDebtorCode,strDebtorName,sum(dblAmt),strCrDr,strBillNo,"
			// +
			// "date(dteBillDate),strInvoiceNo,date(dteInvoiceDate),strNarration "
			// +"from tbljvdebtordtl where strVouchNo='"+VoucherNo+"' and strCrDr='Cr'  "
			// +"group by strDebtorCode order by strDebtorCode ";
			//
			// objJVService.funInsertJV(sqltempDtlCr);

			/*String sqldetordtl = "select strVouchNo, strDebtorCode,strDebtorName,sum(dblAmt)/" + currValue + " as dblAmt,strCrDr,strBillNo," + "  DATE_FORMAT(dteBillDate,'%d-%m-%Y') as dteBillDate,strInvoiceNo, DATE_FORMAT(dteInvoiceDate,'%d-%m-%Y') as dteInvoiceDate,strNarration " + " from tbljvdebtordtl  where strVouchNo='" + VoucherNo + "'"
					+ " group by strDebtorCode ,strCrDr order by strDebtorCode;";*/
			
			String sqldetordtl = "select strVouchNo, strDebtorCode,strDebtorName,sum(dblAmt) /" + conversionRate + " as dblAmt,strCrDr,strBillNo," + "  DATE_FORMAT(dteBillDate,'%d-%m-%Y') as dteBillDate,strInvoiceNo, DATE_FORMAT(dteInvoiceDate,'%d-%m-%Y') as dteInvoiceDate,strNarration " + " from tbljvdebtordtl  where strVouchNo='" + VoucherNo + "'"
					+ " group by strDebtorCode ,strCrDr order by strDebtorCode;";

			JRDesignQuery detorDtl = new JRDesignQuery();
			detorDtl.setText(sqldetordtl);
			JRDesignDataset detorDataset = (JRDesignDataset) datasetMap.get("dsJvdtltemp");
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
			hm.put("currValue",  conversionRate);
			hm.put("strCurrency", currency);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptJVReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
