package com.sanguine.crm.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.sanguine.controller.clsJVGeneratorController;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsInvoiceDtlBean;
import com.sanguine.crm.bean.clsInvoiceProdDtlReportBean;
import com.sanguine.crm.bean.clsInvoiceTaxDtlBean;
import com.sanguine.crm.bean.clsInvoiceTaxGSTBean;
import com.sanguine.crm.bean.clsInvoiceTextBean;
import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanHdModel_ID;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;
import com.sanguine.crm.model.clsInvSalesOrderDtl;
import com.sanguine.crm.model.clsInvSettlementdtlModel;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceHdModel_ID;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceProdTaxDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsDeliveryChallanHdService;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.crm.service.clsSalesOrderService;
import com.sanguine.model.clsAuditDtlModel;
import com.sanguine.model.clsAuditHdModel;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsProductReOrderLevelModel_ID;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.model.clsStkAdjustmentDtlModel;
import com.sanguine.model.clsStkAdjustmentHdModel;
import com.sanguine.model.clsStkAdjustmentHdModel_ID;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsUserDtlModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsDelTransService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStkAdjustmentService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsNumberToWords;
import com.sanguine.util.clsReportBean;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsInvoiceController
{

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsSalesOrderService objSalesOrderService;

	@Autowired
	private clsPartyMasterService objPartyMasterService;

	@Autowired
	private clsDeliveryChallanHdService objDeliveryChallanHdService;

	@Autowired
	private clsSubGroupMasterService objSubGroupService;

	@Autowired
	private clsCRMSettlementMasterService objSettlementService;

	@Autowired
	private clsCommercialTaxInnvoiceController objCommercialTaxInnvoiceController;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	private clsDelTransService objDeleteTranServerice;

	@Autowired
	private clsStkAdjustmentService objStkAdjService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsCRMSettlementMasterService objSttlementMasterService;

	@Autowired
	private clsLinkUpService objLinkupService;

	@Autowired
	private intfBaseService objBaseService;

	@Autowired
	clsJVGeneratorController objJVGen;

	List<clsSubGroupMasterModel> dataSG = new ArrayList<clsSubGroupMasterModel>();

	@RequestMapping(value = "/AutoCompletGetSubgroupNameForInv", method = RequestMethod.POST)
	public @ResponseBody Set<clsSubGroupMasterModel> getSubgroupNames(@RequestParam String term, HttpServletResponse response)
	{
		return simulateSubgroupNameSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<clsSubGroupMasterModel> simulateSubgroupNameSearchResult(String sgName)
	{
		Set<clsSubGroupMasterModel> result = new HashSet<clsSubGroupMasterModel>();
		// iterate a list and filter by ProductName
		for (clsSubGroupMasterModel sg : dataSG)
		{
			if (sg.getStrSGName().contains((sgName.toUpperCase())))
			{
				result.add(sg);
			}
		}

		return result;
	}

	@RequestMapping(value = "/frmInovice", method = RequestMethod.GET)
	public ModelAndView funInvoice(Map<String, Object> model, HttpServletRequest request)
	{
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		dataSG = new ArrayList<clsSubGroupMasterModel>();
		@SuppressWarnings("rawtypes")
		List list = objSubGroupService.funGetList();
		for (Object objSG : list)
		{
			clsSubGroupMasterModel sgModel = (clsSubGroupMasterModel) objSG;
			dataSG.add(sgModel);
		}

		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}

		HashMap<String, clsUserDtlModel> hmUserPrivileges = (HashMap) request.getSession().getAttribute("hmUserPrivileges");
		model.put("urlHits", urlHits);

		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
//		strAgainst.add("Delivery Challan");
		strAgainst.add("Sales Order");
		model.put("againstList", strAgainst);

		Map<String, String> settlementList = objSettlementService.funGetSettlementComboBox(clientCode);
		model.put("settlementList", settlementList);

		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);
		
		String authorizationInvoiceCode = "";
		boolean flagOpenFromAuthorization = true;
		try
		{
			authorizationInvoiceCode = request.getParameter("authorizationInvoiceCode").toString();
		}
		catch (NullPointerException e)
		{
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization)
		{
			model.put("authorizationInvoiceCode", authorizationInvoiceCode);
		}

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmInovice_1", "command", new clsInvoiceBean());
		}
		else
		{
			return new ModelAndView("frmInovice", "command", new clsInvoiceBean());
		}
	}

	// Save or Update Invoice
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/saveInvoice", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsInvoiceBean objBean, BindingResult result, HttpServletRequest req)
	{
		boolean flgHdSave = false;
		String urlHits = "1";
		try
		{
			urlHits = req.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		// List<clsInvoiceGSTModel> listGST=new ArrayList<clsInvoiceGSTModel>();
		if (!result.hasErrors())
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			double dblCurrencyConv = 1.0;

			Date today = Calendar.getInstance().getTime();
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			String reportDate = df.format(today);
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);

			clsInvoiceHdModel objHDModel = new clsInvoiceHdModel();
			objHDModel.setStrUserModified(userCode);
			objHDModel.setDteLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHDModel.setDteInvDate(objBean.getDteInvDate() + " " + reportDate);
			objHDModel.setStrAgainst(objBean.getStrAgainst());
			objHDModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
			if (objBean.getStrAgainst().equalsIgnoreCase("Sales Order"))
			{
				objHDModel.setStrCustCode("");
			}
			else
			{
				objHDModel.setStrCustCode(objBean.getStrCustCode());
			}
			objHDModel.setStrInvNo("");
			objHDModel.setStrDktNo(objBean.getStrDktNo());
			objHDModel.setStrLocCode(objBean.getStrLocCode());
			objHDModel.setStrMInBy(objBean.getStrMInBy());
			objHDModel.setStrNarration(objBean.getStrNarration());
			objHDModel.setStrPackNo(objBean.getStrPackNo());
			objHDModel.setStrPONo(objBean.getStrPONo());
			objHDModel.setStrReaCode(objBean.getStrReaCode());
			objHDModel.setStrSAdd1(objBean.getStrSAdd1());
			objHDModel.setStrSAdd2(objBean.getStrSAdd2());
			objHDModel.setStrSCity(objBean.getStrSCity());
			objHDModel.setStrSCtry(objBean.getStrSCtry());
			objHDModel.setStrSerialNo(objBean.getStrSerialNo());

			objHDModel.setStrSPin(objBean.getStrSPin());
			objHDModel.setStrSState(objBean.getStrSState());
			objHDModel.setStrTimeInOut(objBean.getStrTimeInOut());
			objHDModel.setStrVehNo(objBean.getStrVehNo());
			objHDModel.setStrWarraValidity(objBean.getStrWarraValidity());
			objHDModel.setStrWarrPeriod(objBean.getStrWarrPeriod());
			objHDModel.setDblSubTotalAmt(0.0);
			objHDModel.setStrSOCode(objBean.getStrSOCode());
			objHDModel.setStrSettlementCode(objBean.getStrSettlementCode());
			objHDModel.setStrUserCreated(userCode);
			objHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHDModel.setStrClientCode(clientCode);

			objHDModel.setStrMobileNo(objBean.getStrMobileNoForSettlement());
			double taxamt = 0.0;

			if (objBean.getDblTaxAmt() != null)
			{
				taxamt = objBean.getDblTaxAmt();
			}
			objHDModel.setDblTotalAmt(0.0);
			objHDModel.setDblTaxAmt(0.0);

			objHDModel.setStrCurrencyCode(objBean.getStrCurrencyCode());
			/*clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(objHDModel.getStrCurrencyCode(), clientCode);
			if (objModel == null)
			{
				// dblCurrencyConv=1;
				objHDModel.setDblCurrencyConv(dblCurrencyConv);
			}
			else
			{
				dblCurrencyConv = objModel.getDblConvToBaseCurr();
				objHDModel.setDblCurrencyConv(objModel.getDblConvToBaseCurr());
			}*/
			
			dblCurrencyConv = objBean.getDblCurrencyConv();
			if(dblCurrencyConv ==0){
				dblCurrencyConv=1.0;
			}
			objHDModel.setDblCurrencyConv(dblCurrencyConv);
			objHDModel.setStrDeliveryNote(objBean.getStrDeliveryNote());
			objHDModel.setStrSupplierRef(objBean.getStrSupplierRef());
			objHDModel.setStrOtherRef(objBean.getStrOtherRef());
			objHDModel.setStrBuyersOrderNo(objBean.getStrBuyersOrderNo());
			objHDModel.setDteBuyerOrderNoDated(objBean.getDteBuyerOrderNoDated());
			objHDModel.setStrDispatchDocNo(objBean.getStrDispatchDocNo());
			objHDModel.setDteDispatchDocNoDated(objBean.getDteDispatchDocNoDated());
			objHDModel.setStrDispatchThrough(objBean.getStrDispatchThrough());
			objHDModel.setStrDestination(objBean.getStrDestination());
			objHDModel.setDblExtraCharges(objBean.getDblExtraCharges());

			// /********Save Data forDetail in SO***********////

			StringBuilder sqlQuery = new StringBuilder();
			int decimalPlaces = Integer.parseInt(req.getSession().getAttribute("amtDecPlace").toString());
			String pattern = "";
			if (decimalPlaces == 1)
			{
				pattern = "#.#";
			}
			else if (decimalPlaces == 2)
			{
				pattern = "#.##";
			}
			else if (decimalPlaces == 3)
			{
				pattern = "#.###";
			}
			else if (decimalPlaces == 4)
			{
				pattern = "#.####";
			}
			else if (decimalPlaces == 5)
			{
				pattern = "#.#####";
			}
			else if (decimalPlaces == 6)
			{
				pattern = "#.######";
			}
			else if (decimalPlaces == 7)
			{
				pattern = "#.#######";
			}
			else if (decimalPlaces == 8)
			{
				pattern = "#.########";
			}
			else if (decimalPlaces == 9)
			{
				pattern = "#.#########";
			}
			else if (decimalPlaces == 10)
			{
				pattern = "#.##########";
			}

			DecimalFormat decFormat = new DecimalFormat(pattern);

			List<clsInvoiceModelDtl> listInvDtlModel = null;
			Map<String, List<clsInvoiceModelDtl>> hmInvCustDtl = new HashMap<String, List<clsInvoiceModelDtl>>();
			Map<String, Map<String, clsInvoiceTaxDtlModel>> hmInvCustTaxDtl = new HashMap<String, Map<String, clsInvoiceTaxDtlModel>>();
			Map<String, List<clsInvoiceProdTaxDtl>> hmInvProdTaxDtl = new HashMap<String, List<clsInvoiceProdTaxDtl>>();
			List<clsInvoiceDtlBean> listInvDtlBean = objBean.getListclsInvoiceModelDtl();

			for (clsInvoiceDtlBean objInvDtl : listInvDtlBean)
			{
				if (!(objInvDtl.getDblQty() == 0))
				{
					List list = objGlobalFunctionsService.funGetList("select strExciseable,strPickMRPForTaxCal from tblproductmaster " + " where strProdCode='" + objInvDtl.getStrProdCode() + "' ", "sql");

					Object[] arrProdDtl = (Object[]) list.get(0);
					String excisable = arrProdDtl[0].toString();
					String pickMRP = arrProdDtl[1].toString();
					String key = objInvDtl.getStrCustCode() + "!" + excisable;

					if (hmInvCustDtl.containsKey(key))
					{
						listInvDtlModel = hmInvCustDtl.get(key);
					}
					else
					{
						listInvDtlModel = new ArrayList<clsInvoiceModelDtl>();
					}

					clsInvoiceModelDtl objInvDtlModel = new clsInvoiceModelDtl();

					clsProductMasterModel objProdTempUom = objProductMasterService.funGetObject(objInvDtl.getStrProdCode(), clientCode);

					objInvDtlModel.setStrProdCode(objInvDtl.getStrProdCode());
					objInvDtlModel.setDblPrice(objInvDtl.getDblUnitPrice() * dblCurrencyConv);
					objInvDtlModel.setDblQty(objInvDtl.getDblQty());
					objInvDtlModel.setDblWeight(objInvDtl.getDblWeight());
					objInvDtlModel.setStrProdType(objInvDtl.getStrProdType());
					objInvDtlModel.setStrPktNo(objInvDtl.getStrPktNo());
					objInvDtlModel.setStrRemarks(objInvDtl.getStrRemarks());
					objInvDtlModel.setIntindex(objInvDtl.getIntindex());
					objInvDtlModel.setStrInvoiceable(objInvDtl.getStrInvoiceable());
					objInvDtlModel.setStrSerialNo(objInvDtl.getStrSerialNo());
					objInvDtlModel.setDblUnitPrice(objInvDtl.getDblUnitPrice() * dblCurrencyConv);
					objInvDtlModel.setDblAssValue(objInvDtl.getDblAssValue() * dblCurrencyConv);
					objInvDtlModel.setDblBillRate(objInvDtl.getDblBillRate() * dblCurrencyConv);
					objInvDtlModel.setStrSOCode(objInvDtl.getStrSOCode());
					objInvDtlModel.setStrCustCode(objInvDtl.getStrCustCode());
					objInvDtlModel.setStrUOM(objProdTempUom.getStrReceivedUOM());
					objInvDtlModel.setDblUOMConversion(objProdTempUom.getDblReceiveConversion());
					objInvDtlModel.setDblProdDiscAmount(objInvDtl.getDblDisAmt());
					List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = null;
					if (hmInvProdTaxDtl.containsKey(key))
					{
						listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
					}
					else
					{
						listInvProdTaxDtl = new ArrayList<clsInvoiceProdTaxDtl>();
					}

					double prodMRP = objInvDtl.getDblUnitPrice() * dblCurrencyConv;
					if (objInvDtl.getDblWeight() > 0)
					{
						prodMRP = prodMRP * objInvDtl.getDblWeight();
					}
					double marginePer = 0;
					double marginAmt = 0;
					double billRate = prodMRP;

					sqlQuery.setLength(0);
					sqlQuery.append("select a.dblMargin,a.strProdCode from tblprodsuppmaster a " + " where a.strSuppCode='" + objInvDtl.getStrCustCode() + "' and a.strProdCode='" + objInvDtl.getStrProdCode() + "' " + " and a.strClientCode='" + clientCode + "' ");
					List listProdMargin = objGlobalFunctionsService.funGetList(sqlQuery.toString(), "sql");
					if (listProdMargin.size() > 0)
					{
						Object[] arrObjProdMargin = (Object[]) listProdMargin.get(0);
						marginePer = Double.parseDouble(arrObjProdMargin[0].toString());
						marginAmt = prodMRP * (marginePer / 100);
						billRate = prodMRP - marginAmt;
					}
					clsInvoiceProdTaxDtl objInvProdTaxDtl = new clsInvoiceProdTaxDtl();
					objInvProdTaxDtl.setStrProdCode(objInvDtl.getStrProdCode());
					objInvProdTaxDtl.setStrCustCode(objInvDtl.getStrCustCode());
					objInvProdTaxDtl.setStrDocNo("Margin");
					objInvProdTaxDtl.setDblValue(marginAmt);
					objInvProdTaxDtl.setDblTaxableAmt(0);
					listInvProdTaxDtl.add(objInvProdTaxDtl);

					sqlQuery.setLength(0);
					sqlQuery.append("select a.dblDiscount from tblpartymaster a " + " where a.strPCode='" + objInvDtl.getStrCustCode() + "' and a.strPType='Cust' ");
					List listproddiscount = objGlobalFunctionsService.funGetList(sqlQuery.toString(), "sql");
					Object objproddiscount = (Object) listproddiscount.get(0);
					double discPer = Double.parseDouble(objproddiscount.toString());
					double discAmt = billRate * (discPer / 100) * dblCurrencyConv;
					billRate = billRate - discAmt;
					System.out.println(billRate);
					objInvDtlModel.setDblBillRate(billRate);

					objInvProdTaxDtl = new clsInvoiceProdTaxDtl();
					objInvProdTaxDtl.setStrProdCode(objInvDtl.getStrProdCode());
					objInvProdTaxDtl.setStrCustCode(objInvDtl.getStrCustCode());
					objInvProdTaxDtl.setStrDocNo("Disc");
					objInvProdTaxDtl.setDblValue(discAmt);
					objInvProdTaxDtl.setDblTaxableAmt(0);
					listInvProdTaxDtl.add(objInvProdTaxDtl);

					double prodRateForTaxCal = objInvDtl.getDblUnitPrice() * dblCurrencyConv;
					/*if (objInvDtl.getDblWeight() > 0)
					{
						prodRateForTaxCal = objInvDtl.getDblUnitPrice() * objInvDtl.getDblWeight() * dblCurrencyConv;
					}*/
					String prodTaxDtl = objInvDtl.getStrProdCode() + "," + prodRateForTaxCal + "," + objInvDtl.getStrCustCode() + "," + objInvDtl.getDblQty() + ",0";
					Map<String, String> hmProdTaxDtl = objGlobalFunctions.funCalculateTax(prodTaxDtl, "Sales", objBean.getDteInvDate(), "0", req);
					System.out.println("Map Size= " + hmProdTaxDtl.size());

					Map<String, clsInvoiceTaxDtlModel> hmInvTaxDtl = new HashMap<String, clsInvoiceTaxDtlModel>();
					if (hmInvCustTaxDtl.containsKey(key))
					{
						hmInvTaxDtl = hmInvCustTaxDtl.get(key);
					}
					else
					{
						hmInvTaxDtl.clear();
					}

					for (Map.Entry<String, String> entry : hmProdTaxDtl.entrySet())
					{
						clsInvoiceTaxDtlModel objInvTaxModel = null;

						// 137.2#T0000011#Vat 12.5#NA#12.5#15.244444444444444
						// taxable amt,Tax code,tax desc,tax type,tax per

						String taxDtl = entry.getValue();
						String taxCode = entry.getKey();
						double taxableAmt = Double.parseDouble(taxDtl.split("#")[0]);
						double taxAmt = Double.parseDouble(taxDtl.split("#")[5]);
						String shortName = taxDtl.split("#")[6];

						double taxAmtForSingleQty = taxAmt / objInvDtl.getDblQty();
						if (!pattern.trim().isEmpty())
						{
							taxAmtForSingleQty = Double.parseDouble(decFormat.format(taxAmtForSingleQty));
						}
						taxAmt = taxAmtForSingleQty * objInvDtl.getDblQty();

						// For Check it is Correct Or not
						// double
						// taxAmt=Math.round(Double.parseDouble(taxDtl.split("#")[5]));

						if (hmInvTaxDtl.containsKey(entry.getKey()))
						{
							objInvTaxModel = hmInvTaxDtl.get(entry.getKey());
							objInvTaxModel.setDblTaxableAmt(objInvTaxModel.getDblTaxableAmt() + taxableAmt);
							objInvTaxModel.setDblTaxAmt(objInvTaxModel.getDblTaxAmt() + taxAmt);
						}
						else
						{
							objInvTaxModel = new clsInvoiceTaxDtlModel();
							objInvTaxModel.setStrTaxCode(taxDtl.split("#")[1]);
							objInvTaxModel.setDblTaxAmt(taxAmt);
							objInvTaxModel.setDblTaxableAmt(taxableAmt);
							objInvTaxModel.setStrTaxDesc(taxDtl.split("#")[2]);
						}

						if (null != objInvTaxModel)
						{
							hmInvTaxDtl.put(taxCode, objInvTaxModel);
						}

						objInvProdTaxDtl = new clsInvoiceProdTaxDtl();
						objInvProdTaxDtl.setStrProdCode(objInvDtl.getStrProdCode());
						objInvProdTaxDtl.setStrCustCode(objInvDtl.getStrCustCode());
						objInvProdTaxDtl.setStrDocNo(taxDtl.split("#")[1]);
						objInvProdTaxDtl.setDblValue(taxAmt);
						objInvProdTaxDtl.setDblTaxableAmt(taxableAmt);
						listInvProdTaxDtl.add(objInvProdTaxDtl);
					}

					hmInvCustTaxDtl.put(key, hmInvTaxDtl);
					hmInvProdTaxDtl.put(key, listInvProdTaxDtl);

					boolean flgProdFound = false;
					double taxtotal = 0;
					for (Map.Entry<String, List<clsInvoiceProdTaxDtl>> entryTaxTemp : hmInvProdTaxDtl.entrySet())
					{
						if (!flgProdFound)
						{
							List<clsInvoiceProdTaxDtl> listProdTaxDtl = entryTaxTemp.getValue();
							for (clsInvoiceProdTaxDtl objProdTaxDtl : listInvProdTaxDtl)
							{
								if (objProdTaxDtl.getStrProdCode().equals(objInvDtlModel.getStrProdCode()))
								{
									if (!objProdTaxDtl.getStrDocNo().equals("Margin"))
									{
										if (!objProdTaxDtl.getStrDocNo().equals("Disc"))
										{
											taxtotal += objProdTaxDtl.getDblValue();
											flgProdFound = true;
										}
									}
								}
							}
						}
					}

					double totalMarginAmt = marginAmt * objInvDtlModel.getDblQty();
					double totalDiscAmt = discAmt * objInvDtlModel.getDblQty();
					double assesableRateForSingleQty = (prodMRP) - (totalMarginAmt + totalDiscAmt + taxtotal);
					double assesableRate = (prodMRP * objInvDtlModel.getDblQty()) - (totalMarginAmt + totalDiscAmt + taxtotal);
					if (assesableRate < 0)
					{
						assesableRate = 0;
					}

					double assableUnitRate = (assesableRate / objInvDtlModel.getDblQty());
					if (!pattern.trim().isEmpty())
					{
						assableUnitRate = Double.parseDouble(decFormat.format(assableUnitRate));
					}

					objInvDtlModel.setDblAssValue(assableUnitRate * objInvDtlModel.getDblQty());
					// objInvDtlModel.setDblAssValue(assesableRate);
					listInvDtlModel.add(objInvDtlModel);
					// hmInvCustDtl.put(objInvDtl.getStrCustCode(),listInvDtlModel);
					hmInvCustDtl.put(key, listInvDtlModel);
					System.out.println(hmInvTaxDtl);
				}
			}

			StringBuilder arrInvCode = new StringBuilder();
			StringBuilder arrDcCode = new StringBuilder();
			for (Map.Entry<String, List<clsInvoiceModelDtl>> entry : hmInvCustDtl.entrySet())
			{
				double qty = 0.0;
				double weight = 0.0;
				List<clsInvoiceModelDtl> listInvoiceDtlModel = hmInvCustDtl.get(entry.getKey());

				if (objBean.getStrInvCode().isEmpty()) // New Entry
				{
					String[] invDate = objHDModel.getDteInvDate().split("-");
					String dateInvoice = invDate[2] + "-" + invDate[1] + "-" + invDate[0];
					String invCode = objGlobalFunctions.funGenerateDocumentCode("frmInvoice", dateInvoice, req);
					objHDModel.setStrInvCode(invCode);
					objHDModel.setStrUserCreated(userCode);
					objHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					objHDModel.setStrDulpicateFlag("N");
				}
				else // Update
				{
					objHDModel.setStrUserCreated(userCode);
					objHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					objHDModel.setStrInvCode(objBean.getStrInvCode());
					objHDModel.setStrDulpicateFlag("Y");
				}
				String custCode = entry.getKey().substring(0, entry.getKey().length() - 2);
				String exciseable = entry.getKey().substring(entry.getKey().length() - 1, entry.getKey().length());

				objHDModel.setStrExciseable(exciseable);
				objHDModel.setStrCustCode(custCode);
				objHDModel.setListInvDtlModel(listInvoiceDtlModel);

				double subTotal = 0, taxAmt = 0, totalAmt = 0, totalExcisableAmt = 0;
				for (clsInvoiceModelDtl objInvItemDtl : listInvoiceDtlModel)
				{
					List list = objGlobalFunctionsService.funGetList("select strExciseable,strPickMRPForTaxCal,dblMRP from tblproductmaster " + " where strProdCode='" + objInvItemDtl.getStrProdCode() + "' ", "sql");
					// String excisable=list.get(0).toString();
					Object[] arrProdDtl = (Object[]) list.get(0);
					String excisable = arrProdDtl[0].toString();
					String pickMRP = arrProdDtl[1].toString();
					double dblMrp = Double.parseDouble(arrProdDtl[2].toString()) * dblCurrencyConv;
					String key = custCode + "!" + excisable;
					if (pickMRP.equals("Y"))
					{
						List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
						for (clsInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl)
						{
							if (objInvItemDtl.getStrProdCode().equals(objInvTaxModel.getStrProdCode()))
							{
								if (objInvTaxModel.getStrCustCode().equals(custCode) && objInvTaxModel.getStrProdCode().equals(objInvItemDtl.getStrProdCode()) && !((objInvTaxModel.getStrDocNo().equals("Disc")) || (objInvTaxModel.getStrDocNo().equals("Margin"))))
								{
									String taxCode = objInvTaxModel.getStrDocNo();
									List listAbtment = objGlobalFunctionsService.funGetList("select a.dblAbatement,strExcisable " + " from tbltaxhd a where a.strTaxCode='" + taxCode + "' and a.strClientCode='" + clientCode + "' ", "sql");
									Object[] arrObjExc = (Object[]) listAbtment.get(0);
									double dblAbtmt = Double.parseDouble(arrObjExc[0].toString());
									String excisableTax = arrObjExc[1].toString();

									if (dblAbtmt > 0)
									{
										totalAmt += (objInvItemDtl.getDblQty() * dblMrp) * dblAbtmt / 100;

										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y"))
										{
											totalExcisableAmt += objInvItemDtl.getDblAssValue();
										}
									}
									else
									{
										totalAmt += objInvItemDtl.getDblAssValue();
										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y"))
										{
											totalExcisableAmt += objInvItemDtl.getDblAssValue();
										}
									}
								}
							}
						}
					}
					else
					{

						if (true)
						{
							totalAmt += objInvItemDtl.getDblUnitPrice() * objInvItemDtl.getDblQty();
						}
						else
						{
							totalAmt += objInvItemDtl.getDblAssValue();
						}

						List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
						for (clsInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl)
						{
							if (objInvItemDtl.getStrProdCode().equals(objInvTaxModel.getStrProdCode()))
							{
								if (objInvTaxModel.getStrCustCode().equals(custCode) && objInvTaxModel.getStrProdCode().equals(objInvItemDtl.getStrProdCode()) && !((objInvTaxModel.getStrDocNo().equals("Disc")) || (objInvTaxModel.getStrDocNo().equals("Margin"))))
								{
									String taxCode = objInvTaxModel.getStrDocNo();
									List listExcTax = objGlobalFunctionsService.funGetList("select a.strExcisable from tbltaxhd a where a.strTaxCode='" + taxCode + "' and a.strClientCode='" + clientCode + "' ", "sql");

									if (listExcTax.size() > 0)
									{
										String excisableTax = listExcTax.get(0).toString();
										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y"))
										{
											totalExcisableAmt += objInvItemDtl.getDblAssValue();
										}
									}
								}
							}
						}
					}
				}

				double excisableTaxAmt = 0;
				List<clsInvoiceTaxDtlModel> listInvoiceTaxDtl = new ArrayList<clsInvoiceTaxDtlModel>();
				Map<String, clsInvoiceTaxDtlModel> hmInvTaxDtlTemp = hmInvCustTaxDtl.get(entry.getKey());
				for (Map.Entry<String, clsInvoiceTaxDtlModel> entryTaxDtl : hmInvTaxDtlTemp.entrySet())
				{
					listInvoiceTaxDtl.add(entryTaxDtl.getValue());
					taxAmt += entryTaxDtl.getValue().getDblTaxAmt();

					String sqlTaxDtl = "select strExcisable from tbltaxhd " + " where strTaxCode='" + entryTaxDtl.getValue().getStrTaxCode() + "' ";
					List list = objGlobalFunctionsService.funGetList(sqlTaxDtl, "sql");
					if (list.size() > 0)
					{
						for (int cntExTax = 0; cntExTax < list.size(); cntExTax++)
						{
							String excisable = list.get(cntExTax).toString();
							if (excisable.equalsIgnoreCase("Y"))
							{
								excisableTaxAmt += entryTaxDtl.getValue().getDblTaxAmt();
							}
						}
					}
				}

				double grandTotal = totalAmt + taxAmt+objHDModel.getDblExtraCharges();
				subTotal = totalAmt + excisableTaxAmt;

				if (exciseable.equalsIgnoreCase("Y"))
				{
					subTotal = totalExcisableAmt + excisableTaxAmt;
					grandTotal = totalExcisableAmt + taxAmt;
					objHDModel.setDblTotalAmt(totalExcisableAmt);
				}
				else
				{
					objHDModel.setDblTotalAmt(totalAmt);
				}
				objHDModel.setDblSubTotalAmt(subTotal);
				objHDModel.setDblTaxAmt(taxAmt);
				objHDModel.setDblGrandTotal(grandTotal);

				List<clsInvSalesOrderDtl> listInvSODtl = new ArrayList<clsInvSalesOrderDtl>();
				String[] arrSOCodes = objHDModel.getStrSOCode().split(",");
				for (int cn = 0; cn < arrSOCodes.length; cn++)
				{
					clsInvSalesOrderDtl objInvSODtl = new clsInvSalesOrderDtl();
					objInvSODtl.setStrSOCode(arrSOCodes[cn]);
					objInvSODtl.setDteInvDate(objHDModel.getDteInvDate());
					listInvSODtl.add(objInvSODtl);
				}
				objHDModel.setListInvSalesOrderModel(listInvSODtl);
				objHDModel.setListInvTaxDtlModel(listInvoiceTaxDtl);
				objHDModel.setListInvProdTaxDtlModel(hmInvProdTaxDtl.get(entry.getKey()));
				objHDModel.setDblDiscountAmt(objBean.getDblDiscountAmt() * dblCurrencyConv);

				objHDModel.setDblDiscount(objBean.getDblDiscount());
				double totalAmount = objHDModel.getDblTotalAmt() - objHDModel.getDblDiscountAmt();
				objHDModel.setDblTotalAmt(totalAmount);
				objHDModel.setDblGrandTotal(objHDModel.getDblGrandTotal() - objHDModel.getDblDiscountAmt());
				objHDModel.setStrCloseIV("N");
				objHDModel.setDblExtraCharges(objBean.getDblExtraCharges());
				objHDModel.setStrJVNo("");
				
				//For Keeping audit of voided/deleted items....	
				objHDModel=funSaveVoidedProductList(objHDModel,objBean,clientCode);

				objInvoiceHdService.funAddUpdateInvoiceHd(objHDModel);
				String dcCode = "";
				if (objSetup.getStrEffectOfInvoice().equals("DC"))
				{
					dcCode = funDataSetInDeliveryChallan(objHDModel, req);
				}
				arrInvCode.append(objHDModel.getStrInvCode() + ",");
				arrDcCode.append(dcCode + ",");
			}

			clsPartyMasterModel objCust = objPartyMasterService.funGetObject(objHDModel.getStrCustCode(), clientCode);
			for (clsInvoiceDtlBean objInvDtl : objBean.getListclsInvoiceModelDtl())
			{
				if(objInvDtl.getStrProdCode()!=null)      //Change due to void invoice
				{
					clsProdSuppMasterModel objProdCustModel = new clsProdSuppMasterModel();
					objProdCustModel.setStrSuppCode(objHDModel.getStrCustCode());
					objProdCustModel.setStrSuppName(objCust.getStrPName());
					objProdCustModel.setStrClientCode(clientCode);
					objProdCustModel.setStrProdCode(objInvDtl.getStrProdCode());
					objProdCustModel.setStrProdName(objInvDtl.getStrProdName());
					objProdCustModel.setDblLastCost(objInvDtl.getDblUnitPrice() * dblCurrencyConv);
					objProdCustModel.setStrDefault("N");
					objProdCustModel.setStrLeadTime("");
					objProdCustModel.setStrSuppPartDesc("");
					objProdCustModel.setStrSuppPartNo("");
					objProdCustModel.setStrSuppUOM("");
					objProdCustModel.setDblMargin(0);
					objProdCustModel.setDblMaxQty(0);
					objProdCustModel.setDblStandingOrder(0);
					objProdCustModel.setDtLastDate(objBean.getDteInvDate());
					objProductMasterService.funAddUpdateProdSupplier(objProdCustModel);
					funUpdatePurchagesPricePropertywise(objInvDtl.getStrProdCode(), objCust.getStrLocCode(), clientCode, objInvDtl.getDblUnitPrice() * dblCurrencyConv);
				}
			}

			if (objCompModel.getStrWebBookModule().equals("Yes"))
			{

				objHDModel.setDteInvDate(objHDModel.getDteInvDate().split(" ")[0]);
				boolean authorisationFlag = false;
				if (null != req.getSession().getAttribute("hmAuthorization"))
				{
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.containsKey("Invoice"))
					{
						authorisationFlag = hmAuthorization.get("Invoice");
					}
				}

				if(!authorisationFlag)
				{
					String retuenVal=objJVGen.funGenrateJVforInvoice(objHDModel.getStrInvCode(), clientCode, userCode, propCode, req);
					String JVGenMessage="";
					String[] arrVal=retuenVal.split("!");
					
					boolean flgJVPosting=true;
					if(arrVal[0].equals("ERROR"))
					{
						JVGenMessage=arrVal[1];
						flgJVPosting=false;
					}
					else
					{
						objHDModel.setStrDktNo(arrVal[0]);
						objHDModel.setStrJVNo(arrVal[0]);
						

						//For Keeping audit of voided/deleted items....	
						objHDModel=funSaveVoidedProductList(objHDModel,objBean,clientCode);

						objInvoiceHdService.funAddUpdateInvoiceHd(objHDModel);
					}
					
					req.getSession().setAttribute("JVGen", flgJVPosting);
					req.getSession().setAttribute("JVGenMessage", JVGenMessage);
					
				}

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
				req.getSession().setAttribute("rptInvCode", arrInvCode);
				req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
				req.getSession().setAttribute("rptDcCode", arrDcCode);

				return new ModelAndView("redirect:/frmInovice.html?saddr=" + urlHits);
			}
			else
			{
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
				req.getSession().setAttribute("rptInvCode", arrInvCode);
				req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
				req.getSession().setAttribute("rptDcCode", arrDcCode);
			}
			return new ModelAndView("redirect:/frmInovice.html?saddr=" + urlHits);

		}
		else
		{
			return new ModelAndView("frmInovice?saddr=" + urlHits, "command", new clsInvoiceBean());
		}
	}

	// Convert bean to model function
	@SuppressWarnings("unused")
	private clsInvoiceHdModel funPrepareHdModel(clsInvoiceBean objBean, String userCode, String clientCode, HttpServletRequest req)
	{

		long lastNo = 0;
		clsInvoiceHdModel objModel;
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsInvoiceHdModel INVHDModel;

		if (objBean.getStrInvCode().trim().length() == 0)
		{

			lastNo = objGlobalFunctionsService.funGetLastNo("tblinvoicehd", "InvCode", "intId", clientCode);

			String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
			String cd = objGlobalFunctions.funGetTransactionCode("IV", propCode, year);
			String strInvCode = cd + String.format("%06d", lastNo);

			INVHDModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(strInvCode, clientCode));
			INVHDModel.setStrInvCode(strInvCode);
			INVHDModel.setStrUserCreated(userCode);
			INVHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			INVHDModel.setIntid(lastNo);

		}

		else
		{
			clsInvoiceHdModel objDCModel = objInvoiceHdService.funGetInvoiceHd(objBean.getStrInvCode(), clientCode);
			if (null == objDCModel)
			{
				lastNo = objGlobalFunctionsService.funGetLastNo("tblinvoicehd", "InvCode", "intId", clientCode);
				String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
				String cd = objGlobalFunctions.funGetTransactionCode("IV", propCode, year);
				String strDCCode = cd + String.format("%06d", lastNo);
				INVHDModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(strDCCode, clientCode));
				INVHDModel.setIntid(lastNo);
				INVHDModel.setStrUserCreated(userCode);
				// INVHDModel.setStrPropertyCode(propCode);
				INVHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			}
			else
			{
				INVHDModel = new clsInvoiceHdModel(new clsInvoiceHdModel_ID(objBean.getStrInvCode(), clientCode));
				// objModel.setStrPropertyCode(propCode);
			}
		}
		String[] InvDate = objBean.getDteInvDate().split("/");
		String date = InvDate[2] + "-" + InvDate[0] + "-" + InvDate[1];
		Date today = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String reportDate = df.format(today);

		INVHDModel.setStrUserModified(userCode);
		INVHDModel.setDteLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		INVHDModel.setDteInvDate(date + " " + reportDate);
		INVHDModel.setStrAgainst(objBean.getStrAgainst());
		INVHDModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
		INVHDModel.setStrCustCode(objBean.getStrCustCode());
		INVHDModel.setStrInvNo("");
		INVHDModel.setStrDktNo(objBean.getStrDktNo());
		INVHDModel.setStrLocCode(objBean.getStrLocCode());
		INVHDModel.setStrMInBy(objBean.getStrMInBy());
		INVHDModel.setStrNarration(objBean.getStrNarration());
		INVHDModel.setStrPackNo(objBean.getStrPackNo());
		INVHDModel.setStrPONo(objBean.getStrPONo());
		INVHDModel.setStrReaCode(objBean.getStrReaCode());
		INVHDModel.setStrSAdd1(objBean.getStrSAdd1());
		INVHDModel.setStrSAdd2(objBean.getStrSAdd2());
		INVHDModel.setStrSCity(objBean.getStrSCity());
		INVHDModel.setStrSCtry(objBean.getStrSCtry());
		INVHDModel.setStrSerialNo(objBean.getStrSerialNo());

		if (objBean.getStrSOCode() == null)
		{
			INVHDModel.setStrSOCode("");
		}
		else
		{
			INVHDModel.setStrSOCode(objBean.getStrSOCode());
		}
		INVHDModel.setStrSPin(objBean.getStrSPin());
		INVHDModel.setStrSState(objBean.getStrSState());
		INVHDModel.setStrTimeInOut(objBean.getStrTimeInOut());
		INVHDModel.setStrVehNo(objBean.getStrVehNo());
		INVHDModel.setStrWarraValidity(objBean.getStrWarraValidity());
		INVHDModel.setStrWarrPeriod(objBean.getStrWarrPeriod());
		INVHDModel.setDblSubTotalAmt(objBean.getDblSubTotalAmt());
		double taxamt = 0.0;

		if (objBean.getDblTaxAmt() != null)
		{
			taxamt = objBean.getDblTaxAmt();
		}
		Double amt = objBean.getDblSubTotalAmt() + taxamt;
		INVHDModel.setDblTotalAmt(amt);
		INVHDModel.setDblTaxAmt(taxamt);
		INVHDModel.setDblDiscount(objBean.getDblDiscount());

		double dblDiscountAmt = 0.0;
		if (objBean.getDblDiscount() >= 1)
		{
			dblDiscountAmt = (objBean.getDblSubTotalAmt() * objBean.getDblDiscount()) / 100;
		}
		INVHDModel.setDblDiscountAmt(dblDiscountAmt);
		INVHDModel.setStrCloseIV("N");
		INVHDModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
		INVHDModel.setDblExtraCharges(objBean.getDblExtraCharges());

		return INVHDModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadInvoiceHdData", method = RequestMethod.GET)
	public @ResponseBody clsInvoiceBean funAssignFields(@RequestParam("invCode") String invCode, HttpServletRequest req)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		
		clsInvoiceBean objBeanInv = new clsInvoiceBean();

		List<Object> objDC = objInvoiceHdService.funGetInvoice(invCode, clientCode);
		clsInvoiceHdModel objInvoiceHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++)
		{
			Object[] ob = (Object[]) objDC.get(i);
			objInvoiceHdModel = (clsInvoiceHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanInv = funPrepardHdBean(objInvoiceHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
		double currValue =objBeanInv.getDblCurrencyConv() ;
		
		List<clsInvoiceModelDtl> listDCDtl = new ArrayList<clsInvoiceModelDtl>();
		clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(invCode, clientCode);

		List<clsInvoiceModelDtl> listInvDtlModel = objInvHDModelList.getListInvDtlModel();
		List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();
		for (int i = 0; i < listInvDtlModel.size(); i++)
		{
			clsInvoiceDtlBean objBeanInvoice = new clsInvoiceDtlBean();

			clsInvoiceModelDtl obj = listInvDtlModel.get(i);
			clsProductMasterModel objProdModle = objProductMasterService.funGetObject(obj.getStrProdCode(), clientCode);

			objBeanInvoice.setStrProdCode(obj.getStrProdCode());
			objBeanInvoice.setStrProdName(objProdModle.getStrProdName());
			objBeanInvoice.setStrProdType(obj.getStrProdType());
			objBeanInvoice.setDblQty(obj.getDblQty());
			objBeanInvoice.setDblWeight(obj.getDblWeight());
			objBeanInvoice.setDblUnitPrice(obj.getDblUnitPrice() / currValue);
			objBeanInvoice.setStrPktNo(obj.getStrPktNo());
			objBeanInvoice.setStrRemarks(obj.getStrRemarks());
			objBeanInvoice.setStrInvoiceable(obj.getStrInvoiceable());
			objBeanInvoice.setStrSerialNo(obj.getStrSerialNo());
			objBeanInvoice.setStrCustCode(obj.getStrCustCode());
			objBeanInvoice.setStrSOCode(obj.getStrSOCode());
			objBeanInvoice.setDblDisAmt(obj.getDblProdDiscAmount()/ currValue);
			String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode " + " and a.strCustCode='" + objBeanInv.getStrCustName() + "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode  " + " and a.strCustCode='" + objBeanInv.getStrCustCode() + "')";

			List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");

			if (listPrevInvData.size() > 0)
			{
				Object objInv[] = (Object[]) listPrevInvData.get(0);
				objBeanInvoice.setPrevUnitPrice(Double.parseDouble(objInv[1].toString()) / currValue);
				objBeanInvoice.setPrevInvCode(objInv[2].toString());

			}
			else
			{
				objBeanInvoice.setPrevUnitPrice(0.0);
				objBeanInvoice.setPrevInvCode(" ");
			}

			listInvDtlBean.add(objBeanInvoice);

		}
		objBeanInv.setListclsInvoiceModelDtl(listInvDtlBean);

		String sql = "select strTaxCode,strTaxDesc,dblTaxableAmt,dblTaxAmt from tblinvtaxdtl " + "where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		List<clsInvoiceTaxDtlBean> listInvTaxDtl = new ArrayList<clsInvoiceTaxDtlBean>();
		for (int cnt = 0; cnt < list.size(); cnt++)
		{
			clsInvoiceTaxDtlBean objTaxDtl = new clsInvoiceTaxDtlBean();
			Object[] arrObj = (Object[]) list.get(cnt);
			objTaxDtl.setStrTaxCode(arrObj[0].toString());
			objTaxDtl.setStrTaxDesc(arrObj[1].toString());
			objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString())/ currValue);
			objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString())/ currValue);

			listInvTaxDtl.add(objTaxDtl);
		}
		objBeanInv.setListInvoiceTaxDtl(listInvTaxDtl);
		return objBeanInv;
	}

	private clsInvoiceBean funPrepardHdBean(clsInvoiceHdModel objInvHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel)
	{
		clsInvoiceBean objBean = new clsInvoiceBean();
		String[] date = objInvHdModel.getDteInvDate().split(" ");
		// String [] dateTime=date[2].split(" ");

		// String date1 = date[1]+"/"+dateTime[0]+"/"+date[0];
		double  currValue=objInvHdModel.getDblCurrencyConv();
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
		objBean.setDblSubTotalAmt(objInvHdModel.getDblSubTotalAmt() / currValue);
		objBean.setDblTaxAmt(objInvHdModel.getDblTaxAmt() / currValue);
		objBean.setDblTotalAmt(objInvHdModel.getDblTotalAmt() / currValue);
		objBean.setDblDiscountAmt(objInvHdModel.getDblDiscountAmt() / currValue);
		objBean.setDblDiscount(objInvHdModel.getDblDiscount());
		objBean.setStrSettlementCode(objInvHdModel.getStrSettlementCode());
		objBean.setStrAuthorize(objInvHdModel.getStrAuthorise());

		objBean.setStrDeliveryNote(objInvHdModel.getStrDeliveryNote());
		objBean.setStrSupplierRef(objInvHdModel.getStrSupplierRef());
		objBean.setStrOtherRef(objInvHdModel.getStrOtherRef());
		objBean.setStrBuyersOrderNo(objInvHdModel.getStrBuyersOrderNo());
		objBean.setDteBuyerOrderNoDated(objInvHdModel.getDteBuyerOrderNoDated());
		objBean.setStrDispatchDocNo(objInvHdModel.getStrDispatchDocNo());
		objBean.setDteDispatchDocNoDated(objInvHdModel.getDteDispatchDocNoDated());
		objBean.setStrDispatchThrough(objInvHdModel.getStrDispatchThrough());
		objBean.setStrDestination(objInvHdModel.getStrDestination());
		objBean.setStrCurrencyCode(objInvHdModel.getStrCurrencyCode());
		objBean.setDblCurrencyConv(objInvHdModel.getDblCurrencyConv());
		objBean.setDblExtraCharges(objInvHdModel.getDblExtraCharges());
		
		return objBean;
	}

	// set Product Detail

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadInvoiceProductDetail", method = RequestMethod.GET)
	public @ResponseBody double funAssignFields(@RequestParam("prodCode") String prodCode, HttpServletRequest req, HttpServletResponse response, ModelMap model)
	{
		double dblBillRate = 0.0;
		// clsInvoiceModelDtl dblBillRate1=new clsInvoiceModelDtl();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strCustCode = req.getParameter("strCustCode").toString();

		double dblDiscount = Double.parseDouble(req.getParameter("discount"));
		List listProdSuppModel = objProductMasterService.funGetProdSuppDtl(prodCode, strCustCode, clientCode);
		if (listProdSuppModel.size() > 0)
		{
			Object[] obj = (Object[]) listProdSuppModel.get(0);
			double dblLastCost = Double.parseDouble(obj[1].toString());
			double dblMargin = Double.parseDouble(obj[2].toString());

			System.out.println(dblLastCost + "  " + dblMargin + " " + dblDiscount);

			double marginVal = dblLastCost - ((dblLastCost * dblMargin) / 100);

			double taxableAmt = marginVal - ((marginVal * dblDiscount) / 100);

			ArrayList<Double> tax = new ArrayList<Double>();

			String taxSql = "select b.dblPercent,b.strTaxOnTax,b.strTaxOnTaxCode   " + " from tblpartytaxdtl a,tbltaxhd b ,tblproductmaster c where c.strProdCode='" + prodCode + "'  " + " and  a.strTaxCode=b.strTaxCode and b.strTaxIndicator=c.strTaxIndicator " + " and a.strPCode='" + strCustCode + "' and b.strTaxIndicator!=''   " + " and a.strClientCode='" + clientCode + "' " // and
																																																																																															// b.strTaxCode
																																																																																															// =d.strTaxCode
					+ " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'  " + " order by  b.strTaxCode ";

			List listTax = objGlobalFunctionsService.funGetList(taxSql, "sql");

			for (int j = 0; j < listTax.size(); j++)
			{

				Object[] objTax = (Object[]) listTax.get(j);
				tax.add(Double.parseDouble(objTax[0].toString()));

				// check Tax On Tax

				if (objTax[1].toString().equalsIgnoreCase("Y"))
				{
					String[] taxonTax = objTax[2].toString().split(",");
					int cntTaxonTax = 0;
					while (taxonTax.length > cntTaxonTax)
					{
						String taxonTaxSql = "select a.dblPercent " + "from tbltaxhd a  where a.strTaxCode='" + taxonTax[cntTaxonTax] + "'   and a.strClientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "' ";

						List listTaxonTax = objGlobalFunctionsService.funGetList(taxonTaxSql, "sql");
						for (int cnt = 0; cnt < listTaxonTax.size(); cnt++)
						{
							Object objTaxonTax = (Object) listTaxonTax.get(cnt);
							tax.add(Double.parseDouble(objTaxonTax.toString()));

						}
						cntTaxonTax++;
					}
				}
			}

			List taxReport = new ArrayList();
			for (int k = 0; k < tax.size(); k++)
			{
				taxableAmt = taxableAmt - ((taxableAmt / (100 + tax.get(k))) * tax.get(k));
				taxReport.add(taxableAmt);
			}
			if (taxReport.size() > 1)
			{
				dblBillRate = Double.parseDouble(taxReport.get(1).toString());
			}
			else
			{
				if (taxReport.size() > 0)
				{
					dblBillRate = Double.parseDouble(taxReport.get(0).toString());
				}
				else
				{
					dblBillRate = taxableAmt;
				}
			}
		}
		else
		{
			dblBillRate = 0.0;
		}
		return dblBillRate;

	}

	@RequestMapping(value = "/frmInvoiceSlip", method = RequestMethod.GET)
	public ModelAndView funInvoiceSlip(Map<String, Object> model, HttpServletRequest request)
	{

		String urlHits = "1";
		try
		{
			urlHits = request.getParameter("saddr").toString();
		}
		catch (NullPointerException e)
		{
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
		strAgainst.add("Delivery Challan");
		strAgainst.add("Sales Order");
		model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits))
		{
			return new ModelAndView("frmInvoiceSlip_1", "command", new clsInvoiceBean());
		}
		else
		{
			return new ModelAndView("frmInvoiceSlip", "command", new clsInvoiceBean());
		}

	}

	@RequestMapping(value = "/rptInvoiceSlip", method = RequestMethod.GET)
	public void funOpenInvoiceReport(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{
			String InvCode = objBean.getStrInvCode();

			String type = "pdf";
			JasperPrint jp = funCallReportInvoiceReport(InvCode, type, resp, req);

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/openRptInvoiceSlip", method = RequestMethod.GET)
	public void funOpenReport(HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{

			String InvCode = req.getParameter("rptInvCode").toString();
			req.getSession().removeAttribute("rptInvCode");
			String type = "pdf";

			String[] arrInvCode = InvCode.split(",");
			req.getSession().removeAttribute("rptInvCode");
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrInvCode.length; cnt++)
			{
				String InvCodeSingle = arrInvCode[cnt].toString();
				JasperPrint jp = funCallReportInvoiceReport(InvCodeSingle, type, resp, req);
				jprintlist.add(jp);
			}

			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
	private JasperPrint funCallReportInvoiceReport(String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
	{

		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		try
		{

			String strInvCode = "";
			String dteInvDate = "";
			String strSOCode = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strNarration = "";
			String strCustPONo = "";
			String dteCPODate = "";
			String dblTotalAmt = "";
			String dblSubTotalAmt = "";
			String strVAT = "";
			String strCST = "";
			String strVehNo = "";
			String dblTaxAmt = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			String strRegNo = "";
			double totalInvVal = 0.0;
			double totalInvoicefrieght = 0.0;
			double dblfeightTaxAmt = 0.0;
			double totalvatInvfright = 0.0;
			double exciseTax = 0.0;
			double exciseTaxAmt1 = 0.0;
			clsNumberToWords obj = new clsNumberToWords();

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sql = "select a.strVAT,a.strCST,a.strRangeAdd,a.strDivision,a.strRegNo from tblpropertysetup a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "'  ";
			List listofvat = objGlobalFunctionsService.funGetList(sql, "sql");

			if (!(listofvat.isEmpty()))
			{
				Object[] arrObj = (Object[]) listofvat.get(0);
				strVAT = arrObj[0].toString();
				strCST = arrObj[1].toString();
				strRangeAdd = arrObj[2].toString();
				strDivision = arrObj[3].toString();
				strRegNo = arrObj[4].toString();

			}
			// String sqlHd
			// ="select
			// a.strInvCode,a.dteInvDate,a.strSOCode,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,"
			// +
			// "b.strSPin,b.strSState,b.strSCountry ,c.strCustPONo,date(c.dteCPODate)
			// ,a.dblSubTotalAmt,a.strVehNo,a.dblTotalAmt,a.dblTaxAmt "
			// +"from tblinvoicehd a,tblpartymaster b,tblsalesorderhd c where
			// a.strInvCode='"+InvCode+"' "
			// +"and a.strCustCode=b.strPCode and a.strClientCode='"+clientCode+"' and
			// a.strClientCode=b.strClientCode ";

			HashMap hm = new HashMap();

			String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo " + " from tblinvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");

			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				strInvCode = arrObj[0].toString();
				dteInvDate = arrObj[1].toString();
				String[] datetime = dteInvDate.split(" ");
				dteInvDate = datetime[0];
				time = datetime[1];

				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
			}
			Map<String, String> vatTaxAmtMap = new HashMap();
			Map<String, Double> vatTaxableAmtMap = new HashMap();
			Map<String, Double> AssValueMap = new HashMap();
			Map<String, clsSubGroupTaxDtl> hmExciseTaxDtl = new HashMap<String, clsSubGroupTaxDtl>();

			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			String timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("And");
			String[] wordmin = words[1].split("Paisa");

			timeInWords = words[0] + "" + wordmin[0] + " HRS";
			time = time + " HRS";

			ArrayList fieldList = new ArrayList();

			/*
			 * String sqlDtl=
			 * " select e.strSGName,sum(b.dblQty) as dblQty ,sum(b.dblAssValue) as dblAssValue,sum(b.dblBillRate) as dblBillRate "
			 * +
			 * " from tblinvoicehd a,tblinvoicedtl b,tblproductmaster d,tblsubgroupmaster e  "
			 * + " where a.strInvCode='"+InvCode+"' and b.strInvCode='"+InvCode+"'  "
			 * +" and b.strProdCode=d.strProdCode and d.strSGCode=e.strSGCode "
			 * +" and a.strClientCode='"+clientCode+"' and b.strClientCode='"+
			 * clientCode+"' and "
			 * +" d.strClientCode='"+clientCode+"' and e.strClientCode='"
			 * +clientCode+"' group by e.strSGCode ";
			 */

			String sqlDtl = "SELECT  d.strSGCode,e.strSGName,a.dblValue,sum(c.dblAssValue),sum(c.dblBillRate),sum(c.dblQty), " + " sum(c.dblBillRate*c.dblQty),sum(c.dblAssValue*c.dblQty) " + " FROM tblinvprodtaxdtl a,tblinvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " WHERE a.strInvCode='" + InvCode + "' AND a.strInvCode=c.strInvCode AND a.strProdCode=c.strProdCode  " + " AND a.strDocNo='Disc' AND d.strProdCode=a.strProdCode AND e.strSGCode=d.strSGCode  " + " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "' and e.strClientCode='" + clientCode + "'  " + " group by d.strSGCode ";

			List listprodDtl = objGlobalFunctionsService.funGetList(sqlDtl, "sql");

			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				String strSGCode = objProdDtl[0].toString();
				String strSGName = objProdDtl[1].toString();
				double discount = Double.parseDouble(objProdDtl[2].toString());
				double dblAssRate = Double.parseDouble(objProdDtl[3].toString());
				double dblBillRate = Double.parseDouble(objProdDtl[4].toString());
				double dblQty = Double.parseDouble(objProdDtl[5].toString());
				double invValue = Double.parseDouble(objProdDtl[6].toString());
				double dblAssValue = Double.parseDouble(objProdDtl[7].toString());

				objInvoiceProdDtlReportBean.setStrSGCode(strSGCode);
				objInvoiceProdDtlReportBean.setStrSGName(strSGName);
				objInvoiceProdDtlReportBean.setDblDiscount(discount);
				objInvoiceProdDtlReportBean.setDblAssRate(dblAssRate);
				objInvoiceProdDtlReportBean.setBillRate(dblBillRate);
				objInvoiceProdDtlReportBean.setDblInvValue(invValue);
				objInvoiceProdDtlReportBean.setDblAssValue(dblAssValue);
				objInvoiceProdDtlReportBean.setDblQty(dblQty);
				totalInvVal = totalInvVal + invValue;

				fieldList.add(objInvoiceProdDtlReportBean);

				// Sub Groupwise Asseable Value and exciseDuty
				if (AssValueMap.containsKey(strSGName))
				{
					double assValue = 0.0;
					assValue = AssValueMap.get(strSGName);
					assValue = assValue + dblAssValue;
					AssValueMap.put(strSGName, assValue);
				}
				else
				{
					AssValueMap.put(strSGName, dblAssValue);
				}
			}

			String sqlTax = " select b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt,b.strTaxCode  from tblinvprodtaxdtl a,tbltaxhd b,tblinvoicedtl c,tblproductmaster d,tblinvtaxdtl e " + " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode  " + " and b.strTaxIndicator=d.strTaxIndicator  and a.strInvCode='" + InvCode + "' and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode ";

			List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
			if (!listprodTax.isEmpty())
			{
				for (int i = 0; i < listprodTax.size(); i++)
				{
					Object[] objProdTax = (Object[]) listprodTax.get(i);
					double dblPercent = Double.parseDouble(objProdTax[1].toString());
					// For Total of Vat Tax

					if (vatTaxAmtMap.containsKey(Double.toString(dblPercent)))
					{
						double taxAmt = 0.0;
						double taxableAmt = 0.0;
						String strTax[] = vatTaxAmtMap.get(Double.toString(dblPercent)).split("!");
						taxAmt = Double.parseDouble(strTax[0]);
						taxAmt = taxAmt + Double.parseDouble(objProdTax[2].toString());
						vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt + "!" + objProdTax[0].toString() + "!" + Double.toString(dblPercent));
						taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
						taxableAmt = taxableAmt + Double.parseDouble(objProdTax[3].toString());
						vatTaxableAmtMap.put(objProdTax[4].toString(), taxableAmt);
					}
					else
					{
						vatTaxAmtMap.put(objProdTax[4].toString(), objProdTax[2].toString() + "!" + objProdTax[0].toString() + "!" + Double.toString(dblPercent));
						vatTaxableAmtMap.put(objProdTax[4].toString(), Double.parseDouble(objProdTax[3].toString()));
					}
				}
			}

			// Excise Tax
			String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName,b.dblPercent,c.strExciseChapter " + " from tblinvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblinvoicedtl e " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y'  " + " and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' " + " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and c.strSGCode=d.strSGCode";

			List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
			if (!listExciseTax.isEmpty())
			{
				for (int j = 0; j < listExciseTax.size(); j++)
				{
					clsSubGroupTaxDtl objSGTaxDtl = null;
					Object[] objExciseTax = (Object[]) listExciseTax.get(j);
					if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString()))
					{
						double dblTaxAmtExcise = Double.parseDouble(objExciseTax[2].toString());
						objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
						objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmtExcise);

					}
					else
					{
						double dblTaxAmtExcise = Double.parseDouble(objExciseTax[2].toString());
						objSGTaxDtl = new clsSubGroupTaxDtl();
						objSGTaxDtl.setTaxAmt(dblTaxAmtExcise);
						objSGTaxDtl.setTaxPer(Double.parseDouble(objExciseTax[4].toString()));
						objSGTaxDtl.setSubGroupChapterNo(objExciseTax[5].toString());
					}

					hmExciseTaxDtl.put(objExciseTax[3].toString(), objSGTaxDtl);
				}
			}

			// Tax pass In report through parameter list
			ArrayList<clsInvoiceProdDtlReportBean> taxList = new ArrayList();
			double totalVatTax = 0.0;
			for (Map.Entry<String, String> entry : vatTaxAmtMap.entrySet())
			{
				double dblvatPer = 0.0;
				double dblTaxableAmt = 0.0;
				clsInvoiceProdDtlReportBean objVatTax = new clsInvoiceProdDtlReportBean();
				String strTaxCode = entry.getKey().toString();

				String[] taxdetial = vatTaxAmtMap.get(entry.getKey()).split("!");
				double taxpercent = Double.parseDouble(taxdetial[2].toString());
				objVatTax.setDblVatTaxPer(taxpercent);
				objVatTax.setDblTaxAmt(Double.parseDouble(taxdetial[0]));
				totalVatTax = totalVatTax + Double.parseDouble(taxdetial[0]);
				objVatTax.setDblTaxableAmt(vatTaxableAmtMap.get(entry.getKey()));
				objVatTax.setStrTaxDesc(taxdetial[1]);
				taxList.add(objVatTax);
			}
			hm.put("VatTaxList", taxList);
			hm.put("totalVatTax", totalVatTax);

			// Groupwise of AssValue and Excise Duty Pass in report
			ArrayList<clsInvoiceProdDtlReportBean> assValueList = new ArrayList();

			int i = 0;
			for (Map.Entry<String, Double> entry : AssValueMap.entrySet())
			{
				clsInvoiceProdDtlReportBean objAssVal = null;
				if (hmExciseTaxDtl.containsKey(entry.getKey()))
				{
					clsSubGroupTaxDtl objSGTaxDtl = hmExciseTaxDtl.get(entry.getKey());
					objAssVal.setDblexciseDuty(objSGTaxDtl.getTaxAmt());
					double totAssValue = entry.getValue();
					double percent = objSGTaxDtl.getTaxPer();
					exciseTaxAmt1 = exciseTaxAmt1 + ((totAssValue * percent) / 100);
					objAssVal.setDblGrpAssValue(entry.getValue());
					objAssVal.setStrSGName(entry.getKey());
					objAssVal.setDblExcisePer(percent);
					objAssVal.setStrExciseChapter(objSGTaxDtl.getSubGroupChapterNo());
					assValueList.add(objAssVal);
				}
				else
				{
					objAssVal = new clsInvoiceProdDtlReportBean();
					objAssVal.setDblexciseDuty(0);
					double totAssValue = entry.getValue();
					exciseTaxAmt1 = exciseTaxAmt1 + ((totAssValue * 0) / 100);
					objAssVal.setDblGrpAssValue(entry.getValue());
					objAssVal.setStrSGName(entry.getKey());
					objAssVal.setDblExcisePer(0);
					objAssVal.setStrExciseChapter("");
					assValueList.add(objAssVal);
				}
			}
			hm.put("assValueList", assValueList);

			// Add Freight Tax

			String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "'";
			List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
			if (!listFreightTax.isEmpty())
			{
				Object[] objfrightTax = (Object[]) listFreightTax.get(0);
				dblfeightTaxAmt = Double.parseDouble(objfrightTax[2].toString());
			}
			hm.put("totalfrieghtTax", dblfeightTaxAmt);

			String exciseSubGrp1 = "";
			String exciseSubGrp2 = "";
			String exciseSubGrp3 = "";

			String exciseChapterNo1 = "";
			String exciseChapterNo2 = "";
			String exciseChapterNo3 = "";

			double exciseDuty1 = 0.0;
			double exciseDuty2 = 0.0;
			double exciseDuty3 = 0.0;

			String exciseDetialSql = "select c.strSGName,a.dblPercent,c.strExciseChapter " + " from tbltaxhd a,tbltaxsubgroupdtl b ,tblsubgroupmaster c " + " where a.strTaxCode=b.strTaxCode and b.strSGCode=c.strSGCode " + " AND a.strTaxOnSubGroup='Y' " + " group by c.strSGCode;";

			List listExciseDetialSql = objGlobalFunctionsService.funGetList(exciseDetialSql, "sql");
			if (!listExciseDetialSql.isEmpty())
			{
				for (int j = 0; j < listExciseDetialSql.size(); j++)
				{
					Object[] objExciseDetialSql = (Object[]) listExciseDetialSql.get(j);

					if (objExciseDetialSql[0].toString().toLowerCase().contains("cake"))
					{
						exciseSubGrp1 = "CAKES & PASTERIES";
						exciseChapterNo1 = objExciseDetialSql[2].toString();
						exciseDuty1 = Double.parseDouble(objExciseDetialSql[1].toString());
					}
					else
					{
						if (objExciseDetialSql[0].toString().toLowerCase().contains("chocolate"))
						{
							exciseSubGrp2 = "CHOCLATE";
							exciseChapterNo2 = objExciseDetialSql[2].toString();
							exciseDuty2 = Double.parseDouble(objExciseDetialSql[1].toString());
						}
						else
						{
							if (objExciseDetialSql[0].toString().toLowerCase().contains("biscuits"))
							{
								exciseSubGrp3 = "BISCUITS";
								exciseChapterNo3 = objExciseDetialSql[2].toString();
								exciseDuty3 = Double.parseDouble(objExciseDetialSql[1].toString());
							}
						}
					}

				}
			}

			totalInvoicefrieght = dblfeightTaxAmt + totalInvVal;
			totalvatInvfright = totalVatTax + totalInvoicefrieght;
			// Double amt=Double.parseDouble(totalInvVal);
			// clsNumberToWords obj=new clsNumberToWords();
			String totalAmt = obj.getNumberInWorld(totalInvVal);
			DecimalFormat df = new DecimalFormat("#.##");
			exciseTaxAmt1 = Double.parseDouble(df.format(exciseTaxAmt1).toString());
			clsNumberToWords obj1 = new clsNumberToWords();
			String excisetaxInWords = obj1.getNumberInWorld(exciseTaxAmt1);

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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strInvCode", strInvCode);
			hm.put("dteInvDate", dteInvDate);
			hm.put("strVAT", strVAT);
			hm.put("strCST", strCST);
			hm.put("totalAmt", totalAmt);
			hm.put("strVehNo", strVehNo);
			hm.put("dblSubTotalAmt", dblSubTotalAmt);
			hm.put("dblTotalAmt", dblTotalAmt);
			hm.put("dblTaxAmt", dblTaxAmt);
			hm.put("time", time);
			hm.put("timeInWords", timeInWords);
			hm.put("strRangeAdd", strRangeAdd);
			hm.put("strDivision", strDivision);
			hm.put("strRangeDiv", objSetup.getStrRangeDiv());
			hm.put("strDivisionAdd", objSetup.getStrDivisionAdd());

			hm.put("strRegNo", strRegNo);
			hm.put("totalInvoicefrieght", totalInvoicefrieght);
			hm.put("totalvatInvfright", totalvatInvfright);
			hm.put("exciseTax", exciseTax);
			hm.put("exciseTaxAmt", exciseTaxAmt1);
			hm.put("excisetaxInWords", excisetaxInWords);
			hm.put("exciseSubGrp1", exciseSubGrp1);
			hm.put("exciseSubGrp2", exciseSubGrp2);
			hm.put("exciseSubGrp3", exciseSubGrp3);
			hm.put("exciseChapterNo1", exciseChapterNo1);
			hm.put("exciseChapterNo2", exciseChapterNo2);
			hm.put("exciseChapterNo3", exciseChapterNo3);
			hm.put("exciseDuty1", exciseDuty1);
			hm.put("exciseDuty2", exciseDuty2);
			hm.put("exciseDuty3", exciseDuty3);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			hm.put("ItemDataSource", beanCollectionDataSource);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		}
		catch (Exception e)
		{
			e.printStackTrace();

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
			return jprintlist;
		}
	}

	@RequestMapping(value = "/openRptInvoiceProductSlip", method = RequestMethod.GET)
	public void funOpenProductReport(HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{

			String InvCode = req.getParameter("rptInvCode").toString();
			String[] arrInvCode = InvCode.split(",");
			String InvDate = req.getParameter("rptInvDate").toString();
			req.getSession().removeAttribute("rptInvCode");
			req.getSession().removeAttribute("rptInvDate");
			String type = "pdf";
			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
			InvDate = myFormat.format(fromUser.parse(InvDate));
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrInvCode.length; cnt++)
			{
				String InvCodeSingle = arrInvCode[cnt].toString();
				JasperPrint jp = funCallReportProductReport(InvCodeSingle, InvDate, type, req, resp);
				jprintlist.add(jp);
			}
			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
	public JasperPrint funCallReportProductReport(String InvCode, String dteInvDate, String type, HttpServletRequest req, HttpServletResponse resp)
	{
		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceProductDtlList.jrxml");
			ArrayList fieldList = new ArrayList();
			HashMap reportParams = new HashMap();
			String prodsql = "select a.strProdCode,d.strProdName , d.strSGCode,e.strSGName, a.dblValue,c.dblAssValue,c.dblBillRate ,c.dblQty,(c.dblBillRate*c.dblQty),(c.dblAssValue*c.dblQty) " + " from tblinvprodtaxdtl a,tblinvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode='" + InvCode + "'  and  a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strDocNo='Disc' " + " and d.strProdCode=a.strProdCode and e.strSGCode=d.strSGCode ";

			reportParams.put("InvCode", InvCode);
			reportParams.put("dteInvDate", dteInvDate);

			Map<String, Double> vatTaxAmtMap = new HashMap();
			Map<String, Double> vatTaxableAmtMap = new HashMap();
			Map<String, Double> AssValueMap = new HashMap();

			List listprodDtl = objGlobalFunctionsService.funGetList(prodsql, "sql");

			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				String strProdCode = objProdDtl[0].toString();
				String strProdName = objProdDtl[1].toString();
				String strSGCode = objProdDtl[2].toString();
				String strSGName = objProdDtl[3].toString();
				double discount = Double.parseDouble(objProdDtl[4].toString());
				double dblAssRate = Double.parseDouble(objProdDtl[5].toString());
				double dblBillRate = Double.parseDouble(objProdDtl[6].toString());
				double dblQty = Double.parseDouble(objProdDtl[7].toString());
				double invValue = Double.parseDouble(objProdDtl[8].toString());
				double dblAssValue = Double.parseDouble(objProdDtl[8].toString());

				objInvoiceProdDtlReportBean.setStrProdCode(strProdCode);
				objInvoiceProdDtlReportBean.setStrProdName(strProdName);
				objInvoiceProdDtlReportBean.setStrSGCode(strSGCode);
				objInvoiceProdDtlReportBean.setStrSGName(strSGName);
				objInvoiceProdDtlReportBean.setDblDiscount(discount);
				objInvoiceProdDtlReportBean.setDblAssRate(dblAssRate);
				objInvoiceProdDtlReportBean.setBillRate(dblBillRate);
				objInvoiceProdDtlReportBean.setDblInvValue(invValue);
				objInvoiceProdDtlReportBean.setDblAssValue(dblAssValue);
				objInvoiceProdDtlReportBean.setDblQty(dblQty);

				// Sub Groupwise Asseable Value and exciseDuty
				if (AssValueMap.containsKey(strSGName))
				{
					double assValue = 0.0;
					assValue = AssValueMap.get(strSGName);
					assValue = assValue + dblAssValue;
					AssValueMap.put(strSGName, assValue);
				}
				else
				{
					AssValueMap.put(strSGName, dblAssValue);
				}

				String sqlTax = " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt " + " from tblinvprodtaxdtl a,tbltaxhd b,tblinvoicedtl c,tblproductmaster d,tblinvtaxdtl e " + " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode " + " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='" + strProdCode + "' and a.strInvCode='" + InvCode + "' " + "and a.strInvCode=e.strInvCode and b.strTaxCode=e.strTaxCode  and b.strTaxIndicator<>'' ";

				List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
				if (!listprodTax.isEmpty())
				{
					for (int cnt = 0; cnt < listprodTax.size(); cnt++)
					{
						Object[] objProdTax = (Object[]) listprodTax.get(cnt);
						double dblPercent = Double.parseDouble(objProdTax[2].toString());
						objInvoiceProdDtlReportBean.setTaxRate(dblPercent);

						// For Total of Vat Tax
						if (vatTaxAmtMap.containsKey(Double.toString(dblPercent)))
						{
							double taxAmt = 0.0;
							double taxableAmt = 0.0;
							taxAmt = (double) vatTaxAmtMap.get(Double.toString(dblPercent));
							taxAmt = taxAmt + Double.parseDouble(objProdTax[3].toString());
							vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt);
							taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
							taxableAmt = taxableAmt + Double.parseDouble(objProdTax[4].toString());
							vatTaxableAmtMap.put(Double.toString(dblPercent), taxableAmt);
						}
						else
						{
							vatTaxAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[3].toString()));
							vatTaxableAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[4].toString()));
						}
					}
				}

				fieldList.add(objInvoiceProdDtlReportBean);
			}

			Map<String, clsSubGroupTaxDtl> hmExciseTaxDtl = new HashMap<String, clsSubGroupTaxDtl>();

			// Excise Tax
			String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName " + " from tblinvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblinvoicedtl e " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' " + " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and c.strSGCode=d.strSGCode";

			List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
			if (!listExciseTax.isEmpty())
			{
				for (int j = 0; j < listExciseTax.size(); j++)
				{
					clsSubGroupTaxDtl objSGTaxDtl = new clsSubGroupTaxDtl();
					Object[] objExciseTax = (Object[]) listExciseTax.get(j);
					if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString()))
					{
						objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
						double dblTaxAmt = Double.parseDouble(objExciseTax[2].toString());
						objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmt);

					}
					else
					{
						objSGTaxDtl.setTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
					}
					hmExciseTaxDtl.put(objExciseTax[3].toString(), objSGTaxDtl);
				}
			}

			// Tax pass In report through parameter list
			ArrayList<clsInvoiceProdDtlReportBean> taxList = new ArrayList();
			double totalVatTax = 0.0;
			for (Map.Entry<String, Double> entry : vatTaxAmtMap.entrySet())
			{
				double dblvatPer = 0.0;
				double dblTaxableAmt = 0.0;
				clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
				double taxpercent = Double.parseDouble(entry.getKey().toString());
				obj.setDblVatTaxPer(taxpercent);
				obj.setDblTaxAmt(vatTaxAmtMap.get(entry.getKey()));
				totalVatTax = totalVatTax + vatTaxAmtMap.get(entry.getKey());
				obj.setDblTaxableAmt(vatTaxableAmtMap.get(entry.getKey()));
				taxList.add(obj);
			}
			reportParams.put("VatTaxList", taxList);
			reportParams.put("totalVatTax", totalVatTax);

			// Total of AssValue Pass in report
			ArrayList<clsInvoiceProdDtlReportBean> assValueList = new ArrayList();
			for (Map.Entry<String, Double> entry : AssValueMap.entrySet())
			{
				clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
				if (hmExciseTaxDtl.containsKey(entry.getKey()))
				{
					obj.setDblexciseDuty(hmExciseTaxDtl.get(entry.getKey()).getTaxAmt());
					obj.setDblGrpAssValue(entry.getValue());
					obj.setStrSGName(entry.getKey());
					assValueList.add(obj);
				}
				else
				{
					obj.setDblexciseDuty(0);
					obj.setDblGrpAssValue(entry.getValue());
					obj.setStrSGName(entry.getKey());
					assValueList.add(obj);
				}
			}
			reportParams.put("assValueList", assValueList);
			// Add Freight Tax
			double dblfeightTaxAmt = 0.0;

			String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "'";
			List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
			if (!listFreightTax.isEmpty())
			{
				Object[] objfrightTax = (Object[]) listFreightTax.get(0);
				dblfeightTaxAmt = Double.parseDouble(objfrightTax[2].toString());
			}
			reportParams.put("dblfeightTaxAmt", dblfeightTaxAmt);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);

			// }

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			return jprintlist;
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptTradingInvoiceSlip", method = RequestMethod.GET)
	public void funOpenTradingInvoiceReport(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		try
		{

			String InvCode = req.getParameter("rptInvCode").toString();
			String[] arrInvCode = InvCode.split(",");
			String InvDate = req.getParameter("rptInvDate").toString();
			req.getSession().removeAttribute("rptInvCode");
			req.getSession().removeAttribute("rptInvDate");
			String type = "pdf";

			SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

			try
			{

				InvDate = myFormat.format(fromUser.parse(InvDate));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrInvCode.length; cnt++)
			{
				String InvCodeSingle = arrInvCode[cnt].toString();
				JasperPrint jp = funCallTradingInvoiceReport(InvCodeSingle, InvDate, resp, req);
				jprintlist.add(jp);
			}

			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked", "finally" })
	public JasperPrint funCallTradingInvoiceReport(String InvCode, String InvDate, HttpServletResponse resp, HttpServletRequest req)
	{

		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		try
		{

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String strInvCode = InvCode;
			String dteInvDate = InvDate;
			String strSOCode = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strNarration = "";
			String strCustPONo = "";
			String dteCPODate = "";
			String dblTotalAmt = "";
			String dblSubTotalAmt = "";

			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptTradingTaxInvoice.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			ArrayList fieldList = new ArrayList();
			HashMap reportParams = new HashMap();
			String prodsql = "select a.strProdCode,d.strProdName , d.strSGCode,e.strSGName, a.dblValue,c.dblAssValue,c.dblBillRate ,c.dblQty,(c.dblBillRate*c.dblQty),(c.dblAssValue*c.dblQty) ,d.strUOM " + " from tblinvprodtaxdtl a,tblinvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode='" + InvCode + "'  and  a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strDocNo='Disc' and d.strProdType='Trading' " + " and d.strProdCode=a.strProdCode and e.strSGCode=d.strSGCode ";

			reportParams.put("InvCode", InvCode);
			reportParams.put("dteInvDate", dteInvDate);

			Map<String, Double> vatTaxAmtMap = new HashMap();
			Map<String, Double> vatTaxableAmtMap = new HashMap();
			Map<String, Double> AssValueMap = new HashMap();
			Map<String, Double> exciseTaxAmt = new HashMap();
			List productList = new ArrayList();
			List listprodDtl = objGlobalFunctionsService.funGetList(prodsql, "sql");

			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				String strProdCode = objProdDtl[0].toString();
				String strProdName = objProdDtl[1].toString();
				String strSGCode = objProdDtl[2].toString();
				String strSGName = objProdDtl[3].toString();
				double discount = Double.parseDouble(objProdDtl[4].toString());
				double dblAssRate = Double.parseDouble(objProdDtl[5].toString());
				double dblBillRate = Double.parseDouble(objProdDtl[6].toString());
				double dblQty = Double.parseDouble(objProdDtl[7].toString());
				double invValue = Double.parseDouble(objProdDtl[8].toString());
				double dblAssValue = Double.parseDouble(objProdDtl[9].toString());
				String strUOM = objProdDtl[10].toString();

				objInvoiceProdDtlReportBean.setStrProdCode(strProdCode);
				objInvoiceProdDtlReportBean.setStrProdName(strProdName);
				objInvoiceProdDtlReportBean.setStrSGCode(strSGCode);
				objInvoiceProdDtlReportBean.setStrSGName(strSGName);
				objInvoiceProdDtlReportBean.setDblDiscount(discount);
				objInvoiceProdDtlReportBean.setDblAssRate(dblAssRate);
				objInvoiceProdDtlReportBean.setBillRate(dblBillRate);
				objInvoiceProdDtlReportBean.setDblInvValue(invValue);
				objInvoiceProdDtlReportBean.setDblAssValue(dblAssValue);
				objInvoiceProdDtlReportBean.setDblQty(dblQty);
				objInvoiceProdDtlReportBean.setStrUOM(strUOM);
				productList.add(strProdCode);

				// Sub Groupwise Asseable Value and exciseDuty
				if (AssValueMap.containsKey(strSGName))
				{
					double assValue = 0.0;
					assValue = AssValueMap.get(strSGName);
					assValue = assValue + dblAssValue;
					AssValueMap.put(strSGName, assValue);
				}
				else
				{
					AssValueMap.put(strSGName, dblAssValue);
				}

				String sqlTax = " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt " + " from tblinvprodtaxdtl a,tbltaxhd b,tblinvoicedtl c,tblproductmaster d,tblinvtaxdtl e " + " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode " + " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='" + strProdCode + "' and a.strInvCode='" + InvCode + "' " + "and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode and b.strTaxIndicator<>''";

				List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
				if (!listprodTax.isEmpty())
				{
					Object[] objProdTax = (Object[]) listprodTax.get(0);
					double dblPercent = Double.parseDouble(objProdTax[2].toString());
					objInvoiceProdDtlReportBean.setTaxRate(dblPercent);

					// For Total of Vat Tax

					if (vatTaxAmtMap.containsKey(Double.toString(dblPercent)))
					{
						double taxAmt = 0.0;
						double taxableAmt = 0.0;
						taxAmt = (double) vatTaxAmtMap.get(Double.toString(dblPercent));
						taxAmt = taxAmt + Double.parseDouble(objProdTax[3].toString());
						vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt);
						taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
						taxableAmt = taxableAmt + Double.parseDouble(objProdTax[4].toString());
						vatTaxableAmtMap.put(Double.toString(dblPercent), taxableAmt);
					}
					else
					{
						vatTaxAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[3].toString()));
						vatTaxableAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[4].toString()));
					}
				}

				fieldList.add(objInvoiceProdDtlReportBean);
			}

			Map<String, clsSubGroupTaxDtl> hmExciseTaxDtl = new HashMap<String, clsSubGroupTaxDtl>();
			// Excise Tax
			String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName " + " from tblinvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblinvoicedtl e " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y'  " + " and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' " + " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and d.strProdType='Trading' and c.strSGCode=d.strSGCode";

			List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
			if (!listExciseTax.isEmpty())
			{
				/*
				 * for(int j=0;j<listExciseTax.size();j++) { Object []
				 * objExciseTax=(Object[])listExciseTax.get(j);
				 * if(exciseTaxAmt.containsKey(objExciseTax[3].toString())) { double
				 * exciseAmt=exciseTaxAmt.get(objExciseTax[3].toString()); double dblTaxAmt=
				 * Double.parseDouble(objExciseTax[2].toString());
				 * exciseAmt=exciseAmt+dblTaxAmt;
				 * exciseTaxAmt.put(objExciseTax[3].toString(),exciseAmt ); }else{
				 * exciseTaxAmt.put(objExciseTax[3].toString(),
				 * Double.parseDouble(objExciseTax[2].toString())); } }
				 */

				for (int j = 0; j < listExciseTax.size(); j++)
				{
					clsSubGroupTaxDtl objSGTaxDtl = new clsSubGroupTaxDtl();
					Object[] objExciseTax = (Object[]) listExciseTax.get(j);
					if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString()))
					{
						objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
						double dblTaxAmt = Double.parseDouble(objExciseTax[2].toString());
						objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmt);

					}
					else
					{
						objSGTaxDtl.setTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
					}
					hmExciseTaxDtl.put(objExciseTax[3].toString(), objSGTaxDtl);
				}
			}

			// Tax pass In report through parameter list
			ArrayList<clsInvoiceProdDtlReportBean> taxList = new ArrayList();
			double totalVatTax = 0.0;
			for (Map.Entry<String, Double> entry : vatTaxAmtMap.entrySet())
			{
				double dblvatPer = 0.0;
				double dblTaxableAmt = 0.0;
				clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
				double taxpercent = Double.parseDouble(entry.getKey().toString());
				obj.setDblVatTaxPer(taxpercent);
				obj.setDblTaxAmt(vatTaxAmtMap.get(entry.getKey()));
				totalVatTax = totalVatTax + vatTaxAmtMap.get(entry.getKey());
				obj.setDblTaxableAmt(vatTaxableAmtMap.get(entry.getKey()));
				taxList.add(obj);
			}
			reportParams.put("VatTaxList", taxList);
			reportParams.put("totalVatTax", totalVatTax);

			// Total of AssValue Pass in report
			ArrayList<clsInvoiceProdDtlReportBean> assValueList = new ArrayList();
			for (Map.Entry<String, Double> entry : AssValueMap.entrySet())
			{/*
				 * clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean ( ) ; obj .
				 * setDblexciseDuty ( exciseTaxAmt . get ( entry . getKey ( ) ) ) ; obj .
				 * setDblGrpAssValue ( entry . getValue ( ) ) ; obj . setStrSGName ( entry .
				 * getKey ( ) ) ; assValueList . add ( obj ) ;
				 */

				clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
				if (hmExciseTaxDtl.containsKey(entry.getKey()))
				{
					obj.setDblexciseDuty(hmExciseTaxDtl.get(entry.getKey()).getTaxAmt());
					obj.setDblGrpAssValue(entry.getValue());
					obj.setStrSGName(entry.getKey());
					assValueList.add(obj);
				}
				else
				{
					obj.setDblexciseDuty(0);
					obj.setDblGrpAssValue(entry.getValue());
					obj.setStrSGName(entry.getKey());
					assValueList.add(obj);
				}
			}
			reportParams.put("assValueList", assValueList);
			// Add Freight Tax
			double dblfeightTaxAmt = 0.0;

			String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "' ";
			List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
			if (!listFreightTax.isEmpty())
			{
				Object[] objfrightTax = (Object[]) listFreightTax.get(0);
				dblfeightTaxAmt = Double.parseDouble(objfrightTax[2].toString());
			}
			reportParams.put("frieghtTax", dblfeightTaxAmt);
			// reportParams.put("VatTaxList",List);
			// reportParams.put("frieghtTax",frieghtTax);
			reportParams.put("totalVatTax", totalVatTax);
			reportParams.put("strCompanyName", companyName);
			reportParams.put("strUserCode", userCode);
			reportParams.put("strImagePath", imagePath);
			reportParams.put("strAddr1", objSetup.getStrAdd1());
			reportParams.put("strAddr2", objSetup.getStrAdd2());
			reportParams.put("strCity", objSetup.getStrCity());
			reportParams.put("strState", objSetup.getStrState());
			reportParams.put("strCountry", objSetup.getStrCountry());
			reportParams.put("strPin", objSetup.getStrPin());
			reportParams.put("strPName", strPName);
			reportParams.put("strSAdd1", strSAdd1);
			reportParams.put("strSAdd2", strSAdd2);
			reportParams.put("strSCity", strSCity);
			reportParams.put("strSState", strSState);
			reportParams.put("strSCountry", strSCountry);
			reportParams.put("strSPin", strSPin);
			reportParams.put("strInvCode", strInvCode);
			reportParams.put("dteInvDate", dteInvDate);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);

			// }

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			return jprintlist;
		}

	}

	/**
	 * Open Pending SO form
	 * 
	 * @return
	 */
	@RequestMapping(value = "/frmInvoiceSale", method = RequestMethod.GET)
	public ModelAndView funOpenPOforMR(Map<String, Object> model, HttpServletRequest req)
	{
		String locCode = req.getParameter("strlocCode");
		String dtfullfilled = req.getParameter("dtFullFilled");
		String custCode = req.getParameter("strCustCode");
		model.put("locCode", locCode);
		model.put("dtFullfilled", dtfullfilled);
		model.put("strCustCode", custCode);
		return new ModelAndView("frmInvoiceSale", "command", new clsReportBean());

	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/loadSOforInvoice", method = RequestMethod.GET)
	public @ResponseBody List funLoadPOforGRN(HttpServletRequest request)
	{
		// String strSuppCode=request.getParameter("strSuppCode").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String locCode = request.getParameter("strlocCode");
		String dtfullfilled = request.getParameter("dtFullFilled");
		String custCode = request.getParameter("strCustCode");
		// dtfullfilled=objGlobalFunctions.funGetDate("yyyy-MM-dd",dtfullfilled);
		List SOHelpList = objInvoiceHdService.funListSOforInvoice(locCode, dtfullfilled, clientCode, custCode);
		return SOHelpList;

	}
	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/loadAgainstSOForInvoice", method = RequestMethod.GET)
	public @ResponseBody List<clsSalesOrderDtl> funAssignFieldsForInvoice(@RequestParam("SOCode") String codes, HttpServletRequest request)
	{

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		List<clsSalesOrderDtl> listSaleDtl = new ArrayList<clsSalesOrderDtl>();

		String[] SOCode = codes.split(",");

		List objSales = objSalesOrderService.funGetMultipleSODtlForInvoice(SOCode, clientCode);
		if (null != objSales)
		{
			for (int i = 0; i < objSales.size(); i++)
			{
				Object[] obj = (Object[]) objSales.get(i);
				clsSalesOrderDtl saleDtl = new clsSalesOrderDtl();
				saleDtl.setStrProdCode(obj[0].toString());
				saleDtl.setStrProdName(obj[1].toString());
				saleDtl.setStrProdType(obj[2].toString());

				clsProductMasterModel objProdMaster = objProductMasterService.funGetObject(obj[0].toString(), clientCode);
				saleDtl.setDblAcceptQty(Double.parseDouble(obj[3].toString()));
				saleDtl.setDblUnitPrice(Double.parseDouble(obj[4].toString()));
				// saleDtl.setDblUnitPrice(objProdMaster.getDblMRP());
				saleDtl.setDblWeight(Double.parseDouble(obj[5].toString()));
				saleDtl.setStrClientCode(clientCode);
				saleDtl.setStrCustCode(obj[6].toString());
				saleDtl.setStrSOCode(obj[8].toString());				
				double discPer=Double.parseDouble(obj[9].toString());								
				saleDtl.setDblDiscount((discPer/100)*(saleDtl.getDblAcceptQty()*saleDtl.getDblUnitPrice()));
				saleDtl.setIntindex(i);
				saleDtl.setStrCurrency(obj[10].toString());
				saleDtl.setDblConversion(Double.parseDouble(obj[11].toString()));
				String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj[0].toString() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode " + " and a.strCustCode='" + obj[6].toString() + "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj[0].toString() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode  " + " and a.strCustCode='" + obj[6].toString() + "')";

				List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");

				if (listPrevInvData.size() > 0)
				{
					Object objInv[] = (Object[]) listPrevInvData.get(0);
					saleDtl.setPrevUnitPrice(Double.parseDouble(objInv[1].toString()));
					saleDtl.setPrevInvCode(objInv[2].toString());

				}
				else
				{
					saleDtl.setPrevUnitPrice(0.0);
					saleDtl.setPrevInvCode(" ");
				}
				listSaleDtl.add(saleDtl);
			}
		}
		return listSaleDtl;
	}



	@SuppressWarnings({ "rawtypes", "unused" })
	@RequestMapping(value = "/loadSOTaxDtlforInvoice", method = RequestMethod.GET)
	public @ResponseBody List funLoadSOTaxDtlForInvoice(@RequestParam("SOCode") String codes, HttpServletRequest request)
	{

		String propCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		String[] arrSOCodes = codes.split(",");
		String SOCodes = "";
		for (String code : arrSOCodes)
			SOCodes += ",'" + code + "'";

		List list = new ArrayList();
		SOCodes = SOCodes.substring(1, SOCodes.length());
		StringBuilder sbSql = new StringBuilder();

		try
		{
			sbSql.append("select a.strTaxCode,a.strTaxDesc,sum(a.strTaxableAmt),sum(a.strTaxAmt) " + " from tblsalesordertaxdtl a,tbltaxhd b " + " where a.strSOCode in (" + SOCodes + ") and a.strTaxCode=b.strTaxCode and a.strClientCode='" + clientCode + "' " + " group by a.strTaxCode ");
			list = objBaseService.funGetList(sbSql, "sql");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptInvoiceSlipFromat2", method = RequestMethod.GET)
	public void funOpenInvoiceFromat2Report(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		try
		{
			String userCode = req.getSession().getAttribute("usercode").toString();
			String InvCode = req.getParameter("rptInvCode").toString();
			String[] arrInvCode = InvCode.split(",");
			// String InvDate=req.getParameter("rptInvDate").toString();
			req.getSession().removeAttribute("rptInvCode");
			req.getSession().removeAttribute("rptInvDate");
			String type = "pdf";
			String dteInvForReportname = "";
			String fileReportname = "Invoice_";
			String invcodes = "";
			//
			// SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
			//
			// try {
			//
			// InvDate = myFormat.format(fromUser.parse(InvDate));
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			List<JasperPrint> jprintlist1 = new ArrayList<JasperPrint>();
			List<JasperPrint> jprintlist2 = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrInvCode.length; cnt++)
			{
				String InvCodeSingle = arrInvCode[cnt].toString();
				invcodes = invcodes + InvCodeSingle + "#";
				String sql = "select strInvCode,dteInvDate from tblinvoicehd where strExciseable='Y' and strInvCode='" + InvCodeSingle + "' ";
				List listInvCode = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listInvCode.size() > 0)
				{
					JasperPrint jp = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Original For Buyer");
					jprintlist.add(jp);

					JasperPrint jp1 = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Duplicate For Transporter");
					jprintlist.add(jp1);

					JasperPrint jp2 = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Triplicate To Customer");
					jprintlist.add(jp2);

				}
			}
			if (arrInvCode.length == 1)
			{
				InvCode = InvCode.replaceAll(",", "");
				String sql = "select strInvCode,Date(dteInvDate) from tblinvoicehd where strExciseable='Y' and strInvCode='" + InvCode + "' ";
				List listInvRow = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listInvRow.size() > 0)
				{
					Object[] obj = (Object[]) listInvRow.get(0);

					dteInvForReportname = obj[1].toString();
					fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
					fileReportname.replaceAll(" ", "");
				}
			}
			else
			{
				fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
				fileReportname.replaceAll(" ", "");
			}

			if (jprintlist.size() > 0)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=" + fileReportname + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked", "finally", "rawtypes" })
	public JasperPrint funInvoiceSlipFormat2(String InvCode, String type, HttpServletRequest req, HttpServletResponse resp, String invoiceType)
	{
		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);

		try
		{
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strVehNo = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			double subtotalInv = 0.0;
			double totalAmt = 0.0;
			String totalInvInWords = "";
			double exciseTaxAmtTotal = 0.0;
			String exciseTotalInWords = "";
			double grandTotal = 0.0;
			String excisePerDesc = "";
			String dteInvDate = "";
			String strDulpicateFlag = "";
			String heading = "(Modvat)";
			String strDCCode = "";
			String dteDCDate = "";
			String strTransName = "";
			String strCustECCNo = "";

			clsNumberToWords obj = new clsNumberToWords();

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormat2.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo " + ",a.dblTotalAmt,a.dblGrandTotal " + " from tblinvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";
			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				dteInvDate = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
				subtotalInv = Double.parseDouble(arrObj[10].toString());
				strDulpicateFlag = arrObj[12].toString();
				strCustECCNo = arrObj[13].toString();
				totalAmt = Double.parseDouble(arrObj[14].toString());
				grandTotal = Double.parseDouble(arrObj[15].toString());

			}

			String sqlTransporter = "select a.strVehCode,a.strVehNo ,b.strTransName from tbltransportermasterdtl a,tbltransportermaster b where a.strVehNo='" + strVehNo + "' " + "and a.strTransCode=b.strTransCode and a.strClientCode='" + clientCode + "' ";
			List listTransporter = objGlobalFunctionsService.funGetList(sqlTransporter, "sql");
			if (!listTransporter.isEmpty())
			{
				Object[] arrObj = (Object[]) listTransporter.get(0);
				strTransName = arrObj[2].toString();

			}
			ArrayList fieldList = new ArrayList();
			HashMap hm = new HashMap();

			String sqlFetchDc = "select strDCCode,DATE_FORMAT(date(dteDCDate),'%d-%m-%Y') from tbldeliverychallanhd where strSoCode='" + InvCode + "' and strClientCode='" + clientCode + "' ";
			List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

			if (!listFetchDc.isEmpty())
			{
				Object[] objDc = (Object[]) listFetchDc.get(0);

				strDCCode = objDc[0].toString();
				dteDCDate = objDc[1].toString();

			}

			String prodSql = "select b.strProdCode,d.strProdName,e.strExciseChapter,sum(b.dblQty),(b.dblAssValue/b.dblQty) as Assble_Rate,(b.dblQty*d.dblMRP) as MRP_Value, " + " if(d.strPickMRPForTaxCal='Y',sum(((b.dblQty*d.dblMRP)*(f.dblAbatement/100))),sum(b.dblAssValue)) as Assable_Value  , " + " sum(c.dblValue) as Excise_Duty,sum((b.dblAssValue)) as Invoice_Value,d.strPickMRPForTaxCal   " + " from tblinvoicehd a left outer join  tblinvoicedtl b on a.strInvCode=b.strInvCode  " + " left outer join  tblinvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode " + " left outer join tbltaxhd f on c.strDocNo=f.strTaxCode " + " left outer join tblproductmaster d on b.strProdCode=d.strProdCode " + " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and f.strExcisable='Y' and d.strExciseable='Y' and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc' " + " group by b.strProdCode ";

			/*
			 * String prodSql=
			 * "select b.strProdCode,d.strProdName,e.strExciseChapter,b.dblQty,(b.dblAssValue/b.dblQty) as Assble_Rate,(b.dblQty*d.dblMRP) as MRP_Value, "
			 * +
			 * " if(d.strPickMRPForTaxCal='Y',((b.dblQty*d.dblMRP)*(f.dblAbatement/100)),b.dblAssValue) as Assable_Value  , "
			 * +
			 * " c.dblValue as Excise_Duty,(b.dblAssValue) as Invoice_Value,d.strPickMRPForTaxCal   "
			 * +
			 * " from tblinvoicehd a left outer join  tblinvoicedtl b on a.strInvCode=b.strInvCode  "
			 * +
			 * " left outer join  tblinvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode "
			 * +" left outer join tbltaxhd f on c.strDocNo=f.strTaxCode " +
			 * " left outer join tblproductmaster d on b.strProdCode=d.strProdCode " +
			 * " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " +
			 * " where a.strInvCode='"+InvCode+"' and a.strClientCode='"+clientCode
			 * +"' and b.strClientCode='"+clientCode+"' " +
			 * " and f.strExcisable='Y' and d.strExciseable='Y' and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc'"
			 * ;
			 */

			hm.put("InvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);

			List listprodDtl = objGlobalFunctionsService.funGetList(prodSql, "sql");
			double assVSum = 0.00;
			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				objInvoiceProdDtlReportBean.setStrProdCode(objProdDtl[0].toString());
				objInvoiceProdDtlReportBean.setStrProdName(objProdDtl[1].toString());
				objInvoiceProdDtlReportBean.setStrExciseChapter(objProdDtl[2].toString());
				objInvoiceProdDtlReportBean.setDblQty(Double.parseDouble(objProdDtl[3].toString()));
				objInvoiceProdDtlReportBean.setDblAssRate(Double.parseDouble(objProdDtl[4].toString()));

				objInvoiceProdDtlReportBean.setDblAssValue(Double.parseDouble(objProdDtl[6].toString()));
				objInvoiceProdDtlReportBean.setDblexciseDuty(Double.parseDouble(objProdDtl[7].toString()));
				objInvoiceProdDtlReportBean.setDblInvValue(Double.parseDouble(objProdDtl[8].toString()));
				// subtotalInv=subtotalInv+Double.parseDouble(objProdDtl[8].toString());
				assVSum = assVSum + Double.parseDouble(objProdDtl[6].toString());
				if (objProdDtl[9].toString().equalsIgnoreCase("Y"))
				{
					objInvoiceProdDtlReportBean.setDblMRP(Double.parseDouble(objProdDtl[5].toString()));
				}
				else
				{
					objInvoiceProdDtlReportBean.setDblMRP(0.0);
				}

				/*
				 * String sqlTax=
				 * " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt "
				 * +
				 * " from tblinvprodtaxdtl a,tbltaxhd b,tblinvoicedtl c,tblproductmaster d,tblinvtaxdtl e "
				 * +
				 * " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode "
				 * + " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='"
				 * +objProdDtl[0].toString()+"' and a.strInvCode='"+InvCode+"' " +
				 * "and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode  and b.strTaxIndicator<>'' "
				 * + "and a.strClientCode='"+clientCode+"' and c.strClientCode='"
				 * +clientCode+"' "; List listprodTax=objGlobalFunctionsService.funGetList
				 * (sqlTax,"sql"); if(!listprodTax.isEmpty()) { Object []
				 * objProdTax=(Object[])listprodTax.get(0); double
				 * dblPercent=Double.parseDouble(objProdTax[2].toString());
				 * objInvoiceProdDtlReportBean.setTaxRate(dblPercent); //For Total of Vat Tax
				 * 
				 * if(vatTaxAmtMap.containsKey(Double.toString(dblPercent))) { double
				 * taxAmt=0.0; double taxableAmt=0.0; taxAmt=(double)
				 * vatTaxAmtMap.get(Double.toString(dblPercent)); taxAmt=
				 * taxAmt+Double.parseDouble(objProdTax[3].toString());
				 * vatTaxAmtMap.put(Double.toString(dblPercent),taxAmt); taxableAmt=(double)
				 * vatTaxableAmtMap.get(Double.toString(dblPercent)); taxableAmt=
				 * taxableAmt+Double.parseDouble(objProdTax[4].toString());
				 * vatTaxableAmtMap.put(Double.toString(dblPercent),taxableAmt); } else {
				 * vatTaxAmtMap.put(Double.toString(dblPercent),
				 * Double.parseDouble(objProdTax[3].toString()));
				 * vatTaxableAmtMap.put(Double.toString(dblPercent),
				 * Double.parseDouble(objProdTax[4].toString())); } }
				 */

				fieldList.add(objInvoiceProdDtlReportBean);
			}

			/*
			 * //Tax pass In report through parameter list
			 * ArrayList<clsInvoiceProdDtlReportBean> taxList=new ArrayList(); double
			 * totalVatTax=0.0; for (Map.Entry<String,Double> entry :
			 * vatTaxAmtMap.entrySet()) { clsInvoiceProdDtlReportBean objBeanforVat= new
			 * clsInvoiceProdDtlReportBean(); double
			 * taxpercent=Double.parseDouble(entry.getKey().toString());
			 * objBeanforVat.setDblVatTaxPer(taxpercent);
			 * objBeanforVat.setDblTaxAmt(vatTaxAmtMap.get(entry.getKey()));
			 * totalVatTax=totalVatTax+vatTaxAmtMap.get(entry.getKey()); objBeanforVat
			 * .setDblTaxableAmt(vatTaxableAmtMap.get(entry.getKey()));
			 * //taxList.add(objBeanforVat); }
			 */
			System.out.println("assVSum=" + assVSum);
			excisePerDesc = "";
			exciseTaxAmtTotal = 0;
			double totalVatTax = 0;
			List<clsInvoiceProdDtlReportBean> listVatTax = new ArrayList();
			List<clsInvoiceProdDtlReportBean> listNillVatTax = new ArrayList();
			List<clsInvoiceProdDtlReportBean> listExsiceDutyTax = new ArrayList();

			String exciseSql = "select b.strTaxDesc,sum(a.dblTaxableAmt),sum(a.dblTaxAmt),b.strExcisable,b.dblPercent,b.strTaxCalculation " + "from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and a.strInvCode='" + InvCode + "'  and a.strClientCode='" + clientCode + "' " + "group by a.strTaxCode ; ";
			List listExciseTax = objGlobalFunctionsService.funGetList(exciseSql, "sql");
			if (!listExciseTax.isEmpty())
			{
				for (int cn = 0; cn < listExciseTax.size(); cn++)
				{
					Object[] objExciseTax = (Object[]) listExciseTax.get(cn);

					if (objExciseTax[3].toString().equals("Y"))
					{
						clsInvoiceProdDtlReportBean objExDutyTax = new clsInvoiceProdDtlReportBean();
						excisePerDesc = objExciseTax[0].toString();
						exciseTaxAmtTotal += Double.parseDouble(objExciseTax[2].toString());
						objExDutyTax.setDblexciseDuty(Double.parseDouble(objExciseTax[2].toString()));
						objExDutyTax.setStrExciseDutyPerDes(excisePerDesc);
						listExsiceDutyTax.add(objExDutyTax);
					}
					else
					{
						if (Double.parseDouble(objExciseTax[4].toString()) > 0)
						{
							totalVatTax = Double.parseDouble(objExciseTax[2].toString());
							clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
							double taxableAmt = Double.parseDouble(objExciseTax[1].toString());
							if (objExciseTax[5].toString().equalsIgnoreCase("Backword"))
							{
								taxableAmt = taxableAmt - Double.parseDouble(objExciseTax[2].toString());
							}
							objTax.setDblTaxableAmt(taxableAmt);
							objTax.setDblTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
							objTax.setDblVatTaxPer(Double.parseDouble(objExciseTax[4].toString()));
							listVatTax.add(objTax);
						}
						else
						{
							clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
							objTax.setDblTaxableAmt(Double.parseDouble(objExciseTax[1].toString()));
							objTax.setDblTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
							if (Double.parseDouble(objExciseTax[4].toString()) == 0.0)
							{
								objTax.setStrVatPerWord("NIL");
							}
							else
							{
								objTax.setStrVatPerWord(objExciseTax[4].toString());
							}

							listNillVatTax.add(objTax);
						}
					}
				}

				if (listNillVatTax.isEmpty())
				{
					clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
					objTax.setDblTaxableAmt(0.00);
					objTax.setDblTaxAmt(0.00);
					objTax.setDblVatTaxPer(0.0);
					objTax.setStrVatPerWord("NIL");
					listNillVatTax.add(objTax);
				}
			}

			String taxDtlSql = "select strTaxDesc,0.00,0.00,strExcisable,dblPercent " + " from tbltaxhd " + " where strTaxCode not in (select strtaxCode from tblinvtaxdtl where strInvCode='" + InvCode + "') " + " and (dblPercent=6.00 or dblPercent=13.50) and strTaxOnSP='Sales' and strTaxDesc not like '%Excise%' ";
			List listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
			for (int cn = 0; cn < listTax.size(); cn++)
			{
				Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
				if (arrObjTaxDtl[3].toString().equals("N"))
				{
					totalVatTax = Double.parseDouble(arrObjTaxDtl[2].toString());
					clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
					objTax.setDblTaxableAmt(Double.parseDouble(arrObjTaxDtl[1].toString()));
					objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
					objTax.setDblVatTaxPer(Double.parseDouble(arrObjTaxDtl[4].toString()));
					listVatTax.add(objTax);
				}
			}

			hm.put("nillTaxList", listNillVatTax);
			hm.put("VatTaxList", listVatTax);
			hm.put("totalVatTax", totalVatTax);
			hm.put("exciseDutyList", listExsiceDutyTax);

			String[] datetime = dteInvDate.split(" ");
			dteInvDate = datetime[0];
			time = datetime[1];
			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			String timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("and");
			String[] wordmin = words[1].split("paisa");
			timeInWords = "Hours " + words[0] + "" + wordmin[0];
			// grandTotal=subtotalInv+exciseTaxAmtTotal+totalVatTax;
			DecimalFormat decformat = new DecimalFormat("#.##");
			exciseTaxAmtTotal = Double.parseDouble(decformat.format(exciseTaxAmtTotal).toString());
			// exciseTotalInWords=obj.getNumberInWorld(exciseTaxAmtTotal);
			// totalInvInWords=obj.getNumberInWorld(grandTotal);

			exciseTotalInWords = obj.funConvertAmtInWords(exciseTaxAmtTotal);
			totalInvInWords = obj.funConvertAmtInWords(grandTotal);
			if (strDulpicateFlag.equalsIgnoreCase("N"))
			{
				strDulpicateFlag = "Orignal Copy";
				String sqlUpdateduplicateflag = "update tblinvoicehd set strDulpicateFlag='Y'";
				objGlobalFunctionsService.funUpdateAllModule(sqlUpdateduplicateflag, "sql");
			}
			else
			{
				strDulpicateFlag = "Duplicate Copy";
			}

			String[] date = dteInvDate.split("-");
			dteInvDate = date[2] + "-" + date[1] + "-" + date[0];

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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strInvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);
			hm.put("strVAT", objSetup.getStrVAT());
			hm.put("strCST", objSetup.getStrCST());
			hm.put("strVehNo", strVehNo);
			hm.put("time", time);
			hm.put("timeInWords", timeInWords);
			hm.put("strRangeAdd", objSetup.getStrRangeAdd());
			hm.put("strDivision", objSetup.getStrDivision());
			hm.put("strRangeDiv", objSetup.getStrRangeDiv());
			hm.put("strDivisionAdd", objSetup.getStrDivisionAdd());
			hm.put("strCommi", objSetup.getStrCommi());
			hm.put("strMask", objSetup.getStrMask());
			hm.put("strPhone", objSetup.getStrPhone());
			hm.put("strFax", objSetup.getStrFax());
			hm.put("subtotalInv", totalAmt);
			hm.put("exciseTotal", exciseTaxAmtTotal);
			hm.put("totalInvInWords", totalInvInWords);
			hm.put("exciseTotalInWords", exciseTotalInWords);
			hm.put("grandTotal", grandTotal);
			hm.put("excisePer", excisePerDesc);
			// hm.put("strDulpicateFlag",strDulpicateFlag);
			hm.put("strDulpicateFlag", invoiceType);
			hm.put("excisePer", excisePerDesc);
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			hm.put("ItemDataSource", beanCollectionDataSource);
			hm.put("heading", heading);
			hm.put("strDCCode", strDCCode);
			hm.put("dteDCDate", dteDCDate);
			hm.put("strTransName", strTransName);
			hm.put("strECCNo", objSetup.getStrECCNo());
			hm.put("strCustECCNo", strCustECCNo);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			return jprintlist;
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptInvoiceSlipNonExcisableFromat2", method = RequestMethod.GET)
	public void funOpenInvoiceNonExcisableFromat2Report(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{

			String InvCode = req.getParameter("rptInvCode").toString();
			String[] arrInvCode = InvCode.split(",");
			// String InvDate=req.getParameter("rptInvDate").toString();
			req.getSession().removeAttribute("rptInvCode");
			req.getSession().removeAttribute("rptInvDate");
			String userCode = req.getSession().getAttribute("usercode").toString();
			String type = "pdf";
			String dteInvForReportname = "";
			String fileReportname = "Invoice_";
			String invcodes = "";

			// SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
			// SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
			//
			// try {
			//
			// InvDate = myFormat.format(fromUser.parse(InvDate));
			// } catch (ParseException e) {
			// e.printStackTrace();
			// }
			//

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrInvCode.length; cnt++)
			{
				String InvCodeSingle = arrInvCode[cnt].toString();
				invcodes = invcodes + InvCodeSingle + "#";
				String sql = "select strInvCode from tblinvoicehd where strExciseable='N' and strInvCode='" + InvCodeSingle + "' ";
				List listInvCode = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listInvCode.size() > 0)
				{
					/*
					 * JasperPrint jp= funInvoiceSlipNonExcisableFormat2(InvCodeSingle
					 * ,type,req,resp); jprintlist.add(jp);
					 */

					JasperPrint jp = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Original For Buyer");
					jprintlist.add(jp);

					JasperPrint jp1 = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Duplicate For Transporter");
					jprintlist.add(jp1);

					JasperPrint jp2 = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Triplicate To Customer");
					jprintlist.add(jp2);
				}

			}
			if (arrInvCode.length == 1)
			{
				InvCode = InvCode.replaceAll(",", "");
				String sql = "select strInvCode,Date(dteInvDate) from tblinvoicehd where strExciseable='N' and strInvCode='" + InvCode + "' ";
				List listInvRow = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listInvRow.size() > 0)
				{
					Object[] obj = (Object[]) listInvRow.get(0);

					dteInvForReportname = obj[1].toString();
					fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
					fileReportname.replaceAll(" ", "");
				}

			}
			else
			{
				fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
				fileReportname.replaceAll(" ", "");

			}

			if (jprintlist.size() > 0)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=" + fileReportname + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public JasperPrint funInvoiceSlipNonExcisableFormat2(String InvCode, String type, HttpServletRequest req, HttpServletResponse resp, String invoiceType)
	{
		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		try
		{
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strVehNo = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			double totalAmt = 0.0;
			double subtotalInv = 0.0;
			String totalInvInWords = "";
			double exciseTaxAmtTotal = 0.0;
			String exciseTotalInWords = "";
			double grandTotal = 0.0;
			String excisePerDesc = "";
			String dteInvDate = "";
			String strDulpicateFlag = "";
			String strCustECCNo = "";
			String heading = "(Non Modvat)";
			String strDCCode = "";
			String dteDCDate = "";
			String strTransName = "";

			clsNumberToWords obj = new clsNumberToWords();

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormat2.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo " + ",a.dblTotalAmt,a.dblGrandTotal " + " from tblinvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");

			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				dteInvDate = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
				subtotalInv = Double.parseDouble(arrObj[10].toString());
				strDulpicateFlag = arrObj[12].toString();
				strCustECCNo = arrObj[13].toString();
				totalAmt = Double.parseDouble(arrObj[14].toString());
				grandTotal = Double.parseDouble(arrObj[15].toString());
			}

			String sqlTransporter = "select a.strVehCode,a.strVehNo ,b.strTransName from tbltransportermasterdtl a,tbltransportermaster b where a.strVehNo='" + strVehNo + "' " + "and a.strTransCode=b.strTransCode and a.strClientCode='" + clientCode + "' ";
			List listTransporter = objGlobalFunctionsService.funGetList(sqlTransporter, "sql");
			if (!listTransporter.isEmpty())
			{
				Object[] arrObj = (Object[]) listTransporter.get(0);
				strTransName = arrObj[2].toString();

			}
			ArrayList fieldList = new ArrayList();

			String sqlFetchDc = "select strDCCode,DATE_FORMAT(date(dteDCDate),'%d-%m-%Y') from tbldeliverychallanhd where strSoCode='" + InvCode + "' and strClientCode='" + clientCode + "' ";
			List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

			if (!listFetchDc.isEmpty())
			{
				Object[] objDc = (Object[]) listFetchDc.get(0);

				strDCCode = objDc[0].toString();
				dteDCDate = objDc[1].toString();
			}

			@SuppressWarnings("rawtypes")
			HashMap hm = new HashMap();

			/*
			 * String prodSql=
			 * "select b.strProdCode,d.strProdName,e.strExciseChapter,b.dblQty,(b.dblAssValue/b.dblQty) as Assble_Rate,(b.dblQty*d.dblMRP) as MRP_Value, "
			 * +
			 * " if(d.strPickMRPForTaxCal='Y',((b.dblQty*d.dblMRP)*(f.dblAbatement/100)),(b.dblAssValue)) as Assable_Value  , "
			 * +
			 * " 0 as Excise_Duty,(b.dblAssValue) as Invoice_Value,d.strPickMRPForTaxCal   "
			 * +
			 * " from tblinvoicehd a left outer join  tblinvoicedtl b on a.strInvCode=b.strInvCode  "
			 * +
			 * " left outer join  tblinvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc' "
			 * +
			 * " left outer join tbltaxhd f on c.strDocNo=f.strTaxCode and f.strExcisable='N'  "
			 * +
			 * " left outer join tblproductmaster d on b.strProdCode=d.strProdCode and d.strExciseable='N' "
			 * + " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " +
			 * " where a.strInvCode='"+InvCode+"' and a.strClientCode='"+clientCode
			 * +"' and b.strClientCode='"+clientCode+"' ";
			 */

			String prodSql = "select b.strProdCode,d.strProdName,e.strExciseChapter,sum(b.dblQty),(b.dblAssValue/b.dblQty) as Assble_Rate,sum((b.dblQty*d.dblMRP)) as MRP_Value" + ",sum((b.dblAssValue)) as Assable_Value" + ",0 as Excise_Duty,sum(b.dblAssValue) as Invoice_Value,d.strPickMRPForTaxCal  " + " from tblinvoicehd a left outer join  tblinvoicedtl b on a.strInvCode=b.strInvCode  " + " left outer join  tblinvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc' " + " left outer join tbltaxhd f on c.strDocNo=f.strTaxCode and f.strExcisable='N'  " + " left outer join tblproductmaster d on b.strProdCode=d.strProdCode and d.strExciseable='N' " + " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " group by b.strProdCode";

			hm.put("InvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);

			Map<String, Double> vatTaxAmtMap = new HashMap();
			Map<String, Double> vatTaxableAmtMap = new HashMap();
			List listprodDtl = objGlobalFunctionsService.funGetList(prodSql, "sql");

			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				objInvoiceProdDtlReportBean.setStrProdCode(objProdDtl[0].toString());
				objInvoiceProdDtlReportBean.setStrProdName(objProdDtl[1].toString());
				objInvoiceProdDtlReportBean.setStrExciseChapter(objProdDtl[2].toString());
				objInvoiceProdDtlReportBean.setDblQty(Double.parseDouble(objProdDtl[3].toString()));
				objInvoiceProdDtlReportBean.setDblAssRate(Double.parseDouble(objProdDtl[4].toString()));

				objInvoiceProdDtlReportBean.setDblAssValue(Double.parseDouble(objProdDtl[6].toString()));
				objInvoiceProdDtlReportBean.setDblexciseDuty(Double.parseDouble(objProdDtl[7].toString()));
				objInvoiceProdDtlReportBean.setDblInvValue(Double.parseDouble(objProdDtl[8].toString()));

				if (objProdDtl[9].toString().equalsIgnoreCase("Y"))
				{
					objInvoiceProdDtlReportBean.setDblMRP(Double.parseDouble(objProdDtl[5].toString()));
				}
				else
				{
					objInvoiceProdDtlReportBean.setDblMRP(0.0);

				}

				/*
				 * String sqlTax=
				 * " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt "
				 * +
				 * " from tblinvprodtaxdtl a,tbltaxhd b,tblinvoicedtl c,tblproductmaster d,tblinvtaxdtl e "
				 * +
				 * " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode "
				 * + " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='"
				 * +objProdDtl[0].toString()+"' and a.strInvCode='"+InvCode+"' " +
				 * "and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode  and b.strTaxIndicator<>'' and a.strClientCode='"
				 * +clientCode+"' and c.strClientCode='"+clientCode+"' ";
				 * 
				 * 
				 * List listprodTax=objGlobalFunctionsService.funGetList(sqlTax,"sql" );
				 * if(!listprodTax.isEmpty()) { Object []
				 * objProdTax=(Object[])listprodTax.get(0); double
				 * dblPercent=Double.parseDouble(objProdTax[2].toString());
				 * objInvoiceProdDtlReportBean.setTaxRate(dblPercent);
				 * 
				 * 
				 * //For Total of Vat Tax
				 * 
				 * if(vatTaxAmtMap.containsKey(Double.toString(dblPercent))) { double
				 * taxAmt=0.0; double taxableAmt=0.0; taxAmt=(double)
				 * vatTaxAmtMap.get(Double.toString(dblPercent)); taxAmt=
				 * taxAmt+Double.parseDouble(objProdTax[3].toString());
				 * vatTaxAmtMap.put(Double.toString(dblPercent),taxAmt); taxableAmt=(double)
				 * vatTaxableAmtMap.get(Double.toString(dblPercent)); taxableAmt=
				 * taxableAmt+Double.parseDouble(objProdTax[4].toString());
				 * vatTaxableAmtMap.put(Double.toString(dblPercent),taxableAmt); }else{
				 * vatTaxAmtMap.put(Double.toString(dblPercent),
				 * Double.parseDouble(objProdTax[3].toString()));
				 * vatTaxableAmtMap.put(Double.toString(dblPercent),
				 * Double.parseDouble(objProdTax[4].toString())); } }
				 */

				fieldList.add(objInvoiceProdDtlReportBean);
			}

			/*
			 * //Tax pass In report through parameter list
			 * ArrayList<clsInvoiceProdDtlReportBean> taxList=new ArrayList(); double
			 * totalVatTax=0.0; for (Map.Entry<String,Double> entry :
			 * vatTaxAmtMap.entrySet()) {
			 * 
			 * clsInvoiceProdDtlReportBean objBeanforVat= new clsInvoiceProdDtlReportBean();
			 * double taxpercent=Double.parseDouble(entry.getKey().toString());
			 * objBeanforVat.setDblVatTaxPer(taxpercent);
			 * objBeanforVat.setDblTaxAmt(vatTaxAmtMap.get(entry.getKey()));
			 * totalVatTax=totalVatTax+vatTaxAmtMap.get(entry.getKey()); objBeanforVat
			 * .setDblTaxableAmt(vatTaxableAmtMap.get(entry.getKey()));
			 * taxList.add(objBeanforVat); }
			 */
			excisePerDesc = "0";
			exciseTaxAmtTotal = 0.0;
			double totalVatTax = 0.0;
			List<clsInvoiceProdDtlReportBean> listVatTax = new ArrayList();
			List<clsInvoiceProdDtlReportBean> listNillVatTax = new ArrayList();
			List<clsInvoiceProdDtlReportBean> listExsiceDutyTax = new ArrayList();

			String taxDtlSql = "select b.strTaxDesc,sum(a.dblTaxableAmt),sum(a.dblTaxAmt),b.strExcisable,b.dblPercent,b.strTaxCalculation " + "from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and a.strInvCode='" + InvCode + "'  and a.strClientCode='" + clientCode + "' " + "group by a.strTaxCode ; ";
			List listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
			if (!listTax.isEmpty())
			{
				for (int cn = 0; cn < listTax.size(); cn++)
				{
					Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
					if (arrObjTaxDtl[3].toString().equals("N"))
					{
						if (Double.parseDouble(arrObjTaxDtl[4].toString()) > 0)
						{
							totalVatTax = Double.parseDouble(arrObjTaxDtl[2].toString());
							clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
							double taxableAmt = Double.parseDouble(arrObjTaxDtl[1].toString());
							if (arrObjTaxDtl[5].toString().equalsIgnoreCase("Backword"))
							{
								taxableAmt = taxableAmt - Double.parseDouble(arrObjTaxDtl[2].toString());
							}

							objTax.setDblTaxableAmt(taxableAmt);
							objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
							objTax.setDblVatTaxPer(Double.parseDouble(arrObjTaxDtl[4].toString()));
							listVatTax.add(objTax);
						}
						else
						{
							// totalVatTax=Double.parseDouble(arrObjTaxDtl[2].toString());
							clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
							objTax.setDblTaxableAmt(Double.parseDouble(arrObjTaxDtl[1].toString()));
							objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
							if (Double.parseDouble(arrObjTaxDtl[4].toString()) == 0.0)
							{
								objTax.setStrVatPerWord("NIL");
							}
							else
							{
								objTax.setStrVatPerWord(arrObjTaxDtl[4].toString());
							}

							listNillVatTax.add(objTax);
						}
					}
				}

				if (listNillVatTax.isEmpty())
				{
					clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
					objTax.setDblTaxableAmt(0.00);
					objTax.setDblTaxAmt(0.00);
					objTax.setDblVatTaxPer(0.0);
					objTax.setStrVatPerWord("NIL");
					listNillVatTax.add(objTax);
				}
			}

			taxDtlSql = "select strTaxDesc,0.00,0.00,strExcisable,dblPercent " + " from tbltaxhd " + " where strTaxCode not in (select strtaxCode from tblinvtaxdtl where strInvCode='" + InvCode + "') " + " and (dblPercent=5.50 or dblPercent=12.50) and strTaxOnSP='Sales' and strTaxDesc not like '%Excise%' ";
			listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
			for (int cn = 0; cn < listTax.size(); cn++)
			{
				Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
				if (arrObjTaxDtl[3].toString().equals("N"))
				{
					totalVatTax = Double.parseDouble(arrObjTaxDtl[2].toString());
					clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
					objTax.setDblTaxableAmt(Double.parseDouble(arrObjTaxDtl[1].toString()));
					objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
					objTax.setDblVatTaxPer(Double.parseDouble(arrObjTaxDtl[4].toString()));
					listVatTax.add(objTax);
				}
			}

			hm.put("nillTaxList", listNillVatTax);
			hm.put("VatTaxList", listVatTax);
			hm.put("totalVatTax", totalVatTax);
			hm.put("exciseDutyList", listExsiceDutyTax);

			String[] datetime = dteInvDate.split(" ");
			dteInvDate = datetime[0];
			time = datetime[1];
			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			String timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("and");
			String[] wordmin = words[1].split("paisa");
			timeInWords = "Hours " + words[0] + "" + wordmin[0];
			// grandTotal=subtotalInv+exciseTaxAmtTotal+totalVatTax;
			DecimalFormat decformat = new DecimalFormat("#.##");
			exciseTaxAmtTotal = Double.parseDouble(decformat.format(exciseTaxAmtTotal).toString());
			// exciseTotalInWords=obj.getNumberInWorld(exciseTaxAmtTotal);
			exciseTotalInWords = obj.funConvertAmtInWords(exciseTaxAmtTotal);
			DecimalFormat df = new DecimalFormat("#.##");
			grandTotal = Double.parseDouble(df.format(grandTotal).toString());

			// totalInvInWords=obj.getNumberInWorld(grandTotal);
			totalInvInWords = obj.funConvertAmtInWords(grandTotal);
			if (strDulpicateFlag.equalsIgnoreCase("N"))
			{
				strDulpicateFlag = "Orignal Copy";
				String sqlUpdateduplicateflag = "update tblinvoicehd set strDulpicateFlag='Y'";
				objGlobalFunctionsService.funUpdateAllModule(sqlUpdateduplicateflag, "sql");
			}
			else
			{
				strDulpicateFlag = "Duplicate Copy";

			}

			String[] date = dteInvDate.split("-");
			dteInvDate = date[2] + "-" + date[1] + "-" + date[0];
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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strInvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);
			hm.put("strVAT", objSetup.getStrVAT());
			hm.put("strCST", objSetup.getStrCST());
			hm.put("strVehNo", strVehNo);
			hm.put("time", time);
			hm.put("timeInWords", timeInWords);
			hm.put("strRangeAdd", objSetup.getStrRangeAdd());
			hm.put("strDivision", objSetup.getStrDivision());
			hm.put("strRangeDiv", objSetup.getStrRangeDiv());
			hm.put("strDivisionAdd", objSetup.getStrDivisionAdd());
			hm.put("strCommi", objSetup.getStrCommi());
			hm.put("strMask", objSetup.getStrMask());
			hm.put("strPhone", objSetup.getStrPhone());
			hm.put("strFax", objSetup.getStrFax());
			hm.put("subtotalInv", totalAmt);
			hm.put("exciseTotal", exciseTaxAmtTotal);
			hm.put("totalInvInWords", totalInvInWords);
			hm.put("exciseTotalInWords", exciseTotalInWords);
			hm.put("grandTotal", grandTotal);
			hm.put("excisePer", excisePerDesc);
			hm.put("strECCNo", objSetup.getStrECCNo());
			hm.put("strCustECCNo", strCustECCNo);
			// hm.put("strDulpicateFlag",strDulpicateFlag);
			hm.put("strDulpicateFlag", invoiceType);
			hm.put("heading", heading);
			hm.put("strDCCode", strDCCode);
			hm.put("dteDCDate", dteDCDate);
			hm.put("strTransName", strTransName);
			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			hm.put("ItemDataSource", beanCollectionDataSource);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			return jprintlist;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadProductDataForInvoice", method = RequestMethod.GET)
	public @ResponseBody List funAssignFields(@RequestParam("prodCode") String prodCode, @RequestParam("suppCode") String suppCode, @RequestParam("locCode") String locCode, HttpServletRequest req)
	{
		clsPartyMasterModel objPartyModel = null;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		/*
		 * String propertyCode=req.getSession().getAttribute("propertyCode").toString
		 * (); clsPropertySetupModel
		 * objSetup=objSetupMasterService.funGetObjectPropertySetup (propertyCode,
		 * clientCode);
		 */
		String propertyCode = "";
		List<clsPartyMasterModel> objListPartyModel = objPartyMasterService.funGetLinkLocCustomer(locCode, clientCode);
		if (objListPartyModel.size() > 0)
		{
			objPartyModel = objListPartyModel.get(0);
			propertyCode = objPartyModel.getStrPropCode();
		}

		// clsLocationMasterModel
		// objLocCode=objLocationMasterService.funGetObject(locCode,clientCode);

		List list = new ArrayList<>();
		StringBuilder sqlProdMRP = new StringBuilder();
		sqlProdMRP.append("select  strPickMRPForTaxCal from tblproductmaster where ");
		if (prodCode.length() > 8)
		{
			sqlProdMRP.append(" strBarCode='" + prodCode + "'  ");
		}
		else
		{
			sqlProdMRP.append(" strProdCode='" + prodCode + "'  ");
		}

		sqlProdMRP.append(" and strClientCode='" + clientCode + "' ");
		List listprodMRP = objGlobalFunctionsService.funGetList(sqlProdMRP.toString(), "sql");
		Object objprodMRP = (Object) listprodMRP.get(0);
		String prodMRP = objprodMRP.toString();

		if (prodMRP.equalsIgnoreCase("N"))
		{
			sqlProdMRP.setLength(0);
			sqlProdMRP.append(" SELECT a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, IFNULL(b.strSuppCode,'') AS strSuppCode, " + " IFNULL(c.strPName,'') AS strPName,a.strProdType,a.dblWeight, IFNULL(q.pervInvCode,''), " + " IFNULL(q.prevUnitPrice,0.0),a.dblCostRM, IFNULL(b.dblLastCost,0.0) " + " from tblproductmaster a " + " JOIN tblprodsuppmaster b " + " Join tblpartymaster c " + " LEFT JOIN " + " ( SELECT b.dteInvDate AS pervInvCode,a.dblUnitPrice AS prevUnitPrice,b.strInvCode, " + " a.strProdCode AS prodCode FROM tblinvoicedtl a,tblinvoicehd b " + " WHERE a.strProdCode='" + prodCode + "' AND a.strClientCode='" + clientCode + "' " + " AND b.strInvCode=a.strInvCode AND a.strCustCode='" + suppCode + "' " + " AND b.dteInvDate=( SELECT MAX(b.dteInvDate) " + " FROM tblinvoicedtl a,tblinvoicehd b WHERE a.strProdCode='" + prodCode + "' " + " AND a.strClientCode='" + clientCode + "'  AND b.strInvCode=a.strInvCode " + " AND a.strCustCode='" + suppCode + "')) AS q ON a.strProdCode=q.prodCode " + " where  a.strProdCode=b.strProdCode and ");
			if (prodCode.length() > 8)
			{
				sqlProdMRP.append(" a.strBarCode='" + prodCode + "'  ");
			}
			else
			{
				sqlProdMRP.append(" a.strProdCode='" + prodCode + "'  ");
			}
			sqlProdMRP.append(" and b.strSuppCode=c.strPCode ");

			if (objListPartyModel.size() > 0)
			{
				sqlProdMRP.append(" and c.strLocCode='" + locCode + "' and c.strPropCode='" + propertyCode + "' ");
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sqlProdMRP.toString(), prodCode, clientCode);
			}

			/*
			 * +
			 * "select a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, ifnull(b.strSuppCode,'') as strSuppCode,"
			 * +
			 * " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,ifnull(q.pervInvCode,''),ifnull(q.prevUnitPrice,0.0),a.dblCostRM,ifnull(b.dblLastCost,0.0) "
			 * +
			 * " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='"
			 * +clientCode+"' and b.strDefault='Y' " +
			 * " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='"
			 * +clientCode+"' " +
			 * " left join (select b.dteInvDate as pervInvCode,a.dblUnitPrice as prevUnitPrice,b.strInvCode,a.strProdCode as prodCode from tblinvoicedtl a,tblinvoicehd b "
			 * +" where a.strProdCode='"+prodCode+"' and a.strClientCode='"+
			 * clientCode+"' and b.strInvCode=a.strInvCode  "
			 * +" and a.strCustCode='"+suppCode+
			 * "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b "
			 * +" where a.strProdCode='"+prodCode+"' and a.strClientCode='"+
			 * clientCode+"' and b.strInvCode=a.strInvCode   " +" and a.strCustCode='"
			 * +suppCode+"')) as q on a.strProdCode=q.prodCode    " +
			 * " where  a.strProdCode='" +prodCode+"' and a.strClientCode='"+clientCode+"'"
			 */
			// );

			if (list.size() == 0)
			{

				sqlProdMRP.setLength(0);
				String propCode = req.getSession().getAttribute("propertyCode").toString();
				clsPropertySetupModel objSetUp = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
				if (objSetUp.getStrMultiCurrency().equalsIgnoreCase("N"))
				{
					sqlProdMRP.append("SELECT a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, " + " IFNULL(b.strSuppCode,'') AS strSuppCode, IFNULL(c.strPName,'') AS strPName,a.strProdType, " + "a.dblWeight, '',0.0,a.dblCostRM, IFNULL(a.dblCostRM,0.0) FROM tblproductmaster a , " + " tblprodsuppmaster b , tblpartymaster c 	WHERE ");
					if (prodCode.length() > 8)
					{
						sqlProdMRP.append(" a.strBarCode='" + prodCode + "'  ");
					}
					else
					{
						sqlProdMRP.append(" a.strProdCode='" + prodCode + "'  ");
					}
					sqlProdMRP.append(" and a.strProdCode=b.strProdCode  and b.strSuppCode=c.strPCode and a.strClientCode='" + clientCode + "'");
				}
				else
				{

					sqlProdMRP.append("SELECT a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, " + " IFNULL(b.strSuppCode,'') AS strSuppCode, IFNULL(c.strPName,'') AS strPName,a.strProdType, " + "a.dblWeight, '',0.0,d.dblPrice as dblCostRM, IFNULL(d.dblPrice,0.0) as dblCostRM FROM tblproductmaster a , " + " tblprodsuppmaster b , tblpartymaster c ,tblreorderlevel d	WHERE ");
					if (prodCode.length() > 8)
					{
						sqlProdMRP.append(" a.strBarCode='" + prodCode + "'  ");
					}
					else
					{
						sqlProdMRP.append(" a.strProdCode='" + prodCode + "'  ");
					}
					sqlProdMRP.append(" and a.strProdCode=b.strProdCode  and b.strSuppCode=c.strPCode and a.strClientCode='" + clientCode + "' " + " and d.strProdCode=a.strProdCode and d.strLocationCode='" + locCode + "' and d.strClientCode='" + clientCode + "' ");

				}
				list = objGlobalFunctionsService.funGetProductDataForTransaction(sqlProdMRP.toString(), prodCode, clientCode);
				if (list.size() == 0)
				{
					sqlProdMRP.setLength(0);

					sqlProdMRP.append("select a.strProdcode,a.strProdName,a.strUOM,a.dblUnitPrice, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,'',0.0,a.dblCostRM,ifnull(b.dblLastCost,0.0) " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' " + " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where ");
					if (prodCode.length() > 8)
					{
						sqlProdMRP.append(" a.strBarCode='" + prodCode + "'  ");
					}
					else
					{
						sqlProdMRP.append(" a.strProdCode='" + prodCode + "'  ");
					}
					sqlProdMRP.append(" and a.strClientCode='" + clientCode + "'");
					list = objGlobalFunctionsService.funGetProductDataForTransaction(sqlProdMRP.toString(), prodCode, clientCode);

				}
			}

		}
		else
		{
			sqlProdMRP.setLength(0);
			sqlProdMRP.append("select a.strProdcode,a.strProdName,a.strUOM,a.dblMRP, ifnull(b.strSuppCode,'') as strSuppCode," + " ifnull(c.strPName,'') as strPName,a.strProdType,a.dblWeight,a.dblCostRM,ifnull(b.dblLastCost,0.0) " + " from tblproductmaster a left outer join tblprodsuppmaster b on a.strProdCode=b.strProdCode and b.strClientCode='" + clientCode + "' and b.strDefault='Y' " + " left outer join tblpartymaster c on b.strSuppCode=c.strPCode and c.strClientCode='" + clientCode + "' " + " where ");
			if (prodCode.length() > 8)
			{
				sqlProdMRP.append(" a.strBarCode='" + prodCode + "'  ");
			}
			else
			{
				sqlProdMRP.append(" a.strProdCode='" + prodCode + "'  ");
			}
			sqlProdMRP.append(" and a.strClientCode='" + clientCode + "'");
			list = objGlobalFunctionsService.funGetProductDataForTransaction(sqlProdMRP.toString(), prodCode, clientCode);

		}

		if (list.size() == 0)
		{
			list.add("Invalid Product Code");
			return list;
		}
		else
		{
			return list;
		}

	}

	private String funDataSetInDeliveryChallan(clsInvoiceHdModel objInvHDModel, HttpServletRequest req)
	{

		boolean flg = true;
		clsDeliveryChallanHdModel objDcHdModel = new clsDeliveryChallanHdModel();
		long lastNo = 0;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String sqlFetchDc = "select strDCCode from tbldeliverychallanhd where strSoCode='" + objInvHDModel.getStrInvCode() + "' and strClientCode='" + objInvHDModel.getStrClientCode() + "' ";
		List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

		if (!listFetchDc.isEmpty())
		{
			Object objDc = listFetchDc.get(0);

			objDcHdModel = new clsDeliveryChallanHdModel(new clsDeliveryChallanHdModel_ID(objDc.toString(), clientCode));
		}
		else
		{

			lastNo = objGlobalFunctionsService.funGetLastNo("tbldeliverychallanhd", "DCCode", "intId", clientCode);
			String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
			String cd = objGlobalFunctions.funGetTransactionCode("DC", propCode, year);
			String strDCCode = cd + String.format("%06d", lastNo);
			objDcHdModel.setStrDCCode(strDCCode);
			objDcHdModel.setStrUserCreated(userCode);
			objDcHdModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objDcHdModel.setIntid(lastNo);
		}
		objDcHdModel.setStrSOCode(objInvHDModel.getStrInvCode());
		objDcHdModel.setDteDCDate(objInvHDModel.getDteInvDate());
		objDcHdModel.setStrAgainst(objInvHDModel.getStrAgainst());
		objDcHdModel.setStrPONo(objInvHDModel.getStrPONo());
		objDcHdModel.setStrNarration(objInvHDModel.getStrNarration());
		objDcHdModel.setStrPackNo(objInvHDModel.getStrPackNo());
		objDcHdModel.setStrLocCode(objInvHDModel.getStrLocCode());
		objDcHdModel.setStrVehNo(objInvHDModel.getStrVehNo());
		objDcHdModel.setStrMInBy(objInvHDModel.getStrMInBy());
		objDcHdModel.setStrTimeInOut(objInvHDModel.getStrTimeInOut());
		objDcHdModel.setStrUserModified(objInvHDModel.getStrUserModified());
		objDcHdModel.setDteLastModified(objInvHDModel.getDteLastModified());
		objDcHdModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
		objDcHdModel.setStrDktNo(objInvHDModel.getStrDktNo());
		objDcHdModel.setStrSAdd1(objInvHDModel.getStrSAdd1());
		objDcHdModel.setStrSAdd2(objInvHDModel.getStrSAdd2());
		objDcHdModel.setStrSCity(objInvHDModel.getStrSCity());
		objDcHdModel.setStrSCtry(objInvHDModel.getStrSCtry());
		objDcHdModel.setStrSerialNo(objInvHDModel.getStrSerialNo());
		objDcHdModel.setStrSPin(objInvHDModel.getStrSPin());
		objDcHdModel.setStrSState(objInvHDModel.getStrSState());
		objDcHdModel.setStrReaCode(objInvHDModel.getStrReaCode());
		objDcHdModel.setStrWarraValidity(objInvHDModel.getStrWarraValidity());
		objDcHdModel.setStrWarrPeriod(objInvHDModel.getStrWarrPeriod());
		objDcHdModel.setStrClientCode(objInvHDModel.getStrClientCode());
		objDcHdModel.setStrCustCode(objInvHDModel.getStrCustCode());
		objDcHdModel.setStrSettlementCode(objInvHDModel.getStrSettlementCode());
		objDcHdModel.setStrCloseDC("N");
		objDcHdModel.setStrDCNo("");
		objDeliveryChallanHdService.funAddUpdateDeliveryChallanHd(objDcHdModel);
		clsInvoiceModelDtl objInvDtlModel = null;
		List<clsInvoiceModelDtl> listInvDtl = objInvHDModel.getListInvDtlModel();
		if (!listInvDtl.isEmpty())
			objDeliveryChallanHdService.funDeleteDtl(objDcHdModel.getStrDCCode(), clientCode);
		{
			for (int i = 0; i < listInvDtl.size(); i++)
			{
				objInvDtlModel = listInvDtl.get(i);
				clsDeliveryChallanModelDtl objDcDtlModel = new clsDeliveryChallanModelDtl();
				objDcDtlModel.setStrDCCode(objDcHdModel.getStrDCCode());
				objDcDtlModel.setStrProdCode(objInvDtlModel.getStrProdCode());
				objDcDtlModel.setDblQty(objInvDtlModel.getDblQty());
				objDcDtlModel.setDblPrice(objInvDtlModel.getDblUnitPrice());
				objDcDtlModel.setDblWeight(objInvDtlModel.getDblWeight());
				objDcDtlModel.setStrProdType(objInvDtlModel.getStrProdType());
				objDcDtlModel.setStrPktNo(objInvDtlModel.getStrPktNo());
				objDcDtlModel.setStrRemarks(objInvDtlModel.getStrRemarks());
				objDcDtlModel.setStrInvoiceable(objInvDtlModel.getStrInvoiceable());
				objDcDtlModel.setStrSerialNo(objInvDtlModel.getStrInvoiceable());
				objDcDtlModel.setIntindex(objInvDtlModel.getIntindex());
				objDcDtlModel.setStrClientCode(objInvHDModel.getStrClientCode());

				objDeliveryChallanHdService.funAddUpdateDeliveryChallanDtl(objDcDtlModel);
			}
		}

		String sqlUpdateDC = "update tblinvsalesorderdtl set strDCCode='" + objDcHdModel.getStrDCCode() + "' where strInvCode='" + objInvHDModel.getStrInvCode() + "' ";
		objGlobalFunctionsService.funUpdateAllModule(sqlUpdateDC, "sql");
		return objDcHdModel.getStrDCCode();
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptDeliveryChallanInvoiceSlip", method = RequestMethod.GET)
	public void funOpenDeliverychallanInvoiceReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req)
	{

		try
		{

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String dcCode = req.getParameter("strDocCode").toString();
			String[] arrDcCode = dcCode.split(",");
			String dteDCForReportname = "";
			String fileReportname = "Deliverychallan_";
			String dccodes = "";
			req.getSession().removeAttribute("rptDcCode");
			String type = "pdf";

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			for (int cnt = 0; cnt < arrDcCode.length; cnt++)
			{

				String dcCodeSingle = arrDcCode[cnt].toString();
				dccodes = dccodes + dcCodeSingle + "#";
				JasperPrint jp = funDeliverychallanReportOfInvoice(dcCodeSingle, req);
				jprintlist.add(jp);
			}

			if (arrDcCode.length == 1)
			{
				dccodes = arrDcCode[0].replaceAll(",", "");
				String sql = "select strDCCode,Date(dteDCDate) from tbldeliverychallanhd where  strDCCode='" + dccodes + "' ";
				List listDCRow = objGlobalFunctionsService.funGetList(sql, "sql");
				if (listDCRow.size() > 0)
				{
					Object[] obj = (Object[]) listDCRow.get(0);

					dteDCForReportname = obj[1].toString();
					fileReportname = fileReportname + dccodes + "_" + dteDCForReportname + "_" + userCode;
					fileReportname.replaceAll(" ", "");
				}

			}
			else
			{
				fileReportname = fileReportname + dccodes + "_" + dteDCForReportname + "_" + userCode;
				fileReportname.replaceAll(" ", "");

			}

			if (jprintlist.size() > 0)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=" + fileReportname + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private JasperPrint funDeliverychallanReportOfInvoice(String dcCode, HttpServletRequest req)
	{

		JasperPrint jprintlist = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		try
		{
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strVehNo = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			String dteDCDate = "";
			String timeInWords = "";
			String strSOCode = "";
			String strTransName = "";
			clsNumberToWords obj = new clsNumberToWords();

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptDeliveryChallanSlipofInvoice.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = " select a.strDCCode,a.dteDCDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo,a.strSOCode from tbldeliverychallanhd a,tblpartymaster b where a.strDCCode='" + dcCode + "' " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");

			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				dteDCDate = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
				strSOCode = arrObj[10].toString();
			}

			String sqlTransporter = "select a.strVehCode,a.strVehNo ,b.strTransName from tbltransportermasterdtl a,tbltransportermaster b where a.strVehNo='" + strVehNo + "' " + "and a.strTransCode=b.strTransCode and a.strClientCode='" + clientCode + "' ";
			List listTransporter = objGlobalFunctionsService.funGetList(sqlTransporter, "sql");
			if (!listTransporter.isEmpty())
			{
				Object[] arrObj = (Object[]) listTransporter.get(0);
				strTransName = arrObj[2].toString();

			}

			ArrayList fieldList = new ArrayList();
			HashMap hm = new HashMap();
			String prodSql = "select d.strProdName,c.strExciseChapter,b.dblQty from tbldeliverychallanhd a,tbldeliverychallandtl b ,tblsubgroupmaster c,tblproductmaster d " + "where a.strDCCode='" + dcCode + "'  and a.strDCCode=b.strDCCode and b.strProdCode=d.strProdCode and d.strSGCode=c.strSGCode and a.strClientCode='" + clientCode + "' ";

			Map<String, Double> vatTaxAmtMap = new HashMap();
			Map<String, Double> vatTaxableAmtMap = new HashMap();
			List listprodDtl = objGlobalFunctionsService.funGetList(prodSql, "sql");

			for (int j = 0; j < listprodDtl.size(); j++)
			{
				clsInvoiceProdDtlReportBean objInvoiceProdDtlReportBean = new clsInvoiceProdDtlReportBean();
				Object[] objProdDtl = (Object[]) listprodDtl.get(j);

				objInvoiceProdDtlReportBean.setStrProdName(objProdDtl[0].toString());
				objInvoiceProdDtlReportBean.setStrExciseChapter(objProdDtl[1].toString());
				objInvoiceProdDtlReportBean.setDblQty(Double.parseDouble(objProdDtl[2].toString()));

				fieldList.add(objInvoiceProdDtlReportBean);
			}

			String[] datetime = dteDCDate.split(" ");
			dteDCDate = datetime[0];
			time = datetime[1];
			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("and");
			String[] wordmin = words[1].split("paisa");
			timeInWords = "Hours " + words[0] + "" + wordmin[0];

			String[] date = dteDCDate.split("-");
			dteDCDate = date[2] + "-" + date[1] + "-" + date[0];
			hm.put("dcCode", dcCode);
			hm.put("dteDcvDate", dteDCDate);
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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strVAT", objSetup.getStrVAT());
			hm.put("strCST", objSetup.getStrCST());
			hm.put("strVehNo", strVehNo);
			hm.put("time", time);
			hm.put("strRangeAdd", strRangeAdd);
			hm.put("strDivision", strDivision);
			hm.put("strRangeDiv", objSetup.getStrRangeDiv());
			hm.put("strDivisionAdd", objSetup.getStrDivisionAdd());
			hm.put("strCommi", objSetup.getStrCommi());
			hm.put("strMask", objSetup.getStrMask());
			hm.put("strPhone", objSetup.getStrPhone());
			hm.put("strFax", objSetup.getStrFax());
			hm.put("timeInWords", timeInWords);
			hm.put("invCode", strSOCode);
			hm.put("strTransName", strTransName);

			JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
			hm.put("ItemDataSource", beanCollectionDataSource);
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			jprintlist = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
			return jprintlist;
		}

	}

	@RequestMapping(value = "/SOInvoiceCheck", method = RequestMethod.GET)
	public @ResponseBody String funSOInvoiceCheck(@RequestParam("SOCode") String strSOCodes, @RequestParam("invDate") String dteInvDate, HttpServletRequest req, HttpServletResponse response)
	{

		dteInvDate = dteInvDate.split("-")[2] + "-" + dteInvDate.split("-")[1] + "-" + dteInvDate.split("-")[0];
		String retValue = "N";
		String[] strCodes = strSOCodes.split(",");
		for (String code : strCodes)
		{
			String sql = " select strSOCode from tblinvoicehd a " + "where date(a.dteInvDate)='" + dteInvDate + "' and a.strSOCode like '%" + code + "%' ";
			List listDtl = objGlobalFunctionsService.funGetList(sql, "sql");
			if (listDtl.size() > 0)
			{
				retValue = "Y";
			}

		}

		return retValue;
	}
	
	@RequestMapping(value = "/openRptInvoiceRetailReport", method = RequestMethod.GET)
	public void funOpenInvoiceRetailReport(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
	{

		// String InvCode=req.getParameter("rptInvCode").toString();
		req.getSession().removeAttribute("rptInvCode");
		type = "pdf";
		String[] arrInvCode = InvCode.split(",");
		req.getSession().removeAttribute("rptInvCode");

		InvCode = arrInvCode[0].toString();
		Connection con = objGlobalFunctions.funGetConnection(req);
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null)
		{
			objSetup = new clsPropertySetupModel();
		}
		String suppName = "";

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceGST.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.jpg");

		String challanDate = "";
		String PONO = "";
		String InvDate = "";
		String CustName = "";
		String dcCode = "";

		String sqlProdDtl = " select  c.strProdName,c.strProdNameMarathi,b.dblQty,"
				// + "c.dblCostRM,"
				+ " IFNULL(b.dblPrice,0.00),c.dblMRP, IFNULL(b.dblPrice,0.00) AS dblPrice, a.dteInvDate, " + " IFNULL(d.strPName,''),ifnull(e.strDCCode,''),ifnull(e.dteDCDate,''),ifnull(e.strPONo,''), " + " b.strProdCode,f.strExciseChapter,g.dblValue as discAmt,IFNULL(d.strBAdd1,''),IFNULL(d.strBAdd2,''), " + " IFNULL(d.strBState,''),IFNULL(d.strBPin,'') ,IFNULL(d.strSAdd1,''),IFNULL(d.strSAdd2,''), " + " IFNULL(d.strSState,''),IFNULL(d.strSPin,'') ,ifNull(strCST,''),b.dblProdDiscAmount from tblinvoicehd a left outer join tblinvoicedtl b on a.strInvCode=b.strInvCode   " + " left outer join tblproductmaster c  on b.strProdCode=c.strProdCode left outer join tblpartymaster d on a.strCustCode=d.strPCode " + " left outer join tbldeliverychallanhd e on a.strSOCode=e.strDCCode " + " left outer join tblsubgroupmaster f on f.strSGCode=c.strSGCode " + " left outer join tblinvprodtaxdtl  g on a.strInvCode=g.strInvCode and a.strCustCode=g.strCustCode  " + " and b.strProdCode=g.strProdCode and g.strDocNo='Disc'   " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";

		String bAddress = "";
		String bState = "";
		String bPin = "";
		String sAddress = "";
		String sState = "";
		String sPin = "";
		String custGSTNo = "";
		double totalInvoiceValue = 0.0;
		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlProdDtl, "sql");
		List<clsInvoiceDtlBean> dataList = new ArrayList<clsInvoiceDtlBean>();
		Map<Double, Double> hmCGSTCalculateTax = new HashMap<Double, Double>();
		Map<Double, Double> hmSGSTCalculateTax = new HashMap<Double, Double>();
		if (listProdDtl.size() > 0)
		{
			for (int i = 0; i < listProdDtl.size(); i++)
			{
				Object[] obj = (Object[]) listProdDtl.get(i);
				clsInvoiceDtlBean objDtlBean = new clsInvoiceDtlBean();
				objDtlBean.setStrProdName(obj[0].toString());
				objDtlBean.setStrProdNamemarthi(obj[1].toString());
				objDtlBean.setDblQty(Double.parseDouble(obj[2].toString()));
				objDtlBean.setDblCostRM(Double.parseDouble(obj[3].toString()));
				objDtlBean.setDblMRP(Double.parseDouble(obj[4].toString()));
				objDtlBean.setDblPrice(Double.parseDouble(obj[5].toString()));
				InvDate = objGlobalFunctions.funGetDate("dd-MM-yyyy", obj[6].toString());
				CustName = obj[7].toString();
				challanDate = obj[9].toString();
				PONO = obj[10].toString();
				dcCode = obj[8].toString();
				objDtlBean.setStrHSN(obj[12].toString());
				// objDtlBean.setDblDisAmt(Double.parseDouble(obj[13].toString())*Double.parseDouble(obj[2].toString()));
				bAddress = obj[14].toString() + " " + obj[15].toString();
				bState = obj[16].toString();
				bPin = obj[17].toString();
				objDtlBean.setDblDisAmt(Double.parseDouble(obj[23].toString()));
				sAddress = obj[18].toString() + " " + obj[19].toString();
				sState = obj[20].toString();
				sPin = obj[21].toString();
				custGSTNo = obj[22].toString();
				
				double qty=Double.parseDouble(obj[2].toString());
				double rate=Double.parseDouble(obj[3].toString());
				double subTotal=qty*rate;
				double discAmt=Double.parseDouble(obj[23].toString());								
				double netTotal=subTotal-discAmt;
				totalInvoiceValue = totalInvoiceValue + netTotal;

				String sqlQuery = " select b.strTaxCode,b.dblPercent,a.dblValue,b.strShortName,a.dblTaxableAmt " + " from tblinvprodtaxdtl a,tbltaxhd b " + " where a.strDocNo=b.strTaxCode and a.strInvCode='" + InvCode + "' " + " and  a.strProdCode='" + obj[11].toString() + "' and a.strClientCode='" + clientCode + "'  ";

				List listProdGST = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

				if (listProdGST.size() > 0)
				{
					for (int j = 0; j < listProdGST.size(); j++)
					{
						double cGStAmt = 0.0;
						double sGStAmt = 0.0;
						Object[] objGST = (Object[]) listProdGST.get(j);
						if (objGST[3].toString().equalsIgnoreCase("CGST"))
						{
							objDtlBean.setDblCGSTPer(Double.parseDouble(objGST[1].toString()));
							objDtlBean.setDblCGSTAmt(Double.parseDouble(objGST[2].toString()));
							objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
							totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
						}
						else if (objGST[3].toString().equalsIgnoreCase("SGST"))
						{
							objDtlBean.setDblSGSTPer(Double.parseDouble(objGST[1].toString()));
							objDtlBean.setDblSGSTAmt(Double.parseDouble(objGST[2].toString()));
							objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
							totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
						}
						else
						{
							objDtlBean.setDblNonGSTTaxPer(Double.parseDouble(objGST[1].toString()));
							objDtlBean.setDblNonGSTTaxAmt(Double.parseDouble(objGST[2].toString()));
							objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
							totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
						}
					}
				}
				dataList.add(objDtlBean);

			}
		}

		try
		{
			String shortName = " Paisa";
			String currCode = req.getSession().getAttribute("currencyCode").toString();
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);
			if (objCurrModel != null)
			{
				shortName = objCurrModel.getStrShortName();
			}

			clsNumberToWords obj1 = new clsNumberToWords();
			String totalInvoiceValueInWords = obj1.getNumberInWorld(totalInvoiceValue, shortName);

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
			hm.put("InvCode", InvCode);
			hm.put("InvDate", InvDate);
			hm.put("challanDate", challanDate);
			hm.put("PONO", PONO);
			hm.put("CustName", CustName);
			hm.put("PODate", challanDate);
			hm.put("dcCode", dcCode);
			hm.put("dataList", dataList);
			hm.put("bAddress", bAddress);
			hm.put("bState", bState);
			hm.put("bPin", bPin);
			hm.put("sAddress", sAddress);
			hm.put("sState", sState);
			hm.put("sPin", sPin);
			hm.put("totalInvoiceValueInWords", totalInvoiceValueInWords);
			hm.put("totalInvoiceValue", totalInvoiceValue);
			hm.put("strGSTNo.", objSetup.getStrCST());
			hm.put("custGSTNo", custGSTNo);

			// ////////////

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);

			if (jp != null)
			{

				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (type.trim().equalsIgnoreCase("pdf"))
				{

					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=rptTaxInvoiceRetail" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();

				}
				else
				{

					// code for Exporting FLR 3 in ExcelSheet

					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xls");
					exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					resp.setHeader("Content-Disposition", "attachment;filename=" + "rptTaxInvoiceRetail" + userCode + ".xlsx");
					exporter.exportReport();

				}

			}

			// ///////////////

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/opentxtInvoice", method = RequestMethod.GET)
	public void funOpenInvoiceTextReport(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
	{
		funCallTextInvoice(InvCode, "pdf", resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallTextInvoice(String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{

			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			SimpleDateFormat sfyy = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
			SimpleDateFormat sf = new SimpleDateFormat("dd-mm-yyyy hh:mm a");
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			InvCode = InvCode.split(",")[0].toString();
			clsInvoiceHdModel objModel = objInvoiceHdService.funGetInvoiceHd(InvCode, clientCode);

			String reportName = "";
			if (type.equalsIgnoreCase("Text"))
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceTextSlip.jrxml");
			}
			else
			{
				reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceTextSlip.jrxml");
			}

			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			String strInvCode = "", dtInvDate = "", dtInvTime = "", strUserName = "";

			strInvCode = objModel.getStrInvCode();
			Date dt = sfyy.parse(objModel.getDteInvDate());
			dtInvDate = sf.format(dt);
			String[] dtNTime = dtInvDate.split(" ");
			dtInvDate = dtNTime[0];
			// dtInvDate
			// =dtInvDate.split("-")[2]+"-"+dtInvDate.split("-")[1]+"-"+dtInvDate.split("-")[0];
			dtInvTime = dtNTime[1] + " " + dtNTime[2];

			strUserName = objModel.getStrUserCreated();

			String sqlDtlQuery = "  select d.strExciseChapter,c.strProdName,b.dblQty*b.dblUOMConversion,b.dblPrice," + " (b.dblQty*b.dblUOMConversion*b.dblPrice)  as TotalValue,c.strPartNo,c.strUOM " + " from tblinvoicehd a,tblinvoicedtl b ,tblproductmaster c ,tblsubgroupmaster d where " + " a.strInvCode=b.strInvCode and b.strProdCode=c.strProdCode and c.strSGCode=d.strSGCode " + " and a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "' ";

			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlDtlQuery, "sql");
			List<clsInvoiceTextBean> listDtlBean = new ArrayList<clsInvoiceTextBean>();
			double grandTotal = 0.00;
			for (int j = 0; j < listProdDtl.size(); j++)
			{
				clsInvoiceTextBean objModelDtl = new clsInvoiceTextBean();
				Object[] prodArr = (Object[]) listProdDtl.get(j);
				{
					objModelDtl.setStrHSNNo(prodArr[0].toString());
					if (prodArr[2].toString().length() > 0)
					{
						objModelDtl.setStrProdName(prodArr[5].toString());
					}
					else
					{
						objModelDtl.setStrProdName(prodArr[1].toString());
					}

					objModelDtl.setDblQty(Double.parseDouble(prodArr[2].toString()));
					objModelDtl.setDblPrice(Double.parseDouble(prodArr[3].toString()));
					objModelDtl.setDblTotalValue(Double.parseDouble(prodArr[4].toString()));
					objModelDtl.setStrUOM(prodArr[6].toString());
					grandTotal += Double.parseDouble(prodArr[4].toString());

					listDtlBean.add(objModelDtl);
				}
			}

			/*
			 * String sqlTax=
			 * " select a.strInvCode,a.strTaxCode,b.strTaxDesc,a.dblCGSTPer,sum(a.dblCGSTAmt),a.dblSGSTPer,sum(a.dblSGSTAmt) , b.strGSTNo "
			 * + " from tblinvtaxgst a,tbltaxhd b " + " where a.strInvCode='"+InvCode
			 * +"' and a.strTaxCode = b.strTaxCode  " +
			 * " group by a.strInvCode,a.strTaxCode ";
			 */
			String sqlTax = "  select a.strInvCode,a.strTaxCode,b.strTaxDesc,b.dblPercent,a.dblTaxAmt, b.strShortName " + " from tblinvtaxdtl a,tbltaxhd b  where a.strInvCode='" + InvCode + "' and a.strTaxCode = b.strTaxCode " + " group by a.strInvCode,a.strTaxCode   ";

			List listProdTaxDtl = objGlobalFunctionsService.funGetDataList(sqlTax, "sql");
			List<clsInvoiceTaxGSTBean> listDtlTaxBean = new ArrayList<clsInvoiceTaxGSTBean>();
			// String strGSTNo="";
			for (int j = 0; j < listProdTaxDtl.size(); j++)
			{
				clsInvoiceTaxGSTBean objModelTaxDtl = new clsInvoiceTaxGSTBean();
				Object[] taxArr = (Object[]) listProdTaxDtl.get(j);
				{
					objModelTaxDtl.setStrTaxDesc(taxArr[2].toString());

					objModelTaxDtl.setDblTaxPer(Double.parseDouble(taxArr[3].toString()));
					objModelTaxDtl.setDblTaxAmt(Double.parseDouble(taxArr[4].toString()));
					grandTotal += Double.parseDouble(taxArr[4].toString());

					/*
					 * if(Double.parseDouble(taxArr[3].toString())==0.00) { objModelTaxDtl
					 * .setDblTaxPer(Double.parseDouble(taxArr[5].toString())); objModelTaxDtl
					 * .setDblTaxAmt(Double.parseDouble(taxArr[6].toString()));
					 * grandTotal+=Double.parseDouble(taxArr[6].toString()) ; }else {
					 * objModelTaxDtl.setDblTaxPer(Double.parseDouble(taxArr [3].toString()));
					 * objModelTaxDtl.setDblTaxAmt(Double.parseDouble (taxArr[4].toString()));
					 * grandTotal+= Double.parseDouble(taxArr[4].toString()); }
					 */
					// strGSTNo=taxArr[7].toString();
					listDtlTaxBean.add(objModelTaxDtl);

				}
			}

			List<clsInvSettlementdtlModel> objInvSettleList = objInvoiceHdService.funGetListInvSettlementdtl(InvCode, clientCode);
			List<clsInvSettlementdtlModel> listSettleDtl = new ArrayList<clsInvSettlementdtlModel>();
			for (int i = 0; i < objInvSettleList.size(); i++)
			{
				clsInvSettlementdtlModel objSett = objInvSettleList.get(i);
				clsSettlementMasterModel objSettMaster = objSttlementMasterService.funGetObject(objSett.getStrSettlementCode(), clientCode);
				objSett.setStrSettlementName(objSettMaster.getStrSettlementDesc());
				listSettleDtl.add(objSett);
			}

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
			hm.put("strInvCode", strInvCode);
			hm.put("dtInvDate", dtInvDate);
			hm.put("dtInvTime", dtInvTime);
			hm.put("strUserName", strUserName);
			hm.put("strGSTNo", objSetup.getStrCST());
			hm.put("strInvNote", objSetup.getStrInvNote());
			hm.put("grandTotal", grandTotal);
			hm.put("listDtlBean", listDtlBean);
			hm.put("listDtlTaxBean", listDtlTaxBean);
			hm.put("listSettleDtl", listSettleDtl);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);

			if (jprintlist.size() > 0)
			{
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (type.trim().equalsIgnoreCase("pdf"))
				{
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=" + "rptInvTextSlip_." + type.trim());
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
				else
				{

					if (type.trim().equalsIgnoreCase("Text"))
					{
						ServletOutputStream outStream = resp.getOutputStream();
						JRTextExporter exporterTxt = new JRTextExporter();
						exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporterTxt.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Float(6.55));// 6.55
																											// //6
						exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Float(11.9)); // 11//10
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptInvTextSlip_" + InvCode + "_" + userCode + ".txt");
						resp.setContentType("application/text");
						exporterTxt.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
					else
					{

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xlsx");
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptInvTextSlip_." + type.trim());
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
				}
			}
			else
			{
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				try
				{
					resp.getWriter().append("No Record Found");
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void funStockAdjustment(clsInvoiceHdModel objInVModel, HttpServletRequest req, String customerLocCode, double dblCurrencyConv)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String stkAdjCodeLostNo = "";
		String SACode = "";
		long lastNo = 0;
		String oldSACode = objInVModel.getStrDktNo();
		clsStkAdjustmentHdModel objHdModel;
		if (!oldSACode.equals(""))
		{
			String deleteDtlSql = "delete from tblstockadjustmentdtl  " + " where strSACode= '" + oldSACode + "' and strClientCode='" + objInVModel.getStrClientCode() + "' ";
			objDeleteTranServerice.funDeleteRecord(deleteDtlSql, "sql");

			String deleteHdSql = "delete from tblstockadjustmenthd  " + " where strSACode= '" + oldSACode + "' and strClientCode='" + objInVModel.getStrClientCode() + "' ";
			objDeleteTranServerice.funDeleteRecord(deleteHdSql, "sql");

			objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(oldSACode, objInVModel.getStrClientCode()));
			objHdModel.setIntId(lastNo);
			objHdModel.setDtSADate(objInVModel.getDteInvDate());
			objHdModel.setStrLocCode(customerLocCode);
			objHdModel.setStrNarration("AutoGenerated by Invoice:" + objInVModel.getStrInvCode());
			objHdModel.setStrUserCreated(objInVModel.getStrUserCreated());
			objHdModel.setStrUserModified(objInVModel.getStrUserCreated());
			objHdModel.setDtCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHdModel.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHdModel.setStrReasonCode(objInVModel.getStrReaCode());
			objHdModel.setDblTotalAmt(0.00);
			objHdModel.setStrConversionUOM("RecUOM");
			objHdModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));

		}
		else
		{
			String[] invDate = objInVModel.getDteInvDate().split("-");
			String dateInvoice = invDate[2] + "-" + invDate[1] + "-" + invDate[0];
			String strStkCode = objGlobalFunctions.funGenerateDocumentCode("frmStockAdjustment", dateInvoice, req);

			objHdModel = new clsStkAdjustmentHdModel(new clsStkAdjustmentHdModel_ID(strStkCode, objInVModel.getStrClientCode()));
			objHdModel.setIntId(lastNo);
			objHdModel.setDtSADate(objInVModel.getDteInvDate());
			objHdModel.setStrLocCode(customerLocCode);
			objHdModel.setStrNarration("AutoGenerated by Invoice:" + objInVModel.getStrInvCode());
			objHdModel.setStrUserModified(objInVModel.getStrUserCreated());
			objHdModel.setDtLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			objHdModel.setStrReasonCode(objInVModel.getStrReaCode());
			objHdModel.setDblTotalAmt(0.00);
			objHdModel.setStrConversionUOM("RecUOM");
			objHdModel.setDtCreatedDate(objInVModel.getDteCreatedDate());
			objHdModel.setStrUserCreated(objInVModel.getStrUserCreated());
			objHdModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
		}

		String grnPropertyCode = "";
		clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(objHdModel.getStrLocCode(), clientCode);
		if (objLocModel != null)
		{
			grnPropertyCode = objLocModel.getStrPropertyCode();
		}
		String sqlSupp = "  select a.strDebtorCode from tblpartymaster a where a.strPropCode='" + grnPropertyCode + "' and a.strPType='supp' and a.strClientCode='" + clientCode + "' ";
		List listSuppCode = objGlobalFunctionsService.funGetList(sqlSupp, "sql");
		String scCode = "";
		if (listSuppCode.size() > 0)
		{
			scCode = listSuppCode.get(0).toString();
			objHdModel.setStrNarration("AutoGenerated by Invoice:" + objInVModel.getStrInvCode() + ":SCCode:" + scCode);
		}

		objStkAdjService.funAddUpdate(objHdModel);
		String sqlInvoice = " update tblinvoicehd set strDktNo='" + objHdModel.getStrSACode() + "' where strInvCode='" + objInVModel.getStrInvCode() + "' and strClientCode='" + objHdModel.getStrClientCode() + "' ";
		objGlobalFunctionsService.funUpdateAllModule(sqlInvoice, "sql");

		double totalAmt = 0.00;
		for (clsInvoiceModelDtl objInVdtl : objInVModel.getListInvDtlModel())
		{
			clsStkAdjustmentDtlModel objDtlModel = new clsStkAdjustmentDtlModel();
			totalAmt += objInVdtl.getDblQty() * objInVdtl.getDblUnitPrice();
			objDtlModel.setStrSACode(objHdModel.getStrSACode());
			objDtlModel.setStrProdCode(objInVdtl.getStrProdCode());
			objDtlModel.setDblQty(objInVdtl.getDblQty());
			objDtlModel.setStrType("In");
			objDtlModel.setDblPrice(objInVdtl.getDblPrice());
			objDtlModel.setDblWeight(objInVdtl.getDblWeight());
			objDtlModel.setStrProdChar(" ");
			objDtlModel.setIntIndex(0);
			objDtlModel.setStrClientCode(objHdModel.getStrClientCode());
			objDtlModel.setStrRemark("AutoGenerated by Invoice:" + objInVModel.getStrInvCode() + ":Prod Code:" + objInVdtl.getStrProdCode() + ":Qty:" + objInVdtl.getDblQty());
			objDtlModel.setDblRate(objInVdtl.getDblUnitPrice());
			objDtlModel.setStrWSLinkedProdCode(objInVdtl.getStrProdCode());

			String sql_Conversion = "select dblReceiveConversion,dblIssueConversion,dblRecipeConversion, " + " strReceivedUOM,strIssueUOM,strRecipeUOM " + " from tblproductmaster where strProdCode='" + objInVdtl.getStrProdCode() + "' " + " and strClientCode='" + objHdModel.getStrClientCode() + "' ";
			List listConv = objGlobalFunctionsService.funGetDataList(sql_Conversion, "sql");
			String strReceivedUOM = "";
			String strIssueUOM = "";
			String strRecipeUOM = "";
			BigDecimal recipe = new BigDecimal(0.00);
			double conversionRatio = 1;
			String Displyqty = "";
			for (int k = 0; k < listConv.size(); k++)
			{
				Object[] convArr = (Object[]) listConv.get(k);
				BigDecimal issue = (BigDecimal) convArr[0];
				recipe = (BigDecimal) convArr[2];
				conversionRatio = 1 / issue.doubleValue() / recipe.doubleValue();
				strReceivedUOM = convArr[3].toString();
				strIssueUOM = convArr[4].toString();
				strRecipeUOM = convArr[5].toString();

				Displyqty = objInVdtl.getDblQty() + " " + strReceivedUOM + " " + strRecipeUOM;
			}
			objDtlModel.setStrDisplayQty(Displyqty);
			objDtlModel.setStrParentCode(objInVdtl.getStrProdCode());

			objStkAdjService.funAddUpdateDtl(objDtlModel);
			double priceInCurr = objInVdtl.getDblPrice() / dblCurrencyConv;
			funUpdatePurchagesPricePropertywise(objInVdtl.getStrProdCode(), customerLocCode, objInVModel.getStrClientCode(), objInVdtl.getDblPrice());
		}
		totalAmt = totalAmt + objInVModel.getDblTaxAmt();
		String sqlSAHd = " update tblstockadjustmenthd set dblTotalAmt='" + totalAmt + "' where strSACode='" + objHdModel.getStrSACode() + "' and strClientCode='" + objHdModel.getStrClientCode() + "' ";
		objGlobalFunctionsService.funUpdateAllModule(sqlSAHd, "sql");

		funGenrateJVforAnotherPropertyLikeGRN(objHdModel, objInVModel, objInVModel.getListInvDtlModel(), objInVModel.getListInvTaxDtlModel(), clientCode, userCode, propertyCode, req);
	}

	private void funUpdatePurchagesPricePropertywise(String prodCode, String locCode, String clientCode, double price)
	{
		objProductMasterService.funDeleteProdReorderLoc(prodCode, locCode, clientCode);
		clsProductReOrderLevelModel objProdReOrder = new clsProductReOrderLevelModel(new clsProductReOrderLevelModel_ID(locCode, clientCode, prodCode));
		objProdReOrder.setDblReOrderLevel(0);
		objProdReOrder.setDblReOrderQty(0);
		objProdReOrder.setDblPrice(price);
		objProductMasterService.funAddUpdateProdReOrderLvl(objProdReOrder);

	}

	public void funSaveAudit(String saCode, String strTransMode, HttpServletRequest req)
	{
		try
		{
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsInvoiceHdModel stkAdjHd = objInvoiceHdService.funGetInvoiceDtl(saCode, clientCode);

			List listStkAdjDtl = stkAdjHd.getListInvDtlModel();
			if (null != stkAdjHd)
			{
				if (null != listStkAdjDtl && listStkAdjDtl.size() > 0)
				{
					String sql = "select count(*)+1 from tblaudithd where left(strTransCode,12)='" + stkAdjHd.getStrInvCode() + "'";
					List list = objGlobalFunctionsService.funGetList(sql, "sql");

					if (!list.isEmpty())
					{
						String count = list.get(0).toString();
						clsAuditHdModel model = funPrepairAuditHdModel(stkAdjHd);
						if (strTransMode.equalsIgnoreCase("Deleted"))
						{
							model.setStrTransCode(stkAdjHd.getStrInvCode());
						}
						else
						{
							model.setStrTransCode(stkAdjHd.getStrInvCode() + "-" + count);
						}
						model.setStrClientCode(clientCode);
						model.setStrTransMode(strTransMode);
						model.setStrUserAmed(userCode);
						model.setDtLastModified(objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd"));
						model.setStrUserModified(userCode);
						objGlobalFunctionsService.funSaveAuditHd(model);
						for (int i = 0; i < listStkAdjDtl.size(); i++)
						{
							clsInvoiceModelDtl stkAdjDtl = (clsInvoiceModelDtl) listStkAdjDtl.get(i);
							clsAuditDtlModel AuditMode = new clsAuditDtlModel();
							AuditMode.setStrTransCode(model.getStrTransCode());
							AuditMode.setStrProdCode(stkAdjDtl.getStrProdCode());
							AuditMode.setDblQty(stkAdjDtl.getDblQty());
							AuditMode.setDblUnitPrice(stkAdjDtl.getDblBillRate());
							AuditMode.setDblTotalPrice(stkAdjDtl.getDblPrice());
							AuditMode.setStrRemarks(stkAdjDtl.getStrRemarks());
							AuditMode.setStrType("");
							AuditMode.setStrClientCode(stkAdjHd.getStrClientCode());
							AuditMode.setStrUOM("");
							AuditMode.setStrAgainst("");
							AuditMode.setStrCode("");
							AuditMode.setStrTaxType("");
							AuditMode.setStrPICode("");

							objGlobalFunctionsService.funSaveAuditDtl(AuditMode);
						}
					}
				}
			}
			// }

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	private clsAuditHdModel funPrepairAuditHdModel(clsInvoiceHdModel stkAdjHd)
	{
		clsAuditHdModel AuditHdModel = new clsAuditHdModel();
		if (stkAdjHd != null)
		{
			AuditHdModel.setStrTransCode(stkAdjHd.getStrInvCode());
			AuditHdModel.setDtTransDate(stkAdjHd.getDteInvDate());
			AuditHdModel.setStrTransType("Invoice");
			AuditHdModel.setStrLocCode(stkAdjHd.getStrLocCode());
			AuditHdModel.setStrAuthorise(stkAdjHd.getStrAuthorise());
			AuditHdModel.setStrNarration(stkAdjHd.getStrNarration());
			AuditHdModel.setDblTotalAmt(stkAdjHd.getDblTotalAmt());
			AuditHdModel.setDtDateCreated(stkAdjHd.getDteCreatedDate());
			AuditHdModel.setStrUserCreated(stkAdjHd.getStrUserCreated());
			AuditHdModel.setStrNo("");
			AuditHdModel.setStrCode(stkAdjHd.getStrReaCode());
			AuditHdModel.setStrMInBy("");
			AuditHdModel.setStrTimeInOut("");
			AuditHdModel.setStrVehNo("");
			AuditHdModel.setStrGRNCode("");
			AuditHdModel.setStrSuppCode("");
			AuditHdModel.setStrClosePO("");
			AuditHdModel.setStrExcise("");
			AuditHdModel.setStrCloseReq("");
			AuditHdModel.setStrWoCode("");
			AuditHdModel.setStrBillNo("");
			AuditHdModel.setDblWOQty(0);
			AuditHdModel.setStrRefNo("");
			AuditHdModel.setStrShipmentMode("");
			AuditHdModel.setStrPayMode("");
			AuditHdModel.setStrLocBy("");
			AuditHdModel.setStrLocOn("");
			AuditHdModel.setStrAgainst("");
		}
		return AuditHdModel;
	}



	private String funGenrateJVforAnotherPropertyLikeGRN(clsStkAdjustmentHdModel objHDModel, clsInvoiceHdModel objInvModel, List<clsInvoiceModelDtl> listDtlModel, List<clsInvoiceTaxDtlModel> listTaxDtl, String clientCode, String userCode, String propCode, HttpServletRequest req)
	{
		JSONObject jObjJVData = new JSONObject();

		JSONArray jArrJVdtl = new JSONArray();
		JSONArray jArrJVDebtordtl = new JSONArray();
		String jvCode = "";
		String suppCode = "";
		double currConversion = 1;

		clsLocationMasterModel objLocModel = objLocationMasterService.funGetObject(objHDModel.getStrLocCode(), clientCode);
		if (objLocModel != null)
		{
			propCode = objLocModel.getStrPropertyCode();
		}
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		if (objSetup != null)
		{
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
			if (objCurrModel != null)
			{
				currConversion = objCurrModel.getDblConvToBaseCurr();

			}

		}
		String sqlSupp = "  select a.strPCode from tblpartymaster a where a.strPropCode='" + propCode + "' and a.strPType='supp' and a.strClientCode='" + clientCode + "' ";
		List listSuppCode = objGlobalFunctionsService.funGetList(sqlSupp, "sql");
		if (listSuppCode.size() > 0)
		{
			suppCode = listSuppCode.get(0).toString();

			String saCode = objHDModel.getStrSACode();
			String invDate = objInvModel.getDteInvDate().split(" ")[0];
			double debitAmt = (objInvModel.getDblGrandTotal());
			clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(suppCode, clientCode, propCode, "Customer", "Sale");
			if (objLinkCust != null)
			{
				if (objInvModel.getStrInvNo().equals(""))
				{
					jObjJVData.put("strVouchNo", "");
					jObjJVData.put("strNarration", "JV Generated by GRN:" + objHDModel.getStrSACode());
					jObjJVData.put("strSancCode", "");
					jObjJVData.put("strType", "");
					jObjJVData.put("dteVouchDate", invDate);
					jObjJVData.put("intVouchMonth", 1);
					jObjJVData.put("dblAmt", debitAmt);
					jObjJVData.put("strTransType", "R");
					jObjJVData.put("strTransMode", "A");
					jObjJVData.put("strModuleType", "AP");
					jObjJVData.put("strMasterPOS", "WEBSTOCK");
					jObjJVData.put("strUserCreated", userCode);
					jObjJVData.put("strUserEdited", userCode);
					jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("strClientCode", clientCode);
					jObjJVData.put("strPropertyCode", propCode);

				}
				else
				{
					jObjJVData.put("strVouchNo", objHDModel.getStrSACode());
					jObjJVData.put("strNarration", "JV Generated by GRN:" + objHDModel.getStrSACode());
					jObjJVData.put("strSancCode", "");
					jObjJVData.put("strType", "");
					jObjJVData.put("dteVouchDate", invDate);
					jObjJVData.put("intVouchMonth", 1);
					jObjJVData.put("dblAmt", debitAmt);
					jObjJVData.put("strTransType", "R");
					jObjJVData.put("strTransMode", "A");
					jObjJVData.put("strModuleType", "AP");
					jObjJVData.put("strMasterPOS", "WEBSTOCK");
					jObjJVData.put("strUserCreated", userCode);
					jObjJVData.put("strUserEdited", userCode);
					jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("strClientCode", clientCode);
					jObjJVData.put("strPropertyCode", propCode);

				}
				// jvhd entry end

				// jvdtl entry Start
				for (clsInvoiceModelDtl objDtl : listDtlModel)
				{

					JSONObject jObjDtl = new JSONObject();

					clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
					clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Sale");
					if (objProdModle != null && objLinkSubGroup != null)
					{
						jObjDtl.put("strVouchNo", "");
						jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
						jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
						jObjDtl.put("strCrDr", "Dr");
						jObjDtl.put("dblDrAmt", (objDtl.getDblQty() * objDtl.getDblUnitPrice()) + objInvModel.getDblTaxAmt());
						jObjDtl.put("dblCrAmt", 0.00);
						jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
						jObjDtl.put("strOneLine", "R");
						jObjDtl.put("strClientCode", clientCode);
						jObjDtl.put("strPropertyCode", propCode);
						jArrJVdtl.add(jObjDtl);
					}
				}

				if (listTaxDtl != null)
				{
					for (clsInvoiceTaxDtlModel objTaxDtl : listTaxDtl)
					{
						JSONObject jObjTaxDtl = new JSONObject();
						clsLinkUpHdModel objLinkTax = objLinkupService.funGetARLinkUp(objTaxDtl.getStrTaxCode(), clientCode, propCode, "Tax", "Sale");
						if (objLinkTax != null)
						{
							jObjTaxDtl.put("strVouchNo", "");
							jObjTaxDtl.put("strAccountCode", objLinkTax.getStrAccountCode());
							jObjTaxDtl.put("strAccountName", objLinkTax.getStrMasterDesc());
							jObjTaxDtl.put("strCrDr", "Dr");
							jObjTaxDtl.put("dblDrAmt", objTaxDtl.getDblTaxAmt());
							jObjTaxDtl.put("dblCrAmt", 0.00);
							jObjTaxDtl.put("strNarration", "WS Tax Desc :" + objTaxDtl.getStrTaxDesc());
							jObjTaxDtl.put("strOneLine", "R");
							jObjTaxDtl.put("strClientCode", clientCode);
							jObjTaxDtl.put("strPropertyCode", propCode);
							jArrJVdtl.add(jObjTaxDtl);
						}
					}
				}
				JSONObject jObjCustDtl = new JSONObject();
				jObjCustDtl.put("strVouchNo", "");
				jObjCustDtl.put("strAccountCode", "");
				jObjCustDtl.put("strAccountName", "");
				jObjCustDtl.put("strCrDr", "Cr");
				jObjCustDtl.put("dblDrAmt", 0.00);
				jObjCustDtl.put("dblCrAmt", debitAmt);
				jObjCustDtl.put("strNarration", "GRN Supplier");
				jObjCustDtl.put("strOneLine", "R");
				jObjCustDtl.put("strClientCode", clientCode);
				jObjCustDtl.put("strPropertyCode", propCode);
				jArrJVdtl.add(jObjCustDtl);

				jObjJVData.put("ArrJVDtl", jArrJVdtl);

				// jvdtl entry end

				// jvDebtor detail entry start
				/*
				 * String sql =
				 * " select a.strGRNCode,a.dblTotal,b.strDebtorCode,b.strPName,date(a.dtGRNDate),"
				 * + " a.strNarration ,date(a.dtDueDate),a.strBillNo" +
				 * " from dbwebmms.tblgrnhd a,dbwebmms.tblpartymaster b  " +
				 * " where a.strSuppCode =b.strPCode  " +
				 * " and a.strGRNCode='"+objHDModel.getStrSACode()+"' " +
				 * " and a.strClientCode='" +objInvModel.getStrClientCode()+"'   " ; List
				 * listDebtorDtl=objGlobalFunctionsService .funGetList(sql,"sql"); for(int
				 * i=0;i<listDebtorDtl.size();i++) {
				 */
				JSONObject jObjDtl = new JSONObject();
				// Object[] ob=(Object[])listDebtorDtl.get(i);
				jObjDtl.put("strVouchNo", "");
				jObjDtl.put("strDebtorCode", objLinkCust.getStrAccountCode());
				jObjDtl.put("strDebtorName", objLinkCust.getStrMasterDesc());
				jObjDtl.put("strCrDr", "Cr");
				jObjDtl.put("dblAmt", debitAmt);
				jObjDtl.put("strBillNo", saCode);
				jObjDtl.put("strInvoiceNo", saCode);
				jObjDtl.put("strGuest", "");
				jObjDtl.put("strAccountCode", "");
				jObjDtl.put("strCreditNo", "");
				jObjDtl.put("dteBillDate", invDate);
				jObjDtl.put("dteInvoiceDate", invDate);
				jObjDtl.put("strNarration", invDate);
				jObjDtl.put("dteDueDate", invDate);
				jObjDtl.put("strClientCode", clientCode);
				jObjDtl.put("strPropertyCode", propCode);
				jObjDtl.put("strPOSCode", "");
				jObjDtl.put("strPOSName", "");
				jObjDtl.put("strRegistrationNo", "");

				jArrJVDebtordtl.add(jObjDtl);
				// }

				jObjJVData.put("ArrJVDebtordtl", jArrJVDebtordtl);
			// jvDebtor detail entry end

				JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebBooksIntegration/funGenrateJVforGRN", jObjJVData);
				jvCode = jObj.get("strJVCode").toString();
			}
		}
		return jvCode;
	}

	


	@RequestMapping(value = "/openRptInvoiceRetailNonGSTReport", method = RequestMethod.GET)
	public void funOpenInvoiceRetailNonGSTReport(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
		{
			req.getSession().removeAttribute("rptInvCode");
			type = "pdf";
			String[] arrInvCode = InvCode.split(",");
			req.getSession().removeAttribute("rptInvCode");

			InvCode = arrInvCode[0].toString();
			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceTaxes.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/Sanguine_Logo_Icontest.jpg");

			String challanDate = "";
			String PONO = "";
			String InvDate = "";
			String CustName = "";
			String dcCode = "";

			String sqlProdDtl = " select  c.strProdName,c.strProdNameMarathi,b.dblQty,IFNULL(b.dblPrice,0.00),c.dblMRP"
				+ ", IFNULL(b.dblPrice,0.00) AS dblPrice, a.dteInvDate, " + " IFNULL(d.strPName,''),ifnull(e.strDCCode,''),ifnull(e.dteDCDate,''),ifnull(e.strPONo,''), " + " b.strProdCode,f.strExciseChapter,b.dblProdDiscAmount as discAmt,IFNULL(d.strBAdd1,''),IFNULL(d.strBAdd2,''), " + " IFNULL(d.strBState,''),IFNULL(d.strBPin,'') ,IFNULL(d.strSAdd1,''),IFNULL(d.strSAdd2,''), " + " IFNULL(d.strSState,''),IFNULL(d.strSPin,'') ,ifNull(strCST,''),c.strBarCode,b.strRemarks,ifnull(c.strUOM,0) from tblinvoicehd a left outer join tblinvoicedtl b on a.strInvCode=b.strInvCode   " + " left outer join tblproductmaster c  on b.strProdCode=c.strProdCode left outer join tblpartymaster d on a.strCustCode=d.strPCode " + " left outer join tbldeliverychallanhd e on a.strSOCode=e.strDCCode " + " left outer join tblsubgroupmaster f on f.strSGCode=c.strSGCode " + " left outer join tblinvprodtaxdtl  g on a.strInvCode=g.strInvCode and a.strCustCode=g.strCustCode  " + " and b.strProdCode=g.strProdCode and g.strDocNo='Disc'   " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";

			String bAddress = "";
			String bState = "";
			String bPin = "";
			String sAddress = "";
			String sState = "";
			String sPin = "";
			String custGSTNo = "";
			double totalInvoiceValue = 0.0;
			List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlProdDtl, "sql");
			
			List<clsInvoiceDtlBean> dataList = new ArrayList<clsInvoiceDtlBean>();
			List<clsInvoiceDtlBean>taxSubReportList=new ArrayList<>();
			Map<Double, Double> hmCGSTCalculateTax = new HashMap<Double, Double>();
			Map<Double, Double> hmSGSTCalculateTax = new HashMap<Double, Double>();
			double nonGStAmt = 0.0;
			
			if (listProdDtl.size() > 0)
			{
				for (int i = 0; i < listProdDtl.size(); i++)
				{
					Object[] obj = (Object[]) listProdDtl.get(i);
					clsInvoiceDtlBean objDtlBean = new clsInvoiceDtlBean();
					objDtlBean.setStrProdName(obj[0].toString());
					objDtlBean.setStrProdNamemarthi(obj[1].toString());
					objDtlBean.setDblQty(Double.parseDouble(obj[2].toString()));
					objDtlBean.setDblCostRM(Double.parseDouble(obj[3].toString()) / currValue);
					objDtlBean.setDblMRP(Double.parseDouble(obj[4].toString()) / currValue);
					objDtlBean.setDblPrice(Double.parseDouble(obj[5].toString()) / currValue);

					InvDate = objGlobalFunctions.funGetDate("dd-MM-yyyy", obj[6].toString());
					CustName = obj[7].toString();
					challanDate = obj[9].toString();
					PONO = obj[10].toString();
					dcCode = obj[8].toString();
					objDtlBean.setStrHSN(obj[12].toString());
					objDtlBean.setDblDisAmt(Double.parseDouble(obj[13].toString()) / currValue);
					bAddress = obj[14].toString() + " " + obj[15].toString();
					bState = obj[16].toString();
					bPin = obj[17].toString();

					sAddress = obj[18].toString() + " " + obj[19].toString();
					sState = obj[20].toString();
					sPin = obj[21].toString();
					custGSTNo = obj[22].toString();
					objDtlBean.setStrBarCode(obj[23].toString());
					objDtlBean.setStrRemarks(obj[24].toString());
					objDtlBean.setStrUOM(obj[25].toString());
					totalInvoiceValue = totalInvoiceValue + ((Double.parseDouble(obj[2].toString()) * Double.parseDouble(obj[3].toString())) - Double.parseDouble(obj[13].toString()));
				

					dataList.add(objDtlBean);
				}
			}
			totalInvoiceValue = totalInvoiceValue / currValue;

			try
			{
				
				String sqlQuery = " select b.strTaxCode,b.dblPercent,sum(a.dblTaxAmt),b.strShortName,b.strTaxDesc    " + " from tblinvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and a.strInvCode='" + InvCode + "' " + "  and a.strClientCode='" + clientCode + "'   group by a.strTaxCode ";
				List listProdGST = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
				if (listProdGST.size() > 0)
				{
					for (int j = 0; j < listProdGST.size(); j++)
					{
						double cGStAmt = 0.0;
						double sGStAmt = 0.0;
						Object[] objGST = (Object[]) listProdGST.get(j);
						clsInvoiceDtlBean objTax=new  clsInvoiceDtlBean();
						objTax.setStrTaxDesc(objGST[4].toString());
						objTax.setDblCGSTPer(Double.parseDouble(objGST[1].toString()));
						objTax.setDblCGSTAmt(Double.parseDouble(objGST[2].toString()) / currValue);
						totalInvoiceValue = (totalInvoiceValue + (Double.parseDouble(objGST[2].toString())));
						taxSubReportList.add(objTax);
					}
				}
				if(taxSubReportList.size()==0)
				{
					taxSubReportList.add(new  clsInvoiceDtlBean());
				}
				
				
				
				String shortName = " Paisa";
				String currCode = req.getSession().getAttribute("currencyCode").toString();
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);
				if (objCurrModel != null)
				{
					shortName = objCurrModel.getStrShortName();
				}

				
				int noOfDecimalPlaces=objSetup.getIntdec();
				StringBuilder decimalFormatString=new StringBuilder("0.");
				for(int i=0;i<noOfDecimalPlaces;i++)
				{
					decimalFormatString.append("0");
				}
				if(noOfDecimalPlaces<=0)
				{
					decimalFormatString.setLength(0);
					decimalFormatString.append("0.00");
					
				}
				
				DecimalFormat decimalFormat=new DecimalFormat(decimalFormatString.toString());
				
				clsNumberToWords obj1 = new clsNumberToWords();
				String totalInvoiceValueInWords = obj1.getNumberInWorld(Double.parseDouble(decimalFormat.format(totalInvoiceValue)), shortName);

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
				hm.put("clientContact", objSetup.getStrPhone());
				hm.put("clientEmail", objSetup.getStrEmail());
				hm.put("InvCode", InvCode);
				hm.put("InvDate", InvDate);
				hm.put("challanDate", challanDate);
				hm.put("PONO", PONO);
				hm.put("CustName", CustName);
				hm.put("PODate", challanDate);
				hm.put("dcCode", dcCode);
				hm.put("dataList", dataList);
				
				String taxSubReport = servletContext.getRealPath("/WEB-INF/reports/webcrm/subReportTax.jasper");
				hm.put("taxSubReport", taxSubReport);
				hm.put("taxSubReportList", taxSubReportList);
				hm.put("bAddress", bAddress);
				hm.put("bState", bState);
				hm.put("bPin", bPin);
				hm.put("sAddress", sAddress);
				hm.put("sState", sState);
				hm.put("sPin", sPin);
				hm.put("totalInvoiceValueInWords", totalInvoiceValueInWords);
				hm.put("totalInvoiceValue", totalInvoiceValue);
				hm.put("strGSTNo.", objSetup.getStrCST());
				hm.put("custGSTNo", custGSTNo);

				sqlQuery = "  select ifnull(a.strSettlementCode,''),ifnull(b.strSettlementDesc,''),ifnull(a.strCustCode,''),ifnull(c.strPName,'')\r\n" + ",ifnull(c.strContact,''),ifnull(c.strEmail,''),ifnull(a.strSAdd1,''),ifnull(a.strSAdd2,''),ifnull(a.strSPin,''),ifnull(a.strSState,''),a.strNarration\r\n" + " ,a.strDeliveryNote,a.strSupplierRef,a.strOtherRef,a.strBuyersOrderNo,ifnull(a.dteBuyerOrderNoDated,''),a.strDispatchDocNo,ifnull(a.dteDispatchDocNoDated,'')\r\n" + ",a.strDispatchThrough,a.strDestination " + " from tblinvoicehd a\r\n" + "left outer join tblsettlementmaster b on a.strSettlementCode=b.strSettlementCode\r\n" + "left outer join tblpartymaster c on a.strCustCode=c.strPCode\r\n" + "where a.strInvCode='" + InvCode + "'\r\n" + "and a.strClientCode='" + clientCode + "';";
				List listParameters = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
				if (listParameters != null && listParameters.size() > 0)
				{
					Object[] arrParameters = (Object[]) listParameters.get(0);

					hm.put("bAddr1", arrParameters[6].toString());
					hm.put("bAddr2", arrParameters[7].toString());
					hm.put("bPin", arrParameters[8].toString());
					hm.put("bState", arrParameters[9].toString());
					hm.put("modeOfPayment", arrParameters[1].toString());
					hm.put("reasonRemarks", arrParameters[10].toString());

					hm.put("strDeliveryNote", arrParameters[11].toString());
					hm.put("strSupplierRef", arrParameters[12].toString());
					hm.put("strOtherRef", arrParameters[13].toString());
					hm.put("strBuyersOrderNo", arrParameters[14].toString());
					hm.put("dteBuyerOrderNoDated", arrParameters[15].toString());
					hm.put("strDispatchDocNo", arrParameters[16].toString());
					hm.put("dteDispatchDocNoDated", arrParameters[17].toString());
					hm.put("strDispatchThrough", arrParameters[18].toString());
					hm.put("strDestination", arrParameters[19].toString());
				}
				
				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);

				JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				jprintlist.add(jp);

				if (jp != null)
				{
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf"))
					{
						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=rptTaxInvoiceRetail" + userCode + ".pdf");
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();
					}
					else
					{
						// code for Exporting FLR 3 in ExcelSheet

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xls");
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						resp.setHeader("Content-Disposition", "attachment;filename=" + "rptTaxInvoiceRetail" + userCode + ".xlsx");
						exporter.exportReport();
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}





	// Assign filed function to set data onto form for edit transaction.
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadInvoiceHdDataForGRN", method = RequestMethod.GET)
	public @ResponseBody clsInvoiceBean funInvoiceHdDataForGRN(@RequestParam("invCode") String invCode, HttpServletRequest req)
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
		clsInvoiceBean objBeanInv = new clsInvoiceBean();

		List<Object> objDC = objInvoiceHdService.funGetInvoice(invCode, clientCode);
		clsInvoiceHdModel objInvoiceHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++)
		{
			Object[] ob = (Object[]) objDC.get(i);
			objInvoiceHdModel = (clsInvoiceHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanInv = funPrepardHdBeanInvoiceForGRN(objInvoiceHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanInv.setDblDiscount(objPartyMasterModel.getDblReturnDiscount());
		objBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsInvoiceModelDtl> listDCDtl = new ArrayList<clsInvoiceModelDtl>();
		clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(invCode, clientCode);

		List<clsInvoiceModelDtl> listInvDtlModel = objInvHDModelList.getListInvDtlModel();
		List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();
		for (int i = 0; i < listInvDtlModel.size(); i++)
		{
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

			String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode " + " and a.strCustCode='" + objBeanInv.getStrCustName() + "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode  " + " and a.strCustCode='" + objBeanInv.getStrCustCode() + "')";

			List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");

			if (listPrevInvData.size() > 0)
			{
				Object objInv[] = (Object[]) listPrevInvData.get(0);
				objBeanInvoice.setPrevUnitPrice(Double.parseDouble(objInv[1].toString()));
				objBeanInvoice.setPrevInvCode(objInv[2].toString());

			}
			else
			{
				objBeanInvoice.setPrevUnitPrice(0.0);
				objBeanInvoice.setPrevInvCode(" ");
			}

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

		// String
		// sql="select strTaxCode,strTaxDesc,strTaxableAmt,strTaxAmt from
		// clsInvoiceTaxDtlModel "
		// +
		// "where strInvCode='"+invCode+"' and strClientCode='"+clientCode+"'";
		// List list=objGlobalFunctionsService.funGetList(sql,"hql");
		// List<clsInvoiceTaxDtlModel> listInvTaxDtl=new
		// ArrayList<clsInvoiceTaxDtlModel>();
		// for(int cnt=0;cnt<list.size();cnt++)
		// {
		// clsInvoiceTaxDtlModel objTaxDtl=new clsInvoiceTaxDtlModel();
		// Object[] arrObj=(Object[])list.get(cnt);
		// objTaxDtl.setStrTaxCode(arrObj[0].toString());
		// objTaxDtl.setStrTaxDesc(arrObj[1].toString());
		// objTaxDtl.setDblTaxableAmt(Double.parseDouble(arrObj[2].toString()));
		// objTaxDtl.setDblTaxAmt(Double.parseDouble(arrObj[3].toString()));
		//
		// listInvTaxDtl.add(objTaxDtl);
		// }
		// objBeanInv.setListInvoiceTaxDtl(listInvTaxDtl);

		return objBeanInv;
	}

	private clsInvoiceBean funPrepardHdBeanInvoiceForGRN(clsInvoiceHdModel objInvHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel)
	{

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
		objBean.setStrSettlementCode(objInvHdModel.getStrSettlementCode());

		return objBean;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptInvoiceSlipFormat5Report", method = RequestMethod.GET)
	public void funOpenInvoiceFormat5Report(@RequestParam("rptInvCode") String invCode, @RequestParam("rptInvDate") String invDate, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{
			String InvCode = invCode;
			String[] arrInv = InvCode.split(",");
			InvCode = arrInv[0];
			String type = "pdf";
			JasperPrint jp = funCallReportInvoiceFormat5Report(InvCode, invDate, type, resp, req);

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
	private JasperPrint funCallReportInvoiceFormat5Report(String InvCode, String invDate, String type, HttpServletResponse resp, HttpServletRequest req)
	{

		JasperPrint jp = null;
		Connection con = objGlobalFunctions.funGetConnection(req);

		try
		{
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String strSPin = "";
			String strSState = "";
			String strSCountry = "";
			String strVehNo = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			double subtotalInv = 0.0;
			double totalAmt = 0.0;
			String totalInvInWords = "";
			double exciseTaxAmtTotal = 0.0;
			String exciseTotalInWords = "";
			double grandTotal = 0.0;
			String excisePerDesc = "";
			String dteInvDate = "";
			String strDulpicateFlag = "";
			String heading = "(Modvat)";
			String strDCCode = "";
			String dteDCDate = "";
			String strTransName = "";
			String strCustECCNo = "", custVatNo = "", strMainAdd1 = "", strPoNo = "", strVehicleNo = "";
			String strMainAdd2 = "", strMCity = "", strMCountry = "", strMPin = "", strMState = "";
			String strTerms = "", strRep = "", strShipVia = "", strFOB = "", strProject = "";
			clsNumberToWords obj = new clsNumberToWords();

			dteInvDate = invDate;
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormat5Report.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			HashMap hm = new HashMap();
			ArrayList fieldList = new ArrayList();
			String strAuthLevel1 = "", strAuthLevel2 = "", strUserCreated = "";

			hm.put("InvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);

			String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSPin,b.strSState " // 7
					+ ",b.strSCountry,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo,a.dblTotalAmt,a.dblGrandTotal " // 15
					+ ",b.strVAT,b.strMAdd1,b.strMAdd2,b.strMCity,b.strMPin,b.strMState,b.strMCountry,a.strPONo,a.strVehNo,a.dblDiscountAmt "// 25
					+ ",a.strAuthLevel1,a.strAuthLevel2 ,a.strUserCreated " + " from tblinvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				dteInvDate = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
				subtotalInv = Double.parseDouble(arrObj[10].toString());
				strDulpicateFlag = arrObj[12].toString();
				strCustECCNo = arrObj[13].toString();
				totalAmt = Double.parseDouble(arrObj[14].toString());
				grandTotal = Double.parseDouble(arrObj[15].toString());
				custVatNo = arrObj[16].toString();
				strMainAdd1 = arrObj[17].toString();
				strMainAdd2 = arrObj[18].toString();
				strMCity = arrObj[19].toString();
				strMPin = arrObj[20].toString();
				strMState = arrObj[21].toString();
				strMCountry = arrObj[22].toString();
				strPoNo = arrObj[23].toString();
				strVehicleNo = arrObj[24].toString();
				strAuthLevel1 = arrObj[26].toString();
				strAuthLevel2 = arrObj[27].toString();
				strUserCreated = arrObj[28].toString();
			}
			String[] datetime = dteInvDate.split(" ");
			dteInvDate = datetime[0];
			time = datetime[1];
			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			String timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("and");
			String[] wordmin = words[1].split("paisa");
			timeInWords = "Hours " + words[0] + "" + wordmin[0];

			String[] date = dteInvDate.split("-");
			dteInvDate = date[2] + "-" + date[1] + "-" + date[0];

			String sqlDetailQ = "select  b.dblQty,c.strProdName,c.strUOM,b.dblPrice,b.dblQty *b.dblPrice as amount ,b.strProdCode" + ",a.dblDiscountAmt,a.dblGrandTotal " + " from tblinvoicehd a left outer join tblinvoicedtl b  on a.strInvCode=b.strInvCode " + " left outer join tblproductmaster c on  b.strProdCode=c.strProdCode where  a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";
			list = objGlobalFunctionsService.funGetList(sqlDetailQ, "sql");
			if (!list.isEmpty())
			{
				for (int i = 0; i < list.size(); i++)
				{
					Object[] arrObj = (Object[]) list.get(i);
					clsInvoiceBean objBean = new clsInvoiceBean();
					objBean.setDblQty(Double.parseDouble(arrObj[0].toString()));
					objBean.setStrProdName(arrObj[1].toString());
					objBean.setStrUOM(arrObj[2].toString());
					objBean.setDblUnitPrice(Double.parseDouble(arrObj[3].toString()));
					objBean.setDblSubTotalAmt(Double.parseDouble(arrObj[4].toString()));
					objBean.setStrProdCode(arrObj[5].toString());
					objBean.setDblDiscountAmt(Double.parseDouble(arrObj[6].toString()));
					objBean.setDblTotalAmt(Double.parseDouble(arrObj[3].toString()) * Double.parseDouble(arrObj[0].toString()));
					objBean.setDblGrandTotal(Double.parseDouble(arrObj[7].toString()));
					double taxAmt = 0.00;
					String sqlTax = "SELECT sum(b.dblValue) FROM tblinvtaxdtl a,tblinvprodtaxdtl b " + " WHERE a.strTaxCode=b.strDocNo and a.strInvCode=b.strInvCode and a.strInvCode='" + InvCode + "' " + " AND b.strProdCode='" + arrObj[5].toString() + "' and a.strClientCode='" + clientCode + "' " + " group by b.strProdCode ";
					List listTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
					if (listTax != null)
					{
						for (int j = 0; j < listTax.size(); j++)
						{
							Object objTax = (Object) listTax.get(j);
							taxAmt = taxAmt + Double.parseDouble(objTax.toString());
						}
					}
					objBean.setDblTaxAmt(taxAmt);
					fieldList.add(objBean);

				}
			}

			hm.put("strPoNo", strPoNo);
			hm.put("strCompVatNo", objSetup.getStrVAT());
			hm.put("strCustVatNo", custVatNo);
			hm.put("strMainAdd1", strMainAdd1);
			hm.put("strMainAdd2", strMainAdd2);
			hm.put("strMCity", strMCity);
			hm.put("strMCountry", strMCountry);
			hm.put("strMPin", strMPin);
			hm.put("strMState", strMState);
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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strInvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);
			hm.put("strVAT", objSetup.getStrVAT());
			hm.put("listData", fieldList);
			hm.put("strTerms", strTerms);
			hm.put("strRep", strRep);
			hm.put("strShipVia", strShipVia);
			hm.put("strFOB", strFOB);
			hm.put("strProject", strProject);
			hm.put("strVehicleNo", strVehicleNo);
			hm.put("strAuthLevel1", strAuthLevel1);
			hm.put("strAuthLevel2", strAuthLevel2);
			hm.put("strUserCreated", strUserCreated);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return jp;

	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/rptInvoiceSlipFormat6Report", method = RequestMethod.GET)
	public void funOpenInvoiceFormat6Report(@RequestParam("rptInvCode") String invCode, @RequestParam("rptInvDate") String invDate, HttpServletResponse resp, HttpServletRequest req)
	{
		try
		{
			String InvCode = invCode;
			String[] arrInv = InvCode.split(",");
			InvCode = arrInv[0];
			String type = "pdf";
			JasperPrint jp = funCallReportInvoiceFormat6Report(InvCode, invDate, type, resp, req);

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
			jprintlist.add(jp);
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (jprintlist != null)
			{
				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			}

		}
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
	private JasperPrint funCallReportInvoiceFormat6Report(String InvCode, String invDate, String type, HttpServletResponse resp, HttpServletRequest req)
	{

		JasperPrint jp = null;
		Connection con = objGlobalFunctions.funGetConnection(req);
		
		try
		{
			String strPName = "",strCustEmail="",strCustFaxNo="",strCustPhone="",strCustContact="",strCustVAT="";
			String strSAdd1 = "",strBAdd1 = "";
			String strSAdd2 = "",strBAdd2 = "";
			String strSCity = "",strBCity = "";
			String strSPin = "",strBPin = "";
			String strSState = "",strBState = "";
			String strSCountry = "",strBCountry = "";
			String strVehNo = "";
			String time = "";
			String strRangeAdd = "";
			String strDivision = "";
			double subtotalInv = 0.0,dblExtraCharges=0.0;
			double totalAmt = 0.0;
			String totalInvInWords = "";
			double exciseTaxAmtTotal = 0.0;
			String exciseTotalInWords = "";
			double grandTotal = 0.0;
			String excisePerDesc = "";
			String dteInvDate = "";
			String strDulpicateFlag = "";
			String heading = "(Modvat)";
			String strDCCode = "";
			String dteDCDate = "";
			String strTransName = "";
			String strCustECCNo = "", custVatNo = "", strMainAdd1 = "", strPoNo = "", strVehicleNo = "";
			String strMainAdd2 = "", strMCity = "", strMCountry = "", strMPin = "", strMState = "";
			String strTerms = "", strRep = "", strShipVia = "", strFOB = "", strProject = "";
			clsNumberToWords obj = new clsNumberToWords();

			String[] invoiceDate = invDate.split("-");
			dteInvDate = invoiceDate[1]+"/"+invoiceDate[2]+"/"+invoiceDate[0];
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null)
			{
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormatForMayaReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
			HashMap hm = new HashMap();
			ArrayList fieldList = new ArrayList();
			ArrayList taxList = new ArrayList();
			String strAuthLevel1 = "", strAuthLevel2 = "", strUserCreated = "";

			hm.put("InvCode", InvCode);
			hm.put("dteInvDate", dteInvDate);

			double conversionRate=1.;
			String Currency = "";
			String currencyName="";
			StringBuilder sbSql = new StringBuilder();
			String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
//			sbSql.append("select dblConvToBaseCurr,strShortName from tblcurrencymaster where strCurrencyCode='"+objSetup.getStrCurrencyCode()+"' and strClientCode='"+clientCode+"' ");
//			try
//			{
//				List list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
//				for(int i=0;i<list.size();i++)
//				{	
//				Object[] object = (Object[]) list.get(i);
//				conversionRate=Double.parseDouble(object[0].toString());
//				currencyName = object[1].toString();
//				}
//			}catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//			
			String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSPin,b.strSState " // 7
					+ ",b.strSCountry,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo,a.dblTotalAmt,a.dblGrandTotal " // 15
					+ ",b.strVAT,b.strMAdd1,b.strMAdd2,b.strMCity,b.strMPin,b.strMState,b.strMCountry,a.strPONo,a.strVehNo,a.dblDiscountAmt "// 25
					+ ",a.strAuthLevel1,a.strAuthLevel2 ,a.strUserCreated, "
					+ " b.strBankAdd1,b.strBankAdd2,b.strBCity,b.strBPin,b.strBState,b.strBCountry,b.strEmail,b.strFax"
					+ " ,b.strPhone,b.strContact,b.strVAT,a.dblCurrencyConv,a.strCurrencyCode ,a.dblExtraCharges " 
					+ " from tblinvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " 
					+ " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty())
			{
				Object[] arrObj = (Object[]) list.get(0);
				dteInvDate = arrObj[1].toString();
				strPName = arrObj[2].toString();
				strSAdd1 = arrObj[3].toString();
				strSAdd2 = arrObj[4].toString();
				strSCity = arrObj[5].toString();
				strSPin = arrObj[6].toString();
				strSState = arrObj[7].toString();
				strSCountry = arrObj[8].toString();
				strVehNo = arrObj[9].toString();
				subtotalInv = Double.parseDouble(arrObj[10].toString());
				strDulpicateFlag = arrObj[12].toString();
				strCustECCNo = arrObj[13].toString();
				totalAmt = Double.parseDouble(arrObj[14].toString());
				grandTotal = Double.parseDouble(arrObj[15].toString());
				custVatNo = arrObj[16].toString();
				strMainAdd1 = arrObj[17].toString();
				strMainAdd2 = arrObj[18].toString();
				strMCity = arrObj[19].toString();
				strMPin = arrObj[20].toString();
				strMState = arrObj[21].toString();
				strMCountry = arrObj[22].toString();
				strPoNo = arrObj[23].toString();
				strVehicleNo = arrObj[24].toString();
				strAuthLevel1 = arrObj[26].toString();
				strAuthLevel2 = arrObj[27].toString();
				strUserCreated = arrObj[28].toString();
				strBAdd1 = arrObj[29].toString();
				strBAdd2 = arrObj[30].toString();
				strBCity = arrObj[31].toString();
				strBPin = arrObj[32].toString();
				strBState = arrObj[33].toString();
				strBCountry = arrObj[34].toString();
				strCustEmail = arrObj[35].toString();
				strCustFaxNo = arrObj[36].toString();
				strCustPhone = arrObj[37].toString();
				strCustContact = arrObj[38].toString();
				strCustVAT = arrObj[39].toString(); 
				conversionRate = Double.parseDouble(arrObj[40].toString()); 
				Currency = arrObj[41].toString(); 
				dblExtraCharges= Double.parseDouble(arrObj[42].toString());
			}
			
			clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(Currency, clientCode);
			if (null!=objModel )
			{
				// dblCurrencyConv=1;
				currencyName=objModel.getStrShortName();
				Currency=objModel.getStrCurrencyName();
			}
			String[] datetime = dteInvDate.split(" ");
			dteInvDate = datetime[0];
			time = datetime[1];
			String[] invTime = time.split(":");
			time = invTime[0] + "." + invTime[1];
			Double dblTime = Double.parseDouble(time);
			String timeInWords = obj.getNumberInWorld(dblTime);
			String[] words = timeInWords.split("And");
			String[] wordmin = words[1].split("Paisa");
			timeInWords = "Hours " + words[0] + "" + wordmin[0];

			String[] date = dteInvDate.split("-");
			dteInvDate = date[2] + "-" + date[1] + "-" + date[0];

			double invSubtotal=0.0;
			String sqlDetailQ = "select DATE_FORMAT(a.dteInvDate,'%m/%d/%Y'),c.strProdCode,c.strProdName,b.dblQty,b.dblUnitPrice,a.dblDiscountAmt " 
						+ " from tblinvoicehd a left outer join tblinvoicedtl b  on a.strInvCode=b.strInvCode "
						+ " left outer join tblproductmaster c on  b.strProdCode=c.strProdCode where  a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";
			list = objGlobalFunctionsService.funGetList(sqlDetailQ, "sql");
			if (!list.isEmpty())
			{
				for (int i = 0; i < list.size(); i++)
				{
					Object[] arrObj = (Object[]) list.get(i);
					clsInvoiceBean objBean = new clsInvoiceBean();
					objBean.setDteInvDate(arrObj[0].toString());
					objBean.setStrProdCode(arrObj[1].toString());
					objBean.setStrProdName(arrObj[2].toString());
					objBean.setDblQty(Double.parseDouble(arrObj[3].toString()));
					objBean.setDblUnitPrice(Double.parseDouble(arrObj[4].toString())/conversionRate);
					objBean.setDblDiscountAmt(Double.parseDouble(arrObj[5].toString())/conversionRate);
					invSubtotal = invSubtotal + (Double.parseDouble(arrObj[3].toString())*(Double.parseDouble(arrObj[4].toString())/conversionRate)-(Double.parseDouble(arrObj[5].toString())/conversionRate)) ;
					fieldList.add(objBean);

				}
			}

			clsInvoiceBean objBean = new clsInvoiceBean();
			objBean.setStrTaxName("Invoice SubTotal");
			objBean.setDblTaxAmt(invSubtotal);
			taxList.add(objBean);
			
			if(dblExtraCharges>0){
				objBean = new clsInvoiceBean();
				objBean.setStrTaxName("Extra Charges");
				objBean.setDblTaxAmt(dblExtraCharges);
				taxList.add(objBean);	
			}
			
			double taxAmt = 0.00;
			DecimalFormat df = new DecimalFormat("#.##");
			String sqlTax = "SELECT c.strTaxDesc,a.dblTaxAmt "
					+ "FROM tblinvtaxdtl a,tbltaxhd c "
					+ "WHERE a.strInvCode='"+InvCode+"'"
					+ " and a.strClientCode='"+clientCode+"' and a.strTaxCode=c.strTaxCode";
			List listTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
			if (listTax != null)
			{
				for (int j = 0; j < listTax.size(); j++)
				{
					Object[] objTax = (Object[]) listTax.get(j);
					clsInvoiceBean objTaxBean = new clsInvoiceBean();
					objTaxBean.setDblTaxAmt(Double.parseDouble(objTax[1].toString())/conversionRate);
					objTaxBean.setStrTaxName(objTax[0].toString());
					if(Double.parseDouble(objTax[1].toString())!=0.0)
					{	
					taxList.add(objTaxBean);
					}
				}
			}
			
			
			hm.put("strPoNo", strPoNo);
			hm.put("strCompVatNo", objSetup.getStrVAT());
			hm.put("strCustVatNo", custVatNo);
			hm.put("strMainAdd1", strMainAdd1);
			hm.put("strMainAdd2", strMainAdd2);
			hm.put("strMCity", strMCity);
			hm.put("strMCountry", strMCountry);
			hm.put("strMPin", strMPin);
			hm.put("strMState", strMState);
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
			hm.put("strSAdd1", strSAdd1);
			hm.put("strSAdd2", strSAdd2);
			hm.put("strSCity", strSCity);
			hm.put("strSState", strSState);
			hm.put("strSCountry", strSCountry);
			hm.put("strSPin", strSPin);
			hm.put("strInvCode", InvCode);
			
			hm.put("strVAT", objSetup.getStrVAT());
			hm.put("listData", fieldList);
			hm.put("strTerms", strTerms);
			hm.put("strRep", strRep);
			hm.put("strShipVia", strShipVia);
			hm.put("strFOB", strFOB);
			hm.put("strProject", strProject);
			hm.put("strVehicleNo", strVehicleNo);
			hm.put("strAuthLevel1", strAuthLevel1);
			hm.put("strAuthLevel2", strAuthLevel2);
			hm.put("strUserCreated", strUserCreated);
			hm.put("taxList", taxList);
			hm.put("strBAdd1", strBAdd1);
			hm.put("strBAdd2", strBAdd2);
			hm.put("strBCity", strBCity);
			hm.put("strBState", strBState);
			hm.put("strBCountry", strBCountry);
			hm.put("strBPin", strBPin);
			hm.put("strPhone", objSetup.getStrPhone());
			hm.put("strEmail", objSetup.getStrEmail());
			hm.put("strCustEmail", strCustEmail);
			hm.put("strWebsite", objSetup.getStrWebsite());
			hm.put("strCustFaxNo",strCustFaxNo);
			hm.put("strCustPhone",strCustPhone);
			hm.put("strCustContact",strCustContact);
			hm.put("strCustVAT",strCustVAT);
			hm.put("currencyName",currencyName);
			
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return jp;
	}
	
public void funCallInvoiceFormat(String InvCode, String type,HttpServletResponse resp, HttpServletRequest req){
		
		String invoiceformat= req.getSession().getAttribute("invoieFormat").toString();
		if(invoiceformat.equalsIgnoreCase("Format 1"))
			{
			
			}
		else if(invoiceformat.equalsIgnoreCase("Format 5"))
		{
			
			//window.open(getContextPath()+"/rptInvoiceSlipFormat5Report.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
		}
		else if(invoiceformat.equalsIgnoreCase("RetailNonGSTA4"))
		{
			//window.open(getContextPath()+"/openRptInvoiceRetailNonGSTReport.html?rptInvCode="+code,'_blank');
	    }
		else if(invoiceformat.equalsIgnoreCase("Format 6"))
		{
			//window.open(getContextPath()+"/rptInvoiceSlipFormat6Report.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
			String date="";
			try{
				StringBuilder sb=new StringBuilder("select dteInvDate from tblinvoicehd where strInvCode='"+InvCode+"' ");
				List listDate=objBaseService.funGetListModuleWise(sb, "sql", "WebStocks");
				 date=listDate.get(0).toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			funOpenInvoiceFormat6Report(InvCode,date,  resp,  req);
		}
		else if(invoiceformat.equalsIgnoreCase("Format 7"))
		{
			//window.open(getContextPath()+"/rptInvoiceSlipFormat6Report.html?rptInvCode="+code+"&rptInvDate="+invDate,'_blank');
			String date="";
			try{
				StringBuilder sb=new StringBuilder("select dteInvDate from tblinvoicehd where strInvCode='"+InvCode+"' ");
				List listDate=objBaseService.funGetListModuleWise(sb, "sql", "WebStocks");
				 date=listDate.get(0).toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			funCallReportInvoiceFormat7Report(InvCode,date,  resp,  req);
		}
		else
	    {
	    	//window.open(getContextPath()+"/openRptInvoiceRetailReport.html?rptInvCode="+code,'_blank');
			
			funOpenInvoiceRetailReport(InvCode,type,resp,req);
	    }

	}

@SuppressWarnings("unused")
@RequestMapping(value = "/rptInvoiceSlipFormat6ReportForAudit", method = RequestMethod.GET)
public void funOpenInvoiceFormat6ReportForAudit(@RequestParam("rptInvCode") String invCode, @RequestParam("rptInvDate") String invDate, HttpServletResponse resp, HttpServletRequest req)
{
	try
	{
		String InvCode = invCode;
		String[] arrInv = InvCode.split(",");
		InvCode = arrInv[0];
		String type = "pdf";
		JasperPrint jp = funCallReportInvoiceFormat6ReportForAudit(InvCode, invDate, type, resp, req);

		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		jprintlist.add(jp);
		ServletOutputStream servletOutputStream = resp.getOutputStream();
		if (jprintlist != null)
		{
			JRExporter exporter = new JRPdfExporter();
			resp.setContentType("application/pdf");
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
			exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			resp.setHeader("Content-Disposition", "inline;filename=Invoice.pdf");
			exporter.exportReport();
			servletOutputStream.flush();
			servletOutputStream.close();
		}

	}
	catch (JRException e)
	{
		e.printStackTrace();
	}
	catch (IOException e)
	{
		e.printStackTrace();
	}
}


@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
private JasperPrint funCallReportInvoiceFormat6ReportForAudit(String InvCode, String invDate, String type, HttpServletResponse resp, HttpServletRequest req)
{

	JasperPrint jp = null;
	Connection con = objGlobalFunctions.funGetConnection(req);
	
	try
	{
		String strPName = "",strCustEmail="",strCustFaxNo="",strCustPhone="",strCustContact="",strCustVAT="";
		String strSAdd1 = "",strBAdd1 = "";
		String strSAdd2 = "",strBAdd2 = "";
		String strSCity = "",strBCity = "";
		String strSPin = "",strBPin = "";
		String strSState = "",strBState = "";
		String strSCountry = "",strBCountry = "";
		String strVehNo = "";
		String time = "";
		String strRangeAdd = "";
		String strDivision = "";
		double subtotalInv = 0.0,dblExtraCharges=0.0;
		double totalAmt = 0.0;
		String totalInvInWords = "";
		double exciseTaxAmtTotal = 0.0;
		String exciseTotalInWords = "";
		double grandTotal = 0.0;
		String excisePerDesc = "";
		String dteInvDate = "";
		String strDulpicateFlag = "";
		String heading = "(Modvat)";
		String strDCCode = "";
		String dteDCDate = "";
		String strTransName = "";
		String strCustECCNo = "", custVatNo = "", strMainAdd1 = "", strPoNo = "", strVehicleNo = "";
		String strMainAdd2 = "", strMCity = "", strMCountry = "", strMPin = "", strMState = "";
		String strTerms = "", strRep = "", strShipVia = "", strFOB = "", strProject = "";
		clsNumberToWords obj = new clsNumberToWords();

		String[] invoiceDate = invDate.split("-");
		dteInvDate = invoiceDate[1]+"/"+invoiceDate[2]+"/"+invoiceDate[0];
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();

		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		if (objSetup == null)
		{
			objSetup = new clsPropertySetupModel();
		}
		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormatForMayaReport.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
		HashMap hm = new HashMap();
		ArrayList fieldList = new ArrayList();
		ArrayList taxList = new ArrayList();
		String strAuthLevel1 = "", strAuthLevel2 = "", strUserCreated = "";

		hm.put("InvCode", InvCode);
		hm.put("dteInvDate", dteInvDate);

		double conversionRate=1.;
		String Currency = "";
		String currencyName="";
		StringBuilder sbSql = new StringBuilder();
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
//		sbSql.append("select dblConvToBaseCurr,strShortName from tblcurrencymaster where strCurrencyCode='"+objSetup.getStrCurrencyCode()+"' and strClientCode='"+clientCode+"' ");
//		try
//		{
//			List list = objBaseService.funGetListModuleWise(sbSql, "sql", "WebStocks");
//			for(int i=0;i<list.size();i++)
//			{	
//			Object[] object = (Object[]) list.get(i);
//			conversionRate=Double.parseDouble(object[0].toString());
//			currencyName = object[1].toString();
//			}
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
		String sqlHd = " select a.strTransCode,a.dtTransDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSPin,b.strSState ,b.strSCountry,a.strVehNo,a.dblSubTotal,a.dblTaxAmt,b.strECCNo,a.dblTotalAmt,a.dblSubTotal+a.dblTaxAmt-a.dblDiscount ,b.strVAT,b.strMAdd1,b.strMAdd2,b.strMCity,b.strMPin,b.strMState,b.strMCountry,ifnull(a.strPONo,''),a.strVehNo,a.dblDiscount,a.strUserCreated,  b.strBankAdd1,b.strBankAdd2,b.strBCity,b.strBPin,b.strBState,b.strBCountry,b.strEmail,b.strFax ,b.strPhone,b.strContact,b.strVAT  \n"  
				+ " from tblaudithd a,tblpartymaster b \n" 
				+ " where a.strTransCode='"+InvCode+"' and a.strClientCode='"+clientCode+"' and a.strClientCode=b.strClientCode \n" 
				+ " group by a.strTransCode; ";

		List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
		if (!list.isEmpty())
		{
			Object[] arrObj = (Object[]) list.get(0);
			dteInvDate = arrObj[1].toString();
			strPName = arrObj[2].toString();
			strSAdd1 = arrObj[3].toString();
			strSAdd2 = arrObj[4].toString();
			strSCity = arrObj[5].toString();
			strSPin = arrObj[6].toString();
			strSState = arrObj[7].toString();
			strSCountry = arrObj[8].toString();
			strVehNo = arrObj[9].toString();
			subtotalInv = Double.parseDouble(arrObj[10].toString());
			strCustECCNo = arrObj[12].toString();
			totalAmt = Double.parseDouble(arrObj[13].toString());
			grandTotal = Double.parseDouble(arrObj[14].toString());
			custVatNo = arrObj[15].toString();
			strMainAdd1 = arrObj[16].toString();
			strMainAdd2 = arrObj[17].toString();
			strMCity = arrObj[18].toString();
			strMPin = arrObj[19].toString();
			strMState = arrObj[20].toString();
			strMCountry = arrObj[21].toString();
			strPoNo = arrObj[22].toString();
			strVehicleNo = arrObj[23].toString();
			strUserCreated = arrObj[25].toString();
			strBAdd1 = arrObj[26].toString();
			strBAdd2 = arrObj[27].toString();
			strBCity = arrObj[28].toString();
			strBPin = arrObj[29].toString();
			strBState = arrObj[30].toString();
			strBCountry = arrObj[31].toString();
			strCustEmail = arrObj[32].toString();
			strCustFaxNo = arrObj[33].toString();
			strCustPhone = arrObj[34].toString();
			strCustContact = arrObj[35].toString();
			strCustVAT = arrObj[36].toString(); 
			
		}
		
		clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(Currency, clientCode);
		if (null!=objModel )
		{
			// dblCurrencyConv=1;
			currencyName=objModel.getStrShortName();
			Currency=objModel.getStrCurrencyName();
		}
		String[] datetime = dteInvDate.split(" ");
		dteInvDate = datetime[0];
		System.out.println(datetime.length);
		if(datetime.length!=1)
		{
		time = datetime[1];
		String[] invTime = time.split(":");
		time = invTime[0] + "." + invTime[1];
		Double dblTime = Double.parseDouble(time);
		String timeInWords = obj.getNumberInWorld(dblTime);
		String[] words = timeInWords.split("And");
		String[] wordmin = words[1].split("Paisa");
		timeInWords = "Hours " + words[0] + "" + wordmin[0];
		}
		
		
		double invSubtotal=0.0;
		String sqlDetailQ = "SELECT DATE_FORMAT(a.dtTransDate,'%m/%d/%Y'), IFNULL(c.strProdCode,''), IFNULL(c.strProdName,''), IFNULL(b.dblQty,0.0), IFNULL(b.dblUnitPrice,0.0), IFNULL(a.dblDiscount,0.0)\n"  
				+ " FROM tblaudithd a\n" 
				+ " LEFT OUTER\n"  
				+ " JOIN tblauditdtl b ON a.strTransCode=b.strTransCode\n" 
				+ " LEFT OUTER\n" 
				+ " JOIN tblproductmaster c ON b.strProdCode=c.strProdCode\n" 
				+ " WHERE a.strTransCode='"+InvCode+"' AND a.strClientCode='"+clientCode+"'";
		list = objGlobalFunctionsService.funGetList(sqlDetailQ, "sql");
		if (!list.isEmpty())
		{
			for (int i = 0; i < list.size(); i++)
			{
				Object[] arrObj = (Object[]) list.get(i);
				clsInvoiceBean objBean = new clsInvoiceBean();
				objBean.setDteInvDate(arrObj[0].toString());
				objBean.setStrProdCode(arrObj[1].toString());
				objBean.setStrProdName(arrObj[2].toString());
				objBean.setDblQty(Double.parseDouble(arrObj[3].toString()));
				objBean.setDblUnitPrice(Double.parseDouble(arrObj[4].toString())/conversionRate);
				objBean.setDblDiscountAmt(Double.parseDouble(arrObj[5].toString())/conversionRate);
				invSubtotal = invSubtotal + (Double.parseDouble(arrObj[3].toString())*(Double.parseDouble(arrObj[4].toString())/conversionRate)-(Double.parseDouble(arrObj[5].toString())/conversionRate)) ;
				fieldList.add(objBean);

			}
		}

		clsInvoiceBean objBean = new clsInvoiceBean();
		objBean.setStrTaxName("Invoice SubTotal");
		objBean.setDblTaxAmt(invSubtotal);
		taxList.add(objBean);
		
		if(dblExtraCharges>0){
			objBean = new clsInvoiceBean();
			objBean.setStrTaxName("Extra Charges");
			objBean.setDblTaxAmt(dblExtraCharges);
			taxList.add(objBean);	
		}
		
		double taxAmt = 0.00;
		DecimalFormat df = new DecimalFormat("#.##");
		String sqlTax = "SELECT c.strTaxDesc,a.dblTaxAmt "
				+ "FROM tblinvtaxdtl a,tbltaxhd c "
				+ "WHERE a.strInvCode='"+InvCode+"'"
				+ " and a.strClientCode='"+clientCode+"' and a.strTaxCode=c.strTaxCode";
		List listTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
		if (listTax != null)
		{
			for (int j = 0; j < listTax.size(); j++)
			{
				Object[] objTax = (Object[]) listTax.get(j);
				clsInvoiceBean objTaxBean = new clsInvoiceBean();
				objTaxBean.setDblTaxAmt(Double.parseDouble(objTax[1].toString())/conversionRate);
				objTaxBean.setStrTaxName(objTax[0].toString());
				if(Double.parseDouble(objTax[1].toString())!=0.0)
				{	
				taxList.add(objTaxBean);
				}
			}
		}
		
		
		hm.put("strPoNo", strPoNo);
		hm.put("strCompVatNo", objSetup.getStrVAT());
		hm.put("strCustVatNo", custVatNo);
		hm.put("strMainAdd1", strMainAdd1);
		hm.put("strMainAdd2", strMainAdd2);
		hm.put("strMCity", strMCity);
		hm.put("strMCountry", strMCountry);
		hm.put("strMPin", strMPin);
		hm.put("strMState", strMState);
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
		hm.put("strSAdd1", strSAdd1);
		hm.put("strSAdd2", strSAdd2);
		hm.put("strSCity", strSCity);
		hm.put("strSState", strSState);
		hm.put("strSCountry", strSCountry);
		hm.put("strSPin", strSPin);
		hm.put("strInvCode", InvCode);
		
		hm.put("strVAT", objSetup.getStrVAT());
		hm.put("listData", fieldList);
		hm.put("strTerms", strTerms);
		hm.put("strRep", strRep);
		hm.put("strShipVia", strShipVia);
		hm.put("strFOB", strFOB);
		hm.put("strProject", strProject);
		hm.put("strVehicleNo", strVehicleNo);
		hm.put("strAuthLevel1", strAuthLevel1);
		hm.put("strAuthLevel2", strAuthLevel2);
		hm.put("strUserCreated", strUserCreated);
		hm.put("taxList", taxList);
		hm.put("strBAdd1", strBAdd1);
		hm.put("strBAdd2", strBAdd2);
		hm.put("strBCity", strBCity);
		hm.put("strBState", strBState);
		hm.put("strBCountry", strBCountry);
		hm.put("strBPin", strBPin);
		hm.put("strPhone", objSetup.getStrPhone());
		hm.put("strEmail", objSetup.getStrEmail());
		hm.put("strCustEmail", strCustEmail);
		hm.put("strWebsite", objSetup.getStrWebsite());
		hm.put("strCustFaxNo",strCustFaxNo);
		hm.put("strCustPhone",strCustPhone);
		hm.put("strCustContact",strCustContact);
		hm.put("strCustVAT",strCustVAT);
		hm.put("currencyName",currencyName);
		
		JasperDesign jd = JRXmlLoader.load(reportName);
		JasperReport jr = JasperCompileManager.compileReport(jd);

		jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

	return jp;
}

@RequestMapping(value = "/rptInvoiceSlipFormat7Report", method = RequestMethod.GET)
public void funCallReportInvoiceFormat7Report(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req)
{

	// String InvCode=req.getParameter("rptInvCode").toString();
	req.getSession().removeAttribute("rptInvCode");
	type = "pdf";
	String[] arrInvCode = InvCode.split(",");
	req.getSession().removeAttribute("rptInvCode");

	InvCode = arrInvCode[0].toString();
	Connection con = objGlobalFunctions.funGetConnection(req);
	String clientCode = req.getSession().getAttribute("clientCode").toString();
	String companyName = req.getSession().getAttribute("companyName").toString();
	String userCode = req.getSession().getAttribute("usercode").toString();
	String propertyCode = req.getSession().getAttribute("propertyCode").toString();
	clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
	if (objSetup == null)
	{
		objSetup = new clsPropertySetupModel();
	}
	String suppName = "";

	String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceGSTWithMRP.jrxml");
	String imagePath = servletContext.getRealPath("/resources/images/company_Logo.jpg");

	String challanDate = "";
	String PONO = "";
	String InvDate = "";
	String CustName = "";
	String dcCode = "";

	String sqlProdDtl = " select  c.strProdName,c.strProdNameMarathi,b.dblQty,"
			// + "c.dblCostRM,"
			+ " IFNULL(b.dblPrice,0.00),c.dblMRP, IFNULL(b.dblPrice,0.00) AS dblPrice, a.dteInvDate, " + " IFNULL(d.strPName,''),ifnull(e.strDCCode,''),ifnull(e.dteDCDate,''),ifnull(e.strPONo,''), " + " b.strProdCode,f.strExciseChapter,g.dblValue as discAmt,IFNULL(d.strBAdd1,''),IFNULL(d.strBAdd2,''), " + " IFNULL(d.strBState,''),IFNULL(d.strBPin,'') ,IFNULL(d.strSAdd1,''),IFNULL(d.strSAdd2,''), " + " IFNULL(d.strSState,''),IFNULL(d.strSPin,'') ,ifNull(strCST,''),b.dblProdDiscAmount from tblinvoicehd a left outer join tblinvoicedtl b on a.strInvCode=b.strInvCode   " + " left outer join tblproductmaster c  on b.strProdCode=c.strProdCode left outer join tblpartymaster d on a.strCustCode=d.strPCode " + " left outer join tbldeliverychallanhd e on a.strSOCode=e.strDCCode " + " left outer join tblsubgroupmaster f on f.strSGCode=c.strSGCode " + " left outer join tblinvprodtaxdtl  g on a.strInvCode=g.strInvCode and a.strCustCode=g.strCustCode  " + " and b.strProdCode=g.strProdCode and g.strDocNo='Disc'   " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";

	String bAddress = "";
	String bState = "";
	String bPin = "";
	String sAddress = "";
	String sState = "";
	String sPin = "";
	String custGSTNo = "";
	double totalInvoiceValue = 0.0;
	List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlProdDtl, "sql");
	List<clsInvoiceDtlBean> dataList = new ArrayList<clsInvoiceDtlBean>();
	Map<Double, Double> hmCGSTCalculateTax = new HashMap<Double, Double>();
	Map<Double, Double> hmSGSTCalculateTax = new HashMap<Double, Double>();
	if (listProdDtl.size() > 0)
	{
		for (int i = 0; i < listProdDtl.size(); i++)
		{
			Object[] obj = (Object[]) listProdDtl.get(i);
			clsInvoiceDtlBean objDtlBean = new clsInvoiceDtlBean();
			objDtlBean.setStrProdName(obj[0].toString());
			objDtlBean.setStrProdNamemarthi(obj[1].toString());
			objDtlBean.setDblQty(Double.parseDouble(obj[2].toString()));
			objDtlBean.setDblCostRM(Double.parseDouble(obj[3].toString()));
			objDtlBean.setDblMRP(Double.parseDouble(obj[4].toString()));
			objDtlBean.setDblPrice(Double.parseDouble(obj[5].toString()));
			InvDate = objGlobalFunctions.funGetDate("dd-MM-yyyy", obj[6].toString());
			CustName = obj[7].toString();
			challanDate = obj[9].toString();
			PONO = obj[10].toString();
			dcCode = obj[8].toString();
			objDtlBean.setStrHSN(obj[12].toString());
			// objDtlBean.setDblDisAmt(Double.parseDouble(obj[13].toString())*Double.parseDouble(obj[2].toString()));
			bAddress = obj[14].toString() + " " + obj[15].toString();
			bState = obj[16].toString();
			bPin = obj[17].toString();
			objDtlBean.setDblDisAmt(Double.parseDouble(obj[13].toString()));
			sAddress = obj[18].toString() + " " + obj[19].toString();
			sState = obj[20].toString();
			sPin = obj[21].toString();
			custGSTNo = obj[22].toString();
			
			double qty=Double.parseDouble(obj[2].toString());
			double rate=Double.parseDouble(obj[3].toString());
			double subTotal=qty*rate;
			double discAmt=Double.parseDouble(obj[23].toString());								
			double netTotal=subTotal-discAmt;
			totalInvoiceValue = totalInvoiceValue + netTotal;

			String sqlQuery = " select b.strTaxCode,b.dblPercent,a.dblValue,b.strShortName,a.dblTaxableAmt " + " from tblinvprodtaxdtl a,tbltaxhd b " + " where a.strDocNo=b.strTaxCode and a.strInvCode='" + InvCode + "' " + " and  a.strProdCode='" + obj[11].toString() + "' and a.strClientCode='" + clientCode + "'  ";

			List listProdGST = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

			if (listProdGST.size() > 0)
			{
				for (int j = 0; j < listProdGST.size(); j++)
				{
					double cGStAmt = 0.0;
					double sGStAmt = 0.0;
					Object[] objGST = (Object[]) listProdGST.get(j);
					if (objGST[3].toString().equalsIgnoreCase("CGST"))
					{
						objDtlBean.setDblCGSTPer(Double.parseDouble(objGST[1].toString()));
						objDtlBean.setDblCGSTAmt(Double.parseDouble(objGST[2].toString()));
						objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
						totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
					}
					else if (objGST[3].toString().equalsIgnoreCase("SGST"))
					{
						objDtlBean.setDblSGSTPer(Double.parseDouble(objGST[1].toString()));
						objDtlBean.setDblSGSTAmt(Double.parseDouble(objGST[2].toString()));
						objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
						totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
					}
					else
					{
						objDtlBean.setDblNonGSTTaxPer(Double.parseDouble(objGST[1].toString()));
						objDtlBean.setDblNonGSTTaxAmt(Double.parseDouble(objGST[2].toString()));
						objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
						totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
					}
				}
			}
			dataList.add(objDtlBean);

		}
	}

	try
	{
		String shortName = " Paisa";
		String currCode = req.getSession().getAttribute("currencyCode").toString();
		clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);
		if (objCurrModel != null)
		{
			shortName = objCurrModel.getStrShortName();
		}

		clsNumberToWords obj1 = new clsNumberToWords();
		String totalInvoiceValueInWords = obj1.getNumberInWorld(totalInvoiceValue, shortName);

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
		hm.put("InvCode", InvCode);
		hm.put("InvDate", InvDate);
		hm.put("challanDate", challanDate);
		hm.put("PONO", PONO);
		hm.put("CustName", CustName);
		hm.put("PODate", challanDate);
		hm.put("dcCode", dcCode);
		hm.put("dataList", dataList);
		hm.put("bAddress", bAddress);
		hm.put("bState", bState);
		hm.put("bPin", bPin);
		hm.put("sAddress", sAddress);
		hm.put("sState", sState);
		hm.put("sPin", sPin);
		hm.put("totalInvoiceValueInWords", totalInvoiceValueInWords);
		hm.put("totalInvoiceValue", totalInvoiceValue);
		hm.put("strGSTNo.", objSetup.getStrCST());
		hm.put("custGSTNo", custGSTNo);

		// ////////////

		JasperDesign jd = JRXmlLoader.load(reportName);
		JasperReport jr = JasperCompileManager.compileReport(jd);

		JasperPrint jp = JasperFillManager.fillReport(jr, hm, new JREmptyDataSource());
		List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
		jprintlist.add(jp);

		if (jp != null)
		{

			ServletOutputStream servletOutputStream = resp.getOutputStream();
			if (type.trim().equalsIgnoreCase("pdf"))
			{

				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=rptTaxInvoiceRetail" + userCode + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			}
			else
			{

				// code for Exporting FLR 3 in ExcelSheet

				JRExporter exporter = new JRXlsExporter();
				resp.setContentType("application/xls");
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptTaxInvoiceRetail" + userCode + ".xlsx");
				exporter.exportReport();

			}

		}

		// ///////////////

	}
	catch (Exception e)
	{
		e.printStackTrace();
	}

}




private clsInvoiceHdModel funSaveVoidedProductList(clsInvoiceHdModel objHDModel,clsInvoiceBean objBean,String clientCode)
{
	 if (!objBean.getStrInvCode().isEmpty()) // New Entry
		{
			clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(objBean.getStrInvCode(), clientCode);
			TreeMap hmPreviousInvProdList=new TreeMap<>();
			List<clsInvoiceModelDtl> listOfVoidedInvDtlModel =new ArrayList<>();
			listOfVoidedInvDtlModel=objInvHDModelList.getListVoidedProdInvModel();
			if(objInvHDModelList.getListInvDtlModel().size()>0)
			{
				for(int cnt=0;cnt<objInvHDModelList.getListInvDtlModel().size();cnt++)
				{
					clsInvoiceModelDtl objInvDtlModel=objInvHDModelList.getListInvDtlModel().get(cnt);
					hmPreviousInvProdList.put(objInvDtlModel.getStrProdCode(), objInvDtlModel);
				}
				
				for(int i=0;i<objBean.getListclsInvoiceModelDtl().size();i++)
				{
					if(objBean.getListclsInvoiceModelDtl().get(i).getStrProdCode()!=null)
					{
						String newItem=objBean.getListclsInvoiceModelDtl().get(i).getStrProdCode().toString();
						if(hmPreviousInvProdList.containsKey(newItem))
						{
							clsInvoiceModelDtl objVoidInvDtlModel=(clsInvoiceModelDtl) hmPreviousInvProdList.get(newItem);
							double voidedQty=objVoidInvDtlModel.getDblQty()-objBean.getListclsInvoiceModelDtl().get(i).getDblQty();	
							if(voidedQty>0)
							{   
								voidedQty=funGetExistenceProduct(voidedQty,listOfVoidedInvDtlModel,objVoidInvDtlModel.getStrProdCode());
								objVoidInvDtlModel.setDblAssValue(voidedQty*(objVoidInvDtlModel.getDblAssValue()/objVoidInvDtlModel.getDblQty()));
								objVoidInvDtlModel.setDblQty(voidedQty);
								listOfVoidedInvDtlModel.add(objVoidInvDtlModel);
							}
						}
						else
						{
							clsInvoiceModelDtl objVoidInvDtlModel=(clsInvoiceModelDtl) hmPreviousInvProdList.get(newItem);
							listOfVoidedInvDtlModel.add(objVoidInvDtlModel);
						}
					}
					else
					{
						clsInvoiceModelDtl objVoidInvDtlModel=(clsInvoiceModelDtl)hmPreviousInvProdList.get( (hmPreviousInvProdList.keySet().toArray())[ i ] );
						double voidedQty=objVoidInvDtlModel.getDblQty();
						voidedQty=funGetExistenceProduct(voidedQty,listOfVoidedInvDtlModel,objVoidInvDtlModel.getStrProdCode());
						objVoidInvDtlModel.setDblAssValue(voidedQty*(objVoidInvDtlModel.getDblAssValue()/objVoidInvDtlModel.getDblQty()));
						objVoidInvDtlModel.setDblQty(voidedQty);
						listOfVoidedInvDtlModel.add(objVoidInvDtlModel);
					}
					
				}
				
				if(listOfVoidedInvDtlModel.size()>0)
				{
					objHDModel.setListVoidedProdInvModel(listOfVoidedInvDtlModel);
				}
			}
			
		}	
	   
	   return objHDModel;
  }


  private double funGetExistenceProduct(double voidedQty,List<clsInvoiceModelDtl> listOfVoidedInvDtlModel,String voidedProductCode)
  {
	   if(listOfVoidedInvDtlModel.size()>0)
		{
			for (int cn = 0; cn < listOfVoidedInvDtlModel.size(); cn++)
			{
				clsInvoiceModelDtl objModel=listOfVoidedInvDtlModel.get(cn);
				if(objModel.getStrProdCode().equals(voidedProductCode))
				{
					voidedQty+=Double.valueOf(objModel.getDblQty());	
					listOfVoidedInvDtlModel.remove(cn);
					break;
				}
			}
		}
	   return voidedQty;
  }

	

}



class clsSubGroupTaxDtl
{
	private String subGroupName;

	private double taxAmt;

	private double taxPer;

	private String subGroupChapterNo;

	public String getSubGroupName()
	{
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName)
	{
		this.subGroupName = subGroupName;
	}

	public double getTaxAmt()
	{
		return taxAmt;
	}

	public void setTaxAmt(double taxAmt)
	{
		this.taxAmt = taxAmt;
	}

	public double getTaxPer()
	{
		return taxPer;
	}

	public void setTaxPer(double taxPer)
	{
		this.taxPer = taxPer;
	}

	public String getSubGroupChapterNo()
	{
		return subGroupChapterNo;
	}

	public void setSubGroupChapterNo(String subGroupChapterNo)
	{
		this.subGroupChapterNo = subGroupChapterNo;
	}

}
