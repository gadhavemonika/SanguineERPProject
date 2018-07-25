package com.sanguine.crm.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsStockFlashService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsConsolidateCustomerAvgSOExcelController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	private clsGlobalFunctions objGlobal;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmConsolidateCustomerWiseAvgSalesOrder", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List<String> strAgainst = new ArrayList<>();
		strAgainst.add("Direct");
		strAgainst.add("Delivery Challan");
		strAgainst.add("Sales Order");
		model.put("againstList", strAgainst);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmConsolidateCustomerWiseAvgSalesOrder_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmConsolidateCustomerWiseAvgSalesOrder", "command", new clsReportBean());
		}
	}

	@RequestMapping(value = "/consolidateCustomerAvgSOExcel", method = RequestMethod.GET)
	public ModelAndView loadExcel(@ModelAttribute("command") clsReportBean objBean, HttpServletResponse resp, HttpServletRequest req) {

		String locCode = req.getSession().getAttribute("locationCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String dayfWeek = req.getParameter("strWeekDay").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];

		String dteCurrDateTime = objGlobal.funGetCurrentDateTime("yyyy-MM-dd");
		String[] dteCurrDate = dteCurrDateTime.split(" ");
		objGlobal.funInvokeStockFlash(startDate, locCode, dteCurrDate[0], dteCurrDate[0], clientCode, userCode, "Both", req, resp);

		String dteFromDate = objBean.getDteFromDate();
		String dteToDate = objBean.getDteToDate();

		String repeortfileName = "DailyProduction" + "_" + dteFromDate + "_To_" + dteToDate + "_" + userCode;
		repeortfileName = repeortfileName.replaceAll(" ", "");

		// long diffDay = funDayDifference(dteFromDate,dteToDate);

		String dayNameAndDiffDay = funCountWeekDay(dteFromDate, dteToDate, dayfWeek);
		String tempdayNameAndDiffDay[] = dayNameAndDiffDay.split("#");
		String dayName = tempdayNameAndDiffDay[0];
		long diffDay = Long.parseLong(tempdayNameAndDiffDay[1]);
		// long diffDay = funCountWeekDay(dteFromDate,dteToDate, dayfWeek);

		if (diffDay == 0) {
			diffDay = 1;
		}

		/**
		 * @ listsqlMainData is used for comparing data and Display
		 * 
		 */

		String sqlMainData = " select c.strPName,f.strSGName,e.strProdName,sum(b.dblAcceptQty),d.dblClosingStk " + " from tblsalesorderhd a,tblsalesorderdtl b,tblpartymaster c,tblcurrentstock d,tblproductmaster e,tblsubgroupmaster f " + " where a.strSOCode=b.strSOCode and a.strStatus='Normal Order' and a.strCustCode=c.strPCode "
				+ " and b.strProdCode=d.strProdCode and b.strProdCode=e.strProdCode " + " and e.strSGCode=f.strSGCode and date(a.dteFulmtDate) " + " between '"
				+ dteFromDate
				+ "' and '"
				+ dteToDate
				+ "' "
				+ " and DAYOFWEEK(a.dteFulmtDate)="
				+ dayfWeek
				+ "   "
				+ " and d.strUserCode='"
				+ userCode
				+ "' and a.strClientCode='"
				+ clientCode
				+ "' "
				+ " and b.strClientCode='"
				+ clientCode
				+ "' and c.strClientCode='"
				+ clientCode
				+ "' "
				+ " and c.strClientCode='"
				+ clientCode
				+ "' and d.strClientCode='"
				+ clientCode
				+ "' "
				+ " and e.strClientCode='" + clientCode + "' and f.strClientCode='" + clientCode + "' " + " group by e.strProdCode,f.strSGCode,c.strPCode " + " order by c.strPName,f.strSGName,e.strProdName  ";
		List listsqlMainData = objGlobalService.funGetList(sqlMainData);

		List ExportList = new ArrayList();
		ExportList.add(repeortfileName);

		String sqlCustNameHeader = "select b.strPName,a.strCustCode from tblsalesorderhd a,tblpartymaster b " + " where a.strCustCode=b.strPCode and a.strStatus='Normal Order'  " + " and a.dteFulmtDate between '" + dteFromDate + "' and '" + dteToDate + "' " + " and a.strClientCode = '" + clientCode + "' " + " and b.strClientCode='" + clientCode + "'" + " group by a.strCustCode ";
		List listCustNameHeader = objGlobalService.funGetList(sqlCustNameHeader);

		int arrlen = 1;

		// for Title Rows

		List<String> listRptName = new ArrayList<String>();
		listRptName.add("");
		listRptName.add(" Daily Production ");
		for (int cnt = 0; cnt < listCustNameHeader.size(); cnt++) {
			listRptName.add("");
		}
		listRptName.add("");
		listRptName.add("");
		listRptName.add("");

		String[] ExcelRptName = new String[listRptName.size()];
		ExcelRptName = listRptName.toArray(ExcelRptName);
		ExportList.add(ExcelRptName);

		List<String> listtitle = new ArrayList<String>();
		listtitle.add("From Fullfillment Date");
		listtitle.add(dteFromDate.split("-")[2] + "-" + dteFromDate.split("-")[1] + "-" + dteFromDate.split("-")[0]);
		// for(int cnt=0;cnt<listCustNameHeader.size();cnt++)
		// {
		// listtitle.add("");
		// }
		int cellcnt = listCustNameHeader.size() + 3;

		listtitle.add("To Fullfillment Date");
		listtitle.add(dteToDate.split("-")[2] + "-" + dteToDate.split("-")[1] + "-" + dteToDate.split("-")[0]);
		listtitle.add("Day");
		listtitle.add(dayName);

		cellcnt = cellcnt - 4;
		for (int restCell = 0; cellcnt < cellcnt; restCell++) {
			listtitle.add("");
		}

		String[] ExcelTitle = new String[listtitle.size()];
		ExcelTitle = listtitle.toArray(ExcelTitle);

		ExportList.add(ExcelTitle);

		// for Blank Rows
		List<String> listBlank = new ArrayList<String>();
		listBlank.add("");
		listBlank.add("");
		for (int cnt = 0; cnt < listCustNameHeader.size(); cnt++) {
			listBlank.add("");
		}
		listBlank.add("");
		listBlank.add("");
		listBlank.add("");
		ExportList.add(listBlank);

		/**
		 * @ listHeader is used for Header of Excel sheet
		 * 
		 */

		List<String> listHeader = new ArrayList<String>();
		listHeader.add("Sr No."); // for Header srno
		listHeader.add("Product Name");

		for (int cnt = 0; cnt < listCustNameHeader.size(); cnt++) {
			Object[] arrObjCustNameHeader = (Object[]) listCustNameHeader.get(cnt);
			listHeader.add(arrObjCustNameHeader[0].toString());

		}
		listHeader.add("Total");
		listHeader.add("Stock");
		listHeader.add("Production Qty");
		String[] ExcelHeader = new String[listHeader.size()];
		ExcelHeader = listHeader.toArray(ExcelHeader);

		ExportList.add(ExcelHeader);

		LinkedHashMap<String, List<String>> hmSGProd = new LinkedHashMap<String, List<String>>();
		List ProdDtlList = new ArrayList<>();

		/**
		 * @ listSG is used for SG Name
		 * 
		 * @ hmSGProd hash map contain sgName as key and @ lst as SG of that
		 * Product
		 * 
		 * 
		 */

		String sqlSG = " select f.strSGName,e.strSGCode,e.strProdName,b.strProdCode " + " from tblsalesorderhd a,tblsalesorderdtl b,tblpartymaster c," + " tblcurrentstock d,tblproductmaster e,tblsubgroupmaster f " + " where a.strSOCode=b.strSOCode and a.strStatus='Normal Order' and a.strCustCode=c.strPCode and b.strProdCode=d.strProdCode "
				+ " and b.strProdCode=e.strProdCode and e.strSGCode=f.strSGCode " + " and date(a.dteFulmtDate) between '"
				+ dteFromDate
				+ "' and '"
				+ dteToDate
				+ "'  "
				+ " and d.strUserCode='"
				+ userCode
				+ "'  "
				+ " and a.strClientCode='"
				+ clientCode
				+ "' and b.strClientCode='"
				+ clientCode
				+ "' "
				+ " and c.strClientCode='"
				+ clientCode
				+ "' and c.strClientCode='"
				+ clientCode
				+ "' "
				+ "  and d.strClientCode='"
				+ clientCode
				+ "' and e.strClientCode='"
				+ clientCode
				+ "' "
				+ "  and f.strClientCode='"
				+ clientCode + "' " + "  group by e.strProdCode,f.strSGCode " + " order by f.intSortingNo,e.strProdName  ";

		List listSG = objGlobalService.funGetList(sqlSG);
		// List lst =new ArrayList<String>();
		for (int sg = 0; sg < listSG.size(); sg++) {
			Object[] arrObjSG = (Object[]) listSG.get(sg);
			String arrSG = arrObjSG[0].toString();
			if (hmSGProd.containsKey(arrSG)) {
				hmSGProd.get(arrSG).add(arrObjSG[2].toString());
			} else {
				List lst = new ArrayList<>();
				lst.add(arrObjSG[2].toString());
				hmSGProd.put(arrSG, lst);
			}

		}

		/**
		 * @ ProdDtlList is used for Export Excel Detail data In this list 1st
		 * SG name 2nd Product Name 3rd to so on as per customer (Qty of that
		 * Product which is the customers Ordered) after Qty totals Row wise of
		 * that product after totals Avg of that customer Product ordered after
		 * Avg Stock of that Product after Production = Avg Qty totals Row -
		 * Stock of that product if stk is more than Qty Totals row wise then it
		 * set Zero
		 * 
		 */

		int cnttag = 0;
		int intProdSrNo = 1;
		List listSGName = new ArrayList<String>();
		HashMap<String, Double> hmSubGrouptotal = new HashMap<String, Double>();
		for (Map.Entry<String, List<String>> entry : hmSGProd.entrySet()) {
			double[] totSGQty = new double[listCustNameHeader.size()];
			String sgName = entry.getKey();

			listSGName.add(sgName);
			List DataList = new ArrayList<>();
			List DataListSg = new ArrayList<>();

			/**
			 * First Time it Print Blank of subgroup total coloumn wise of each
			 * customer
			 */
			// Start// for subgroup Total coloumn wise of each customer

			if (cnttag == 0) {
				DataListSg.add("SUBGROUP#");
				DataListSg.add("");
			} else {
				DataListSg.add("SUBGROUP TOTAL#");
				DataListSg.add("");
			}
			int cntSGtolDisplay = 0;
			if (listSGName.size() == 1) {
				for (Object ObjcustName : listCustNameHeader) {

					DataListSg.add("");
					cntSGtolDisplay++;
				}
			} else {
				for (Object ObjcustName : listCustNameHeader) {
					Object[] arrObjCustName = (Object[]) ObjcustName;
					String custName = arrObjCustName[0].toString();
					String preSGName = sgName;
					if (listSGName.size() > 1) {
						System.out.print(listSGName.get(listSGName.size() - 2) + "==" + (listSGName.size() - 1));
						preSGName = listSGName.get(listSGName.size() - 2).toString();
					}
					String key1 = preSGName + "#" + custName;

					if (hmSubGrouptotal.containsKey(key1)) {
						double totalQty = hmSubGrouptotal.get(key1);
						DataListSg.add(totalQty);
					} else {
						DataListSg.add("");
					}
				}

			}
			DataListSg.add("");
			DataListSg.add("");
			DataListSg.add("");
			ProdDtlList.add(DataListSg);
			// End//////////////////////////////////////

			DataList.add(sgName + "#");
			// for(Object ObjcustName : listCustNameHeader)
			// {
			// Object[] arrObjCustName=(Object[]) ObjcustName;
			// String custName = arrObjCustName[0].toString();
			// String preSGName=sgName;
			// if(listSGName.size()>1)
			// {
			// System.out.print(listSGName.get(listSGName.size()-2)+"=="+(listSGName.size()-1));
			// preSGName=listSGName.get(listSGName.size()-2).toString();
			// }
			// String key1=preSGName+"#"+custName;
			//
			// if(hmSubGrouptotal.containsKey(key1))
			// {
			// double totalQty=hmSubGrouptotal.get(key1);
			// DataList.add(totalQty);
			// }
			// else
			// {
			// DataList.add("");
			// }
			// }
			for (Object ObjcustName : listCustNameHeader) {
				DataList.add("");
			}

			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			ProdDtlList.add(DataList);
			List<String> listval = entry.getValue();

			/*
			 * Iterarte Subgroup of that list value(ProductName) check if
			 * Customer Name, SG name, Product Name, matches of that Subgroup
			 * List than is add in @ prodDataList
			 * 
			 * # is given of that SubGroup and SubGroup Total for blue style for
			 * Cell
			 * 
			 * @ qtyMD qty is for avg of that customer according to form date
			 * and to date
			 */

			for (String strSGProd : listval) {
				List prodDataList = new ArrayList<>();
				prodDataList.add(intProdSrNo); // for Product wise Sr No
				prodDataList.add(strSGProd);
				intProdSrNo++;
				double dblTotCustQty = 0;

				int cntCust = 0;
				double[] totSGCustQtyL2 = new double[listCustNameHeader.size()];
				for (Object ObjcustName : listCustNameHeader) {
					double custTotRowQty = 0;
					double[] totSGCustQtyL3 = new double[listCustNameHeader.size()];
					Object[] arrObjCustName = (Object[]) ObjcustName;
					String custName = arrObjCustName[0].toString();
					boolean flgCustInsertcell = false;

					for (int md = 0; md < listsqlMainData.size(); md++) {
						Object[] arrObjMD = (Object[]) listsqlMainData.get(md);
						{
							String custNameMD = arrObjMD[0].toString();
							String sgNameMD = arrObjMD[1].toString();
							String prodNameMD = arrObjMD[2].toString();
							double qtyMD = Double.parseDouble(arrObjMD[3].toString());
							qtyMD = Math.round(qtyMD / diffDay);
							if (custName.equals(custNameMD)) {
								if (strSGProd.equals(prodNameMD)) {
									prodDataList.add(qtyMD);
									dblTotCustQty = dblTotCustQty + qtyMD;

									String key = sgNameMD + "#" + custName;
									double qty = 0;
									boolean flgAddqty = false;
									if (hmSubGrouptotal.containsKey(key)) {
										qty = qtyMD + hmSubGrouptotal.get(key);
										flgAddqty = true;
									}
									if (flgAddqty) {
										hmSubGrouptotal.put(key, qty);
										flgAddqty = false;
									} else {
										hmSubGrouptotal.put(key, qtyMD);
									}

									// if(cntCust==0)
									// {
									// totSGCustQtyL3[cntCust]=qtyMD;
									// }else
									// {
									// totSGCustQtyL3[cntCust]=totSGCustQtyL3[cntCust-1]+qtyMD;
									// }

									flgCustInsertcell = true;

									break;

								}

							}

						}

					}
					// sub
					totSGCustQtyL2[cntCust] = totSGCustQtyL3[cntCust];

					if (!flgCustInsertcell) {
						prodDataList.add("");
					}

					cntCust++;
				}
				// totSGQty=totSGCustQtyL3[cntCust];
				prodDataList.add(dblTotCustQty);

				/*
				 * add Stock in @ prodDataList
				 */

				double stkQtyMD = 0;
				for (int md = 0; md < listsqlMainData.size(); md++) {
					Object[] arrObjMD = (Object[]) listsqlMainData.get(md);
					{
						// String custNameMD = arrObjMD[0].toString();
						String prodNameMD = arrObjMD[2].toString();
						stkQtyMD = Double.parseDouble(arrObjMD[4].toString());
						if (stkQtyMD < 0) {
							stkQtyMD = 0;
						}
						if (strSGProd.equals(prodNameMD)) {
							prodDataList.add(stkQtyMD);
							break;
						}
					}

				}

				/*
				 * add Production Qty in @ prodDataList
				 */

				double opQty = dblTotCustQty - stkQtyMD;
				if (opQty < 0) {
					prodDataList.add(0);
				} else {
					prodDataList.add(opQty);
				}
				sgName = "";
				ProdDtlList.add(prodDataList);
			}

			cnttag++;
		}

		/**
		 * Add Subgroup Total colomn wise for each customer @ hmSGProd
		 * 
		 * 
		 */

		if (hmSGProd.size() == cnttag) {
			List DataListLast = new ArrayList<>();
			DataListLast.add("SUBGROUP TOTAL#");
			DataListLast.add("");
			for (Object ObjcustName : listCustNameHeader) {
				Object[] arrObjCustName = (Object[]) ObjcustName;
				String custName = arrObjCustName[0].toString();
				String preSGName = "";
				if (listSGName.size() > 1) {
					System.out.print(listSGName.get(listSGName.size() - 1) + "==" + (listSGName.size() - 1));
					preSGName = listSGName.get(listSGName.size() - 1).toString();
				}
				String key1 = preSGName + "#" + custName;

				if (hmSubGrouptotal.containsKey(key1)) {
					double totalQty = hmSubGrouptotal.get(key1);
					DataListLast.add(totalQty);
				} else {
					DataListLast.add("");
				}
			}
			DataListLast.add("");
			DataListLast.add("");
			DataListLast.add("");
			ProdDtlList.add(DataListLast);

		}

		ExportList.add(ProdDtlList);
		// return a view which will be resolved by an excel view resolver
		// HSSFWorkbook workbook =new HSSFWorkbook();
		//
		// try {
		// funCreateExcelSheetDocument(ExportList,workbook, req, resp);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return new ModelAndView("styleExcelView", "sheetlist", ExportList);

	}

	// private void funCreateExcelSheetDocument(List list,HSSFWorkbook workbook,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// // get data model which is passed by the Spring container
	// response.setContentType("application/vnd.ms-excel");
	// response.setHeader("Content-disposition",
	// "attachment; filename=Report.xls");
	// List Datalist = list;
	// String[] HeaderList= (String[]) Datalist.get(0);
	//
	// List listStock = new ArrayList();
	// try{
	// listStock =(List) Datalist.get(1);
	// }catch(Exception e){
	// listStock=new ArrayList();
	// }
	//
	// // create a new Excel sheet
	// HSSFSheet sheet = workbook.createSheet("Sheet");
	// sheet.setDefaultColumnWidth(20);
	//
	// // create style for header cells
	// CellStyle style = workbook.createCellStyle();
	// Font font = workbook.createFont();
	// font.setFontName("Arial");
	// style.setFillForegroundColor(HSSFColor.BLUE.index);
	// style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	// font.setColor(HSSFColor.WHITE.index);
	// style.setFont(font);
	//
	//
	//
	//
	//
	// // create header row
	// HSSFRow header = sheet.createRow(0);
	// for (int rowCount = 0;rowCount<HeaderList.length;rowCount++)
	// {
	// header.createCell(rowCount).setCellValue(HeaderList[rowCount].toString());
	// header.getCell(rowCount).setCellStyle(style);
	// }
	//
	// // create data rows
	// // aRow is add Row
	// int ColrowCount = 1 ;
	// for(int rowCount=0;rowCount<listStock.size();rowCount++)
	// {
	// HSSFRow aRow = sheet.createRow(ColrowCount++);
	// List arrObj=(List) listStock.get(rowCount);
	//
	// if(arrObj.get(0).toString().contains("#"))
	// {
	//
	// for(int Count=0;Count<arrObj.size();Count++)
	// {
	// if(null!=arrObj.get(Count) && arrObj.get(Count).toString().length()>0)
	// {
	//
	//
	// if(isNumeric(arrObj.get(Count).toString()))
	// {
	// aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
	// aRow.getCell(Count).setCellStyle(style);
	// }
	// else
	// {
	//
	// if(arrObj.get(Count).toString().contains("#"))
	// {
	// String subgroup=arrObj.get(Count).toString().replace("#", "");
	// aRow.createCell(Count).setCellValue(subgroup);
	// aRow.getCell(Count).setCellStyle(style);
	//
	// }else
	// {
	// aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
	// aRow.getCell(Count).setCellStyle(style);
	// }
	//
	//
	// }
	// }
	// else
	// {
	// aRow.createCell(Count).setCellValue("");
	// CellStyle style2 = workbook.createCellStyle();
	// Font font2 = workbook.createFont();
	// font2.setFontName("Arial");
	// style2.setFillForegroundColor(HSSFColor.BLUE.index);
	// style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
	// font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	// font2.setColor(HSSFColor.WHITE.index);
	// style2.setFont(font2);
	// aRow.getCell(Count).setCellStyle(style2);
	// }
	// }
	//
	//
	//
	// }else
	// {
	//
	// for(int Count=0;Count<arrObj.size();Count++)
	// {
	// if(null!=arrObj.get(Count) && arrObj.get(Count).toString().length()>0)
	// {
	//
	//
	// if(isNumeric(arrObj.get(Count).toString()))
	// {
	// aRow.createCell(Count).setCellValue(Double.parseDouble(arrObj.get(Count).toString()));
	// }
	// else
	// {
	// aRow.createCell(Count).setCellValue(arrObj.get(Count).toString());
	// }
	// }
	// else
	// {
	// aRow.createCell(Count).setCellValue("");
	// }
	// }
	//
	// }
	//
	// }
	//
	//
	//
	// }
	// public static boolean isNumeric(String str)
	// {
	// return str.matches("-?\\d+(\\.\\d+)?"); //match a number with optional
	// '-' and decimal.
	// }

	@SuppressWarnings("finally")
	private long funDayDifference(String fromDate, String toDate) {
		long diffDays = 0;
		try {

			// HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = null;
			Date d2 = null;

			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			// long diffSeconds = diff / 1000 % 60;
			// long diffMinutes = diff / (60 * 1000) % 60;
			// long diffHours = diff / (60 * 60 * 1000) % 24;
			diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			// System.out.print(diffHours + " hours, ");
			// System.out.print(diffMinutes + " minutes, ");
			// System.out.print(diffSeconds + " seconds.");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			return diffDays;
		}
	}

	private String funCountWeekDay(String d1, String d2, String weekday) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long days = 0;
		Date date1;
		Date date2;
		String dayName = "";
		try {
			date1 = df.parse(d1);
			date2 = df.parse(d2);

			String fromDate[] = d1.split("-");
			int fyear = Integer.parseInt(fromDate[0]);
			int fmonth = Integer.parseInt(fromDate[1]);
			int fday = Integer.parseInt(fromDate[2]);

			String toDate[] = d2.split("-");
			int tyear = Integer.parseInt(toDate[0]);
			int tmonth = Integer.parseInt(toDate[1]);
			int tday = Integer.parseInt(toDate[2]);

			LocalDate startDate = new LocalDate(fyear, fmonth, fday);
			LocalDate endDate = new LocalDate(tyear, tmonth, tday);

			switch (weekday) {
			case "1":

				dayName = "SUNDAY";
				LocalDate thisSUNDAY = startDate.withDayOfWeek(DateTimeConstants.SUNDAY);

				if (startDate.isAfter(thisSUNDAY)) {
					startDate = thisSUNDAY.plusWeeks(1); // start on next SUNDAY
				} else {
					startDate = thisSUNDAY; // start on this SUNDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}

				break;

			case "2":
				dayName = "MONDAY";
				LocalDate thisMONDAY = startDate.withDayOfWeek(DateTimeConstants.MONDAY);

				if (startDate.isAfter(thisMONDAY)) {
					startDate = thisMONDAY.plusWeeks(1); // start on next MONDAY
				} else {
					startDate = thisMONDAY; // start on this MONDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}

				break;

			case "3":
				dayName = "TUESDAY";
				LocalDate thisTUESDAY = startDate.withDayOfWeek(DateTimeConstants.TUESDAY);

				if (startDate.isAfter(thisTUESDAY)) {
					startDate = thisTUESDAY.plusWeeks(1); // start on next
															// WEDNESDAY
				} else {
					startDate = thisTUESDAY; // start on this WEDNESDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}
				break;

			case "4":
				dayName = "WEDNESDAY";
				LocalDate thisWEDNESDAY = startDate.withDayOfWeek(DateTimeConstants.WEDNESDAY);

				if (startDate.isAfter(thisWEDNESDAY)) {
					startDate = thisWEDNESDAY.plusWeeks(1); // start on next
															// WEDNESDAY
				} else {
					startDate = thisWEDNESDAY; // start on this WEDNESDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}
				break;

			case "5":
				dayName = "THURSDAY";
				LocalDate thisTHURSDAY = startDate.withDayOfWeek(DateTimeConstants.THURSDAY);

				if (startDate.isAfter(thisTHURSDAY)) {
					startDate = thisTHURSDAY.plusWeeks(1); // start on next
															// THURSDAY
				} else {
					startDate = thisTHURSDAY; // start on this THURSDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}
				break;

			case "6":
				dayName = "FRIDAY";
				LocalDate thisFRIDAY = startDate.withDayOfWeek(DateTimeConstants.FRIDAY);

				if (startDate.isAfter(thisFRIDAY)) {
					startDate = thisFRIDAY.plusWeeks(1); // start on next FRIDAY
				} else {
					startDate = thisFRIDAY; // start on this FRIDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}
				break;

			case "7":
				dayName = "SATURDAY";
				LocalDate thisSATURDAY = startDate.withDayOfWeek(DateTimeConstants.SATURDAY);

				if (startDate.isAfter(thisSATURDAY)) {
					startDate = thisSATURDAY.plusWeeks(1); // start on next
															// SATURDAY
				} else {
					startDate = thisSATURDAY; // start on this SATURDAY
				}

				while (startDate.isBefore(endDate)) {
					System.out.println(startDate);
					startDate = startDate.plusWeeks(1);
					days++;
				}

				break;

			}

			int intweekday = Integer.parseInt(weekday);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date2);
			System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
			if (calendar.get(Calendar.DAY_OF_WEEK) == intweekday) {
				days = days + 1;
			}
			System.out.println("final day=" + days);

			// System.out.println("number of days between 2 dates"+days);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return dayName + "#" + days;
		}

	}

}
