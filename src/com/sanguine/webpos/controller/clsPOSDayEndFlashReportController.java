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
import com.sanguine.webpos.bean.clsPOSDayEndFlashBean;
import com.sanguine.webpos.bean.clsPOSItemModifierMasterBean;
import com.sanguine.webpos.bean.clsPOSTaxWaiseBean;
import com.sanguine.webpos.bean.clsWebPOSReportBean;
import com.sanguine.webpos.util.clsGroupWiseComparator;

@Controller
public class clsPOSDayEndFlashReportController {

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private ServletContext servletContext;

	Map map = new HashMap();

	@RequestMapping(value = "/frmPOSDayEndFlash", method = RequestMethod.GET)
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
			return new ModelAndView("frmPOSDayEndFlashReport_1", "command", new clsWebPOSReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmPOSDayEndFlashReport", "command", new clsWebPOSReportBean());
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptPOSDayEndFlashReport", method = RequestMethod.POST)
	private ModelAndView funReport(@ModelAttribute("command") clsWebPOSReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String strPOSName = objBean.getStrPOSName();

		String strFromdate = objBean.getFromDate();
		String strToDate = objBean.getToDate();
		Map resMap = new LinkedHashMap();

		resMap = FunGetData(clientCode, strFromdate, strToDate, strPOSName);

		List ExportList = new ArrayList();

		String FileName = "DayEndFlash" + strFromdate + "_To_" + strToDate;

		ExportList.add(FileName);

		List List = (List) resMap.get("listcol");

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
	@RequestMapping(value = { "/loadDayEndFlash" }, method = RequestMethod.GET)
	@ResponseBody
	public Map FunLoadBulItemkPricingMaster(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String strFromdate = req.getParameter("fromDate");

		String strToDate = req.getParameter("toDate");

		String posName = req.getParameter("posName");

		resMap = FunGetData(clientCode, strFromdate, strToDate, posName);
		return resMap;
	}

	@SuppressWarnings({ "unchecked" })
	private LinkedHashMap FunGetData(String clientCode, String strFromdate, String strToDate, String strPOSName) {
		LinkedHashMap resMap = new LinkedHashMap();

		double amtTotal = 0, netTotal = 0, paxTotal = 0;

		List colHeader = new ArrayList();
		String posCode = "All";

		String fromDate1 = strFromdate.split("-")[2] + "-" + strFromdate.split("-")[1] + "-" + strFromdate.split("-")[0];

		String toDate1 = strToDate.split("-")[2] + "-" + strToDate.split("-")[1] + "-" + strToDate.split("-")[0];

		if (!strPOSName.equalsIgnoreCase("ALL")) {
			if (map.containsKey(strPOSName)) {
				posCode = (String) map.get(strPOSName);
				// System.out.println(posCode.length());
			}

		}

		JSONObject jObjFillter = new JSONObject();
		jObjFillter.put("strFromdate", fromDate1);
		jObjFillter.put("strToDate", toDate1);
		jObjFillter.put("posCode", posCode);

		JSONObject jObj = objGlobalFunctions.funPOSTMethodUrlJosnObjectData(clsPOSGlobalFunctionsController.POSWSURL + "/WebPOSReport/funDayEndFlashReport", jObjFillter);
		List list = new ArrayList();
		List totalList = new ArrayList();
		JSONArray jarr = (JSONArray) jObj.get("jArr");

		totalList.add("Total");
		totalList.add(" ");
		if (null != jarr) {
			for (int i = 0; i < jarr.size(); i++) {
				JSONObject jObjtemp = (JSONObject) jarr.get(i);
				List arrList = new ArrayList();

				arrList.add(jObjtemp.get("strPOSName").toString());
				arrList.add(jObjtemp.get("dtePOSDate").toString());
				arrList.add(Double.parseDouble(jObjtemp.get("dblHDAmt").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblDiningAmt").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTakeAway").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTotalSale").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblFloat").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblCash").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblAdvance").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTransferIn").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTotalReceipt").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblPayments").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblWithdrawal").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTransferOut").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblRefund").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblTotalPay").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblCashInHand").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblNoOfBill").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblNoOfVoidedBill").toString()));
				arrList.add(Double.parseDouble(jObjtemp.get("dblNoOfModifyBill").toString()));

				list.add(arrList);

			}

			JSONObject jObjTotal = (JSONObject) jObj.get("jObjTatol");
			;
			totalList.add(Double.parseDouble(jObjTotal.get("sumHdAmt").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumDining").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumTaleAway").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumtSale").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumFloat").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumCash").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumAdvance").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumTransferIn").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumTotalReceipt").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumPay").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumWithDrawal").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumTransferOut").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumRefund").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumRefund").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumTotalPay").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumCashInhand").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumNoOfBill").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumNoOfVoidedBill").toString()));
			totalList.add(Double.parseDouble(jObjTotal.get("sumNoOfModifyBill").toString()));

			List listcol = new ArrayList();

			listcol.add("pos");
			listcol.add("pos Date");
			listcol.add("HD Amt");
			listcol.add("Dining Amt");
			listcol.add("DTake Away");
			listcol.add("Total Sale");
			listcol.add("Float");
			listcol.add("Cash");
			listcol.add("Advance");
			listcol.add("Transfer In");
			listcol.add("Total Receipt");
			listcol.add("Pay");
			listcol.add("With Drawal");
			listcol.add("Tranf Out");
			listcol.add("Refund");
			listcol.add("Total Pay");
			listcol.add("Cash In Hand");
			listcol.add("No Of Bill");
			listcol.add("No Of Voided Bill");
			listcol.add("No Of Modify Bil");

			resMap.put("listcol", listcol);
			resMap.put("List", list);
			resMap.put("totalList", totalList);
		}

		return resMap;
	}
}
