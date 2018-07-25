package com.sanguine.excise.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.IOFileUploadException;
//import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;
import com.sanguine.excise.model.clsExcisePOSLinkUpModel;
import com.sanguine.excise.model.clsExciseStkPostingDtlModel;
import com.sanguine.excise.model.clsOpeningStockModel;
import com.sanguine.excise.service.clsBrandMasterService;
import com.sanguine.excise.service.clsExcisePOSLinkUpService;
import com.sanguine.excise.service.clsOpeningStockService;
import com.sanguine.service.clsGlobalFunctionsService;

/**
 * @author Vikram Salunkhe
 *
 */
@Controller
public class clsExciseExcelExportImportController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsBrandMasterService objBrandMasterService;

	@Autowired
	private clsFLR3AController objclsFLR3AController;

	@Autowired
	private clsExcisePOSLinkUpService objExcisePOSLinkUpService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	private clsOpeningStockService objOpeningStockService;

	final static Logger logger = Logger.getLogger(clsExciseExcelExportImportController.class);

	/**
	 * Open The Excel Export Import From
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmExciseExcelExportImport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(HttpServletRequest request) {
		return new ModelAndView("frmExciseExcelExportImport");
	}

	// Start Excise Module Export Functions

	/**
	 * Opening Stock Export Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ExcisePhyStkPostExcelExport", method = RequestMethod.GET)
	public ModelAndView funExcisePhyStkPostExcelExport(HttpServletRequest request, @RequestParam("strInPeg") String inPeg) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String isBrandGlobal = "Custom";
		String isSizeGlobal = "Custom";
		String tempSizeClientCode = clientCode;
		try {
			isSizeGlobal = request.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			tempSizeClientCode = "All";
		}

		String tempBrandClientCode = clientCode;
		try {
			isBrandGlobal = request.getSession().getAttribute("strBrandMaster").toString();
		} catch (Exception e) {
			isBrandGlobal = "Custom";
		}
		if (isBrandGlobal.equalsIgnoreCase("All")) {
			tempBrandClientCode = "All";
		}

		String header = "Category Name,Brand Code,Brand Name,MRP,Size Name,Size,Physical Stk. Btl,Physical Stk.Qty.,Unit";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String hql = "select b.strSubCategoryName,a.strBrandCode,a.strBrandName,a.dblMRP,c.strSizeName," + " c.intQty,a.intPegSize from tblbrandmaster a,tblsubcategorymaster b,tblsizemaster c " + " where a.strBrandCode NOT IN(SELECT distinct strParentCode FROM tblexciserecipermasterhd) " + " and a.strSubCategoryCode=b.strSubCategoryCode and a.strSizeCode=c.strSizeCode " + " and a.strClientCode='"
				+ tempBrandClientCode + "' and c.strClientCode='" + tempSizeClientCode + "'" + " ORDER BY b.strSubCategoryCode,c.intQty DESC,a.strBrandName";

		List list = objGlobalFunctionsService.funGetDataList(hql, "sql");
		List ExcisePhyStkPstlist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			List DataList = new ArrayList();

			Integer size = Integer.parseInt(ob[5].toString());
			Integer pegSize = Integer.parseInt(ob[6].toString());

			DataList.add(ob[0]);
			DataList.add(ob[1]);
			DataList.add(ob[2]);
			DataList.add(ob[3]);
			DataList.add(ob[4]);
			DataList.add(size);
			DataList.add("0");
			DataList.add("0");
			if (size <= pegSize) {
				DataList.add("Btls");
			} else {
				DataList.add(inPeg);
			}
			ExcisePhyStkPstlist.add(DataList);
		}
		ExportList.add(ExcisePhyStkPstlist);

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ExcisePOSSaleDataExport", method = RequestMethod.GET)
	public ModelAndView funExcisePOSSaleDataExcelExport(HttpServletRequest request) {
		String header = "Brand Code,Brand Name,Qty Of Sale In PEG/ML/Btl's";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	// Excel Of Brand Price List

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/BrandPriceExcelSheet", method = RequestMethod.GET)
	private ModelAndView funBrandPriceExcelSheet(HttpServletResponse resp, HttpServletRequest req) {

		String header = "SubCategoryName,Brandcode,BrandName,Size,Rate";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

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

		String sqlPrice = "SELECT c.strSubCategoryName as SubCategoryName,a.strBrandCode as Brandcode,a.strBrandName as BrandName, " + " b.strSizeName as Size,IFNULL(d.dblRate,0.0) as Rate " + " FROM tblbrandmaster a LEFT JOIN  tblratemaster d on a.strBrandCode=d.strBrandCode, " + " tblsizemaster b,tblsubcategorymaster c WHERE a.strBrandCode IN (SELECT DISTINCT strBrandCode FROM ( "
				+ " SELECT p.strBrandCode FROM tbltpdtl p,tblbrandmaster m " + " WHERE p.strClientCode='" + clientCode + "' AND p.strBrandCode=m.strBrandCode " + " AND m.strSubCategoryCode=m.strSubCategoryCode UNION ALL ( " + " SELECT q.strBrandCode FROM tblopeningstock q,tblbrandmaster n " + " WHERE q.strClientCode='" + clientCode + "' AND q.strBrandCode=n.strBrandCode "
				+ " AND n.strSubCategoryCode=n.strSubCategoryCode)) AS strBrandCode) AND a.strSizeCode=b.strSizeCode  " + " and a.strSubCategoryCode=c.strSubCategoryCode AND a.strClientCode='" + tempBrandClientCode + "' AND b.strClientCode='" + tempSizeClientCode + "' " + "  ORDER BY c.strSubCategoryCode,a.strBrandName,b.intQty desc ";

		List list = objGlobalFunctionsService.funGetDataList(sqlPrice, "sql");

		List<List> detail = new ArrayList<List>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			List<String> DataList = new ArrayList<String>();
			DataList.add(obj[0].toString());
			DataList.add(obj[1].toString());
			DataList.add(obj[2].toString());
			DataList.add(obj[3].toString());
			DataList.add(obj[4].toString());

			detail.add(DataList);

		}
		ExportList.add(detail);
		return new ModelAndView("excelView", "stocklist", ExportList);

	}

	// Export Excel for Opening Stock

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ExciseOpeningStockExport12", method = RequestMethod.GET)
	private ModelAndView funOpeningStockExport(HttpServletRequest req, HttpServletResponse res) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
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
		List ExportList = new ArrayList();
		String header = "BrandCode,BrandName,LicenceCode,Opening_BTL,Opening_Peg,Opening_IN_ML";
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String sqlBrandList = "select a.strBrandCode,a.strBrandName from tblbrandmaster a where a.strClientCode='" + tempBrandClientCode + "' ";
		List list = objGlobalFunctionsService.funGetDataList(sqlBrandList, "sql");
		List detail = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			List<String> DataList = new ArrayList<String>();
			DataList.add(obj[0].toString());
			DataList.add(obj[1].toString());
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");

			detail.add(DataList);
		}

		ExportList.add(detail);
		return new ModelAndView("excelView", "stocklist", ExportList);

	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// @RequestMapping(value = "/ExciseFLR4ReportExport", method =
	// RequestMethod.GET)
	// public ModelAndView funExciseFLR4Report(HttpServletRequest request)
	// {
	//
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// List ExportList=new ArrayList();
	// List detail=new ArrayList();
	// List subCatCodeForFetch=new ArrayList();
	//
	//
	// String
	// sql_SubCategoryList="select strSubCategoryCode, strSubCategoryName from tblsubcategorymaster "
	// + "  ORDER BY strSubCategoryCode";
	// List subCategoryList =
	// objGlobalFunctionsService.funGetDataList(sql_SubCategoryList, "sql");
	//
	// for(int j = 0; j < subCategoryList.size(); j++)
	// {
	//
	// Object obj[] = (Object[])subCategoryList.get(j);
	// subCatCodeForFetch.add(obj[0].toString());
	//
	// }
	// for(int i=0;i<subCatCodeForFetch.size();i++){
	// String isSizeGlobal="Custom";
	// String tempSizeClientCode=clientCode;
	// try{
	// isSizeGlobal =
	// request.getSession().getAttribute("strSizeMaster").toString();
	// }catch(Exception e){
	// isSizeGlobal="Custom";
	// }
	// if(isSizeGlobal.equalsIgnoreCase("All")){
	// tempSizeClientCode="All";
	// }
	// String isBrandGlobal="Custom";
	// String tempBrandClientCode=clientCode;
	// try{
	// isBrandGlobal =
	// request.getSession().getAttribute("strBrandMaster").toString();
	// }catch(Exception e){
	// isBrandGlobal="Custom";
	// }
	// if(isBrandGlobal.equalsIgnoreCase("All")){
	// tempBrandClientCode="All";
	// }
	//
	// String
	// sql_SizeList="SELECT DISTINCT a.strSizeCode,a.strSizeName,a.intQty,c.strIsDecimal, c.strSubCategoryName "
	// +"FROM tblsizemaster a,tblbrandmaster b,tblsubcategorymaster c "
	// +"WHERE b.strSubCategoryCode=c.strSubCategoryCode and b.strBrandCode IN ( "
	// + " (SELECT DISTINCT strBrandCode FROM "
	// +
	// " (SELECT p.strBrandCode FROM tbltpdtl p,tblbrandmaster m WHERE p.strClientCode='"+clientCode+"' AND "
	// +
	// " p.strBrandCode=m.strBrandCode AND m.strSubCategoryCode='"+subCatCodeForFetch.get(i).toString()+"' "
	// + " UNION ALL  "
	// +
	// " (SELECT q.strBrandCode FROM tblopeningstock q,tblbrandmaster n WHERE q.strClientCode='"+clientCode+"' AND "
	// +
	// " q.strBrandCode=n.strBrandCode AND n.strSubCategoryCode='"+subCatCodeForFetch.get(i).toString()+"')) AS strBrandCode) AND"
	// +
	// " a.strSizeCode=b.strSizeCode AND b.strSubCategoryCode='"+subCatCodeForFetch.get(i).toString()+"' AND b.strSubCategoryCode=c.strSubCategoryCode AND "
	// +
	// " a.strClientCode='"+tempSizeClientCode+"' and b.strClientCode='"+tempBrandClientCode+"' ORDER BY intQty DESC";
	// List data= objGlobalFunctionsService.funGetDataList(sql_SizeList, "sql");
	// if(!(data.isEmpty())){
	// String header="";
	//
	// for(int a=0;a<data.size();i++)
	// {
	// Object[] ob=(Object[])data.get(i);
	//
	// List DataList=new ArrayList<>();
	// DataList.add(ob[0].toString());
	// DataList.add(ob[1].toString());
	// DataList.add(ob[2].toString());
	// DataList.add(ob[3].toString());
	// DataList.add(ob[4].toString());
	// detail.add(DataList);
	// }
	// }
	// ExportList.add(detail);
	// return new ModelAndView("excelView", "stocklist", ExportList);
	// }
	// return null;
	// }
	// End Excise Module Export Functions

	/**
	 * This function is for Import the data from Excel File
	 * 
	 * @param excelfile
	 * @param request
	 * @param res
	 * @return
	 * @throws IOFileUploadException
	 */

	@SuppressWarnings({ "rawtypes", "resource" })
	@RequestMapping(value = "/SaveExciseExcelExportImport", method = RequestMethod.POST)
	public @ResponseBody List funUploadExcel(@RequestParam("file") MultipartFile excelfile, HttpServletRequest request, HttpServletResponse res) throws IOFileUploadException {
		List list = new ArrayList<>();
		String formname = request.getParameter("formname").toString();
		try {

			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			switch (formname) {
			case "frmExcisePhyStkPosting":
				list = funExcisePhyStkPosting(worksheet, request);
				break;

			case "ExcisePOSSaleDataExcelExportImport":
				list = funPOSSaleDataPosting(worksheet, request);
				break;

			case "ExciseOpeningStockExportImport":
				list = funOpeningStockImport(worksheet, request);
				break;

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	// Starting Excise Import

	/**
	 * @param worksheet
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List funExcisePhyStkPosting(HSSFSheet worksheet, HttpServletRequest request) {

		List listOpeningStklist = new ArrayList<>();
		int RowCount = 0;
		try {
			int i = 1;
			String clientCode = request.getSession().getAttribute("clientCode").toString();

			String isBrandGlobal = "Custom";
			String isSizeGlobal = "Custom";
			String tempSizeClientCode = clientCode;
			try {
				isSizeGlobal = request.getSession().getAttribute("strSizeMaster").toString();
			} catch (Exception e) {
				isSizeGlobal = "Custom";
			}
			if (isSizeGlobal.equalsIgnoreCase("All")) {
				tempSizeClientCode = "All";
			}

			String tempBrandClientCode = clientCode;
			try {
				isBrandGlobal = request.getSession().getAttribute("strBrandMaster").toString();
			} catch (Exception e) {
				isBrandGlobal = "Custom";
			}
			if (isBrandGlobal.equalsIgnoreCase("All")) {
				tempBrandClientCode = "All";
			}

			String fromDate = request.getSession().getAttribute("startDate").toString();
			fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			String toDate = format1.format(cal.getTime());

			while (i <= worksheet.getLastRowNum()) {

				HSSFRow row = worksheet.getRow(i++);
				RowCount = row.getRowNum();

				String phyBtls = String.valueOf(row.getCell(6).getNumericCellValue());
				String phyQty = String.valueOf(row.getCell(7).getNumericCellValue());
				if (!phyBtls.equals("") || !phyQty.equals("")) {
					if (Double.parseDouble(phyBtls) > 0 || Double.parseDouble(phyQty) > 0) {

						String brandCode = row.getCell(1).getStringCellValue();
						String brandName = row.getCell(2).getStringCellValue();
						String brandMRP = String.valueOf(row.getCell(3).getNumericCellValue());
						String brandUOM = row.getCell(8).getStringCellValue();
						Integer brandBts = (int) Double.parseDouble(phyBtls);
						Integer brandQty = (int) Double.parseDouble(phyQty);

						Long decQty = new Long(brandQty);

						String sql_BrandList = "select a.strBrandCode,a.strSizeCode,a.strShortName,b.intQty,a.intPegSize " + "  from tblbrandmaster a, tblsizemaster b " + " where a.strBrandCode='" + brandCode + "' and a.strSizeCode=b.strSizeCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' ORDER BY b.intQty DESC";
						List objBrandData = objGlobalFunctionsService.funGetDataList(sql_BrandList, "sql");

						String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandCode + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
						List ObjOPDataList = objGlobalFunctionsService.funGetDataList(sql_OpData, "sql");

						if (objBrandData.size() > 0) {
							Object brandData[] = (Object[]) objBrandData.get(0);

							Integer intOpBtls = 0;
							Integer intOpPeg = 0;
							Integer intOpML = 0;
							if (ObjOPDataList.size() > 0) {
								Object opData[] = (Object[]) ObjOPDataList.get(0);
								intOpBtls = Integer.parseInt(opData[0].toString());
								intOpPeg = Integer.parseInt(opData[1].toString());
								intOpML = Integer.parseInt(opData[2].toString());
							}

							List brandDataList = new LinkedList();
							brandDataList.add(brandData[0]);
							brandDataList.add(brandData[1]);
							brandDataList.add(brandData[2]);
							brandDataList.add(brandData[3]);
							brandDataList.add(brandData[4]);
							brandDataList.add(intOpBtls);
							brandDataList.add(intOpPeg);
							brandDataList.add(intOpML);

							LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, request, fromDate, toDate);

							String currentStk = "0.0";
							if (stkData != null) {
								currentStk = stkData.get(6).toString().trim();
							}

							String[] strCurentArr = String.valueOf(currentStk).split("\\.");
							Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
							Long stkPeg = new Long(0);
							Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
							Long sysStock = funStockInML(stBls, stkPeg, stkML, brandDataList);

							Long phyBls = new Long(brandBts);
							Long phyPeg = new Long(0);
							Long phyML = new Long(0);

							if (brandUOM.equalsIgnoreCase("Peg")) {
								phyPeg = decQty;
							} else if (brandUOM.equalsIgnoreCase("ML")) {
								phyML = decQty;
							}
							Long phyStock = funStockInML(phyBls, phyPeg, phyML, brandDataList);

							Long varianceInML = sysStock - phyStock;
							Long brandSize = Long.parseLong(brandDataList.get(3).toString());
							Long brandPegSize = Long.parseLong(brandDataList.get(4).toString());

							if (brandPegSize <= 0) {
								brandPegSize = brandSize;
							}

							clsExciseStkPostingDtlModel exPhyStkDtl = new clsExciseStkPostingDtlModel();
							exPhyStkDtl.setStrBrandCode(brandCode);
							exPhyStkDtl.setStrBrandName(brandName);
							exPhyStkDtl.setDblBrandMRP(Double.valueOf(brandMRP));
							exPhyStkDtl.setIntPhyBtl(phyBls);
							exPhyStkDtl.setIntPhyML(phyML);
							exPhyStkDtl.setIntPhyPeg(phyPeg);
							exPhyStkDtl.setIntSysBtl(stBls);
							exPhyStkDtl.setIntSysML(stkML);
							exPhyStkDtl.setIntSysPeg(stkPeg);
							exPhyStkDtl.setIntVarianceInML(varianceInML);
							exPhyStkDtl.setIntBrandSize(brandSize);
							exPhyStkDtl.setIntPegSize(brandPegSize);
							exPhyStkDtl.setStrOpStk(currentStk);

							listOpeningStklist.add(exPhyStkDtl);
						}

					}
				}
			}
		} catch (Exception e) {
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount);
			return list;
		}
		return listOpeningStklist;
	}

	/**
	 * @param worksheet
	 * @param request
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List funPOSSaleDataPosting(HSSFSheet worksheet, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		Boolean allItemNotLinked = false;
		int RowCount = 0;
		List<List> responseList = new ArrayList<List>();
		List<clsExciseManualSaleDtlModel> modelList = new ArrayList<clsExciseManualSaleDtlModel>();
		List<String> stkExcededBrandList = new ArrayList<String>();

		try {

			int i = 1;
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

			while (i <= worksheet.getLastRowNum()) {

				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);

				// Sets the Read data to the model class
				RowCount = row.getRowNum();

				String itemCode = String.valueOf(row.getCell(0).getStringCellValue());
				String itemName = String.valueOf(row.getCell(1).getStringCellValue());

				String itemInLinkUp = "select a.strBrandCode,a.strSizeCode,a.strShortName, " + " b.intQty,a.intPegSize,c.intConversionFactor " + " from tblbrandmaster a, tblsizemaster b, tblexciseposlinkup c " + " where c.strPOSItemCode='" + itemCode + "' and c.strClientCode='" + clientCode + "' " + " and c.strBrandCode=a.strBrandCode and a.strSizeCode=b.strSizeCode " + " and  a.strClientCode='"
						+ tempBrandClientCode + "' and b.strClientCode='" + tempSizeClientCode + "' " + " ORDER BY b.intQty DESC ";
				List brandList = objGlobalFunctionsService.funGetDataList(itemInLinkUp, "sql");

				if (brandList.size() > 0) {
					String brandQty = String.valueOf(row.getCell(2).toString());
					if (!brandQty.equals("")) {
						Long qty = new Long(0);
						Boolean isQty = false;
						try {
							String tempArr[] = brandQty.split("\\.");
							qty = Long.parseLong(tempArr[0].toString());
							isQty = true;
						} catch (Exception e) {
							isQty = false;
						}
						if (isQty) {

							String fromDate = req.getSession().getAttribute("startDate").toString();
							fromDate = fromDate.split("/")[2] + "-" + fromDate.split("/")[1] + "-" + fromDate.split("/")[0];
							Calendar cal = Calendar.getInstance();
							SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
							String toDate = format1.format(cal.getTime());

							Object brandData[] = (Object[]) brandList.get(0);

							String sql_OpData = "select c.intOpBtls,c.intOpPeg,c.intOpML from tblbrandmaster a,tblopeningstock c " + " where a.strBrandCode='" + brandData[0].toString() + "' and a.strBrandCode=c.strBrandCode  " + " and  a.strClientCode='" + tempBrandClientCode + "' and  c.strClientCode='" + clientCode + "' ";
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

							List brandDataList = new LinkedList();

							brandDataList.add(brandData[0]);
							brandDataList.add(brandData[1]);
							brandDataList.add(brandData[2]);
							brandDataList.add(brandData[3]);
							brandDataList.add(brandData[4]);
							brandDataList.add(intOpBtls);
							brandDataList.add(intOpPeg);
							brandDataList.add(intOpML);
							LinkedList stkData = objclsFLR3AController.funStockList(brandDataList, req, fromDate, toDate);

							String currentStk = "0.0";
							if (stkData != null) {
								currentStk = stkData.get(6).toString().trim();
							}

							String[] strCurentArr = String.valueOf(currentStk).split("\\.");
							Long stBls = Long.parseLong(strCurentArr[0].toString().trim());
							Long stkPeg = new Long(0);
							Long stkML = Long.parseLong(strCurentArr[1].toString().trim());
							Long availableStk = funStockInML(stBls, stkPeg, stkML, brandDataList);

							Long saleBls = new Long(0);
							Long salePeg = new Long(0);
							Long saleML = new Long(0);

							Integer brandSize = Integer.parseInt(brandDataList.get(3).toString());
							Integer brandPegSize = Integer.parseInt(brandDataList.get(4).toString());

							if (brandSize <= brandPegSize) {
								saleBls = qty;
							} else {
								if (Integer.parseInt(brandData[5].toString()) > 1) {
									salePeg = qty;
								} else {
									saleML = qty;
								}

							}
							Long SaleQty = funStockInML(saleBls, salePeg, saleML, brandDataList);

							if (availableStk >= SaleQty) {

								clsExciseManualSaleDtlModel objModel = new clsExciseManualSaleDtlModel();
								objModel.setIntBrandSize(Long.parseLong(brandDataList.get(3).toString()));
								objModel.setIntBtl(saleBls);
								objModel.setIntML(saleML);
								objModel.setIntPeg(salePeg);
								objModel.setIntPegSize(Long.parseLong(brandDataList.get(4).toString()));
								objModel.setStrBillGenFlag("N");
								objModel.setStrBrandCode(brandDataList.get(0).toString());
								objModel.setStrBrandName(brandDataList.get(2).toString());
								objModel.setStrOpStk(currentStk);
								modelList.add(objModel);
							} else {
								stkExcededBrandList.add(itemName);
							}
						}
					}
				} else {
					clsExcisePOSLinkUpModel objclsPOSLinkUp = objExcisePOSLinkUpService.funGetPOSLinkUp(itemCode, clientCode);
					if (objclsPOSLinkUp == null) {
						String sql = " insert into tblexciseposlinkup (strPOSItemCode,strPOSItemName,strClientCode) " + " values ('" + itemCode + "','" + itemName + "','" + clientCode + "')";
						objExcisePOSLinkUpService.funExecute(sql);
						allItemNotLinked = true;
					}
				}

			}

		} catch (Exception e) {
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount);
			return list;
		}

		if (allItemNotLinked) {
			List list = new ArrayList<>();
			list.add("Item Not Linked");
			list.add("All Item's are Not Linked Please Link It");
			return list;
		} else {
			responseList.add(modelList);
			responseList.add(stkExcededBrandList);
			return responseList;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List funOpeningStockImport(HSSFSheet worksheet, HttpServletRequest req) {
		List listOpeningStklist = new ArrayList();
		int RowCount = 0;
		try {

			List listresponse = new ArrayList();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String statrDate = req.getSession().getAttribute("startDate").toString();
			String[] spDate = statrDate.split("/");
			String day = spDate[0];

			String month = spDate[1];
			String yr = spDate[2];
			String stDate = yr + "-" + month + "-" + day;

			int i = 1;
			boolean flgCheckDuplicate = false;
			String SqlCheckDuplicate = "select a.strLicenceCode,a.strBrandCode from tblopeningstock a where a.strClientCode='" + clientCode + "'";
			List listCheckDuplicate = objGlobalFunctionsService.funGetDataList(SqlCheckDuplicate, "sql");
			while (i <= worksheet.getLastRowNum()) {

				flgCheckDuplicate = false;

				HSSFRow row = worksheet.getRow(i++);

				if (!(row == null)) {

					RowCount = row.getRowNum();
					String brandCode;
					try {
						brandCode = row.getCell(0).getStringCellValue();
					} catch (Exception e) {
						brandCode = "";
					}
					if (!(brandCode.isEmpty()) || !(brandCode.equals(""))) {
						String brandName = row.getCell(1).getStringCellValue();
						String licenceCode;
						try {
							licenceCode = row.getCell(2).getStringCellValue();
						} catch (Exception e) {
							licenceCode = "";
						}

						if (!(licenceCode.isEmpty()) || !(licenceCode.equals(""))) {

							// String
							// SqlCheckDuplicate="select a.strLicenceCode,a.strBrandCode,a.intOpBtls,a.intOpPeg,a.intOpML from tblopeningstock a "
							// +
							// "where a.strBrandCode='"+brandCode+"' and a.strLicenceCode='"+licenceCode+"' and a.strClientCode='"+clientCode+"' ";

							if (!(listCheckDuplicate.isEmpty()))
								for (int cnt = 0; cnt < listCheckDuplicate.size(); cnt++) {

									Object[] objCheckDuplicate = (Object[]) listCheckDuplicate.get(cnt);
									String chckLicenceCode = objCheckDuplicate[0].toString();
									String chckBrandCode = objCheckDuplicate[1].toString();
									if (chckLicenceCode.equals(licenceCode) && chckBrandCode.equals(brandCode)) {
										flgCheckDuplicate = true;
										break;

									}
								}

							if (flgCheckDuplicate) {
								List list = new ArrayList<>();
								listOpeningStklist.add("Invalid Excel File");
								listOpeningStklist.add("Duplicate Entry" + "BrandCode=" + brandCode + "    Licence=" + licenceCode);

								break;

							}
							Long intOpBtls = 0l;
							Long intOpPeg = 0l;
							Long intOpML = 0l;
							try {
								intOpBtls = (long) row.getCell(3).getNumericCellValue();
							} catch (Exception e) {
								intOpBtls = 0l;
							}

							try {
								intOpPeg = (long) row.getCell(4).getNumericCellValue();
							} catch (Exception e) {
								intOpPeg = 0l;
							}
							try {
								intOpML = (long) row.getCell(5).getNumericCellValue();
							} catch (Exception e) {
								intOpML = 0l;
							}
							//
							// if((!strOpBtls.isEmpty())||(!strOpBtls.equals(""))||(strOpBtls!=null)){
							// intOpBtls=(long)
							// row.getCell(3).getNumericCellValue();
							// }
							// if(!(strOpPeg.isEmpty())||!(strOpPeg.equals(""))||strOpPeg!=null){
							// intOpPeg=(long)
							// row.getCell(4).getNumericCellValue();
							// }
							// if(!(strOPnML.isEmpty())||!(strOPnML.equals(""))){
							// intOpML=(long)
							// row.getCell(5).getNumericCellValue();
							// }
							// objOpeningModel.setStrBrandCode(brandCode);
							// objOpeningModel.setStrLicenceCode(licenceCode);
							// objOpeningModel.setStrBrandName(brandName);
							// objOpeningModel.setIntOpBtls(intOpBtls);
							// objOpeningModel.setIntOpPeg(intOpPeg);
							// objOpeningModel.setIntOpML(intOpML);
							// objOpeningModel.setStrUserCreated(userCode);
							// objOpeningModel.setStrUserEdited(userCode);
							// objOpeningModel.setDteDateCreated(stDate);
							// objOpeningModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							// objOpeningModel.setStrClientCode(clientCode);
							List listAddUpdate = new ArrayList<>();
							listAddUpdate.add(brandCode);
							listAddUpdate.add(licenceCode);
							listAddUpdate.add(brandName);
							listAddUpdate.add(intOpBtls);
							listAddUpdate.add(intOpPeg);
							listAddUpdate.add(intOpML);
							listAddUpdate.add(userCode);
							listAddUpdate.add(userCode);
							listAddUpdate.add(stDate);
							listAddUpdate.add(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
							listAddUpdate.add(clientCode);
							listresponse.add(listAddUpdate);

						}
					}
				}
			}
			if (!flgCheckDuplicate) {
				for (int a = 0; a < listresponse.size(); a++) {

					List listAddUpdate = (List) listresponse.get(a);

					clsOpeningStockModel objOpeningModel = new clsOpeningStockModel();

					objOpeningModel.setStrBrandCode(listAddUpdate.get(0).toString());
					objOpeningModel.setStrLicenceCode(listAddUpdate.get(1).toString());
					objOpeningModel.setStrBrandName(listAddUpdate.get(2).toString());
					objOpeningModel.setIntOpBtls((Long) listAddUpdate.get(3));
					objOpeningModel.setIntOpPeg((Long) listAddUpdate.get(4));
					objOpeningModel.setIntOpML((Long) listAddUpdate.get(5));
					objOpeningModel.setStrUserCreated(userCode);
					objOpeningModel.setStrUserEdited(userCode);
					objOpeningModel.setDteDateCreated(stDate);
					objOpeningModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objOpeningModel.setStrClientCode(clientCode);
					objOpeningStockService.funAddUpdateExBrandOpMaster(objOpeningModel);
					listOpeningStklist.add(objOpeningModel);

				}

			}

		} catch (Exception e) {
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount);
			return list;
		}

		return listOpeningStklist;
	}

	@SuppressWarnings("rawtypes")
	public Long funStockInML(Long bottals, Long intpegs, Long intML, List brandDataList) {

		Integer brandSize = Integer.parseInt(brandDataList.get(3).toString());
		Integer pegSize = Integer.parseInt(brandDataList.get(4).toString());

		Long quantity = (long) 0;
		if (pegSize <= 0) {
			pegSize = brandSize;
		}
		Long btsMl = brandSize * bottals;
		Long pegMl = pegSize * intpegs;
		quantity = btsMl + pegMl + intML;

		return quantity;
	}

}
