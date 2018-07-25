package com.sanguine.webpos.controller;

import java.util.ArrayList;
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
import com.sanguine.webpos.bean.clsPOSBillReportBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;

@Controller
public class clsPOSPostingReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctions;

	@RequestMapping(value = "/frmPOSPostingReport", method = RequestMethod.GET)
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

		JSONArray jArryPosList = objPOSGlobalFunctions.funGetAllPOSForMaster(strClientCode);

		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
		}
		model.put("posList", poslist);
		List sgNameList = new ArrayList<String>();
		sgNameList.add("ALL");

		model.put("sgNameList", sgNameList);
		String strPosCode = request.getSession().getAttribute("loginPOS").toString();
		String posURL = clsPOSGlobalFunctionsController.POSWSURL + "/APOSIntegration/funGetPOSDate" + "?POSCode=" + strPosCode;
		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData(posURL);
		String posDate = jObj.get("POSDate").toString();
		request.setAttribute("POSDate", posDate);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPostingReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPostingReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPostingReport", method = RequestMethod.POST)
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
		HashMap hm = new HashMap();
		try {
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptPostingReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String type = objBean.getStrDocType();

			String strPOSName = objBean.getStrPOSName();
			String posCode = "ALL";

			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = funGetPOSCode(strPOSName);
			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);

			List<List<clsWebPOSReportBean>> listData = new ArrayList<>();
			List listOfSettlement = new ArrayList<>();
			List listOfGroupWiseSales = new ArrayList<>();
			List listOfTaxWiseSales = new ArrayList<>();
			List listOfGroupWiseSalesForDineIn = new ArrayList<>();
			List listOfGroupWiseSalesForTakeAway = new ArrayList<>();
			List listOfGroupWiseSalesForHomeDel = new ArrayList<>();
			clsWebPOSReportBean objReportBean = null;
			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funPostingReport", jObjFillter);
			JSONArray jarr = null;
			List list = new ArrayList();

			jarr = (JSONArray) jObj.get("jArrSttleData");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				// objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfSettlement.add(objReportBean);
			}
			jarr = (JSONArray) jObj.get("jArrGroupSale");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfGroupWiseSales.add(objReportBean);
			}
			jarr = (JSONArray) jObj.get("jArrTaxWise");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				// objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfTaxWiseSales.add(objReportBean);
			}
			jarr = (JSONArray) jObj.get("jArrGroupWiseDineIn");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				// objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				// objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfGroupWiseSalesForDineIn.add(objReportBean);
			}
			jarr = (JSONArray) jObj.get("jArrGroupWiseTakeAway");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				// objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				// objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfGroupWiseSalesForTakeAway.add(objReportBean);
			}
			jarr = (JSONArray) jObj.get("jArrGroupWiseHomeDel");
			for (int i = 0; i < jarr.size(); i++) {
				objReportBean = new clsWebPOSReportBean();
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				objReportBean.setStrSettelmentDesc(jObjtemp.get("strSettelmentDesc").toString());
				// objReportBean.setStrSettelmentType(jObjtemp.get("strSettelmentType").toString());
				objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettelmentAmt").toString()));
				// objReportBean.setStrRemarks(jObjtemp.get("strRemark").toString());
				listOfGroupWiseSalesForHomeDel.add(objReportBean);
			}

			hm.put("listOfSettlement", listOfSettlement);
			hm.put("listOfGroupWiseSales", listOfGroupWiseSales);
			hm.put("listOfTaxWiseSales", listOfTaxWiseSales);
			hm.put("finalDisAmt", Double.parseDouble(jObj.get("finalDisAmt").toString()));
			hm.put("finalTipAmt", Double.parseDouble(jObj.get("finalTipAmt").toString()));
			hm.put("listOfGroupWiseSalesForDineIn", listOfGroupWiseSalesForDineIn);
			hm.put("listOfGroupWiseSalesForTakeAway", listOfGroupWiseSalesForTakeAway);
			hm.put("listOfGroupWiseSalesForHomeDel", listOfGroupWiseSalesForHomeDel);
			hm.put("finalRoundOff", Double.parseDouble(jObj.get("finalRoundOff").toString()));
			hm.put("totalDebitAmt", Double.parseDouble(jObj.get("totalDebitAmt").toString()));
			hm.put("totalCreditAmt", Double.parseDouble(jObj.get("totalCreditAmt").toString()));
			hm.put("debitRoundOff", Double.parseDouble(jObj.get("debitRoundOff").toString()));
			// objReportBean.setStrBillNo(jObjtemp.get("strBillNo").toString());
			// objReportBean.setDteBillDate(jObjtemp.get("dteBillDate").toString());
			// objReportBean.setStrPosName(jObjtemp.get("strPosName").toString());
			// objReportBean.setStrSettelmentMode(jObjtemp.get("strSettelmentMode").toString());
			// objReportBean.setDblDiscountAmt(Double.parseDouble(jObjtemp.get("dblDiscountAmt").toString()));
			// objReportBean.setDblTaxAmt(Double.parseDouble(jObjtemp.get("dblTaxAmt").toString()));
			// objReportBean.setDblSettlementAmt(Double.parseDouble(jObjtemp.get("dblSettlementAmt").toString()));

			// JSONArray jArray = (JSONArray) jObjtemp.get("listOfSettlement");
			// // JSONArray jArray = (JSONArray)Objtemp.getJSONArray("list");
			// for(int i=0;i<jArray.size();i++)
			// {
			// objReportBean
			// =jArray.getJSONObject(i).getString("strSettelmentDesc");
			// }
			// strSettelmentDesc
			// listData.add( (JSONArray) jObjtemp.get("listOfSettlement"));

			hm.put("posCode", posCode);
			hm.put("posName", strPOSName);
			hm.put("imagePath", imagePath);
			hm.put("clientName", companyName);
			hm.put("fromDateToDisplay", strFromdate);
			hm.put("toDateToDisplay", strToDate);
			hm.put("shiftNo", "1");
			hm.put("userName", userCode);

			listData.add(listOfSettlement);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listData);
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

	public String funGetPOSCode(String strPOSName) {
		String posCode = "";
		String posUrl = clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSPOSMaster/funGetPOSNameData";

		System.out.println(posUrl);

		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strPOSName", strPOSName);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			posCode = jObj.get("strPosCode").toString();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posCode;

	}

}
