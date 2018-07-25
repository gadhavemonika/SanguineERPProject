package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
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
import com.sanguine.bean.clsSupplierMasterBean;
import com.sanguine.excise.model.clsExciseSupplierMasterModel;
import com.sanguine.excise.service.clsExciseSupplierMasterService;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSupplierMasterModel;
import com.sanguine.model.clsSupplierMasterModel_ID;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSupplierMasterService;
import com.sanguine.util.clsReportBean;
import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryCreditorMasterModel_ID;
import com.sanguine.webbooks.model.clsSundryCreditorOpeningBalMasterModel;
import com.sanguine.webbooks.service.clsSundryCreditorMasterService;

@Controller
public class clsSupplierMasterController {
	@Autowired
	private clsSupplierMasterService objSupplierMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	
	clsGlobalFunctions objGlobal;
	@Autowired
	clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsSundryCreditorMasterService objSundryCreditorMasterService;

	@Autowired
	private clsLocationMasterService objLocationMasterService;

	// for Excise Service
	@Autowired
	private clsExciseSupplierMasterService objExciseSupplierMasterService;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;

	@RequestMapping(value = "/frmSupplierMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listType = new ArrayList<>();
		// listType.add("");
		listType.add("Local");
		listType.add("Foreign");
		model.put("typeList", listType);

		List<String> listCategory = new ArrayList<>();
		// listCategory.add("");
		listCategory.add("Wholesale Dealer");
		listCategory.add("Industrial Consumer");
		listCategory.add("Government Department");
		listCategory.add("Bakery");
		listCategory.add("Retailor");
		model.put("categoryList", listCategory);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		List<String> listPartyIndiacator = new ArrayList<>();
		listPartyIndiacator.add("");
		listPartyIndiacator.add("A");
		listPartyIndiacator.add("B");
		listPartyIndiacator.add("C");
		listPartyIndiacator.add("D");
		listPartyIndiacator.add("E");
		listPartyIndiacator.add("F");
		listPartyIndiacator.add("G");
		listPartyIndiacator.add("H");
		listPartyIndiacator.add("I");
		listPartyIndiacator.add("J");
		listPartyIndiacator.add("K");
		listPartyIndiacator.add("L");
		listPartyIndiacator.add("M");
		listPartyIndiacator.add("N");
		listPartyIndiacator.add("O");
		listPartyIndiacator.add("P");
		listPartyIndiacator.add("Q");
		listPartyIndiacator.add("R");
		listPartyIndiacator.add("S");
		listPartyIndiacator.add("T");
		listPartyIndiacator.add("U");
		listPartyIndiacator.add("V");
		listPartyIndiacator.add("W");
		listPartyIndiacator.add("X");
		listPartyIndiacator.add("Y");
		listPartyIndiacator.add("Z");
		model.put("partyIndicatorList", listPartyIndiacator);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSupplierMaster_1", "command", new clsSupplierMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSupplierMaster", "command", new clsSupplierMasterModel());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveSupplierMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSupplierMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		clsSundaryCreditorMasterModel objCreditorModel = null;
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		if (!result.hasErrors()) {
			clsSupplierMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			clsCompanyMasterModel objCompModel = objSetupMasterService.funGetObject(clientCode);
			if (objCompModel.getStrWebBookModule().equalsIgnoreCase("Yes")) {
				objCreditorModel = funPrepareDebtorModel(objBean, userCode, clientCode, propertyCode);
				objSundryCreditorMasterService.funAddUpdateSundryCreditorMaster(objCreditorModel);
				objModel.setStrDebtorCode(objCreditorModel.getStrCreditorCode());
			} else {
				objModel.setStrDebtorCode("");
			}
			objSupplierMasterService.funAddUpdate(objModel);

			// for supplier entry in excise db
			if (objModel.getStrExcisable().equals("Y")) {
				String exciseSuppCode = funSetExciseSuppData(objBean, clientCode, userCode);
				if (exciseSuppCode.trim().length() > 0) {
					objSupplierMasterService.funExciseUpdate(objModel.getStrPCode(), clientCode, exciseSuppCode);
				}
			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Supplier Code : ".concat(objModel.getStrPCode()));
			return new ModelAndView("redirect:/frmSupplierMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmSupplierMaster?saddr=" + urlHits, "command", new clsSupplierMasterModel());
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadSupplierMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSupplierMasterModel funAssignFields(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSupplierMasterModel objModel = objSupplierMasterService.funGetObject(PCode, clientCode);

		if (null == objModel) {
			objModel = new clsSupplierMasterModel();
			objModel.setStrPCode("Invalid Code");
			return objModel;
		} else {
			clsLocationMasterModel objLoc = objLocationMasterService.funGetObject(objModel.getStrLocCode(), clientCode);
			if (objLoc != null) {
				objModel.setStrLocName(objLoc.getStrLocName());
			}

			return objModel;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadSupplierTaxDtl", method = RequestMethod.GET)
	public @ResponseBody List funAssignFieldsForSupplierTaxDtl(@RequestParam("partyCode") String PCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strTaxCode,b.strTaxDesc from tblpartytaxdtl a,tbltaxhd b " + "where a.strTaxCode=b.strTaxCode and a.strPCode='" + PCode + "'";
		List arrList = objGlobalFunctionsService.funGetList(sql, clientCode);

		return arrList;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsSupplierMasterModel funPrepareModel(clsSupplierMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsSupplierMasterModel objModel;
		if (objBean.getStrPCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "SupplierMaster", "intPid", clientCode);
			String PCode = "S" + String.format("%06d", lastNo);
			objModel = new clsSupplierMasterModel(new clsSupplierMasterModel_ID(PCode, clientCode));
			objModel.setIntPId(lastNo);
			objModel.setStrUserCreated(userCode);
			objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsSupplierMasterModel objModel1 = objSupplierMasterService.funGetObject(objBean.getStrPCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblpartymaster", "SupplierMaster", "intPid", clientCode);
				String PCode = "S" + String.format("%06d", lastNo);
				objModel = new clsSupplierMasterModel(new clsSupplierMasterModel_ID(PCode, clientCode));
				objModel.setIntPId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				objModel = new clsSupplierMasterModel(new clsSupplierMasterModel_ID(objBean.getStrPCode(), clientCode));
			}
		}

		objModel.setStrPName(objBean.getStrPName().toUpperCase());
		objModel.setStrPhone(objBean.getStrPhone());
		objModel.setStrMobile(objBean.getStrMobile());
		objModel.setStrFax(objBean.getStrFax());
		objModel.setStrContact(objBean.getStrContact());
		objModel.setStrEmail(objBean.getStrEmail());

		objModel.setStrBankName(objBean.getStrBankName());
		objModel.setStrBankAdd1(objBean.getStrBankAdd1());
		objModel.setStrBankAdd2(objBean.getStrBankAdd2());
		objModel.setStrTaxNo1(objBean.getStrTaxNo1());
		objModel.setStrTaxNo2(objBean.getStrTaxNo2());
		objModel.setStrPmtTerms(objGlobal.funIfNull(objBean.getStrPmtTerms(), "", ""));
		objModel.setStrAcCrCode(objBean.getStrAcCrCode());
		objModel.setStrMAdd1(objBean.getStrMAdd1());
		objModel.setStrMAdd2(objBean.getStrMAdd2());
		objModel.setStrMCity(objBean.getStrMCity());
		objModel.setStrMPin(objBean.getStrMPin());
		objModel.setStrMState(objBean.getStrMState());
		objModel.setStrMCountry(objBean.getStrMCountry());
		objModel.setStrBAdd1(objBean.getStrBAdd1());
		objModel.setStrBAdd2(objBean.getStrBAdd2());
		objModel.setStrBCity(objBean.getStrBCity());
		objModel.setStrBPin(objBean.getStrBPin());
		objModel.setStrBState(objBean.getStrBState());
		objModel.setStrBCountry(objBean.getStrBCountry());
		objModel.setStrSAdd1(objBean.getStrSAdd1());
		objModel.setStrSAdd2(objBean.getStrSAdd2());
		objModel.setStrSCity(objBean.getStrSCity());
		objModel.setStrSPin(objBean.getStrSPin());
		objModel.setStrSState(objBean.getStrSState());
		objModel.setStrSCountry(objBean.getStrSCountry());
		objModel.setStrCST(objBean.getStrCST());
		objModel.setStrVAT(objBean.getStrVAT());
		objModel.setStrExcise(objBean.getStrExcise());
		objModel.setStrServiceTax(objBean.getStrServiceTax());
		objModel.setStrSubType(objGlobal.funIfNull(objBean.getStrSubType(), "", ""));
		objModel.setDtExpiryDate(objGlobal.funIfNull(objBean.getDtExpiryDate(), "2014-7-30", objBean.getDtExpiryDate()));
		objModel.setStrManualCode(objBean.getStrManualCode());
		objModel.setStrRegistration(objBean.getStrRegistration());
		objModel.setStrRange(objBean.getStrRange());
		objModel.setStrDivision(objBean.getStrDivision());
		objModel.setStrCommissionerate(objBean.getStrCommissionerate());
		objModel.setStrBankAccountNo(objBean.getStrBankAccountNo());
		objModel.setStrBankABANo(objBean.getStrBankABANo());
		objModel.setIntCreditDays(objBean.getIntCreditDays());
		objModel.setDblCreditLimit(objBean.getDblCreditLimit());
		objModel.setDblLatePercentage(objBean.getDblLatePercentage());
		objModel.setDblRejectionPercentage(objBean.getDblRejectPercentage());
		objModel.setStrIBANNo(objBean.getStrIBANNo());
		objModel.setStrSwiftCode(objBean.getStrSwiftCode());
		objModel.setStrCategory(objBean.getStrCategory());
		objModel.setStrExcisable(objGlobal.funIfNull(objBean.getStrExcisable(), "", ""));
		objModel.setStrCategory(objGlobal.funIfNull(objBean.getStrCategory(), "", objBean.getStrCategory()));
		objModel.setStrUserModified(userCode);
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserCreated(userCode);
		objModel.setStrPartyType(objGlobal.funIfNull(objBean.getStrPartyType(), " ", objBean.getStrPartyType()));
		objModel.setStrPType("Supp");
		objModel.setStrPartyIndi(objBean.getStrPartyIndi());
		objModel.setStrOperational(objBean.getStrOperational());
		objModel.setStrAccManager("");
		objModel.setStrECCNo("");
		objModel.setDtInstallions(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrGSTNo(objBean.getStrGSTNo());
		objModel.setStrLocCode(objBean.getStrLocCode());
		objModel.setStrPropCode(objBean.getStrPropCode());
		objModel.setStrExternalCode(objBean.getStrExternalCode());
		objModel.setStrCurrency(objGlobal.funIfNull(objBean.getStrCurrency(), " ", objBean.getStrCurrency()));
		objModel.setStrComesaRegion(objBean.getStrComesaRegion());

		List<clsPartyTaxIndicatorDtlModel> listPartyTaxDtl = new ArrayList<clsPartyTaxIndicatorDtlModel>();
		List<clsPartyTaxIndicatorDtlModel> arrListTemp = objBean.getListclsPartyTaxIndicatorDtlModel();
		if (arrListTemp != null) {
			Iterator<clsPartyTaxIndicatorDtlModel> it = arrListTemp.iterator();
			while (it.hasNext()) {
				clsPartyTaxIndicatorDtlModel Obj = it.next();
				if (null == Obj.getStrTaxCode()) {
					it.remove();
				} else {
					listPartyTaxDtl.add(Obj);
				}
			}
		}
		objModel.setArrListPartyTaxDtlModel(listPartyTaxDtl);

		return objModel;
	}

	@RequestMapping(value = "loadAllSupplier", method = RequestMethod.GET)
	public @ResponseBody List<clsSupplierMasterModel> funGetAllSupplierList(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsSupplierMasterModel> listSupplierdtl = objSupplierMasterService.funGetList(clientCode);
		if (listSupplierdtl.isEmpty()) {
			listSupplierdtl = new ArrayList<clsSupplierMasterModel>();
		}
		return listSupplierdtl;
	}

	@RequestMapping(value = "/frmSupplierList", method = RequestMethod.GET)
	public ModelAndView funOpenReportForm() throws JRException {
		return new ModelAndView("frmSupplierList", "command", new clsReportBean());
	}

	@RequestMapping(value = "/rptSupplierList", method = RequestMethod.POST)
	public ModelAndView funShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String suppType = "ALL";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();
		String pCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		List<clsSupplierMasterModel> supplierMasterList = funPrepareSupplierList(objSupplierMasterService.funGetDtlList(pCode, clientCode));
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(supplierMasterList);
		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		model.put("strSuppType", suppType);
		modelAndView = new ModelAndView("SupplierList" + type.trim().toUpperCase() + "Report", model);

		return modelAndView;
	}

	@SuppressWarnings("rawtypes")
	private List<clsSupplierMasterModel> funPrepareSupplierList(List listSupplier) {
		String suppAddress = "";
		List<clsSupplierMasterModel> objSupplierList = new ArrayList<clsSupplierMasterModel>();
		for (int i = 0; i < listSupplier.size(); i++) {
			suppAddress = "";
			clsSupplierMasterModel supp = (clsSupplierMasterModel) listSupplier.get(i);
			clsSupplierMasterModel objSupp = new clsSupplierMasterModel();
			objSupp.setStrPCode(supp.getStrPCode());
			objSupp.setStrPName(supp.getStrPName());
			objSupp.setStrPhone(supp.getStrPhone());
			objSupp.setStrMobile(supp.getStrMobile());
			objSupp.setStrFax(supp.getStrFax());
			objSupp.setStrContact(supp.getStrContact());
			objSupp.setStrEmail(supp.getStrEmail());
			suppAddress = supp.getStrSAdd1() + "\n" + supp.getStrSAdd2() + "\n" + supp.getStrSCity();
			objSupp.setStrSAdd1(suppAddress);
			objSupplierList.add(objSupp);
		}
		return objSupplierList;

	}

	@RequestMapping(value = "/checkSuppName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("suppName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmSupplierMaster");
		return count;

	}

	/**
	 * Report Code
	 * 
	 * @return
	 * @throws JRException
	 */
	// Jai chandra 04-01-2015

	@RequestMapping(value = "/frmProdWiseSuppRateHis", method = RequestMethod.GET)
	public ModelAndView funOpenProdWiseSuppRateHisForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsReportBean objRptBean = new clsReportBean();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		objRptBean.setGroup(mapGroup);
		objRptBean.setSubGroup(mapSubGroup);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmProdWiseSuppRateHis_1", "command", objRptBean);
		} else {
			return new ModelAndView("frmProdWiseSuppRateHis", "command", objRptBean);
		}

	}

	@RequestMapping(value = "/rptProdWiseSuppHis", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReport(objBean, resp, req);
	}

	private void funCallReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			objGlobal = new clsGlobalFunctions();
			Connection con = objGlobal.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String fromDate = objBean.getDtFromDate();
			String toDate = objBean.getDtToDate();
			String type = objBean.getStrDocType();
			String ProdCode = objBean.getStrDocCode();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String[] splitDate = fromDate.split("-");
			String frmDate = splitDate[2] + "-" + splitDate[1] + "-" + splitDate[0];
			String[] splitToDate = toDate.split("-");
			String ToDate = splitToDate[2] + "-" + splitToDate[1] + "-" + splitToDate[0];

			String reportName="";
			if(objSetup.getStrRateHistoryFormat().equalsIgnoreCase("Format 1"))
			{
			reportName = servletContext.getRealPath("/WEB-INF/reports/rptProdWiseSuppHis.jrxml");
			}
			else
			{
			reportName = servletContext.getRealPath("/WEB-INF/reports/rptProdWiseSuppHis1.jrxml");	
			}
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHDQuery = "select 1 from dual";

			String sqlDtlQuery = "select b.strProdCode, d.strProdName, c.strPName, a.strAgainst , a.strPONo,  DATE_FORMAT(date(a.dtGRNDate),'%d-%m-%Y') as dtGRNDate, " + " a.strGRNCode, b.dblQty, d.strReceivedUOM, b.dblUnitPrice, b.dblTotalPrice  " + " from tblgrnhd a, tblgrndtl b, tblpartymaster c, tblproductmaster d " + " where a.strGRNCode = b.strGRNCode and a.strSuppCode = c.strPCode "
					+ " and b.strProdCode = d.strProdCode and a.dtGRNDate >= '" + frmDate + "' and a.dtGRNDate <= '" + ToDate + "' " + " and a.strClientCode ='" + clientCode + "' and b.strClientCode = '" + clientCode + "' and c.strClientCode = '" + clientCode + "' and d.strClientCode = '" + clientCode + "' ";
			String SGCode = "";
			String strSGCodes[] = objBean.getStrSGCode().split(",");
			for (int i = 0; i < strSGCodes.length; i++) {
				if (SGCode.length() > 0) {
					SGCode = SGCode + " or d.strSGCode='" + strSGCodes[i] + "' ";
				} else {
					SGCode = "d.strSGCode='" + strSGCodes[i] + "' ";
				}

			}

			sqlDtlQuery = sqlDtlQuery + " and " + "(" + SGCode + ")";
			if (ProdCode != null && ProdCode.length() > 0) {
				sqlDtlQuery = sqlDtlQuery + "and b.strProdCode = '" + ProdCode + "' ";
			}

			sqlDtlQuery = sqlDtlQuery + " order by d.strProdName, a.dtGRNDate, a.strGRNCode ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlHDQuery);
			jd.setQuery(newQuery);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtlQuery);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsProdWiseSuppRatedtl");
			subDataset.setQuery(subQuery);

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
			hm.put("strFromDate", frmDate);
			hm.put("strToDate", ToDate);
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
				exporterXLS.setParameter(JRPdfExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptProdWiseSuppHis." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	private String funSetExciseSuppData(clsSupplierMasterBean objBean, String clientCode, String userCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsExciseSupplierMasterModel objModel = new clsExciseSupplierMasterModel();
		String exciseSuppCode = "";
		if (objBean != null) {
			if (!(objBean.getStrExcise().isEmpty())) {
				List objList = objExciseSupplierMasterService.funGetObject(objBean.getStrExcise(), clientCode);
				Object obj[] = (Object[]) objList.get(0);
				clsExciseSupplierMasterModel objModel1 = (clsExciseSupplierMasterModel) obj[0];
				if (objModel1 != null) {
					objModel.setStrSupplierCode(objModel1.getStrSupplierCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCountAllModule("tblsuppliermaster", "intId");
				String licenceCode = "S" + String.format("%05d", lastNo);

				objModel.setStrSupplierCode(licenceCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			// objModel.setStrSupplierNo(objBean.getStrSupplierNo());
			objModel.setStrSupplierName(objBean.getStrPName());
			objModel.setStrVATNo(objBean.getStrVAT());
			objModel.setStrTINNo("");
			objModel.setStrAddress(objBean.getStrSAdd1() + "," + objBean.getStrSAdd2());
			objModel.setStrCityCode(objBean.getStrSCity());
			objModel.setStrPINCode(objBean.getStrSPin());
			objModel.setStrEmailId(objBean.getStrEmail());
			objModel.setLongTelephoneNo(Long.parseLong(objBean.getStrPhone()));
			objModel.setLongMobileNo(Long.parseLong(objBean.getStrPhone()));
			objModel.setStrContactPerson(objBean.getStrContact());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			exciseSuppCode = objModel.getStrSupplierCode();
			objExciseSupplierMasterService.funAddUpdateExciseSupplierMaster(objModel);
		}
		return exciseSuppCode;
	}


	private clsSundaryCreditorMasterModel funPrepareDebtorModel(clsSupplierMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSundaryCreditorMasterModel objModel;
		List<clsSundryCreditorOpeningBalMasterModel> listSundryCreditorOpenongBalModel=null;
		if (objBean.getStrDebtorCode() == null || objBean.getStrDebtorCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNoModuleWise("tblsundarycreditormaster", "CreditorMaster", "intGId", clientCode, "5-WebBook");
			String debtorCode = "C" + String.format("%08d", lastNo);
			objModel = new clsSundaryCreditorMasterModel(new clsSundryCreditorMasterModel_ID(debtorCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setDteStartDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		} else {
			objModel = objSundryCreditorMasterService.funGetSundryCreditorMaster(objBean.getStrDebtorCode().trim(), clientCode);
			if (null == objModel) {
				lastNo = objGlobalFunctionsService.funGetLastNoModuleWise("tblsundarycreditormaster", "CreditorMaster", "intGId", clientCode, "5-WebBook");
				String debtorCode = "C" + String.format("%08d", lastNo);
				objModel = new clsSundaryCreditorMasterModel(new clsSundryCreditorMasterModel_ID(debtorCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setDteStartDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {/*
				String strStarDate = objModel1.getDteStartDate();
				objModel = new clsSundaryCreditorMasterModel(new clsSundryCreditorMasterModel_ID(objBean.getStrDebtorCode(), clientCode));
				objModel.setDteStartDate(strStarDate);
				listSundryCreditorOpenongBalModel = objModel1.getListSundryCreditorOpenongBalModel();
				objModel.setListSundryCreditorOpenongBalModel(listSundryCreditorOpenongBalModel);
			*/}
		}
		/* setting main data */
		
		objModel.setStrPrefix("");
		objModel.setStrFirstName(objBean.getStrPName());
		objModel.setStrMiddleName("");
		objModel.setStrLastName("");
		objModel.setStrCategoryCode("");
		/* setting main data */

		objModel.setStrAddressLine1(objBean.getStrMAdd1());
		objModel.setStrAddressLine2(objBean.getStrMAdd2());
		objModel.setStrAddressLine3("");
		objModel.setStrBlocked("NO");
		objModel.setStrCity(objBean.getStrMCity());
		objModel.setLongZipCode(objBean.getStrMPin());
		objModel.setStrTelNo1(objBean.getStrPhone());
		objModel.setStrTelNo2(objBean.getStrMobile());
		objModel.setStrFax(objBean.getStrFax());
		objModel.setStrArea("");
		objModel.setStrEmail(objBean.getStrEmail());
		objModel.setStrContactPerson1(objBean.getStrContact());
		objModel.setStrContactDesignation1("");
		objModel.setStrContactEmail1("");
		objModel.setStrContactTelNo1("");
		objModel.setStrContactPerson2("");
		objModel.setStrContactDesignation2("");
		objModel.setStrContactEmail2("");
		objModel.setStrContactTelNo2("");
		objModel.setStrLandmark("");
		objModel.setStrCreditorFullName(objBean.getStrPName());
		objModel.setStrExpired("N");
		objModel.setStrExpiryReasonCode("");
		objModel.setStrECSYN("N");
		objModel.setStrAccountNo("");
		objModel.setStrHolderName("");
		objModel.setStrMICRNo("");
		objModel.setDblECS("0.00");
		objModel.setStrSaveCurAccount("");
		objModel.setStrAlternateCode("");
		objModel.setDblOutstanding("0.00");
		objModel.setStrStatus("");
		objModel.setIntDays1("0");
		objModel.setIntDays2("0");
		objModel.setIntDays3("0");
		objModel.setIntDays4("0");
		objModel.setIntDays5("0");
		objModel.setDblCrAmt("0");
		objModel.setDblDrAmt("0");
		objModel.setDteLetterProcess(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrReminder1("0");
		objModel.setStrReminder2("0");
		objModel.setStrReminder3("0");
		objModel.setStrReminder4("0");
		objModel.setStrReminder5("0");
		objModel.setDblLicenseFee("");
		objModel.setDblAnnualFee("");
		objModel.setStrRemarks("");
		objModel.setStrClientApproval("");
		objModel.setStrAMCLink("");
		objModel.setStrCurrencyType("");
		objModel.setStrAccountHolderCode("");
		objModel.setStrAccountHolderName("");
		objModel.setStrAMCCycle("Yearly");

		objModel.setStrAMCRemarks("");
		objModel.setStrClientComment("");
		objModel.setStrBillingToCode("");
		objModel.setDblAnnualFeeInCurrency("");
		objModel.setDblLicenseFeeInCurrency("");
		objModel.setStrState("");
		objModel.setStrRegion("");
		objModel.setStrCountry("");
		objModel.setStrConsolidated("");
		objModel.setIntCreditDays("");
		objModel.setStrCreditorStatusCode("");
		objModel.setLongMobileNo("");
		objModel.setStrECSActivate("");
		objModel.setStrReminderStatus1("");
		objModel.setDteRemainderDate1("");
		objModel.setStrReminderStatus2("");
		objModel.setDteRemainderDate2("");
		objModel.setStrReminderStatus3("");
		objModel.setDteRemainderDate3("");
		objModel.setStrReminderStatus4("");
		objModel.setDteRemainderDate4("");
		objModel.setStrReminderStatus5("");
		objModel.setDteRemainderDate5("");
		objModel.setStrAllInvoiceHeader("");

		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propertyCode);
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrAccountCode("");
		objModel.setStrAccountName("");
		objModel.setStrOperational(objBean.getStrOperational());
		return objModel;
	}



	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadGetSupplierLocationProperty", method = RequestMethod.GET)
	public @ResponseBody clsSupplierMasterModel funAssignFieldsForSupplierTaxDtl(@RequestParam("locCode") String locCode, @RequestParam("propCode") String propCode, HttpServletRequest req) {
		clsSupplierMasterModel objPartyModel = new clsSupplierMasterModel();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String hql = " from clsSupplierMasterModel where  strLocCode='" + locCode + "' and strPropCode='" + propCode + "' and strClientCode='" + clientCode + "' ";
		List arrList = objGlobalFunctionsService.funGetList(hql, "hql");
		if (arrList.size() > 0) {
			objPartyModel = (clsSupplierMasterModel) arrList.get(0);
		} else {
			objPartyModel.setStrPCode("");
			objPartyModel.setStrPName("");
		}

		return objPartyModel;
	}

}
