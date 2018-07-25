package com.sanguine.excise.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.excise.bean.clsFLR3ABean;
import com.sanguine.excise.model.clsFLR3AModelRow;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsFLR3AController {

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Global Declaration
	boolean sizeIsGreater = false;
	String lastSizeCode = "";
	final Integer totalSizeLength = 8;
	List<String> globalSizeList;
	String licenceCode = "All";

	// Open SizeMaster
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExciseFLR3A", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listType = new ArrayList<>();
		listType.add("Text");
		listType.add("Integer");
		listType.add("List");
		ArrayList<String> listLicence = new ArrayList<String>();
		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");
		if (licenceMaster.size() > 0) {
			for (int i = 0; i < licenceMaster.size(); i++) {
				// Object licenceData[] = (Object[]) licenceMaster.get(0);
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
			return new ModelAndView("frmExciseFLR3A_1", "command", new clsFLR3ABean());
		} else {
			return new ModelAndView("frmExciseFLR3A", "command", new clsFLR3ABean());
		}
	}

	/**
	 * Function For Save data from Jsp
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/saveFLRData", method = RequestMethod.POST)
	public void funSaveData(@ModelAttribute("command") @Valid clsFLR3ABean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {

		try {
			if (!result.hasErrors()) {
				List<clsFLR3AModelRow> list = objBean.getFLRList();
				ArrayList<clsFLR3AModelRow> dataList = new ArrayList<clsFLR3AModelRow>();
				if (list != null) {
					for (clsFLR3AModelRow objModel : list) {
						if (objModel.getStrCol1() != null && objModel.getStrCol1().length() > 0) {
							dataList.add(objModel);
						}
					}
				}

				String licenceNo = req.getSession().getAttribute("LicenceNo").toString();
				String companyName = req.getSession().getAttribute("companyName").toString();
				String fromDate = objBean.getStrFromDate();
				// String toDate=objBean.getStrToDate();
				String date = fromDate;
				HashMap<String, Object> reportParams = new HashMap<String, Object>();
				reportParams.put("strLicenceNo", licenceNo);
				reportParams.put("strCompanyName", companyName);
				reportParams.put("strDate", date);

				JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataList);
				InputStream reportName = servletContext.getResourceAsStream("/WEB-INF/exciseReport/rptFLR-3AReport.jrxml");
				JasperDesign jd = JRXmlLoader.load(reportName);
				JasperReport jr = JasperCompileManager.compileReport(jd);
				JasperPrint jp = JasperFillManager.fillReport(jr, reportParams, JRdataSource);
				// JasperViewer.viewReport(jp, false);

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();

				jprintlist.add(jp);
				ServletOutputStream servletOutputStream = resp.getOutputStream();

				if (jp != null) {
					if (objBean.getStrExportType().equalsIgnoreCase("EXCEL")) {

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xls");
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						resp.setHeader("Content-Disposition", "inline;filename=" + "FLR-3A." + "xls");
						exporter.exportReport();

					} else {

						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						resp.setHeader("Content-Disposition", "inline;filename=" + "FLR-3A." + "pdf");
						exporter.exportReport();
					}
				}

				servletOutputStream.flush();
				servletOutputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/fetchExciseReportColumn", method = RequestMethod.GET)
	public @ResponseBody HashMap funExciseReportColumn(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		licenceCode = req.getParameter("licenceCode");
		String Licence_sql = "";
		if (!licenceCode.equalsIgnoreCase("All")) {
			Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strLicenceCode='" + licenceCode + "' and a.strPropertyCode='" + propertyCode + "'";
		} else {
			Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "' ";

		}
		List licenceMaster = objGlobalFunctionsService.funGetDataList(Licence_sql, "sql");
		if (licenceMaster.size() > 0) {
			for (int i = 0; i <= licenceMaster.size(); i++) {
				Object licenceData[] = (Object[]) licenceMaster.get(0);
				String licenceNo = licenceData[0].toString();

				req.getSession().removeAttribute("LicenceNo");
				req.getSession().setAttribute("LicenceNo", licenceNo);

				String address = licenceData[1].toString();
				req.getSession().removeAttribute("address");
				req.getSession().setAttribute("address", address);

				// String LicenseCode=licenceData[2].toString();
			}

		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}

		LinkedHashMap<String, Object> responseMap = new LinkedHashMap<String, Object>();

		// String
		// sql_SizeList="select strSizeCode,intQty from tblsizemaster where "
		// + "strClientCode='"+clientCode+"' ORDER BY intQty DESC";
		// List sizeList= objGlobalFunctionsService.funGetDataList(sql_SizeList,
		// "sql");

		String sql_SubCategoryList = "select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster " + "  ORDER BY strSubCategoryCode";

		List subList = objGlobalFunctionsService.funGetDataList(sql_SubCategoryList, "sql");

		LinkedList<String> subCateList = new LinkedList<String>();
		LinkedList<String> subCateName = new LinkedList<String>();

		for (int i = 0; i < subList.size(); i++) {
			Object obj[] = (Object[]) subList.get(i);
			subCateList.add(obj[0].toString());
			subCateName.add(obj[1].toString());
		}
		req.getSession().removeAttribute("totalSizeLength");
		req.getSession().setAttribute("totalSizeLength", totalSizeLength);
		responseMap.put("totalSizeLength", totalSizeLength);
		responseMap.put("SubCategory", subCateList);
		responseMap.put("SubCategoryName", subCateName);

		return responseMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/fetchExciseRowData", method = RequestMethod.GET)
	public @ResponseBody LinkedHashMap funExciseRowData(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isDecimal = "decimal";

		LinkedHashMap finalresponse = new LinkedHashMap();

		LinkedList<String> sizeCodeRow = new LinkedList<String>();
		LinkedList<String> sizeQtyRow = new LinkedList<String>();
		LinkedList<String> sizeQty = new LinkedList<String>();

		String subCatCode = req.getParameter("subCategory");
		String subCategoryName = req.getParameter("subCategoryName");

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
		// sql_SizeList="select DISTINCT a.strSizeCode,a.strSizeName,a.intQty,c.strIsDecimal from tblsizemaster a,tblbrandmaster b,tblsubcategorymaster c  "
		// +
		// " where a.strSizeCode=b.strSizeCode and b.strSubCategoryCode='"+subCatCode+"' and b.strSubCategoryCode=c.strSubCategoryCode and a.strClientCode='"
		// +
		// tempSizeClientCode+"' and b.strClientCode='"+tempBrandClientCode+"' ORDER BY intQty DESC";

		String sql_SizeList = "select DISTINCT a.strSizeCode,a.strSizeName,a.intQty,c.strIsDecimal from tblsizemaster a,tblbrandmaster b,tblsubcategorymaster c  " + " where b.strBrandCode IN " + " (SELECT DISTINCT strBrandCode FROM " + " (SELECT p.strBrandCode FROM tbltpdtl p,tblbrandmaster m WHERE p.strClientCode='" + clientCode + "' AND "
				+ " p.strBrandCode=m.strBrandCode AND m.strSubCategoryCode='" + subCatCode + "' " + " UNION ALL  " + " (SELECT q.strBrandCode FROM tblopeningstock q,tblbrandmaster n WHERE q.strClientCode='" + clientCode + "' AND " + " q.strBrandCode=n.strBrandCode AND n.strSubCategoryCode='" + subCatCode + "')) AS strBrandCode) AND" + " a.strSizeCode=b.strSizeCode AND b.strSubCategoryCode='"
				+ subCatCode + "' AND b.strSubCategoryCode=c.strSubCategoryCode AND " + " a.strClientCode='" + tempSizeClientCode + "' and b.strClientCode='" + tempBrandClientCode + "' ORDER BY intQty DESC";

		List sizeList = objGlobalFunctionsService.funGetDataList(sql_SizeList, "sql");

		sizeCodeRow.add(" ");
		sizeCodeRow.add(" ");
		sizeCodeRow.add(" ");

		sizeQtyRow.add("SizeCode");
		sizeQtyRow.add(subCategoryName);
		sizeQtyRow.add("T.P. NO.");

		sizeQty.add(" ");
		sizeQty.add(" ");
		sizeQty.add(" ");

		LinkedList sizecodeList = new LinkedList();
		LinkedList sizeNameList = new LinkedList();
		LinkedList sizeQtyList = new LinkedList();

		for (int i = 0; i < sizeList.size(); i++) {
			Object obj[] = (Object[]) sizeList.get(i);
			sizecodeList.add(obj[0].toString());
			if (i < totalSizeLength) {
				sizeQtyList.add(obj[2].toString());
				sizeNameList.add(obj[1].toString());
				isDecimal = obj[3].toString();
			}

		}

		for (int p = 0; p < 4; p++) {

			sizeCodeRow.addAll(sizecodeList);
			sizeQtyRow.addAll(sizeNameList);
			sizeQty.addAll(sizeQtyList);

			for (int cnt = sizeList.size(); cnt < totalSizeLength; cnt++) {
				sizeCodeRow.add(" ");
				sizeQtyRow.add(" ");
				sizeQty.add(" ");
			}
		}
		if (sizeList.size() >= totalSizeLength) {
			sizeIsGreater = true;
			lastSizeCode = "";
			lastSizeCode = sizecodeList.get(8).toString();
			globalSizeList = new LinkedList<String>();
		} else {
			sizeIsGreater = false;
			globalSizeList = new LinkedList<String>();
		}

		for (int i = 0; i < sizeList.size(); i++) {
			Object obj[] = (Object[]) sizeList.get(i);
			globalSizeList.add(obj[0].toString());
		}

		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");
		licenceCode = req.getParameter("licenceCode");
		req.getSession().removeAttribute("licenceCode");
		req.getSession().setAttribute("licenceCode", licenceCode);

		LinkedList data = funExciseStkManupulation(subCatCode, fromDate1, toDate1, req);

		LinkedList rowData = new LinkedList();
		List lastRow = new LinkedList();

		if (data.size() > 0) {
			finalresponse.put("SizeCode", sizeCodeRow);
			finalresponse.put("SizeQty", sizeQtyRow);
			rowData = (LinkedList) funGenrateFullRow(sizecodeList, data, isDecimal);
			lastRow = functionTotalStk(sizecodeList, sizeQtyList, data, req, subCatCode, subCategoryName);

			if (lastRow.size() > 0) {
				lastRow = funIsDecimalCheck(lastRow, isDecimal);
			}
		}

		finalresponse.put("Data", rowData);
		finalresponse.put("Total", lastRow);

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

			String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize,a.strBrandName " + " from tblbrandmaster a, tblsizemaster b  " + " where a.strBrandCode IN " + " (SELECT DISTINCT strBrandCode FROM " + " (SELECT p.strBrandCode FROM tbltpdtl p,tblbrandmaster m WHERE p.strClientCode='" + clientCode + "' AND"
					+ " p.strBrandCode=m.strBrandCode AND m.strSubCategoryCode='" + subCatCode + "' " + " UNION ALL  " + " (SELECT q.strBrandCode FROM tblopeningstock q,tblbrandmaster n WHERE q.strClientCode='" + clientCode + "' AND " + " q.strBrandCode=n.strBrandCode AND n.strSubCategoryCode='" + subCatCode + "')) AS strBrandCode) AND " + " a.strSizeCode=b.strSizeCode AND a.strSubCategoryCode='"
					+ subCatCode + "' " + " AND a.strClientCode='" + tempBrandClientCode + "' AND b.strClientCode='" + tempSizeClientCode + "' ORDER BY a.strShortName ";

			if (sdf.parse(sysDate).compareTo(sdf.parse(fromDate)) >= 0) {

				// String
				// sql_BrandList="select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize "
				// + " from tblbrandmaster a, tblsizemaster b  "
				// +
				// " where a.strBrandCode NOT IN(SELECT distinct strParentCode FROM tblexciserecipermasterhd) "
				// +
				// " and a.strSizeCode=b.strSizeCode and  a.strSubCategoryCode='"+subCatCode+"' "
				// +
				// " and  a.strClientCode='"+tempBrandClientCode+"' and b.strClientCode='"+tempSizeClientCode+"'  ORDER BY a.strShortName ";

				List brandList = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");

				for (int i = 0; i < brandList.size(); i++) {
					Object objBrandData[] = (Object[]) brandList.get(i);
					LinkedList ls = new LinkedList();
					ls.add(objBrandData[0]);
					ls.add(objBrandData[1]);
					ls.add(objBrandData[2]);
					ls.add(objBrandData[3]);
					ls.add(objBrandData[4]);
					System.out.println(objBrandData[5]);
					// ls.add("B0817");
					// ls.add("S00001");
					// ls.add("ZONIN CHIANTI REGION");
					// ls.add("750");
					// ls.add("150");

					// System.out.println(objBrandData[5]);

					String sql_OpData = "";
					if (!licenceCode.equalsIgnoreCase("All")) {
						sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licenceCode + "' ";
					} else {
						sql_OpData = "select ifnull(sum(c.intOpBtls),'0'), ifnull(sum(c.intOpPeg),'0'), ifnull(sum(c.intOpML),'0') from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";

					}
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
					// DateFormat outputFormatter = new
					// SimpleDateFormat("yyyy-MM-dd");
					// fromDate= outputFormatter.format(fromDate);
					// toDate= outputFormatter.format(toDate);

					LinkedList brandData = funStockList(ls, req, fromDate, toDate);
					if (Double.parseDouble(brandData.get(3).toString()) > 0 || Double.parseDouble(brandData.get(4).toString()) > 0) {
						responsebrand.add(brandData);
					}
				}

			} else {

				String tempFromDate = sysDate;
				Date convertedCurrentDate = sdf.parse(fromDate);
				Date oneDayBefore = new Date(convertedCurrentDate.getTime() - 1);
				DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
				String tempToDate = outputFormatter.format(oneDayBefore);

				// String
				// sql_BrandList="select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize "
				// + " from tblbrandmaster a, tblsizemaster b  "
				// +
				// " where a.strBrandCode NOT IN(SELECT distinct strParentCode FROM tblexciserecipermasterhd) "
				// +
				// " and a.strSizeCode=b.strSizeCode and  a.strSubCategoryCode='"+subCatCode+"' "
				// +
				// " and  a.strClientCode='"+tempBrandClientCode+"' and b.strClientCode='"+tempSizeClientCode+"'  ORDER BY a.strShortName ";

				List brandList = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");

				for (int i = 0; i < brandList.size(); i++) {
					Object objBrandData[] = (Object[]) brandList.get(i);
					LinkedList ls = new LinkedList();
					// ls.add("B0054");
					// ls.add("S00010");
					// ls.add("CORONA BEER");
					// ls.add("355");
					// ls.add("355");
					ls.add(objBrandData[0]);
					ls.add(objBrandData[1]);
					ls.add(objBrandData[2]);
					ls.add(objBrandData[3]);
					ls.add(objBrandData[4]);
					System.out.println(objBrandData[5]);

					String sql_OpData = "";

					if (!licenceCode.equalsIgnoreCase("All")) {
						sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' and c.strLicenceCode='" + licenceCode + "'";

					} else {
						sql_OpData = "select ifnull(sum(c.intOpBtls),'0'), ifnull(sum(c.intOpPeg),'0'), ifnull(sum(c.intOpML),'0') from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + ls.get(0) + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
					}

					// String
					// sql_OpData="select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c "
					// +
					// " where a.strBrandCode='"+ls.get(0)+"' and a.strBrandCode=c.strBrandCode  "
					// +
					// " and  a.strClientCode='"+tempBrandClientCode+"' and  c.strClientCode='"+clientCode+"' ";
					// ls.add("B0817");
					// ls.add("S00001");
					// ls.add("ZONIN CHIANTI REGION");
					// ls.add("750");
					// ls.add("150");
					//
					// System.out.println(objBrandData[5]);
					//
					//
					// String
					// sql_OpData="select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c "
					// +
					// " where a.strBrandCode='B0817' and a.strBrandCode=c.strBrandCode  "
					// +
					// " and  a.strClientCode='"+tempBrandClientCode+"' and  c.strClientCode='"+clientCode+"' ";
					//
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
					if (sizeIsGreater) {
						if (globalSizeList.indexOf(ls.get(1).toString()) >= totalSizeLength) {
							brandData.add(lastSizeCode);
						} else {
							brandData.add(ls.get(1).toString());
						}

					} else {
						brandData.add(ls.get(1).toString());
					}
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
		// check for Licence code is not set in session show data only client
		// wise
		try {
			licenceCode = req.getSession().getAttribute("licenceCode").toString();
		} catch (Exception e) {
			licenceCode = "All";
		}

		// Check For is Brand Has Recipe Here Only those brand allow which has
		// no recipe

		String sql_RecipeList = "select distinct strParentCode FROM tblexciserecipermasterhd a " + "where a.strParentCode='" + objBrandData.get(0).toString() + "' and a.strClientCode='" + clientCode + "' ";
		List recipeList = objGlobalFunctionsService.funGetDataList(sql_RecipeList, "sql");
		if (recipeList.size() == 0) {

			LinkedList<String> brandData = new LinkedList<String>();
			if (sizeIsGreater) {
				if (globalSizeList.indexOf(objBrandData.get(1).toString()) >= totalSizeLength) {
					brandData.add(lastSizeCode);
				} else {
					brandData.add(objBrandData.get(1).toString());
				}

			} else {
				brandData.add(objBrandData.get(1).toString());
			}
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
		for (int i = 0; i < totalSizeLength; i++) {
			blankData.add(" ");
		}
		return blankData;
	}

	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, String> funTpStock(String brandCode, HttpServletRequest req, String fromDate, String toDate) {

		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String sql_TpQty = "";
		if ((!licenceCode.equalsIgnoreCase("All")) && (!licenceCode.equalsIgnoreCase(""))) {
			sql_TpQty = "select a.strTPNo,b.intBottals,a.strTPCode from tbltphd a, tbltpdtl b where b.strTPCode=a.strTPCode and " + "date(a.strTpDate) between '" + fromDate + "' and '" + toDate + "' and b.strBrandCode='" + brandCode + "' and b.strClientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "' and a.strLicenceCode='" + licenceCode + "' ";

		} else {
			sql_TpQty = "select a.strTPNo,b.intBottals,a.strTPCode from tbltphd a, tbltpdtl b where b.strTPCode=a.strTPCode and " + "date(a.strTpDate) between '" + fromDate + "' and '" + toDate + "' and b.strBrandCode='" + brandCode + "' and b.strClientCode='" + clientCode + "' and a.strClientCode='" + clientCode + "' ";

		}
		List tpList = objGlobalFunctionsService.funGetDataList(sql_TpQty, "sql");
		for (int i = 0; i < tpList.size(); i++) {
			Object tpData[] = (Object[]) tpList.get(i);
			// result.put(tpData[0].toString(),tpData[1].toString());
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
				String sql_SalesQty = "";
				if (!licenceCode.equalsIgnoreCase("All")) {
					sql_SalesQty = "select ifnull(sum(a.intTotalPeg),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + recipeObj[0].toString() + "' and a.strClientCode='" + clientCode + "' and a.strLicenceCode='" + licenceCode + "' ";

				} else {
					sql_SalesQty = "select ifnull(sum(a.intTotalPeg),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + recipeObj[0].toString() + "' and a.strClientCode='" + clientCode + "'  ";

				}

				List totalPeg = objGlobalFunctionsService.funGetDataList(sql_SalesQty, "sql");
				if (totalPeg.size() > 0) {
					Long peg = Long.parseLong(totalPeg.get(0).toString());
					Integer qtyInRecipe = (int) Math.floor(brandSize / Double.parseDouble(recipeObj[2].toString()));
					Long totalMl = (qtyInRecipe) * (peg);
					brandSaleDataInPMl += totalMl;
				}
			}
		}

		String sql_SalesQty = "";
		if (!licenceCode.equalsIgnoreCase("All")) {
			sql_SalesQty = "select ifnull(sum(a.intQty),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + brandCode + "' and a.strClientCode='" + clientCode + "' and a.strLicenceCode='" + licenceCode + "' ";

		} else {
			sql_SalesQty = "select ifnull(sum(a.intQty),'0') as Qty from tblexcisesaledata a where " + "date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and " + "a.strItemCode='" + brandCode + "' and a.strClientCode='" + clientCode + "'  ";

		}

		List brandList = objGlobalFunctionsService.funGetDataList(sql_SalesQty, "sql");
		if (brandList.size() > 0) {
			if (brandList.get(0) != null) {
				Long peg = Long.parseLong((brandList.get(0).toString()));
				// Long qtyInML= (peg * brandPegSize);
				// brandSaleDataInPMl += qtyInML;
				brandSaleDataInPMl += peg;
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
			if (totalSizeLength < sizeCodeList.size()) {
				cnt = totalSizeLength;
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

			if (totalSizeLength > sizeCodeList.size()) {
				for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

			if (totalSizeLength > sizeCodeList.size()) {
				for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

			if (totalSizeLength > sizeCodeList.size()) {
				for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

			if (totalSizeLength > sizeCodeList.size()) {
				for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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
	public List functionTotalStk(LinkedList sizeCodeList, LinkedList sizeQtyList, LinkedList result, HttpServletRequest req, String subCategory, String subcategoryName) {

		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		LinkedList totalList = new LinkedList();
		LinkedHashMap<String, Object> openingList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> tpList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> saleList = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> closingList = new LinkedHashMap<String, Object>();

		openingList = funTotalCalculate(openingList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 3);
		tpList = funTotalCalculate(tpList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 4);
		saleList = funTotalCalculate(saleList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 5);
		closingList = funTotalCalculate(closingList, (LinkedList<LinkedList<String>>) result.get(1), sizeCodeList, 6);

		totalList.add(" ");
		totalList.add(subcategoryName + " Total");
		totalList.add(" ");

		int cnt = sizeCodeList.size();
		if (totalSizeLength < sizeCodeList.size()) {
			cnt = totalSizeLength;
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

		if (totalSizeLength > sizeCodeList.size()) {
			for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

		if (totalSizeLength > sizeCodeList.size()) {
			for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

		if (totalSizeLength > sizeCodeList.size()) {
			for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

		if (totalSizeLength > sizeCodeList.size()) {
			for (int i = 0; i < (totalSizeLength - sizeCodeList.size()); i++) {
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

}
