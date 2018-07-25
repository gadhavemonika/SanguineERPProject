package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsPOSDashboardBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsGrnAndInvoiceController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmGrnAndInvoiceComparision", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPOSDashboardBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGrnAndInvoiceComparision_1", "command", new clsPOSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGrnAndInvoiceComparision", "command", new clsPOSDashboardBean());
		} else {
			return null;
		}
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value = { "/loadComparisonGRNandInv" }, method =
	// RequestMethod.GET)
	// @ResponseBody
	// public clsPOSDashboardBean funLoadComparisionData(HttpServletRequest req)
	// {
	//
	// LinkedHashMap resMap = new LinkedHashMap();
	// clsPOSDashboardBean objBean = new clsPOSDashboardBean();
	//
	// String clientCode = req.getSession().getAttribute("clientCode")
	// .toString();
	//
	// String fromDate = req.getParameter("fromDate");
	//
	// String toDate = req.getParameter("toDate");
	//
	// objBean = FunGetData(clientCode, fromDate, toDate);
	//
	// return objBean;
	//
	// }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadComparisonGRNandInv" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunGetData(HttpServletRequest req) {
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");
		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		String sqlGrn = "select sum(a.dblTotal),monthname(a.dtGRNDate),Year(a.dtBillDate) ,a.dtBillDate from tblgrnhd a where a.dtBillDate between '" + fromDate1 + "' and '" + toDate1 + "' " + " group by year(a.dtBillDate), monthname(a.dtBillDate) ORDER BY YEAR(a.dtBillDate) ASC, MONTH(a.dtBillDate) ASC";

		String sqlInvoice = "select sum(a.dblTotalAmt) ,monthname(a.dteInvDate),Year(a.dteInvDate) ,a.dteInvDate from tblinvoicehd a " + "where a.dteInvDate between '" + fromDate1 + "' and '" + toDate1 + "' " + "group by year(a.dteInvDate), monthname(a.dteInvDate)  ORDER BY YEAR(a.dteInvDate) ASC, MONTH(a.dteInvDate) ASC";

		List listGrn = objGlobalFunctionsService.funGetList(sqlGrn.toString(), "sql");

		List listInvoice = objGlobalFunctionsService.funGetList(sqlInvoice.toString(), "sql");
		List<clsPOSDashboardBean> listResInv = new ArrayList<clsPOSDashboardBean>();
		List<clsPOSDashboardBean> listResGrn = new ArrayList<clsPOSDashboardBean>();
		List<clsPOSDashboardBean> arrGraphList = new ArrayList<clsPOSDashboardBean>();
		if (listGrn.size() > 0) {

			for (int i = 0; i < listGrn.size(); i++) {
				Object[] obj = (Object[]) listGrn.get(i);
				String monthName = obj[1].toString();

				if (monthName.equals("January")) {
					monthName = "JAN";
				} else if (monthName.equals("February")) {
					monthName = "FEB";
				} else if (monthName.equals("March")) {
					monthName = "MAR";
				} else if (monthName.equals("April")) {
					monthName = "APR";
				} else if (monthName.equals("May")) {
					monthName = "MAY";
				} else if (monthName.equals("June")) {
					monthName = "JUN";
				} else if (monthName.equals("July")) {
					monthName = "JUL";
				} else if (monthName.equals("August")) {
					monthName = "AUG";
				} else if (monthName.equals("September")) {
					monthName = "SEP";
				} else if (monthName.equals("October")) {
					monthName = "OCT";
				} else if (monthName.equals("November")) {
					monthName = "NOV";
				} else if (monthName.equals("December")) {
					monthName = "DEC";
				}
				clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
				objDashBoardBean.setDblGrnAmt(Double.parseDouble(obj[0].toString()));
				objDashBoardBean.setStrMonthName(monthName);

				listResGrn.add(objDashBoardBean);
			}
		}

		// Map hmInv = new HashMap();
		// if (listInvoice.size() > 0) {
		//
		// for (int i = 0; i < listInvoice.size(); i++) {
		// Object[] obj = (Object[]) listInvoice.get(i);
		// String monthName = obj[1].toString();
		// clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
		// objDashBoardBean.setDblInvAmt(Double.parseDouble(obj[0].toString()));
		// objDashBoardBean.setStrMonthName(monthName);
		// listResInv.add(objDashBoardBean);
		// hmInv.put(monthName, Double.parseDouble(obj[0].toString()));
		// }
		// }
		//
		// if (listResGrn.size() > 0) {
		// for (int i = 0; i < listResGrn.size(); i++) {
		// clsPOSDashboardBean objBeanInvData = listResGrn.get(i);
		//
		//
		//
		//
		// if (listInvoice.size() > 0) {
		// clsPOSDashboardBean objBeanGrnData = listResInv.get(i);
		// if (hmInv.containsKey(objBeanGrnData.getStrMonthName())) {
		//
		// objBeanInvData.setDblInvAmt(Double.parseDouble(hmInv.get(objBeanGrnData.getStrMonthName()).toString()));
		// } else {
		// objBeanInvData.setDblInvAmt(0.0);
		// }
		// }
		//
		// arrGraphList.add(objBeanInvData);
		// }
		//
		// }

		Map<String, Double> hmGrn = new HashMap<String, Double>();
		Map<String, Double> hmInv = new HashMap<String, Double>();

		if (listGrn.size() > 0) {

			for (int i = 0; i < listGrn.size(); i++) {
				Object[] obj = (Object[]) listGrn.get(i);

				hmGrn.put(obj[1].toString() + "-" + obj[2].toString(), Double.parseDouble(obj[0].toString()));

			}
		}

		if (listInvoice.size() > 0) {

			for (int i = 0; i < listInvoice.size(); i++) {
				Object[] obj = (Object[]) listInvoice.get(i);

				hmInv.put(obj[1].toString() + "-" + obj[2].toString(), Double.parseDouble(obj[0].toString()));
			}
		}

		for (Map.Entry<String, Double> entryGrn : hmGrn.entrySet()) {
			clsPOSDashboardBean objBeanGrnData = new clsPOSDashboardBean();
			String monthYearName = entryGrn.getKey();
			Double dblGrnAmt = entryGrn.getValue();
			if (hmInv.containsKey(monthYearName)) {
				objBeanGrnData.setDblGrnAmt(dblGrnAmt);
				objBeanGrnData.setDblInvAmt(hmInv.get(monthYearName));
				hmInv.remove(monthYearName);
			} else {

				objBeanGrnData.setDblGrnAmt(dblGrnAmt);
				objBeanGrnData.setDblInvAmt(0.0);

			}
			String[] monthName = monthYearName.split("-");
			objBeanGrnData.setStrMonthName(monthName[0]);

			arrGraphList.add(objBeanGrnData);
		}
		for (Map.Entry<String, Double> entryInv : hmInv.entrySet()) {

			clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
			objDashBoardBean.setDblGrnAmt(0.0);
			String monthYearName = entryInv.getKey();
			objDashBoardBean.setDblInvAmt(entryInv.getValue());
			String[] monthName = monthYearName.split("-");
			objDashBoardBean.setStrMonthName(monthName[0]);
			arrGraphList.add(objDashBoardBean);

		}

		objBean.setArrGraphGrnInvList(arrGraphList);

		return objBean;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value = { "/loadComparisonGRNandInvLineChart" }, method =
	// RequestMethod.GET)
	// @ResponseBody
	// public clsPOSDashboardBean funloadComparisonGRNandInvLineChart(
	// HttpServletRequest req) {
	//
	// LinkedHashMap resMap = new LinkedHashMap();
	// clsPOSDashboardBean objBean = new clsPOSDashboardBean();
	//
	// String clientCode = req.getSession().getAttribute("clientCode")
	// .toString();
	//
	// String fromDate = req.getParameter("fromDate");
	//
	// String toDate = req.getParameter("toDate");
	//
	// objBean = FunGetGrnInvLineChar(clientCode, fromDate, toDate);
	//
	// return objBean;
	//
	// }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadComparisonGRNandInvLineChart" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean FunGetGrnInvLineChar(HttpServletRequest req) {
		HashMap<String, clsWebPOSReportBean> mapData = new HashMap<String, clsWebPOSReportBean>();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];
		List<clsPOSDashboardBean> arrMonthList = new ArrayList<clsPOSDashboardBean>();
		List<clsPOSDashboardBean> arrGraphList = new ArrayList<clsPOSDashboardBean>();

		String sqlGrn = "select sum(a.dblTotal),monthname(a.dtGRNDate),Year(a.dtBillDate) ,a.dtBillDate,d.strGName " + " from tblgrnhd a ,tblgrndtl b,tblsubgroupmaster c,tblgroupmaster d,tblproductmaster e " + " where a.dtBillDate between '" + fromDate1 + "' and '" + toDate1 + "'  "
				+ " and a.strGRNCode=b.strGRNCode and b.strProdCode=e.strProdCode and e.strSGCode=c.strSGCode and c.strGCode=d.strGCode " + " group by  monthname(a.dtBillDate),year(a.dtBillDate) " + " order by  monthname(a.dtBillDate),year(a.dtBillDate),d.strGCode  ";

		List listGrn = objGlobalFunctionsService.funGetList(sqlGrn.toString(), "sql");

		String sqlInv = "select sum(a.dblTotalAmt), MONTHNAME(a.dteInvDate), YEAR(a.dteInvDate),a.dteInvDate,e.strGName " + " from tblinvoicehd a,tblinvoicedtl b,tblproductmaster c ,tblsubgroupmaster d  ,tblgroupmaster e " + " where a.dteInvDate between '" + fromDate1 + "' and '" + toDate1 + "'  " + " and a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode "
				+ " and c.strSGCode=d.strSGCode and d.strGCode=e.strGCode " + " group by month(a.dteInvDate),year(a.dteInvDate)  " + " order by  monthname(a.dteInvDate),year(a.dteInvDate),e.strGCode ";

		List listInv = objGlobalFunctionsService.funGetList(sqlInv.toString(), "sql");

		Map<String, Double> hmGrn = new HashMap<String, Double>();
		Map<String, Double> hmInv = new HashMap<String, Double>();

		if (listGrn.size() > 0) {

			for (int i = 0; i < listGrn.size(); i++) {
				Object[] obj = (Object[]) listGrn.get(i);

				hmGrn.put(obj[1].toString() + "-" + obj[2].toString(), Double.parseDouble(obj[0].toString()));

			}
		}

		if (listInv.size() > 0) {

			for (int i = 0; i < listInv.size(); i++) {
				Object[] obj = (Object[]) listInv.get(i);

				hmInv.put(obj[1].toString() + "-" + obj[2].toString(), Double.parseDouble(obj[0].toString()));

			}
		}

		for (Map.Entry<String, Double> entryGrn : hmGrn.entrySet()) {
			String monthYearName = entryGrn.getKey();
			Double dblGrnAmt = entryGrn.getValue();
			if (hmInv.containsKey(monthYearName)) {
				dblGrnAmt = hmInv.get(monthYearName) - dblGrnAmt;
				hmInv.remove(monthYearName);
			} else {

				dblGrnAmt = -dblGrnAmt;
			}

			clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
			objDashBoardBean.setDblGrnAmt(dblGrnAmt);
			String[] monthName = monthYearName.split("-");
			objDashBoardBean.setStrMonthName(monthName[0]);

			arrGraphList.add(objDashBoardBean);
		}
		for (Map.Entry<String, Double> entryInv : hmInv.entrySet()) {

			clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
			objDashBoardBean.setDblGrnAmt(entryInv.getValue());
			String monthYearName = entryInv.getKey();
			String[] monthName = monthYearName.split("-");
			objDashBoardBean.setStrMonthName(monthName[0]);
			arrGraphList.add(objDashBoardBean);

		}

		objBean.setArrGraphGrnInvList(arrGraphList);
		return objBean;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value = { "/loadGroupWiseGrnPieChart" }, method =
	// RequestMethod.GET)
	// @ResponseBody
	// public clsPOSDashboardBean funloadGroupWiseGrnPieChart(
	// HttpServletRequest req) {
	//
	// LinkedHashMap resMap = new LinkedHashMap();
	// clsPOSDashboardBean objBean = new clsPOSDashboardBean();
	//
	// String clientCode = req.getSession().getAttribute("clientCode")
	// .toString();
	//
	// String fromDate = req.getParameter("fromDate");
	//
	// String toDate = req.getParameter("toDate");
	//
	// objBean = funGetGrnPieChartData(clientCode, fromDate, toDate);
	//
	// return objBean;
	//
	// }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadGroupWiseGrnPieChart" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean funGetGrnPieChartData(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");
		clsPOSDashboardBean objBean = new clsPOSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];
		List<clsPOSDashboardBean> arrMonthList = new ArrayList<clsPOSDashboardBean>();
		List<clsPOSDashboardBean> arrGraphList = new ArrayList<clsPOSDashboardBean>();

		String sqlGrn = " SELECT SUM(a.dblTotal), date(a.dtBillDate),d.strGName " + " FROM tblgrnhd a,tblgrndtl b,tblsubgroupmaster c,tblgroupmaster d,tblproductmaster e " + " WHERE a.dtBillDate BETWEEN '" + fromDate1 + "' AND '" + toDate1 + "' AND a.strGRNCode=b.strGRNCode " + "  AND b.strProdCode=e.strProdCode AND e.strSGCode=c.strSGCode AND c.strGCode=d.strGCode "
				+ " GROUP BY d.strGCode ORDER BY  d.strGCode ";

		List listGrn = objGlobalFunctionsService.funGetList(sqlGrn.toString());

		if (listGrn.size() > 0) {

			for (int i = 0; i < listGrn.size(); i++) {
				Object[] obj = (Object[]) listGrn.get(i);

				clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
				objDashBoardBean.setStrPOSName(obj[2].toString());
				objDashBoardBean.setDblGrnAmt(Double.parseDouble(obj[0].toString()));
				arrGraphList.add(objDashBoardBean);

			}
		}

		objBean.setArrGraphGrnInvList(arrGraphList);
		return objBean;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value = { "/loadGroupWiseInvPieChart" }, method =
	// RequestMethod.GET)
	// @ResponseBody
	// public clsPOSDashboardBean funLoadGroupWiseInvPieChart(
	// HttpServletRequest req) {
	//
	// LinkedHashMap resMap = new LinkedHashMap();
	// clsPOSDashboardBean objBean = new clsPOSDashboardBean();
	//
	// String clientCode = req.getSession().getAttribute("clientCode")
	// .toString();
	//
	// String fromDate = req.getParameter("fromDate");
	//
	// String toDate = req.getParameter("toDate");
	//
	// objBean = funGetInvPieChartData(clientCode, fromDate, toDate);
	//
	// return objBean;
	//
	// }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadGroupWiseInvPieChart" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPOSDashboardBean funGetInvPieChartData(HttpServletRequest req) {

		clsPOSDashboardBean objBean = new clsPOSDashboardBean();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");
		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];
		List<clsPOSDashboardBean> arrMonthList = new ArrayList<clsPOSDashboardBean>();
		List<clsPOSDashboardBean> arrGraphList = new ArrayList<clsPOSDashboardBean>();

		String sqlGrn = " select sum(a.dblTotalAmt),date(a.dteInvDate),e.strGName " + " from tblinvoicehd a,tblinvoicedtl b,tblproductmaster c ,tblsubgroupmaster d  ,tblgroupmaster e " + " where a.dteInvDate between '" + fromDate1 + "' AND '" + toDate1 + "' " + " and a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode " + " and c.strSGCode=d.strSGCode and d.strGCode=e.strGCode "
				+ " GROUP BY e.strGCode ORDER BY e.strGCode  ";

		List listGrn = objGlobalFunctionsService.funGetList(sqlGrn.toString());

		if (listGrn.size() > 0) {

			for (int i = 0; i < listGrn.size(); i++) {
				Object[] obj = (Object[]) listGrn.get(i);

				clsPOSDashboardBean objDashBoardBean = new clsPOSDashboardBean();
				objDashBoardBean.setStrPOSName(obj[2].toString());
				objDashBoardBean.setDblInvAmt(Double.parseDouble(obj[0].toString()));
				arrGraphList.add(objDashBoardBean);

			}
		}

		objBean.setArrGraphGrnInvList(arrGraphList);
		return objBean;
	}

}
