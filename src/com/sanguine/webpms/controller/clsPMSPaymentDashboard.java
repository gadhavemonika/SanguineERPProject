package com.sanguine.webpms.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
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
public class clsPMSPaymentDashboard {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private SessionFactory webPMSSessionFactory;
	Map<String, String> hmPOSData;

	@RequestMapping(value = "/frmPaymentDashboard", method = RequestMethod.GET)
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
			return new ModelAndView("frmPaymentDashboard_1", "command", new clsPMSDashboardBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPaymentDashboard", "command", new clsPMSDashboardBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadDataForPMSPaymentDashboard" }, method = RequestMethod.GET)
	@ResponseBody
	public clsPMSDashboardBean FunLoadPOSWiseSalesReport(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		clsPMSDashboardBean objBean = new clsPMSDashboardBean();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportTypedata");

		String posCode = "ALL";

		objBean = FunGetData(clientCode, fromDate, toDate, strReportType, req);

		return objBean;
	}

	private clsPMSDashboardBean FunGetData(String clientCode, String fromDate, String toDate, String strReportType, HttpServletRequest req) {
		StringBuilder sbSql = new StringBuilder();
		clsPMSDashboardBean objBean = new clsPMSDashboardBean();

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		try {
			List<clsWebPMSReportBean> arrSettlementList = new ArrayList<clsWebPMSReportBean>();

			// for settlement wise report
			sbSql.setLength(0);
			sbSql.append(" select sum(a.dblPaidAmt),date(a.dteReceiptDate),c.strSettlementDesc " + " from tblreceipthd a ,tblreceiptdtl b,tblsettlementmaster c " + " where a.strReceiptNo=b.strReceiptNo and  " + " date(a.dteReceiptDate) between '" + fromDate1 + "' and '" + toDate1 + "' " + " and b.strSettlementCode=c.strSettlementCode and a.strClientCode=b.strClientCode "
					+ " and b.strClientCode=c.strClientCode and a.strClientCode='" + clientCode + "' " + " group by b.strSettlementCode  ");
			List listSettlementCount = objGlobalFunctionsService.funGetDataList(sbSql.toString(), "sql");

			if (listSettlementCount != null) {
				for (int i = 0; i < listSettlementCount.size(); i++) {
					Object[] obj = (Object[]) listSettlementCount.get(i);
					clsWebPMSReportBean objListBean = new clsWebPMSReportBean();
					objListBean.setType(obj[2].toString());
					objListBean.setAmount(Double.valueOf(obj[0].toString()));
					arrSettlementList.add(objListBean);
				}
			}

			objBean.setArrFootCountList(arrSettlementList);

			/*
			 * sbSql.setLength(0); sbSql.append(
			 * " SELECT b.strRoomNo,date(a.dteBillDate),date(a.dteDateCreated), "
			 * + " IFNULL(c.intNoOfNights,f.intNoOfNights),'', " +
			 * " CONCAT(IFNULL(d.strFirstName,''), IFNULL(d.strMiddleName,'') , IFNULL(d.strLastName,'')) as  Name, "
			 * +
			 * " IFNULL(d.strPANNo,d.strPassportNo), IFNULL(d.strNationality,''),"
			 * +
			 * " CONCAT(IFNULL(d.strAddress,''),IFNULL(d.strCity,''),IFNULL(d.strCountry,'')) as address"
			 * + " FROM tblbillhd a" +
			 * " LEFT OUTER JOIN tblcheckindtl b ON a.strCheckInNo=b.strCheckInNo AND b.strPayee='Y'"
			 * +
			 * " LEFT OUTER JOIN tblguestmaster d ON b.strGuestCode=d.strGuestCode"
			 * +
			 * " LEFT OUTER JOIN tblcheckinhd e ON a.strCheckInNo=e.strCheckInNo"
			 * + " LEFT OUTER JOIN tblwalkinhd f ON e.strWalkInNo=f.strWalkinNo"
			 * +
			 * " LEFT OUTER JOIN tblreservationhd c ON e.strReservationNo=c.strReservationNo"
			 * +
			 * " WHERE a.dteBillDate BETWEEN '"+fromDate1+"' AND '"+toDate1+"' "
			 * + " GROUP BY a.strCheckInNo  ");
			 * 
			 * SessionFactory sessionFactory1 = null; if
			 * (req.getSession().getAttribute
			 * ("selectedModuleName").toString().equalsIgnoreCase("3-WebPMS")) {
			 * sessionFactory1 = webPMSSessionFactory; } Query query =
			 * sessionFactory1
			 * .getCurrentSession().createSQLQuery(sbSql.toString())
			 * .addEntity(clsPMSPaymentDashboard.class); List result =
			 * query.list();
			 * 
			 * if (result != null) { for (int i = 0; i < result.size(); i++) {
			 * Object[] obj = (Object[]) result.get(i); clsWebPMSReportBean
			 * objListBean=new clsWebPMSReportBean();
			 * objListBean.setType(obj[2].toString());
			 * objListBean.setAmount(Double.valueOf(obj[0].toString()));
			 * arrSettlementList.add(objListBean); } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

		return objBean;
	}

}