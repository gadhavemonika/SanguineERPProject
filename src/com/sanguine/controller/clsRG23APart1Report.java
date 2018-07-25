package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsRG23APart1Report extends AbstractExcelView {

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmRG-23A-Part-I", method = RequestMethod.GET)
	public ModelAndView funOpenRG23APart1(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmRG-23A-Part-I_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmRG-23A-Part-I", "command", new clsReportBean());
		}
	}

	@Override
	@RequestMapping(value = "/rptRG-23A-Part-I", method = RequestMethod.GET)
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("dtFromDate").toString();
		String toDate = request.getParameter("dtToDate").toString();
		String companyName = request.getSession().getAttribute("companyName").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String locCode = request.getParameter("strLocationCode").toString();
		// String
		// locCode=request.getSession().getAttribute("locationCode").toString();
		String sql = "";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "inline;filename=FORM-RG-23A-Part-I_" + fromDate + "_To_" + toDate + "_" + userCode + ".xls");

		List listDrawDtlRow = new ArrayList();
		HSSFSheet sheet = workbook.createSheet("18062000");
		sheet.setDefaultColumnWidth(15);
		// /Bold Font
		Font font = workbook.createFont();
		font.setFontName("Arial");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.BLACK.index);
		// /Normal Font

		Font normlFont = workbook.createFont();
		normlFont.setFontName("Arial");
		normlFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		normlFont.setColor(HSSFColor.BLACK.index);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);

		CellStyle styleNormlfont = workbook.createCellStyle();
		styleNormlfont.setFont(normlFont);

		// /////Create Style for set a text in middle of cell and table border
		CellStyle styleOfAligment = workbook.createCellStyle();
		styleOfAligment.setFont(font);
		styleOfAligment.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleOfAligment.setAlignment(style.ALIGN_CENTER);

		CellStyle styleOfAligmentNormalFont = workbook.createCellStyle();
		styleOfAligmentNormalFont.setFont(normlFont);
		styleOfAligmentNormalFont.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleOfAligmentNormalFont.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleOfAligmentNormalFont.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleOfAligmentNormalFont.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleOfAligmentNormalFont.setAlignment(style.ALIGN_CENTER);

		// ////First Row////////////////////
		String firstRow = ", , , , ,FROM R.G.-23-A Part-I ";
		HSSFRow header = sheet.createRow(0);
		String[] data = firstRow.split(",");
		// ExportList.add(ExcelHeader);
		for (int i = 0; i < data.length; i++) {
			header.createCell(i).setCellValue(data[i]);

			header.getCell(i).setCellStyle(style);
		}

		sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to4 and row
													// 0
				0, // first row (0-based)
				0, // last row (0-based)
				0, // first column (0-based)
				4 // last column (0-based)
		));

		// ///////// SecondRow/////////
		String scndRow = ",,,,Central Excise Series No.55-GG FROM R.G. 23-A PART-I";
		HSSFRow secndRowHeader = sheet.createRow(1);
		data = scndRow.split(",");
		for (int i = 0; i < data.length; i++) {
			secndRowHeader.createCell(i).setCellValue(data[i]);
			if (i == 4) {
				secndRowHeader.getCell(i).setCellStyle(style);
			} else {
				secndRowHeader.getCell(i).setCellStyle(styleNormlfont);
			}
		}

		sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to2 and row
													// 1
				1, // first row (0-based)
				1, // last row (0-based)
				0, // first column (0-based)
				2 // last column (0-based)
		));
		// ////Third Row////////
		String thrdRow = ", , , ,Stock Account of 'inputs' for use in or in relation to the Manufacture of Final Product of (RULES 57 - A ),";
		HSSFRow ThirdRowheader = sheet.createRow(2);
		data = thrdRow.split(",");
		for (int i = 0; i < data.length; i++) {
			ThirdRowheader.createCell(i).setCellValue(data[i]);
			if (i == 4) {
				ThirdRowheader.getCell(i).setCellStyle(style);
			} else {
				ThirdRowheader.getCell(i).setCellStyle(styleNormlfont);
			}

		}
		sheet.addMergedRegion(new CellRangeAddress(// //Merging col 0to1 and row
													// 2
				2, // first row (0-based)
				2, // last row (0-based)
				0, // first column (0-based)
				1 // last column (0-based)
		));
		// //Fourth Row///
		// String
		// fouthRow="Stock Account of 'inputs' for use or in relation to the manufacture of final' final Product, ,";
		// String fouthRow=", ,";
		// HSSFRow fouthRowHeader = sheet.createRow(3);
		// data=fouthRow.split(",");
		// for(int i=0;i<data.length;i++)
		// {
		// fouthRowHeader.createCell(i+4).setCellValue(data[i]);
		// if(i==0)
		// {
		// fouthRowHeader.getCell(i+4).setCellStyle(style);
		// }else{
		// fouthRowHeader.getCell(i+4).setCellStyle(styleNormlfont);
		// }
		//
		// }

		// ///////Table Header////
		String tableData = "Sr. no.,Date,Description of inputs received,Qty Rec.,Particutor or Challan ARI/Other approved Documents Bill of ,Name and address of the manufacture / importer stock -stock-yard from whom the inputs received ,ECC No of Suppliers , Range & Division Custom House from Whose jursidiction the inputs received ,Issued for use inor in relation to the manufacture of final ,, Issued for clearance as such , , , ,Balance quantity in stock ,Central Excise Officers initials,Remarks";
		HSSFRow tableHeader = sheet.createRow(4);
		String[] tblfirstRowData = tableData.split(",");
		for (int i = 0; i < tblfirstRowData.length; i++) {
			tableHeader.createCell(i).setCellValue(tblfirstRowData[i]);
			tableHeader.getCell(i).setCellStyle(styleOfAligment);
		}

		// ///////////table header 2nd row/////////
		String tblscndRow = " , , , , , , , , , , On payment of duty, ,Otherwise, , , , ";
		tableHeader = sheet.createRow(5);
		data = tblscndRow.split(",");
		for (int i = 0; i < data.length; i++) {
			tableHeader.createCell(i).setCellValue(data[i]);
			tableHeader.getCell(i).setCellStyle(styleOfAligment);
		}

		// ///////////table header 3rd row/////////
		String tblthirdRow = " , , , , , , , ,Chit No & Date, Qty,ARI/Challan No. & Date,Qnty ,Document particulors ,Qty , , , ";
		tableHeader = sheet.createRow(6);
		data = tblthirdRow.split(",");
		for (int i = 0; i < data.length; i++) {
			tableHeader.createCell(i).setCellValue(data[i]);

			tableHeader.getCell(i).setCellStyle(styleOfAligment);
		}
		int col = 0;
		// for(int i=0;i<4;i++){

		// sheet.addMergedRegion(new CellRangeAddress(////Merging col 4to9 and
		// row 8
		// 8, //first row (0-based)
		// 8, //last row (0-based)
		// 4+col, //first column (0-based)
		// 5+col //last column (0-based)
		//
		// ));
		// col=col+2;
		// }
		//

		String tblfifthRow = "1,2,3,4,5,6,6(a),7,8,9,10,11,12,13,14,15,16";
		tableHeader = sheet.createRow(7);
		String[] dataNo = tblfifthRow.split(",");
		for (int i = 0; i < dataNo.length; i++) {
			tableHeader.createCell(i).setCellValue(dataNo[i]);
			tableHeader.getCell(i).setCellStyle(styleOfAligment);
		}

		// /////////////Start Merging First row of table header////////
		for (int i = 0; i < 8; i++)// /////////
		{
			sheet.addMergedRegion(new CellRangeAddress(// //Merging row 4to6 and
														// col 1,2,2,4,5,6,7,8
														// Table header
					4, // first row (0-based)
					6, // last row (0-based)
					i, // first column (0-based)
					i // last column (0-based)
			));
		}

		sheet.addMergedRegion(new CellRangeAddress(// /Merging col 8to 9 and row
													// no is 4
				4, // first row (0-based)
				5, // last row (0-based)
				8, // first column (0-based)
				9 // last column (0-based)
		));

		sheet.addMergedRegion(new CellRangeAddress(// /Merging col 10to 13 and
													// row no is 4
				4, // first row (0-based)
				4, // last row (0-based)
				10, // first column (0-based)
				13 // last column (0-based)
		));

		for (int i = 14; i < 17; i++)// /////////
		{
			sheet.addMergedRegion(new CellRangeAddress(// //Merging row 4to6 and
														// col 14,15,16 Table
														// header
					4, // first row (0-based)
					6, // last row (0-based)
					i, // first column (0-based)
					i // last column (0-based)
			));
		}

		// ////////End Merging First row of table header///

		// ////////strt Merging scnd row of table header///
		sheet.addMergedRegion(new CellRangeAddress(// Merging col 10th to 11 and
													// row no 5
				5, // first row (0-based)
				5, // last row (0-based)
				10, // first column (0-based)
				11 // last column (0-based)
		));

		sheet.addMergedRegion(new CellRangeAddress(// Merging col 12th to 13 and
													// row no 5
				5, // first row (0-based)
				5, // last row (0-based)
				12, // first column (0-based)
				13 // last column (0-based)
		));

		List dataList = new ArrayList();
		sql = " select date(b.dtGRNDate),a.strProdCode,DATE_FORMAT(b.dtGRNDate,'%d %b %y'),c.strProdName,sum(a.dblQty),b.strGRNCode,d.strPName,d.strECCNo, " + "  CONCAT(d.strRange,',',d.strDivision) as rangeNdivision " + "from tblgrndtl a ,tblgrnhd b,tblproductmaster c ,tblpartymaster d " + " where a.strGRNCode=b.strGRNCode and a.strProdCode=c.strProdCode " + " and b.strSuppCode=d.strPCode and  "
				+ " date(b.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' " + " and b.strLocCode='" + locCode + "' " + "  group by a.strProdCode,b.dtGRNDate " + " order by b.dtGRNDate ";
		List listGRN = objGlobalFunctionsService.funGetDataList(sql, "sql");

		sql = " select date(b.dtMISDate),a.strProdCode,a.strMISCode,sum(a.dblQty) " + "  from tblmisdtl a,tblmishd b " + " where a.strMISCode=b.strMISCode and " + " date(b.dtMISDate) between '" + fromDate + "' and '" + toDate + "' " + " and b.strLocFrom='" + locCode + "' " + "  group by a.strMISCode  ,b.dtMISDate,a.strProdCode order by b.dtMISDate,a.strProdCode,a.strMISCode;  ";
		List listMIS = objGlobalFunctionsService.funGetDataList(sql, "sql");

		HashMap<String, String> hmMIS = new HashMap<String, String>();

		String misOldDate = "";
		String misOldProdCode = "";
		double misOldQty = 0.00;
		String preMISCode = "";
		for (int j = 0; j < listMIS.size(); j++) {
			Object[] objMis = (Object[]) listMIS.get(j);
			String misDate = objMis[0].toString();
			String misProdCode = objMis[1].toString();
			double misQty = Double.parseDouble(objMis[3].toString());
			String misCode = objMis[2].toString();
			;
			if (misOldDate.equals(misDate) && misOldProdCode.equals(misProdCode)) {
				misOldQty += misQty;
				preMISCode += misCode + ",";
				misOldDate = misDate;
				misOldProdCode = misProdCode;

			} else {
				misOldQty = misQty;
				preMISCode = misCode + ",";
				misOldDate = misDate;
				misOldProdCode = misProdCode;
			}
			hmMIS.put(misProdCode + "!" + misDate, misOldQty + "!" + preMISCode);
		}

		int srNo = 1;
		String preGRNCode = "";
		String preProdName = "";
		String preGRNDate = "";
		double preGrnQty = 0.00;
		for (int i = 0; i < listGRN.size(); i++) {

			Object[] objGrn = (Object[]) listGRN.get(i);
			String grnDate = objGrn[0].toString();
			String grnProdCode = objGrn[1].toString();
			double grnQty = Double.parseDouble(objGrn[4].toString());

			String misData = hmMIS.get(grnProdCode + "!" + grnDate);

			if (misData != null) {
				String mis[] = misData.split("!");
				String misProdCode = mis[1].toString();
				double misQty = Double.parseDouble(mis[0].toString());

				double bal = grnQty - misQty;
				if (bal < 0) {
					bal = 0.00;
				}

				List listRow = new ArrayList();
				listRow.add(srNo);
				listRow.add(objGrn[2].toString());
				listRow.add(objGrn[3].toString());
				listRow.add(objGrn[4].toString());
				listRow.add(objGrn[5].toString());
				listRow.add(objGrn[6].toString());
				listRow.add(objGrn[7].toString());
				listRow.add(objGrn[8].toString());
				listRow.add(misProdCode);
				listRow.add(misQty);
				listRow.add("");
				listRow.add("");
				listRow.add("");
				listRow.add("");
				listRow.add(bal);//
				listRow.add("");
				listRow.add("");

				srNo++;

				listDrawDtlRow.add(listRow);

			}

			// for(int j=0 ;j<listMIS.size();j++)
			// {
			// Object[] objMis= (Object[]) listMIS.get(j);
			// String misDate=objMis[0].toString();
			// String misProdCode=objMis[1].toString();
			// double misQty =Double.parseDouble(objMis[3].toString());
			// if(grnDate.equals(misDate))
			// {
			// if(grnProdCode.equals(grnProdCode))
			// {
			// double bal=grnQty-misQty;
			// if(bal<0)
			// {
			// bal=0.00;
			// }
			//
			//
			//
			// List listRow = new ArrayList();
			// listRow.add(srNo);
			// listRow.add(objGrn[2].toString());
			// listRow.add(objGrn[3].toString());
			// listRow.add(objGrn[4].toString());
			// listRow.add(objGrn[5].toString());
			// listRow.add(objGrn[6].toString());
			// listRow.add(objGrn[7].toString());
			// listRow.add(objGrn[8].toString());
			// listRow.add(objMis[2].toString());
			// listRow.add(objMis[3].toString());
			// listRow.add("");
			// listRow.add("");
			// listRow.add("");
			// listRow.add("");
			// listRow.add(bal);//
			// listRow.add("");
			// listRow.add("");
			//
			// srNo++;
			//
			// listDrawDtlRow.add(listRow);
			// preGRNDate=objGrn[2].toString();
			// preProdName=objGrn[3].toString();
			// preGRNCode=objGrn[4].toString();
			// preGrnQty=grnQty;
			//
			// }
			// }
			// }

		}

		// /Start Dtl data of Table

		int ColrowCount = 8;// starting row no of Dtl data
		for (int rowCount = 0; rowCount < listDrawDtlRow.size(); rowCount++) {
			HSSFRow aRow = sheet.createRow(ColrowCount++);
			List arrObj = (List) listDrawDtlRow.get(rowCount);
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

		//
		workbook.write(response.getOutputStream());

	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional
												// '-' and decimal.
	}

}
