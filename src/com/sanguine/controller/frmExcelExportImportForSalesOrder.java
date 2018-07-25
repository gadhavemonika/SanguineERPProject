package com.sanguine.controller;

import java.util.ArrayList;
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

import com.sanguine.crm.bean.clsSalesOrderBean;
import com.sanguine.crm.model.clsSalesOrderDtl;
import com.sanguine.model.clsOpeningStkDtl;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;

@Controller
public class frmExcelExportImportForSalesOrder {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	final static Logger logger = Logger.getLogger(clsExcelExportImportController.class);

	@RequestMapping(value = "/frmExcelExportImportForSalesOrder", method = RequestMethod.GET)
	public ModelAndView funOpenForm(HttpServletRequest request) {
		return new ModelAndView("frmXlsExportImportForSalesOrder");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/funSalesOrderProductExport", method = RequestMethod.GET)
	public ModelAndView funSalesOrderProductExport(HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List list = new ArrayList<>();
		List prodListForSalesOrder = new ArrayList();
		String strGCodes = "";
		if (request.getParameter("strSubGroupCode") != null) {
			String tempSGCodes[] = request.getParameter("strSubGroupCode").split(",");
			for (int i = 0; i < tempSGCodes.length; i++) {
				if (strGCodes.length() > 0) {
					strGCodes = strGCodes + " or a.strSGCode='" + tempSGCodes[i] + "' ";
				} else {
					strGCodes = "a.strSGCode='" + tempSGCodes[i] + "' ";

				}
			}
		}
		String header = "Product Code,Product Name,Qty";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);

		String sql = " select a.strProdCode,a.strProdName,'' as Qty from tblproductmaster a " + " where a.strProdType='Produced' and a.strClientCode='" + clientCode + "' ";
		if (strGCodes.length() > 0) {
			sql += " and " + strGCodes;
		}
		list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			Object[] ob = (Object[]) list.get(i);
			List DataList = new ArrayList<>();
			DataList.add(ob[0]);
			DataList.add(ob[1]);
			DataList.add(ob[2]);
			prodListForSalesOrder.add(DataList);
		}
		ExportList.add(prodListForSalesOrder);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/ExcelExportImportSales", method = RequestMethod.POST)
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

			case "frmSalesOrder":
				list = funSalesOrderProductsData(worksheet, request);
				break;

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List funSalesOrderProductsData(HSSFSheet worksheet, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<clsSalesOrderDtl> listSODtl = new ArrayList<clsSalesOrderDtl>();
		int RowCount = 0;
		String prodCode = "";
		try {

			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the Candidate Model

				clsSalesOrderDtl salesDtl = new clsSalesOrderDtl();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);

				// Sets the Read data to the model class
				RowCount = row.getRowNum();
				prodCode = row.getCell(0).getStringCellValue();
				double qty = 0;

				Cell c = row.getCell(2);
				if (c != null) // && c.getStringCellValue()!= "")
				{
					if (row.getCell(2).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						clsProductMasterModel prodModel = objProductMasterService.funGetObject(row.getCell(0).getStringCellValue(), clientCode);
						salesDtl.setStrProdCode(row.getCell(0).getStringCellValue());
						salesDtl.setStrProdName(row.getCell(1).getStringCellValue());

						qty = row.getCell(2).getNumericCellValue();
						salesDtl.setDblQty(qty);
						salesDtl.setDblAcceptQty(qty);

						salesDtl.setDblUnitPrice(prodModel.getDblMRP());
						salesDtl.setDblWeight(prodModel.getDblWeight());
						salesDtl.setStrRemarks("");
						salesDtl.setStrProdChar("");
						salesDtl.setStrUOM(prodModel.getStrUOM());

						listSODtl.add(salesDtl);
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
		return listSODtl;
	}

}
