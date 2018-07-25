package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.excise.bean.clsExciseFLR4ReportBean;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExcisePurchaseReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open PurchaseReport form
	@RequestMapping(value = "/frmExcisePurchaseReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePurchaseReport_1", "command", new clsExciseFLR4ReportBean());
		} else {
			return new ModelAndView("frmExcisePurchaseReport", "command", new clsExciseFLR4ReportBean());
		}

	}

	// Code For Export of Purchase Report
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptExcisePurchaseExcelExport", method = RequestMethod.POST)
	private ModelAndView funPurchaseReportExcelExport(@ModelAttribute("command") @Valid clsExciseFLR4ReportBean objbean, HttpServletResponse resp, HttpServletRequest req) {
		String fromDate = objbean.getStrFromDate();
		String toDate = objbean.getStrToDate();
		String header = "Date,Supplier Name,T.P No.,InvoiceNo,Brand Name,Size,Qty";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		System.out.println(fromDate + "printtttt");

		String sqlPurchase = "SELECT a.strTpDate, c.strSupplierName,a.strTPNo,a.strInvoiceNo,d.strBrandName,e.strSizeName,b.intBottals " + "FROM tbltphd a,tbltpdtl b,tblsuppliermaster c,tblbrandmaster d,tblsizemaster e " + "WHERE a.strTPCode=b.strTPCode AND a.strSupplierCode=c.strSupplierCode AND b.strBrandCode=d.strBrandCode " + "AND d.strSizeCode=e.strSizeCode AND a.strTpDate BETWEEN '"
				+ fromDate + "' AND '" + toDate + "' AND a.strClientCode='" + clientCode + "' " + "ORDER BY a.strTpDate,a.strSupplierCode,a.strInvoiceNo,a.strTPNo ";

		List list = objGlobalFunctionsService.funGetDataList(sqlPurchase, "sql");

		List<List> detail = new ArrayList<List>();
		String chckSupplierName = "";
		String chckTpDate = "";
		String chckTPNo = "";
		String chckInvoice = "";
		int count = 0;
		int chck = 0;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			List<String> DataList = new ArrayList<String>();

			// check to match its previous TpDate and SupplierName
			if (obj[0].toString().equalsIgnoreCase(chckTpDate) && obj[1].toString().equalsIgnoreCase(chckSupplierName)) {
				DataList.add("");
				DataList.add("");
				if (obj[2].toString().equalsIgnoreCase(chckTPNo)) {
					DataList.add("");
				} else {
					DataList.add(obj[2].toString());
				}
				// check to match its previous Invoice No
				if (obj[3].toString().equalsIgnoreCase(chckInvoice)) {
					DataList.add("");
				} else {
					DataList.add(obj[3].toString());
				}
				DataList.add(obj[4].toString());
				DataList.add(obj[5].toString());
				DataList.add(obj[6].toString());
				count = 1;
				chck = 0;
			} else {

				if (((!obj[0].toString().equalsIgnoreCase(chckTpDate)) || (!obj[1].toString().equalsIgnoreCase(chckSupplierName))) && count == 1) {
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					count = 0;
					chck = 1;
					i--;
				} else {
					DataList.add(obj[0].toString());
					DataList.add(obj[1].toString());
					DataList.add(obj[2].toString());
					DataList.add(obj[3].toString());
					DataList.add(obj[4].toString());
					DataList.add(obj[5].toString());
					DataList.add(obj[6].toString());
					chck = 0;
					count = 1;
				}
			}

			if (chck == 0) {
				chckTpDate = obj[0].toString();
				chckSupplierName = obj[1].toString();
				chckTPNo = obj[2].toString();
				chckInvoice = obj[3].toString();

			}
			detail.add(DataList);
		}
		ExportList.add(detail);
		return new ModelAndView("excelView", "stocklist", ExportList);

	}

}
