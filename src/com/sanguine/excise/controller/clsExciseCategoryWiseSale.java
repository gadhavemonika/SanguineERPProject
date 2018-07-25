package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExciseCategoryWiseSaleBean;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseCategoryWiseSale {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	// open CategoryWisesale Form
	@RequestMapping(value = "/frmExciseCategoryWiseSale", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseCategoryWiseSale_1", "command", new clsExciseCategoryWiseSaleBean());
		} else {
			return new ModelAndView("frmExciseCategoryWiseSale", "command", new clsExciseCategoryWiseSaleBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/saveCategoryWiseSale", method = RequestMethod.GET)
	public @ResponseBody Map<String, ArrayList<Object>> funAssignFieldsForForm(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isBrandGlobal = "Custom";
		String isSizeGlobal = "Custom";
		String tempSizeClientCode = clientCode;
		try {
			isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			tempSizeClientCode = "All";
		}

		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}

		String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate dtFrom = dtf.parseLocalDate(fromDate);
		LocalDate dtTo = dtf.parseLocalDate(toDate);

		ArrayList date = new ArrayList();
		// Map detail=new HashMap();
		ArrayList subCategory = new ArrayList();
		LinkedHashMap finalResult = new LinkedHashMap();
		ArrayList detail = new ArrayList();

		String sqlCategoryName = "select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster " + "  ORDER BY strSubCategoryCode ";
		List objCategoryName = objGlobalFunctionsService.funGetDataList(sqlCategoryName, "sql");

		String subcateogoryCode = "";
		for (LocalDate startDate = dtFrom; startDate.isBefore(dtTo.plusDays(1)); startDate = startDate.plusDays(1)) {
			subCategory.clear();
			subCategory.add("Date");
			List saleDetail = new ArrayList();
			String formattedStartDate = dtf.print(startDate);
			for (int i = 0; i < objCategoryName.size(); i++) {

				Object obj[] = (Object[]) objCategoryName.get(i);
				subcateogoryCode = obj[0].toString();
				String subcateogoryName = obj[1].toString();

				// String
				// sql_BrandList="select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize "
				// + "  from tblbrandmaster a, tblsizemaster b "
				// +
				// " where a.strSubCategoryCode='"+subcateogoryCode+"' and a.strSizeCode=b.strSizeCode  "
				// +
				// " and  a.strClientCode='"+tempBrandClientCode+"' and b.strClientCode='"+tempSizeClientCode+"' ";

				String sql_BrandList = "select ifnull(sum(a.intQty),'0') from tblexcisesaledata a,tblbrandmaster b,tblsubcategorymaster c " + "where c.strSubCategoryCode='" + subcateogoryCode + "' and a.strItemCode=b.strBrandCode " + "and b.strSubCategoryCode=c.strSubCategoryCode and date(a.dteBillDate) between '" + formattedStartDate + "' and '" + formattedStartDate + "' "
						+ "and a.strClientCode='" + clientCode + "' and b.strClientCode='" + tempBrandClientCode + "' ";

				List brandData = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");
				Object obj1 = (Object) brandData.get(0);
				String total = obj1.toString();
				saleDetail.add(total);
				subCategory.add(subcateogoryName);
			}
			// date.add(formattedStartDate);
			detail.add(formattedStartDate);
			detail.add(saleDetail);
		}
		finalResult.put("Header", subCategory);
		// finalResult.put("Date",date);
		finalResult.put("Data", detail);

		return finalResult;
	}

}
