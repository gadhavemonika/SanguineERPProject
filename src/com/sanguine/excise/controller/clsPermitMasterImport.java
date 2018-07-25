package com.sanguine.excise.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.SimpleDateFormat;
import com.sanguine.excise.model.clsPermitMasterModel;
import com.sanguine.excise.service.clsPermitMasterImportService;
import com.sanguine.service.clsGlobalFunctionsService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class clsPermitMasterImport {

	@Autowired
	private clsPermitMasterImportService objMasterDataImport;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open ExcisePOSDataExportImport
	@RequestMapping(value = "/frmMasterDataImport", method = RequestMethod.GET)
	public ModelAndView funOpenMasterDataForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMasterDataImport_1");
		} else {
			return new ModelAndView("frmMasterDataImport_1");
		}
	}

	@RequestMapping(value = "/MasterDataExport", method = RequestMethod.GET)
	public ModelAndView funExcisePOSDataExcelExport(HttpServletRequest request) {
		String header = "POS Item Code, POS Item Name,Quantity,Rate,POS Code,Bill Date,Client Code";
		List ExportList = new ArrayList();
		String[] ExcelHeader = header.split(",");
		ExportList.add(ExcelHeader);
		return new ModelAndView("excelView", "stocklist", ExportList);
	}

	// Save or Update ExcisePOSDataExportImport
	@SuppressWarnings("resource")
	@RequestMapping(value = "/saveMasterDataImport", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> funAddUpdate(@RequestParam("file") MultipartFile excelfile, HttpServletRequest request, HttpServletResponse res) {

		// String
		// clientCode=request.getSession().getAttribute("clientCode").toString();
		HashMap<String, String> success = new HashMap<String, String>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(excelfile.getInputStream());
			HSSFSheet worksheet = workbook.getSheetAt(0);
			int i = 1;
			while (i <= worksheet.getLastRowNum()) {
				clsPermitMasterModel objExMasterData = new clsPermitMasterModel();
				HSSFRow row = worksheet.getRow(i++);

				objExMasterData.setStrPermitCode((row.getCell(0).getStringCellValue()));
				objExMasterData.setStrPermitName((row.getCell(1).getStringCellValue()));
				objExMasterData.setStrPermitNo(row.getCell(2).getStringCellValue());
				objExMasterData.setStrPermitPlace((row.getCell(3).getStringCellValue()));
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String issueDate = dt.format((row.getCell(4).getDateCellValue()));
				String expDate = dt.format((row.getCell(5).getDateCellValue()));
				objExMasterData.setDtePermitIssue(issueDate);
				objExMasterData.setDtePermitExp(expDate);
				objExMasterData.setStrPermitStatus(row.getCell(6).getStringCellValue());
				String editedDate = dt.format((row.getCell(7).getDateCellValue()));
				objExMasterData.setDtePermitEdited(editedDate);
				objExMasterData.setStrPermitUserCode("" + row.getCell(8).getNumericCellValue());
				objMasterDataImport.funAddUpdatePermitMaster(objExMasterData);
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
