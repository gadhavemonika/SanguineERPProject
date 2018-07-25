package com.sanguine.crm.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
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
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.bean.clsInvoiceDtlBean;
import com.sanguine.crm.bean.clsInvoiceProdDtlReportBean;
import com.sanguine.crm.bean.clsInvoiceTaxDtlBean;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsProFormaInvSalesOrderDtl;
import com.sanguine.crm.model.clsProFormaInvoiceHdModel;
import com.sanguine.crm.model.clsProFormaInvoiceHdModel_ID;
import com.sanguine.crm.model.clsProFormaInvoiceModelDtl;
import com.sanguine.crm.model.clsProFormaInvoiceProdTaxDtl;
import com.sanguine.crm.model.clsProFormaInvoiceTaxDtlModel;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.crm.service.clsProFormaInvoiceHdService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProdSuppMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsProductReOrderLevelModel_ID;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsUserDtlModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.util.clsNumberToWords;

@Controller
public class clsProFormaInvoice {

	@Autowired
	private clsSubGroupMasterService objSubGroupService;
	
	@Autowired
	private clsCRMSettlementMasterService objSettlementService;
	
	@Autowired
	clsProFormaInvoiceHdService objProFormaInvoiceHdService;
	
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
	private clsCurrencyMasterService objCurrencyMasterService;
	@Autowired
	private clsPartyMasterService objPartyMasterService;
	
	

	List<clsSubGroupMasterModel> dataSG = new ArrayList<clsSubGroupMasterModel>();
	
	@RequestMapping(value = "/frmProFormaInvoice", method = RequestMethod.GET)
	public ModelAndView funInvoice(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		
		dataSG = new ArrayList<clsSubGroupMasterModel>();
		@SuppressWarnings("rawtypes")
		List list = objSubGroupService.funGetList();
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
		
		HashMap<String,clsUserDtlModel> hmUserPrivileges = (HashMap)request.getSession().getAttribute("hmUserPrivileges");
		model.put("urlHits", urlHits);
			
		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
		strAgainst.add("Delivery Challan");
		strAgainst.add("Sales Order");
		model.put("againstList", strAgainst);

		Map<String, String> settlementList = objSettlementService.funGetSettlementComboBox(clientCode);
		model.put("settlementList", settlementList);
		
		String authorizationInvoiceCode = "";
		boolean flagOpenFromAuthorization = true;
		try {
			authorizationInvoiceCode = request.getParameter("authorizationInvoiceCode").toString();
		} catch (NullPointerException e) {
			flagOpenFromAuthorization = false;
		}
		model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
		if (flagOpenFromAuthorization) {
			model.put("authorizationInvoiceCode", authorizationInvoiceCode);
		}
		
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProFormaInvoice_1", "command", new clsInvoiceBean());
		} else {
			return new ModelAndView("frmProFormaInvoice", "command", new clsInvoiceBean());
		}
	}
	
	// Assign filed function to set data onto form for edit transaction.
		@SuppressWarnings("rawtypes")
		@RequestMapping(value = "/loadProFormaInvoiceHdData", method = RequestMethod.GET)
		public @ResponseBody clsInvoiceBean funAssignFields(@RequestParam("invCode") String invCode, HttpServletRequest req) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());
			clsInvoiceBean objBeanInv = new clsInvoiceBean();

			List<Object> objDC = objProFormaInvoiceHdService.funGetInvoice(invCode, clientCode);
			clsProFormaInvoiceHdModel objInvoiceHdModel = null;
			clsLocationMasterModel objLocationMasterModel = null;
			clsPartyMasterModel objPartyMasterModel = null;

			for (int i = 0; i < objDC.size(); i++) {
				Object[] ob = (Object[]) objDC.get(i);
				objInvoiceHdModel = (clsProFormaInvoiceHdModel) ob[0];
				objLocationMasterModel = (clsLocationMasterModel) ob[1];
				objPartyMasterModel = (clsPartyMasterModel) ob[2];
			}

			objBeanInv = funPrepardHdBean(objInvoiceHdModel, objLocationMasterModel, objPartyMasterModel, currValue);
			objBeanInv.setStrCustName(objPartyMasterModel.getStrPName());
			objBeanInv.setStrLocName(objLocationMasterModel.getStrLocName());
			List<clsProFormaInvoiceModelDtl> listDCDtl = new ArrayList<clsProFormaInvoiceModelDtl>();
			clsProFormaInvoiceHdModel objInvHDModelList = objProFormaInvoiceHdService.funGetProFormaInvoiceDtl(invCode, clientCode);
//
			List<clsProFormaInvoiceModelDtl> listInvDtlModel = objInvHDModelList.getListInvDtlModel();
			List<clsInvoiceDtlBean> listInvDtlBean = new ArrayList();
			for (int i = 0; i < listInvDtlModel.size(); i++) {
				clsInvoiceDtlBean objBeanInvoice = new clsInvoiceDtlBean();

				clsProFormaInvoiceModelDtl obj = listInvDtlModel.get(i);
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
				objBeanInvoice.setDblDisAmt(obj.getDblProdDiscAmount());
				String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblproformainvoicedtl a,tblproformainvoicehd b " + " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode " + " and a.strCustCode='" + objBeanInv.getStrCustName() + "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblproformainvoicedtl a,tblproformainvoicehd b "
						+ " where a.strProdCode='" + obj.getStrProdCode() + "' and a.strClientCode='" + clientCode + "' and b.strInvCode=a.strInvCode  " + " and a.strCustCode='" + objBeanInv.getStrCustCode() + "')";

				List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");

				if (listPrevInvData.size() > 0) {
					Object objInv[] = (Object[]) listPrevInvData.get(0);
					objBeanInvoice.setPrevUnitPrice(Double.parseDouble(objInv[1].toString()) / currValue);
					objBeanInvoice.setPrevInvCode(objInv[2].toString());

				} else {
					objBeanInvoice.setPrevUnitPrice(0.0);
					objBeanInvoice.setPrevInvCode(" ");
				}

				listInvDtlBean.add(objBeanInvoice);

			}
			objBeanInv.setListclsInvoiceModelDtl(listInvDtlBean);

			String sql = "select strTaxCode,strTaxDesc,dblTaxableAmt,dblTaxAmt from tblproformainvtaxdtl " + "where strInvCode='" + invCode + "' and strClientCode='" + clientCode + "'";
			List list = objGlobalFunctionsService.funGetList(sql, "sql");
			List<clsInvoiceTaxDtlBean> listInvTaxDtl = new ArrayList<clsInvoiceTaxDtlBean>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				clsInvoiceTaxDtlBean objTaxDtl = new clsInvoiceTaxDtlBean();
				Object[] arrObj = (Object[]) list.get(cnt);
				objTaxDtl.setStrTaxCode(arrObj[0].toString());
				objTaxDtl.setStrTaxDesc(arrObj[1].toString());
				objTaxDtl.setStrTaxableAmt(Double.parseDouble(arrObj[2].toString()));
				objTaxDtl.setStrTaxAmt(Double.parseDouble(arrObj[3].toString()));

				listInvTaxDtl.add(objTaxDtl);
			}
			objBeanInv.setListInvoiceTaxDtl(listInvTaxDtl);
			return objBeanInv;
		}

		private clsInvoiceBean funPrepardHdBean(clsProFormaInvoiceHdModel objInvHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel, double currValue) {
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
			objBean.setDblSubTotalAmt(objInvHdModel.getDblSubTotalAmt() / currValue);
			objBean.setDblTaxAmt(objInvHdModel.getDblTaxAmt() / currValue);
			objBean.setDblTotalAmt(objInvHdModel.getDblTotalAmt() / currValue);
			objBean.setDblDiscountAmt(objInvHdModel.getDblDiscountAmt() / currValue);
			objBean.setDblDiscount(objInvHdModel.getDblDiscount() );
			objBean.setStrSettlementCode(objInvHdModel.getStrSettlementCode());
			objBean.setStrAuthorize(objInvHdModel.getStrAuthorise());

			return objBean;
		}

		
		// Save or Update Invoice
		@SuppressWarnings({ "unused", "rawtypes" })
		@RequestMapping(value = "/saveProFormaInvoice", method = RequestMethod.POST)
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
				double dblCurrencyConv = 1.0;
				
				Date today = Calendar.getInstance().getTime();
				DateFormat df = new SimpleDateFormat("HH:mm:ss");
				String reportDate = df.format(today);
				clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
				clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);

				clsProFormaInvoiceHdModel objHDModel = new clsProFormaInvoiceHdModel();
				objHDModel.setStrUserModified(userCode);
				objHDModel.setDteLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				objHDModel.setDteInvDate(objBean.getDteInvDate() + " " + reportDate);
				objHDModel.setStrAgainst(objBean.getStrAgainst());
				objHDModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));
				if (objBean.getStrAgainst().equalsIgnoreCase("Sales Order")) {
					objHDModel.setStrCustCode("");
				} else {
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

				if (objBean.getDblTaxAmt() != null) {
					taxamt = objBean.getDblTaxAmt();
				}
				objHDModel.setDblTotalAmt(0.0);
				objHDModel.setDblTaxAmt(0.0);

				objHDModel.setStrCurrencyCode(objSetup.getStrCurrencyCode());
				clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(objSetup.getStrCurrencyCode(), clientCode);
				if (objModel == null) {
					// dblCurrencyConv=1;
					objHDModel.setDblCurrencyConv(dblCurrencyConv);
				} else {
					dblCurrencyConv = objModel.getDblConvToBaseCurr();
					objHDModel.setDblCurrencyConv(objModel.getDblConvToBaseCurr());
				}

				// /********Save Data forDetail in SO***********////

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

				List<clsProFormaInvoiceModelDtl> listInvDtlModel = null;
				Map<String, List<clsProFormaInvoiceModelDtl>> hmInvCustDtl = new HashMap<String, List<clsProFormaInvoiceModelDtl>>();
				Map<String, Map<String, clsProFormaInvoiceTaxDtlModel>> hmInvCustTaxDtl = new HashMap<String, Map<String, clsProFormaInvoiceTaxDtlModel>>();
				Map<String, List<clsProFormaInvoiceProdTaxDtl>> hmInvProdTaxDtl = new HashMap<String, List<clsProFormaInvoiceProdTaxDtl>>();
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
							listInvDtlModel = new ArrayList<clsProFormaInvoiceModelDtl>();
						}

						clsProFormaInvoiceModelDtl objInvDtlModel = new clsProFormaInvoiceModelDtl();

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
						List<clsProFormaInvoiceProdTaxDtl> listInvProdTaxDtl = null;
						if (hmInvProdTaxDtl.containsKey(key)) {
							listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
						} else {
							listInvProdTaxDtl = new ArrayList<clsProFormaInvoiceProdTaxDtl>();
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
							marginAmt = prodMRP * (marginePer / 100);
							billRate = prodMRP - marginAmt;
						}
						clsProFormaInvoiceProdTaxDtl objInvProdTaxDtl = new clsProFormaInvoiceProdTaxDtl();
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

						objInvProdTaxDtl = new clsProFormaInvoiceProdTaxDtl();
						objInvProdTaxDtl.setStrProdCode(objInvDtl.getStrProdCode());
						objInvProdTaxDtl.setStrCustCode(objInvDtl.getStrCustCode());
						objInvProdTaxDtl.setStrDocNo("Disc");
						objInvProdTaxDtl.setDblValue(discAmt);
						objInvProdTaxDtl.setDblTaxableAmt(0);
						listInvProdTaxDtl.add(objInvProdTaxDtl);

						double prodRateForTaxCal = objInvDtl.getDblUnitPrice() * dblCurrencyConv;
						if (objInvDtl.getDblWeight() > 0) {
							prodRateForTaxCal = objInvDtl.getDblUnitPrice() * objInvDtl.getDblWeight() * dblCurrencyConv;
						}
						String prodTaxDtl = objInvDtl.getStrProdCode() + "," + prodRateForTaxCal + "," + objInvDtl.getStrCustCode() + "," + objInvDtl.getDblQty() + ",0";
						Map<String, String> hmProdTaxDtl = objGlobalFunctions.funCalculateTax(prodTaxDtl, "Sales", objBean.getDteInvDate(), "0", req);
						System.out.println("Map Size= " + hmProdTaxDtl.size());

						Map<String, clsProFormaInvoiceTaxDtlModel> hmInvTaxDtl = new HashMap<String, clsProFormaInvoiceTaxDtlModel>();
						if (hmInvCustTaxDtl.containsKey(key)) {
							hmInvTaxDtl = hmInvCustTaxDtl.get(key);
						} else {
							hmInvTaxDtl.clear();
						}

						for (Map.Entry<String, String> entry : hmProdTaxDtl.entrySet()) {
							clsProFormaInvoiceTaxDtlModel objInvTaxModel = null;

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
								objInvTaxModel = new clsProFormaInvoiceTaxDtlModel();
								objInvTaxModel.setStrTaxCode(taxDtl.split("#")[1]);
								objInvTaxModel.setDblTaxAmt(taxAmt);
								objInvTaxModel.setDblTaxableAmt(taxableAmt);
								objInvTaxModel.setStrTaxDesc(taxDtl.split("#")[2]);
							}

							if (null != objInvTaxModel) {
								hmInvTaxDtl.put(taxCode, objInvTaxModel);
							}

							objInvProdTaxDtl = new clsProFormaInvoiceProdTaxDtl();
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
						for (Map.Entry<String, List<clsProFormaInvoiceProdTaxDtl>> entryTaxTemp : hmInvProdTaxDtl.entrySet()) {
							if (!flgProdFound) {
								List<clsProFormaInvoiceProdTaxDtl> listProdTaxDtl = entryTaxTemp.getValue();
								for (clsProFormaInvoiceProdTaxDtl objProdTaxDtl : listInvProdTaxDtl) {
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
				for (Map.Entry<String, List<clsProFormaInvoiceModelDtl>> entry : hmInvCustDtl.entrySet()) {
					double qty = 0.0;
					double weight = 0.0;
					List<clsProFormaInvoiceModelDtl> listInvoiceDtlModel = hmInvCustDtl.get(entry.getKey());

					if (objBean.getStrInvCode().isEmpty()) // New Entry
					{
						String[] invDate = objHDModel.getDteInvDate().split("-");
						String dateInvoice = invDate[2] + "-" + invDate[1] + "-" + invDate[0];
						String invCode = objGlobalFunctions.funGenerateDocumentCode("frmProFormaInvoice", dateInvoice, req);
						objHDModel.setStrInvCode(invCode);
						objHDModel.setStrUserCreated(userCode);
						objHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
						objHDModel.setStrDulpicateFlag("N");
					} else // Update
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
					for (clsProFormaInvoiceModelDtl objInvItemDtl : listInvoiceDtlModel) {
						List list = objGlobalFunctionsService.funGetList("select strExciseable,strPickMRPForTaxCal,dblMRP from tblproductmaster " + " where strProdCode='" + objInvItemDtl.getStrProdCode() + "' ", "sql");
						// String excisable=list.get(0).toString();
						Object[] arrProdDtl = (Object[]) list.get(0);
						String excisable = arrProdDtl[0].toString();
						String pickMRP = arrProdDtl[1].toString();
						double dblMrp = Double.parseDouble(arrProdDtl[2].toString()) * dblCurrencyConv;
						String key = custCode + "!" + excisable;
						if (pickMRP.equals("Y")) {
							List<clsProFormaInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
							for (clsProFormaInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl) {
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

							List<clsProFormaInvoiceProdTaxDtl> listInvProdTaxDtl = hmInvProdTaxDtl.get(key);
							for (clsProFormaInvoiceProdTaxDtl objInvTaxModel : listInvProdTaxDtl) {
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
					List<clsProFormaInvoiceTaxDtlModel> listInvoiceTaxDtl = new ArrayList<clsProFormaInvoiceTaxDtlModel>();
					Map<String, clsProFormaInvoiceTaxDtlModel> hmInvTaxDtlTemp = hmInvCustTaxDtl.get(entry.getKey());
					for (Map.Entry<String, clsProFormaInvoiceTaxDtlModel> entryTaxDtl : hmInvTaxDtlTemp.entrySet()) {
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

					List<clsProFormaInvSalesOrderDtl> listInvSODtl = new ArrayList<clsProFormaInvSalesOrderDtl>();
					String[] arrSOCodes = objHDModel.getStrSOCode().split(",");
					for (int cn = 0; cn < arrSOCodes.length; cn++) {
						clsProFormaInvSalesOrderDtl objInvSODtl = new clsProFormaInvSalesOrderDtl();
						objInvSODtl.setStrSOCode(arrSOCodes[cn]);
						objInvSODtl.setDteInvDate(objHDModel.getDteInvDate());
						listInvSODtl.add(objInvSODtl);
					}
					objHDModel.setListInvSalesOrderModel(listInvSODtl);
					objHDModel.setListInvTaxDtlModel(listInvoiceTaxDtl);
					objHDModel.setListInvProdTaxDtlModel(hmInvProdTaxDtl.get(entry.getKey()));
					objHDModel.setDblDiscountAmt(objBean.getDblDiscountAmt()* dblCurrencyConv);
					
					objHDModel.setDblDiscount(objBean.getDblDiscount());
					double totalAmount=objHDModel.getDblTotalAmt()- objHDModel.getDblDiscountAmt();
					objHDModel.setDblTotalAmt(totalAmount);
					objHDModel.setDblGrandTotal(objHDModel.getDblGrandTotal()- objHDModel.getDblDiscountAmt());
					objProFormaInvoiceHdService.funAddUpdateProFormaInvoiceHd(objHDModel);
					String dcCode = "";
					if (objSetup.getStrEffectOfInvoice().equals("DC")) {
//						dcCode = funDataSetInDeliveryChallan(objHDModel, req);
					}
					arrInvCode.append(objHDModel.getStrInvCode() + ",");
					arrDcCode.append(dcCode + ",");
				}

				clsPartyMasterModel objCust = objPartyMasterService.funGetObject(objHDModel.getStrCustCode(), clientCode);
				for (clsInvoiceDtlBean objInvDtl : objBean.getListclsInvoiceModelDtl()) {
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

				if (objCompModel.getStrWebBookModule().equals("Yes")) {

					objHDModel.setDteInvDate(objHDModel.getDteInvDate().split(" ")[0]);
					if(clientCode.equals("226.001"))
					{
						//String strJVNo = funGenrateJVforInvoice(objHDModel, objHDModel.getListInvDtlModel(), objHDModel.getListInvTaxDtlModel(), clientCode, userCode, propCode, req);
						//objHDModel.setStrDktNo(strJVNo);
						//objJVGen.funGenrateJVforInvoice(objHDModel.getStrInvCode(), clientCode, userCode, propCode, req);
					}
					else
					{
						String strJVNo = "";//funGenrateJVforInvoice(objHDModel, objHDModel.getListInvDtlModel(), objHDModel.getListInvTaxDtlModel(), clientCode, userCode, propCode, req);
//***
						objHDModel.setStrDktNo(strJVNo);
					}

					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
					req.getSession().setAttribute("rptInvCode", arrInvCode);
					req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
					//req.getSession().setAttribute("rptDcCode", arrDcCode);

					return new ModelAndView("redirect:/frmProFormaInvoice.html?saddr=" + urlHits);
				} else {
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "Invoice Code : ".concat(arrInvCode.toString()));
					req.getSession().setAttribute("rptInvCode", arrInvCode);
					req.getSession().setAttribute("rptInvDate", objHDModel.getDteInvDate());
					//req.getSession().setAttribute("rptDcCode", arrDcCode);
				}
				return new ModelAndView("redirect:/frmProFormaInvoice.html?saddr=" + urlHits);

			} else {
				return new ModelAndView("frmProFormaInvoice?saddr=" + urlHits, "command", new clsInvoiceBean());
			}
		}

		// Convert bean to model function
		@SuppressWarnings("unused")
		private clsProFormaInvoiceHdModel funPrepareHdModel(clsInvoiceBean objBean, String userCode, String clientCode, HttpServletRequest req) {
			
			long lastNo = 0;
			clsInvoiceHdModel objModel;
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String startDate = req.getSession().getAttribute("startDate").toString();
			clsProFormaInvoiceHdModel INVHDModel;
			
			if (objBean.getStrInvCode().trim().length() == 0) {

				lastNo = objGlobalFunctionsService.funGetLastNo("tblproformainvoicehd", "InvCode", "intId", clientCode);
//***
				String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
				String cd = objGlobalFunctions.funGetTransactionCode("PIV", propCode, year);
				String strInvCode = cd + String.format("%06d", lastNo);

				INVHDModel = new clsProFormaInvoiceHdModel(new clsProFormaInvoiceHdModel_ID(strInvCode, clientCode));
				INVHDModel.setStrInvCode(strInvCode);
				INVHDModel.setStrUserCreated(userCode);
				INVHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				INVHDModel.setIntid(lastNo);

			}

			else {
				clsProFormaInvoiceHdModel objDCModel = (clsProFormaInvoiceHdModel) objProFormaInvoiceHdService.funGetInvoice(objBean.getStrInvCode(), clientCode);
				if (null == objDCModel) {
					lastNo = objGlobalFunctionsService.funGetLastNo("tblproformainvoicehd", "InvCode", "intId", clientCode);
//***					
					String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
					String cd = objGlobalFunctions.funGetTransactionCode("PIV", propCode, year);
					String strDCCode = cd + String.format("%06d", lastNo);
					INVHDModel = new clsProFormaInvoiceHdModel(new clsProFormaInvoiceHdModel_ID(strDCCode, clientCode));
					INVHDModel.setIntid(lastNo);
					INVHDModel.setStrUserCreated(userCode);
					// INVHDModel.setStrPropertyCode(propCode);
					INVHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				} else {
					INVHDModel = new clsProFormaInvoiceHdModel(new clsProFormaInvoiceHdModel_ID(objBean.getStrInvCode(), clientCode));
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

			if (objBean.getStrSOCode() == null) {
				INVHDModel.setStrSOCode("");
			} else {
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

			if (objBean.getDblTaxAmt() != null) {
				taxamt = objBean.getDblTaxAmt();
			}
			Double amt = objBean.getDblSubTotalAmt() + taxamt;
			INVHDModel.setDblTotalAmt(amt);
			INVHDModel.setDblTaxAmt(taxamt);
			INVHDModel.setDblDiscount(objBean.getDblDiscount());

			double dblDiscountAmt = 0.0;
			if (objBean.getDblDiscount() >= 1) {
				dblDiscountAmt = (objBean.getDblSubTotalAmt() * objBean.getDblDiscount()) / 100;

			}
			INVHDModel.setDblDiscountAmt(dblDiscountAmt);
			INVHDModel.setStrAuthorise(objGlobalFunctions.funCheckFormAuthorization("Invoice", req));

			return INVHDModel;

		}

		private void funUpdatePurchagesPricePropertywise(String prodCode, String locCode, String clientCode, double price) {
			objProductMasterService.funDeleteProdReorderLoc(prodCode, locCode, clientCode);
			clsProductReOrderLevelModel objProdReOrder = new clsProductReOrderLevelModel(new clsProductReOrderLevelModel_ID(locCode, clientCode, prodCode));
			objProdReOrder.setDblReOrderLevel(0);
			objProdReOrder.setDblReOrderQty(0);
			objProdReOrder.setDblPrice(price);
			objProductMasterService.funAddUpdateProdReOrderLvl(objProdReOrder);

		}
	
		
		
		@RequestMapping(value = "/openRptProFormaInvoiceSlip", method = RequestMethod.GET)
		public void funOpenReport(HttpServletResponse resp, HttpServletRequest req) {
			try {

				String InvCode = req.getParameter("rptInvCode").toString();
				req.getSession().removeAttribute("rptInvCode");
				String type = "pdf";

				String[] arrInvCode = InvCode.split(",");
				req.getSession().removeAttribute("rptInvCode");
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int cnt = 0; cnt < arrInvCode.length; cnt++) {
					String InvCodeSingle = arrInvCode[cnt].toString();
					JasperPrint jp = funCallReportInvoiceReport(InvCodeSingle, type, resp, req);
					jprintlist.add(jp);
				}

				if (jprintlist != null) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		

		@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
		private JasperPrint funCallReportInvoiceReport(String InvCode, String type, HttpServletResponse resp, HttpServletRequest req) {

			JasperPrint jprintlist = null;
			Connection con = objGlobalFunctions.funGetConnection(req);
			try {

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
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceSlip.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sql = "select a.strVAT,a.strCST,a.strRangeAdd,a.strDivision,a.strRegNo from tblpropertysetup a where a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "'  ";
				List listofvat = objGlobalFunctionsService.funGetList(sql, "sql");

				if (!(listofvat.isEmpty())) {
					Object[] arrObj = (Object[]) listofvat.get(0);
					strVAT = arrObj[0].toString();
					strCST = arrObj[1].toString();
					strRangeAdd = arrObj[2].toString();
					strDivision = arrObj[3].toString();
					strRegNo = arrObj[4].toString();

				}
			
				HashMap hm = new HashMap();

				String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo " + " from tblproformainvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

				List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");

				if (!list.isEmpty()) {
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

				

				String sqlDtl = "SELECT  d.strSGCode,e.strSGName,a.dblValue,sum(c.dblAssValue),sum(c.dblBillRate),sum(c.dblQty), " + " sum(c.dblBillRate*c.dblQty),sum(c.dblAssValue*c.dblQty) " + " FROM tblproformainvprodtaxdtl a,tblproformainvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " WHERE a.strInvCode='" + InvCode + "' AND a.strInvCode=c.strInvCode AND a.strProdCode=c.strProdCode  "
						+ " AND a.strDocNo='Disc' AND d.strProdCode=a.strProdCode AND e.strSGCode=d.strSGCode  " + " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "' and e.strClientCode='" + clientCode + "'  " + " group by d.strSGCode ";

				List listprodDtl = objGlobalFunctionsService.funGetList(sqlDtl, "sql");

				for (int j = 0; j < listprodDtl.size(); j++) {
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
					if (AssValueMap.containsKey(strSGName)) {
						double assValue = 0.0;
						assValue = AssValueMap.get(strSGName);
						assValue = assValue + dblAssValue;
						AssValueMap.put(strSGName, assValue);
					} else {
						AssValueMap.put(strSGName, dblAssValue);
					}
				}

				String sqlTax = " select b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt,b.strTaxCode  from tblproformainvprodtaxdtl a,tbltaxhd b,tblproformainvoicedtl c,tblproductmaster d,tblproformainvtaxdtl e " + " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode  " + " and b.strTaxIndicator=d.strTaxIndicator  and a.strInvCode='" + InvCode
						+ "' and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode ";

				List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
				if (!listprodTax.isEmpty()) {
					for (int i = 0; i < listprodTax.size(); i++) {
						Object[] objProdTax = (Object[]) listprodTax.get(i);
						double dblPercent = Double.parseDouble(objProdTax[1].toString());
						// For Total of Vat Tax

						if (vatTaxAmtMap.containsKey(Double.toString(dblPercent))) {
							double taxAmt = 0.0;
							double taxableAmt = 0.0;
							String strTax[] = vatTaxAmtMap.get(Double.toString(dblPercent)).split("!");
							taxAmt = Double.parseDouble(strTax[0]);
							taxAmt = taxAmt + Double.parseDouble(objProdTax[2].toString());
							vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt + "!" + objProdTax[0].toString() + "!" + Double.toString(dblPercent));
							taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
							taxableAmt = taxableAmt + Double.parseDouble(objProdTax[3].toString());
							vatTaxableAmtMap.put(objProdTax[4].toString(), taxableAmt);
						} else {
							vatTaxAmtMap.put(objProdTax[4].toString(), objProdTax[2].toString() + "!" + objProdTax[0].toString() + "!" + Double.toString(dblPercent));
							vatTaxableAmtMap.put(objProdTax[4].toString(), Double.parseDouble(objProdTax[3].toString()));
						}
					}
				}

				// Excise Tax
				String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName,b.dblPercent,c.strExciseChapter " + " from tblproformainvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblproformainvoicedtl e " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y'  " + " and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' "
						+ " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and c.strSGCode=d.strSGCode";

				List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
				if (!listExciseTax.isEmpty()) {
					for (int j = 0; j < listExciseTax.size(); j++) {
						clsSubGroupTaxDtl objSGTaxDtl = null;
						Object[] objExciseTax = (Object[]) listExciseTax.get(j);
						if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString())) {
							double dblTaxAmtExcise = Double.parseDouble(objExciseTax[2].toString());
							objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
							objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmtExcise);

						} else {
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
				for (Map.Entry<String, String> entry : vatTaxAmtMap.entrySet()) {
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
				for (Map.Entry<String, Double> entry : AssValueMap.entrySet()) {
					clsInvoiceProdDtlReportBean objAssVal = null;
					if (hmExciseTaxDtl.containsKey(entry.getKey())) {
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
					} else {
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

				String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblproformainvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "'";
				List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
				if (!listFreightTax.isEmpty()) {
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
				if (!listExciseDetialSql.isEmpty()) {
					for (int j = 0; j < listExciseDetialSql.size(); j++) {
						Object[] objExciseDetialSql = (Object[]) listExciseDetialSql.get(j);

						if (objExciseDetialSql[0].toString().toLowerCase().contains("cake")) {
							exciseSubGrp1 = "CAKES & PASTERIES";
							exciseChapterNo1 = objExciseDetialSql[2].toString();
							exciseDuty1 = Double.parseDouble(objExciseDetialSql[1].toString());
						} else {
							if (objExciseDetialSql[0].toString().toLowerCase().contains("chocolate")) {
								exciseSubGrp2 = "CHOCLATE";
								exciseChapterNo2 = objExciseDetialSql[2].toString();
								exciseDuty2 = Double.parseDouble(objExciseDetialSql[1].toString());
							} else {
								if (objExciseDetialSql[0].toString().toLowerCase().contains("biscuits")) {
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

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				return jprintlist;
			}
		}

		@RequestMapping(value = "/openRptProFormaInvoiceProductSlip", method = RequestMethod.GET)
		public void funOpenProductReport(HttpServletResponse resp, HttpServletRequest req) {
			try {

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
				for (int cnt = 0; cnt < arrInvCode.length; cnt++) {
					String InvCodeSingle = arrInvCode[cnt].toString();
					JasperPrint jp = funCallReportProductReport(InvCodeSingle, InvDate, type, req, resp);
					jprintlist.add(jp);
				}
				if (jprintlist != null) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
		public JasperPrint funCallReportProductReport(String InvCode, String dteInvDate, String type, HttpServletRequest req, HttpServletResponse resp) {
			JasperPrint jprintlist = null;
			Connection con = objGlobalFunctions.funGetConnection(req);
			try {
				String clientCode = req.getSession().getAttribute("clientCode").toString();
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceProductDtlList.jrxml");
				ArrayList fieldList = new ArrayList();
				HashMap reportParams = new HashMap();
				String prodsql = "select a.strProdCode,d.strProdName , d.strSGCode,e.strSGName, a.dblValue,c.dblAssValue,c.dblBillRate ,c.dblQty,(c.dblBillRate*c.dblQty),(c.dblAssValue*c.dblQty) " + " from tblproformainvprodtaxdtl a,tblproformainvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode='" + InvCode
						+ "'  and  a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strDocNo='Disc' " + " and d.strProdCode=a.strProdCode and e.strSGCode=d.strSGCode ";

				reportParams.put("InvCode", InvCode);
				reportParams.put("dteInvDate", dteInvDate);

				Map<String, Double> vatTaxAmtMap = new HashMap();
				Map<String, Double> vatTaxableAmtMap = new HashMap();
				Map<String, Double> AssValueMap = new HashMap();

				List listprodDtl = objGlobalFunctionsService.funGetList(prodsql, "sql");

				for (int j = 0; j < listprodDtl.size(); j++) {
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
					if (AssValueMap.containsKey(strSGName)) {
						double assValue = 0.0;
						assValue = AssValueMap.get(strSGName);
						assValue = assValue + dblAssValue;
						AssValueMap.put(strSGName, assValue);
					} else {
						AssValueMap.put(strSGName, dblAssValue);
					}

					String sqlTax = " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt " 
						+ " from tblproformainvprodtaxdtl a,tbltaxhd b,tblproformainvoicedtl c,tblproductmaster d,tblproformainvtaxdtl e " 
						+ " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode " 
						+ " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='"+ strProdCode + "' and a.strInvCode='" + InvCode + "' " 
						+ "and a.strInvCode=e.strInvCode and b.strTaxCode=e.strTaxCode  and b.strTaxIndicator<>'' ";

					List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
					if (!listprodTax.isEmpty()) {
						for(int cnt=0;cnt<listprodTax.size();cnt++)
						{
							Object[] objProdTax = (Object[]) listprodTax.get(cnt);
							double dblPercent = Double.parseDouble(objProdTax[2].toString());
							objInvoiceProdDtlReportBean.setTaxRate(dblPercent);
		
						// For Total of Vat Tax
							if (vatTaxAmtMap.containsKey(Double.toString(dblPercent))) {
								double taxAmt = 0.0;
								double taxableAmt = 0.0;
								taxAmt = (double) vatTaxAmtMap.get(Double.toString(dblPercent));
								taxAmt = taxAmt + Double.parseDouble(objProdTax[3].toString());
								vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt);
								taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
								taxableAmt = taxableAmt + Double.parseDouble(objProdTax[4].toString());
								vatTaxableAmtMap.put(Double.toString(dblPercent), taxableAmt);
							} else {
								vatTaxAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[3].toString()));
								vatTaxableAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[4].toString()));
							}
						}
					}

					fieldList.add(objInvoiceProdDtlReportBean);
				}

				Map<String, clsSubGroupTaxDtl> hmExciseTaxDtl = new HashMap<String, clsSubGroupTaxDtl>();

				// Excise Tax
				String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName " 
					+ " from tblproformainvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblproformainvoicedtl e " 
					+ " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' "
					+ " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and c.strSGCode=d.strSGCode";

				List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
				if (!listExciseTax.isEmpty()) {
					for (int j = 0; j < listExciseTax.size(); j++) {
						clsSubGroupTaxDtl objSGTaxDtl = new clsSubGroupTaxDtl();
						Object[] objExciseTax = (Object[]) listExciseTax.get(j);
						if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString())) {
							objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
							double dblTaxAmt = Double.parseDouble(objExciseTax[2].toString());
							objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmt);

						} else {
							objSGTaxDtl.setTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
						}
						hmExciseTaxDtl.put(objExciseTax[3].toString(), objSGTaxDtl);
					}
				}

				// Tax pass In report through parameter list
				ArrayList<clsInvoiceProdDtlReportBean> taxList = new ArrayList();
				double totalVatTax = 0.0;
				for (Map.Entry<String, Double> entry : vatTaxAmtMap.entrySet()) {
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
				for (Map.Entry<String, Double> entry : AssValueMap.entrySet()) {
					clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
					if (hmExciseTaxDtl.containsKey(entry.getKey())) {
						obj.setDblexciseDuty(hmExciseTaxDtl.get(entry.getKey()).getTaxAmt());
						obj.setDblGrpAssValue(entry.getValue());
						obj.setStrSGName(entry.getKey());
						assValueList.add(obj);
					} else {
						obj.setDblexciseDuty(0);
						obj.setDblGrpAssValue(entry.getValue());
						obj.setStrSGName(entry.getKey());
						assValueList.add(obj);
					}
				}
				reportParams.put("assValueList", assValueList);
				// Add Freight Tax
				double dblfeightTaxAmt = 0.0;

				String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblproformainvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "'";
				List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
				if (!listFreightTax.isEmpty()) {
					Object[] objfrightTax = (Object[]) listFreightTax.get(0);
					dblfeightTaxAmt = Double.parseDouble(objfrightTax[2].toString());
				}
				reportParams.put("dblfeightTaxAmt", dblfeightTaxAmt);

				JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(fieldList);
				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				jprintlist = JasperFillManager.fillReport(jr, reportParams, beanCollectionDataSource);

				// }

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				return jprintlist;
			}
		}

		@SuppressWarnings("unused")
		@RequestMapping(value = "/rptProFormaTradingInvoiceSlip", method = RequestMethod.GET)
		public void funOpenTradingInvoiceReport(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req) {

			try {

				String InvCode = req.getParameter("rptInvCode").toString();
				String[] arrInvCode = InvCode.split(",");
				String InvDate = req.getParameter("rptInvDate").toString();
				req.getSession().removeAttribute("rptInvCode");
				req.getSession().removeAttribute("rptInvDate");
				String type = "pdf";

				SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

				try {

					InvDate = myFormat.format(fromUser.parse(InvDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int cnt = 0; cnt < arrInvCode.length; cnt++) {
					String InvCodeSingle = arrInvCode[cnt].toString();
					JasperPrint jp = funCallTradingInvoiceReport(InvCodeSingle, InvDate, resp, req);
					jprintlist.add(jp);
				}

				if (jprintlist != null) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@SuppressWarnings({ "unused", "rawtypes", "unchecked", "finally" })
		public JasperPrint funCallTradingInvoiceReport(String InvCode, String InvDate, HttpServletResponse resp, HttpServletRequest req) {

			JasperPrint jprintlist = null;
			Connection con = objGlobalFunctions.funGetConnection(req);
			try {

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
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}

				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptTradingTaxInvoice.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				ArrayList fieldList = new ArrayList();
				HashMap reportParams = new HashMap();
				String prodsql = "select a.strProdCode,d.strProdName , d.strSGCode,e.strSGName, a.dblValue,c.dblAssValue,c.dblBillRate ,c.dblQty,(c.dblBillRate*c.dblQty),(c.dblAssValue*c.dblQty) ,d.strUOM " + " from tblproformainvprodtaxdtl a,tblproformainvoicedtl c,tblproductmaster d,tblsubgroupmaster e " + " where a.strInvCode='" + InvCode
						+ "'  and  a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strDocNo='Disc' and d.strProdType='Trading' " + " and d.strProdCode=a.strProdCode and e.strSGCode=d.strSGCode ";

				reportParams.put("InvCode", InvCode);
				reportParams.put("dteInvDate", dteInvDate);

				Map<String, Double> vatTaxAmtMap = new HashMap();
				Map<String, Double> vatTaxableAmtMap = new HashMap();
				Map<String, Double> AssValueMap = new HashMap();
				Map<String, Double> exciseTaxAmt = new HashMap();
				List productList = new ArrayList();
				List listprodDtl = objGlobalFunctionsService.funGetList(prodsql, "sql");

				for (int j = 0; j < listprodDtl.size(); j++) {
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
					if (AssValueMap.containsKey(strSGName)) {
						double assValue = 0.0;
						assValue = AssValueMap.get(strSGName);
						assValue = assValue + dblAssValue;
						AssValueMap.put(strSGName, assValue);
					} else {
						AssValueMap.put(strSGName, dblAssValue);
					}

					String sqlTax = " select a.strProdCode,b.strTaxDesc,b.dblPercent,e.dblTaxAmt,e.dblTaxableAmt " + " from tblproformainvprodtaxdtl a,tbltaxhd b,tblproformainvoicedtl c,tblproductmaster d,tblproformainvtaxdtl e " + " where a.strDocNo=b.strTaxCode and a.strInvCode=c.strInvCode and a.strProdCode=c.strProdCode and a.strProdCode=d.strProdCode " + " and b.strTaxIndicator=d.strTaxIndicator and a.strProdCode='"
							+ strProdCode + "' and a.strInvCode='" + InvCode + "' " + "and a.strInvCode=e.strInvCode   and b.strTaxCode=e.strTaxCode and b.strTaxIndicator<>''";

					List listprodTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
					if (!listprodTax.isEmpty()) {
						Object[] objProdTax = (Object[]) listprodTax.get(0);
						double dblPercent = Double.parseDouble(objProdTax[2].toString());
						objInvoiceProdDtlReportBean.setTaxRate(dblPercent);

						// For Total of Vat Tax

						if (vatTaxAmtMap.containsKey(Double.toString(dblPercent))) {
							double taxAmt = 0.0;
							double taxableAmt = 0.0;
							taxAmt = (double) vatTaxAmtMap.get(Double.toString(dblPercent));
							taxAmt = taxAmt + Double.parseDouble(objProdTax[3].toString());
							vatTaxAmtMap.put(Double.toString(dblPercent), taxAmt);
							taxableAmt = (double) vatTaxableAmtMap.get(Double.toString(dblPercent));
							taxableAmt = taxableAmt + Double.parseDouble(objProdTax[4].toString());
							vatTaxableAmtMap.put(Double.toString(dblPercent), taxableAmt);
						} else {
							vatTaxAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[3].toString()));
							vatTaxableAmtMap.put(Double.toString(dblPercent), Double.parseDouble(objProdTax[4].toString()));
						}
					}

					fieldList.add(objInvoiceProdDtlReportBean);
				}

				Map<String, clsSubGroupTaxDtl> hmExciseTaxDtl = new HashMap<String, clsSubGroupTaxDtl>();
				// Excise Tax
				String sqlExciseTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt,c.strSGName " + " from tblproformainvtaxdtl a,tbltaxhd b,tblsubgroupmaster c,tblproductmaster d,tblproformainvoicedtl e " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y'  " + " and b.strTaxOnSubGroup='Y'and a.strInvCode='" + InvCode + "' "
						+ " and a.strInvCode=e.strInvCode and  e.strProdCode=d.strProdCode and d.strProdType='Trading' and c.strSGCode=d.strSGCode";

				List listExciseTax = objGlobalFunctionsService.funGetList(sqlExciseTax, "sql");
				if (!listExciseTax.isEmpty()) {
					

					for (int j = 0; j < listExciseTax.size(); j++) {
						clsSubGroupTaxDtl objSGTaxDtl = new clsSubGroupTaxDtl();
						Object[] objExciseTax = (Object[]) listExciseTax.get(j);
						if (hmExciseTaxDtl.containsKey(objExciseTax[3].toString())) {
							objSGTaxDtl = hmExciseTaxDtl.get(objExciseTax[3].toString());
							double dblTaxAmt = Double.parseDouble(objExciseTax[2].toString());
							objSGTaxDtl.setTaxAmt(objSGTaxDtl.getTaxAmt() + dblTaxAmt);

						} else {
							objSGTaxDtl.setTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
						}
						hmExciseTaxDtl.put(objExciseTax[3].toString(), objSGTaxDtl);
					}
				}

				// Tax pass In report through parameter list
				ArrayList<clsInvoiceProdDtlReportBean> taxList = new ArrayList();
				double totalVatTax = 0.0;
				for (Map.Entry<String, Double> entry : vatTaxAmtMap.entrySet()) {
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
				for (Map.Entry<String, Double> entry : AssValueMap.entrySet()) {
					
					
					
					

					clsInvoiceProdDtlReportBean obj = new clsInvoiceProdDtlReportBean();
					if (hmExciseTaxDtl.containsKey(entry.getKey())) {
						obj.setDblexciseDuty(hmExciseTaxDtl.get(entry.getKey()).getTaxAmt());
						obj.setDblGrpAssValue(entry.getValue());
						obj.setStrSGName(entry.getKey());
						assValueList.add(obj);
					} else {
						obj.setDblexciseDuty(0);
						obj.setDblGrpAssValue(entry.getValue());
						obj.setStrSGName(entry.getKey());
						assValueList.add(obj);
					}
				}
				reportParams.put("assValueList", assValueList);
				// Add Freight Tax
				double dblfeightTaxAmt = 0.0;

				String SqlFreightTax = "select b.strTaxDesc,a.dblTaxableAmt,a.dblTaxAmt from tblproformainvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and b.strTaxOnTax='Y' and b.strTaxOnST='Y' " + " and b.strTaxOnSubGroup='N'and a.strInvCode='" + InvCode + "' ";
				List listFreightTax = objGlobalFunctionsService.funGetList(SqlFreightTax, "sql");
				if (!listFreightTax.isEmpty()) {
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

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				return jprintlist;
			}

		}
		
		@SuppressWarnings("unused")
		@RequestMapping(value = "/rptProFormaInvoiceSlipFromat2", method = RequestMethod.GET)
		public void funOpenInvoiceFromat2Report(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req) {

			try {
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
				

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				List<JasperPrint> jprintlist1 = new ArrayList<JasperPrint>();
				List<JasperPrint> jprintlist2 = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int cnt = 0; cnt < arrInvCode.length; cnt++) {
					String InvCodeSingle = arrInvCode[cnt].toString();
					invcodes = invcodes + InvCodeSingle + "#";
					String sql = "select strInvCode,dteInvDate from tblproformainvoicehd where strExciseable='Y' and strInvCode='" + InvCodeSingle + "' ";
					List listInvCode = objGlobalFunctionsService.funGetList(sql, "sql");
					if (listInvCode.size() > 0) {
						JasperPrint jp = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Original For Buyer");
						jprintlist.add(jp);

						JasperPrint jp1 = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Duplicate For Transporter");
						jprintlist.add(jp1);

						JasperPrint jp2 = funInvoiceSlipFormat2(InvCodeSingle, type, req, resp, "Triplicate To Customer");
						jprintlist.add(jp2);

					}
				}
				if (arrInvCode.length == 1) {
					InvCode = InvCode.replaceAll(",", "");
					String sql = "select strInvCode,Date(dteInvDate) from tblproformainvoicehd where strExciseable='Y' and strInvCode='" + InvCode + "' ";
					List listInvRow = objGlobalFunctionsService.funGetList(sql, "sql");
					if (listInvRow.size() > 0) {
						Object[] obj = (Object[]) listInvRow.get(0);

						dteInvForReportname = obj[1].toString();
						fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
						fileReportname.replaceAll(" ", "");
					}
				} else {
					fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
					fileReportname.replaceAll(" ", "");
				}

				if (jprintlist.size() > 0) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@SuppressWarnings({ "unchecked", "finally", "rawtypes" })
		public JasperPrint funInvoiceSlipFormat2(String InvCode, String type, HttpServletRequest req, HttpServletResponse resp, String invoiceType) {
			JasperPrint jprintlist = null;
			Connection con = objGlobalFunctions.funGetConnection(req);

			try {
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
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceSlipFormat2.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo " + ",a.dblTotalAmt,a.dblGrandTotal " + " from tblproformainvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='"
						+ clientCode + "' and a.strClientCode=b.strClientCode ";
				List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
				if (!list.isEmpty()) {
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
				if (!listTransporter.isEmpty()) {
					Object[] arrObj = (Object[]) listTransporter.get(0);
					strTransName = arrObj[2].toString();

				}
				ArrayList fieldList = new ArrayList();
				HashMap hm = new HashMap();

				String sqlFetchDc = "select strDCCode,DATE_FORMAT(date(dteDCDate),'%d-%m-%Y') from tbldeliverychallanhd where strSoCode='" + InvCode + "' and strClientCode='" + clientCode + "' ";
				List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

				if (!listFetchDc.isEmpty()) {
					Object[] objDc = (Object[]) listFetchDc.get(0);

					strDCCode = objDc[0].toString();
					dteDCDate = objDc[1].toString();

				}

				String prodSql = "select b.strProdCode,d.strProdName,e.strExciseChapter,sum(b.dblQty),(b.dblAssValue/b.dblQty) as Assble_Rate,(b.dblQty*d.dblMRP) as MRP_Value, " + " if(d.strPickMRPForTaxCal='Y',sum(((b.dblQty*d.dblMRP)*(f.dblAbatement/100))),sum(b.dblAssValue)) as Assable_Value  , " + " sum(c.dblValue) as Excise_Duty,sum((b.dblAssValue)) as Invoice_Value,d.strPickMRPForTaxCal   "
						+ " from tblproformainvoicehd a left outer join  tblproformainvoicedtl b on a.strInvCode=b.strInvCode  " + " left outer join  tblproformainvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode " + " left outer join tbltaxhd f on c.strDocNo=f.strTaxCode " + " left outer join tblproductmaster d on b.strProdCode=d.strProdCode "
						+ " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and f.strExcisable='Y' and d.strExciseable='Y' and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc' " + " group by b.strProdCode ";

				
				hm.put("InvCode", InvCode);
				hm.put("dteInvDate", dteInvDate);

				List listprodDtl = objGlobalFunctionsService.funGetList(prodSql, "sql");
				double assVSum = 0.00;
				for (int j = 0; j < listprodDtl.size(); j++) {
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
					if (objProdDtl[9].toString().equalsIgnoreCase("Y")) {
						objInvoiceProdDtlReportBean.setDblMRP(Double.parseDouble(objProdDtl[5].toString()));
					} else {
						objInvoiceProdDtlReportBean.setDblMRP(0.0);
					}

					

					fieldList.add(objInvoiceProdDtlReportBean);
				}

				
				System.out.println("assVSum=" + assVSum);
				excisePerDesc = "";
				exciseTaxAmtTotal = 0;
				double totalVatTax = 0;
				List<clsInvoiceProdDtlReportBean> listVatTax = new ArrayList();
				List<clsInvoiceProdDtlReportBean> listNillVatTax = new ArrayList();
				List<clsInvoiceProdDtlReportBean> listExsiceDutyTax = new ArrayList();

				String exciseSql = "select b.strTaxDesc,sum(a.dblTaxableAmt),sum(a.dblTaxAmt),b.strExcisable,b.dblPercent,b.strTaxCalculation " + "from tblproformainvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and a.strInvCode='" + InvCode + "'  and a.strClientCode='" + clientCode + "' " + "group by a.strTaxCode ; ";
				List listExciseTax = objGlobalFunctionsService.funGetList(exciseSql, "sql");
				if (!listExciseTax.isEmpty()) {
					for (int cn = 0; cn < listExciseTax.size(); cn++) {
						Object[] objExciseTax = (Object[]) listExciseTax.get(cn);

						if (objExciseTax[3].toString().equals("Y")) {
							clsInvoiceProdDtlReportBean objExDutyTax = new clsInvoiceProdDtlReportBean();
							excisePerDesc = objExciseTax[0].toString();
							exciseTaxAmtTotal += Double.parseDouble(objExciseTax[2].toString());
							objExDutyTax.setDblexciseDuty(Double.parseDouble(objExciseTax[2].toString()));
							objExDutyTax.setStrExciseDutyPerDes(excisePerDesc);
							listExsiceDutyTax.add(objExDutyTax);
						} else {
							if (Double.parseDouble(objExciseTax[4].toString()) > 0) {
								totalVatTax = Double.parseDouble(objExciseTax[2].toString());
								clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
								double taxableAmt = Double.parseDouble(objExciseTax[1].toString());
								if (objExciseTax[5].toString().equalsIgnoreCase("Backword")) {
									taxableAmt = taxableAmt - Double.parseDouble(objExciseTax[2].toString());
								}
								objTax.setDblTaxableAmt(taxableAmt);
								objTax.setDblTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
								objTax.setDblVatTaxPer(Double.parseDouble(objExciseTax[4].toString()));
								listVatTax.add(objTax);
							} else {
								clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
								objTax.setDblTaxableAmt(Double.parseDouble(objExciseTax[1].toString()));
								objTax.setDblTaxAmt(Double.parseDouble(objExciseTax[2].toString()));
								if (Double.parseDouble(objExciseTax[4].toString()) == 0.0) {
									objTax.setStrVatPerWord("NIL");
								} else {
									objTax.setStrVatPerWord(objExciseTax[4].toString());
								}

								listNillVatTax.add(objTax);
							}
						}
					}

					if (listNillVatTax.isEmpty()) {
						clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
						objTax.setDblTaxableAmt(0.00);
						objTax.setDblTaxAmt(0.00);
						objTax.setDblVatTaxPer(0.0);
						objTax.setStrVatPerWord("NIL");
						listNillVatTax.add(objTax);
					}
				}

				String taxDtlSql = "select strTaxDesc,0.00,0.00,strExcisable,dblPercent " + " from tbltaxhd " + " where strTaxCode not in (select strtaxCode from tblproformainvtaxdtl where strInvCode='" + InvCode + "') " + " and (dblPercent=6.00 or dblPercent=13.50) and strTaxOnSP='Sales' and strTaxDesc not like '%Excise%' ";
				List listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
				for (int cn = 0; cn < listTax.size(); cn++) {
					Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
					if (arrObjTaxDtl[3].toString().equals("N")) {
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
				if (strDulpicateFlag.equalsIgnoreCase("N")) {
					strDulpicateFlag = "Orignal Copy";
					String sqlUpdateduplicateflag = "update tblproformainvoicehd set strDulpicateFlag='Y'";
					objGlobalFunctionsService.funUpdateAllModule(sqlUpdateduplicateflag, "sql");
				} else {
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
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return jprintlist;
			}
		}

		@SuppressWarnings("unused")
		@RequestMapping(value = "/rptProFormaInvoiceSlipNonExcisableFromat2", method = RequestMethod.GET)
		public void funOpenInvoiceNonExcisableFromat2Report(@ModelAttribute("command") clsInvoiceBean objBean, HttpServletResponse resp, HttpServletRequest req) {
			try {

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

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				for (int cnt = 0; cnt < arrInvCode.length; cnt++) {
					String InvCodeSingle = arrInvCode[cnt].toString();
					invcodes = invcodes + InvCodeSingle + "#";
					String sql = "select strInvCode from tblproformainvoicehd where strExciseable='N' and strInvCode='" + InvCodeSingle + "' ";
					List listInvCode = objGlobalFunctionsService.funGetList(sql, "sql");
					if (listInvCode.size() > 0) {
						
						JasperPrint jp = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Original For Buyer");
						jprintlist.add(jp);

						JasperPrint jp1 = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Duplicate For Transporter");
						jprintlist.add(jp1);

						JasperPrint jp2 = funInvoiceSlipNonExcisableFormat2(InvCodeSingle, type, req, resp, "Triplicate To Customer");
						jprintlist.add(jp2);
					}

				}
				if (arrInvCode.length == 1) {
					InvCode = InvCode.replaceAll(",", "");
					String sql = "select strInvCode,Date(dteInvDate) from tblproformainvoicehd where strExciseable='N' and strInvCode='" + InvCode + "' ";
					List listInvRow = objGlobalFunctionsService.funGetList(sql, "sql");
					if (listInvRow.size() > 0) {
						Object[] obj = (Object[]) listInvRow.get(0);

						dteInvForReportname = obj[1].toString();
						fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
						fileReportname.replaceAll(" ", "");
					}

				} else {
					fileReportname = fileReportname + invcodes + "_" + dteInvForReportname + "_" + userCode;
					fileReportname.replaceAll(" ", "");

				}

				if (jprintlist.size() > 0) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		public JasperPrint funInvoiceSlipNonExcisableFormat2(String InvCode, String type, HttpServletRequest req, HttpServletResponse resp, String invoiceType) {
			JasperPrint jprintlist = null;
			Connection con = objGlobalFunctions.funGetConnection(req);
			try {
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
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptInvoiceSlipFormat2.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

				String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity, " + " b.strSPin,b.strSState,b.strSCountry ,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo " + ",a.dblTotalAmt,a.dblGrandTotal " + " from tblproformainvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='"
						+ clientCode + "' and a.strClientCode=b.strClientCode ";

				List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");

				if (!list.isEmpty()) {
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
				if (!listTransporter.isEmpty()) {
					Object[] arrObj = (Object[]) listTransporter.get(0);
					strTransName = arrObj[2].toString();

				}
				ArrayList fieldList = new ArrayList();

				String sqlFetchDc = "select strDCCode,DATE_FORMAT(date(dteDCDate),'%d-%m-%Y') from tbldeliverychallanhd where strSoCode='" + InvCode + "' and strClientCode='" + clientCode + "' ";
				List listFetchDc = objGlobalFunctionsService.funGetList(sqlFetchDc, "sql");

				if (!listFetchDc.isEmpty()) {
					Object[] objDc = (Object[]) listFetchDc.get(0);

					strDCCode = objDc[0].toString();
					dteDCDate = objDc[1].toString();
				}

				@SuppressWarnings("rawtypes")
				HashMap hm = new HashMap();

			

				String prodSql = "select b.strProdCode,d.strProdName,e.strExciseChapter,sum(b.dblQty),(b.dblAssValue/b.dblQty) as Assble_Rate,sum((b.dblQty*d.dblMRP)) as MRP_Value" + ",sum((b.dblAssValue)) as Assable_Value" + ",0 as Excise_Duty,sum(b.dblAssValue) as Invoice_Value,d.strPickMRPForTaxCal  " + " from tblproformainvoicehd a left outer join  tblproformainvoicedtl b on a.strInvCode=b.strInvCode  "
						+ " left outer join  tblproformainvprodtaxdtl c on b.strInvCode=c.strInvCode and b.strProdCode=c.strProdCode and c.strDocNo!='Margin' and c.strDocNo!='Disc' " + " left outer join tbltaxhd f on c.strDocNo=f.strTaxCode and f.strExcisable='N'  " + " left outer join tblproductmaster d on b.strProdCode=d.strProdCode and d.strExciseable='N' "
						+ " left outer join tblsubgroupmaster e on d.strSGCode=e.strSGCode " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " group by b.strProdCode";

				hm.put("InvCode", InvCode);
				hm.put("dteInvDate", dteInvDate);

				Map<String, Double> vatTaxAmtMap = new HashMap();
				Map<String, Double> vatTaxableAmtMap = new HashMap();
				List listprodDtl = objGlobalFunctionsService.funGetList(prodSql, "sql");

				for (int j = 0; j < listprodDtl.size(); j++) {
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

					if (objProdDtl[9].toString().equalsIgnoreCase("Y")) {
						objInvoiceProdDtlReportBean.setDblMRP(Double.parseDouble(objProdDtl[5].toString()));
					} else {
						objInvoiceProdDtlReportBean.setDblMRP(0.0);

					}

					

					fieldList.add(objInvoiceProdDtlReportBean);
				}

				
				excisePerDesc = "0";
				exciseTaxAmtTotal = 0.0;
				double totalVatTax = 0.0;
				List<clsInvoiceProdDtlReportBean> listVatTax = new ArrayList();
				List<clsInvoiceProdDtlReportBean> listNillVatTax = new ArrayList();
				List<clsInvoiceProdDtlReportBean> listExsiceDutyTax = new ArrayList();

				String taxDtlSql = "select b.strTaxDesc,sum(a.dblTaxableAmt),sum(a.dblTaxAmt),b.strExcisable,b.dblPercent,b.strTaxCalculation " + "from tblproformainvtaxdtl a,tbltaxhd b " + " where a.strTaxCode=b.strTaxCode and a.strInvCode='" + InvCode + "'  and a.strClientCode='" + clientCode + "' " + "group by a.strTaxCode ; ";
				List listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
				if (!listTax.isEmpty()) {
					for (int cn = 0; cn < listTax.size(); cn++) {
						Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
						if (arrObjTaxDtl[3].toString().equals("N")) {
							if (Double.parseDouble(arrObjTaxDtl[4].toString()) > 0) {
								totalVatTax = Double.parseDouble(arrObjTaxDtl[2].toString());
								clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
								double taxableAmt = Double.parseDouble(arrObjTaxDtl[1].toString());
								if (arrObjTaxDtl[5].toString().equalsIgnoreCase("Backword")) {
									taxableAmt = taxableAmt - Double.parseDouble(arrObjTaxDtl[2].toString());
								}

								objTax.setDblTaxableAmt(taxableAmt);
								objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
								objTax.setDblVatTaxPer(Double.parseDouble(arrObjTaxDtl[4].toString()));
								listVatTax.add(objTax);
							} else {
								// totalVatTax=Double.parseDouble(arrObjTaxDtl[2].toString());
								clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
								objTax.setDblTaxableAmt(Double.parseDouble(arrObjTaxDtl[1].toString()));
								objTax.setDblTaxAmt(Double.parseDouble(arrObjTaxDtl[2].toString()));
								if (Double.parseDouble(arrObjTaxDtl[4].toString()) == 0.0) {
									objTax.setStrVatPerWord("NIL");
								} else {
									objTax.setStrVatPerWord(arrObjTaxDtl[4].toString());
								}

								listNillVatTax.add(objTax);
							}
						}
					}

					if (listNillVatTax.isEmpty()) {
						clsInvoiceProdDtlReportBean objTax = new clsInvoiceProdDtlReportBean();
						objTax.setDblTaxableAmt(0.00);
						objTax.setDblTaxAmt(0.00);
						objTax.setDblVatTaxPer(0.0);
						objTax.setStrVatPerWord("NIL");
						listNillVatTax.add(objTax);
					}
				}

				taxDtlSql = "select strTaxDesc,0.00,0.00,strExcisable,dblPercent " + " from tbltaxhd " + " where strTaxCode not in (select strtaxCode from tblproformainvtaxdtl where strInvCode='" + InvCode + "') " + " and (dblPercent=5.50 or dblPercent=12.50) and strTaxOnSP='Sales' and strTaxDesc not like '%Excise%' ";
				listTax = objGlobalFunctionsService.funGetList(taxDtlSql, "sql");
				for (int cn = 0; cn < listTax.size(); cn++) {
					Object[] arrObjTaxDtl = (Object[]) listTax.get(cn);
					if (arrObjTaxDtl[3].toString().equals("N")) {
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
				if (strDulpicateFlag.equalsIgnoreCase("N")) {
					strDulpicateFlag = "Orignal Copy";
					String sqlUpdateduplicateflag = "update tblproformainvoicehd set strDulpicateFlag='Y'";
					objGlobalFunctionsService.funUpdateAllModule(sqlUpdateduplicateflag, "sql");
				} else {
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

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				return jprintlist;
			}
		}



		@SuppressWarnings("unused")
		@RequestMapping(value = "/rptProFormaInvoiceSlipFormat5Report", method = RequestMethod.GET)
		public void funOpenInvoiceFormat5Report(@RequestParam("rptInvCode") String invCode, @RequestParam("rptInvDate") String invDate, HttpServletResponse resp, HttpServletRequest req) {
			try {
				String InvCode = invCode;
				String[] arrInv = InvCode.split(",");
				InvCode = arrInv[0];
				String type = "pdf";
				JasperPrint jp = funCallReportInvoiceFormat5Report(InvCode, invDate, type, resp, req);

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (jprintlist != null) {
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

			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes", "unused", "finally" })
		private JasperPrint funCallReportInvoiceFormat5Report(String InvCode, String invDate, String type, HttpServletResponse resp, HttpServletRequest req) {

			JasperPrint jp = null;
			Connection con = objGlobalFunctions.funGetConnection(req);

			try {
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
				if (objSetup == null) {
					objSetup = new clsPropertySetupModel();
				}
				String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceSlipFormat5Report.jrxml");
				String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");
				HashMap hm = new HashMap();
				ArrayList fieldList = new ArrayList();
				String strAuthLevel1="",strAuthLevel2="",strUserCreated="";
				
				hm.put("InvCode", InvCode);
				hm.put("dteInvDate", dteInvDate);

				String sqlHd = " select a.strInvCode,a.dteInvDate,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSPin,b.strSState " //7
						+ ",b.strSCountry,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.strDulpicateFlag,b.strECCNo,a.dblTotalAmt,a.dblGrandTotal " //15
						+ ",b.strVAT,b.strMAdd1,b.strMAdd2,b.strMCity,b.strMPin,b.strMState,b.strMCountry,a.strPONo,a.strVehNo,a.dblDiscountAmt "//25
						+ ",a.strAuthLevel1,a.strAuthLevel2 ,a.strUserCreated "
						+ " from tblproformainvoicehd a,tblpartymaster b where a.strInvCode='" + InvCode + "'  " + " and a.strCustCode=b.strPCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

				List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
				if (!list.isEmpty()) {
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
					strAuthLevel1=arrObj[26].toString();
					strAuthLevel2=arrObj[27].toString();
					strUserCreated=arrObj[28].toString();
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

				String sqlDetailQ = "select  b.dblQty,c.strProdName,c.strUOM,b.dblPrice,b.dblQty *b.dblPrice as amount ,b.strProdCode"
					+ ",a.dblDiscountAmt,a.dblGrandTotal "
					+ " from tblproformainvoicehd a left outer join tblproformainvoicedtl b  on a.strInvCode=b.strInvCode " 
					+ " left outer join tblproductmaster c on  b.strProdCode=c.strProdCode where  a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";
				list = objGlobalFunctionsService.funGetList(sqlDetailQ, "sql");
				if (!list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						Object[] arrObj = (Object[]) list.get(i);
						clsInvoiceBean objBean = new clsInvoiceBean();
						objBean.setDblQty(Double.parseDouble(arrObj[0].toString()));
						objBean.setStrProdName(arrObj[1].toString());
						objBean.setStrUOM(arrObj[2].toString());
						objBean.setDblUnitPrice(Double.parseDouble(arrObj[3].toString()));
						objBean.setDblSubTotalAmt(Double.parseDouble(arrObj[4].toString()));
						objBean.setStrProdCode(arrObj[5].toString());
						objBean.setDblDiscountAmt(Double.parseDouble(arrObj[6].toString()));
						objBean.setDblTotalAmt(Double.parseDouble(arrObj[3].toString())*Double.parseDouble(arrObj[0].toString()));
						objBean.setDblGrandTotal(Double.parseDouble(arrObj[7].toString()));
						double taxAmt = 0.00;
						String sqlTax = "SELECT sum(b.dblValue) FROM tblproformainvtaxdtl a,tblproformainvprodtaxdtl b " 
							+ " WHERE a.strTaxCode=b.strDocNo and a.strInvCode=b.strInvCode and a.strInvCode='" + InvCode + "' "
							+ " AND b.strProdCode='" + arrObj[5].toString() + "' and a.strClientCode='" + clientCode + "' "
							+ " group by b.strProdCode ";
						List listTax = objGlobalFunctionsService.funGetList(sqlTax, "sql");
						if (listTax != null) {
							for (int j = 0; j < listTax.size(); j++) {
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

			} catch (Exception e) {
				e.printStackTrace();
			}

			return jp;

		}

		@RequestMapping(value = "/openRptProFormaInvoiceRetailNonGSTReport", method = RequestMethod.GET)
		public void funOpenInvoiceRetailNonGSTReport(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req) {

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
			double currValue = Double.parseDouble(req.getSession().getAttribute("currValue").toString());

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceTaxes.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/Sanguine_Logo_Icontest.jpg");

			String challanDate = "";
			String PONO = "";
			String InvDate = "";
			String CustName = "";
			String dcCode = "";

			String sqlProdDtl = " select  c.strProdName,c.strProdNameMarathi,b.dblQty,"
					// + "c.dblCostRM,"
					+ " IFNULL(b.dblPrice,0.00),c.dblMRP, IFNULL(b.dblPrice,0.00) AS dblPrice, a.dteInvDate, " + " IFNULL(d.strPName,''),ifnull(e.strDCCode,''),ifnull(e.dteDCDate,''),ifnull(e.strPONo,''), " + " b.strProdCode,f.strExciseChapter,g.dblValue as discAmt,IFNULL(d.strBAdd1,''),IFNULL(d.strBAdd2,''), "
					+ " IFNULL(d.strBState,''),IFNULL(d.strBPin,'') ,IFNULL(d.strSAdd1,''),IFNULL(d.strSAdd2,''), " + " IFNULL(d.strSState,''),IFNULL(d.strSPin,'') ,ifNull(strCST,''),c.strBarCode,b.strRemarks,ifnull(c.strUOM,0) from tblproformainvoicehd a left outer join tblproformainvoicedtl b on a.strInvCode=b.strInvCode   "
					+ " left outer join tblproductmaster c  on b.strProdCode=c.strProdCode left outer join tblpartymaster d on a.strCustCode=d.strPCode " + " left outer join tbldeliverychallanhd e on a.strSOCode=e.strDCCode " + " left outer join tblsubgroupmaster f on f.strSGCode=c.strSGCode " + " left outer join tblproformainvprodtaxdtl  g on a.strInvCode=g.strInvCode and a.strCustCode=g.strCustCode  "
					+ " and b.strProdCode=g.strProdCode and g.strDocNo='Disc'   " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";

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
			double nonGStAmt = 0.0;
			if (listProdDtl.size() > 0) {
				for (int i = 0; i < listProdDtl.size(); i++) {
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
					objDtlBean.setDblDisAmt(Double.parseDouble(obj[13].toString()) * Double.parseDouble(obj[2].toString()) / currValue);
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

					
					String sqlQuery = " select b.strTaxCode,b.dblPercent,a.dblValue,b.strShortName,b.strTaxDesc  " + " from tblproformainvprodtaxdtl a,tbltaxhd b " + " where a.strDocNo=b.strTaxCode and a.strInvCode='" + InvCode + "' " + " and  a.strProdCode='" + obj[11].toString() + "' and a.strClientCode='" + clientCode + "'  ";

					List listProdGST = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

					if (listProdGST.size() > 0) {
						for (int j = 0; j < listProdGST.size(); j++) {
							double cGStAmt = 0.0;
							double sGStAmt = 0.0;

							Object[] objGST = (Object[]) listProdGST.get(j);
							if (objGST[3].toString().equalsIgnoreCase("CGST")) {
								objDtlBean.setDblCGSTPer(Double.parseDouble(objGST[1].toString()));
								objDtlBean.setDblCGSTAmt(Double.parseDouble(objGST[2].toString()) / currValue);
								totalInvoiceValue = (totalInvoiceValue + (Double.parseDouble(objGST[2].toString())));
							} else if (objGST[3].toString().equalsIgnoreCase("SGST")) {
								objDtlBean.setDblSGSTPer(Double.parseDouble(objGST[1].toString()));
								objDtlBean.setDblSGSTAmt(Double.parseDouble(objGST[2].toString()) / currValue);
								totalInvoiceValue = (totalInvoiceValue + (Double.parseDouble(objGST[2].toString())));
							} else {
								objDtlBean.setDblNonGSTTaxPer(Double.parseDouble(objGST[1].toString()));
								nonGStAmt = nonGStAmt + (Double.parseDouble(objGST[2].toString()) / currValue);
								objDtlBean.setDblNonGSTTaxAmt(nonGStAmt);
								objDtlBean.setStrTaxDesc(objGST[4].toString());
								totalInvoiceValue = (totalInvoiceValue + (Double.parseDouble(objGST[2].toString())));
							}
						}
					}

					dataList.add(objDtlBean);

				}
			}
			totalInvoiceValue = totalInvoiceValue / currValue;
			

			try {
				String shortName = " Paisa";
				String currCode = req.getSession().getAttribute("currencyCode").toString();
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);
				if (objCurrModel != null) {
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

				if (jp != null) {

					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf")) {

						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=rptTaxInvoiceRetail" + userCode + ".pdf");
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();

					} else {

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

			} catch (Exception e) {
				e.printStackTrace();
			}

		}


		@RequestMapping(value = "/openRptProFormaInvoiceRetailReport", method = RequestMethod.GET)
		public void funOpenInvoiceRetailReport(@RequestParam("rptInvCode") String InvCode, String type, HttpServletResponse resp, HttpServletRequest req) {

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
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String suppName = "";

			String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptProFormaInvoiceGST.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/Sanguine_Logo_Icontest.jpg");

			String challanDate = "";
			String PONO = "";
			String InvDate = "";
			String CustName = "";
			String dcCode = "";

			String sqlProdDtl = " select  c.strProdName,c.strProdNameMarathi,b.dblQty,"
					// + "c.dblCostRM,"
					+ " IFNULL(b.dblPrice,0.00),c.dblMRP, IFNULL(b.dblPrice,0.00) AS dblPrice, a.dteInvDate, " + " IFNULL(d.strPName,''),ifnull(e.strDCCode,''),ifnull(e.dteDCDate,''),ifnull(e.strPONo,''), " + " b.strProdCode,f.strExciseChapter,g.dblValue as discAmt,IFNULL(d.strBAdd1,''),IFNULL(d.strBAdd2,''), "
					+ " IFNULL(d.strBState,''),IFNULL(d.strBPin,'') ,IFNULL(d.strSAdd1,''),IFNULL(d.strSAdd2,''), " + " IFNULL(d.strSState,''),IFNULL(d.strSPin,'') ,ifNull(strCST,''),b.dblProdDiscAmount from tblproformainvoicehd a left outer join tblproformainvoicedtl b on a.strInvCode=b.strInvCode   "
					+ " left outer join tblproductmaster c  on b.strProdCode=c.strProdCode left outer join tblpartymaster d on a.strCustCode=d.strPCode " + " left outer join tbldeliverychallanhd e on a.strSOCode=e.strDCCode " + " left outer join tblsubgroupmaster f on f.strSGCode=c.strSGCode " + " left outer join tblproformainvprodtaxdtl  g on a.strInvCode=g.strInvCode and a.strCustCode=g.strCustCode  "
					+ " and b.strProdCode=g.strProdCode and g.strDocNo='Disc'   " + " where a.strInvCode='" + InvCode + "' and a.strClientCode='" + clientCode + "' ";

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
			if (listProdDtl.size() > 0) {
				for (int i = 0; i < listProdDtl.size(); i++) {
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
					totalInvoiceValue = totalInvoiceValue + ((Double.parseDouble(obj[2].toString()) * Double.parseDouble(obj[3].toString())) - Double.parseDouble(obj[23].toString()));
					
					String sqlQuery = " select b.strTaxCode,b.dblPercent,a.dblValue,b.strShortName,a.dblTaxableAmt " + " from tblproformainvprodtaxdtl a,tbltaxhd b " + " where a.strDocNo=b.strTaxCode and a.strInvCode='" + InvCode + "' " + " and  a.strProdCode='" + obj[11].toString() + "' and a.strClientCode='" + clientCode + "'  ";

					List listProdGST = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");

					if (listProdGST.size() > 0) {
						for (int j = 0; j < listProdGST.size(); j++) {
							double cGStAmt = 0.0;
							double sGStAmt = 0.0;
							Object[] objGST = (Object[]) listProdGST.get(j);
							if (objGST[3].toString().equalsIgnoreCase("CGST")) {
								objDtlBean.setDblCGSTPer(Double.parseDouble(objGST[1].toString()));
								objDtlBean.setDblCGSTAmt(Double.parseDouble(objGST[2].toString()));
								objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
								totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
							} else if (objGST[3].toString().equalsIgnoreCase("SGST")) {
								objDtlBean.setDblSGSTPer(Double.parseDouble(objGST[1].toString()));
								objDtlBean.setDblSGSTAmt(Double.parseDouble(objGST[2].toString()));
								objDtlBean.setDblTaxableAmt(Double.parseDouble(objGST[4].toString()));
								totalInvoiceValue = totalInvoiceValue + Double.parseDouble(objGST[2].toString());
							} else {
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


			try {
				String shortName = " Paisa";
				String currCode = req.getSession().getAttribute("currencyCode").toString();
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);
				if (objCurrModel != null) {
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

				if (jp != null) {

					ServletOutputStream servletOutputStream = resp.getOutputStream();
					if (type.trim().equalsIgnoreCase("pdf")) {

						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=rptTaxInvoiceRetail" + userCode + ".pdf");
						exporter.exportReport();
						servletOutputStream.flush();
						servletOutputStream.close();

					} else {

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

			} catch (Exception e) {
				e.printStackTrace();
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

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*private String funGenrateJVforInvoice(clsInvoiceHdModel objModel, List<clsInvoiceModelDtl> listDtlModel, List<clsInvoiceTaxDtlModel> listTaxDtl, String clientCode, String userCode, String propCode, HttpServletRequest req) {
			JSONObject jObjJVData = new JSONObject();

			JSONArray jArrJVdtl = new JSONArray();
			JSONArray jArrJVDebtordtl = new JSONArray();
			String jvCode = "";
			String custCode = objModel.getStrCustCode();
			double debitAmt = objModel.getDblGrandTotal();
			String strCurr = req.getSession().getAttribute("currValue").toString();
			double currValue = Double.parseDouble(strCurr);
			clsLinkUpHdModel objLinkCust = objLinkupService.funGetARLinkUp(custCode, clientCode, propCode, "Customer", "Sale");
			if (objLinkCust != null) {
				if (objModel.getStrDktNo().equals("")) {
					jObjJVData.put("strVouchNo", "");
					jObjJVData.put("strNarration", "JV Generated by Invoice:" + objModel.getStrInvCode());
					jObjJVData.put("strSancCode", "");
					jObjJVData.put("strType", "");
					jObjJVData.put("dteVouchDate", objModel.getDteInvDate());
					jObjJVData.put("intVouchMonth", 1);
//					jObjJVData.put("dblAmt", debitAmt);
					jObjJVData.put("dblAmt", objModel.getDblSubTotalAmt());
					jObjJVData.put("strTransType", "R");
					jObjJVData.put("strTransMode", "A");
					jObjJVData.put("strModuleType", "AR");
					jObjJVData.put("strMasterPOS", "CRM");
					jObjJVData.put("strUserCreated", userCode);
					jObjJVData.put("strUserEdited", userCode);
					jObjJVData.put("dteDateCreated", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("dteDateEdited", objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
					jObjJVData.put("strClientCode", clientCode);
					jObjJVData.put("strPropertyCode", propCode);

				} else {
					jObjJVData.put("strVouchNo", objModel.getStrDktNo());
					jObjJVData.put("strNarration", "JV Generated by Invoice:" + objModel.getStrInvCode());
					jObjJVData.put("strSancCode", "");
					jObjJVData.put("strType", "");
					jObjJVData.put("dteVouchDate", objModel.getDteInvDate());
					jObjJVData.put("intVouchMonth", 1);
//					jObjJVData.put("dblAmt", debitAmt);
					jObjJVData.put("dblAmt", objModel.getDblSubTotalAmt());
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
				for (clsInvoiceModelDtl objDtl : listDtlModel) {

					JSONObject jObjDtl = new JSONObject();

					clsProductMasterModel objProdModle = objProductMasterService.funGetObject(objDtl.getStrProdCode(), clientCode);
					clsLinkUpHdModel objLinkSubGroup = objLinkupService.funGetARLinkUp(objProdModle.getStrSGCode(), clientCode, propCode, "SubGroup", "Sale");
					if (objProdModle != null && objLinkSubGroup != null) {
						jObjDtl.put("strVouchNo", "");
						jObjDtl.put("strAccountCode", objLinkSubGroup.getStrAccountCode());
						jObjDtl.put("strAccountName", objLinkSubGroup.getStrMasterDesc());
						jObjDtl.put("strCrDr", "Cr");
						jObjDtl.put("dblDrAmt", 0.00);
						jObjDtl.put("dblCrAmt", objDtl.getDblQty() * objDtl.getDblUnitPrice());
						jObjDtl.put("strNarration", "WS Product code :" + objDtl.getStrProdCode());
						jObjDtl.put("strOneLine", "R");
						jObjDtl.put("strClientCode", clientCode);
						jObjDtl.put("strPropertyCode", propCode);
						jArrJVdtl.add(jObjDtl);
					}
				}

				if (listTaxDtl != null) {
					for (clsInvoiceTaxDtlModel objTaxDtl : listTaxDtl) {
						JSONObject jObjTaxDtl = new JSONObject();
						clsLinkUpHdModel objLinkTax = objLinkupService.funGetARLinkUp(objTaxDtl.getStrTaxCode(), clientCode, propCode, "Tax", "Sale");
						if (objLinkTax != null) {
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
					}
				}
				
				if (objModel.getDblDiscountAmt() / currValue != 0) {
					JSONObject jObjTaxDtl = new JSONObject();
					clsLinkUpHdModel objLinkDisc = objLinkupService.funGetARLinkUp("Discount", clientCode, propCode, "Discount", "Sale");
					if (objLinkDisc != null) {
						jObjTaxDtl.put("strVouchNo", "");
						jObjTaxDtl.put("strAccountCode", objLinkDisc.getStrAccountCode());
						jObjTaxDtl.put("strAccountName", objLinkDisc.getStrMasterDesc());
						jObjTaxDtl.put("strCrDr", "Dr");
						jObjTaxDtl.put("dblDrAmt", objModel.getDblDiscountAmt());
						jObjTaxDtl.put("dblCrAmt", 0.00);
						jObjTaxDtl.put("strNarration", "WS Sale Discount Desc :" + objModel.getDblDiscountAmt());
						jObjTaxDtl.put("strOneLine", "R");
						jObjTaxDtl.put("strClientCode", clientCode);
						jObjTaxDtl.put("strPropertyCode", propCode);
						jArrJVdtl.add(jObjTaxDtl);
					}

				}
				
				
				

				JSONObject jObjCustDtl = new JSONObject();
				jObjCustDtl.put("strVouchNo", "");
				jObjCustDtl.put("strAccountCode", "");
				jObjCustDtl.put("strAccountName", "");
				jObjCustDtl.put("strCrDr", "Dr");
				jObjCustDtl.put("dblDrAmt", debitAmt);
				jObjCustDtl.put("dblCrAmt", 0.00);
				jObjCustDtl.put("strNarration", "Invoice Customer");
				jObjCustDtl.put("strOneLine", "R");
				jObjCustDtl.put("strClientCode", clientCode);
				jObjCustDtl.put("strPropertyCode", propCode);
				jArrJVdtl.add(jObjCustDtl);

				jObjJVData.put("ArrJVDtl", jArrJVdtl);

				// jvdtl entry end

				// jvDebtor detail entry start
				String sql = " select a.strInvCode,a.dblGrandTotal,b.strDebtorCode,b.strPName,date(a.dteInvDate)," 
						   + " a.strNarration ,date(a.dteInvDate),a.strInvCode" + " from tblinvoicehd a,tblpartymaster b " 
						   + " where a.strCustCode =b.strPCode  " + " and a.strInvCode='" + objModel.getStrInvCode() + "' " 
						   + " and a.strClientCode='" + objModel.getStrClientCode() + "'   ";
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

				JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGenrateJVforInvoice", jObjJVData);
				jvCode = jObj.get("strJVCode").toString();
			}
			return jvCode;
		}
*/
}
