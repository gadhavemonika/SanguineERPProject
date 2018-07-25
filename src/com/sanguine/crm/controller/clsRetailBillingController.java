package com.sanguine.crm.controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.sanguine.crm.bean.clsDeliveryChallanBean;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsInvoiceDtlBean;
import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanHdModel_ID;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;
import com.sanguine.crm.model.clsInvSalesOrderDtl;
import com.sanguine.crm.model.clsInvSettlementdtlModel;
import com.sanguine.crm.model.clsInvoiceGSTModel;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceModelDtl;
import com.sanguine.crm.model.clsInvoiceProdTaxDtl;
import com.sanguine.crm.model.clsInvoiceTaxDtlModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsDeliveryChallanHdService;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;

@Controller
public class clsRetailBillingController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSubGroupMasterService objSUbGroupService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsDeliveryChallanHdService objDeliveryChallanHdService;

	@Autowired
	private clsCommercialTaxInnvoiceController objCommercialTaxInnvoiceController;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	private clsCRMSettlementMasterService objSttlementMasterService;

	List<clsSubGroupMasterModel> dataSG = new ArrayList<clsSubGroupMasterModel>();

	@RequestMapping(value = "/AutoCompletGetSubgroupName", method = RequestMethod.POST)
	public @ResponseBody Set<clsSubGroupMasterModel> getSubgroupNames(@RequestParam String term, HttpServletResponse response) {
		return simulateSubgroupNameSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<clsSubGroupMasterModel> simulateSubgroupNameSearchResult(String sgName) {
		Set<clsSubGroupMasterModel> result = new HashSet<clsSubGroupMasterModel>();
		// iterate a list and filter by ProductName
		for (clsSubGroupMasterModel sg : dataSG) {
			if (sg.getStrSGName().contains((sgName.toUpperCase()))) {
				result.add(sg);
			}
		}

		return result;
	}

	// Open Retail Billing
	@RequestMapping(value = "/frmRetailBilling", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		dataSG = new ArrayList<clsSubGroupMasterModel>();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		@SuppressWarnings("rawtypes")
		List list = objSUbGroupService.funGetList();
		for (Object objSG : list) {
			clsSubGroupMasterModel sgModel = (clsSubGroupMasterModel) objSG;
			dataSG.add(sgModel);
		}

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		Map<String, String> hmSettlement = objSttlementMasterService.funGetSettlementComboBox(clientCode);

		model.put("hmSettlement", hmSettlement);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRetailBilling_1", "command", new clsInvoiceBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRetailBilling", "command", new clsInvoiceBean());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/AutoCompletGetProdNamewithSubgroup", method = RequestMethod.POST)
	public @ResponseBody Set<clsProductMasterModel> GetProdNamewithSubgroup(@RequestParam String term, @RequestParam String sgName, @RequestParam String custcode, HttpServletResponse response, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		return simulateGetProdNamewithSubgroupSearchResult(sgName, term, clientCode, custcode);

	}

	private Set<clsProductMasterModel> simulateGetProdNamewithSubgroupSearchResult(String sgName, String ProdName, String clientCode, String custCode) {
		Set<clsProductMasterModel> result = new HashSet<clsProductMasterModel>();
		// iterate a list and filter by ProductName
		if (ProdName.equals("")) {
			List list = objProductMasterService.funGetSubGroupNameWiseProductList(sgName, clientCode);
			for (Object obj : list) {
				Object[] objArr = (Object[]) obj;
				clsProductMasterModel prodModel = (clsProductMasterModel) objArr[0];
				result.add(prodModel);

			}

		} else {
			List list = objProductMasterService.funGetSubGroupNameWiseProductList(sgName, clientCode);
			for (Object obj : list) {
				Object[] objArr = (Object[]) obj;
				clsProductMasterModel prodModel = (clsProductMasterModel) objArr[0];
				if (prodModel.getStrProdName().contains((ProdName.toUpperCase()))) {
					String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + prodModel.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode and a.strInvCode Like'__RB%' " + " and a.strCustCode='" + custCode
							+ "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + prodModel.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode and a.strInvCode Like'__RB%' " + " and a.strCustCode='" + custCode + "')";

					List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");

					if (listPrevInvData.size() > 0) {
						Object objInv[] = (Object[]) listPrevInvData.get(0);
						prodModel.setPrevUnitPrice(Double.parseDouble(objInv[1].toString()));
						prodModel.setPrevInvCode(objInv[2].toString());

					} else {
						prodModel.setPrevUnitPrice(0.0);
						prodModel.setPrevInvCode(" ");
					}
					result.add(prodModel);
				}
			}

		}

		return result;
	}

	// Save or Update Invoice
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping(value = "/saveRetailingBillng", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsInvoiceBean objBean, BindingResult result, HttpServletRequest req) {
		boolean flgHdSave = false;

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		// List<clsInvoiceGSTModel> listGST=new ArrayList<clsInvoiceGSTModel>();
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsInvoiceHdModel objHdModel = null;
			double dblCurrencyConv = 1.0;
			/*
			 * objHdModel
			 * =funPrepardModel(objBean,clientCode,userCode,propCode,req);
			 * 
			 * }
			 */

			objGlobal = new clsGlobalFunctions();
			Date today = Calendar.getInstance().getTime();
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			String reportDate = df.format(today);
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);

			clsInvoiceHdModel objHDModel = new clsInvoiceHdModel();
			objHDModel.setStrUserModified(userCode);
			objHDModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objHDModel.setDteInvDate(objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDteInvDate()) + " " + reportDate);
			objHDModel.setStrAgainst(objBean.getStrAgainst());
			objHDModel.setStrAuthorise(objBean.getStrAuthorise());

			objHDModel.setStrCustCode(objBean.getStrCustCode());

			if (objBean.getStrSettlementCode() == null) {
				objHDModel.setStrSettlementCode("");
			} else {
				objHDModel.setStrSettlementCode(objBean.getStrSettlementCode());
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
			objHDModel.setStrAuthorise("");
			objHDModel.setDblSubTotalAmt(0.0);
			objHDModel.setStrSOCode(objBean.getStrSOCode());
			objHDModel.setStrSettlementCode(objBean.getStrSettlementCode());

			objHDModel.setStrUserCreated(userCode);
			objHDModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objHDModel.setStrClientCode(clientCode);
			double taxamt = 0.0;

			if (objBean.getDblTaxAmt() != null) {
				taxamt = objBean.getDblTaxAmt();
			}
			objHDModel.setDblTotalAmt(0.0);
			objHDModel.setDblTaxAmt(0.0);
			objHDModel.setDblDiscountAmt(0.0);
			objHDModel.setStrCurrencyCode(objSetup.getStrCurrencyCode());
			clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
			if (objModel == null) {
				// dblCurrencyConv=1;
				objHDModel.setDblCurrencyConv(1);
			} else {
				dblCurrencyConv = objModel.getDblConvToBaseCurr();
				objHDModel.setDblCurrencyConv(objModel.getDblConvToBaseCurr());
			}

			StringBuilder sqlQuery = new StringBuilder();
			int decimalPlaces = Integer.parseInt(req.getSession().getAttribute("amtDecPlace").toString());
			String pattern = "";
			if (decimalPlaces == 1) {
				pattern = "#.#";
			} else if (decimalPlaces == 2) {
				pattern = "#.##";
			} else if (decimalPlaces == 3) {
				pattern = "#.###";
			} else if (decimalPlaces == 4) {
				pattern = "#.####";
			} else if (decimalPlaces == 5) {
				pattern = "#.#####";
			} else if (decimalPlaces == 6) {
				pattern = "#.######";
			} else if (decimalPlaces == 7) {
				pattern = "#.#######";
			} else if (decimalPlaces == 8) {
				pattern = "#.########";
			} else if (decimalPlaces == 9) {
				pattern = "#.#########";
			} else if (decimalPlaces == 10) {
				pattern = "#.##########";
			}

			DecimalFormat decFormat = new DecimalFormat(pattern);

			List<clsInvoiceModelDtl> listInvDtlModel = null;
			Map<String, List<clsInvoiceModelDtl>> hmInvCustDtl = new HashMap<String, List<clsInvoiceModelDtl>>();
			Map<String, Map<String, clsInvoiceTaxDtlModel>> hmInvCustTaxDtl = new HashMap<String, Map<String, clsInvoiceTaxDtlModel>>();
			Map<String, List<clsInvoiceProdTaxDtl>> hmInvProdTaxDtl = new HashMap<String, List<clsInvoiceProdTaxDtl>>();
			List<clsInvoiceDtlBean> listInvDtlBean = objBean.getListclsInvoiceModelDtl();

			for (clsInvoiceDtlBean objInvDtl : listInvDtlBean) {
				if (!(objInvDtl.getDblQty() == 0)) {
					List list = objGlobalFunctionsService.funGetList("select strExciseable,strPickMRPForTaxCal from tblproductmaster " + " where strProdCode='" + objInvDtl.getStrProdCode() + "' ", "sql");

					Object[] arrProdDtl = (Object[]) list.get(0);
					String excisable = arrProdDtl[0].toString();
					String pickMRP = arrProdDtl[1].toString();
					String key = objInvDtl.getStrCustCode() + "!" + excisable;

					if (hmInvCustDtl.containsKey(key)) {
						listInvDtlModel = hmInvCustDtl.get(key);
					} else {
						listInvDtlModel = new ArrayList<clsInvoiceModelDtl>();
					}

					clsInvoiceModelDtl objInvDtlModel = new clsInvoiceModelDtl();

					objInvDtlModel.setStrProdCode(objInvDtl.getStrProdCode());
					objInvDtlModel.setDblPrice(objInvDtl.getDblRate() * dblCurrencyConv);
					objInvDtlModel.setDblQty(objInvDtl.getDblQty());
					objInvDtlModel.setDblWeight(objInvDtl.getDblWeight());
					objInvDtlModel.setStrProdType(objInvDtl.getStrProdType());
					objInvDtlModel.setStrPktNo(objInvDtl.getStrPktNo());
					objInvDtlModel.setStrRemarks(objInvDtl.getStrRemarks());
					objInvDtlModel.setIntindex(objInvDtl.getIntindex());
					objInvDtlModel.setStrInvoiceable(objInvDtl.getStrInvoiceable());
					objInvDtlModel.setStrSerialNo(objInvDtl.getStrSerialNo());
					objInvDtlModel.setDblUnitPrice(objInvDtl.getDblRate() * dblCurrencyConv);
					objInvDtlModel.setDblAssValue(objInvDtl.getDblAssValue() * dblCurrencyConv);
					objInvDtlModel.setDblBillRate(objInvDtl.getDblBillRate() * dblCurrencyConv);
					objInvDtlModel.setStrSOCode(objInvDtl.getStrSOCode());
					objInvDtlModel.setStrCustCode(objInvDtl.getStrCustCode());
					objInvDtlModel.setStrUOM(objInvDtl.getStrUOM());
					objInvDtlModel.setDblUOMConversion(objInvDtl.getDblUOMConversion());
					objInvDtlModel.setDblQty(objInvDtl.getDblQty() / objInvDtl.getDblUOMConversion());

					List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = null;
					if (hmInvProdTaxDtl.containsKey(key)) {
						listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
					} else {
						listInvProdTaxDtl = new ArrayList<clsInvoiceProdTaxDtl>();
					}

					double prodMRP = objInvDtl.getDblUnitPrice() * dblCurrencyConv;
					if (objInvDtl.getDblWeight() > 0) {
						prodMRP = prodMRP * objInvDtl.getDblWeight();
					}
					double marginePer = 0;
					double marginAmt = 0;
					double billRate = prodMRP;

					sqlQuery.setLength(0);
					sqlQuery.append("select a.dblMargin,a.strProdCode from tblprodsuppmaster a " + " where a.strSuppCode='" + objInvDtl.getStrCustCode() + "' and a.strProdCode='" + objInvDtl.getStrProdCode() + "' " + " and a.strClientCode='" + clientCode + "' ");
					List listProdMargin = objGlobalFunctionsService.funGetList(sqlQuery.toString(), "sql");
					if (listProdMargin.size() > 0) {
						Object[] arrObjProdMargin = (Object[]) listProdMargin.get(0);
						marginePer = Double.parseDouble(arrObjProdMargin[0].toString());
						marginAmt = prodMRP * (marginePer / 100) * dblCurrencyConv;
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
					double discAmt = billRate * (discPer / 100);
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

					double prodRateForTaxCal = objInvDtl.getDblRate() * dblCurrencyConv;
					if (objInvDtl.getDblWeight() > 0) {
						prodRateForTaxCal = objInvDtl.getDblUnitPrice() * objInvDtl.getDblWeight() * dblCurrencyConv;
					}
					String prodTaxDtl = objInvDtl.getStrProdCode() + "," + prodRateForTaxCal + "," + objInvDtl.getStrCustCode() + "," + objInvDtl.getDblQty() + ",0";
					Map<String, String> hmProdTaxDtl = objGlobalFunctions.funCalculateTax(prodTaxDtl, "Sales", objGlobalFunctions.funGetDate("yyyy-MM-dd", objBean.getDteInvDate()), "0", req);
					System.out.println("Map Size= " + hmProdTaxDtl.size());

					Map<String, clsInvoiceTaxDtlModel> hmInvTaxDtl = new HashMap<String, clsInvoiceTaxDtlModel>();
					if (hmInvCustTaxDtl.containsKey(key)) {
						hmInvTaxDtl = hmInvCustTaxDtl.get(key);
					} else {
						hmInvTaxDtl.clear();
					}

					for (Map.Entry<String, String> entry : hmProdTaxDtl.entrySet()) {
						clsInvoiceTaxDtlModel objInvTaxModel = null;

						// 137.2#T0000011#Vat 12.5#NA#12.5#15.244444444444444
						// taxable amt,Tax code,tax desc,tax type,tax per

						String taxDtl = entry.getValue();
						String taxCode = entry.getKey();
						double taxableAmt = Double.parseDouble(taxDtl.split("#")[0]);
						double taxAmt = Double.parseDouble(taxDtl.split("#")[5]);
						String shortName = taxDtl.split("#")[6];

						double taxAmtForSingleQty = taxAmt / objInvDtl.getDblQty();
						if (!pattern.trim().isEmpty()) {
							taxAmtForSingleQty = Double.parseDouble(decFormat.format(taxAmtForSingleQty));
						}
						taxAmt = taxAmtForSingleQty * objInvDtl.getDblQty();

						// For Check it is Correct Or not
						// double
						// taxAmt=Math.round(Double.parseDouble(taxDtl.split("#")[5]));

						if (hmInvTaxDtl.containsKey(entry.getKey())) {
							objInvTaxModel = hmInvTaxDtl.get(entry.getKey());
							objInvTaxModel.setDblTaxableAmt(objInvTaxModel.getDblTaxableAmt() + taxableAmt);
							objInvTaxModel.setDblTaxAmt(objInvTaxModel.getDblTaxAmt() + taxAmt);
						} else {
							objInvTaxModel = new clsInvoiceTaxDtlModel();
							objInvTaxModel.setStrTaxCode(taxDtl.split("#")[1]);
							objInvTaxModel.setDblTaxAmt(taxAmt);
							objInvTaxModel.setDblTaxableAmt(taxableAmt);
							objInvTaxModel.setStrTaxDesc(taxDtl.split("#")[2]);
						}

						if (null != objInvTaxModel) {
							hmInvTaxDtl.put(taxCode, objInvTaxModel);
						}

						objInvProdTaxDtl = new clsInvoiceProdTaxDtl();
						objInvProdTaxDtl.setStrProdCode(objInvDtl.getStrProdCode());
						objInvProdTaxDtl.setStrCustCode(objInvDtl.getStrCustCode());
						objInvProdTaxDtl.setStrDocNo(taxDtl.split("#")[1]);
						objInvProdTaxDtl.setDblValue(taxAmt);
						objInvProdTaxDtl.setDblTaxableAmt(taxableAmt);
						listInvProdTaxDtl.add(objInvProdTaxDtl);

						/*
						 * clsInvoiceGSTModel objGstModel=new
						 * clsInvoiceGSTModel();
						 * objGstModel.setStrProdCode(objInvDtl
						 * .getStrProdCode());
						 * objGstModel.setStrTaxCode(taxCode);
						 * if(shortName.equalsIgnoreCase("CGST")) {
						 * objGstModel.setDblTaxableAmt(taxableAmt);
						 * objGstModel.setDblCGSTAmt(taxAmt);
						 * objGstModel.setDblCGSTPer
						 * (Double.parseDouble(taxDtl.split
						 * ("#")[4].toString())); }
						 * if(shortName.equalsIgnoreCase("SGST")) {
						 * objGstModel.setDblTaxableAmt(taxableAmt);
						 * objGstModel.setDblSGSTAmt(taxAmt);
						 * objGstModel.setDblSGSTPer
						 * (Double.parseDouble(taxDtl.split
						 * ("#")[4].toString())); } listGST.add(objGstModel);
						 */
					}

					hmInvCustTaxDtl.put(key, hmInvTaxDtl);
					hmInvProdTaxDtl.put(key, listInvProdTaxDtl);

					boolean flgProdFound = false;
					double taxtotal = 0;
					for (Map.Entry<String, List<clsInvoiceProdTaxDtl>> entryTaxTemp : hmInvProdTaxDtl.entrySet()) {
						if (!flgProdFound) {
							List<clsInvoiceProdTaxDtl> listProdTaxDtl = entryTaxTemp.getValue();
							for (clsInvoiceProdTaxDtl objProdTaxDtl : listInvProdTaxDtl) {
								if (objProdTaxDtl.getStrProdCode().equals(objInvDtlModel.getStrProdCode())) {
									if (!objProdTaxDtl.getStrDocNo().equals("Margin")) {
										if (!objProdTaxDtl.getStrDocNo().equals("Disc")) {
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
					if (assesableRate < 0) {
						assesableRate = 0;
					}

					double assableUnitRate = (assesableRate / objInvDtlModel.getDblQty());
					if (!pattern.trim().isEmpty()) {
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
			for (Map.Entry<String, List<clsInvoiceModelDtl>> entry : hmInvCustDtl.entrySet()) {
				double qty = 0.0;
				double weight = 0.0;
				List<clsInvoiceModelDtl> listInvoiceDtlModel = hmInvCustDtl.get(entry.getKey());

				if (objBean.getStrInvCode().isEmpty()) // New Entry
				{
					String invDt = objHDModel.getDteInvDate().split(" ")[0];
					String[] invDate = invDt.split("-");
					String dateInvoice = invDate[2] + "-" + invDate[1] + "-" + invDate[0];
					String invCode = objGlobalFunctions.funGenerateDocumentCode("frmRetailBilling", objBean.getDteInvDate(), req);
					// String
					// invCode=objGlobalFunctions.funGenerateDocumentCodeForLocSeries("frmRetailBilling",
					// objBean.getDteInvDate(),objBean.getStrLocCode(), req);
					objHDModel.setStrInvCode(invCode);
					objHDModel.setStrUserCreated(userCode);
					objHDModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objHDModel.setStrDulpicateFlag("N");
				} else // Update
				{
					objHDModel.setStrUserCreated(userCode);
					objHDModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objHDModel.setStrInvCode(objBean.getStrInvCode());
					objHDModel.setStrDulpicateFlag("Y");
				}
				String custCode = entry.getKey().substring(0, entry.getKey().length() - 2);
				String exciseable = entry.getKey().substring(entry.getKey().length() - 1, entry.getKey().length());

				objHDModel.setStrExciseable(exciseable);
				objHDModel.setStrCustCode(custCode);
				objHDModel.setListInvDtlModel(listInvoiceDtlModel);

				double subTotal = 0, taxAmt = 0, totalAmt = 0, totalExcisableAmt = 0;
				for (clsInvoiceModelDtl objInvItemDtl : listInvoiceDtlModel) {
					List list = objGlobalFunctionsService.funGetList("select strExciseable,strPickMRPForTaxCal,dblMRP from tblproductmaster " + " where strProdCode='" + objInvItemDtl.getStrProdCode() + "' ", "sql");
					// String excisable=list.get(0).toString();
					Object[] arrProdDtl = (Object[]) list.get(0);
					String excisable = arrProdDtl[0].toString();
					String pickMRP = arrProdDtl[1].toString();
					double dblMrp = Double.parseDouble(arrProdDtl[2].toString());
					String key = custCode + "!" + excisable;
					if (pickMRP.equals("Y")) {
						List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
						for (clsInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl) {
							if (objInvItemDtl.getStrProdCode().equals(objInvTaxModel.getStrProdCode())) {
								if (objInvTaxModel.getStrCustCode().equals(custCode) && objInvTaxModel.getStrProdCode().equals(objInvItemDtl.getStrProdCode()) && !((objInvTaxModel.getStrDocNo().equals("Disc")) || (objInvTaxModel.getStrDocNo().equals("Margin")))) {
									String taxCode = objInvTaxModel.getStrDocNo();
									List listAbtment = objGlobalFunctionsService.funGetList("select a.dblAbatement,strExcisable " + " from tbltaxhd a where a.strTaxCode='" + taxCode + "' and a.strClientCode='" + clientCode + "' ", "sql");
									Object[] arrObjExc = (Object[]) listAbtment.get(0);
									double dblAbtmt = Double.parseDouble(arrObjExc[0].toString());
									String excisableTax = arrObjExc[1].toString();

									if (dblAbtmt > 0) {
										totalAmt += (objInvItemDtl.getDblQty() * dblMrp) * dblAbtmt / 100;

										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y")) {
											totalExcisableAmt += objInvItemDtl.getDblAssValue();
										}
									} else {
										totalAmt += objInvItemDtl.getDblAssValue();
										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y")) {
											totalExcisableAmt += objInvItemDtl.getDblAssValue();
										}
									}
								}
							}
						}
					} else {
						if (true) {
							totalAmt += objInvItemDtl.getDblUnitPrice() * objInvItemDtl.getDblQty();
						} else {
							totalAmt += objInvItemDtl.getDblAssValue();
						}

						List<clsInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
						for (clsInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl) {
							if (objInvItemDtl.getStrProdCode().equals(objInvTaxModel.getStrProdCode())) {
								if (objInvTaxModel.getStrCustCode().equals(custCode) && objInvTaxModel.getStrProdCode().equals(objInvItemDtl.getStrProdCode()) && !((objInvTaxModel.getStrDocNo().equals("Disc")) || (objInvTaxModel.getStrDocNo().equals("Margin")))) {
									String taxCode = objInvTaxModel.getStrDocNo();
									List listExcTax = objGlobalFunctionsService.funGetList("select a.strExcisable from tbltaxhd a where a.strTaxCode='" + taxCode + "' and a.strClientCode='" + clientCode + "' ", "sql");

									if (listExcTax.size() > 0) {
										String excisableTax = listExcTax.get(0).toString();
										if (excisableTax.equalsIgnoreCase("Y") && excisable.equalsIgnoreCase("Y")) {
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
				for (Map.Entry<String, clsInvoiceTaxDtlModel> entryTaxDtl : hmInvTaxDtlTemp.entrySet()) {
					listInvoiceTaxDtl.add(entryTaxDtl.getValue());
					taxAmt += entryTaxDtl.getValue().getDblTaxAmt();

					String sqlTaxDtl = "select strExcisable from tbltaxhd " + " where strTaxCode='" + entryTaxDtl.getValue().getStrTaxCode() + "' ";
					List list = objGlobalFunctionsService.funGetList(sqlTaxDtl, "sql");
					if (list.size() > 0) {
						for (int cntExTax = 0; cntExTax < list.size(); cntExTax++) {
							String excisable = list.get(cntExTax).toString();
							if (excisable.equalsIgnoreCase("Y")) {
								excisableTaxAmt += entryTaxDtl.getValue().getDblTaxAmt();
							}
						}
					}
				}

				double grandTotal = totalAmt + taxAmt;
				subTotal = totalAmt + excisableTaxAmt;

				if (exciseable.equalsIgnoreCase("Y")) {
					subTotal = totalExcisableAmt + excisableTaxAmt;
					grandTotal = totalExcisableAmt + taxAmt;
					objHDModel.setDblTotalAmt(totalExcisableAmt);
				} else {
					objHDModel.setDblTotalAmt(totalAmt);
				}
				objHDModel.setDblSubTotalAmt(subTotal);
				objHDModel.setDblTaxAmt(taxAmt);
				objHDModel.setDblGrandTotal(grandTotal);

				List<clsInvSalesOrderDtl> listInvSODtl = new ArrayList<clsInvSalesOrderDtl>();
				String[] arrSOCodes = objHDModel.getStrSOCode().split(",");
				for (int cn = 0; cn < arrSOCodes.length; cn++) {
					clsInvSalesOrderDtl objInvSODtl = new clsInvSalesOrderDtl();
					objInvSODtl.setStrSOCode(arrSOCodes[cn]);
					objInvSODtl.setDteInvDate(objHDModel.getDteInvDate());
					listInvSODtl.add(objInvSODtl);
				}
				objHDModel.setListInvSalesOrderModel(listInvSODtl);
				objHDModel.setListInvTaxDtlModel(listInvoiceTaxDtl);
				objHDModel.setListInvProdTaxDtlModel(hmInvProdTaxDtl.get(entry.getKey()));

				objInvoiceHdService.funAddUpdateInvoiceHd(objHDModel);
				// String dcCode=funDataSetInDeliveryChallan(objHDModel,req);
				arrInvCode.append(objHDModel.getStrInvCode() + ",");
				// arrDcCode.append(dcCode+",");
			}

			// **********************Save data Invoice
			// GST*************************

			/*
			 * for(clsInvoiceGSTModel objGSTModel:listGST) {
			 * 
			 * objGSTModel.setStrInvCode(objHDModel.getStrInvCode());
			 * objGSTModel.setStrClientCode(objHDModel.getStrClientCode());
			 * objInvoiceHdService.funAddUpdateInvoiceGST(objGSTModel); }
			 */

			// for(clsInvoiceDtlBean objInvDtl:listInvDtlBean)
			// {
			// clsTaxHdModel objTax=new clsTaxHdModel();
			//
			// Map<String,String>hGST=
			// objGlobalfunction.funCalculateTax(objInvDtl.getStrProdCode(),"Sales",objHDModel.getDteInvDate()
			// , req);
			//
			//
			// List
			// listTax=objGlobalFunctionsService.funGetList("from clsTaxHdModel a,clsProductMasterModel b  where a.strShortName='CGST' and b.strProdCode='"+objInvDtl.getStrProdCode()+"' and a.strTaxIndicator=b.strTaxIndicator and  a.strClientCode = '"+clientCode+"'",
			// "hql");
			// clsInvoiceGSTModel objGSTModel=new clsInvoiceGSTModel();
			// // Select * from tbltaxhd a,tblproductmaster b
			// // where a.strShortName='CGST' and b.strProdCode='' and
			// a.strTaxIndicator=b.strTaxIndicator
			// if(listTax.size()>0){
			//
			// Object [] obj=(Object[])listTax.get(0);
			// clsTaxHdModel objTaxModel=(clsTaxHdModel)obj[0];
			// objGSTModel.setDblCGSTPer(objTaxModel.getDblPercent());
			//
			// objGSTModel.setDblCGSTAmt(objTaxModel.getDblAmount());
			// }
			// objGSTModel.setStrProdCode(objInvDtl.getStrProdCode());
			// objGSTModel.setStrInvCode(objHDModel.getStrClientCode());
			// objGSTModel.setStrClientCode(objHDModel.getStrInvCode());
			//
			//
			// @@
			// List
			// listSGST=objGlobalFunctionsService.funGetList("from clsTaxHdModel a,clsProductMasterModel b  where a.strShortName='SCGST' and b.strProdCode='"+objInvDtl.getStrProdCode()+"' and a.strTaxIndicator=b.strTaxIndicator and  a.strClientCode = '"+clientCode+"'",
			// "hql");
			// if(listSGST.size()>0){
			// Object [] obj=(Object[])listSGST.get(0);
			// clsTaxHdModel objTaxModelSGST=(clsTaxHdModel)obj[0];
			//
			// objGSTModel.setDblSGSTPer(objTaxModelSGST.getDblPercent());
			// objGSTModel.setDblSGSTAmt(objTaxModelSGST.getDblAmount());
			// objInvoiceHdService.funAddUpdateInvoiceGST(objGSTModel);
			// }
			// }
			// /******************GST
			// End*********************************************

			for (clsInvSettlementdtlModel objInvSetlDtl : objBean.getListInvsettlementdtlModel()) {
				objInvSetlDtl.setDblActualAmt(objInvSetlDtl.getDblActualAmt() * dblCurrencyConv);
				objInvSetlDtl.setDblPaidAmt(objInvSetlDtl.getDblPaidAmt() * dblCurrencyConv);
				objInvSetlDtl.setDblRefundAmt(objInvSetlDtl.getDblRefundAmt() * dblCurrencyConv);
				objInvSetlDtl.setDblSettlementAmt(objInvSetlDtl.getDblSettlementAmt() * dblCurrencyConv);
				objInvSetlDtl.setStrCustomerCode(objBean.getStrCustCode());
				objInvSetlDtl.setStrClientCode(clientCode);
				objInvSetlDtl.setStrDataPostFlag("N");
				objInvSetlDtl.setStrInvCode(objHDModel.getStrInvCode());
				objInvSetlDtl.setDteInvDate(objHDModel.getDteInvDate().split(" ")[0]);
				objInvSetlDtl.setStrRoomNo("");
				objInvSetlDtl.setStrFolioNo("");
				objInvSetlDtl.setStrGiftVoucherCode("");
				objInvoiceHdService.funAddUpdateInvSettlementdtl(objInvSetlDtl);
			}

			if (objCompModel.getStrWebBookModule().equals("Yes")) {
				objHDModel.setDteInvDate(objHDModel.getDteInvDate().split(" ")[0]);
				String strJVNo = objCommercialTaxInnvoiceController.funGenrateJVforComercialTax(objHDModel, clientCode, userCode, propCode, "Invoice", req);

				objHDModel.setStrSOCode(strJVNo);

				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
				req.getSession().setAttribute("rptInvCode", arrInvCode);
				req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
				// req.getSession().setAttribute("rptDcCode",arrDcCode);

				return new ModelAndView("redirect:/frmRetailBilling.html?saddr=" + urlHits);
			} else {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
				req.getSession().setAttribute("rptInvCode", arrInvCode);
				req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
				// req.getSession().setAttribute("rptDcCode",arrDcCode);
			}
			return new ModelAndView("redirect:/frmRetailBilling.html?saddr=" + urlHits);
			/*
			 * req.getSession().setAttribute("success", true);
			 * req.getSession().setAttribute
			 * ("successMessage","Invoice Code : ".concat
			 * (arrInvCode.toString()));
			 * req.getSession().setAttribute("rptInvCode",arrInvCode);
			 * req.getSession
			 * ().setAttribute("rptInvDate",objHDModel.getDteInvDate()); //
			 * req.getSession().setAttribute("rptDcCode",arrDcCode);
			 * 
			 * return new
			 * ModelAndView("redirect:/frmRetailBilling.html?saddr="+urlHits);
			 */

		} else {
			return new ModelAndView("frmRetailBilling?saddr=" + urlHits, "command", new clsInvoiceBean());
		}
		// return new
		// ModelAndView("redirect:/frmRetailBilling.html?saddr="+urlHits);
	}

	/*
	 * private clsInvoiceHdModel funPrepardModel(clsInvoiceBean objBean,String
	 * clientCode,String userCode,String propCode,HttpServletRequest req) {
	 * clsInvoiceHdModel objHdModel =null; boolean res=false;
	 * if(null!=req.getSession().getAttribute("hmAuthorization")) {
	 * HashMap<String,Boolean>
	 * hmAuthorization=(HashMap)req.getSession().getAttribute
	 * ("hmAuthorization"); if(hmAuthorization.get("RetailBilling")) { res=true;
	 * } }
	 * 
	 * objGlobal=new clsGlobalFunctions(); long lastNo=0; String strDocNo="";
	 * objHdModel = new clsInvoiceHdModel();
	 * if(objBean.getStrInvCode().trim().length()==0) {
	 * strDocNo=objGlobalFunctions
	 * .funGenerateDocumentCodeForLocSeries("frmRetailBilling",
	 * objBean.getDteInvDate(),objBean.getStrLocCode(), req);
	 * objHdModel.setStrInvCode(strDocNo); objHdModel.setIntid(lastNo);
	 * objHdModel.setStrUserCreated(userCode);
	 * objHdModel.setDteCreatedDate(objGlobal
	 * .funGetCurrentDateTime("yyyy-MM-dd"));
	 * 
	 * if(res) { objHdModel.setStrAuthorise("No"); } else {
	 * objHdModel.setStrAuthorise("Yes"); }
	 * 
	 * }else { objHdModel
	 * =objInvoiceHdService.funGetInvoiceHd(objBean.getStrInvCode(),clientCode);
	 * if(objHdModel== null){
	 * strDocNo=objGlobalFunctions.funGenerateDocumentCodeForLocSeries
	 * ("frmRetailBilling", objBean.getDteInvDate(),objBean.getStrLocCode(),
	 * req); objHdModel.setStrInvCode(strDocNo); objHdModel.setIntid(lastNo);
	 * objHdModel.setStrUserCreated(userCode);
	 * objHdModel.setDteCreatedDate(objGlobal
	 * .funGetCurrentDateTime("yyyy-MM-dd")); if(res) {
	 * objHdModel.setStrAuthorise("No");
	 * 
	 * } else { objHdModel.setStrAuthorise("Yes"); } } else {
	 * if(objGlobalFunctions.funCheckAuditFrom("frmRetailBilling", req)) { //
	 * funSaveAudit(objBean.getStrInvCode(),"Edit",req); }
	 * objHdModel.setStrInvCode(objBean.getStrInvCode()); } }
	 * 
	 * objHdModel.setDteInvDate(objGlobalFunctions.funGetDate("yyyy-MM-dd",
	 * objBean.getDteInvDate())); objHdModel.setStrAgainst("");
	 * objHdModel.setStrSOCode("");
	 * objHdModel.setStrCustCode(objBean.getStrCustCode());
	 * objHdModel.setStrDktNo(""); objHdModel.setStrDulpicateFlag("N");
	 * objHdModel.setStrExciseable("N"); objHdModel.setStrInvNo("");
	 * objHdModel.setStrLocCode(objBean.getStrLocCode());
	 * objHdModel.setStrMInBy(""); objHdModel.setStrNarration("");
	 * objHdModel.setStrPackNo(""); objHdModel.setStrPONo("");
	 * objHdModel.setStrReaCode(""); objHdModel.setStrSAdd1("");
	 * objHdModel.setStrSAdd2(""); objHdModel.setStrSCity("");
	 * objHdModel.setStrSCtry(""); objHdModel.setStrSerialNo("");
	 * objHdModel.setStrSPin(""); objHdModel.setStrSState("");
	 * objHdModel.setStrTimeInOut(""); objHdModel.setStrVehNo("");
	 * objHdModel.setStrWarraValidity(""); objHdModel.setStrWarrPeriod("");
	 * objHdModel
	 * .setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
	 * objHdModel.setDblDiscount(0.00); objHdModel.setDblDiscountAmt(0.00);
	 * objHdModel.setDblGrandTotal(0.00); objHdModel.setDblTaxAmt(0.00);
	 * objHdModel.setDblTotalAmt(0.00);
	 * 
	 * List<clsInvoiceGSTModel> listGST=new ArrayList<clsInvoiceGSTModel>();
	 * 
	 * 
	 * List<clsInvoiceModelDtl> listInvDtlModel =new
	 * ArrayList<clsInvoiceModelDtl>(); for(clsInvoiceDtlBean objDtlBean :
	 * objBean.getListclsInvoiceModelDtl()) {
	 * Map<String,List<clsInvoiceModelDtl>> hmInvCustDtl=new
	 * HashMap<String,List<clsInvoiceModelDtl>>();
	 * Map<String,Map<String,clsInvoiceTaxDtlModel>> hmInvCustTaxDtl=new
	 * HashMap<String,Map<String,clsInvoiceTaxDtlModel>>();
	 * Map<String,List<clsInvoiceProdTaxDtl>> hmInvProdTaxDtl=new
	 * HashMap<String,List<clsInvoiceProdTaxDtl>>(); List<clsInvoiceProdTaxDtl>
	 * listInvProdTaxDtl=null; clsInvoiceProdTaxDtl objInvProdTaxDtl=new
	 * clsInvoiceProdTaxDtl();
	 * 
	 * List list=objGlobalFunctionsService.funGetList(
	 * "select strExciseable,strPickMRPForTaxCal from tblproductmaster " +
	 * " where strProdCode='"+objDtlBean.getStrProdCode()+"' ", "sql");
	 * 
	 * Object[] arrProdDtl=(Object[]) list.get(0); String
	 * excisable=arrProdDtl[0].toString(); String
	 * pickMRP=arrProdDtl[1].toString(); String
	 * key=objDtlBean.getStrCustCode()+"!"+excisable;
	 * 
	 * if(hmInvCustDtl.containsKey(key)) {
	 * listInvDtlModel=hmInvCustDtl.get(key); } else { listInvDtlModel=new
	 * ArrayList<clsInvoiceModelDtl>(); }
	 * 
	 * 
	 * 
	 * clsInvoiceModelDtl objInvDtlModel =new clsInvoiceModelDtl();
	 * objInvDtlModel.setDblAssValue(0.00); objInvDtlModel.setDblBillRate(0.00);
	 * objInvDtlModel.setDblPrice(objDtlBean.getDblMRP());
	 * objInvDtlModel.setDblQty(objDtlBean.getDblQty());
	 * objInvDtlModel.setDblUnitPrice(objDtlBean.getDblRate());
	 * objInvDtlModel.setDblWeight(0.00); objInvDtlModel.setIntindex(0);
	 * objInvDtlModel.setStrInvoiceable("N");
	 * objInvDtlModel.setStrCustCode(objDtlBean.getStrCustCode());
	 * objInvDtlModel.setStrPktNo("");
	 * objInvDtlModel.setStrProdCode(objDtlBean.getStrProdCode());
	 * objInvDtlModel.setStrProdType(""); objInvDtlModel.setStrRemarks("");
	 * objInvDtlModel.setStrSerialNo(""); objInvDtlModel.setStrSOCode("");
	 * listInvDtlModel.add(objInvDtlModel);
	 * 
	 * double disAmt =0; String prodCodeForTax="";
	 * prodCodeForTax=prodCodeForTax+
	 * "!"+objDtlBean.getStrProdCode()+","+objDtlBean
	 * .getDblRate()+","+objDtlBean
	 * .getStrCustCode()+","+objDtlBean.getDblQty()+","+disAmt;
	 * Map<String,String> hGST=
	 * objGlobalFunctions.funCalculateTax(prodCodeForTax
	 * ,"Sales",objGlobalFunctions.funGetDate("yyyy-MM-dd",
	 * objBean.getDteInvDate()) , req);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * return objHdModel;
	 * 
	 * }
	 */

	private String funDataSetInDeliveryChallan(clsInvoiceHdModel objInvHDModel, HttpServletRequest req) {

		boolean flg = true;
		clsDeliveryChallanHdModel objDcHdModel = new clsDeliveryChallanHdModel();
		long lastNo = 0;
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String sqlFetchDc = "select strDCCode from tbldeliverychallanhd where strSoCode='" + objInvHDModel.getStrInvCode() + "' and strClientCode='" + objInvHDModel.getStrClientCode() + "' ";
		List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

		if (!listFetchDc.isEmpty()) {
			Object objDc = listFetchDc.get(0);

			objDcHdModel = new clsDeliveryChallanHdModel(new clsDeliveryChallanHdModel_ID(objDc.toString(), clientCode));
		} else {

			lastNo = objGlobalFunctionsService.funGetLastNo("tbldeliverychallanhd", "DCCode", "intId", clientCode);
			String year = objGlobal.funGetSplitedDate(startDate)[2];
			String cd = objGlobal.funGetTransactionCode("DC", propCode, year);
			String strDCCode = cd + String.format("%06d", lastNo);
			objDcHdModel.setStrDCCode(strDCCode);
			objDcHdModel.setStrUserCreated(userCode);
			objDcHdModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
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
		objDcHdModel.setStrAuthorise(objInvHDModel.getStrAuthorise());
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
		objDcHdModel.setStrCloseDC("N");
		objDcHdModel.setStrDCNo("");
		objDeliveryChallanHdService.funAddUpdateDeliveryChallanHd(objDcHdModel);
		clsInvoiceModelDtl objInvDtlModel = null;
		List<clsInvoiceModelDtl> listInvDtl = objInvHDModel.getListInvDtlModel();
		if (!listInvDtl.isEmpty())
			objDeliveryChallanHdService.funDeleteDtl(objDcHdModel.getStrDCCode(), clientCode);
		{
			for (int i = 0; i < listInvDtl.size(); i++) {
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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadRetailInvoiceHdData", method = RequestMethod.GET)
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

		objBeanInv = funPrepardHdBean(objInvoiceHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsInvoiceModelDtl> listDCDtl = new ArrayList<clsInvoiceModelDtl>();
		clsInvoiceHdModel objInvHDModelList = objInvoiceHdService.funGetInvoiceDtl(invCode, clientCode);

		List<clsInvSettlementdtlModel> objInvSettleList = objInvoiceHdService.funGetListInvSettlementdtl(invCode, objBeanInv.getDteInvDate().split(" ")[0], clientCode);
		List<clsInvSettlementdtlModel> objUpdateInvSettleList = new ArrayList<clsInvSettlementdtlModel>();

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
			objBeanInvoice.setDblMRP(objProdModle.getDblMRP());
			objBeanInvoice.setStrUOM(objProdModle.getStrUOM());
			objBeanInvoice.setDblUOMConversion(obj.getDblUOMConversion());
			listInvDtlBean.add(objBeanInvoice);

		}
		objBeanInv.setListclsInvoiceModelDtl(listInvDtlBean);

		for (int i = 0; i < objInvSettleList.size(); i++) {
			clsInvSettlementdtlModel objSett = objInvSettleList.get(i);
			clsSettlementMasterModel objSettMaster = objSttlementMasterService.funGetObject(objSett.getStrSettlementCode(), clientCode);
			objSett.setStrSettlementName(objSettMaster.getStrSettlementDesc());
			objUpdateInvSettleList.add(objSett);
		}
		objBeanInv.setListInvsettlementdtlModel(objUpdateInvSettleList);

		return objBeanInv;
	}

	private clsInvoiceBean funPrepardHdBean(clsInvoiceHdModel objInvHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel) {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadProductDataForRetailling", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funAssignFields(@RequestParam("prodCode") String prodCode, @RequestParam("custCode") String suppCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String currVal = req.getSession().getAttribute("currValue").toString();
		String locCode = req.getSession().getAttribute("locationCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);

		clsProductMasterModel objProduct = objProductMasterService.funGetObject(prodCode, clientCode);

		if (objSetup.getStrMultiCurrency().equalsIgnoreCase("Y")) {
			clsProductReOrderLevelModel objReOrder = objProductMasterService.funGetProdReOrderLvl(prodCode, locCode, clientCode);
			double dblreOrderPrice = 0;
			if (objReOrder != null) {
				dblreOrderPrice = objReOrder.getDblPrice();
			}
			objProduct.setDblUnitPrice(dblreOrderPrice);
		}
		String sql = " select max(a.dblPrice) from tblstockadjustmentdtl a,tblstockadjustmenthd b " + " where a.strSACode=b.strSACode and a.strProdCode='" + prodCode + "' and a.strRemark like '%Invoice%'" + " and b.strLocCode='" + locCode + "' " + " and a.strClientCode='" + clientCode + "' ";
		List listLastInvPrice = objGlobalFunctionsService.funGetList(sql);
		if (!listLastInvPrice.isEmpty()) {
			Object objInv = listLastInvPrice.get(0);
			if (objInv != null) {
				objProduct.setDblUnitPrice(Double.parseDouble(objInv.toString()));
			} else {
				objProduct.setDblUnitPrice(objProduct.getDblUnitPrice() / Double.parseDouble(currVal));
				objProduct.setDblMRP(objProduct.getDblMRP() / Double.parseDouble(currVal));
			}

		}
		if (objProduct == null) {
			objProduct = new clsProductMasterModel();
			objProduct.setStrProdCode("Invalid Product Code");
		}
		return objProduct;

	}

}
