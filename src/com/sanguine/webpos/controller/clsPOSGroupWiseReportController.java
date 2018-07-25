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
import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupWiseComparator;

@Controller
public class clsPOSGroupWiseReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;
	@Autowired
	private clsPOSGlobalFunctionsController objPOSGlobalFunctionsController;

	@Autowired
	private ServletContext servletContext;
	Map<String, String> hmSubGroupName = null;
	Map<String, String> hmPOSData = null;
	Map<String, String> hmSubGroupCode = null;

	@RequestMapping(value = "/frmPOSGroupWiseReport", method = RequestMethod.GET)
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

		hmPOSData = new HashMap<String, String>();
		JSONArray jArryPosList = objPOSGlobalFunctionsController.funGetAllPOSForMaster(strClientCode);
		for (int i = 0; i < jArryPosList.size(); i++) {
			JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
			poslist.add(josnObjRet.get("strPosName"));
			hmPOSData.put(josnObjRet.get("strPosName").toString(), josnObjRet.get("strPosCode").toString());
		}
		model.put("posList", poslist);

		List listSubGroup = funGetSubGroupGDetail(strClientCode);

		model.put("listSubGroup", listSubGroup);
		// return new ModelAndView("frmPOSGroupMaster");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSGroupWiseReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSGroupWiseReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSGroupWiseSales", method = RequestMethod.POST)
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
			String reportName = servletContext.getRealPath("/WEB-INF/reports/webpos/rptGroupWiseSalesReport.jrxml");
			String imagePath = servletContext.getRealPath("/resources/images/company_Logo.png");

			List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

			String strFromdate = objBean.getFromDate().split("-")[2] + "-" + objBean.getFromDate().split("-")[1] + "-" + objBean.getFromDate().split("-")[0];

			String strToDate = objBean.getToDate().split("-")[2] + "-" + objBean.getToDate().split("-")[1] + "-" + objBean.getToDate().split("-")[0];

			String type = objBean.getStrDocType();

			String strSGName = objBean.getStrSGName();
			String strPOSName = objBean.getStrPOSName();
			String posCode = "ALL";
			String sgCode = "ALL";
			if (!strPOSName.equalsIgnoreCase("ALL")) {
				posCode = hmPOSData.get(strPOSName);
			}

			if (!strSGName.equalsIgnoreCase("ALL")) {
				sgCode = hmSubGroupName.get("strSGName");// funGetSGCode(strSGName);
			}

			JSONObject jObjFillter = new JSONObject();
			jObjFillter.put("strFromdate", strFromdate);
			jObjFillter.put("strToDate", strToDate);
			jObjFillter.put("posCode", posCode);
			jObjFillter.put("sgCode", sgCode);
			jObjFillter.put("strShiftNo", "1");
			jObjFillter.put("userCode", userCode);

			JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/WebPOSReport/funGroupWiseSalesReport", jObjFillter);
			List<clsGroupWaiseSalesBean> list = new ArrayList<clsGroupWaiseSalesBean>();
			JSONArray jarr = (JSONArray) jObj.get("jArr");
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);

				// list.add(jObjtemp.get("strGroupCode"));
				// list.add(jObjtemp.get("strGroupName"));
				// list.add(jObjtemp.get("dblQuantity"));
				// list.add(jObjtemp.get("dblAmtLessDis"));
				// list.add(jObjtemp.get("strPOSName"));
				// list.add(jObjtemp.get("strUserCode"));
				// list.add(jObjtemp.get("dblRate"));
				// list.add(jObjtemp.get("dblAmount"));
				// list.add(jObjtemp.get("dblDiscountAmt"));
				// list.add(jObjtemp.get("strPOSCode"));
				// list.add(jObjtemp.get("dblAmtWithTax"));

				clsGroupWaiseSalesBean objClsGroupWaiseSalesBean = new clsGroupWaiseSalesBean();
				objClsGroupWaiseSalesBean.setGroupName(jObjtemp.get("strGroupName").toString());
				objClsGroupWaiseSalesBean.setPosName(jObjtemp.get("strPOSName").toString());
				objClsGroupWaiseSalesBean.setQty(Double.parseDouble(jObjtemp.get("dblQuantity").toString()));
				;
				objClsGroupWaiseSalesBean.setSalesAmt(Double.parseDouble(jObjtemp.get("dblAmount").toString()));
				objClsGroupWaiseSalesBean.setDiscAmt(Double.parseDouble(jObjtemp.get("dblDiscountAmt").toString()));
				objClsGroupWaiseSalesBean.setSubTotal(Double.parseDouble(jObjtemp.get("dblAmtLessDis").toString()));

				list.add(objClsGroupWaiseSalesBean);
			}
			Comparator<clsGroupWaiseSalesBean> groupComparator = new Comparator<clsGroupWaiseSalesBean>() {

				@Override
				public int compare(clsGroupWaiseSalesBean o1, clsGroupWaiseSalesBean o2) {
					return o1.getGroupName().compareToIgnoreCase(o2.getGroupName());
				}
			};

			Collections.sort(list, new clsGroupWiseComparator(groupComparator));

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

	/*
	 * public String funGetPOSCode(String strPOSName) { String posCode="";
	 * String posUrl =
	 * "http://localhost:8080/prjSanguineWebService/WebPOSPOSMaster/funGetPOSNameData"
	 * ;
	 * 
	 * System.out.println(posUrl);
	 * 
	 * try { JSONObject objRows = new JSONObject(); objRows.put("strPOSName",
	 * strPOSName);
	 * 
	 * JSONObject jObj =
	 * objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl,objRows);
	 * posCode= jObj.get("strPosCode").toString();
	 * 
	 * // URL url = new URL(posUrl);
	 * 
	 * // HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //
	 * conn.setRequestMethod("POST"); // conn.setRequestProperty("Content-Type",
	 * "application/json"); // OutputStream os = conn.getOutputStream(); //
	 * os.write(objRows.toString().getBytes()); // os.flush(); // if
	 * (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) // { // throw
	 * new RuntimeException("Failed : HTTP error code : " +
	 * conn.getResponseCode()); // } // BufferedReader br = new
	 * BufferedReader(new InputStreamReader((conn.getInputStream()))); // String
	 * output = "", op = ""; // // while ((output = br.readLine()) != null) // {
	 * // op += output; // } // System.out.println("Result= " + op); //
	 * conn.disconnect(); // // JSONParser parser = new JSONParser(); // Object
	 * obj = parser.parse(op); // josnObjRet = (JSONObject) obj;
	 * 
	 * 
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return posCode;
	 * 
	 * }
	 */

	public ArrayList<String> funGetSubGroupGDetail(String clientCode) {

		JSONObject jObj = objGlobalFunctions.funGETMethodUrlJosnObjectData("http://localhost:8080/prjSanguineWebService/APOSMastersIntegration/funGetAllSubGroup?clientCode=" + clientCode);
		JSONArray jArr = (JSONArray) jObj.get("allSGData");
		JSONObject subJsonObject = new JSONObject();
		hmSubGroupName = new HashedMap();
		hmSubGroupCode = new HashedMap();
		ArrayList<String> lstSGData = new ArrayList<String>();
		lstSGData.add("ALL");
		if (null != jArr) {
			for (int i = 0; i < jArr.size(); i++) {
				subJsonObject = (JSONObject) jArr.get(i);

				hmSubGroupName.put(subJsonObject.get("strSubGroupName").toString(), subJsonObject.get("strSubGroupCode").toString());
				hmSubGroupCode.put(subJsonObject.get("strSubGroupCode").toString(), subJsonObject.get("strSubGroupName").toString());
				lstSGData.add(subJsonObject.get("strSubGroupName").toString());
			}
		}
		return lstSGData;
	}

	/*
	 * public String funGetSGCode(String strSGName) { String sgCode=""; String
	 * posUrl =
	 * "http://localhost:8080/prjSanguineWebService/WebPOSSGMaster/funGetSGNameData"
	 * ; try { JSONObject objRows = new JSONObject(); objRows.put("strSGName",
	 * strSGName);
	 * 
	 * JSONObject jObj =
	 * objGlobalFunctions.funPOSTMethodUrlJosnObjectData(posUrl,objRows);
	 * sgCode= jObj.get("strSubGroupCode").toString(); // URL url = new
	 * URL(posUrl); // // HttpURLConnection conn = (HttpURLConnection)
	 * url.openConnection(); // conn.setRequestMethod("POST"); //
	 * conn.setRequestProperty("Content-Type", "application/json"); //
	 * OutputStream os = conn.getOutputStream(); //
	 * os.write(objRows.toString().getBytes()); // os.flush(); // if
	 * (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) // { // throw
	 * new RuntimeException("Failed : HTTP error code : " +
	 * conn.getResponseCode()); // } // BufferedReader br = new
	 * BufferedReader(new InputStreamReader((conn.getInputStream()))); // String
	 * output = "", op = ""; // // while ((output = br.readLine()) != null) // {
	 * // op += output; // } // System.out.println("Result= " + op); //
	 * conn.disconnect(); // // JSONParser parser = new JSONParser(); // Object
	 * obj = parser.parse(op); // josnObjRet = (JSONObject) obj; //
	 * 
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return sgCode;
	 * 
	 * }
	 */

	// public List funGetPOSList()
	// {
	// List listPos= new ArrayList();
	// listPos.add("ALL");
	// String posUrl =
	// "http://localhost:8080/prjSanguineWebService/APOSIntegration/funGetPOS";
	//
	// System.out.println(posUrl);
	//
	// try {
	//
	// URL url = new URL(posUrl);
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setRequestMethod("GET");
	// conn.setRequestProperty("Accept", "application/json");
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader((conn.getInputStream())));
	// String output = "", op = "";
	// while ((output = br.readLine()) != null)
	// {
	// op += output;
	// }
	// System.out.println("Obj="+op);
	// conn.disconnect();
	//
	// JSONParser parser = new JSONParser();
	// Object obj = parser.parse(op);
	// JSONObject jObj = (JSONObject) obj;
	// JSONArray jArryPosList =(JSONArray) jObj.get("posList");
	// for(int i =0 ;i<jArryPosList.size();i++)
	// {
	// JSONObject josnObjRet = (JSONObject) jArryPosList.get(i);
	// listPos.add(josnObjRet.get("strPosName"));
	// }
	// // System.out.println("hhh");
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return listPos;
	//
	// }

}
