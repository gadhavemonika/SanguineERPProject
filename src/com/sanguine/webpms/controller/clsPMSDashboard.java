package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPMSDashboardBean;
import com.sanguine.webpms.bean.clsWebPMSReportBean;

@Controller
public class clsPMSDashboard {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	Map<String, String> hmPOSData;

	@RequestMapping(value = "/frmDashboard", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") @Valid clsPMSDashboardBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest request) {
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List poslist = new ArrayList();
		poslist.add("ALL");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDashboard_1", "command", new clsPMSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmDashboard", "command", new clsPMSDashboardBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadDataForPMSDashboard" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPMSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPMSDashboardBean objBean = new clsPMSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportTypedata");

		String posCode = "ALL";

		objBean = FunGetData(clientCode, fromDate, toDate, strReportType);

		return objBean;
	}

	private clsPMSDashboardBean FunGetData(String clientCode, String fromDate, String toDate, String strReportType) {
		StringBuilder sbSql = new StringBuilder();
		clsPMSDashboardBean objBean = new clsPMSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		try {

			if (strReportType.equals("Today's Foot Count")) {
				List<clsWebPMSReportBean> arrFootCountList = new ArrayList<clsWebPMSReportBean>();

				// for Arraival Count
				sbSql.setLength(0);
				sbSql.append("select  ifnull(Sum(a.intNoOfAdults+a.intNoOfChild),0) as Arrived  from tblcheckinhd a " + " where date(a.dteArrivalDate) = '" + fromDate1 + "' ");
				List listAvailableCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");// sql
				if (listAvailableCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Arrived");
					objListBean.setCount(Integer.valueOf(listAvailableCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				// for DueTOArrive Count
				sbSql.setLength(0);
				sbSql.append("select ifnull(Sum(a.intNoOfAdults+a.intNoOfChild),0) as DueTOArrive from tblreservationhd a " + " where a.strReservationNo not in(select strReservationNo from tblcheckinhd) " + " and date(a.dteArrivalDate) = '" + fromDate1 + "' ");
				List listDueTOArriveCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listDueTOArriveCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Due To Arrive");
					objListBean.setCount(Integer.valueOf(listDueTOArriveCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				// for Walkin Count
				sbSql.setLength(0);
				sbSql.append(" select ifnull(Sum(a.intNoOfAdults+a.intNoOfChild) ,0) as walkin" + " from tblwalkinhd a where date(a.dteWalkinDate) = '" + fromDate1 + "' ");
				List listWalkinCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listWalkinCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Walkin");
					objListBean.setCount(Integer.valueOf(listWalkinCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				// for DueOut Count
				sbSql.setLength(0);
				sbSql.append(" select ifnull(Sum(a.intNoOfAdults+a.intNoOfChild),0) as DueOut from tblcheckinhd a " + " where a.strCheckInNo not in(select strCheckInNo " + " from tblbillhd) and date(a.dteDepartureDate) >= '" + fromDate1 + "' ");
				List listDueOutCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listDueOutCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Due Out");
					objListBean.setCount(Integer.valueOf(listDueOutCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				// for CheckedOut Count
				sbSql.setLength(0);
				sbSql.append(" select ifnull(Sum(a.intNoOfAdults+a.intNoOfChild),0) as CheckOut from tblcheckinhd a " + " where a.strCheckInNo  in(select strCheckInNo from tblbillhd) " + " and date(a.dteDepartureDate) >= '" + fromDate1 + "' ");
				List listCheckedOutCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listCheckedOutCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Checked Out");
					objListBean.setCount(Integer.valueOf(listCheckedOutCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				objBean.setArrFootCountList(arrFootCountList);
			} else {
				List<clsWebPMSReportBean> arrFootCountList = new ArrayList<clsWebPMSReportBean>();

				// for free room
				sbSql.setLength(0);
				sbSql.append(" select count(*) from tblroom a where a.strStatus='Free' and a.strClientCode='" + clientCode + "'  ");
				List listFreeRoomCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listFreeRoomCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Free Room");
					objListBean.setCount(Integer.valueOf(listFreeRoomCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}

				// for occupied room
				sbSql.setLength(0);
				sbSql.append(" select count(*) from tblroom a where a.strStatus='Occupied' and a.strClientCode='" + clientCode + "'  ");
				List listOccupiedCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");
				if (listOccupiedCount != null) {
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType("Occupied Room");
					objListBean.setCount(Integer.valueOf(listOccupiedCount.get(0).toString()));
					arrFootCountList.add(objListBean);
				}
				objBean.setArrFootCountList(arrFootCountList);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objBean;
	}

}