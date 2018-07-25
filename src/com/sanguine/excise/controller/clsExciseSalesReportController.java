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
public class clsExciseSalesReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmExciseSalesReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseSalesReport_1", "command", new clsExciseFLR4ReportBean());
		} else {
			return new ModelAndView("frmExciseSalesReport", "command", new clsExciseFLR4ReportBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/rptExciseSalesExcelExport", method = RequestMethod.POST)
	private ModelAndView funSaleeReportExcelExport(@ModelAttribute("command") @Valid clsExciseFLR4ReportBean objbean, HttpServletResponse resp, HttpServletRequest req) {
		String fromDate = objbean.getStrFromDate();
		String toDate = objbean.getStrToDate();
		String header = "Date,SRNO.,Brand Name,Size,Bottles,Peg,ML";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sqlPurchase = "select a.dteBillDate,b.intSalesHd,c.strBrandName,d.strSizeName ,b.intBtl,b.intPeg,b.intML " + "from tblmanualsaleshd a,tblmanualsalesdtl b ,tblbrandmaster c,tblsizemaster d " + "where a.intId=b.intSalesHd and b.strBrandCode=c.strBrandCode and c.strSizeCode=d.strSizeCode " + " AND a.dteBillDate BETWEEN '" + fromDate + "' AND '" + toDate + "' AND a.strClientCode='"
				+ clientCode + "' " + "ORDER BY a.dteBillDate,b.intSalesHd,c.strBrandName ";

		List list = objGlobalFunctionsService.funGetDataList(sqlPurchase, "sql");

		List<List> detail = new ArrayList<List>();
		String chckSalesHd = "";
		String chckBillDate = "";

		int count = 0;
		int chck = 0;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			List<String> DataList = new ArrayList<String>();

			// check to match its previous TpDate and SupplierName
			if (obj[0].toString().equalsIgnoreCase(chckBillDate)) {
				DataList.add("");
				if (obj[1].toString().equalsIgnoreCase(chckSalesHd)) {
					DataList.add("");
				} else {
					DataList.add(obj[1].toString());
				}
				DataList.add(obj[2].toString());
				DataList.add(obj[3].toString());
				DataList.add(obj[4].toString());
				DataList.add(obj[5].toString());
				DataList.add(obj[6].toString());
				count = 1;
				chck = 0;
			} else {
				// code for LineChange in Excel sheet
				if ((!obj[0].toString().equalsIgnoreCase(chckBillDate)) && count == 1) {
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
				chckBillDate = obj[0].toString();
				chckSalesHd = obj[1].toString();

			}
			detail.add(DataList);
		}
		ExportList.add(detail);
		return new ModelAndView("excelView", "stocklist", ExportList);

	}

}
