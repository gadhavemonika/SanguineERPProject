package com.sanguine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.document.AbstractExcelView;

@Controller
public class clsExcelBuilderFromDateTodateCompanyNameWithReportName extends AbstractExcelView {

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container

		List Datalist = (List) model.get("listFromDateTodateWithReportName");

		String reportName = (String) Datalist.get(0);

		
		List tilte1 = (List) Datalist.get(1);
		List tilte2 = (List) Datalist.get(2);
		List tilte3 = (List) Datalist.get(3);

		List listdate = new ArrayList();
		try {
			listdate = (List) Datalist.get(4);
		} catch (Exception e) {
			listdate = new ArrayList();
		}

		String[] HeaderList = (String[]) Datalist.get(5);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName.trim() + ".xls");

		List listStock = new ArrayList();
		try {
			listStock = (List) Datalist.get(6);
		} catch (Exception e) {
			listStock = new ArrayList();
		}

		
		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Sheet");
		sheet.setDefaultColumnWidth(20);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		// create header row
		HSSFRow titile = sheet.createRow(0);

		for (int rowtitile = 0; rowtitile < tilte1.size(); rowtitile++) {
			titile.createCell(rowtitile).setCellValue(tilte1.get(rowtitile).toString());
			titile.getCell(rowtitile).setCellStyle(style);
		}
		
		HSSFRow tit2 = sheet.createRow(1);
		for (int rowtitile = 0; rowtitile < tilte2.size(); rowtitile++) {
			tit2.createCell(rowtitile).setCellValue(tilte2.get(rowtitile).toString());
			tit2.getCell(rowtitile).setCellStyle(style);
		}
		
		HSSFRow tit3 = sheet.createRow(2);
		for (int rowtitile = 0; rowtitile < tilte3.size(); rowtitile++) {
			tit3.createCell(rowtitile).setCellValue(tilte3.get(rowtitile).toString());
			tit3.getCell(rowtitile).setCellStyle(style);
		}

		
		HSSFRow fittler = sheet.createRow(3);
		for (int rowfitter = 0; rowfitter < listdate.size(); rowfitter++) {
			fittler.createCell(rowfitter).setCellValue(listdate.get(rowfitter).toString());
			fittler.getCell(rowfitter).setCellStyle(style);
		}
		HSSFRow blank = sheet.createRow(4);
		for (int rowtitile = 0; rowtitile < 1; rowtitile++) {
			blank.createCell(rowtitile).setCellValue("");
			// titile.getCell(rowtitile).setCellStyle(style);
		}

		HSSFRow header = sheet.createRow(5);

		for (int rowCount = 0; rowCount < HeaderList.length; rowCount++) {
			header.createCell(rowCount).setCellValue(HeaderList[rowCount].toString());
			header.getCell(rowCount).setCellStyle(style);
		}

		// create data rows
		// aRow is add Row
		int ColrowCount = 6;
		for (int rowCount = 0; rowCount < listStock.size(); rowCount++) {
			HSSFRow aRow = sheet.createRow(ColrowCount++);
			List arrObj = (List) listStock.get(rowCount);
			for (int Count = 0; Count < arrObj.size(); Count++) {
				System.out.print(arrObj.get(Count));
				if (null != arrObj.get(Count) && arrObj.get(Count).toString().length() > 0) {

					if (isNumeric(arrObj.get(Count).toString())) {
						aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
					} else {
						aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
					}
				} else {
					aRow.createCell(Count).setCellValue("");
				}
			}

		}
		
		

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
