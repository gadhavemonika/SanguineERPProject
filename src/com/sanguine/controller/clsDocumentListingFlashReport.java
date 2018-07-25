package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMaterialReturnHdModel;
import com.sanguine.model.clsRequisitionHdModel;
import com.sanguine.model.clsStockFlashModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsDocumentListingFlashReport {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	clsGlobalFunctions objGlobal;

	private List listDocFlash = null;

	@RequestMapping(value = "/frmDocumentListingFlashReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmDocumentFlashReport", method = RequestMethod.GET)
	private @ResponseBody List funShowDocumentFlashFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String docFlashType = spParam1[0];
		String loc = spParam1[1];
		String property = spParam1[2];
		String[] spDate = fDate.split("-");
		fDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String[] spTDate = tDate.split("-");
		tDate = spTDate[2] + "-" + spTDate[1] + "-" + spTDate[0];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		double dblTotalValue = 0, totSubTotal = 0, totTax = 0, totDis = 0, totExCharge = 0;

		switch (docFlashType) {
		case "GRN": {
			String sql = " select a.strGRNCode,date(a.dtGRNDate),b.strPName,a.strBillNo,date(a.dtBillDate),a.dblSubTotal,a.dblTaxAmt,a.dblDisRate," + " a.dblExtra,a.dblTotal " + " from tblgrnhd a ,tblpartymaster b ,tbllocationmaster c " + " where a.strSuppCode=b.strPCode " + " and a.strLocCode='" + loc + "' " + " and a.strLocCode=c.strLocCode " + " and a.strClientCode=b.strClientCode "
					+ " and b.strClientCode=c.strClientCode " + " and date(a.dtGRNDate) between '" + fDate + "' and '" + tDate + "' and a.strClientCode='" + clientCode + "'  ";

			System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);
			List<clsGRNHdModel> listDccFlashGRNModel = new ArrayList<clsGRNHdModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				clsGRNHdModel objDccFlashGRNModel = new clsGRNHdModel();
				objDccFlashGRNModel.setStrGRNCode(arrObj[0].toString());
				objDccFlashGRNModel.setDtGRNDate(arrObj[1].toString());
				objDccFlashGRNModel.setStrSuppName(arrObj[2].toString());
				objDccFlashGRNModel.setStrBillNo(arrObj[3].toString());
				objDccFlashGRNModel.setDtBillDate(arrObj[4].toString());
				objDccFlashGRNModel.setDblSubTotal(Double.parseDouble(arrObj[5].toString()));
				objDccFlashGRNModel.setDblTaxAmt(Double.parseDouble(arrObj[6].toString()));
				objDccFlashGRNModel.setDblDisAmt(Double.parseDouble(arrObj[7].toString()));
				objDccFlashGRNModel.setDblExtra(Double.parseDouble(arrObj[8].toString()));
				objDccFlashGRNModel.setDblTotal(Double.parseDouble(arrObj[9].toString()));

				totSubTotal = totSubTotal + Double.parseDouble(arrObj[5].toString());
				totTax = totTax + Double.parseDouble(arrObj[6].toString());
				totDis = totDis + Double.parseDouble(arrObj[7].toString());
				totExCharge = totExCharge + Double.parseDouble(arrObj[8].toString());
				dblTotalValue = dblTotalValue + Double.parseDouble(arrObj[9].toString());
				listDccFlashGRNModel.add(objDccFlashGRNModel);
			}

			ModelAndView objModelView = funGetModelAndView(req);
			objModelView.addObject("listGRNHdModel", listDccFlashGRNModel);

			listDocFlash = new ArrayList();
			listDocFlash.add(listDccFlashGRNModel);
			listDocFlash.add(totSubTotal);
			listDocFlash.add(totTax);
			listDocFlash.add(totDis);
			listDocFlash.add(totExCharge);
			listDocFlash.add(dblTotalValue);

			break;
		}

		case "MR": {
			String sql = " select a.strReqCode,date(a.dtReqDate),b.strLocName,c.strLocName,date(a.dtReqiredDate),a.dblSubTotal " + " from tblreqhd a,tbllocationmaster b,tbllocationmaster c " + " where a.strLocBy=b.strLocCode and a.strLocOn =c.strLocCode and  date(a.dtReqDate) BETWEEN '" + fDate + "' and '" + tDate + "' and a.strClientCode='" + clientCode + "'  ";

			System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);
			List<clsRequisitionHdModel> listDccFlashReqModel = new ArrayList<clsRequisitionHdModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				clsRequisitionHdModel objDccFlashReqModel = new clsRequisitionHdModel();
				objDccFlashReqModel.setStrReqCode(arrObj[0].toString());
				objDccFlashReqModel.setDtReqDate(arrObj[1].toString());
				objDccFlashReqModel.setStrLocByName(arrObj[2].toString());
				objDccFlashReqModel.setStrLocOnName(arrObj[3].toString());
				objDccFlashReqModel.setDtReqiredDate(arrObj[4].toString());
				objDccFlashReqModel.setDblSubTotal(Double.parseDouble(arrObj[5].toString()));

				totSubTotal = totSubTotal + Double.parseDouble(arrObj[5].toString());

				listDccFlashReqModel.add(objDccFlashReqModel);
			}

			ModelAndView objModelView = funGetModelAndView(req);
			objModelView.addObject("listGRNHdModel", listDccFlashReqModel);

			listDocFlash = new ArrayList();
			listDocFlash.add(listDccFlashReqModel);
			listDocFlash.add(totSubTotal);

			break;

		}

		case "MIS": {
			String sql = " select a.strMISCode,date(a.dtMISDate),b.strLocName,c.strLocName,a.dblTotalAmt " + " from tblmishd a ,tbllocationmaster b , tbllocationmaster c " + " where a.strLocFrom=b.strLocCode and a.strLocTo =c.strLocCode  " + " and date(a.dtMISDate) BETWEEN '" + fDate + "' and '" + tDate + "' and a.strClientCode='" + clientCode + "'  ";
			double totTotalAmt = 0.00;
			System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);
			List<clsMISHdModel> listDccFlashMISModel = new ArrayList<clsMISHdModel>();
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				clsMISHdModel objDccFlashMISModel = new clsMISHdModel();
				objDccFlashMISModel.setStrMISCode(arrObj[0].toString());
				objDccFlashMISModel.setDtMISDate(arrObj[1].toString());
				objDccFlashMISModel.setStrLocFromName(arrObj[2].toString());
				objDccFlashMISModel.setStrLocToName(arrObj[3].toString());
				objDccFlashMISModel.setDblTotalAmt(Double.parseDouble(arrObj[4].toString()));

				totTotalAmt = totTotalAmt + Double.parseDouble(arrObj[4].toString());

				listDccFlashMISModel.add(objDccFlashMISModel);
			}

			ModelAndView objModelView = funGetModelAndView(req);
			objModelView.addObject("listMISHdModel", listDccFlashMISModel);

			listDocFlash = new ArrayList();
			listDocFlash.add(listDccFlashMISModel);
			listDocFlash.add(totTotalAmt);

			break;
		}
		}

		return listDocFlash;
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		// String usercode=req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;

		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmDocumentListingFlashReport_1", "command", new clsReportBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmDocumentListingFlashReport", "command", new clsReportBean());
		}

		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();

		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}

		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}
		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		objModelView.addObject("listLocation", mapLocation);

		return objModelView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/downloadDocFlashExcel", method = RequestMethod.GET)
	public ModelAndView downloadDocFlashExcel(@RequestParam(value = "docFlashType") String docFlashType, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();

		List listExportDoc = new ArrayList();

		if (docFlashType.equals("GRN")) {
			String[] ExcelHeader = { "GRN Code", "GRN Date", "Supplier Name", "Bill No", "Bill Date", "Sub Total", "Tax Amt", "Dis Amt", "Extra Charges", "Grand Total" };
			listExportDoc.add(ExcelHeader);
			List listGRNModel = new ArrayList();
			List listGrn = new ArrayList<>();
			listGrn = (List) listDocFlash.get(0);
			for (int cnt = 0; cnt < listGrn.size(); cnt++) {
				clsGRNHdModel objGRNHdModel = (clsGRNHdModel) listGrn.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(objGRNHdModel.getStrGRNCode());
				DataList.add(objGRNHdModel.getDtGRNDate());
				DataList.add(objGRNHdModel.getStrSuppName());
				DataList.add(objGRNHdModel.getStrBillNo());
				DataList.add(objGRNHdModel.getDtBillDate());
				DataList.add(objGRNHdModel.getDblSubTotal());
				DataList.add(objGRNHdModel.getDblTaxAmt());
				DataList.add(objGRNHdModel.getDblDisAmt());
				DataList.add(objGRNHdModel.getDblExtra());
				DataList.add(objGRNHdModel.getDblTotal());

				listGRNModel.add(DataList);
			}

			listExportDoc.add(listGRNModel);
		}

		if (docFlashType.equals("MR")) {
			String[] ExcelHeader = { "Req Code ", "Req Date", "Loc By", "Loc On", "Required Date", "Sub Total" };
			listExportDoc.add(ExcelHeader);
			List listMRModel = new ArrayList();
			List listMR = new ArrayList<>();
			listMR = (List) listDocFlash.get(0);
			for (int cnt = 0; cnt < listMR.size(); cnt++) {
				clsRequisitionHdModel objMRHdModel = (clsRequisitionHdModel) listMR.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(objMRHdModel.getStrReqCode());
				DataList.add(objMRHdModel.getDtReqDate());
				DataList.add(objMRHdModel.getStrLocByName());
				DataList.add(objMRHdModel.getStrLocOnName());
				DataList.add(objMRHdModel.getDtReqiredDate());
				DataList.add(objMRHdModel.getDblSubTotal());
				listMRModel.add(DataList);
			}
			listExportDoc.add(listMRModel);
		}

		if (docFlashType.equals("MIS")) {
			String[] ExcelHeader = { "MIS Code", "MIS Date", "Loc From", "Loc To", "Total Amt" };
			listExportDoc.add(ExcelHeader);
			List listMISModel = new ArrayList();
			List listMIS = new ArrayList<>();
			listMIS = (List) listDocFlash.get(0);
			for (int cnt = 0; cnt < listMIS.size(); cnt++) {
				clsMISHdModel objMISHdModel = (clsMISHdModel) listMIS.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(objMISHdModel.getStrMISCode());
				DataList.add(objMISHdModel.getDtMISDate());
				DataList.add(objMISHdModel.getStrLocFromName());
				DataList.add(objMISHdModel.getStrLocToName());
				DataList.add(objMISHdModel.getDblTotalAmt());
				listMISModel.add(DataList);
			}
			listExportDoc.add(listMISModel);
		}

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "stocklist", listExportDoc);

	}

}
