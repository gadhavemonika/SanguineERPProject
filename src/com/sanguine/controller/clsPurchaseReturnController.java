package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
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
import com.sanguine.bean.clsPurchaseReturnBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseReturnDtlModel;
import com.sanguine.model.clsPurchaseReturnHdModel;
import com.sanguine.model.clsPurchaseReturnHdModel_ID;
import com.sanguine.model.clsPurchaseReturnTaxDtlModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPurchaseReturnService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsPurchaseReturnController {

	final static Logger logger = Logger.getLogger(clsPurchaseReturnController.class);
	@Autowired
	ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsPurchaseReturnService objPurchaseReturnService;

	@Autowired
	private clsLocationMasterService objLocService;

	@Autowired
	private clsSupplierMasterService objSupplierMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsLinkUpService objLinkupService;

	@Autowired
	private clsProductMasterService objProductMasterService;
	
	@Autowired
	private clsJVGeneratorController objJVGen;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;
	
	
	/**
	 * Open Purchase Return Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmPurchaseReturn", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseReturnForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmWebStockHelpPurchaseReturn");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		/**
		 * Checking Authorization
		 */
		String docCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			docCode = request.getParameter("authorizationPRCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationPRCode", docCode);
		}
		model.put("urlHits", urlHits);

		/**
		 * Set Process
		 */
		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmPurchaseReturn", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}
		
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseReturn_1", "command", new clsPurchaseReturnBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseReturn", "command", new clsPurchaseReturnBean());
		} else {
			return null;
		}

	}

	/**
	 * Save Purchase Return Form
	 * 
	 * @param PRBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/savePR", method = RequestMethod.POST)
	public ModelAndView funSavePurchaseReturn(@ModelAttribute("command") clsPurchaseReturnBean PRBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String PRCode="";
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		//double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		double currValue = PRBean.getDblConversion();
		if(currValue==0){
			currValue=1;
		}
		if (!result.hasErrors()) {
			clsPurchaseReturnHdModel objPRModel = null;
			List<clsPurchaseReturnDtlModel> listonPRDtl = PRBean.getListPurchaseReturnDtl();
			if (null != listonPRDtl && listonPRDtl.size() > 0) {
				objPRModel = funPrepareModelHd(PRBean, req, currValue);
				objPRModel.setStrJVNo("");
				objPurchaseReturnService.funAddPRHd(objPRModel);
				String strPrCode = objPRModel.getStrPRCode();
				objPurchaseReturnService.funDeleteDtl(strPrCode, clientCode);
				for (clsPurchaseReturnDtlModel ob : listonPRDtl) {
					if (ob.getStrProdCode() != null) {
						ob.setDblDiscount(ob.getDblDiscount() * currValue);
						ob.setDblTotalPrice(ob.getDblTotalPrice() * currValue);
						ob.setDblUnitPrice(ob.getDblUnitPrice() * currValue);
						ob.setStrPRCode(strPrCode);
						ob.setStrProdChar(objGlobalFunctions.funIfNull(ob.getStrProdChar(), "NA", ob.getStrProdChar()));
						ob.setStrClientCode(clientCode);
						objPurchaseReturnService.funAddUpdatePRDtl(ob);
					}

				}
				
				List<clsPurchaseReturnTaxDtlModel> listPRTaxDtlModel = PRBean.getListPurchaseReturnTaxDtl();
				if (null != listPRTaxDtlModel) {
					objPurchaseReturnService.funDeletePRTaxDtl(PRBean.getStrPRCode(), clientCode);
					for (clsPurchaseReturnTaxDtlModel obTaxDtl : listPRTaxDtlModel) {
						if (null != obTaxDtl.getStrTaxCode()) {
							obTaxDtl.setStrPRCode(strPrCode);
							obTaxDtl.setStrClientCode(clientCode);
							obTaxDtl.setStrTaxableAmt(obTaxDtl.getStrTaxableAmt() * currValue);
							obTaxDtl.setStrTaxAmt(obTaxDtl.getStrTaxAmt() * currValue);
							objPurchaseReturnService.funAddUpdatePRTaxDtl(obTaxDtl);
						}
					}
				}
				PRCode=objPRModel.getStrPRCode();
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "PR Code : ".concat(objPRModel.getStrPRCode()));
				req.getSession().setAttribute("rptPRCode", objPRModel.getStrPRCode());
				req.getSession().setAttribute("rptCurrencyCode", objPRModel.getStrCurrency());
			}

			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			if (objCompModel.getStrWebBookModule().equals("Yes")) {
				
				boolean authorisationFlag=false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.containsKey("Purchase Return")) {
						authorisationFlag=hmAuthorization.get("Purchase Return");
					}
				}
				
				if(!authorisationFlag)
				{
					String retuenVal=objJVGen.funGenrateJVforPurchaseReturn(PRCode, clientCode, userCode, propCode, req);
					String JVGenMessage="";
					String[] arrVal=retuenVal.split("!");
					
					boolean flgJVPosting=true;
					if(arrVal[0].equals("ERROR"))
					{
						JVGenMessage=arrVal[1];
						flgJVPosting=false;
					}else{
						objPRModel.setStrJVNo(arrVal[0]);
						objPurchaseReturnService.funAddPRHd(objPRModel);
						}
					req.getSession().setAttribute("JVGen", flgJVPosting);
					req.getSession().setAttribute("JVGenMessage", JVGenMessage);
				}
			}
			
			return new ModelAndView("redirect:/frmPurchaseReturn.html?saddr=" + urlHits);

		} else {
			return new ModelAndView("redirect:/frmPurchaseReturn.html?saddr=" + urlHits);
		}

	}

	/**
	 * Prepare Purchase Return hdModel
	 * 
	 * @param PRBean
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsPurchaseReturnHdModel funPrepareModelHd(clsPurchaseReturnBean PRBean, HttpServletRequest req, double currValue) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		/*
		 * String
		 * propCode=req.getSession().getAttribute("propertyCode").toString();
		 * String
		 * startDate=req.getSession().getAttribute("startDate").toString();
		 * String
		 * res=req.getSession().getAttribute("PRAuthorization").toString();
		 */
		boolean res = false;
		if (null != req.getSession().getAttribute("hmAuthorization")) {
			HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
			if (hmAuthorization.get("Purchase Return")) {
				res = true;
			}
		}
		long lastNo = 0;
		clsPurchaseReturnHdModel objPRHd;

		if (PRBean.getStrPRCode().trim().length() == 0) {
			String strPRCode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseReturn", PRBean.getDtPRDate(), req);
			objPRHd = new clsPurchaseReturnHdModel(new clsPurchaseReturnHdModel_ID(strPRCode, clientCode));

			objPRHd.setIntid(lastNo);
			objPRHd.setStrUserCreated(userCode);
			objPRHd.setDtDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsPurchaseReturnHdModel prModel = objPurchaseReturnService.funGetObject(PRBean.getStrPRCode(), clientCode);
			if (null == prModel) {
				String strPRCode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseReturn", PRBean.getDtPRDate(), req);
				objPRHd = new clsPurchaseReturnHdModel(new clsPurchaseReturnHdModel_ID(strPRCode, clientCode));
				objPRHd.setIntid(lastNo);
				objPRHd.setStrUserCreated(userCode);
				objPRHd.setDtDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmPurchaseReturn", req)) {
					funSaveAudit(PRBean.getStrPRCode(), "Edit", req);
				}

				objPRHd = new clsPurchaseReturnHdModel(new clsPurchaseReturnHdModel_ID(PRBean.getStrPRCode(), clientCode));
			}
		}

		objPRHd.setStrLocCode(PRBean.getStrLocCode());
		objPRHd.setDtPRDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", PRBean.getDtPRDate()));
		objPRHd.setStrSuppCode(PRBean.getStrSuppCode());

		if (res) {
			objPRHd.setStrAuthorise("No");

		} else {
			objPRHd.setStrAuthorise("Yes");
		}
		objPRHd.setStrPRNo(objGlobalFunctions.funIfNull(PRBean.getStrPRNo(), "", PRBean.getStrPRNo()));
		objPRHd.setStrMInBy(PRBean.getStrMInBy());
		objPRHd.setStrVehNo(PRBean.getStrVehNo());
		objPRHd.setStrTimeInOut(PRBean.getStrTimeInOut());
		objPRHd.setStrAgainst(PRBean.getStrAgainst());
		objPRHd.setStrGRNCode(PRBean.getStrGRNCode());
		objPRHd.setDblSubTotal(PRBean.getDblSubTotal() * currValue);
		objPRHd.setDblDisRate(PRBean.getDblDisRate());
		objPRHd.setDblDisAmt(PRBean.getDblDisAmt() * currValue);
		objPRHd.setDblExtra(PRBean.getDblExtra() * currValue);
		objPRHd.setDblTotal(PRBean.getDblTotal() * currValue);
		objPRHd.setStrUserModified(userCode);
		objPRHd.setStrNarration(PRBean.getStrNarration());
		objPRHd.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		objPRHd.setStrCurrency(PRBean.getStrCurrency());
		objPRHd.setDblConversion(currValue);

		return objPRHd;
	}

	/**
	 * Load Purchase Return HdData
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadPurchaseReturnData", method = RequestMethod.GET)
	public @ResponseBody clsPurchaseReturnHdModel funOpenFormWithPRCode(HttpServletRequest request) {
		
		String PRCode = request.getParameter("PRCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPurchaseReturnHdModel prModel = objPurchaseReturnService.funGetObject(PRCode, clientCode);
		if (null == prModel) {
			prModel = new clsPurchaseReturnHdModel();
			prModel.setStrPRCode("Invalid Code");
			return prModel;
		} else {
			double currConversion = prModel.getDblConversion();
			clsLocationMasterModel objLocationFrom = objLocService.funGetObject(prModel.getStrLocCode(), clientCode);
			prModel.setStrLocName(objLocationFrom.getStrLocName());
			prModel.setDtPRDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", prModel.getDtPRDate()));
			clsSupplierMasterModel objSupplier = objSupplierMasterService.funGetObject(prModel.getStrSuppCode(), clientCode);
			prModel.setStrSupplierName(objSupplier.getStrPName());
			return prModel;
		}
	}

	/**
	 * Load Purchase Return Dtl Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadPurchaseReturnDtlData", method = RequestMethod.GET)
	public @ResponseBody List funLoadPRDtlData(HttpServletRequest request) {
		
		String PRCode = request.getParameter("PRCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List prDtlModel = objPurchaseReturnService.funGetDtlList(PRCode, clientCode);
		clsPurchaseReturnHdModel prModel = objPurchaseReturnService.funGetObject(PRCode, clientCode);
		List<clsPurchaseReturnDtlModel> listPrDtl = new ArrayList<clsPurchaseReturnDtlModel>();
		for (int i = 0; i < prDtlModel.size(); i++) {
			Object[] ob = (Object[]) prDtlModel.get(i);
			clsPurchaseReturnDtlModel PRProdDtl = (clsPurchaseReturnDtlModel) ob[0];
			
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			
			clsPurchaseReturnDtlModel objPRDtl = new clsPurchaseReturnDtlModel();
			objPRDtl.setStrProdCode(PRProdDtl.getStrProdCode());
			objPRDtl.setStrPRCode(PRProdDtl.getStrPRCode());
			objPRDtl.setStrProdName(prodMaster.getStrProdName());
			objPRDtl.setStrUOM(PRProdDtl.getStrUOM());
			objPRDtl.setDblDiscount(PRProdDtl.getDblDiscount());
			objPRDtl.setDblQty(PRProdDtl.getDblQty());
			objPRDtl.setDblUnitPrice(PRProdDtl.getDblUnitPrice());
			objPRDtl.setDblTotalPrice(PRProdDtl.getDblTotalPrice());
			objPRDtl.setDblWeight(PRProdDtl.getDblWeight());
			objPRDtl.setDblTotalWt(PRProdDtl.getDblTotalWt());
			listPrDtl.add(objPRDtl);

		}
		return listPrDtl;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param PRCode
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String PRCode, String strTransMode, HttpServletRequest req) {
		
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsPurchaseReturnHdModel prModel = objPurchaseReturnService.funGetObject(PRCode, clientCode);
			if (null != prModel) {
				// String
				// userCode=req.getSession().getAttribute("usercode").toString();
				List prDtlModel = objPurchaseReturnService.funGetDtlList(PRCode, clientCode);
				if (null != prDtlModel && prDtlModel.size() > 0) {
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + prModel.getStrPRCode() + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (!list.isEmpty()) {
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(prModel);
						if (strTransMode.equalsIgnoreCase("Deleted")) {
							model.setStrTransCode(prModel.getStrPRCode());
						} else {
							model.setStrTransCode(prModel.getStrPRCode() + "-" + count);
						}
						model.setStrClientCode(clientCode);
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						for (int i = 0; i < prDtlModel.size(); i++) {
							Object[] ob = (Object[]) prDtlModel.get(i);
							clsPurchaseReturnDtlModel PRDtlModel = (clsPurchaseReturnDtlModel) ob[0];
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(PRDtlModel.getStrProdCode());
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrRemarks("");
							AuditMode.setDblDiscount(PRDtlModel.getDblDiscount());
							AuditMode.setDblQty(PRDtlModel.getDblQty());
							AuditMode.setDblUnitPrice(PRDtlModel.getDblUnitPrice());
							AuditMode.setDblTax(0.00);
							AuditMode.setDblTaxableAmt(0.00);
							AuditMode.setDblTaxAmt(0.00);
							AuditMode.setStrTaxType("");
							AuditMode.setDblTotalPrice(PRDtlModel.getDblTotalPrice());
							AuditMode.setStrPICode("");
							AuditMode.setStrClientCode(PRDtlModel.getStrClientCode());
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Prepare Audit HdModel
	 * 
	 * @param PRModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsPurchaseReturnHdModel PRModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (PRModel != null) {
			AuditHdModel.setStrTransCode(PRModel.getStrPRCode());
			AuditHdModel.setDtTransDate(PRModel.getDtPRDate());
			AuditHdModel.setStrTransType("Purchase Return");
			AuditHdModel.setStrSuppCode(PRModel.getStrSuppCode());
			AuditHdModel.setStrAgainst(PRModel.getStrAgainst());
			AuditHdModel.setDblExtra(PRModel.getDblExtra());
			AuditHdModel.setDblDisRate(PRModel.getDblDisRate());
			AuditHdModel.setDblDiscount(PRModel.getDblDisAmt());
			AuditHdModel.setDblSubTotal(PRModel.getDblSubTotal());
			AuditHdModel.setDblTotalAmt(PRModel.getDblTotal());
			AuditHdModel.setStrAuthorise(PRModel.getStrAuthorise());
			AuditHdModel.setStrMInBy(PRModel.getStrMInBy());
			AuditHdModel.setStrTimeInOut(PRModel.getStrTimeInOut());
			AuditHdModel.setStrVehNo(PRModel.getStrVehNo());
			AuditHdModel.setStrGRNCode(PRModel.getStrGRNCode());
			AuditHdModel.setStrCode(PRModel.getStrPurCode());
			AuditHdModel.setStrLocCode(PRModel.getStrLocCode());
			AuditHdModel.setStrNarration(PRModel.getStrNarration());
			AuditHdModel.setStrNo(PRModel.getStrPRNo());
			AuditHdModel.setDtDateCreated(PRModel.getDtDateCreated());
			AuditHdModel.setStrUserCreated(PRModel.getStrUserCreated());
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
		}
		return AuditHdModel;
	}

	/**
	 * End Function Audit
	 */

	/**
	 * Purchase Return Slip
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmPurchaseReturnSlip", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseReturnSlipForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmWebStockHelpPurchaseReturnSlip");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseReturnSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPurchaseReturnSlip", "command", new clsReportBean());
		}
	}

	public void funCallReport(String PRCode,String currencyCode,String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();

			JasperPrint p = funCallRangePrintReport(PRCode,currencyCode, resp, req);
			jprintlist.add(p);

			if (type.trim().equalsIgnoreCase("pdf")) {

				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptPurchaseReturnSlip." + type.trim());
				exporter.exportReport();

				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPurchaseReturnSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (JRException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/openRptPRSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) throws JRException, IOException {
		String PRCode = req.getParameter("rptPRCode").toString();
		req.getSession().removeAttribute("rptPRCode");
		String currencyCode=req.getParameter("currency").toString();
		req.getSession().removeAttribute("currency");
		String type = "pdf";

		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		ServletOutputStream servletOutputStream = resp.getOutputStream();

		JasperPrint p = funCallRangePrintReport(PRCode,currencyCode, resp, req);
		jprintlist.add(p);

		if (type.trim().equalsIgnoreCase("pdf")) {

			JRExporter exporter = new JRPdfExporter();
			resp.setContentType("application/pdf");
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
			resp.setHeader("Content-Disposition", "inline;filename=" + "rptPurchaseReturnSlip." + type.trim());
			exporter.exportReport();

			servletOutputStream.flush();
			servletOutputStream.close();
		} else if (type.trim().equalsIgnoreCase("xls")) {
			JRExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
			resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPurchaseReturnSlip." + type.trim());
			exporterXLS.exportReport();
			resp.setContentType("application/xlsx");

		}
	}

	/**
	 * Purchase Return Range Printing Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPurchaseReturnSlip", method = RequestMethod.POST)
	private @ResponseBody void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		
		String dtFromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String dtToDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
		String strFromPRCode = objBean.getStrFromDocCode();
		String strToPRCode = objBean.getStrToDocCode();
		String type = objBean.getStrDocType();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String tempLoc[] = objBean.getStrLocationCode().split(",");
		String tempSupp[] = objBean.getStrSuppCode().split(",");
		String strLocCodes = "";
		String strSuppCodes = "";

		for (int i = 0; i < tempLoc.length; i++) {
			if (strLocCodes.length() > 0) {
				strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";
			} else {
				strLocCodes = " a.strLocCode='" + tempLoc[i] + "' ";

			}
		}

		for (int i = 0; i < tempSupp.length; i++) {
			if (strSuppCodes.length() > 0) {
				strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i] + "' ";
			} else {
				strSuppCodes = " a.strSuppCode='" + tempSupp[i] + "' ";

			}
		}
		try {
			String sql = "select strPRCode,strCurrency from tblpurchasereturnhd a where date(a.dtPRDate) between '" + dtFromDate + "' and '" + dtToDate + "' and a.strClientCode='" + clientCode + "'";
			if (objBean.getStrLocationCode().length() > 0) {
				sql = sql + " and (" + strLocCodes + ")  ";
			}
			if (objBean.getStrSuppCode().length() > 0) {
				sql = sql + " and (" + strSuppCodes + ")  ";
			}
			if (strFromPRCode.trim().length() > 0 && strToPRCode.trim().length() > 0) {
				sql = sql + " and a.strPRCode between '" + strFromPRCode + "' and '" + strToPRCode + "' ";
			}

			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			
			
			if (list != null && !list.isEmpty()) {
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int i = 0; i < list.size(); i++) {
					Object[] arrObj = (Object[]) list.get(i);
					JasperPrint p = funCallRangePrintReport(arrObj[0].toString(),arrObj[1].toString(), resp, req);
					jprintlist.add(p);
				}

				if (type.trim().equalsIgnoreCase("pdf")) {

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptPurchaseReturnSlip." + type.trim());
					exporter.exportReport();

					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPurchaseReturnSlip." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");

				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");
			}

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public JasperPrint funCallRangePrintReport(String PRCode,String currencyCode, HttpServletResponse resp, HttpServletRequest req) {
		
		Connection con = objGlobalFunctions.funGetConnection(req);
		JasperPrint p = null;
		try {
			
			
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			//double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			double currValue = 1.0;
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			
			String prCode = "", prDate = "", locName = "", pName = "", grnCode = "",currencyName="";
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseReturnSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDS = "SELECT a.strPRCode,DATE_FORMAT(a.dtPRDate,'%d-%m-%Y'),b.strLocName, c.strPName, a.strAgainst AS strAgainst, a.strGRNCode AS strGRNCode, "
					+ " a.strNarration,d.strCurrencyName,a.strCurrency "
					+ "FROM tblpurchasereturnhd a, tbllocationmaster b, tblpartymaster c,tblcurrencymaster d "
					+ "WHERE a.strLocCode=b.strLocCode AND a.strClientCode=b.strClientCode AND a.strSuppCode=c.strPCode AND a.strCurrency=d.strCurrencyCode "
					+ "AND a.strClientCode=b.strClientCode AND a.strClientCode='" + clientCode + "' AND a.strPRCode='"+PRCode+"'  and a.strCurrency='"+currencyCode+"'";

			List listds = objGlobalFunctionsService.funGetList(sqlDS, "sql");
		
			if (listds.size() > 0) {
		
				for (int i = 0; i < listds.size(); i++) {
					Object[] obProdDtl = (Object[]) listds.get(i);
		
					prCode = obProdDtl[0].toString();
					prDate = obProdDtl[1].toString();
					locName =obProdDtl[2].toString();
					pName = obProdDtl[3].toString();
					grnCode =obProdDtl[5].toString();
					currencyName =obProdDtl[7].toString();
					objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(obProdDtl[8].toString(), clientCode);
					if (objCurrModel != null) {
						currValue = objCurrModel.getDblConvToBaseCurr();
					}
				}
			}
			
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			
			String sql = " SELECT e.strProdCode,e.strProdName,d.strUOM AS UOM,d.dblQty, "
						+ " d.dblUnitPrice/" + currValue + " AS dblUnitPrice,d.dblDiscount, d.dblTotalPrice/" + currValue + " AS dblTotalPrice, "
						+ " a.dblDisAmt/" + currValue + " AS dblDisAmt,a.dblDisRate,a.dblExtra/" + currValue + " AS dblExtra,"
						+ "a.dblSubTotal/" + currValue + " AS dblSubTotal, IFNULL(a.dblTaxAmt/" + currValue + ",0) AS dblTaxAmt, IFNULL(a.dblTotal/" + currValue + ",0) AS dblTotal "
						+ "FROM "+webStockDB+".tblpurchasereturnhd a,"+webStockDB+".tblpurchasereturndtl d,"+webStockDB+".tblproductmaster e " 
						+ "WHERE d.strProdCode=e.strProdCode AND a.strPRCode=d.strPRCode "
						+ "AND a.strPRCode='"+PRCode+"' AND a.strClientCode=d.strClientCode AND d.strClientCode=e.strClientCode "
						+ "AND a.strClientCode='" + clientCode + "' ";
		
			
			
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProddtl");
			subDataset.setQuery(subQuery);
			
			
			String taxSummary = " SELECT a.strTaxCode, a.strTaxDesc, a.strTaxableAmt/" + currValue + " AS strTaxableAmt, a.strTaxAmt/" + currValue + " AS strTaxAmt, b.dblTotal/" + currValue + " as dblTotal "
							  + " from "+webStockDB+".tblpurchasereturntaxdtl a ,"+webStockDB+".tblpurchasereturnhd b " + "where a.strPRCode='" + PRCode + " ' and a.strClientCode='" + clientCode + "' and a.strPRCode=b.strPRCode ";
			
			JRDesignQuery taxSummQuery = new JRDesignQuery();
			taxSummQuery.setText(taxSummary);
			JRDesignDataset taxSumDataset = (JRDesignDataset) datasetMap.get("dsTaxSummary");
			taxSumDataset.setQuery(taxSummQuery);
			
			

			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode.toUpperCase());
			hm.put("strImagePath", imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strPhoneNo", objSetup.getStrPhone());
			hm.put("strEmailAddress", objSetup.getStrEmail());
			hm.put("strWebSite", objSetup.getStrWebsite());
			hm.put("prCode", prCode);
			hm.put("prDate", prDate);
			hm.put("locName", locName);
			hm.put("pName", pName);
			hm.put("grnCode", grnCode);
			hm.put("currencyName", currencyName);
			
			
			p = JasperFillManager.fillReport(jr, hm, con);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			return p;
		}
	}
	
	

	private String funGenrateJVforPurchaseReturn(clsPurchaseReturnHdModel objModel, List<clsPurchaseReturnDtlModel> listDtlModel, String clientCode, String userCode, String propCode, HttpServletRequest req) {
		JSONObject jObjJVData = new JSONObject();

		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		String jvCode = "";
		String custCode = objModel.getStrSuppCode();
		double debitAmt = objModel.getDblTotal();
		clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custCode, clientCode, propCode, "Supplier", "Purchase");
		if (objLinkCust != null) {
			if (objModel.getStrPRNo().equals("")) {
				jObjJVData.put("strVouchNo", "");
				jObjJVData.put("strNarration", "JV Genrated by Pur-Ret:" + objModel.getStrPRCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtPRDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			} else {
				jObjJVData.put("strVouchNo", objModel.getStrPRNo());
				jObjJVData.put("strNarration", "JV Genrated by Pur-Ret:" + objModel.getStrGRNCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDtPRDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AP");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}
			// jvhd entry end

			// jvdtl entry Start
			for (clsPurchaseReturnDtlModel objDtl : listDtlModel) {

				JSONObject jObjDtl = new JSONObject();

				clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Purchase");
				if (objProdModle != null && objLinkSubGroup != null) {
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
					jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
					jObjDtl.put("strCrDr", "Cr");
					jObjDtl.put("dblDrAmt", 0.00);
					jObjDtl.put("dblCrAmt", objDtl.getDblTotalPrice());
					jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
					jObjDtl.put("strOneLine", "R");
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jArrJVdtl.add(jObjDtl);
				}
			}

			/*
			 * if(listTaxDtl!=null) { for(clsGRNTaxDtlModel objTaxDtl :
			 * listTaxDtl) { JSONObject jObjTaxDtl =new JSONObject();
			 * clsLinkUpHdModel objLinkTax =
			 * objLinkupService.funGetARLinkUp(objTaxDtl
			 * .getStrTaxCode(),clientCode); if(objLinkTax!=null ) {
			 * jObjTaxDtl.put("strVouchNo", "");
			 * jObjTaxDtl.put("strAccountCode", objLinkTax.getStrAccountCode());
			 * jObjTaxDtl.put("strAccountName", objLinkTax.getStrGDes());
			 * jObjTaxDtl.put("strCrDr", "Cr"); jObjTaxDtl.put("dblDrAmt",
			 * 0.00); jObjTaxDtl.put("dblCrAmt", objTaxDtl.getStrTaxAmt());
			 * jObjTaxDtl.put("strNarration",
			 * "WS Tax Desc :"+objTaxDtl.getStrTaxDesc());
			 * jObjTaxDtl.put("strOneLine", "R");
			 * jObjTaxDtl.put("strClientCode", clientCode);
			 * jObjTaxDtl.put("strPropertyCode", propCode);
			 * jArrJVdtl.add(jObjTaxDtl); } } }
			 */
			JSONObject jObjCustDtl = new JSONObject();
			jObjCustDtl.put("strVouchNo", "");
			jObjCustDtl.put("strAccountCode", "");
			jObjCustDtl.put("strAccountName", "");
			jObjCustDtl.put("strCrDr", "Dr");
			jObjCustDtl.put("dblDrAmt", objModel.getDblTotal());
			jObjCustDtl.put("dblCrAmt", 0.00);
			jObjCustDtl.put("strNarration", "GRN Supplier Return");
			jObjCustDtl.put("strOneLine", "R");
			jObjCustDtl.put("strClientCode", clientCode);
			jObjCustDtl.put("strPropertyCode", propCode);
			jArrJVdtl.add(jObjCustDtl);

			jObjJVData.put("ArrJVDtl", jArrJVdtl);

			// jvdtl entry end

			// jvDebtor detail entry start
			String sql = " select a.strPRCode,a.dblTotal,b.strDebtorCode,b.strPName,date(a.dtPRDate),  a.strNarration ,date(a.dtPRDate),'' " + " from tblpurchasereturnhd a,tblpartymaster b   " + " where a.strSuppCode =b.strPCode   " + " and a.strPRCode='" + objModel.getStrPRCode() + "' " + " and a.strClientCode='" + objModel.getStrClientCode() + "'   ";
			List listTax = objGlobalFunctionsService.funGetList(sql, "sql");
			if (null != listTax) {
				for (int i = 0; i < listTax.size(); i++) {
					JSONObject jObjDtl = new JSONObject();
					Object[] ob = (Object[]) listTax.get(i);
					jObjDtl.put("strVouchNo", "");
					jObjDtl.put("strDebtorCode", ob[2].toString());
					jObjDtl.put("strDebtorName", ob[3].toString());
					jObjDtl.put("strCrDr", "Dr");
					jObjDtl.put("dblAmt", ob[1].toString());
					jObjDtl.put("strBillNo", ob[7].toString());
					jObjDtl.put("strInvoiceNo", ob[0].toString());
					jObjDtl.put("strGuest", "");
					jObjDtl.put("strAccountCode", objLinkCust.getStrAccountCode());
					jObjDtl.put("strCreditNo", "");
					jObjDtl.put("dteBillDate", ob[4].toString());
					jObjDtl.put("dteInvoiceDate", ob[4].toString());
					jObjDtl.put("strNarration", ob[5].toString());
					jObjDtl.put("dteDueDate", ob[6].toString());
					jObjDtl.put("strClientCode", clientCode);
					jObjDtl.put("strPropertyCode", propCode);
					jObjDtl.put("strPOSCode", "");
					jObjDtl.put("strPOSName", "");
					jObjDtl.put("strRegistrationNo", "");

					jArrJVDebtordtl.add(jObjDtl);
				}
			}

			jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);
			// jvDebtor detail entry end

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGenrateJVforGRN", jObjJVData);
			jvCode = jObj.get("strJVCode").toString();
		}
		return jvCode;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPRTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List<clsPurchaseReturnTaxDtlModel> loadPRTaxDtl(Map<String, Object> model, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String strPRCode = request.getParameter("PRCode").toString();

		List<clsPurchaseReturnTaxDtlModel> listPRTaxDtl = new ArrayList<clsPurchaseReturnTaxDtlModel>();
		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from clsPurchaseReturnTaxDtlModel " + "where strPRCode='" + strPRCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		if(null!=list)
		{
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsPurchaseReturnTaxDtlModel objTaxDtl = new clsPurchaseReturnTaxDtlModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objTaxDtl.setStrTaxCode(arrObj[0].toString());
				objTaxDtl.setStrTaxDesc(arrObj[1].toString());
				objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
				objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
				listPRTaxDtl.add(objTaxDtl);
			}
		}
		
		return listPRTaxDtl;
	}
	

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGRNDtlForPR", method = RequestMethod.GET)
	public @ResponseBody List loadGRNDtlForPR(HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String grnCode = request.getParameter("GRNCode").toString();

		List listTempDtl = objPurchaseReturnService.funGetGRNDtlList(grnCode, clientCode);
		List<clsGRNDtlModel> listGRNDtl = new ArrayList<clsGRNDtlModel>();
// assign list data to model obj 
		for (int i = 0; i < listTempDtl.size(); i++) {
			Object[] ob = (Object[]) listTempDtl.get(i);
			
			clsGRNDtlModel objGRNDtl = new clsGRNDtlModel();
			objGRNDtl.setStrGRNCode(ob[1].toString());
			objGRNDtl.setStrProdCode(ob[0].toString());
			objGRNDtl.setStrClientCode(clientCode);
			objGRNDtl.setStrProdName(ob[8].toString());
			objGRNDtl.setDblUnitPrice(Double.parseDouble(ob[5].toString()));
			objGRNDtl.setDblWeight(Double.parseDouble(ob[6].toString()));
			objGRNDtl.setDblQty(Double.parseDouble(ob[4].toString()));//pending qty
			objGRNDtl.setDblTotalWt(Double.parseDouble(ob[14].toString()) * Double.parseDouble(ob[4].toString()));//weight * qty
			objGRNDtl.setDblTotalPrice(Double.parseDouble(ob[5].toString())*Double.parseDouble(ob[4].toString()));// unit price* qty
			objGRNDtl.setStrUOM(ob[7].toString());
			objGRNDtl.setDblDiscount(Double.parseDouble(ob[10].toString()));

			listGRNDtl.add(objGRNDtl);
		}
		return listGRNDtl;
	}

}
