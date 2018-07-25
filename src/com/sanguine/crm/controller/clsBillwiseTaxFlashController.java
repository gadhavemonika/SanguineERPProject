package com.sanguine.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTaxMasterService;

@Controller
public class clsBillwiseTaxFlashController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsTaxMasterService objTaxMasterService;

	// for Jsp Open
	@RequestMapping(value = "/frmBillwiseTaxFlash", method = RequestMethod.GET)
	public ModelAndView funOpenBillwiseTaxFlashForm(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);
	}

	// it is for Export the Excel file of the BillwiseTaxData.
	// it export all taxes(Columns) of bills during the dates in matrix format
	// Please UnderStand the Logic of the function.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadBillwisetaxData", method = RequestMethod.GET)
	private ModelAndView funExportBillwisetaxDataFlash(@RequestParam(value = "fromDate") String fDate, @RequestParam(value = "toDate") String tDate, @RequestParam(value = "strLocCode") String strLocCode, @RequestParam(value = "strHSN") String strHSN, HttpServletRequest req, HttpServletResponse resp) {

		List retList = new ArrayList();
		List headerList = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		/*
		 * String sql =
		 * " select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strTaxCode,c.strTaxDesc,b.dblTaxAmt "
		 * + " from tblinvoicehd a, tblinvtaxdtl b,tbltaxhd c  " +
		 * " where a.strInvCode=b.strInvCode   " +
		 * " and c.strTaxCode=b.strTaxCode and a.strLocCode='"+strLocCode+"' " +
		 * " and  date(a.dteInvDate) BETWEEN '"+fDate+"' and '"+tDate+"' " +
		 * " and a.strClientCode='"
		 * +clientCode+"' and  b.strClientCode='"+clientCode
		 * +"' and c.strClientCode='"+clientCode+"' " +
		 * " order by a.dteInvDate,a.strInvCode,b.strTaxCode ";
		 */
		String sql = "  select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strDocNo,c.strTaxDesc, " + "  b.dblValue,f.strExciseChapter " + " from tblinvoicehd a, tblinvprodtaxdtl b,tbltaxhd c ,tblproductmaster e,tblsubgroupmaster f  " + " where a.strInvCode=b.strInvCode    and c.strTaxCode=b.strDocNo and b.strProdCode = e.strProdCode  "
				+ " and e.strSGCode=f.strSGCode   and a.strLocCode='" + strLocCode + "'  " + "   and  date(a.dteInvDate) BETWEEN  '" + fDate + "' and '" + tDate + "'   " + "  and a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "'  " + "  order by a.dteInvDate,a.strInvCode,b.strDocNo  ";

		List list = objGlobalService.funGetList(sql);
		NavigableSet<String> stBill = new TreeSet<String>();
		// NavigableSet<String> stTaxDesc = new TreeSet<String>();
		SortedSet<String> stTaxCode = new TreeSet<String>();
		Map<String, String> hmTaxHsn = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] arrObj = (Object[]) list.get(i);
			stBill.add(arrObj[0].toString());
			stTaxCode.add(arrObj[2].toString());
			// stTaxDesc.add(arrObj[3].toString());
			if (hmTaxHsn.containsKey(arrObj[2].toString())) {
				hmTaxHsn.put(arrObj[2].toString(), hmTaxHsn.get(arrObj[2].toString()) + "," + arrObj[5].toString());
			} else {
				hmTaxHsn.put(arrObj[2].toString(), arrObj[5].toString());
			}
		}

		String userCode = req.getSession().getAttribute("usercode").toString();
		retList.add("BillwisetaxData_" + fDate + "to" + tDate + "_" + userCode);

		headerList.add("Bill No");
		headerList.add("Bill Date");
		for (String strTaxCode : stTaxCode) {
			String hsn = hmTaxHsn.get(strTaxCode);
			String[] arrHsn = hsn.split(",");
			Set<String> stHSN = new HashSet<String>();
			for (String hsnNo : arrHsn) {
				stHSN.add(hsnNo);
			}
			clsTaxHdModel objModel = objTaxMasterService.funGetObject(strTaxCode, clientCode);
			String disHsn = "";
			for (String str : stHSN) {
				disHsn += str + ", ";
			}
			disHsn = (String) disHsn.subSequence(0, disHsn.length() - 2);
			if (strHSN.equals("Yes")) {
				headerList.add(objModel.getStrTaxDesc() + " (" + disHsn + ")");
			} else {
				headerList.add(objModel.getStrTaxDesc());
			}
		}
		headerList.add("Total Tax Amt");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		List listTaxCode = new ArrayList<>();
		listTaxCode.addAll(stTaxCode);
		retList.add(ExcelHeader);
		List detailList = new ArrayList();
		Map<String, Double> hmTaxWiseTotal = new HashMap<String, Double>();
		for (String strBill : stBill) {
			double billwiseRowTaxTotalAmt = 0.00;
			List DataList = new ArrayList();
			String sqlBill = " select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y') " + " from tblinvoicehd a " + " where " + " a.strLocCode='" + strLocCode + "' " + " and a.strInvCode='" + strBill + "' " + " group by a.strInvCode ";
			List listRowData = objGlobalService.funGetList(sqlBill);
			for (int i = 0; i < listRowData.size(); i++) {
				Object[] arrObj = (Object[]) listRowData.get(i);
				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
			}

			for (String strTaxCode : stTaxCode) {
				String sqlTaxBill = "  select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strTaxCode,c.strTaxDesc, " + " b.dblTaxAmt  from tblinvoicehd a, tblinvtaxdtl b,tbltaxhd   c " + " where a.strInvCode=b.strInvCode    " + " and c.strTaxCode=b.strTaxCode and a.strLocCode='" + strLocCode + "' " + " and a.strInvCode='" + strBill + "' and b.strTaxCode='" + strTaxCode + "' "
						+ " group by a.strInvCode,b.strTaxCode ";
				List listTaxData = objGlobalService.funGetList(sqlTaxBill);
				for (int i = 0; i < listTaxData.size(); i++) {
					Object[] arrObjTax = (Object[]) listTaxData.get(i);
					DataList.add(arrObjTax[4].toString());
					billwiseRowTaxTotalAmt += Double.parseDouble(arrObjTax[4].toString());

					if (hmTaxWiseTotal.containsKey(strTaxCode)) {
						double oldTaxesAmt = hmTaxWiseTotal.get(strTaxCode);
						hmTaxWiseTotal.put(strTaxCode, Double.parseDouble(arrObjTax[4].toString()) + oldTaxesAmt);
					} else {
						hmTaxWiseTotal.put(strTaxCode, Double.parseDouble(arrObjTax[4].toString()));
					}

				}
				if (listTaxData.size() == 0) {
					DataList.add(0.00);
				}

			}
			DataList.add(billwiseRowTaxTotalAmt); // total Amt
			detailList.add(DataList);
		}

		// Blank Row at Bottom
		List blankList = new ArrayList();
		detailList.add(blankList);

		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		double dblSumOfTotalTaxesAmt = 0.00;
		for (String strTaxCode : stTaxCode) {
			double dblSumOfEachTaxesAmt = hmTaxWiseTotal.get(strTaxCode);
			totalsList.add(dblSumOfEachTaxesAmt);
			dblSumOfTotalTaxesAmt += dblSumOfEachTaxesAmt;
		}
		totalsList.add(dblSumOfTotalTaxesAmt);
		detailList.add(totalsList);

		retList.add(detailList);

		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);

		// return null;
	}

	// it is for shows in jsp file of the BillwiseTaxData.
	// it shows all taxes(Columns) of bills during the dates in matrix format
	// Please UnderStand the Logic of the function.
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/showBillwisetaxDataFlash", method = RequestMethod.GET)
	private @ResponseBody List funShowBillwisetaxDataFlash(@RequestParam(value = "fromDate") String fDate, @RequestParam(value = "toDate") String tDate, @RequestParam(value = "strLocCode") String strLocCode, @RequestParam(value = "strHSN") String strHSN, HttpServletRequest req, HttpServletResponse resp) {

		List retList = new ArrayList();
		List headerList = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		/*
		 * String sql =
		 * " select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strTaxCode,c.strTaxDesc,b.dblTaxAmt "
		 * + " from tblinvoicehd a, tblinvtaxdtl b,tbltaxhd c  " +
		 * " where a.strInvCode=b.strInvCode   " +
		 * " and c.strTaxCode=b.strTaxCode and a.strLocCode='"+strLocCode+"' " +
		 * " and  date(a.dteInvDate) BETWEEN '"+fDate+"' and '"+tDate+"' " +
		 * " and a.strClientCode='"
		 * +clientCode+"' and  b.strClientCode='"+clientCode
		 * +"' and c.strClientCode='"+clientCode+"' " +
		 * " order by a.dteInvDate,a.strInvCode,b.strTaxCode ";
		 */

		String sql = "  select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strDocNo,c.strTaxDesc, " + "  b.dblValue,f.strExciseChapter " + " from tblinvoicehd a, tblinvprodtaxdtl b,tbltaxhd c ,tblproductmaster e,tblsubgroupmaster f  " + " where a.strInvCode=b.strInvCode    and c.strTaxCode=b.strDocNo and b.strProdCode = e.strProdCode  "
				+ " and e.strSGCode=f.strSGCode   and a.strLocCode='" + strLocCode + "'  " + "   and  date(a.dteInvDate) BETWEEN  '" + fDate + "' and '" + tDate + "'   " + "  and a.strClientCode='" + clientCode + "' and  b.strClientCode='" + clientCode + "' " + " and c.strClientCode='" + clientCode + "'  " + "  order by a.dteInvDate,a.strInvCode,b.strDocNo  ";

		List list = objGlobalService.funGetList(sql);
		NavigableSet<String> stBill = new TreeSet<String>();
		// NavigableSet<String> stTaxDesc = new TreeSet<String>();
		SortedSet<String> stTaxCode = new TreeSet<String>();
		Map<String, String> hmTaxHsn = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Object[] arrObj = (Object[]) list.get(i);
			stBill.add(arrObj[0].toString());
			stTaxCode.add(arrObj[2].toString());
			if (hmTaxHsn.containsKey(arrObj[2].toString())) {
				hmTaxHsn.put(arrObj[2].toString(), hmTaxHsn.get(arrObj[2].toString()) + "," + arrObj[5].toString());
			} else {
				hmTaxHsn.put(arrObj[2].toString(), arrObj[5].toString());
			}

			// stTaxDesc.add(arrObj[3].toString());
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		// /retList.add("BillwisetaxData_"+fDate+"to"+tDate+"_"+userCode);

		headerList.add("Bill No");
		headerList.add("Bill Date");
		for (String strTaxCode : stTaxCode) {
			String hsn = hmTaxHsn.get(strTaxCode);
			String[] arrHsn = hsn.split(",");
			Set<String> stHSN = new HashSet<String>();
			for (String hsnNo : arrHsn) {
				stHSN.add(hsnNo);
			}
			clsTaxHdModel objModel = objTaxMasterService.funGetObject(strTaxCode, clientCode);
			String disHsn = "";
			for (String str : stHSN) {
				disHsn += str + ", ";
			}
			disHsn = (String) disHsn.subSequence(0, disHsn.length() - 2);
			if (strHSN.equals("Yes")) {
				headerList.add(objModel.getStrTaxDesc() + " (" + disHsn + ")");
			} else {
				headerList.add(objModel.getStrTaxDesc());
			}

		}
		headerList.add("Total Tax Amt");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		List listTaxCode = new ArrayList<>();
		listTaxCode.addAll(stTaxCode);
		retList.add(ExcelHeader);
		List detailList = new ArrayList();
		Map<String, Double> hmTaxWiseTotal = new HashMap<String, Double>();
		for (String strBill : stBill) {
			double billwiseRowTaxTotalAmt = 0.00;
			List DataList = new ArrayList();
			String sqlBill = " select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y') " + " from tblinvoicehd a " + " where " + " a.strLocCode='" + strLocCode + "' " + " and a.strInvCode='" + strBill + "' " + " group by a.strInvCode ";
			List listRowData = objGlobalService.funGetList(sqlBill);
			for (int i = 0; i < listRowData.size(); i++) {
				Object[] arrObj = (Object[]) listRowData.get(i);
				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
			}

			for (String strTaxCode : stTaxCode) {
				String sqlTaxBill = "  select a.strInvCode,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strTaxCode,c.strTaxDesc, " + " b.dblTaxAmt  from tblinvoicehd a, tblinvtaxdtl b,tbltaxhd   c " + " where a.strInvCode=b.strInvCode    " + " and c.strTaxCode=b.strTaxCode and a.strLocCode='" + strLocCode + "' " + " and a.strInvCode='" + strBill + "' and b.strTaxCode='" + strTaxCode + "' "
						+ " group by a.strInvCode,b.strTaxCode ";
				List listTaxData = objGlobalService.funGetList(sqlTaxBill);
				for (int i = 0; i < listTaxData.size(); i++) {
					Object[] arrObjTax = (Object[]) listTaxData.get(i);
					DataList.add(arrObjTax[4].toString());
					billwiseRowTaxTotalAmt += Double.parseDouble(arrObjTax[4].toString());

					if (hmTaxWiseTotal.containsKey(strTaxCode)) {
						double oldTaxesAmt = hmTaxWiseTotal.get(strTaxCode);
						hmTaxWiseTotal.put(strTaxCode, Double.parseDouble(arrObjTax[4].toString()) + oldTaxesAmt);
					} else {
						hmTaxWiseTotal.put(strTaxCode, Double.parseDouble(arrObjTax[4].toString()));
					}

				}
				if (listTaxData.size() == 0) {
					DataList.add(0.00);
				}

			}
			DataList.add(billwiseRowTaxTotalAmt); // total Amt
			detailList.add(DataList);
		}

		// Blank Row at Bottom
		List blankList = new ArrayList();
		blankList.add("");
		blankList.add("");
		for (String strTaxCode : stTaxCode) {
			blankList.add("");
		}
		blankList.add("");
		detailList.add(blankList);

		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		double dblSumOfTotalTaxesAmt = 0.00;
		for (String strTaxCode : stTaxCode) {
			double dblSumOfEachTaxesAmt = hmTaxWiseTotal.get(strTaxCode);
			totalsList.add(dblSumOfEachTaxesAmt);
			dblSumOfTotalTaxesAmt += dblSumOfEachTaxesAmt;
		}
		totalsList.add(dblSumOfTotalTaxesAmt);
		detailList.add(totalsList);

		retList.add(detailList);

		return retList;

	}

	// set Combo box data of login property
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBillwiseTaxFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmBillwiseTaxFlash");
		}
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();

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

}
