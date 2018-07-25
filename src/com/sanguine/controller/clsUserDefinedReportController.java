package com.sanguine.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsFormSearchElements;
import com.sanguine.bean.clsUserDefinedReportBean;
import com.sanguine.model.clsUserDefinedReportModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsUserDefinedService;

@Controller
public class clsUserDefinedReportController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsUserDefinedService userDefinedService;
	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmUserDefinedReport", method = RequestMethod.GET)
	public ModelAndView funUserDefinedReport(@ModelAttribute("command") @Valid clsUserDefinedReportBean objBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		userDefinedService.funGetTableNames("table");
		ModelAndView mv = new ModelAndView("frmUserDefinedReport");
		String sql = "select strUDCName,strUDCCode from clsUDReportCategoryMasterModel " + "where strClientCode='" + clientCode + "'";
		List arList = objGlobalFunctionsService.funGetList(sql, "hql");

		Map<String, String> hmUDReportCategory = new HashedMap();

		for (int cnt = 0; cnt < arList.size(); cnt++) {
			Object[] arrObj = (Object[]) arList.get(cnt);
			hmUDReportCategory.put(arrObj[0].toString(), arrObj[0].toString());
		}
		HashMap<String, String> mapTableNames = (HashMap<String, String>) userDefinedService.funGetTableNames("table");
		mapTableNames = clsGlobalFunctions.funSortByValues(mapTableNames);
		mv.addObject("tableNames", mapTableNames);
		mv.addObject("category", hmUDReportCategory);
		mv.addObject("command", objBean != null ? objBean : new clsUserDefinedReportBean());
		return mv;
	}

	@RequestMapping(value = "/loadUserCodes", method = RequestMethod.GET)
	public @ResponseBody List<String> funLoadUsersForUDReport(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<String> arList = new ArrayList<String>();
		String sql = "select strUserCode from clsUserMasterModel where strClientCode='" + clientCode + "'";
		arList = objGlobalFunctionsService.funGetList(sql, "hql");

		Collections.sort(arList);
		return arList;
	}

	@RequestMapping(value = "/loadReportCategory", method = RequestMethod.GET)
	public @ResponseBody List<String> funLoadReportCategory(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<String> arList = new ArrayList<String>();
		String sql = "select strUDCName from clsUDReportCategoryMasterModel where strClientCode='" + clientCode + "'";
		arList = objGlobalFunctionsService.funGetList(sql, "hql");
		return arList;
	}

	@RequestMapping(value = "/loadColumnNames", method = RequestMethod.GET)
	// public @ResponseBody clsSecurityShellBean
	// funAssignFields(@RequestParam("userCode") String code)
	public @ResponseBody ArrayList<String> funLoadColumnNamesUserDefinedReport(@RequestParam(value = "tableName") String tableName) {
		ArrayList<String> arList = new ArrayList<String>();
		for (Map.Entry<String, String> map : userDefinedService.funGetColumnNames(tableName).entrySet()) {
			arList.add(map.getValue());
		}
		Collections.sort(arList);
		return arList;
	}

	@RequestMapping(value = "/loadColumnNames1", method = RequestMethod.GET)
	// public @ResponseBody clsSecurityShellBean
	// funAssignFields(@RequestParam("userCode") String code)
	public @ResponseBody ArrayList<String> funLoadColumnNamesForSQL(@RequestParam(value = "tableName") String tableName) {
		ArrayList<String> arList = new ArrayList<String>();
		Map<String, String> hmTableNames = userDefinedService.funGetTableNames("sql");
		if (tableName.contains(",")) {
			String[] spTables = tableName.split(",");
			for (int i = 0; i < spTables.length; i++) {
				String tbName = spTables[i].substring(0, spTables[i].indexOf(" "));
				tableName = hmTableNames.get(tbName);
				for (Map.Entry<String, String> map : userDefinedService.funGetColumnNames(tableName).entrySet()) {
					arList.add(map.getValue());
				}
			}
		} else if (tableName.contains("left outer join")) {
			String[] spTables = tableName.split("left outer join");
			for (int i = 0; i < spTables.length; i++) {
				String[] sp1 = spTables[i].split("on");
				String tbName = sp1[0];
				tableName = hmTableNames.get(tbName);
				for (Map.Entry<String, String> map : userDefinedService.funGetColumnNames(tableName).entrySet()) {
					arList.add(map.getValue());
				}
			}
		} else {
			tableName = hmTableNames.get(tableName);
			for (Map.Entry<String, String> map : userDefinedService.funGetColumnNames(tableName).entrySet()) {
				arList.add(map.getValue());
			}
		}
		Collections.sort(arList);
		return arList;
	}

	@RequestMapping(value = "/getReportData", method = RequestMethod.GET)
	public @ResponseBody List funGetReportData(@RequestParam(value = "reportQuery") String reportQuery, @RequestParam(value = "queryType") String queryType) {
		System.out.println(reportQuery);
		List arrList = new ArrayList<String>();
		arrList = objGlobalFunctionsService.funGetList(reportQuery, queryType);
		return arrList;
	}

	@RequestMapping(value = "/exportReportData", method = RequestMethod.GET)
	public ModelAndView funExportExcel(@RequestParam(value = "reportQuery") String param1, @RequestParam(value = "columns") String param2, @RequestParam(value = "queryType") String param3, HttpServletRequest req) {
		String[] arrExcelHeader = param2.split(",");
		List listUDReport = new ArrayList();
		listUDReport.add(arrExcelHeader);
		List arrList = new ArrayList<String>();
		List arrList1 = new ArrayList<String>();

		arrList1 = objGlobalFunctionsService.funGetList(param1, param3);
		for (int cnt = 0; cnt < arrList1.size(); cnt++) {
			Object[] arrObj = (Object[]) arrList1.get(cnt);
			List arrListTemp = new ArrayList<String>();
			for (int cnt1 = 0; cnt1 < arrObj.length; cnt1++) {
				arrListTemp.add(arrObj[cnt1]);
			}
			arrList.add(arrListTemp);
		}

		listUDReport.add(arrList);

		return new ModelAndView("excelView", "stocklist", listUDReport);
	}

	@RequestMapping(value = "/checkQuery", method = RequestMethod.GET)
	public @ResponseBody boolean funcheckQuery(@RequestParam(value = "queryStr") String query) {
		if (query.contains("*")) {
			String temQuery = query.toLowerCase();
			String tableName = funGetTableName(temQuery);
			String columns = "";
			Map<String, String> hmTableNames = userDefinedService.funGetTableNames("sql");
			tableName = hmTableNames.get(tableName);
			for (Map.Entry<String, String> map : userDefinedService.funGetColumnNames(tableName).entrySet()) {
				columns += "," + map.getValue();
			}
			columns = columns.substring(1, columns.length());
			query = "select " + columns + " " + temQuery.substring(temQuery.indexOf("from"), temQuery.length());
		}

		if (query == null && (query.trim().isEmpty() || query.trim().toUpperCase().indexOf("SELECT") != 0 || query.trim().indexOf(";") != -1)) {
			return false;
		}
		return userDefinedService.funCheckQuery(query);
	}

	@RequestMapping(value = "/saveUserDefinedReport", method = RequestMethod.POST)
	public ModelAndView funSaveUserDefinedReport(@ModelAttribute("command") @Valid clsUserDefinedReportBean objBean, BindingResult result, HttpServletResponse resp, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		objGlobal = new clsGlobalFunctions();
		String tableName = "";

		if ("SQL".equals(objBean.getStrType())) {
		} else if ("TABLE".equals(objBean.getStrType())) {
			String columns = "";
			for (String column : objBean.getStrSelectedFields()) {
				if (column != null)
					columns += column + ", ";
			}
			columns = columns.substring(0, columns.lastIndexOf(","));
		}

		String authoriseUsers = "";
		String selectedFields = "";
		String reportCode = objBean.getStrReportCode();
		String reportDesc = objBean.getStrReportDesc();
		String reportType = objBean.getStrType();

		ArrayList<String> arrListSelectedUsers = objBean.getStrSelectedUsers();
		if (null != arrListSelectedUsers) {
			for (int cnt = 0; cnt < arrListSelectedUsers.size(); cnt++) {
				if (null != arrListSelectedUsers.get(cnt)) {
					authoriseUsers = authoriseUsers + "," + arrListSelectedUsers.get(cnt);
				}
			}
			StringBuilder sb = new StringBuilder(authoriseUsers);
			authoriseUsers = sb.delete(0, 1).toString();
		}

		if (reportType.equalsIgnoreCase("TABLE")) {
			ArrayList<String> arrListSelectedFields = objBean.getStrSelectedFields();
			if (null != arrListSelectedFields) {
				for (int cnt = 0; cnt < arrListSelectedFields.size(); cnt++) {
					if (null != arrListSelectedFields.get(cnt)) {
						selectedFields = selectedFields + "," + arrListSelectedFields.get(cnt);
					}
				}
				StringBuilder sb = new StringBuilder(selectedFields);
				selectedFields = sb.delete(0, 1).toString();
			}
		}

		selectedFields = objBean.getStrFinalColumns();
		String criteria = "";
		String groupByFields = "", sortByFields = "";
		String reportQuery = objBean.getStrReportQuery().toLowerCase();
		String reportQuery1 = objBean.getStrReportQuery();

		tableName = funGetTableName(reportQuery).trim();
		if (reportType.equalsIgnoreCase("SQL Based")) {
			// int len=tableName.length()-3;
			// tableName=tableName.substring(1,len);
			tableName = objBean.getStrFinalTable();
		} else {
			tableName = objBean.getStrTable();
		}

		System.out.println(tableName);
		String headLine1 = "", headLine2 = "", footLine1 = "", footLine2 = "";

		clsUserDefinedReportModel objModel = new clsUserDefinedReportModel();
		objModel.setStrReportCode(reportCode);
		objModel.setStrReportDesc(reportDesc);
		objModel.setStrType(reportType);
		objModel.setStrTable(tableName);
		objModel.setStrQuery(reportQuery1);
		objModel.setStrSelectedFields(selectedFields);

		objModel.setStrLayout("");
		objModel.setStrFieldSize("");

		objModel.setStrCriteria(objBean.getStrFinalWhere());
		objModel.setStrGroupBy(objBean.getStrFinalGroup());
		objModel.setStrSortBy(objBean.getStrFinalSort());

		objModel.setStrSubTotal(objGlobal.funIfNull(objBean.getStrSubTotal(), "", objBean.getStrSubTotal()));
		objModel.setStrGrandTotal(objGlobal.funIfNull(objBean.getStrGrandTotal(), "", objBean.getStrGrandTotal()));

		objModel.setStrHeadLine1(objGlobal.funIfNull(headLine1, "", headLine1));
		objModel.setStrHeadLine2(objGlobal.funIfNull(headLine2, "", headLine2));
		objModel.setStrFootLine1(objGlobal.funIfNull(footLine1, "", footLine1));
		objModel.setStrFootLine2(objGlobal.funIfNull(footLine2, "", footLine2));

		objModel.setStrSearchCode(objGlobal.funIfNull(objBean.getStrSearchCode(), "", objBean.getStrSearchCode()));
		objModel.setStrCategory(objGlobal.funIfNull(objBean.getStrCategory(), "", objBean.getStrCategory()));
		objModel.setStrOperational(objBean.getStrOperational());
		objModel.setStrImgURL("");
		objModel.setStrClientCode(clientCode);
		objModel.setStrUserCode(authoriseUsers);
		userDefinedService.funSaveUpdateUR(objModel);

		return new ModelAndView("redirect:/frmUserDefinedReport.html");
	}

	@RequestMapping(value = "/getReport", method = RequestMethod.GET)
	public @ResponseBody List funGetReport(@RequestParam(value = "reportName") String reportName) {
		List arrList = new ArrayList<String>();
		return arrList;
	}

	private String funGetOrderByClause(String text, String type) {
		String orderByClause = "";
		if (type.equals("TABLE")) {
			if (text.contains("order by")) {
				int orderByIndex = text.lastIndexOf("order by");
				orderByClause = text.substring(orderByIndex, text.length());
			}
		} else {
			int index = text.lastIndexOf(") a");
			String orderby = text.substring(index, text.length());
			int orderByIndex = orderby.lastIndexOf("order by");
			if (orderByIndex > -1) {
				orderByClause = orderby.substring(orderByIndex, orderby.length());
			}
		}
		return orderByClause;
	}

	private String funGetGroupByClause(String text, String type) {
		String groupByClause = "";
		if (type.equals("TABLE")) {
			if (text.contains("group by")) {
				int groupByIndex = text.lastIndexOf("group by");
				int endIndex = text.length();
				if (text.contains("order by")) {
					endIndex = text.lastIndexOf("order by");
				}
				groupByClause = text.substring(groupByIndex, endIndex);
			}
		} else {
			int index = text.lastIndexOf(") a");
			String groupby = text.substring(index, text.length());
			int groupByIndex = groupby.lastIndexOf("group by");
			int endIndex = groupby.length();
			if (groupby.contains("order by")) {
				endIndex = groupby.lastIndexOf("order by");
			}
			if (groupByIndex > -1) {
				groupByClause = groupby.substring(groupByIndex, endIndex);
			}
		}
		return groupByClause;
	}

	private String funGetWhereClause(String text, String type) {
		String whereClause = "";
		if (type.equals("TABLE")) {
			if (text.contains("where")) {
				int whereIndex = text.lastIndexOf("where");
				int endIndex = text.length();

				if (text.contains("group by")) {
					endIndex = text.lastIndexOf("group by");
				} else if (text.contains("order by")) {
					endIndex = text.lastIndexOf("order by");
				}
				whereClause = text.substring(whereIndex, endIndex);
			}
		} else {
			int index = text.lastIndexOf(") a");
			String where = text.substring(index, text.length());
			if (where.contains("where")) {
				int whereIndex = where.lastIndexOf("where");
				int endIndex = where.length();

				if (where.contains("group by")) {
					endIndex = where.lastIndexOf("group by");
				} else if (where.contains("order by")) {
					endIndex = where.lastIndexOf("order by");
				}
				whereClause = where.substring(whereIndex, endIndex);
			}
		}
		return whereClause;
	}

	private String funGetTableName(String text) {
		String tableName = "";
		int fromIndex = text.indexOf("from");
		int endIndex = text.length();

		if (text.contains("where")) {
			endIndex = text.indexOf("where");
		} else if (text.contains("order by")) {
			endIndex = text.indexOf("order by");
		}
		tableName = text.substring(fromIndex + 4, endIndex);
		return tableName;
	}

	private List getResult(clsUserDefinedReportBean objBean) {
		String columns = "";

		for (String column : objBean.getStrSelectedFields()) {
			if (column != null)
				columns += userDefinedService.funGetPropertyNames(objBean.getStrTable()).get(column) + ", ";
		}

		columns = columns.substring(0, columns.lastIndexOf(","));
		String query = "select " + columns + " from " + objBean.getStrTable();
		System.out.println(query);
		List reslut = objGlobalFunctionsService.funGetList(query, "hql");
		List<clsFormSearchElements> elements = new ArrayList<clsFormSearchElements>();
		for (Iterator it = reslut.iterator(); it.hasNext();) {
			clsFormSearchElements element = new clsFormSearchElements((Object[]) it.next());
			elements.add(element);

		}
		return elements;
	}

	@RequestMapping(value = "/frmUserDefinedReportView", method = RequestMethod.GET)
	public ModelAndView funUserDefinedReportView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		ModelAndView mv = new ModelAndView("frmUserDefinedReportView");
		String sql = "select strReportDesc from clsUserDefinedReportModel where strClientCode='" + clientCode + "'";
		List arList = objGlobalFunctionsService.funGetList(sql, "hql");
		Map<String, String> hmUDReport = new HashedMap();

		for (int cnt = 0; cnt < arList.size(); cnt++) {
			String name = (String) arList.get(cnt);
			hmUDReport.put(name, name);
		}

		mv.addObject("category", hmUDReport);
		return mv;
	}

	@RequestMapping(value = "/getReportName", method = RequestMethod.GET)
	public @ResponseBody List funGetReportColumns(@RequestParam(value = "reportCode") String reportCode) {
		List arrList = new ArrayList<String>();
		String sql = "select strReportDesc,strSelectedFields,strQuery,strType,strCriteria,strGroupBy,strSortBy,strTable " + "from tbluserdefinedreport " + "where strReportCode='" + reportCode + "'";
		arrList = objGlobalFunctionsService.funGetList(sql, "sql");
		return arrList;
	}

	@RequestMapping(value = "/loadUserDefinedReport", method = RequestMethod.GET)
	public @ResponseBody List funLoadUserDefinedReport(@RequestParam(value = "reportCode") String reportCode) {
		List arrList = new ArrayList<String>();
		String sql = "select strReportCode,strReportDesc,strcategory,strOperational,strType,strTable,strQuery" + ",strSelectedFields,strCriteria,strGroupBy,strSortBy,strUserCode " + "from tbluserdefinedreport " + "where strReportCode='" + reportCode + "'";
		arrList = objGlobalFunctionsService.funGetList(sql, "sql");
		return arrList;
	}

}
