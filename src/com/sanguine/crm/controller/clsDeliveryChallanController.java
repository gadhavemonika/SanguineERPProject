package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
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
import com.sanguine.crm.bean.clsDeliveryChallanBean;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanHdModel_ID;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.model.clsInvoiceHdModel_ID;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsDeliveryChallanHdService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;
import com.sanguine.service.clsUOMService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDeliveryChallanController {

	@Autowired
	private clsDeliveryChallanHdService objDeliveryChallanHdService;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsUOMService objclsUOMService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsSubGroupMasterService objSubGroupService;

	@Autowired
	private clsCRMSettlementMasterService objSettlementService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	List<clsSubGroupMasterModel> dataSG = new ArrayList<clsSubGroupMasterModel>();

	@RequestMapping(value = "/AutoCompletGetSubgroupNameForDC", method = RequestMethod.POST)
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

	// Open DeliveryChallan
	// Open DeliveryChallan
		@RequestMapping(value = "/frmDeliveryChallan", method = RequestMethod.GET)
		public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

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
			model.put("urlHits", urlHits);

			List<String> strAgainst = new ArrayList<>();
			strAgainst.add("Direct");
			strAgainst.add("Project");
			strAgainst.add("Sales Order");
			strAgainst.add("Invoice");
			model.put("againstList", strAgainst);

			Map<String, String> settlementList = objSettlementService.funGetSettlementComboBox(clientCode);
			model.put("settlementList", settlementList);
			
			String authorizationDCCode = "";
			boolean flagOpenFromAuthorization = true;
			try
			{
				authorizationDCCode = request.getParameter("authorizationDCCode").toString();
			}
			catch (NullPointerException e)
			{
				flagOpenFromAuthorization = false;
			}
			model.put("flagOpenFromAuthorization", flagOpenFromAuthorization);
			if (flagOpenFromAuthorization)
			{
				model.put("authorizationDCCode", authorizationDCCode);
			}
			
			if ("2".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmDeliveryChallan_1", "command", new clsDeliveryChallanBean());
			} else if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmDeliveryChallan", "command", new clsDeliveryChallanBean());
			} else {
				return null;
			}

		}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadDeliveryChallanHdData", method = RequestMethod.GET)
	public @ResponseBody clsDeliveryChallanBean funAssignFields(@RequestParam("dcCode") String dcCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsDeliveryChallanBean objBeanDC = new clsDeliveryChallanBean();

		List<Object> objDC = objDeliveryChallanHdService.funGetDeliveryChallan(dcCode, clientCode);
		clsDeliveryChallanHdModel objDeliveryChallanHdModel = null;
		clsLocationMasterModel objLocationMasterModel = null;
		clsPartyMasterModel objPartyMasterModel = null;

		for (int i = 0; i < objDC.size(); i++) {
			Object[] ob = (Object[]) objDC.get(i);
			objDeliveryChallanHdModel = (clsDeliveryChallanHdModel) ob[0];
			objLocationMasterModel = (clsLocationMasterModel) ob[1];
			objPartyMasterModel = (clsPartyMasterModel) ob[2];
		}

		objBeanDC = funPrepardHdBean(objDeliveryChallanHdModel, objLocationMasterModel, objPartyMasterModel);
		objBeanDC.setStrCustName(objPartyMasterModel.getStrPName());
		objBeanDC.setStrLocName(objLocationMasterModel.getStrLocName());
		List<clsDeliveryChallanModelDtl> listDCDtl = new ArrayList<clsDeliveryChallanModelDtl>();
		List<clsInvoiceBean> listPreviousInv = new ArrayList<clsInvoiceBean>();
		List<Object> objDCDtlModelList = objDeliveryChallanHdService.funGetDeliveryChallanDtl(dcCode, clientCode);
		for (int i = 0; i < objDCDtlModelList.size(); i++) {
			Object[] ob = (Object[]) objDCDtlModelList.get(i);
			clsDeliveryChallanModelDtl dcDtl = (clsDeliveryChallanModelDtl) ob[0];
			clsProductMasterModel prodmast = (clsProductMasterModel) ob[1];
			clsDeliveryChallanHdModel objHDModel = (clsDeliveryChallanHdModel) ob[2];
			dcDtl.setStrProdName(prodmast.getStrProdName());
			String sqlHd = "select b.dteInvDate,a.dblUnitPrice,b.strInvCode  from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + dcDtl.getStrProdCode() + "' and a.strClientCode='" + dcDtl.getStrClientCode() + "' and b.strInvCode=a.strInvCode " + " and a.strCustCode='" + objHDModel.getStrCustCode()
					+ "' and  b.dteInvDate=(SELECT MAX(b.dteInvDate) from tblinvoicedtl a,tblinvoicehd b " + " where a.strProdCode='" + dcDtl.getStrProdCode() + "' and a.strClientCode='" + dcDtl.getStrClientCode() + "' and b.strInvCode=a.strInvCode  " + " and a.strCustCode='" + objHDModel.getStrCustCode() + "')";

			List listPrevInvData = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			clsInvoiceBean objInvBean = new clsInvoiceBean();
			if (listPrevInvData.size() > 0) {
				Object objInv[] = (Object[]) listPrevInvData.get(0);
				objInvBean.setDblUnitPrice(Double.parseDouble(objInv[1].toString()));
				objInvBean.setStrInvCode(objInv[2].toString());
				listPreviousInv.add(objInvBean);
			} else {
				objInvBean.setDblUnitPrice(0.0);
				objInvBean.setStrInvCode(" ");
				listPreviousInv.add(objInvBean);
			}
			listDCDtl.add(dcDtl);
		}
		objBeanDC.setListclsInvoiceBean(listPreviousInv);
		objBeanDC.setListclsDeliveryChallanModelDtl(listDCDtl);
		return objBeanDC;
	}

	// Save or Update DeliveryChallan
	@RequestMapping(value = "/saveDeliveryChallan", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsDeliveryChallanBean objBean, BindingResult result, HttpServletRequest req) {
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
			clsDeliveryChallanHdModel objHdModel = funPrepareHdModel(objBean, userCode, clientCode, req);

			flgHdSave = objDeliveryChallanHdService.funAddUpdateDeliveryChallanHd(objHdModel);
			String strDCCode = objHdModel.getStrDCCode();
			List<clsDeliveryChallanModelDtl> listDCDtlModel = objBean.getListclsDeliveryChallanModelDtl();
			if (flgHdSave) {
				if (null != listDCDtlModel) {
					objDeliveryChallanHdService.funDeleteDtl(strDCCode, clientCode);
					int intindex = 1;
					
					for (clsDeliveryChallanModelDtl obDCDtl : listDCDtlModel) {
						if (null != obDCDtl.getStrProdCode()) {
							clsProductMasterModel objProdModle = objProductMasterService.funGetObject(obDCDtl.getStrProdCode(), clientCode);
							obDCDtl.setDblPrice(objProdModle.getDblUnitPrice());
							obDCDtl.setIntindex(intindex);
							obDCDtl.setStrDCCode(strDCCode);
							obDCDtl.setStrClientCode(clientCode);
							objDeliveryChallanHdService.funAddUpdateDeliveryChallanDtl(obDCDtl);
							intindex++;
						}
					}
					req.getSession().setAttribute("success", true);
					req.getSession().setAttribute("successMessage", "DC Code : ".concat(objHdModel.getStrDCCode()));
					req.getSession().setAttribute("rptDCCode", objHdModel.getStrDCCode());
				}
				
				if (objHdModel.getStrAgainst().equalsIgnoreCase("Invoice")) {

					String sqlCloseDC = "update tblinvoicehd set strCloseIV='Y'  where strInvCode='" + objBean.getStrDCCode() + "' and strClientCode='" + clientCode + "'";
					objGlobalFunctionsService.funUpdateAllModule(sqlCloseDC, "sql");
				}
			}

			return new ModelAndView("redirect:/frmDeliveryChallan.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmDeliveryChallan?saddr=" + urlHits, "command", new clsDeliveryChallanBean());
		}
	}

	// Convert bean to model function
	private clsDeliveryChallanHdModel funPrepareHdModel(clsDeliveryChallanBean objBean, String userCode, String clientCode, HttpServletRequest req) {
		
		long lastNo = 0;
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		clsDeliveryChallanHdModel DCHDModel=new clsDeliveryChallanHdModel();
		
		if (objBean.getStrDCCode().trim().length() == 0) {

			lastNo = objGlobalFunctionsService.funGetLastNo("tbldeliverychallanhd", "DCCode", "intId", clientCode);
			String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
			String cd = objGlobalFunctions.funGetTransactionCode("DC", propCode, year);
			String strDCCode = cd + String.format("%06d", lastNo);

			DCHDModel = new clsDeliveryChallanHdModel(new clsDeliveryChallanHdModel_ID(strDCCode, clientCode));
			DCHDModel.setStrDCCode(strDCCode);
			DCHDModel.setStrUserCreated(userCode);
			DCHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
			DCHDModel.setIntid(lastNo);
		}
		else
		{
			DCHDModel = objDeliveryChallanHdService.funGetDeliveryChallanHd(objBean.getStrDCCode(), clientCode);
			if (null == DCHDModel)
			{
				lastNo = objGlobalFunctionsService.funGetLastNo("tbldeliverychallanhd", "DCCode", "intId", clientCode);
				String year = objGlobalFunctions.funGetSplitedDate(startDate)[2];
				String cd = objGlobalFunctions.funGetTransactionCode("DC", propCode, year);
				String strDCCode = cd + String.format("%06d", lastNo);
				DCHDModel = new clsDeliveryChallanHdModel(new clsDeliveryChallanHdModel_ID(strDCCode, clientCode));
				DCHDModel.setStrDCCode(strDCCode);
				DCHDModel.setStrUserCreated(userCode);
				DCHDModel.setDteCreatedDate(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
				DCHDModel.setIntid(lastNo);
			}
			else
			{
				DCHDModel = new clsDeliveryChallanHdModel(new clsDeliveryChallanHdModel_ID(objBean.getStrDCCode(), clientCode));
			}
		}
		
		DCHDModel.setStrUserModified(userCode);
		DCHDModel.setDteLastModified(objGlobalFunctions.funGetCurrentDateTime("yyyy-MM-dd"));
		DCHDModel.setDteDCDate(objBean.getDteDCDate());
		DCHDModel.setStrAgainst(objBean.getStrAgainst());
		DCHDModel.setStrAuthorise(objBean.getStrAuthorise());
		DCHDModel.setStrCustCode(objBean.getStrCustCode());
		DCHDModel.setStrDCNo("");
		DCHDModel.setStrDktNo(objBean.getStrDktNo());
		DCHDModel.setStrLocCode(objBean.getStrLocCode());
		DCHDModel.setStrMInBy(objBean.getStrMInBy());
		DCHDModel.setStrNarration(objBean.getStrNarration());
		DCHDModel.setStrPackNo(objBean.getStrPackNo());
		DCHDModel.setStrPONo(objBean.getStrPONo());
		DCHDModel.setStrReaCode(objBean.getStrReaCode());
		DCHDModel.setStrSAdd1(objBean.getStrSAdd1());
		DCHDModel.setStrSAdd2(objBean.getStrSAdd2());
		DCHDModel.setStrSCity(objBean.getStrSCity());
		DCHDModel.setStrSCtry(objBean.getStrSCtry());
		DCHDModel.setStrSerialNo(objBean.getStrSerialNo());
		DCHDModel.setStrSettlementCode(objBean.getStrSettlementCode());
		if (objBean.getStrSOCode() == null) {
			DCHDModel.setStrSOCode("");
		} else {
			DCHDModel.setStrSOCode(objBean.getStrSOCode());
		}
		DCHDModel.setStrSPin(objBean.getStrSPin());
		DCHDModel.setStrSState(objBean.getStrSState());
		DCHDModel.setStrTimeInOut(objBean.getStrTimeInOut());
		DCHDModel.setStrVehNo(objBean.getStrVehNo());
		DCHDModel.setStrWarraValidity(objBean.getStrWarraValidity());
		DCHDModel.setStrWarrPeriod(objBean.getStrWarrPeriod());
		DCHDModel.setStrAuthorise("");
		DCHDModel.setStrCloseDC("N");

		return DCHDModel;

	}

	private clsDeliveryChallanBean funPrepardHdBean(clsDeliveryChallanHdModel objDCHdModel, clsLocationMasterModel objLocationMasterModel, clsPartyMasterModel objPartyMasterModel) {

		clsDeliveryChallanBean objBean = new clsDeliveryChallanBean();
		objBean.setDteDCDate(objDCHdModel.getDteDCDate());
		objBean.setStrAgainst(objDCHdModel.getStrAgainst());
		objBean.setStrCustCode(objDCHdModel.getStrCustCode());
		objBean.setStrDCCode(objDCHdModel.getStrDCCode());
		objBean.setStrDCNo(objDCHdModel.getStrDCNo());
		objBean.setStrDktNo(objDCHdModel.getStrDktNo());
		objBean.setStrLocCode(objDCHdModel.getStrLocCode());
		objBean.setStrMInBy(objDCHdModel.getStrMInBy());
		objBean.setStrNarration(objDCHdModel.getStrNarration());
		objBean.setStrPackNo(objDCHdModel.getStrPackNo());
		objBean.setStrPONo(objDCHdModel.getStrPONo());

		objBean.setStrReaCode(objDCHdModel.getStrReaCode());
		objBean.setStrSAdd1(objDCHdModel.getStrSAdd1());
		objBean.setStrSAdd2(objDCHdModel.getStrSAdd2());
		objBean.setStrSCity(objDCHdModel.getStrSCity());
		objBean.setStrSCountry(objDCHdModel.getStrSCtry());
		objBean.setStrSCtry(objDCHdModel.getStrSCtry());
		objBean.setStrSerialNo(objDCHdModel.getStrSerialNo());
		objBean.setStrSOCode(objDCHdModel.getStrSOCode());
		objBean.setStrSPin(objDCHdModel.getStrSPin());
		objBean.setStrSState(objDCHdModel.getStrSState());
		objBean.setStrTimeInOut(objDCHdModel.getStrTimeInOut());
		objBean.setStrVehNo(objDCHdModel.getStrVehNo());
		objBean.setStrWarraValidity(objDCHdModel.getStrWarraValidity());
		objBean.setStrWarrPeriod(objDCHdModel.getStrWarrPeriod());
		objBean.setStrSettlementCode(objDCHdModel.getStrSettlementCode());
		return objBean;
	}

	@RequestMapping(value = "/frmDeliveryChallanList", method = RequestMethod.GET)
	public ModelAndView funDeliveryChallanListForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryChallanList_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDeliveryChallanList", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/frmDeliveryChallanSlip", method = RequestMethod.GET)
	public ModelAndView funDeliveryChallanSlipForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDeliveryChallanSlip_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmDeliveryChallanSlip", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/rptDeliveryChallanSlip", method = RequestMethod.GET)
	private void funReportDeliveryChallanReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String DCCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String showBOM = objBean.getStrShowBOM();

		funCallReportDeliveryChallanReport(DCCode, type, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void funCallReportDeliveryChallanReport(String DCCode, String type, HttpServletResponse resp, HttpServletRequest req) {
		try {

			String strDCCode = "";
			String dteDCDate = "";
			String strSOCode = "";
			String strPName = "";
			String strBAdd1 = "";
			String strBAdd2 = "";
			String strBCity = "";
			String strBPin = "";
			String strBState = "";
			String strBCountry = "";
			String strNarration = "";
			String strCustPONo = "";
			String dteCPODate = "";
			
			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptDeliveryChallanSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "select a.strDCCode,a.dteDCDate,a.strSOCode,b.strPName,b.strSAdd1,b.strSAdd2,b.strSCity,b.strSPin,b.strSState,b.strSCountry , ifnull(c.strCustPONo,''),ifnull(date(c.dteCPODate),'')  " + "	from tbldeliverychallanhd a   " + " left outer join tblpartymaster b on a.strCustCode=b.strPCode " + " left outer join   tblsalesorderhd c on a.strSOCode=c.strSOCode "
					+ " where a.strDCCode='" + DCCode + "' " + " and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);
				strDCCode = arrObj[0].toString();
				dteDCDate = arrObj[1].toString();
				strSOCode = arrObj[2].toString();
				strPName = arrObj[3].toString();
				strBAdd1 = arrObj[4].toString();
				strBAdd2 = arrObj[5].toString();
				strBCity = arrObj[6].toString();
				strBPin = arrObj[7].toString();
				strBState = arrObj[8].toString();
				strBCountry = arrObj[9].toString();
				strCustPONo = arrObj[10].toString();
				dteCPODate = arrObj[11].toString();

			}

			String sqlDtl = " select b.strProdName,a.dblQty,'',b.strUOM,a.strPktNo,a.strRemarks ,b.strPartNo "
				+ " from tbldeliverychallandtl a,tblproductmaster b where strDCCode='" + DCCode + "' "
				+ " and a.strProdCode=b.strProdCode  and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode  order by a.strPktNo,a.strProdCode ";

			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dsDCSlip");
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
			hm.put("strPName", strPName);
			hm.put("strBAdd1", strBAdd1);
			hm.put("strBAdd2", strBAdd2);
			hm.put("strBCity", strBCity);
			hm.put("strBState", strBState);
			hm.put("strBCountry", strBCountry);
			hm.put("strBPin", strBPin);
			hm.put("strDCCode", strDCCode);
			hm.put("dteDCDate", dteDCDate);

			hm.put("strclientCode", clientCode);
			hm.put("strCustPONo", strCustPONo);
			hm.put("dteCPODate", dteCPODate);
			hm.put("strInvCode", strSOCode);
			
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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/openRptDCSlip", method = RequestMethod.GET)
	public void funOpenReport(HttpServletResponse resp, HttpServletRequest req) {

		String DCCode = req.getParameter("rptDCCode").toString();
		req.getSession().removeAttribute("rptDCCode");
		String type = "pdf";

		funCallReportDeliveryChallanReport(DCCode, type, resp, req);

	}

	@RequestMapping(value = "/rptDeliveryChallanList", method = RequestMethod.GET)
	private void funDeliveryChallanReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String SOCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		String fromDate = objBean.getDtFromDate();
		String toDate = objBean.getDtToDate();
		String against = objBean.getStrAgainst();

		funCallDeliveryChallanReport(SOCode, type, fromDate, toDate, against, resp, req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallDeliveryChallanReport(String SOCode, String type, String fromDate, String toDate, String against, HttpServletResponse resp, HttpServletRequest req) {

		try {
			String strDNCode = "";
			String dteDCDate = "";
			String strPName = "";
			String strSAdd1 = "";
			String strSAdd2 = "";
			String strSCity = "";
			String totalQty = "";
			
			Connection con = objGlobalFunctions.funGetConnection(req);
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String companyName = req.getSession().getAttribute("companyName").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			if (objSetup == null) {
				objSetup = new clsPropertySetupModel();
			}
			String reportName;
			if (against.equalsIgnoreCase("Summary")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/rptDeliveryChallanListSummary.jrxml");
			} else {
				reportName = servletContext.getRealPath("/WEB-INF/reports/rptDeliveryChallanListDetail.jrxml");
			}
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlHd = "SELECT  DATE(a.dteDCDate),b.strPName,a.strSAdd1,a.strSAdd2,a.strSCity,a.strSState,a.strSCtry," + " b.strBAdd1,b.strBAdd2,b.strBCity,b.strBState,b.strBPin,b.strBCountry,b.strPName " + "FROM tbldeliverychallanhd a,tblpartymaster b " + "WHERE a.strCustCode= '" + SOCode + "' AND a.strCustCode=b.strPCode " + "AND a.strClientCode='" + clientCode
					+ "' AND a.strClientCode=b.strClientCode " + "AND a.dteDCDate BETWEEN '" + fromDate + "' and '" + toDate + "'  ";

			List list = objGlobalFunctionsService.funGetList(sqlHd, "sql");
			if (!list.isEmpty()) {
				Object[] arrObj = (Object[]) list.get(0);

				dteDCDate = arrObj[0].toString();
				strPName = arrObj[1].toString();
				strSAdd1 = arrObj[2].toString();
				strSAdd2 = arrObj[3].toString();
				strSCity = arrObj[4].toString();
			}

			String sqlDtl;
			if (against.equalsIgnoreCase("Summary")) {
				sqlDtl = "select a.strDCCode,a.dteDCDate,a.strCustCode, b.strPName,a.strPONo,a.strPackNo,a.strAgainst,a.strSOCode " + "from tbldeliverychallanhd a,tblpartymaster b where a.strCustCode='" + SOCode + "' and a.strCustCode=b.strPCode " + "and a.strClientCode='" + clientCode + "' and a.strClientCode=b.strClientCode and a.dteDCDate between '" + fromDate + "' and '" + toDate + "' ";

			} else {
				sqlDtl = "select a.strDCCode ,b.strProdCode,b.strProdName,a.dblQty,a.strRemarks " + "from tbldeliverychallandtl a,tblproductmaster b,tbldeliverychallanhd c " + "where c.strCustCode='" + SOCode + "' and a.strDCCode=c.strDCCode and a.strClientCode='" + clientCode + "' and " + "a.strProdCode=b.strProdCode and c.dteDCDate between '" + fromDate + "' and '" + toDate + "'  ";
			}
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery subQuery = new JRDesignQuery();
			subQuery.setText(sqlDtl);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignDataset subDataset;
			if (against.equalsIgnoreCase("Summary")) {
				subDataset = (JRDesignDataset) datasetMap.get("dsDeliverychallanListSummary");
			} else {
				subDataset = (JRDesignDataset) datasetMap.get("dsDeliverychallanListDetail");
			}
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
			hm.put("strPin", objSetup.getStrPin());
			hm.put("strPName", strPName);
			hm.put("fromDate", fromDate);
			hm.put("toDate", toDate);

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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptBillPassingReport." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	/*
	 * 
	 * *
	 */

	@RequestMapping(value = "/openRptDCRetailSlip", method = RequestMethod.GET)
	public void funCallDeliveryChallanRetailReport(HttpServletResponse resp, HttpServletRequest req) {

		String DCCode = req.getParameter("rptDCCode").toString();
		req.getSession().removeAttribute("rptDCCode");
		String type = "pdf";
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

		String reportName = servletContext.getRealPath("/WEB-INF/reports/webcrm/rptDeliveryChallanRetail.jrxml");
		String imagePath = servletContext.getRealPath("/resources/images/company_Logo.jpg");

		String sqlQuery = " select c.strProdName,c.strProdNameMarathi,b.dblQty,DATE_FORMAT(a.dteDCDate,'%d-%m-%Y'),d.strLocName,a.strPONo ,e.strPName ,c.strPartNo from tbldeliverychallanhd a,tbldeliverychallandtl b ,tblproductmaster c ,tbllocationmaster d,tblpartymaster e " + " where a.strDCCode='" + DCCode
				+ "' and a.strDCCode=b.strDCCode and b.strProdCode=c.strProdCode and a.strLocCode=d.strLocCode and  a.strClientCode='" + clientCode + "' and a.strCustCode=e.strPCode ";

		String challanDate = "";
		String PONO = "";
		String Location = "";
		String CustName = "";

		List listProdDtl = objGlobalFunctionsService.funGetDataList(sqlQuery, "sql");
		for (int i = 0; i < listProdDtl.size(); i++) {
			Object[] obj = (Object[]) listProdDtl.get(i);
			challanDate = obj[3].toString();
			PONO = obj[5].toString();
			Location = obj[4].toString();
			CustName = obj[6].toString();

		}

		try {
			JasperDesign jd = JRXmlLoader.load(reportName);
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlQuery);
			jd.setQuery(newQuery);
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
			hm.put("challanNo", DCCode);
			hm.put("challanDate", challanDate);
			hm.put("PONO", PONO);
			hm.put("Location", Location);
			hm.put("CustName", CustName);
			hm.put("PODate", challanDate);

			JasperPrint p = JasperFillManager.fillReport(jr, hm, con);
			if (type.trim().equalsIgnoreCase("pdf")) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				byte[] bytes = null;
				JRPdfExporter exporter = new JRPdfExporter();
				/*
				 * exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING
				 * , "UTF-8");
				 * exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
				 * "UTF-8");
				 */
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, p);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, resp.getOutputStream());

				bytes = JasperRunManager.runReportToPdf(jr, hm, con);
				resp.setContentType("application/pdf");
				resp.setContentLength(bytes.length);
				resp.setHeader("Content-Disposition", "inline;filename=rptDeliveryChallanRetail" + userCode + ".pdf");
				servletOutputStream.write(bytes, 0, bytes.length);
				// exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();
			} else if (type.trim().equalsIgnoreCase("xls")) {
				JRExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, p);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, resp.getOutputStream());
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptDeliveryChallanRetail" + userCode + ".xlsx");
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
