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

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpos.bean.clsGroupSubGroupItemBean;
import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupSubGroupComparator;

/**
 * \
 * 
 * @author Vinayak
 *
 */
@Controller
public class clsPOSGroupSubGroupWiseReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctions;
	Map<String, String> mapPOS = new HashMap<String, String>();
	Map<String, String> mapSubGrp = new HashMap<String, String>();
	Map<String, String> mapGroup = new HashMap<String, String>();

	@RequestMapping(value = "/frmPOSGroupSubGroupWiseReport", method = RequestMethod.GET)
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
			mapPOS.put(josnObjRet.get("strPosName").toString(), josnObjRet.get("strPosCode").toString());
		}
		model.put("posList", poslist);

		List listSubGroup = new ArrayList<String>();
		listSubGroup.add("ALL");
		listSubGroup = funGetSubGroupGDetail(strClientCode);
		model.put("listSubGroupName", listSubGroup);

		List listGroup = new ArrayList<String>();
		listGroup.add("ALL");

		JSONArray jGroupArry = funGetAllGroup(strClientCode);
		for (int i = 0; i < jGroupArry.size(); i++) {
			JSONObject jObjtemp = (JSONObject) jGroupArry.get(i);
			listGroup.add(jObjtemp.get("strGroupName").toString());
			mapGroup.put(jObjtemp.get("strGroupName").toString(), jObjtemp.get("strGroupCode").toString());
		}
		model.put("listGroupName", listGroup);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSGroupSubGroupWiseReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSGroupSubGroupWiseReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptGroupSubGroupWiseReport", method = RequestMethod.POST)
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
		List<clsGroupSubGroupItemBean> listOfGroupSubGroupWiseSales = new ArrayList<clsGroupSubGroupItemBean>();
		try {
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptGroupSubGroupWiseReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String type = objBean.getStrDocType();

			String strSubGrpName = objBean.getStrSGName();
			String strPOSName = objBean.getStrPOSName();
			String strGroupName = objBean.getStrGroupName();
			String posCode = "ALL";
			String subGroupCode = "ALL";
			String groupCode = "ALL";
			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = mapPOS.get(strPOSName);// funGetPOSCode(strPOSName);
			}

			if (!strSubGrpName.equalsIgnoreCase("ALL")) {
				subGroupCode = mapSubGrp.get(strSubGrpName);// funGetSGCode(strSGName);
			}

			if (!strGroupName.equalsIgnoreCase("ALL")) {
				groupCode = mapGroup.get(strGroupName); // funGetGCode(strGroupName);
			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("sgCode", subGroupCode);
			jObjFillter.put("gCode", groupCode);
			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funGroupSubGroupWiseReport", jObjFillter);
			List<clsGroupSubGroupItemBean> list = new ArrayList<clsGroupSubGroupItemBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				clsGroupSubGroupItemBean objGroupSubGroupBean = new clsGroupSubGroupItemBean();
				objGroupSubGroupBean.setStrGroupName(jObjtemp.get("strGroupName").toString());
				objGroupSubGroupBean.setStrItemName(jObjtemp.get("strItemName").toString());

				objGroupSubGroupBean.setDblQuantity(Double.parseDouble(jObjtemp.get("dblQuantity").toString()));
				;
				objGroupSubGroupBean.setDblAmount(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				objGroupSubGroupBean.setStrItemCode(jObjtemp.get("strItemCode").toString());
				objGroupSubGroupBean.setStrSubGroupName(jObjtemp.get("strSubGroupName").toString());

				list.add(objGroupSubGroupBean);
			}
			Comparator<clsGroupSubGroupItemBean> groupComparator = new Comparator<clsGroupSubGroupItemBean>() {

				@Override
				public int compare(clsGroupSubGroupItemBean o1, clsGroupSubGroupItemBean o2) {
					return o1.getStrGroupName().compareToIgnoreCase(o2.getStrGroupName());
				}
			};

			Comparator<clsGroupSubGroupItemBean> subGroupComparator = new Comparator<clsGroupSubGroupItemBean>() {

				@Override
				public int compare(clsGroupSubGroupItemBean o1, clsGroupSubGroupItemBean o2) {
					return o1.getStrSubGroupName().compareToIgnoreCase(o2.getStrSubGroupName());
				}
			};

			Comparator<clsGroupSubGroupItemBean> codeComparator = new Comparator<clsGroupSubGroupItemBean>() {

				@Override
				public int compare(clsGroupSubGroupItemBean o1, clsGroupSubGroupItemBean o2) {
					return o1.getStrItemCode().substring(0, 7).compareToIgnoreCase(o2.getStrItemCode().substring(0, 7));
				}
			};

			Collections.sort(listOfGroupSubGroupWiseSales, new clsGroupSubGroupComparator(groupComparator, subGroupComparator, codeComparator));

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

	public ArrayList<String> funGetSubGroupGDetail(String clientCode) {

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllSubGroup?clientCode=" + clientCode);
		JSONArray jArr = (JSONArray) jObj.get("allSGData");
		JSONObject subJsonObject = new JSONObject();
		mapSubGrp = new HashedMap();
		// hmSubGroupCode=new HashedMap();
		ArrayList<String> lstSGData = new ArrayList<String>();
		lstSGData.add("ALL");
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				subJsonObject = (JSONObject) jArr.get(i);

				mapSubGrp.put(subJsonObject.get("strSubGroupName").toString(), subJsonObject.get("strSubGroupCode").toString());
				// hmSubGroupCode.put(subJsonObject.get("strSubGroupCode").toString(),subJsonObject.get("strSubGroupName").toString());
				lstSGData.add(subJsonObject.get("strSubGroupName").toString());
			}
		}
		return lstSGData;
	}

	public JSONArray funGetAllGroup(String strClientCode) {
		List sglist = new ArrayList<String>();
		JSONArray jArry = new JSONArray();
		String posUrl = "http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllGroup";
		try {
			JSONObject objRows = new JSONObject();
			objRows.put("strClientCode", strClientCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl, objRows);
			jArry = (JSONArray) jObj.get("allGroupData");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jArry;
	}

}
