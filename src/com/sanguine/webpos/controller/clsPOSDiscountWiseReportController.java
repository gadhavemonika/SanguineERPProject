package com.sanguine.webpos.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsPOSDiscountWiseBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupWiseComparator;

@Controller
public class clsPOSDiscountWiseReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;
	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSDiscountReport.html", method = RequestMethod.GET)
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
		List sgNameList = new ArrayList<String>();
		sgNameList.add("ALL");

		model.put("sgNameList", sgNameList);
		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDiscountWiseReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDiscountWiseReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSDiscountWiseSales", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		// objGlobal=new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String companyName = req.getSession().getAttribute("companyName").toString();
		// String posCode=req.getSession().getAttribute("loginPOS").toString();
		List listLive = null;
		List listQFile = null;
		List listModLive = null;
		List listModQFile = null;
		try {
			String reportName;
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String type = objBean.getStrDocType();

			String strPOSName = objBean.getStrPOSName();
			String strViewType = objBean.getStrViewType();
			String posCode = "ALL";

			if (!strPOSName.equalsIgnoreCase("ALL")) {
				if (map.containsKey(strPOSName)) {
					posCode = (String) map.get(strPOSName);
					// System.out.println(posCode.length());
				}

			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("strViewType", strViewType);

			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funDiscountWiseSalesReport", jObjFillter);
			List<clsPOSDiscountWiseBean> list = new ArrayList<clsPOSDiscountWiseBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			if (strViewType.equalsIgnoreCase("Summary")) {
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillDiscountReport.jrxml");

				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					clsPOSDiscountWiseBean objClsDiscoutWaiseSalesBean = new clsPOSDiscountWiseBean();
					objClsDiscoutWaiseSalesBean.setStrBillNo(jObjtemp.get("strBillNo").toString());
					objClsDiscoutWaiseSalesBean.setDteBillDate(jObjtemp.get("dteBillDate").toString());
					objClsDiscoutWaiseSalesBean.setStrPosName(jObjtemp.get("strPosName").toString());
					objClsDiscoutWaiseSalesBean.setDblSubTotal(Double.parseDouble(jObjtemp.get("dblSubTotal").toString()));
					objClsDiscoutWaiseSalesBean.setDblGrandTotal(Double.parseDouble(jObjtemp.get("dblGrandTotal").toString()));
					objClsDiscoutWaiseSalesBean.setStrItemCode(jObjtemp.get("strReasonName").toString());
					objClsDiscoutWaiseSalesBean.setStrItemName(jObjtemp.get("strDiscRemarks").toString());
					objClsDiscoutWaiseSalesBean.setDblDiscountPer(Double.parseDouble(jObjtemp.get("dblDiscPer").toString()));
					objClsDiscoutWaiseSalesBean.setDblDiscountAmt(Double.parseDouble(jObjtemp.get("dblDiscAmt").toString()));
					objClsDiscoutWaiseSalesBean.setDblBillDiscPer(Double.parseDouble(jObjtemp.get("dblDiscOnAmt").toString()));
					objClsDiscoutWaiseSalesBean.setStrDiscType(jObjtemp.get("strDiscOnType").toString());
					objClsDiscoutWaiseSalesBean.setStrDiscValue(jObjtemp.get("strDiscOnValue").toString());
					objClsDiscoutWaiseSalesBean.setStrSettelmentMode(jObjtemp.get("strUserEdited").toString());

					list.add(objClsDiscoutWaiseSalesBean);
				}
			} else {
				System.out.println("Ur in else Controll");
				reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptBillDiscountDetailReport.jrxml");
				for (int i = 0; i < jarr.size(); i++) {
					JSONObject jObjtemp = (JSONObject) jarr.get(i);

					clsPOSDiscountWiseBean objClsDiscoutWaiseSalesBean = new clsPOSDiscountWiseBean();
					objClsDiscoutWaiseSalesBean.setStrBillNo(jObjtemp.get("strBillNo").toString());
					objClsDiscoutWaiseSalesBean.setDteBillDate(jObjtemp.get("dteBillDate").toString());
					objClsDiscoutWaiseSalesBean.setStrPosName(jObjtemp.get("strPosName").toString());
					objClsDiscoutWaiseSalesBean.setDblSubTotal(Double.parseDouble(jObjtemp.get("dblSubTotal").toString()));
					objClsDiscoutWaiseSalesBean.setDblGrandTotal(Double.parseDouble(jObjtemp.get("dblGrandTotal").toString()));
					objClsDiscoutWaiseSalesBean.setStrItemCode(jObjtemp.get("strItemCode").toString());
					objClsDiscoutWaiseSalesBean.setStrItemName(jObjtemp.get("strItemName").toString());
					objClsDiscoutWaiseSalesBean.setDblQuantity(Double.parseDouble(jObjtemp.get("dblQuantity").toString()));
					objClsDiscoutWaiseSalesBean.setDblAmount(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
					objClsDiscoutWaiseSalesBean.setDblDiscountPer(Double.parseDouble(jObjtemp.get("dblDiscountPer").toString()));
					objClsDiscoutWaiseSalesBean.setDblDiscountAmt(Double.parseDouble(jObjtemp.get("dblDiscountAmt").toString()));
					objClsDiscoutWaiseSalesBean.setDblBillDiscPer(Double.parseDouble(jObjtemp.get("dblBillDiscPer").toString()));

					list.add(objClsDiscoutWaiseSalesBean);
				}
			}

			HashMap hm = new HashMap();
			hm.put("posCode", posCode);
			hm.put("posName", strPOSName);
			hm.put("imagePath", imagePath);
			hm.put("clientName", companyName);
			hm.put("fromDateToDisplay", strFromdate);
			hm.put("toDateToDisplay", strToDate);
			hm.put("shiftNo", "1");
			hm.put("userName", userCode);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				if (objBean.getStrDocType().equals("PDF")) {
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=GroupWiseSalesReport_" + strFromdate + "_To_" + strToDate + "_" + userCode + ".pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				} else {
					JRExporter exporter = new JRXlsExporter();
					resp.setContentType("application/xlsx");
					exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=ShopOrderListTableWise_" + strFromdate + "_To_" + strToDate + "_" + userCode + ".xls");
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

	}

}
