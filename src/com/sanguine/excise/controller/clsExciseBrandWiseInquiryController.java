package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.bean.clsExciseBrandWiseInquiryBean;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsBrandWiseInquiryReportData;

@Controller
public class clsExciseBrandWiseInquiryController {
	private static final String String = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	// Open BrandWise form
	@RequestMapping(value = "/frmExciseBrandWiseInquiry", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseBrandWiseInquiry_1", "command", new clsExciseBrandWiseInquiryBean());
		} else {
			return new ModelAndView("frmExciseBrandWiseInquiry", "command", new clsExciseBrandWiseInquiryBean());
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/saveBrandWise", method = RequestMethod.POST)
	public ModelAndView funAssignFieldsForExcel(@ModelAttribute("command") @Valid clsExciseBrandWiseInquiryBean objbean, HttpServletRequest req, HttpServletRequest res) {

		List ExportList = new ArrayList();
		List finalResponse = new ArrayList();
		String header = "Date,Purchase,Sale,Closing";

		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		for (int i = 1; i < objbean.getObjBrandWiseInquiry().size(); i++) {
			clsBrandWiseInquiryReportData obj = (clsBrandWiseInquiryReportData) objbean.getObjBrandWiseInquiry().get(i);
			List listBrandDtl = new ArrayList();
			listBrandDtl.add(obj.getDteDate());
			listBrandDtl.add(obj.getStrPurchase());
			listBrandDtl.add(obj.getStrSale());
			listBrandDtl.add(obj.getStrClosingStk());
			finalResponse.add(listBrandDtl);

		}
		ExportList.add(finalResponse);

		return new ModelAndView("excelView", "stocklist", ExportList);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadExciseBrandWiseInquiryExport", method = RequestMethod.GET)
	public @ResponseBody List<ArrayList<Object>> funAssignFieldsForForm(HttpServletRequest req) {

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
		String brandCode = req.getParameter("strBrandCode");
		List brandDataList = new LinkedList();

		String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandCode + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";
		Object brandData[] = (Object[]) objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql").get(0);

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
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate dtFrom = dtf.parseLocalDate(fromDate);
		LocalDate dtTo = dtf.parseLocalDate(toDate);
		ArrayList detail = new ArrayList();
		String onedaybeforefromdte = dtFrom.minusDays(2).toString();
		String sysDate = req.getSession().getAttribute("startDate").toString();
		sysDate = sysDate.split("/")[2] + "-" + sysDate.split("/")[1] + "-" + sysDate.split("/")[0];

		brandDataList.add(brandData[0]); // BrandCode
		brandDataList.add(brandData[1]); // SizeCode
		brandDataList.add(brandData[2]); // ShortName
		brandDataList.add(brandData[3]); // intSize
		brandDataList.add(brandData[4]); // intPegSize
		brandDataList.add(intOpBtls); // OpeningBottals
		brandDataList.add(intOpPeg); // OpeningPegs
		brandDataList.add(intOpML); // OpeningMLs

		LinkedList stockdetail = objclsFLR3AController.funStockList(brandDataList, req, sysDate, onedaybeforefromdte);
		brandDataList.clear();
		brandDataList.add(brandData[0]); // BrandCode
		brandDataList.add(brandData[1]); // SizeCode
		brandDataList.add(brandData[2]); // ShortName
		brandDataList.add(brandData[3]); // intSize
		brandDataList.add(brandData[4]); // intPegSize

		// brandDataList.add(intOpBtls); //OpeningBottals
		// brandDataList.add(intOpPeg); //OpeningPegs
		// brandDataList.add(intOpML); //OpeningMLs

		String OpnBtlPeg1 = stockdetail.get(6).toString();
		String[] arr = OpnBtlPeg1.split("\\.");
		brandDataList.add(Integer.parseInt(arr[0]));// OpeningBottals
		String changeOpnPeg = new String();
		if (arr[1].startsWith("0")) {
			changeOpnPeg = arr[1].substring(1, arr[1].length()).toString().trim();
			intOpPeg = Integer.parseInt(changeOpnPeg);
			brandDataList.add(intOpPeg);// OpeningPegs
		} else {
			changeOpnPeg = arr[1].toString().trim();
			intOpPeg = Integer.parseInt(changeOpnPeg);
			brandDataList.add(intOpPeg);// OpeningPegs
		}
		brandDataList.add(intOpML); // OpeningMLs

		for (LocalDate startDate = dtFrom; startDate.isBefore(dtTo.plusDays(1)); startDate = startDate.plusDays(1)) {
			String formattedStartDate = dtf.print(startDate);
			LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, formattedStartDate, formattedStartDate);
			if (stkData != null) {
				brandDataList.clear();
				brandDataList.add(brandData[0]); // BrandCode
				brandDataList.add(brandData[1]); // SizeCode
				brandDataList.add(brandData[2]); // ShortName
				brandDataList.add(brandData[3]); // intSize
				brandDataList.add(brandData[4]); // intPegSize
				String OpnBtlPeg = stkData.get(6).toString();
				String[] arr1 = OpnBtlPeg.split("\\.");
				brandDataList.add(Integer.parseInt(arr1[0]));// OpeningBottals
				String changeOpnPeg1 = new String();
				if (arr1[1].startsWith("0")) {
					changeOpnPeg1 = arr1[1].substring(1, arr1[1].length()).toString().trim();
					intOpPeg = Integer.parseInt(changeOpnPeg1);

					brandDataList.add(intOpPeg);// OpeningPegs
				} else {
					// intOpPeg=Integer.parseInt(arr1[1].toString());
					changeOpnPeg1 = arr1[1].toString().trim();
					intOpPeg = Integer.parseInt(changeOpnPeg1);
					brandDataList.add(intOpPeg);// OpeningPegs
				}
				brandDataList.add(intOpML); // OpeningMLs
				String opnStck;
				if (startDate == dtFrom) {
					opnStck = stockdetail.get(6).toString().trim(); // Opening
																	// stock for
																	// 1st date
				} else {
					opnStck = stkData.get(3).toString().trim();// opening stock
																// for other
																// date
				}
				String purchase = stkData.get(4).toString();
				String sale = stkData.get(5).toString();
				String closingstck = stkData.get(6).toString().trim();
				List DataList = new ArrayList();
				DataList.add(formattedStartDate);
				DataList.add(opnStck);
				DataList.add(purchase);
				DataList.add(sale);

				DataList.add(closingstck);
				detail.add(DataList);

			}
		}

		return detail;
	}
}
