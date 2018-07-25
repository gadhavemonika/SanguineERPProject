package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
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
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsComercialInvTaxBeanReport;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsInvoiceDtlBean;
import com.sanguine.crm.bean.clsInvoiceTaxDtlBean;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceHdModel_ID;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsTaxMasterService;
import com.sanguine.util.clsNumberToWords;
import com.sanguine.util.clsReportBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsCommercialTaxInnvoiceController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalfunction;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsLinkUpService objLinkupService;

	@Autowired
	private clsTaxMasterService objTaxMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@RequestMapping(value = "/frmCommercialTaxInnvoice", method = RequestMethod.GET)
	public ModelAndView funInvoice(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		// List<String> strAgainst = new ArrayList<>();
		// strAgainst.add("Direct");
		// strAgainst.add("Delivery Challan");
		// strAgainst.add("Sales Order");
		// model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCommercialTaxInnvoice_1", "command", new clsInvoiceBean());
		} else {
			return new ModelAndView("frmCommercialTaxInnvoice", "command", new clsInvoiceBean());
		}
	}

	// Save or Update Invoice
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/saveCommercialInvoice", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsInvoiceBean objBean, BindingResult result, HttpServletRequest req) {
		boolean flgHdSave = false;
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
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsInvoiceHdModel objHdModel = null;

			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			clsInvoiceHdModel objHdCommerModel = funPreparedHdCommerModel(objBean, clientCode, userCode, propCode, req);

			if (objCompModel.getStrWebBookModule().equals("Yes")) {
				String strJVNo = funGenrateJVforComercialTax(objHdCommerModel, clientCode, userCode, propCode, "InvComm", req);

				objHdCommerModel.setStrSOCode(strJVNo);

				objInvoiceHdService.funAddUpdateInvoiceHd(objHdCommerModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(objHdCommerModel.getStrInvCode()));
				req.getSession().setAttribute("rptInvCode", objHdCommerModel.getStrInvCode());

				return new ModelAndView("redirect:/frmCommercialTaxInnvoice.html?saddr=" + urlHits);
			} else {
				objInvoiceHdService.funAddUpdateInvoiceHd(objHdCommerModel);
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(objHdCommerModel.getStrInvCode()));
				req.getSession().setAttribute("rptInvCode", objHdCommerModel.getStrInvCode());

				return new ModelAndView("redirect:/frmCommercialTaxInnvoice.html?saddr=" + urlHits);
			}
		}

		return new ModelAndView("redirect:/frmCommercialTaxInnvoice.html?saddr=" + urlHits, "command", new clsInvoiceBean());

	}

	private clsInvoiceHdModel funPreparedHdCommerModel(clsInvoiceBean objBean, String clientCode, String userCode, String propCode, HttpServletRequest req) {

		String[] invDate = objBean.getDteInvDate().split("-");
		String dateInvoice = invDate[2] + "-" + invDate[1] + "-" + invDate[0];
		String invCode = objGlobalfunction.funGenerateDocumentCode("frmInvoice", dateInvoice, req);
		clsInvoiceHdModel objHdCommerModel = null;
		double dblCurrencyConv = 1.0;
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		if (objBean.getStrInvCode().trim().length() == 0) {

			objHdCommerModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(invCode, clientCode));
			objHdCommerModel.setIntid(0);
			objHdCommerModel.setStrUserCreated(userCode);
			objHdCommerModel.setDteCreatedDate(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsInvoiceHdModel objHdCommerModel1 = objInvoiceHdService.funGetInvoiceHd(objBean.getStrInvCode(), clientCode);
			if (null == objHdCommerModel1) {

				objHdCommerModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(invCode, clientCode));
				objHdCommerModel.setIntid(0);
				objHdCommerModel.setStrUserCreated(userCode);
				objHdCommerModel.setDteCreatedDate(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objHdCommerModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(objBean.getStrInvCode(), clientCode));
			}
		}

		objHdCommerModel.setStrUserModified(userCode);
		objHdCommerModel.setDteLastModified(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
		objHdCommerModel.setDteInvDate(objBean.getDteInvDate());
		objHdCommerModel.setStrAgainst(objBean.getStrAgainst());
		objHdCommerModel.setStrAuthorise(objBean.getStrAuthorise());
		objHdCommerModel.setStrAgainst("");
		if (null == objBean.getStrSOCode()) {
			objHdCommerModel.setStrSOCode("");
		} else {
			objHdCommerModel.setStrSOCode(objBean.getStrSOCode());
		}
		objHdCommerModel.setStrCustCode(objBean.getStrCustCode());
		objHdCommerModel.setStrInvNo("");
		objHdCommerModel.setStrDktNo(objBean.getStrDktNo());
		objHdCommerModel.setStrLocCode(objBean.getStrLocCode());
		objHdCommerModel.setStrMInBy(objBean.getStrMInBy());
		objHdCommerModel.setStrNarration(objBean.getStrNarration());
		objHdCommerModel.setStrPackNo(objBean.getStrPackNo());
		objHdCommerModel.setStrPONo(objBean.getStrPONo());
		objHdCommerModel.setStrReaCode(objBean.getStrReaCode());
		objHdCommerModel.setStrSAdd1(objBean.getStrSAdd1());
		objHdCommerModel.setStrSAdd2(objBean.getStrSAdd2());
		objHdCommerModel.setStrSCity(objBean.getStrSCity());
		objHdCommerModel.setStrSCtry(objBean.getStrSCtry());
		objHdCommerModel.setStrSerialNo(objBean.getStrSerialNo());

		objHdCommerModel.setStrSPin(objBean.getStrSPin());
		objHdCommerModel.setStrSState(objBean.getStrSState());
		objHdCommerModel.setStrTimeInOut(objBean.getStrTimeInOut());
		objHdCommerModel.setStrVehNo(objBean.getStrVehNo());
		objHdCommerModel.setStrWarraValidity(objBean.getStrWarraValidity());
		objHdCommerModel.setStrWarrPeriod(objBean.getStrWarrPeriod());
		objHdCommerModel.setStrAuthorise("");
		objHdCommerModel.setDblSubTotalAmt(0.0);
		objHdCommerModel.setStrVehNo("");

		objHdCommerModel.setStrUserCreated(userCode);
		objHdCommerModel.setDteCreatedDate(objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
		objHdCommerModel.setStrClientCode(clientCode);
		objHdCommerModel.setStrPONo("");
		objHdCommerModel.setStrExciseable("N");
		objHdCommerModel.setStrDulpicateFlag("N");
		objHdCommerModel.setStrWarraValidity("");
		objHdCommerModel.setStrWarrPeriod("");
		objHdCommerModel.setDblTotalAmt(0.0);
		objHdCommerModel.setDblTaxAmt(0.0);
		objHdCommerModel.setDblDiscountAmt(0.0);
		objHdCommerModel.setStrSettlementCode("");
		objHdCommerModel.setStrCurrencyCode(objSetup.getStrCurrencyCode());
		clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
		if (objModel == null) {
			dblCurrencyConv = 1;
			objHdCommerModel.setDblCurrencyConv(dblCurrencyConv);
		} else {
			dblCurrencyConv = objModel.getDblConvToBaseCurr();
			objHdCommerModel.setDblCurrencyConv(objModel.getDblConvToBaseCurr());
		}
		objHdCommerModel.setStrMobileNo("");

		double subTotal = 0.00;
		List listInvDtlModel = new ArrayList<clsInvoiceDtlBean>();
		for (clsInvoiceDtlBean obJspBean : objBean.getListclsInvoiceModelDtl()) {
			clsInvoiceModelDtl obDtl = new clsInvoiceModelDtl();
			subTotal += obJspBean.getDblUnitPrice() * obJspBean.getDblQty() * dblCurrencyConv;
			obDtl.setStrProdCode(obJspBean.getStrProdCode());
			obDtl.setStrCustCode(obJspBean.getStrCustCode());
			obDtl.setDblQty(obJspBean.getDblQty());
			obDtl.setDblPrice(obJspBean.getDblUnitPrice() * obJspBean.getDblQty() * dblCurrencyConv);
			obDtl.setDblWeight(obJspBean.getDblWeight());
			obDtl.setStrProdType(obJspBean.getStrProdType());
			obDtl.setStrPktNo(obJspBean.getStrPktNo());
			obDtl.setStrRemarks(obJspBean.getStrRemarks());
			obDtl.setIntindex(obJspBean.getIntindex());
			obDtl.setStrInvoiceable(obJspBean.getStrInvoiceable());
			obDtl.setStrSerialNo(obJspBean.getStrSerialNo());
			obDtl.setDblAssValue(obJspBean.getDblAssValue());
			obDtl.setDblUnitPrice(obJspBean.getDblUnitPrice() * dblCurrencyConv);
			obDtl.setDblBillRate(obJspBean.getDblBillRate() * dblCurrencyConv);
			obDtl.setStrSOCode(obJspBean.getStrSOCode());
			obDtl.setStrUOM(obJspBean.getStrUOM());

			listInvDtlModel.add(obDtl);
		}
		objHdCommerModel.setListInvDtlModel(listInvDtlModel);

		List listInvTaxDtlModel = new ArrayList<clsInvoiceDtlBean>();
		double dblGtotal = subTotal;
		double dblTotalTaxAmt = 0.00;
		if (objBean.getListInvoiceTaxDtl() != null && objBean.getListInvoiceTaxDtl().size() > 0) {
			for (clsInvoiceTaxDtlBean obJspTaxBean : objBean.getListInvoiceTaxDtl()) {
				clsInvoiceTaxDtlModel objTaxDtlModel = new clsInvoiceTaxDtlModel();

				objTaxDtlModel.setStrTaxCode(obJspTaxBean.getStrTaxCode());
				objTaxDtlModel.setStrTaxDesc(obJspTaxBean.getStrTaxDesc());
				objTaxDtlModel.setDblTaxableAmt(obJspTaxBean.getStrTaxableAmt());
				objTaxDtlModel.setDblTaxAmt(obJspTaxBean.getStrTaxAmt());

				dblGtotal += obJspTaxBean.getStrTaxAmt();
				dblTotalTaxAmt += obJspTaxBean.getStrTaxAmt();
				listInvTaxDtlModel.add(objTaxDtlModel);
			}
		}
		objHdCommerModel.setListInvTaxDtlModel(listInvTaxDtlModel);

		objHdCommerModel.setDblSubTotalAmt(subTotal);
		objHdCommerModel.setDblTotalAmt(subTotal);
		objHdCommerModel.setDblTaxAmt(dblTotalTaxAmt);
		objHdCommerModel.setDblGrandTotal(dblGtotal);

		return objHdCommerModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadComericalInvoiceHdData", method = RequestMethod.GET)
	public @ResponseBody clsInvoiceBean funAssignFields(@RequestParam("invCode") String invCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsInvoiceBean objBeanInv = new clsInvoiceBean();

		List<Object> objDC = objInvoiceHdService.funGetInvoice(invCode, clientCode);
		clsInvoiceHdModel objInvoiceHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++) {
			Object[] ob = (Object[]) objDC.get(i);
			objInvoiceHdModel = (clsInvoiceHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanInv = funPrepardComericalHdBean(objInvoiceHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsInvoiceModelDtl> listDCDtl = new ArrayList<clsInvoiceModelDtl>();
		clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(invCode, clientCode);

		List<clsInvoiceModelDtl> listInvDtlModel = objInvHDModelList.getListInvDtlModel();
		List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();
		for (int i = 0; i < listInvDtlModel.size(); i++) {
			clsInvoiceDtlBean objBeanInvoice = new clsInvoiceDtlBean();

			clsInvoiceModelDtl obj = listInvDtlModel.get(i);
			clsProductMasterModel objProdModle = objProductMasterService.funGetObject(obj.getStrProdCode(), clientCode);

			objBeanInvoice.setStrProdCode(obj.getStrProdCode());
			objBeanInvoice.setStrProdName(objProdModle.getStrProdName());
			objBeanInvoice.setStrProdType(obj.getStrProdType());
			objBeanInvoice.setDblQty(obj.getDblQty());
			objBeanInvoice.setDblWeight(obj.getDblWeight());
			objBeanInvoice.setDblUnitPrice(obj.getDblUnitPrice());
			objBeanInvoice.setStrPktNo(obj.getStrPktNo());
			objBeanInvoice.setStrRemarks(obj.getStrRemarks());
			objBeanInvoice.setStrInvoiceable(obj.getStrInvoiceable());
			objBeanInvoice.setStrSerialNo(obj.getStrSerialNo());
			objBeanInvoice.setStrCustCode(obj.getStrCustCode());
			objBeanInvoice.setStrSOCode(obj.getStrSOCode());
			objBeanInvoice.setStrUOM(objProdModle.getStrUOM());
			listInvDtlBean.add(objBeanInvoice);

		}
		objBeanInv.setListclsInvoiceModelDtl(listInvDtlBean);

		// Object ob = listInvDtlModel.get(index)
		// clsInvoiceModelDtl invDtl = (clsInvoiceModelDtl) ob[0];
		// clsProductMasterModel prodmast =(clsProductMasterModel) ob[1];

		// invDtl.setStrProdName(prodmast.getStrProdName());
		// listDCDtl.add(invDtl);
		// }

		// objBeanInv.setListclsInvoiceModelDtl(listDCDtl);
		List<clsInvoiceTaxDtlBean> listInvBeanTaxDtl = new ArrayList<clsInvoiceTaxDtlBean>();
		for (clsInvoiceTaxDtlModel objInvTaxDtl : objInvHDModelList.getListInvTaxDtlModel()) {
			clsInvoiceTaxDtlBean objTaxBeanDtl = new clsInvoiceTaxDtlBean();

			objTaxBeanDtl.setStrTaxCode(objInvTaxDtl.getStrTaxCode());
			objTaxBeanDtl.setStrTaxDesc(objInvTaxDtl.getStrTaxDesc());
			objTaxBeanDtl.setStrTaxableAmt(objInvTaxDtl.getDblTaxableAmt());
			objTaxBeanDtl.setStrTaxAmt(objInvTaxDtl.getDblTaxAmt());

			listInvBeanTaxDtl.add(objTaxBeanDtl);
		}
		objBeanInv.setListInvoiceTaxDtl(listInvBeanTaxDtl);

		return objBeanInv;
	}

	private clsInvoiceBean funPrepardComericalHdBean(clsInvoiceHdModel objInvHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel) {

		clsInvoiceBean objBean = new clsInvoiceBean();

		String[] date = objInvHdModel.getDteInvDate().split(" ");
		// String [] dateTime=date[2].split(" ");

		// String date1 = date[1]+"/"+dateTime[0]+"/"+date[0];
		objBean.setDteInvDate(date[0]);
		objBean.setStrAgainst(objInvHdModel.getStrAgainst());
		objBean.setStrCustCode(objInvHdModel.getStrCustCode());
		objBean.setStrInvCode(objInvHdModel.getStrInvCode());
		objBean.setStrInvNo(objInvHdModel.getStrInvNo());
		objBean.setStrDktNo(objInvHdModel.getStrDktNo());
		objBean.setStrLocCode(objInvHdModel.getStrLocCode());
		objBean.setStrMInBy(objInvHdModel.getStrMInBy());
		objBean.setStrNarration(objInvHdModel.getStrNarration());
		objBean.setStrPackNo(objInvHdModel.getStrPackNo());
		objBean.setStrPONo(objInvHdModel.getStrPONo());

		objBean.setStrReaCode(objInvHdModel.getStrReaCode());
		objBean.setStrSAdd1(objInvHdModel.getStrSAdd1());
		objBean.setStrSAdd2(objInvHdModel.getStrSAdd2());
		objBean.setStrSCity(objInvHdModel.getStrSCity());
		objBean.setStrSCountry(objInvHdModel.getStrSCtry());
		objBean.setStrSCtry(objInvHdModel.getStrSCtry());
		objBean.setStrSerialNo(objInvHdModel.getStrSerialNo());
		objBean.setStrSOCode(objInvHdModel.getStrSOCode());
		objBean.setStrSPin(objInvHdModel.getStrSPin());
		objBean.setStrSState(objInvHdModel.getStrSState());
		objBean.setStrTimeInOut(objInvHdModel.getStrTimeInOut());
		objBean.setStrVehNo(objInvHdModel.getStrVehNo());
		objBean.setStrWarraValidity(objInvHdModel.getStrWarraValidity());
		objBean.setStrWarrPeriod(objInvHdModel.getStrWarrPeriod());
		objBean.setDblSubTotalAmt(objInvHdModel.getDblSubTotalAmt());
		objBean.setDblTaxAmt(objInvHdModel.getDblTaxAmt());
		objBean.setDblTotalAmt(objInvHdModel.getDblTotalAmt());
		objBean.setDblDiscount(objInvHdModel.getDblDiscount());

		return objBean;
	}

	@RequestMapping(value = "/rptComercialTaxInvSlip", method = RequestMethod.GET)
	private void funReportComercialTaxInvReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String strInvCode = objBean.getStrDocCode();
		if (null == strInvCode) {
			strInvCode = req.getParameter("rptInvCode").toString();
		}
		String type = objBean.getStrDocType();
		if (null == type) {
			type = "pdf";
		}

		funCallReportComercialTaxInvReport(strInvCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReportComercialTaxInvReport(String strInvCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String strSOCode = "";
			String dteInvDate = "";
			String strPName = "";
			String strCustAdd1 = "";

			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptComercialTaxInvoiceSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			clsNumberToWords obj = new clsNumberToWords();
			ArrayList fieldList = new ArrayList();

			String sqlHd = "select date(a.dteInvDate),CONCAT(b.strMAdd1,',',b.strMAdd2,',',b.strMCity,',',b.strMPin) as address,b.strPName " + " from tblinvoicehd a,tblpartymaster b  " + " where  a.strCustCode=b.strPCode and  a.strInvCode='" + strInvCode + "' " + " and  a.strClientCode='" + clientCode + "'  ";
			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				dteInvDate = arrObj[0].toString();
				strCustAdd1 = arrObj[1].toString();
				strPName = arrObj[2].toString();
			}

			String sqlDtl = "select b.strProdName strProdName,a.dblQty as Quantity,a.dblUnitPrice as Rates,(a.dblUnitPrice* a.dblQty) as Amount  " + " from tblinvoicedtl  a,tblproductmaster b " + " where a.strProdCode=b.strProdCode " + " and a.strInvCode='" + strInvCode + "' and a.strClientCode='" + clientCode + "'  ";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtl, "sql");

			double totalAmt = 0.00;
			for (int j = 0; j < listProdDtl.size(); j++) {
				clsComercialInvTaxBeanReport objReportBean = new clsComercialInvTaxBeanReport();
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				{
					totalAmt += Double.parseDouble(prodArr[3].toString());
					objReportBean.setStrProdName(prodArr[0].toString());
					objReportBean.setDblQty(Double.parseDouble(prodArr[1].toString()));
					objReportBean.setDblRate(Double.parseDouble(prodArr[2].toString()));
					objReportBean.setDblAmt(Double.parseDouble(prodArr[3].toString()));
				}
				fieldList.add(objReportBean);
			}

			String sqltaxDtl = "  select a.strTaxDesc,a.dblTaxAmt " + "from tblinvtaxdtl  a where a.strInvCode='" + strInvCode + "' and a.strClientCode='" + clientCode + "'; ";

			List listTaxDtl = objGlobalFunctionsService.funGetDataList(sqltaxDtl, "sql");

			ArrayList<clsComercialInvTaxBeanReport> invTaxList = new ArrayList<clsComercialInvTaxBeanReport>();
			double totalTaxAmt = 0.00;
			for (int j = 0; j < listTaxDtl.size(); j++) {
				{
					clsComercialInvTaxBeanReport objTaxBean = new clsComercialInvTaxBeanReport();

					Object[] prodTaxArr = (Object[]) listTaxDtl.get(j);
					{
						totalTaxAmt += Double.parseDouble(prodTaxArr[1].toString());
						objTaxBean.setDblTaxAmt(Double.parseDouble(prodTaxArr[1].toString()));
						objTaxBean.setStrtaxDes(prodTaxArr[0].toString());
					}
					invTaxList.add(objTaxBean);
				}
			}
			String totalInvInWords = obj.funConvertAmtInWords(Math.rint(totalTaxAmt + totalAmt));
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
			hm.put("strPName", strPName);
			hm.put("strCFullAdd", strCustAdd1);
			hm.put("strInvCode", strInvCode);
			hm.put("dteInvDate", dteInvDate);
			hm.put("invTaxList", invTaxList);
			hm.put("subtotal", totalAmt);
			hm.put("grandTotal", Math.rint(totalTaxAmt + totalAmt));
			hm.put("vatNo", "VAT NO: " + objSetup.getStrVAT());
			hm.put("gtotalNoWord", totalInvInWords);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JasperPrint jp = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptComercialInvSlip_" + dteInvDate + "_" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptComercialInvSlip_" + dteInvDate + "_" + userCode + "." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String funGenrateJVforComercialTax(clsInvoiceHdModel objModel, String clientCode, String userCode, String propCode, String strType, HttpServletRequest req) {
		JSONObject jObjJVData = new JSONObject();
		String jvCode = "";
		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		try {
			String custCode = objModel.getStrCustCode();
			double debitAmt = objModel.getDblGrandTotal();
			clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custCode, clientCode, propCode, "Customer", "Sale");

			if (objModel.getStrSOCode().equals("")) {
				jObjJVData.put("strVouchNo", "");
				jObjJVData.put("strNarration", "JV Genrated by " + strType + " code:" + objModel.getStrInvCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDteInvDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AR");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", objModel.getStrUserCreated());
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objModel.getDteCreatedDate());
				jObjJVData.put("dteDateEdited", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			} else {
				jObjJVData.put("strVouchNo", objModel.getStrSOCode());
				jObjJVData.put("strNarration", "JV Genrated by " + strType + " code:" + objModel.getStrInvCode());
				jObjJVData.put("strSancCode", "");
				jObjJVData.put("strType", "");
				jObjJVData.put("dteVouchDate", objModel.getDteInvDate());
				jObjJVData.put("intVouchMonth", 1);
				jObjJVData.put("dblAmt", debitAmt);
				jObjJVData.put("strTransType", "R");
				jObjJVData.put("strTransMode", "A");
				jObjJVData.put("strModuleType", "AR");
				jObjJVData.put("strMasterPOS", "CRM");
				jObjJVData.put("strUserCreated", userCode);
				jObjJVData.put("strUserEdited", userCode);
				jObjJVData.put("dteDateCreated", objModel.getDteCreatedDate());
				jObjJVData.put("dteDateEdited", objGlobalfunction.funGetCurrentDateTime("yyyy-MM-dd"));
				jObjJVData.put("strClientCode", clientCode);
				jObjJVData.put("strPropertyCode", propCode);

			}

			for (clsInvoiceModelDtl objDtl : objModel.getListInvDtlModel()) {
				JSONObject jObjDtl = new JSONObject();

				clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Sale");

				jObjDtl.put("strVouchNo", "");
				jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
				jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
				jObjDtl.put("strCrDr", "Cr");
				jObjDtl.put("dblDrAmt", objDtl.getDblPrice());
				jObjDtl.put("dblCrAmt", 0.00);
				jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
				jObjDtl.put("strOneLine", "R");
				jObjDtl.put("strClientCode", clientCode);
				jObjDtl.put("strPropertyCode", propCode);
				jArrJVdtl.add(jObjDtl);

			}

			for (clsInvoiceTaxDtlModel objTaxDtl : objModel.getListInvTaxDtlModel()) {
				JSONObject jObjTaxDtl = new JSONObject();
				// List
				// list=objTaxMasterService.funGetTaxes(objTaxDtl.getStrTaxCode(),
				// clientCode);
				// Object[] objArr = (Object[]) list.get(0);
				clsLinkUpHdModel objLinkTax = objLinkupService.funGetARLinkUp(objTaxDtl.getStrTaxCode(), clientCode, propCode, "Tax", "Sale");
				jObjTaxDtl.put("strVouchNo", "");
				jObjTaxDtl.put("strAccountCode", objLinkTax.getStrAccountCode());
				jObjTaxDtl.put("strAccountName", objLinkTax.getStrMasterDesc());
				jObjTaxDtl.put("strCrDr", "Cr");
				jObjTaxDtl.put("dblDrAmt", 0.00);
				jObjTaxDtl.put("dblCrAmt", objTaxDtl.getDblTaxAmt());
				jObjTaxDtl.put("strNarration", "WS Tax Desc :" + objTaxDtl.getStrTaxDesc());
				jObjTaxDtl.put("strOneLine", "R");
				jObjTaxDtl.put("strClientCode", clientCode);
				jObjTaxDtl.put("strPropertyCode", propCode);
				jArrJVdtl.add(jObjTaxDtl);
			}
			JSONObject jObjCustDtl = new JSONObject();
			jObjCustDtl.put("strVouchNo", "");
			jObjCustDtl.put("strAccountCode", objLinkCust.getStrAccountCode());
			jObjCustDtl.put("strAccountName", objLinkCust.getStrMasterDesc());
			jObjCustDtl.put("strCrDr", "Dr");
			jObjCustDtl.put("dblDrAmt", objModel.getDblGrandTotal());
			jObjCustDtl.put("dblCrAmt", 0.00);
			jObjCustDtl.put("strNarration", "CRM Customer");
			jObjCustDtl.put("strOneLine", "R");
			jObjCustDtl.put("strClientCode", clientCode);
			jObjCustDtl.put("strPropertyCode", propCode);
			jArrJVdtl.add(jObjCustDtl);

			jObjJVData.put("ArrJVDtl", jArrJVdtl);

			// jvDebtor detail entry start
			String sql = "  select a.strInvCode,a.dblGrandTotal,b.strDebtorCode , " + " c.strDebtorFullName,date(a.dteInvDate),a.strNarration,date(a.dteInvDate),a.strPackNo " + " from dbwebmms.tblinvoicehd a,dbwebmms.tblpartymaster b ,dbwebbooks.tblsundarydebtormaster c " + " where a.strCustCode =b.strPCode and  b.strDebtorCode = c.strDebtorCode  and a.strInvCode='" + objModel.getStrInvCode()
					+ "' " + "  and a.strClientCode='" + objModel.getStrClientCode() + "' ;   ";
			List listTax = objGlobalFunctionsService.funGetList(sql, "sql");
			for (int i = 0; i < listTax.size(); i++) {
				JSONObject jObjDtl = new JSONObject();
				Object[] ob = (Object[]) listTax.get(i);
				jObjDtl.put("strVouchNo", "");
				jObjDtl.put("strDebtorCode", ob[2].toString());
				jObjDtl.put("strDebtorName", ob[3].toString());
				jObjDtl.put("strCrDr", "Cr");
				jObjDtl.put("dblAmt", ob[1].toString());
				jObjDtl.put("strBillNo", ob[7].toString());
				jObjDtl.put("strInvoiceNo", ob[0].toString());
				jObjDtl.put("strGuest", "");
				jObjDtl.put("strAccountCode", "");
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

			jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funSaveJVFromComericalTaxInvoice", jObjJVData);
			jvCode = jObj.get("strJVCode").toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return jvCode;
	}

}
