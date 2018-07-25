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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsCostCenterBean;
import com.sanguine.webpos.bean.clsPhysicalStockFlashBean;
import com.sanguine.webpos.bean.clsSubGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsPhysicalStockFlashComparator;
import com.sanguine.webpos.util.clsSubGroupWiseComparator;

@Controller
public class clsPhysicalStockFlashController {
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSPhysicalStockFlash", method = RequestMethod.GET)
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

			return new ModelAndView("frmPOSPhysicalStockFlash_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSPhysicalStockFlash", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadPhysicalStockFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public LinkedHashMap FunLoadPhysicalStockFlash(HttpServletRequest req) {

		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate = req.getParameter("fromDate");

		String toDate = req.getParameter("toDate");

		String posName = req.getParameter("posName");
		String fromDate1 = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];

		String toDate1 = toDate.split("-")[2] + "-" + toDate.split("-")[1] + "-" + toDate.split("-")[0];

		String posCode = "ALL";

		if (map.containsKey(posName)) {
			posCode = (String) map.get(posName);

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("fromDate", fromDate1);
		jObjFillter.put("toDate", toDate1);

		jObjFillter.put("posCode", posCode);

		JSONObject jObj = new JSONObject();

		jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetAllPhysicalStockFlash", jObjFillter);
		List<clsPhysicalStockFlashBean> list = new ArrayList<clsPhysicalStockFlashBean>();
		JSONArray jarr = (JSONArray) jObj.get("jArr");
		if (null != jarr) {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsPhysicalStockFlashBean objClsGroupWaiseSalesBean = new clsPhysicalStockFlashBean();
				objClsGroupWaiseSalesBean.setStrPSPCode(jObjtemp.get("PSPCode").toString());
				objClsGroupWaiseSalesBean.setDteDate(jObjtemp.get("Date").toString());
				objClsGroupWaiseSalesBean.setDblCompStk(Double.parseDouble(jObjtemp.get("CompStk").toString()));
				objClsGroupWaiseSalesBean.setDblPhyStk(Double.parseDouble(jObjtemp.get("PhyStk").toString()));
				objClsGroupWaiseSalesBean.setDblVariance(Double.parseDouble(jObjtemp.get("Variance").toString()));
				list.add(objClsGroupWaiseSalesBean);
			}
		}
		resMap.put("List", list);
		return resMap;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPhysicalStockFlash", method = RequestMethod.POST)
	private void funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {
		// objGlobal=new clsGlobalFunctions();
		String phyStkNo = objBean.getStrPSPCode();
		String date = objBean.getDteDate();
		String posName = objBean.getStrPOSName();
		String reportName = "";
		// String posCode=req.getSession().getAttribute("loginPOS").toString();

		try {
			reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptPhysicalStockFlash.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String dateToDisplay = date.split("-")[2] + "-" + date.split("-")[1] + "-" + date.split("-")[0];

			String posCode = "ALL";

			if (map.containsKey(posName)) {
				posCode = (String) map.get(posName);

			}

			JSONObject jObjFillter = new JSONObject();

			jObjFillter.put("pspCode", phyStkNo);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funGetPhysicalStockFlash", jObjFillter);
			List<clsPhysicalStockFlashBean> list = new ArrayList<clsPhysicalStockFlashBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsPhysicalStockFlashBean objClsGroupWaiseSalesBean = new clsPhysicalStockFlashBean();
				objClsGroupWaiseSalesBean.setStrPSPCode(jObjtemp.get("pspCode").toString());
				objClsGroupWaiseSalesBean.setStrItemName(jObjtemp.get("ItemName").toString());
				objClsGroupWaiseSalesBean.setDblCompStk(Double.parseDouble(jObjtemp.get("CompStk").toString()));
				objClsGroupWaiseSalesBean.setDblPhyStk(Double.parseDouble(jObjtemp.get("PhyStk").toString()));
				objClsGroupWaiseSalesBean.setDblVariance(Double.parseDouble(jObjtemp.get("Variance").toString()));
				list.add(objClsGroupWaiseSalesBean);

			}
			Comparator<clsPhysicalStockFlashBean> groupComparator = new Comparator<clsPhysicalStockFlashBean>() {

				@Override
				public int compare(clsPhysicalStockFlashBean o1, clsPhysicalStockFlashBean o2) {
					return o1.getStrPSPCode().compareToIgnoreCase(o2.getStrPSPCode());
				}
			};

			Collections.sort(list, new clsPhysicalStockFlashComparator(groupComparator));

			HashMap hm = new HashMap();
			hm.put("posCode", posCode);
			hm.put("posName", posName);
			hm.put("imagePath", imagePath);
			hm.put("phyStkNo", phyStkNo);
			hm.put("dateToDisplay", dateToDisplay);

			JasperDesign jd = JRXmlLoader.load(reportName);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			// jp = JasperFillManager.fillReport(jr, hm, new
			// JREmptyDataSource());

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
			JasperPrint print = JasperFillManager.fillReport(jr, hm, beanCollectionDataSource);
			jprintlist.add(print);

			if (jprintlist.size() > 0) {
				ServletOutputStream servletOutputStream = resp.getOutputStream();

				JRExporter exporter = new JRPdfExporter();
				resp.setContentType("application/pdf");
				exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
				exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
				exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				resp.setHeader("Content-Disposition", "inline;filename=SubGroupWiseSalesReport_" + phyStkNo + ".pdf");
				exporter.exportReport();
				servletOutputStream.flush();
				servletOutputStream.close();

			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				resp.getWriter().append("No Record Found");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
