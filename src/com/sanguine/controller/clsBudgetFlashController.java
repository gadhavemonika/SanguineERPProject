package com.sanguine.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsLinkLocToOtherPropLocBean;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;

@Controller
public class clsBudgetFlashController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@RequestMapping(value = "/frmBudgetFlash", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		ModelAndView objModelView = null;
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		Map<String, String> mapProperties = objGlobalService.funGetPropertyList(clientCode);

		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

		Calendar cal = Calendar.getInstance();
		String month = monthName[cal.get(Calendar.MONTH)];
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] arrMonth = startDate.split("/");
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		int fincialMonth = Integer.parseInt(arrMonth[1]);

		List list = new ArrayList();

		for (int i = fincialMonth; i < monthName.length; i++) {
			String listMonth = monthName[i];
			list.add(listMonth);
		}
		for (int j = 1; j < fincialMonth; j++) {
			String listMonth = monthName[j];
			list.add(listMonth);
		}

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		model.put("mapProperties", mapProperties);
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBudgetFlash_1", "command", new clsLinkLocToOtherPropLocBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBudgetFlash", "command", new clsLinkLocToOtherPropLocBean());
		}
		objModelView.addObject("LoggedInProp", propertyCode);
		return objModelView;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadBudgetHeader", method = RequestMethod.GET)
	public @ResponseBody List funLoadFlashData(@RequestParam("year") String year, HttpServletRequest request) {
		List listFlash = new ArrayList();

		String[] strYear = year.split("-");
		String[] monthName = { "Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };

		Calendar cal = Calendar.getInstance();
		int currMonth = cal.get(cal.MONTH) + 1;
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] arrMonth = startDate.split("/");

		int fincialMonth = Integer.parseInt(arrMonth[1]);

		for (int i = fincialMonth - 1; i < monthName.length; i++) {

			String listMonth = monthName[i];
			listFlash.add(listMonth + "-" + strYear[0]);
		}
		for (int j = 0; j < fincialMonth - 1; j++) {

			String listMonth = monthName[j];
			listFlash.add(listMonth + "-" + strYear[1]);
		}
		return listFlash;

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadBudgetDetail", method = RequestMethod.GET)
	public @ResponseBody List funLoadFlashDetailData(@RequestParam("propCode") String propCode, @RequestParam("year") String year, HttpServletRequest request) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		List listmonth = funLoadFlashData(year, request);
		List finalList = new ArrayList();
		String startDate = request.getSession().getAttribute("startDate").toString();
		String[] arrMonth = startDate.split("/");
		int strtmonth = Integer.parseInt(arrMonth[1].toString());
		String month;
		List resultList1 = new ArrayList();
		resultList1.add("GroupCode");
		for (int i = 0; i < listmonth.size(); i++) {

			resultList1.add("");

			resultList1.add(listmonth.get(i).toString());
			resultList1.add("");

		}
		finalList.add(resultList1);
		List resultList2 = new ArrayList();
		resultList2.add("");
		for (int i = 0; i < listmonth.size(); i++) {

			resultList2.add("Bud");
			resultList2.add("GRN");
			resultList2.add("Var");

		}
		finalList.add(resultList2);
		for (Map.Entry<String, String> gCode : mapGroup.entrySet()) {
			List resultList = new ArrayList();
			resultList.add(gCode.getValue());
			int j = 1;
			for (int i = 0; i < listmonth.size(); i++) {
				String[] yearmonth = listmonth.get(i).toString().split("-");
				month = funGetMonthNumber(yearmonth[0]);
				double grnAmt = 0.0;
				double budAmt = 0.0;
				String sqlbudget = "select ifnull(strMonth" + j + ",0) from tblbudgetmasterhd a ,tblbudgetmasterdtl b  where a.strBudgetCode=b.strBudgetCode " + " and a.strPropertyCode='" + propCode + "' and a.strFinYear='" + year + "' and b.strGroupCode='" + gCode.getKey() + "' and " + " a.strClientCode='" + clientCode + "'";

				List listBudget = objGlobalService.funGetDataList(sqlbudget, "sql");
				if (listBudget.size() > 0) {
					resultList.add(listBudget.get(0));
					budAmt = Double.parseDouble(listBudget.get(0).toString());
				} else {
					resultList.add(0.0);
				}
				String[] yearForGRN = year.split("-");
				String sqlGRNQuery = " select ifnull(sum(a.dblTotal),0) from tblgrnhd a " + " left outer join tblgrndtl b on a.strGRNCode=b.strGRNCode " + " left outer join tblproductmaster c on b.strProdCode=c.strProdCode " + " left outer join tblsubgroupmaster d on c.strSGCode=d.strSGCode " + " left outer join tblgroupmaster e  on d.strGCode=e.strGCode   "
						+ " left outer join tbllocationmaster f on a.strLocCode=f.strLocCode " + " left outer join tblpropertymaster g on f.strPropertyCode=g.strPropertyCode  " + " where year(a.dtGRNDate) between '" + yearForGRN[0] + "' and '" + yearForGRN[1] + "'  " + " AND month(a.dtGRNDate) between '" + month + "' and '" + month + "' and e.strGCode='" + gCode.getKey() + "' "
						+ "  and g.strPropertyCode='" + propCode + "' and a.strClientCode='" + clientCode + "' " + " group by e.strGCode ";

				List listGRN = objGlobalService.funGetDataList(sqlGRNQuery, "sql");

				if (listGRN.size() > 0) {
					resultList.add(listGRN.get(0));
					grnAmt = Double.parseDouble(listGRN.get(0).toString());
				} else {
					resultList.add(0.0);
				}

				resultList.add(budAmt - grnAmt);
				j++;
			}
			finalList.add(resultList);
		}

		return finalList;

	}

	private String funGetMonthNumber(String monthName) {

		String result = "";
		if (monthName.equals("Jan")) {
			result = "01";
		} else if (monthName.equals("Feb")) {
			result = "02";
		} else if (monthName.equals("March")) {
			result = "03";
		} else if (monthName.equals("April")) {
			result = "04";
		} else if (monthName.equals("May")) {
			result = "05";
		} else if (monthName.equals("June")) {
			result = "06";
		} else if (monthName.equals("July")) {
			result = "07";
		} else if (monthName.equals("Aug")) {
			result = "08";
		} else if (monthName.equals("Sept")) {
			result = "09";
		} else if (monthName.equals("Oct")) {
			result = "10";
		} else if (monthName.equals("Nov")) {
			result = "11";
		} else if (monthName.equals("Dec")) {
			result = "12";

		}
		return result;
	}

}
