package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxWaiseBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupWiseComparator;

@Controller
public class clsPOSDebitCardFlashReportController {
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSDebitCardFlashReports", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
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

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOS");
		JSONArray jArryPosList = (JSONArray) jObj.get("posList");
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));

			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}
		model.put("posList", poslist);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDebitCardFlashReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDebitCardFlashReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSDebitCardFlashReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strPOSName = objBean.getStrPOSName();

		String strFromdate = objBean.getFromDate();
		String strToDate = objBean.getToDate();
		String strReportType = objBean.getStrReportType();
		String auditType = objBean.getStrPSPCode();
		Map resMap = new LinkedHashMap();

		/*
		 * String reportName = servletContext.getRealPath(
		 * "/WEB-INF/reports/webpos/rptTaxWiseReport.jrxml"); String imagePath =
		 * servletContext.getRealPath("/resources/images/company_Logo.png");
		 */

		// List<JasperPrint> jprintlist =new ArrayList<JasperPrint>();

		resMap = FunGetData(clientCode, strFromdate, strToDate, strPOSName, auditType);

		List ExportList = new ArrayList();

		String FileName = "DebitCardFlash_" + strFromdate + "_To_" + strToDate;

		ExportList.add(FileName);

		List List = (List) resMap.get("arrListHeader");

		String[] headerList = new String[List.size()];
		for (int i = 0; i < List.size(); i++) {
			headerList[i] = (String) List.get(i);
		}

		ExportList.add(headerList);

		List dataList = (List) resMap.get("List");
		List totalList = (List) resMap.get("totalList");

		dataList.add(totalList);

		ExportList.add(dataList);

		return new ModelAndView("excelViewWithReportName", "listWithReportName", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadDebitCardFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadDaywiseSalesSummary1(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strFromdate = req.getParameter("fromDate");

		String strToDate = req.getParameter("toDate");

		String strPOSName = req.getParameter("posName");

		String auditType = req.getParameter("auditType");

		resMap = FunGetData(clientCode, strFromdate, strToDate, strPOSName, auditType);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String strFromdate, String strToDate, String strPOSName, String auditType) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;
		String posCode = "ALL";

		List colHeader = new ArrayList();

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			if (map.containsKey(strPOSName)) {
				posCode = (String) map.get(strPOSName);
				// System.out.println(posCode.length());
			}

		}

		String fromDate1 = strFromdate.split("-")[2] + "-" + strFromdate.split("-")[1] + "-" + strFromdate.split("-")[0];

		String toDate1 = strToDate.split("-")[2] + "-" + strToDate.split("-")[1] + "-" + strToDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("strFromdate", fromDate1);
		jObjFillter.put("strToDate", toDate1);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("strShiftNo", "1");
		jObjFillter.put("auditType", auditType);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funDebitCardFlashReport", jObjFillter);
		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");
		JSONObject jObjTotal = (JSONObject) jObj.get("jObjTatol");
		List arrListHeader = null;
		String str = "";
		totalList.add("Total");

		switch (auditType) {

		case "Consumption Report":

			arrListHeader = new ArrayList();
			arrListHeader.add("pos");
			arrListHeader.add("Bill No");
			arrListHeader.add("Card No");
			arrListHeader.add("Customer Name");
			arrListHeader.add("Bill Date");
			arrListHeader.add("Bill Time");
			arrListHeader.add("Bill Amount");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strPOSName").toString());
					arrList.add(jObjtemp.get("strBillNo").toString());
					arrList.add(jObjtemp.get("strCardNo").toString());
					arrList.add(jObjtemp.get("strCustomerName").toString());
					arrList.add(jObjtemp.get("dteBillDate").toString());
					arrList.add(jObjtemp.get("dteBillTime").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblTransactionAmt").toString()));

					list.add(arrList);

				}

			}
			break;

		case "Recharge Details":

			arrListHeader = new ArrayList();

			arrListHeader.add("pos Name");
			arrListHeader.add("Recharge No");
			arrListHeader.add("Card No");
			arrListHeader.add("Customer Name");
			arrListHeader.add("Recharge Date");
			arrListHeader.add("Recharge Time");
			arrListHeader.add("Amount");
			arrListHeader.add("User ");
			arrListHeader.add("User Name");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strPOSName").toString());
					arrList.add(jObjtemp.get("intRechargeNo").toString());
					arrList.add(jObjtemp.get("strCardNo").toString());
					arrList.add(jObjtemp.get("strCustomerName").toString());
					arrList.add(jObjtemp.get("dteBillDate").toString());
					arrList.add(jObjtemp.get("dteBillTime").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblRechargeAmount").toString()));
					arrList.add(jObjtemp.get("strUserCode").toString());
					arrList.add(jObjtemp.get("strUserName").toString());

					list.add(arrList);
				}
			}
			break;

		case "Refund Details":

			arrListHeader = new ArrayList();

			arrListHeader.add("POS Name");
			arrListHeader.add("Refund No");
			arrListHeader.add("Card No");
			arrListHeader.add("Customer Name");
			arrListHeader.add("Refund Date");
			arrListHeader.add("Refund Time");
			arrListHeader.add("Amtount");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strPOSName").toString());
					arrList.add(jObjtemp.get("strRefundNo").toString());
					arrList.add(jObjtemp.get("strCardNo").toString());
					arrList.add(jObjtemp.get("strCustomerName").toString());
					arrList.add(jObjtemp.get("dteBillDate").toString());
					arrList.add(jObjtemp.get("dteBillTime").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblRefundAmt").toString()));

					list.add(arrList);
				}
			}
			break;

		case "Debit Card Status":
			str = "DCS";
			arrListHeader = new ArrayList();
			arrListHeader.add("Card No");
			arrListHeader.add("Customer Name");
			arrListHeader.add("Recharge Amt");
			arrListHeader.add("Refund Amt");
			arrListHeader.add("Redeem Amt");
			arrListHeader.add("Balance Amt");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strCardNo").toString());
					arrList.add(jObjtemp.get("strCustomerName").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblRechargeAmount").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("dblRefundAmt").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("dblRedeemAmt").toString()));
					arrList.add(Double.parseDouble(jObjtemp.get("balanceAmt").toString()));

					list.add(arrList);
				}
			}
			break;

		case "Unused Card Balance":

			arrListHeader = new ArrayList();

			arrListHeader.add("Card No");
			arrListHeader.add("POS Date");
			arrListHeader.add("User Created");
			arrListHeader.add("Card Amount");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strCardNo").toString());
					arrList.add(jObjtemp.get("dtePOSDate").toString());
					arrList.add(jObjtemp.get("strUserCreated").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblCardAmt").toString()));

					list.add(arrList);
				}
			}
			break;

		case "User Wise Recharge Details":

			arrListHeader = new ArrayList();
			arrListHeader.add("POS");
			arrListHeader.add("User");
			arrListHeader.add("Settelment");
			arrListHeader.add("Recharge Amount");

			if (null != jarr) {
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);
					List arrList = new ArrayList();

					arrList.add(jObjtemp.get("strPOSName").toString());
					arrList.add(jObjtemp.get("strUserName").toString());
					arrList.add(jObjtemp.get("strSettelmentDesc").toString());
					arrList.add(Double.parseDouble(jObjtemp.get("dblRechargeAmt").toString()));

					list.add(arrList);
				}
			}
			break;

		}

		if (str.equalsIgnoreCase("DCS")) {
			totalList.add(Double.parseDouble(jObjTotal.get("totalBalance").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("totalRechargeAmt").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("totalRefundAmt").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("totalRedeemAmt").toString()));
		} else {
			totalList.add(Double.parseDouble(jObjTotal.get("total").toString()));
		}

		resMap.put("List", list);
		resMap.put("totalList", totalList);
		resMap.put("arrListHeader", arrListHeader);
		return resMap;
	}

}
