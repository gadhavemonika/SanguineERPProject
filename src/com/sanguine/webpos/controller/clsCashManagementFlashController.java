package com.sanguine.webpos.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsBillDtl;
import com.sanguine.webpos.bean.clsCashManagementDtlBean;

@Controller
public class clsCashManagementFlashController {
	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobal;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSCashMgmtReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		String posCode = request.getSession().getAttribute("loginPOS").toString();
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<Object> posList = new ArrayList<Object>();
		JSONArray jArrList = new JSONArray();
		jArrList = objPOSGlobal.funGetAllPOSForMaster(clientCode);
		for (int i = 0; i < jArrList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArrList.get(i);
			posList.add(josnObjRet.get("strPosName"));
			map.put(josnObjRet.get("strPosName"), josnObjRet.get("strPosCode"));
		}

		model.put("posList", posList);
		String posUrL = "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOSDate" + "?POSCode=" + posCode;
		JSONObject jObj = objGlobal.funGETMethodUrlJosnObjectData(posUrL);

		String posDate = (String) jObj.get("POSDate");
		String[] output = posDate.split(" ");
		String POSDate = output[0];
		model.put("posDate", POSDate);

		Map mapAmount = new HashMap<>();

		mapAmount.put("<=", "<=");
		mapAmount.put(">=", ">=");
		mapAmount.put("=", "=");
		model.put("mapAmount", mapAmount);

		Map mapReportType = new HashMap<>();

		mapReportType.put("Detail", "Detail");
		mapReportType.put("Summary", "Summary");

		model.put("mapReportType", mapReportType);

		Map mapTransType = new HashMap<>();
		mapTransType.put("All", "All");
		mapTransType.put("Transfer In", "Transfer In");
		mapTransType.put("Float", "Float");
		mapTransType.put("Refund", "Refund");
		mapTransType.put("Withdrawal", "Withdrawal");
		mapTransType.put("Payments", "Payments");
		mapTransType.put("Transfer Out", "Transfer Out");
		model.put("mapTransType", mapTransType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCashMgmtReport_1", "command", new clsCashManagementDtlBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSCashMgmtReport", "command", new clsCashManagementDtlBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadSalesTbl", method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadSalesDtl(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String strReportType = req.getParameter("strReportType");

		String posCode = "";

		String posName = req.getParameter("posName");
		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);
		}

		String transType = req.getParameter("transType");

		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);
		jObjFillter.put("posCode", posCode);
		jObjFillter.put("strReportType", strReportType);
		jObjFillter.put("clientCode", clientCode);
		jObjFillter.put("transType", transType);
		JSONObject jObj = new JSONObject();

		jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetCashManagementFlash", jObjFillter);

		JSONArray jColHeaderArr = (JSONArray) jObj.get("ColHeader");
		JSONArray jarr = (JSONArray) jObj.get("jArr");
		String reportType = jObj.get("reportType").toString();
		double amtTotal = 0;
		List list = new ArrayList();
		List totalList = new ArrayList();
		totalList.add("Total");
		amtTotal = Double.parseDouble(jObj.get("total").toString());
		if (reportType.equalsIgnoreCase("Detail")) {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				// clsAuditFlashBean objClsGroupWaiseSalesBean=new
				// clsAuditFlashBean();
				List arrList = new ArrayList();
				arrList.add(jObjtemp.get("userCode").toString());
				arrList.add(jObjtemp.get("transType").toString());
				arrList.add(jObjtemp.get("date").toString());
				arrList.add(jObjtemp.get("posName").toString());
				arrList.add(jObjtemp.get("reason").toString());
				arrList.add(jObjtemp.get("remarks").toString());
				arrList.add(Double.parseDouble(jObjtemp.get("amount").toString()));
				list.add(arrList);
			}
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(amtTotal);
		} else {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				// clsAuditFlashBean objClsGroupWaiseSalesBean=new
				// clsAuditFlashBean();
				List arrList = new ArrayList();
				arrList.add(jObjtemp.get("userCode").toString());
				arrList.add(jObjtemp.get("transType").toString());
				arrList.add(jObjtemp.get("date").toString());
				arrList.add(jObjtemp.get("posName").toString());
				arrList.add(Double.parseDouble(jObjtemp.get("amount").toString()));
				list.add(arrList);
			}
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(" ");
			totalList.add(amtTotal);
		}

		resMap.put("List", list);
		resMap.put("totalList", totalList);
		resMap.put("ColHeader", jColHeaderArr);

		return resMap;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSCashManagementFlash", method = RequestMethod.POST)
	private JSONObject funReport(@ModelAttribute("command") clsCashManagementDtlBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		String posCode = "";
		String strDocType = "PDF";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		String fromDate = objBean.getFromDate();
		String toDate = objBean.getToDate();
		String posName = objBean.getPosName();
		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);
		}
		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		try {
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptCashManagementReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");//

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("fromDate", fromDate1);
			jObjFillter.put("toDate", toDate1);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("clientCode", clientCode);

			JSONObject jObj = new JSONObject();
			jObj = objGlobal.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funExportToJasperFile", jObjFillter);

			String address2 = (String) jObj.get("clientAddress2");
			String address3 = (String) jObj.get("clientAddress3");
			HashMap hm = new HashMap();
			hm.put("posCode", posCode);
			hm.put("posName", posName);
			hm.put("imagePath", imagePath);
			hm.put("clientName", companyName);
			hm.put("fromDateToDisplay", fromDate1);
			hm.put("toDateToDisplay", toDate1);
			hm.put("userName", userCode);
			hm.put("address2", address2);
			hm.put("address3", address3);

			List<clsCashManagementDtlBean> listOfCashManagementData = new ArrayList<clsCashManagementDtlBean>();
			List<clsCashManagementDtlBean> list = new ArrayList<clsCashManagementDtlBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsCashManagementDtlBean objCashMgmtDtlBean = new clsCashManagementDtlBean();
				objCashMgmtDtlBean.setFloatAmt(Double.parseDouble(jObjtemp.get("floatAmt").toString()));
				objCashMgmtDtlBean.setUserName(jObjtemp.get("userName").toString());

				objCashMgmtDtlBean.setSaleAmt(Double.parseDouble(jObjtemp.get("saleAmount").toString()));
				objCashMgmtDtlBean.setTotSaleAmt(Double.parseDouble(jObjtemp.get("totSaleAmt").toString()));
				objCashMgmtDtlBean.setAdvanceAmt(Double.parseDouble(jObjtemp.get("advAmt").toString()));
				objCashMgmtDtlBean.setWithdrawlAmt(Double.parseDouble(jObjtemp.get("withdrawlAmt").toString()));
				objCashMgmtDtlBean.setPaymentAmt(Double.parseDouble(jObjtemp.get("paymentsAmt").toString()));
				objCashMgmtDtlBean.setTotPaymentAmt(Double.parseDouble(jObjtemp.get("totPaymentsAmt").toString()));
				objCashMgmtDtlBean.setRefundAmt(Double.parseDouble(jObjtemp.get("refundAmt").toString()));
				objCashMgmtDtlBean.setTransferInAmt(Double.parseDouble(jObjtemp.get("transInAmt").toString()));
				objCashMgmtDtlBean.setTransferOutAmt(Double.parseDouble(jObjtemp.get("transOutAmt").toString()));
				objCashMgmtDtlBean.setSaleAfterRolling(Double.parseDouble(jObjtemp.get("postRollingSalesAmt").toString()));
				objCashMgmtDtlBean.setBalanceAmt(Double.parseDouble(jObjtemp.get("balanceAmt").toString()));
				objCashMgmtDtlBean.setTotalBalanceAmt(Double.parseDouble(jObjtemp.get("totalBalanceAmt").toString()));
				objCashMgmtDtlBean.setTotalRollingAmt(Double.parseDouble(jObjtemp.get("totalRollingAmt").toString()));
				objCashMgmtDtlBean.setRollingAmt(Double.parseDouble(jObjtemp.get("rollingAmt").toString()));
				objCashMgmtDtlBean.setTotalPostRollingSalesAmt(Double.parseDouble(jObjtemp.get("totalPostRollingSalesAmt").toString()));

				listOfCashManagementData.add(objCashMgmtDtlBean);
			}
			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfCashManagementData);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (strDocType.equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=CreditBillOutstandingReport_" + fromDate1 + "_To_" + toDate1 + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Hi");

		return null;
	}
}
