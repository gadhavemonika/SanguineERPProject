package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
public class clsBrandWiseClosingReportController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@RequestMapping(value = "/frmBrandWiseClosingReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmBrandWiseClosingReport_1", "command", new clsExciseCategoryWiseSaleBean());
		} else {
			return new ModelAndView("frmBrandWiseClosingReport", "command", new clsExciseCategoryWiseSaleBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadBrandWiseClosingReport", method = RequestMethod.GET)
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
		ArrayList brandList = new ArrayList();
		ArrayList brandList1 = new ArrayList();
		ArrayList subcategorylist = new ArrayList();
		ArrayList closigstcklist = new ArrayList();
		String sqlCategoryName = "select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster " + "  ORDER BY strSubCategoryCode ";
		List objCategoryName = objGlobalFunctionsService.funGetDataList(sqlCategoryName, "sql");
		HashMap brandsubcatMap = new HashMap();
		for (int i = 0; i < objCategoryName.size(); i++) {

			Object obj[] = (Object[]) objCategoryName.get(i);
			String subcateogoryCode = obj[0].toString();
			String subcateogoryName = obj[1].toString();
			// ArrayList subCategoryList=new ArrayList();
			// subCategoryList.add(subcateogoryName);
			//
			String sql_BrandList = "select a.strBrandCode ,a.strSizeCode,a.strShortName,c.intQty,a.intPegSize,a.strBrandName " + " from tblbrandmaster a,tblsubcategorymaster b ,tblsizemaster c " + "where b.strSubCategoryCode='" + subcateogoryCode + "' and b.strSubCategoryCode=a.strSubCategoryCode and a.strSizeCode=c.strSizeCode  " + "and  a.strClientCode='" + tempBrandClientCode
					+ "' and c.strClientCode='" + tempSizeClientCode + "' " + "order by b.strSubCategoryCode,a.strBrandCode  ";
			List brandDataList = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");
			ArrayList closingstckList = new ArrayList();
			ArrayList brandNameList = new ArrayList();
			if (!brandDataList.isEmpty()) {
				for (int j = 0; j < brandDataList.size(); j++) {

					Object brandData[] = (Object[]) brandDataList.get(j);
					String brandCode = brandData[0].toString();
					String brandName = brandData[5].toString();
					String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandCode + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
					List ObjOPDataList = objGlobalFunctionsService.funGetDataList(sql_OpData, "sql");
					Integer intOpBtls = 0;
					Integer intOpPeg = 0;
					Integer intOpML = 0;
					if (ObjOPDataList.size() > 0) {
						Object opData[] = (Object[]) ObjOPDataList.get(0);
						intOpBtls = Integer.parseInt(opData[0].toString());
						intOpPeg = Integer.parseInt(opData[1].toString());
						intOpML = Integer.parseInt(opData[2].toString());
					}
					brandList.add(brandData[0]); // BrandCode
					brandList.add(brandData[1]); // SizeCode
					brandList.add(brandData[2]); // ShortName
					brandList.add(brandData[3]); // intSize
					brandList.add(brandData[4]); // intPegSize
					brandList.add(intOpBtls); // OpeningBottals
					brandList.add(intOpPeg); // OpeningPegs
					brandList.add(intOpML); // OpeningMLs
					LinkedList stkData = objclsFLR3AController.funStockList(brandList, req, fromDate, toDate);
					String closingstck = stkData.get(6).toString();
					brandNameList.add(brandName);
					closingstckList.add(closingstck);
					brandList.clear();
				}
				brandList1.add(brandNameList);
				subcategorylist.add(subcateogoryName);
				closigstcklist.add(closingstckList);
			}
		}
		brandsubcatMap.put("subCategoryName", subcategorylist);
		brandsubcatMap.put("brandNameList", brandList1);
		brandsubcatMap.put("closingstckList", closigstcklist);
		return brandsubcatMap;
	}
}
