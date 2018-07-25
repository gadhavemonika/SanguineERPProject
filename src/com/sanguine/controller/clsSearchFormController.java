package com.sanguine.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.bean.clsFormSearchElements;
import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsSessionMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.webpos.controller.clsPOSGlobalFunctionsController;

@Controller
public class clsSearchFormController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	
	@Autowired
	private intfBaseService objBaseService;

	final static Logger logger = Logger.getLogger(clsSearchFormController.class);
	// Variables For WebMMS
	String showLocWiseProdMaster = "";
	String strMisCode = "";
	String BatchProdCode = "";
	String showPrptyWiseProdDoc = "N";
	String ShowTransAsc_Desc = "";
	String WorkFlowbasedAuth = "";
	String PICode = "";
	String POCode = "";
	String GrnCode = "";
	String NonMoveSlowMovingpropCode = "";
	String strModule = "1";
	private String txtArrivalDate;

	// Variable For Excise
	private String txtFromDate = "";
	private String txtToDate = "";
	String licenceCode = "";
	String strMenuCode = "";
	String roomTypeCode=""; 

	// Variable For CRM
	private String strSubConCode = "";

	@RequestMapping(value = "/searchform", method = RequestMethod.GET)
	public ModelAndView funOpenSearchForm(Map<String, Object> model, @ModelAttribute("formname") String value, BindingResult result, @RequestParam(value = "formname") String formName, @RequestParam(value = "searchText") String search_with, HttpServletRequest req) {
		req.getSession().setAttribute("formName", formName);
		req.getSession().setAttribute("searchText", search_with);

		showLocWiseProdMaster = req.getSession().getAttribute("showLocWiseProdMaster").toString();
		strMisCode = "";
		BatchProdCode = "";
		showPrptyWiseProdDoc = "N";
		ShowTransAsc_Desc = "";

		// Getting Varible From Request For WebPOS
		if (req.getParameter("strMenuCode") != null) {
			strMenuCode = req.getParameter("strMenuCode");
		}

		// Getting Varible From Request For WebExcise
		if (req.getParameter("prodCode") != null) {
			BatchProdCode = req.getParameter("prodCode");
		}
		if (null != req.getParameter("MISCode")) {
			strMisCode = req.getParameter("MISCode");
		}

		WorkFlowbasedAuth = req.getSession().getAttribute("strWorkFlowbasedAuth").toString();

		if (req.getSession().getAttribute("GRNCode") != null) {
			GrnCode = req.getSession().getAttribute("GRNCode").toString();
		}
		if (req.getSession().getAttribute("showPrptyWiseProdDoc") != null) {
			showPrptyWiseProdDoc = req.getSession().getAttribute("showPrptyWiseProdDoc").toString();
		}
		if (req.getSession().getAttribute("ShowTransAsc_Desc") != null) {
			ShowTransAsc_Desc = req.getSession().getAttribute("ShowTransAsc_Desc").toString();
		}

		if (req.getParameter("PICode") != null) {
			PICode = req.getParameter("PICode").toString();
		}

		if (req.getParameter("POCode") != null) {
			POCode = req.getParameter("POCode").toString();
		}
		if (req.getParameter("propCode") != null) {
			NonMoveSlowMovingpropCode = req.getParameter("propCode").toString();
		}

		Map<String, Object> map = null;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			map = funGetSearchDetail(formName, search_with, req);
			strModule = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {

			// Getting Varible From Request For WebExcise

			if (req.getParameter("txtFromDate") != null) {
				txtFromDate = req.getParameter("txtFromDate").toString();
				txtFromDate = txtFromDate.split("-")[2] + "-" + txtFromDate.split("-")[1] + "-" + txtFromDate.split("-")[0];
			}

			if (req.getParameter("txtToDate") != null) {
				txtToDate = req.getParameter("txtToDate").toString();
				txtToDate = txtToDate.split("-")[2] + "-" + txtToDate.split("-")[1] + "-" + txtToDate.split("-")[0];
			}
			if (req.getParameter("licenceCode") != null) {
				licenceCode = req.getParameter("licenceCode").toString();

			}

			map = funGetExciseSearchDetail(formName, search_with, req);
			strModule = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			if (req.getParameter("fromDate") != null) {
				txtFromDate = req.getParameter("fromDate").toString();

			}
			if (req.getParameter("toDate") != null) {
				txtToDate = req.getParameter("toDate").toString();

			}
			if (req.getParameter("arrivalDate") != null) {
				txtArrivalDate = req.getParameter("arrivalDate").toString();

			}

			map = funGetPMSSearchDetail(formName, search_with, req);
			strModule = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			map = funGetWebClubSearchDetail(formName, search_with, req);
			strModule = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {

			if (req.getParameter("strSubConCode") != null) {
				strSubConCode = req.getParameter("strSubConCode").toString();
			}

			map = funGetCRMSearchDetail(formName, search_with, req);
			strModule = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			// map = funGetWebPOSSearchDetail( formName, search_with, req);
			strModule = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "8";
		}

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			Map<String, Object> hmSearchData = funGetWebPOSSearchDetail(formName, search_with, req);
			model.put("searchFormTitle", (String) hmSearchData.get("searchFormTitle"));
		} else {
			model.put("searchFormTitle", map.get("searchFormTitle"));
		}
		return new ModelAndView("frmSearch");
	}

	@RequestMapping(value = "/loadSearchColumnNames", method = RequestMethod.GET)
	public @ResponseBody List<String> funColumnNames(HttpServletRequest req) {
		String formName = req.getSession().getAttribute("formName").toString();
		String search_with = req.getSession().getAttribute("searchText").toString();

		Map<String, Object> map = null;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			map = funGetSearchDetail(formName, search_with, req);
			strModule = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			map = funGetExciseSearchDetail(formName, search_with, req);
			strModule = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			map = funGetPMSSearchDetail(formName, search_with, req);
			strModule = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			map = funGetWebClubSearchDetail(formName, search_with, req);
			strModule = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			map = funGetCRMSearchDetail(formName, search_with, req);
			strModule = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			// map = funGetWebPOSSearchDetail( formName, search_with, req);
			strModule = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "8";
		}

		LinkedList<String> columnName = new LinkedList<String>();
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {

			Map<String, Object> hmSearchData = funGetWebPOSSearchDetail(formName, search_with, req);
			String listColumnNames[] = ((String) hmSearchData.get("listColumnNames")).split("\\,");
			for (int i = 0; i < listColumnNames.length; i++) {
				columnName.add(listColumnNames[i]);
			}

		} else {
			String listColumnNames[] = ((String) map.get("listColumnNames")).split("\\,");
			for (int i = 0; i < listColumnNames.length; i++) {
				columnName.add(listColumnNames[i]);
			}
		}
		return columnName;
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@RequestMapping(value = "/searchData", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funSearchFormData(HttpServletRequest req) {
		String formName = req.getSession().getAttribute("formName").toString();
		req.getSession().removeAttribute("formName");
		String search_with = req.getSession().getAttribute("searchText").toString();
		req.getSession().removeAttribute("searchText");
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		LinkedHashMap resMap = new LinkedHashMap();
		Map<String, Object> model = funGetSearchData(formName, search_with, req);

		List list = (List) model.get("listRecords");
		LinkedList data = new LinkedList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				LinkedList ls = new LinkedList();
				clsFormSearchElements objModel = (clsFormSearchElements) list.get(i);

				ls.add(objModel.getField1() != null ? objModel.getField1() : "");
				ls.add(objModel.getField2() != null ? objModel.getField2() : "");
				ls.add(objModel.getField3() != null ? objModel.getField3() : "");
				ls.add(objModel.getField4() != null ? objModel.getField4() : "");
				ls.add(objModel.getField5() != null ? objModel.getField5() : "");
				ls.add(objModel.getField6() != null ? objModel.getField6() : "");
				ls.add(objModel.getField7() != null ? objModel.getField7() : "");
				ls.add(objModel.getField8() != null ? objModel.getField8() : "");
				ls.add(objModel.getField9() != null ? objModel.getField9() : "");
				ls.add(objModel.getField10() != null ? objModel.getField10() : "");
				ls.add(objModel.getField11() != null ? objModel.getField11() : "");
				ls.add(objModel.getField12() != null ? objModel.getField12() : "");

				ls.add(objModel.getField13() != null ? objModel.getField13() : "");
				ls.add(objModel.getField14() != null ? objModel.getField14() : "");
				ls.add(objModel.getField15() != null ? objModel.getField15() : "");
				ls.add(objModel.getField16() != null ? objModel.getField16() : "");
				ls.add(objModel.getField17() != null ? objModel.getField17() : "");
				ls.add(objModel.getField18() != null ? objModel.getField18() : "");
				ls.add(objModel.getField19() != null ? objModel.getField19() : "");

				ls.removeAll(Collections.singleton(null));
				data.add(ls);
			}
		}
		resMap.put("data", data);
		return resMap;
	}

	// Old Searching Functionality Now Unused
	/*
	 * @RequestMapping(value = "/advSearchform", method = RequestMethod.GET)
	 * public @ResponseBody Map<String, Object> funOpenAdvSearchForm(
	 * 
	 * @ModelAttribute("formname") String value, BindingResult result,
	 * 
	 * @RequestParam(value = "formname") String formName,
	 * 
	 * @RequestParam(value = "searchText") String search_with,
	 * HttpServletRequest req) { Map<String, Object> model =
	 * funGetSearchData(formName, search_with, req); return model; }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> funGetSearchData(String formName, String search_with, HttpServletRequest req) {
		boolean flgQuerySelection = false;
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String searchFormName = "";
		List<String> listColumnNames;

		String multiDocCodeSelection = "No";

		Map<String, Object> map = null;
		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("1-WebStocks")) {
			map = funGetSearchDetail(formName, search_with, req);
			strModule = "1";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("2-WebExcise")) {
			map = funGetExciseSearchDetail(formName, search_with, req);
			strModule = "2";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			map = funGetPMSSearchDetail(formName, search_with, req);
			strModule = "3";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("4-WebClub")) {
			map = funGetWebClubSearchDetail(formName, search_with, req);
			strModule = "4";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("5-WebBookAR")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "5";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("6-WebCRM")) {
			map = funGetCRMSearchDetail(formName, search_with, req);
			strModule = "6";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {

			strModule = "7";
		} else if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("8-WebBookAPGL")) {
			map = funGetWebBookSearchDetail(formName, search_with, req);
			strModule = "8";
		}

		Map<String, Object> model = new HashMap<String, Object>();

		if (req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			Map<String, Object> hmSearchData = funGetWebPOSSearchDetail(formName, search_with, req);

			String tempColmn = (String) hmSearchData.get("listColumnNames");
			searchFormName = (String) hmSearchData.get("searchFormTitle");
			List<String> list = (List) hmSearchData.get("listSearchData");

			listColumnNames = new ArrayList(Arrays.asList(tempColmn.split(",")));
			model.put("listColumns", listColumnNames);
			model.put("listRecords", funSetFormSearchElements(list));
			model.put("searchFormName", searchFormName);
			model.put("multipleSelection", multiDocCodeSelection);
		}
		if (formName.contains("Web-Service")) {
			Map<String, Object> hmSearchData = funGetSearchDetail(formName, search_with, req);

			String tempColmn = (String) hmSearchData.get("listColumnNames");
			searchFormName = (String) hmSearchData.get("searchFormTitle");
			List<String> list = (List) hmSearchData.get("listSearchData");

			listColumnNames = new ArrayList(Arrays.asList(tempColmn.split(",")));
			model.put("listColumns", listColumnNames);
			model.put("listRecords", funSetFormSearchElements(list));
			model.put("searchFormName", searchFormName);
			model.put("multipleSelection", multiDocCodeSelection);
		} else if (!formName.contains("Web-Service") && !req.getSession().getAttribute("selectedModuleName").toString().equalsIgnoreCase("7-WebPOS")) {
			flgQuerySelection = Boolean.parseBoolean((String) map.get("flgQuerySelection"));
			columnNames = (String) map.get("columnNames");
			tableName = (String) map.get("tableName");
			searchFormName = formName;
			criteria = (String) map.get("criteria");
			String tempColmn = (String) map.get("listColumnNames");
			listColumnNames = new ArrayList(Arrays.asList(tempColmn.split(",")));
			String query = "";
			model.put("listColumns", listColumnNames);

			if (flgQuerySelection) {

				if (tableName.contains("union")) {
					query = "select " + columnNames + " FROM " + tableName;
				} else {
					if (tableName.trim().startsWith("from")) {
						query = "select " + columnNames + " " + tableName;
					} else {
						query = tableName;
					}
				}

				String grpBy = "";
				String orderBy = "";
				// if(tableName.contains("group by"))
				// {
				// StringBuilder sb=new StringBuilder(tableName);
				// int ind=sb.indexOf("group by");
				// query=sb.substring(0,ind).toString();
				// grpBy=sb.substring(ind,sb.length()).toString();
				// }
				//
				// if(tableName.contains("order by"))
				// {
				// StringBuilder sb=new StringBuilder(tableName);
				// int ind=sb.indexOf("order by");
				// query=sb.substring(0,ind).toString();
				// orderBy=sb.substring(ind,sb.length()).toString();
				// }

				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				// query=query+" "+ criteria+" "+grpBy+" "+orderBy;
				query = query + " " + criteria;
				List list = objGlobalFunctionsService.funGetDataList(query, "sql");
				model.put("listRecords", funSetFormSearchElements(list));
				flgQuerySelection = false;
			} else {
				String grpBy = "";
				String orderBy = "";
				if (tableName.contains("group by")) {
					StringBuilder sb = new StringBuilder(tableName);
					int ind = sb.indexOf("group by");
					query = sb.substring(0, ind).toString();
					grpBy = sb.substring(ind, sb.length()).toString();
					tableName = sb.delete(ind, sb.length()).toString();
				}

				if (tableName.contains("order by")) {
					StringBuilder sb = new StringBuilder(tableName);
					int ind = sb.indexOf("order by");
					query = sb.substring(0, ind).toString();
					orderBy = sb.substring(ind, sb.length()).toString();
					tableName = sb.delete(ind, sb.length()).toString();
				}

				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				criteria = " " + criteria + " " + grpBy + " " + orderBy;
				query = "select new com.sanguine.bean.clsFormSearchElements(" + columnNames + ") from " + tableName + criteria;
				List list = objGlobalFunctionsService.funGetDataList(query, "hql");
				model.put("listRecords", list);
				model.put("searchFormName", searchFormName);
				model.put("multipleSelection", multiDocCodeSelection);
			}
		}

		return model;
	}

	/*
	 * Start WebStocks Search
	 */
	@SuppressWarnings("finally")
	private Map<String, Object> funGetSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();

		try {
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String strLocCode = req.getSession().getAttribute("locationCode").toString();
			String financialYear = req.getSession().getAttribute("financialYear").toString();
			String[] finYear = financialYear.split("-");
			String showAllProd = "Y";
			if (null == req.getSession().getAttribute("showAllProdToAllLoc")) {
				showAllProd = "N";
			} else {
				showAllProd = "Y";
			}
			String columnNames = "";
			String tableName = "";
			String criteria = "";
			String listColumnNames = "";
			String multiDocCodeSelection = "No";
			boolean flgQuerySelection = false;
			String idColumnName = "";
			String searchFormTitle = "";
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
			String strShowTransOrder = objSetup.getStrShowTransAsc_Desc();
			JSONArray jArrSearchList = null;
			List<Object[]> listSearchData = new ArrayList<Object[]>();
			switch (formName) {

			case "BrandMasterWeb-Service": {
				listColumnNames = "Brand Code , Brand Name ";
				searchFormTitle = "Brand Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "exciseSupplierWeb-Service": {
				listColumnNames = "Supplier Code , Supplier Name ";
				searchFormTitle = "Supplier Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "SundryDebtorWeb-Service": {
				listColumnNames = "Debtor Code , Debtor Name ";
				searchFormTitle = "Debtor Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "SundryCreditorWeb-Service": {
				listColumnNames = "Creditor Code , Creditor Name ";
				searchFormTitle = "Creditor Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "AccountMasterWeb-Service": {
				listColumnNames = "Account Code , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "AccountMasterGLOnlyWeb-Service": {
				listColumnNames = "Account Code , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "suppLinkedWeb-Service": {
				listColumnNames = "Supplier Code , Supplier Name ,Account Code, Account Name ";
				searchFormTitle = "Linked Supplier to Account";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "TaxWeb-Service": {
				listColumnNames = "Account Code , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "DiscountWeb-Service": {
				listColumnNames = "Sr No , Discount Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "RoundOffWeb-Service": {
				listColumnNames = "Sr No  , Discount Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "ExtraChargeWeb-Service": {
				listColumnNames = " SrNo. , Discount Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "opstock": {
				columnNames = "a.strOpStkCode,b.strLocName,a.dtExpDate,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsInitialInventoryModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "'";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = tableName + " order by a.strOpStkCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "OpStock Code,Location,Expiry Date,User Created,Date Created";
				idColumnName = "strOpStkCode";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Opening Stock";
				break;
			}

			case "group": {
				columnNames = "strGCode,strGName,strGDesc";
				tableName = "clsGroupMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Group Code,Group Name,Group Desc";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strGCode";
				searchFormTitle = "Group Master";
				break;
			}

			case "tc": {
				columnNames = "strTCCode,strTCName,strApplicable";
				tableName = "clsTCMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "TC Code,TC Name,Applicable";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strTCCode";
				searchFormTitle = "T C Master";
				break;
			}

			case "locby": {
				columnNames = "a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.propertyName";
				tableName = "clsLocationMasterModel a,clsPropertyMaster b  where b.strPropertyCode=a.strPropertyCode" + "   and a.strActive='Y' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'";
				listColumnNames = "Location Code,Location Name,Location Desc,Location Type,Property Name";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				break;
			}

			case "locon": {
				columnNames = "a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName";
				tableName = "tbllocationmaster a,tblpropertymaster b  where b.strPropertyCode=a.strPropertyCode " + "  and a.strActive='Y' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " union " + " select a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName " + " from tbllocationmaster a,tblpropertymaster b,tbllinklocotherproploc c "
						+ " where c.strPropertyCode='" + propCode + "'  and  b.strPropertyCode=c.strPropertyCode  and c.strLocCode='" + search_with + "' and a.strLocCode=c.strToLoc " + " and c.strClientCode='" + clientCode + "' ";
				listColumnNames = "Location Code,Location Name,Location Desc,Location Type,Property Name";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				flgQuerySelection = true;
				break;
			}

			case "StoreLocationTo": {
				columnNames = "a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName";
				tableName = "  tbllocationmaster a,tblpropertymaster b where b.strPropertyCode=a.strPropertyCode " + " and a.strPropertyCode='" + propCode + "' and  a.strType='Stores' and a.strActive='Y' " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " union " + " select a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName "
						+ " from tbllocationmaster a,tblpropertymaster b,tbllinklocotherproploc c " + " where c.strPropertyCode='" + propCode + "'  and  b.strPropertyCode=c.strPropertyCode  and c.strLocCode='" + search_with + "' and a.strLocCode=c.strToLoc " + " and c.strClientCode='" + clientCode + "' ";

				listColumnNames = "Location Code,Location Name,Location Desc,Location Type,Property Name";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				flgQuerySelection = true;
				break;
			}

			case "LocationToAllPropertyStore": {
				columnNames = "a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName";
				tableName = "tbllocationmaster a,tblpropertymaster b  where a.strPropertyCode=b.strPropertyCode and " + " a.strType='Stores' and a.strActive='Y' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " union " + " select a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.strPropertyName "
						+ " from tbllocationmaster a,tblpropertymaster b,tbllinklocotherproploc c " + " where c.strPropertyCode='" + propCode + "'  and  b.strPropertyCode=c.strPropertyCode  and c.strLocCode='" + search_with + "' and a.strLocCode=c.strToLoc " + " and c.strClientCode='" + clientCode + "' ";
				listColumnNames = "Location Code,Location Name,Location Desc,Location Type,Property Name";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				flgQuerySelection = true;
				break;
			}

			case "PropertyWiseLocation": {
				columnNames = "a.strLocCode,a.strLocName,a.strLocDesc,a.strType,b.propertyName";
				tableName = "clsLocationMasterModel a,clsPropertyMaster b  where a.strPropertyCode=b.strPropertyCode and " + "  a.strActive='Y'  and a.strPropertyCode='" + NonMoveSlowMovingpropCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
				listColumnNames = "Location Code,Location Name,Location Desc,Location Type,Property Name";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				break;
			}

			case "taxmaster": {
				columnNames = "strTaxCode,strTaxDesc,strTaxIndicator,strExternalCode";
				tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
				listColumnNames = "Tax Code,Tax Desc,Tax Indicator,External Code";
				idColumnName = "strTaxCode";
				searchFormTitle = "Tax Master";
				break;
			}

			case "attributevaluemaster": {
				columnNames = "strAVCode,strAVName";
				tableName = "clsAttributeValueMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Att Value Code,Att Name";
				idColumnName = "strAVCode";
				searchFormTitle = "Attribute Value Master";
				break;
			}

			case "attributemaster": {
				columnNames = "strAttCode,strAttName,strAttType";
				tableName = "clsAttributeMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Attribute Code,Attribute Name,Attribute Type";
				idColumnName = "strAttCode";
				searchFormTitle = "Attribute Master";
				break;
			}

			case "subgroup": {
				columnNames = "strSGCode,strSGName,intSortingNo";
				tableName = "select strSGCode,strSGName,intSortingNo from tblsubgroupmaster where strClientCode='" + clientCode + "' ";
				listColumnNames = "Sub-Group Code,Sub-Group Name,Sorting No";
				idColumnName = "strSGCode";
				searchFormTitle = "Sub Group Master";
				flgQuerySelection = true;
				break;
			}

			case "locationmaster": {
				columnNames = "strLocCode,strLocName,strType,strLocDesc,strExternalCode";
				tableName = "clsLocationMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Location Code,Location Name,Type,Description,External Code";
				idColumnName = "strLocCode";
				searchFormTitle = "Location Master";
				break;
			}

			case "productmaster": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a , clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + " where  ";
				}

				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}
			case "productmasterStkable": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a , clsSubGroupMasterModel c " + ", clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and  b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + " where  ";
				}

				tableName = tableName + "   a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strNonStockableItem='N' and a.strNotInUse='N' " + " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'";

				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}
			case "prodforPI": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strBarCode," + " a.strNonStockableItem,e.strPIcode,e.dblQty ";
				tableName = " select a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strBarCode," + " a.strNonStockableItem,e.strPIcode,e.dblQty " + " from tblproductmaster a,tblreorderlevel b , tblsubgroupmaster c ," + " tblgroupmaster d ,tblpurchaseindenddtl e" + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' "
						+ " and e.strProdCode=a.strProdCode and e.strPIcode='" + PICode + "' and a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'" + " and d.strClientCode='" + clientCode + "' and e.strClientCode='" + clientCode + "' ";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Code,Non Stockable,PICode,PIQty ";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				flgQuerySelection = true;
				break;
			}
			case "prodforPO": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strBarCode," + " a.strNonStockableItem,e.strPOCode,e.dblOrdQty ";
				tableName = " select a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strBarCode," + " a.strNonStockableItem,e.strPOCode,e.dblOrdQty " + " from tblproductmaster a,tblreorderlevel b , tblsubgroupmaster c ," + " tblgroupmaster d ,tblpurchaseorderdtl e" + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' "
						+ " and e.strProdCode=a.strProdCode and e.strPOCode='" + POCode + "'  and a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'" + " and d.strClientCode='" + clientCode + "' and e.strClientCode='" + clientCode + "' ";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Code,Non Stockable,POCode,POQty ";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				flgQuerySelection = true;
				break;
			}

			case "prodmasterPropwise": {
				if (!showLocWiseProdMaster.equalsIgnoreCase("N")) {
					columnNames = "a.strProdCode,a.strProdName,a.strUOM,a.strProdType" + ", a.strSpecification,a.strBarCode,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strProdNameMarathi,a.strPartNo ";
					tableName = "clsProductMasterModel a" + " where (a.strLocCode='" + strLocCode + "' or a.strLocCode='') " + " and a.strClientCode='" + clientCode + "'";

				} else {
					columnNames = "a.strProdCode,a.strProdName,a.strUOM,a.strProdType" + ",a.strSpecification,a.strBarCode,a.strCalAmtOn,a.strClass,a.strNonStockableItem ,a.strProdNameMarathi,a.strPartNo";
					tableName = "clsProductMasterModel a " + " where  a.strClientCode='" + clientCode + "' ";
				}
				listColumnNames = "Product Code,Product Name,UOM,Product Type,Specification,Code,Cal Amt On" + ",Class,Non Stockable,Marathi,strPartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "childcode": {
				columnNames = "strProdCode,strPartNo,strProdName,strProdType";
				tableName = "clsProductMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,POS Item Code,Product Name,Product Type";
				idColumnName = "strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "property": {
				columnNames = "strPropertyCode,propertyName ";
				tableName = "clsPropertyMaster where strClientCode='" + clientCode + "'";
				listColumnNames = "Property Code,Property Name";
				idColumnName = "strPropertyCode";
				searchFormTitle = "Property Master";
				break;
			}

			case "usermaster": {
				columnNames = "strUserCode,strUserName";
				tableName = "clsUserMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "User Code,User Name";
				idColumnName = "strUserCode";
				searchFormTitle = "User Master";
				break;
			}

			case "reason": {
				columnNames = "strReasonCode,strReasonName,strReasonDesc";
				tableName = "clsReasonMaster where strClientCode='" + clientCode + "'";
				listColumnNames = "Reason Code,Reason Name,Reason Description";
				idColumnName = "strReasonCode";
				searchFormTitle = "Reason Master";
				break;
			}

			case "charCode": {
				columnNames = "strCharCode,strCharName,strCharType,strCharDesc";
				tableName = "clsCharacteristicsMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Characteristics Code,Characteristics Name,Characteristics Type,Characteristics Desc";
				idColumnName = "strCharCode";
				searchFormTitle = "Characteristics Master";
				break;
			}

			case "processcode": {
				columnNames = "strProcessCode,strProcessName,strDesc";
				tableName = "clsProcessMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Process Code,Process Name,Description";
				idColumnName = "strProcessCode";
				searchFormTitle = "Process Master";
				break;
			}

			case "bomcode": {
				columnNames = "a.strBOMCode,a.strParentCode,b.strProdName,a.strProcessCode,b.strPartNo ";
				tableName = "clsBomHdModel a,clsProductMasterModel b where a.strParentCode=b.strProdCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and a.strBOMType='R' ";
				listColumnNames = "BOM Code,Parent Code,Product Name,Process Name,PartNo";
				idColumnName = "strBOMCode";

				String strIndustryType = "";
				List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
				if (listClsCompanyMasterModel.size() > 0) {
					clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);
					strIndustryType = objCompanyMasterModel.getStrIndustryType();
				}
				if (strIndustryType.equalsIgnoreCase("Manufacture")) {
					searchFormTitle = "BOM Master";
				} else {
					searchFormTitle = "Recipe Master";
				}

				break;
			}

			case "suppcode": {
				columnNames = "strPCode,strPName,strMobile,strEmail,strContact,strExternalCode";
				if (objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
					tableName = "clsSupplierMasterModel where strClientCode='" + clientCode + "' and strPType ='Supp' or strPType='' ";
					listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person,External Code";
					idColumnName = "strPCode";
				} else {
					tableName = "clsSupplierMasterModel where strPropCode='" + propCode + "' and strClientCode='" + clientCode + "' and strPType ='Supp' or strPType='' ";
					listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person,External Code";
					idColumnName = "strPCode";
				}
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Supplier Master";
				break;
			}
			case "suppcodeActive": {
				columnNames = "strPCode,strPName,strMobile,strEmail,strContact";
				if (objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
					tableName = "clsSupplierMasterModel where strClientCode='" + clientCode + "' and ( strPType ='Supp' or strPType='' ) and (  strOperational='Y' or strOperational='' ) ";
					listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person";
					idColumnName = "strPCode";
				} else {
					tableName = "clsSupplierMasterModel where strPropCode='" + propCode + "' and strClientCode='" + clientCode + "' and ( strPType ='Supp' or strPType='' ) and (  strOperational='Y' or strOperational='' ) ";
					listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person";
					idColumnName = "strPCode";
				}
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Supplier Master";
				break;
			}

			case "MIS": {
				columnNames = "a.strMISCode,a.strAgainst,b.strLocName ,c.strLocName,a.dtMISDate" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsMISHdModel a,clsLocationMasterModel b ,clsLocationMasterModel c" + " where a.strLocFrom=b.strLocCode and a.strLocTo=c.strLocCode " + " and a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "' and  c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtMISDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}

				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("MIS")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					sqlforNoraml += " and a.strAuthorise='No'";
				}
				sqlforNoraml = sqlforNoraml + " order by a.strMISCode " + ShowTransAsc_Desc + " ";
				tableName = sqlforNoraml;
				listColumnNames = "MIS Code,Against,Loc From,Loc To,MIS Date,User Created,Date Created,Narration";
				idColumnName = "strMISCode";
				searchFormTitle = "Material Issue Slip";
				break;
			}

			case "ratecontno": {
				columnNames = "strRateContNo,dtRateContDate,strSuppCode,strUserCreated,dtCreatedDate";
				tableName = "clsRateContractHdModel where strClientCode='" + clientCode + "'";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Rate Contract")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				tableName = tableName + " order by strRateContNo " + ShowTransAsc_Desc + " ";
				listColumnNames = "Rate Contract No,Rate Contract Date,Supplier Code,User Created,Date Created";
				idColumnName = "strRateContNo";
				searchFormTitle = "Rate Contractor";
				break;
			}

			case "stkpostcode": {
				columnNames = "a.strPSCode,a.dtPSDate,b.strLocName,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkPostingHdModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPSDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}

				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Physical Stock Posting")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strPSCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Physical Stk Code,Physical Stk Date,Location,User Created,Date Created";
				idColumnName = "strPSCode";
				searchFormTitle = "Physical Stock Posting";
				break;
			}

			case "stkadjcode": {
				columnNames = "a.strSACode,a.dtSADate,b.strLocName,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkAdjustmentHdModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtSADate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Stock Adjustment")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strSACode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Stk Adjustment Code,Stk Adjustment Date,Location,User Created,Date Created";
				idColumnName = "strSACode";
				searchFormTitle = "Stock Adjustment";
				break;
			}

			case "MaterialReq": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = sqlforNoraml;
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Material Req")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Material Requisition";

				break;
			}

			case "MaterialReqSlip": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}

				tableName += " and a.strAuthorise='No'";

				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Material Requisition";

				break;
			}

			case "MaterialReqDelete": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = sqlforNoraml;

				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Material Requisition";

				break;
			}

			case "Production": {
				columnNames = "strPDCode,dtPDDate,strLocCode,strNarration,strWOCode,strAuthorise,strUserCreated,dtCreatedDate ";
				tableName = "clsProductionHdModel where strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM dtPDDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strPDCode like '" + propertyCode + "%' ";
				}

				listColumnNames = "Production Code,Date,Location,Narration,Work Order Code,Authorise,User Created,Date Created ";
				idColumnName = "strPDCode";
				searchFormTitle = "Production";
				break;
			}

			case "ProductionWorkOrder": {
				columnNames = "strWOCode,dtWODate,strSOCode,strStatus,strAuthorise,strUserCreated,dtDateCreated";
				tableName = "clsWorkOrderHdModel where strStatus!='Completed' and strClientCode='" + clientCode + "'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strWOCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "WorkOrder Code,Date,So Code,Status,Authorise,User Created,Date Created ";
				idColumnName = "strWOCode";
				searchFormTitle = "Work Order";
				break;
			}

			case "WorkOrder": {
				columnNames = "strWOCode,dtWODate,strSOCode,strStatus,strAuthorise,strUserCreated,dtDateCreated";
				tableName = "clsWorkOrderHdModel where strClientCode='" + clientCode + "'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strWOCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "WorkOrder Code,Date,So Code,Status,Authorise,User Created,Date Created ";
				idColumnName = "strWOCode";
				searchFormTitle = "Work Order";
				break;
			}

			case "stktransfercode": {
				columnNames = "a.strSTCode,a.dtSTDate,b.strLocName,c.strLocName,a.strNarration,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkTransferHdModel a,clsLocationMasterModel b,clsLocationMasterModel c" + " where a.strFromLocCode=b.strLocCode and a.strToLocCode=c.strLocCode and" + " a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtSTDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Stock Transfer")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strSTCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by strSTCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "ST Code,ST Date,From Location,To Location,Narration,User Created,Date Created";
				idColumnName = "strSTCode";
				searchFormTitle = "Stock Tranfer";
				break;
			}

			case "grncode": {

				columnNames = "a.strGRNCode,a.dtGRNDate,c.strLocName,a.strAgainst,a.strPONo,b.strPName,a.strBillNo" + ",a.strPayMode,a.strUserCreated,a.dtDateCreated,a.strNarration,a.strRefNo";
				tableName = "clsGRNHdModel a,clsSupplierMasterModel b ,clsLocationMasterModel c " + "where a.strSuppCode=b.strPCode and a.strLocCode=c.strLocCode  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtGRNDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + " and c.strPropertyCode='" + propCode + "' ";
				}
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("GRN")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strGRNCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "GRN Code,GRN Date,Location,Against,POCode,Supplier Name,GRN No," + "Pay Mode,User Created,Date Created,Narration,Ref No";
				idColumnName = "strGRNCode";
				searchFormTitle = "GRN";
				break;
			}
			
			case "grncodeforprint": {

				columnNames = "a.strGRNCode,a.dtGRNDate,c.strLocName,a.strAgainst,a.strPONo,b.strPName,a.strBillNo" + ",a.strPayMode,a.strUserCreated,a.dtDateCreated,a.strNarration,a.strRefNo";
				tableName = "clsGRNHdModel a,clsSupplierMasterModel b ,clsLocationMasterModel c " + "where a.strSuppCode=b.strPCode and a.strLocCode=c.strLocCode  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtGRNDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + " and c.strPropertyCode='" + propCode + "' ";
				}
				
				tableName = tableName + " order by a.strGRNCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "GRN Code,GRN Date,Location,Against,POCode,Supplier Name,GRN No," + "Pay Mode,User Created,Date Created,Narration,Ref No";
				idColumnName = "strGRNCode";
				searchFormTitle = "GRN";
				break;
			}

			case "POCodeAgainstGRN": {
				columnNames = "strPOCode,dtPODate,strPayMode,strUserCreated,dtDateCreated";
				tableName = "select strPOCode,dtPODate,strAgainst,strUserCreated,dtDateCreated " + "from tblpurchaseorderhd Where strPOCode IN " + "(select distinct a.strPOCode from tblpurchaseorderdtl a left outer join vGRNPODtl b " + "on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode and b.strClientCode='" + clientCode + "'"
						+ "where  a.dblOrdQty > ifnull(b.POQty,0) and a.strClientCode='" + clientCode + "') " + "and strClosePO != 'Yes' and strAuthorise='Yes' and strClientCode='" + clientCode + "'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPOCode like '" + propertyCode + "%' ";
				}

				listColumnNames = "PO Code,PO Date,Pay Mode,User Created,Date Created";
				idColumnName = "strPOCode";
				flgQuerySelection = true;
				searchFormTitle = "Purchase Order";
				break;
			}

			case "PRCodeAgainstGRN": {
				columnNames = "strPRCode,dtPRDate,strAgainst,strUserCreated,dtDateCreated";
				tableName = "clsPurchaseReturnHdModel where strAuthorise='Yes' and  strClientCode='" + clientCode + "'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strPRCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "PR Code,PR Date,Against,User Created,Date Created";
				idColumnName = "strPRCode";
				searchFormTitle = "GRN";
				break;
			}

			case "SRCodeAgainstGRN": {
				columnNames = "strPRCode,dtPRDate,strAgainst,strUserCreated,dtDateCreated";
				tableName = "clsPurchaseOrderHdModel where strAuthorise='Yes' and  strClientCode='" + clientCode + "'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strPRCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "PR Code,PR Date,Against,User Created,Date Created";
				idColumnName = "strPRCode";
				searchFormTitle = "GRN";
				break;
			}

			case "ShortSupplyAgainstGRN": {
				columnNames = "strPOCode,dtPODate,strAgainst";
				tableName = "select strPOCode,dtPODate,strAgainst from tblpurchaseorderhd Where strPOCode IN " + "(select distinct a.strPOCode from tblpurchaseorderdtl a left outer join vGRNPODtl b " + "on a.strPOCode = b.POCode and a.strProdCode = b.strProdCode " + "where  a.dblOrdQty > b.POQty) " + "and strClosePO = 'Yes' and strAuthorise='Yes'";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strPOCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "PO Code,PO Date,Against";
				idColumnName = "strPOCode";
				searchFormTitle = "";
				break;
			}

			case "ProductionOrder": {
				columnNames = "strOPCode,dtOPDate,strStatus,dtFulmtDate,dtfulfilled,strNarration,strUserCreated,dtDateCreated";
				tableName = " from tblproductionorderhd where strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM dtOPDate) between '" + finYear[0] + "' and '" + finYear[1] + "' " + " and strOPCode not in ( select strSOcode from tblworkorderhd where strClientCode='" + clientCode + "' ) ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Production Order")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strOPCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "Production Order Code,Production Order Date,Fulfilment Date,Fulfilled Date,Narration,User Created,Date Created";
				idColumnName = "strOPCode";
				flgQuerySelection = true;
				searchFormTitle = "Production Order";
				break;
			}

			case "OPCodeForWO": {
				columnNames = "strOPCode,dtOPDate,strStatus,dtFulmtDate,dtfulfilled,strNarration,strUserCreated,dtDateCreated";
				tableName = "clsProductionOrderHdModel where strClientCode='" + clientCode + "' and strStatus = 'N'  " + " and EXTRACT(YEAR FROM dtOPDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Production Order")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strOPCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "Production Order Code,Production Order Date,Fulfilment Date,Fulfilled Date,Narration,User Created,Date Created";
				idColumnName = "strOPCode";
				searchFormTitle = "Production Order";
				break;
			}

			case "BillPassing": {
				columnNames = "strBillPassNo,dtBillDate,strPVno,strNarration,strUserCreated,dtDateCreated";
				tableName = "clsBillPassHdModel where strClientCode='" + clientCode + "'";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Bill Passing")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				tableName = tableName + " order by strBillPassNo " + ShowTransAsc_Desc + " ";
				listColumnNames = "Bill Pass No,Bill Date,Purchase Voucher No,Narration,User Created,Date Created";
				idColumnName = "strBillPassNo";
				searchFormTitle = "Bill Passing";
				break;
			}

			case "PICode": {
				columnNames = "a.strPICode,a.dtPIDate,b.strLocName,a.strNarration,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsPurchaseIndentHdModel a,clsLocationMasterModel b " + " where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and a.strPICode not in (select strSOCode from clsPurchaseOrderHdModel) ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + " and b.strPropertyCode='" + propCode + "' ";
				}
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Purchase Indent")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				tableName = tableName + " order by a.strPICode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PI Code,PI Date,Location,Narration,User Created,Date Created";
				idColumnName = "strPICode";
				multiDocCodeSelection = "Yes";
				searchFormTitle = "Purchase Indent";
				break;
			}

			case "purchaseorder": {
				columnNames = "a.strPOCode,a.dtPODate,b.strPName,a.strAgainst,a.strPayMode,a.dtPayDate" + ",a.strUserCreated,a.dtDateCreated,a.strClosePO";
				tableName = "clsPurchaseOrderHdModel a,clsSupplierMasterModel b " + "where a.strSuppCode=b.strPCode and a.strPOCode not in " + "(select strPONo from clsGRNHdModel) " + "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPODate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Purchase Order")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPOCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by a.strPOCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PO Code,PO Date,Supplier Code,Against,Payment Mode,Payment Date,User Created" + ",Date Created,Close PO";
				idColumnName = "strPOCode";
				searchFormTitle = "Purchase Order";
				break;
			}
			
			case "purchaseorderforprint": {
				columnNames = "a.strPOCode,a.dtPODate,b.strPName,a.strAgainst,a.strPayMode,a.dtPayDate" + ",a.strUserCreated,a.dtDateCreated,a.strClosePO";
				tableName = "clsPurchaseOrderHdModel a,clsSupplierMasterModel b " + "where a.strSuppCode=b.strPCode and a.strPOCode not in " + "(select strPONo from clsGRNHdModel) " + "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPODate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Purchase Order")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					//tableName += " and a.strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPOCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by a.strPOCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PO Code,PO Date,Supplier Code,Against,Payment Mode,Payment Date,User Created" + ",Date Created,Close PO";
				idColumnName = "strPOCode";
				searchFormTitle = "Purchase Order";
				break;
			}

			case "treeMasterForm": {
				System.out.println(req.getSession().getAttribute("moduleMap"));
				columnNames = "strFormName,strFormDesc";
				tableName = "clsTreeMasterModel where strType='T' ";
				listColumnNames = "Form Name,Form Description";
				idColumnName = "strFormName";
				searchFormTitle = "";
				break;
			}

			case "MaterialReturn": {
				columnNames = "strMRetCode,dtMRetDate,strAgainst,strUserCreated,dtCreatedDate";
				tableName = "clsMaterialReturnHdModel where strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM dtMRetDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Material Return")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strMRetCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by strMRetCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Material Return Code,Material Return Name,Against,User Created,Date Created";
				idColumnName = "strMRetCode";
				searchFormTitle = "Material Return";
				break;
			}
			case "PurchaseReturn": {
				columnNames = "a.strPRCode,a.dtPRDate,a.strAgainst,b.strLocName,a.strNarration,a.strUserCreated,a.dtDateCreated";
				tableName = "select a.strPRCode,a.dtPRDate,a.strAgainst,b.strLocName,a.strNarration,a.strUserCreated,a.dtDateCreated " + " from tblpurchasereturnhd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPRDate) between '" + finYear[0] + "' and '" + finYear[1]
						+ "' ";
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Purchase Return")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPRCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by a.strPRCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PR Code,Date,Against,Location,Narration,User Created,Date Created";
				idColumnName = "strPRCode";
				flgQuerySelection = true;
				searchFormTitle = "Purchase Return";
				break;
			}

			case "ProductRecipe": {
				columnNames = "a.strParentCode,b.strPartNo,b.strProdName ";
				tableName = " clsBomHdModel a,clsProductMasterModel b where a.strParentCode=b.strProdCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,POS Item Code,Product Name";
				idColumnName = "strProdCode";
				searchFormTitle = "Product Master";
				break;
			}
			case "tcForSetup": {
				columnNames = "strTCCode,strTCName,strApplicable";
				tableName = "clsTCMasterModel where strClientCode='" + clientCode + "' and strApplicable='Y'";
				listColumnNames = "TC Code,TC Name,Applicable";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strTCCode";
				searchFormTitle = "T C Master";
				break;
			}
			case "UDCCode": {
				columnNames = "strUDCCode,strUDCName,strUDCDesc";
				tableName = "clsUDReportCategoryMasterModel where strClientCode='" + clientCode + "' ";
				listColumnNames = "UD Report Code,UD Report Name,UD Report Desc";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strUDCCode";
				searchFormTitle = "UD Report Category Master";
				break;
			}

			case "udreportname": {
				columnNames = "strReportCode,strReportDesc,strType";
				tableName = "clsUserDefinedReportModel where strClientCode='" + clientCode + "' ";
				listColumnNames = "UD Report Code,UD Report Name,UD Report Type";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strReportCode";
				searchFormTitle = "User Defined Report";
				break;
			}

			case "UOMmaster": {
				columnNames = "strUOMName";
				tableName = "clsUOMModel where strClientCode='" + clientCode + "' ";
				listColumnNames = "UOM Name";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strUOMName";
				searchFormTitle = "Unit Of Measurment";
				break;
			}

			case "nonindicatortax": {
				columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
				tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxIndicator=''";
				listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
				idColumnName = "strTaxCode";
				searchFormTitle = "Tax Master";
				break;
			}

			case "OpenTaxesForPurchase": {
				columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
				
				/*String showAllTaxesOnTransactions = req.getSession().getAttribute("showAllTaxesOnTransactions").toString();
				if(showAllTaxesOnTransactions.equals("Y"))
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Purchase' and strTaxType='Fixed Amount' ";
				}
				else
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "'  ";
				}*/
				
				tableName = "clsTaxHdModel where strClientCode='" + clientCode + "'  ";
				if(clientCode.equals("226.001"))
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Purchase' and strTaxType='Fixed Amount' ";
				}
				
				listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
				idColumnName = "strTaxCode";
				searchFormTitle = "Tax Master";
				break;
			}
			
			case "OpenTaxesForSales": {
				columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
				/*String showAllTaxesOnTransactions = req.getSession().getAttribute("showAllTaxesOnTransactions").toString();
				if(showAllTaxesOnTransactions.equals("Y"))
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Sales' and strTaxType='Fixed Amount' ";
				}
				else
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "'  ";
				}*/
				
				tableName = "clsTaxHdModel where strClientCode='" + clientCode + "'  ";
				if(clientCode.equals("226.001"))
				{
					tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Sales' and strTaxType='Fixed Amount' ";
				}
				
				listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
				idColumnName = "strTaxCode";
				searchFormTitle = "Tax Master";
				break;
			}
			case "grnproduct": {
				columnNames = "a.strProdCode,b.strProdName";
				tableName = "clsGRNDtlModel a ,clsProductMasterModel b where a.strProdCode=b.strProdCode and " + " a.strGRNCode='" + GrnCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' group by a.strProdCode ";
				listColumnNames = "Product Code,Product Name";
				idColumnName = "a.strProdCode";
				searchFormTitle = "GRN Product List";
				break;
			}
			case "Batch": {
				columnNames = "a.strBatchCode,a.strManuBatchCode,a.strProdCode,b.strProdName,a.dtExpiryDate,a.strTransCode";
				tableName = "clsBatchHdModel a ,clsProductMasterModel b where a.strProdCode=b.strProdCode and " + " a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
				listColumnNames = "Batch Code,Manufacture Batch Code,Product Code,Product Name,Expiry Date,Transaction Code";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Batch List";
				break;
			}

			case "BatchHelpForMIS": {
				columnNames = "a.strProdCode,b.strProdName,a.strRemarks";
				tableName = "select a.strProdCode,b.strProdName,a.strRemarks from tblmisdtl a ,tblproductmaster b where a.strProdCode=b.strProdCode and " + " a.strMISCode='" + strMisCode + "' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
				listColumnNames = "Product Code,Product Name,Remarks";
				idColumnName = "a.strProdCode";
				flgQuerySelection = true;
				searchFormTitle = "Batch Product List";
				break;
			}
			case "ProdBatchCode": {
				columnNames = "a.strBatchCode,a.strManuBatchCode,a.dtExpiryDate,a.strTransCode,a.dblPendingQty";
				tableName = "select a.strBatchCode,a.strManuBatchCode,a.dtExpiryDate,a.strTransCode,a.dblPendingQty " + " from tblbatchhd a where a.strProdCode='" + BatchProdCode + "' and " + " a.strTransType='GRN' and  a.dblPendingQty > 0 and a.strClientCode='" + clientCode + "'  ";
				listColumnNames = "Batch Code,Manufacture Batch Code,Expiry Date,Transaction Code,Pending Qty";
				idColumnName = "a.strProdCode";
				flgQuerySelection = true;
				searchFormTitle = "Batch List";
				break;
			}

			case "yieldcode": {
				columnNames = "a.strBOMCode,a.strParentCode,b.strProdName,a.strProcessCode ";
				tableName = "clsBomHdModel a,clsProductMasterModel b where a.strParentCode=b.strProdCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and a.strBOMType='Y' ";
				listColumnNames = "BOM Code,Parent Code,Product Name,Process Name";
				idColumnName = "strBOMCode";
				searchFormTitle = "Yield Master";
				break;
			}

			case "productInUse": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + "  where   ";
				}
				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strNotInUse='N' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "CountryCode": {
				columnNames = "a.strCountryCode,a.strCountryName,a.strUserCreated,a.dtLastModified ";
				tableName = "clsWSCountryMasterModel a  where a.strClientCode='" + clientCode + "' ";
				listColumnNames = "Country Code,Country Name,User Created,Date Modified ";

				idColumnName = "a.strCountryCode";
				searchFormTitle = "Country Master";
				break;
			}

			case "StateCode": {
				columnNames = "a.strStateCode,a.strStateName,b.strCountryName,a.strUserCreated ";
				tableName = "clsWSStateMasterModel a , clsWSCountryMasterModel b where a.strCountryCode=b.strCountryCode and a.strClientCode='" + clientCode + "' ";
				listColumnNames = "State Code,State Name,Country Name,User Created ";

				idColumnName = "a.strStateCode";
				searchFormTitle = "State Master";
				break;
			}

			case "CityCode": {
				columnNames = "a.strCityCode,a.strCityName,b.strStateName,c.strCountryName ";
				tableName = " clsWSCityMasterModel a ,clsWSStateMasterModel b , clsWSCountryMasterModel c " + " where a.strStateCode=b.strStateCode" + " and a.strCountryCode=c.strCountryCode" + " and a.strClientCode='" + clientCode + "' ";
				listColumnNames = "City Code,City Name,State Name,Country Name ";

				idColumnName = "a.strCityCode";
				searchFormTitle = "City Master";
				break;
			}

			case "VehCode": {
				columnNames = "a.strVehCode,a.strVehNo,a.strUserCreated,a.dtCreatedDate ";
				tableName = " clsVehicleMasterModel a  " + " where  a.strClientCode='" + clientCode + "' ";
				listColumnNames = "Vehicle Code,Vehicle No,Created Name,Created date ";

				idColumnName = "a.strVehCode";
				searchFormTitle = "Vehicle Master";
				break;
			}

			case "RouteCode": {
				columnNames = "a.strRouteCode,a.strRouteName,a.strUserCreated,a.dtCreatedDate ";
				tableName = " clsRouteMasterModel a  " + " where  a.strClientCode='" + clientCode + "' ";
				listColumnNames = "Route Code,Route Name,Created Name,Created date ";

				idColumnName = "a.strRouteCode";
				searchFormTitle = "Route Master";
				break;
			}

			case "productProduced": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + "  where   ";
				}
				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strProdType='Produced' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "exciseDuty": {
				columnNames = "a.strExciseCode,a.strDesc,a.strSGCode,a.strCessTax ";
				tableName = " clsExciseMasterModel a  " + " where  a.strClientCode='" + clientCode + "' ";
				listColumnNames = "Excise Code,Description,Category Code,Cess Tax ";

				idColumnName = "a.strExciseCode";
				searchFormTitle = "Excise Master";
				break;
			}

			case "subgroupExcisable": {
				columnNames = "strSGCode,strSGName";
				tableName = "clsSubGroupMasterModel where strClientCode='" + clientCode + "' and strExciseable='Y' ";
				listColumnNames = "Sub-Group Code,Sub-Group Name";
				idColumnName = "strSGCode";
				searchFormTitle = "Sub Group Master";
				break;
			}

			case "RawProduct": {
				String loctemp = strLocCode;

				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {

					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode  " + "and b.strLocationCode='" + loctemp + "' and  b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + "  where   ";
				}
				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strNotInUse='N' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Specification,Cal Amt On" + ",Class,Non Stockable,a.strPartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "custMaster": {
				columnNames = "strPCode,strPName,strMobile,strEmail,strContact";
				tableName = "clsPartyMasterModel where strPropCode='" + propCode + "'  and  strClientCode='" + clientCode + "' ";
				if (!objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
					tableName += " and strPropCode='" + propertyCode + "' ";
				}

				tableName += " and strPType='cust' ORDER BY strPCode " + strShowTransOrder + " ";
				listColumnNames = "Customer Code,Customer Name,Mobile No,Email,Contact Person";
				idColumnName = "strPCode";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Customer Master";
				break;
			}

			case "transCode": {
				columnNames = "strTransCode,strTransName,strDesc";
				tableName = "clsTransporterHdModel where strClientCode='" + clientCode + "'  ";
				listColumnNames = "Transpoter Code,Transpoter Name,Desc ";
				idColumnName = "strTransCode";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Transpoter Master";
				break;
			}

			case "PendingMaterialReq": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strCloseReq='N'  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = sqlforNoraml;
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Material Req")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Material Requisition";

				break;
			}

			case "productSubContracted": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem";
				tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + "  where   ";
				}
				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strProdType='Sub-Contracted' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "salesorder": {
				// columnNames =
				// "a.strSOCode,a.dteSODate,a.strCustPONo,strCustCode,strLocCode";
				columnNames = "a.strSOCode,a.dteSODate,a.strCustPONo,b.strPName,c.strLocName,a.strStatus ";
				tableName = "clsSalesOrderHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
				listColumnNames = "SO Code,So Date, CustomerPONo,Customer Name,Location Name,Order Type";
				idColumnName = "strSOCode";
				// flgQuerySelection=true;
				searchFormTitle = "Sales Order";
				break;
			}

			case "manufactureMaster": {
				columnNames = "strManufacturerCode,strManufacturerName";
				tableName = "clsManufactureMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Manufacturer Code,Manufacturer Name";
				idColumnName = "strManufacturerCode";
				searchFormTitle = "Manufacture Master";
				break;
			}

			case "CurrencyCode": {
				columnNames = "strCurrencyCode,strCurrencyName,strShortName";
				tableName = "clsCurrencyMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Currency Code,Currency Name,ShortName";
				idColumnName = "strCurrencyCode";
				searchFormTitle = "Currency Master";
				break;
			}
			case "invoice": {

				columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
				tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode " + "and a.strInvCode like '%IV%' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strInvCode like '" + propertyCode + "%' ";
				}

				listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
				idColumnName = "a.strInvCode";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Invoice";
				break;
			}
			
			
			case "dsPOData": {

				columnNames = "a.strPOCode,a.strSuppCode,b.strPName,a.dtPODate";
				tableName = " clsPurchaseOrderHdModel a, clsSupplierMasterModel b " + " where a.strSuppCode=b.strPCode and a.strClientCode=b.strClientCode and a.strClientCode='" + clientCode + "' ";

				listColumnNames = "Po Code,Supplier Code,Supplier Name,PO Date";
				idColumnName = "a.strPOCode";
				searchFormTitle = "Delivery Schedule PO";
				break;
			}

			case "dscode": {

				columnNames = "a.strDSCode,a.dteDSDate,a.strLocCode,b.strLocName";
				tableName = " clsDeliveryScheduleModulehd a, clsLocationMasterModel b " + " where a.strLocCode=b.strLocCode and a.strClientCode=b.strClientCode and a.strClientCode='" + clientCode + "' ";

				listColumnNames = "DS Code,DS Date,Location Code,Location Name";
				idColumnName = "a.strDSCode";
				searchFormTitle = "Delivery Schedule Code";
				break;
			}

			case "dsCodeAgainstGRN": {
				columnNames = " a.strDSCode, a.dteDSDate, a.strLocCode, b.strLocName ";
				tableName = " clsDeliveryScheduleModulehd a, clsLocationMasterModel b " + " where a.strLocCode=b.strLocCode and a.strClientCode=b.strClientCode " + " and a.strClientCode='" + clientCode + "' and a.strCloseDS='N' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and b.strPropertyCode = '" + propertyCode + "' ";
				}

				listColumnNames = "DS Code,DS Date,Location Code,Location Name";
				idColumnName = "a.strDSCode";
				searchFormTitle = "DS Against GRN";
				break;
			}

			case "session": {
				columnNames = "strSessionCode,strSessionName,strSDesc";
				tableName = "clsSessionMasterModel where strClientCode='" + clientCode + "'";
				listColumnNames = "Session Code,Session Name,Session Desc";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				idColumnName = "strSessionCode";
				searchFormTitle = "Session Master";
				break;
			}
			
			case "MaterialReturnslip": {
				columnNames = "strMRetCode,dtMRetDate,strAgainst,strUserCreated,dtCreatedDate";
				tableName = "clsMaterialReturnHdModel where strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM dtMRetDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strMRetCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by strMRetCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Material Return Code,Material Return Name,Against,User Created,Date Created";
				idColumnName = "strMRetCode";
				searchFormTitle = "Material Return";
				break;
			}

			case "productProducedslip": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + "  where   ";
				}
				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strProdType='Produced' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "bomcodeslip": {
				columnNames = "a.strBOMCode,a.strParentCode,b.strProdName,a.strProcessCode,b.strPartNo ";
				tableName = "clsBomHdModel a,clsProductMasterModel b where a.strParentCode=b.strProdCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and a.strBOMType='R' ";
				listColumnNames = "BOM Code,Parent Code,Product Name,Process Name,PartNo";
				idColumnName = "strBOMCode";

				String strIndustryType = "";
				List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
				if (listClsCompanyMasterModel.size() > 0) {
					clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);
					strIndustryType = objCompanyMasterModel.getStrIndustryType();
				}
				if (strIndustryType.equalsIgnoreCase("Manufacture")) {
					searchFormTitle = "BOM Master";
				} else {
					searchFormTitle = "Recipe Master";
				}

				break;
			}

			case "ProductionOrderslip": {
				columnNames = "strOPCode,dtOPDate,strStatus,dtFulmtDate,dtfulfilled,strNarration,strUserCreated,dtDateCreated";
				tableName = " from tblproductionorderhd where strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM dtOPDate) between '" + finYear[0] + "' and '" + finYear[1] + "' " + " and strOPCode not in ( select strSOcode from tblworkorderhd where strClientCode='" + clientCode + "' ) ";
				
								
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and strOPCode like '" + propertyCode + "%' ";
				}
				listColumnNames = "Production Order Code,Production Order Date,Fulfilment Date,Fulfilled Date,Narration,User Created,Date Created";
				idColumnName = "strOPCode";
				flgQuerySelection = true;
				searchFormTitle = "Production Order";
				break;
			}

			case "PICodeslip": {
				columnNames = "a.strPICode,a.dtPIDate,b.strLocName,a.strNarration,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsPurchaseIndentHdModel a,clsLocationMasterModel b " + " where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and a.strPICode not in (select strSOCode from clsPurchaseOrderHdModel) ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + " and b.strPropertyCode='" + propCode + "' ";
				}
				
									
				tableName = tableName + " order by a.strPICode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PI Code,PI Date,Location,Narration,User Created,Date Created";
				idColumnName = "strPICode";
				multiDocCodeSelection = "Yes";
				searchFormTitle = "Purchase Indent";
				break;
			}

			case "purchaseorderslip": {
				columnNames = "a.strPOCode,a.dtPODate,b.strPName,a.strAgainst,a.strPayMode,a.dtPayDate" + ",a.strUserCreated,a.dtDateCreated,a.strClosePO";
				tableName = "clsPurchaseOrderHdModel a,clsSupplierMasterModel b " + "where a.strSuppCode=b.strPCode and a.strPOCode not in " + "(select strPONo from clsGRNHdModel) " + "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPODate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				
									
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPOCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by a.strPOCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PO Code,PO Date,Supplier Code,Against,Payment Mode,Payment Date,User Created" + ",Date Created,Close PO";
				idColumnName = "strPOCode";
				searchFormTitle = "Purchase Order";
				break;
			}

			case "PurchaseReturnslip": {
				columnNames = "a.strPRCode,a.dtPRDate,a.strAgainst,b.strLocName,a.strNarration,a.strUserCreated,a.dtDateCreated";
				tableName = "select a.strPRCode,a.dtPRDate,a.strAgainst,b.strLocName,a.strNarration,a.strUserCreated,a.dtDateCreated " + " from tblpurchasereturnhd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPRDate) between '" + finYear[0] + "' and '" + finYear[1]
						+ "' ";
				
								
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strPRCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by a.strPRCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "PR Code,Date,Against,Location,Narration,User Created,Date Created";
				idColumnName = "strPRCode";
				flgQuerySelection = true;
				searchFormTitle = "Purchase Return";
				break;
			}

			case "productmasterslip": {
				columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
				tableName = "clsProductMasterModel a , clsSubGroupMasterModel c , clsGroupMasterModel d ";
				if (showAllProd.equals("N")) {
					tableName = tableName + " ,clsProductReOrderLevelModel b " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and b.strClientCode='" + clientCode + "' and ";
				} else {
					tableName = tableName + " where  ";
				}

				tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
				listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
				idColumnName = "a.strProdCode";
				searchFormTitle = "Product Master";
				break;
			}

			case "stkadjcodeslip": {
				columnNames = "a.strSACode,a.dtSADate,b.strLocName,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkAdjustmentHdModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtSADate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}
				
				tableName = tableName + " order by a.strSACode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Stk Adjustment Code,Stk Adjustment Date,Location,User Created,Date Created";
				idColumnName = "strSACode";
				searchFormTitle = "Stock Adjustment";
				break;
			}

			case "stkpostcodeslip": {
				columnNames = "a.strPSCode,a.dtPSDate,b.strLocName,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkPostingHdModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtPSDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}

				tableName = tableName + " order by a.strPSCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Physical Stk Code,Physical Stk Date,Location,User Created,Date Created";
				idColumnName = "strPSCode";
				searchFormTitle = "Physical Stock Posting";
				break;
			}


			case "MISslip": {
				columnNames = "a.strMISCode,a.strAgainst,b.strLocName ,c.strLocName,a.dtMISDate" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsMISHdModel a,clsLocationMasterModel b ,clsLocationMasterModel c" + " where a.strLocFrom=b.strLocCode and a.strLocTo=c.strLocCode " + " and a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "' and  c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtMISDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}

				sqlforNoraml = sqlforNoraml + " order by a.strMISCode " + ShowTransAsc_Desc + " ";
				tableName = sqlforNoraml;
				listColumnNames = "MIS Code,Against,Loc From,Loc To,MIS Date,User Created,Date Created,Narration";
				idColumnName = "strMISCode";
				searchFormTitle = "Material Issue Slip";
				break;
			}

			case "stktransfercodeslip": {
				columnNames = "a.strSTCode,a.dtSTDate,b.strLocName,c.strLocName,a.strNarration,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsStkTransferHdModel a,clsLocationMasterModel b,clsLocationMasterModel c" + " where a.strFromLocCode=b.strLocCode and a.strToLocCode=c.strLocCode and" + " a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtSTDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strSTCode like '" + propertyCode + "%' ";
				}
				tableName = tableName + " order by strSTCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "ST Code,ST Date,From Location,To Location,Narration,User Created,Date Created";
				idColumnName = "strSTCode";
				searchFormTitle = "Stock Tranfer";
				break;
			}


			case "opstockslip": {
				columnNames = "a.strOpStkCode,b.strLocName,a.dtExpDate,a.strUserCreated,a.dtCreatedDate";
				tableName = "clsInitialInventoryModel a,clsLocationMasterModel b " + "where a.strLocCode=b.strLocCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "'";

				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = tableName + " order by a.strOpStkCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "OpStock Code,Location,Expiry Date,User Created,Date Created";
				idColumnName = "strOpStkCode";
				criteria = getCriteriaQuery(columnNames, search_with, tableName);
				searchFormTitle = "Opening Stock";
				break;
			}

			case "SettlementWeb-Service": {
				listColumnNames = " SrNo. , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}

			case "OtherChargeWeb-Service": {
				listColumnNames = " SrNo. , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}
			
			case "SupplierAccCodeWeb-Service" :
			{
				listColumnNames = " strAccountCode , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
				
			
			}
			
			case "CustomerAccCodeWeb-Service" :
			{
				listColumnNames = " Account Code , Account Name ";
				searchFormTitle = "Account Master";
				JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
				jArrSearchList = (JSONArray) jObjSearchData.get(formName);
				break;
			}
			
			case "grnForPR": {

				columnNames = "a.strGRNCode,a.dtGRNDate,c.strLocName,a.strAgainst,a.strPONo,b.strPName,a.strBillNo" + ",a.strPayMode,a.strUserCreated,a.dtDateCreated,a.strNarration,a.strRefNo";
				tableName = "clsGRNHdModel a,clsSupplierMasterModel b ,clsLocationMasterModel c " + "where a.strSuppCode=b.strPCode and a.strLocCode=c.strLocCode  and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' " + " and EXTRACT(YEAR FROM a.dtGRNDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName = tableName + " and c.strPropertyCode='" + propCode + "' ";
				}
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("GRN")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='Yes'";
				}
				tableName = tableName + " order by a.strGRNCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "GRN Code,GRN Date,Location,Against,POCode,Supplier Name,GRN No," + "Pay Mode,User Created,Date Created,Narration,Ref No";
				idColumnName = "strGRNCode";
				searchFormTitle = "GRN";
				break;
			}
			
			case "StockReq": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}
				tableName = sqlforNoraml;
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Stock Req")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Stock Requisition";

				break;
			}

			case "StockReqSlip": {
				columnNames = "a.strReqCode,a.dtReqDate,b.strLocName,c.strLocName,a.strAuthorise" + ",a.strUserCreated,a.dtCreatedDate,a.strNarration";
				String sqlforNoraml = "clsRequisitionHdModel a,clsLocationMasterModel b,clsLocationMasterModel c " + " where a.strLocBy=b.strLocCode and a.strLocOn=c.strLocCode " + " and a.strReqCode not in (select strReqCode from clsMISHdModel) " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "' "
						+ " and EXTRACT(YEAR FROM a.dtReqDate) between '" + finYear[0] + "' and '" + finYear[1] + "' ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					sqlforNoraml = sqlforNoraml + "and b.strPropertyCode='" + propCode + "' ";
				}

				tableName += " and a.strAuthorise='No'";

				tableName = tableName + " order by a.strReqCode " + ShowTransAsc_Desc + " ";
				listColumnNames = "Req Code,Req Date,Loc By,Loc On,Authorise,User Created,Date Created,Narration";
				idColumnName = "a.strReqCode";
				searchFormTitle = "Stock Requisition";

				break;
			}
			case "salesReturn": {
				columnNames = "a.strSRCode,a.dteSRDate,b.strPName,c.strLocName ";
				tableName = "clsSalesReturnHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
				if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
					tableName += " and a.strSRCode like '" + propertyCode + "%' ";
				}
				boolean flgAuth = false;
				if (null != req.getSession().getAttribute("hmAuthorization")) {
					HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
					if (hmAuthorization.get("Sales Return")) {
						flgAuth = true;
					}
				}
				if (flgAuth) {
					tableName += " and a.strAuthorise='No'";
				}
				listColumnNames = "SR Code,SR Date,Customer Name,Location Name";
				idColumnName = "strSOCode";
				// flgQuerySelection=true;
				searchFormTitle = "Sales Order";
				break;
			}	

			}

			if (null != jArrSearchList) {
				for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
					JSONArray jArrSearchRow = (JSONArray) jArrSearchList.get(cnt);
					Object[] arrObj = new Object[jArrSearchRow.size()];
					for (int row = 0; row < jArrSearchRow.size(); row++) {
						arrObj[row] = jArrSearchRow.get(row);
					}
					listSearchData.add(arrObj);
				}

				mainMap.put("criteria", criteria);
				mainMap.put("listColumnNames", listColumnNames);
				mainMap.put("idColumnName", idColumnName);
				mainMap.put("searchFormTitle", searchFormTitle);
				mainMap.put("listSearchData", listSearchData);
			} else {

				mainMap.put("columnNames", columnNames);
				mainMap.put("tableName", tableName);
				mainMap.put("criteria", criteria);
				mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
				mainMap.put("listColumnNames", listColumnNames);
				mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
				mainMap.put("idColumnName", idColumnName);
				mainMap.put("searchFormTitle", searchFormTitle);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			return mainMap;
		}
	}

	/*
	 * End WebStocks Search
	 */

	/**
	 * Start Excise Search
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */

	private Map<String, Object> funGetExciseSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String listColumnNames = "";
		String multiDocCodeSelection = "No";
		boolean flgQuerySelection = false;
		String idColumnName = "";
		String searchFormTitle = "";
		JSONArray jArrSearchList = null;
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		// String strShowTransOrder=objSetup.getStrShowTransAsc_Desc();
		List<Object[]> listSearchData = new ArrayList<Object[]>();
		switch (formName) {

		case "CityCode": {

			String isCityGlobal = "Custom";
			try {
				isCityGlobal = req.getSession().getAttribute("strCity").toString();
			} catch (Exception e) {
				isCityGlobal = "Custom";
			}
			if (isCityGlobal.equalsIgnoreCase("All")) {
				clientCode = "All";
			}

			columnNames = "strCityCode,strCityName";
			tableName = "clsCityMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "City Code,City Name";
			idColumnName = "strCityCode";
			searchFormTitle = "City Master";
			break;
		}

		case "LicenceCode": {
			String isCityGlobal = "Custom";
			String globCity = clientCode;
			try {
				isCityGlobal = req.getSession().getAttribute("strCity").toString();
			} catch (Exception e) {
				isCityGlobal = "Custom";
			}
			if (isCityGlobal.equalsIgnoreCase("All")) {
				globCity = "All";
			}

			columnNames = "a.strLicenceCode,a.strLicenceNo,a.strLicenceName,b.strCityName";
			tableName = "from tbllicencemaster a, tblcitymaster b " + "where a.strCity = b.strCityCode and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + globCity + "' and a.strPropertyCode='" + propertyCode + "' ";
			listColumnNames = "Licence Code,Licence No, Company Name, City";
			idColumnName = "strLicenceCode";
			flgQuerySelection = true;
			searchFormTitle = "Licence Master";
			break;

		}

		case "CategoryCode": {
			String isCategoryGlobal = "Custom";
			try {
				isCategoryGlobal = req.getSession().getAttribute("strCategory").toString();
			} catch (Exception e) {
				isCategoryGlobal = "Custom";
			}
			if (isCategoryGlobal.equalsIgnoreCase("All")) {
				clientCode = "All";
			}

			columnNames = "strCategoryCode,strCategoryName";
			tableName = "clsCategoryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Category Code,Category Name";
			idColumnName = "strCategoryCode";
			searchFormTitle = "Category Master";
			break;
		}

		case "SubCategoryCode": {
			String isSubCategoryGlobal = "Custom";
			try {
				isSubCategoryGlobal = req.getSession().getAttribute("strSubCategory").toString();
			} catch (Exception e) {
				isSubCategoryGlobal = "Custom";
			}
			if (isSubCategoryGlobal.equalsIgnoreCase("All")) {
				clientCode = "All";
			}

			columnNames = "strSubCategoryCode,strSubCategoryName,strCategoryCode";
			tableName = "clsSubCategoryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Sub Category Code,Sub Category Name, Category Code";
			idColumnName = "strSubCategoryCode";
			searchFormTitle = "Sub Category Master";
			break;
		}

		case "SizeCode": {
			String isSizeGlobal = "Custom";
			try {
				isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
			} catch (Exception e) {
				isSizeGlobal = "Custom";
			}
			if (isSizeGlobal.equalsIgnoreCase("All")) {
				clientCode = "All";
			}

			columnNames = "strSizeCode,strSizeName,intQty,strUOM,strNarration";
			tableName = "from tblsizemaster " + "where strClientCode='" + clientCode + "'";
			listColumnNames = "Size Code,Size Name,Quantity,UOM,Narration";
			idColumnName = "strSizeCode";
			flgQuerySelection = true;
			searchFormTitle = "Size Master";
			break;

		}

		case "BrandCode": {
			String isBrandGlobal = "Custom";
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}

			String tempClientCode = clientCode;
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.strBrandCode,a.strBrandName,b.strSizeName,b.intQty,ifnull(d.dblRate,'0') as rate,c.strSubCategoryName";
			tableName = " from tblbrandmaster a LEFT OUTER JOIN tblratemaster d ON d.strBrandCode = a.strBrandCode  AND d.strClientCode='" + clientCode + "'," + " tblsizemaster b,tblsubcategorymaster c " + " where a.strSizeCode = b.strSizeCode AND a.strSubCategoryCode=c.strSubCategoryCode " + " AND a.strClientCode='" + tempClientCode + "' ";
			listColumnNames = "Brand Code,Brand Name, Size, Quantity,Rate,Sub Category";
			idColumnName = "strBrandCode";
			flgQuerySelection = true;
			searchFormTitle = "Brand Master";
			break;
		}

		case "TransactionalBrands": {
			String isBrandGlobal = "Custom";
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}

			String tempClientCode = clientCode;
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.strBrandCode,a.strBrandName,b.strSizeName,b.intQty,ifnull(d.dblRate,'0') as rate,c.strSubCategoryName";
			tableName = " from tblbrandmaster a LEFT OUTER JOIN tblratemaster d ON d.strBrandCode = a.strBrandCode  AND d.strClientCode='" + clientCode + "', " + "	tblsizemaster b,tblsubcategorymaster c " + " WHERE a.strBrandCode IN (SELECT DISTINCT strBrandCode FROM " + " (SELECT p.strBrandCode FROM tbltpdtl p,tblbrandmaster m WHERE p.strClientCode='" + clientCode + "' AND "
					+ " p.strBrandCode=m.strBrandCode UNION ALL " + " (SELECT q.strBrandCode FROM tblopeningstock q,tblbrandmaster n " + " WHERE q.strClientCode='" + clientCode + "' AND q.strBrandCode=n.strBrandCode)) " + " AS strBrandCode) AND a.strSizeCode = b.strSizeCode " + " AND a.strSubCategoryCode=c.strSubCategoryCode" + " AND a.strClientCode='" + tempClientCode + "' ";

			listColumnNames = "Brand Code,Brand Name, Size, Quantity,Rate,Sub Category";
			idColumnName = "strBrandCode";
			flgQuerySelection = true;
			searchFormTitle = "Brand Master";
			break;
		}

		case "childCode": {
			String isBrandGlobal = "Custom";
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}

			String tempClientCode = clientCode;
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.strBrandCode,a.strBrandName,b.strSizeName,b.intQty,ifnull(d.dblRate,'0') as rate,c.strSubCategoryName";
			tableName = " from tblbrandmaster a LEFT OUTER JOIN tblratemaster d ON d.strBrandCode = a.strBrandCode  AND d.strClientCode='" + clientCode + "'," + " tblsizemaster b,tblsubcategorymaster c " + " where a.strSizeCode = b.strSizeCode AND a.strSubCategoryCode=c.strSubCategoryCode " + " AND a.strClientCode='" + tempClientCode + "' ";
			listColumnNames = "Brand Code,Brand Name, Size, Quantity,Rate,Sub Category";
			idColumnName = "strBrandCode";
			flgQuerySelection = true;
			searchFormTitle = "Brand Master";
			break;

		}

		case "RecipeCode": {
			columnNames = "strRecipeCode,strParentCode,strParentName,dteValidFrom,dteValidTo";
			tableName = "from tblexciserecipermasterhd " + "where strClientCode='" + clientCode + "'";
			listColumnNames = "Recipe Code,Parent Code,Recipe Name,Valid From, Valid To";
			idColumnName = "strRecipeCode";
			flgQuerySelection = true;
			searchFormTitle = "Recipe Master";
			break;

		}

		case "SupplierCode": {
			String isSupplierGlobal = "Custom";
			try {
				isSupplierGlobal = req.getSession().getAttribute("strSupplier").toString();
			} catch (Exception e) {
				isSupplierGlobal = "Custom";
			}
			if (isSupplierGlobal.equalsIgnoreCase("All")) {
				clientCode = "All";
			}

			columnNames = "a.strSupplierCode,a.strSupplierName,a.strVATNo,a.strTINNo,b.strCityName";
			tableName = "from tblsuppliermaster a, tblcitymaster b " + "where a.strCityCode=b.strCityCode and a.strClientCode='" + clientCode + "'";
			listColumnNames = "Supplier Code,Supplier Name,VAT No,TIN No,City";
			idColumnName = "strSupplierCode";
			flgQuerySelection = true;
			searchFormTitle = "Excise Supplier Master";
			break;
		}

		case "exciselocationmaster": {
			columnNames = "strLocCode,strLocName";
			tableName = "clsExciseLocationMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Location Code,Location Name";
			idColumnName = "strLocCode";
			searchFormTitle = "Location Master";
			break;

		}

		case "brandOpeningCode": {
			String isBrandGlobal = "Custom";
			String tempClientCode = clientCode;
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.intId,b.strBrandCode,b.strBrandName,a.intOpBtls,a.intOpPeg,a.intOpML";
			tableName = " from tblopeningstock a,tblbrandmaster b " + " where a.strBrandCode=b.strBrandCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + tempClientCode + "' and a.strLicenceCode='" + licenceCode + "' ";
			listColumnNames = "ID,Brand Code,Brand Name,Opening Btls,Opening Pegs,Opening MLs";
			idColumnName = "intId";
			flgQuerySelection = true;
			searchFormTitle = "Excise Opening Master";
			break;
		}
		case "TPCode": {

			columnNames = "a.strTPCode,a.strTPNo,a.strInvoiceNo,a.strTpDate,b.strSupplierName,a.dblTotalBill,DATE_FORMAT(a.strTpDate,'%M %Y') AS transDate";
			tableName = "from tbltphd a,tblsuppliermaster b " + "where a.strClientCode='" + clientCode + "' and a.strSupplierCode=b.strSupplierCode and a.strLicenceCode='" + licenceCode + "' ";
			listColumnNames = "TP Code,TP Number,Invoice Number,Date,Supplier,Total Bill,Month OF Transaction";
			idColumnName = "strTPCode";
			flgQuerySelection = true;
			searchFormTitle = "TP Master";
			break;

		}

		case "SalesId": {

			columnNames = "intId,dteBillDate,strClientCode,DATE_FORMAT(dteBillDate,'%M %Y') AS transDate";
			tableName = "from tblmanualsaleshd " + "where strClientCode='" + clientCode + " ' and  strLicenceCode='" + licenceCode + "'";
			listColumnNames = "Id,Sale Date,Client Code,Month OF Transaction";
			idColumnName = "intId";
			flgQuerySelection = true;
			searchFormTitle = "Excise Sales Master";
			break;

		}

		case "excisePermitCode": {
			columnNames = "a.strPermitCode,a.strPermitName,a.strPermitNo,a.dtePermitExp,a.strPermitType";
			tableName = "clsExcisePermitMasterModel a  " + "where a.strClientCode='" + clientCode + "'";
			listColumnNames = "Permit Code,Permit Name,Permit No.,Expiry Date,Permit Type";
			idColumnName = "strPermitCode";
			searchFormTitle = "Excise Permit Master";
			break;
		}

		case "fromBillNo": {
			String isBrandGlobal = "Custom";
			String tempClientCode = clientCode;
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.intBillNo,date(a.dteBillDate) as billDate,a.strSalesCode,IFNULL(c.strPermitName,'One Day Permit') as permitName," + " IFNULL(c.strPermitNo,a.strPermitCode) as permitNo, b.strBrandName,a.intTotalPeg,a.dblTotalAmt ";
			tableName = "from tblexcisesaledata a LEFT JOIN tblpermitmaster c ON a.strPermitCode=c.strPermitCode,tblbrandmaster b" + " where a.strItemCode=b.strBrandCode and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + tempClientCode + "' and date(a.dteBillDate) " + " between '" + txtFromDate + "' and '" + txtToDate + "' ";

			if (!licenceCode.equalsIgnoreCase("All")) {
				tableName = tableName + " and a.strLicenceCode='" + licenceCode + "' ";
			}
			tableName = tableName + " ORDER BY a.intBillNo ";

			listColumnNames = "Bill No,Sale Date,Sale Id,Permit Holder Name,Permit No,Brand Name,Total Peg/Bts,Total Amount";
			idColumnName = "strBillNo";
			flgQuerySelection = true;
			searchFormTitle = "Generated Bill";
			break;
		}

		case "toBillNo": {

			String isBrandGlobal = "Custom";
			String tempClientCode = clientCode;
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempClientCode = "All";
			}

			columnNames = "a.intBillNo,date(a.dteBillDate) as billDate,a.strSalesCode,IFNULL(c.strPermitName,'One Day Permit') as permitName," + " IFNULL(c.strPermitNo,a.strPermitCode) as permitNo, b.strBrandName,a.intTotalPeg,a.dblTotalAmt ";
			tableName = "from tblexcisesaledata a LEFT JOIN tblpermitmaster c ON a.strPermitCode=c.strPermitCode,tblbrandmaster b" + " where a.strItemCode=b.strBrandCode and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + tempClientCode + "' and date(a.dteBillDate) " + " between '" + txtFromDate + "' and '" + txtToDate + "' ";
			if (!licenceCode.equalsIgnoreCase("All")) {
				tableName = tableName + "and a.strLicenceCode='" + licenceCode + "' ";
			}
			tableName = tableName + "ORDER BY a.intBillNo ";

			listColumnNames = "Bill No,Sale Date,Sale Id,Permit Holder Name,Permit No,Brand Name,Total Peg/Bts,Total Amount";
			idColumnName = "strBillNo";
			flgQuerySelection = true;
			searchFormTitle = "Generated Bill";
			break;
		}

		case "excisestkpostcode": {
			columnNames = "a.strPSPCode,a.dtePostingDate,b.strLicenceNo,a.strUserCreated,a.dteDateCreated";
			tableName = "clsExciseStkPostingHdModel a,clsExciseLicenceMasterModel b " + "where a.strLicenceCode=b.strLicenceCode and a.strClientCode='" + clientCode + "' " + "and b.strClientCode='" + clientCode + "'";
			listColumnNames = "Stk Posting Code,Stock Posting Date,Licence No.,User Created,Date Created";
			idColumnName = "strPSPCode";
			searchFormTitle = "Physical Stock Posting";
			break;

		}

		case "oneDayPass": {
			columnNames = "a.intId,a.dteDate,a.intFromNo,a.intToNo ";
			tableName = " from tblonedaypass a " + "where  a.strClientCode='" + clientCode + "' ";
			listColumnNames = "ID, Generated Date, From No.,To No.";
			idColumnName = "intId";
			flgQuerySelection = true;
			searchFormTitle = "One Day Pass";
			break;

		}

		case "usermaster": {
			columnNames = "a.strUserCode,a.strUserName";
			tableName = "from "+webStockDB+".tbluserhd a where a.strClientCode='" + clientCode + "'";
			listColumnNames = "User Code,User Name";
			idColumnName = "strUserCode";
			searchFormTitle = "User Master";
			flgQuerySelection = true;
			break;
		}
		case "exciseproperty": {
			columnNames = "a.strPropertyCode,a.strpropertyName ";
			tableName = "from "+webStockDB+".tblpropertymaster a where a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Property Code,Property Name";
			idColumnName = "strPropertyCode";
			searchFormTitle = "Property Master";
			flgQuerySelection = true;
			break;
		}

		case "POSItemWeb-Service": {
			listColumnNames = "Item Code , Item Name ";
			searchFormTitle = "POS Item Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		}

		if (null != jArrSearchList) {
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrSearchRow = (JSONArray) jArrSearchList.get(cnt);
				Object[] arrObj = new Object[jArrSearchRow.size()];
				for (int row = 0; row < jArrSearchRow.size(); row++) {
					arrObj[row] = jArrSearchRow.get(row);
				}
				listSearchData.add(arrObj);
			}

			mainMap.put("criteria", criteria);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
			mainMap.put("listSearchData", listSearchData);
		} else {

			mainMap.put("columnNames", columnNames);
			mainMap.put("tableName", tableName);
			mainMap.put("criteria", criteria);
			mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
		}
		return mainMap;
	}

	/*
	 * End Excise Search
	 */

	/**
	 * PMS Search Start
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */
	private Map<String, Object> funGetPMSSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String listColumnNames = "";
		String multiDocCodeSelection = "No";
		boolean flgQuerySelection = false;
		String idColumnName = "";
		String searchFormTitle = "";
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		String strShowTransOrder = objSetup.getStrShowTransAsc_Desc();
		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		String strWebBooksDB=req.getSession().getAttribute("WebBooksDB").toString();
		switch (formName) {
		case "roomCode": {
			columnNames = "a.strRoomCode,a.strRoomDesc,b.strRoomTypeDesc,a.strFloorCode,a.strBedType";
			tableName = " from tblroom a,tblroomtypemaster b " + " where a.strRoomTypeCode=b.strRoomTypeCode and a.strStatus='Free' and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Description,Room Type,Floor No,Bed Type,Funiture";
			idColumnName = "a.strRoomCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Room Master";
			flgQuerySelection = true;
			break;
		}

		case "roomForMaster": {
			columnNames = "a.strRoomCode,a.strRoomDesc,b.strRoomTypeDesc,a.strFloorCode,a.strBedType";
			tableName = " from tblroom a,tblroomtypemaster b " + " where a.strRoomTypeCode=b.strRoomTypeCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Description,Type,Floor No,Bed Type,Funiture,Extra Bed";
			idColumnName = "strRoomCode";
			flgQuerySelection = true;
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Room Master";
			break;
		}

		case "roomCodeForFolio": {
			columnNames = "a.strRoomCode,a.strRoomDesc,c.strRoomTypeDesc";
			tableName = " from tblroom a,tblfoliohd b,tblroomtypemaster c " + " where a.strRoomCode=b.strRoomNo and a.strRoomTypeCode=c.strRoomTypeCode " + " and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Description,Room Type,Floor No,Bed Type,Funiture,Extra Bed";
			idColumnName = "strRoomCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Room Master";
			flgQuerySelection = true;
			break;
		}

		case "taxGroupCode": {
			columnNames = "strTaxGroupCode,strTaxGroupDesc,strUserCreated,dteDateCreated,strUserEdited,dteDateEdited";
			tableName = "clsTaxGroupMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Tax Group Code,Tax Group Desc,User Created,Date Created,User Edited,Date Edited";
			idColumnName = "strTaxGroupCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Tax Group Master";
			break;
		}

		case "taxCode": {
			columnNames = "strTaxCode,strTaxDesc";
			tableName = "clsPMSTaxMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Tax Code,Tax Desc";
			idColumnName = "strTaxCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Tax Master";
			break;
		}

		case "ReservationNo": {
			
			columnNames = " a.strReservationNo,ifnull(a.strNoRoomsBooked,''),a.dteArrivalDate,a.dteDepartureDate,CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName) ";
			tableName = " from tblreservationhd a ,tblreservationdtl b,tblguestmaster c "
					  + " where a.strReservationNo=b.strReservationNo and b.strGuestCode=c.strGuestCode "
					  + " and a.strReservationNo NOT IN (select strReservationNo from tblcheckinhd) "
					  + " and a.strCancelReservation='N' group by a.strReservationNo ";
			listColumnNames = "Reservation No,Rooms for Reservation,Arrival Date,Departure Time,Guest Name";
			idColumnName = "strReservationNo";
			flgQuerySelection = true;
			searchFormTitle = "Reservations";
			break;
		}

		case "deptCode": {
			columnNames = "strDeptCode,strDeptDesc,strOperational,strRevenueProducing,strDiscount,strType,strDeactivate" + ",strUserEdited,dteDateEdited";
			tableName = "clsDepartmentMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Code,Department,Operational,Revenue Producing,Discount,Type,Deactivate,Last Edited By,Updated Date";
			idColumnName = "strDeptCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Department Master";
			break;
		}

		case "baggage": {
			columnNames = "strBaggageCode,strBaggageDesc,strUserEdited,dteDateEdited";
			tableName = "clsBaggageMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Code,Baggage Desc,Last Edited By,Updated Date";
			idColumnName = "strBaggageCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Baggage Master";
			break;
		}

		case "bathType": {
			columnNames = "strBathTypeCode,strBathTypeDesc,strUserEdited,dteDateEdited";
			tableName = "clsBathTypeMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,BathType Desc,Last Edited By,Updated Date";
			idColumnName = "strBathTypeCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "BathType Master";
			break;
		}

		case "roomType": {
			columnNames = "strRoomTypeCode,strRoomTypeDesc,strUserEdited,dteDateEdited";
			tableName = "clsRoomTypeMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,RoomType Desc,Last Edited By,Updated Date";
			idColumnName = "strRoomTypeCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "RoomType Master";
			break;
		}

		case "business": {
			columnNames = "strBusinessSourceCode,strDescription,strInvolvesAmt,strInstAccepted,strReqSlipReqd,strUserEdited,dteDateEdited";
			tableName = "clsBusinessSourceMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Business Source,Involves Amount,Instrument Accepted,Request Slip Requied,Last Edited By,Updated Date ";
			idColumnName = "strBusinessSourceCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Business Source Master";
			break;
		}

		case "extraBed": {
			columnNames = "a.strExtraBedTypeCode,a.strExtraBedTypeDesc,a.intNoBeds,a.dblChargePerBed,a.strUserEdited,a.dteDateEdited";
			tableName = " from tblextrabed a where a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,ExtraBed Desc,No Of Bed,Bed Per Charges,Last Edited By,Updated Date";
			idColumnName = "strExtraBedTypeCode";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "ExtraBed Master";
			break;
		}

		case "reasonPMS": {
			columnNames = "strReasonCode,strReasonDesc,strReasonType,strUserEdited,dteDateEdited";
			tableName = "clsPMSReasonMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Reason Desc,Reason Type,Last Edited By,Updated Date";
			idColumnName = "strReasonCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Reason Master";
			break;
		}

		case "incomeHead": {
			columnNames = "a.strIncomeHeadCode,a.strIncomeHeadDesc,b.strDeptDesc,a.strUserEdited,a.dteDateEdited";
			tableName = " from tblincomehead a,tbldepartmentmaster b " + " where a.strDeptCode=b.strDeptCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,IncomeHead Desc,Dept,Last Edited By,Updated Date";
			idColumnName = "strIncomeHeadCode";
			searchFormTitle = "IncomeHead Master";
			flgQuerySelection = true;
			break;
		}

		case "plan": {
			columnNames = "strPlanCode,strPlanDesc,strUserEdited,dteDateEdited";
			tableName = "clsPlanMasterModel  where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Plan Desc,Last Edited By,Updated Date";
			idColumnName = "strPlanCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Plan Master";
			break;
		}

		case "service": {
			columnNames = "strService,strDeptCode,strIncomeHeadCode,strUserEdited,dteDateEdited";
			tableName = "clsChargePostingHdModel  where strClientCode='" + clientCode + "' ";
			listColumnNames = "Service Code,Dept Code,IncomeHead Code,Last Edited By,Updated Date";
			idColumnName = "strService";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Charge Posting Master";
			break;
		}

		case "settlementCode": {
			columnNames = "strSettlementCode,strSettlementType,strSettlementDesc,strApplicable";
			tableName = "clsPMSSettlementMasterHdModel  where strClientCode='" + clientCode + "' ";
			listColumnNames = "Settlement Code,Settlement Type,Settlement Desc,Applicable";
			idColumnName = "strSettlementCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Settlement Master";
			break;
		}

		case "AgentCode": {
			columnNames = "strAgentCode,dteFromDate,dteToDate,strAgentCommCode,strCorporateCode ";
			tableName = "clsAgentMasterHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Agent Code,FromDate,ToDate,Commision Code,CorporateCode";
			idColumnName = "strAgentCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Agent Master";
			break;
		}

		case "AgentCommCode": {
			columnNames = "strAgentCommCode,dteFromDate,dteToDate,strCalculatedOn,strCommisionPaid,strCommisionOn ";
			tableName = "clsAgentCommisionHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Commisssion Code,FromDate,ToDate,CalculatedOn,CommisionPaid,CommisionOn ";
			idColumnName = "strAgentCommCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Agent Commision Master";
			break;
		}

		case "CorporateCode": {
			columnNames = "strCorporateCode,strCorporateDesc,strPlanCode,strSegmentCode,strBlackList,strCity,strState,strCountry,strUserEdited,dteDateEdited ";
			tableName = "clsCorporateMasterHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,CorporateDesc,Plan,Segment,strBlackList,City,State,Country,Last Edited By,Updated Date ";
			idColumnName = "strCorporateCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Corporate Master";
			break;
		}

		case "BookerCode": {
			columnNames = "strBookerCode,strBookerName,strBlackList,strAddress,lngMobileNo,lngTelephoneNo,strEmailId,strCity,strState,strCountry ";
			tableName = "clsBookerMasterHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Booker Code,Booker Name,Black List,Address,MobileNo,TelephoneNo,EmailId,City,State,Country ";
			idColumnName = "strBookerCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Booker Master";
			break;
		}

		case "BookingTypeCode": {
			columnNames = "strBookingTypeCode,strBookingTypeDesc,strUserEdited,dteDateEdited ";
			tableName = "clsBookingTypeHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "BookingTypeCode,BookingType,Last Edited By,Updated Date ";
			idColumnName = "strBookingTypeCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Booking Type";
			break;
		}

		case "BillingInstCode": {
			columnNames = "strBillingInstCode,strBillingInstDesc,strUserEdited,dteDateEdited ";
			tableName = "clsBillingInstructionsHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "BillingInstructionCode,Billing Instructions,Last Edited By,Updated Date ";
			idColumnName = "strBillingInstCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Billing InstructionCode";
			break;
		}

		case "checkInRooms": {
			columnNames = " d.strRoomCode,d.strRoomDesc,a.strCheckInNo,a.strReservationNo,a.strRegistrationNo,CONCAT(e.strFirstName,' ',e.strMiddleName,' ',e.strLastName) ";
			tableName = "  from tblcheckinhd a,tblcheckindtl b ,tblfoliohd c,tblroom d,tblguestmaster e"
					  + " where a.strCheckInNo=c.strCheckInNo and a.strCheckInNo=b.strCheckInNo"
					  + " and c.strRoomNo=d.strRoomCode and b.strGuestCode=e.strGuestCode and b.strPayee='Y' ";
			listColumnNames = " Room No.,Room Desc.,CheckIn No,Reservation No.,Registration No.,Guest Name ";
			idColumnName = " strRoomCode";
			flgQuerySelection = true;
			searchFormTitle = "All Check-In Rooms";
			break;
		}

		case "checkIn": {
			columnNames = " a.strCheckInNo,a.strRegistrationNo,a.strReservationNo ,date(a.dteArrivalDate),date(a.dteDepartureDate) "
					+ " ,d.strRoomDesc,CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName)";

			tableName = " from tblcheckinhd a,tblcheckindtl b,tblguestmaster c,tblroom d " + " where  a.strCheckInNo " + " not in (select strCheckInNo from tblbillhd  where strClientCode='" + clientCode + "') " + " and a.strClientCode='" + clientCode + "' "
					+ " and a.strCheckInNo=b.strCheckInNo and b.strGuestCode=c.strGuestCode and b.strRoomNo=d.strRoomCode and b.strPayee='Y' ";
			listColumnNames = " Check In No,Registration No,Reservation No, Arrival Date,Departure Date,Room No,Guest Name ";

			idColumnName = " strCheckInNo";
			flgQuerySelection = true;
			searchFormTitle = "All Check-In Rooms";
			break;
		}

		
		case "WalkinNo": {
			columnNames = " a.strWalkinNo,date(a.dteWalkinDate),a.tmeWalkinTime,date(a.dteCheckOutDate),a.tmeCheckOutTime,c.strRoomTypeDesc,a.strRemarks,CONCAT(d.strFirstName,' ',d.strMiddleName,' ',d.strLastName)  ";
			tableName = "from tblwalkinhd a,tblwalkindtl b,tblroomtypemaster c,tblguestmaster d " + " where a.strWalkinNo=b.strWalkinNo and  b.strRoomType=c.strRoomTypeCode and b.strGuestCode=d.strGuestCode "
					  + " and a.strWalkinNo NOT IN (select strWalkinNo from tblcheckinhd) group by a.strWalkinNo ";
			listColumnNames = "Walkin No,Walkin Date,Walkin Time,Check Out Date,Check Out Time,Room Desc, Remarks,Guest Name";
			idColumnName = "strWalkinNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Walkin Transaction";
			break;
		}

		case "guestCode": {
			columnNames = "strGuestCode,strGuestPrefix,strFirstName,strMiddleName,strLastName,strGender,dteDOB,strDesignation,strAddress,strCity";
			tableName = "clsGuestMasterHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Guest Code,Guest prefix,First Name,Middle Name,Last Name,Gender,DOB,Designation,Address,City";
			idColumnName = "strGuestCode";
			// flgQuerySelection=true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Guest Master";
			break;
		}


		case "billNo": {
			columnNames = "a.strBillNo ,CONCAT(c.strFirstName,c.strMiddleName,c.strLastName),a.dteBillDate,a.strFolioNo,a.strReservationNo,a.dblGrandTotal,d.strRoomDesc ";
			tableName = "from  tblbillhd a ,tblcheckindtl b,tblguestmaster c,tblroom d " 
					+ " where a.strClientCode='" + clientCode + "' and a.strCheckInNo=b.strCheckInNo   " 
					+ " and b.strGuestCode=c.strGuestCode and b.strPayee='Y'  and b.strRoomNo=d.strRoomCode "
					+ " group by a.strBillNo";

			listColumnNames = "Bill No ,Guest Name,Bill Date,Folio No,Reservation No,GrandTotal,Room No ";
			idColumnName = "strBillNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Bill Form";
			break;
		}

		case "folioNoForNoPost": {
			columnNames = " distinct(strFolioNo), CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName) as Geust " + ", d.strRoomDesc,a.strCheckInNo,a.strRegistrationNo,a.strReservationNo,a.strWalkInNo ";
			tableName = " from tblfoliohd a,tblguestmaster c,tblroom d ,tblcheckinhd e" 
			          + " where a.strGuestCode=c.strGuestCode and a.strRoomNo=d.strRoomCode  and e.strCheckInNo=a.strCheckInNo and e.strNoPostFolio='N' ";
			listColumnNames = "Folio No ,Guest Name ,Room No,Check In No,Registration No,Reservation No,Walk-In No ";
			idColumnName = "strFolioNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Folios";
			break;
		}
		
		case "folioNo": {
			columnNames = " distinct(strFolioNo), CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName) as Geust " + ", d.strRoomDesc,a.strCheckInNo,a.strRegistrationNo,a.strReservationNo,a.strWalkInNo ";
			tableName = " from tblfoliohd a,tblguestmaster c,tblroom d " 
			          + " where a.strGuestCode=c.strGuestCode and a.strRoomNo=d.strRoomCode ";
			listColumnNames = "Folio No ,Guest Name ,Room No,Check In No,Registration No,Reservation No,Walk-In No ";
			idColumnName = "strFolioNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Folios";
			break;
		}

		case "folioPayee": {
			columnNames = " distinct(strFolioNo),d.strRoomDesc,a.strCheckInNo,a.strRegistrationNo,a.strReservationNo" 
		     + ",CONCAT(c.strFirstName,' ',c.strMiddleName,' ',c.strLastName) as Geust ";
			tableName = " from tblfoliohd a,tblguestmaster c,tblroom d ,tblcheckindtl e" 
			+ " where a.strGuestCode=c.strGuestCode and a.strRoomNo=d.strRoomCode " 
			+ " and a.strCheckInNo=e.strCheckInNo and e.strRegistrationNo =a.strRegistrationNo and e.strPayee='Y' and e.strGuestCode=a.strGuestCode ";
			listColumnNames = "Folio No,Room Desc,Check In No,Registration No,Reservation No,Guest Name";
			idColumnName = "strFolioNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Folios";
			break;
		}

		case "receiptNo": {
			columnNames = "a.strReceiptNo,date(a.dteReceiptDate),a.strAgainst,a.strCheckInNo,a.strReservationNo,a.strBillNo ";
			tableName = "from tblreceipthd a ";
			listColumnNames = "Receipt No,Receipt Date,Against,Check In No,Reservation No,Bill No";
			idColumnName = "strReceiptNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Payment Receipt";
			break;
		}

		case "accountCode": {
			columnNames = " a.strAccountCode,a.strAccountName,a.strOperational,a.strType,b.strGroupName,a.intOpeningBal,strDebtor,strCreditor ";
			//
			
			tableName = " from "+strWebBooksDB+".tblacmaster a,"+strWebBooksDB+".tblacgroupmaster b " + " where a.strGroupCode=b.strGroupCode ";

			listColumnNames = " Account Code,Account Name,Operational,Type,Account Group,Opening Bal";
			idColumnName = " strAccountCode";
			flgQuerySelection = true;
			searchFormTitle = "Account Master LinkedUp";
			break;
		}


		case "checkInForBill": {
			/*
			 * columnNames =
			 * "strBillNo,dteBillDate,strFolioNo,strRegistrationNo,strReservationNo,dblGrandTotal"
			 * ; tableName =
			 * "from  tblbillhd where strClientCode='"+clientCode+"' ";
			 */
			columnNames = "  a.strCheckInNo,CONCAT(c.strFirstName,c.strMiddleName,c.strLastName), a.dteBillDate,a.strFolioNo,  " + " a.strRegistrationNo,a.strReservationNo,a.dblGrandTotal ,d.strPayee";
			tableName = " from tblcheckinhd b  ,tblbillhd a ,tblcheckindtl d ,tblguestmaster c " + " where d.strPayee='Y' and b.strCheckInNo =a.strCheckInNo  " + " and b.strCheckInNo=d.strCheckInNo and d.strGuestCode=c.strGuestCode and a.dteBillDate between '" + txtFromDate + " ' and '" + txtToDate + " ' " + " group by a.strCheckInNo, d.strPayee ";

			listColumnNames = "CheckIN No,Bill Date,Guest Name,Folio No,Registration No,Reservation No,GrandTotal ,Payee";
			idColumnName = "strBillNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Bill Form";
			break;
		}

		case "BillForPayment": {
			columnNames = "  a.strBillNo, CONCAT(c.strFirstName,c.strMiddleName,c.strLastName), " + " DATE_FORMAT(a.dteBillDate,'%d-%m-%Y'),a.dblGrandTotal, " + " (a.dblGrandTotal-ifnull(sum(d.dblPaidAmt),0)) as RemainingAmt " + " ,a.strFolioNo,a.strRegistrationNo,a.strReservationNo ";
			tableName = "from  tblbillhd a left outer join tblreceipthd d  on a.strCheckInNo=d.strCheckInNo and a.strFolioNo = d.strFolioNo and a.strBillNo=d.strBillNo," + "tblcheckindtl b,tblguestmaster c " + " where  a.strClientCode='" + clientCode + "'  AND a.strCheckInNo=b.strCheckInNo " + " and a.strBillSettled='N' " + " AND b.strGuestCode=c.strGuestCode AND b.strPayee='Y' group by a.strBillNo ";

			listColumnNames = "Bill No ,Guest Name,Bill Date,Grand Total,Balance Amt,Folio No,Registration No,Reservation No ";
			idColumnName = "strBillNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Bill For Payment";
			break;
		}
		
		case "marketsource": {
			columnNames = "strMarketSourceCode,strDescription,strReqSlipReqd,strUserEdited,dteDateEdited";
			tableName = "clsMarketSourceMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Market Code,Market Source,Request Slip Requied,Last Edited By,Updated Date ";
			idColumnName = "strMarketSourceCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Market Source Master";
			break;
		}
		
		case "floormaster": {
			columnNames = " strFloorCode,strFloorName,dblFloorAmt,strUserEdited,dteDateEdited ";
			tableName = " from tblfloormaster where strClientCode='" + clientCode + "' ";
			listColumnNames = " Floor Code,Floor Name,Amount,Last Edited By,Updated Date ";
			idColumnName = " strFloorCode ";
			searchFormTitle = " Floor Master ";
			flgQuerySelection = true;
			break;
		}
		
		case "folioNoForReOpen": {
			columnNames = " a.strFolioNo, CONCAT(d.strFirstName,' ',d.strMiddleName,' ',d.strLastName) as Guest,e.strRoomDesc,a.strCheckInNo,a.strRegistrationNo,a.strReservationNo,b.strWalkInNo";
			tableName = " from tblbillhd a, tblcheckinhd b,tblcheckindtl c,tblguestmaster d,tblroom e "
					  + " where a.strCheckInNo=b.strCheckInNo and b.strCheckInNo=c.strCheckInNo and  c.strGuestCode=d.strGuestCode "
					  + " and a.strRoomNo=e.strRoomCode "
					  + " and  a.strFolioNo  Not IN(select strFolioNo from tblfoliohd c) "
					  + " group by a.strFolioNo ";
			listColumnNames = "Folio No ,Guest Name ,Room No,Check In No,Registration No,Reservation No";
			idColumnName = "strFolioNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Folios";
			break;
		}
		
		case "reservationForBill": {
			
			columnNames = " a.strReservationNo,ifnull(a.strNoRoomsBooked,''),a.dteArrivalDate,a.dteDepartureDate ";
			tableName = " from tblreservationhd a " + "   where a.strReservationNo NOT IN (select strReservationNo from tblcheckinhd) " + "  "
					  + " and a.strCancelReservation='N' and date(a.dteDepartureDate) >='" + txtToDate + " ' ";
			listColumnNames = "Reservation No,Rooms for Reservation,Arrival Date,Departure Time";
			idColumnName = "strReservationNo";
			flgQuerySelection = true;
			searchFormTitle = "Reservations";
			break;
		}
		
		case "checkInForProvisionBill": {
			/*
			 * columnNames =
			 * "strBillNo,dteBillDate,strFolioNo,strRegistrationNo,strReservationNo,dblGrandTotal"
			 * ; tableName =
			 * "from  tblbillhd where strClientCode='"+clientCode+"' ";
			 */
			columnNames = "b.strCheckInNo, CONCAT(c.strFirstName,c.strMiddleName,c.strLastName), b.dteCheckInDate,b.strRegistrationNo,b.strReservationNo,d.strPayee";
			tableName = " from tblcheckinhd b,tblcheckindtl d,tblguestmaster c "
			+ " WHERE b.strCheckInNo NOT IN (select strCheckInNo from tblbillhd) and d.strPayee='Y' "
			+ " AND b.strCheckInNo=d.strCheckInNo and c.strGuestCode=d.strGuestCode "
			+ " AND date(b.dteDepartureDate)>='"+txtToDate+"'"
			+ " GROUP BY b.strCheckInNo, d.strPayee ";

			listColumnNames = "CheckIN No,CheckiIN Date,Guest Name,Registration No,Reservation No,Payee";
			idColumnName = "strCheckInNo";
			flgQuerySelection = true;
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			searchFormTitle = "Bill Form";
			break;
		}
		
		
		case "usermaster": {
			columnNames = "strUserCode,strUserName";
			tableName = "from "+webStockDB+".tbluserhd where strClientCode='" + clientCode + "' ";
			listColumnNames = "User Code,User Name";
			idColumnName = "strUserCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "User Master";
			flgQuerySelection = true;
			break;
		}
		
		case "package": {
			columnNames = " strPackageCode,strPackageName,strUserCreated,strClientCode";
			tableName = "clsPackageMasterHdModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Code,Package Name,Created By,Created Date";
			idColumnName = "strPackageCode";
			searchFormTitle = "Package Master";
			break;
		}
		
		case "roomByRoomType": {
			if (req.getParameter("strRoomTypeCode") != null) {
				roomTypeCode = req.getParameter("strRoomTypeCode");
			}
			columnNames = "a.strRoomCode,a.strRoomDesc,b.strRoomTypeDesc,a.strFloorCode,a.strBedType";
			tableName = " from tblroom a,tblroomtypemaster b " + " where a.strRoomTypeCode=b.strRoomTypeCode "
					  + " and b.strRoomTypeCode='"+roomTypeCode+"' and a.strStatus='Free' and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Code,Description,Type,Floor No,Bed Type,Funiture,Extra Bed";
			idColumnName = "strRoomCode";
			flgQuerySelection = true;
			search_with="";
			searchFormTitle = "Room Master";
			break;
		}
	

		}

		mainMap.put("columnNames", columnNames);
		mainMap.put("tableName", tableName);
		mainMap.put("criteria", criteria);
		mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
		mainMap.put("listColumnNames", listColumnNames);
		mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
		mainMap.put("idColumnName", idColumnName);
		mainMap.put("searchFormTitle", searchFormTitle);
		return mainMap;
	}

	/*
	 * End PMS Search
	 */

	/**
	 * Webclub Search Start
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */
	private Map<String, Object> funGetWebClubSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();

		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String listColumnNames = "";
		String multiDocCodeSelection = "No";
		boolean flgQuerySelection = false;
		String idColumnName = "";
		String searchFormTitle = "";
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		String strShowTransOrder = objSetup.getStrShowTransAsc_Desc();
		switch (formName) {
		case "WCgroup": {
			columnNames = "strGroupCode,strGroupName,strShortName,strCategory,strCrDr";
			tableName = "clsWebClubGroupMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Group Code,Group Name,Short Name,Category,Type";
			idColumnName = "strGroupCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Group Master";
			break;
		}

		case "WCCatMaster": {
			columnNames = " strCatCode,strCatName,strGroupCategoryCode,strTenure ";
			tableName = "clsWebClubMemberCategoryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Category Code,Category Name,Group Category,Tenure";
			idColumnName = "strCatCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Category Master";
			break;
		}

		case "WCmemProfile": {
			columnNames = "strCustomerCode,strMemberCode,strFullName,dteCreatedDate,strCategoryCode";
			tableName = "clsWebClubMemberProfileModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Member Code,Member Name,Date,Customer,Category";
			idColumnName = "strCustomerCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Profile";
			break;
		}

		case "WCmemProfileCustomer": {
			columnNames = "strCustomerCode,strMemberCode,strFullName,strFirstName,strCategoryCode";
			tableName = "clsWebClubMemberProfileModel where strClientCode='" + clientCode + "' and strCustomerCode=strPrimaryCustomerCode ";
			listColumnNames = "Customer,Member Code,Member Name,First Name,Category";
			idColumnName = "strCustomerCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Profile";
			break;
		}

		case "WCAreaMaster": {
			columnNames = "a.strAreaCode,a.strAreaName,a.strCityCode, b.strCityName ";
			tableName = "from tblareamaster a ,tblcitymaster b where a.strCityCode=b.strCityCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Area Code,Area Name,City Code,City Name ";
			idColumnName = "a.strAreaCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Area Master";
			flgQuerySelection = true;
			break;
		}

		case "WCResAreaMaster": {
			columnNames = "a.strAreaCode,a.strAreaName,a.strCityCode, b.strCityName ";
			tableName = "from tblareamaster a ,tblcitymaster b where a.strCityCode=b.strCityCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Area Code,Area Name,City Code,City Name ";
			idColumnName = "a.strAreaCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Area Master";
			flgQuerySelection = true;
			break;
		}

		case "WCComAreaMaster": {
			columnNames = "a.strAreaCode,a.strAreaName,a.strCityCode, b.strCityName ";
			tableName = "from tblareamaster a ,tblcitymaster b where a.strCityCode=b.strCityCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Area Code,Area Name,City Code,City Name ";
			idColumnName = "a.strAreaCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Area Master";
			flgQuerySelection = true;
			break;
		}

		case "WCBillingAreaMaster": {
			columnNames = "a.strAreaCode,a.strAreaName,a.strCityCode, b.strCityName ";
			tableName = "from tblareamaster a ,tblcitymaster b where a.strCityCode=b.strCityCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Area Code,Area Name,City Code,City Name ";
			idColumnName = "a.strAreaCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Area Master";
			flgQuerySelection = true;
			break;
		}

		case "WCCityMaster": {
			columnNames = "a.strCityCode,a.strCityName,b.strCountryName, c.strStateName ";
			tableName = "from tblcitymaster a ,tblcountrymaster b ,tblstatemaster c " + " where a.strCountryCode=b.strCountryCode and a.strStateCode=c.strStateCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "City Code,City Name,State Name,Country Name ";
			idColumnName = "a.strCityCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "City Master";
			flgQuerySelection = true;
			break;
		}

		case "WCResCityMaster": {
			columnNames = "a.strCityCode,a.strCityName,b.strCountryName, c.strStateName ";
			tableName = "from tblcitymaster a ,tblcountrymaster b ,tblstatemaster c " + " where a.strCountryCode=b.strCountryCode and a.strStateCode=c.strStateCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "City Code,City Name,State Name,Country Name ";
			idColumnName = "a.strCityCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "City Master";
			flgQuerySelection = true;
			break;
		}

		case "WCComCityMaster": {
			columnNames = "a.strCityCode,a.strCityName,b.strCountryName, c.strStateName ";
			tableName = "from tblcitymaster a ,tblcountrymaster b ,tblstatemaster c " + " where a.strCountryCode=b.strCountryCode and a.strStateCode=c.strStateCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "City Code,City Name,State Name,Country Name ";
			idColumnName = "a.strCityCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "City Master";
			flgQuerySelection = true;
			break;
		}

		case "WCBillingCityMaster": {
			columnNames = "a.strCityCode,a.strCityName,b.strCountryName, c.strStateName ";
			tableName = "from tblcitymaster a ,tblcountrymaster b ,tblstatemaster c " + " where a.strCountryCode=b.strCountryCode and a.strStateCode=c.strStateCode and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "City Code,City Name,State Name,Country Name ";
			idColumnName = "a.strCityCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "City Master";
			flgQuerySelection = true;
			break;
		}

		case "WCCountryMaster": {
			columnNames = "strCountryCode,strCountryName ";
			tableName = "clsWebClubCountryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Country Code,Country Name ";
			idColumnName = "strCountryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Counntry Master";
			break;
		}

		case "WCResCountryMaster": {
			columnNames = "strCountryCode,strCountryName ";
			tableName = "clsWebClubCountryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Country Code,Country Name ";
			idColumnName = "strCountryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Counntry Master";
			break;
		}

		case "WCComCountryMaster": {
			columnNames = "strCountryCode,strCountryName ";
			tableName = "clsWebClubCountryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Country Code,Country Name ";
			idColumnName = "strCountryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Counntry Master";
			break;
		}

		case "WCBillingCountryMaster": {
			columnNames = "strCountryCode,strCountryName ";
			tableName = "clsWebClubCountryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Country Code,Country Name ";
			idColumnName = "strCountryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Counntry Master";
			break;
		}

		case "WCRegionMaster": {
			columnNames = "strRegionCode,strRegionName ";
			tableName = "clsWebClubRegionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Region Code,Region Name ";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}

		case "WCResRegionMaster": {
			columnNames = "strRegionCode,strRegionName ";
			tableName = "clsWebClubRegionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Region Code,Region Name ";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}

		case "WCComRegionMaster": {
			columnNames = "strRegionCode,strRegionName ";
			tableName = "clsWebClubRegionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Region Code,Region Name ";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}

		case "WCBillingRegionMaster": {
			columnNames = "strRegionCode,strRegionName ";
			tableName = "clsWebClubRegionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Region Code,Region Name ";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}

		case "WCStateMaster": {
			columnNames = " a.strStateCode,a.strStateName,b.strRegionName,c.strCountryName ";
			tableName = " from tblstatemaster a, tblregionmaster b, tblcountrymaster c  " + " where a.strCountryCode=c.strCountryCode and a.strRegionCode=b.strRegionCode " + " and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "State Code,State Name,Region Name,Country Name ";
			idColumnName = "strRegionCode";
			searchFormTitle = "State Master";
			flgQuerySelection = true;
			break;
		}

		case "WCResStateMaster": {
			columnNames = " a.strStateCode,a.strStateName,b.strRegionName,c.strCountryName ";
			tableName = " from tblstatemaster a, tblregionmaster b, tblcountrymaster c  " + " where a.strCountryCode=c.strCountryCode and a.strRegionCode=b.strRegionCode " + " and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "State Code,State Name,Region Name,Country Name ";
			idColumnName = "strRegionCode";
			searchFormTitle = "State Master";
			flgQuerySelection = true;
			break;
		}

		case "WCComStateMaster": {
			columnNames = " a.strStateCode,a.strStateName,b.strRegionName,c.strCountryName ";
			tableName = " from tblstatemaster a, tblregionmaster b, tblcountrymaster c  " + " where a.strCountryCode=c.strCountryCode and a.strRegionCode=b.strRegionCode " + " and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "State Code,State Name,Region Name,Country Name ";
			idColumnName = "strRegionCode";
			searchFormTitle = "State Master";
			flgQuerySelection = true;
			break;
		}

		case "WCBillingStateMaster": {
			columnNames = " a.strStateCode,a.strStateName,b.strRegionName,c.strCountryName ";
			tableName = " from tblstatemaster a, tblregionmaster b, tblcountrymaster c  " + " where a.strCountryCode=c.strCountryCode and a.strRegionCode=b.strRegionCode " + " and a.strClientCode='" + clientCode + "' ";
			listColumnNames = "State Code,State Name,Region Name,Country Name ";
			idColumnName = "strRegionCode";
			searchFormTitle = "State Master";
			flgQuerySelection = true;
			break;
		}

		case "WCEducationMaster": {
			columnNames = " strEducationCode ,strEducationDesc  ";
			tableName = " clsWebClubEducationMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Education Code,Education Desc ";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			idColumnName = "strEducationCode";
			searchFormTitle = "Education Master";

			break;
		}

		case "WCMaritalMaster": {
			columnNames = "strMaritalCode,strMaritalName ";
			tableName = " clsWebClubMaritalStatusModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Marital Code,Marital Name ";
			idColumnName = "strMaritalCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Marital Status Master";
			break;
		}

		case "WCProfessionMaster": {
			columnNames = "strProfessionCode,strProfessionName ";
			tableName = " clsWebClubProfessionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Profession Code,Profession Name ";
			idColumnName = "strProfessionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Profession Master";
			break;
		}

		case "WCDependentProfessionMaster": {
			columnNames = "strProfessionCode,strProfessionName ";
			tableName = " clsWebClubProfessionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Profession Code,Profession Name ";
			idColumnName = "strProfessionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Profession Master";
			break;
		}

		case "WCDesignationMaster": {
			columnNames = "strDesignationCode,strDesignationName ";
			tableName = " clsWebClubDesignationMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Designation Code,Designation Name ";
			idColumnName = "strDesignationCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Designation Master";
			break;
		}

		case "WCReasonMaster": {
			columnNames = "strReasonCode,strReasonDesc ";
			tableName = " clsWebClubReasonMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Reason Code,Reason Desc ";
			idColumnName = "strReasonCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Reason Master";
			break;
		}

		case "WCDependentReasonMaster": {
			columnNames = "strReasonCode,strReasonDesc ";
			tableName = " clsWebClubReasonMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Reason Code,Reason Desc ";
			idColumnName = "strReasonCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Reason Master";
			break;
		}

		case "WCBlockReasonMaster": {
			columnNames = "strReasonCode,strReasonDesc ";
			tableName = " clsWebClubReasonMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Reason Code,Reason Desc ";
			idColumnName = "strReasonCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Reason Master";
			break;
		}

		case "WCSpouseProfessionMaster": {
			columnNames = "strProfessionCode,strProfessionName ";
			tableName = " clsWebClubProfessionMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Profession Code,Profession Name ";
			idColumnName = "strProfessionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Profession Master";
			break;
		}

		case "WCCompanyCode": {
			columnNames = "strCompanyCode,strCompanyName,strMemberCode,strCategoryCode  ";
			tableName = " clsWebClubCompanyMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Company Code,Company Name , Member Code, Category Code";
			idColumnName = "strCompanyCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Company Master";
			break;
		}

		case "WCSubCategoryMaster": {
			columnNames = "strSCCode,strSCName,strSCDesc ";
			tableName = " clsWebClubSubCategoryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "SubCategory Code,SubCategory Name , SubCategory Desc";
			idColumnName = "strSCCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Sub Category Master";
			break;
		}

		case "WCCompanyTypeMaster": {
			columnNames = "strCompanyTypeCode,strCompanyName";
			tableName = " clsWebClubCompanyTypeMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Company Type Code,Company Type Name ";
			idColumnName = "strCompanyTypeCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Company Type Master";
			break;
		}

		case "WCLockerMaster": {
			columnNames = "strLockerCode,strLockerName";
			tableName = " clsWebClubLockerMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Locker Code,Locker Name ";
			idColumnName = "strLockerCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Locker Master";
			break;
		}

		case "WCMemberForm": {
			columnNames = "strFormNo,strProspectName,strCategoryCode,dtePrintDate";
			tableName = " clsWebClubMemberFormGenerationModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Form No,Prospect Name,Category Code,Print Date ";
			idColumnName = "strFormNo";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Form Generation";

			break;
		}

		case "WCCommitteeMemberRole": {
			// columnNames = "strRoleCode,strRoleDesc,intRoleRank ";
			// tableName =
			// " clsWebClubCommitteeMemberRoleMasterModel where strClientCode='"+clientCode+"' ";
			// listColumnNames="Role Code,Role Desc,Role rank ";
			// idColumnName="strRoleCode";
			// criteria = getCriteriaQuery(columnNames,search_with,tableName);
			// searchFormTitle="Member Role Master";
			//
			//
			columnNames = "strRoleCode,strRoleDesc,intRoleRank";
			tableName = "from tblcommitteememberrolemaster where strClientCode='" + clientCode + "' ";
			listColumnNames = "Role Code,Role Name,Role rank ";
			idColumnName = "strRoleCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Role Master";
			flgQuerySelection = true;
			break;

		}

		case "WCRelationMaster": {
			columnNames = "strRelationCode,strRelation ";
			tableName = " clsWebClubRelationMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Relation Code,Relation Desc ";
			idColumnName = "strRelationCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Relation Master";
			break;
		}

		case "WCStaffMaster": {
			columnNames = "strStaffCode,strStaffName ";
			tableName = " clsWebClubStaffMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Staff Code,Staff Name ";
			idColumnName = "strStaffCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Staff Master";
			break;
		}

		case "WCCurrencyDetailsMaster": {
			columnNames = "strCurrCode,strDesc ";
			tableName = " clsWebClubCurrencyDetailsMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Currency Code,Currency Details ";
			idColumnName = "strCurrencyDetailsCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Currency Details Master";
			break;
		}

		case "WCInvitedByMaster": {
			columnNames = "strInvCode,strInvName";
			tableName = " clsWebClubInvitedByMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Inv Code,Inv Desc ";
			idColumnName = "strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invitation Master";
			break;
		}

		case "WCItemCategoryMaster": {
			columnNames = "strItemCategoryCode,strItemCategoryName ";
			tableName = " clsWebClubItemCategoryMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Item Category Code,Item Category Name ";
			idColumnName = "strItemCategoryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Item Category Master";
			break;
		}
		case "WCProfileMaster": {
			columnNames = "strProfileCode,strProfileDesc ";
			tableName = " clsWebClubProfileMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Profile Code,Profile Desc ";
			idColumnName = "strProfileCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Profile Master";
			break;
		}
		case "WCSalutationMaster": {
			columnNames = "strSalutationCode,strSalutationDesc ";
			tableName = " clsWebClubSalutationMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Salutation Code,Salutation Desc ";
			idColumnName = "strSalutationCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Salutation Master";
			break;
		}
		case "WCTitleMaster": {
			columnNames = "strTitleCode,strTitleDesc ";
			tableName = " clsWebClubTitleMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Title Code,Title Desc ";
			idColumnName = "strTitleCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Title Master";
			break;
		}
		case "usermaster": {
			columnNames = "a.strUserCode,a.strUserName";
			tableName = "from "+webStockDB+".tbluserhd a where a.strClientCode='" + clientCode + "'";
			listColumnNames = "User Code,User Name";
			idColumnName = "strUserCode";
			searchFormTitle = "User Master";
			flgQuerySelection = true;
			break;
		}
		}

		mainMap.put("columnNames", columnNames);
		mainMap.put("tableName", tableName);
		mainMap.put("criteria", criteria);
		mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
		mainMap.put("listColumnNames", listColumnNames);
		mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
		mainMap.put("idColumnName", idColumnName);
		mainMap.put("searchFormTitle", searchFormTitle);
		return mainMap;
	}

	/*
	 * End WebClub Search
	 */

	/**
	 * CRM Search Start
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> funGetCRMSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strLocCode = req.getSession().getAttribute("locationCode").toString();
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String listColumnNames = "";
		String multiDocCodeSelection = "No";
		boolean flgQuerySelection = false;
		String idColumnName = "";
		String searchFormTitle = "";
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		String strShowTransOrder = objSetup.getStrShowTransAsc_Desc();
		JSONArray jArrSearchList = null;
		List<Object[]> listSearchData = new ArrayList<Object[]>();
		if (!strShowTransOrder.equals("Asc")) {
			strShowTransOrder = "DESC";
		} else {
			strShowTransOrder = "ASC";
		}

		String showAllProd = "Y";
		if (null == req.getSession().getAttribute("showAllProdToAllLoc")) {
			showAllProd = "N";
		} else {
			showAllProd = "Y";
		}

		// String
		// searchText=req.getSession().getAttribute("searchText").toString();

		switch (formName) {
		case "custMaster": {
			columnNames = "strPCode,strPName,strMobile,strEmail,strContact,strPNHindi";
			tableName = "clsPartyMasterModel where  strClientCode='" + clientCode + "' and strPType='cust' ";

			if (!objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
				tableName += " and strPropCode='" + propCode + "' ";
			}

			tableName += "ORDER BY strPCode " + strShowTransOrder + " ";
			listColumnNames = "Customer Code,Customer Name,Mobile No,Email,Contact Person,Marathi Name";
			idColumnName = "strPCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Customer Master";
			break;
		}

		case "custMasterActive": {
			columnNames = "strPCode,strPName,strMobile,strEmail,strContact,strPNHindi";
			tableName = "clsPartyMasterModel where  strClientCode='" + clientCode + "' and strPType='cust' and strOperational='Y' ";

			if (!objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
				tableName += " and strPropCode='" + propCode + "' ";
			}

			tableName += "ORDER BY strPCode " + strShowTransOrder + " ";
			listColumnNames = "Customer Code,Customer Name,Mobile No,Email,Contact Person,Marathi Name";
			idColumnName = "strPCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Customer Master";
			break;
		}

		case "salesorder": {
			// columnNames =
			// "a.strSOCode,a.dteSODate,a.strCustPONo,strCustCode,strLocCode";
			columnNames = "a.strSOCode,a.dteSODate,a.strCustPONo,b.strPName,c.strLocName,a.strStatus ";
			tableName = "clsSalesOrderHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
			
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Sales Order")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='No'";
			}
			listColumnNames = "SO Code,So Date, CustomerPONo,Customer Name,Location Name,Order Type";
			idColumnName = "strSOCode";
			// flgQuerySelection=true;
			searchFormTitle = "Sales Order";
			break;
		}

		case "productmaster": {
			// columnNames =
			// "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType"
			// +
			// ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem";
			// tableName =
			// "clsProductMasterModel a,clsProductReOrderLevelModel b , "
			// + " clsSubGroupMasterModel c,clsGroupMasterModel d "
			// +
			// " where a.strProdCode=b.strProdCode and b.strLocationCode='"+strLocCode+"' "
			// + " and a.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			// +
			// " and a.strClientCode='"+clientCode+"' and b.strClientCode='"+clientCode+"' ";
			// listColumnNames="Product Code,Product Name,Sub Group,Group,UOM,Product Type,Specification,Cal Amt On"
			// + ",Class,Non Stockable";
			// idColumnName="a.strProdCode";
			// searchFormTitle="Product Master";
			// break;

			columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
			tableName = "clsProductMasterModel a , clsSubGroupMasterModel c , clsGroupMasterModel d ";
			if (showAllProd.equals("N")) {
				tableName = tableName + " ,clsProductReOrderLevelModel b " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + " where  ";
			}

			tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;

		}

		case "customerProduct": {
			// columnNames =
			// "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType"
			// +
			// ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem";
			// tableName =
			// "clsProductMasterModel a,clsProductReOrderLevelModel b , "
			// + " clsSubGroupMasterModel c,clsGroupMasterModel d "
			// +
			// " where a.strProdCode=b.strProdCode and b.strLocationCode='"+strLocCode+"' "
			// + " and a.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			// +
			// " and a.strClientCode='"+clientCode+"' and b.strClientCode='"+clientCode+"' ";
			// listColumnNames="Product Code,Product Name,Sub Group,Group,UOM,Product Type,Specification,Cal Amt On"
			// + ",Class,Non Stockable";
			// idColumnName="a.strProdCode";
			// searchFormTitle="Product Master";
			// break;

			columnNames = "ps.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
			tableName = "clsProductMasterModel a , clsSubGroupMasterModel c , clsGroupMasterModel d,clsProdSuppMasterModel ps ";
			if (showAllProd.equals("N")) {
				tableName = tableName + " ,clsProductReOrderLevelModel b " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + " where  ";
			}

			tableName = tableName + " a.strProdCode=ps.strProdCode and a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;

		}

		case "locationmaster": {
			columnNames = "strLocCode,strLocName,strType,strLocDesc";
			tableName = "clsLocationMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Location Code,Location Name,Type,Description";
			idColumnName = "strLocCode";
			searchFormTitle = "Location Master";
			break;
		}

		case "ProductionOrder": {
			columnNames = "strOPCode,dtOPDate,strStatus,dtFulmtDate,dtfulfilled,strNarration,strUserCreated,dtDateCreated";
			tableName = "clsProductionOrderHdModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strOPCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap<String, Boolean>) (req.getSession().getAttribute("hmAuthorization"));
				if (hmAuthorization.get("Production Order")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and strAuthorise='No'";
			}
			listColumnNames = "Production Order Code,Production Order Date,Fulfilment Date,Fulfilled Date,Narration,User Created,Date Created";
			idColumnName = "strOPCode";
			searchFormTitle = "Production Order";
			break;
		}

		case "subContractor": {
			columnNames = "strPCode,strPName,strMobile,strEmail,strContact";
			tableName = "clsPartyMasterModel where strPropCode='" + propCode + "' and  strClientCode='" + clientCode + "' and strPType='subc'";
			listColumnNames = "Contractor Code,Contractor Name,Mobile No,Email,Contact Person";
			idColumnName = "strPCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Sub Contractor Master";
			break;
		}

		case "JOCode": {
			columnNames = "a.strJOCode,a.dteJODate,a.strSOCode,b.strProdName,a.dblQty,a.strParentJOCode,a.strStatus,b.strBarCode";
			tableName = " from tbljoborderhd a, tblproductmaster b " + "where a.strProdCode=b.strProdCode and a.strSOCode='' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'  ";
			listColumnNames = "Job Order Code,Date,SO Code,Product Name,Quantity,Parent Job Order Code,Status,Code";
			idColumnName = "strJOCode";
			flgQuerySelection = true;
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Job Order";
			break;
		}

		case "AllJobOrder": {
			columnNames = "a.strJOCode,a.dteJODate,a.strSOCode,b.strProdName,a.dblQty,a.strParentJOCode,a.strStatus,b.strBarCode";
			tableName = " from tbljoborderhd a, tblproductmaster b " + "where a.strProdCode=b.strProdCode and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' ";
			listColumnNames = "Job Order Code,Date,SO Code,Product Name,Quantity,Parent Job Order Code,Status,Code";
			idColumnName = "strJOCode";
			flgQuerySelection = true;
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Job Order";
			break;
		}

		case "deliveryChallan": {

			columnNames = "a.strDCCode,a.strSOCode,a.dteDCDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsDeliveryChallanHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode and a.strCloseDC='N' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strDCCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Delivery Challan")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='No'";
			}
			listColumnNames = "DC Code,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strDCCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Delivery Challan";
			break;
		}

		case "JACode": {
			columnNames = "a.strJACode,a.strJANo,b.strPName,a.dteJADate,a.strRef,a.dteRefDate,a.strDispatchMode,a.strPayment,a.strTaxes";
			tableName = " clsJOAllocationHdModel a, clsPartyMasterModel b " + " where a.strSCCode=b.strPCode and a.strClientCode='" + clientCode + "' " + " and a.strClientCode=b.strClientCode ";
			listColumnNames = "JA Code,JA NO.,Sub Contractor Name,Date,Ref,Ref Date,Dispatcher Mode,Payment,Taxes";
			idColumnName = "a.strJACode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Job Order Allocation";
			break;
		}

		case "suppcode": {
			columnNames = "strPCode,strPName,strMobile,strEmail,strContact";
			if (objSetup.getStrShowAllPartyToAllLoc().equalsIgnoreCase("Y")) {
			tableName = "clsSupplierMasterModel where   strClientCode='" + clientCode + "' and strPType='supp' or strPType='' ";
			listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person";
			idColumnName = "strPCode";
			}else{
				tableName = "clsSupplierMasterModel where strPropCode='" + propCode + "' and strClientCode='" + clientCode + "' and strPType='supp' or strPType='' ";
				listColumnNames = "Supplier Code,Supplier Name,Mobile No,Email-id,Contact Person";
				idColumnName = "strPCode";
			}
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Supplier Master";
			break;
		}

		case "processcode": {
			columnNames = "strProcessCode,strProcessName,strDesc";
			tableName = "clsProcessMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Process Code,Process Name,Description";
			idColumnName = "strProcessCode";
			searchFormTitle = "Process Master";
			break;
		}

		case "DNCode": {
			columnNames = "strDNCode,dteDNDate,strDNType" + ",strJACode,strPName as SCName ,ifnull(b.strLocName,ifnull(c.strPName,''))  as strLocName,dteExpDate";
			tableName = "from tbldeliverynotehd a, tbllocationmaster b , tblpartymaster c " + "	where a.strLocCode=b.strLocCode or a.strLocCode=c.strPCode and a.strClientCode='" + clientCode + "'" + " and a.strSCCode=c.strPCode and b.strClientCode=a.strClientCode " + " and c.strClientCode=a.strClientCode GROUP BY a.strDNCode ";
			listColumnNames = "Delivery Note Code,Date,Type,JA Code,Contractor Name,Location Name,Exp Date";
			idColumnName = "strDNCode";
			flgQuerySelection = true;
			searchFormTitle = "Delivery Note";
			break;
		}

		case "SCDNCode": {
			columnNames = " a.strDNCode ,a.strSCCode ,b.strPName,a.dteDNDate ";
			tableName = " clsDeliveryNoteHdModel a , clsPartyMasterModel b " + "	where a.strSCCode=b.strPCode and a.strClientCode=b.strClientCode and " + " a.strClientCode='" + clientCode + "'and a.strSCCode='" + strSubConCode + "' ";

			listColumnNames = "Delivery Note Code,Sub Contractor Code,Sub Contractor Name,Delivery Note Date";
			idColumnName = "a.strDNCode";
			searchFormTitle = "Delivery Note Against Supplier";
			break;
		}

		case "SCGRNCode": {
			columnNames = " a.strSRCode,a.dteSRDate,a.strSRNo,b.strPName,a.strSCDNCode  ";
			tableName = " clsSubContractorGRNModelHd a , clsPartyMasterModel b " + "	where a.strSCCode=b.strPCode and a.strClientCode=b.strClientCode and " + " a.strClientCode='" + clientCode + "' ";

			listColumnNames = "SC GRN Code,SC GRN Date,Manual no,Suppplier Name,SC DC Code";
			idColumnName = "a.strSRCode";
			searchFormTitle = "SubContractor GRN";
			break;
		}

		case "expProductmaster": {
			columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem";
			tableName = "clsProductMasterModel a, " + "  clsSubGroupMasterModel c,clsGroupMasterModel d ";

			if (showAllProd.equals("N")) {
				tableName = tableName + " , clsProductReOrderLevelModel b  " + " where a.strProdCode=b.strProdCode and b.strLocationCode='" + strLocCode + "' and b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + " where  ";
			}
			tableName = tableName + " a.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " and a.strClientCode='" + clientCode + "' and a.strProdType='Sub-Contracted'  ";
			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;
		}

		case "invoice": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode " + "and a.strInvCode like '%IV%' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Invoice")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='No'";
			}
			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}

		case "invoiceForDC": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " 
				+ " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' "
				+ " and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode and a.strCloseIV='N' and a.strInvCode like '%IV%' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Delivery Challan")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='Yes'";
			}

			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}
		
		case "invoiceAll": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}
			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}
		
		case "salesReturn": {
			columnNames = "a.strSRCode,a.dteSRDate,b.strPName,c.strLocName ";
			tableName = "clsSalesReturnHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strSRCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Sales Return")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='No'";
			}
			listColumnNames = "SR Code,SR Date,Customer Name,Location Name";
			idColumnName = "strSOCode";
			// flgQuerySelection=true;
			searchFormTitle = "Sales Order";
			break;
		}		

		case "taxmaster": {
			columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
			tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strPropertyCode='" + propertyCode + "' ";
			listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
			idColumnName = "strTaxCode";
			searchFormTitle = "Tax Master";
			break;
		}

		case "VehCode": {
			columnNames = "a.strVehCode,a.strVehNo,a.strUserCreated,a.dtCreatedDate ";
			tableName = " clsVehicleMasterModel a  " + " where  a.strClientCode='" + clientCode + "' ";
			listColumnNames = "Vehicle Code,Vehicle No,Created Name,Created date ";

			idColumnName = "a.strVehCode";
			searchFormTitle = "Vehicle Master";
			break;
		}

		case "productProduced": {
			columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
			tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
			if (showAllProd.equals("N")) {
				tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + "  where   ";
			}
			tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and (a.strProdType = 'Produced' or a.strForSale='Y') " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
			if (!search_with.equals("")) {
				tableName = tableName + " and a.strSGCode='" + search_with + "' ";
			}

			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;
		}

		case "nonindicatortax": {
			columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
			tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxIndicator=''";
			listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
			idColumnName = "strTaxCode";
			searchFormTitle = "Tax Master";
			break;
		}

		case "OpenTaxesForPurchase": {
			columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
			tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Purchase' and strTaxType='Fixed Amount' ";
			listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
			idColumnName = "strTaxCode";
			searchFormTitle = "Tax Master";
			break;
		}

		case "OpenTaxesForSales": {
			columnNames = "strTaxCode,strTaxDesc,strTaxIndicator";
			tableName = "clsTaxHdModel where strClientCode='" + clientCode + "' and strTaxOnSP='Sales' and strTaxType='Fixed Amount' ";
			listColumnNames = "Tax Code,Tax Desc,Tax Indicator";
			idColumnName = "strTaxCode";
			searchFormTitle = "Tax Master";
			break;
		}

		case "usermaster": {
			columnNames = "strUserCode,strUserName";
			tableName = "clsUserMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "User Code,User Name";
			idColumnName = "strUserCode";
			searchFormTitle = "User Master";
			break;
		}

		case "productSubContracted": {
			columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem";
			tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
			if (showAllProd.equals("N")) {
				tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode and  b.strLocationCode='" + strLocCode + "' and " + " b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + "  where   ";
			}
			tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strProdType='Sub-Contracted' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;
		}

		case "RawProduct": {
			String loctemp = strLocCode;

			columnNames = "a.strProdCode,a.strProdName,c.strSGName,d.strGName,a.strUOM,a.strProdType,a.strBarCode" + ",a.strSpecification,a.strCalAmtOn,a.strClass,a.strNonStockableItem,a.strPartNo";
			tableName = "clsProductMasterModel a, clsSubGroupMasterModel c , clsGroupMasterModel d ";
			if (showAllProd.equals("N")) {
				tableName = tableName + " ,clsProductReOrderLevelModel b " + " where  a.strProdCode=b.strProdCode " + "and b.strLocationCode='" + loctemp + "' and  b.strClientCode='" + clientCode + "' and ";
			} else {
				tableName = tableName + "  where   ";
			}
			tableName = tableName + "  a.strSGCode=c.strSGCode and c.strGCode=d.strGCode and a.strNotInUse='N' and a.strProdType='Procured' " + " and a.strClientCode='" + clientCode + "'  and c.strClientCode='" + clientCode + "' and d.strClientCode='" + clientCode + "'";
			listColumnNames = "Product Code,Product Name,Sub Group,Group,UOM,Product Type,Code,Specification,Cal Amt On" + ",Class,Non Stockable,PartNo";
			idColumnName = "a.strProdCode";
			searchFormTitle = "Product Master";
			break;
		}

		case "settlementMaster": {
			columnNames = "strSettlementCode,strSettlementDesc,strSettlementType";
			tableName = "clsSettlementMasterModel where strClientCode='" + clientCode + "'";
			listColumnNames = "Settlement Code,Settlement Desc,Settlement Type";
			idColumnName = "strSettlementCode";
			searchFormTitle = "Settlement Master";
			break;
		}

		case "subgroup": {
			columnNames = "strSGCode,strSGName,intSortingNo";
			tableName = "select strSGCode,strSGName,intSortingNo from tblsubgroupmaster where strClientCode='" + clientCode + "' ";
			listColumnNames = "Sub-Group Code,Sub-Group Name,Sorting No";
			idColumnName = "strSGCode";
			searchFormTitle = "Sub Group Master";
			flgQuerySelection = true;
			break;
		}

		case "invoiceRetail": {
			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + " and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode " + " and  a.strInvCode like '%RB%'  ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}
			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}

		case "property": {
			columnNames = "strPropertyCode,propertyName ";
			tableName = "clsPropertyMaster where strClientCode='" + clientCode + "'";
			listColumnNames = "Property Code,Property Name";
			idColumnName = "strPropertyCode";
			searchFormTitle = "Property Master";
			break;
		}

		case "salesPersonCode": {
			columnNames = "strSalesPersonCode,strSalesPersonName";
			tableName = "select strSalesPersonCode,strSalesPersonName from tblsalesperson where strClientCode='" + clientCode + "' ";
			listColumnNames = "Sales Person Code ,Sales Person Name ";
			idColumnName = "strSalesPersonCode";
			searchFormTitle = "Sales Person  Master";
			flgQuerySelection = true;
			break;
		}
		
		case "proformaInvoice": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsProFormaInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode " + "and a.strInvCode like '%IV%' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}

			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}
		
		case "invoiceslip": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode " + "and a.strInvCode like '%IV%' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}

			listColumnNames = "InvoiceCode,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}
		
		case "salesorderslip": {
			// columnNames =
			// "a.strSOCode,a.dteSODate,a.strCustPONo,strCustCode,strLocCode";
			columnNames = "a.strSOCode,a.dteSODate,a.strCustPONo,b.strPName,c.strLocName,a.strStatus ";
			tableName = "clsSalesOrderHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
			listColumnNames = "SO Code,So Date, CustomerPONo,Customer Name,Location Name,Order Type";
			idColumnName = "strSOCode";
			// flgQuerySelection=true;
			searchFormTitle = "Sales Order";
			break;
		}
		
		case "salesReturnslip": {
			columnNames = "a.strSRCode,a.dteSRDate,b.strPName,c.strLocName ";
			tableName = "clsSalesReturnHdModel a,clsPartyMasterModel b,clsLocationMasterModel c " + "where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and a.strClientCode=c.strClientCode  ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strSRCode like '" + propertyCode + "%' ";
			}
			listColumnNames = "SR Code,SR Date,Customer Name,Location Name";
			idColumnName = "strSOCode";
			// flgQuerySelection=true;
			searchFormTitle = "Sales Order";
			break;
		}
		
		case "DNCodeslip": {
			columnNames = "strDNCode,dteDNDate,strDNType" + ",strJACode,strPName as SCName ,ifnull(b.strLocName,ifnull(c.strPName,''))  as strLocName,dteExpDate";
			tableName = "from tbldeliverynotehd a, tbllocationmaster b , tblpartymaster c " + "	where a.strLocCode=b.strLocCode or a.strLocCode=c.strPCode and a.strClientCode='" + clientCode + "'" + " and a.strSCCode=c.strPCode and b.strClientCode=a.strClientCode " + " and c.strClientCode=a.strClientCode GROUP BY a.strDNCode ";
			listColumnNames = "Delivery Note Code,Date,Type,JA Code,Contractor Name,Location Name,Exp Date";
			idColumnName = "strDNCode";
			flgQuerySelection = true;
			searchFormTitle = "Delivery Note";
			break;
		}
		
		case "deliveryChallanslip": {

			columnNames = "a.strDCCode,a.strSOCode,a.dteDCDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsDeliveryChallanHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " + " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' " + "and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode and a.strCloseDC='N' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strDCCode like '" + propertyCode + "%' ";
			}
			listColumnNames = "DC Code,SO Code,DC Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strDCCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Delivery Challan";
			break;
		}

		case "JOCodeslip": {
			columnNames = "a.strJOCode,a.dteJODate,a.strSOCode,b.strProdName,a.dblQty,a.strParentJOCode,a.strStatus,b.strBarCode";
			tableName = " from tbljoborderhd a, tblproductmaster b " + "where a.strProdCode=b.strProdCode and a.strSOCode='' and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "'  ";
			listColumnNames = "Job Order Code,Date,SO Code,Product Name,Quantity,Parent Job Order Code,Status,Code";
			idColumnName = "strJOCode";
			flgQuerySelection = true;
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Job Order";
			break;
		}

		case "SCGRNCodeslip": {
			columnNames = " a.strSRCode,a.dteSRDate,a.strSRNo,b.strPName,a.strSCDNCode  ";
			tableName = " clsSubContractorGRNModelHd a , clsPartyMasterModel b " + "	where a.strSCCode=b.strPCode and a.strClientCode=b.strClientCode and " + " a.strClientCode='" + clientCode + "' ";

			listColumnNames = "SC GRN Code,SC GRN Date,Manual no,Suppplier Name,SC DC Code";
			idColumnName = "a.strSRCode";
			searchFormTitle = "SubContractor GRN";
			break;
		}

		case "JACodeslip": {
			columnNames = "a.strJACode,a.strJANo,b.strPName,a.dteJADate,a.strRef,a.dteRefDate,a.strDispatchMode,a.strPayment,a.strTaxes";
			tableName = " clsJOAllocationHdModel a, clsPartyMasterModel b " + " where a.strSCCode=b.strPCode and a.strClientCode='" + clientCode + "' " + " and a.strClientCode=b.strClientCode ";
			listColumnNames = "JA Code,JA NO.,Sub Contractor Name,Date,Ref,Ref Date,Dispatcher Mode,Payment,Taxes";
			idColumnName = "a.strJACode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Job Order Allocation";
			break;
		}
		
				
		case "SundryDebtorWeb-Service": {
			listColumnNames = "Debtor Code , Debtor Name ";
			searchFormTitle = "Debtor Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "SundryCreditorWeb-Service": {
			listColumnNames = "Creditor Code , Creditor Name ";
			searchFormTitle = "Creditor Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "AccountMasterWeb-Service": {
			listColumnNames = "Account Code , Account Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "AccountMasterGLOnlyWeb-Service": {
			listColumnNames = "Account Code , Account Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "suppLinkedWeb-Service": {
			listColumnNames = "Supplier Code , Supplier Name ,Account Code, Account Name ";
			searchFormTitle = "Linked Supplier to Account";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "TaxWeb-Service": {
			listColumnNames = "Account Code , Account Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "DiscountWeb-Service": {
			listColumnNames = "Sr No , Discount Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "RoundOffWeb-Service": {
			listColumnNames = "Sr No  , Discount Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "ExtraChargeWeb-Service": {
			listColumnNames = " SrNo. , Discount Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		
		case "SettlementWeb-Service": {
			listColumnNames = " SrNo. , Account Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		
		case "CustomerAccCodeWeb-Service" :
		{
			listColumnNames = " Account Code , Account Name ";
			searchFormTitle = "Account Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}


		case "invoiceForSR": {

			columnNames = "a.strInvCode,a.strSOCode,a.dteInvDate,b.strPName,c.strLocName,a.strAuthorise";
			tableName = " clsInvoiceHdModel a, clsPartyMasterModel b,clsLocationMasterModel c " 
				+ " where a.strCustCode=b.strPCode and a.strLocCode=c.strLocCode and a.strClientCode='" + clientCode + "' "
				+ " and a.strClientCode=b.strClientCode and b.strClientCode=c.strClientCode and a.strCloseIV='N' and a.strInvCode like '%IV%' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strInvCode like '" + propertyCode + "%' ";
			}
			boolean flgAuth = false;
			if (null != req.getSession().getAttribute("hmAuthorization")) {
				HashMap<String, Boolean> hmAuthorization = (HashMap) req.getSession().getAttribute("hmAuthorization");
				if (hmAuthorization.get("Invoice")) {
					flgAuth = true;
				}
			}
			if (flgAuth) {
				tableName += " and a.strAuthorise='Yes'";
			}

			listColumnNames = "InvoiceCode,SO Code,Inv Date,Party Name,Location Name, Authorise";
			idColumnName = "a.strInvCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Invoice";
			break;
		}
		
		
		case "categoryMaster": {
			columnNames = "strCategoryCode,strCategoryDesc,strUserCreated,dtCreatedDate";
			tableName = "clsCategoryMasterModel where  strClientCode='" + clientCode + "'  ";
			tableName += "ORDER BY strCategoryCode";
			listColumnNames = "Category Code,Category Name,Created User,Created Date";
			idColumnName = "strCategoryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Category Master";
			break;
		}
		
		case "crmRegionMaster": {
			columnNames = "strRegionCode,strRegionDesc,strUserCreated,dtCreatedDate ";
			tableName = " clsRegionMasterModel where  strClientCode='" + clientCode + "'  ";
			tableName += "ORDER BY strRegionCode";
			listColumnNames = "Region Code,Region Name,Created User,Created Date";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}
		
		
		}
		if (null != jArrSearchList) {
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrSearchRow = (JSONArray) jArrSearchList.get(cnt);
				Object[] arrObj = new Object[jArrSearchRow.size()];
				for (int row = 0; row < jArrSearchRow.size(); row++) {
					arrObj[row] = jArrSearchRow.get(row);
				}
				listSearchData.add(arrObj);
			}

			mainMap.put("criteria", criteria);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
			mainMap.put("listSearchData", listSearchData);
		} else {
			mainMap.put("columnNames", columnNames);
			mainMap.put("tableName", tableName);
			mainMap.put("criteria", criteria);
			mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
		}
		return mainMap;
	}

	/*
	 * End CRM Search
	 */

	/**
	 * WebBook Search Start
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */
	private Map<String, Object> funGetWebBookSearchDetail(String formName, String search_with, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String columnNames = "";
		String tableName = "";
		String criteria = "";
		String listColumnNames = "";
		String multiDocCodeSelection = "No";
		boolean flgQuerySelection = false;
		String idColumnName = "";
		String searchFormTitle = "";
		JSONArray jArrSearchList = null;
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		List<Object[]> listSearchData = new ArrayList<Object[]>();

		String webStockDB=req.getSession().getAttribute("WebStockDB").toString();
		switch (formName) {

		case "suppMasterWeb-Service": {
			listColumnNames = "Supplier Code , Supplier Name ";
			searchFormTitle = "Supplier Master";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "suppLinkedWeb-Service": {
			listColumnNames = "Supplier Code , Supplier Name ,Account Code, Account Name ";
			searchFormTitle = "Linked Supplier to Account";
			JSONObject jObjSearchData = funGetIndependentWebServiceDetails(formName, clientCode, propertyCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "bankCode": {
			columnNames = "strBankCode,strBankName,strBranch,strMICR";
			tableName = "clsBankMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Bank Code,Bank Name,Branch Name,MIRC";
			idColumnName = "strBankCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Bank Master";
			break;
		}
		case "acGroupCode": {
			columnNames = "strGroupCode,strGroupName,strShortName,strCategory,strDefaultType";
			tableName = "clsACGroupMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Group Code,Group Name,Short Name,Category,Default Type";
			idColumnName = "strGroupCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Group Master";
			break;
		}
		case "chargeCode": {
			columnNames = "strChargeCode,strChargeName,strAcctCode";
			tableName = "clsChargeMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Charge Code,Charge Name,Account Code";
			idColumnName = "strChargeCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Charge Master";
			break;
		}

		case "accountCode": {
			columnNames = "a.strAccountCode,a.strAccountName,a.strOperational,a.strType,b.strGroupName,a.strCreditor,a.strDebtor ";
			tableName = "clsWebBooksAccountMasterModel a,clsACGroupMasterModel b where a.strClientCode='" + clientCode + "' and a.strGroupCode=b.strGroupCode ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type,Group Name, Creditor,Debtor";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "accountCodeCash": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strType='Cash' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "accountCodeBank": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strType='Bank' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "debtorAccountCode": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strDebtor='Yes' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "creditorAccountCode": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strCreditor='Yes' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "acHolderCode": {
			columnNames = "strACHolderCode,strACHolderName,strDesignation,intMobileNumber,strEmailId";
			tableName = "clsAccountHolderMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "AC Holder Code,AC Holder Name,Designation,Mobile Number,Email Id";
			idColumnName = "strACHolderCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "AC Holder Master";
			break;
		}
		case "remarkCode": {
			columnNames = "strRemarkCode,strDescription,strActiveYN";
			tableName = "clsNarrationMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Remark Code,Description,ActiveYN";
			idColumnName = "strRemarkCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Remark Master";
			break;
		}
		case "interfaceCode": {
			columnNames = "strInterfaceCode,strInterfaceName,strAccountCode,strAccountName";
			tableName = "clsInterfaceMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Interface Code,Interface Name,Account Code,Account Name";
			idColumnName = "strInterfaceCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Interface Master";
			break;
		}
		case "sanctionCode": {
			columnNames = "strSanctionCode,strSanctionName,strOperational";
			tableName = "clsSanctionAutherityMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Sanction Code,Sanction Name,Operational";
			idColumnName = "strSanctionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Sanction Master";
			break;
		}
		case "debtorCode": {
			columnNames = "strDebtorCode,strPrefix,strFirstName,strMiddleName,strLastName";
			tableName = "clsSundryDebtorMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Debtor Code,Prefix,First Name,Middle Name,Last Name";
			idColumnName = "strDebtorCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Debtor Master";
			break;
		}

		case "creditorCode": {
			columnNames = "strCreditorCode,strPrefix,strFirstName,strMiddleName,strLastName";
			tableName = "clsSundaryCreditorMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Creditor Code,Prefix,First Name,Middle Name,Last Name";
			idColumnName = "strCreditorCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Creditor Master";
			break;
		}

		case "categoryCode": {
			columnNames = "strCatCode,strCatName,strGroupCategoryCode,strcatdesc";
			tableName = "clsCategoryMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Category Code,Category Name,Category Group Code,Category Desc";
			idColumnName = "strCatCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Debtor Master";
			break;
		}
		case "cityCode": {
			columnNames = "strCityCode,strCityName,strCountryCode,strStateCode,strSTDCode";
			tableName = "clsCityMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "City Code,City Name,Country Code,State Code,SDT Code";
			idColumnName = "strCityCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "City Master";
			break;
		}
		case "areaCode": {
			columnNames = "strAreaCode,strAreaName,strCityCode,strUserCreated";
			tableName = "clsAreaMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Area Code,Area Name,City Code,User Created";
			idColumnName = "strAreaCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Area Master";
			break;
		}
		case "stateCode": {
			columnNames = "strStateCode,strStateName,strStateDesc,strRegionCode,strCountryCode";
			tableName = "clsStateMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "State Code,State Name,State Description,Region Code,Country Code";
			idColumnName = "strStateCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "State Master";
			break;
		}
		case "regionCode": {
			columnNames = "strRegionCode,strRegionName";
			tableName = "clsRegionMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Region Code,Region Name";
			idColumnName = "strRegionCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Region Master";
			break;
		}
		case "countryCode": {
			columnNames = "strCountryCode,strCountryName";
			tableName = "clsCountryMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Country Code,Country Name";
			idColumnName = "strCountryCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Country Master";
			break;
		}
		case "reasonCode": {
			columnNames = "strReasonCode,strReasonDesc";
			tableName = "clsWebBooksReasonMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Reason Code,Reason Name";
			idColumnName = "strReasonCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Reason Master";
			break;
		}
		case "memberCode": {
			columnNames = "strDebtorCode,strPrefix,strFirstName,strMiddleName,strLastName";
			tableName = "from tblsundarydebtormaster where strClientCode='" + clientCode + "'  ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Member Code,Prefix,First Name,Middle Name,Last Name";
			idColumnName = "strDebtorCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Member Master";

			flgQuerySelection = true;

			break;
		}
		case "letterCode": {
			columnNames = "strLetterCode,strLetterName,strReminderYN,strIsCircular";
			tableName = "clsLetterMasterModel where strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Letter Code,Letter Name,Reminder Y/N,Circular Y/N";
			idColumnName = "strLetterCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Letter Master";
			break;
		}

		case "GLCode": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' " + "and strType='GL Code' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "oneLineAcc": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel " + "where strDebtor='No' and strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}

		case "JVNo": {
			columnNames = "a.strVouchNo,a.strNarration,a.dteVouchDate,a.strModuleType,a.strSource,ifnull(b.strDebtorName,''),ifnull(a.dblAmt,0.0),ifnull(b.strCrDr,''),ifnull(c.strPName,''),ifnull(b.dblAmt,0.0) ";
			tableName =" from tbljvhd a left outer join tbljvdebtordtl b on  a.strVouchNo=b.strVouchNo  left outer join "+webStockDB+".tblpartymaster c on b.strDebtorCode=c.strDebtorCode "
					+ " where a.strClientCode='" + clientCode + "' group by  a.strVouchNo ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Vouch No,Narration,Date,Type,Source,Name,Amount,DR/CR,Custome/Supplier";
			idColumnName = "strVouchNo";
			flgQuerySelection = true;
			searchFormTitle = "JV";
			break;
		}
		
		case "cashBankAccNo": {
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel " + "where strClientCode='" + clientCode + "' and strType!='GL Code' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}
		case "ReceiptNo": {
			columnNames = "a.strVouchNo,a.strNarration,a.dteVouchDate,a.strType,a.strChequeNo,ifnull(b.strBankName,''),a.strBranch,a.strReceiptType,c.strDebtorName,ifnull(a.dblAmt,0.0),ifnull(d.strPName,'') ";
			tableName = "from tblreceipthd a left outer join tblbankmaster b on a.strDrawnOn=b.strBankCode left outer join tblreceiptdebtordtl c on a.strVouchNo=c.strVouchNo "
					+ " left outer join "+webStockDB+".tblpartymaster d on c.strDebtorCode=d.strDebtorCode where a.strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Vouch No,Narration,Date,Type,Cheque No,Drawn On Name,Branch Name,Receipt Type,Debtor Name,Amount,Customer/Supplier Name";
			idColumnName = "strVouchNo";
			flgQuerySelection = true;
			searchFormTitle = "Receipt";
			break;
		}



		case "PaymentNo": {
			columnNames = "a.strVouchNo,a.strNarration,a.dteVouchDate,a.strType,a.strChequeNo,ifnull(b.strBankName,''),c.strDebtorName,ifnull(a.dblAmt,0.0),ifnull(d.strPName,'') ";
			tableName = "from tblpaymenthd a left outer join tblbankmaster b on a.strDrawnOn=b.strBankCode left outer join tblpaymentdebtordtl c on a.strVouchNo=c.strVouchNo "
					+ " left outer join "+webStockDB+".tblpartymaster d on c.strDebtorCode=d.strDebtorCode where a.strClientCode='" + clientCode + "' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and a.strPropertyCode = '" + propertyCode + "' ";
			}
			flgQuerySelection = true;
			listColumnNames = "Vouch No,Narration,Date,Payment Type,Cheque No,Drawn On,Debtor Name,Amount,Customer/Supplier Name";
			idColumnName = "a.strVouchNo";
			searchFormTitle = "Payment";
			break;
		}

		case "SCCode": {
			columnNames = "strVoucherNo,strSuppName,strBillNo,dteVoucherDate ";
			tableName = "clsSundaryCrBillModel where strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Voucher No,Supplier Name,Bill,Voucher Date";
			idColumnName = "strVoucherNo";
			searchFormTitle = "Sundry Creditor Bill";
			break;
		}

		case "usermaster": {
			columnNames = "strUserCode,strUserName";
			tableName = "from "+webStockDB+".tbluserhd where strClientCode='" + clientCode + "' ";
			listColumnNames = "User Code,User Name";
			idColumnName = "strUserCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "User Master";
			flgQuerySelection = true;

			break;
		}

		case "reason": {
			columnNames = "strReasonCode,strReasonName";
			tableName = "from "+webStockDB+".tblreasonmaster where strClientCode='" + clientCode + "' ";
			listColumnNames = "Reason Code,Reason Name";
			idColumnName = "strReasonCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Reason Master";
			flgQuerySelection = true;

			break;
		}
		
		case "ReceiptNoslip": {
			columnNames = "strVouchNo,strNarration,dteVouchDate,strModuleType ";
			tableName = "clsReceiptHdModel where strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Vouch No,Narration,Date,Type";
			idColumnName = "strVouchNo";
			searchFormTitle = "Receipt";
			break;
		}
		
		case "JVNoslip": {
			columnNames = "strVouchNo,strNarration,dteVouchDate,strModuleType ";
			tableName = "clsJVHdModel where strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Vouch No,Narration,Date,Type";
			idColumnName = "strVouchNo";
			searchFormTitle = "JV";
			break;
		}
		
		case "PaymentNoslip": {
			columnNames = "strVouchNo,strNarration,dteVouchDate,strModuleType ";
			tableName = "clsPaymentHdModel where strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Vouch No,Narration,Date,Type";
			idColumnName = "strVouchNo";
			searchFormTitle = "Payment";
			break;
		}
		
		case "SCCodeslip": {
			columnNames = "strVoucherNo,strSuppName,strBillNo,dteVoucherDate ";
			tableName = "clsSundaryCrBillModel where strClientCode='" + clientCode + "'";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y")) {
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Voucher No,Supplier Name,Bill,Voucher Date";
			idColumnName = "strVoucherNo";
			searchFormTitle = "Sundry Creditor Bill";
			break;
		}
		
		case "employeeCode": {
			columnNames = "strEmployeeCode,strEmployeeName";
			tableName = "clsEmployeeMasterModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "Employee Code,Employee Name";
			idColumnName = "strEmployeeCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Employee Master";
			break;
		}
		
		case "EmployeeAccountCode":
		{
			columnNames = "strAccountCode,strAccountName,strOperational,strType";
			tableName = "clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strEmployee='Yes' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y"))
			{
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}
		
		case "bankAccNo":
		{
			columnNames = "strAccountCode,strAccountName,strOperational,strType ";
			tableName = " clsWebBooksAccountMasterModel where strClientCode='" + clientCode + "' and strType='Bank' ";
			if (showPrptyWiseProdDoc.equalsIgnoreCase("Y"))
			{
				tableName += " and strPropertyCode = '" + propertyCode + "' ";
			}
			listColumnNames = "Account Code,Account Name,Operational,Type";
			idColumnName = "strAccountCode";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "Account Master";
			break;
		}
	
		case "userDefinedReportCode": {
			columnNames = "strReportId,strReportName,dteUserDefDate,strUserCreated";
			tableName = "clsUserDefinedReportHdModel where strClientCode='" + clientCode + "' ";
			listColumnNames = "UserDefined Code,UserDefined Name,UserDefined Date,User";
			idColumnName = "strReportId";
			criteria = getCriteriaQuery(columnNames, search_with, tableName);
			searchFormTitle = "User Defined Master";
			break;
		}

	}

		if (null != jArrSearchList) {
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrSearchRow = (JSONArray) jArrSearchList.get(cnt);
				Object[] arrObj = new Object[jArrSearchRow.size()];
				for (int row = 0; row < jArrSearchRow.size(); row++) {
					arrObj[row] = jArrSearchRow.get(row);
				}
				listSearchData.add(arrObj);
			}

			mainMap.put("criteria", criteria);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
			mainMap.put("listSearchData", listSearchData);
		} else {

			mainMap.put("columnNames", columnNames);
			mainMap.put("tableName", tableName);
			mainMap.put("criteria", criteria);
			mainMap.put("multiDocCodeSelection", multiDocCodeSelection);
			mainMap.put("listColumnNames", listColumnNames);
			mainMap.put("flgQuerySelection", String.valueOf(flgQuerySelection));
			mainMap.put("idColumnName", idColumnName);
			mainMap.put("searchFormTitle", searchFormTitle);
		}

		return mainMap;
	}

	/*
	 * End WebBook Search
	 */

	/**
	 * WebPOS Search Start
	 * 
	 * @param formName
	 * @param search_with
	 * @param req
	 * @return
	 */
	private Map<String, Object> funGetWebPOSSearchDetail(String formName, String searchCode, HttpServletRequest req) {
		Map<String, Object> mainMap = new HashMap<>();

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String criteria = "";
		String listColumnNames = "";
		String idColumnName = "";
		String searchFormTitle = "";
		JSONArray jArrSearchList = null;
		List<Object[]> listSearchData = new ArrayList<Object[]>();

		switch (formName) {
		case "POSGroupMaster": {
			listColumnNames = "Group Code,Group Name,Operational";
			searchFormTitle = "Group Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSReasonMaster": {
			listColumnNames = "Reason Code,Reason Name";
			searchFormTitle = "Reason Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSCustomerTypeMaster": {
			listColumnNames = "Customer Type Code,CuStomer Type";
			searchFormTitle = "Customer Type Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSZoneMaster": {
			listColumnNames = "Zone Code,Zone Name";
			searchFormTitle = "Zone Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSshiMaster": {
			listColumnNames = "Shift Code,Pos Code,ShiftDate,Shift Start Time,Shift End Time,Bill Date Type";
			searchFormTitle = "Shift Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSAreaMaster": {
			listColumnNames = "Area Code,Area Name,POS Name";
			searchFormTitle = "Area Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSTableMaster": {
			listColumnNames = "Table No,Table Name,Area Name,Waiter Name,POS Name,Status";
			searchFormTitle = "Table Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSWaiterMaster": {
			listColumnNames = "Waiter No,Waiter ShortName,Operational";
			searchFormTitle = "Waiter Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "WebBooksAcountMaster": {
			listColumnNames = "Account Code,Account Name";
			searchFormTitle = "Account Details";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "ExciseLicenseMaster": {
			listColumnNames = "License Code,License Name";
			searchFormTitle = "License Details";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSMenuHeadMaster": {
			listColumnNames = "Menu Code,Menu Name,Operational";
			searchFormTitle = "Menu Head";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSSubMenuHeadMaster": {
			listColumnNames = "SubMenu Code,SubMenu Name,Operational";
			searchFormTitle = "SubMenu Head";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSModifierGroupMaster": {
			listColumnNames = "Modifier GroupCode,Modifier GroupName,Modifier GroupShortName,Operational";
			searchFormTitle = "Modifier GroupMaster";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSItemModifierMaster": {
			listColumnNames = "Modifier Code,Modifier Name,Description ";
			searchFormTitle = "Item Modifier";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSMenuItemMaster": {
			listColumnNames = "Item Code,Item Name,Item Type,Revenue,Tax Id,External Code,SubGroup Name";
			searchFormTitle = "Menu Item Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSMaster": {
			listColumnNames = "POS Code,POS Name";
			searchFormTitle = "POS Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSTaxMaster": {
			listColumnNames = "Tax Code,Tax Desc";
			searchFormTitle = "Tax Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSCustomerMaster": {
			listColumnNames = "Customer Code,CuStomer Name,Mobile No,Address ";
			searchFormTitle = "Customer Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSCustomerAreaMaster": {
			listColumnNames = "Customer Area Code,CuStomer Area Name, Address";
			searchFormTitle = "Customer Area Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSMenuItemPricingMaster": {
			listColumnNames = "Id,Item Code,Item Name,POS,Area,Menu,Price Mond,Price Tue,Price Wed,Price Thu,Price Fri,Price Sat,Price Sun " + ",Popular,From Date,To Date,Cost Center,Sub Menu Head,Hourly Pricing";
			searchFormTitle = "Menu Item Pricing Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSSettlementMaster": {
			listColumnNames = "Settlement Code,Settlement Name ";
			searchFormTitle = "Settlement Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSAdvOrderMaster": {
			listColumnNames = "Adv Order Type Code,Adv Order Type Name ";
			searchFormTitle = "Advance Order Type Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSDeliveryBoyMaster": {
			listColumnNames = "Delivery Boy Code, Delivery Boy Name ";
			searchFormTitle = "Delivery Boy Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSOrderMaster": {
			listColumnNames = "Order Code, Order Desc ";
			searchFormTitle = "Order Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "MenuItemForPrice": {
			listColumnNames = "Item Code, Item Name, Item Type, RevenueHead, Tax Id, External Code, SubGroup Name ";
			searchFormTitle = "Item Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "MenuItemForRecipeChild": {
			listColumnNames = "Item Code, Item Name, Item Type, RevenueHead, Tax Id, External Code, SubGroup Name ";
			searchFormTitle = "Item Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSRecipeMaster": {
			listColumnNames = "Recipe Code, Item Code, Item Name ";
			searchFormTitle = "Recipe Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSPromotionMaster": {
			listColumnNames = "Promotion Code, Promotion Name, Promotion On ";
			searchFormTitle = "Promotion Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "webstockusermaster": {
			listColumnNames = "User Code,User Name,Super User";
			searchFormTitle = "User Master";
			JSONObject jObjSearchData = funGetWebstockUserSearchDetails(clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSCounterMaster": {
			listColumnNames = "Counter Type Code,Counter Name,Operational,User,POS";
			searchFormTitle = "Counter Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSDebitCardMaster": {
			listColumnNames = "Card Type Code,Card Name";
			searchFormTitle = "Debit Card Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSUnSettleBill": {
			listColumnNames = "Bill No,Table Name,Total Amount,Settle Mode,User,Remark";
			searchFormTitle = "UnSettle Bill";
			clientCode = req.getSession().getAttribute("clientCode").toString();
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
			JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
			String date = (String) jObj.get("POSDate");
			String[] splitDate = date.split(" ");
			String data = clientCode + "#" + strPosCode + "#" + splitDate[0] + "#" + userCode;
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, data);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSItemList": {
			listColumnNames = "Item Code,Item Name,Item Type,External Code";
			searchFormTitle = "POS Item List";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "PhysicalStock": {
			String posCode = req.getSession().getAttribute("loginPOS").toString();
			listColumnNames = "PSP_Code,Item_Code,Item_Name,Created_Date";
			searchFormTitle = "Physical Stock Details";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode + "#" + posCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "StockIn": {
			listColumnNames = "StockIn_Code,Reason Code,Reason_Name";
			searchFormTitle = "StockIn Details";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "StockOut": {
			listColumnNames = "StockOut_Code,Reason Code,Reason_Name";
			searchFormTitle = "StockOut Details";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSUserMaster": {
			listColumnNames = "User Code,Swipe Card,Super Type";
			searchFormTitle = "User Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSTableReservation": {
			listColumnNames = "Reservation Code,Customer Name,Building Code,Building Name,City";
			searchFormTitle = " Table Reservation";
			String strPosCode = req.getSession().getAttribute("loginPOS").toString();

			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, strPosCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSTableReserveMaster": {
			listColumnNames = "Table No,Table Name,Area Name,Waiter Name,POS Name,Status";
			searchFormTitle = "Table Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}
		case "POSFactoryMaster": {
			listColumnNames = "Factory Code,Factory Name,User Created,User Edited,Date Created";
			searchFormTitle = "Factory Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSCostCenterMaster": {
			listColumnNames = "Cost Center Code,Cost Center Name";
			searchFormTitle = "Cost Center Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSTDHOnItem": {
			listColumnNames = "Item Code,Item Name";
			searchFormTitle = "Search Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, strMenuCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		case "POSLoadTDHData": {
			listColumnNames = "TDH Code,Description,Menu Code,Item Code,Quantity";
			searchFormTitle = "TDH Master";
			JSONObject jObjSearchData = funGetPOSSearchDetails(formName, clientCode);
			jArrSearchList = (JSONArray) jObjSearchData.get(formName);
			break;
		}

		}

		if (null != jArrSearchList) {
			for (int cnt = 0; cnt < jArrSearchList.size(); cnt++) {
				JSONArray jArrSearchRow = (JSONArray) jArrSearchList.get(cnt);
				Object[] arrObj = new Object[jArrSearchRow.size()];
				for (int row = 0; row < jArrSearchRow.size(); row++) {
					arrObj[row] = jArrSearchRow.get(row);
				}
				listSearchData.add(arrObj);
			}
		}

		mainMap.put("criteria", criteria);
		mainMap.put("listColumnNames", listColumnNames);
		mainMap.put("idColumnName", idColumnName);
		mainMap.put("searchFormTitle", searchFormTitle);
		mainMap.put("listSearchData", listSearchData);
		return mainMap;
	}

	/*
	 * End WebPOS Search
	 */

	private JSONObject funGetPOSSearchDetails(String searchFormName, String clientCode) {
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/APOSSearchIntegration/funGetPOSSearchAll" + "?masterName=" + searchFormName + "&clientCode=" + URLEncoder.encode(clientCode);
		;
		System.out.println("posUrl:" + posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObjSearchDetails;
	}

	private JSONObject funGetIndependentWebServiceDetails(String searchFormName, String clientCode, String propCode) {
		JSONObject jObjSearchDetails = new JSONObject();
		String strUrl = "";
		StringBuilder sbSql=new StringBuilder();
		
		switch (searchFormName) {

		case "BrandMasterWeb-Service": {
			sbSql.setLength(0);
			sbSql = new StringBuilder(" select a.strBrandCode , a.strBrandName "
		    		+ " from tblbrandmaster a where a.strClientCode = '"+clientCode+"'  " );
		//	strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetExciseBrandSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode;
		}
		break;

		case "exciseSupplierWeb-Service": {
			sbSql.setLength(0);
			 sbSql = new StringBuilder("select a.strSupplierCode , a.strSupplierName "
			    		+ " from tblsuppliermaster a where a.strClientCode = '"+clientCode+"'  ");	
				    
			//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetExciseBrandSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode;
		}
		break;
			case "SundryDebtorWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				
				sbSql.setLength(0);
				sbSql.append(" select a.strDebtorCode, a.strFirstName "
		    		+ " from tblsundarydebtormaster a where a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "SundryCreditorWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				
				sbSql.setLength(0);
				sbSql.append(" select a.strCreditorCode , a.strFirstName "
		    		+ " from tblsundarycreditormaster a where a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "AccountMasterWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
		    		+ " from tblacmaster a where a.strOperational='Yes' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "AccountMasterGLOnlyWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "suppLinkedWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegrationAuto/funGetWebStockSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "POSItemWeb-Service": {
				strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/ExciseIntegration/funGetPOSItemSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode;
			}
			break;

			case "TaxWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "DiscountWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "RoundOffWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "ExtraChargeWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "SettlementWeb-Service": {
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;

			case "OtherChargeWeb-Service" :{
				//strUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebBooksIntegration/funGetWebBooksSearch" + "?masterName=" + searchFormName + "&clientCode=" + clientCode + "&propCode=" + propCode;
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;
			
			case "SupplierAccCodeWeb-Service":{
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='No' and strCreditor='Yes' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;
			
			case "CustomerAccCodeWeb-Service":{
				sbSql.setLength(0);
				sbSql.append(" select a.strAccountCode , a.strAccountName "
					+ " from tblacmaster a where a.strOperational='Yes' and a.strType='GL Code' and a.strDebtor='Yes' and strCreditor='No' and a.strClientCode = '"+clientCode+"' and a.strPropertyCode='"+propCode+"'  ");
			}
			break;
		}
		
		try
		{
			JSONArray jArrData = new JSONArray();
			if(sbSql.length()>0)
			{
				List list=objBaseService.funGetListModuleWise(sbSql, "sql","WebBooks");
				if(list.size()>0)
				{
					for(int cn=0;cn<list.size();cn++)
					{
						Object[] arrObj=(Object[])list.get(cn);
						JSONArray jArrtem =new JSONArray();
					   	jArrtem.add(arrObj[0].toString());
					   	jArrtem.add(arrObj[1].toString());
					   	jArrData.add(jArrtem);
					}
				}
				jObjSearchDetails.put(searchFormName, jArrData);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
				
		/*try {
			URL url = new URL(strUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return jObjSearchDetails;
	}

	private String getCriteriaQuery(String columnNames, String search_with, String tableName) {
		String criteria = "";
		if (!tableName.contains("union")) {
			if (columnNames != null && columnNames.trim().length() > 0 && search_with != null && search_with.trim().length() > 0) {
				if (tableName.contains("where")) {
					criteria += " and ( ";
				} else {
					criteria += " where (";
				}
				for (String columnName : columnNames.split(",")) {
					criteria += columnName + " LIKE '%" + search_with + "%'  OR ";

				}
				criteria = criteria.substring(0, criteria.lastIndexOf("OR") - 2);
				criteria = criteria + " )";
			}
		}
		return criteria;
	}

	@SuppressWarnings("rawtypes")
	public List<clsFormSearchElements> funSetFormSearchElements(List list) {
		List<clsFormSearchElements> listSearchForm = new ArrayList<clsFormSearchElements>();
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			clsFormSearchElements objSearchForm = null;

			switch (ob.length) {
			case 2:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 3:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 4:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 5:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 6:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 7:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 8:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 9:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 10:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 11:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 12:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 13:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 14:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 15:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 16:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 17:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString(), ob[16].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 18:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString(), ob[16].toString(), ob[17].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 19:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString(), ob[16].toString(), ob[17].toString(),
						ob[18].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 20:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString(), ob[16].toString(), ob[17].toString(),
						ob[18].toString(), ob[19].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 21:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[13].toString(), ob[14].toString(), ob[15].toString(), ob[16].toString(), ob[17].toString(),
						ob[18].toString(), ob[19].toString(), ob[20].toString());
				listSearchForm.add(objSearchForm);
				break;

			case 22:
				objSearchForm = new clsFormSearchElements(ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), ob[4].toString(), ob[5].toString(), ob[6].toString(), ob[7].toString(), ob[8].toString(), ob[9].toString(), ob[10].toString(), ob[11].toString(), ob[12].toString(), ob[12].toString(), ob[12].toString(), ob[12].toString(), ob[12].toString(), ob[13].toString(),
						ob[14].toString(), ob[15].toString(), ob[16].toString(), ob[17].toString(), ob[18].toString(), ob[19].toString(), ob[20].toString(), ob[21].toString());
				listSearchForm.add(objSearchForm);
				break;

			}
		}
		return listSearchForm;
	}

	private JSONObject funGetWebstockUserSearchDetails(String clientCode) {
		JSONObject jObjSearchDetails = new JSONObject();
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/MMSIntegration/funGetUserMaster" + "?ClientCode=" + clientCode;
		System.out.println("posUrl:" + posUrl);

		try {
			URL url = new URL(posUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "", op = "";
			while ((output = br.readLine()) != null) {
				op += output;
			}
			System.out.println("Obj=" + op);
			conn.disconnect();

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(op);
			jObjSearchDetails = (JSONObject) obj;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObjSearchDetails;
	}

}
