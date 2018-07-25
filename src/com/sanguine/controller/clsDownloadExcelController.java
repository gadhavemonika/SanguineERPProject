package com.sanguine.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsStockFlashService;

@Controller
public class clsDownloadExcelController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String reportType = spParam1[0];
		String locCode = spParam1[1];
		String propCode = spParam1[2];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		List listStock = new ArrayList();
		listStock.add("StockFlash_" + fromDate + "to" + toDate + "_" + userCode);
		String strCurr = req.getSession().getAttribute("currValue").toString();
		double currValue = Double.parseDouble(strCurr);
		
		double totalOpeningStock=0.00,totalGRN=0.00,totalSCGRN=0.00,totalStkTransferIn=0.00,totalStkAdjIn=0.00;
		double totalMISIn=0.00,totalProducedQty=0.00,totalSalesRet=0.00,totalMaterialRet=0.00,totalPurchaseRet=0.00,totalDelNote=0.00;
		double totalStkTransOut=0.00,totalStkAdjOut=0.00,totalMISOut=0.00,totalQtyConsumed=0.00,totalSaleAmt=0.00,totalClosingStk=0.00,totalValueTotal = 0.00,totalIssueUOMStk=0.00;
		double totalReciept=0.00,totalIssue=0.00;
		
		String stockableItem = "B";
		if (strNonStkItems.equals("Stockable")) {
			stockableItem = "Y";
		}
		if (strNonStkItems.equals("Non Stockable")) {
			stockableItem = "N";
		}

		if ("Detail".equalsIgnoreCase(reportType)) {
			String[] ExcelHeader = { "Property Name", "Product Code", "Product Name", "Location", "Group", "Sub Group", "UOM", "Bin No", "Unit Price", "Opening Stock", "GRN", "SCGRN", "	Stock Transfer In", "Stock Adj In", "MIS In", "Qty Produced", "Sales Return", "Material Return", "Purchase Return", "Delivery Note", "Stock Trans Out", "Stock Adj Out", "MIS Out", "Qty Consumed", "Sales",
					"Closing Stock", "Value", "Issue UOM Stock", "Issue Conversion", "Issue UOM", "Part No" };
			listStock.add(ExcelHeader);

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			System.out.println(startDate);
			objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);

			String sql = "";
			if (qtyWithUOM.equals("No")) {
				if (strGCode.equals("ALL") && strSGCode.equals("ALL")) // for
																		// All
																		// Group
																		// and
																		// All
																		// SubGroup
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " , " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value"
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value " + ",a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "

							/*
							 * +
							 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
							 * + ",tblpropertymaster f " +
							 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							 * +
							 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
							 */
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "'  " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
				} else if (!(strGCode.equals("ALL")) && strSGCode.equals("ALL")) // for
																					// Particulor
																					// group
																					// and
																					// All
																					// SubGroup
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)/" + currValue + " , " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value,"
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value,"

							+ "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
							/*
							 * +
							 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
							 * + ",tblpropertymaster f " +
							 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							 * +
							 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
							 */
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' "
							// + " and a.strUserCode='"+userCode+"'  "
							+ "and c.strGCode='" + strGCode + "' " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}

				} else // // for Particulor group and Particulor SubGroup
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + ", " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value, "
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value,"

							+ "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
							/*
							 * +
							 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
							 * + ",tblpropertymaster f " +
							 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							 * +
							 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
							 */
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' "
							// + "and a.strUserCode='"+userCode+"' "
							+ "and c.strGCode='" + strGCode + "' and b.strSGCode='" + strSGCode + "' " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
				}

			} else {
				sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + " ,d.strGName,c.strSGName,b.strUOM,b.strBinNo"
				// + " ,b.dblCostRM"
						+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " " + ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

				+ " ,funGetUOM(a.dblGRN,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSCGRN,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkTransIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkAdjIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMISIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblQtyProduced,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSalesReturn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMaterialReturnIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblPurchaseReturn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblDeliveryNote,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkTransOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkAdjOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMISOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblQtyConsumed,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSales,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMaterialReturnOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblClosingStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

						// + ",(a.dblClosingStk*b.dblCostRM) as Value,"
						+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value," + "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
						/*
						 * +
						 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
						 * + ",tblpropertymaster f " +
						 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
						 * +
						 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
						 */
						+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
						+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' ";
				// + " and a.strUserCode='"+userCode+"' ";

				if (strNonStkItems.equals("Non Stockable")) {
					sql += "	and b.strNonStockableItem='Y' ";
				} else if (strNonStkItems.equals("Stockable")) {
					sql += "	and b.strNonStockableItem='N' ";
				}

				if (!(prodType.equalsIgnoreCase("ALL"))) {
					sql += " and  b.strProdType <> '" + prodType + "'  ";
				}

				if (!strGCode.equalsIgnoreCase("All")) {
					sql += "and d.strGCode='" + strGCode + "' ";
				}

				if (!strSGCode.equalsIgnoreCase("All")) {
					sql += "and c.strSGCode='" + strSGCode + "' ";
				}
				if(!strManufactureCode.equals("")){
					sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
				}
				// if(strNonStkItems.equals("Stockable"))
				// {
				// sql+= "and b.strNonStockableItem='N' ";
				// }
				// if(strNonStkItems.equals("Non Stockable"))
				// {
				// sql+= "and b.strNonStockableItem='Y' ";
				// }

			}
			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			// System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);

			List listStockFlashModel = new ArrayList();
			if (qtyWithUOM.equals("No")) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					List DataList = new ArrayList<>();
					DataList.add(arrObj[0].toString());
					DataList.add(arrObj[1].toString());
					DataList.add(arrObj[2].toString());
					DataList.add(arrObj[3].toString());
					DataList.add(arrObj[4].toString());
					DataList.add(arrObj[5].toString());
					DataList.add(arrObj[6].toString());
					DataList.add(arrObj[7].toString());
					DataList.add(arrObj[8].toString());
					DataList.add(arrObj[9].toString());
					DataList.add(arrObj[10].toString());
					DataList.add(arrObj[11].toString());
					DataList.add(arrObj[12].toString());
					DataList.add(arrObj[13].toString());
					DataList.add(arrObj[14].toString());
					DataList.add(arrObj[15].toString());
					DataList.add(arrObj[16].toString());
					DataList.add(arrObj[17].toString());
					DataList.add(arrObj[18].toString());
					DataList.add(arrObj[19].toString());
					DataList.add(arrObj[20].toString());
					DataList.add(arrObj[21].toString());
					DataList.add(arrObj[22].toString());
					DataList.add(arrObj[23].toString());
					DataList.add(arrObj[24].toString());

					/*
					 * DataList.add(Double.parseDouble(arrObj[8].toString()));
					 * DataList.add(Double.parseDouble(arrObj[9].toString()));
					 * DataList.add(Double.parseDouble(arrObj[10].toString()));
					 * DataList.add(Double.parseDouble(arrObj[11].toString()));
					 * DataList.add(Double.parseDouble(arrObj[12].toString()));
					 * DataList.add(Double.parseDouble(arrObj[13].toString()));
					 * DataList.add(Double.parseDouble(arrObj[14].toString()));
					 * DataList.add(Double.parseDouble(arrObj[15].toString()));
					 * DataList.add(Double.parseDouble(arrObj[16].toString()));
					 * DataList.add(Double.parseDouble(arrObj[17].toString()));
					 * DataList.add(Double.parseDouble(arrObj[18].toString()));
					 * DataList.add(Double.parseDouble(arrObj[19].toString()));
					 * DataList.add(Double.parseDouble(arrObj[20].toString()));
					 * DataList.add(Double.parseDouble(arrObj[21].toString()));
					 * DataList.add(Double.parseDouble(arrObj[22].toString()));
					 * DataList.add(Double.parseDouble(arrObj[23].toString()));
					 * DataList.add(Double.parseDouble(arrObj[24].toString()));
					 */

					DataList.add(arrObj[26].toString());
					double value = Double.parseDouble(arrObj[27].toString());
					if (value < 0) {
						// value=value*(-1);
					}
					
					//dblValueTotal += value;
					DataList.add(value);
					DataList.add(arrObj[26].toString());
					DataList.add(arrObj[29].toString());
					DataList.add(arrObj[30].toString());
					DataList.add(arrObj[31].toString());

					listStockFlashModel.add(DataList);
					
					totalOpeningStock+=Double.parseDouble(arrObj[9].toString());
					totalGRN+= Double.parseDouble(arrObj[10].toString());
					totalSCGRN+= Double.parseDouble(arrObj[11].toString());
					totalStkTransferIn+= Double.parseDouble(arrObj[12].toString());
					totalStkAdjIn+= Double.parseDouble(arrObj[13].toString());
					totalMISIn+= Double.parseDouble(arrObj[14].toString());
					totalProducedQty+= Double.parseDouble(arrObj[15].toString());
					totalSalesRet+= Double.parseDouble(arrObj[16].toString());
					totalMaterialRet+= Double.parseDouble(arrObj[17].toString());
					totalPurchaseRet+= Double.parseDouble(arrObj[18].toString());
					totalDelNote+= Double.parseDouble(arrObj[19].toString());
					totalStkTransOut+= Double.parseDouble(arrObj[20].toString());
					totalStkAdjOut+= Double.parseDouble(arrObj[21].toString());
					totalMISOut+= Double.parseDouble(arrObj[22].toString());
					totalQtyConsumed+= Double.parseDouble(arrObj[23].toString());
					totalSaleAmt+= Double.parseDouble(arrObj[24].toString());
					totalClosingStk+= Double.parseDouble(arrObj[26].toString());
					totalValueTotal+= value;
					totalIssueUOMStk+= Double.parseDouble(arrObj[28].toString());

				}

			} else {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					List DataList = new ArrayList<>();
					DataList.add(arrObj[0].toString());
					DataList.add(arrObj[1].toString());
					DataList.add(arrObj[2].toString());
					DataList.add(arrObj[3].toString());
					DataList.add(arrObj[4].toString());
					DataList.add(arrObj[5].toString());
					DataList.add(arrObj[6].toString());
					DataList.add(arrObj[7].toString());
					DataList.add(arrObj[8].toString());
					DataList.add(funGetDecimalValue(arrObj[9].toString()));
					DataList.add(funGetDecimalValue(arrObj[10].toString()));
					DataList.add(funGetDecimalValue(arrObj[11].toString()));
					DataList.add(funGetDecimalValue(arrObj[12].toString()));
					DataList.add(funGetDecimalValue(arrObj[13].toString()));
					DataList.add(funGetDecimalValue(arrObj[14].toString()));
					DataList.add(funGetDecimalValue(arrObj[15].toString()));
					DataList.add(funGetDecimalValue(arrObj[16].toString()));
					DataList.add(funGetDecimalValue(arrObj[17].toString()));
					DataList.add(funGetDecimalValue(arrObj[18].toString()));
					DataList.add(funGetDecimalValue(arrObj[19].toString()));
					DataList.add(funGetDecimalValue(arrObj[20].toString()));
					DataList.add(funGetDecimalValue(arrObj[21].toString()));
					DataList.add(funGetDecimalValue(arrObj[22].toString()));
					DataList.add(funGetDecimalValue(arrObj[23].toString()));
					DataList.add(funGetDecimalValue(arrObj[24].toString()));

					DataList.add(funGetDecimalValue(arrObj[26].toString()));
					double value = Double.parseDouble(arrObj[27].toString());
					if (value < 0) {
						// value=value*(-1);
					}
					
					//dblValueTotal += value;
					DataList.add(value);
					DataList.add(funGetDecimalValue(arrObj[26].toString()));
					DataList.add(arrObj[29].toString());
					DataList.add(arrObj[30].toString());
					DataList.add(arrObj[31].toString());

					listStockFlashModel.add(DataList);
					
					totalOpeningStock+=Double.parseDouble(arrObj[9].toString());
					totalGRN+= Double.parseDouble(arrObj[10].toString());
					totalSCGRN+= Double.parseDouble(arrObj[11].toString());
					totalStkTransferIn+= Double.parseDouble(arrObj[12].toString());
					totalStkAdjIn+= Double.parseDouble(arrObj[13].toString());
					totalMISIn+= Double.parseDouble(arrObj[14].toString());
					totalProducedQty+= Double.parseDouble(arrObj[15].toString());
					totalSalesRet+= Double.parseDouble(arrObj[16].toString());
					totalMaterialRet+= Double.parseDouble(arrObj[17].toString());
					totalPurchaseRet+= Double.parseDouble(arrObj[18].toString());
					totalDelNote+= Double.parseDouble(arrObj[19].toString());
					totalStkTransOut+= Double.parseDouble(arrObj[20].toString());
					totalStkAdjOut+= Double.parseDouble(arrObj[21].toString());
					totalMISOut+= Double.parseDouble(arrObj[22].toString());
					totalQtyConsumed+= Double.parseDouble(arrObj[23].toString());
					totalSaleAmt+= Double.parseDouble(arrObj[24].toString());
					totalClosingStk+= Double.parseDouble(arrObj[26].toString());
					totalValueTotal+= value;
					totalIssueUOMStk+= Double.parseDouble(arrObj[28].toString());
				}
			}
			List DataList = new ArrayList<>();
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("Total ");
			DataList.add(totalOpeningStock);
			DataList.add(totalGRN);
			DataList.add(totalSCGRN);
			DataList.add(totalStkTransferIn);
			DataList.add(totalStkAdjIn);
			DataList.add(totalMISIn);
			DataList.add(totalProducedQty);
			DataList.add(totalSalesRet);
			DataList.add(totalMaterialRet);
			DataList.add(totalPurchaseRet);
			DataList.add(totalDelNote);
			DataList.add(totalStkTransOut);
			DataList.add(totalStkAdjOut);
			DataList.add(totalMISOut);
			DataList.add(totalQtyConsumed);
			DataList.add(totalSaleAmt);
			DataList.add(totalClosingStk);
			NumberFormat formatter = new DecimalFormat("###.#####");
			String f = formatter.format(totalValueTotal);
			DataList.add(f);
			DataList.add(totalIssueUOMStk);
			DataList.add("");
			DataList.add("");
			DataList.add("");

			listStockFlashModel.add(DataList);
			listStock.add(listStockFlashModel);
			// return a view which will be resolved by an excel view resolver
			return new ModelAndView("excelViewWithReportName", "listWithReportName", listStock);
		} else if ("Summary".equalsIgnoreCase(reportType)) {
			String[] ExcelHeader = { "Property Name", "Product Code", "Product Name", "Location", "Group", "Sub Group", "UOM", "Bin No", "Unit Price", "Opening Stock", "Receipts", "Issue", "Closing Stock", "Value", "Issue UOM Stock", "Issue Conversion", "Issue UOM", "Part No" };
			listStock.add(ExcelHeader);

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			System.out.println(startDate);
			objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);

			// objGlobal.funDeleteAndInsertStkTempTable(clientCode,userCode,locCode);
			// objGlobal.funProcessStock(locCode, fromDate, toDate, clientCode,
			// userCode);
			String sql = "";

			/*
			 * sql="select f.propertyName,a.strProdCode,b.strProdName,e.strLocName"
			 * + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo " +
			 * ",b.dblCostRM,a.dblOpeningStk,(a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Receipts "
			 * +
			 * ",(a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue "
			 * +
			 * ",a.dblClosingStk,(a.dblClosingStk*b.dblCostRM) as Value,a.dblClosingStk as IssueUOMStock "
			 * + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo " +
			 * "from clsCurrentStockModel a,clsProductMasterModel b,clsSubGroupMasterModel c"
			 * +
			 * ",clsGroupMasterModel d,clsLocationMasterModel e,clsPropertyMaster f "
			 * +
			 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
			 * +
			 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
			 * + "and a.strClientCode=:clientCode and a.strUserCode=:userCode ";
			 * 
			 * if(!strGCode.equalsIgnoreCase("All")) { sql+=
			 * "and d.strGCode='"+strGCode+"' "; }
			 * 
			 * if(!strSGCode.equalsIgnoreCase("All")) { sql+=
			 * "and c.strSGCode='"+strSGCode+"' "; }
			 * if(strNonStkItems.equals("Stockable")) { sql+=
			 * "and b.strNonStockableItem='N' "; }
			 * if(strNonStkItems.equals("Non Stockable")) { sql+=
			 * "and b.strNonStockableItem='Y' "; }
			 * if(showZeroItems.equals("No")) { sql+=
			 * "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 "
			 * +
			 * "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 "
			 * +
			 * "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 "
			 * + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)"; }
			 */

			if (qtyWithUOM.equals("No")) {
				sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo "
						// + ",b.dblCostRM,"
						+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice), " + "a.dblOpeningStk,(a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Receipts " + ",(a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue " + ",a.dblClosingStk,"
						// + "(a.dblClosingStk*b.dblCostRM) as Value,"
						+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value," + "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
						/*
						 * +
						 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
						 * + ",tblpropertymaster f " +
						 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
						 * +
						 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
						 */
						+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
						+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' ";
				// + "and a.strUserCode='"+userCode+"' ";

				if (strNonStkItems.equals("Non Stockable")) {
					sql += "	and b.strNonStockableItem='Y' ";
				} else if (strNonStkItems.equals("Stockable")) {
					sql += "	and b.strNonStockableItem='N' ";
				}

				if (!(prodType.equalsIgnoreCase("ALL"))) {
					sql += " and  b.strProdType <> '" + prodType + "'  ";
				}
				if(!strManufactureCode.equals("")){
					sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
				}
			} else {
				sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo "
						// + ",b.dblCostRM"
						+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) "

						+ ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

						+ ",funGetUOM((a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn),b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as Receipts "

						+ ",funGetUOM((a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote),b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as Issue "

						+ ",funGetUOM(a.dblClosingStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

						// + ",(a.dblClosingStk*b.dblCostRM) as Value,"
						+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value," + " a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
						/*
						 * +
						 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
						 * + ",tblpropertymaster f " +
						 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
						 * +
						 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
						 */
						+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
						+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' "

						+ "and a.strClientCode='" + clientCode + "' ";
				// + " and a.strUserCode='"+userCode+"' ";
				if (strNonStkItems.equals("Non Stockable")) {
					sql += "	and b.strNonStockableItem='Y' ";
				} else if (strNonStkItems.equals("Stockable")) {
					sql += "	and b.strNonStockableItem='N' ";
				}

				if (!(prodType.equalsIgnoreCase("ALL"))) {
					sql += " and  b.strProdType <> '" + prodType + "'  ";
				}
				if(!strManufactureCode.equals("")){
					sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
				}

			}

			if (!strGCode.equalsIgnoreCase("All")) {
				sql += "and d.strGCode='" + strGCode + "' ";
			}

			if (!strSGCode.equalsIgnoreCase("All")) {
				sql += "and c.strSGCode='" + strSGCode + "' ";
			}

			// if(strNonStkItems.equals("Stockable"))
			// {
			// sql+= "and b.strNonStockableItem='N' ";
			// }
			// if(strNonStkItems.equals("Non Stockable"))
			// {
			// sql+= "and b.strNonStockableItem='Y' ";
			// }
			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);
			List listStockFlashModel = new ArrayList();

			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				List DataList = new ArrayList<>();

				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
				DataList.add(arrObj[2].toString());
				DataList.add(arrObj[3].toString());
				DataList.add(arrObj[4].toString());
				DataList.add(arrObj[5].toString());
				DataList.add(arrObj[6].toString());
				DataList.add(arrObj[7].toString());
				DataList.add(arrObj[8].toString());
				DataList.add(arrObj[9].toString());
				DataList.add(arrObj[10].toString());
				double issueQty = 0.0;
				if (qtyWithUOM.equals("Yes")) {
					if (!arrObj[11].toString().equals("")) {
						issueQty = Double.parseDouble(arrObj[11].toString().split(" ")[0]);
						if (issueQty < 0) {
							issueQty = issueQty * (-1);
						}
						DataList.add(issueQty);

					} else {
						DataList.add(arrObj[11].toString());

					}
				} else {
					issueQty = Double.parseDouble(arrObj[11].toString());
					if (issueQty < 0) {
						issueQty = issueQty * (-1);
					}
					DataList.add(issueQty);
				}

				DataList.add(arrObj[12].toString());

				double value = Double.parseDouble(arrObj[13].toString());
				// To Fix Methods
				// value=Math.round(value*100.0)/100.0); // fix to 2 digit
				// value=Math.round(value*1000.0)/1000.0); // fix to 3 digit

				if (value < 0) {
					value = value * (-1);
				}
				DataList.add(value);
				DataList.add(arrObj[12].toString());
				DataList.add(arrObj[15].toString());
				DataList.add(arrObj[16].toString());
				DataList.add(arrObj[17].toString());

				listStockFlashModel.add(DataList);
				
				totalOpeningStock+=Double.parseDouble(arrObj[9].toString());
				totalReciept+= Double.parseDouble(arrObj[10].toString());
				if ( Double.parseDouble(arrObj[11].toString()) < 0) 
				{
					totalIssue+= Double.parseDouble(arrObj[11].toString()) * (-1);
				}
				else
				{
					totalIssue+= Double.parseDouble(arrObj[11].toString());
				}
				totalClosingStk+= Double.parseDouble(arrObj[12].toString());
				totalValueTotal+= value;
				totalIssueUOMStk+= Double.parseDouble(arrObj[14].toString());
			}
			List DataList = new ArrayList<>();
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("");
			DataList.add("Total ");
			DataList.add(totalOpeningStock);
			DataList.add(totalReciept);
			DataList.add(totalIssue);
			DataList.add(totalClosingStk);
			DataList.add(totalValueTotal);
			DataList.add(totalIssueUOMStk);
			DataList.add("");
			DataList.add("");
			DataList.add("");

			listStockFlashModel.add(DataList);
			listStock.add(listStockFlashModel);
			return new ModelAndView("excelViewWithReportName", "listWithReportName", listStock);
		} 
		else if("Total".equalsIgnoreCase(reportType))
		{
			String[] ExcelHeader = { "Transaction Type","Value"};
			listStock.add(ExcelHeader);

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			System.out.println(startDate);
			objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);

			String sql = "";
			if (qtyWithUOM.equals("No")) {
				if (strGCode.equals("ALL") && strSGCode.equals("ALL")) 
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " , " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value " + ",a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "'  " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
				} else if (!(strGCode.equals("ALL")) && strSGCode.equals("ALL")) 
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)/" + currValue + " , " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value,"
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value,"
							+ "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' "
							// + " and a.strUserCode='"+userCode+"'  "
							+ "and c.strGCode='" + strGCode + "' " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}

				} else // // for Particulor group and Particulor SubGroup
				{
					sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + ",b.dblCostRM,"
							+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + ", " + "a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn" + ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut "
							+ ",a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value, "
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value,"
							+ "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "

							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' "
							// + "and a.strUserCode='"+userCode+"' "
							+ "and c.strGCode='" + strGCode + "' and b.strSGCode='" + strSGCode + "' " + "and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
				}

			} else {
				sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + " ,d.strGName,c.strSGName,b.strUOM,b.strBinNo"
				// + " ,b.dblCostRM"
						+ " ,(if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " " + ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

				+ " ,funGetUOM(a.dblGRN,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSCGRN,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkTransIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkAdjIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMISIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblQtyProduced,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSalesReturn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMaterialReturnIn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblPurchaseReturn,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblDeliveryNote,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkTransOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblStkAdjOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMISOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblQtyConsumed,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblSales,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblMaterialReturnOut,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

				+ " ,funGetUOM(a.dblClosingStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

						// + ",(a.dblClosingStk*b.dblCostRM) as Value,"
						+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice))/" + currValue + " as Value," + "a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
						+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
						+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + "and a.strClientCode='" + clientCode + "' ";
				// + " and a.strUserCode='"+userCode+"' ";

				if (strNonStkItems.equals("Non Stockable")) {
					sql += "	and b.strNonStockableItem='Y' ";
				} else if (strNonStkItems.equals("Stockable")) {
					sql += "	and b.strNonStockableItem='N' ";
				}

				if (!(prodType.equalsIgnoreCase("ALL"))) {
					sql += " and  b.strProdType <> '" + prodType + "'  ";
				}

				if (!strGCode.equalsIgnoreCase("All")) {
					sql += "and d.strGCode='" + strGCode + "' ";
				}

				if (!strSGCode.equalsIgnoreCase("All")) {
					sql += "and c.strSGCode='" + strSGCode + "' ";
				}
				if(!strManufactureCode.equals("")){
					sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
				}

			}
			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			// System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
			// userCode);
			List list = objGlobalService.funGetList(sql);

			List listStockFlashModel = new ArrayList();
			if (qtyWithUOM.equals("No")) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					double value = Double.parseDouble(arrObj[27].toString());
					if (value < 0) {
						// value=value*(-1);
					}
					totalOpeningStock+=Double.parseDouble(arrObj[9].toString());
					totalGRN+= Double.parseDouble(arrObj[10].toString());
					totalSCGRN+= Double.parseDouble(arrObj[11].toString());
					totalStkTransferIn+= Double.parseDouble(arrObj[12].toString());
					totalStkAdjIn+= Double.parseDouble(arrObj[13].toString());
					totalMISIn+= Double.parseDouble(arrObj[14].toString());
					totalProducedQty+= Double.parseDouble(arrObj[15].toString());
					totalSalesRet+= Double.parseDouble(arrObj[16].toString());
					totalMaterialRet+= Double.parseDouble(arrObj[17].toString());
					totalPurchaseRet+= Double.parseDouble(arrObj[18].toString());
					totalDelNote+= Double.parseDouble(arrObj[19].toString());
					totalStkTransOut+= Double.parseDouble(arrObj[20].toString());
					totalStkAdjOut+= Double.parseDouble(arrObj[21].toString());
					totalMISOut+= Double.parseDouble(arrObj[22].toString());
					totalQtyConsumed+= Double.parseDouble(arrObj[23].toString());
					totalSaleAmt+= Double.parseDouble(arrObj[24].toString());
					totalClosingStk+= Double.parseDouble(arrObj[26].toString());
					totalValueTotal+= value;
					totalIssueUOMStk+= Double.parseDouble(arrObj[28].toString());

				}

			} else {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					double value = Double.parseDouble(arrObj[27].toString());
					if (value < 0) {
						// value=value*(-1);
					}
					totalOpeningStock+=Double.parseDouble(arrObj[9].toString());
					totalGRN+= Double.parseDouble(arrObj[10].toString());
					totalSCGRN+= Double.parseDouble(arrObj[11].toString());
					totalStkTransferIn+= Double.parseDouble(arrObj[12].toString());
					totalStkAdjIn+= Double.parseDouble(arrObj[13].toString());
					totalMISIn+= Double.parseDouble(arrObj[14].toString());
					totalProducedQty+= Double.parseDouble(arrObj[15].toString());
					totalSalesRet+= Double.parseDouble(arrObj[16].toString());
					totalMaterialRet+= Double.parseDouble(arrObj[17].toString());
					totalPurchaseRet+= Double.parseDouble(arrObj[18].toString());
					totalDelNote+= Double.parseDouble(arrObj[19].toString());
					totalStkTransOut+= Double.parseDouble(arrObj[20].toString());
					totalStkAdjOut+= Double.parseDouble(arrObj[21].toString());
					totalMISOut+= Double.parseDouble(arrObj[22].toString());
					totalQtyConsumed+= Double.parseDouble(arrObj[23].toString());
					totalSaleAmt+= Double.parseDouble(arrObj[24].toString());
					totalClosingStk+= Double.parseDouble(arrObj[26].toString());
					totalValueTotal+= value;
					totalIssueUOMStk+= Double.parseDouble(arrObj[28].toString());
				}
			}
			List DataList = new ArrayList<>();
			DataList.add("Opening Stock");
			DataList.add(totalOpeningStock);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("GRN");
			DataList.add(totalGRN);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("SCGRN");
			DataList.add(totalSCGRN);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Stock Transfer");
			DataList.add(totalStkTransferIn);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Stock Adj In");
			DataList.add(totalStkAdjIn);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("MIS In");
			DataList.add(totalMISIn);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Qty Produced");
			DataList.add(totalProducedQty);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Sales Return");
			DataList.add(totalSalesRet);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Material Return");
			DataList.add(totalMaterialRet);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Purchase Return");
			DataList.add(totalPurchaseRet);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Delivery Note");
			DataList.add(totalDelNote);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Stock Transfer out");
			DataList.add(totalStkTransOut);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Stock Adj Out");
			DataList.add(totalStkAdjOut);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("MIS Out");
			DataList.add(totalMISOut);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Quantity Consumed");
			DataList.add(totalQtyConsumed);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Sale Amount");
			DataList.add(totalSaleAmt);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Closing Stock");
			DataList.add(totalClosingStk);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Value");
			NumberFormat formatter = new DecimalFormat("###.#####");
			String f = formatter.format(totalValueTotal);
			DataList.add(f);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("Issue UOM Stock");
			DataList.add(totalIssueUOMStk);
			listStockFlashModel.add(DataList);
			DataList = new ArrayList<>();
			DataList.add("");
			DataList.add("");
			listStockFlashModel.add(DataList);
			listStock.add(listStockFlashModel);
			return new ModelAndView("excelViewWithReportName", "listWithReportName", listStock);
		}
		else // if("Mini".equalsIgnoreCase(reportType))
		{

			String[] ExcelHeader = { "Product Code", "Product Name", "Closing Stock", "Value" };
			listStock.add(ExcelHeader);

			String startDate = req.getSession().getAttribute("startDate").toString();
			String[] sp = startDate.split(" ");
			String[] spDate = sp[0].split("/");
			startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
			System.out.println(startDate);
			objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);

			String sql = "";
			if (qtyWithUOM.equals("No")) {
				if (strGCode.equals("ALL") && strSGCode.equals("ALL")) // for
																		// All
																		// Group
																		// and
																		// All
																		// SubGroup
				{
					sql = " select a.strProdCode,b.strProdName, "
							+ " a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value "
							+ " (a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value "
							+ ",d.strGName,c.strSGName "
							// +
							// " from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
							// + ",tblpropertymaster f "
							// +
							// " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							// +
							// " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "

							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' "

							+ " and a.strClientCode='" + clientCode + "' "
							// + "and a.strUserCode='"+userCode+"' "
							+ " and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' " + "  order by c.intSortingNo ";
					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}

					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
				} else if (!(strGCode.equals("ALL")) && strSGCode.equals("ALL")) // for
																					// Particulor
																					// group
																					// and
																					// All
																					// SubGroup
				{
					sql = "select a.strProdCode,b.strProdName," + " a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value "
							+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value " + ",d.strGName,c.strSGName "
							/*
							 * +
							 * " from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
							 * + ",tblpropertymaster f " +
							 * " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							 * +
							 * " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
							 */
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' " + " and a.strClientCode='" + clientCode + "' "
							// + "and a.strUserCode='"+userCode+"' "
							+ " and c.strGCode='" + strGCode + "' " + " and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' " + "  order by c.intSortingNo ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}

					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}

				} else // // for Particulor group and Particulor SubGroup
				{
					sql = "select a.strProdCode,b.strProdName, " + " a.dblClosingStk,"
							// + "(a.dblClosingStk*b.dblCostRM) as Value "
							+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value" + ",d.strGName,c.strSGName "
							/*
							 * +
							 * " from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e "
							 * + " ,tblpropertymaster f " +
							 * " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
							 * +
							 * " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
							 */
							+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
							+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' "

							+ " and a.strClientCode='" + clientCode + "' "
							// + "and a.strUserCode='"+userCode+"' "
							+ " and c.strGCode='" + strGCode + "' and b.strSGCode='" + strSGCode + "' " + " and a.strLocCode='" + locCode + "' and e.strPropertyCode='" + propCode + "' " + "  order by c.intSortingNo ";

					if (strNonStkItems.equals("Non Stockable")) {
						sql += "	and b.strNonStockableItem='Y' ";
					} else if (strNonStkItems.equals("Stockable")) {
						sql += "	and b.strNonStockableItem='N' ";
					}

					if (!(prodType.equalsIgnoreCase("ALL"))) {
						sql += " and  b.strProdType <> '" + prodType + "'  ";
					}
					if(!strManufactureCode.equals("")){
						sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
					}
				}

			}

			if (showZeroItems.equals("No")) {
				sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
			}

			// System.out.println(sql);
			// List list=objStkFlashService.funGetStockFlashDat
			// a(sql,clientCode, userCode);
			List list = objGlobalService.funGetList(sql);

			List listStockFlashModel = new ArrayList();

			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] arrObj = (Object[]) list.get(cnt);
				List DataList = new ArrayList<>();
				DataList.add(arrObj[0].toString());
				DataList.add(arrObj[1].toString());
				DataList.add(arrObj[2].toString());
				// DataList.add(arrObj[3].toString());

				// DataList.add(arrObj[26].toString());
				double value = Double.parseDouble(arrObj[3].toString());
				if (value < 0) {
					// value=value*(-1);
				}
				DataList.add(value);

				listStockFlashModel.add(DataList);

			}
			listStock.add(listStockFlashModel);
			// return a view which will be resolved by an excel view resolver
			return new ModelAndView("excelViewWithReportName", "listWithReportName", listStock);

		}

	}

	private String funGetDecimalValue(String strValue) {
		String strVal = "";
		String[] spl = strValue.split(" ");
		if (spl.length == 2) // for Single UOM
		{

			String[] splValue = strValue.split("\\.");
			if (splValue.length == 2) {
				String firstValue = splValue[0];
				String secondValue = splValue[1];
				strVal = firstValue + " " + secondValue.split(" ")[1];
			} else {
				strVal = splValue[0];
			}
		}
		if (spl.length == 3)// for Two UOM
		{
			String[] splValue = strValue.split("\\.");
			if (splValue.length > 1) {
				String firstValue = splValue[0];
				String secondValue = splValue[1];
				if (splValue.length == 3) {
					strVal = firstValue + " " + secondValue + " " + splValue[2].split(" ")[1];
				}
				if (splValue.length == 2) {
					strVal = firstValue + " " + secondValue;
				}
			} else {
				strVal = splValue[0];
			}
		}

		return strVal;
	}

	/*
	 * private String funGetDecimalValue(String strValue) { String strVal = "";
	 * if(strValue.contains("BTL") || strValue.contains("ML")) {
	 * if(strValue.contains("BTL")) { if(strValue.contains("ML")) { String[]
	 * splValue = strValue.split("\\."); String btlValue=splValue[0]; String
	 * mlValue=splValue[1];
	 * 
	 * 
	 * String ml =" ML"; if(mlValue.length()>3) { mlValue=mlValue.substring(0,
	 * 3); } strVal = btlValue+"."+mlValue+ml; }else { strVal = strValue; }
	 * 
	 * }else { if(strValue.contains("ML")) { if(strValue.contains("ML.")) {
	 * String[] splMlOnlyValue = strValue.split(" ML"); String deciVal =
	 * splMlOnlyValue[0]; String ml =" ML"; if(deciVal.length()>3) {
	 * deciVal=deciVal.substring(0, 3); }
	 * 
	 * strValue = deciVal+ml;
	 * 
	 * }
	 * 
	 * if(strValue.contains(".")) { String[] splValue = strValue.split("\\.");
	 * String btlValue=splValue[0]; //String mlValue=splValue[1];
	 * 
	 * String ml =" ML"; if(btlValue.length()>3) {
	 * btlValue=btlValue.substring(0, 3); }
	 * 
	 * strVal = btlValue+ml; }else { strVal = strValue; }
	 * 
	 * 
	 * } } strValue=strVal; }else { strVal=strValue; }
	 * 
	 * 
	 * 
	 * return strVal; }
	 */

}
