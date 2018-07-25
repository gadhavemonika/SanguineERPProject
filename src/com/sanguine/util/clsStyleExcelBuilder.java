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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class clsStyleExcelBuilder extends AbstractExcelView {

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container

		List Datalist = (List) model.get("sheetlist");
		String fileRptName = Datalist.get(0).toString();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileRptName + ".xls");

		List listBlankRow = new ArrayList();

		String[] titleRptName = (String[]) Datalist.get(1);

		String[] titleList = (String[]) Datalist.get(2);
		listBlankRow = (List) Datalist.get(3);
		String[] HeaderList = (String[]) Datalist.get(4);

		List listSheet = new ArrayList();

		try {
			listSheet = (List) Datalist.get(5);
		} catch (Exception e) {
			listSheet = new ArrayList();
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

		HSSFRow rptName = sheet.createRow(0);
		for (int rowCount = 0; rowCount < titleRptName.length; rowCount++) {
			rptName.createCell(rowCount).setCellValue(titleRptName[rowCount].toString());
			rptName.getCell(rowCount).setCellStyle(style);
		}

		HSSFRow title = sheet.createRow(1);
		for (int rowCount = 0; rowCount < titleList.length; rowCount++) {
			title.createCell(rowCount).setCellValue(titleList[rowCount].toString());
			title.getCell(rowCount).setCellStyle(style);
		}

		// create header row
		HSSFRow header = sheet.createRow(3);
		for (int rowCount = 0; rowCount < HeaderList.length; rowCount++) {
			header.createCell(rowCount).setCellValue(HeaderList[rowCount].toString());
			header.getCell(rowCount).setCellStyle(style);
		}

		// create data rows
		// aRow is add Row
		int ColrowCount = 4;
		for (int rowCount = 0; rowCount < listSheet.size(); rowCount++) {
			HSSFRow aRow = sheet.createRow(ColrowCount++);
			List arrObj = (List) listSheet.get(rowCount);

			if (arrObj.get(0).toString().contains("#")) {

				for (int Count = 0; Count < arrObj.size(); Count++) {
					if (null != arrObj.get(Count) && arrObj.get(Count).toString().length() > 0) {

						if (isNumeric(arrObj.get(Count).toString())) {
							aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
							aRow.getCell(Count).setCellStyle(style);
						} else {

							if (arrObj.get(Count).toString().contains("#")) {
								String subgroup = arrObj.get(Count).toString().replace("#", "");
								aRow.createCell(Count).setCellValue(subgroup);
								aRow.getCell(Count).setCellStyle(style);

							} else {
								aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
								aRow.getCell(Count).setCellStyle(style);
							}

						}
					} else {
						aRow.createCell(Count).setCellValue("");
						CellStyle style2 = workbook.createCellStyle();
						Font font2 = workbook.createFont();
						font2.setFontName("Arial");
						style2.setFillForegroundColor(HSSFColor.BLUE.index);
						style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
						font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
						font2.setColor(HSSFColor.WHITE.index);
						style2.setFont(font2);
						aRow.getCell(Count).setCellStyle(style2);
					}
				}

			} else {

				for (int Count = 0; Count < arrObj.size(); Count++) {
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

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
