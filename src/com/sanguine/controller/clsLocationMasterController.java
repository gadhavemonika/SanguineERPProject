package com.sanguine.controller;

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
import com.sanguine.bean.clsLocationMasterBean;
import com.sanguine.model.clsLinkUpHdModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsLocationMasterModel_ID;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsLinkUpService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsLocationMasterController {
	@Autowired
	private clsLocationMasterService objLocationMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private clsSetupMasterService objSetupMasterService;
	@Autowired
	private clsProductMasterService objProductMasterService;
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsLinkUpService objARLinkUpService;

	/**
	 * Global variable For AutoComplete
	 */
	Set<String> data = new HashSet<String>();

	/**
	 * Auto Complete Location Name
	 * 
	 * @param term
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/AutoCompletGetLocationName", method = RequestMethod.POST)
	public @ResponseBody Set<String> getLocationNames(@RequestParam String term, HttpServletResponse response) {
		return simulateSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<String> simulateSearchResult(String LocName) {
		Set<String> result = new HashSet<String>();
		// iterate a list and filter by ProductName
		for (String Location : data) {
			if (Location.contains(LocName.toUpperCase())) {
				result.add(Location);
			}
		}
		return result;
	}

	/**
	 * form Open Locationn Master
	 * 
	 * @param model
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/frmLocationMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		clsLocationMasterModel objBean = new clsLocationMasterModel();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql = "select strLocName from tbllocationmaster";
		@SuppressWarnings("rawtypes")
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			String LcoationName = list.get(i).toString();
			data.add(LcoationName.toUpperCase());
		}
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> listType = new ArrayList<>();
		listType.add("Stores");
		listType.add("Cost Center");
		listType.add("Profit Center");
		model.put("listType", listType);
		sql = "SELECT a.strAccountCode ,a.strMasterDesc FROM tbllinkup a " + " where a.strMasterCode='Disc' and a.strClientCode='" + clientCode + "' ;";
		List list2 = objGlobalFunctionsService.funGetList(sql, "sql");
		if (!list2.isEmpty()) {
			Object[] ob = (Object[]) list2.get(0);
			objBean.setStrAcCode(ob[0].toString());
			objBean.setStrAcName(ob[1].toString());
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLocationMaster_1", "command", objBean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLocationMaster", "command", objBean);
		} else {
			return null;
		}

	}

	/**
	 * Save Location Master Data
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveLocationMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsLocationMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			clsLocationMasterModel objModel = funPrepareLocationModel(objBean, userCode, clientCode);
			objLocationMasterService.funAddUpdate(objModel);
			List<clsProductReOrderLevelModel> listProdReOrder = objBean.getListReorderLvl();
			String DeleteSql = "delete from tblreorderlevel where strLocationCode='" + objModel.getStrLocCode() + "' and strClientCode='" + clientCode + "' ";
			objGlobalFunctionsService.funUpdate(DeleteSql, "sql");
			if (null != listProdReOrder) {

				objLocationMasterService.funAddUpdateProductReOrderLevel(listProdReOrder, objModel.getStrLocCode(), clientCode);
			}

			if (objModel.getStrLocCode().length() > 0) {
				clsLinkUpHdModel objlinkUpModel = new clsLinkUpHdModel();

				if (null!=objBean.getStrAcCode() && objBean.getStrAcCode().length() > 0) {
					String delete = " delete from tbllinkup where strMasterCode='Disc' and strClientCode='" + clientCode + "' ";
					objARLinkUpService.funExecute(delete);
					objlinkUpModel.setStrAccountCode(objBean.getStrAcCode());
					objlinkUpModel.setStrMasterDesc(objBean.getStrAcName());
					objlinkUpModel.setStrMasterCode("Disc");
					objlinkUpModel.setStrMasterName("Discount Amount");
					objlinkUpModel.setStrExSuppCode("");
					objlinkUpModel.setStrExSuppName("");
					objlinkUpModel.setStrClientCode(clientCode);
					objlinkUpModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					;
					objlinkUpModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objlinkUpModel.setStrUserCreated(userCode);
					objlinkUpModel.setStrUserEdited(userCode);
					objARLinkUpService.funAddUpdateARLinkUp(objlinkUpModel);
				}

			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Location Code : ".concat(objModel.getStrLocCode()));
			return new ModelAndView("redirect:/frmLocationMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmLocationMaster?saddr=" + urlHits);
		}
	}

	/**
	 * Load Location Master Data
	 * 
	 * @param locCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loadLocationMasterData", method = RequestMethod.GET)
	public @ResponseBody clsLocationMasterModel funAssignFields(@RequestParam("locCode") String locCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);
		if (null == objLocCode) {
			objLocCode = new clsLocationMasterModel();
			objLocCode.setStrLocCode("Invalid Code");
		}

		return objLocCode;
	}

	/**
	 * Load Property Wise Location
	 * 
	 * @param propCode
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadForInventryLocationForProperty", method = RequestMethod.GET)
	public @ResponseBody List funLoadInventryLocation(@RequestParam("propCode") String propCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String usercode = request.getSession().getAttribute("usercode").toString();
		String sql = " ";
		List list = null;
		if (usercode.equalsIgnoreCase("SANGUINE")) {
			sql = "select strLocName,strLocCode from clsLocationMasterModel where strClientCode='" + clientCode + "' " + "and strPropertyCode='" + propCode + "'";
			list = objGlobalFunctionsService.funGetList(sql, "hql");
		} else {
			sql = " select c.strLocName,a.strLocCode from tbluserlocdtl a,tblpropertymaster b,tbllocationmaster c 	" + "	where a.strPropertyCode=b.strPropertyCode  and a.strLocCode=c.strLocCode and a.strPropertyCode='" + propCode + "' " + "	and a.strUserCode='" + usercode + "' and a.strClientCode='" + clientCode + "' group by a.strLocCode  ";
			list = objGlobalFunctionsService.funGetList(sql, "sql");
		}

		return list;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadLocationForProperty", method = RequestMethod.GET)
	public @ResponseBody List funLoadLocation(@RequestParam("propCode") String propCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String usercode = request.getSession().getAttribute("usercode").toString();
		String sql = " ";
		List list = null;

		sql = "select strLocName,strLocCode from clsLocationMasterModel where strClientCode='" + clientCode + "' " + "and strPropertyCode='" + propCode + "'";
		list = objGlobalFunctionsService.funGetList(sql, "hql");

		return list;
	}

	/**
	 * Load All Location for All Property
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadAllLocationForAllProperty", method = RequestMethod.GET)
	public @ResponseBody List<clsLocationMasterModel> funLoadAllLocAllProp(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql = "from clsLocationMasterModel where strClientCode='" + clientCode + "' ";
		List<clsLocationMasterModel> list = objGlobalFunctionsService.funGetList(sql, "hql");
		return list;
	}

	/**
	 * Load Reorder Level Data
	 * 
	 * @param strLocCode
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadReorderLvlMasterData", method = RequestMethod.GET)
	public @ResponseBody List funLoadReorderLvlData(@RequestParam("strLocCode") String strLocCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strProdCode,b.strProdName,a.dblReOrderLevel,a.dblReOrderQty,a.dblPrice from clsProductReOrderLevelModel a,clsProductMasterModel b where a.strProdCode=b.strProdCode and a.strLocationCode='" + strLocCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
		List list = objGlobalFunctionsService.funGetList(sql, "hql");
		return list;
	}

	/**
	 * Perpare Location Master Model
	 * 
	 * @param objLocationBean
	 * @param userCode
	 * @param clientCode
	 * @return
	 */

	private clsLocationMasterModel funPrepareLocationModel(clsLocationMasterBean objLocationBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsLocationMasterModel loc;
		if (objLocationBean.getStrLocCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tbllocationmaster", "LocationeMaster", "intid", clientCode);
			String LocCode = "L" + String.format("%06d", lastNo);
			loc = new clsLocationMasterModel(new clsLocationMasterModel_ID(LocCode, clientCode));
			loc.setIntid(lastNo);
			String monthEndDate = objLocationBean.getStrMonthEnd();
			String[] date = monthEndDate.split("-");
			monthEndDate = date[2] + date[1];
			loc.setStrMonthEnd(monthEndDate);

		} else {
			clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(objLocationBean.getStrLocCode(), clientCode);
			if (null == objLocCode) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tbllocationmaster", "LocationeMaster", "intid", clientCode);
				String LocCode = "L" + String.format("%06d", lastNo);
				loc = new clsLocationMasterModel(new clsLocationMasterModel_ID(LocCode, clientCode));
				loc.setIntid(lastNo);
				String monthEndDate = objLocationBean.getStrMonthEnd();
				String[] date = monthEndDate.split("-");
				monthEndDate = date[2] + date[1];
				loc.setStrMonthEnd(monthEndDate);
			} else {
				loc = new clsLocationMasterModel(new clsLocationMasterModel_ID(objLocationBean.getStrLocCode(), clientCode));
				loc.setStrMonthEnd(objLocCode.getStrMonthEnd());
			}

		}
		loc.setStrLocName(objLocationBean.getStrLocName().toUpperCase());
		loc.setStrLocDesc(objLocationBean.getStrLocDesc());
		loc.setStrAvlForSale(objGlobal.funIfNull(objLocationBean.getStrAvlForSale(), "N", "Y"));
		loc.setStrActive(objGlobal.funIfNull(objLocationBean.getStrActive(), "N", "Y"));
		loc.setStrPickable(objGlobal.funIfNull(objLocationBean.getStrPickable(), "N", "Y"));
		loc.setStrReceivable(objGlobal.funIfNull(objLocationBean.getStrReceivable(), "N", "Y"));
		loc.setStrExciseNo(objLocationBean.getStrExciseNo());
		loc.setStrType(objLocationBean.getStrType());
		loc.setStrPropertyCode(objLocationBean.getStrPropertyCode());
		/*
		 * String monthEndDate=objLocationBean.getStrMonthEnd(); String[] date =
		 * monthEndDate.split("-"); monthEndDate=date[2]+"-" + date[1]+"-" +
		 * date[0]; loc.setStrMonthEnd(monthEndDate);
		 */
		loc.setStrExternalCode(objLocationBean.getStrExternalCode());
		loc.setStrLocPropertyCode(objLocationBean.getStrLocPropertyCode());
		loc.setStrUserCreated(userCode);
		loc.setStrUserModified(userCode);
		loc.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		loc.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		loc.setStrUnderLocCode(objLocationBean.getStrUnderLocCode());
		return loc;
	}

	/**
	 * Location Listing Report
	 * 
	 * @param objBean
	 * @param modelAndView
	 * @param model
	 * @param req
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/rptLocationList", method = RequestMethod.GET)
	public ModelAndView funShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		objGlobal = new clsGlobalFunctions();

		String locCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		objGlobal = new clsGlobalFunctions();
		List<clsLocationMasterModel> locationMasterList = funPrepareLocationList(objLocationMasterService.funGetdtlList(locCode, clientCode));
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(locationMasterList);
		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		return new ModelAndView("locationList" + type.trim().toUpperCase() + "Report", model);

	}

	@SuppressWarnings("rawtypes")
	private List<clsLocationMasterModel> funPrepareLocationList(List listLocation) {
		List<clsLocationMasterModel> LocationList = new ArrayList<clsLocationMasterModel>();
		for (int i = 0; i < listLocation.size(); i++) {
			clsLocationMasterModel loc = (clsLocationMasterModel) listLocation.get(i);
			clsLocationMasterModel objLocList = new clsLocationMasterModel();
			objLocList.setStrLocCode(loc.getStrLocCode());
			objLocList.setStrLocName(loc.getStrLocName());
			objLocList.setStrLocDesc(loc.getStrLocDesc());
			objLocList.setStrAvlForSale(loc.getStrAvlForSale());
			objLocList.setStrActive(loc.getStrActive());
			objLocList.setStrPickable(loc.getStrPickable());
			objLocList.setStrReceivable(loc.getStrReceivable());
			LocationList.add(objLocList);
		}
		return LocationList;
	}

	/**
	 * Open Location list Form
	 * 
	 * @return
	 * @throws JRException
	 */
	@RequestMapping(value = "/frmLocationList", method = RequestMethod.GET)
	public ModelAndView funAttributeListfrom() throws JRException {
		return new ModelAndView("frmLocationList", "command", new clsReportBean());

	}

	/**
	 * Check Duplicate Location Name when Submiting the Data
	 * 
	 * @param Name
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/checklocName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("locName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmLocationMaster");
		return count;

	}

	/**
	 * Open Location Wise Product Slip Form
	 * 
	 * @return
	 * @throws JRException
	 */

	@RequestMapping(value = "/frmLocationWiseProductSlip", method = RequestMethod.GET)
	public ModelAndView funOpenStkAdjSlipForm(Map<String, Object> model, HttpServletRequest request) {
		ModelAndView mv = null;
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			mv = new ModelAndView("frmLocationWiseProductSlip_1", "command", new clsReportBean());

		} else {
			mv = new ModelAndView("frmLocationWiseProductSlip", "command", new clsReportBean());
		}

		mv.addObject("listgroup", mapGroup);
		mv.addObject("listsubGroup", mapSubGroup);

		return mv;

	}

	/**
	 * Call Location Wise Product Slip Report
	 * 
	 * @param objBean
	 * @param resp
	 * @param req
	 */
	@RequestMapping(value = "/rptLocationWiseProductSlip", method = RequestMethod.GET)
	private void funReport(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		funCallReport(objBean, resp, req);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void funCallReport(clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		try {
			String strProdCode = objBean.getStrProdCode();
			String type = objBean.getStrDocType();
			String tempToLoc[] = objBean.getStrToLocCode().split(",");
			String tempSG[] = objBean.getStrSGCode().split(",");
			String strToLocCodes = "";
			String strSGCodes = "";

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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/rptLocationwiseProductSlip.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			String sqlDtlQuery = "select b.strLocName, c.strProdName, c.strReceivedUOM, a.dblReOrderQty, a.dblReOrderLevel " + " from tblreorderlevel a, tbllocationmaster b, tblproductmaster c," + " tblsubgroupmaster d,tblgroupmaster e " + " where a.strLocationCode=b.strLocCode and a.strProdCode=c.strProdCode and " + " a.strClientCode ='" + clientCode + "' and b.strClientCode = '" + clientCode
					+ "' " + " and c.strClientCode = '" + clientCode + "' and  c.strSGCode=d.strSGCode and e.strGCode=d.strGCode ";

			for (int i = 0; i < tempToLoc.length; i++) {
				if (strToLocCodes.length() > 0) {
					strToLocCodes = strToLocCodes + " or a.strLocationCode='" + tempToLoc[i] + "' ";
				} else {
					strToLocCodes = "a.strLocationCode='" + tempToLoc[i] + "' ";

				}
			}

			for (int i = 0; i < tempSG.length; i++) {
				if (strSGCodes.length() > 0) {
					strSGCodes = strSGCodes + " or c.strSGCode='" + tempSG[i] + "' ";
				} else {
					strSGCodes = "c.strSGCode='" + tempSG[i] + "' ";

				}
			}

			if (strProdCode.trim().length() > 0) {
				sqlDtlQuery = sqlDtlQuery + " and a.strProdCode='" + strProdCode + "' ";
			}
			sqlDtlQuery = sqlDtlQuery + " and " + "(" + strToLocCodes + ")" + " and " + "(" + strSGCodes + ") ";
			JasperDesign jd = JRXmlLoader.load(reportName);
			Map<String, JRDataset> datasetMap = jd.getDatasetMap();
			JRDesignQuery newQuery = new JRDesignQuery();
			newQuery.setText(sqlDtlQuery);
			JRDesignDataset subDataset = (JRDesignDataset) datasetMap.get("dslocwdtl");
			subDataset.setQuery(newQuery);

			JasperReport jr = JasperCompileManager.compileReport(jd);

			HashMap hm = new HashMap();
			hm.put("strCompanyName", companyName);
			hm.put("strUserCode", userCode);
			hm.put("strImagePath", imagePath);
			// System.out.println(imagePath);
			hm.put("strAddr1", objSetup.getStrAdd1());
			hm.put("strAddr2", objSetup.getStrAdd2());
			hm.put("strCity", objSetup.getStrCity());
			hm.put("strState", objSetup.getStrState());
			hm.put("strCountry", objSetup.getStrCountry());
			hm.put("strPin", objSetup.getStrPin());
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
				resp.setHeader("Content-Disposition", "attachment;filename=" + "rptMISSlip." + type.trim());
				exporterXLS.exportReport();
				resp.setContentType("application/xlsx");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadLocationPropertyWise", method = RequestMethod.GET)
	public @ResponseBody List<clsLocationMasterModel> funAssignAllLocation(@RequestParam("strProperty") String strProperty, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String PropertyCode = req.getSession().getAttribute("propertyCode").toString();
		List<clsLocationMasterModel> list = objLocationMasterService.funLoadLocationPropertyWise(strProperty, clientCode);
		return list;

	}

}
