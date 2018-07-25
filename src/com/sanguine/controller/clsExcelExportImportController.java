package com.sanguine.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.IOFileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsOpeningStkDtl;
import com.sanguine.model.clsPOSSalesDtlModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsPOSLinkUpService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsSubGroupMasterService;

@Controller
public class clsExcelExportImportController {

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsSubGroupMasterService objSubGroupMasterService;

	@Autowired
	private clsGroupMasterService objGroupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsPOSLinkUpService objPOSLinkUpService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGRNController objGRNController;

	final static Logger logger = Logger.getLogger(clsExcelExportImportController.class);

	/**
	 * Open The Excel Export Import From
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmExcelExportImport", method = RequestMethod.GET)
	public ModelAndView funOpenForm(HttpServletRequest request) {
		String exportUOM = request.getParameter("exportUOM");
		request.getSession().removeAttribute("exportUOM");
		request.getSession().setAttribute("exportUOM", exportUOM);
		return new ModelAndView("frmExcelExportImport");
	}

	/**
	 * Opening Stock Export Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmOpeningStkExcelExport", method = RequestMethod.GET)
	public ModelAndView funstkOpeningExcelExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String locCode = request.getParameter("strLocCode");
		String header = "Group Name, SubGroupName,ProductCode,ProductName,Qty,UOM,Cost Per Unit,Revision Level,Lot No";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		String hql = "";
		if (objSetup.getStrShowAllProdToAllLoc() == null || objSetup.getStrShowAllProdToAllLoc() == "N") {
			hql = " from clsProductMasterModel a, clsSubGroupMasterModel b,clsGroupMasterModel c ,clsProductReOrderLevelModel d" + " where a.strSGCode=b.strSGCode  and b.strGCode=c.strGCode and a.strProdCode=d.strProdCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + "and c.strClientCode='" + clientCode + "'  and d.strLocationCode='" + locCode + "' ";
		} else {
			hql = "from clsProductMasterModel a, clsSubGroupMasterModel b,clsGroupMasterModel c " + " where a.strSGCode=b.strSGCode  and b.strGCode=c.strGCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' and c.strClientCode='" + clientCode + "'";
		}

		List list = objGlobalFunctionsService.funGetList(hql, "hql");
		String expUOM = request.getSession().getAttribute("exportUOM").toString();
		request.getSession().removeAttribute("exportUOM");
		List OpeningStklist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			clsProductMasterModel prodModel = (clsProductMasterModel) ob[0];
			clsSubGroupMasterModel subGroupModel = (clsSubGroupMasterModel) ob[1];
			clsGroupMasterModel groupModel = (clsGroupMasterModel) ob[2];

			List DataList = new ArrayList<>();
			DataList.add(groupModel.getStrGName());
			DataList.add(subGroupModel.getStrSGName());
			DataList.add(prodModel.getStrProdCode());
			DataList.add(prodModel.getStrProdName());
			DataList.add("");
			if (expUOM.equals("RecUOM")) {
				DataList.add(prodModel.getStrReceivedUOM());
			}
			if (expUOM.equals("IssueUOM")) {
				DataList.add(prodModel.getStrIssueUOM());
			}
			if (expUOM.equals("RecipeUOM")) {
				DataList.add(prodModel.getStrRecipeUOM());
			}

			DataList.add(prodModel.getDblCostRM());
			DataList.add("0.00");
			DataList.add("0.00");
			OpeningStklist.add(DataList);
		}
		ExportList.add(OpeningStklist);

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	/**
	 * Exporting Physical Stock Posting Data
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/PhyStkPstExcelExport", method = RequestMethod.GET)
	public ModelAndView funPhyStkPstExcelExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		// String
		// locCode=request.getSession().getAttribute("locationCode").toString();
		String locCode = request.getParameter("strLocCode");
		String header = "Group Name, SubGroupName,ProductCode,ProductName,Qty,UOM";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);
		String hql = "";
		if (objSetup.getStrShowAllProdToAllLoc() == null || objSetup.getStrShowAllProdToAllLoc() == "N") {
			hql = " from clsProductMasterModel a, clsSubGroupMasterModel b,clsGroupMasterModel c ,clsProductReOrderLevelModel d" + " where a.strSGCode=b.strSGCode  and b.strGCode=c.strGCode and a.strProdCode=d.strProdCode " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + "and c.strClientCode='" + clientCode + "'  and d.strLocationCode='" + locCode + "' ";
		} else {
			hql = " from clsProductMasterModel a, clsSubGroupMasterModel b,clsGroupMasterModel c " + " where a.strSGCode=b.strSGCode  and b.strGCode=c.strGCode  " + " and a.strClientCode='" + clientCode + "' and b.strClientCode='" + clientCode + "' " + "and c.strClientCode='" + clientCode + "'  ";
		}

		List list = objGlobalFunctionsService.funGetList(hql, "hql");
		List PhyStkPstlist = new ArrayList();
		for (int i = 0; i < list.size(); i++) {

			Object[] ob = (Object[]) list.get(i);
			clsProductMasterModel prodModel = (clsProductMasterModel) ob[0];
			clsSubGroupMasterModel subGroupModel = (clsSubGroupMasterModel) ob[1];
			clsGroupMasterModel groupModel = (clsGroupMasterModel) ob[2];
			List DataList = new ArrayList<>();

			DataList.add(groupModel.getStrGName());
			DataList.add(subGroupModel.getStrSGName());
			DataList.add(prodModel.getStrProdCode());
			DataList.add(prodModel.getStrProdName());
			DataList.add("");
			DataList.add(prodModel.getStrUOM());
			PhyStkPstlist.add(DataList);

		}
		ExportList.add(PhyStkPstlist);

		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	/**
	 * Location Master Reorder Level Exporting
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/LocationMasterReorderLevelExcelExport", method = RequestMethod.GET)
	public ModelAndView funLocMastReOrderLevelExcelExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String LocCode = "";
		List list = new ArrayList<>();
		List LocMastReOrderLvllist = new ArrayList();
		if (request.getParameter("locCode") != null) {
			LocCode = request.getParameter("locCode").toString();
		}
		String header = "Location Code,Location Name,GroupName, SubGroupName,ProductCode,ProductName,Non Stockable,ReOrderLevel,ReOrderQty";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String sql = "select ifnull(e.strLocCode,'') as strLocCode,ifnull(e.strLocName,'') as strLocName,ifnull(c.strGName,'') as strGName,ifnull(b.strSGName,'') as strSGName," + " a.strProdCode,a.strProdName,a.strNonStockableItem,ifnull(d.dblReOrderLevel,0.00) as dblReOrderLevel,ifnull(d.dblReOrderQty,0.00) as dblReOrderQty "
				+ " from tblproductmaster a left outer join tblsubgroupmaster b on a.strSGCode=b.strSGCode and b.strClientCode='" + clientCode + "' " + " left outer join tblgroupmaster c on c.strGCode=b.strGCode and c.strClientCode='" + clientCode + "' " + " left outer join tblreorderlevel d on d.strProdCode=a.strProdCode and d.strLocationCode='" + LocCode + "'  and d.strClientCode='"
				+ clientCode + "'" + " left outer join tbllocationmaster e on d.strLocationCode=e.strLocCode and e.strLocCode='" + LocCode + "'  and  e.strClientCode='" + clientCode + "'" + " where a.strClientCode='" + clientCode + "' ";
		list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			List DataList = new ArrayList<>();
			DataList.add(ob[0]);
			DataList.add(ob[1]);
			DataList.add(ob[2]);
			DataList.add(ob[3]);
			DataList.add(ob[4]);
			DataList.add(ob[5]);
			DataList.add(ob[6]);
			DataList.add(ob[7]);
			DataList.add(ob[8]);
			LocMastReOrderLvllist.add(DataList);
		}
		ExportList.add(LocMastReOrderLvllist);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	/**
	 * for POS Sales Excel Export for third party POS Sales
	 * 
	 * @param request
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/POSSalesExcelExport", method = RequestMethod.GET)
	public ModelAndView funPOSSalesExcelExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String LocCode = "";
		List list = new ArrayList<>();
		List LocMastReOrderLvllist = new ArrayList();
		if (request.getParameter("locCode") != null) {
			LocCode = request.getParameter("locCode").toString();
		}
		String header = "POS Code,POS Item Code,POS Item Name,Qty,Rate,BillDate(dd/mm/yyyy)";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		ExportList.add(LocMastReOrderLvllist);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	/**
	 * Only Import
	 * 
	 * @param excelfile
	 * @param request
	 * @param res
	 * @return
	 * @throws IOFileUploadException
	 */

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/ExcelExportImport", method = RequestMethod.POST)
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
			case "frmOpeningStock":
				list = funOpeningStocks(worksheet, request);
				break;

			case "frmPhysicalStkPosting":
				list = funPhyStkPsting(worksheet, request);
				break;
			case "frmLocationMaster":
				list = funLocMastReOrderLvl(worksheet, request);
				break;

			case "frmPOSSalesSheet":
				list = funLoadPOSSalesData(worksheet, request);
				break;

			case "frmSalesOrder":
				list = funLoadPOSSalesData(worksheet, request);
				break;

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * Start WebStock Import
	 */
	/**
	 * Opening Stock Import function
	 * 
	 * @param worksheet
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funOpeningStocks(HSSFSheet worksheet, HttpServletRequest request) {
		String expUOM = request.getSession().getAttribute("exportUOM").toString();

		List listOpeningStklist = new ArrayList<>();
		int RowCount = 0;
		String prodCode = "";
		try {

			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the Candidate Model
				clsOpeningStkDtl OpeningStkDtl = new clsOpeningStkDtl();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);

				// Sets the Read data to the model class
				RowCount = row.getRowNum();
				prodCode = row.getCell(2).getStringCellValue();
				Cell c = row.getCell(4);
				if (c != null && c.getCellType() != 1) {
					OpeningStkDtl.setStrProdCode(row.getCell(2).getStringCellValue());
					String prodName = "";
					if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						prodName = String.valueOf(row.getCell(3).getNumericCellValue());
					} else {
						prodName = row.getCell(3).getRichStringCellValue().toString();
					}
					OpeningStkDtl.setStrProdName(prodName);
					OpeningStkDtl.setDblQty(row.getCell(4).getNumericCellValue());

					String uom = funGetProductUOM(row.getCell(2).getStringCellValue(), expUOM, request);
					OpeningStkDtl.setStrUOM(uom); // thiS is for Independent of
													// excel sheet uom

					// OpeningStkDtl.setStrUOM(row.getCell(5).getStringCellValue());
					// thiS is for according to EXcel uom
					OpeningStkDtl.setDblCostPUnit(row.getCell(6).getNumericCellValue());
					OpeningStkDtl.setDblRevLvl(row.getCell(7).getNumericCellValue());
					OpeningStkDtl.setStrLotNo(String.valueOf(row.getCell(8).getNumericCellValue()));
					// Sends the model object to service layer for validation,
					// data processing and then to persist
					listOpeningStklist.add(OpeningStkDtl);
				}

			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + prodCode + " ");
			return list;
		}
		return listOpeningStklist;
	}

	/**
	 * \ Physical Stock Posting Import function
	 * 
	 * @param worksheet
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funPhyStkPsting(HSSFSheet worksheet, HttpServletRequest request) {
		List listPhyStklist = new ArrayList<>();
		int RowCount = 0;
		String prodCode = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		try {
			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the Candidate Model
				clsStkPostingDtlModel PhyStkDtl = new clsStkPostingDtlModel();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				RowCount = row.getRowNum();
				prodCode = row.getCell(2).getStringCellValue();
				Cell c = row.getCell(4);
				if (c != null && c.getCellType() != 1) {
					if (row.getCell(4).getNumericCellValue() >= 0) {
						PhyStkDtl.setStrProdCode(row.getCell(2).getStringCellValue());
						String prodName = "";
						if (row.getCell(3).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							prodName = String.valueOf(row.getCell(3).getNumericCellValue());
						} else {
							prodName = row.getCell(3).getRichStringCellValue().toString();
						}

						PhyStkDtl.setStrProdName(prodName);
						PhyStkDtl.setDblPStock(row.getCell(4).getNumericCellValue());
						clsProductMasterModel Prodmodel = objProductMasterService.funGetObject(prodCode, clientCode);
						PhyStkDtl.setDblPrice(Prodmodel.getDblCostRM());
						PhyStkDtl.setDblWeight(Prodmodel.getDblWeight());

						List ProdList = objGRNController.funLatestGRNProductRate(prodCode, request);

						if (!ProdList.isEmpty()) {
							PhyStkDtl.setDblActualRate(Double.parseDouble((ProdList.get(1)).toString()));

						} else {
							PhyStkDtl.setDblActualRate(Prodmodel.getDblCostRM());
						}

						// PhyStkDtl.setDblCStock(objGlobalFunctions.funGetCurrentStockForProduct(prodCode,
						// objMISHd.getStrLocFrom(), clientCode,
						// userCode,startDate,objGlobalFunctions.funGetCurrentDate("yyyy-MM-dd")));
						// Sends the model object to service layer for
						// validation,
						// data processing and then to persist
						listPhyStklist.add(PhyStkDtl);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + prodCode + " ");
			return list;
		}
		return listPhyStklist;
	}

	/**
	 * Location Master Reorder Level Import
	 * 
	 * @param worksheet
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List funLocMastReOrderLvl(HSSFSheet worksheet, HttpServletRequest request) {
		List listReoderLvllist = new ArrayList<>();
		int RowCount = 0;
		String prodCode = "";
		try {
			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the Candidate Model

				List ReorderLvlList = new ArrayList<>();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				String ReOrderlvl = String.valueOf(row.getCell(7).getNumericCellValue());
				String ReOrderQty = String.valueOf(row.getCell(8).getNumericCellValue());
				if (!ReOrderlvl.equals("") || !ReOrderQty.equals("")) {
					RowCount = row.getRowNum();
					ReorderLvlList.add(row.getCell(4).getStringCellValue());
					prodCode = row.getCell(4).getStringCellValue();
					String prodName = "";
					if (row.getCell(5).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						prodName = String.valueOf(row.getCell(5).getNumericCellValue());
					} else {
						prodName = row.getCell(5).getRichStringCellValue().toString();
					}
					ReorderLvlList.add(prodName);
					ReorderLvlList.add(row.getCell(7).getNumericCellValue());
					ReorderLvlList.add(row.getCell(8).getNumericCellValue());
					// Sends the model object to service layer for validation,
					// data processing and then to persist
					listReoderLvllist.add(ReorderLvlList);
				}
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + prodCode + " ");
			return list;
		}
		return listReoderLvllist;
	}

	private String funGetProductUOM(String prodCode, String UOMType, HttpServletRequest request) {
		String UOM = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String hql = "from clsProductMasterModel a  where a.strProdCode='" + prodCode + "'  and a.strClientCode='" + clientCode + "' ";

		List list = objGlobalFunctionsService.funGetList(hql, "hql");
		clsProductMasterModel prodModel = (clsProductMasterModel) list.get(0);
		if (UOMType.equals("RecUOM")) {
			UOM = prodModel.getStrReceivedUOM();
		}
		if (UOMType.equals("IssueUOM")) {
			UOM = prodModel.getStrIssueUOM();
		}
		if (UOMType.equals("RecipeUOM")) {
			UOM = prodModel.getStrRecipeUOM();
		}
		return UOM;
	}

	private List funLoadPOSSalesData(HSSFSheet worksheet, HttpServletRequest request) {
		List listPOSSalelist = new ArrayList<>();
		List<clsPOSSalesDtlModel> listPOSSalesDtl = new ArrayList<clsPOSSalesDtlModel>();
		int RowCount = 0;
		String prodCode = "";
		String sql = " insert into tblpossalesdtl (strClientCode,strPOSItemCode,dblQuantity,dblRate,dteBillDate,strPOSCode,strPOSItemName,strSACode,strWSItemCode)  ";
		String sqlValues = " values ";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sf2 = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = null;
		Date toDate = null;
		// new File("C:\\Directory1").mkdir();
		Date dte = null;
		try {
			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the Candidate Model

				List POSDataList = new ArrayList<>();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				clsPOSSalesDtlModel objSalesDtl = new clsPOSSalesDtlModel();
				String posCode = (row.getCell(0) != null) ? row.getCell(0).getStringCellValue() : "";
				String itemCode = (row.getCell(1) != null) ? row.getCell(1).getStringCellValue() : "";
				String itemName = (row.getCell(2) != null) ? row.getCell(2).getStringCellValue() : "";
				String qty = (row.getCell(3) != null) ? String.valueOf(row.getCell(3).getNumericCellValue()) : "";
				String rate = (row.getCell(4) != null) ? String.valueOf(row.getCell(4).getNumericCellValue()) : "";
				String billDate = (row.getCell(5) != null) ? row.getCell(5).toString() : "";
				if (!posCode.equals("") && !itemName.equals("") && !qty.equals("") && !rate.equals("") && !billDate.equals("")) {
					dte = new Date(billDate);

					if (i == 2) {
						fromDate = dte;
						toDate = dte;
					}
					if (dte.compareTo(fromDate) < 0) {
						fromDate = dte;
					}
					if (dte.compareTo(toDate) > 0) {
						toDate = dte;
					}

					billDate = sf.format(dte);
					// System.out.println(sf.parse(billDate));
					if (!posCode.equals("") && !itemName.equals("") && !qty.equals("") && !rate.equals("")) {
						objSalesDtl.setStrPOSCode(posCode);
						objSalesDtl.setStrPOSItemCode(itemCode);
						objSalesDtl.setStrPOSItemName(itemName);
						objSalesDtl.setDblQuantity(Double.parseDouble(qty));
						objSalesDtl.setDblRate(Double.parseDouble(rate));
						objSalesDtl.setDteBillDate(billDate);
						listPOSSalesDtl.add(objSalesDtl);
						sqlValues += " ('" + clientCode + "','" + itemCode + "','" + qty + "','" + rate + "','" + billDate + "','" + posCode + "','" + itemName + "','',''),";
					}

				}
			}

			if (listPOSSalesDtl.size() > 0) {
				sqlValues = sqlValues.substring(0, sqlValues.length() - 1);
				objPOSLinkUpService.funExecute(sql + sqlValues);
				listPOSSalelist.add(listPOSSalesDtl);
				listPOSSalelist.add(sf2.format(fromDate));
				listPOSSalelist.add(sf2.format(toDate));
				System.out.println("fromDate==" + sf2.format(fromDate) + "---------" + "ToDate==" + sf2.format(toDate));
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			List list = new ArrayList<>();
			list.add("Invalid Excel File");
			list.add("Invalid Entry In Row No." + RowCount + " and Product Code " + prodCode + " ");
			return list;
		}
		return listPOSSalelist;
	}

	/*
	 * End WebStock Import
	 */

}
