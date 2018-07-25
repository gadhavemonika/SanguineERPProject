package com.sanguine.crm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.math.BigDecimal;
import com.sanguine.crm.bean.clsSalesReturnBean;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;

@Controller
public class clsSalesReturnFlashController {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsCRMSettlementMasterService objSettlementService;

	
	@RequestMapping(value = "/frmSalesReturnFlash", method = RequestMethod.GET)
	public ModelAndView funInvoice(@ModelAttribute("command") clsSalesReturnBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);
	}
	private ModelAndView funGetModelAndView(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		ModelAndView objModelView = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmSalesReturnFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmSalesReturnFlash");
		}
		Map<String, String> mapProperty = objGlobalService.funGetPropertyList(clientCode);
		if (mapProperty.isEmpty()) {
			mapProperty.put("", "");
		}
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String locationCode = req.getSession().getAttribute("locationCode").toString();
		Map<String, String> settlementList = objSettlementService.funGetSettlementComboBox(clientCode);
		settlementList.put("All", "All");
		objModelView.addObject("settlementList", settlementList);

		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		return objModelView;
	}
	
	
	@RequestMapping(value = "/loadSalesReturnFlashRegionWise", method = RequestMethod.GET)
	public @ResponseBody List funLoadSalesReturnFlash(HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");

		List<clsSalesReturnBean> listofSalesRetFlash = new ArrayList<clsSalesReturnBean>();
		List listofSaleRetuTotal = new ArrayList<>();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		
		String sql=" select a.strSRCode,a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),a.strLocCode,b.strPName,(a.dblTotalAmt-a.dblTaxAmt),a.dblTaxAmt,a.dblTotalAmt,a.strDCCode"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by c.strSRCode;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				clsSalesReturnBean objBean = new clsSalesReturnBean();
				objBean.setStrSRCode(objRet[0].toString());
				objBean.setStrAgainst(objRet[1].toString());
				objBean.setDteSRDate(objRet[2].toString());
				objBean.setStrCustName(objRet[4].toString());
				objBean.setDblTotalAmt(objRet[5].toString());
				objBean.setDblTaxAmt(Double.parseDouble(objRet[6].toString()));
				objBean.setDblGrandTotal(Double.parseDouble(objRet[7].toString()));
				objBean.setStrDCCode(objRet[8].toString());
				//objBean.setStrClientCode(objRet[0].toString());
				sqlLoc.setLength(0);
				sqlLoc.append("select a.strPropertyName from tblpropertymaster a, tbllocationmaster b where "
						+ " a.strPropertyCode=b.strPropertyCode and b.strLocCode='"+objRet[3].toString()+"';");
				
				list = objGlobalService.funGetList(sqlLoc.toString(), "sql");
				if(!list.isEmpty()){
					objBean.setStrPropertyName(list.get(0).toString());
				}
				
				listofSalesRetFlash.add(objBean);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[7].toString())).add(dblTotalValue);
				dblSubTotalValue =  new BigDecimal(objRet[5].toString()).add(dblSubTotalValue);
				dblTaxTotalValue =  new BigDecimal(objRet[6].toString()).add(dblTaxTotalValue);
			}
		}
		listofSaleRetuTotal.add(listofSalesRetFlash);
		listofSaleRetuTotal.add(dblTotalValue);
		listofSaleRetuTotal.add(dblSubTotalValue);
		listofSaleRetuTotal.add(dblTaxTotalValue);
		
		return listofSaleRetuTotal;
	}

	
	@RequestMapping(value = "/loadItemWiseSaleReturnDtl", method = RequestMethod.GET)
	public @ResponseBody List funLoadItemWiseSaleReturnDtl(HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblQty = new BigDecimal(0);
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");

		List<clsSalesReturnBean> listofSalesRetFlash = new ArrayList<clsSalesReturnBean>();
		List listofSaleRetuTotal = new ArrayList<>();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		
		String sql=" select d.strProdCode,d.strProdName, a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),e.strLocName,b.strPName,sum(c.dblQty * c.dblPrice),sum(c.dblQty)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode "
				+ " left outer join tblproductmaster d on c.strProdCode=d.strProdCode"
				+ " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by c.strProdCode"
				+ " order by c.strSRCode,d.strProdName;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				clsSalesReturnBean objBean = new clsSalesReturnBean();
				objBean.setStrSRCode(objRet[0].toString());
				objBean.setStrProductName(objRet[1].toString());
				objBean.setStrAgainst(objRet[2].toString());
				objBean.setDteSRDate(objRet[3].toString());
				objBean.setStrLocName(objRet[4].toString());
				objBean.setStrCustName(objRet[5].toString());
				objBean.setDblConversion(Double.parseDouble(objRet[7].toString()));//For Qty
				objBean.setDblTotalAmt(objRet[6].toString());
				//objBean.setDblGrandTotal(Double.parseDouble(objRet[6].toString())+Double.parseDouble(objRet[7].toString()));
				//objBean.setStrClientCode(objRet[0].toString());
				
				
				listofSalesRetFlash.add(objBean);
				
				dblQty =  new BigDecimal(objRet[7].toString()).add(dblQty);
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[6].toString())).add(dblTotalValue);
				//dblSubTotalValue =  new BigDecimal(objRet[6].toString()).add(dblSubTotalValue);
				//dblTaxTotalValue =  new BigDecimal(objRet[7].toString()).add(dblTaxTotalValue);
			}
		}
		listofSaleRetuTotal.add(listofSalesRetFlash);
		listofSaleRetuTotal.add(dblTotalValue);
		listofSaleRetuTotal.add(dblQty);
		//listofSaleRetuTotal.add(dblSubTotalValue);
		//listofSaleRetuTotal.add(dblTaxTotalValue);
		
		
		return listofSaleRetuTotal;
	}
	@RequestMapping(value = "/loadCustomerWiseSaleReturnDtl", method = RequestMethod.GET)
	public @ResponseBody List funLoadCustomerWiseSaleReturnDtl(HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");

		List<clsSalesReturnBean> listofSalesRetFlash = new ArrayList<clsSalesReturnBean>();
		List listofSaleRetuTotal = new ArrayList<>();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		
		String sql=" SELECT b.strPCode,b.strPName,a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),d.strLocName,sum(c.dblQty*c.dblPrice)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode left outer join tbllocationmaster d on a.strLocCode=d.strLocCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by b.strPCode"
				+ " order by b.strPName";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				clsSalesReturnBean objBean = new clsSalesReturnBean();
				objBean.setStrSRCode(objRet[0].toString());
				objBean.setStrAgainst(objRet[2].toString());
				objBean.setDteSRDate(objRet[3].toString());
				objBean.setStrCustName(objRet[1].toString());
				objBean.setStrLocName(objRet[4].toString());
				objBean.setDblTotalAmt(objRet[5].toString());
				listofSalesRetFlash.add(objBean);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[5].toString())).add(dblTotalValue);

			}
		}
		listofSaleRetuTotal.add(listofSalesRetFlash);
		listofSaleRetuTotal.add(dblTotalValue);
		
		return listofSaleRetuTotal;
	}
	
	@RequestMapping(value = "/loadGroupSubGroupWiseSaleReturnDtl", method = RequestMethod.GET)
	public @ResponseBody List funLoadGroupSubGroupWiseSaleReturnDtl(HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblQty = new BigDecimal(0);
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");

		List<clsSalesReturnBean> listofSalesRetFlash = new ArrayList<clsSalesReturnBean>();
		List listofSaleRetuTotal = new ArrayList<>();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		
		String sql=" select g.strGName,f.strSGName, a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),e.strLocName,b.strPName,sum(c.dblQty * c.dblPrice),sum(c.dblQty)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode "
				+ " left outer join tblproductmaster d on c.strProdCode=d.strProdCode"
				+ " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode"
				+ " Left outer join tblsubgroupmaster f on d.strSGCode=f.strSGCode"
				+ " Left outer join tblgroupmaster g on f.strGCode=g.strGCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " GROUP BY  g.strGCode,f.strSGCode "
				+ " ORDER BY g.strGName;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				clsSalesReturnBean objBean = new clsSalesReturnBean();
				objBean.setStrSRCode(objRet[0].toString());
				objBean.setStrProductName(objRet[1].toString());
				objBean.setStrAgainst(objRet[2].toString());
				objBean.setDteSRDate(objRet[3].toString());
				objBean.setStrLocName(objRet[4].toString());
				objBean.setStrCustName(objRet[5].toString());
				objBean.setDblConversion(Double.parseDouble(objRet[7].toString()));//For Qty
				objBean.setDblTotalAmt(objRet[6].toString());
				//objBean.setDblGrandTotal(Double.parseDouble(objRet[6].toString())+Double.parseDouble(objRet[7].toString()));
				//objBean.setStrClientCode(objRet[0].toString());
				
				
				listofSalesRetFlash.add(objBean);
				
				dblQty =  new BigDecimal(objRet[7].toString()).add(dblQty);
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[6].toString())).add(dblTotalValue);
				//dblSubTotalValue =  new BigDecimal(objRet[6].toString()).add(dblSubTotalValue);
				//dblTaxTotalValue =  new BigDecimal(objRet[7].toString()).add(dblTaxTotalValue);
			}
		}
		listofSaleRetuTotal.add(listofSalesRetFlash);
		listofSaleRetuTotal.add(dblTotalValue);
		listofSaleRetuTotal.add(dblQty);
		//listofSaleRetuTotal.add(dblSubTotalValue);
		//listofSaleRetuTotal.add(dblTaxTotalValue);
		
		
		return listofSaleRetuTotal;
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportRegionWiseSalesReturnFlash", method = RequestMethod.GET)
	private ModelAndView funExportRegionWiseSalesReturnFlash(HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");
		
		String userCode = request.getSession().getAttribute("usercode").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");

	
		String sql=" select a.strSRCode,a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),a.strLocCode,b.strPName,(a.dblTotalAmt-a.dblTaxAmt),a.dblTaxAmt,a.dblTotalAmt,a.strDCCode"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by c.strSRCode;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				List DataList = new ArrayList<>();
				sqlLoc.setLength(0);
				sqlLoc.append("select a.strPropertyName from tblpropertymaster a, tbllocationmaster b where "
						+ " a.strPropertyCode=b.strPropertyCode and b.strLocCode='"+objRet[3].toString()+"';");
				
				list = objGlobalService.funGetList(sqlLoc.toString(), "sql");
				if(!list.isEmpty()){
					DataList.add(list.get(0).toString());
				}
				DataList.add(objRet[0].toString());
				DataList.add(objRet[1].toString());
				DataList.add(objRet[8].toString());
				DataList.add(objRet[4].toString());
				DataList.add(objRet[2].toString());
				DataList.add(Double.parseDouble(objRet[5].toString()));
				DataList.add(Double.parseDouble(objRet[6].toString()));
				DataList.add(Double.parseDouble(objRet[7].toString()));		
				detailList.add(DataList);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[7].toString())).add(dblTotalValue);
				dblSubTotalValue =  new BigDecimal(objRet[5].toString()).add(dblSubTotalValue);
				dblTaxTotalValue =  new BigDecimal(objRet[6].toString()).add(dblTaxTotalValue);
			}
		}
		totalsList.add(dblSubTotalValue);
		totalsList.add(dblTaxTotalValue);
		totalsList.add(dblTotalValue);
		
	
		headerList.add("Property Name");
		headerList.add("SR Code");
		headerList.add("Date");
		headerList.add("DC Code");
		headerList.add("Customer Name");
		headerList.add("Against");
		headerList.add("SubTotal");
		headerList.add("Tax Amount");
		headerList.add("Grand Total");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("RegionWiseSalesReturnData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportItemWiseSalesReturnFlash", method = RequestMethod.GET)
	private ModelAndView funExportItemWiseSalesReturnFlash(HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");	
		
		String userCode = request.getSession().getAttribute("usercode").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblQty = new BigDecimal(0);
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");
		
		String sql=" select d.strProdCode,d.strProdName, a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),e.strLocName,b.strPName,sum(c.dblQty * c.dblPrice),sum(c.dblQty)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode "
				+ " left outer join tblproductmaster d on c.strProdCode=d.strProdCode"
				+ " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by c.strProdCode"
				+ " order by c.strSRCode,d.strProdName;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objRet[0].toString());
				DataList.add(objRet[1].toString());
				DataList.add(objRet[2].toString());
				DataList.add(objRet[3].toString());
				DataList.add(objRet[4].toString());
				DataList.add(objRet[5].toString());
				DataList.add(objRet[7].toString());
				DataList.add(objRet[6].toString());
				detailList.add(DataList);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[6].toString())).add(dblTotalValue);
				dblQty =  new BigDecimal(objRet[7].toString()).add(dblQty);
			}
		}
		totalsList.add(dblQty);
		totalsList.add(dblTotalValue);
		
	
		headerList.add("SR Code");
		headerList.add("Product Name");
		headerList.add("Against");
		headerList.add("Date");
		headerList.add("Customer Name");
		headerList.add("Location");
		headerList.add("Quantity");
		headerList.add("SubTotal");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("ItemWiseSalesReturnData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportCustomerWiseSalesReturnFlash", method = RequestMethod.GET)
	private ModelAndView funExportCustomerWiseSalesReturnFlash(HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");
		totalsList.add("");	

		String userCode = request.getSession().getAttribute("usercode").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");
		
		String sql=" SELECT b.strPCode,b.strPName,a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),d.strLocName,sum(c.dblQty*c.dblPrice)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode left outer join tbllocationmaster d on a.strLocCode=d.strLocCode"
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " group by b.strPCode"
				+ " order by b.strPName";
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");
		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objRet[0].toString());
				DataList.add(objRet[1].toString());
				DataList.add(objRet[2].toString());
				DataList.add(objRet[4].toString());
				DataList.add(objRet[3].toString());
				DataList.add(objRet[5].toString());
				detailList.add(DataList);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[5].toString())).add(dblTotalValue);
			}
		}
		totalsList.add(dblTotalValue);
		
	
		headerList.add("Customer Code");
		headerList.add("Customer Name");
		headerList.add("Against");
		headerList.add("Location");
		headerList.add("Date");
		headerList.add("SubTotal");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("CustomerWiseSalesReturnData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportGroupSubGroupWiseSalesReturnFlash", method = RequestMethod.GET)
	private ModelAndView funExportGroupSubGroupWiseInvoiceFlash(HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");	
		
		String userCode = request.getSession().getAttribute("usercode").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		BigDecimal dblQty = new BigDecimal(0);
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");
		
		String sql=" select g.strGName,f.strSGName, a.strAgainst,DATE_FORMAT(Date(a.dteSRDate),'%d-%m-%Y'),e.strLocName,b.strPName,sum(c.dblQty * c.dblPrice),sum(c.dblQty)"
				+ " from tblsalesreturnhd a "
				+ " left outer join tblsalesreturndtl c on a.strSRCode=c.strSRCode"
				+ " left outer join tblpartymaster b on a.strCustCode=b.strPCode "
				+ " left outer join tblproductmaster d on c.strProdCode=d.strProdCode"
				+ " left outer join tbllocationmaster e on a.strLocCode=e.strLocCode"
				+ " Left outer join tblsubgroupmaster f on d.strSGCode=f.strSGCode"
				+ " Left outer join tblgroupmaster g on f.strGCode=g.strGCode "
				+ " where a.strLocCode='"+locCode+"' and a.strClientCode='"+strClientCode+"' "
				+ " and date(a.dteSRDate) BETWEEN '"+fromDate+"' and '"+toDate+"'"
				+ " GROUP BY  g.strGCode,f.strSGCode "
				+ " ORDER BY g.strGName;";
		
		List listOfSaleRet = objGlobalService.funGetList(sql, "sql");

		if (!listOfSaleRet.isEmpty()) {
			StringBuilder sqlLoc=new StringBuilder("");
			List list =null; 
			for (int i = 0; i < listOfSaleRet.size(); i++) {
				Object[] objRet = (Object[]) listOfSaleRet.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objRet[0].toString());
				DataList.add(objRet[1].toString());
				DataList.add(objRet[2].toString());
				DataList.add(objRet[3].toString());
				DataList.add(objRet[5].toString());
				DataList.add(objRet[4].toString());
				DataList.add(objRet[7].toString());
				DataList.add(objRet[6].toString());
				detailList.add(DataList);
				
				dblTotalValue = new BigDecimal(Double.parseDouble(objRet[6].toString())).add(dblTotalValue);
				dblQty =  new BigDecimal(objRet[7].toString()).add(dblQty);
			}
		}
		totalsList.add(dblQty);
		totalsList.add(dblTotalValue);
		
	
		headerList.add("Group Name");
		headerList.add("SubGroup Name");
		headerList.add("Against");
		headerList.add("Date");
		headerList.add("Customer Name");
		headerList.add("Location");
		headerList.add("Quantity");
		headerList.add("SubTotal");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("GroupSubGroupWiseSalesReturnData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
}
