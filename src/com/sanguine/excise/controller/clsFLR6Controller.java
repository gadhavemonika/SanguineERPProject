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

import com.sanguine.excise.bean.clsExciseFLRR6Bean;
import com.sanguine.excise.model.clsFLR6ModelColumns;
import com.sanguine.excise.model.clsFLR6ModelRow;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsFLR6Controller {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	// private clsGlobalFunctions objGlobal=null;

	@Autowired
	private ServletContext servletContext;

	// Global Variables
	final Integer totalsubCat1 = 5;
	final Integer totalsubCat2 = 6;
	final Integer maxCol = 6;

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmExciseFLR6", method = RequestMethod.GET)
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
		ArrayList<String> listLicence = new ArrayList<String>();
		String Licence_sql = "select a.strLicenceNo,CONCAT(a.strAddress1,',',a.strAddress2," + "',',a.strAddress3,',',b.strCityName,CONCAT('-',a.strPINCode)) as address, b.strCityName,a.strLicenceCode " + "from tbllicencemaster a ,tblcitymaster b " + "where a.strCity=b.strCityCode and a.strClientCode='" + clientCode + "' and a.strPropertyCode='" + propertyCode + "'  ";
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

				String cityName = licenceData[2].toString();
				req.getSession().removeAttribute("cityName");
				req.getSession().setAttribute("cityName", cityName);
				listLicence.add(licenceData[3].toString());
			}
		} else {
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Please Add Licence To This Client");
		}
		listLicence.add("All");
		model.put("listLicence", listLicence);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseFLR6_1", "command", new clsExciseFLRR6Bean());
		} else {
			return new ModelAndView("frmExciseFLR6", "command", new clsExciseFLRR6Bean());
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/loadFLR6Columns", method = RequestMethod.GET)
	public @ResponseBody Map funExciseFLRReportColumns(HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");

		String strFromBillNo = req.getParameter("strFromBillNo");
		String strToBillNo = req.getParameter("strToBillNo");
		String licenceCode = req.getParameter("licenceCode");

		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

		LinkedHashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
		LinkedList<String> subCatCode1 = new LinkedList<String>();
		LinkedHashSet<String> colHeader1 = new LinkedHashSet<String>();
		LinkedHashSet<String> subCategoryCode1 = new LinkedHashSet<String>();
		LinkedHashMap<String, Integer> catSizeMap1 = new LinkedHashMap<String, Integer>();

		LinkedList<String> subCatCode2 = new LinkedList<String>();
		LinkedHashSet<String> colHeader2 = new LinkedHashSet<String>();
		LinkedHashSet<String> subCategoryCode2 = new LinkedHashSet<String>();
		LinkedHashMap<String, Integer> catSizeMap2 = new LinkedHashMap<String, Integer>();

		LinkedList<String> ls = new LinkedList<String>();
		ls.add("SubCatCode");
		ls.add("Sr. No.");
		ls.add("Name Of Permit Holder Purchaser");
		ls.add("Permit Number");
		ls.add("Date Of Expiry");
		ls.add("Where Granted");

		colHeader1.addAll(ls);
		subCategoryCode1.addAll(ls);

		colHeader2.addAll(ls);
		subCategoryCode2.addAll(ls);

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
		if (licenceCode.equalsIgnoreCase("All")) {
			sql_SubCategoryList = " select DISTINCT d.strSubCategoryCode,d.strSubCategoryName," + " d.intAvailableSizes from tblexcisesaledata a,tblbrandmaster b,tblsubcategorymaster d " + " where a.strItemCode=b.strBrandCode and b.strSubCategoryCode=d.strSubCategoryCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + tempBrandClientCode + "' "
					+ " and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and a.intBillNo " + " BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' ORDER BY d.strSubCategoryCode ";
		} else {
			sql_SubCategoryList = " select DISTINCT d.strSubCategoryCode,d.strSubCategoryName," + " d.intAvailableSizes from tblexcisesaledata a,tblbrandmaster b,tblsubcategorymaster d " + " where a.strItemCode=b.strBrandCode and b.strSubCategoryCode=d.strSubCategoryCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + tempBrandClientCode + "' "
					+ " and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and a.intBillNo " + " BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' and a.strLicenceCode='" + licenceCode + "' ORDER BY d.strSubCategoryCode ";

		}
		List subCategoryList = objGlobalFunctionsService.funGetDataList(sql_SubCategoryList, "sql");

		for (int j = 0; j < subCategoryList.size(); j++) {
			Integer maxSizeLength = maxCol;
			if (j < totalsubCat1) {
				Object obj[] = (Object[]) subCategoryList.get(j);
				subCatCode1.add(obj[0].toString());
				colHeader1.add(obj[1].toString());
				subCategoryCode1.add(obj[0].toString());
				catSizeMap1.put(obj[0].toString(), maxSizeLength);
			} else {

				Object obj[] = (Object[]) subCategoryList.get(j);
				subCatCode2.add(obj[0].toString());
				colHeader2.add(obj[1].toString());
				subCategoryCode2.add(obj[0].toString());
				catSizeMap2.put(obj[0].toString(), maxSizeLength);
			}
		}

		List sizeData1 = funSizeRows(subCatCode1, req);
		List sizeCatCode1 = (List) sizeData1.get(0);
		List sizeName1 = (List) sizeData1.get(1);
		resMap.put("SubCatCode1", subCategoryCode1);
		resMap.put("Header1", colHeader1);
		resMap.put("CatSizeLenght1", catSizeMap1);
		resMap.put("SizeName1", sizeName1);
		List rowData1 = (sizeCatCode1.size() > 0 ? funExciseReportRows(sizeCatCode1, req, subCatCode1) : null);
		resMap.put("Data1", rowData1);
		resMap.put("Total1", (rowData1.size() > 0) ? funCaluculateTotal(rowData1, req) : "");
		resMap.put("Summary1", (rowData1.size() > 0) ? funSummary(sizeCatCode1, req, subCatCode1) : "");

		if (subCatCode2.size() > 0) {
			List sizeData2 = funSizeRows(subCatCode2, req);
			List sizeCatCode2 = (List) sizeData2.get(0);
			List sizeName2 = (List) sizeData2.get(1);
			resMap.put("SubCatCode2", subCategoryCode2);
			resMap.put("Header2", colHeader2);
			resMap.put("CatSizeLenght2", catSizeMap2);
			resMap.put("SizeName2", sizeName2);
			List rowData2 = (sizeCatCode2.size() > 0 ? funExciseReportRows(sizeCatCode2, req, subCatCode2) : null);
			resMap.put("Data2", rowData2);
			resMap.put("Total2", (rowData2.size() > 0) ? funCaluculateTotal(rowData2, req) : "");
			resMap.put("Summary2", (rowData2.size() > 0) ? funSummary(sizeCatCode2, req, subCatCode2) : "");
		}

		return resMap;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LinkedList funSizeRows(List<String> subCatCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");

		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

		String strFromBillNo = req.getParameter("strFromBillNo");
		String strToBillNo = req.getParameter("strToBillNo");
		String licenceCode = req.getParameter("licenceCode");

		LinkedList result = new LinkedList();
		LinkedList catSizeKey = new LinkedList();
		LinkedList catSizeVal = new LinkedList();

		catSizeKey.add(" ");
		catSizeKey.add(" ");
		catSizeKey.add(" ");
		catSizeKey.add(" ");
		catSizeKey.add(" ");
		catSizeKey.add(" ");

		catSizeVal.add(" ");
		catSizeVal.add(" ");
		catSizeVal.add(" ");
		catSizeVal.add(" ");
		catSizeVal.add(" ");
		catSizeVal.add(" ");

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
			if (licenceCode.equalsIgnoreCase("All")) {
				sql_sizeDataList = "select DISTINCT c.strSizeCode,c.strSizeName,d.strSubCategoryName,d.intAvailableSizes " + " from tblexcisesaledata a,tblbrandmaster b,tblsizemaster c,tblsubcategorymaster d " + " where a.strItemCode=b.strBrandCode and b.strSizeCode=c.strSizeCode " + " and b.strSubCategoryCode=d.strSubCategoryCode " + " and a.strClientCode='" + clientCode
						+ "' and b.strClientCode='" + tempBrandClientCode + "' and " + " c.strClientCode='" + tempSizeClientCode + "' and b.strSubCategoryCode='" + subCatCode.get(i) + "' " + " and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and a.intBillNo " + " BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' ORDER BY c.intQty desc ";
			} else {
				sql_sizeDataList = "select DISTINCT c.strSizeCode,c.strSizeName,d.strSubCategoryName,d.intAvailableSizes " + " from tblexcisesaledata a,tblbrandmaster b,tblsizemaster c,tblsubcategorymaster d " + " where a.strItemCode=b.strBrandCode and b.strSizeCode=c.strSizeCode " + " and b.strSubCategoryCode=d.strSubCategoryCode " + " and a.strClientCode='" + clientCode
						+ "' and b.strClientCode='" + tempBrandClientCode + "' and " + " c.strClientCode='" + tempSizeClientCode + "' and b.strSubCategoryCode='" + subCatCode.get(i) + "' " + " and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and a.intBillNo " + " BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' and a.strLicenceCode='" + licenceCode
						+ "' ORDER BY c.intQty desc ";
			}
			List sizeDataList = objGlobalFunctionsService.funGetDataList(sql_sizeDataList, "sql");
			Integer maxSizeLength = maxCol;
			Integer sizeListLength = sizeDataList.size();
			if (sizeListLength > 0) {
				for (int j = 0; j < sizeListLength; j++) {
					Object obj[] = (Object[]) sizeDataList.get(j);
					// maxSizeLength=Integer.parseInt(obj[3].toString());
					if (j < maxSizeLength) {
						catSizeKey.add(subCatCode.get(i).toString() + "." + obj[0].toString());
						catSizeVal.add(obj[1].toString());
					}
				}

				while (sizeListLength < maxSizeLength) {
					catSizeKey.add(" ");
					catSizeVal.add(" ");
					sizeListLength++;
				}
			}
			// else{
			// String
			// sql_sizeList="select DISTINCT a.strSubCategoryName,a.intAvailableSizes "
			// +
			// "from tblsubcategorymaster a where a.strSubCategoryCode='"+subCatCode.get(i)+"' ";
			// List sizeList=
			// objGlobalFunctionsService.funGetDataList(sql_sizeList, "sql");
			// if(sizeList.size()>0){
			// Object obj[]= (Object[]) sizeList.get(0);
			// maxSizeLength=Integer.parseInt(obj[1].toString());
			// }
			//
			// for(int p=0;p<maxSizeLength;p++){
			// catSizeKey.add(" ");
			// catSizeVal.add(" ");
			// }
			//
			// }

		}
		result.add(catSizeKey);
		result.add(catSizeVal);

		return result;
	}

	/***
	 * 
	 * @param headers
	 *            :-List which contain subcategoryList
	 * @param req
	 *            :- it is HttpServletRequest request
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> funExciseReportRows(List catSizeData, HttpServletRequest req, List catList) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String cityName = req.getSession().getAttribute("cityName").toString();

		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");

		String strFromBillNo = req.getParameter("strFromBillNo");
		String strToBillNo = req.getParameter("strToBillNo");
		String licenceCode = req.getParameter("licenceCode");
		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		LinkedList<Object> respList = new LinkedList<Object>();

		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

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
		String sql_salesDataList = "";
		for (int p = 0; p < catList.size(); p++) {
			if (licenceCode.equalsIgnoreCase("All")) {
				sql_salesDataList = "select DISTINCT c.strSubCategoryCode,a.intBillNo," + " IFNULL(b.strPermitName,'One Day Permit') AS permitName," + " IFNULL(b.strPermitNo,a.strPermitCode) AS permitNo," + " IFNULL(b.dtePermitExp,a.dteBillDate) AS expDate," + " IFNULL(b.StrPermitPlace,'" + cityName + "') AS place," + " a.intTotalPeg,a.strItemCode,d.intQty AS size,c.intPegSize,d.strSizeCode "
						+ " from tblexcisesaledata a LEFT JOIN tblpermitmaster b ON a.strPermitCode=b.strPermitCode," + " tblbrandmaster c,tblsizemaster d where a.strItemCode=c.strBrandCode " + " and c.strSizeCode=d.strSizeCode and c.strSubCategoryCode='" + catList.get(p) + "' " + " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + tempBrandClientCode + "' "
						+ " and d.strClientCode='" + tempSizeClientCode + "' and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strItemCode=c.strBrandCode and a.intBillNo BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' ORDER BY a.intBillNo";
			} else {
				sql_salesDataList = "select DISTINCT c.strSubCategoryCode,a.intBillNo," + " IFNULL(b.strPermitName,'One Day Permit') AS permitName," + " IFNULL(b.strPermitNo,a.strPermitCode) AS permitNo," + " IFNULL(b.dtePermitExp,a.dteBillDate) AS expDate," + " IFNULL(b.StrPermitPlace,'" + cityName + "') AS place," + " a.intTotalPeg,a.strItemCode,d.intQty AS size,c.intPegSize,d.strSizeCode "
						+ " from tblexcisesaledata a LEFT JOIN tblpermitmaster b ON a.strPermitCode=b.strPermitCode," + " tblbrandmaster c,tblsizemaster d where a.strItemCode=c.strBrandCode " + " and c.strSizeCode=d.strSizeCode and c.strSubCategoryCode='" + catList.get(p) + "' " + " and a.strClientCode='" + clientCode + "' and c.strClientCode='" + tempBrandClientCode + "' "
						+ " and d.strClientCode='" + tempSizeClientCode + "' and date(a.dteBillDate) between '" + fromDate + "' and '" + toDate + "' and a.strLicenceCode='" + licenceCode + "' " + " and a.strItemCode=c.strBrandCode and a.intBillNo BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' ORDER BY a.intBillNo";
			}
			List salesDataList = objGlobalFunctionsService.funGetDataList(sql_salesDataList, "sql");

			for (int i = 0; i < salesDataList.size(); i++) {
				LinkedList<String> saleList = new LinkedList<String>();
				Object obj[] = (Object[]) salesDataList.get(i);
				String subCatCode = obj[0].toString();
				String sizeCode = obj[10].toString();
				Integer brandSize = Integer.parseInt(obj[8].toString());
				Integer pegSize = Integer.parseInt(obj[9].toString());
				if (pegSize <= 0) {
					pegSize = brandSize;
				}
				saleList.add(subCatCode + "." + sizeCode);
				saleList.add(obj[1].toString());
				saleList.add(obj[2].toString());
				saleList.add(obj[3].toString());

				String permitExpDate = obj[4].toString();
				permitExpDate = permitExpDate.split("-")[2] + "-" + permitExpDate.split("-")[1] + "-" + permitExpDate.split("-")[0];
				saleList.add(permitExpDate);
				saleList.add(obj[5].toString());

				Long saleQtyInPeg = Long.parseLong(obj[6].toString());

				for (int j = 6; j < catSizeData.size(); j++) {
					try {
						if (catSizeData.get(j) != null) {
							if (catSizeData.get(j).toString().equalsIgnoreCase(saleList.get(0))) {
								if (inPeg.equalsIgnoreCase("Peg")) {
									saleList.add("" + saleQtyInPeg);
								} else {
									Long saleQtyInML = saleQtyInPeg * pegSize;
									saleList.add("" + saleQtyInML);
								}
							} else {
								saleList.add(" ");
							}
						} else {
							saleList.add(" ");
						}
					} catch (Exception e) {
						saleList.add(" ");
					}
				}
				respList.add(saleList);
			}

		}

		return respList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List funSummary(List sizeCatCode, HttpServletRequest req, List catList) {

		String strFromBillNo = req.getParameter("strFromBillNo");
		String strToBillNo = req.getParameter("strToBillNo");
		String licenceCode = req.getParameter("licenceCode");

		ArrayList<String> tempCatName = new ArrayList<String>();

		LinkedList respList = new LinkedList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String fromDate1 = req.getParameter("fromDate");
		String toDate1 = req.getParameter("toDate");

		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		String fromDate = fromDate1.split("-")[2] + "-" + fromDate1.split("-")[1] + "-" + fromDate1.split("-")[0];
		String toDate = toDate1.split("-")[2] + "-" + toDate1.split("-")[1] + "-" + toDate1.split("-")[0];

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
		String summeryData_sql = "";
		for (int p = 0; p < catList.size(); p++) {
			if (licenceCode.equalsIgnoreCase("All")) {
				summeryData_sql = "select DISTINCT a.strSubCategoryName,d.strSizeName,sum(b.intTotalPeg) as sale," + " d.intQty as size,c.intPegSize from tblsubcategorymaster a, " + " tblexcisesaledata b,tblbrandmaster c,tblsizemaster d " + " where b.strItemCode=c.strBrandCode and c.strSubCategoryCode=a.strSubCategoryCode " + " and b.strClientCode='" + clientCode + "'  and c.strClientCode='"
						+ tempBrandClientCode + "'" + "  and d.strClientCode='" + tempSizeClientCode + "' and date(b.dteBillDate) " + " between '" + fromDate + "' and '" + toDate + "' and c.strSubCategoryCode='" + catList.get(p) + "' " + " and b.intBillNo BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' and c.strSizeCode=d.strSizeCode "
						+ " GROUP By d.strSizeName,a.strSubCategoryCode order by a.strSubCategoryCode, d.intQty desc";
			} else {
				summeryData_sql = "select DISTINCT a.strSubCategoryName,d.strSizeName,sum(b.intTotalPeg) as sale," + " d.intQty as size,c.intPegSize from tblsubcategorymaster a, " + " tblexcisesaledata b,tblbrandmaster c,tblsizemaster d " + " where b.strItemCode=c.strBrandCode and c.strSubCategoryCode=a.strSubCategoryCode " + " and b.strClientCode='" + clientCode + "'  and c.strClientCode='"
						+ tempBrandClientCode + "'" + "  and d.strClientCode='" + tempSizeClientCode + "' and date(b.dteBillDate) " + " between '" + fromDate + "' and '" + toDate + "' and c.strSubCategoryCode='" + catList.get(p) + "' " + " and b.intBillNo BETWEEN '" + strFromBillNo + "' and '" + strToBillNo + "' and c.strSizeCode=d.strSizeCode and b.strLicenceCode='" + licenceCode + "' "
						+ " GROUP By d.strSizeName,a.strSubCategoryCode order by a.strSubCategoryCode, d.intQty desc";
			}
			List dataList = objGlobalFunctionsService.funGetDataList(summeryData_sql, "sql");

			for (int i = 0; i < dataList.size(); i++) {
				LinkedList<String> summeryList = new LinkedList<String>();
				Object obj[] = (Object[]) dataList.get(i);
				summeryList.add(" ");
				summeryList.add("`");

				if (tempCatName.contains(obj[0].toString())) {
					summeryList.add(" ");
				} else {

					tempCatName.add(obj[0].toString());
					summeryList.add(obj[0].toString());
				}

				summeryList.add(obj[1].toString());

				Integer size = Integer.parseInt(obj[3].toString());
				Integer pegSize = Integer.parseInt(obj[4].toString());
				if (pegSize <= 0) {
					pegSize = size;
				}
				Long saleQtyInPeg = Long.parseLong(obj[2].toString());

				if (inPeg.equalsIgnoreCase("Peg")) {
					Integer pegMadeInBrand = (int) Math.floor(size / pegSize);
					if (pegMadeInBrand == 0) {
						pegMadeInBrand = 1;
					}
					if (saleQtyInPeg >= pegMadeInBrand) {
						Long bts = saleQtyInPeg / pegMadeInBrand;
						Long pegs = saleQtyInPeg % pegMadeInBrand;
						String decQty = "0";
						if (pegs.toString().length() > 1) {
							decQty = "" + pegs;
						} else {
							decQty = "0" + pegs;
						}
						String total = bts + "." + decQty;
						summeryList.add(total);
					} else {

						String decQty = "0";
						if (saleQtyInPeg.toString().length() > 1) {
							decQty = "" + saleQtyInPeg;
						} else {
							decQty = "0" + saleQtyInPeg;
						}
						summeryList.add(0 + "." + decQty);
					}
				} else {
					Long saleQty = saleQtyInPeg * pegSize;
					if (saleQty >= size) {
						Double sale = Double.parseDouble(saleQty.toString()) / size;
						String[] strSaleArr = String.valueOf(sale).split("\\.");
						Integer bts = Integer.parseInt(strSaleArr[0].toString());
						Long mlQty = Math.round(Double.parseDouble("0." + strSaleArr[1].toString()) * size);
						String total = bts + "." + mlQty;
						summeryList.add(total);
					} else {
						summeryList.add(0 + "." + saleQty);
					}
				}
				int listSize = summeryList.size();
				if (listSize < sizeCatCode.size()) {
					for (int j = 0; j < (sizeCatCode.size() - listSize); j++) {
						summeryList.add(" ");
					}
				}
				respList.add(summeryList);
			}

		}

		return respList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funCaluculateTotal(List rowData, HttpServletRequest req) {

		String inPeg = "";
		try {
			inPeg = req.getParameter("inPeg").toString();
		} catch (Exception e) {
			inPeg = "ML";
		}

		LinkedHashMap<String, String> hs = new LinkedHashMap<String, String>();
		hs.put("col0", " ");
		hs.put("col1", "`");
		if (inPeg.equalsIgnoreCase("Peg")) {
			hs.put("col2", "TOTAL IN PEG");
		} else {
			hs.put("col2", "TOTAL IN ML");
		}

		hs.put("col3", " ");
		hs.put("col4", " ");
		hs.put("col5", " ");
		for (int i = 0; i < rowData.size(); i++) {
			String key = "col";
			List ls = (List) rowData.get(i);
			for (int j = 6; j < ls.size(); j++) {
				if (hs.containsKey(key + j)) {
					if (inPeg.equalsIgnoreCase("Peg")) {
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
						Integer tempVal = Integer.parseInt(hs.get(key + j));
						hs.put(key + j, (tempVal + Integer.parseInt(val)) + "");
					}

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

	/**
	 * Function For Save Data or Create Jasper Report
	 */
	@RequestMapping(value = "/saveFLR6Data", method = RequestMethod.POST)
	public void funSaveData(@ModelAttribute("command") @Valid clsExciseFLRR6Bean objBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {

		try {

			if (!result.hasErrors()) {

				String companyName = req.getSession().getAttribute("companyName").toString();
				String fromDate = objBean.getStrFromDate();
				String licenceCode = objBean.getStrLicenceCode();
				// String toDate=objBean.getStrToDate();
				String date = fromDate;
				String licenceNo = req.getSession().getAttribute("LicenceNo").toString();

				JasperPrint jp1 = null;
				JasperPrint jp2 = null;

				if (objBean.getHeaderList1() != null) {

					Map<String, Object> reportParams1 = new HashMap<String, Object>();
					reportParams1.put("strCompanyName", companyName);
					reportParams1.put("strDate", date);
					reportParams1.put("strLicenceNo", licenceNo);

					List<clsFLR6ModelColumns> collist = objBean.getHeaderList1();
					if (collist != null) {
						clsFLR6ModelColumns objModel = collist.get(0);
						if (objModel.getStrCol1() != null && objModel.getStrCol1().length() > 0) {
							reportParams1.put("strCol1", objModel.getStrCol1().trim());
						}

						if (objModel.getStrCol2() != null && objModel.getStrCol2().length() > 0) {
							reportParams1.put("strCol2", objModel.getStrCol2().trim());
						}

						if (objModel.getStrCol3() != null && objModel.getStrCol3().length() > 0) {
							reportParams1.put("strCol3", objModel.getStrCol3().trim());
						}

						if (objModel.getStrCol4() != null && objModel.getStrCol4().length() > 0) {
							reportParams1.put("strCol4", objModel.getStrCol4().trim());
						}

						if (objModel.getStrCol5() != null && objModel.getStrCol5().length() > 0) {
							reportParams1.put("strCol5", objModel.getStrCol5().trim());
						}

						if (objModel.getStrCol6() != null && objModel.getStrCol6().length() > 0) {
							reportParams1.put("strCol6", objModel.getStrCol6().trim());
						}

						if (objModel.getStrCol7() != null && objModel.getStrCol7().length() > 0) {
							reportParams1.put("strCol7", objModel.getStrCol7().trim());
						}

						if (objModel.getStrCol8() != null && objModel.getStrCol8().length() > 0) {
							reportParams1.put("strCol8", objModel.getStrCol8().trim());
						}

						if (objModel.getStrCol9() != null && objModel.getStrCol9().length() > 0) {
							reportParams1.put("strCol9", objModel.getStrCol9().trim());
						}

						if (objModel.getStrCol10() != null && objModel.getStrCol10().length() > 0) {
							reportParams1.put("strCol10", objModel.getStrCol10().trim());
						}

						if (objModel.getStrCol11() != null && objModel.getStrCol11().length() > 0) {
							reportParams1.put("strCol11", objModel.getStrCol11().trim());
						}

						if (objModel.getStrCol12() != null && objModel.getStrCol12().length() > 0) {
							reportParams1.put("strCol12", objModel.getStrCol12().trim());
						}

						if (objModel.getStrCol13() != null && objModel.getStrCol13().length() > 0) {
							reportParams1.put("strCol13", objModel.getStrCol13().trim());
						}

						if (objModel.getStrCol14() != null && objModel.getStrCol14().length() > 0) {
							reportParams1.put("strCol14", objModel.getStrCol14().trim());
						}

						if (objModel.getStrCol15() != null && objModel.getStrCol15().length() > 0) {
							reportParams1.put("strCol15", objModel.getStrCol15().trim());
						}

						if (objModel.getStrCol16() != null && objModel.getStrCol16().length() > 0) {
							reportParams1.put("strCol16", objModel.getStrCol16().trim());
						}

					}

					List<clsFLR6ModelRow> dlist = objBean.getRowList1();
					LinkedList<Object> dataList = new LinkedList<Object>();
					if (dlist != null) {
						for (clsFLR6ModelRow objModel : dlist) {
							if (objModel.getStrRow1() != null && objModel.getStrRow1().length() > 0) {
								dataList.add(objModel);
							}
						}
					}

					JRDataSource JRdataSource = new JRBeanCollectionDataSource(dataList);
					String reportName = servletContext.getRealPath("/WEB-INF/exciseReport/rptFLR6Report.jrxml");
					JasperDesign jd = JRXmlLoader.load(reportName);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					jp1 = JasperFillManager.fillReport(jr, reportParams1, JRdataSource);

				}

				if (objBean.getHeaderList2() != null) {

					Map<String, Object> reportParams2 = new HashMap<String, Object>();
					reportParams2.put("strCompanyName", companyName);
					reportParams2.put("strDate", date);
					reportParams2.put("strLicenceNo", licenceNo);

					List<clsFLR6ModelColumns> collist2 = objBean.getHeaderList2();

					if (collist2 != null) {

						clsFLR6ModelColumns objModel = collist2.get(0);
						if (objModel.getStrCol1() != null && objModel.getStrCol1().length() > 0)
							reportParams2.put("strCol1", objModel.getStrCol1().trim());
						if (objModel.getStrCol2() != null && objModel.getStrCol2().length() > 0)
							reportParams2.put("strCol2", objModel.getStrCol2().trim());
						if (objModel.getStrCol3() != null && objModel.getStrCol3().length() > 0)
							reportParams2.put("strCol3", objModel.getStrCol3().trim());
						if (objModel.getStrCol4() != null && objModel.getStrCol4().length() > 0)
							reportParams2.put("strCol4", objModel.getStrCol4().trim());
						if (objModel.getStrCol5() != null && objModel.getStrCol5().length() > 0)
							reportParams2.put("strCol5", objModel.getStrCol5().trim());
						if (objModel.getStrCol6() != null && objModel.getStrCol6().length() > 0)
							reportParams2.put("strCol6", objModel.getStrCol6().trim());
						if (objModel.getStrCol7() != null && objModel.getStrCol7().length() > 0)
							reportParams2.put("strCol7", objModel.getStrCol7().trim());
						if (objModel.getStrCol8() != null && objModel.getStrCol8().length() > 0)
							reportParams2.put("strCol8", objModel.getStrCol8().trim());
						if (objModel.getStrCol9() != null && objModel.getStrCol9().length() > 0)
							reportParams2.put("strCol9", objModel.getStrCol9().trim());
						if (objModel.getStrCol10() != null && objModel.getStrCol10().length() > 0)
							reportParams2.put("strCol10", objModel.getStrCol10().trim());
						if (objModel.getStrCol11() != null && objModel.getStrCol11().length() > 0)
							reportParams2.put("strCol11", objModel.getStrCol11().trim());
						if (objModel.getStrCol12() != null && objModel.getStrCol12().length() > 0)
							reportParams2.put("strCol12", objModel.getStrCol12().trim());
						if (objModel.getStrCol13() != null && objModel.getStrCol13().length() > 0)
							reportParams2.put("strCol13", objModel.getStrCol13().trim());
						if (objModel.getStrCol14() != null && objModel.getStrCol14().length() > 0)
							reportParams2.put("strCol14", objModel.getStrCol14().trim());
						if (objModel.getStrCol15() != null && objModel.getStrCol15().length() > 0)
							reportParams2.put("strCol15", objModel.getStrCol15().trim());
						if (objModel.getStrCol16() != null && objModel.getStrCol16().length() > 0)
							reportParams2.put("strCol16", objModel.getStrCol16().trim());

					}

					List<clsFLR6ModelRow> dlist1 = objBean.getRowList2();
					LinkedList<Object> dataList1 = new LinkedList<Object>();
					if (dlist1 != null) {
						for (clsFLR6ModelRow objModel : dlist1) {
							if (objModel.getStrRow1() != null && objModel.getStrRow1().length() > 0) {
								dataList1.add(objModel);
							}
						}
					}

					JRDataSource JRdataSource1 = new JRBeanCollectionDataSource(dataList1);
					String reportName1 = servletContext.getRealPath("/WEB-INF/exciseReport/rptFLR6Report.jrxml");
					JasperDesign jd1 = JRXmlLoader.load(reportName1);
					JasperReport jr1 = JasperCompileManager.compileReport(jd1);
					jp2 = JasperFillManager.fillReport(jr1, reportParams2, JRdataSource1);

				}

				List<JasperPrint> jprintlist = new ArrayList<JasperPrint>();
				ServletOutputStream servletOutputStream = resp.getOutputStream();

				if (jp1 != null) {

					jprintlist.add(jp1);
					if (jp2 != null) {
						jprintlist.add(jp2);
					}

					if (objBean.getStrExportType().equalsIgnoreCase("EXCEL")) {

						JRExporter exporter = new JRXlsExporter();
						resp.setContentType("application/xls");
						exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
						exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, servletOutputStream);
						resp.setHeader("Content-Disposition", "inline;filename=" + "FLR-6." + "xls");
						exporter.exportReport();

					} else {

						JRExporter exporter = new JRPdfExporter();
						resp.setContentType("application/pdf");
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jprintlist);
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, servletOutputStream);
						exporter.setParameter(JRPdfExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
						resp.setHeader("Content-Disposition", "inline;filename=" + "FLR-6." + "pdf");
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
}
