package com.sanguine.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.Connection;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsGRNBean;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditGRNTaxDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsDeliveryScheduleModuledtl;
import com.sanguine.model.clsDeliveryScheduleModulehd;
import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsGRNTaxDtlModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMISHdModel_ID;
import com.sanguine.model.clsPOTaxDtlModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsPurchaseOrderHdModel;
import com.sanguine.model.clsPurchaseReturnDtlModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsTransectionProdCharModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsDeliveryScheduleService;
import com.sanguine.service.clsGRNService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsMISService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsPropertyMasterService;
import com.sanguine.service.clsPurchaseOrderService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsGRNFixedAmtTax;
import com.sanguine.util.clsReportBean;

@Controller
public class clsGRNController {
	final static Logger logger = LoggerFactory.getLogger(clsGRNController.class);
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGRNService objGRNService;
	@Autowired
	private clsProductMasterService objProdSuppService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsProductMasterService objProductMasterService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsPropertyMasterService objPropertyMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsUOMService objclsUOMService;

	@Autowired
	private clsPurchaseOrderService objPurchaseOrderService;

	@Autowired
	private clsMISService objMISService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsLinkUpService objLinkupService;

	@Autowired
	private clsDeliveryScheduleService objDeliveryScheduleService;

	@Autowired
	private clsJVGeneratorController objJVGen;
	
	@Autowired
	private intfBaseService objBaseService;
	
	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@SuppressWarnings("rawtypes")
	private List listPOInGRN = null;

	/**
	 * Open GRN form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/frmGRN", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		request.getSession().setAttribute("formName", "frmWebStockHelpGRN");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String authorizationGRNCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationGRNCode = request.getParameter("authorizationGRNCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationGRNCode", authorizationGRNCode);
		}
		model.put("urlHits", urlHits);
		List<clsGRNFixedAmtTax> listFixedAmtTax = new ArrayList<clsGRNFixedAmtTax>();

		clsGRNBean objBean = new clsGRNBean();
		objBean.setStrLocCode(request.getSession().getAttribute("locationCode").toString());

		/*
		 * Set Process
		 */
		List<String> list = objGlobalFunctions.funGetSetUpProcess("frmGRN", propCode, clientCode);
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
		
		/*
		 * Set UOM List
		 */
		List<String> uomList = new ArrayList<String>();
		uomList = objclsUOMService.funGetUOMList(clientCode);
		model.put("uomList", uomList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRN_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRN", "command", objBean);
		} else {
			return null;
		}

	}

	/**
	 * Open Pending PO form
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmGRNPODetails", method = RequestMethod.GET)
	public ModelAndView funOpenPOforMR() {
		return new ModelAndView("frmGRNPODetails");

	}

	/**
	 * Load Pending PO data in Pending PO form
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPOforGRN", method = RequestMethod.GET)
	public @ResponseBody List funLoadPOforGRN(HttpServletRequest request) {
		String strSuppCode = request.getParameter("strSuppCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List POHelpList = objGRNService.funLoadPOforGRN(strSuppCode, propCode, clientCode);
		return POHelpList;

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/frmGRN1", method = RequestMethod.POST)
	public ModelAndView funOpenFormWithGRNCode(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		clsGRNBean bean = new clsGRNBean();
		String grnCode = request.getParameter("GRNCode").toString();
		List listGRNHd = objGRNService.funGetObject(grnCode, clientCode);
		bean = funPrepareBean(listGRNHd);
		if (null == listGRNHd) {
		} else {
			request.getSession().setAttribute("transname", "frmGRN.jsp");
			request.getSession().setAttribute("formname", "GRN");
			request.getSession().setAttribute("code", bean.getStrGRNCode());
		}
		List listTempDtl = objGRNService.funGetDtlList(grnCode, clientCode);
		List<clsGRNDtlModel> listGRNDtl = new ArrayList<clsGRNDtlModel>();

		for (int i = 0; i < listTempDtl.size(); i++) {
			Object[] ob = (Object[]) listTempDtl.get(i);
			clsGRNDtlModel grnDtl = (clsGRNDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			clsGRNDtlModel objGRNDtl = new clsGRNDtlModel();
			double totalWt = prodMaster.getDblWeight() * objGRNDtl.getDblQty();
			objGRNDtl.setStrGRNCode(grnDtl.getStrGRNCode());
			objGRNDtl.setStrProdCode(grnDtl.getStrProdCode());
			objGRNDtl.setStrClientCode(grnDtl.getStrClientCode());
			objGRNDtl.setStrProdChar(grnDtl.getStrProdChar());
			objGRNDtl.setStrProdName(prodMaster.getStrProdName());
			objGRNDtl.setStrTaxType(grnDtl.getStrTaxType());
			objGRNDtl.setDblUnitPrice(prodMaster.getDblCostRM());
			objGRNDtl.setDblWeight(prodMaster.getDblWeight());
			objGRNDtl.setDblQty(grnDtl.getDblQty());
			objGRNDtl.setDblRejected(grnDtl.getDblRejected());
			objGRNDtl.setDblDiscount(grnDtl.getDblDiscount());
			objGRNDtl.setDblTax(grnDtl.getDblTax());
			objGRNDtl.setDblTaxableAmt(grnDtl.getDblTaxableAmt());
			objGRNDtl.setDblTaxAmt(grnDtl.getDblTaxAmt());
			objGRNDtl.setDblWeight(grnDtl.getDblWeight());
			objGRNDtl.setDblDCQty(grnDtl.getDblDCQty());
			objGRNDtl.setDblDCWt(grnDtl.getDblDCWt());
			objGRNDtl.setStrRemarks(grnDtl.getStrRemarks());
			objGRNDtl.setDblQtyRbl(grnDtl.getDblQtyRbl());
			objGRNDtl.setStrGRNProdChar(grnDtl.getStrGRNProdChar());
			objGRNDtl.setDblPOWeight(grnDtl.getDblPOWeight());
			objGRNDtl.setStrCode(grnDtl.getStrCode());
			objGRNDtl.setDblPackForw(grnDtl.getDblPackForw());
			double totalPrice = objGRNDtl.getDblUnitPrice() * objGRNDtl.getDblQty();
			objGRNDtl.setDblTotalWt(totalWt);
			objGRNDtl.setDblTotalPrice(totalPrice);
			objGRNDtl.setStrUOM(prodMaster.getStrUOM());
			listGRNDtl.add(objGRNDtl);
		}
		bean.setListGRNDtl(listGRNDtl);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRN_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRN", "command", bean);
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGRNHd", method = RequestMethod.GET)
	public @ResponseBody clsGRNHdModel funLoadGRNHd(Map<String, Object> model, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		clsGRNBean bean = new clsGRNBean();
		String grnCode = request.getParameter("GRNCode").toString();
		List listGRNHd = objGRNService.funGetObject(grnCode, clientCode);
		if (listGRNHd.size() == 0) {
			clsGRNHdModel tempGRNHd = new clsGRNHdModel();
			tempGRNHd.setStrGRNCode("Invalid Code");
			return tempGRNHd;
		} else {
			Object[] ob = (Object[]) listGRNHd.get(0);
			clsGRNHdModel tempGRNHd = (clsGRNHdModel) ob[0];
			clsSupplierMasterModel supplierMaster = (clsSupplierMasterModel) ob[1];
			clsLocationMasterModel locMaster = (clsLocationMasterModel) ob[2];

			tempGRNHd.setDtGRNDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtGRNDate()));
			tempGRNHd.setDtDueDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtDueDate()));
			tempGRNHd.setDtRefDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtRefDate()));
			tempGRNHd.setDtBillDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtBillDate()));
			tempGRNHd.setStrSuppCode(supplierMaster.getStrPCode());
			tempGRNHd.setStrSuppName(supplierMaster.getStrPName());
			tempGRNHd.setStrLocName(locMaster.getStrLocName());
			request.getSession().setAttribute("transname", "frmGRN.jsp");
			request.getSession().setAttribute("formname", "GRN");
			request.getSession().setAttribute("code", bean.getStrGRNCode());
			return tempGRNHd;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGRNTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List<clsGRNTaxDtlModel> funLoadGRNDtl(Map<String, Object> model, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String grnCode = request.getParameter("GRNCode").toString();

		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from clsGRNTaxDtlModel " + "where strGRNCode='" + grnCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		List<clsGRNTaxDtlModel> listGRNTaxDtl = new ArrayList<clsGRNTaxDtlModel>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			clsGRNTaxDtlModel objTaxDtl = new clsGRNTaxDtlModel();
			Object[] arrObj = (Object[]) list.get(cnt);
			objTaxDtl.setStrTaxCode(arrObj[0].toString());
			objTaxDtl.setStrTaxDesc(arrObj[1].toString());
			objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
			objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
			listGRNTaxDtl.add(objTaxDtl);
		}

		return listGRNTaxDtl;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadPOTaxDtlonGRN", method = RequestMethod.GET)
	public @ResponseBody List<clsPOTaxDtlModel> funLoadPOTaxDtlonGRN(Map<String, Object> model, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();
		String poCode = request.getParameter("POCode").toString();

		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from clsPOTaxDtlModel " + "where strPOCode='" + poCode + "' and strClientCode='" + clientCode + "'";
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

		return listPOTaxDtl;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGRNDtl", method = RequestMethod.GET)
	public @ResponseBody List funLoadGRNTaxDtl(HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String grnCode = request.getParameter("GRNCode").toString();

		List listTempDtl = objGRNService.funGetDtlList(grnCode, clientCode);
		List<clsGRNDtlModel> listGRNDtl = new ArrayList<clsGRNDtlModel>();

		for (int i = 0; i < listTempDtl.size(); i++) {
			Object[] ob = (Object[]) listTempDtl.get(i);
			clsGRNDtlModel grnDtl = (clsGRNDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			clsGRNDtlModel objGRNDtl = new clsGRNDtlModel();
			double totalWt = prodMaster.getDblWeight() * objGRNDtl.getDblQty();
			objGRNDtl.setStrGRNCode(grnDtl.getStrGRNCode());
			objGRNDtl.setStrProdCode(grnDtl.getStrProdCode());
			objGRNDtl.setStrClientCode(grnDtl.getStrClientCode());
			objGRNDtl.setStrProdChar(grnDtl.getStrProdChar());
			objGRNDtl.setStrProdName(prodMaster.getStrProdName());
			objGRNDtl.setStrTaxType(grnDtl.getStrTaxType());
			objGRNDtl.setDblUnitPrice(grnDtl.getDblUnitPrice());
			objGRNDtl.setDblWeight(prodMaster.getDblWeight());
			objGRNDtl.setDblQty(grnDtl.getDblQty());
			objGRNDtl.setDblRejected(grnDtl.getDblRejected());
			objGRNDtl.setDblDiscount(grnDtl.getDblDiscount());
			objGRNDtl.setDblTax(grnDtl.getDblTax());
			objGRNDtl.setDblTaxableAmt(grnDtl.getDblTaxableAmt());
			objGRNDtl.setDblTaxAmt(grnDtl.getDblTaxAmt());
			objGRNDtl.setDblWeight(grnDtl.getDblWeight());
			objGRNDtl.setDblDCQty(grnDtl.getDblDCQty());
			objGRNDtl.setDblDCWt(grnDtl.getDblDCWt());
			objGRNDtl.setStrRemarks(grnDtl.getStrRemarks());
			objGRNDtl.setDblQtyRbl(grnDtl.getDblQtyRbl());
			objGRNDtl.setStrGRNProdChar(grnDtl.getStrGRNProdChar());
			objGRNDtl.setDblPOWeight(grnDtl.getDblPOWeight());
			objGRNDtl.setStrCode(grnDtl.getStrCode());
			objGRNDtl.setDblPackForw(grnDtl.getDblPackForw());
			objGRNDtl.setDblTotalWt(totalWt);
			objGRNDtl.setDblTotalPrice(grnDtl.getDblTotalPrice());
			objGRNDtl.setStrUOM(prodMaster.getStrUOM());
			objGRNDtl.setStrExpiry(prodMaster.getStrExpDate());
			objGRNDtl.setStrMISCode(grnDtl.getStrMISCode());
			objGRNDtl.setStrIssueLocation(grnDtl.getStrIssueLocation());
			clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(grnDtl.getStrIssueLocation(), clientCode);
			if (objLocModel != null) {
				objGRNDtl.setStrIsueLocName(objLocModel.getStrLocName());
			} else {
				objGRNDtl.setStrIsueLocName("");
			}
			objGRNDtl.setStrStkble(prodMaster.getStrNonStockableItem());
			String sql = "select IfNULL(b.strReqCode,'') from tblpurchaseorderdtl a " + " inner join tblmrpidtl b on a.strPIcode =b.strPIcode and b.strClientCode='" + clientCode + "'" + " where a.strPOCode='" + grnDtl.getStrCode() + "'  and a.strClientCode='" + clientCode + "' group by b.strReqCode";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			if (!list.isEmpty() && list != null) {
				objGRNDtl.setStrReqCode(list.get(0).toString());
			} else {
				objGRNDtl.setStrReqCode("");
			}
			listGRNDtl.add(objGRNDtl);

		}

		return listGRNDtl;
	}

	/**
	 * GRN Save Function
	 * 
	 * @param objBean
	 * @param result
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/saveGRN", method = RequestMethod.POST)
	public String funAddUpdate(@ModelAttribute("command") @Valid clsGRNBean objBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";
		String strmsg = "Update";
		DecimalFormat df = new DecimalFormat("#.##");
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (objBean.getStrGRNCode().trim().length() == 0) {
			strmsg = "Inserted";
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String startDate = request.getSession().getAttribute("startDate").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		Double stock, weightedAvg, weightedStk, weigthedvalue = 0.00;
		clsGRNHdModel objHdModel = funPrepareModel(objBean, request);
		double currValue =1.0;
	
		/*clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objBean.getStrCurrency(), clientCode);
		if (objCurrModel != null) {
			currValue = objCurrModel.getDblConvToBaseCurr();

		}*/
	
		String grnCode = "";
		if (!result.hasErrors()) {

			clsPropertySetupModel objSetUp = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			List<clsGRNDtlModel> listGRNDtl = objBean.getListGRNDtl();
			if (null != listGRNDtl && listGRNDtl.size() > 0) {
				grnCode = objHdModel.getStrGRNCode();
				currValue=objHdModel.getDblConversion();
				objGRNService.funDeleteDtl(grnCode, clientCode);
				double totalValue = 0.00;
				boolean flagDtlDataInserted = false;
				List<clsGRNDtlModel> batchList = new ArrayList<clsGRNDtlModel>();
				for (clsGRNDtlModel ob : listGRNDtl) {
					if (null != ob.getStrProdCode()) {
						String binNo = objGlobalFunctions.funIfNull(objBean.getStrBillNo(), "", ob.getStrBinNo());
						List listProdSupp = objGRNService.funGetProdSupp(objHdModel.getStrSuppCode(), ob.getStrProdCode(), clientCode);
						funAddProdSuppMaster(listProdSupp, clientCode, ob.getDblUnitPrice() * currValue, objHdModel.getStrSuppCode(), ob.getStrProdCode(), userCode, binNo, grnCode);
						ob.setStrGRNCode(grnCode);
						ob.setStrProdChar(" ");
						if (ob.getStrMISCode() == null) {
							ob.setStrMISCode("");
						}
						ob.setStrClientCode(clientCode);
						ob.setDblDiscount(currValue * ob.getDblDiscount());
						ob.setDblRate(ob.getDblRate() * currValue);
						ob.setDblTax(ob.getDblTax() * currValue);
						ob.setDblTaxableAmt(ob.getDblTaxableAmt() * currValue);
						ob.setDblTaxAmt(ob.getDblTaxAmt() * currValue);
						ob.setDblTotalPrice(ob.getDblTotalPrice() * currValue);
						ob.setDblUnitPrice(ob.getDblUnitPrice() * currValue);
						objGRNService.funAddUpdateDtl(ob);
						clsProductMasterModel objModel = objProductMasterService.funGetObject(ob.getStrProdCode(), clientCode);

						if (objSetUp.getStrMultiCurrency().equalsIgnoreCase("N")) {

							if (ob.getDblUnitPrice() != objModel.getDblCostRM()) {
								// Weighted average Calculating Logic and Update
								// In Product Master
								stock = objGlobalFunctions.funGetCurrentStockForProduct(ob.getStrProdCode(), objHdModel.getStrLocCode(), clientCode, userCode, startDate, objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
								String strstock = stock.toString();

								if (strstock.contains("-")) {
									stock = 0.0;
								}
								weigthedvalue = stock * objModel.getDblCostRM();
								double tempval = ob.getDblQty() * ob.getDblUnitPrice();
								weightedStk = stock + ob.getDblQty();
								if (weightedStk == 0.0) {
									weightedStk = 1.0;
								}
								double temp = weigthedvalue + tempval;
								weightedAvg = temp / weightedStk;
								weightedAvg = Double.parseDouble(df.format(temp / weightedStk).toString());
								// weightedAvg=Math.rint(weightedAvg);
								String strweightedAvg = weightedAvg.toString();

								if (strweightedAvg.contains("-")) {
									weightedAvg = weightedAvg * (-1);
								}
								objProductMasterService.funProductUpdateCostRM(weightedAvg, ob.getStrProdCode(), clientCode);
							}
						} else {
							double dblreOrderPrice = 0;
							clsProductReOrderLevelModel objReOrder = objProductMasterService.funGetProdReOrderLvl(ob.getStrProdCode(), objHdModel.getStrLocCode(), clientCode);
							if (objReOrder != null) {
								if (ob.getDblUnitPrice() != objReOrder.getDblPrice()) {
									dblreOrderPrice = objReOrder.getDblPrice();
									stock = objGlobalFunctions.funGetCurrentStockForProduct(ob.getStrProdCode(), objHdModel.getStrLocCode(), clientCode, userCode, startDate, objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
									String strstock = stock.toString();

									if (strstock.contains("-")) {
										stock = 0.0;
									}
									weigthedvalue = stock * dblreOrderPrice;
									double tempval = ob.getDblQty() * ob.getDblUnitPrice();
									weightedStk = stock + ob.getDblQty();
									if (weightedStk == 0.0) {
										weightedStk = 1.0;
									}
									double temp = weigthedvalue + tempval;
									weightedAvg = temp / weightedStk;
									weightedAvg = Double.parseDouble(df.format(temp / weightedStk).toString());
									objReOrder.setDblPrice(weightedAvg);
									objProductMasterService.funAddUpdateProdReOrderLvl(objReOrder);
									List<clsLocationMasterModel> list = objLocationMasterService.funLoadLocationPropertyWise(objSetUp.getStrPropertyCode(), clientCode);
									for (clsLocationMasterModel obj : list) {
										if (!(obj.getStrLocCode().equals(objHdModel.getStrLocCode()))) {
											System.out.println("prod " + ob.getStrProdCode() + "Loc" + obj.getStrLocCode() + " HDLOC" + objHdModel.getStrLocCode());
											clsProductReOrderLevelModel reeorderLevelForPropertyWiseLocation = objProductMasterService.funGetProdReOrderLvl(ob.getStrProdCode(), obj.getStrLocCode(), clientCode);
											reeorderLevelForPropertyWiseLocation.setDblPrice(weightedAvg);
											objProductMasterService.funAddUpdateProdReOrderLvl(reeorderLevelForPropertyWiseLocation);
										}
									}
								}
							}

						}
						if (ob.getStrExpiry().equalsIgnoreCase("y") && ob.getStrExpiry() != null) {
							clsGRNDtlModel BatchModel = new clsGRNDtlModel();
							BatchModel.setStrGRNCode(grnCode);
							BatchModel.setStrProdCode(ob.getStrProdCode());
							BatchModel.setStrProdName(ob.getStrProdName());
							BatchModel.setDblQty(ob.getDblQty());
							batchList.add(BatchModel);
						}

						flagDtlDataInserted = true;

					}
				}

				List<clsGRNTaxDtlModel> listGRNTaxDtlModel = objBean.getListGRNTaxDtl();
				if (null != listGRNTaxDtlModel) {
					objGRNService.funDeleteGRNTaxDtl(objBean.getStrGRNCode(), clientCode);
					for (clsGRNTaxDtlModel obTaxDtl : listGRNTaxDtlModel) {
						if (null != obTaxDtl.getStrTaxCode()) {
							obTaxDtl.setStrGRNCode(grnCode);
							obTaxDtl.setStrClientCode(clientCode);
							obTaxDtl.setStrTaxableAmt(obTaxDtl.getStrTaxableAmt() * currValue);
							obTaxDtl.setStrTaxAmt(obTaxDtl.getStrTaxAmt() * currValue);
							objGRNService.funAddUpdateGRNTaxDtl(obTaxDtl);
						}
					}
				}
				objHdModel.setStrJVNo("");
				objHdModel.setDblValueTotal(totalValue * currValue);
				
				
				
//				(clsGRNHdModel objModel, List<clsGRNDtlModel> listDtlModel, List<clsGRNTaxDtlModel> listTaxDtl, String clientCode, String userCode, String propCode, HttpServletRequest req) {
				objGRNService.funAddUpdate(objHdModel);
				if (flagDtlDataInserted) {
					String strGeneratedMISCode = funSaveNONStkMIS(objBean, objHdModel.getStrGRNCode(), request);
					request.getSession().setAttribute("success", true);
					request.getSession().setAttribute("successMessage", "GRN Code : ".concat(objHdModel.getStrGRNCode()));
					if (strGeneratedMISCode != "") {
						request.getSession().setAttribute("successMessageMIS", "MIS Code : ".concat(strGeneratedMISCode));
					}
					request.getSession().setAttribute("rptGRNCode", objHdModel.getStrGRNCode());
					if (!batchList.isEmpty()) {
						request.getSession().setAttribute("BatchProcessList", batchList);
					} else {
						request.getSession().setAttribute("BatchProcessList", null);
					}
					request.getSession().setAttribute("strmsg", strmsg);
				}
			}

			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			if (objCompModel.getStrWebBookModule().equals("Yes")) {
				
				boolean authorisationFlag=false;
				if (null != request.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) request.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.containsKey("GRN")) {
						authorisationFlag=hmAuthorization.get("GRN");
					}
				}
				
				if(!authorisationFlag)
				{
					String retuenVal=objJVGen.funGenrateJVforGRN(grnCode, clientCode, userCode, propCode, request);
					String JVGenMessage="";
					String[] arrVal=retuenVal.split("!");
					
					boolean flgJVPosting=true;
					if(arrVal[0].equals("ERROR"))
					{
						JVGenMessage=arrVal[1];
						flgJVPosting=false;
					}else{
					objHdModel.setStrJVNo(arrVal[0]);
					objGRNService.funAddUpdate(objHdModel);
					}
					request.getSession().setAttribute("JVGen", flgJVPosting);
					request.getSession().setAttribute("JVGenMessage", JVGenMessage);
				}
			}
		
			return ("redirect:/frmGRN.html?saddr=" + urlHits);
		} else {
			return ("redirect:/frmGRN.html?saddr=" + urlHits);
		}
	}

	/**
	 * NonStockable Issue Function Logic
	 * 
	 * @param objBean
	 * @param strGRNCode
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String funSaveNONStkMIS(clsGRNBean objBean, String strGRNCode, HttpServletRequest req) {
		
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsMISHdModel objMISHd = null;
		String strGeneratedMISCode = "";
		String strMISCode = "";
		String grnDate = objBean.getDtGRNDate();

		for (clsGRNDtlModel objGRNDtl : objBean.getListGRNDtl()) {
			if (null != objGRNDtl.getStrProdCode()) {
				if (objGRNDtl.getStrStkble().equalsIgnoreCase("Y") && !objGRNDtl.getStrIssueLocation().equals("") && null != objGRNDtl.getStrStkble()) {
					String reqCode = "";
					String against = "Direct";
					String sql = "select IfNULL(b.strReqCode,'') from tblpurchaseorderdtl a " + " inner join tblmrpidtl b on a.strPIcode =b.strPIcode and b.strClientCode='" + clientCode + "'" + " where a.strPOCode='" + objGRNDtl.getStrCode() + "' and a.strClientCode='" + clientCode + "' " + "group by b.strReqCode";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (!list.isEmpty() && list != null) {
						against = "Requisition";
						reqCode = list.get(0).toString();
					}
					if (objGRNDtl.getStrMISCode().isEmpty()) {
						strMISCode = objGlobalFunctions.funGenerateDocumentCode("frmMIS", grnDate, req);
						objMISHd = new clsMISHdModel(new clsMISHdModel_ID(strMISCode, clientCode));
						objMISHd.setIntid(0);
						objMISHd.setStrUserCreated(userCode);
						objMISHd.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					} else {
						strMISCode = objGRNDtl.getStrMISCode();
						objMISHd = new clsMISHdModel(new clsMISHdModel_ID(objGRNDtl.getStrMISCode().trim(), clientCode));
					}

					objMISHd.setStrMISCode(strMISCode);
					objMISHd.setStrLocFrom(objBean.getStrLocCode());
					objMISHd.setStrLocTo(objGRNDtl.getStrIssueLocation());
					objMISHd.setDtMISDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", grnDate));
					objMISHd.setStrUserModified(userCode);
					objMISHd.setStrNarration("Generated GRN Code:-" + strGRNCode);
					objMISHd.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					objMISHd.setStrAuthorise("Yes");
					objMISHd.setDblTotalAmt(objGRNDtl.getDblTotalPrice());
					objMISHd.setStrAgainst(against);
					objMISHd.setStrReqCode(reqCode);

					clsMISDtlModel objMISDtlModel = new clsMISDtlModel();
					objMISDtlModel.setStrProdCode(objGRNDtl.getStrProdCode());
					objMISDtlModel.setDblQty(objGRNDtl.getDblQty());
					objMISDtlModel.setDblUnitPrice(objGRNDtl.getDblUnitPrice());
					objMISDtlModel.setDblTotalPrice(objGRNDtl.getDblTotalPrice());
					objMISDtlModel.setStrRemarks(objGRNDtl.getStrRemarks());
					objMISDtlModel.setStrReqCode(reqCode);

					List<clsMISDtlModel> listMISDtlModel = new ArrayList<clsMISDtlModel>();
					listMISDtlModel.add(objMISDtlModel);
					objMISHd.setClsMISDtlModel(listMISDtlModel);

					objMISService.funAddMISHd(objMISHd);

					String updateQuery = "update tblgrndtl set strMISCode='" + strMISCode + "' " + "	where strgrnCode = '" + objGRNDtl.getStrGRNCode() + "' and strprodcode = '" + objMISDtlModel.getStrProdCode() + "' " + " and strIssueLocation='" + objGRNDtl.getStrIssueLocation() + "' and strClientCode='" + clientCode + "'";
					objMISService.funInsertNonStkItemDirect(updateQuery);

					if (strGeneratedMISCode.length() > 0) {
						strGeneratedMISCode = strGeneratedMISCode + "," + strMISCode;
					} else {
						strGeneratedMISCode = strMISCode;
					}
				}
			}
		}

		return strGeneratedMISCode;
	}



	@SuppressWarnings("finally")
	@RequestMapping(value = "/loadProductDataWithTax", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funAssignFields(@RequestParam("prodCode") String prodCode, @RequestParam("locCode") String locCode, @RequestParam("suppCode") String strSuppCode,@RequestParam("billDate") String billDate, HttpServletRequest request) {
		clsProductMasterModel objProdMasterModel = null;
		try {
			String clientCode = request.getSession().getAttribute("clientCode").toString();
			String propCode = request.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetUp = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			
			// For Accoding to Bar Code checking length
			objProdMasterModel=funGetSupplierWiseContractRate(prodCode,strSuppCode,billDate);
			if(objProdMasterModel.getStrProdCode()==null)
			{
				if (objSetUp.getStrMultiCurrency().equalsIgnoreCase("N")) {
					if (prodCode.length() > 8) {
						objProdMasterModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
					} else {
						objProdMasterModel = objProductMasterService.funGetObject(prodCode, clientCode);
					}
				} else {

					double dblreOrderPrice = 0;
					clsProductReOrderLevelModel objReOrder = objProductMasterService.funGetProdReOrderLvl(prodCode, locCode, clientCode);
					if (objReOrder != null) {
						dblreOrderPrice = objReOrder.getDblPrice();
					}

					if (prodCode.length() > 8) {
						objProdMasterModel = objProductMasterService.funGetBarCodeProductObject(prodCode, clientCode);
					} else {
						objProdMasterModel = objProductMasterService.funGetObject(prodCode, clientCode);
					}

					objProdMasterModel.setDblCostRM(dblreOrderPrice);
				}
				
				objProdMasterModel.setStrRemark("Not Contract Rate Amt");
			}
			
			// objProdMasterModel=objProductMasterService.funGetObject(prodCode,clientCode);
			if (null == objProdMasterModel) {
				objProdMasterModel = new clsProductMasterModel();
				objProdMasterModel.setStrProdCode("Invalid Code");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return objProdMasterModel;
		}
	}


	@RequestMapping(value = "/loadProductDataWithSuppWiseRate", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funGetSupplierWiseObject(@RequestParam("prodCode") String prodCode, @RequestParam("suppCode") String strSuppCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsProductMasterModel objProdMasterModel = objProductMasterService.funGetSupplierWiseObject(strSuppCode, prodCode, clientCode);
		return objProdMasterModel;
	}

	@RequestMapping(value = "/loadMultiPODiscount", method = RequestMethod.GET)
	private @ResponseBody clsPurchaseOrderHdModel funGetPOHdData(@RequestParam("POCodes") String POCodes, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPurchaseOrderHdModel POModel = new clsPurchaseOrderHdModel();
		double disCountAmt = 0;
		double extraCharge = 0;
		String strDNCodes[] = POCodes.split(",");
		for (int i = 0; i < strDNCodes.length; i++) {
			clsPurchaseOrderHdModel objPurchaseOrderHdModel = objPurchaseOrderService.funGetObject(strDNCodes[i], clientCode);
			extraCharge = extraCharge + objPurchaseOrderHdModel.getDblExtra();
			disCountAmt = disCountAmt + objPurchaseOrderHdModel.getDblDiscount();
		}
		POModel.setDblDiscount(disCountAmt);
		POModel.setDblExtra(extraCharge);
		return POModel;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadProductDataForPOTrans", method = RequestMethod.GET)
	public @ResponseBody List funLoadProductForPO(@RequestParam("prodCode") String prodCode, @RequestParam("POCode") String POCode,@RequestParam("suppCode") String strSuppCode,@RequestParam("billDate") String billDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "";
		List ProdList = new ArrayList();
		ProdList =funGetSupplierWiseContractRateForPO(prodCode,strSuppCode,POCode,billDate);
		if(ProdList==null)
		{
			sql = "select a.strProdCode,a.strProdName,e.dblPrice,a.dblWeight,a.strBinNo,a.strReceivedUOM," + " a.strExpDate,a.strNonStockableItem," + " e.dblOrdQty-ifnull(sum(f.dblQty),0) as dblOrdQty,e.strPOCode" + "	from tblproductmaster a,tblpurchaseorderdtl e" + " left outer join tblgrndtl f  on f.strCode=e.strPOCode and f.strCode='" + POCode + "' " + "	and f.strClientCode='" + clientCode + "' "
					+ " where  e.strProdCode=a.strProdCode and  e.strProdCode='" + prodCode + "' and e.strPOCode='" + POCode + "'  " + " and a.strClientCode='" + clientCode + "' and e.strClientCode='" + clientCode + "'" + "	group by a.strProdCode";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");

			
			if (list.size() > 0) {
				Object[] ob = (Object[]) list.get(0);

				ProdList.add(ob[0].toString());
				ProdList.add(ob[1].toString());
				ProdList.add(Double.parseDouble(ob[2].toString()));
				ProdList.add(ob[3].toString());
				ProdList.add(ob[4].toString());
				ProdList.add(ob[5].toString());
				ProdList.add(ob[6].toString());
				ProdList.add(ob[7].toString());
				ProdList.add(Double.parseDouble(ob[8].toString()));
				ProdList.add(ob[9].toString());
				ProdList.add("Not Contract Rate Amt");
			} else {
				ProdList = new ArrayList();
				ProdList.add("Invalid Code ");
			}
		}
		
		return ProdList;
	}

	@RequestMapping(value = "/loadAgainstPO", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields1(@RequestParam("POCode") String POCode, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		listPOInGRN = objGRNService.funGetDtlListAgainst(POCode, clientCode, "clsPurchaseOrderDtl");
        double dblFOB=0,dblFreight=0,dblInsurance=0,dblOtherCharges=0,dblCIF=0,dblClearingAgentCharges=0,dblVATClaim=0;
        
        List<clsGRNDtlModel> listGRNDtlModel = new ArrayList<clsGRNDtlModel>();
		for (int i = 0; i < listPOInGRN.size(); i++) {
			clsGRNDtlModel objGRNDtlModel = new clsGRNDtlModel();
			Object[] ob = (Object[]) listPOInGRN.get(i);
			objGRNDtlModel.setStrCode(ob[1].toString());
			objGRNDtlModel.setStrProdCode(ob[0].toString());
			double pendingQty = Double.parseDouble(ob[4].toString());
			objGRNDtlModel.setDblQty(pendingQty);
			double totalPrice = pendingQty * (Double.parseDouble(ob[5].toString()));
			objGRNDtlModel.setDblTotalPrice(totalPrice - Double.parseDouble(ob[10].toString()));
			double totalweight = pendingQty * (Double.parseDouble(ob[6].toString()));
			objGRNDtlModel.setDblTotalWt(totalweight);
			objGRNDtlModel.setStrUOM(ob[7].toString());
			objGRNDtlModel.setStrProdName(ob[8].toString());
			objGRNDtlModel.setDblRate(Double.parseDouble(ob[5].toString()));
			objGRNDtlModel.setDblWeight(Double.parseDouble(ob[6].toString()));
			objGRNDtlModel.setStrExpiry(ob[9].toString());
			objGRNDtlModel.setDblDiscount(Double.parseDouble(ob[10].toString()));
			objGRNDtlModel.setStrIssueLocation(ob[11].toString());
			objGRNDtlModel.setStrIsueLocName(ob[12].toString());
			objGRNDtlModel.setStrStkble(ob[13].toString());
			objGRNDtlModel.setStrReqCode(ob[14].toString());
			objGRNDtlModel.setDblConversionRate(Double.parseDouble(ob[15].toString()));
			objGRNDtlModel.setStrCurrency(ob[16].toString());
			if(i==0)
			{
				dblFOB=Double.valueOf(ob[23].toString());
				dblFreight=Double.valueOf(ob[17].toString());
				dblInsurance=Double.valueOf(ob[18].toString());
				dblOtherCharges=Double.valueOf(ob[19].toString());
				dblCIF=Double.valueOf(ob[20].toString());
				dblClearingAgentCharges=Double.valueOf(ob[21].toString());
				dblVATClaim=Double.valueOf(ob[22].toString());
				
			}
			
			listGRNDtlModel.add(objGRNDtlModel);
			
		}
		
		List poDetailsList = new ArrayList();
		poDetailsList.add(listGRNDtlModel);
		poDetailsList.add(dblFreight);
		poDetailsList.add(dblInsurance);
		poDetailsList.add(dblOtherCharges);
		poDetailsList.add(dblCIF);
		poDetailsList.add(dblClearingAgentCharges);
		poDetailsList.add(dblVATClaim);
		poDetailsList.add(dblFOB);
		
		return poDetailsList;
	}


	@RequestMapping(value = "/loadAgainstShortSupply", method = RequestMethod.GET)
	public @ResponseBody List<clsGRNDtlModel> funAssignFields4(@RequestParam("POCode") String POCode, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		listPOInGRN = objGRNService.funGetDtlListAgainst(POCode, clientCode, "clsPurchaseOrderDtl");
		List<clsGRNDtlModel> listGRNDtlModel = new ArrayList<clsGRNDtlModel>();
		for (int i = 0; i < listPOInGRN.size(); i++) {
			clsGRNDtlModel objGRNDtlModel = new clsGRNDtlModel();
			Object[] ob = (Object[]) listPOInGRN.get(i);
			objGRNDtlModel.setStrCode(ob[1].toString());
			objGRNDtlModel.setStrProdCode(ob[0].toString());
			double pendingQty = Double.parseDouble(ob[4].toString());
			objGRNDtlModel.setDblQty(pendingQty);
			double totalPrice = pendingQty * (Double.parseDouble(ob[5].toString()));
			objGRNDtlModel.setDblTotalPrice(totalPrice - Double.parseDouble(ob[10].toString()));
			double totalweight = pendingQty * (Double.parseDouble(ob[6].toString()));
			objGRNDtlModel.setDblTotalWt(totalweight);
			objGRNDtlModel.setStrUOM(ob[7].toString());
			objGRNDtlModel.setStrProdName(ob[8].toString());
			objGRNDtlModel.setDblRate(Double.parseDouble(ob[5].toString()));
			objGRNDtlModel.setDblWeight(Double.parseDouble(ob[6].toString()));
			objGRNDtlModel.setStrExpiry(ob[9].toString());
			objGRNDtlModel.setDblDiscount(Double.parseDouble(ob[10].toString()));
			objGRNDtlModel.setStrIssueLocation(ob[11].toString());
			objGRNDtlModel.setStrIsueLocName(ob[12].toString());
			objGRNDtlModel.setStrStkble(ob[13].toString());
			listGRNDtlModel.add(objGRNDtlModel);
		}
		return listGRNDtlModel;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadAgainstPR", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields2(@RequestParam("PRCode") String PRCode, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsPurchaseReturnDtlModel> listPRDtlModel = new ArrayList<clsPurchaseReturnDtlModel>();
		List tempPRList = objGlobalFunctionsService.funGetPurchaseReturnList(PRCode, clientCode);
		for (int i = 0; i < tempPRList.size(); i++) {
			clsPurchaseReturnDtlModel objPRDtlModel = new clsPurchaseReturnDtlModel();
			Object[] ob = (Object[]) tempPRList.get(i);
			clsPurchaseReturnDtlModel prDtl = (clsPurchaseReturnDtlModel) ob[0];
			clsProductMasterModel prodMaster = (clsProductMasterModel) ob[1];
			objPRDtlModel.setStrPRCode(prDtl.getStrPRCode());
			objPRDtlModel.setStrProdCode(prDtl.getStrProdCode());
			objPRDtlModel.setDblQty(prDtl.getDblQty());
			objPRDtlModel.setStrProdName(prodMaster.getStrProdName());
			objPRDtlModel.setDblWeight(prodMaster.getDblWeight());
			objPRDtlModel.setDblTotalPrice(prodMaster.getDblCostRM() * prDtl.getDblQty());
			objPRDtlModel.setDblTotalWt(prodMaster.getDblWeight() * prDtl.getDblQty());
			objPRDtlModel.setStrUOM(prodMaster.getStrUOM());
			objPRDtlModel.setDblUnitPrice(prodMaster.getDblCostRM());
			objPRDtlModel.setStrExpiry(prodMaster.getStrExpDate());
			listPRDtlModel.add(objPRDtlModel);
		}
		return listPRDtlModel;
	}

	@SuppressWarnings("rawtypes")
	private boolean funAddProdSuppMaster(List listProdSupp, String clientCode, double lastPrice, String suppCode, String prodCode, String userCode, String binNo, String strGRNCode) {
		boolean flgInsert = false;
		
		if (listProdSupp.size() > 0) {

			clsProdSuppMasterModel objProdSupp = (clsProdSuppMasterModel) listProdSupp.get(0);
			objGRNService.funDeleteProdSupp(objProdSupp.getStrSuppCode(), objProdSupp.getStrProdCode(), clientCode);
			objProdSupp.setDblLastCost(lastPrice);
			objProdSupp.setDtLastDate(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
			objProdSuppService.funAddUpdateProdSupplier(objProdSupp);
		} else {
			clsProdSuppMasterModel objProdSupp = new clsProdSuppMasterModel();
			objGRNService.funDeleteProdSupp(objProdSupp.getStrSuppCode(), objProdSupp.getStrProdCode(), clientCode);
			objProdSupp.setStrSuppCode(suppCode);
			objProdSupp.setStrProdCode(prodCode);
			objProdSupp.setStrClientCode(clientCode);
			objProdSupp.setDblMaxQty(0.00);
			objProdSupp.setStrDefault("Y");
			objProdSupp.setStrLeadTime("");
			objProdSupp.setStrSuppPartDesc("");
			objProdSupp.setStrSuppPartNo("");
			objProdSupp.setStrSuppUOM("");
			objProdSupp.setDblLastCost(lastPrice);
			objProdSupp.setDtLastDate(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
			String sql = "update tblprodsuppmaster set strDefault='N' where  strProdCode='" + prodCode + "' and strClientCode='" + clientCode + "'";
			objGlobalFunctionsService.funUpdate(sql, "sql");
			objProdSuppService.funAddUpdateProdSupplier(objProdSupp);

		}

		return flgInsert;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private clsGRNHdModel funPrepareModel(clsGRNBean objBean, HttpServletRequest request) {
		double currValue =1.0;
		if(objBean.getDblConversion()>0){
			currValue=objBean.getDblConversion();
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();

		long lastNo = 0;
		String strDocNo = "";
		clsGRNHdModel objHdModel = new clsGRNHdModel();
		if (objBean.getStrGRNCode().trim().length() == 0) {
		
			strDocNo = objGlobalFunctions.funGenerateDocumentCode("frmGRN", objBean.getDtGRNDate(), request);
			objHdModel.setStrGRNCode(strDocNo);
			objHdModel.setIntId(lastNo);
			objHdModel.setStrUserCreated(userCode);
			objHdModel.setDtDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHdModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("GRN", request));
			
		} else {
			List listGRNHd = objGRNService.funGetObject(objBean.getStrGRNCode(), clientCode);
			if (listGRNHd.size() == 0) {
				strDocNo = objGlobalFunctions.funGenerateDocumentCode("frmGRN", objBean.getDtGRNDate(), request);
				objHdModel.setStrGRNCode(strDocNo);
				objHdModel.setIntId(lastNo);
				objHdModel.setStrUserCreated(userCode);
				objHdModel.setDtDateCreated(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				objHdModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("GRN", request));
				
			} else {
				if (objGlobalFunctions.funCheckAuditFrom("frmGRN", request)) {
					funSaveAudit(objBean.getStrGRNCode(), "Edit", request);
				}
				objHdModel.setStrGRNCode(objBean.getStrGRNCode());
			}
		}

		objHdModel.setStrPONo(objBean.getStrPONo());
		objHdModel.setStrNo(objBean.getStrGRNNo());
		objHdModel.setDtGRNDate(objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", objBean.getDtGRNDate()));
		objHdModel.setStrSuppCode(objBean.getStrSuppCode());
		objHdModel.setStrLocCode(objBean.getStrLocCode());
		objHdModel.setStrBillNo(objBean.getStrBillNo());
		objHdModel.setDtRefDate(objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", objBean.getDtRefDate()));
		objHdModel.setDtBillDate(objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", objBean.getDtBillDate()));
		objHdModel.setDtDueDate(objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", objBean.getDtDueDate()));
		objHdModel.setStrRefNo(objBean.getStrRefNo());
		objHdModel.setStrAgainst(objBean.getStrAgainst());
		objHdModel.setStrPONo(objGlobalFunctions.funIfNull(objBean.getStrPONo(), " ", objBean.getStrPONo()));
		objHdModel.setStrPayMode(objBean.getStrPayMode());
		objHdModel.setStrTimeInOut(objBean.getStrTimeInOut());
		objHdModel.setDblSubTotal(objBean.getDblSubTotal() * currValue);
		String conversion = objGlobalFunctions.funIfNull(String.valueOf(objBean.getDblConversion()), "1.0000", String.valueOf(objBean.getDblConversion()));
		objHdModel.setDblConversion(Double.parseDouble(conversion));
		objHdModel.setDblDisRate(objBean.getDblDisRate());
		objHdModel.setDblDisAmt(objBean.getDblDisAmt() * currValue);
		objHdModel.setDblTaxAmt(objBean.getDblTaxAmt() * currValue);
		objHdModel.setDblExtra(objBean.getDblExtra() * currValue);
		double totalAmt = objBean.getDblTotal();
		objHdModel.setDblTotal(totalAmt * currValue);
		objHdModel.setDblRoundOff(objBean.getDblRoundOff() * currValue);
		objHdModel.setDblLessAmt(objBean.getDblLessAmt() * currValue);
		objHdModel.setStrVehNo(objGlobalFunctions.funIfNull(objBean.getStrVehNo(), " ", objBean.getStrVehNo()));
		objHdModel.setStrMInBy(objGlobalFunctions.funIfNull(objBean.getStrMInBy(), " ", objBean.getStrMInBy()));
		objHdModel.setStrNarration(objGlobalFunctions.funIfNull(objBean.getStrNarration(), " ", objBean.getStrNarration()));
		objHdModel.setStrCurrency(objGlobalFunctions.funIfNull(objBean.getStrCurrency(), " ", objBean.getStrCurrency()));
		objHdModel.setStrShipmentMode(objGlobalFunctions.funIfNull(objBean.getStrShipmentMode(), " ", objBean.getStrShipmentMode()));
		objHdModel.setStrUserModified(userCode);
		objHdModel.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
		objHdModel.setStrClientCode(clientCode);
		objHdModel.setDblConversion(currValue);
		objHdModel.setStrCurrency(objBean.getStrCurrency());
		if(null!=objBean.getStrPONo())
		{
			try
			{
				StringBuilder sbSql=new StringBuilder();
				sbSql.append("select dblFOB,dblFreight,dblInsurance,dblOtherCharges,dblClearingAgentCharges,dblVATClaim from tblpurchaseorderhd "
					+ "where strPOCode='"+objBean.getStrPONo()+"' and strClientCode='"+clientCode+"' ");
				List listPO=objBaseService.funGetList(sbSql, "sql");
				if(listPO.size()>0)
				{
					for(int cn=0;cn<listPO.size();cn++)
					{
						Object[] arrObj=(Object[])listPO.get(cn);
						objHdModel.setDblFOB(Double.parseDouble(arrObj[0].toString()));
						objHdModel.setDblFreight(Double.parseDouble(arrObj[1].toString()));
						objHdModel.setDblInsurance(Double.parseDouble(arrObj[2].toString()));
						objHdModel.setDblOtherCharges(Double.parseDouble(arrObj[3].toString()));
						objHdModel.setDblClearingCharges(Double.parseDouble(arrObj[4].toString()));
						objHdModel.setDblVATClaim(Double.parseDouble(arrObj[5].toString()));
					}
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return objHdModel;
	}

	@SuppressWarnings("rawtypes")
	private clsGRNBean funPrepareBean(List listGRNHd) {
		
		clsGRNBean objBean = new clsGRNBean();
		Object[] ob = (Object[]) listGRNHd.get(0);
		clsGRNHdModel tempGRNHd = (clsGRNHdModel) ob[0];
		clsSupplierMasterModel supplierMaster = (clsSupplierMasterModel) ob[1];
		clsLocationMasterModel locMaster = (clsLocationMasterModel) ob[2];

		objBean.setStrGRNCode(tempGRNHd.getStrGRNCode());
		objBean.setStrGRNNo(tempGRNHd.getStrNo());
		objBean.setDtGRNDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtGRNDate()));
		objBean.setDtDueDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtDueDate()));
		objBean.setDtRefDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtRefDate()));
		objBean.setDtBillDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", tempGRNHd.getDtBillDate()));
		objBean.setStrSuppCode(supplierMaster.getStrPCode());
		objBean.setStrSuppName(supplierMaster.getStrPName());
		objBean.setStrAgainst(tempGRNHd.getStrAgainst());
		objBean.setStrPayMode(tempGRNHd.getStrPayMode());
		objBean.setStrRefNo(tempGRNHd.getStrRefNo());
		objBean.setStrLocCode(tempGRNHd.getStrLocCode());
		objBean.setStrLocName(locMaster.getStrLocName());
		objBean.setStrNarration(tempGRNHd.getStrNarration());
		objBean.setDblDisRate(tempGRNHd.getDblDisRate());
		objBean.setDblDisAmt(tempGRNHd.getDblDisAmt());
		objBean.setDblConversion(tempGRNHd.getDblConversion());
		objBean.setDblExtra(tempGRNHd.getDblExtra());
		objBean.setDblLessAmt(tempGRNHd.getDblLessAmt());
		objBean.setDblSubTotal(tempGRNHd.getDblSubTotal());
		objBean.setDblTaxAmt(tempGRNHd.getDblTaxAmt());
		objBean.setDblTotal(tempGRNHd.getDblTotal());
		objBean.setStrConsignedCountry(tempGRNHd.getStrConsignedCountry());
		objBean.setStrCountryofOrigin(tempGRNHd.getStrCountryofOrigin());
		objBean.setStrCurrency(tempGRNHd.getStrCurrency());
		objBean.setStrMInBy(tempGRNHd.getStrMInBy());
		objBean.setStrTimeInOut(tempGRNHd.getStrTimeInOut());
		objBean.setStrVehNo(tempGRNHd.getStrVehNo());

		return objBean;
	}

	/**
	 * Checking Non Stockable Item
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/CheckNonStkItem", method = RequestMethod.GET)
	public @ResponseBody String funCheckNonStkItem(@RequestParam("GRNCode") String GRNCode, HttpServletRequest request) {
		String flagNonStkItem = "false";
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		List listTempDtl = objGRNService.funGetDtlList(GRNCode, clientCode);
		for (int i = 0; i < listTempDtl.size(); i++) {
			Object[] ob = (Object[]) listTempDtl.get(i);
			clsGRNDtlModel grnDtl = (clsGRNDtlModel) ob[0];
			clsProductMasterModel objModel = objProductMasterService.funGetObject(grnDtl.getStrProdCode(), clientCode);
			if (objModel.getStrNonStockableItem().equalsIgnoreCase("Yes")) {
				flagNonStkItem = "true";
			}
		}

		return flagNonStkItem;
	}

	/**
	 * Auditing Function Start
	 * 
	 * @param POModel
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	public void funSaveAudit(String strGRNCode, String strTransMode, HttpServletRequest req) {
		try {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			List listGRNHd = objGRNService.funGetObject(strGRNCode, clientCode);
			if (!listGRNHd.isEmpty()) {
				Object[] obHD = (Object[]) listGRNHd.get(0);
				clsGRNHdModel GRNModel = (clsGRNHdModel) obHD[0];

				List listTempDtl = objGRNService.funGetDtlList(GRNModel.getStrGRNCode(), clientCode);
				if (null != listTempDtl && listTempDtl.size() > 0) {
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + GRNModel.getStrGRNCode() + "' and strClientCode='" + clientCode + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");
					if (!list.isEmpty()) {
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(GRNModel);
						if (strTransMode.equalsIgnoreCase("Deleted")) {
							model.setStrTransCode(GRNModel.getStrGRNCode());
						} else {
							model.setStrTransCode(GRNModel.getStrGRNCode() + "-" + count);
						}
						model.setStrClientCode(clientCode);
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						List<clsAuditGRNTaxDtlModel> GrnTaxList = funPrepareGRNTaxDtl(GRNModel.getStrGRNCode(), req);
						for (clsAuditGRNTaxDtlModel Taxlist : GrnTaxList) {
							clsAuditGRNTaxDtlModel taxModel = new clsAuditGRNTaxDtlModel();
							taxModel.setStrGRNCode(model.getStrTransCode());
							taxModel.setStrClientCode(Taxlist.getStrClientCode());
							taxModel.setStrTaxableAmt(Taxlist.getStrTaxableAmt());
							taxModel.setStrTaxAmt(Taxlist.getStrTaxAmt());
							taxModel.setStrTaxCode(Taxlist.getStrTaxCode());
							taxModel.setStrTaxDesc(Taxlist.getStrTaxCode());
							objGlobalFunctionsService.funSaveAuditTaxDtl(taxModel);
						}

						for (int i = 0; i < listTempDtl.size(); i++) {
							Object[] ob = (Object[]) listTempDtl.get(i);
							clsGRNDtlModel grnDtl = (clsGRNDtlModel) ob[0];
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(grnDtl.getStrProdCode());
							AuditMode.setStrPICode("");
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrRemarks(grnDtl.getStrRemarks());
							AuditMode.setDblDiscount(grnDtl.getDblDiscount());
							AuditMode.setDblQty(grnDtl.getDblQty());
							AuditMode.setDblUnitPrice(grnDtl.getDblUnitPrice());
							AuditMode.setStrTaxType(grnDtl.getStrTaxType());
							AuditMode.setDblTax(grnDtl.getDblTax());
							AuditMode.setDblTaxableAmt(grnDtl.getDblTaxableAmt());
							AuditMode.setDblTaxAmt(grnDtl.getDblTaxAmt());
							AuditMode.setDblTotalPrice(grnDtl.getDblTotalPrice());
							AuditMode.setDblRejected(grnDtl.getDblRejected());
							AuditMode.setDblDCQty(grnDtl.getDblDCQty());
							AuditMode.setDblDCWt(grnDtl.getDblDCWt());
							AuditMode.setDblQtyRbl(grnDtl.getDblQtyRbl());
							AuditMode.setDblPackForw(grnDtl.getDblPackForw());
							AuditMode.setDblPOWeight(grnDtl.getDblPOWeight());
							AuditMode.setStrCode(grnDtl.getStrCode());
							AuditMode.setStrClientCode(grnDtl.getStrClientCode());
							AuditMode.setStrProdChar(grnDtl.getStrGRNProdChar());
							AuditMode.setDblRate(grnDtl.getDblRate());
							AuditMode.setDblValue(grnDtl.getDblValue());
							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * Preparing AuditHd Model
	 * 
	 * @param GRNModel
	 * @return
	 */
	private clsAuditHdModel funPrepairAuditHdModel(clsGRNHdModel GRNModel) {
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (GRNModel != null) {
			AuditHdModel.setStrTransCode(GRNModel.getStrGRNCode());
			AuditHdModel.setDtTransDate(GRNModel.getDtGRNDate());
			AuditHdModel.setStrTransType("GRN(Good Receiving Note)");
			// AuditHdModel.setStrTransMode("Edit");
			AuditHdModel.setStrAgainst(GRNModel.getStrAgainst());
			AuditHdModel.setStrLocCode(GRNModel.getStrLocCode());
			AuditHdModel.setStrSuppCode(GRNModel.getStrSuppCode());
			AuditHdModel.setStrNarration(GRNModel.getStrNarration());
			AuditHdModel.setStrPayMode(GRNModel.getStrPayMode());
			AuditHdModel.setDblDisRate(GRNModel.getDblDisRate());
			AuditHdModel.setDblDiscount(GRNModel.getDblDisAmt());
			AuditHdModel.setDblTaxAmt(GRNModel.getDblTaxAmt());
			AuditHdModel.setDblExtra(GRNModel.getDblExtra());
			AuditHdModel.setDblSubTotal(GRNModel.getDblSubTotal());
			AuditHdModel.setDblTotalAmt(GRNModel.getDblTotal());
			AuditHdModel.setStrBillNo(GRNModel.getStrBillNo());
			AuditHdModel.setDtBillDate(GRNModel.getDtBillDate());
			AuditHdModel.setDtDueDate(GRNModel.getDtDueDate());
			AuditHdModel.setDtRefDate(GRNModel.getDtRefDate());
			AuditHdModel.setStrMInBy(GRNModel.getStrMInBy());
			AuditHdModel.setStrNo(GRNModel.getStrNo());
			AuditHdModel.setStrRefNo(GRNModel.getStrRefNo());
			AuditHdModel.setStrShipmentMode(GRNModel.getStrShipmentMode());
			AuditHdModel.setStrTimeInOut(GRNModel.getStrTimeInOut());
			AuditHdModel.setStrVehNo(GRNModel.getStrVehNo());
			AuditHdModel.setDtRefDate(GRNModel.getDtRefDate());
			AuditHdModel.setStrAuthorise(GRNModel.getStrAuthorise());
			AuditHdModel.setStrPONo(GRNModel.getStrPONo());
			AuditHdModel.setDtDateCreated(GRNModel.getDtDateCreated());
			AuditHdModel.setStrUserCreated(GRNModel.getStrUserCreated());
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrCode("");

		}
		return AuditHdModel;

	}

	@SuppressWarnings({ "rawtypes" })
	public List<clsAuditGRNTaxDtlModel> funPrepareGRNTaxDtl(String grnCode, HttpServletRequest request) {
		
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// userCode=request.getSession().getAttribute("usercode").toString();

		String sql = "select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt,strClientCode from clsGRNTaxDtlModel " + " where strGRNCode='" + grnCode + "' and strClientCode='" + clientCode + "' ";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		List<clsAuditGRNTaxDtlModel> listGRNTaxDtl = new ArrayList<clsAuditGRNTaxDtlModel>();
		for (int cnt = 0; cnt < list.size(); cnt++) {
			clsAuditGRNTaxDtlModel objTaxDtl = new clsAuditGRNTaxDtlModel();
			Object[] arrObj = (Object[]) list.get(cnt);
			objTaxDtl.setStrTaxCode(arrObj[0].toString());
			objTaxDtl.setStrTaxDesc(arrObj[1].toString());
			objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
			objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));
			objTaxDtl.setStrClientCode(arrObj[4].toString());
			listGRNTaxDtl.add(objTaxDtl);
		}

		return listGRNTaxDtl;
	}

	/**
	 * Open GRN Slip After Saving GRN
	 * 
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/openRptGrnSlip", method = RequestMethod.GET)
	private void funCallReportOnSubmit(HttpServletResponse resp, HttpServletRequest req) {
		String GRNCode = req.getParameter("rptGRNCode").toString();
		// req.getSession().removeAttribute("rptGRNCode");
		String type = "pdf";
		funCallReport(GRNCode, type, resp, req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReport(String GRNCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {
			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String strFinYear = req.getSession().getAttribute("financialYear").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			double currValue=1.0;
			String Currency="";
			String sql1=" select a.dblConversion,ifnull(strCurrency,'') from tblgrnhd a where a.strGRNCode='"+GRNCode+"' ";
			List list = objGlobalFunctionsService.funGetList(sql1, "sql");	
			if(list.size()>0)
			{
				Object obj[]=(Object[])list.get(0);
				currValue=Double.parseDouble(obj[0].toString());
				Currency=obj[1].toString();
			}
			clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(Currency, clientCode);
			if (null!=objModel )
			{
				// dblCurrencyConv=1;
				
				Currency=objModel.getStrCurrencyName();
			}
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptGrnDtlSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = " SELECT g.strGRNCode,  DATE_FORMAT(g.dtGRNDate,'%d-%m-%Y')as dtGRNDate, g.strSuppCode, g.strAgainst, g.strPONo, g.strBillNo, DATE_FORMAT(g.dtBillDate,'%d-%m-%Y') as dtBillDate, " + "  DATE_FORMAT(g.dtDueDate,'%d-%m-%Y') as dtDueDate, g.strPayMode, g.dblSubTotal/(" + currValue + ") as dblSubTotal , g.dblDisRate, g.dblDisAmt/(" + currValue + ") as dblDisAmt, g.dblTaxAmt/("
					+ currValue + ") as dblTaxAmt, g.dblExtra/(" + currValue + ") as dblExtra, g.dblTotal/(" + currValue + ") as dblTotal, " + " g.strNarration, g.strLocCode, s.strPCode, s.strPName, s.strBAdd1, s.strBAdd2, s.strBCity, s.strBPin, "
					+ " s.strBState, s.strBCountry, g.strNo,g.strRefNo, DATE_FORMAT(g.dtRefDate,'%d-%m-%Y') as dtRefDate,g.dblLessAmt,dblTaxAmt ,g.dblDisRate,g.strNarration ,g.strVehNo,g.strUserCreated,g.strAuthLevel2,g.strAuthLevel1 " + " FROM "+webStockDB+".tblgrnhd AS g INNER JOIN "+webStockDB+".tblpartymaster AS s ON g.strSuppCode = s.strPCode and s.strClientCode='" + clientCode + "'"
					+ "	Left outer join "+webStockDB+".tblgrntaxdtl as t on t.strGRNCode=g.strGRNCode and t.strClientCode='" + clientCode + "'" + " WHERE g.strGRNCode = '" + GRNCode + "' and g.strClientCode='" + clientCode + "' group by g.strGRNCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);
			String sql2 = "select g.strGRNCode,g.strProdCode,p.strProdName,ifnull(p.strReceivedUOM,'') as strReceivedUOM,g.dblQty,g.dblRejected,ifnull(g.dblDiscount/(" + currValue + "),0) as dblDiscount, " + " g.strTaxType,ifnull(g.dblTaxableAmt/(" + currValue + "),0) as dblTaxableAmt,g.dblTax,ifnull(g.dblTaxAmt/(" + currValue + "),0) as dblTaxAmt, g.dblUnitPrice/(" + currValue
					+ ") as dblUnitPrice,g.dblWeight,g.strProdChar,g.dblDCQty, " + " g.dblDCWt,g.strRemarks,g.dblQtyRbl,g.strGRNProdChar,g.dblPOWeight,g.strCode, g.dblRework,g.dblPackForw," + " g.dblRate,g.dblValue/(" + currValue + ") as dblValue,p.strPartNo,p.dblUnitPrice/(" + currValue + ") as stdRate,p.dblUnitPrice/(" + currValue
					+ ")*(g.dblQty-g.dblRejected) as stdAmt from "+webStockDB+".tblgrndtl g,"+webStockDB+".tblproductmaster p " + " where g.strProdCode=p.strProdCode and g.strGRNCode='" + GRNCode + "' and g.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "' order by p.strProdName ";
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql2);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsGrnDtl");
			subDataset.setQuery(subQuery);

			String Sql3 = "select a.strTaxDesc,a.strTaxAmt/(" + currValue + ") as strTaxAmt from "+webStockDB+".tblgrntaxdtl a where strGRNCode='" + GRNCode + "' and strClientCode='" + clientCode + "'";

			JRDesignQuery taxQuery = new JRDesignQuery();
			taxQuery.setText(Sql3);
			JRDesignDataset taxDataset = (JRDesignDataset) datasetMap.get("dsTaxDtl");
			taxDataset.setQuery(taxQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strGRN Code", GRNCode);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strFinYear", strFinYear);
			hm.put("currValue", currValue);
			hm.put("Currency",Currency);
			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "inline;filename=" + "rptGRNSlip." + type.trim());
				resp.setContentLength(bytes.length);
				servletOutputStream.write(bytes, 0, bytes.length);
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptGRNSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}






	/**
	 * Open Report From
	 */
	@RequestMapping(value = "/frmGrnSlip", method = RequestMethod.GET)
	public ModelAndView funOpenMISSlipForm(Map<String, Object> model, HttpServletRequest request) {

		request.getSession().setAttribute("formName", "frmWebStockHelpGRNSlip");
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGrnSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmGrnSlip", "command", new clsReportBean());
		}

	}

	/**
	 * GRN Range Printing Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptGrnSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());

		String strFromGRNCode = objBean.getStrFromDocCode();
		String strToGRNCode = objBean.getStrToDocCode();

		String type = objBean.getStrDocType();
		String tempSupp[] = objBean.getStrSuppCode().split(",");
		String strSuppCodes = "";
		try {
			String sql = "select a.strGRNCode from tblgrnhd a where date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' ";

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

			if (strFromGRNCode.trim().length() > 0 && strToGRNCode.trim().length() > 0) {
				sql = sql + " and a.strGRNCode between '" + strFromGRNCode + "' and '" + strToGRNCode + "' ";
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
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptGRNSlip." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else if (type.trim().equalsIgnoreCase("xls")) {
					JRExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptGRNSlip." + type.trim());
					exporterXLS.exportReport();
					resp.setContentType("application/xlsx");
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");
			}
		} catch (JRException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	@SuppressWarnings({ "finally", "rawtypes", "unchecked" })
	public JasperPrint funCallRangePrintReport(String GRNCode, HttpServletResponse resp, HttpServletRequest req)
	{

		Connection con = objGlobalFunctions.funGetConnection(req);
		JasperPrint p = null;
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptGrnDtlSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String sql = " SELECT g.strGRNCode, DATE_FORMAT(g.dtGRNDate,'%d-%m-%Y') as dtGRNDate, g.strSuppCode, g.strAgainst, g.strPONo, g.strBillNo, DATE_FORMAT(g.dtBillDate,'%d-%m-%Y') as dtBillDate, DATE_FORMAT(g.dtDueDate,'%d-%m-%Y') " + " as dtDueDate, g.strPayMode, g.dblSubTotal, g.dblDisRate, g.dblDisAmt, g.dblTaxAmt, g.dblExtra, g.dblTotal, " + " g.strNarration, g.strLocCode, s.strPCode, s.strPName, s.strBAdd1, s.strBAdd2, s.strBCity, s.strBPin, " + " s.strBState, s.strBCountry, g.strNo,g.strRefNo, DATE_FORMAT(g.dtRefDate,'%d-%m-%Y') as dtRefDate,g.dblLessAmt,dblTaxAmt ,g.dblDisRate,g.strNarration ,g.strVehNo " + ",g.strUserCreated,g.strAuthLevel1,g.strAuthLevel2 " + " FROM tblgrnhd AS g INNER JOIN tblpartymaster AS s ON g.strSuppCode = s.strPCode and s.strClientCode='" + clientCode + "'" + "	Left outer join tblgrntaxdtl as t on t.strGRNCode=g.strGRNCode and t.strClientCode='" + clientCode + "'" + " WHERE g.strGRNCode = '" + GRNCode + "' and g.strClientCode='" + clientCode + "'";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sql);
			jd.setQuery(newQuery);
			String sql2 = "select g.strGRNCode,g.strProdCode,p.strProdName,ifnull(p.strReceivedUOM,'') as strReceivedUOM,g.dblQty,g.dblRejected,g.dblDiscount," + " g.strTaxType,g.dblTaxableAmt,g.dblTax,dblTaxAmt, g.dblUnitPrice,g.dblWeight,g.strProdChar,g.dblDCQty," + " g.dblDCWt,g.strRemarks,g.dblQtyRbl,g.strGRNProdChar,g.dblPOWeight,g.strCode, g.dblRework,g.dblPackForw," + " g.dblRate,g.dblValue,p.strPartNo ,p.dblUnitPrice as stdRate,p.dblUnitPrice*(g.dblQty-g.dblRejected) as stdAmt  from tblgrndtl g,tblproductmaster p " + " where g.strProdCode=p.strProdCode and g.strGRNCode='" + GRNCode + "' and g.strClientCode='" + clientCode + "' and p.strClientCode='" + clientCode + "' order by p.strProdName ";
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sql2);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsGrnDtl");
			subDataset.setQuery(subQuery);
			String Sql3 = "select a.strTaxDesc,a.strTaxAmt from tblgrntaxdtl a where strGRNCode='" + GRNCode + "' and strClientCode='" + clientCode + "'";

			JRDesignQuery taxQuery = new JRDesignQuery();
			taxQuery.setText(Sql3);
			JRDesignDataset taxDataset = (JRDesignDataset) datasetMap.get("dsTaxDtl");
			taxDataset.setQuery(taxQuery);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			hm.put("strGRN Code", GRNCode);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
			p = JasperFillManager.fillReport(jr, hm, con);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		finally
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{

				e.printStackTrace();
			}
			return p;
		}
	}

	
	@RequestMapping(value = "/frmGRNRegisterReport", method = RequestMethod.GET)
	public ModelAndView funOpenReportForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		} else {
			mapLocation.put("All", "All");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		model.put("listLocation", mapLocation);

		HashMap<String, String> mapSupplier = objGlobalService.funGetSupplierList(propertyCode, clientCode);
		if (mapSupplier.isEmpty()) {
			mapSupplier.put("", "");
		} else {
			mapSupplier.put("All", "All");
		}

		mapSupplier = clsGlobalFunctions.funSortByValues(mapSupplier);
		model.put("listSupplier", mapSupplier);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRNRegisterReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRNRegisterReport", "command", new clsReportBean());
		} else {
			return null;
		}
		// return new ModelAndView("frmJV","command", new clsJVHdModel());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptGRNRegisterReport", method = RequestMethod.POST)
	private ModelAndView funCallGRNRegisterReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());

		String supplierCode = objBean.getStrSuppCode();
		String locationCode = objBean.getStrLocationCode();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String tempLoc[] = objBean.getStrLocationCode().split(",");
		String strLocCodes = "";

		String locNames = "";
		for (int i = 0; i < tempLoc.length; i++) {
			if (strLocCodes.length() > 0) {
				clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
				locNames += objLocModel.getStrLocName() + ",";
				strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";
			} else {
				clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
				locNames += objLocModel.getStrLocName() + ",";
				strLocCodes = "a.strLocCode='" + tempLoc[i] + "' ";
			}
		}

		String tempSupp[] = objBean.getStrSuppCode().split(",");
		String strSuppCodes = "";

		for (int i = 0; i < tempSupp.length; i++) {
			if (strSuppCodes.length() > 0) {
				strSuppCodes = strSuppCodes + " or a.strSuppCode='" + tempSupp[i] + "' ";
			} else {
				strSuppCodes = "a.strSuppCode='" + tempSupp[i] + "' ";

			}
		}

		List ExportList = new ArrayList();
		ExportList.add("rptGRNRegisterReport_" + fromDate + "to" + toDate + "_" + userCode);
		List titleData = new ArrayList<>();
		titleData.add("GRN Register Report");
		ExportList.add(titleData);
		List filterData = new ArrayList<>();
		titleData.add("Locations");
		titleData.add(locNames);
		filterData.add("From Date");
		filterData.add(fromDate);
		filterData.add("To Date");
		filterData.add(toDate);

		ExportList.add(filterData);

		String header = "GRNCode ,GRNDate,SuppCode,SuppName,PIndicator,LocCode,LocName,ProdCode,  ProdName,  GroupCode, GroupName, " + "SGCode, SubGroupName, class,TaxCode,TaxName,TaxPer,RejQty,AcceptQty,Rate,Amount, TaxAmt,TotalAmt, ";
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String sqlDtl = "  select a.strGRNCode as GRNCode ,DATE_FORMAT(DATE(a.dtGRNDate),'%d-%m-%Y')  AS GRNDate,a.strSuppCode as SuppCode, d.strPName as SuppName," + "  d.strPType as PIndicator, a.strLocCode as LocCode,  e.strLocName as LocName,b.strProdCode, " + "  c.strProdName as ProdName,f.strGCode as GroupCode, g.strGName as GroupName, f.strSGCode, "
				+ "  f.strSGName as SubGroupName, case c.strClass  WHEN '' THEN 'NA'  else c.strClass  end  as strClass," + "  'TaxCode','TaxName','TaxPer',  b.dblRejected as RejQty," + "  (b.dblQty-b.dblRejected) as AcceptQty,b.dblUnitPrice as Rate, " + "  b.dblUnitPrice*(b.dblQty-b.dblRejected) as Amount, 'TaxAmt'," + "  ifnull(a.dblTotal,'') as TotalAmt "
				+ "  from tblgrnhd a ,tblgrndtl b ,tblproductmaster c,tblpartymaster d,tbllocationmaster e,tblsubgroupmaster f,tblgroupmaster g  " + "	 where a.strGRNCode=b.strGRNCode and b.strProdCode=c.strProdCode " + "  and a.strSuppCode=d.strPCode and a.strLocCode=e.strLocCode and c.strSGCode=f.strSGCode " + "  and f.strGCode = g.strGCode and a.strClientCode='" + clientCode + "' "
				+ "  and date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' ";

		sqlDtl = sqlDtl + " and " + "(" + strLocCodes + ")" + "and " + "(" + strSuppCodes + ") ";

		sqlDtl += " and a.strClientCode=b.strClientCode " + " and b.strClientCode=c.strClientCode " + " and c.strClientCode=d.strClientCode " + " and d.strClientCode=e.strClientCode " + " and e.strClientCode=f.strClientCode " + " and f.strClientCode=g.strClientCode and e.strPropertyCode='" + propertyCode + "' " + " order by a.strGRNCode,d.strPName,c.strProdName ";

		List list = objGlobalFunctionsService.funGetList(sqlDtl.toString(), "sql");
		List OpeningStklist = new ArrayList();
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		double subToltal = 0.00;
		String grnCode = "";
		double grandToltal = 0.00;
		HashMap<String, Double> hmTaxTotalGrid = new HashMap<String, Double>();
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			String prodCode = ob[7].toString();
			String unitPrice = ob[19].toString();
			String suppCode = ob[2].toString();
			String qty = ob[18].toString();
			String disAmt = "0";
			String dteGRN = ob[1].toString();
			String taxableAmt = "0";
			String taxCode = "";
			String taxDesc = "";
			String taxPer1 = "0";
			String taxAmt = "0";
			double dblTaxamt = 0;
			double dblRowAmtTotal = 0.00;
			if (i == 0) {
				grnCode = "a.strGRNCode='" + ob[0].toString() + "'";
			} else {
				if (!grnCode.contains(ob[0].toString())) {
					grnCode = grnCode + " or a.strGRNCode='" + ob[0].toString() + "'";
				}
			}
			String prdDetailForTax = prodCode + "," + unitPrice + "," + suppCode + "," + qty + "," + disAmt;
			Map<String, String> hmProdTax = objGlobalFunctions.funCalculateTax(prdDetailForTax, "Purchase", objGlobalFunctions.funGetDate("yyyy-MM-dd", dteGRN), "0", req);

			if (hmProdTax.size() > 0) {
				for (Map.Entry<String, String> entry : hmProdTax.entrySet()) {
					String taxdetails = entry.getValue();
					String[] spItem = taxdetails.split("#");
					taxableAmt = spItem[0];
					taxCode = spItem[1] + "," + taxCode;
					taxDesc = spItem[2] + "," + taxDesc;
					taxPer1 = spItem[4] + "," + taxPer1;
					dblTaxamt += Double.parseDouble(spItem[5].toString());
					taxAmt = spItem[5];
					if (hmTaxTotalGrid.containsKey(spItem[2])) {
						double dbltax = hmTaxTotalGrid.get(spItem[2]);
						hmTaxTotalGrid.put(spItem[2], dbltax + Double.parseDouble(taxAmt));
						// hmTaxTotalGrid.put(spItem[2], (dbltax+dblTaxamt));
					} else {
						hmTaxTotalGrid.put(spItem[2], Double.parseDouble(taxAmt));
						// hmTaxTotalGrid.put(spItem[2], dblTaxamt);
					}

					System.out.println(entry.getKey() + "/" + entry.getValue());
				}
				taxCode = taxCode.substring(0, taxCode.length() - 1);
			}

			List DataList = new ArrayList<>();
			DataList.add(ob[0].toString()); // GRNCode
			DataList.add(ob[1].toString()); // GRNDate
			DataList.add(ob[2].toString()); // SuppCode
			DataList.add(ob[3].toString()); // SuppName
			DataList.add(ob[4].toString()); // PIndicator
			DataList.add(ob[5].toString()); // LocCode
			DataList.add(ob[6].toString()); // LocName

			DataList.add(ob[7].toString()); // strProdCode
			DataList.add(ob[8].toString()); // ProdName
			DataList.add(ob[9].toString()); // GroupCode
			DataList.add(ob[10].toString()); // GroupName
			DataList.add(ob[11].toString()); // SGCode
			DataList.add(ob[12].toString()); // SGName
			DataList.add(ob[13].toString()); // strClass

			DataList.add(taxCode); // TaxCode
			// Double acceptQty=
			// (GRNDtlModel.getDblQty()-GRNDtlModel.getDblRejected());
			DataList.add(taxDesc); // TaxNAme
			DataList.add(taxPer1); // taxper
			// Double amount=GRNDtlModel.getDblUnitPrice()*acceptQty;
			DataList.add(ob[17].toString()); // RejQty
			DataList.add(ob[18].toString()); // AcceptQty
			DataList.add(Double.parseDouble(ob[19].toString()) / currValue); // Rate
			DataList.add(Double.parseDouble(ob[20].toString()) / currValue); // Amount
			DataList.add(dblTaxamt / currValue); // TaxAmt
			dblRowAmtTotal = Double.parseDouble(ob[20].toString()) + dblTaxamt;
			DataList.add(dblRowAmtTotal / currValue); // TotalAmt = Amt + taxAmt
			grandToltal += dblRowAmtTotal;

			subToltal += Double.parseDouble(ob[20].toString());

			OpeningStklist.add(DataList);

		}

		List DataListblank = new ArrayList<>();
		for (int i = 0; i < 23; i++) {
			DataListblank.add("");
		}

		OpeningStklist.add(DataListblank);

		if (subToltal > 0) {
			List DataListSubtotal = new ArrayList<>();
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");

			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");

			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");

			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");

			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");
			DataListSubtotal.add("");

			DataListSubtotal.add("");
			DataListSubtotal.add("Sub Total");
			DataListSubtotal.add(subToltal / currValue);

			OpeningStklist.add(DataListSubtotal);
		}

		String sqldisAmt = " SELECT ifnull(sum(a.dblDisAmt ),0)  ,ifnull(sum(a.dblExtra),0) FROM tblgrnhd a " + " WHERE DATE(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' and a.strClientCode='" + clientCode + "' and (" + grnCode + " ) ";
		List listDis = objGlobalFunctionsService.funGetList(sqldisAmt, "sql");
		double totDisAmt = 0.0;
		double totExtraAmt = 0.0;
		if (listDis.size() > 0) {
			Object[] obj = (Object[]) listDis.get(0);
			totDisAmt = Double.parseDouble(obj[0].toString());
			totExtraAmt = Double.parseDouble(obj[1].toString());
		}

		List DataListSubtotal = new ArrayList<>();
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");

		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");

		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");

		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");

		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");
		DataListSubtotal.add("");

		DataListSubtotal.add("");
		DataListSubtotal.add("Total Discount");
		DataListSubtotal.add(totDisAmt / currValue);

		OpeningStklist.add(DataListSubtotal);

		List DataListExtra = new ArrayList<>();
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");

		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");

		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");

		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");

		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");
		DataListExtra.add("");

		DataListExtra.add("");
		DataListExtra.add("Total Extra Charges");
		DataListExtra.add(totExtraAmt / currValue);

		OpeningStklist.add(DataListExtra);

		for (Map.Entry<String, Double> entry : hmTaxTotalGrid.entrySet()) {
			List DataListTax = new ArrayList<>();
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");

			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");

			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");

			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");

			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");
			DataListTax.add("");

			DataListTax.add("");
			DataListTax.add(entry.getKey());
			DataListTax.add(entry.getValue() / currValue);

			OpeningStklist.add(DataListTax);
		}
		grandToltal = grandToltal - totDisAmt + totExtraAmt;
		if (grandToltal > 0) {
			List DataListgrandtotal = new ArrayList<>();
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");

			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");

			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");

			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");

			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");
			DataListgrandtotal.add("");

			DataListgrandtotal.add("");
			DataListgrandtotal.add("Grand Total");
			DataListgrandtotal.add(grandToltal / currValue);

			OpeningStklist.add(DataListgrandtotal);
		}

		ExportList.add(OpeningStklist);

		return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", ExportList);
		// return new ModelAndView("excelView", "stocklist", ExportList);
	}



	@RequestMapping(value = "/frmGRNSummaryReport", method = RequestMethod.GET)
	public ModelAndView funOpenGRNSummaryReportForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGRNSummaryReport_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmGRNSummaryReport", "command", new clsReportBean());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/rptGRNSummaryReport", method = RequestMethod.POST)
	private ModelAndView funCallGRNSummaryReportt(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String fromDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtFromDate());
		String toDate = objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDtToDate());
		String supplierCode = objBean.getStrSuppCode();
		String[] tempSupplierCode = objBean.getStrSuppCode().split(",");
		String strSuppCode = "";
		String gruopcode = objBean.getStrGCode();
		String[] tempgroupCode = objBean.getStrGCode().split(",");
		String strGCode = "";
		String subGroupCode = objBean.getStrSGCode();
		String[] tempsubGroupCode = objBean.getStrSGCode().split(",");
		String strSGCode = "";
		String tempLoc[] = objBean.getStrLocationCode().split(",");
		String strLocCodes = "";

		String type = objBean.getStrDocType();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		if (tempSupplierCode[0].length() > 0) {
			for (int i = 0; i < tempSupplierCode.length; i++) {
				if (strSuppCode.length() > 0) {
					strSuppCode = strSuppCode + " or a.strSuppCode='" + tempSupplierCode[i] + "' ";
				} else {
					strSuppCode = "a.strSuppCode='" + tempSupplierCode[i] + "' ";

				}
			}
		}

		for (int i = 0; i < tempgroupCode.length; i++) {
			if (strGCode.length() > 0) {
				strGCode = strGCode + " or f.strGCode='" + tempgroupCode[i] + "' ";
			} else {
				strGCode = "f.strGCode='" + tempgroupCode[i] + "' ";

			}
		}
		for (int i = 0; i < tempsubGroupCode.length; i++) {
			if (strSGCode.length() > 0) {
				strSGCode = strSGCode + " or c.strSGCode='" + tempsubGroupCode[i] + "' ";
			} else {
				strSGCode = "c.strSGCode='" + tempsubGroupCode[i] + "' ";

			}
		}
		String locNames = "";
		for (int i = 0; i < tempLoc.length; i++) {
			if (strLocCodes.length() > 0) {
				clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
				locNames += objLocModel.getStrLocName() + ",";
				strLocCodes = strLocCodes + " or a.strLocCode='" + tempLoc[i] + "' ";
			} else {
				clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(tempLoc[i], clientCode);
				locNames += objLocModel.getStrLocName() + ",";
				strLocCodes = "a.strLocCode='" + tempLoc[i] + "' ";

			}
		}

		List ExportList = new ArrayList();

		ExportList.add("rptGRNSummaryReport_" + fromDate + "to" + toDate + "_" + userCode);

		List titleData = new ArrayList<>();
		titleData.add("GRN Summary Report");
		ExportList.add(titleData);

		List filterData = new ArrayList<>();
		titleData.add("Locations");
		titleData.add(locNames);
		filterData.add("From Date");
		filterData.add(fromDate);
		filterData.add("To Date");
		filterData.add(toDate);
		ExportList.add(filterData);

		String header = "Group ,SubGroup,Location,Party Indicator,NonTaxableAmount,TaxableAmount,TaxAmount,TaxBillAmount, TotalAmount ";
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		String sqlDtl = "SELECT  g.strGName AS GroupName,f.strSGName AS SubGroupName,  e.strLocName AS LocName,d.strPType AS PIndicator, " + " IFNULL((a.dblTotal-(h.strTaxableAmt+h.strTaxAmt)),'') as NONTaxAmt, " + " IFNULL(h.strTaxableAmt,'') AS TaxableAmt,IFNULL(h.strTaxAmt,'') AS TaxAmt, IFNULL((h.strTaxableAmt+h.strTaxAmt),'') AS TaxBillAmt, "
				+ " IFNULL((a.dblSubTotal+a.dblDisAmt+a.dblTaxAmt+dblExtra), '') as TotalAmt " + " FROM tblgrnhd a " + " LEFT OUTER " + " JOIN tblgrndtl b ON a.strGRNCode=b.strGRNCode " + " LEFT OUTER " + " JOIN tblproductmaster c ON b.strProdCode=c.strProdCode " + " LEFT OUTER " + " JOIN tblpartymaster d ON a.strSuppCode=d.strPCode " + " LEFT OUTER "
				+ " JOIN tbllocationmaster e ON a.strLocCode=e.strLocCode " + " LEFT OUTER " + " JOIN tblsubgroupmaster f ON c.strSGCode=f.strSGCode " + " LEFT OUTER " + " JOIN tblgroupmaster g ON f.strGCode = g.strGCode " + " LEFT OUTER " + " JOIN tblgrntaxdtl h ON a.strGRNCode=h.strGRNCode " + " LEFT OUTER " + " JOIN tbltaxhd i ON h.strTaxCode=i.strTaxCode " + " WHERE a.strClientCode='"
				+ clientCode + "' AND DATE(a.dtGRNDate) BETWEEN '" + fromDate + "' AND '" + toDate + "'  ";

		sqlDtl = sqlDtl + " and " + "(" + strGCode + ")" + "and " + "(" + strSGCode + ") " + "and " + "(" + strLocCodes + ") ";
		if (strSuppCode.length() > 0) {
			sqlDtl = sqlDtl + " and " + "(" + strSuppCode + ") ";
		}

		sqlDtl = sqlDtl + " AND a.strClientCode=b.strClientCode " + " AND b.strClientCode=c.strClientCode AND c.strClientCode=d.strClientCode " + " AND d.strClientCode=e.strClientCode AND e.strClientCode=f.strClientCode  " + " AND f.strClientCode=g.strClientCode AND e.strPropertyCode='" + propertyCode + "' " + " GROUP BY a.strGRNCode  order by e.strLocName,g.strGName,f.strSGName ";

		List list = objGlobalFunctionsService.funGetList(sqlDtl.toString(), "sql");
		List GRNSummarylist = new ArrayList();
		double totNonTaxAmt = 0.00;
		double totTaxableAmt = 0.00;
		double totTaxAmt = 0.00;
		double totTaxbillAmt = 0.00;
		double totAmt = 0.00;
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);

			List DataList = new ArrayList<>();
			DataList.add(ob[0].toString());
			DataList.add(ob[1].toString());
			DataList.add(ob[2].toString());
			DataList.add(ob[3].toString());

			DataList.add(ob[4].toString());
			totNonTaxAmt = totNonTaxAmt + Double.parseDouble(ob[4].toString().isEmpty() ? "0.0" : ob[4].toString());

			DataList.add(ob[5].toString());
			totTaxableAmt = totTaxableAmt + Double.parseDouble(ob[5].toString().isEmpty() ? "0.0" : ob[5].toString());
			;

			DataList.add(ob[6].toString());
			totTaxAmt = totTaxAmt + Double.parseDouble(ob[6].toString().isEmpty() ? "0.0" : ob[6].toString());
			;

			DataList.add(ob[7].toString());
			totTaxbillAmt = totTaxbillAmt + Double.parseDouble(ob[7].toString().isEmpty() ? "0.0" : ob[7].toString());
			;

			DataList.add(ob[8].toString());
			totAmt = totAmt + Double.parseDouble(ob[8].toString().isEmpty() ? "0.0" : ob[8].toString());
			;

			GRNSummarylist.add(DataList);
		}

		List totDataList = new ArrayList<>();
		totDataList.add("");
		totDataList.add("");
		totDataList.add("");
		totDataList.add("Total");
		totDataList.add(totNonTaxAmt);
		totDataList.add(totTaxableAmt);
		totDataList.add(totTaxAmt);
		totDataList.add(totTaxbillAmt);
		totDataList.add(totAmt);

		GRNSummarylist.add(totDataList);

		ExportList.add(GRNSummarylist);

		// return new ModelAndView("excelView", "stocklist", ExportList);
		return new ModelAndView("excelViewFromDateTodateWithReportName", "listFromDateTodateWithReportName", ExportList);
	}

	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/charSalesData", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody String funLoadCharBean(@RequestBody Object obj, HttpServletRequest request) {
		@SuppressWarnings("unused")
		List<clsTransectionProdCharModel> listTransProdChar = new ArrayList<clsTransectionProdCharModel>();

		return null;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadGRNProductRate", method = RequestMethod.GET)
	public @ResponseBody List funLatestGRNProductRate(@RequestParam("prodCode") String prodCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "";

		sql = " SELECT ifnull(MAX(DATE(a.dtGRNDate)),''),ifnull(b.dblUnitPrice,p.dblCostRM) " + " FROM tblproductmaster p left outer join tblgrndtl b on p.strProdCode=b.strProdCode " + " left outer join tblgrnhd a on a.strGRNCode=b.strGRNCode " + " WHERE   p.strProdCode='" + prodCode + "' AND p.strClientCode='" + clientCode + "';  ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		List ProdList = new ArrayList();
		if (list.size() > 0) {
			Object[] ob = (Object[]) list.get(0);

			ProdList.add(ob[0].toString());
			ProdList.add(Double.parseDouble(ob[1].toString()));

		} else {
			ProdList = new ArrayList();
			ProdList.add("Invalid Code ");
		}
		return ProdList;
	}

	@RequestMapping(value = "/loadAgainstDS", method = RequestMethod.GET)
	public @ResponseBody clsGRNBean funLoadAgainstDS(@RequestParam("dsCode") String dsCode, HttpServletRequest req, HttpServletResponse response) {
		List<clsGRNDtlModel> listGRNDtl = new ArrayList<clsGRNDtlModel>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsDeliveryScheduleModulehd objModel = objDeliveryScheduleService.funLoadDSData(dsCode, clientCode);
		clsGRNBean objBean = new clsGRNBean();

		if (null != objModel) {
			objBean.setStrLocCode(objModel.getStrLocCode());
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(objModel.getStrLocCode(), clientCode);
			objBean.setStrLocName(objLocCode.getStrLocName());
			objBean.setStrPONo(objModel.getStrDSCode());

			for (clsDeliveryScheduleModuledtl objDtl : objModel.getListDeliveryModelDtl()) {
				clsGRNDtlModel objGRNModelDtl = new clsGRNDtlModel();
				objGRNModelDtl.setStrProdCode(objDtl.getStrProdCode());
				clsProductMasterModel objProdModel = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
				objGRNModelDtl.setStrProdName(objProdModel.getStrProdName());
				objGRNModelDtl.setDblQtyRbl(objDtl.getDblQty());
				objGRNModelDtl.setDblQty(objDtl.getDblQty());
				objGRNModelDtl.setDblDCQty(objDtl.getDblQty());
				objGRNModelDtl.setDblDCWt(0);
				objGRNModelDtl.setDblRate(objDtl.getDblUnitPrice());
				objGRNModelDtl.setDblTotalWt(0);
				objGRNModelDtl.setDblRejected(0);
				objGRNModelDtl.setStrUOM(objDtl.getStrUOM());
				objGRNModelDtl.setDblUnitPrice(objDtl.getDblUnitPrice());
				objGRNModelDtl.setDblDiscount(0);
				objGRNModelDtl.setDblPackForw(0);
				objGRNModelDtl.setDblTotalPrice(objDtl.getDblTotalPrice());
				objGRNModelDtl.setStrRemarks(objProdModel.getStrRemark());
				objGRNModelDtl.setDblPOWeight(0);
				objGRNModelDtl.setStrIssueLocation("");
				objGRNModelDtl.setStrIsueLocName("");
				objGRNModelDtl.setStrCode("");
				objGRNModelDtl.setDblRework(0);

				listGRNDtl.add(objGRNModelDtl);
			}

			objBean.setListGRNDtl(listGRNDtl);
		}
		return objBean;
	}
	
	
	
	public clsProductMasterModel funGetSupplierWiseContractRate(String prodCode,String strSuppCode,String billDate) {
		clsProductMasterModel objProdMasterModel =new clsProductMasterModel();
		if(!billDate.isEmpty())
		{
			billDate=objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", billDate);
			if(billDate.contains("-"))
			{
				billDate=billDate.split(" ")[0];
			}
		}
		
		String sql = " select b.strProductCode,b.strProductName,b.dblRate,c.dblCostRM,c.dblWeight,c.strBinNo,c.strUOM,'N',c.strNonStockableItem,c.strLocCode from tblrateconthd a , tblratecontdtl b,tblproductmaster c "
				+ " where a.strRateContNo=b.strRateContNo and b.strProductCode=c.strProdCode"
				+ " and a.strSuppCode='"+strSuppCode+"' and b.strProductCode='"+prodCode+"' and DATE(a.dtFromDate) <='"+billDate+"'  AND DATE(a.dtToDate) >='"+billDate+"'  ";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		List ProdList = new ArrayList();
		if (list.size() > 0) {
			Object[] ob = (Object[]) list.get(0);
			objProdMasterModel.setStrProdCode(ob[0].toString());
			objProdMasterModel.setStrProdName(ob[1].toString());
			objProdMasterModel.setDblCostRM(Double.valueOf(ob[2].toString()));
			objProdMasterModel.setDblWeight(Double.valueOf(ob[4].toString()));
			objProdMasterModel.setStrBinNo(ob[5].toString());
			objProdMasterModel.setStrUOM(ob[6].toString());
			objProdMasterModel.setStrExpDate(ob[7].toString());
			objProdMasterModel.setStrNonStockableItem(ob[8].toString());
			objProdMasterModel.setStrLocCode(ob[9].toString());
			objProdMasterModel.setStrRemark("Contract Rate Amt");
		} 
		return objProdMasterModel;
	}
	
	
	
	
	
	
	
	public List funGetSupplierWiseContractRateForPO(String prodCode,String strSuppCode,String poCode,String billDate) {
		clsProductMasterModel objProdMasterModel =new clsProductMasterModel();
		if(!billDate.isEmpty())
		{
			billDate=objGlobalFunctions.funGetDateAndTime("yyyy-MM-dd", billDate);
			if(billDate.contains("-"))
			{
				billDate=billDate.split(" ")[0];
			}
		}
		List ProdList = null;
		String sql=" SELECT b.strProductCode,b.strProductName,b.dblRate,c.dblCostRM,c.dblWeight,c.strBinNo,c.strUOM,'N',c.strNonStockableItem,c.strLocCode,e.dblOrdQty-ifnull(sum(f.dblQty),0) as dblOrdQty,e.strPOCode FROM tblrateconthd a, tblratecontdtl b,tblproductmaster c"
				+ " ,tblpurchaseorderdtl e left outer join tblgrndtl f  on f.strCode=e.strPOCode "
				+ " WHERE a.strRateContNo=b.strRateContNo AND b.strProductCode=c.strProdCode "
				+ " and f.strCode='"+poCode+"'and a.strSuppCode='"+strSuppCode+"' AND b.strProductCode='"+prodCode+"' and DATE(a.dtFromDate) <='"+billDate+"'  AND DATE(a.dtToDate) >='"+billDate+"'  "
				+ " group by c.strProdCode";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");

		if (list.size() > 0) {
			Object[] ob = (Object[]) list.get(0);
			ProdList=new ArrayList<>();
			ProdList.add(ob[0].toString());
			ProdList.add(ob[1].toString());
			ProdList.add(Double.parseDouble(ob[2].toString()));
			ProdList.add(ob[4].toString());
			ProdList.add(ob[5].toString());
			ProdList.add(ob[6].toString());
			ProdList.add(ob[7].toString());
			ProdList.add(ob[8].toString());
			ProdList.add(ob[9].toString());
			ProdList.add(Double.parseDouble(ob[10].toString()));
			ProdList.add(ob[11].toString());
			ProdList.add("Contract Rate Amt");
		} 
		
		return ProdList;
	}
}
