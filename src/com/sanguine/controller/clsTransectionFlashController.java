package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DecimalFormat;
import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStockFlashService;

@Controller
public class clsTransectionFlashController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	clsMISController objMIS;

	@Autowired
	clsGRNController objGRN;

	@Autowired
	clsMaterialReturnController objMatReturn;

	@Autowired
	clsStkAdjustmentController objStkAdj;

	@Autowired
	clsMaterialReqController objMatReq;

	@Autowired
	clsStkTransferController objStkTransfer;

	@Autowired
	clsProductionController objProduction;

	@Autowired
	clsPurchaseReturnController objPurRet;

	@Autowired
	clsStockController objOpStk;

	@Autowired
	clsSetupMasterService objSetupMasterService;

	@RequestMapping(value = "/frmTransactionFlash", method = RequestMethod.GET)
	private ModelAndView funOpenStockLedger(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, HttpServletRequest req) {
		return funGetModelAndView(req);
	}

	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		ModelAndView objModelView = new ModelAndView("frmTransactionFlash");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		objModelView.addObject("listProperty", mapProperty);
		HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}

		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);
		return objModelView;
	}

	@RequestMapping(value = "/frmTransReport", method = RequestMethod.GET)
	private @ResponseBody List funShowfrmTransReportFlash(@RequestParam(value = "locCode") String locCode, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "userCode") String userCode, HttpServletRequest req, HttpServletResponse resp) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		System.out.println(startDate);

		List listTransFlah = funTransReportFlashQuery(locCode, fromDate, toDate, userCode);

		return listTransFlah;
	}

	@RequestMapping(value = "/openTransection", method = RequestMethod.GET)
	private ModelAndView funOpenStockLedgerFromStockFlash(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, @RequestParam(value = "transType") String transData, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "locCode") String locCode, @RequestParam(value = "propCode") String propCode,
			@RequestParam(value = "user") String user,

			HttpServletRequest req) {
		ModelAndView view = new ModelAndView("frmTransactionFlash", "command", objPropBean);
		transData = transData + "," + fDate + "," + tDate + "," + locCode + "," + propCode + "," + user;
		view.addObject("transData", transData);
		view.addObject("flgTransShow", "true");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		view.addObject("listProperty", mapProperty);
		HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}

		view.addObject("LoggedInProp", propertyCode);
		view.addObject("LoggedInLoc", locationCode);

		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		view.addObject("listLocation", mapLocation);
		return view;
		// return funGetModelAndView1();
	}

	@RequestMapping(value = "/showTransWiseData", method = RequestMethod.GET)
	private @ResponseBody List funShowTransWiseData(@RequestParam(value = "transType") String transType, @RequestParam(value = "locCode") String locCode, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "user") String user, HttpServletRequest req, HttpServletResponse resp) {

		String sql = "";
		fDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		tDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		List listTransWise = funTransWiseDataQuery(transType, locCode, fDate, tDate, user);

		return listTransWise;
	}

	@RequestMapping(value = "/downloadExcelTransReportFlashOrDetail", method = RequestMethod.GET)
	public ModelAndView downloadExcelTransReportFlash(@RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "locCode") String locCode, @RequestParam(value = "userCode") String userCode, @RequestParam(value = "transeRptType") String transeRptType, HttpServletRequest req, HttpServletResponse resp) {

		List listTranesSumData = new ArrayList();
		listTranesSumData.add("TranesSumData_" + fDate + "to" + tDate + "_" + userCode);

		fDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		tDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		if (userCode.length() == 0) {
			userCode = "All";
		}

		if ((transeRptType.length()) != 0) {

			String[] ExcelHeader = { "Trans Code", "Transe Date", "User Created", "User Edited", "Total Value" };
			listTranesSumData.add(ExcelHeader);
			List listTransWise = funTransWiseDataQuery(transeRptType, locCode, fDate, tDate, userCode);
			List listTranesData = new ArrayList();
			for (int cnt = 0; cnt < listTransWise.size(); cnt++) {
				Object[] arrObj = (Object[]) listTransWise.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
				DataList.add(arrObj[2].toString());
				DataList.add(arrObj[3].toString());
				DataList.add(arrObj[4].toString());
				listTranesData.add(DataList);
			}
			listTranesSumData.add(listTranesData);

		} else {
			String[] ExcelHeader = { "Trans Type", "Location", "User Name", "No of Trans" };
			listTranesSumData.add(ExcelHeader);
			List listTransWiseData = funTransReportFlashQuery(locCode, fDate, tDate, userCode);
			List listTranesData = new ArrayList();
			for (int cnt = 0; cnt < listTransWiseData.size(); cnt++) {
				Object[] arrObj = (Object[]) listTransWiseData.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(arrObj[2].toString());
				DataList.add(arrObj[3].toString());
				DataList.add(arrObj[4].toString());
				DataList.add(arrObj[1].toString());
				listTranesData.add(DataList);
			}
			listTranesSumData.add(listTranesData);

		}
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listTranesSumData);

	}

	private List funTransReportFlashQuery(String locCode, String fromDate, String toDate, String userCode) {
		String sql = " select TransNo,NoOfTranse,TransType,Location,UserName from "

		+ "(";

		sql += " select 1 TransNo,count(a.strUserCreated) NoOfTranse , 'Opening Stk' TransType, b.strLocName Location, " + " a.strUserCreated username " + " from tblinitialinventory a,tbllocationmaster b " + "  where   a.strLocCode='" + locCode + "' and a.strLocCode=b.strLocCode " + " and date(a.dtCreatedDate) = '" + fromDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 2 TransNo,count(a.strUserCreated) NoOfTranse , 'MIS Out' TransType, b.strLocName Location,a.strUserCreated username " + " from tblmishd a, tbllocationmaster b where   a.strLocFrom='" + locCode + "'  " + " and  a.strLocFrom=b.strLocCode " + " and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 3 TransNo,count(a.strUserCreated) NoOfTranse , 'MIS In' TransType, b.strLocName Location,a.strUserCreated username " + " from tblmishd a, tbllocationmaster b  " + " where   a.strLocTo='" + locCode + "' and a.strLocTo=b.strLocCode and " + " date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 4 TransNo,count(a.strUserCreated) NoOfTranse , 'GRN' TransType, b.strLocName Location,a.strUserCreated username " + " from tblgrnhd a, tbllocationmaster b where  a.strLocCode='" + locCode + "' " + " and a.strLocCode=b.strLocCode and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "'  ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 5 TransNo,count(a.strUserCreated) NoOfTranse , 'Stock Adjustment In' TransType, c.strLocName Location, " + " a.strUserCreated username from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c  " + " where a.strSACode = b.strSACode   and a.strLocCode=c.strLocCode and b.strType='IN' " + " and a.strLocCode='" + locCode + "' and date(a.dtSADate) >= '"
				+ fromDate + "' " + " and date(a.dtSADate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 5 TransNo,count(a.strUserCreated) NoOfTranse , 'StkAdj Out' TransType, c.strLocName Location," + " a.strUserCreated username from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + " where a.strSACode = b.strSACode  and a.strLocCode=c.strLocCode and b.strType='OUT' " + " and a.strLocCode='" + locCode + "' and date(a.dtSADate) >= '" + fromDate
				+ "' and date(a.dtSADate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 6 TransNo,count(a.strUserCreated) NoOfTranse , 'Stock Transfer In' TransType, b.strLocName Location, " + "a.strUserCreated username " + " from tblstocktransferhd a, tbllocationmaster b  where  a.strToLocCode = b.strLocCode " + " and a.strToLocCode='" + locCode + "' " + " and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 7 TransNo,count(a.strUserCreated) NoOfTranse , 'Stock Transfer Out' TransType, b.strLocName Location," + " a.strUserCreated username from tblstocktransferhd a, tbllocationmaster b " + " where  a.strFromLocCode = b.strLocCode and a.strFromLocCode='" + locCode + "' and " + " date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 8 TransNo,count(a.strUserCreated) NoOfTranse , 'Material Return In' TransType, b.strLocName Location," + " a.strUserCreated username from tblmaterialreturnhd a, tbllocationmaster b " + " where a.strLocTo=b.strLocCode  and a.strLocTo='" + locCode + "' " + " and date(a.dtMRetDate) >= '" + fromDate + "'  and date(a.dtMRetDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 9 TransNo,count(a.strUserCreated) NoOfTranse , 'Material Return Out' TransType, b.strLocName Location," + " a.strUserCreated username from tblmaterialreturnhd a, tbllocationmaster b " + " where a.strLocFrom=b.strLocCode  and a.strLocFrom='" + locCode + "' and " + " date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 10 TransNo,count(a.strUserCreated) NoOfTranse , 'Production' TransType, b.strLocName Location," + " a.strUserCreated username from tblproductionhd a, tbllocationmaster b " + " where  a.strLocCode=b.strLocCode  and a.strLocCode='" + locCode + "' " + " and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 11 TransNo,count(a.strUserCreated) NoOfTranse , 'Purchase Return' TransType, b.strLocName Location," + " a.strUserCreated username from tblpurchasereturnhd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strLocCode='" + locCode + "' " + " and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 12 TransNo,count(a.strUserCreated) NoOfTranse , 'Sales Ret' TransType, b.strLocName Location," + " a.strUserCreated username from tblsalesreturnhd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strLocCode = '" + locCode + "'  and " + " date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 13 TransNo,count(a.strUserCreated) NoOfTranse , 'Delivery challan' TransType, b.strLocName Location," + " a.strUserCreated username from tbldeliverychallanhd a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strLocCode = '" + locCode + "' " + " and date(a.dteDCDate) >= '" + fromDate + "' and date(a.dteDCDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username " + "union all ";

		sql += " select 14 TransNo,count(a.strUserCreated) NoOfTranse , 'Invoice' TransType, b.strLocName Location," + " a.strUserCreated username from tblinvoicehd  a, tbllocationmaster b " + " where a.strLocCode=b.strLocCode and a.strLocCode = '" + locCode + "' " + " and date(a.dteInvDate) >= '" + fromDate + "' and date(a.dteInvDate) <= '" + toDate + "' ";
		if (!userCode.equalsIgnoreCase("All")) {
			sql += " and a.strUserCreated='" + userCode + "' ";
		}
		sql += " group by username ";

		sql += ") a "

		+ " order by TransNo desc  ";

		List listTransFlah = objGlobalService.funGetList(sql, "sql");
		;

		return listTransFlah;
	}

	private List funTransWiseDataQuery(String transType, String locCode, String fDate, String tDate, String user) {
		String sql = "";
		switch (transType) {

		case "Opening Stk": {
			sql = " select a.strOpStkCode,DATE_FORMAT(a.dtCreatedDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,'0.00' " + " from tblinitialinventory a where a.strLocCode='" + locCode + "' and " + " date(a.dtCreatedDate) >= '" + fDate + "' and date(a.dtCreatedDate) <= '" + tDate + "'  ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		case "MIS Out": {
			sql = " select a.strMISCode,DATE_FORMAT(a.dtMISDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt " + " from tblmishd a where a.strLocFrom='" + locCode + "' and " + " date(a.dtMISDate) >= '" + fDate + "' and date(a.dtMISDate) <= '" + tDate + "' ";

			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "MIS In": {
			sql = " select a.strMISCode,DATE_FORMAT(a.dtMISDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt " + " from tblmishd a where a.strLocTo='" + locCode + "' and " + " date(a.dtMISDate) >= '" + fDate + "' and date(a.dtMISDate) <= '" + tDate + "' ";

			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "GRN": {
			sql = " select a.strGRNCode,DATE_FORMAT(a.dtGRNDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotal " + " from tblgrnhd a where a.strLocCode='" + locCode + "' and " + " date(a.dtGRNDate) >= '" + fDate + "' and date(a.dtGRNDate) <= '" + tDate + "'  ";

			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "Stock Adjustment In": {
			sql = " select a.strSACode,DATE_FORMAT(a.dtSADate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt  " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b " + "  where a.strSACode=b.strSACode and b.strType='IN' and a.strLocCode='" + locCode + "' " + " and date(a.dtSADate) >= '" + fDate + "' and date(a.dtSADate) <= '" + tDate + "' ";

			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "StkAdj Out": {
			sql = " select a.strSACode,DATE_FORMAT(a.dtSADate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt  " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b " + "  where a.strSACode=b.strSACode and b.strType='OUT' and a.strLocCode='" + locCode + "' " + " and date(a.dtSADate) >= '" + fDate + "' and date(a.dtSADate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "Stock Transfer In": {
			sql = " select a.strSTCode,DATE_FORMAT(a.dtSTDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt " + " from tblstocktransferhd a where a.strToLocCode='" + locCode + "' " + " and date(a.dtSTDate) >= '" + fDate + "' and date(a.dtSTDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "Stock Transfer Out": {
			sql = " select a.strSTCode,DATE_FORMAT(a.dtSTDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotalAmt " + " from tblstocktransferhd a where a.strFromLocCode='" + locCode + "' " + " and date(a.dtSTDate) >= '" + fDate + "' and date(a.dtSTDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}
			break;
		}

		case "Material Return In": {
			sql = "  select a.strMRetCode,DATE_FORMAT(a.dtMRetDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,'0.00' " + " from tblmaterialreturnhd a where a.strLocTo='" + locCode + "' " + " and date(a.dtMRetDate) >= '" + fDate + "' and date(a.dtMRetDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		case "Material Return Out": {
			sql = " select a.strMRetCode,DATE_FORMAT(a.dtMRetDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,'0.00' " + " from tblmaterialreturnhd a where a.strLocFrom='" + locCode + "' " + " and date(a.dtMRetDate) >= '" + fDate + "' and date(a.dtMRetDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		case "Production": {
			sql = " select a.strPDCode,DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified ,'0.00' " + " from tblproductionhd a where a.strLocCode='" + locCode + "' " + " and date(a.dtPDDate) >= '" + fDate + "' and date(a.dtPDDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		case "Purchase Return": {
			sql = " select a.strPRCode,DATE_FORMAT(a.dtPRDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblTotal  " + " from tblpurchasereturnhd a where a.strLocCode='" + locCode + "' " + " and date(a.dtPRDate) >= '" + fDate + "' and date(a.dtPRDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}
		case "Sales Ret": {
			sql = " select a.strSRCode,DATE_FORMAT(a.dteSRDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserEdited,a.dblTaxAmt " + " from tblsalesreturnhd a where a.strLocCode='" + locCode + "' " + " and date(a.dteSRDate) >= '" + fDate + "' and date(a.dteSRDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		case "Delivery challan": {
			sql = " select a.strDCCode,DATE_FORMAT(a.dteDCDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,'0.00' " + " from tbldeliverychallanhd a where a.strLocCode='" + locCode + "' " + " and date(a.dteDCDate) >= '" + fDate + "' and date(a.dteDCDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}
		case "Invoice": {
			sql = " select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y') Transedate ,a.strUserCreated,a.strUserModified,a.dblGrandTotal " + " from tblinvoicehd a where a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) >= '" + fDate + "' and date(a.dteInvDate) <= '" + tDate + "' ";
			if (!user.equalsIgnoreCase("All")) {
				sql += " and a.strUserCreated='" + user + "' ";
			}

			break;
		}

		}
		List listTransWise = objGlobalService.funGetList(sql, "sql");

		return listTransWise;
	}

}
