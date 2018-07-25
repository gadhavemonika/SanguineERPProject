package com.sanguine.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.bean.clsClientBean;
import com.sanguine.bean.clsPurchaseOrderBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseOrderDtlModel;
import com.sanguine.model.clsPurchaseOrderHdModel;
import com.sanguine.model.clsPurchaseOrderHdModel_ID;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsTCMasterModel;
import com.sanguine.model.clsTCTransModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPurchaseIndentHdService;
import com.sanguine.service.clsPurchaseOrderService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.service.clsTCMasterService;
import com.sanguine.service.clsTCTransService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsNumberToWords;
import com.sanguine.util.clsReportBean;

@Controller
public class clsPurchaseOrderController {
	final static Logger logger = Logger.getLogger(clsPurchaseOrderController.class);

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsProductMasterService objProdMasterService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private clsPurchaseOrderService objPurchaseOrderService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsPurchaseIndentHdService objClsPurchaseIndentHdService;
	@Autowired
	private clsTCMasterService objTCMaster;
	@Autowired
	private clsTCTransService objTCTransService;

	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsUOMService objclsUOMService;
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	clsSupplierMasterService objSupplierMasterService;

	/**
	 * Open Purchase Order Form
	 * 
	 * @param bean
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView funOpenPurchaseOrderForm(@ModelAttribute("command") clsPurchaseOrderBean bean, Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmPurchaseOrder");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();

		} catch (NullPointerException e) {
			urlHits = "1";
		}
		
		/**
		 * Code use when this form is open form authorization toll on click of
		 * view Button Ritesh 25 Feb 2015
		 */
		String authorizationPOCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationPOCode = request.getParameter("authorizationPOCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationPOCode", authorizationPOCode);
		}

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		/**
		 * Set Process
		 */

		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmPurchaseOrder", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}

		/**
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);

		/**
		 * End of Code use when this form is open form authorization toll on
		 * click of view Button Ritesh 25 Feb 2015
		 */

		model.put("urlHits", urlHits);
		
		String sql_Setup = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
		List listTC_Setup = objTCTransService.funGetTCTransList(sql_Setup, propCode, clientCode, "Property Setup");
		if (listTC_Setup.size() == 0) {
			List<clsTCMasterModel> listTCMaster = objTCMaster.funGetTCMasterList(clientCode);
			bean.setListTCMaster(listTCMaster);
		} else {
			List<clsTCMasterModel> listTCMasterForPO = new ArrayList<clsTCMasterModel>();
			for (int cnt = 0; cnt < listTC_Setup.size(); cnt++) {
				clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
				Object[] arrObject = (Object[]) listTC_Setup.get(cnt);
				objTCMasterModel.setStrTCCode(arrObject[0].toString());
				objTCMasterModel.setStrTCName(arrObject[1].toString());
				objTCMasterModel.setStrTCDesc(arrObject[2].toString());
				listTCMasterForPO.add(objTCMasterModel);
			}
			bean.setListTCMaster(listTCMasterForPO);
		}

		model.put("TCMasterList", bean);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseOrder_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseOrder");
		} else {
			return null;
		}
	}

	/**
	 * Fill Against Combo Box
	 * 
	 * @param fromName
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/fillPOAgainstCombo", method = RequestMethod.GET)
	public @ResponseBody List<String> fungetprocess(@RequestParam("formName") String fromName, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			if (propCode != null) {
				List list = objGlobalFunctions.funGetSetUpProcess(fromName, propCode, clientCode);
				return list;
			}
		} catch (Exception e) {
			logger.error("Sorry, something wrong!", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load Purchase Order Data
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPOData", method = RequestMethod.GET)
	public @ResponseBody clsPurchaseOrderBean funOpenPurchaseOrderForm1(HttpServletRequest request, Map<String, Object> model) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String code = request.getParameter("POCode").toString();
		clsPurchaseOrderBean bean = new clsPurchaseOrderBean();
	
		clsPurchaseOrderHdModel objPurchaseOrderHdModel = objPurchaseOrderService.funGetObject(code, clientCode);
		if (null == objPurchaseOrderHdModel) {
			bean.setStrPOCode("Invalid Code");
			return bean;
		} else {
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			double currConversion = objPurchaseOrderHdModel.getDblConversion();
			if(currConversion==0){
				currConversion=1;
			}
//			if (objSetup != null) {
//				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objPurchaseOrderHdModel.getStrCurrency(), clientCode);
//				if (objCurrModel != null) {
//					currConversion = objCurrModel.getDblConvToBaseCurr();
//
//				}
//
//			}
			bean.setStrPOCode(code);
			bean.setStrVAddress1(objPurchaseOrderHdModel.getStrVAddress1());
			bean.setStrVAddress2(objPurchaseOrderHdModel.getStrVAddress2());
			bean.setStrVCity(objPurchaseOrderHdModel.getStrVCity());
			bean.setStrVCountry(objPurchaseOrderHdModel.getStrVCountry());
			bean.setStrVPin(objPurchaseOrderHdModel.getStrVPin());
			bean.setStrVState(objPurchaseOrderHdModel.getStrVState());
			bean.setStrSAddress1(objPurchaseOrderHdModel.getStrSAddress1());
			bean.setStrSAddress2(objPurchaseOrderHdModel.getStrSAddress2());
			bean.setStrSCity(objPurchaseOrderHdModel.getStrSCity());
			bean.setStrSCountry(objPurchaseOrderHdModel.getStrSCountry());
			bean.setStrSPin(objPurchaseOrderHdModel.getStrSPin());
			bean.setStrSState(objPurchaseOrderHdModel.getStrSState());
			bean.setStrYourRef(objPurchaseOrderHdModel.getStrYourRef());
			bean.setStrSuppCode(objPurchaseOrderHdModel.getStrSuppCode());
			bean.setStrAgainst(objPurchaseOrderHdModel.getStrAgainst());
			bean.setStrSOCode(objPurchaseOrderHdModel.getStrSOCode());
			bean.setDteDelDate(objGlobal.funGetDate("yyyy/MM/dd", objPurchaseOrderHdModel.getDtDelDate()));
			bean.setDtPODate(objGlobal.funGetDate("yyyy/MM/dd", objPurchaseOrderHdModel.getDtPODate()));
			bean.setDtPayDate(objGlobal.funGetDate("yyyy/MM/dd", objPurchaseOrderHdModel.getDtPayDate()));
			bean.setStrPayMode(objPurchaseOrderHdModel.getStrPayMode());
			bean.setStrCurrency(objPurchaseOrderHdModel.getStrCurrency());
			bean.setStrAgainst(objPurchaseOrderHdModel.getStrAgainst());
			bean.setStrPerRef(objPurchaseOrderHdModel.getStrPerRef());
			bean.setDblExtra(objPurchaseOrderHdModel.getDblExtra()/currConversion);
			bean.setDblDiscount(objPurchaseOrderHdModel.getDblDiscount()/currConversion);
			bean.setStrCode(objPurchaseOrderHdModel.getStrCode());
			bean.setDblTotal(objPurchaseOrderHdModel.getDblTotal()/currConversion);
			bean.setDblFinalAmt(objPurchaseOrderHdModel.getDblFinalAmt()/currConversion);
			bean.setStrClosePO(objPurchaseOrderHdModel.getStrClosePO());

			bean.setDblFOB(objPurchaseOrderHdModel.getDblFOB()/currConversion);
			bean.setDblFreight(objPurchaseOrderHdModel.getDblFreight()/currConversion);
			bean.setDblInsurance(objPurchaseOrderHdModel.getDblInsurance()/currConversion);
			bean.setDblOtherCharges(objPurchaseOrderHdModel.getDblOtherCharges()/currConversion);
			bean.setDblCIF(objPurchaseOrderHdModel.getDblCIF()/currConversion);
			bean.setDblClearingAgentCharges(objPurchaseOrderHdModel.getDblClearingAgentCharges()/currConversion);
			bean.setDblVATClaim(objPurchaseOrderHdModel.getDblVATClaim()/currConversion);
			bean.setDblConversion(currConversion); 
			List<clsPurchaseOrderDtlModel> listPurchaseOrderDtl = new ArrayList<clsPurchaseOrderDtlModel>();
			List listPODtl = objPurchaseOrderService.funGetDtlList(code, clientCode);
			for (int cnt = 0; cnt < listPODtl.size(); cnt++) {
				Object[] objPODtl = (Object[]) listPODtl.get(cnt);
				clsPurchaseOrderDtlModel objPurchaseOrderDtl = new clsPurchaseOrderDtlModel();
				objPurchaseOrderDtl.setStrPOCode(code);
				objPurchaseOrderDtl.setStrProdCode(objPODtl[0].toString());
				objPurchaseOrderDtl.setStrProdName(objPODtl[11].toString());
				objPurchaseOrderDtl.setStrSuppCode(objPODtl[14].toString());
				objPurchaseOrderDtl.setStrSuppName(objPODtl[15].toString());
				objPurchaseOrderDtl.setStrUOM(objPODtl[12].toString());
				objPurchaseOrderDtl.setStrRemarks(objPODtl[5].toString());
				objPurchaseOrderDtl.setDblDiscount(Double.parseDouble(objPODtl[7].toString())/currConversion);
				objPurchaseOrderDtl.setDblOrdQty(Double.parseDouble(objPODtl[1].toString()));
				objPurchaseOrderDtl.setDblPrice(Double.parseDouble(objPODtl[2].toString())/currConversion);
				objPurchaseOrderDtl.setDblWeight(Double.parseDouble(objPODtl[3].toString()));
				double totalWeight = Double.parseDouble(objPODtl[3].toString()) * Double.parseDouble(objPODtl[1].toString());
				objPurchaseOrderDtl.setDblTotalWt(totalWeight);
				objPurchaseOrderDtl.setStrPICode(objPODtl[6].toString());
				objPurchaseOrderDtl.setDblAmount(Double.parseDouble(objPODtl[16].toString())/currConversion);
				objPurchaseOrderDtl.setStrUpdate(objPODtl[9].toString());
				listPurchaseOrderDtl.add(objPurchaseOrderDtl);
			}
			bean.setListPODtlModel(listPurchaseOrderDtl);

			String sql_PO = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
			List listTC_PO = objTCTransService.funGetTCTransList(sql_PO, bean.getStrPOCode(), clientCode, "Purchase Order");

			if (listTC_PO.size() == 0) {
				List<clsTCMasterModel> listTCMaster = objTCMaster.funGetTCMasterList(clientCode);
				bean.setListTCMaster(listTCMaster);
			} else {
				List<clsTCMasterModel> listTCMasterForPO = new ArrayList<clsTCMasterModel>();
				for (int cnt = 0; cnt < listTC_PO.size(); cnt++) {
					clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
					Object[] arrObject = (Object[]) listTC_PO.get(cnt);
					objTCMasterModel.setStrTCCode(arrObject[0].toString());
					objTCMasterModel.setStrTCName(arrObject[1].toString());
					objTCMasterModel.setStrTCDesc(arrObject[2].toString());
					listTCMasterForPO.add(objTCMasterModel);
				}
				bean.setListTCMaster(listTCMasterForPO);
			}

			String sql = "select strTaxCode,strTaxDesc,strTaxableAmt/'"+currConversion+"',strTaxAmt/'"+currConversion+"' from clsPOTaxDtlModel " + "where strPOCode='" + bean.getStrPOCode() + "' and strClientCode='" + clientCode + "'";
			List list = objGlobalFunctionsService.funGetList(sql, "hql");
			List<clsPOTaxDtlModel> listPOTaxDtl = new ArrayList<clsPOTaxDtlModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsPOTaxDtlModel objTaxDtl = new clsPOTaxDtlModel();
				Object[] arrObj = (Object[]) list.get(cnt);
				objTaxDtl.setStrTaxCode(arrObj[0].toString());
				objTaxDtl.setStrTaxDesc(arrObj[1].toString());
				objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
				objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
				listPOTaxDtl.add(objTaxDtl);
			}
			bean.setListPOTaxDtl(listPOTaxDtl);

			model.put("TCMasterList", bean);
			return bean;
		}

	}

	/**
	 * Load Terms and Condition
	 * 
	 * @param partyCode
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadTCForSupplier", method = RequestMethod.GET)
	public @ResponseBody List funAssignTCForSupplier(@RequestParam("partyCode") String partyCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select b.strTCCode,c.strTCName,b.strTCDesc " + "from clsRateContractHdModel a,clsTCTransModel b,clsTCMasterModel c " + "where a.strRateContNo=b.strTransCode and b.strTCCode=c.strTCCode " + "and a.strSuppCode='" + partyCode + "' and a.strClientCode='" + clientCode + "'";

		List listTCForSupplier = objGlobalFunctionsService.funGetList(sql, "hql");
		return listTCForSupplier;
	}

	/**
	 * Load Rate Form Rate Contractor
	 * 
	 * @param suppCode
	 * @param prodCode
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadProdRateFromRateContractor", method = RequestMethod.GET)
	public @ResponseBody List funGetProdRateFromRateContractor(@RequestParam("suppCode") String suppCode, @RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select b.strProductCode,b.dblRate from tblrateconthd a,tblratecontdtl b " + " where a.strRateContNo=b.strRateContNo and a.strSuppCode='" + suppCode + "' and b.strProductCode='" + prodCode + "' " + " and a.strClientCode='" + clientCode + "'";

		List listProdRate = objGlobalFunctionsService.funGetList(sql, "sql");
		return listProdRate;
	}

	/**
	 * Load Pending Purchase Indent Data
	 * 
	 * @param PICode
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPurchaseIndentDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignPIData(@RequestParam("PICode") String PICode, HttpServletRequest req) {
		String strPICodes = "";
		String tempPoCode[] = PICode.split(",");
		for (int i = 0; i < tempPoCode.length; i++) {
			if (strPICodes.length() > 0) {
				strPICodes = strPICodes + " or a.strPICode='" + tempPoCode[i] + "' ";
			} else {
				strPICodes = "a.strPICode='" + tempPoCode[i] + "' ";

			}
		}

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = " select a.strProdCode,(a.dblQty - ifnull(b.POQty,0)) as dblQty,c.dblCostRM,c.dblWeight, " + " a.strPICode , c.strProdName,c.strUOM,ifnull(d.strSuppCode,'') as strSuppCode, " + " ifnull(e.strPName,'') as strPName,date(a.dtReqDate)   from tblpurchaseindenddtl a " + " left outer join (select b.strPICode, b.strProdCode,sum(dblOrdQty) POQty "
				+ " from tblpurchaseorderhd a,tblpurchaseorderdtl b  where a.strPOCode = b.strPOCode " + " and  a.strClientCode='" + clientCode + "'  and b.strClientCode='" + clientCode + "' " + "	group by b.strPICode, b.strProdCode) as b on a.strPIcode = b.strPICode and a.strProdCode = b.strProdCode " + "	left outer join tblproductmaster c on a.strProdCode=c.strProdCode and c.strClientCode='"
				+ clientCode + "' " + "	left outer join tblprodsuppmaster d on c.strProdCode=d.strProdCode and d.strDefault='y'and d.strClientCode='" + clientCode + "' " + " left outer join tblpartymaster e on d.strSuppCode=e.strPCode and e.strClientCode='" + clientCode + "'  " + "	 where  a.dblQty > ifnull(b.POQty,0) and a.strClientCode='" + clientCode + "' and (" + strPICodes + ")  ";

		return objPurchaseOrderService.funGetPIData(sql, PICode, clientCode);
	}


	
	/**
	 * Load Pending Purchase Indent Data from Notification click
	 * 
	 * @param PICode
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPIDataFromNotification", method = RequestMethod.GET)
	public ModelAndView funLoadPIDataFromNotification(@ModelAttribute("command") clsPurchaseOrderBean objPOBean,@RequestParam("PICode") String PICode, 
			Map<String, Object> model, HttpServletRequest request) {
		objPOBean.setStrSOCode(PICode);
		objPOBean.setStrAgainst("Purchase Indent");

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmPurchaseOrder");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();

		} catch (NullPointerException e) {
			urlHits = "1";
		}
		
		/**
		 * Code use when this form is open form authorization toll on click of
		 * view Button Ritesh 25 Feb 2015
		 */
		String authorizationPOCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationPOCode = request.getParameter("authorizationPOCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationPOCode", authorizationPOCode);
		}

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		/**
		 * Set Process
		 */

		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmPurchaseOrder", propCode, clientCode);
		if (list.size() > 0) {
			model.put("strProcessList", list);
		} else {
			list = new ArrayList<String>();
			model.put("strProcessList", list);
		}

		/**
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);

		/**
		 * End of Code use when this form is open form authorization toll on
		 * click of view Button Ritesh 25 Feb 2015
		 */

		model.put("urlHits", urlHits);
		
		String sql_Setup = "select a.strTCCode,b.strTCName,a.strTCDesc " + "from clsTCTransModel a,clsTCMasterModel b " + "where a.strTCCode=b.strTCCode and a.strTransCode=:transCode " + "and a.strClientCode=:clientCode and a.strTransType=:transType";
		List listTC_Setup = objTCTransService.funGetTCTransList(sql_Setup, propCode, clientCode, "Property Setup");
		if (listTC_Setup.size() == 0) {
			List<clsTCMasterModel> listTCMaster = objTCMaster.funGetTCMasterList(clientCode);
			objPOBean.setListTCMaster(listTCMaster);
		} else {
			List<clsTCMasterModel> listTCMasterForPO = new ArrayList<clsTCMasterModel>();
			for (int cnt = 0; cnt < listTC_Setup.size(); cnt++) {
				clsTCMasterModel objTCMasterModel = new clsTCMasterModel();
				Object[] arrObject = (Object[]) listTC_Setup.get(cnt);
				objTCMasterModel.setStrTCCode(arrObject[0].toString());
				objTCMasterModel.setStrTCName(arrObject[1].toString());
				objTCMasterModel.setStrTCDesc(arrObject[2].toString());
				listTCMasterForPO.add(objTCMasterModel);
			}
			objPOBean.setListTCMaster(listTCMasterForPO);
		}

		model.put("TCMasterList", objPOBean);
		return new ModelAndView("frmPurchaseOrder", "command", objPOBean);
	}
	
	
	
	/**
	 * load Product Data
	 * 
	 * @param prodCode
	 * @param suppCode
	 * @param req
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadProductDataForTrans", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields(@RequestParam("prodCode") String prodCode, @RequestParam("suppCode") String suppCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String RateFrom = req.getSession().getAttribute("RateFrom").toString();
		String sql = "";
		List list = new ArrayList<>();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetUp = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		clsSupplierMasterModel objSupplierMasterModel = null;

		if (null != suppCode) {
			objSupplierMasterModel = objSupplierMasterService.funGetObject(suppCode, clientCode);
		}

		if (RateFrom.equalsIgnoreCase("PurchaseRate")) {
			if (objSetUp.getStrMultiCurrency().equalsIgnoreCase("N")) {
				sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' "
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
				//change for maya-- show sales price of product 
//				sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo,a.dblCostRM " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' "
//						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
			} else {
				String locCode = req.getSession().getAttribute("locationCode").toString();

				sql = "select a.strProdcode,a.strProdName,a.strUOM,ifnull(d.dblPrice,0.0) as dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo,a.dblCostRM " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' "
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + "  left outer join tblreorderlevel d on a.strProdCode=d.strProdCode and d.strLocationCode='" + locCode + "' and d.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
			}
			list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
		}
		if (RateFrom.equalsIgnoreCase("SupplierRate")) {
			if (suppCode != null && suppCode != "") {
				sql = "select a.strProdcode,a.strProdName,a.strUOM,b.dblLastCost, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo,a.dblCostRM " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' "
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "' and strSuppCode='" + suppCode + "'";
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
				if (list.size() < 0 || list.isEmpty() || list == null) {
					sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo,a.dblCostRM " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y'"
							+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
					list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
				}
			} else {
				sql = "select a.strProdcode,a.strProdName,a.strUOM,a.dblCostRM, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.strPartNo,a.dblCostRM " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y'"
						+ " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "'";
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
			}
		}

		if (list.size() == 0) {
			list.add("Invalid Product Code");
			return list;
		} else {
			/*
			 * if(null!=objSupplierMasterModel) { Object[]
			 * arrObj=(Object[])list.get(0); double
			 * unitPrice=Double.parseDouble(arrObj[3].toString());
			 * unitPrice=objGlobalFunctionsService
			 * .funGetCurrencyConversion(unitPrice,
			 * objSupplierMasterModel.getStrCurrency(), clientCode);
			 * arrObj[3]=unitPrice; list.set(0, arrObj); }
			 */

			return list;
		}
	}
	/**
	 * Load product Data For Purchase Indent
	 * 
	 * @param prodCode
	 * @param PICode
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadProductDataForPITrans", method = RequestMethod.GET)
	public @ResponseBody List funLoadProductForPI(@RequestParam("prodCode") String prodCode, @RequestParam("PICode") String PICode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql = "select a.strProdcode,a.strProdName,a.strUOM, " + " ifnull(b.strSuppCode,'') as strSuppCode, ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,d.dblQty,d.dblRate,d.strPIcode " + " from tblproductmaster a inner join tblpurchaseindenddtl d on a.strProdCode=d.strProdCode and d.strPIcode='" + PICode + "' and d.strClientCode='" + clientCode + "'"
				+ " left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' " + " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where  a.strProdCode='" + prodCode + "' and a.strClientCode='" + clientCode + "' ";
		List list = objGlobalFunctionsService.funGetProductDataForTransaction(sql, prodCode, clientCode);
		if (list.size() == 0) {
			list.add("Invalid Product Code");
			return list;
		} else {
			return list;
		}

	}

	/**
	 * Load Purchase Indent Dtl for Purchase Order
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadPIDtlDataForPO", method = RequestMethod.POST)
	public ModelAndView funLoadPOFormWithPurchaseIndent(HttpServletRequest request) {
		clsPurchaseOrderBean bean = new clsPurchaseOrderBean();
		String code = request.getParameter("PICode").toString();
		String strLocCode = request.getParameter("strLocCode").toString();
		/**
		 * Changes By Vikash Location Code in funGetDtlList(27/11/2014)
		 */
		List<clsPurchaseIndentDtlModel> listPurchaseIndentDtlModel = objClsPurchaseIndentHdService.funGetDtlList(code, request.getSession().getAttribute("clientCode").toString(), strLocCode);
		List<clsPurchaseOrderDtlModel> listclsPurchaseOrderDtlModel = new ArrayList<clsPurchaseOrderDtlModel>();

		for (clsPurchaseIndentDtlModel ob : listPurchaseIndentDtlModel) {
			clsPurchaseOrderDtlModel objclsPurchaseOrderDtlModel = new clsPurchaseOrderDtlModel();
			objclsPurchaseOrderDtlModel.setStrPICode(code);
			objclsPurchaseOrderDtlModel.setStrProdCode(ob.getStrProdCode());
			objclsPurchaseOrderDtlModel.setStrUpdate("N");
			objclsPurchaseOrderDtlModel.setDblOrdQty(ob.getDblQty());
			objclsPurchaseOrderDtlModel.setDblWeight(0.00);
			listclsPurchaseOrderDtlModel.add(objclsPurchaseOrderDtlModel);
		}
		bean.setListPODtlModel(listclsPurchaseOrderDtlModel);
		return new ModelAndView("frmPurchaseOrder", "command", bean);
	}

	/**
	 * Save PO Data
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/savePOData", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsPurchaseOrderBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String strGeneratedPOCodes = "";
		objGlobal = new clsGlobalFunctions();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String POCode = "";
		double totalPrice = 0.00;
		double finalAmt = 0.00;
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		double currConversion = 1.0;
		if(objBean.getDblConversion()>0){
			currConversion=objBean.getDblConversion();
		}
		

		
		if (!result.hasErrors()) {
			if (null != objBean.getStrSuppCode() && objBean.getStrSuppCode().trim().length() != 0) {
				List<clsPurchaseOrderDtlModel> listclsPurchaseOrderDtlModel = objBean.getListPODtlModel();
				if (null != listclsPurchaseOrderDtlModel && listclsPurchaseOrderDtlModel.size() > 0) {
					clsPurchaseOrderHdModel objPurchaseOrderHdModel = funPrepareMaster(objBean, objBean.getStrSuppCode(), req,currConversion);
					objPurchaseOrderService.funAddUpdatePurchaseOrderHd(objPurchaseOrderHdModel);

					POCode = objPurchaseOrderHdModel.getStrPOCode();
					strGeneratedPOCodes = POCode;
					objPurchaseOrderService.funDeletePODtl(POCode, clientCode);
					for (clsPurchaseOrderDtlModel ob : listclsPurchaseOrderDtlModel) {
						if (null != ob.getStrProdCode()) {
							ob.setStrClientCode(clientCode);
							ob.setStrPOCode(POCode);
							ob.setDtDelDate(objPurchaseOrderHdModel.getDtDelDate());
							ob.setStrAmntNO(objPurchaseOrderHdModel.getStrAmntNO());
							ob.setDblAmount(ob.getDblAmount()*currConversion);
							ob.setDblDiscount(ob.getDblDiscount()*currConversion);
							ob.setDblPrice(ob.getDblPrice()*currConversion);
							ob.setDblTax(ob.getDblTax()*currConversion);
							ob.setDblTaxableAmt(ob.getDblTaxableAmt()*currConversion);
							ob.setDblTax(ob.getDblTax()*currConversion);
							ob.setDblTotalPrice(ob.getDblTotalPrice()*currConversion);
							
							ob.setStrProcessCode("");
							ob.setStrProdChar("");
							ob.setStrTaxType("");
							objPurchaseOrderService.funAddUpdatePurchaseOrderDtl(ob);
						}
					}
				}
			} else {
				List<clsPurchaseOrderDtlModel> listclsPurchaseOrderDtlModel1 = objBean.getListPODtlModel();
				if (null != listclsPurchaseOrderDtlModel1 && listclsPurchaseOrderDtlModel1.size() > 0) {
					Set<String> setSupplierCode = new HashSet<>();
					for (clsPurchaseOrderDtlModel tempOb : listclsPurchaseOrderDtlModel1) {
						if (null != tempOb.getStrProdCode()) {
							setSupplierCode.add(tempOb.getStrSuppCode());
						}
					}
					for (String supplier : setSupplierCode) {
						clsPurchaseOrderHdModel objPurchaseOrderHdModel = funPrepareMaster(objBean, supplier, req,currConversion);
						POCode = objPurchaseOrderHdModel.getStrPOCode();
						objPurchaseOrderService.funDeletePODtl(POCode, clientCode);
						for (clsPurchaseOrderDtlModel tempOb : listclsPurchaseOrderDtlModel1) {
							if (null != tempOb.getStrProdCode() && supplier.equalsIgnoreCase(tempOb.getStrSuppCode())) {
								tempOb.setStrClientCode(clientCode);
								tempOb.setStrPOCode(POCode);
								tempOb.setDtDelDate(objPurchaseOrderHdModel.getDtDelDate());
								tempOb.setStrAmntNO(objPurchaseOrderHdModel.getStrAmntNO());
								tempOb.setStrProcessCode("");
								tempOb.setStrProdChar("");
								tempOb.setStrTaxType("");
								totalPrice = totalPrice + tempOb.getDblAmount();
								tempOb.setDblAmount(tempOb.getDblAmount()*currConversion);
								tempOb.setDblDiscount(tempOb.getDblDiscount()*currConversion);
								tempOb.setDblPrice(tempOb.getDblPrice()*currConversion);
								tempOb.setDblTax(tempOb.getDblTax()*currConversion);
								tempOb.setDblTaxableAmt(tempOb.getDblTaxableAmt()*currConversion);
								tempOb.setDblTax(tempOb.getDblTax()*currConversion);
								tempOb.setDblTotalPrice(tempOb.getDblTotalPrice()*currConversion);
								objPurchaseOrderService.funAddUpdatePurchaseOrderDtl(tempOb);
							}
						}
						objPurchaseOrderHdModel.setDblTotal(totalPrice*currConversion);
						double taxAmt = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblTaxAmt()), "0.0000", String.valueOf(objBean.getDblTaxAmt())));
						finalAmt = (totalPrice*currConversion + taxAmt*currConversion + objPurchaseOrderHdModel.getDblExtra()) - (objPurchaseOrderHdModel.getDblDiscount());
						if (!clientCode.equals("226.001")) {
							objPurchaseOrderHdModel.setDblFinalAmt(finalAmt);
						}

						objPurchaseOrderService.funAddUpdatePurchaseOrderHd(objPurchaseOrderHdModel);
						totalPrice = 0.00;
						if (strGeneratedPOCodes.length() > 0) {
							strGeneratedPOCodes = strGeneratedPOCodes + "," + POCode;

						} else {
							strGeneratedPOCodes = POCode;
						}
					}
				}
			}
			if (null != objBean.getListTCMaster()) {
				String sql_Delete = "delete from clsTCTransModel where strTransCode='" + POCode + "' " + "and strTransType='Purchase Order' and strClientCode='" + clientCode + "'";
				objTCTransService.funDeleteTCTransList(sql_Delete);

				List<clsTCTransModel> listTCTransModel = objGlobal.funPrepareTCTransModel(objBean.getListTCMaster(), POCode, userCode, clientCode, "Purchase Order");
				for (int cnt = 0; cnt < listTCTransModel.size(); cnt++) {
					clsTCTransModel objTCTrans = listTCTransModel.get(cnt);
					objTCTransService.funAddTCTrans(objTCTrans);
				}
			}

			List<clsPOTaxDtlModel> listPOTaxDtlModel = objBean.getListPOTaxDtl();
			if (null != listPOTaxDtlModel) {
				objPurchaseOrderService.funDeletePOTaxDtl(objBean.getStrPOCode(), clientCode);
				for (clsPOTaxDtlModel obTaxDtl : listPOTaxDtlModel) {
					if (null != obTaxDtl.getStrTaxCode()) {
						obTaxDtl.setStrPOCode(POCode);
						obTaxDtl.setStrClientCode(clientCode);
						obTaxDtl.setStrTaxableAmt(obTaxDtl.getStrTaxableAmt()*currConversion);
						obTaxDtl.setStrTaxAmt(obTaxDtl.getStrTaxAmt()*currConversion);
						objPurchaseOrderService.funAddUpdatePOTaxDtl(obTaxDtl);
					}
				}
			}
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "PO Code : ".concat(strGeneratedPOCodes));
			req.getSession().setAttribute("rptPOCode", strGeneratedPOCodes);
			return new ModelAndView("redirect:/frmPurchaseOrder.html?saddr=" + urlHits);
		}
		return new ModelAndView("frmPurchaseOrder?saddr=" + urlHits);
	}

	/**
	 * Prepare Purchase Order HdModel
	 * 
	 * @param objBean
	 * @param SupplierCode
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private clsPurchaseOrderHdModel funPrepareMaster(clsPurchaseOrderBean objBean, String SupplierCode, HttpServletRequest req,double currConversion) {
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		// String
		// startDate=req.getSession().getAttribute("startDate").toString();
		long lastNo = 0;
		objGlobal = new clsGlobalFunctions();
		clsPurchaseOrderHdModel objPurchaseOrderHdModel;
		if (objBean.getStrPOCode().trim().length() == 0) {
			String POcode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseOrder", objBean.getDtPODate(), req);
			objPurchaseOrderHdModel = new clsPurchaseOrderHdModel(new clsPurchaseOrderHdModel_ID(POcode, clientCode));
			objPurchaseOrderHdModel.setIntId(lastNo);

			boolean res = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Purchase Order")) {
					res = true;
				}
			}
			if (res) {
				objPurchaseOrderHdModel.setStrAuthorise("No");
			} else {
				objPurchaseOrderHdModel.setStrAuthorise("Yes");
			}
			objPurchaseOrderHdModel.setStrUserCreated(userCode);
			objPurchaseOrderHdModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsPurchaseOrderHdModel objClsPurchaseOrderHdModel = objPurchaseOrderService.funGetObject(objBean.getStrPOCode(), clientCode);
			if (null == objClsPurchaseOrderHdModel) {
				String POcode = objGlobalFunctions.funGenerateDocumentCode("frmPurchaseOrder", objBean.getDtPODate(), req);
				objPurchaseOrderHdModel = new clsPurchaseOrderHdModel(new clsPurchaseOrderHdModel_ID(POcode, clientCode));
				objPurchaseOrderHdModel.setIntId(lastNo);
				objPurchaseOrderHdModel.setStrAuthorise("No");
				objPurchaseOrderHdModel.setStrUserCreated(userCode);
				objPurchaseOrderHdModel.setDtDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmPurchaseOrder", req)) {
					funSaveAudit(objBean.getStrPOCode(), "Edit", req);
				}
				objPurchaseOrderHdModel = new clsPurchaseOrderHdModel(new clsPurchaseOrderHdModel_ID(objBean.getStrPOCode(), clientCode));
				objPurchaseOrderService.funDeletePOTaxDtl(objBean.getStrPOCode(), clientCode);
			}

		}
		objPurchaseOrderHdModel.setDtPODate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtPODate()));
		objPurchaseOrderHdModel.setStrSuppCode(SupplierCode);
		objPurchaseOrderHdModel.setStrAgainst(objBean.getStrAgainst());
		objPurchaseOrderHdModel.setStrSOCode(objGlobal.funIfNull(objBean.getStrSOCode(), "", objBean.getStrSOCode()));
		objPurchaseOrderHdModel.setDblTotal(objBean.getDblTotal()*currConversion);
		System.out.println("Total= " + objPurchaseOrderHdModel.getDblTotal()*currConversion);
		objPurchaseOrderHdModel.setStrVAddress1(objBean.getStrVAddress1());
		objPurchaseOrderHdModel.setStrVAddress2(objBean.getStrVAddress2());
		objPurchaseOrderHdModel.setStrVCity(objBean.getStrVCity());
		objPurchaseOrderHdModel.setStrVCountry(objBean.getStrVCountry());
		objPurchaseOrderHdModel.setStrVPin(objBean.getStrVPin());
		objPurchaseOrderHdModel.setStrSAddress1(objBean.getStrSAddress1());
		objPurchaseOrderHdModel.setStrSAddress2(objBean.getStrSAddress2());
		objPurchaseOrderHdModel.setStrSCity(objBean.getStrSCity());
		objPurchaseOrderHdModel.setStrVState(objBean.getStrVState());
		objPurchaseOrderHdModel.setStrSCountry(objBean.getStrSCountry());
		objPurchaseOrderHdModel.setStrSPin(objBean.getStrSPin());
		objPurchaseOrderHdModel.setStrSState(objBean.getStrSState());
		objPurchaseOrderHdModel.setStrYourRef(objBean.getStrYourRef());
		objPurchaseOrderHdModel.setStrPerRef(objGlobal.funIfNull(objBean.getStrPerRef(), "", objBean.getStrPerRef()));
		objPurchaseOrderHdModel.setStrEOE(objGlobal.funIfNull(objBean.getStrEOE(), "", objBean.getStrEOE()));
		objPurchaseOrderHdModel.setStrCode(objGlobal.funIfNull(objBean.getStrCode(), "", objBean.getStrCode()));
		objPurchaseOrderHdModel.setStrUserModified(userCode);
		objPurchaseOrderHdModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objPurchaseOrderHdModel.setStrClosePO(objGlobal.funIfNull(objBean.getStrClosePO(), "No", objBean.getStrClosePO()));
		objPurchaseOrderHdModel.setDtDelDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtDelDate()));
		double taxAmt = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblTaxAmt()), "0.0000", String.valueOf(objBean.getDblTaxAmt())));
		objPurchaseOrderHdModel.setDblTaxAmt(taxAmt*currConversion);
		double extraAmt = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblExtra()), "0.0000", String.valueOf(objBean.getDblExtra())));
		objPurchaseOrderHdModel.setDblExtra(extraAmt*currConversion);
		double finalAmt = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblFinalAmt()), "0.0000", String.valueOf(objBean.getDblFinalAmt())));
		
		objPurchaseOrderHdModel.setStrExcise(objGlobal.funIfNull(objBean.getStrExcise(), "", objBean.getStrExcise()));
		double discountAmt = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblDiscount()), "0.0000", String.valueOf(objBean.getDblDiscount())));
		objPurchaseOrderHdModel.setDblDiscount(discountAmt*currConversion);
		double dblTotal = Double.parseDouble(objGlobal.funIfNull(String.valueOf(objBean.getDblTotal()), "0.0000", String.valueOf(objBean.getDblTotal())));
		finalAmt = (dblTotal + taxAmt + extraAmt) - discountAmt;
		objPurchaseOrderHdModel.setDblFinalAmt(finalAmt*currConversion);
		objPurchaseOrderHdModel.setStrPayMode(objBean.getStrPayMode());
		objPurchaseOrderHdModel.setStrCurrency(objBean.getStrCurrency());
		String amedment = objGlobal.funIfNull(objBean.getStrAmedment(), "", objBean.getStrAmedment());
		objPurchaseOrderHdModel.setStrAmedment(amedment);
		String amntNo = objGlobal.funIfNull(objBean.getStrAmntNO(), "", objBean.getStrAmntNO());
		objPurchaseOrderHdModel.setStrAmntNO(amntNo);
		objPurchaseOrderHdModel.setStrEdit(objGlobal.funIfNull(objBean.getStredit(), "", objBean.getStredit()));
		objPurchaseOrderHdModel.setStrUserAmed(objGlobal.funIfNull(objBean.getStrUserAmed(), "", objBean.getStrUserAmed()));
		objPurchaseOrderHdModel.setDtPayDate(objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtPayDate()));
		objPurchaseOrderHdModel.setDblConversion(currConversion);
		objPurchaseOrderHdModel.setDblExchangeRate(0.00);
		
		if (clientCode.equals("226.001")) {
			double vatAmt = 0;
			if (objBean.getListPOTaxDtl() != null) {
				for (clsPOTaxDtlModel objPOTaxDtlModel : objBean.getListPOTaxDtl()) {
					if (objPOTaxDtlModel.getStrTaxDesc().equals("VAT 16%"))
						vatAmt = objPOTaxDtlModel.getStrTaxAmt();
				}
			}
			finalAmt = finalAmt + objBean.getDblFreight() + objBean.getDblInsurance() + objBean.getDblOtherCharges() + objBean.getDblClearingAgentCharges()+objBean.getDblVATClaim();
			objPurchaseOrderHdModel.setDblFinalAmt(finalAmt*currConversion);
			
			double exchangeRate=(dblTotal + taxAmt + extraAmt) - discountAmt;
			exchangeRate=exchangeRate+objBean.getDblFreight() + objBean.getDblInsurance() + objBean.getDblOtherCharges() + objBean.getDblClearingAgentCharges();
			exchangeRate = exchangeRate - vatAmt;
			objPurchaseOrderHdModel.setDblExchangeRate(exchangeRate*currConversion);
		}

		int qtyDecPlace=2;
		try{
			qtyDecPlace= Integer.parseInt(req.getSession().getAttribute("qtyDecPlace").toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		clsNumberToWords obj = new clsNumberToWords();
		objPurchaseOrderHdModel.setStrFinalAmtInWord(obj.getNumberInWorld(funGetDecimalFormatByGlobalPattern(finalAmt,qtyDecPlace)));
		objPurchaseOrderHdModel.setStrPropCode(propCode);
		objPurchaseOrderHdModel.setDblFOB(objBean.getDblFOB()*currConversion);
		objPurchaseOrderHdModel.setDblInsurance(objBean.getDblInsurance()*currConversion);
		objPurchaseOrderHdModel.setDblFreight(objBean.getDblFreight()*currConversion);
		objPurchaseOrderHdModel.setDblOtherCharges(objBean.getDblOtherCharges()*currConversion);
		double CIFAmt=objBean.getDblCIF()-discountAmt;
		objPurchaseOrderHdModel.setDblCIF(CIFAmt*currConversion);
		objPurchaseOrderHdModel.setDblClearingAgentCharges(objBean.getDblClearingAgentCharges()*currConversion);
		objPurchaseOrderHdModel.setDblVATClaim(objBean.getDblVATClaim()*currConversion);

		return objPurchaseOrderHdModel;
	}


	/**
	 * Open Pending Purchase Indent for PO
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmPIforPO", method = RequestMethod.GET)
	public ModelAndView funOpenPIforPO() {
		return new ModelAndView("frmPIforPO");

	}

	/**
	 * Load Pending Purchase Indent for PO
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadPIforPO", method = RequestMethod.GET)
	public @ResponseBody List funLoadHelpPIforPO(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		return objPurchaseOrderService.funGetHelpdataPIforPo(clientCode, propCode);
	}

	@RequestMapping(value = "/getPODate", method = RequestMethod.GET)
	public @ResponseBody String funGetPODate(HttpServletRequest request) {
		String PODate = null;
		objGlobal = new clsGlobalFunctions();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String code = request.getParameter("POCode").toString();
		clsPurchaseOrderHdModel objPurchaseOrderHdModel = objPurchaseOrderService.funGetObject(code, clientCode);
		PODate = objGlobal.funGetDate("yyyy/MM/dd", objPurchaseOrderHdModel.getDtPODate());
		return PODate;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param POModel
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String POCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsPurchaseOrderHdModel POModel = objPurchaseOrderService.funGetObject(POCode, clientCode);
			if (null != POModel) {
				List<clsPurchaseOrderDtlModel> listPODtl = objPurchaseOrderService.funGetPODtlList(POModel.getStrPOCode(), clientCode);
				if (null != listPODtl && listPODtl.size() > 0) {
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + POModel.getStrPOCode() + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (!list.isEmpty()) {
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(POModel);
						model.setStrClientCode(clientCode);
						if (strTransMode.equalsIgnoreCase("Deleted")) {
							model.setStrTransCode(POModel.getStrPOCode());
						} else {
							model.setStrTransCode(POModel.getStrPOCode() + "-" + count);

						}
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobal.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						for (clsPurchaseOrderDtlModel ob : listPODtl) {
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(ob.getStrProdCode());
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrRemarks(ob.getStrRemarks());
							AuditMode.setDblDiscount(ob.getDblDiscount());
							AuditMode.setDblQty(ob.getDblOrdQty());
							AuditMode.setDblUnitPrice(ob.getDblPrice());
							AuditMode.setDblTax(ob.getDblTax());
							AuditMode.setDblTaxableAmt(ob.getDblTaxableAmt());
							AuditMode.setDblTaxAmt(ob.getDblTaxAmt());
							AuditMode.setStrTaxType(ob.getStrTaxType());
							AuditMode.setDblTotalPrice(ob.getDblTotalPrice());
							AuditMode.setStrPICode(ob.getStrPICode());
							AuditMode.setStrClientCode(ob.getStrClientCode());
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	/**
	 * Prepare Audit HdModel
	 * 
	 * @param POModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsPurchaseOrderHdModel POModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (POModel != null) {
			AuditHdModel.setStrTransCode(POModel.getStrPOCode());
			AuditHdModel.setDtTransDate(POModel.getDtPODate());
			AuditHdModel.setStrTransType("Purchase Order");
			// AuditHdModel.setStrTransMode("Edit");
			AuditHdModel.setStrSuppCode(POModel.getStrSuppCode());
			AuditHdModel.setStrAgainst(POModel.getStrAgainst());
			AuditHdModel.setStrClosePO(POModel.getStrClosePO());
			AuditHdModel.setDtDelDate(POModel.getDtDelDate());
			AuditHdModel.setStrPayMode(POModel.getStrPayMode());
			AuditHdModel.setDtPayDate(POModel.getDtPayDate());
			AuditHdModel.setDblDiscount(POModel.getDblDiscount());
			AuditHdModel.setDblTaxAmt(POModel.getDblTaxAmt());
			AuditHdModel.setDblExtra(POModel.getDblExtra());
			AuditHdModel.setDblSubTotal(POModel.getDblTotal());
			AuditHdModel.setDblTotalAmt(POModel.getDblFinalAmt());
			AuditHdModel.setStrExcise(POModel.getStrExcise());
			AuditHdModel.setStrAuthorise(POModel.getStrAuthorise());
			AuditHdModel.setStrPerRef(POModel.getStrPerRef());
			AuditHdModel.setStrRefNo(POModel.getStrYourRef());
			AuditHdModel.setStrAmedment(POModel.getStrAmedment());
			AuditHdModel.setStrAmntNO(POModel.getStrAmntNO());
			AuditHdModel.setStrPayMode(POModel.getStrPayMode());
			AuditHdModel.setStrSAddress1(POModel.getStrSAddress1());
			AuditHdModel.setStrSAddress2(POModel.getStrSAddress2());
			AuditHdModel.setStrSCity(POModel.getStrSCity());
			AuditHdModel.setStrSCountry(POModel.getStrSCountry());
			AuditHdModel.setStrSPin(POModel.getStrSPin());
			AuditHdModel.setStrSState(POModel.getStrSState());
			AuditHdModel.setStrVAddress1(POModel.getStrVAddress1());
			AuditHdModel.setStrVAddress2(POModel.getStrVAddress2());
			AuditHdModel.setStrVCity(POModel.getStrVCity());
			AuditHdModel.setStrVState(POModel.getStrVState());
			AuditHdModel.setStrVCountry(POModel.getStrVCountry());
			AuditHdModel.setStrFinalAmtInWord(POModel.getStrFinalAmtInWord());
			AuditHdModel.setStrCurrency(POModel.getStrCurrency());
			AuditHdModel.setStrEOE(POModel.getStrEOE());
			AuditHdModel.setStrUserAmed(POModel.getStrUserAmed());
			AuditHdModel.setDblConversion(POModel.getDblConversion());
			AuditHdModel.setStrCode(POModel.getStrSOCode());
			AuditHdModel.setStrEdit(POModel.getStrEdit());
			AuditHdModel.setDtDateCreated(POModel.getDtDateCreated());
			AuditHdModel.setStrUserCreated(POModel.getStrUserCreated());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrLocCode("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrNarration("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");

		}
		return AuditHdModel;

	}

	/**
	 * End Function Audit
	 */

	/**
	 * Open Purchase Indent Slip
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmPurchaseOrderSlip", method = RequestMethod.GET)
	public ModelAndView funOpenPOSlipForm(Map<String, Object> model, HttpServletRequest request) {
		request.getSession().setAttribute("formName", "frmWebStockHelpPurchaseOrderSlip");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPurchaseOrderSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmPurchaseOrderSlip", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/openPOSlip", method = RequestMethod.GET)
	public void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		try {
			String POCode = req.getParameter("rptPOCode").toString();
			req.getSession().removeAttribute("rptPOCode");
			String type = "pdf";
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			String strPOCodes[] = POCode.split(",");
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			for (int i = 0; i < strPOCodes.length; i++) {
				JasperPrint p = funCallRangePrintReport(strPOCodes[i], resp, req);
				jprintlist.add(p);
			}
			if (type.trim().equalsIgnoreCase("pdf")) {
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptPOSlip." + type.trim());
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/invokePurchaseOrderSlip", method = RequestMethod.GET)
	public void funCallReportOnClick(@RequestParam(value = "docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) throws MessagingException {
		String type = "pdf";
		funCallReport(docCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void funCallReport(String POcode, String type, HttpServletResponse resp, HttpServletRequest req) {
//		try {
//			objGlobal = new clsGlobalFunctions();
//			Connection con = objGlobal.funGetConnection(req);
//			String clientCode = req.getSession().getAttribute("clientCode").toString();
//			String companyName = req.getSession().getAttribute("companyName").toString();
//			String userCode = req.getSession().getAttribute("usercode").toString();
//			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
//
//			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
//			if (objSetup == null) {
//				objSetup = new clsPropertySetupModel();
//			}
//			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip.jrxml");
//			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
//			String sql = "SELECT po.strPOCode,date(po.dtPODate) as dtPODate,po.strSuppCode,po.strAgainst,po.strSOCode,po.dblFinalAmt," + " po.strVAddress1,po.strVAddress2,po.strVCity,po.strVState,po.strVCountry,po.strVPin,po.strSAddress1," + " po.strSAddress2,po.strSCity,po.strSState,po.strSCountry,po.strSPin,po.strYourRef,po.strPerRef,po.strEOE,"
//					+ " po.strCode,date(po.dtDelDate) as dtDelDate,po.dblExtra,po.strFinalAmtInWord as AmtInWords,po.dblDiscount," + " po.strPayMode,po.strCurrency,po.strAmedment,po.strAmntNO,po.stredit,po.strUserAmed,date(po.dtPayDate) as dtPayDate,"
//					+ " po.dblConversion,po.dtLastModified,po.strAuthorise,po.dblTaxAmt,s.strPName,s.strContact,s.strPhone,s.strMobile,s.strFax, s.strMAdd1, s.strMAdd2,s.strMCity," + " s.strMPin,s.strMState,s.strMCountry,u.strUserName,u.strSignatureImg FROM tblpurchaseorderhd AS po " + " left outer JOIN  tblpartymaster AS s ON po.strSuppCode = s.strPCode and s.strClientCode='" + clientCode + "' "
//					+ " left outer join  tbluserhd u on po.strUserModified=u.strUserCode and u.strClientCode='" + clientCode + "' " + " left outer join tblcurrencymaster c on po.strCurrency=c.strCurrencyCode and c.strClientCode='" + clientCode + "' " + " WHERE (po.strPOCode = '" + POcode + "' and po.strClientCode='" + clientCode + "' ) ";
//			JasperDesign jd = JRXmlLoader.load(reportName);
//			JRDesignQuery newQuery = new JRDesignQuery();
//			newQuery.setText(sql);
//			jd.setQuery(newQuery);
//			String sql2 = "SELECT  1 slno,   po.strProdCode,p.strProdName as strProdName, p.strUOM, po.dblOrdQty, po.dblPrice," + " po.dbldiscount,po.dblOrdQty*po.dblPrice-po.dbldiscount dblamt,  p.strPartNo,po.dblWeight,p.strCalAmtOn," + " po.strProdChar,p.strSpecification,po.strProcessCode,pr.strProcessName,po.strRemarks,ph.strEOE,po.strUpdate, ph.dblExtra "
//					+ " FROM  tblpurchaseorderdtl po  INNER JOIN tblpurchaseorderhd ph ON po.strPOCode = ph.strPOCode and ph.strClientCode='" + clientCode + "' " + " INNER JOIN tblproductmaster p ON po.strProdCode = p.strProdCode and p.strClientCode='" + clientCode + "' " + " left outer JOIN tblprocessmaster pr ON po.strProcessCode = pr.strProcessCode and pr.strClientCode='" + clientCode + "' "
//					+ "  where po.strPOCode='" + POcode + "' and po.strClientCode='" + clientCode + "' order by po.intindex";
//			JRDesignQuery subQuery = new JRDesignQuery();
//			subQuery.setText(sql2);
//			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
//			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPODtl");
//			subDataset.setQuery(subQuery);
//			String sql3 = "select a.strTCName,if(b.strTCDesc='null','',b.strTCDesc) as strTCDesc  from tbltctransdtl b,tbltcmaster a" + " where b.strTCCode=a.strTCCode and  b.strTransCode='" + POcode + "'" + " and b.strTransType='Purchase Order' and  b.strClientCode='" + clientCode + "'  and a.strClientCode='" + clientCode + "' ";
//			JRDesignQuery subQuery1 = new JRDesignQuery();
//			subQuery1.setText(sql3);
//			JRDesignDataset subDataset1 = (JRDesignDataset) datasetMap.get("dsTC");
//			subDataset1.setQuery(subQuery1);
//
//			String taxQuery = " select a.strTaxDesc,a.strTaxAmt from tblpotaxdtl a where strPOCode='" + POcode + "' and strClientCode='" + clientCode + "' ";
//			JRDesignQuery subQuery2 = new JRDesignQuery();
//			subQuery2.setText(taxQuery);
//			JRDesignDataset subDataset2 = (JRDesignDataset) datasetMap.get("dsTax");
//			subDataset2.setQuery(subQuery2);
//
//			JasperReport jr = JasperCompileManager.compileReport(jd);
//
//			HashMap hm = new HashMap();
//			hm.put("strCompanyName", companyName);
//			hm.put("strUserCode", userCode.toUpperCase());
//			hm.put("strImagePath", imagePath);
//			hm.put("strAddr1", objSetup.getStrAdd1());
//			hm.put("strAddr2", objSetup.getStrAdd2());
//			hm.put("strCity", objSetup.getStrCity());
//			hm.put("strState", objSetup.getStrState());
//			hm.put("strCountry", objSetup.getStrCountry());
//			hm.put("strPin", objSetup.getStrPin());
//			hm.put("strFax", objSetup.getStrFax());
//			hm.put("strPhoneNo", objSetup.getStrPhone());
//			hm.put("strEmailAddress", objSetup.getStrEmail());
//			hm.put("strWebSite", objSetup.getStrWebsite());
//			hm.put("strVAT", objSetup.getStrVAT());
//			hm.put("strCST", objSetup.getStrCST());
//			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
//			if (type.trim().equalsIgnoreCase("pdf")) {
//				JRExporter exporterXLS = new JRPdfExporter();
//				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
//				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
//				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPOSlip_" + POcode + "." + type.trim());
//				exporterXLS.exportReport();
//				resp.setContentType("application/pdf");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	
	public void funCallReport(String POcode, String type, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		String strPOCodes[] = POcode.split(",");
		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		for (int i = 0; i < strPOCodes.length; i++) {
			JasperPrint p = funCallRangePrintReport(strPOCodes[i], resp, req);
			jprintlist.add(p);
		}
		if (type.trim().equalsIgnoreCase("pdf")) {
			JRExporter exporter = new JRPdfExporter();
			resp.setContentType("application/pdf");
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
			resp.setHeader("Content-Disposition", "inline;filename=" + "rptPOSlip." + type.trim());
			exporter.exportReport();
			servletOutputStream.flush();
			servletOutputStream.close();
		}
		} catch (IOException | JRException e) {
		e.printStackTrace();
	}
	}
	/**
	 * Range PO Printing
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPurchaseOrderSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", objBean.getDtToDate());

		String type = objBean.getStrDocType();
		String strFromPOCode = objBean.getStrFromDocCode();
		String strToPOCode = objBean.getStrToDocCode();
		String tempSupp[] = objBean.getStrSuppCode().split(",");
		String strSuppCodes = "";

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			String sql = "select strPOCode from tblpurchaseorderhd a where date(a.dtPODate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "'";
			if (objBean.getStrSuppCode().trim().length() > 0) {
				for (int i = 0; i < tempSupp.length; i++) {
					if (strSuppCodes.length() > 0) {
						strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i] + "' ";
					} else {
						strSuppCodes = " a.strSuppCode='" + tempSupp[i] + "' ";

					}
				}
				sql = sql + " and " + "(" + strSuppCodes + ") ";
			}

			if (strFromPOCode.trim().length() > 0 && strToPOCode.trim().length() > 0) {
				sql = sql + " and a.strPOCode between '" + strFromPOCode + "' and '" + strToPOCode + "' ";
			}

			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (list != null && !list.isEmpty()) {
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int i = 0; i < list.size(); i++) {
					JasperPrint p = funCallRangePrintReport(list.get(i).toString(), resp, req);
					jprintlist.add(p);
				}

				if (type.trim().equalsIgnoreCase("pdf")) {

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptPOSlip." + type.trim());
					exporter.exportReport();

					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptPOSlip." + type.trim());
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
	public JasperPrint funCallRangePrintReport(String POcode, HttpServletResponse resp, HttpServletRequest req) {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			JasperPrint p = null;
			try {

				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String userCode = req.getSession().getAttribute("usercode").toString();
				String propertyCode = req.getSession().getAttribute("propertyCode").toString();

				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
				String billAddress = "";
				String shippingAddress = "";
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				} else {
					billAddress = objSetup.getStrBAdd1() + "," + objSetup.getStrBAdd2() + "," + objSetup.getStrBCity() + "," + objSetup.getStrBState() + "," + objSetup.getStrBPin();
					shippingAddress = objSetup.getStrSAdd1() + "," + objSetup.getStrSAdd2() + "," + objSetup.getStrSCity() + "," + objSetup.getStrSState() + "," + objSetup.getStrSPin();
					;
				}
				//global format--> format 3
				String reportName=servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipForAll.jrxml");
				if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 1"))
				{
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip.jrxml");
					if (clientCode.equals("226.001")) {
	//					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip1.jrxml");
						reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip2.jrxml");
					}
				}
				else if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 2"))
				{
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipWithoutAmtRateAndDisc.jrxml");
				}
				else if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 3")){
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlipForAll.jrxml");
				}

				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				
				double dblFOB = 0.0,dblFOBInBase=0.00;
				double dblFreight = 0.0,dblFreightInBase=0.00;
				double dblInsurance = 0.0,dblInsuranceInBase=0.00;
				double dblOtherCharges = 0.0,dblOtherChargesInBase=0.00;
				double dblCIF = 0.0,dblCIFInBase=0.00;
				double conversionRate = 1;
				double dblVATClaim = 0.0;
				double dblClearingAgentCharges = 0.0;
				String strPartyType = "";
				double costOfConsignmentInBase=0.0,costOfConsignment=0.00;
				double totalTaxAmt=0.00;
				double grandTotalInBaseCurrency=0.00;
				String strCurrencyName="";
				String sql1 = "select a.dblFOB,a.dblFreight,a.dblInsurance,a.dblOtherCharges,a.dblCIF,c.strCurrencyName,a.dblConversion,a.dblClearingAgentCharges,a.dblVATClaim"
						+ ",b.strPartyType,a.dblExchangeRate,a.dblTaxAmt,a.dblFinalAmt " 
						+ " from tblpurchaseorderhd a,tblpartymaster b,tblcurrencymaster c " 
						+ " where a.strSuppCode=b.strPCode and a.strPOCode='" + POcode + "' and b.strPartyType='Foreign' "
						+ " and a.strCurrency=c.strCurrencyCode and a.strClientCode='" + clientCode + "' ";
				List list = objGlobalFunctionsService.funGetList(sql1, "sql");
				if (list != null && !list.isEmpty()) {
					Object[] ob = (Object[]) list.get(0);
					conversionRate = Double.parseDouble(ob[6].toString());
					dblFOB = Double.parseDouble(ob[0].toString()) / conversionRate;
					dblFreight = Double.parseDouble(ob[1].toString()) / conversionRate;
					dblInsurance = Double.parseDouble(ob[2].toString()) / conversionRate;
					dblOtherCharges = Double.parseDouble(ob[3].toString()) / conversionRate;
					dblCIF = Double.parseDouble(ob[4].toString()) / conversionRate;
					strCurrencyName=ob[5].toString();
					dblFOBInBase = Double.parseDouble(ob[0].toString());
					dblFreightInBase = Double.parseDouble(ob[1].toString());
					dblInsuranceInBase = Double.parseDouble(ob[2].toString());
					dblOtherChargesInBase = Double.parseDouble(ob[3].toString());
					dblCIFInBase = Double.parseDouble(ob[4].toString());
					
					dblClearingAgentCharges = Double.parseDouble(ob[7].toString()) / conversionRate;
					dblVATClaim = Double.parseDouble(ob[8].toString()) / conversionRate;
					strPartyType = ob[9].toString();
					costOfConsignment=Double.parseDouble(ob[10].toString()) ;
					costOfConsignmentInBase=Double.parseDouble(ob[10].toString()) / conversionRate;
					
					totalTaxAmt=Double.parseDouble(ob[11].toString()) ;
					grandTotalInBaseCurrency = Double.parseDouble(ob[12].toString()) / conversionRate;
				}
				else{
					sql1="select ifnull(b.strCurrencyName,''),a.dblConversion from tblpurchaseorderhd a left outer join tblcurrencymaster b on a.strCurrency=b.strCurrencyCode "
							+ " where a.strPOCode='"+POcode + "' AND a.strClientCode='" + clientCode + "';";
					list = objGlobalFunctionsService.funGetList(sql1, "sql");
					if (list != null && !list.isEmpty()) {
						Object[] ob = (Object[]) list.get(0);
						conversionRate = Double.parseDouble(ob[1].toString());
						strCurrencyName=ob[0].toString();
					}
				}
				String sql = "SELECT po.strPOCode,date(po.dtPODate) as dtPODate,po.strSuppCode,po.strAgainst,po.strSOCode,po.dblFinalAmt/"+conversionRate+" as dblFinalAmt, po.strVAddress1,po.strVAddress2,po.strVCity,po.strVState,po.strVCountry,po.strVPin,po.strSAddress1," + " po.strSAddress2,po.strSCity,po.strSState,po.strSCountry,po.strSPin,po.strYourRef,po.strPerRef,po.strEOE,"
						+ " po.strCode,date(po.dtDelDate) as dtDelDate,po.dblExtra/"+conversionRate+" as dblExtra,po.strFinalAmtInWord as AmtInWords,po.dblDiscount/"+conversionRate+" as dblDiscount," + " po.strPayMode,po.strCurrency,po.strAmedment,po.strAmntNO,po.stredit,po.strUserAmed,date(po.dtPayDate) as dtPayDate,"
						+ " po.dblConversion,po.dtLastModified,po.strAuthorise,po.dblTaxAmt/"+conversionRate+" as dblTaxAmt,s.strPName,s.strContact,s.strPhone,s.strMobile,s.strFax, s.strMAdd1, s.strMAdd2,s.strMCity," + " s.strMPin,s.strMState,s.strMCountry,u.strUserName,u.strSignatureImg, " + " ((po.dblDiscount*100)/po.dblTotal) as dblDisPer "
						+ " ,po.strUserCreated,po.strAuthLevel1,po.strAuthLevel2 " 
						+ " FROM tblpurchaseorderhd AS po "
						+ " left outer JOIN  tblpartymaster AS s ON po.strSuppCode = s.strPCode and s.strClientCode='" + clientCode + "' " + " left outer join  tbluserhd u on po.strUserModified=u.strUserCode and u.strClientCode='" + clientCode + "' " + " left outer join tblcurrencymaster c on po.strCurrency=c.strCurrencyCode and c.strClientCode='" + clientCode + "' " + " WHERE (po.strPOCode = '"
						+ POcode + "' and po.strClientCode='" + clientCode + "' ) ";
				System.out.println(sql);
				
				String taxQuery = "";
				if(objSetup.getStrPOSlipFormat().equalsIgnoreCase("Format 1"))
				{
				if (strPartyType.equalsIgnoreCase("Foreign") && clientCode.equals("226.001")) {
					reportName = servletContext.getRealPath("/WEB-INF/reports/rptPurchaseOrderSlip_ForeignSupplier.jrxml");
				}
				}
				JasperDesign jd = JRXmlLoader.load(reportName);
				JRDesignQuery newQuery = new JRDesignQuery();
				newQuery.setText(sql);
				jd.setQuery(newQuery);

				double POProductQty=0;
				String sqlPurDtl = "SELECT  po.strProdCode, po.dblOrdQty, po.dblPrice" 
					+ " FROM  tblpurchaseorderdtl po "
					+ " where po.strPOCode='" + POcode + "' and po.strClientCode='" + clientCode + "' ";
				List listPurDtl = objGlobalFunctionsService.funGetList(sqlPurDtl, "sql");
				if (listPurDtl != null && !listPurDtl.isEmpty()) {
					Object[] ob = (Object[]) listPurDtl.get(0);
					POProductQty=Double.parseDouble(ob[1].toString());
				}
				
				String sql2 = "SELECT  1 slno, po.strProdCode,p.strProdName as strProdName, p.strUOM, po.dblOrdQty, po.dblPrice/"+conversionRate+" as dblPrice ," 
					+ " po.dbldiscount/"+conversionRate+" as dbldiscount,(po.dblOrdQty*po.dblPrice-po.dbldiscount)/"+conversionRate+" dblamt,  p.strPartNo,po.dblWeight,p.strCalAmtOn," + " po.strProdChar,p.strSpecification,po.strProcessCode,pr.strProcessName,po.strRemarks,ph.strEOE,po.strUpdate,ph.dblExtra/"+conversionRate+" as dblExtra "
					+ " FROM  tblpurchaseorderdtl po  INNER JOIN tblpurchaseorderhd ph ON po.strPOCode = ph.strPOCode and ph.strClientCode='" + clientCode + "' " + " INNER JOIN tblproductmaster p ON po.strProdCode = p.strProdCode and p.strClientCode='" + clientCode + "' " + " left outer JOIN tblprocessmaster pr ON po.strProcessCode = pr.strProcessCode and pr.strClientCode='" + clientCode + "' "
					+ "  where po.strPOCode='" + POcode + "' and po.strClientCode='" + clientCode + "' order by po.intindex";
				System.out.println(sql2);
				JRDesignQuery subQuery = new JRDesignQuery();
				subQuery.setText(sql2);
				Map<String, JRDataset> datasetMap = jd.getDatasetMap();
				JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsPODtl");
				subDataset.setQuery(subQuery);
				String sql3 = "select a.strTCName,if(b.strTCDesc='null','',b.strTCDesc) as strTCDesc  from tbltctransdtl b,tbltcmaster a" + " where b.strTCCode=a.strTCCode and  b.strTransCode='" + POcode + "'" + " and b.strTransType='Purchase Order' and  b.strClientCode='" + clientCode + "'  and a.strClientCode='" + clientCode + "' ";
				JRDesignQuery subQuery1 = new JRDesignQuery();
				subQuery1.setText(sql3);
				JRDesignDataset subDataset1 = (JRDesignDataset) datasetMap.get("dsTC");
				subDataset1.setQuery(subQuery1);

				
				String taxQueryForBase="";
				
				taxQuery=" select a.strTaxDesc,a.strTaxAmt as strTaxAmt from tblpotaxdtl a where strPOCode='" + POcode + "' and strClientCode='" + clientCode + "' ";
				if (clientCode.equals("226.001")) {
					
					taxQuery=" select a.strTaxDesc";

					if (strPartyType.equalsIgnoreCase("Foreign"))
					{
						taxQuery+=",a.strTaxAmt AS strBaseTaxAmt";
					}
					taxQuery+=",a.strTaxAmt/" + conversionRate + " as strTaxAmt from tblpotaxdtl a where strPOCode='" + POcode + "' and strClientCode='" + clientCode + "' ";
				}

				JRDesignQuery subQuery2 = new JRDesignQuery();
				subQuery2.setText(taxQuery);
				JRDesignDataset subDataset2 = (JRDesignDataset) datasetMap.get("dsTax");
				subDataset2.setQuery(subQuery2);
				
//				if (strPartyType.equalsIgnoreCase("Foreign") && clientCode.equals("226.001")) {
//					JRDesignQuery subQuery3 = new JRDesignQuery();
//					subQuery3.setText(taxQueryForBase);
//					JRDesignDataset subDataset3 = (JRDesignDataset) datasetMap.get("dsOriginalAmtTax");
//					subDataset3.setQuery(subQuery3);
//				}

				String imgChefsCornerQualityPolicy ="";
				if(clientCode.equals("211.001"))//CHEFS CORNER
				{
					//imgChefsCornerQualityPolicy=servletContext.getRealPath("/resources/images/imgChefsCornerQualityPolicy.jpg");
					imgChefsCornerQualityPolicy=""
							+ "Quality Policy"
							+ "\n"
							+ "\n\t We at Chefs Corner are committed to provide Healthy,Quality,Tasty and Variety foods "
							+ "to our customer in hygienic environment at all times by continual improvement and thus achieve "
							+ "customer satisfaction by fulfilling all applicable requirements."
							+ "\n\n"
							+ "Quality Objectives"
							+ "\n"
							+ "\n> To Achieve Customer Satisfaction"
							+ "\n> To Reduce Customer Complaints"
							+ "\n> To Provide awareness to all employees"
							+ "\n> To Reduce Wastage"
							+ "\n";
				}
				
				
				
				JasperReport jr = JasperCompileManager.compileReport(jd);

				HashMap hm = new HashMap();
				hm.put("strCompanyName", companyName);
				hm.put("strUserCode", userCode.toUpperCase());
				hm.put("strImagePath", imagePath);
				hm.put("imgQualityPolicy", imgChefsCornerQualityPolicy);
				hm.put("strAddr1", objSetup.getStrAdd1());
				hm.put("strAddr2", objSetup.getStrAdd2());
				hm.put("strCity", objSetup.getStrCity());
				hm.put("strState", objSetup.getStrState());
				hm.put("strCountry", objSetup.getStrCountry());
				hm.put("strPin", objSetup.getStrPin());
				hm.put("strFax", objSetup.getStrFax());
				hm.put("strPhoneNo", objSetup.getStrPhone());
				hm.put("strEmailAddress", objSetup.getStrEmail());
				hm.put("strWebSite", objSetup.getStrWebsite());
				hm.put("strVAT", objSetup.getStrVAT());
				hm.put("strCST", objSetup.getStrCST());
				hm.put("strSAdd", shippingAddress);
				hm.put("strBAdd", billAddress);
				

				if (strPartyType.equalsIgnoreCase("Foreign") && clientCode.equals("226.001")) {
					
					double costOfProduct=0;
					hm.put("dblFOB", funGetFormattedString(dblFOB));
					hm.put("dblFreight", funGetFormattedString(dblFreight));
					hm.put("dblInsurance", funGetFormattedString(dblInsurance));
					hm.put("dblOtherCharges", funGetFormattedString(dblOtherCharges));
					hm.put("dblCIF", funGetFormattedString(dblCIF));
					hm.put("dblClearingAgentCharges", funGetFormattedString(dblClearingAgentCharges));
					hm.put("dblVATClaim", funGetFormattedString(dblVATClaim));
					hm.put("grandTotalInBaseCurrency", funGetFormattedString(grandTotalInBaseCurrency));
					hm.put("dblCostOfConsignmentInBase",funGetFormattedString(costOfConsignmentInBase));
					costOfProduct=costOfConsignmentInBase/POProductQty;
					hm.put("dblCostOfProduct",costOfProduct);
					
					hm.put("dblFOBOriginal", dblFOBInBase);
					hm.put("dblFreightOriginal", dblFreightInBase);
					hm.put("dblInsuranceOriginal", dblInsuranceInBase);
					hm.put("dblOtherChargesOriginal", dblOtherChargesInBase);
					hm.put("dblCIFOriginal", dblCIFInBase);
					hm.put("dblExchangeRate", conversionRate);
					hm.put("dblCostOfConsignment",costOfConsignment/conversionRate );
					hm.put("strForeignPartyType",strPartyType);
					hm.put("strCurrency", strCurrencyName);
						
				}else if (clientCode.equals("226.001")) {
					
					hm.put("dblFOB", String.valueOf(dblFOB));
					hm.put("dblFreight", String.valueOf(dblFreight));
					hm.put("dblInsurance", String.valueOf(dblInsurance));
					hm.put("dblOtherCharges", String.valueOf(dblOtherCharges));
					hm.put("dblCIF", String.valueOf(dblCIF));
					hm.put("dblClearingAgentCharges", String.valueOf(dblClearingAgentCharges));
					hm.put("dblVATClaim", String.valueOf(dblVATClaim));
					
					hm.put("dblFOBOriginal", String.valueOf(dblFOBInBase));
					hm.put("dblFreightOriginal", String.valueOf(dblFreightInBase));
					hm.put("dblInsuranceOriginal", String.valueOf(dblInsuranceInBase));
					hm.put("dblOtherChargesOriginal", String.valueOf(dblOtherChargesInBase));
					hm.put("dblCIFOriginal", String.valueOf(dblCIFInBase));
					hm.put("strLocalPartyType",strPartyType);
					hm.put("strCurrency", strCurrencyName);
					hm.put("dblConversionRate", conversionRate);
				}

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



	
	private String funGetFormattedString(double input) {
		String output = BigDecimal.valueOf(input).toPlainString();
		String pattern = "######.#######";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		output = decimalFormat.format(input);

		if (output.contains(".")) {
			String[] arr = output.split("\\.");
			if (arr[1].toString().length() < 7) {
				int len = 7 - arr[1].length();
				String zeros = "";
				for (int i = 0; i < len; i++) {
					zeros = zeros + "0";
				}

				output = output + zeros;
			}
		} else {
			output = output + ".0000000";
		}

		return output;
	}

	@RequestMapping(value = "/loadPropertyAddress", method = RequestMethod.GET)
	public @ResponseBody clsPropertySetupModel funLoadPropertyAddress(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		return objSetup;
	}

	@RequestMapping(value = "/calculateBudgetAmt", method = RequestMethod.GET)
	public @ResponseBody List funCalculateBudgetAmt(@RequestParam("strProdCode") String strProdCode, @RequestParam("poDate") String poDate, HttpServletRequest req) {
		List result = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();

		String sql = "select c.strGCode,c.strGName  from  tblproductmaster a ,tblsubgroupmaster b,tblgroupmaster c where a.strProdCode='" + strProdCode + "' " + " and a.strSGCode=b.strSGCode and b.strGCode=c.strGCode ";

		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		if (list != null && !list.isEmpty()) {
			String[] date = poDate.split("-");
			String month = date[1];
			Object[] obj = (Object[]) list.get(0);
			String gCode = obj[0].toString();
			String gName = obj[1].toString();
			double grnAmt = 0.0;
			double budAmt = 0.0;
			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] arrMonth = startDate.split("/");
			int strtmonth = Integer.parseInt(arrMonth[1].toString());
			String financialYear = req.getSession().getAttribute("financialYear").toString();
			int j = funClaculateMonthNo(strtmonth, Integer.parseInt(date[1]));

			String sqlbudget = "select ifnull(strMonth" + j + ",0) from tblbudgetmasterhd a ,tblbudgetmasterdtl b  where a.strBudgetCode=b.strBudgetCode " + " and a.strPropertyCode='" + propCode + "' and a.strFinYear='" + financialYear + "' and b.strGroupCode='" + gCode + "' and " + " a.strClientCode='" + clientCode + "'";
			List listBudget = objGlobalFunctionsService.funGetList(sqlbudget, "sql");

			if (listBudget.size() > 0) {
				budAmt = Double.parseDouble(listBudget.get(0).toString());
			}

			String sqlGRNQuery = " select ifnull(sum(a.dblTotal),0) from tblgrnhd a " + " left outer join tblgrndtl b on a.strGRNCode=b.strGRNCode " + " left outer join tblproductmaster c on b.strProdCode=c.strProdCode " + " left outer join tblsubgroupmaster d on c.strSGCode=d.strSGCode " + " left outer join tblgroupmaster e  on d.strGCode=e.strGCode   "
					+ " left outer join tbllocationmaster f on a.strLocCode=f.strLocCode " + " left outer join tblpropertymaster g on f.strPropertyCode=g.strPropertyCode  " + " where year(a.dtGRNDate) between '" + date[2] + "' and '" + date[2] + "'  " + " AND month(a.dtGRNDate) between '" + date[1] + "' and '" + date[1] + "' and e.strGCode='" + gCode + "' " + "  and g.strPropertyCode='"
					+ propCode + "' and a.strClientCode='" + clientCode + "' " + " group by e.strGCode ";
			List listGRN = objGlobalFunctionsService.funGetDataList(sqlGRNQuery, "sql");
			if (listGRN.size() > 0) {
				grnAmt = Double.parseDouble(listGRN.get(0).toString());
			}
			result.add(gName);
			result.add(grnAmt - budAmt);
		}

		return result;

	}

	private int funClaculateMonthNo(int strtMonth, int poMont) {
		int count = poMont - strtMonth;
		if (count >= 0) {
			count++;

		} else {

			count = 13 + count;
		}
		return count;

	}
	


private Double funGetDecimalFormatByGlobalPattern(double input,int qtyPlaces) {
		String output = BigDecimal.valueOf(input).toPlainString();
		String pattern = "##.";
		while(qtyPlaces!=0){
			pattern+="#";
			qtyPlaces--;
		}
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		output = decimalFormat.format(input);
		
		return Double.parseDouble(output);
	}

}
