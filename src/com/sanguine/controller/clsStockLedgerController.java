package com.sanguine.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DecimalFormat;
import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.crm.controller.clsDeliveryChallanController;
import com.sanguine.crm.controller.clsInvoiceController;
import com.sanguine.crm.controller.clsSalesReturnController;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStockFlashService;
import com.sanguine.service.clsUserMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStockLedgerController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;

	@Autowired
	clsMISController objMIS;

	@Autowired
	clsGRNController objGRN;

	@Autowired
	clsMaterialReturnController objMatReturn;

	@Autowired
	clsStkAdjustmentController objStkAdj;

	@Autowired
	clsMaterialReqController objMatReq;

	@Autowired
	clsStkTransferController objStkTransfer;

	@Autowired
	clsProductionController objProduction;

	@Autowired
	clsPurchaseReturnController objPurRet;

	@Autowired
	clsStockController objOpStk;

	@Autowired
	clsSetupMasterService objSetupMasterService;

	@Autowired
	clsDeliveryChallanController objDeliveryChallan;

	@Autowired
	clsInvoiceController objInvoice;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@Autowired
	private clsUserMasterService objUserMasterService;
	@Autowired
	private clsSalesReturnController  objsaleRet; 
	@RequestMapping(value = "/frmStockLedger", method = RequestMethod.GET)
	private ModelAndView funOpenStockLedger(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, HttpServletRequest req) {
		return funGetModelAndView(req);
	}

	@RequestMapping(value = "/frmStockLedgerFromStockFlash", method = RequestMethod.GET)
	private ModelAndView funOpenStockLedgerFromStockFlash(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, @RequestParam(value = "param1") String paramForStkLedger, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req) {
		paramForStkLedger = paramForStkLedger + "," + fDate + "," + tDate;
		// req.getSession().setAttribute("stkledger", paramForStkLedger);
		ModelAndView view = new ModelAndView("frmStockLedgerFromStockFlash", "command", objPropBean);
		view.addObject("stkledger", paramForStkLedger);
		view.addObject("flgStkFlash", "true");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		view.addObject("listProperty", mapProperty);
		HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
		if (mapLocation.isEmpty()) {
			mapLocation.put("", "");
		}

		view.addObject("LoggedInProp", propertyCode);
		view.addObject("LoggedInLoc", locationCode);

		mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
		view.addObject("listLocation", mapLocation);
		return view;
		// return funGetModelAndView1();
	}

	@RequestMapping(value = "/frmStockLedgerReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockDetailFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req, HttpServletResponse resp) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[0];
		String propCode = spParam1[1];
		String prodCode = spParam1[2];
		String type = spParam1[3];
		String qtyWithUOM = spParam1[4];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		Map<String, Map<String, ArrayList<Object>>> mapProductStock = new TreeMap<String, Map<String, ArrayList<Object>>>();
		DecimalFormat df = new DecimalFormat("#.###");
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		System.out.println(startDate);
		// 2015-06-01

		String propertyCode = "";
		String sql = "select strPropertyCode from tbllocationmaster " + " where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
		List listProperty = objGlobalService.funGetList(sql, "sql");
		Iterator itrPropCode = listProperty.iterator();
		if (itrPropCode.hasNext()) {
			propertyCode = itrPropCode.next().toString();
		}

		Map<String, String> hmAuthorisedForms = new HashMap<String, String>();
		sql = "select strFormName from tblworkflowforslabbasedauth " + "where strPropertyCode='" + propertyCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalService.funGetList(sql, "sql");
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String formName = itr.next().toString();
			hmAuthorisedForms.put(formName, formName);
		}

		List listTempStkLedger = null;

		String newToDate = "";
		if (!startDate.equals(fromDate)) {
			String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1;

			try {
				dt1 = obj.parse(tempFromDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dt1);
				cal.add(Calendar.DATE, -1);
				newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

				listTempStkLedger = funCalculateStock(prodCode, locCode, startDate, newToDate, qtyWithUOM, hmAuthorisedForms, startDate, req, resp);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String selectOpStk = "";
		double totalOpStk = 0;
		String uomRecv = "";
		String conRecipe = "";
		String uomIssue = "";
		String conIssue = "";
		String strOpStockWithUOM = "";
		if (null != listTempStkLedger) {
			double tempReceipts = 0, tempIssues = 0, rate = 0;
			for (int cnt = 0; cnt < listTempStkLedger.size(); cnt++) {
				// Object[] arrObj=(Object[])listTempStkLedger.get(cnt);
				// tempReceipts+=Math.rint(Double.parseDouble(arrObj[3].toString()));
				// tempIssues+=Math.rint(Double.parseDouble(arrObj[4].toString()));
				// rate=Double.parseDouble(arrObj[6].toString());
				// String
				// tempDate=arrObj[0].toString().split("-")[2]+"-"+arrObj[0].toString().split("-")[0]+"-"+arrObj[0].toString().split("-")[1];
				//
				List listTemp = (List) listTempStkLedger.get(cnt);
				if (qtyWithUOM.equals("No")) {
					tempReceipts += Double.parseDouble(listTemp.get(3).toString().split(" ")[0].toString());
					tempIssues += Double.parseDouble(listTemp.get(4).toString().split(" ")[0].toString());
					rate = Double.parseDouble(listTemp.get(6).toString());
					String tempDate = listTemp.get(0).toString().split("-")[2] + "-" + listTemp.get(0).toString().split("-")[0] + "-" + listTemp.get(0).toString().split("-")[1];
				} else {
					String[] uomConv = listTemp.get(7).toString().split("!");
					conRecipe = uomConv[0];
					conIssue = uomConv[1];
					uomRecv = uomConv[2];
					uomIssue = uomConv[3];

					// Receipts Qty Start
					if ((listTemp.get(3).toString().equals("0"))) {
						tempReceipts += Double.parseDouble(listTemp.get(3).toString());
					} else {
						double dblReptHigh = 0;
						double dblRectlow = 0;
						if (listTemp.get(3).toString().contains(uomConv[2])) {
							String[] highNLowData = listTemp.get(3).toString().split("\\.");
							// high no qty
							String[] highNo = highNLowData[0].split(" ");
							dblReptHigh = Double.parseDouble(highNo[0]) * Double.parseDouble(conRecipe);
							// low No Qty
							if (highNLowData.length > 1) {
								String[] lowNo = highNLowData[1].split(" ");
								dblRectlow = Double.parseDouble(lowNo[0]);
							}
							double totHighlowRecipt = dblReptHigh + dblRectlow;
							tempReceipts += totHighlowRecipt;
						} else {
							dblRectlow = Double.parseDouble(listTemp.get(3).toString().split(" ")[0].toString());
							tempReceipts += dblRectlow;
						}
					}
					// Receipts Qty Ends

					// Issue Qty Start
					if ((listTemp.get(4).toString().equals("0"))) {
						tempIssues += Double.parseDouble(listTemp.get(4).toString());
					} else {
						double dblIssHigh = 0;
						double dblIsslow = 0;
						if (listTemp.get(4).toString().contains(uomConv[2])) {
							String[] highNLowData = listTemp.get(4).toString().split("\\.");
							// high no qty
							String[] highNo = highNLowData[0].split(" ");
							dblIssHigh = Double.parseDouble(highNo[0]) * Double.parseDouble(conRecipe);
							// low No Qty
							if (highNLowData.length > 1) {
								String[] lowNo = highNLowData[1].split(" ");
								dblIsslow = Double.parseDouble(lowNo[0]);
							}
							double totHighlowIssue = dblIssHigh + dblIsslow;
							tempIssues += totHighlowIssue;
						} else {
							dblIsslow = Double.parseDouble(listTemp.get(4).toString().split(" ")[0].toString());
							tempIssues += dblIsslow;
						}

					}
					// Issue Qty Ends
					String tempDate = listTemp.get(0).toString().split("-")[2] + "-" + listTemp.get(0).toString().split("-")[0] + "-" + listTemp.get(0).toString().split("-")[1];
				}
				// arrObj[3].toString().split(" ")[0].toString();
				// tempReceipts+=Math.rint(Double.parseDouble(arrObj[3].toString().split(" ")[0].toString()));
				// tempIssues+=Math.rint(Double.parseDouble(arrObj[4].toString().split(" ")[0].toString()));

			}
			if (qtyWithUOM.equals("No")) {
				totalOpStk = tempReceipts - tempIssues;
			} else {
				totalOpStk = tempReceipts - tempIssues;
				if (listTempStkLedger.isEmpty()) {
					// totalOpStk=totalOpStk;
					conRecipe = "0";
				} else {
					totalOpStk = totalOpStk / Double.parseDouble(conRecipe);
				}
				Double totstkOp = new Double(totalOpStk);
				if (totstkOp.toString().contains(".")) {
					String[] strtot = totstkOp.toString().split("\\.");
					double highQty = Double.parseDouble(strtot[0]);
					Double dblTemplow = new Double(totstkOp - highQty);
					dblTemplow = Double.parseDouble(df.format(dblTemplow).toString());
					String lowQtyWithUOM = new Integer((int) (dblTemplow * Double.parseDouble(conRecipe))).toString() + " " + uomIssue;
					String highQtyWithUOM = strtot[0].toString() + " " + uomRecv;
					strOpStockWithUOM = highQtyWithUOM + "." + lowQtyWithUOM;
				}

			}
			if (totalOpStk < 0) {
				if (qtyWithUOM.equals("No")) {
					selectOpStk = " select '" + toDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, 0 Receipt" + "," + totalOpStk + " Issue,'Opening Stk' Name, " + rate + " Rate ";
					System.out.println(selectOpStk);
				} else {
					selectOpStk = " select '" + toDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, 0 Receipt" + ",'" + strOpStockWithUOM + "' Issue,'Opening Stk' Name, " + rate + " Rate, " + " CONCAT_WS('!',b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) UOMString  " + " from tblproductmaster b where b.strProdCode = '" + prodCode + "' ";
				}
			} else {
				if (qtyWithUOM.equals("No")) {
					selectOpStk = " select '" + fromDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, " + totalOpStk + " Receipt" + ",0 Issue,'Opening Stk' Name, " + rate + " Rate ";
					System.out.println(selectOpStk);
				} else {
					if (startDate.equals(fromDate)) {
						selectOpStk = "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate"
						// +
						// ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString "
								+ ", CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblinitialinventory a, tblinitialinvdtl b, tblproductmaster c " + "where a.strOpStkCode  = b.strOpStkCode and b.strProdCode=c.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
						if (!locCode.equalsIgnoreCase("All")) {
							selectOpStk += "and a.strLocCode='" + locCode + "' ";
						}
						if (null != hmAuthorisedForms.get("frmOpeningStock")) {
							selectOpStk += "and a.strAuthorise='Yes' ";
						}
						selectOpStk += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,b.strProdCode ";
					} else {
						selectOpStk = " select '" + fromDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, '" + strOpStockWithUOM + "' Receipt" + ",0 Issue,'Opening Stk' Name, " + rate + " Rate, " + " CONCAT_WS('!',b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) UOMString  " + " from tblproductmaster b where b.strProdCode = '" + prodCode + "' ";
						System.out.println(selectOpStk);
					}
				}
			}
		}

		sql = "";
		if (qtyWithUOM.equals("No")) {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate from "

			+ "(";
			if (!selectOpStk.isEmpty()) {
				sql += selectOpStk;
				sql += " union all ";
			} else {

				sql += "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue " + ",'Opening Stock' Name,dblCostPerUnit Rate " + "from tblinitialinventory a, tblinitialinvdtl b " + "where a.strOpStkCode  = b.strOpStkCode " + "and b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmOpeningStock")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtCreatedDate) = '" + startDate + "'  " + "group by a.dtCreatedDate,strProdCode "

				+ "union all ";

			}
			sql += "select a.dtMISDate TransDate,3 TransNo, 'MIS In' TransType, a.strMISCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue " + ",c.strLocName Name ,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b ,tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + " group by a.dtMISDate,a.strMISCode,a.strLocTo " + ""

			+ "union all "

			/*
			 * +
			 * "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue"
			 * + ",'Opening Stock' Name,dblCostPerUnit Rate " +
			 * "from tblinitialinventory a, tblinitialinvdtl b " +
			 * "where a.strOpStkCode  = b.strOpStkCode " +
			 * "and b.strProdCode = '"+prodCode+"' ";
			 * if(!locCode.equalsIgnoreCase("All")) { sql+=
			 * "and a.strLocCode='"+locCode+"' "; }
			 * if(null!=hmAuthorisedForms.get("frmOpeningStock")) { sql+=
			 * "and a.strAuthorise='Yes' "; } sql+=
			 * "and date(a.dtCreatedDate) >= '"
			 * +startDate+"' and date(a.dtCreatedDate) <= '" +toDate+"'  " +
			 * "group by a.dtCreatedDate,strProdCode "
			 * 
			 * + "union all "
			 */

			+ "select a.dtGRNDate TransDate,2 TransNo, 'GRN' TransType, a.strGRNCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strPName Name,b.dblUnitPrice Rate " + "from tblgrnhd a, tblgrndtl b,tblpartymaster c " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue"
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
			// + ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue" + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b, tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo "

			+ ""

			+ "union all "

			+ "select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt,  " + "ifnull(sum(b.dblQty),0) Issue ,c.strLocName Name,d.dblCostRM Rate  " + "from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode = b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode " + ""

			+ "UNION ALL  ";

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {
				sql += "	 SELECT a.dteInvDate TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tblinvoicehd a, tblinvoicedtl b, tbllocationmaster c,tblproductmaster d " + " where a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND "
						+ " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteInvDate) >= '" + fromDate + "' " + " AND DATE(a.dteInvDate) <= '" + toDate + "'  GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode ";
			} else {

				sql += "	 SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c,tblproductmaster d "
						+ " where a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND " + " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteDCDate) >= '" + fromDate + "' " + " AND DATE(a.dteDCDate) <= '" + toDate + "'  GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode ";

			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, IFNULL(SUM(b.dblQty),0) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate " + " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL  ";

			sql += "select a.dteDNDate TransDate,15 TransNo, 'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c	" + " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode   and b.strProdCode= '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += ") a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";
		} else {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate, UOMString from "

			+ "(";
			if (!selectOpStk.isEmpty()) {
				sql += selectOpStk;
				sql += " union all ";
			}
			sql += "select a.dtMISDate TransDate,3 TransNo, 'MIS In' TransType, a.strMISCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt " + ", 0 Issue ,c.strLocName Name " + ",b.dblUnitPrice Rate"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmishd a, tblmisdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + " ";

			sql += "union all ";

			if (startDate.equals(fromDate)) {
				sql += "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate"
				// +
				// ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString "
						+ ", CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblinitialinventory a, tblinitialinvdtl b, tblproductmaster c " + "where a.strOpStkCode  = b.strOpStkCode and b.strProdCode=c.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmOpeningStock")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,b.strProdCode "

				+ "union all ";
			}

			sql += "select a.dtGRNDate TransDate,2 TransNo, 'GRN' TransType, a.strGRNCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strPName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblgrnhd a, tblgrndtl b,tblpartymaster c, tblproductmaster d " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d  " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue "
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// // +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
			// + ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate "
					// +
					// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c, tblproductmaster d " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmishd a, tblmisdtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all "

			+ " select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt, " + " funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue , " + " c.strLocName Name,'1' Rate  "
			// +
			// " funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c, tblproductmaster d " + " where a.strSRCode  = b.strSRCode and a.strLocCode=c.strLocCode   and b.strProdCode=d.strProdCode  " + " and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += " and a.strLocCode='" + locCode + "' ";
			}
			sql += "and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode "

			+ ""

			+ " UNION ALL ";

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {

				sql += " SELECT a.dteInvDate TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate, " + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tblinvoicehd a, tblinvoicedtl b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteInvDate) >= '" + fromDate + "' AND DATE(a.dteInvDate) <= '" + toDate + "' " + " GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode  ";
			} else {

				sql += " SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate, " + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteDCDate) >= '" + fromDate + "' AND DATE(a.dteDCDate) <= '" + toDate + "' " + " GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode  ";

			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' AND b.strProdCode=d.strProdCode ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL ";

			sql += "select a.dteDNDate TransDate,15 TransNo,  'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c,tblproductmaster d	"
					+ " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode and b.strProdCode='" + prodCode + "' AND b.strProdCode=d.strProdCode  ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += " ) a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";
		}

		System.out.println(sql);
		List listStkLedger = objGlobalService.funGetList(sql, "sql");
		List listReturn = new ArrayList();
		Map<String, List> hmProduction = new HashMap<String, List>();
		for (int i = 0; i < listStkLedger.size(); i++) {
			Object[] obj = (Object[]) listStkLedger.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listReturn.add(dataList);
		}

		// sql="select b.strProdCode  from tblproductionhd a, tblproductiondtl b where a.strLocCode='L000001' "
		// +" and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
		// ;
		//
		//
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");

		sql = "SELECT b.strChildCode,b.dblQty,a.strParentCode,c.dblRecipeConversion " + " ,c.strReceivedUOM,c.strRecipeUOM " + " ,c.dblCostRM ,CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblbommasterhd a ,tblbommasterdtl b,tblproductmaster c  " + " where a.strBOMCode=b.strBOMCode and b.strChildCode='" + prodCode + "' "
				+ " and b.strChildCode=c.strProdCode and a.strClientCode=b.strClientCode " + " and b.strClientCode=c.strClientCode and c.strClientCode='" + clientCode + "'  ";

		List listChildProdStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int cnt = 0; cnt < listChildProdStkLedger.size(); cnt++) {
			Object objParent[] = (Object[]) listChildProdStkLedger.get(cnt);

			if (qtyWithUOM.equals("No")) {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, 0 Receipt, ifnull(sum(b.dblQtyProd),0) Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '"
						+ objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt

						double qty = Double.parseDouble(objParent[1].toString()) / Double.parseDouble(objParent[3].toString()) * Double.parseDouble(obj[4].toString());
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString());
						}

						dataList.add(qty);// Issue
						dataList.add(obj[5]);// LocName
						dataList.add(obj[6]);// Rate

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			} else {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",0 Receipt,  funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
				// +
				// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString  " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt
						String[] strProdQtyUOM = obj[4].toString().split(" ");
						strProdQtyUOM[1] = objParent[5].toString();
						double qty = Double.parseDouble(objParent[1].toString()) / Double.parseDouble(objParent[3].toString()) * Double.parseDouble(strProdQtyUOM[0]);
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString().split(" ")[0]);
						}
						String strProdUom = "";
						for (int a = 1; a < strProdQtyUOM.length; a++) {
							strProdUom = strProdUom + "" + strProdQtyUOM[a];

						}
						String qtyUOM = funGetDispalyUOMQty(prodCode, qty, clientCode);

						dataList.add(qtyUOM);// Issue&prodUOM
						dataList.add(obj[5]);// LocName
						dataList.add(objParent[6].toString());// Rate
						dataList.add(objParent[7].toString());

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			}
		}

		for (Map.Entry<String, List> enty : hmProduction.entrySet()) {

			List listChildTotlQty = enty.getValue();
			listReturn.add(listChildTotlQty);
		}

		// sql=
		// "SELECT  count(*) from tblbommasterhd a where a.strParentCode='"+prodCode+"' ";
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");
		//
		// if (listParentProdStkLedger != null)
		// {
		//
		//
		if (qtyWithUOM.equals("No")) {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate ,6 TransNo " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
					+ "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		} else {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM)  Receipt, 0 Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString ,6 TransNo " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		}

		// }

		List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int i = 0; i < listProductionStkLedger.size(); i++) {
			Object[] obj = (Object[]) listProductionStkLedger.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listReturn.add(dataList);

		}

		return listReturn;
	}

	private List funCalculateStock(String prodCode, String locCode, String fromDate, String toDate, String qtyWithUOM, Map hmAuthorisedForms, String startDate, HttpServletRequest req, HttpServletResponse resp) {
		String sql = "";
		if (qtyWithUOM.equals("No")) {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate from ( ";
			sql += "select a.dtMISDate TransDate,2 TransNo, 'MIS In' TransType, a.strMISCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue " + ",c.strLocName Name ,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b ,tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + " group by a.dtMISDate,a.strMISCode,a.strLocTo " + ""

			+ "union all "

			+ "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate " + "from tblinitialinventory a, tblinitialinvdtl b " + "where a.strOpStkCode  = b.strOpStkCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,strProdCode "

			+ "union all "

			+ "select a.dtGRNDate TransDate,3 TransNo, 'GRN' TransType, a.strGRNCode RefNo, ifnull(sum(b.dblQty-b.dblRejected),0) Receipt, 0 Issue" + ",c.strPName Name,b.dblUnitPrice Rate " + "from tblgrnhd a, tblgrndtl b,tblpartymaster c " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue"
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
			// + ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue" + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b, tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all "

			+ "select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt,  " + "ifnull(sum(b.dblQty),0) Issue ,c.strLocName Name,d.dblCostRM Rate  " + "from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode = b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode and "
					+ " b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode " + ""

			+ "UNION ALL  ";

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {
				sql += "	 SELECT a.dteInvDate TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tblinvoicehd a, tblinvoicedtl b, tbllocationmaster c,tblproductmaster d " + " where a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND "
						+ " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteInvDate) >= '" + fromDate + "' " + " AND DATE(a.dteInvDate) <= '" + toDate + "'  GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode ";
			} else {
				sql += "	 SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c,tblproductmaster d "
						+ " where a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND " + " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteDCDate) >= '" + fromDate + "' " + " AND DATE(a.dteDCDate) <= '" + toDate + "'  GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode ";
			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, IFNULL(SUM(b.dblQty),0) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate " + " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL  ";

			sql += "select a.dteDNDate TransDate,15 TransNo, 'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c	" + " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode   and b.strProdCode= '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += ") a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";
		} else {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate, UOMString from ( ";

			sql += "select a.dtMISDate TransDate,2 TransNo, 'MIS In' TransType, a.strMISCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt " + ", 0 Issue ,c.strLocName Name " + ",b.dblUnitPrice Rate"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmishd a, tblmisdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all "

			+ "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblinitialinventory a, tblinitialinvdtl b, tblproductmaster c " + "where a.strOpStkCode  = b.strOpStkCode and b.strProdCode=c.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmOpeningStock")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,b.strProdCode "

			+ "union all "

			+ "select a.dtGRNDate TransDate,3 TransNo, 'GRN' TransType, a.strGRNCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty-b.dblRejected),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strPName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblgrnhd a, tblgrndtl b,tblpartymaster c, tblproductmaster d " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ",  funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d  " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue "
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// // +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
			// + ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate "
					// +
					// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c, tblproductmaster d " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ", c.strLocName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + "  from tblmishd a, tblmisdtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all "

			+ " select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt, " + " funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue , " + " c.strLocName Name,'1' Rate  "
			// +
			// "  funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c, tblproductmaster d " + " where a.strSRCode  = b.strSRCode and a.strLocCode=c.strLocCode   and b.strProdCode=d.strProdCode  " + " and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += " and a.strLocCode='" + locCode + "' ";
			}
			sql += "and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL ";

			String propCode = req.getSession().getAttribute("propertyCode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (!objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {
				sql += " SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate, " + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteDCDate) >= '" + fromDate + "' AND DATE(a.dteDCDate) <= '" + toDate + "' " + " GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode  ";
			} else {

				sql += " SELECT a.dteInvDate  TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate," + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tblinvoicehd a, tblinvoicedtl  b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteInvDate) >= '" + fromDate + "' AND DATE(a.dteInvDate) <= '" + toDate + "' " + " GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode  ";
			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' AND b.strProdCode=d.strProdCode ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL ";

			sql += "select a.dteDNDate TransDate,15 TransNo,  'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c,tblproductmaster d	"
					+ " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode and b.strProdCode='" + prodCode + "' AND b.strProdCode=d.strProdCode  ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += ") a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";

		}
		System.out.println(sql);
		List listStkLedger = objGlobalService.funGetList(sql, "sql");
		List listReturn = new ArrayList();
		Map<String, List> hmProduction = new HashMap<String, List>();
		for (int i = 0; i < listStkLedger.size(); i++) {
			Object[] obj = (Object[]) listStkLedger.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listReturn.add(dataList);
		}

		// sql="select b.strProdCode  from tblproductionhd a, tblproductiondtl b where a.strLocCode='L000001' "
		// +" and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
		// ;
		//
		//
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");

		sql = "SELECT b.strChildCode,b.dblQty,a.strParentCode from tblbommasterhd a ,tblbommasterdtl b  where a.strBOMCode=b.strBOMCode and b.strChildCode='" + prodCode + "' ";

		List listChildProdStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int cnt = 0; cnt < listChildProdStkLedger.size(); cnt++) {
			Object objParent[] = (Object[]) listChildProdStkLedger.get(cnt);
			objParent[2].toString();

			if (qtyWithUOM.equals("No")) {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, 0 Receipt, ifnull(sum(b.dblQtyProd),0) Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '"
						+ objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt

						double qty = Double.parseDouble(objParent[1].toString()) * Double.parseDouble(obj[4].toString());
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString());
						}

						dataList.add(qty);// Issue
						dataList.add(obj[5]);// LocName
						dataList.add(obj[6]);// Rate

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			} else {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",0 Receipt,  funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
				// +
				// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString  " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt
						String[] strProdQtyUOM = obj[4].toString().split(" ");
						double qty = Double.parseDouble(objParent[1].toString()) * Double.parseDouble(strProdQtyUOM[0]);
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString().split(" ")[0]);
						}
						String strProdUom = "";
						for (int a = 1; a < strProdQtyUOM.length; a++) {
							strProdUom = strProdUom + "" + strProdQtyUOM[a];

						}
						dataList.add(qty + " " + strProdUom);// Issue&prodUOM
						dataList.add(obj[5]);// LocName
						dataList.add(obj[6]);// Rate
						dataList.add(obj[7]);

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			}
		}

		for (Map.Entry<String, List> enty : hmProduction.entrySet()) {

			List listChildTotlQty = enty.getValue();
			listReturn.add(listChildTotlQty);
		}

		// sql=
		// "SELECT  count(*) from tblbommasterhd a where a.strParentCode='"+prodCode+"' ";
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");
		//
		// if (listParentProdStkLedger != null)
		// {
		//
		//
		if (qtyWithUOM.equals("No")) {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate ,6 TransNo " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
					+ "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		} else {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM)  Receipt, 0 Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString ,6 TransNo " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		}

		// }

		List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int i = 0; i < listProductionStkLedger.size(); i++) {
			Object[] obj = (Object[]) listProductionStkLedger.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listReturn.add(dataList);

		}

		return listReturn;
	}

	public Map<String, ArrayList<Object>> funCalculateStockForTrans(String prodCode, String locCode, String fromDate, String toDate, String clientCode, String transType, String startDate) {
		String flgQueryType = "hql";
		double finalStock = 0, grnQty = 0;
		String sql = "", trans = "Receipt";
		Map<String, ArrayList<Object>> mapTransactionStock = new HashMap<String, ArrayList<Object>>();

		switch (transType) {
		case "OPSTK":
			trans = "Receipt";
			sql = "select a.strOpStkCode,date(a.dtCreatedDate),sum(b.dblQty) " + "from clsInitialInventoryModel a,clsOpeningStkDtl b " + "where a.strOpStkCode=b.strOpStkCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtCreatedDate) between '" + startDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strOpStkCode,b.strProdCode";
			break;

		case "GRN":
			trans = "Receipt";
			sql = "select a.strGRNCode,date(a.dtGRNDate),sum(b.dblQty-b.dblRejected) " + "from clsGRNHdModel a,clsGRNDtlModel b " + "where a.strGRNCode=b.strGRNCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtGRNDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strGRNCode,b.strProdCode order by a.dtGRNDate ";
			break;

		case "StkAdjIn":
			trans = "Receipt";
			sql = "select a.strSACode,date(a.dtSADate),sum(b.dblQty) " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' " + " and b.strType='IN'";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strSACode,b.strProdCode";
			break;

		case "StkTransIn":
			trans = "Receipt";
			sql = "select a.strSTCode,date(a.dtSTDate),sum(b.dblQty) " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			sql += "group by b.strSTCode,b.strProdCode";
			break;

		case "MaterialReturnIn":
			trans = "Receipt";
			sql = "select a.strMRetCode,date(a.dtMRetDate),sum(b.dblQty) " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			sql += "group by b.strMRetCode,b.strProdCode";
			break;

		case "Production":
			trans = "Receipt";
			sql = "select a.strPDCode,date(a.dtPDDate),sum(b.dblQtyProd) " + "from clsProductionHdModel a,clsProductionDtlModel b " + "where a.strPDCode=b.strPDCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtPDDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strPDCode,b.strProdCode";
			break;

		case "PurchaseReturn":
			trans = "Issue";
			sql = "select a.strPRCode,date(a.dtPRDate),sum(b.dblQty) " + "from clsPurchaseReturnHdModel a,clsPurchaseReturnDtlModel b " + "where a.strPRCode=b.strPRCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtPRDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strPRCode,b.strProdCode";
			break;

		case "MISIn":
			trans = "Receipt";
			flgQueryType = "sql";
			sql = "select a.strMISCode,date(a.dtMISDate),sum(b.dblQty) " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			sql += "group by b.strMISCode,b.strProdCode order by a.dtMISDate ";
			break;

		case "StkAdjOut":
			trans = "Issue";
			sql = "select a.strSACode,date(a.dtSADate),sum(b.dblQty) " + "from clsStkAdjustmentHdModel a,clsStkAdjustmentDtlModel b " + "where a.strSACode=b.strSACode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSADate) between '" + fromDate + "' and '" + toDate + "' " + " and b.strType='OUT'";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			sql += "group by b.strSACode,b.strProdCode";
			break;

		case "MISOut":
			trans = "Issue";
			flgQueryType = "sql";
			sql = "select a.strMISCode,date(a.dtMISDate),sum(b.dblQty) " + "from tblmishd a,tblmisdtl b " + "where a.strMISCode=b.strMISCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMISDate) between '" + fromDate + "' and '" + toDate + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			sql += "group by b.strMISCode,b.strProdCode order by a.dtMISDate ";
			break;

		case "StkTransOut":
			trans = "Receipt";
			sql = "select a.strSTCode,date(a.dtSTDate),sum(b.dblQty) " + "from clsStkTransferHdModel a,clsStkTransferDtlModel b " + "where a.strSTCode=b.strSTCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtSTDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			sql += "group by b.strSTCode,b.strProdCode";
			break;

		case "MaterialReturnOut":
			trans = "Receipt";
			sql = "select a.strMRetCode,date(a.dtMRetDate),sum(b.dblQty) " + "from clsMaterialReturnHdModel a,clsMaterialReturnDtlModel b " + "where a.strMRetCode=b.strMRetCode and b.strProdCode='" + prodCode + "' " + "and date(a.dtMRetDate) between '" + fromDate + "' and '" + toDate + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			sql += "group by b.strMRetCode,b.strProdCode";
			break;
		}

		if (sql.trim().length() > 0) {
			ArrayList list = (ArrayList) objGlobalService.funGetList(sql, flgQueryType);
			if (list.size() > 0) {
				for (int cnt = 0; cnt < list.size(); cnt++) {
					Object[] arrObj = (Object[]) list.get(cnt);
					ArrayList listTemp = new ArrayList<Object>();
					listTemp.add(arrObj[0].toString());
					listTemp.add(arrObj[1].toString());
					System.out.println("Date=" + arrObj[1].toString());
					if (trans.equalsIgnoreCase("Receipt")) {
						listTemp.add(arrObj[2].toString());
						listTemp.add("R");
					} else {
						listTemp.add(arrObj[2].toString());
						listTemp.add("I");
					}

					System.out.println(arrObj[0]);
					mapTransactionStock.put(arrObj[0].toString(), listTemp);
				}
			}
		}

		return mapTransactionStock;
	}

	/*
	 * private ModelAndView funGetModelAndView1() { ModelAndView
	 * objModelView=new ModelAndView("frmStockLedgerFromStockFlash");
	 * 
	 * Map<String, String> mapProperty=
	 * objGlobalService.funGetPropertyList("001"); if(mapProperty.isEmpty()) {
	 * mapProperty.put("", ""); }
	 * objModelView.addObject("listProperty",mapProperty); Map<String, String>
	 * mapLocation= objGlobalService.funGetLocationList("001");
	 * if(mapLocation.isEmpty()) { mapLocation.put("", ""); }
	 * objModelView.addObject("listLocation",mapLocation); return objModelView;
	 * }
	 */

	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		ModelAndView objModelView = new ModelAndView("frmStockLedger");
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		String usercode = req.getSession().getAttribute("usercode").toString();
		if (usercode.equalsIgnoreCase("SANGUINE")) {
			Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
			if (mapProperty.isEmpty()) {
				mapProperty.put("", "");
			}
			objModelView.addObject("listProperty", mapProperty);
			HashMap<String, String> mapLocation = objGlobalService.funGetLocationList(propertyCode, clientCode);
			if (mapLocation.isEmpty()) {
				mapLocation.put("", "");
			}

			objModelView.addObject("LoggedInProp", propertyCode);
			objModelView.addObject("LoggedInLoc", locationCode);

			mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
			objModelView.addObject("listLocation", mapLocation);
		} else {
			Map<String, String> mapProperty = objUserMasterService.funGetUserBasedProperty(usercode, clientCode);
			// Map<String, String> mapProperty=
			// objGlobalService.funGetPropertyList(clientCode);
			if (mapProperty.isEmpty()) {
				mapProperty.put("", "");
			}

			objModelView.addObject("listProperty", mapProperty);
			objModelView.addObject("LoggedInProp", propertyCode);
			objModelView.addObject("LoggedInLoc", locationCode);

			HashMap<String, String> mapLocation = objUserMasterService.funGetUserBasedPropertyLocation(propertyCode, usercode, clientCode);
			if (mapLocation.isEmpty()) {
				mapLocation.put("", "");
			}
			mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
			objModelView.addObject("listLocation", mapLocation);
		}
		return objModelView;
	}

	
    @RequestMapping(value = "/openSlip", method = RequestMethod.GET)
	private void funCallJsperReport(@RequestParam("docCode") String docCode, HttpServletResponse resp, HttpServletRequest req) {
		String[] sp = docCode.split(",");
		docCode = sp[0];
		String currCode = req.getSession().getAttribute("currencyCode").toString();
		switch (sp[1]) {
		case "MIS In":
			objMIS.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Opening Stk":
			objOpStk.funCallOpeningReport(docCode, "pdf", resp, req);
			break;

		case "GRN":
			objGRN.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Stock Adjustment In":
			objStkAdj.funCallReportBeanWise(docCode, "pdf", resp, req);
			break;

		case "Stock Transfer In":
			objStkTransfer.funCallReport(docCode, "pdf", resp, req);
			break;

		case "StkTrans In":
			objStkTransfer.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Material Return In":
			objMatReturn.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Production":
			// objProduction.funCallReport(docCode,"pdf",resp,req);
			break;

		case "Purchase Return":
			objPurRet.funCallReport(docCode,currCode, "pdf", resp, req);
			break;

		case "StkAdj Out":
			objStkAdj.funCallReportBeanWise(docCode, "pdf", resp, req);
			break;

		case "MIS Out":
			objMIS.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Stock Transfer Out":
			objStkTransfer.funCallReport(docCode, "pdf", resp, req);
			break;

		case "StkTrans Out":
			objStkTransfer.funCallReport(docCode, "pdf", resp, req);
			break;

		case "Delivery challan":
			objDeliveryChallan.funCallReportDeliveryChallanReport(docCode, "pdf", resp, req);
			break;

		case "Delivery Chal.":
			objDeliveryChallan.funCallReportDeliveryChallanReport(docCode, "pdf", resp, req);
			break;


		case "Invoice":
			//objInvoice.funOpenInvoiceRetailReport(docCode, "pdf", resp, req);
			objInvoice.funCallInvoiceFormat(docCode, "pdf", resp, req);
			break;
			
		case "StkAdj In":
			objStkAdj.funCallReportBeanWise(docCode, "pdf", resp, req);
			break;
			
		case "Sales Ret":
			clsReportBean objBean=new clsReportBean();
		    objBean.setStrSRCode(docCode);
		    objBean.setStrDocType("PDF");
		    objsaleRet.funCallReport(objBean,"", resp, req) ;
		break;

		}

	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmExportStockLedger", method = RequestMethod.GET)
	private ModelAndView funExportStockLedger(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, HttpServletRequest req, HttpServletResponse resp) {

		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[0];
		String propCode = spParam1[1];
		String prodCode = spParam1[2];
		String type = spParam1[3];
		String qtyWithUOM = spParam1[4];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		List listStock = new ArrayList();
		listStock.add("StockLedger_" + fromDate + "to" + toDate + "_" + userCode);
		String[] ExcelHeader = { "Transaction Date", "CostCenter/Supplier Name", "Transaction Type", "Ref No", "Receipt", "Issue", "Balance", "Rate", "Value" };
		listStock.add(ExcelHeader);
		Map<String, Map<String, ArrayList<Object>>> mapProductStock = new TreeMap<String, Map<String, ArrayList<Object>>>();
		DecimalFormat df3 = new DecimalFormat("#.###");
		DecimalFormat df3Zeors = new DecimalFormat("0.000");
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		System.out.println(startDate);
		// 2015-06-01

		String propertyCode = "";
		String sql = "select strPropertyCode from tbllocationmaster " + " where strLocCode='" + locCode + "' and strClientCode='" + clientCode + "'";
		List listProperty = objGlobalService.funGetList(sql, "sql");
		Iterator itrPropCode = listProperty.iterator();
		if (itrPropCode.hasNext()) {
			propertyCode = itrPropCode.next().toString();
		}

		Map<String, String> hmAuthorisedForms = new HashMap<String, String>();
		sql = "select strFormName from tblworkflowforslabbasedauth " + "where strPropertyCode='" + propertyCode + "' and strClientCode='" + clientCode + "'";
		List list = objGlobalService.funGetList(sql, "sql");
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String formName = itr.next().toString();
			hmAuthorisedForms.put(formName, formName);
		}

		List listTempStkLedger = null;

		String newToDate = "";
		if (!startDate.equals(fromDate)) {
			String tempFromDate = fromDate.split("-")[2] + "-" + fromDate.split("-")[1] + "-" + fromDate.split("-")[0];
			SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy");
			Date dt1;

			try {
				dt1 = obj.parse(tempFromDate);
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(dt1);
				cal.add(Calendar.DATE, -1);
				newToDate = (cal.getTime().getYear() + 1900) + "-" + (cal.getTime().getMonth() + 1) + "-" + (cal.getTime().getDate());

				listTempStkLedger = funCalculateStock(prodCode, locCode, startDate, newToDate, qtyWithUOM, hmAuthorisedForms, startDate, req, resp);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String selectOpStk = "";
		double totalOpStk = 0;
		String uomRecv = "";
		String conRecipe = "";
		String uomIssue = "";
		String conIssue = "";
		String strOpStockWithUOM = "";

		if (null != listTempStkLedger) {
			double tempReceipts = 0, tempIssues = 0, rate = 0;
			for (int cnt = 0; cnt < listTempStkLedger.size(); cnt++) {
				// Object[] arrObj=(Object[])listTempStkLedger.get(cnt);
				// tempReceipts+=Math.rint(Double.parseDouble(arrObj[3].toString()));
				// tempIssues+=Math.rint(Double.parseDouble(arrObj[4].toString()));
				// rate=Double.parseDouble(arrObj[6].toString());
				// String
				// tempDate=arrObj[0].toString().split("-")[2]+"-"+arrObj[0].toString().split("-")[0]+"-"+arrObj[0].toString().split("-")[1];
				//
				List listTemp = (List) listTempStkLedger.get(cnt);
				if (qtyWithUOM.equals("No")) {
					tempReceipts += Double.parseDouble(listTemp.get(3).toString().split(" ")[0].toString());
					tempIssues += Double.parseDouble(listTemp.get(4).toString().split(" ")[0].toString());
					rate = Double.parseDouble(listTemp.get(6).toString());
					String tempDate = listTemp.get(0).toString().split("-")[2] + "-" + listTemp.get(0).toString().split("-")[0] + "-" + listTemp.get(0).toString().split("-")[1];
				} else {
					String[] uomConv = listTemp.get(7).toString().split("!");
					conRecipe = uomConv[0];
					conIssue = uomConv[1];
					uomRecv = uomConv[2];
					uomIssue = uomConv[3];

					// Receipts Qty Start
					if ((listTemp.get(3).toString().equals("0"))) {
						tempReceipts += Double.parseDouble(listTemp.get(3).toString());
					} else {
						double dblReptHigh = 0;
						double dblRectlow = 0;
						if (listTemp.get(3).toString().contains(uomConv[2])) {
							String[] highNLowData = listTemp.get(3).toString().split("\\.");
							// high no qty
							String[] highNo = highNLowData[0].split(" ");
							dblReptHigh = Double.parseDouble(highNo[0]) * Double.parseDouble(conRecipe);
							// low No Qty
							if (highNLowData.length > 1) {
								String[] lowNo = highNLowData[1].split(" ");
								dblRectlow = Double.parseDouble(lowNo[0]);
							}
							double totHighlowRecipt = dblReptHigh + dblRectlow;
							tempReceipts += totHighlowRecipt;
						} else {
							dblRectlow = Double.parseDouble(listTemp.get(3).toString().split(" ")[0].toString());
							tempReceipts += dblRectlow;
						}
					}
					// Receipts Qty Ends

					// Issue Qty Start
					if ((listTemp.get(4).toString().equals("0"))) {
						tempIssues += Double.parseDouble(listTemp.get(4).toString());
					} else {
						double dblIssHigh = 0;
						double dblIsslow = 0;
						if (listTemp.get(4).toString().contains(uomConv[2])) {
							String[] highNLowData = listTemp.get(4).toString().split("\\.");
							// high no qty
							String[] highNo = highNLowData[0].split(" ");
							dblIssHigh = Double.parseDouble(highNo[0]) * Double.parseDouble(conRecipe);
							// low No Qty
							if (highNLowData.length > 1) {
								String[] lowNo = highNLowData[1].split(" ");
								dblIsslow = Double.parseDouble(lowNo[0]);
							}
							double totHighlowIssue = dblIssHigh + dblIsslow;
							tempIssues += totHighlowIssue;
						} else {
							dblIsslow = Double.parseDouble(listTemp.get(4).toString().split(" ")[0].toString());
							tempIssues += dblIsslow;
						}

					}
					// Issue Qty Ends
					String tempDate = listTemp.get(0).toString().split("-")[2] + "-" + listTemp.get(0).toString().split("-")[0] + "-" + listTemp.get(0).toString().split("-")[1];
				}
				// arrObj[3].toString().split(" ")[0].toString();
				// tempReceipts+=Math.rint(Double.parseDouble(arrObj[3].toString().split(" ")[0].toString()));
				// tempIssues+=Math.rint(Double.parseDouble(arrObj[4].toString().split(" ")[0].toString()));

			}
			if (qtyWithUOM.equals("No")) {
				totalOpStk = tempReceipts - tempIssues;
			} else {
				totalOpStk = tempReceipts - tempIssues;
				if (listTempStkLedger.isEmpty()) {
					conRecipe = "0";
				} else {
					totalOpStk = totalOpStk / Double.parseDouble(conRecipe);
				}
				Double totstkOp = new Double(totalOpStk);
				if (totstkOp.toString().contains(".")) {
					String[] strtot = totstkOp.toString().split("\\.");
					double highQty = Double.parseDouble(strtot[0]);
					Double dblTemplow = new Double(totstkOp - highQty);
					dblTemplow = Double.parseDouble(df3.format(dblTemplow).toString());
					String lowQtyWithUOM = new Integer((int) (dblTemplow * Double.parseDouble(conRecipe))).toString() + " " + uomIssue;
					String highQtyWithUOM = strtot[0].toString() + " " + uomRecv;
					strOpStockWithUOM = highQtyWithUOM + "." + lowQtyWithUOM;
				}

			}
			if (totalOpStk < 0) {
				selectOpStk = " select '" + toDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, 0 Receipt" + "," + totalOpStk + " Issue,'Opening Stk' Name, " + rate + " Rate ";
				System.out.println(selectOpStk);

			} else {
				if (qtyWithUOM.equals("No")) {
					selectOpStk = " select '" + fromDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, " + totalOpStk + " Receipt" + ",0 Issue,'Opening Stk' Name, " + rate + " Rate ";
					System.out.println(selectOpStk);
				} else {
					if (startDate.equals(fromDate)) {
						selectOpStk = "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate"
						// +
						// ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString "
								+ ", CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblinitialinventory a, tblinitialinvdtl b, tblproductmaster c " + "where a.strOpStkCode  = b.strOpStkCode and b.strProdCode=c.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
						if (!locCode.equalsIgnoreCase("All")) {
							selectOpStk += "and a.strLocCode='" + locCode + "' ";
						}
						if (null != hmAuthorisedForms.get("frmOpeningStock")) {
							selectOpStk += "and a.strAuthorise='Yes' ";
						}
						selectOpStk += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,b.strProdCode ";
					} else {
						selectOpStk = " select '" + fromDate + "' TransDate, 1 TransNo " + ",'Opening Stk' TransType, 'OP' RefNo, '" + strOpStockWithUOM + "' Receipt" + ",0 Issue,'Opening Stk' Name, " + rate + " Rate, " + " CONCAT_WS('!',b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) UOMString  " + " from tblproductmaster b where b.strProdCode = '" + prodCode + "' ";
						System.out.println(selectOpStk);
					}
				}
			}
		}

		// if(null!=listTempStkLedger)
		// {
		// double tempReceipts=0,tempIssues=0,rate=0;
		// for(int cnt=0;cnt<listTempStkLedger.size();cnt++)
		// {
		// Object[] arrObj=(Object[])listTempStkLedger.get(cnt);
		// // tempReceipts+=Math.rint(Double.parseDouble(arrObj[3].toString()));
		// // tempIssues+=Math.rint(Double.parseDouble(arrObj[4].toString()));
		// tempReceipts+=Double.parseDouble(arrObj[3].toString());
		// tempIssues+=Double.parseDouble(arrObj[4].toString());
		// rate=Double.parseDouble(arrObj[6].toString());
		// String
		// tempDate=arrObj[0].toString().split("-")[2]+"-"+arrObj[0].toString().split("-")[0]+"-"+arrObj[0].toString().split("-")[1];
		// }
		//
		// totalOpStk=tempReceipts-tempIssues;
		// if(totalOpStk<0)
		// {
		// selectOpStk=" select '"+toDate+"' TransDate, 1 TransNo "
		// + ",'Opening Stk' TransType, 'OP' RefNo, 0 Receipt"
		// + ","+totalOpStk+" Issue,'Opening Stk' Name, "+rate+" Rate ";
		// System.out.println(selectOpStk);
		// }
		// else
		// {
		// selectOpStk=" select '"+fromDate+"' TransDate, 1 TransNo "
		// + ",'Opening Stk' TransType, 'OP' RefNo, "+totalOpStk+" Receipt"
		// + ",0 Issue,'Opening Stk' Name, "+rate+" Rate ";
		// System.out.println(selectOpStk);
		// }
		// }

		sql = "";
		if (qtyWithUOM.equals("No")) {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate from "

			+ "(";
			if (!selectOpStk.isEmpty()) {
				sql += selectOpStk;
				sql += " union all ";
			} else {

				sql += "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue " + ",'Opening Stock' Name,dblCostPerUnit Rate " + "from tblinitialinventory a, tblinitialinvdtl b " + "where a.strOpStkCode  = b.strOpStkCode " + "and b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmOpeningStock")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtCreatedDate) = '" + startDate + "'  " + "group by a.dtCreatedDate,strProdCode "

				+ "union all ";

			}
			sql += "select a.dtMISDate TransDate,3 TransNo, 'MIS In' TransType, a.strMISCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue " + ",c.strLocName Name ,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b ,tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + " group by a.dtMISDate,a.strMISCode,a.strLocTo " + ""

			+ "union all "

			/*
			 * +
			 * "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue"
			 * + ",'Opening Stock' Name,dblCostPerUnit Rate " +
			 * "from tblinitialinventory a, tblinitialinvdtl b " +
			 * "where a.strOpStkCode  = b.strOpStkCode " +
			 * "and b.strProdCode = '"+prodCode+"' ";
			 * if(!locCode.equalsIgnoreCase("All")) { sql+=
			 * "and a.strLocCode='"+locCode+"' "; }
			 * if(null!=hmAuthorisedForms.get("frmOpeningStock")) { sql+=
			 * "and a.strAuthorise='Yes' "; } sql+=
			 * "and date(a.dtCreatedDate) >= '"
			 * +startDate+"' and date(a.dtCreatedDate) <= '" +toDate+"'  " +
			 * "group by a.dtCreatedDate,strProdCode "
			 * 
			 * + "union all "
			 */

			+ "select a.dtGRNDate TransDate,2 TransNo, 'GRN' TransType, a.strGRNCode RefNo, ifnull(sum(b.dblQty-b.dblRejected),0) Receipt, 0 Issue" + ",c.strPName Name,b.dblUnitPrice Rate " + "from tblgrnhd a, tblgrndtl b,tblpartymaster c " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo, ifnull(sum(b.dblQty),0) Receipt, 0 Issue" + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue"
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
					+ ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue" + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name, b.dblRate Rate " + "from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate " + "from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode " + "and b.strProdCode = '" + prodCode
					+ "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,'1' Rate " + "from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt, ifnull(sum(b.dblQty),0) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate " + "from tblmishd a, tblmisdtl b, tbllocationmaster c " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo "

			+ ""

			+ "union all "

			+ "select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt,  " + "ifnull(sum(b.dblQty),0) Issue ,c.strLocName Name,d.dblCostRM Rate  " + "from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode = b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode and "
					+ " b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode " + "union all ";

			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {
				sql += "	 SELECT a.dteInvDate TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tblinvoicehd a, tblinvoicedtl b, tbllocationmaster c,tblproductmaster d " + " where a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND "
						+ " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteInvDate) >= '" + fromDate + "' " + " AND DATE(a.dteInvDate) <= '" + toDate + "'  GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode ";
			} else {

				sql += "	 SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0) Issue,c.strLocName Name,d.dblUnitPrice Rate " + "  FROM tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c,tblproductmaster d "
						+ " where a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode AND " + " b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode = '" + locCode + "' ";
				}
				sql += " AND DATE(a.dteDCDate) >= '" + fromDate + "' " + " AND DATE(a.dteDCDate) <= '" + toDate + "'  GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode ";

			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, IFNULL(SUM(b.dblQty),0) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate " + " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL  ";

			sql += "select a.dteDNDate TransDate,15 TransNo, 'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c	" + " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode   and b.strProdCode= '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += ") a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";
		} else {
			sql = "select DATE_FORMAT(date(TransDate),'%d-%m-%Y'),TransType,RefNo,Receipt,Issue,Name,Rate, UOMString from "

			+ "(";
			if (!selectOpStk.isEmpty()) {
				sql += selectOpStk;
				sql += " union all ";
			}
			sql += "select a.dtMISDate TransDate,3 TransNo, 'MIS In' TransType, a.strMISCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt " + ", 0 Issue ,c.strLocName Name " + ",b.dblUnitPrice Rate"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmishd a, tblmisdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all ";

			if (startDate.equals(fromDate)) {
				sql += "select a.dtCreatedDate TransDate,1 TransNo, 'Opening Stk' TransType, a.strOpStkCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) Receipt, 0 Issue" + ",'Opening Stock' Name,dblCostPerUnit Rate"
				// +
				// ", funGetUOM(ifnull(sum(b.dblQty),0),c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString "
						+ ", CONCAT_WS('!',c.dblRecipeConversion,c.dblIssueConversion,c.strReceivedUOM,c.strRecipeUOM) UOMString " + " from tblinitialinventory a, tblinitialinvdtl b, tblproductmaster c " + "where a.strOpStkCode  = b.strOpStkCode and b.strProdCode=c.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmOpeningStock")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtCreatedDate) >= '" + startDate + "' and date(a.dtCreatedDate) <= '" + toDate + "'  " + "group by a.dtCreatedDate,b.strProdCode "

				+ "union all ";
			}

			sql += "select a.dtGRNDate TransDate,2 TransNo, 'GRN' TransType, a.strGRNCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty-b.dblRejected),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strPName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblgrnhd a, tblgrndtl b,tblpartymaster c, tblproductmaster d " + "where a.strGRNCode = b.strGRNCode and a.strSuppCode=c.strPCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmGRN")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtGRNDate) >= '" + fromDate + "' and date(a.dtGRNDate) <= '" + toDate + "' " + "group by a.dtGRNDate , a.strGRNCode, a.strSuppCode, RefNo "

			+ "union all "

			+ "select a.dtSADate TransDate,11 TransNo, 'StkAdj In' TransType, a.strSACode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='IN' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate, a.strSACode, a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,4 TransNo, 'StkTrans In' TransType, a.strSTCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,(b.dblTotalPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d  " + "where a.strSTCode  = b.strSTCode and a.strToLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strToLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strToLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,5 TransNo, 'Mat Ret In' TransType, a.strMRetCode RefNo" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocTo='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocTo " + ""

			// + "union all "
			//
			// +
			// "select a.dtPDDate TransDate,6 TransNo, 'Production' TransType, a.strPDCode RefNo"
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, 0 Issue "
			// +
			// ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// // +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
			// +
			// " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d "
			// +
			// "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode "
			// + "and b.strProdCode = '"+prodCode+"' ";
			// if(!locCode.equalsIgnoreCase("All"))
			// {
			// sql+= "and a.strLocCode='"+locCode+"' ";
			// }
			// if(null!=hmAuthorisedForms.get("frmProduction"))
			// {
			// sql+= "and a.strAuthorise='Yes' ";
			// }
			// sql+=
			// "and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
			// + "group by a.dtPDDate, a.strPDCode, a.strLocCode "
			// + ""

					+ "union all "

					+ "select a.dtPRDate TransDate,7 TransNo, 'Purchase Ret' TransType, a.strPRCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate "
					// +
					// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblpurchasereturnhd a, tblpurchasereturndtl b,tbllocationmaster c, tblproductmaster d " + "where a.strPRCode  = b.strPRCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmPurchaseReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPRDate) >= '" + fromDate + "' and date(a.dtPRDate) <= '" + toDate + "' " + "group by a.dtPRDate,a.strPRCode,a.strLocCode  " + ""

			+ "union all "

			+ "select a.dtSADate TransDate,12 TransNo, 'StkAdj Out' TransType, a.strSACode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, b.dblRate Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstockadjustmenthd a, tblstockadjustmentdtl b,tbllocationmaster c, tblproductmaster d " + "where a.strSACode = b.strSACode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' " + "and b.strType='Out' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockAdjustment")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSADate) >= '" + fromDate + "' and date(a.dtSADate) <= '" + toDate + "' " + "group by a.dtSADate,a.strSACode,a.strLocCode " + ""

			+ "union all "

			+ "select a.dtSTDate TransDate,8 TransNo, 'StkTrans Out' TransType, a.strSTCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,(b.dblPrice/ifnull(sum(b.dblQty),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblstocktransferhd a, tblstocktransferdtl b ,tbllocationmaster c, tblproductmaster d " + "where a.strSTCode  = b.strSTCode and a.strFromLocCode = c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strFromLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmStockTransfer")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtSTDate) >= '" + fromDate + "' and date(a.dtSTDate) <= '" + toDate + "' " + "group by a.dtSTDate, a.strSTCode, a.strFromLocCode " + ""

			+ "union all "

			+ "select a.dtMRetDate TransDate,9 TransNo, 'Mat Ret Out' TransType, a.strMRetCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,'1' Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmaterialreturnhd a, tblmaterialreturndtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMRetCode  = b.strMRetCode and a.strLocFrom=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMaterialReturn")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMRetDate) >= '" + fromDate + "' and date(a.dtMRetDate) <= '" + toDate + "' " + "group by a.dtMRetDate, a.strMRetCode, a.strLocFrom " + ""

			+ "union all "

			+ "select a.dtMISDate TransDate,10 TransNo, 'MIS Out' TransType, a.strMISCode RefNo, 0 Receipt" + ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name,b.dblUnitPrice Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblmishd a, tblmisdtl b, tbllocationmaster c, tblproductmaster d " + "where a.strMISCode = b.strMISCode and a.strLocTo=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocFrom = '" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmMIS")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtMISDate) >= '" + fromDate + "' and date(a.dtMISDate) <= '" + toDate + "' " + "group by a.dtMISDate, a.strMISCode, a.strLocTo " + ""

			+ "union all "

			+ " select a.dteSRDate TransDate,11 TransNo, 'Sales Ret' TransType, a.strSRCode RefNo, 0 Receipt, " + " funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue , " + " c.strLocName Name,'1' Rate  "
			// +
			// " funGetUOM(ifnull(sum(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tblsalesreturnhd a, tblsalesreturndtl b, tbllocationmaster c, tblproductmaster d " + " where a.strSRCode  = b.strSRCode and a.strLocCode=c.strLocCode   and b.strProdCode=d.strProdCode  " + " and b.strProdCode = '" + prodCode + "' ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += " and a.strLocCode='" + locCode + "' ";
			}
			sql += "and date(a.dteSRDate) >= '" + fromDate + "' and date(a.dteSRDate) <= '" + toDate + "' " + "group by a.dteSRDate, a.strSRCode, a.strLocCode "

			+ "union all ";
			clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propCode, clientCode);

			if (objSetup.getStrEffectOfInvoice().equalsIgnoreCase("Invoice")) {

				sql += " SELECT a.dteInvDate TransDate,13 TransNo, 'Invoice' TransType, a.strInvCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate, " + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tblinvoicehd a, tblinvoicedtl b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strInvCode=b.strInvCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteInvDate) >= '" + fromDate + "' AND DATE(a.dteInvDate) <= '" + toDate + "' " + " GROUP BY a.dteInvDate, a.strInvCode, a.strLocCode  ";
			} else {

				sql += " SELECT a.dteDCDate TransDate,13 TransNo, 'Delivery Chal.' TransType, a.strDCCode RefNo, 0 Receipt," + " funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue," + " c.strLocName Name,'1' Rate, " + " CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ " FROM  tbldeliverychallanhd a, tbldeliverychallandtl b, tbllocationmaster c, tblproductmaster d " + " WHERE  a.strDCCode=b.strDCCode AND a.strLocCode=c.strLocCode AND b.strProdCode=d.strProdCode " + " AND b.strProdCode = '" + prodCode + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += " and a.strLocCode='" + locCode + "' ";
				}
				sql += "AND DATE(a.dteDCDate) >= '" + fromDate + "' AND DATE(a.dteDCDate) <= '" + toDate + "' " + " GROUP BY a.dteDCDate, a.strDCCode, a.strLocCode  ";
			}

			sql += "UNION ALL  ";
			sql += "select a.dteSRDate TransDate,14 TransNo, 'SC GRN' TransType, a.strSRCode RefNo, funGetUOM(IFNULL(SUM(b.dblQty),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Receipt, " + "0 Issue,c.strLocName Name,b.dblPrice Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ " from tblscreturnhd a,tblscreturndtl b ,tbllocationmaster c ,tblproductmaster d " + "where a.strSRCode=b.strSRCode and a.strLocCode=c.strLocCode and b.strProdCode= '" + prodCode + "' AND b.strProdCode=d.strProdCode ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteSRDate) >= '" + fromDate + "' " + " AND DATE(a.dteSRDate) <= '" + toDate + "'  GROUP BY a.dteSRDate, a.strSRCode, a.strLocCode "

			+ " UNION ALL ";

			sql += "select a.dteDNDate TransDate,15 TransNo,  'Delivery Note' TransType, a.strDNCode RefNo, 0 Receipt," + " IFNULL(SUM(b.dblQty),0)  Issue,c.strLocName Name,0 Rate ,CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString " + " from tbldeliverynotehd a,tbldeliverynotedtl b ,tbllocationmaster c,tblproductmaster d	"
					+ " where a.strDNCode=b.strDNCode and a.strLocCode=c.strLocCode and b.strProdCode='" + prodCode + "' AND b.strProdCode=d.strProdCode  ";

			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode = '" + locCode + "' ";
			}
			sql += " AND DATE(a.dteDNDate) >= '" + fromDate + "' " + " AND DATE(a.dteDNDate) <= '" + toDate + "'  GROUP BY a.dteDNDate, a.strDNCode, a.strLocCode ";

			sql += " ) a " + "where date(TransDate) IS NOT NULL "
			// + "order by Date(TransDate) desc,Receipt desc";

					+ "order by Date(TransDate) desc ,TransNo desc ,Receipt desc";
		}

		System.out.println(sql);

		List listStkLedger1 = objGlobalService.funGetList(sql, "sql");

		List listStkLedger = new ArrayList();
		Map<String, List> hmProduction = new HashMap<String, List>();
		for (int i = 0; i < listStkLedger1.size(); i++) {
			Object[] obj = (Object[]) listStkLedger1.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listStkLedger.add(dataList);
		}

		// sql="select b.strProdCode  from tblproductionhd a, tblproductiondtl b where a.strLocCode='L000001' "
		// +" and date(a.dtPDDate) >= '"+fromDate+"' and date(a.dtPDDate) <= '"+toDate+"' "
		// ;
		//
		//
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");

		sql = "SELECT b.strChildCode,b.dblQty,a.strParentCode from tblbommasterhd a ,tblbommasterdtl b  where a.strBOMCode=b.strBOMCode and b.strChildCode='" + prodCode + "' ";

		List listChildProdStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int cnt = 0; cnt < listChildProdStkLedger.size(); cnt++) {
			Object objParent[] = (Object[]) listChildProdStkLedger.get(cnt);
			objParent[2].toString();

			if (qtyWithUOM.equals("No")) {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, 0 Receipt, ifnull(sum(b.dblQtyProd),0) Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode " + "and b.strProdCode = '"
						+ objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt

						double qty = Double.parseDouble(objParent[1].toString()) * Double.parseDouble(obj[4].toString());
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString());
						}

						dataList.add(qty);// Issue
						dataList.add(obj[5]);// LocName
						dataList.add(obj[6]);// Rate

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			} else {

				sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",0 Receipt,  funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
				// +
				// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
						+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString  " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + objParent[2].toString() + "' ";
				if (!locCode.equalsIgnoreCase("All")) {
					sql += "and a.strLocCode='" + locCode + "' ";
				}
				if (null != hmAuthorisedForms.get("frmProduction")) {
					sql += "and a.strAuthorise='Yes' ";
				}
				sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

				List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");
				if (!listProductionStkLedger.isEmpty()) {

					for (int i = 0; i < listProductionStkLedger.size(); i++) {
						Object[] obj = (Object[]) listProductionStkLedger.get(i);
						List dataList = new ArrayList();

						dataList.add(obj[0]);// Date
						dataList.add(obj[1]);// TransType
						dataList.add(obj[2]);// REfNo
						dataList.add(obj[3]);// Recipt
						String[] strProdQtyUOM = obj[4].toString().split(" ");
						double qty = Double.parseDouble(objParent[1].toString()) * Double.parseDouble(strProdQtyUOM[0]);
						if (hmProduction.containsKey(obj[2].toString())) {
							List listQty = (List) hmProduction.get(obj[2].toString());
							qty = qty + Double.parseDouble(listQty.get(4).toString().split(" ")[0]);
						}
						String strProdUom = "";
						for (int a = 1; a < strProdQtyUOM.length; a++) {
							strProdUom = strProdUom + "" + strProdQtyUOM[a];

						}
						dataList.add(qty + " " + strProdUom);// Issue&prodUOM
						dataList.add(obj[5]);// LocName
						dataList.add(obj[6]);// Rate
						dataList.add(obj[7]);

						hmProduction.put(obj[2].toString(), dataList);

					}
				}

			}
		}

		for (Map.Entry<String, List> enty : hmProduction.entrySet()) {

			List listChildTotlQty = enty.getValue();
			listStkLedger.add(listChildTotlQty);
		}

		// sql=
		// "SELECT  count(*) from tblbommasterhd a where a.strParentCode='"+prodCode+"' ";
		// List listParentProdStkLedger=objGlobalService.funGetList(sql, "sql");
		//
		// if (listParentProdStkLedger != null)
		// {
		//
		//
		if (qtyWithUOM.equals("No")) {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y')  TransDate, 'Production' TransType, a.strPDCode RefNo, ifnull(sum(b.dblQtyProd),0) Receipt, 0 Issue" + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate ,6 TransNo " + "from tblproductionhd a, tblproductiondtl b, tbllocationmaster c " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode "
					+ "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		} else {

			sql = "select DATE_FORMAT(a.dtPDDate,'%d-%m-%Y') TransDate, 'Production' TransType, a.strPDCode RefNo" + ",funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM)  Receipt, 0 Issue " + ",c.strLocName Name, (b.dblPrice/ifnull(sum(b.dblQtyProd),1)) Rate "
			// +
			// ", funGetUOM(ifnull(sum(b.dblQtyProd),0),d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString "
					+ ", CONCAT_WS('!',d.dblRecipeConversion,d.dblIssueConversion,d.strReceivedUOM,d.strRecipeUOM) UOMString ,6 TransNo " + " from tblproductionhd a, tblproductiondtl b, tbllocationmaster c, tblproductmaster d " + "where a.strPDCode  = b.strPDCode and a.strLocCode=c.strLocCode and b.strProdCode=d.strProdCode " + "and b.strProdCode = '" + prodCode + "' ";
			if (!locCode.equalsIgnoreCase("All")) {
				sql += "and a.strLocCode='" + locCode + "' ";
			}
			if (null != hmAuthorisedForms.get("frmProduction")) {
				sql += "and a.strAuthorise='Yes' ";
			}
			sql += "and date(a.dtPDDate) >= '" + fromDate + "' and date(a.dtPDDate) <= '" + toDate + "' " + "group by a.dtPDDate, a.strPDCode, a.strLocCode ";

		}

		// }

		List listProductionStkLedger = objGlobalService.funGetList(sql, "sql");

		for (int i = 0; i < listProductionStkLedger.size(); i++) {
			Object[] obj = (Object[]) listProductionStkLedger.get(i);
			List dataList = new ArrayList();
			for (int j = 0; j < obj.length; j++) {
				dataList.add(obj[j]);
			}
			listStkLedger.add(dataList);

		}

		List listStockledger = new ArrayList();
		List sumarylist = new ArrayList();

		double reciptTtl = 0.0;
		double issueTtl = 0.0;
		double rate = 0.0;
		double opnStock = 0.0;
		double clsngBal = 0.;
		double bal = 0.0;

		List<Object[]> arr = new ArrayList<Object[]>();
		int i = listStkLedger.size() - 1;
		for (int cnt = listStkLedger.size() - 1; cnt >= 0; cnt--) {
			// Object[] arrObj=(Object[])listStkLedger.get(cnt);
			// List DataList=new ArrayList<>();
			// DataList.add(arrObj[0].toString());//0
			// DataList.add(arrObj[5].toString());//1
			// DataList.add(arrObj[1].toString());//2
			// DataList.add(arrObj[2].toString());//3
			// DataList.add(arrObj[3].toString());//4
			// DataList.add(arrObj[4].toString());//5
			// var cells1=tableRows[cnt].cells;

			List listObj = (List) listStkLedger.get(cnt);
			List DataList = new ArrayList<>();
			DataList.add(listObj.get(0).toString());// 0
			DataList.add(listObj.get(5).toString());// 1
			DataList.add(listObj.get(1).toString());// 2
			DataList.add(listObj.get(2).toString());// 3
			DataList.add(listObj.get(3).toString());// 4
			DataList.add(listObj.get(4).toString());// 5

			String rec = "";
			String op = "";
			// rec=rec.substring(7,rec.lastIndexOf("<"));
			String issue = "";
			// issue=issue.substring(7,issue.lastIndexOf("<"));
			String finalBal = "";
			double rate1 = 0.0;
			// double
			// rate=parseFloat(rate1.substring(7,rate1.lastIndexOf("<")));
			// bal=bal+(Double.parseDouble(arrObj[3].toString())-Double.parseDouble(arrObj[4].toString()));
			if (qtyWithUOM.equalsIgnoreCase("No")) {
				// rec=arrObj[3].toString();
				// issue=arrObj[4].toString();
				// bal=bal+(Double.parseDouble(arrObj[3].toString())-Double.parseDouble(arrObj[4].toString()));
				// DataList.add(bal);

				rec = listObj.get(3).toString();
				issue = listObj.get(4).toString();
				bal = bal + (Double.parseDouble(listObj.get(3).toString()) - Double.parseDouble(listObj.get(4).toString()));
				DataList.add(bal);
			} else {

				String[] uomConv = listObj.get(7).toString().split("!");
				conRecipe = uomConv[0];
				conIssue = uomConv[1];
				uomRecv = uomConv[2];
				uomIssue = uomConv[3];

				op = listObj.get(3).toString();
				rec = op;
				issue = listObj.get(4).toString();
				// String [] recArr=rec.split(" ");
				// rec=recArr[0];
				// issue=arrObj[4].toString().split("")[0];
				//
				// bal=bal+(Double.parseDouble(rec)-Double.parseDouble(issue));
				// String uom=arrObj[7].toString();
				//
				// String[] spUOM=uom.split("!");
				// String recipeConv=spUOM[0];
				// String issueConv=spUOM[1];
				// String receivedUOM=spUOM[2];
				// String recipeUOM=spUOM[3];
				//
				// String balance=Double.toString(bal);
				// String[] spBalance=balance.split("\\.");
				// String finalBal="";
				// if(!(spBalance[0].equalsIgnoreCase(" ")))
				// {
				// finalBal=spBalance[0]+' '+receivedUOM;
				// }
				// if(!(spBalance[1].equalsIgnoreCase("")))
				// {
				// Double
				// balWithUOM=Double.parseDouble(spBalance[1])*Double.parseDouble(recipeConv)*Double.parseDouble(issueConv);
				// finalBal=finalBal+'.'+balWithUOM+' '+recipeUOM;
				// }

				if (listObj.get(1).toString().equals("Opening Stk")) {
					if (op.equals("0")) {
						finalBal = "0" + " " + uomRecv + "." + "0" + " " + uomIssue;
					} else {

						if (op.contains(".")) {
							Double dblOp = 0.0;
							String[] opArr = op.split("\\.");
							if (opArr.length > 1) {
								String[] opArrUp = opArr[0].split(" ");
								double upQty = Double.parseDouble(opArrUp[0].toString());
								upQty = upQty * Double.parseDouble(conRecipe);
								String[] opArrDwn = opArr[1].split(" ");
								double dwnQty = Double.parseDouble(opArrDwn[0].toString());
								dblOp = (upQty + dwnQty) / Double.parseDouble(conRecipe);
							} else {
								String[] opArrUp = opArr[0].split(" ");
								double firstQty = Double.parseDouble(opArrUp[0].toString());
								dblOp = (dblOp) / Double.parseDouble(conRecipe);
							}

							bal = bal + dblOp;
							Double dblshowBal = bal;

							String[] arrAry = df3Zeors.format(dblshowBal).toString().split("\\.");
							// String restQty=new
							// Double(dblshowBal-Integer.parseInt(arrAry[0])
							// ).toString();
							String restQty = df3Zeors.format(dblshowBal - Integer.parseInt(arrAry[0]));
							Double rtQty = Double.parseDouble(restQty) * Double.parseDouble(conRecipe);
							restQty = rtQty.toString();
							String[] arrRestQty = restQty.split("\\.");

							finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[0] + " " + uomIssue;

						} else {
							String[] opArrDwn = op.split(" ");
							Double dwnQty = Double.parseDouble(opArrDwn[0].toString());
							double dwntempQty = dwnQty / Double.parseDouble(conRecipe);
							bal = bal + dwntempQty;
							finalBal = "0" + " " + uomRecv + "." + Math.rint(dwnQty) + " " + uomIssue;
						}
					}
				} else {
					if (rec.equals("0")) {
						if (bal > 0) {
							String[] arrAry = String.valueOf(bal).split("\\.");
							// String restQty=new
							// Double(dblRec-Integer.parseInt(arrAry[0])
							// ).toString();
							String restQty = df3.format(bal - Integer.parseInt(arrAry[0]));
							Double rtQty = Double.parseDouble(restQty) * Double.parseDouble(conRecipe);
							restQty = rtQty.toString();
							String[] arrRestQty = restQty.split("\\.");
							finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[0] + " " + uomIssue;
						} else {
							finalBal = "0" + " " + uomRecv + "." + "0" + " " + uomIssue;
						}
					} else {
						if (rec.contains(".")) {
							String[] recArr = rec.split("\\.");
							Double dblRec = 0.0;
							if (recArr.length > 1) {
								String[] recArrUp = recArr[0].split(" ");
								double upQty = Double.parseDouble(recArrUp[0].toString());
								upQty = upQty * Double.parseDouble(conRecipe);
								String[] recArrDwn = recArr[1].split(" ");
								double dwnQty = Double.parseDouble(recArrDwn[0].toString());
								dblRec = (upQty + dwnQty) / Double.parseDouble(conRecipe);
							} else {
								String[] recArrUp = recArr[0].split(" ");
								double firstQty = Double.parseDouble(recArrUp[0].toString());
								dblRec = (firstQty) / Double.parseDouble(conRecipe);
							}

							bal = bal + dblRec;
							Double dblshowBal = bal;
							String[] arrAry = df3Zeors.format(dblshowBal).toString().split("\\.");
							// String restQty=new
							// Double(dblRec-Integer.parseInt(arrAry[0])
							// ).toString();
							String restQty = df3Zeors.format(dblshowBal - Integer.parseInt(arrAry[0]));
							Double rtQty = Double.parseDouble(restQty) * Double.parseDouble(conRecipe);
							restQty = rtQty.toString();
							String[] arrRestQty = restQty.split("\\.");
							finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[0] + " " + uomIssue;

						} else {
							String[] recArrDwn = rec.split(" ");
							Double dwnQty = Double.parseDouble(recArrDwn[0].toString());
							double dwntempQty = dwnQty / Double.parseDouble(conRecipe);
							bal = bal + dwntempQty;
							finalBal = "0" + " " + uomRecv + "." + Math.rint(dwnQty) + " " + uomIssue;
							Double dblshowBal = bal;

							String[] arrAry = df3Zeors.format(dblshowBal).toString().split("\\.");
							String restQty = df3Zeors.format(dblshowBal - Integer.parseInt(arrAry[0]));
							String[] arrRestQty = restQty.split("\\.");
							finalBal = arrAry[0] + " " + uomRecv + "." + df3Zeors.format(Double.parseDouble(arrRestQty[1])) + " " + uomIssue;
						}
					}
				}

				if (issue.equals("0")) {
					if (bal > 0) {
						String[] arrAry = String.valueOf(bal).split("\\.");
						// String restQty=new
						// Double(dblRec-Integer.parseInt(arrAry[0])
						// ).toString();
						String restQty = df3.format(bal - Integer.parseInt(arrAry[0]));
						Double rtQty = Double.parseDouble(restQty) * Double.parseDouble(conRecipe);
						restQty = rtQty.toString();
						String[] arrRestQty = restQty.split("\\.");
						finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[0] + " " + uomIssue;
					} else {
						finalBal = "0" + " " + uomRecv + "." + "0" + " " + uomIssue;
					}
				} else {
					if (issue.contains(".")) {
						Double dblIss = 0.0;
						String[] issArr = issue.split("\\.");
						if (issArr.length > 1) {
							String[] issArrUp = issArr[0].split(" ");
							double upQty = Double.parseDouble(issArrUp[0].toString());
							upQty = upQty * Double.parseDouble(conRecipe);
							String[] issArrDwn = issArr[1].split(" ");
							double dwnQty = Double.parseDouble(issArrDwn[0].toString());
							dblIss = (upQty + dwnQty) / Double.parseDouble(conRecipe);
						} else {
							String[] issArrUp = issArr[0].split(" ");
							double firstQty = Double.parseDouble(issArrUp[0].toString());
							dblIss = (firstQty) / Double.parseDouble(conRecipe);
						}
						bal = bal - dblIss;
						Double dblshowBal = new Double(df3Zeors.format(bal));
						;

						String[] arrAry = df3Zeors.format(dblshowBal).toString().split("\\.");
						String restQty = df3Zeors.format(dblshowBal - Integer.parseInt(arrAry[0]));
						Double rtQty = Double.parseDouble(restQty) * Double.parseDouble(conRecipe);
						restQty = rtQty.toString();
						String[] arrRestQty = restQty.split("\\.");
						finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[0] + " " + uomIssue;
					} else {
						String[] issArrDwn = issue.split(" ");
						Double dwnQty = Double.parseDouble(issArrDwn[0].toString());
						double dwntempQty = dwnQty / Double.parseDouble(conRecipe);
						bal = bal - dwntempQty;
						finalBal = "0" + " " + uomRecv + "." + Math.rint(dwnQty) + " " + uomIssue;
						Double dblshowBal = bal;

						String[] arrAry = df3Zeors.format(dblshowBal).toString().split("\\.");
						String restQty = df3Zeors.format(dblshowBal - Integer.parseInt(arrAry[0]));
						String[] arrRestQty = restQty.split("\\.");
						finalBal = arrAry[0] + " " + uomRecv + "." + arrRestQty[1] + " " + uomIssue;
					}
				}

				// String [] recArr=rec.split("");

				DataList.add(finalBal);
			}
			DataList.add(listObj.get(6).toString());// col 7
			DecimalFormat df = new DecimalFormat("#.##");
			Double issueOrReceipt = 0.0;

			if (qtyWithUOM.equalsIgnoreCase("No")) {
				issueOrReceipt = Double.parseDouble(rec) + Double.parseDouble(issue);
			} else {
				issueOrReceipt = bal;
			}

			double value = rate * issueOrReceipt;
			DataList.add(df.format(value).toString());
			reciptTtl = reciptTtl + Double.parseDouble(listObj.get(3).toString().split(" ")[0]);
			issueTtl = issueTtl + Double.parseDouble(listObj.get(4).toString().split(" ")[0]);
			rate = Double.parseDouble(listObj.get(6).toString());
			if (listObj.get(2).toString().equalsIgnoreCase("Opening Stk")) {
				opnStock = Double.parseDouble(listObj.get(3).toString().split(" ")[0]);
			}

			listStockledger.add(DataList);
		}
		double closingBalance = opnStock + reciptTtl;
		closingBalance = closingBalance - issueTtl;

		List DataList = new ArrayList<>();
		DataList.add("");
		listStockledger.add(DataList);
		DataList = new ArrayList<>();
		DataList.add("Transaction Type");
		DataList.add("Quantity");
		DataList.add("Value");
		listStockledger.add(DataList);
		DataList = new ArrayList<>();
		DataList.add("Opening Stock");
		DataList.add(opnStock);
		DataList.add(opnStock * rate);
		listStockledger.add(DataList);
		DataList = new ArrayList<>();
		DataList.add("Total Receipts");
		DataList.add(reciptTtl);
		DataList.add(reciptTtl * rate);
		listStockledger.add(DataList);
		DataList = new ArrayList<>();
		DataList.add("Total Issues");
		DataList.add(issueTtl);
		DataList.add(issueTtl * rate);
		listStockledger.add(DataList);
		DataList = new ArrayList<>();
		DataList.add("Closing Balance");
		DataList.add(closingBalance);
		DataList.add(closingBalance * rate);
		listStockledger.add(DataList);

		listStock.add(listStockledger);
		listStock.add(sumarylist);

		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listStock);

	}

	private String funGetDispalyUOMQty(String strProdCode, double dblQty, String clientCode) {
		String strDispQty = "";
		DecimalFormat df3Zeors = new DecimalFormat("0.000");
		clsProductMasterModel objProdModel = objProductMasterService.funGetObject(strProdCode, clientCode);
		if (dblQty < 1) {
			double qtytemp = Double.parseDouble(df3Zeors.format(dblQty * objProdModel.getDblRecipeConversion()).toString());
			strDispQty = qtytemp + " " + objProdModel.getStrRecipeUOM();

		} else {
			Double qty = dblQty;
			String[] spqty = (qty.toString()).split("\\.");
			double lowest = qty - Double.parseDouble(spqty[0]);
			double qtytemp = Double.parseDouble(df3Zeors.format(lowest * objProdModel.getDblRecipeConversion()).toString());
			strDispQty = spqty[0] + " " + objProdModel.getStrReceivedUOM() + "." + qtytemp + " " + objProdModel.getStrRecipeUOM();
		}

		return strDispQty;
	}

}
