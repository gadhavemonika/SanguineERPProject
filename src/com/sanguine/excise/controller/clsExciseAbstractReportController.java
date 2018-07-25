package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.sanguine.excise.bean.clsExciseAbtractReportBean;
import com.sanguine.excise.model.clsExciseAbstractReportColumnModel;
import com.sanguine.excise.model.clsExciseAbstractReportModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseAbstractReportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private ServletContext servletContext;

	final Integer totalsubCat = 11;
	final Integer maxCol = 5;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExciseAbstractReport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		ArrayList<String> listLicence = new ArrayList<String>();
		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");

		if (licenceMaster.size() > 0) {
			for (int i = 0; i < licenceMaster.size(); i++) {
				Object licenceData[] = (Object[]) licenceMaster.get(i);
				String licenceNo = licenceData[0].toString();
				req.getSession().removeAttribute("LicenceNo");
				req.getSession().setAttribute("LicenceNo", licenceNo);
				String address = licenceData[1].toString();
				req.getSession().removeAttribute("address");
				req.getSession().setAttribute("address", address);
				listLicence.add(licenceData[2].toString());
			}
		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}
		listLicence.add("All");
		model.put("listLicence", listLicence);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseAbstractReport_1", "command", new clsExciseAbtractReportBean());
		} else {
			return new ModelAndView("frmExciseAbstractReport", "command", new clsExciseAbtractReportBean());
		}
	}

	/**
	 * Function For Save Data or Create Jasper Report
	 */
	@RequestMapping(value = "/saveAbstractReportData", method = RequestMethod.POST)
	public void funSaveData(@ModelAttribute("command") @Valid clsExciseAbtractReportBean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {

		try {
			String companyName = req.getSession().getAttribute("companyName").toString();
			String fromDate = objBean.getStrFromDate();
			String date = fromDate;
			if (!result.hasErrors()) {
				Map<String, Object> reportParams = new HashMap<String, Object>();
				String licenceNo = req.getSession().getAttribute("LicenceNo").toString();
				reportParams.put("strCompanyName", companyName);
				reportParams.put("strDate", date);
				reportParams.put("strLicenceNo", licenceNo);
				List<clsExciseAbstractReportColumnModel> collist = objBean.getColRow();
				if (collist != null) {
					clsExciseAbstractReportColumnModel objModel = collist.get(0);
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
				List<clsExciseAbstractReportModel> dlist = objBean.getRowList();
				ArrayList<Object> dataList = new ArrayList<Object>();
				if (dlist != null) {
					for (clsExciseAbstractReportModel objModel : dlist) {
						if (objModel.getStrRow1() != null && objModel.getStrRow1().length() > 0)
							dataList.add(objModel);
					}

				}
				JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataList);
				String reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptAbstractReport.jrxml");
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
	@RequestMapping(value = { "/loadAbstractReportColumns" }, method = RequestMethod.GET)
	@ResponseBody
	public Map funExciseAbstractReportColumns(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");
		String licenceCode = req.getParameter("licenceCode");
		String fromDate = (new StringBuilder(String.valueOf(fromDate1.split("-")[2]))).append("-").append(fromDate1.split("-")[1]).append("-").append(fromDate1.split("-")[0]).toString();
		String toDate = (new StringBuilder(String.valueOf(toDate1.split("-")[2]))).append("-").append(toDate1.split("-")[1]).append("-").append(toDate1.split("-")[0]).toString();
		LinkedHashMap resMap = new LinkedHashMap();
		LinkedList subCatCode = new LinkedList();
		LinkedHashSet colHeader = new LinkedHashSet();
		LinkedHashSet subCategoryCode = new LinkedHashSet();
		LinkedHashMap catSizeMap = new LinkedHashMap();
		LinkedList ls = new LinkedList();
		ls.add(" ");
		ls.add("T.P. NO.");
		colHeader.addAll(ls);
		subCategoryCode.addAll(ls);
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
		String sql_SubCategoryList = "";
		if (!licenceCode.equalsIgnoreCase("All")) {
			sql_SubCategoryList = (new StringBuilder(" select DISTINCT e.strSubCategoryCode, " + "  e.strSubCategoryName,e.intAvailableSizes from tbltphd a, " + " tbltpdtl b,tblbrandmaster c, tblsubcategorymaster e  where b.strTPCode=a.strTPCode and " + " date(a.strTpDate) between '")).append(fromDate).append("' and '").append(toDate)
					.append("' " + " and b.strBrandCode=c.strBrandCode  and c.strSubCategoryCode = e.strSubCategoryCode " + " and a.strClientCode='").append(clientCode).append("' and b.strClientCode='").append(clientCode).append("' " + " and c.strClientCode='").append(tempBrandClientCode).append("'and a.strLicenceCode='").append(licenceCode)
					.append("' " + " GROUP BY e.strSubCategoryCode ORDER BY e.strSubCategoryCode ").toString();
		} else {
			sql_SubCategoryList = (new StringBuilder(" select DISTINCT e.strSubCategoryCode, " + "  e.strSubCategoryName,e.intAvailableSizes from tbltphd a, " + " tbltpdtl b,tblbrandmaster c, tblsubcategorymaster e  where b.strTPCode=a.strTPCode and " + " date(a.strTpDate) between '")).append(fromDate).append("' and '").append(toDate)
					.append("' " + " and b.strBrandCode=c.strBrandCode  and c.strSubCategoryCode = e.strSubCategoryCode " + " and a.strClientCode='").append(clientCode).append("' and b.strClientCode='").append(clientCode).append("' " + " and c.strClientCode='").append(tempBrandClientCode).append("' " + " GROUP BY e.strSubCategoryCode ORDER BY e.strSubCategoryCode ").toString();

		}
		List subCategoryList = objGlobalFunctionsService.funGetDataList(sql_SubCategoryList, "sql");
		for (int j = 0; j < subCategoryList.size(); j++) {
			Integer maxSizeLength = maxCol;
			if (j < totalsubCat) {
				Object obj[] = (Object[]) subCategoryList.get(j);
				subCatCode.add(obj[0].toString());
				colHeader.add(obj[1].toString());
				subCategoryCode.add(obj[0].toString());
				catSizeMap.put(obj[0].toString(), maxSizeLength);
			}
		}

		List sizeData = funSizeRows(subCatCode, req);
		List sizeCatCode = (List) sizeData.get(0);
		List sizeName = (List) sizeData.get(1);
		resMap.put("SubCatCode", subCategoryCode);
		resMap.put("Header", colHeader);
		resMap.put("CatSizeLenght", catSizeMap);
		resMap.put("SizeName", sizeName);
		List rowData = sizeCatCode.size() <= 0 ? null : funExciseReportRows(sizeCatCode, req);
		LinkedList totalRow = new LinkedList();
		if (rowData.size() > 0) {
			resMap.put("Data", rowData);
			totalRow = funCaluculateTotal(rowData, req);
			totalRow.set(1, "TOTAL");
			resMap.put("Total", totalRow);
		}
		return resMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList funSizeRows(List subCatCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");
		String licenceCode = req.getParameter("licenceCode");
		String fromDate = (new StringBuilder(String.valueOf(fromDate1.split("-")[2]))).append("-").append(fromDate1.split("-")[1]).append("-").append(fromDate1.split("-")[0]).toString();
		String toDate = (new StringBuilder(String.valueOf(toDate1.split("-")[2]))).append("-").append(toDate1.split("-")[1]).append("-").append(toDate1.split("-")[0]).toString();
		LinkedList result = new LinkedList();
		LinkedList catSizeKey = new LinkedList();
		LinkedList catSizeName = new LinkedList();
		LinkedList catSizeQty = new LinkedList();
		catSizeKey.add(" ");
		catSizeKey.add(" ");
		catSizeName.add(" ");
		catSizeName.add(" ");
		catSizeQty.add(" ");
		catSizeQty.add(" ");
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
		String sql_sizeDataList = "";
		for (int i = 0; i < subCatCode.size(); i++) {
			if (!licenceCode.equalsIgnoreCase("All")) {
				sql_sizeDataList = "select DISTINCT d.strSizeCode,d.strSizeName,d.intQty,e.strSubCategoryCode, " + " e.strSubCategoryName,e.intAvailableSizes,a.strTpDate from tbltphd a, " + " tbltpdtl b,tblbrandmaster c, tblsizemaster d,tblsubcategorymaster e " + " where b.strTPCode=a.strTPCode and  date(a.strTpDate) between " + " '" + fromDate + "' and '" + toDate
						+ "' and c.strSubCategoryCode=e.strSubCategoryCode " + " and b.strBrandCode=c.strBrandCode and c.strSubCategoryCode='" + subCatCode.get(i) + "' " + " and c.strSizeCode=d.strSizeCode and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + tempBrandClientCode + "' " + " and d.strClientCode='" + tempSizeClientCode
						+ "' and a.strLicenceCode='" + licenceCode + "'" + "  GROUP BY e.strSubCategoryCode,d.intQty  " + " ORDER BY e.strSubCategoryCode,d.intQty desc ";
			} else {
				sql_sizeDataList = "select DISTINCT d.strSizeCode,d.strSizeName,d.intQty,e.strSubCategoryCode, " + " e.strSubCategoryName,e.intAvailableSizes,a.strTpDate from tbltphd a, " + " tbltpdtl b,tblbrandmaster c, tblsizemaster d,tblsubcategorymaster e " + " where b.strTPCode=a.strTPCode and  date(a.strTpDate) between " + " '" + fromDate + "' and '" + toDate
						+ "' and c.strSubCategoryCode=e.strSubCategoryCode " + " and b.strBrandCode=c.strBrandCode and c.strSubCategoryCode='" + subCatCode.get(i) + "' " + " and c.strSizeCode=d.strSizeCode and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "' and c.strClientCode='" + tempBrandClientCode + "' " + " and d.strClientCode='" + tempSizeClientCode
						+ "' " + "  GROUP BY e.strSubCategoryCode,d.intQty  " + " ORDER BY e.strSubCategoryCode,d.intQty desc ";

			}
			List sizeDataList = objGlobalFunctionsService.funGetDataList(sql_sizeDataList, "sql");
			Integer maxSizeLength = maxCol;
			Integer sizeListLength = sizeDataList.size();
			if (sizeListLength > 0) {
				for (int j = 0; j < sizeListLength.intValue(); j++) {
					Object obj[] = (Object[]) sizeDataList.get(j);
					if (j < maxSizeLength) {
						catSizeKey.add(subCatCode.get(i).toString() + "." + obj[0].toString());
						catSizeName.add(obj[1].toString());
						catSizeQty.add(obj[2].toString());
					}
				}
				while (sizeListLength < maxSizeLength) {
					catSizeKey.add(" ");
					catSizeName.add(" ");
					catSizeQty.add(" ");
					sizeListLength++;
				}

			} else {
				String sql_sizeList = "select DISTINCT a.strSubCategoryName,a.intAvailableSizes " + "from tblsubcategorymaster a where a.strSubCategoryCode='" + subCatCode.get(i) + "' ";
				List sizeList = objGlobalFunctionsService.funGetDataList(sql_sizeList, "sql");
				if (sizeList.size() > 0) {
					Object obj[] = (Object[]) sizeList.get(0);
					maxSizeLength = Integer.parseInt(obj[1].toString());
				}
				for (int p = 0; p < maxSizeLength; p++) {
					catSizeKey.add(" ");
					catSizeName.add(" ");
					catSizeQty.add(" ");
				}

			}
		}

		result.add(catSizeKey);
		result.add(catSizeName);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funExciseReportRows(List catSizeData, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");
		String licenceCode = req.getParameter("licenceCode");
		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

		LinkedList respList = new LinkedList();
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
		String sql_tpDataList = "";
		if (!licenceCode.equalsIgnoreCase("All")) {
			sql_tpDataList = "select a.strTPNo,SUM(b.intBottals),a.strTPCode,a.strInvoiceNo,a.strTpDate, " + " d.strSubCategoryCode,c.strSizeCode from tbltphd a,tbltpdtl b,tblbrandmaster c, " + " tblsubcategorymaster d,tblsizemaster e where b.strTPCode=a.strTPCode and  date(a.strTpDate) between " + " '" + fromDate + "' and '" + toDate
					+ "' and c.strSizeCode=e.strSizeCode and b.strBrandCode=c.strBrandCode " + " and c.strSubCategoryCode=d.strSubCategoryCode  and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'  and c.strClientCode='" + tempBrandClientCode + "' and a.strLicenceCode='" + licenceCode + "' " + "  GROUP BY a.strTpDate,a.strTPNo,d.strSubCategoryCode,e.intQty "
					+ " ORDER BY d.strSubCategoryCode,e.intQty desc ";
		} else {

			sql_tpDataList = "select a.strTPNo,SUM(b.intBottals),a.strTPCode,a.strInvoiceNo,a.strTpDate, " + " d.strSubCategoryCode,c.strSizeCode from tbltphd a,tbltpdtl b,tblbrandmaster c, " + " tblsubcategorymaster d,tblsizemaster e where b.strTPCode=a.strTPCode and  date(a.strTpDate) between " + " '" + fromDate + "' and '" + toDate
					+ "' and c.strSizeCode=e.strSizeCode and b.strBrandCode=c.strBrandCode " + " and c.strSubCategoryCode=d.strSubCategoryCode  and a.strClientCode='" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'  and c.strClientCode='" + tempBrandClientCode + "' " + "  GROUP BY a.strTpDate,a.strTPNo,d.strSubCategoryCode,e.intQty "
					+ " ORDER BY d.strSubCategoryCode,e.intQty desc ";
		}

		List tpDataList = objGlobalFunctionsService.funGetDataList(sql_tpDataList, "sql");
		HashMap<String, LinkedList> tpDataMap = new HashMap<String, LinkedList>();
		for (int i = 0; i < tpDataList.size(); i++) {
			Object obj[] = (Object[]) tpDataList.get(i);
			String subCatCode = obj[5].toString();
			String sizeCode = obj[6].toString();
			Long tpQty = Long.valueOf(Long.parseLong(obj[1].toString()));
			String tpNO = obj[0].toString();
			if (tpDataMap.containsKey(tpNO)) {
				LinkedList oldList = (LinkedList) tpDataMap.get(tpNO);
				String catSizeCode = subCatCode + "." + sizeCode;
				for (int j = 2; j < catSizeData.size(); j++)
					try {
						if (catSizeData.get(j) != null) {
							if (catSizeData.get(j).toString().equalsIgnoreCase(catSizeCode)) {
								oldList.set(j, "" + tpQty);
							}// code match if block
						}// cat list data not null
					} catch (Exception e) {
					}// try Catch Block
				tpDataMap.put(tpNO, oldList);
			} else {
				LinkedList tpList = new LinkedList();
				String catSizeCode = (subCatCode) + "." + (sizeCode);
				tpList.add(tpNO);
				tpList.add(obj[0].toString());
				for (int j = 2; j < catSizeData.size(); j++)
					try {
						if (catSizeData.get(j) != null) {
							if (catSizeData.get(j).toString().equalsIgnoreCase(catSizeCode)) {
								tpList.add("" + tpQty);
							} else {
								tpList.add(" ");
							}// code match if block
						} else {
							tpList.add(" ");
						}
					} catch (Exception e) {
						tpList.add(" ");
					}

				tpDataMap.put(tpNO, tpList);
			}
		}

		for (Map.Entry<String, LinkedList> data : tpDataMap.entrySet()) {
			respList.add(data.getValue());
		}
		return respList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList funCaluculateTotal(List rowData, HttpServletRequest req) {

		LinkedHashMap<String, String> hs = new LinkedHashMap<String, String>();
		hs.put("col0", " ");
		hs.put("col1", "`");
		for (int i = 0; i < rowData.size(); i++) {
			String key = "col";
			List ls = (List) rowData.get(i);
			for (int j = 2; j < ls.size(); j++) {
				if (hs.containsKey(key + j)) {
					String val = "0";
					if (ls.get(j).toString().trim().length() > 0) {
						val = ls.get(j).toString().trim();
					} else {
						val = "0";
					}
					Integer tempVal = Integer.parseInt(hs.get(key + j));
					hs.put(key + j, (tempVal + Integer.parseInt(val)) + "");
				} else {
					String val = "0";
					if (ls.get(j).toString().trim().length() > 0) {
						val = ls.get(j).toString().trim();
					} else {
						val = "0";
					}
					hs.put(key + j, val);
				}
			}
		}

		LinkedList totalRow = new LinkedList();

		for (int i = 0; i < hs.size(); i++) {
			if (hs.get("col" + i).toString().trim().length() > 0) {
				if (hs.get("col" + i).toString().trim().equalsIgnoreCase("0")) {
					totalRow.add(" ");
				} else {
					totalRow.add(hs.get("col" + i).toString());
				}
			} else {
				totalRow.add(" ");
			}

		}
		return totalRow;
	}

}
