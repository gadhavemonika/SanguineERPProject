package com.sanguine.excise.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.excise.bean.clsExciseChataiReportBean;
import com.sanguine.excise.model.clsExciseChataiReportColumnModel;
import com.sanguine.excise.model.clsExciseChataiReportModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseChataiReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private ServletContext servletContext;

	final Integer totalsubCat = 11;
	final Integer maxCol = 5;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExciseChataiReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "'";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");

		if (licenceMaster.size() > 0) {
			Object licenceData[] = (Object[]) licenceMaster.get(0);
			String licenceNo = licenceData[0].toString();
			req.getSession().removeAttribute("LicenceNo");
			req.getSession().setAttribute("LicenceNo", licenceNo);
			String address = licenceData[1].toString();
			req.getSession().removeAttribute("address");
			req.getSession().setAttribute("address", address);
		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseChataiReport_1", "command", new clsExciseChataiReportBean());
		} else {
			return new ModelAndView("frmExciseChataiReport", "command", new clsExciseChataiReportBean());
		}
	}

	/**
	 * Function For Save Data or Create Jasper Report
	 */
	@RequestMapping(value = "/saveChataiReportData", method = RequestMethod.POST)
	public void funSaveData(@ModelAttribute("command") @Valid clsExciseChataiReportBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {

		try {
			String companyName = req.getSession().getAttribute("companyName").toString();
			String fromDate = objBean.getStrFromDate();
			String toDate = objBean.getStrToDate();
			if (!result.hasErrors()) {
				Map<String, Object> reportParams = new HashMap<String, Object>();
				String licenceNo = req.getSession().getAttribute("LicenceNo").toString();
				reportParams.put("strCompanyName", companyName);
				reportParams.put("strFromDate", fromDate);
				reportParams.put("strToDate", toDate);
				reportParams.put("strLicenceNo", licenceNo);
				List<clsExciseChataiReportColumnModel> collist = objBean.getColRow();
				if (collist != null) {
					clsExciseChataiReportColumnModel objModel = collist.get(0);
					if (objModel.getStrCol1() != null && objModel.getStrCol1().length() > 0)
						reportParams.put("strCol1", objModel.getStrCol1().trim());
					if (objModel.getStrCol2() != null && objModel.getStrCol2().length() > 0)
						reportParams.put("strCol2", objModel.getStrCol2().trim());
					if (objModel.getStrCol3() != null && objModel.getStrCol3().length() > 0)
						reportParams.put("strCol3", objModel.getStrCol3().trim());
					if (objModel.getStrCol4() != null && objModel.getStrCol4().length() > 0)
						reportParams.put("strCol4", objModel.getStrCol4().trim());
					if (objModel.getStrCol5() != null && objModel.getStrCol5().length() > 0)
						reportParams.put("strCol5", objModel.getStrCol5().trim());
					if (objModel.getStrCol6() != null && objModel.getStrCol6().length() > 0)
						reportParams.put("strCol6", objModel.getStrCol6().trim());
					if (objModel.getStrCol7() != null && objModel.getStrCol7().length() > 0)
						reportParams.put("strCol7", objModel.getStrCol7().trim());
					if (objModel.getStrCol8() != null && objModel.getStrCol8().length() > 0)
						reportParams.put("strCol8", objModel.getStrCol8().trim());
					if (objModel.getStrCol9() != null && objModel.getStrCol9().length() > 0)
						reportParams.put("strCol9", objModel.getStrCol9().trim());
					if (objModel.getStrCol10() != null && objModel.getStrCol10().length() > 0)
						reportParams.put("strCol10", objModel.getStrCol10().trim());
					if (objModel.getStrCol11() != null && objModel.getStrCol11().length() > 0)
						reportParams.put("strCol11", objModel.getStrCol11().trim());
					if (objModel.getStrCol12() != null && objModel.getStrCol12().length() > 0)
						reportParams.put("strCol12", objModel.getStrCol12().trim());
				}
				List<clsExciseChataiReportModel> dlist = objBean.getRowList();
				ArrayList<Object> dataList = new ArrayList<Object>();
				if (dlist != null) {
					for (clsExciseChataiReportModel objModel : dlist) {
						if (objModel.getStrRow1() != null && objModel.getStrRow1().length() > 0)
							dataList.add(objModel);
					}

				}
				JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataList);
				String reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptChataiReport.jrxml");
				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, JRdataSource);
				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				if (jp != null) {
					jprintlist.add(jp);
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					JRExporter exporter = new JRPdfExporter();
					resp.setContentType("application/pdf");
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
					exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
					resp.setHeader("Content-Disposition", "inline;filename=Abstract Report.pdf");
					exporter.exportReport();
					servletOutputStream.flush();
					servletOutputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/loadChataiReportColumns" }, method = RequestMethod.GET)
	@ResponseBody
	public Map funExciseAbstractReportColumns(HttpServletRequest req) {
		LinkedHashMap resMap = new LinkedHashMap();
		LinkedList subCatCode = new LinkedList();
		LinkedHashSet colHeader = new LinkedHashSet();
		LinkedHashSet subCategoryCode = new LinkedHashSet();
		LinkedHashMap catSizeMap = new LinkedHashMap();

		colHeader.add(" ");
		subCategoryCode.add(" ");

		LinkedList subCatCodeForFetch = new LinkedList();
		String sql_SubCategoryList = "select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster " + "  ORDER BY strSubCategoryCode";
		List subCategoryList = objGlobalFunctionsService.funGetDataList(sql_SubCategoryList, "sql");
		for (int j = 0; j < subCategoryList.size(); j++) {
			if (j < totalsubCat) {
				Object obj[] = (Object[]) subCategoryList.get(j);
				subCatCodeForFetch.add(obj[0].toString());
			}
		}

		List sizeName = new ArrayList();
		List OpRow = new ArrayList();
		List purchaseRow = new ArrayList();
		List totalRow = new ArrayList();
		List saleRow = new ArrayList();
		List closingRow = new ArrayList();

		sizeName.add(" ");
		OpRow.add("OPNING BALANCE");
		purchaseRow.add("PURCHASE");
		totalRow.add("TOTAL");
		saleRow.add("SALE");
		closingRow.add("CLOSING BALANCE");

		for (int i = 0; i < subCatCodeForFetch.size(); i++) {
			LinkedHashMap data = funExciseRowData(req, subCatCodeForFetch.get(i).toString());
			if (!(data.isEmpty())) {

				String sql_SubCate = "select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster " + " where strSubCategoryCode='" + subCatCodeForFetch.get(i) + "'";
				List subCat = objGlobalFunctionsService.funGetDataList(sql_SubCate, "sql");
				Integer maxSizeLength = maxCol;
				Object obj[] = (Object[]) subCat.get(0);
				subCatCode.add(obj[0].toString());
				colHeader.add(obj[1].toString());
				subCategoryCode.add(obj[0].toString());
				catSizeMap.put(obj[0].toString(), maxSizeLength);

				List sizeData = (List) data.get("SizeQty");
				for (int p = 1; p < (maxCol + 1); p++) {
					sizeName.add(sizeData.get(p));
				}

				List rowData = (List) data.get("Total");

				Integer cnt = maxCol + 3;
				for (int p = 3; p < (maxCol + 3); p++) {
					OpRow.add(rowData.get(p));
				}

				Integer purchaseCnt = cnt + maxCol;
				for (int p = cnt; p < purchaseCnt; p++) {
					purchaseRow.add(rowData.get(p));
				}

				Integer totalCnt = purchaseCnt + maxCol;
				for (int p = purchaseCnt; p < totalCnt; p++) {
					totalRow.add(rowData.get(p));
				}

				Integer saleCnt = totalCnt + maxCol;
				for (int p = totalCnt; p < saleCnt; p++) {
					saleRow.add(rowData.get(p));
				}

				Integer closingCnt = saleCnt + maxCol;
				for (int p = saleCnt; p < closingCnt; p++) {
					closingRow.add(rowData.get(p));
				}

			}

		}

		if (colHeader.size() > 1) {
			resMap.put("SubCatCode", subCategoryCode);
			resMap.put("Header", colHeader);
			resMap.put("CatSizeLenght", catSizeMap);
			resMap.put("SizeName", sizeName);
			resMap.put("Opening", OpRow);
			resMap.put("Purchase", purchaseRow);
			resMap.put("Total", totalRow);
			resMap.put("Sales", saleRow);
			resMap.put("Closing", closingRow);

		}
		return resMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap funExciseRowData(HttpServletRequest req, String subCategory) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isDecimal = "decimal";

		LinkedHashMap finalresponse = new LinkedHashMap();

		LinkedList<String> sizeCodeRow = new LinkedList<String>();
		LinkedList<String> sizeQtyRow = new LinkedList<String>();
		LinkedList<String> sizeQty = new LinkedList<String>();

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

		String isBrandGlobal = "Custom";
		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}

		// String
		// sql_SizeList=" select e.strSizeCode,e.strSizeName,e.intQty,f.strIsDecimal,c.strTPCode,b.intId  from tblbrandmaster a  "
		// +
		// " left outer join tblopeningstock b on a.strBrandCode=b.strBrandCode and b.strClientCode='"+clientCode+"' "
		// +
		// " left outer join tbltpdtl c on a.strBrandCode=c.strBrandCode and c.strClientCode='"+clientCode+"' "
		// +
		// " inner join tblsubcategorymaster f on a.strSubCategoryCode=f.strSubCategoryCode "
		// +
		// " inner join tblsizemaster e on a.strSizeCode=e.strSizeCode and e.strClientCode='"+tempSizeClientCode+"' "
		// +
		// " where a.strSubCategoryCode='"+subCategory+"' and a.strClientCode='"+tempBrandClientCode+"' "
		// +
		// " and c.strTPCode is not null or b.intId is not null group by a.strSizeCode ORDER BY e.intQty desc ";

		String sql_SizeList = "select DISTINCT a.strSizeCode,a.strSizeName,a.intQty,c.strIsDecimal from tblsizemaster a,tblbrandmaster b,tblsubcategorymaster c  " + " where a.strSizeCode=b.strSizeCode and b.strSubCategoryCode='" + subCategory + "' and b.strSubCategoryCode=c.strSubCategoryCode and a.strClientCode='" + tempSizeClientCode + "' and b.strClientCode='" + tempBrandClientCode
				+ "' ORDER BY intQty DESC";

		List sizeList = objGlobalFunctionsService.funGetDataList(sql_SizeList, "sql");

		sizeCodeRow.add(" ");
		sizeQtyRow.add("Size Name");
		sizeQty.add(" ");

		LinkedList sizecodeList = new LinkedList();
		LinkedList sizeNameList = new LinkedList();
		LinkedList sizeQtyList = new LinkedList();

		for (int i = 0; i < sizeList.size(); i++) {
			Object obj[] = (Object[]) sizeList.get(i);
			sizecodeList.add(obj[0].toString());
			if (i < maxCol) {
				sizeQtyList.add(obj[2].toString());
				sizeNameList.add(obj[1].toString());
				isDecimal = obj[3].toString();
			}

		}

		for (int p = 0; p < 4; p++) {

			sizeCodeRow.addAll(sizecodeList);
			sizeQtyRow.addAll(sizeNameList);
			sizeQty.addAll(sizeQtyList);

			for (int cnt = sizeList.size(); cnt < maxCol; cnt++) {
				sizeCodeRow.add(" ");
				sizeQtyRow.add(" ");
				sizeQty.add(" ");
			}
		}

		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");

		LinkedList data = funExciseStkManupulation(subCategory, fromDate1, toDate1, req);

		LinkedList rowData = new LinkedList();
		List lastRow = new LinkedList();

		if (data.size() > 0) {
			finalresponse.put("SizeCode", sizeCodeRow);
			finalresponse.put("SizeQty", sizeQtyRow);
			rowData = (LinkedList) funGenrateFullRow(sizecodeList, data, isDecimal);
			lastRow = functionTotalStk(sizecodeList, sizeQtyList, data, req, subCategory);

			if (lastRow.size() > 0) {
				lastRow = funIsDecimalCheck(lastRow, isDecimal);
			}

			finalresponse.put("Data", rowData);
			finalresponse.put("Total", lastRow);
		}

		return finalresponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList<LinkedList<String>> funExciseStkManupulation(String subCatCode, String fromDate1, String toDate1, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		LinkedList finalresponse = new LinkedList();
		LinkedList<LinkedList<String>> responsebrand = new LinkedList<LinkedList<String>>();
		String sysDate = req.getSession().getAttribute("startDate").toString();
		sysDate = sysDate.split("/")[2] + "-" + sysDate.split("/")[1] + "-" + sysDate.split("/")[0];

		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		LinkedList subCategoryData = new LinkedList<String>();

		try {

			String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
			String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

			String isBrandGlobal = "Custom";
			String tempBrandClientCode = clientCode;
			try {
				isBrandGlobal = req.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempBrandClientCode = "All";
			}

			if (sdf.parse(sysDate).compareTo(sdf.parse(fromDate)) >= 0) {

				// String
				// sql_BrandList="select a.strBrandCode,a.strSizeCode,a.strShortName,e.intQty,"
				// + " a.intPegSize,c.strTPCode,b.intId from tblbrandmaster a "
				// +
				// "	left outer join tblopeningstock b on a.strBrandCode=b.strBrandCode and b.strClientCode='"+clientCode+"' "
				// +
				// "	left outer join tbltpdtl c on a.strBrandCode=c.strBrandCode and c.strClientCode='"+clientCode+"' "
				// +
				// " inner join tblsizemaster e on a.strSizeCode=e.strSizeCode and e.strClientCode='"+tempSizeClientCode+"' "
				// +
				// " where a.strSubCategoryCode='"+subCatCode+"' and a.strClientCode='"+tempBrandClientCode+"' "
				// +
				// " and c.strTPCode is not null or b.intId is not null group by a.strBrandCode ORDER BY a.strShortName ";

				String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + " from tblbrandmaster a, tblsizemaster b  " + " where a.strBrandCode NOT IN(SELECT distinct strParentCode FROM tblexciserecipermasterhd) " + " and a.strSizeCode=b.strSizeCode and  a.strSubCategoryCode='" + subCatCode + "' " + " and  a.strClientCode='" + tempBrandClientCode
						+ "' and b.strClientCode='" + tempSizeClientCode + "'  ORDER BY a.strShortName ";

				List brandList = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");

				for (int i = 0; i < brandList.size(); i++) {
					Object objBrandData[] = (Object[]) brandList.get(i);
					LinkedList ls = new LinkedList();
					ls.add(objBrandData[0]);
					ls.add(objBrandData[1]);
					ls.add(objBrandData[2]);
					ls.add(objBrandData[3]);
					ls.add(objBrandData[4]);

					String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";

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

					ls.add(intOpBtls);
					ls.add(intOpPeg);
					ls.add(intOpML);

					LinkedList brandData = funStockList(ls, req, fromDate, toDate);
					if (Double.parseDouble(brandData.get(3).toString()) > 0 || Double.parseDouble(brandData.get(4).toString()) > 0) {
						responsebrand.add(brandData);
					}
				}

			} else {

				String tempFromDate = sysDate;
				String tToDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
				Date convertedCurrentDate = sdf.parse(tToDate);
				Date oneDayBefore = new Date(convertedCurrentDate.getTime() - 1);
				DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String tempToDate = outputFormatter.format(oneDayBefore);

				// String
				// sql_BrandList="select a.strBrandCode,a.strSizeCode,a.strShortName,e.intQty,"
				// + " a.intPegSize,c.strTPCode,b.intId from tblbrandmaster a "
				// +
				// "	left outer join tblopeningstock b on a.strBrandCode=b.strBrandCode and b.strClientCode='"+clientCode+"' "
				// +
				// "	left outer join tbltpdtl c on a.strBrandCode=c.strBrandCode and c.strClientCode='"+clientCode+"' "
				// +
				// " inner join tblsizemaster e on a.strSizeCode=e.strSizeCode and e.strClientCode='"+tempSizeClientCode+"' "
				// +
				// " where a.strSubCategoryCode='"+subCatCode+"' and a.strClientCode='"+tempBrandClientCode+"' "
				// +
				// " and c.strTPCode is not null or b.intId is not null group by a.strBrandCode ORDER BY a.strShortName ";

				String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + " from tblbrandmaster a, tblsizemaster b  " + " where a.strBrandCode NOT IN(SELECT distinct strParentCode FROM tblexciserecipermasterhd) " + " and a.strSizeCode=b.strSizeCode and  a.strSubCategoryCode='" + subCatCode + "' " + " and  a.strClientCode='" + tempBrandClientCode
						+ "' and b.strClientCode='" + tempSizeClientCode + "'  ORDER BY a.strShortName ";

				List brandList = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");

				for (int i = 0; i < brandList.size(); i++) {
					Object objBrandData[] = (Object[]) brandList.get(i);
					LinkedList ls = new LinkedList();
					ls.add(objBrandData[0]);
					ls.add(objBrandData[1]);
					ls.add(objBrandData[2]);
					ls.add(objBrandData[3]);
					ls.add(objBrandData[4]);

					String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";

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

					ls.add(intOpBtls);
					ls.add(intOpPeg);
					ls.add(intOpML);

					Integer sizeData = Integer.parseInt(ls.get(3).toString());
					Integer pegSize = Integer.parseInt(ls.get(4).toString());
					if (pegSize <= 0) {
						pegSize = sizeData;
					}
					LinkedList tempBrandData = funStockList(ls, req, tempFromDate, tempToDate);

					LinkedList brandData = new LinkedList<String>();
					brandData.add(ls.get(1).toString());
					brandData.add(ls.get(2).toString());
					LinkedHashMap tpData = funTpStock(ls.get(0).toString(), req, fromDate, toDate);
					String tpNoString = " ";
					Long tpQty = new Long("0");
					LinkedList<String> tpNoList = new LinkedList<String>();
					tpNoList.addAll(tpData.keySet());
					Iterator it = tpNoList.iterator();
					while (it.hasNext()) {
						String tpNo = it.next().toString();
						tpNoString += "," + tpNo;
						tpQty = tpQty + Long.parseLong(tpData.get(tpNo).toString());
					}
					if (tpNoString.length() > 2) {
						brandData.add(tpNoString.substring(2));
					} else {
						brandData.add(tpNoString);
					}

					BigDecimal OpeningQty = new BigDecimal(tempBrandData.get(6).toString().trim());
					brandData.add(OpeningQty + " ");

					String[] strOpeningArr = String.valueOf(OpeningQty.toPlainString()).split("\\.");
					Long opBtlQty = Long.parseLong(strOpeningArr[0].toString()) * sizeData;
					Long opDecimalQty = new Long(0);
					Long openingStk = new Long(0);
					if (inPeg.equalsIgnoreCase("Peg")) {
						if (strOpeningArr.length > 1) {
							opDecimalQty = new Long(strOpeningArr[1].toString());
							opDecimalQty = opDecimalQty * pegSize;
						}

						openingStk = opBtlQty + opDecimalQty;
					} else {
						if (strOpeningArr.length > 1) {
							opDecimalQty = new Long(strOpeningArr[1]);
						}
						openingStk = opBtlQty + opDecimalQty;
					}

					Long totalTpInML = tpQty * sizeData;

					if (inPeg.equalsIgnoreCase("Peg")) {
						brandData.add(tpQty + " ");
					} else {
						brandData.add(tpQty + " ");
					}

					Long saleStkMl = funSalesStk(ls.get(0).toString(), sizeData, req, fromDate, toDate, pegSize);
					Double saleInBtlNMl = (saleStkMl / Double.parseDouble(sizeData + " "));
					String[] strSalesArr = String.valueOf(new BigDecimal(saleInBtlNMl).toPlainString()).split("\\.");
					Integer salesPtToMl = 0;
					if (strSalesArr.length > 1) {
						Double decimalSalesQty = Double.parseDouble("0." + strSalesArr[1]);
						salesPtToMl = (int) (Math.round(decimalSalesQty * sizeData));
					}

					if (inPeg.equalsIgnoreCase("Peg")) {
						Integer pegs = salesPtToMl / pegSize;
						String decQty = "0";
						if (pegs.toString().length() > 1) {
							decQty = "" + pegs;
						} else {
							decQty = "0" + pegs;
						}
						String total = strSalesArr[0].toString() + "." + decQty;
						brandData.add(total + " ");
					} else {
						BigDecimal salesWithML = new BigDecimal(strSalesArr[0] + "." + salesPtToMl);
						brandData.add(salesWithML + " ");
					}

					Long closingStk = (openingStk + totalTpInML) - saleStkMl;
					Double closeStkInMl = (closingStk / Double.parseDouble(sizeData + " "));
					String[] strCloseArr = String.valueOf(new BigDecimal(closeStkInMl).toPlainString()).split("\\.");
					Integer closePtToMl = 0;
					if (strCloseArr.length > 1) {
						Double decimalCloseQty = Double.parseDouble("0." + strCloseArr[1]);
						closePtToMl = (int) (Math.round(decimalCloseQty * sizeData));
					}

					if (inPeg.equalsIgnoreCase("Peg")) {
						Integer pegs = closePtToMl / pegSize;
						String decQty = "0";
						if (pegs.toString().length() > 1) {
							decQty = "" + pegs;
						} else {
							decQty = "0" + pegs;
						}
						String total = strCloseArr[0].toString() + "." + decQty;
						brandData.add(total + " ");
					} else {
						BigDecimal closeWithML = new BigDecimal(strCloseArr[0] + "." + closePtToMl);
						brandData.add(closeWithML + " ");
					}

					brandData.add(pegSize.toString());

					if (Double.parseDouble(brandData.get(3).toString()) > 0 || Double.parseDouble(brandData.get(4).toString()) > 0) {
						responsebrand.add(brandData);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (responsebrand.size() > 0) {
			finalresponse.add(subCategoryData);
			finalresponse.add(responsebrand);
		}
		return finalresponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList<String> funStockList(List objBrandData, HttpServletRequest req, String fromDate, String toDate) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String inPeg = " ";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		// Check For is Brand Has Recipe Here Only those brand allow which has
		// no recipe

		String sql_RecipeList = "select distinct strParentCode FROM tblexciserecipermasterhd a " + "where a.strParentCode='" + objBrandData.get(0).toString() + "' and a.strClientCode='" + clientCode + "' ";
		List recipeList = objGlobalFunctionsService.funGetDataList(sql_RecipeList, "sql");
		if (recipeList.size() == 0) {

			LinkedList<String> brandData = new LinkedList<String>();
			brandData.add(objBrandData.get(1).toString());
			brandData.add(objBrandData.get(2).toString());

			Integer sizeData = Integer.parseInt(objBrandData.get(3).toString());
			Integer pegSize = Integer.parseInt(objBrandData.get(4).toString());

			if (pegSize <= 0) {
				pegSize = sizeData;
			}
			LinkedHashMap tpData = funTpStock(objBrandData.get(0).toString(), req, fromDate, toDate);
			String tpNoString = " ";
			Long tpQty = new Long("0");
			LinkedList<String> tpNoList = new LinkedList<String>();
			tpNoList.addAll(tpData.keySet());
			Iterator it = tpNoList.iterator();
			while (it.hasNext()) {
				String tpNo = it.next().toString();
				tpNoString += "," + tpNo;
				tpQty = tpQty + Long.parseLong(tpData.get(tpNo).toString());
			}
			if (tpNoString.length() > 2) {
				brandData.add(tpNoString.substring(2));
			} else {
				brandData.add(tpNoString);
			}

			Long bottals = new Long(0);
			Long intpegs = new Long(0);
			Long intML = new Long(0);

			if (objBrandData.get(5).toString() != null) {
				bottals = Long.parseLong(objBrandData.get(5).toString());
			} else {
				bottals = (long) 0;
			}
			if (objBrandData.get(6).toString() != null) {
				intpegs = Long.parseLong(objBrandData.get(6).toString());
			} else {
				intpegs = (long) 0;
			}

			if (objBrandData.get(7).toString() != null) {
				intML = Long.parseLong(objBrandData.get(7).toString());
			} else {
				intML = (long) 0;
			}

			Long btsMl = sizeData * bottals;
			Long pegMl = pegSize * intpegs;
			Long openingStk = btsMl + pegMl + intML;

			if (inPeg.equalsIgnoreCase("Peg")) {
				String decQty = "0";
				if (intpegs.toString().length() > 1) {
					decQty = "" + intpegs;
				} else {
					decQty = "0" + intpegs;
				}
				String total = bottals + "." + decQty;
				brandData.add(total + " ");
			} else {
				String total = bottals + "." + pegMl;
				brandData.add(total + " ");
			}

			Long totalTpInML = tpQty * sizeData;
			if (inPeg.equalsIgnoreCase("Peg")) {
				brandData.add(tpQty + " ");
			} else {
				brandData.add(tpQty + " ");
			}

			Long saleStkMl = funSalesStk(objBrandData.get(0).toString(), sizeData, req, fromDate, toDate, pegSize);
			Double saleInBtlNMl = (saleStkMl / Double.parseDouble(sizeData + ""));
			String[] strSalesArr = String.valueOf(new BigDecimal(saleInBtlNMl).toPlainString()).split("\\.");
			Integer salesPtToMl = 0;
			if (strSalesArr.length > 1) {
				Double decimalSalesQty = Double.parseDouble("0." + strSalesArr[1]);
				salesPtToMl = (int) (Math.round(decimalSalesQty * sizeData));
			}

			if (inPeg.equalsIgnoreCase("Peg")) {
				Integer pegs = salesPtToMl / pegSize;
				String decQty = "0";
				if (pegs.toString().length() > 1) {
					decQty = "" + pegs;
				} else {
					decQty = "0" + pegs;
				}

				String total = strSalesArr[0].toString() + "." + decQty;
				brandData.add(total + " ");
			} else {
				BigDecimal salesWithML = new BigDecimal(strSalesArr[0] + "." + salesPtToMl);
				brandData.add(salesWithML + " ");
			}

			Long closingStk = (openingStk + totalTpInML) - saleStkMl;
			Double closeStkInMl = (closingStk / Double.parseDouble(sizeData + ""));
			String[] strCloseArr = String.valueOf(new BigDecimal(closeStkInMl).toPlainString()).split("\\.");
			Integer closePtToMl = 0;
			if (strCloseArr.length > 1) {
				Double decimalCloseQty = Double.parseDouble("0." + strCloseArr[1]);
				closePtToMl = (int) (Math.round(decimalCloseQty * sizeData));
			}

			if (inPeg.equalsIgnoreCase("Peg")) {
				Integer pegs = closePtToMl / pegSize;
				String decQty = "0";
				if (pegs.toString().length() > 1) {
					decQty = "" + pegs;
				} else {
					decQty = "0" + pegs;
				}
				String total = strCloseArr[0].toString() + "." + decQty;
				brandData.add(total + " ");
			} else {
				BigDecimal closeWithML = new BigDecimal(strCloseArr[0] + "." + closePtToMl);
				brandData.add(closeWithML + " ");
			}
			brandData.add(pegSize.toString());
			return brandData;
		} else {
			return null;
		}
	}

	public LinkedList<String> funAllBlankData() {
		LinkedList<String> blankData = new LinkedList<String>();
		for (int i = 0; i < maxCol; i++) {
			blankData.add(" ");
		}
		return blankData;
	}

	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, String> funTpStock(String brandCode, HttpServletRequest req, String fromDate, String toDate) {

		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql_TpQty = "select a.strTPNo,b.intBottals,a.strTPCode from tbltphd a, tbltpdtl b where b.strTPCode=a.strTPCode and " + "date(a.strTpDate) between '" + fromDate + "' and '" + toDate + "' and b.strBrandCode='" + brandCode + "' and b.strClientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "'";

		List tpList = objGlobalFunctionsService.funGetDataList(sql_TpQty, "sql");
		for (int i = 0; i < tpList.size(); i++) {
			Object tpData[] = (Object[]) tpList.get(i);
			result.put(tpData[0].toString(), tpData[1].toString());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public Long funSalesStk(String brandCode, Integer brandSize, HttpServletRequest req, String fromDate, String toDate, Integer brandPegSize) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql_RecipeQty = "select a.strParentCode,d.intQty as brandQty,b.dblQty " + "from tblexciserecipermasterhd a, tblexciserecipermasterdtl b,tblbrandmaster c,tblsizemaster d " + "where a.strRecipeCode=b.strRecipeCode and a.strParentCode=c.strBrandCode " + "and c.strSizeCode=d.strSizeCode and b.strBrandCode='" + brandCode + "' and b.strClientCode='" + clientCode + "' "
				+ " and a.strClientCode='" + clientCode + "'";

		List RecipeList = objGlobalFunctionsService.funGetDataList(sql_RecipeQty, "sql");
		Long brandSaleDataInPMl = new Long(0);
		if (RecipeList.size() > 0) {
			for (int i = 0; i < RecipeList.size(); i++) {
				Object recipeObj[] = (Object[]) RecipeList.get(i);

				String sql_SalesQty = "select ifnull(sum(a.intTotalPeg),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + recipeObj[0].toString() + "' and a.strClientCode='" + clientCode + "'";
				List totalPeg = objGlobalFunctionsService.funGetDataList(sql_SalesQty, "sql");
				if (totalPeg.size() > 0) {
					Long peg = Long.parseLong(totalPeg.get(0).toString());
					Integer qtyInRecipe = (int) Math.floor(brandSize / Double.parseDouble(recipeObj[2].toString()));
					Long totalMl = (qtyInRecipe) * (peg);
					brandSaleDataInPMl += totalMl;
				}
			}
		}
		String sql_SalesQty = "select ifnull(sum(a.intTotalPeg),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + brandCode + "' and a.strClientCode='" + clientCode + "'";
		List brandList = objGlobalFunctionsService.funGetDataList(sql_SalesQty, "sql");
		if (brandList.size() > 0) {
			if (brandList.get(0) != null) {
				Long peg = Long.parseLong((brandList.get(0).toString()));
				Long qtyInML = (peg * brandPegSize);
				brandSaleDataInPMl += qtyInML;
			}
		}
		return brandSaleDataInPMl;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funGenrateFullRow(List sizeCodeList, List data, String isDecimal) {

		LinkedList responseData = new LinkedList();
		LinkedList ls = (LinkedList) data.get(1);
		for (int p = 0; p < ls.size(); p++) {

			LinkedList dataList = (LinkedList) ls.get(p);

			LinkedList fullRow = new LinkedList();
			fullRow.add(dataList.get(0).toString());
			fullRow.add(dataList.get(1).toString());
			fullRow.add(dataList.get(2).toString());

			int cnt = sizeCodeList.size();
			if (maxCol < sizeCodeList.size()) {
				cnt = maxCol;
			}

			for (int i = 0; i < cnt; i++) {

				if (sizeCodeList.get(i).toString().equals((dataList.get(0).toString()))) {
					if (Double.parseDouble(dataList.get(3).toString()) > 0) {
						if (isDecimal.equalsIgnoreCase("decimal")) {
							fullRow.add(dataList.get(3));
						} else {
							String[] tempArr = dataList.get(3).toString().split("\\.");
							fullRow.add(tempArr[0].toString());
						}
					} else {
						fullRow.add(" ");
					}
				} else {
					fullRow.add(" ");
				}
			}

			if (maxCol > sizeCodeList.size()) {
				for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
					fullRow.add(" ");
				}
			}

			for (int i = 0; i < cnt; i++) {
				if (sizeCodeList.get(i).toString().equals((dataList.get(0).toString()))) {
					if (Double.parseDouble(dataList.get(4).toString()) > 0) {
						if (isDecimal.equalsIgnoreCase("decimal")) {
							fullRow.add(dataList.get(4));
						} else {
							String[] tempArr = dataList.get(4).toString().split("\\.");
							fullRow.add(tempArr[0].toString());
						}
					} else {
						fullRow.add(" ");
					}
				} else {
					fullRow.add(" ");
				}
			}

			if (maxCol > sizeCodeList.size()) {
				for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
					fullRow.add(" ");
				}
			}

			for (int i = 0; i < cnt; i++) {
				if (sizeCodeList.get(i).toString().equals((dataList.get(0).toString()))) {
					if (Double.parseDouble(dataList.get(5).toString()) > 0) {
						if (isDecimal.equalsIgnoreCase("decimal")) {
							fullRow.add(dataList.get(5));
						} else {
							String[] tempArr = dataList.get(5).toString().split("\\.");
							fullRow.add(tempArr[0].toString());
						}
					} else {
						fullRow.add(" ");
					}
				} else {
					fullRow.add(" ");
				}
			}

			if (maxCol > sizeCodeList.size()) {
				for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
					fullRow.add(" ");
				}
			}

			for (int i = 0; i < cnt; i++) {
				if (sizeCodeList.get(i).toString().equals((dataList.get(0).toString()))) {
					if (Double.parseDouble(dataList.get(6).toString()) > 0) {
						if (isDecimal.equalsIgnoreCase("decimal")) {
							fullRow.add(dataList.get(6));
						} else {
							String[] tempArr = dataList.get(6).toString().split("\\.");
							fullRow.add(tempArr[0].toString());
						}
					} else {
						fullRow.add(" ");
					}
				} else {
					fullRow.add(" ");
				}
			}

			if (maxCol > sizeCodeList.size()) {
				for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
					fullRow.add(" ");
				}
			}
			responseData.add(fullRow);
		}
		return responseData;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funIsDecimalCheck(List data, String isDecimal) {

		List retuenList = new LinkedList();
		for (int i = 0; i < data.size(); i++) {
			String value = data.get(i).toString();
			if (value.trim().length() > 0 && (!value.trim().isEmpty())) {
				if (!(value.trim().contentEquals("0.00"))) {
					if (isDecimal.equalsIgnoreCase("decimal")) {
						retuenList.add(value);
					} else {
						if ((!(value.trim().isEmpty())) && value.trim().length() > 0) {
							String[] tempArr = value.trim().split("\\.");
							retuenList.add(tempArr[0].toString());
						} else {
							retuenList.add(value);
						}
					}
				} else {
					retuenList.add(" ");
				}
			} else {
				retuenList.add(" ");
			}
		}
		return retuenList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List functionTotalStk(LinkedList sizeCodeList, LinkedList sizeQtyList, LinkedList result, HttpServletRequest req, String subCategory) {

		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		LinkedList totalList = new LinkedList();
		LinkedHashMap<String, Object> openingList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> tpList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> totList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> saleList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> closingList = new LinkedHashMap<String, Object>();

		openingList = funTotalCalculate(openingList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 3);
		tpList = funTotalCalculate(tpList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 4);
		totList = funTotalOfOpningAndTp(openingList, tpList);
		saleList = funTotalCalculate(saleList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 5);
		closingList = funTotalCalculate(closingList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 6);

		totalList.add(" ");
		totalList.add(" Total");
		totalList.add(" ");

		int cnt = sizeCodeList.size();
		if (maxCol < sizeCodeList.size()) {
			cnt = maxCol;
		}

		for (int i = 0; i < cnt; i++) {
			if (openingList.containsKey(sizeCodeList.get(i))) {

				List totallist = (List) (openingList.get(sizeCodeList.get(i)));
				Integer Btls = Integer.parseInt(totallist.get(1).toString());
				Integer decimalQty = Integer.parseInt(totallist.get(2).toString());
				Integer tempPegSize = Integer.parseInt(totallist.get(0).toString());
				Integer size = Integer.parseInt(sizeQtyList.get(i).toString());

				if (inPeg.equalsIgnoreCase("Peg")) {
					Integer pegMadeInBrand = (int) Math.floor(size / tempPegSize);
					if (pegMadeInBrand == 0) {
						pegMadeInBrand = 1;
					}
					if (pegMadeInBrand <= decimalQty) {
						Integer tempBls = (decimalQty / pegMadeInBrand);
						Integer tempPeg = (decimalQty % pegMadeInBrand);
						Btls = Btls + tempBls;
						String decQty = "0";
						if (tempPeg.toString().length() == 2) {
							decQty = "" + tempPeg;
						} else if (tempPeg.toString().length() == 1) {
							decQty = "0" + tempPeg;
						} else {
							decQty = "00";
						}

						String totalStk = Btls + "." + decQty;
						totalList.add(totalStk);
					} else {
						String decPlace = "00";
						if (decimalQty.toString().length() >= 2) {
							decPlace = decimalQty + "";
						} else {
							decPlace = "0" + decimalQty;
						}
						String totalStk = Btls + "." + decPlace;
						totalList.add(totalStk);
					}

				} else {
					if (decimalQty >= size) {
						Integer tempBls = (decimalQty / size);
						Integer tempDec = (decimalQty % size);
						Btls = Btls + tempBls;
						totalList.add(Btls + "." + tempDec + " ");
					} else {
						String totalStk = Btls + "." + decimalQty;
						totalList.add(totalStk);
					}
				}
			} else {
				totalList.add(" ");
			}
		}

		if (maxCol > sizeCodeList.size()) {
			for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
				totalList.add(" ");
			}
		}

		for (int i = 0; i < cnt; i++) {
			if (tpList.containsKey(sizeCodeList.get(i))) {
				List totallist = (List) (tpList.get(sizeCodeList.get(i)));
				Integer Btls = Integer.parseInt(totallist.get(1).toString());
				Integer decimalQty = Integer.parseInt(totallist.get(2).toString());
				Integer size = Integer.parseInt(sizeQtyList.get(i).toString());
				if (inPeg.equalsIgnoreCase("Peg")) {
					if (Btls > 0) {
						String totalStk = Btls + "";
						totalList.add(totalStk);
					} else {
						totalList.add(" ");
					}

				} else {
					if (decimalQty >= size) {
						Integer tempBls = (decimalQty / size);
						Integer tempDec = (decimalQty % size);
						Btls = Btls + tempBls;
						totalList.add(Btls + "." + tempDec + " ");
					} else {
						String totalStk = Btls + "." + decimalQty;
						totalList.add(totalStk);
					}
				}
			} else {
				totalList.add(" ");
			}
		}

		if (maxCol > sizeCodeList.size()) {
			for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
				totalList.add(" ");
			}
		}

		for (int i = 0; i < cnt; i++) {
			if (totList.containsKey(sizeCodeList.get(i))) {

				List totallist = (List) (totList.get(sizeCodeList.get(i)));
				Integer Btls = Integer.parseInt(totallist.get(1).toString());
				Integer decimalQty = Integer.parseInt(totallist.get(2).toString());
				Integer tempPegSize = Integer.parseInt(totallist.get(0).toString());
				Integer size = Integer.parseInt(sizeQtyList.get(i).toString());

				if (inPeg.equalsIgnoreCase("Peg")) {
					Integer pegMadeInBrand = (int) Math.floor(size / tempPegSize);
					if (pegMadeInBrand == 0) {
						pegMadeInBrand = 1;
					}
					if (pegMadeInBrand <= decimalQty) {
						Integer tempBls = (decimalQty / pegMadeInBrand);
						Integer tempPeg = (decimalQty % pegMadeInBrand);
						Btls = Btls + tempBls;
						String decQty = "0";
						if (tempPeg.toString().length() == 2) {
							decQty = "" + tempPeg;
						} else if (tempPeg.toString().length() == 1) {
							decQty = "0" + tempPeg;
						} else {
							decQty = "00";
						}

						String totalStk = Btls + "." + decQty;
						totalList.add(totalStk);
					} else {
						String decPlace = "00";
						if (decimalQty.toString().length() >= 2) {
							decPlace = decimalQty + "";
						} else {
							decPlace = "0" + decimalQty;
						}
						String totalStk = Btls + "." + decPlace;
						totalList.add(totalStk);
					}

				} else {
					if (decimalQty >= size) {
						Integer tempBls = (decimalQty / size);
						Integer tempDec = (decimalQty % size);
						Btls = Btls + tempBls;
						totalList.add(Btls + "." + tempDec + " ");
					} else {
						String totalStk = Btls + "." + decimalQty;
						totalList.add(totalStk);
					}
				}
			} else {
				totalList.add(" ");
			}
		}

		if (maxCol > sizeCodeList.size()) {
			for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
				totalList.add(" ");
			}
		}

		for (int i = 0; i < cnt; i++) {
			if (saleList.containsKey(sizeCodeList.get(i))) {

				List totallist = (List) (saleList.get(sizeCodeList.get(i)));
				Integer Btls = Integer.parseInt(totallist.get(1).toString());
				Integer decimalQty = Integer.parseInt(totallist.get(2).toString());
				Integer tempPegSize = Integer.parseInt(totallist.get(0).toString());
				Integer size = Integer.parseInt(sizeQtyList.get(i).toString());

				if (inPeg.equalsIgnoreCase("Peg")) {
					Integer pegMadeInBrand = (int) Math.floor(size / tempPegSize);
					if (pegMadeInBrand == 0) {
						pegMadeInBrand = 1;
					}
					if (pegMadeInBrand <= decimalQty) {
						Integer tempBls = (decimalQty / pegMadeInBrand);
						Integer tempPeg = (decimalQty % pegMadeInBrand);
						Btls = Btls + tempBls;
						String decQty = "0";
						if (tempPeg.toString().length() == 2) {
							decQty = "" + tempPeg;
						} else if (tempPeg.toString().length() == 1) {
							decQty = "0" + tempPeg;
						} else {
							decQty = "00";
						}

						String totalStk = Btls + "." + decQty;
						totalList.add(totalStk);
					} else {
						String decPlace = "00";
						if (decimalQty.toString().length() >= 2) {
							decPlace = decimalQty + "";
						} else {
							decPlace = "0" + decimalQty;
						}
						String totalStk = Btls + "." + decPlace;
						totalList.add(totalStk);
					}

				} else {
					if (decimalQty >= size) {
						Integer tempBls = (decimalQty / size);
						Integer tempDec = (decimalQty % size);
						Btls = Btls + tempBls;
						totalList.add(Btls + "." + tempDec + " ");
					} else {
						String totalStk = Btls + "." + decimalQty;
						totalList.add(totalStk);
					}
				}
			} else {
				totalList.add(" ");
			}
		}

		if (maxCol > sizeCodeList.size()) {
			for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
				totalList.add(" ");
			}
		}

		for (int i = 0; i < cnt; i++) {
			if (closingList.containsKey(sizeCodeList.get(i))) {

				List totallist = (List) (closingList.get(sizeCodeList.get(i)));
				Integer Btls = Integer.parseInt(totallist.get(1).toString());
				Integer decimalQty = Integer.parseInt(totallist.get(2).toString());
				Integer tempPegSize = Integer.parseInt(totallist.get(0).toString());
				Integer size = Integer.parseInt(sizeQtyList.get(i).toString());

				if (inPeg.equalsIgnoreCase("Peg")) {
					Integer pegMadeInBrand = (int) Math.floor(size / tempPegSize);
					if (pegMadeInBrand == 0) {
						pegMadeInBrand = 1;
					}
					if (pegMadeInBrand <= decimalQty) {
						Integer tempBls = (decimalQty / pegMadeInBrand);
						Integer tempPeg = (decimalQty % pegMadeInBrand);
						Btls = Btls + tempBls;
						String decQty = "0";
						if (tempPeg.toString().length() == 2) {
							decQty = "" + tempPeg;
						} else if (tempPeg.toString().length() == 1) {
							decQty = "0" + tempPeg;
						} else {
							decQty = "00";
						}

						String totalStk = Btls + "." + decQty;
						totalList.add(totalStk);
					} else {
						String decPlace = "00";
						if (decimalQty.toString().length() >= 2) {
							decPlace = decimalQty + "";
						} else {
							decPlace = "0" + decimalQty;
						}
						String totalStk = Btls + "." + decPlace;
						totalList.add(totalStk);
					}

				} else {
					if (decimalQty >= size) {
						Integer tempBls = (decimalQty / size);
						Integer tempDec = (decimalQty % size);
						Btls = Btls + tempBls;
						totalList.add(Btls + "." + tempDec + " ");
					} else {
						String totalStk = Btls + "." + decimalQty;
						totalList.add(totalStk);
					}
				}
			} else {
				totalList.add(" ");
			}
		}

		if (maxCol > sizeCodeList.size()) {
			for (int i = 0; i < (maxCol - sizeCodeList.size()); i++) {
				totalList.add(" ");
			}
		}
		return totalList;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedHashMap<String, Object> funTotalCalculate(LinkedHashMap<String, Object> dataList, LinkedList<LinkedList<String>> result, LinkedList<String> sizeCodeList, int index) {
		for (int i = 0; i < result.size(); i++) {
			LinkedList ls = result.get(i);
			for (int k = 0; k < sizeCodeList.size(); k++) {
				if (sizeCodeList.get(k).toString().equalsIgnoreCase(ls.get(0).toString())) {
					if (dataList.containsKey(sizeCodeList.get(k))) {

						List tempLs = new LinkedList();
						List temp1 = (List) dataList.get(sizeCodeList.get(k));
						Long bts1 = Long.parseLong(temp1.get(1).toString());
						Long ml1 = Long.parseLong(temp1.get(2).toString());

						String temp2[] = String.valueOf(ls.get(index).toString().trim()).split("\\.");
						Long ml2 = new Long(0);
						Long bts2 = Long.parseLong(temp2[0].toString());
						if (temp2.length > 1) {
							ml2 = Long.parseLong(temp2[1].toString());
						}
						Long totalBts = bts1 + bts2;
						Long totalMl = ml1 + ml2;

						Integer tempPegSize = Integer.parseInt(ls.get(7).toString());
						if (tempPegSize <= 0) {
							tempPegSize = 1;
						}

						tempLs.add(tempPegSize);
						tempLs.add(totalBts);
						tempLs.add(totalMl);
						dataList.put(sizeCodeList.get(k), tempLs);
						break;
					} else {
						List tempLs = new LinkedList();
						Integer tempPegSize = Integer.parseInt(ls.get(7).toString());
						if (tempPegSize <= 0) {
							tempPegSize = 1;
						}

						tempLs.add(tempPegSize);
						Long ml = new Long(0);
						Long bts = new Long(0);

						String temp2[] = String.valueOf(ls.get(index).toString().trim()).split("\\.");
						bts = Long.parseLong(temp2[0].toString());
						if (temp2.length > 1) {
							ml = Long.parseLong(temp2[1].toString());
						}
						tempLs.add(bts);
						tempLs.add(ml);
						dataList.put(sizeCodeList.get(k), tempLs);
						break;
					}
				}
			}
		}
		return dataList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public LinkedHashMap<String, Object> funTotalOfOpningAndTp(LinkedHashMap<String, Object> OpList, LinkedHashMap<String, Object> tpList) {

		LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();
		resultMap.putAll(OpList);
		for (Map.Entry<String, Object> data : tpList.entrySet()) {

			if (resultMap.containsKey(data.getKey())) {

				List tempLs = new LinkedList();

				List temp1 = (List) resultMap.get(data.getKey());
				Long bts1 = Long.parseLong(temp1.get(1).toString());
				Long ml1 = Long.parseLong(temp1.get(2).toString());

				List temp2 = (List) data.getValue();
				Long bts2 = Long.parseLong(temp2.get(1).toString());
				Long ml2 = new Long(temp2.get(2).toString());

				Long totalBts = bts1 + bts2;
				Long totalMl = ml1 + ml2;

				Integer tempPegSize = Integer.parseInt(temp1.get(0).toString());
				if (tempPegSize <= 0) {
					tempPegSize = 1;
				}

				tempLs.add(tempPegSize);
				tempLs.add(totalBts);
				tempLs.add(totalMl);

				resultMap.remove(data.getKey());
				resultMap.put(data.getKey(), tempLs);

			} else {

				List tempLs = new LinkedList();
				List temp1 = (List) data.getValue();
				Long bts1 = Long.parseLong(temp1.get(1).toString());
				Long ml1 = new Long(temp1.get(2).toString());

				Integer tempPegSize = Integer.parseInt(temp1.get(0).toString());
				if (tempPegSize <= 0) {
					tempPegSize = 1;
				}

				tempLs.add(tempPegSize);

				tempLs.add(bts1);
				tempLs.add(ml1);

				resultMap.remove(data.getKey());
				resultMap.put(data.getKey(), tempLs);
			}

		}
		return resultMap;
	}

}
