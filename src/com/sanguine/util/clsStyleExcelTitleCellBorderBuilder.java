package com.sanguine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class clsStyleExcelTitleCellBorderBuilder extends AbstractExcelView {

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container

		List Datalist = (List) model.get("sheetlist");

		String repotfileName = (String) Datalist.get(0);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + repotfileName + ".xls");

		List tilte1 = (List) Datalist.get(1);
		List tilte2 = (List) Datalist.get(2);

		String[] header = (String[]) Datalist.get(3);

		List listStock = new ArrayList();
		try {
			listStock = (List) Datalist.get(4);
		} catch (Exception e) {
			listStock = new ArrayList();
		}

		// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Sheet");
		sheet.setDefaultColumnWidth(20);

		CellStyle styleOfAligment = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Arial");
		styleOfAligment.setFont(font2);
		styleOfAligment.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// styleOfAligment.setAlignment(styleOfAligment.ALIGN_CENTER);

		CellStyle styleTitle = workbook.createCellStyle();
		Font font3 = workbook.createFont();
		font3.setFontName("Arial");
		font3.setColor(HSSFColor.BLACK.index);
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font3.setFontHeightInPoints((short) 12);
		styleTitle.setFont(font3);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setAlignment(style.ALIGN_CENTER);

		HSSFRow tit1 = sheet.createRow(0);
		for (int rowCount = 0; rowCount < tilte1.size(); rowCount++) {
			tit1.createCell(rowCount).setCellValue(tilte1.get(rowCount).toString());
			tit1.getCell(rowCount).setCellStyle(styleTitle);
		}

		HSSFRow tit2 = sheet.createRow(1);
		for (int rowCount = 0; rowCount < tilte2.size(); rowCount++) {
			tit2.createCell(rowCount).setCellValue(tilte2.get(rowCount).toString());

		}

		// create header row
		HSSFRow headertable = sheet.createRow(2);
		for (int rowCount = 0; rowCount < header.length; rowCount++) {
			headertable.createCell(rowCount).setCellValue(header[rowCount].toString());
			headertable.getCell(rowCount).setCellStyle(style);
		}

		// create data rows
		// aRow is add Row
		int ColrowCount = 3;
		for (int rowCount = 0; rowCount < listStock.size(); rowCount++) {
			HSSFRow aRow = sheet.createRow(ColrowCount++);
			List arrObj = (List) listStock.get(rowCount);
			for (int Count = 0; Count < arrObj.size(); Count++) {
				if (null != arrObj.get(Count) && arrObj.get(Count).toString().length() > 0) {

					if (isNumeric(arrObj.get(Count).toString())) {
						aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
						aRow.getCell(Count).setCellStyle(styleOfAligment);
					} else {
						aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
						aRow.getCell(Count).setCellStyle(styleOfAligment);
					}
				} else {
					aRow.createCell(Count).setCellValue("");
					aRow.getCell(Count).setCellStyle(styleOfAligment);
				}
			}

		}

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
