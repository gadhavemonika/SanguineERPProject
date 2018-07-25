package com.sanguine.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsStockFlashModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.service.clsSetupMasterService;
import com.sanguine.service.clsStockFlashService;
import com.sanguine.service.clsUserMasterService;

@Controller
public class clsStockFlashController {
	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsStockFlashService objStkFlashService;
	@Autowired
	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGroupMasterService objGrpMasterService;

	@Autowired
	private clsUserMasterService objUserMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@RequestMapping(value = "/frmStockFlash", method = RequestMethod.GET)
	private ModelAndView funLoadPropertySelection(@ModelAttribute("command") clsStockFlashBean objPropBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);
	}

	/*
	 * @SuppressWarnings("rawtypes")
	 * 
	 * @RequestMapping(value = "/frmStockFlashReport", method =
	 * RequestMethod.GET) private ModelAndView
	 * funShowStockFlash(@ModelAttribute("command") clsStockFlashBean
	 * objStkFlashBean,BindingResult result,HttpServletRequest req) {
	 * //objGlobal=new clsGlobalFunctions(); String
	 * clientCode=req.getSession().getAttribute("clientCode").toString(); String
	 * userCode=req.getSession().getAttribute("usercode").toString(); String
	 * locCode=objStkFlashBean.getStrLocationCode(); String
	 * strSGCode=objStkFlashBean.getStrSGCode(); String
	 * fromDate=objGlobal.funGetDate
	 * ("yyyy-MM-dd",objStkFlashBean.getDteFromDate()); String
	 * toDate=objGlobal.funGetDate("yyyy-MM-dd",objStkFlashBean.getDteToDate());
	 * 
	 * String startDate=req.getSession().getAttribute("startDate").toString();
	 * String[] sp=startDate.split(" "); String[] spDate=sp[0].split("/");
	 * startDate=spDate[2]+"-"+spDate[1]+"-"+spDate[0];
	 * System.out.println(startDate); String newToDate="";
	 * 
	 * objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate,
	 * clientCode, userCode);
	 * 
	 * String
	 * sql="select f.propertyName,a.strProdCode,b.strProdName,e.strLocName" +
	 * ",d.strGName,c.strSGName,b.strUOM,b.strBinNo " +
	 * ",b.dblCostRM,a.dblOpeningStk,(dblOpeningStk+dblGRN+dblSCGRN+dblStkTransIn+dblStkAdjIn+dblMISIn+dblQtyProduced) as Receipts "
	 * +
	 * ",(dblStkTransOut-dblStkAdjOut-dblMISOut-dblQtyConsumed-dblSales-dblMaterialReturnOut-dblDeliveryNote) as Issue "
	 * +
	 * ",a.dblClosingStk,(a.dblClosingStk*b.dblCostRM) as Value,a.dblClosingStk as IssueUOMStock "
	 * + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo " +
	 * "from clsCurrentStockModel a,clsProductMasterModel b,clsSubGroupMasterModel c"
	 * + ",clsGroupMasterModel d,clsLocationMasterModel e,clsPropertyMaster f "
	 * +
	 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
	 * + "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.propertyCode " +
	 * "and a.strClientCode=:clientCode " +
	 * "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 "
	 * +
	 * "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 "
	 * +
	 * "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 "
	 * + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
	 * //System.out.println(sql); List
	 * list=objStkFlashService.funGetStockFlashData(sql,clientCode, userCode);
	 * List<clsStockFlashModel> listStockFlashModel=new
	 * ArrayList<clsStockFlashModel>();
	 * 
	 * for(int cnt=0;cnt<list.size();cnt++) { Object[]
	 * arrObj=(Object[])list.get(cnt); clsStockFlashModel objStkFlashModel=new
	 * clsStockFlashModel();
	 * objStkFlashModel.setStrPropertyName(arrObj[0].toString());
	 * objStkFlashModel.setStrProdCode(arrObj[1].toString());
	 * objStkFlashModel.setStrProdName(arrObj[2].toString());
	 * objStkFlashModel.setStrLocName(arrObj[3].toString());
	 * objStkFlashModel.setStrGroupName(arrObj[4].toString());
	 * objStkFlashModel.setStrSubGroupName(arrObj[5].toString());
	 * objStkFlashModel.setStrUOM(arrObj[6].toString());
	 * objStkFlashModel.setStrBinNo(arrObj[7].toString());
	 * objStkFlashModel.setDblCostRM(Double.parseDouble(arrObj[8].toString()));
	 * objStkFlashModel.setDblOpStock(Double.parseDouble(arrObj[9].toString()));
	 * 
	 * double receipts=Double.parseDouble(arrObj[10].toString()); double
	 * issue=Double.parseDouble(arrObj[11].toString()); double
	 * closingStock=Double.parseDouble(arrObj[12].toString()); double
	 * value=Double.parseDouble(arrObj[13].toString());
	 * objStkFlashModel.setDblReceipts(receipts);
	 * objStkFlashModel.setDblIssue(issue);
	 * objStkFlashModel.setDblClosingStock(closingStock);
	 * objStkFlashModel.setDblValue(value);
	 * objStkFlashModel.setDblIssueUOMStock(closingStock);
	 * objStkFlashModel.setDblIssueConversion
	 * (Double.parseDouble(arrObj[15].toString()));
	 * objStkFlashModel.setStrIssueUOM(arrObj[16].toString());
	 * objStkFlashModel.setStrPartNo(arrObj[17].toString());
	 * 
	 * listStockFlashModel.add(objStkFlashModel); }
	 * 
	 * ModelAndView objModelView=funGetModelAndView(req);
	 * objModelView.addObject("stkFlashList",listStockFlashModel);
	 * 
	 * return objModelView; }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmStockFlashSummaryReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockSummaryFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		double dblTotalValue = 0;

		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		// /System.out.println(startDate);

		String stockableItem = "B";
		if (strNonStkItems.equals("Stockable")) {
			stockableItem = "Y";
		}
		if (strNonStkItems.equals("Non Stockable")) {
			stockableItem = "N";
		}

		objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
		String sql = "";// clsPropertyMaster

		if (qtyWithUOM.equals("No")) {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo "
					// + ",b.dblCostRM,"
					+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice), " + "a.dblOpeningStk,(a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn) as Receipts " + ",(a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote) as Issue " + ",a.dblClosingStk,"
					// + "(a.dblClosingStk*b.dblCostRM) as Value"
					+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value"

					+ ",a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
					/*
					 * +
					 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
					 * + ",tblpropertymaster f " +
					 * " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
					 * +
					 * " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
					 * ;
					 */

					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

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
			sql += "and a.strClientCode='" + clientCode + "' ";
			// + "and g.strClientCode='"+clientCode+"' "
			// + "and a.strUserCode='"+userCode+"' ";
		} else {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo "
					// + ",b.dblCostRM"
					+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) " + ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

					+ ",funGetUOM((a.dblGRN+dblSCGRN+a.dblStkTransIn+a.dblStkAdjIn+a.dblMISIn+a.dblQtyProduced+a.dblMaterialReturnIn),b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as Receipts "

					+ ",funGetUOM((a.dblStkTransOut-a.dblStkAdjOut-a.dblMISOut-a.dblQtyConsumed-a.dblSales-a.dblMaterialReturnOut-a.dblDeliveryNote),b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as Issue "

					+ ",funGetUOM(a.dblClosingStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM)"

					// + ",(a.dblClosingStk*b.dblCostRM) as Value, "
					+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value,"

					+ " a.dblClosingStk as IssueUOMStock " + ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
					/*
					 * +
					 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
					 * + ",tblpropertymaster f  " +
					 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
					 * +
					 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
					 * ;
					 */
					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

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
			sql += "and a.strClientCode='" + clientCode + "' ";
			// + "and g.strClientCode='"+clientCode+"' "
			// + "and a.strUserCode='"+userCode+"' ";
		}

		if (!strGCode.equalsIgnoreCase("All")) {
			sql += "and d.strGCode='" + strGCode + "' ";
		}

		if (!strSGCode.equalsIgnoreCase("All")) {
			sql += "and c.strSGCode='" + strSGCode + "' ";
		}
		/*
		 * if(strNonStkItems.equals("Stockable")) { sql+=
		 * "and b.strNonStockableItem='N' "; }
		 * if(strNonStkItems.equals("Non Stockable")) { sql+=
		 * "and b.strNonStockableItem='Y' "; }
		 */
		if (showZeroItems.equals("No")) {
			sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
		}

		System.out.println(sql);
		// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
		// userCode);
		List list = objGlobalService.funGetList(sql);

		List<clsStockFlashModel> listStockFlashModel = new ArrayList<clsStockFlashModel>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsStockFlashModel objStkFlashModel = new clsStockFlashModel();
			objStkFlashModel.setStrPropertyName(arrObj[0].toString());
			objStkFlashModel.setStrProdCode(arrObj[1].toString());
			objStkFlashModel.setStrProdName(arrObj[2].toString());
			objStkFlashModel.setStrLocName(arrObj[3].toString());
			objStkFlashModel.setStrGroupName(arrObj[4].toString());
			objStkFlashModel.setStrSubGroupName(arrObj[5].toString());
			objStkFlashModel.setStrUOM(arrObj[6].toString());
			objStkFlashModel.setStrBinNo(arrObj[7].toString());
			objStkFlashModel.setDblCostRM(arrObj[8].toString());
			objStkFlashModel.setDblOpStock(arrObj[9].toString());

			double issueQty = 0.0;
			double receipt = 0.0;
			double dblClosing = 0.0;
			if (qtyWithUOM.equals("Yes")) {
				if (!arrObj[10].toString().equals("")) {
					receipt = Double.parseDouble(arrObj[10].toString().split(" ")[0]);
					objStkFlashModel.setDblReceipts(Double.toString(receipt));
				} else {
					objStkFlashModel.setDblReceipts("");
				}
				if (!arrObj[11].toString().equals("")) {
					issueQty = Double.parseDouble(arrObj[11].toString().split(" ")[0]);
					if (issueQty < 0) {
						issueQty = -1 * issueQty;
					}

					objStkFlashModel.setDblIssue(Double.toString(issueQty));
				} else {
					objStkFlashModel.setDblIssue("");
				}
				if (!arrObj[12].toString().equals("")) {
					dblClosing = Double.parseDouble(arrObj[12].toString().split(" ")[0]);

					objStkFlashModel.setDblClosingStock(Double.toString(dblClosing));
				} else {
					objStkFlashModel.setDblClosingStock("");
				}
			} else {
				issueQty = Double.parseDouble(arrObj[11].toString());
				if (issueQty < 0) {
					issueQty = -1 * issueQty;
				}
				objStkFlashModel.setDblIssue(Double.toString(issueQty));
				objStkFlashModel.setDblReceipts(arrObj[10].toString());
				objStkFlashModel.setDblClosingStock(arrObj[12].toString());

			}

			double value = Double.parseDouble(arrObj[13].toString());
			if (value < 0) {
				value = value * (0);
			}
			double temp = value;
			dblTotalValue = dblTotalValue + temp;
			objStkFlashModel.setDblValue(String.valueOf(value));
			objStkFlashModel.setDblIssueUOMStock(arrObj[12].toString());
			objStkFlashModel.setDblIssueConversion(arrObj[15].toString());
			objStkFlashModel.setStrIssueUOM(arrObj[16].toString());
			objStkFlashModel.setStrPartNo(arrObj[17].toString());

			listStockFlashModel.add(objStkFlashModel);
		}

		ModelAndView objModelView = funGetModelAndView(req);
		objModelView.addObject("stkFlashList", listStockFlashModel);

		List stkFlashList = new ArrayList();
		stkFlashList.add(listStockFlashModel);
		stkFlashList.add(dblTotalValue);
		return stkFlashList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmStockFlashDetailReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockDetailFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		// double dblTotalValue=0;
		BigDecimal dblTotalValue = new BigDecimal(0);
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		System.out.println(startDate);
		String newToDate = "";
		String stockableItem = "B";
		if (strNonStkItems.equals("Stockable")) {
			stockableItem = "Y";
		}
		if (strNonStkItems.equals("Non Stockable")) {
			stockableItem = "N";
		}

		objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
		String sql = "";

		double dblPrice = 0.0;
		String currCode = req.getSession().getAttribute("currencyCode").toString();
		clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);

		if (qtyWithUOM.equals("No")) {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo " + " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) " + ",a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn"
					+ ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut " + ",a.dblClosingStk," + "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value"
					// + "(a.dblClosingStk*b.dblCostRM) as Value"
					+ ",a.dblClosingStk as IssueUOMStock "
					+ ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
					/*
					 * +
					 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
					 * + ",tblpropertymaster f" // + ",tblreorderlevel g  " +
					 * " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
					 * +
					 * " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
					 * ;
					 */
					// + " and b.strProdCode=g.strProdCode "
					// + " and g.strLocationCode='"+locCode+"' ";

					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

			if (strNonStkItems.equals("Non Stockable")) {
				sql += "	and b.strNonStockableItem='Y' ";
			} else if (strNonStkItems.equals("Stockable")) {
				sql += "	and b.strNonStockableItem='N' ";
			}
			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}

			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and  b.strProdType = '" + prodType + "'  ";
			}
			sql += "and a.strClientCode='" + clientCode + "' ";
			// + "and g.strClientCode='"+clientCode+"'  "
			// + "and a.strUserCode='"+userCode+"' ";
		} else {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + " ,d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + " ,b.dblCostRM"
					+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) "

					+ ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

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
					+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value,"

					+ "a.dblClosingStk as IssueUOMStock "
					+ ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
					/*
					 * +
					 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e"
					 * + ",tblpropertymaster f " // + ",tblreorderlevel g " +
					 * " where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
					 * +
					 * " and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
					 * ;
					 */
					// + " and b.strProdCode=g.strProdCode  "
					// + " and g.strLocationCode='"+locCode+"' ";

					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

			if (strNonStkItems.equals("Non Stockable")) {
				sql += "	and b.strNonStockableItem='Y' ";
			} else if (strNonStkItems.equals("Stockable")) {
				sql += "	and b.strNonStockableItem='N' ";
			}
			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}
			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and b.strProdType = '" + prodType + "'  ";
			}
			sql += "and a.strClientCode='" + clientCode + "' ";
			// + "and g.strClientCode='"+clientCode+"' "
			// + "and a.strUserCode='"+userCode+"' ";
		}

		if (!strGCode.equalsIgnoreCase("All")) {
			sql += "and d.strGCode='" + strGCode + "' ";
		}

		if (!strSGCode.equalsIgnoreCase("All")) {
			sql += "and c.strSGCode='" + strSGCode + "' ";
		}
		/*
		 * if(strNonStkItems.equals("Stockable")) { sql+=
		 * "and b.strNonStockableItem='N' "; }
		 * if(strNonStkItems.equals("Non Stockable")) { sql+=
		 * "and b.strNonStockableItem='Y' "; }
		 */

		if (showZeroItems.equals("No")) {
			sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
		}

		System.out.println(sql);
		// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
		// userCode);
		List list = objGlobalService.funGetList(sql);

		List<clsStockFlashModel> listStockFlashModel = new ArrayList<clsStockFlashModel>();

		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsStockFlashModel objStkFlashModel = new clsStockFlashModel();
			objStkFlashModel.setStrPropertyName(arrObj[0].toString());
			objStkFlashModel.setStrProdCode(arrObj[1].toString());
			objStkFlashModel.setStrProdName(arrObj[2].toString());
			objStkFlashModel.setStrLocName(arrObj[3].toString());
			objStkFlashModel.setStrGroupName(arrObj[4].toString());
			objStkFlashModel.setStrSubGroupName(arrObj[5].toString());
			objStkFlashModel.setStrUOM(arrObj[6].toString());
			objStkFlashModel.setStrBinNo(arrObj[7].toString());
			objStkFlashModel.setDblCostRM(arrObj[8].toString());
			objStkFlashModel.setDblOpStock(funGetDecimalValue(arrObj[9].toString())); // openStk
			objStkFlashModel.setDblGRN(funGetDecimalValue(arrObj[10].toString())); // GRN
			objStkFlashModel.setDblSCGRN(funGetDecimalValue(arrObj[11].toString())); // SCGRN
			objStkFlashModel.setDblStkTransIn(funGetDecimalValue(arrObj[12].toString())); // stkTrans
			objStkFlashModel.setDblStkAdjIn(funGetDecimalValue(arrObj[13].toString())); // STKAdj
			objStkFlashModel.setDblMISIn(funGetDecimalValue(arrObj[14].toString()));
			objStkFlashModel.setDblQtyProduced(funGetDecimalValue(arrObj[15].toString()));
			objStkFlashModel.setDblSalesReturn(funGetDecimalValue(arrObj[16].toString()));
			objStkFlashModel.setDblMaterialReturnIn(funGetDecimalValue(arrObj[17].toString()));
			objStkFlashModel.setDblPurchaseReturn(funGetDecimalValue(arrObj[18].toString()));
			objStkFlashModel.setDblDeliveryNote(funGetDecimalValue(arrObj[19].toString()));
			objStkFlashModel.setDblStkTransOut(funGetDecimalValue(arrObj[20].toString()));
			objStkFlashModel.setDblStkAdjOut(funGetDecimalValue(arrObj[21].toString()));
			objStkFlashModel.setDblMISOut(funGetDecimalValue(arrObj[22].toString()));
			objStkFlashModel.setDblQtyConsumed(funGetDecimalValue(arrObj[23].toString()));
			objStkFlashModel.setDblSales(funGetDecimalValue(arrObj[24].toString()));
			objStkFlashModel.setDblMaterialReturnOut(arrObj[25].toString());
			objStkFlashModel.setDblClosingStock(funGetDecimalValue(arrObj[26].toString()));

			BigDecimal value = new BigDecimal(arrObj[27].toString());
			// double value=Double.parseDouble(arrObj[27].toString());
			BigDecimal zero = new BigDecimal("0");
			// double temp=value;
			if (value.compareTo(BigDecimal.ZERO) < 0) {
				value = new BigDecimal("0");
			}

			dblTotalValue = dblTotalValue.add(value);
			objStkFlashModel.setDblValue(String.valueOf(value));
			objStkFlashModel.setDblIssueUOMStock(funGetDecimalValue(arrObj[26].toString()));
			objStkFlashModel.setDblIssueConversion(arrObj[29].toString());
			objStkFlashModel.setStrIssueUOM(arrObj[30].toString());
			objStkFlashModel.setStrPartNo(arrObj[31].toString());

			listStockFlashModel.add(objStkFlashModel);
		}

		ModelAndView objModelView = funGetModelAndView(req);
		objModelView.addObject("stkFlashList", listStockFlashModel);
		List stkFlashList = new ArrayList();
		stkFlashList.add(listStockFlashModel);
		stkFlashList.add(dblTotalValue);
		return stkFlashList;
	}

	@SuppressWarnings("unchecked")
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String usercode = req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmStockFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmStockFlash");
		}
		if (usercode.equalsIgnoreCase("SANGUINE")) {

			Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
			if (mapProperty.isEmpty()) {
				mapProperty.put("", "");
			}
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String locationCode = req.getSession().getAttribute("locationCode").toString();

			objModelView.addObject("listProperty", mapProperty);
			objModelView.addObject("LoggedInProp", propertyCode);
			objModelView.addObject("LoggedInLoc", locationCode);

			HashMap<String, String> mapLocation = (HashMap<String, String>) objGlobalService.funGetLocationList(propertyCode, clientCode);
			if (mapLocation.isEmpty()) {
				mapLocation.put("", "");
			}
			mapLocation = clsGlobalFunctions.funSortByValues(mapLocation);
			objModelView.addObject("listLocation", mapLocation);
		} else {
			Map<String, String> mapProperty = objUserMasterService.funGetUserBasedProperty(usercode, clientCode);
			// Map<String, String> mapProperty=
			// objGlobalService.funGetPropertyList(clientCode);
			if (mapProperty.isEmpty()) {
				mapProperty.put("", "");
			}
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String locationCode = req.getSession().getAttribute("locationCode").toString();

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

		Map<String, String> mapGroup = objGrpMasterService.funGetGroups(clientCode);
		mapGroup.put("ALL", "ALL");
		Map<String, String> mapSubGroup = new HashMap<String, String>();
		mapSubGroup.put("ALL", "ALL");
		objModelView.addObject("listSubGroup", mapSubGroup);
		objModelView.addObject("listGroup", mapGroup);

		List<String> listType = new ArrayList<String>();
		listType.add("ALL");
		listType.add("Procured");
		listType.add("Produced");
		listType.add("Sub-Contracted");
		listType.add("Tools");
		listType.add("Service");
		listType.add("Labour");
		listType.add("Overhead");
		listType.add("Scrap");
		listType.add("Non-Inventory");
		listType.add("Trading");

		objModelView.addObject("typeList", listType);

		return objModelView;
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
		if (spl.length == 1)// for No UOM
		{
			strVal = spl[0];
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
	 * if(strValue.contains(".")) { // if(strValue.contains("ML") &&
	 * !strValue.contains("BTL")) // { // String[] splValue =
	 * strValue.split("\\."); // String btlValue=splValue[0]; // String
	 * mlValue=splValue[1]; // // //String ml =" ML"; // if(btlValue.length()>3)
	 * // { // btlValue=btlValue.substring(0, 3); // } // // strVal =
	 * btlValue+mlValue; // }else // {
	 * 
	 * String[] splValue = strValue.split("\\."); String btlValue=splValue[0];
	 * //String mlValue=splValue[1];
	 * 
	 * String ml =" ML"; if(btlValue.length()>3) {
	 * btlValue=btlValue.substring(0, 3); }
	 * 
	 * strVal = btlValue+ml; // } }else { strVal = strValue; }
	 * 
	 * 
	 * } } strValue=strVal; }else { strVal=strValue; }
	 * 
	 * 
	 * 
	 * return strVal; }
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmMiniStockFlashReport", method = RequestMethod.GET)
	private @ResponseBody List funShowMiniStockFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];

		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		double dblTotalValue = 0;

		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		// /System.out.println(startDate);

		String stockableItem = "B";
		if (strNonStkItems.equals("Stockable")) {
			stockableItem = "Y";
		}
		if (strNonStkItems.equals("Non Stockable")) {
			stockableItem = "N";
		}
		double totVal = 0.00;

		objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
		String sql = "";// clsPropertyMaster

		sql = "select a.strProdCode,b.strProdName," + "a.dblClosingStk,"
				// + "(a.dblClosingStk*b.dblCostRM) as Value "
				+ "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value"

				/*
				 * +
				 * "from tblcurrentstock a,tblproductmaster b,tblsubgroupmaster c,tblgroupmaster d,tbllocationmaster e "
				 * + ",tblpropertymaster f " +
				 * "where a.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=d.strGCode "
				 * +
				 * "and a.strLocCode=e.strLocCode and e.strPropertyCode=f.strPropertyCode "
				 * ;
				 */

				+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
				+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

		if (strNonStkItems.equals("Non Stockable")) {
			sql += "	and b.strNonStockableItem='Y' ";
		} else if (strNonStkItems.equals("Stockable")) {
			sql += "	and b.strNonStockableItem='N' ";
		}

		if (!(prodType.equalsIgnoreCase("ALL"))) {
			sql += " and b.strProdType <> '" + prodType + "'  ";
		}
		sql += "and a.strClientCode='" + clientCode + "' ";
		// + "and g.strClientCode='"+clientCode+"' "
		// + "and a.strUserCode='"+userCode+"' ";

		if (!strGCode.equalsIgnoreCase("All")) {
			sql += "and d.strGCode='" + strGCode + "' ";
		}

		if (!strSGCode.equalsIgnoreCase("All")) {
			sql += "and c.strSGCode='" + strSGCode + "' ";
		}
		
		if(!strManufactureCode.equals("")){
			sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
		}
		
		/*
		 * if(strNonStkItems.equals("Stockable")) { sql+=
		 * "and b.strNonStockableItem='N' "; }
		 * if(strNonStkItems.equals("Non Stockable")) { sql+=
		 * "and b.strNonStockableItem='Y' "; }
		 */
		if (showZeroItems.equals("No")) {
			sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
		}

		System.out.println(sql);
		// List list=objStkFlashService.funGetStockFlashData(sql,clientCode,
		// userCode);
		List list = objGlobalService.funGetList(sql);

		List<clsStockFlashModel> listStockFlashModel = new ArrayList<clsStockFlashModel>();
		DecimalFormat df = new DecimalFormat("0.00");
		for (int cnt = 0; cnt < list.size(); cnt++) {
			Object[] arrObj = (Object[]) list.get(cnt);
			clsStockFlashModel objStkFlashModel = new clsStockFlashModel();
			// objStkFlashModel.setStrPropertyName(arrObj[0].toString());
			objStkFlashModel.setStrProdCode(arrObj[0].toString());
			objStkFlashModel.setStrProdName(arrObj[1].toString());
			// objStkFlashModel.setStrLocName(arrObj[3].toString());
			// objStkFlashModel.setStrGroupName(arrObj[4].toString());
			// objStkFlashModel.setStrSubGroupName(arrObj[5].toString());
			// objStkFlashModel.setStrUOM(arrObj[6].toString());
			// objStkFlashModel.setStrBinNo(arrObj[7].toString());
			// objStkFlashModel.setDblCostRM(arrObj[8].toString());
			// objStkFlashModel.setDblOpStock(arrObj[9].toString());
			//
			// objStkFlashModel.setDblReceipts(arrObj[10].toString());
			// double issueQty= Double.parseDouble(arrObj[11].toString());
			//
			// if(issueQty<0)
			// {
			// issueQty=-1*issueQty;
			// }

			// objStkFlashModel.setDblIssue(Double.toString(issueQty));

			objStkFlashModel.setDblClosingStock(arrObj[2].toString());
			totVal += Double.parseDouble(arrObj[3].toString());
			double value = Double.parseDouble(arrObj[3].toString());
			if (value < 0) {
				value = value * (0);
			}
			double temp = Double.parseDouble(df.format(value));
			dblTotalValue = Double.parseDouble(df.format(dblTotalValue)) + temp;
			objStkFlashModel.setDblValue(String.valueOf(value));
			// objStkFlashModel.setDblIssueUOMStock(arrObj[12].toString());
			// objStkFlashModel.setDblIssueConversion(arrObj[15].toString());
			// objStkFlashModel.setStrIssueUOM(arrObj[16].toString());
			// objStkFlashModel.setStrPartNo(arrObj[17].toString());

			listStockFlashModel.add(objStkFlashModel);
		}

		ModelAndView objModelView = funGetModelAndView(req);
		objModelView.addObject("stkFlashList", listStockFlashModel);
		System.out.println(totVal);
		List stkFlashList = new ArrayList();
		stkFlashList.add(listStockFlashModel);
		stkFlashList.add(dblTotalValue);
		return stkFlashList;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmStockFlashTotalReport", method = RequestMethod.GET)
	private @ResponseBody List funShowStockTotalFlash(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate, @RequestParam(value = "prodType") String prodType, @RequestParam(value = "ManufCode") String strManufactureCode, HttpServletRequest req, HttpServletResponse resp) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String[] spParam1 = param1.split(",");
		String locCode = spParam1[1];
		String showZeroItems = spParam1[3];
		String strSGCode = spParam1[4];
		String strNonStkItems = spParam1[5];
		String strGCode = spParam1[6];
		String qtyWithUOM = spParam1[7];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);
		String startDate = req.getSession().getAttribute("startDate").toString();
		String[] sp = startDate.split(" ");
		String[] spDate = sp[0].split("/");
		startDate = spDate[2] + "-" + spDate[1] + "-" + spDate[0];
		System.out.println(startDate);
		String newToDate = "";
		String stockableItem = "B";
		if (strNonStkItems.equals("Stockable")) {
			stockableItem = "Y";
		}
		if (strNonStkItems.equals("Non Stockable")) {
			stockableItem = "N";
		}
		
		double dblTotalValue = 0,totalOpeningStock = 0,totalGRN = 0,totalSCGRN = 0,totalStkTransferIn = 0;
		double totalStkAdjIn = 0,totalMISIn = 0,totalProducedQty = 0,totalSalesRet = 0,totalMaterialRet = 0;
		double totalPurchaseRet = 0,totalDelNote = 0,totalStkTransOut = 0,totalStkAdjOut = 0,totalMISOut = 0;
		double totalQtyConsumed = 0,totalSaleAmt = 0,totalClosingStk = 0,totalValueTotal = 0,totalIssueUOMStk =0;
		
		objGlobal.funInvokeStockFlash(startDate, locCode, fromDate, toDate, clientCode, userCode, stockableItem, req, resp);
		String sql = "";

		double dblPrice = 0.0;
		String currCode = req.getSession().getAttribute("currencyCode").toString();
		clsCurrencyMasterModel objModel = objCurrencyMasterService.funGetCurrencyMaster(currCode, clientCode);

		if (qtyWithUOM.equals("No")) {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + ",d.strGName,c.strSGName,b.strUOM,b.strBinNo " + " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) " + ",a.dblOpeningStk,a.dblGRN,a.dblSCGRN" + ",a.dblStkTransIn,a.dblStkAdjIn,a.dblMISIn,a.dblQtyProduced" + ",a.dblSalesReturn,a.dblMaterialReturnIn,a.dblPurchaseReturn"
					+ ",a.dblDeliveryNote,a.dblStkTransOut,a.dblStkAdjOut,a.dblMISOut" + ",a.dblQtyConsumed,a.dblSales,a.dblMaterialReturnOut " + ",a.dblClosingStk," + "(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value"
					+ ",a.dblClosingStk as IssueUOMStock "
					+ ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

			if (strNonStkItems.equals("Non Stockable")) {
				sql += "	and b.strNonStockableItem='Y' ";
			} else if (strNonStkItems.equals("Stockable")) {
				sql += "	and b.strNonStockableItem='N' ";
			}
			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}

			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and  b.strProdType = '" + prodType + "'  ";
			}
			sql += "and a.strClientCode='" + clientCode + "' ";
	
		} else {
			sql = "select f.strPropertyName,a.strProdCode,b.strProdName,e.strLocName" + " ,d.strGName,c.strSGName,b.strUOM,b.strBinNo"
					// + " ,b.dblCostRM"
					+ " ,if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice) "

					+ ",funGetUOM(a.dblOpeningStk,b.dblRecipeConversion,b.dblIssueConversion,b.strReceivedUOM,b.strRecipeUOM) as OpeningStk"

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
					+ ",(a.dblClosingStk*if(ifnull(g.dblPrice,0)=0,b.dblCostRM,g.dblPrice)) as Value,"

					+ "a.dblClosingStk as IssueUOMStock "
					+ ",b.dblIssueConversion,b.strIssueUOM,b.strPartNo "
	
					+ " FROM tblcurrentstock a " + " left outer join tblproductmaster b on a.strProdCode=b.strProdCode " + " left outer join tblsubgroupmaster c on b.strSGCode=c.strSGCode " + " left outer join tblgroupmaster d on c.strGCode=d.strGCode " + " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode "
					+ " left outer join tblpropertymaster f on e.strPropertyCode=f.strPropertyCode " + " left outer join tblreorderlevel g on a.strProdCode=g.strProdCode and g.strLocationCode='" + locCode + "'  " + " where  a.strUserCode='" + userCode + "' ";

			if (strNonStkItems.equals("Non Stockable")) {
				sql += "	and b.strNonStockableItem='Y' ";
			} else if (strNonStkItems.equals("Stockable")) {
				sql += "	and b.strNonStockableItem='N' ";
			}
			if(!strManufactureCode.equals("")){
				sql += " and b.strManufacturerCode='" + strManufactureCode + "'";	
			}
			if (!(prodType.equalsIgnoreCase("ALL"))) {
				sql += " and b.strProdType = '" + prodType + "'  ";
			}
			sql += "and a.strClientCode='" + clientCode + "' ";

		}

		if (!strGCode.equalsIgnoreCase("All")) {
			sql += "and d.strGCode='" + strGCode + "' ";
		}

		if (!strSGCode.equalsIgnoreCase("All")) {
			sql += "and c.strSGCode='" + strSGCode + "' ";
		}

		if (showZeroItems.equals("No")) {
			sql += "and (a.dblOpeningStk >0 or a.dblGRN >0 or dblSCGRN >0 or a.dblStkTransIn >0 or a.dblStkAdjIn >0 " + "or a.dblMISIn >0 or a.dblQtyProduced >0 or a.dblMaterialReturnIn>0 or a.dblStkTransOut >0 " + "or a.dblStkAdjOut >0 or a.dblMISOut >0 or a.dblQtyConsumed  >0 or a.dblSales  >0 " + "or a.dblMaterialReturnOut  >0 or a.dblDeliveryNote > 0)";
		}

		System.out.println(sql);
		
		List list = objGlobalService.funGetList(sql);

		List<clsStockFlashModel> listStockFlashModel = new ArrayList<clsStockFlashModel>();

		for (int cnt = 0; cnt < list.size(); cnt++) 
		{
			Object[] arrObj = (Object[]) list.get(cnt);
			
			totalOpeningStock += new Double(arrObj[9].toString());
			totalGRN += new Double(arrObj[10].toString());
			totalSCGRN +=new Double(arrObj[11].toString());
			totalStkTransferIn +=new Double(arrObj[12].toString());
			totalStkAdjIn +=new Double(arrObj[13].toString());
			totalMISIn +=new Double(arrObj[14].toString());
			totalProducedQty +=new Double(arrObj[15].toString());
			totalSalesRet += new Double(arrObj[16].toString());
			totalMaterialRet +=new Double(arrObj[17].toString());
			totalPurchaseRet +=new Double(arrObj[18].toString());
			totalDelNote +=new Double(arrObj[19].toString());
			totalStkTransOut += new Double(arrObj[20].toString());
			totalStkAdjOut +=new Double(arrObj[21].toString());
			totalMISOut += new Double(arrObj[22].toString());
			totalQtyConsumed +=new Double(arrObj[23].toString());
			totalSaleAmt += new Double(arrObj[24].toString());
			totalClosingStk += new Double(arrObj[26].toString());
			totalValueTotal += new Double(arrObj[27].toString());
			totalIssueUOMStk += new Double(arrObj[26].toString());
		}

		ModelAndView objModelView = funGetModelAndView(req);
		clsStockFlashModel obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Opening Stock");
		obStkjModel.setDblValue(String.valueOf(totalOpeningStock));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("GRN");
		obStkjModel.setDblValue(String.valueOf(totalGRN));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("SCGRN");
		obStkjModel.setDblValue(String.valueOf(totalSCGRN));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Stock Transfer");
		obStkjModel.setDblValue(String.valueOf(totalStkTransferIn));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Stock Adj In");
		obStkjModel.setDblValue(String.valueOf(totalStkAdjIn));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("MIS In");
		obStkjModel.setDblValue(String.valueOf(totalMISIn));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Qty Produced");
		obStkjModel.setDblValue(String.valueOf(totalProducedQty));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Sales Return");
		obStkjModel.setDblValue(String.valueOf(totalSalesRet));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Material Return");
		obStkjModel.setDblValue(String.valueOf(totalMaterialRet));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Purchase Return");
		obStkjModel.setDblValue(String.valueOf(totalPurchaseRet));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Delivery Note");
		obStkjModel.setDblValue(String.valueOf(totalDelNote));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Stock Transfer out");
		obStkjModel.setDblValue(String.valueOf(totalStkTransOut));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Stock Adj Out");
		obStkjModel.setDblValue(String.valueOf(totalStkAdjOut));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("MIS Out");
		obStkjModel.setDblValue(String.valueOf(totalMISOut));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Quantity Consumed");
		obStkjModel.setDblValue(String.valueOf(totalQtyConsumed));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Sale Amount");
		obStkjModel.setDblValue(String.valueOf(totalSaleAmt));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Closing Stock");
		obStkjModel.setDblValue(String.valueOf(totalClosingStk));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Value");
		obStkjModel.setDblValue(String.valueOf(totalValueTotal));
		listStockFlashModel.add(obStkjModel);
		obStkjModel=new clsStockFlashModel();
		obStkjModel.setStrPropertyName("Issue UOM Stock");
		obStkjModel.setDblValue(String.valueOf(totalIssueUOMStk));
		listStockFlashModel.add(obStkjModel);
		objModelView.addObject("stkFlashList", listStockFlashModel);
		List stkFlashList = new ArrayList();
		stkFlashList.add(listStockFlashModel);
		return stkFlashList;
	}

}
