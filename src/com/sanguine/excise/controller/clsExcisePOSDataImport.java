package com.sanguine.excise.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.excise.model.clsExcisePOSSaleModel;
import com.sanguine.excise.service.clsExcisePOSSaleService;

@Controller
public class clsExcisePOSDataImport {

	@Autowired
	private clsExcisePOSSaleService objclsExcisePOSSalesDtlService;

	@RequestMapping(value = "/frmExcisePOSDataExportImport", method = RequestMethod.GET)
	public ModelAndView funOpenExcisePOSDataExcelExportImportForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExcisePOSDataExportImport_1");
		} else {
			return new ModelAndView("frmExcisePOSDataExportImport");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ExcisePOSDataExport", method = RequestMethod.GET)
	public ModelAndView funExcisePOSDataExcelExport(HttpServletRequest request) {
		String header = "POS Item Code, POS Item Name,Quantity,Rate,POS Code,Bill Date,Client Code";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	// Save or Update ExcisePOSDataExportImport
	@SuppressWarnings("resource")
	@RequestMapping(value = "/saveExcisePOSDataExportImport", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> funAddUpdate(@RequestParam("file") MultipartFile excelfile, HttpServletRequest request, HttpServletResponse res) {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		HashMap<String, String> success = new HashMap<String, String>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
			HSSFSheet worksheet = workbook.getSheetAt(0);
			int i = 1;
			// System.out.println(worksheet.getLastRowNum());
			while (i <= worksheet.getLastRowNum()) {

				clsExcisePOSSaleModel objExPOSData = new clsExcisePOSSaleModel();
				HSSFRow row = worksheet.getRow(i++);
				String posItemCode;
				try {
					posItemCode = row.getCell(0).getStringCellValue();

				} catch (Exception e) {
					posItemCode = "";
				}

				if (!posItemCode.equals("") || !posItemCode.isEmpty()) {
					String Qty = String.valueOf(row.getCell(2).getNumericCellValue());
					String qty[] = Qty.split("\\.");
					// if(row.get)
					if (!Qty.equals("")) {
						objExPOSData.setStrPOSItemCode((row.getCell(0).getStringCellValue()));
						objExPOSData.setStrPOSItemName((row.getCell(1).getStringCellValue()));
						objExPOSData.setIntQuantity(Long.parseLong(qty[0]));
						objExPOSData.setDblRate((row.getCell(3).getNumericCellValue()));
						objExPOSData.setStrPOSCode((row.getCell(4).getStringCellValue()));
						SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String billDate = dt.format((row.getCell(5).getDateCellValue()));
						objExPOSData.setDteBillDate(billDate);
						objExPOSData.setStrClientCode(clientCode);
						objExPOSData.setStrBrandCode(" ");
						objExPOSData.setStrBillNo(" ");

						objclsExcisePOSSalesDtlService.funAddUpdate(objExPOSData);
						// System.out.println(i);
						// System.out.println(row.getCell(0).getStringCellValue());
						// System.out.println(row.getCell(1).getStringCellValue());
					}
				}
			}
			success.put("success", "true");
			success.put("successMessage", "Data Added Successfully");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
