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
public class clsExcelBuilderForAccountReports extends AbstractExcelView {

	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container

		List listExcelData = (List) model.get("excelDataList");
		
		String reportName = (String) listExcelData.get(0);
		List listHeaderLevelInfo=(List)listExcelData.get(1);
		List listdate = new ArrayList();
		try {
			listdate = (List) listExcelData.get(2);
		} catch (Exception e) {
			listdate = new ArrayList();
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + reportName.trim() + ".xls");

		List listData = new ArrayList();
		listData=(List)listExcelData.get(3);
		
	// create a new Excel sheet
		HSSFSheet sheet = workbook.createSheet("Sheet");
		sheet.setDefaultColumnWidth(20);

	// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		
		CellStyle style2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Arial");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setFont(font2);
		
		
		// create header row
		
		int excelRowCount=1;

		for (int rowCnt = 0; rowCnt < listHeaderLevelInfo.size(); rowCnt++) {
			
			HSSFRow data = sheet.createRow(excelRowCount);
			excelRowCount++;
			data.createCell(4).setCellValue(listHeaderLevelInfo.get(rowCnt).toString());
			data.getCell(4).setCellStyle(style);
		}
		
		HSSFRow fittler = sheet.createRow(excelRowCount);
		excelRowCount++;
		for (int rowfitter = 0; rowfitter < listdate.size(); rowfitter++) {
			fittler.createCell(rowfitter).setCellValue(listdate.get(rowfitter).toString());
			fittler.getCell(rowfitter).setCellStyle(style2);
		}
				
		for (int rowtitile = 0; rowtitile < 1; rowtitile++) {
			HSSFRow blank = sheet.createRow(excelRowCount);
			excelRowCount++;
			blank.createCell(rowtitile).setCellValue("");
			// titile.getCell(rowtitile).setCellStyle(style);
		}
 
	// create data rows
	// aRow is add Row
		for (int rowCount = 0; rowCount < listData.size(); rowCount++) {
			
			List list = (List) listData.get(rowCount);
			HSSFRow blankRow = sheet.createRow(excelRowCount);
			excelRowCount++;
			blankRow.createCell(0).setCellValue("");
			
			for (int cnt = 0; cnt < list.size(); cnt++) {
				List list2=(List)list.get(cnt);
				HSSFRow aRow = sheet.createRow(excelRowCount);
				excelRowCount++;
				
				for(int j=0;j<list2.size();j++)
				{
					String rowData=(String)list2.get(j);
					if (isNumeric(rowData)) {
						aRow.createCell(j).setCellValue(Double.parseDouble(rowData));
					} else {
						aRow.createCell(j).setCellValue(rowData);
					}
					if(list2.size()==1 && cnt==0)
						aRow.getCell(0).setCellStyle(style2);
				}
			}
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
