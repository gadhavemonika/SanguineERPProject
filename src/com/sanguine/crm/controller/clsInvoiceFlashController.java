package com.sanguine.crm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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

import com.ibm.icu.math.BigDecimal;
import com.sanguine.bean.clsStockFlashBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.model.clsInvoiceHdModel;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsInvoiceFlashController {

	@Autowired
	private clsInvoiceHdService objInvoiceHdService;

	@Autowired
	private clsGlobalFunctionsService objGlobalService;
	@Autowired
	private clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsCRMSettlementMasterService objSettlementService;
	
	@Autowired
	private clsCurrencyMasterService objCurrencyMasterService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;
	
	String baseCurrencyCode="";
	Map<String,String> currencyList=new TreeMap<String, String>();
	@RequestMapping(value = "/frmInvoiceFlash", method = RequestMethod.GET)
	public ModelAndView funInvoice(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		return funGetModelAndView(req);
	}

	@RequestMapping(value = "/loadInvoiceFlash", method = RequestMethod.GET)
	public @ResponseBody List funLoadInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String dbWebBook=request.getSession().getAttribute("WebBooksDB").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		DecimalFormat df = new DecimalFormat("#.##");
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, strClientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, strClientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		List<clsInvoiceBean> listofInvFlash = new ArrayList<clsInvoiceBean>();
		List listofInvoiveTotal = new ArrayList<>();
		String sqlInvoiceFlash = "select a.strInvCode ,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strPName,a.strAgainst,a.strVehNo,a.dblSubTotalAmt/" + currValue + ",a.dblTaxAmt/" + currValue + ""
				+ ",a.dblGrandTotal/" + currValue + ",a.strExciseable,c.strSettlementDesc,ifnull(d.strVouchNo,'') "
				+ " FROM tblpartymaster b,tblsettlementmaster c,tblinvoicehd a left outer join "+dbWebBook+".tbljvhd d on a.strInvCode=d.strSourceDocNo"
				+ " where   date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strLocCode='" + locCode +"' "
				+ " and a.strCustCode=b.strPCode and  a.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		

		sqlInvoiceFlash = sqlInvoiceFlash + "and a.strSettlementCode=c.strSettlementCode "
			+ "  order by a.strInvCode ";
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");

		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				clsInvoiceBean objBean = new clsInvoiceBean();
				objBean.setStrInvCode(objInvoice[0].toString());
				objBean.setDteInvDate(objInvoice[1].toString());
				objBean.setStrCustName(objInvoice[2].toString());
				objBean.setStrAgainst(objInvoice[3].toString());
				objBean.setStrVehNo(objInvoice[4].toString());
				objBean.setDblSubTotalAmt(Double.parseDouble(objInvoice[5].toString()));
				objBean.setDblTaxAmt(Double.parseDouble(objInvoice[6].toString()));
				objBean.setDblTotalAmt(Double.parseDouble(objInvoice[7].toString()));
				objBean.setStrExciseable(objInvoice[8].toString());
				objBean.setStrSerialNo(objInvoice[10].toString());
				objBean.setStrCurrency(currencyName);
				listofInvFlash.add(objBean);
				objBean.setStrSettleDesc(objInvoice[9].toString());
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[7].toString()));
				dblTotalValue = dblTotalValue.add(value);
				value = new BigDecimal(Double.parseDouble(objInvoice[5].toString()));
				dblSubTotalValue = dblSubTotalValue.add(value);

				value = new BigDecimal(Double.parseDouble(objInvoice[6].toString()));
				dblTaxTotalValue = dblTaxTotalValue.add(value);
			}
		}
		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		listofInvoiveTotal.add(dblSubTotalValue);
		listofInvoiveTotal.add(dblTaxTotalValue);
		System.out.print(dblTaxTotalValue + "ttoalsubtotal" + dblTaxTotalValue);
		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/exportInvoiceExcel", method = RequestMethod.GET)
	public ModelAndView funExportInvoiceFlash(@RequestParam("custcode") String strCustCode, HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		List listInvoice = new ArrayList();

		clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);

		String repeortfileName = "InvoiceFlashReport" + "_" + objLocCode.getStrLocName() + "_" + fromDate + "_To_" + toDate + "_" + userCode;
		repeortfileName = repeortfileName.replaceAll(" ", "");
		listInvoice.add(repeortfileName);

		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		String[] ExcelHeader = { "Invoice Code", "Date", "Customer Name", "Against", "Vehicle No", "Excisable", "Sub Total", "Tax Amount", "Total Amount" };
		listInvoice.add(ExcelHeader);

		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "select a.strInvCode ,a.dteInvDate,b.strPName,a.strAgainst,a.strVehNo,a.dblSubTotalAmt,a.dblTaxAmt,a.dblGrandTotal,a.strExciseable from tblinvoicehd a ,tblpartymaster b  where   date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strLocCode='" + locCode + "' and a.strCustCode=b.strPCode and  a.strClientCode='" + strClientCode + "'";
		if (!strCustCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and a.strCustCode='" + strCustCode + "' ";
		}

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(objInvoice[8].toString());
				floatingPoint = Double.parseDouble(objInvoice[5].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);

				floatingPoint = Double.parseDouble(objInvoice[6].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);

				floatingPoint = Double.parseDouble(objInvoice[7].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);

				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[7].toString()));
				dblTotalValue = dblTotalValue.add(value);
				value = new BigDecimal(Double.parseDouble(objInvoice[5].toString()));
				dblSubTotalValue = dblSubTotalValue.add(value);

				value = new BigDecimal(Double.parseDouble(objInvoice[6].toString()));
				dblTaxTotalValue = dblTaxTotalValue.add(value);
				listofInvFlash.add(DataList);

				if (i == listOfInvoice.size() - 1) {
					DataList = new ArrayList<>();
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("");
					DataList.add("Total");
					String a = df.format(dblSubTotalValue);
					DataList.add(df.format(dblSubTotalValue));
					a = df.format(dblTaxTotalValue);
					DataList.add(df.format(dblTaxTotalValue));
					DataList.add(df.format(dblTotalValue));
					listofInvFlash.add(DataList);
				}
			}
		}
		listInvoice.add(listofInvFlash);
		// return new ModelAndView("excelView", "stocklist", listInvoice);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", listInvoice);
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
			objModelView = new ModelAndView("frmInvoiceFlash_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			objModelView = new ModelAndView("frmInvoiceFlash");
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
		
		currencyList.put("All", "All");
		List<clsCurrencyMasterModel> listCurrency = objCurrencyMasterService.funGetAllCurrencyDataModel(clientCode);
		if (listCurrency != null) 
		{
			for(int cnt=0;cnt<listCurrency.size();cnt++)
			{
				clsCurrencyMasterModel objModel=listCurrency.get(cnt);
				currencyList.put(objModel.getStrCurrencyCode(),objModel.getStrCurrencyName());
			}
		
		}
		objModelView.addObject("currencyList", currencyList);
		
		clsPropertySetupModel objSetup = objSetupMasterService.funGetObjectPropertySetup(propertyCode, clientCode);
		baseCurrencyCode= objSetup.getStrCurrencyCode();
		
		objModelView.addObject("listProperty", mapProperty);
		objModelView.addObject("LoggedInProp", propertyCode);
		objModelView.addObject("LoggedInLoc", locationCode);

		return objModelView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadTenderWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funTenderWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();

		clsLocationMasterModel objLocCode = objLocationMasterService.funGetObject(locCode, clientCode);

		BigDecimal dblTotalValue = new BigDecimal(0);

		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "select c.strSettlementCode,ifnull(c.strSettlementDesc,''),ifnull(c.strSettlementType,''),sum(a.dblGrandTotal) "
				+ " from tblinvoicehd a,tblsettlementmaster c,tblpartymaster d  "
				+ " where  a.strSettlementCode=c.strSettlementCode and  a.strCustCode=d.strPCode "
				+ "  and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		

		sqlInvoiceFlash += "group by a.strSettlementCode ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());

				floatingPoint = Double.parseDouble(objInvoice[3].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadOpertorWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funOperatorWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "SELECT b.strUserCode,b.strUserName,  sum(a.dblGrandTotal)/" + currValue + ",sum(a.dblDiscountAmt)/" + currValue + ",ifnull(c.strSettlementDesc,'')  from tblinvoicehd a,tbluserhd b,tblsettlementmaster c " + " WHERE a.strUserCreated=b.strUserCode and a.strSettlementCode=c.strSettlementCode " + " and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate
				+ "' and  a.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by a.strUserCreated,c.strSettlementCode order by a.strUserCreated ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		String strUserCode = "";
		double userSalesTtl = 0.0;
		Map<String, Double> hmUserSalesDtl = new HashMap<String, Double>();
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				floatingPoint = Double.parseDouble(objInvoice[2].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());

				if (!(objInvoice[0].toString().equals(strUserCode))) {
					// DataList.add("");
					if (i == 0) {
						hmUserSalesDtl.put(objInvoice[0].toString(), Double.parseDouble(objInvoice[2].toString()));
					} else {
						hmUserSalesDtl.put(strUserCode, userSalesTtl);
					}

					userSalesTtl = 0.0;
				} else {
					// DataList.add(userSalesTtl);
				}
				userSalesTtl += Double.parseDouble(objInvoice[2].toString());
				strUserCode = objInvoice[0].toString();
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
			hmUserSalesDtl.put(strUserCode, userSalesTtl);
		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		listofInvoiveTotal.add(hmUserSalesDtl);
		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCustomerWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funCustomerWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
	

		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "select b.strCustCode,a.strPName,a.strPType,count(b.strInvCode),sum(b.dblGrandTotal)/" + currValue + " " + " from tblpartymaster a,tblinvoicehd b where b.strCustCode=a.strPCode  and b.strLocCode='" + locCode + "' " + " and date(b.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  b.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strCustCode  order by sum(b.dblGrandTotal) desc ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(currencyName);

				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[4].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);

		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadSKUWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funProductWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		List listofInvFlash = new ArrayList();

		String strClientCode = request.getSession().getAttribute("clientCode").toString();
		String sqlInvoiceFlash = "SELECT  b.strProdCode,c.strProdName,sum(b.dblQty)/" + currValue + ", SUM(b.dblQty*b.dblPrice)/" + currValue + " FROM tblinvoicedtl b, tblproductmaster c,tblinvoicehd a,tblpartymaster d " + " WHERE a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode and a.strCustCode=d.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + strClientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strProdCode ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(currencyName);

				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				BigDecimal qty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(qty);
				
				
			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		listofInvoiveTotal.add(dblTotalQty);

		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadCategoryWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funCategoryWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String withZeroAmt = request.getParameter("withZeroAmt").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalDis = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		List listofInvFlash = new ArrayList();
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		String sqlInvoiceFlash = "select b.strSGCode,c.strSGName,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + ",e.strGName,sum(d.dblProdDiscAmount)/" + currValue + "  from tblinvoicehd a,tblproductmaster b,tblsubgroupmaster c,tblinvoicedtl d,tblgroupmaster e,tblpartymaster f " + " where a.strInvCode=d.strInvCode  and d.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=e.strGCode and a.strCustCode=f.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate
				+ "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "  group by  c.strSGCode ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[4].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				if(withZeroAmt.equals("Yes"))
				{
					double amt=Double.valueOf(objInvoice[3].toString())-Double.valueOf(objInvoice[5].toString());
					DataList.add(String.valueOf(amt));
				}
				else
				{
					DataList.add(objInvoice[3].toString());
				}
				
				DataList.add(objInvoice[5].toString());
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal disValue = new BigDecimal(Double.parseDouble(objInvoice[5].toString()));
				dblTotalDis= dblTotalDis.add(disValue);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				if(withZeroAmt.equals("Yes"))
				{
					dblTotalValue=dblTotalValue.subtract(dblTotalDis);
				}
				BigDecimal qtyTot = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty= dblTotalQty.add(qtyTot);

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		listofInvoiveTotal.add(dblTotalDis);
		listofInvoiveTotal.add(dblTotalQty);
		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadManufactureWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funManufactureWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);

		List listofInvFlash = new ArrayList();
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		String sqlInvoiceFlash = "  select b.strManufacturerCode,c.strManufacturerName,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + " from tblinvoicehd a,tblproductmaster b,tblmanufacturemaster c,tblinvoicedtl d,tblpartymaster e " + " where a.strInvCode=d.strInvCode  and d.strProdCode=b.strProdCode and b.strManufacturerCode=c.strManufacturerCode  and a.strCustCode=e.strPCode  and a.strLocCode='" + locCode + "' "
				+ " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += " group by  c.strManufacturerCode ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				BigDecimal Qty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(Qty);
				

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		listofInvoiveTotal.add(dblTotalQty);

		return listofInvoiveTotal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadDepartmentWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funDepartmentWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty= new BigDecimal(0);

		List listofInvFlash = new ArrayList();
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		String sqlInvoiceFlash = " select b.strLocCode,b.strLocName ,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + " from tblinvoicehd a,tbllocationmaster b,tblinvoicedtl d,tblpartymaster c " + "   where a.strInvCode=d.strInvCode  and a.strLocCode=b.strLocCode  and a.strCustCode=c.strPCode  " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!locCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + "and a.strLocCode='" + locCode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += " group by  a.strLocCode ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				BigDecimal totalQty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(totalQty);
				

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalQty);
		listofInvoiveTotal.add(dblTotalValue);

		return listofInvoiveTotal;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadMonthWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funMonthWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		List listofInvFlash = new ArrayList();

		String sqlInvoiceFlash = "select c.strSettlementCode,ifnull(c.strSettlementDesc,''),ifnull(c.strSettlementType,''),sum(a.dblGrandTotal)/" + currValue + ",MONTHNAME(DATE(a.dteInvDate)), YEAR(DATE(a.dteInvDate))"
				+ " from tblinvoicehd a,tblsettlementmaster c,tblpartymaster d " + " where  a.strSettlementCode=c.strSettlementCode and a.strCustCode=d.strPCode " + " and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by a.strSettlementCode,YEAR(DATE(a.dteInvDate)),MONTHNAME(DATE(a.dteInvDate))"
				        + "  order by YEAR(DATE(a.dteInvDate)) desc,MONTHNAME(DATE(a.dteInvDate)) desc";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());

				floatingPoint = Double.parseDouble(objInvoice[3].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);
				DataList.add(objInvoice[4].toString());
				DataList.add(objInvoice[5].toString());
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);
		return listofInvoiveTotal;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadRegionWiseDtl", method = RequestMethod.GET)
	public @ResponseBody List funRegionWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		List listofInvoiveTotal = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		List listofInvFlash = new ArrayList();

		String sqlInvoiceFlash = "SELECT c.strRegionCode,c.strRegionDesc, SUM(b.dblGrandTotal)/" + currValue + ""
				+ " FROM tblpartymaster a,tblinvoicehd b,tblregionmaster c"
				+ " WHERE b.strCustCode=a.strPCode AND a.strRegion=c.strRegionCode  "
				+ " and b.strLocCode='" + locCode + "' " + " "
				+ " and date(b.dteInvDate) between '" + fromDate + "' and '" + toDate + "' "
				+ " and  b.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by a.strRegion  order by sum(b.dblGrandTotal) desc ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(currencyName);
				listofInvFlash.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}

		}

		listofInvoiveTotal.add(listofInvFlash);
		listofInvoiveTotal.add(dblTotalValue);

		return listofInvoiveTotal;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportBillWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportBillWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

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
		totalsList.add("");
		totalsList.add("");
		
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String dbWebBook=request.getSession().getAttribute("WebBooksDB").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String currencyName="";

		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{   
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblSubTotalValue = new BigDecimal(0);
		BigDecimal dblTaxTotalValue = new BigDecimal(0);
		
		String sqlInvoiceFlash = "select a.strInvCode ,DATE_FORMAT(a.dteInvDate,'%d-%m-%Y'),b.strPName,a.strAgainst,a.strVehNo,a.dblSubTotalAmt/" + currValue + ",a.dblTaxAmt/" + currValue + ""
				+ ",a.dblGrandTotal/" + currValue + ",a.strExciseable,c.strSettlementDesc,ifnull(d.strVouchNo,'')  "
				+ " FROM tblpartymaster b,tblsettlementmaster c,tblinvoicehd a left outer join "+dbWebBook+".tbljvhd d on a.strInvCode=d.strSourceDocNo"
				+ " where   date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' " + " and a.strLocCode='" + locCode +"' "
				+ " and a.strCustCode=b.strPCode and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		
		sqlInvoiceFlash = sqlInvoiceFlash + "and a.strSettlementCode=c.strSettlementCode ";
		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		 for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[10].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[9].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(currencyName);
				DataList.add(Double.parseDouble(objInvoice[5].toString()));
				DataList.add(Double.parseDouble(objInvoice[6].toString()));
				DataList.add(Double.parseDouble(objInvoice[7].toString()));
				
				detailList.add(DataList);
				
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[7].toString()));
				dblTotalValue = dblTotalValue.add(value);
				value = new BigDecimal(Double.parseDouble(objInvoice[5].toString()));
				dblSubTotalValue = dblSubTotalValue.add(value);
				value = new BigDecimal(Double.parseDouble(objInvoice[6].toString()));
				dblTaxTotalValue = dblTaxTotalValue.add(value);
			}
		}

		totalsList.add(dblSubTotalValue);
		totalsList.add(dblTaxTotalValue);
		totalsList.add(dblTotalValue);
		
		
		headerList.add("Invoice Code");
		headerList.add("Date");
		headerList.add("JV No");
		headerList.add("Customer Name");
		headerList.add("Settement");
		headerList.add("Against");
		headerList.add("Vehicle No");
		headerList.add("Currency");
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
		
		retList.add("BillWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportSettlementWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportSettlementWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");	
		totalsList.add("");	
		
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		
		String sqlInvoiceFlash = "select c.strSettlementCode,ifnull(c.strSettlementDesc,''),ifnull(c.strSettlementType,''),sum(a.dblGrandTotal)/" + currValue + " "
				+ " from tblinvoicehd a,tblsettlementmaster c,tblpartymaster d "
				+ " where  a.strSettlementCode=c.strSettlementCode and  a.strCustCode=d.strPCode "
				+ " and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}

		sqlInvoiceFlash += "group by a.strSettlementCode ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		
		  for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(currencyName);
				DataList.add(new BigDecimal(Double.parseDouble(objInvoice[3].toString())));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
		}

		totalsList.add(dblTotalValue);
		
		headerList.add("Settlement Code");
		headerList.add("Settlement Name");
		headerList.add("Settlement Type");
		headerList.add("Currency");
		headerList.add("Sales Amount");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("SettlementWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportOperatorWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportOperatorWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

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

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		
		String sqlInvoiceFlash = "SELECT b.strUserCode,b.strUserName,  sum(a.dblGrandTotal)/" + currValue + ",sum(a.dblDiscountAmt)/" + currValue + ",ifnull(c.strSettlementDesc,'')  from tblinvoicehd a,tbluserhd b,tblsettlementmaster c " + " WHERE a.strUserCreated=b.strUserCode and a.strSettlementCode=c.strSettlementCode " + " and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate
				+ "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by a.strUserCreated,c.strSettlementCode order by a.strUserCreated ";


		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		String strUserCode = "";
		double userSalesTtl = 0.0;
		Map<String, Double> hmUserSalesDtl = new HashMap<String, Double>();
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		
		  for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				floatingPoint = Double.parseDouble(objInvoice[2].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);
				DataList.add(objInvoice[3].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(floatingPoint);

				if (!(objInvoice[0].toString().equals(strUserCode))) {
					// DataList.add("");
					if (i == 0) {
						hmUserSalesDtl.put(objInvoice[0].toString(), Double.parseDouble(objInvoice[2].toString()));
					} else {
						hmUserSalesDtl.put(strUserCode, userSalesTtl);
					}

					userSalesTtl = 0.0;
				} else {
					// DataList.add(userSalesTtl);
				}
				userSalesTtl += Double.parseDouble(objInvoice[2].toString());
				strUserCode = objInvoice[0].toString();
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
			hmUserSalesDtl.put(strUserCode, userSalesTtl);
		}

		totalsList.add(dblTotalValue);
		
		
		
		headerList.add("User Code");
		headerList.add("User Name");
		headerList.add("Currency");
		headerList.add("Sales Amount");
		headerList.add("Discount Amt");
		headerList.add("Settlement Mode");
		headerList.add("Operator total");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("OperatorWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportCustomerWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportCustomerWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

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

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		String sqlInvoiceFlash = "select b.strCustCode,a.strPName,a.strPType,count(b.strInvCode),sum(b.dblGrandTotal)/" + currValue + " " + " from tblpartymaster a,tblinvoicehd b where b.strCustCode=a.strPCode  and b.strLocCode='" + locCode + "' " + " and date(b.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  b.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strCustCode  order by sum(b.dblGrandTotal) desc ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		  for (int i = 0; i < listOfInvoice.size(); i++) 
			{
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[3].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[4].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[4].toString()));
				dblTotalValue = dblTotalValue.add(value);

		   }
		}

		totalsList.add(dblTotalValue);
		
		headerList.add("Customer Code");
		headerList.add("Customer Name");
		headerList.add("Customer Type");
		headerList.add("No Of Bills");
		headerList.add("Currency");
		headerList.add("Sales Amount");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("CustomerWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportProductWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportProductWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
			
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		String sqlInvoiceFlash = "SELECT  b.strProdCode,c.strProdName,sum(b.dblQty)/" + currValue + ", SUM(b.dblQty*b.dblPrice)/" + currValue + " FROM tblinvoicedtl b, tblproductmaster c,tblinvoicehd a,tblpartymaster d " + " WHERE a.strInvCode=b.strInvCode  and b.strProdCode=c.strProdCode and a.strCustCode=d.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by b.strProdCode ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		for (int i = 0; i < listOfInvoice.size(); i++) 
		   {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[2].toString()));
				DataList.add(Double.valueOf(objInvoice[3].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);			
				BigDecimal valueQty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(valueQty);
			}
		}

		totalsList.add(dblTotalQty);
		totalsList.add(dblTotalValue);
	
		
		headerList.add("Product Code");
		headerList.add("Product Name");
		headerList.add("Currency");
		headerList.add("Quantity");
		headerList.add("Sales Amount");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("ProductWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportGroupSubGroupWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportGroupSubGroupWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");
		
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String withZeroAmt = request.getParameter("withZeroAmt").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		BigDecimal dblTotalDis = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		String sqlInvoiceFlash = "select b.strSGCode,c.strSGName,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + ",e.strGName,sum(d.dblProdDiscAmount)/" + currValue + "  from tblinvoicehd a,tblproductmaster b,tblsubgroupmaster c,tblinvoicedtl d,tblgroupmaster e,tblpartymaster f " + " where a.strInvCode=d.strInvCode  and d.strProdCode=b.strProdCode and b.strSGCode=c.strSGCode and c.strGCode=e.strGCode and a.strCustCode=f.strPCode and a.strLocCode='" + locCode + "' " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate
				+ "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "  group by  c.strSGCode ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) 
		{
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				BigDecimal disValue = new BigDecimal(Double.parseDouble(objInvoice[5].toString()));
				dblTotalDis= dblTotalDis.add(disValue);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				if(withZeroAmt.equals("Yes"))
				{
					dblTotalValue=dblTotalValue.subtract(dblTotalDis);
				}
				BigDecimal qtyTot = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty= dblTotalQty.add(qtyTot);
			}
		}
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[4].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[2].toString()));
				DataList.add(Double.valueOf(objInvoice[5].toString()));
				if(withZeroAmt.equals("Yes"))
				{
					double amt=Double.valueOf(objInvoice[3].toString())-Double.valueOf(objInvoice[5].toString());
					DataList.add(String.valueOf(amt));
					double dbtTota=dblTotalValue.doubleValue();
					DataList.add(String.valueOf(Math.round((amt/dblTotalValue.doubleValue())*100))+"%");
				}
				else
				{
					DataList.add(Double.valueOf(objInvoice[3].toString()));
					DataList.add(String.valueOf(Math.round((Double.valueOf(objInvoice[3].toString())/dblTotalValue.doubleValue())*100))+"%");
				}
				
				detailList.add(DataList);
				
			}
		}
		totalsList.add(dblTotalQty);
		totalsList.add(dblTotalDis);
		totalsList.add(dblTotalValue);
		
		
		headerList.add("Group Name");
		headerList.add("SubGroup Name");
		headerList.add("Currency");
		headerList.add("Quantity");
		headerList.add("Dis Amount");
		headerList.add("Sales Amt");
		headerList.add("Sales Per");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("GroupSubGroupWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportManufactureWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportManufactureWiseWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");
	
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		String sqlInvoiceFlash = "  select b.strManufacturerCode,c.strManufacturerName,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + " from tblinvoicehd a,tblproductmaster b,tblmanufacturemaster c,tblinvoicedtl d,tblpartymaster e " + " where a.strInvCode=d.strInvCode  and d.strProdCode=b.strProdCode and b.strManufacturerCode=c.strManufacturerCode  and a.strCustCode=e.strPCode  and a.strLocCode='" + locCode + "' "
				+ " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += " group by  c.strManufacturerCode ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[2].toString()));
				DataList.add(Double.valueOf(objInvoice[3].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				BigDecimal valueQty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(valueQty);

			}
		}
		totalsList.add(dblTotalQty);
		totalsList.add(dblTotalValue);
		
		
		headerList.add("Manufacture Code");
		headerList.add("Manufacture Name");
		headerList.add("Currency");
		headerList.add("Qty");
		headerList.add("Sales Amt");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("ManufactureWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportDepartmentWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportDepartmentWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest req) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");
		
		String fromDate = req.getParameter("frmDte").toString();
		String toDate = req.getParameter("toDte").toString();
		String locCode = req.getParameter("locCode").toString();
		String custCode = req.getParameter("custCode").toString();
		String currencyCode=req.getParameter("currencyCode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String currencyName="";
		List listInvoice = new ArrayList();
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		List listofInvFlash = new ArrayList();
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}

		String sqlInvoiceFlash = " select b.strLocCode,b.strLocName ,sum(d.dblQty)/" + currValue + ",sum(d.dblQty*d.dblUnitPrice)/" + currValue + " from tblinvoicehd a,tbllocationmaster b,tblinvoicedtl d,tblpartymaster c " + "   where a.strInvCode=d.strInvCode  and a.strLocCode=b.strLocCode  and a.strCustCode=c.strPCode  " + " and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		if (!locCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + "and a.strLocCode='" + locCode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += " group by  a.strLocCode ";

		DecimalFormat df = new DecimalFormat("#.##");

		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[2].toString()));
				DataList.add(Double.valueOf(objInvoice[3].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);
				
				BigDecimal qty = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalQty = dblTotalQty.add(qty);

			}

		}

		totalsList.add(dblTotalQty);
		totalsList.add(dblTotalValue);
		
		headerList.add("Department Code");
		headerList.add("Department Name");
		headerList.add("Currency");
		headerList.add("Quantity");
		headerList.add("Sales Amt");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("DepartmentWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportMonthWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportMonthWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

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
		
		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}


		String sqlInvoiceFlash = "select c.strSettlementCode,ifnull(c.strSettlementDesc,''),ifnull(c.strSettlementType,''),sum(a.dblGrandTotal)/" + currValue + ",MONTHNAME(DATE(a.dteInvDate)), YEAR(DATE(a.dteInvDate))"
				+ " from tblinvoicehd a,tblsettlementmaster c,tblpartymaster d " + " where  a.strSettlementCode=c.strSettlementCode and a.strCustCode=d.strPCode " + " and a.strLocCode='" + locCode + "' and date(a.dteInvDate) between '" + fromDate + "' and '" + toDate + "' and  a.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strSettlementCode='" + settlementcode + "' ";
		}
		
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  a.strCurrencyCode='" + currencyCode + "' ";
		}
		
		sqlInvoiceFlash += "group by a.strSettlementCode,YEAR(DATE(a.dteInvDate)),MONTHNAME(DATE(a.dteInvDate))"
				        + "  order by YEAR(DATE(a.dteInvDate)) desc,MONTHNAME(DATE(a.dteInvDate)) desc";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
			for (int i = 0; i < listOfInvoice.size(); i++) {
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[1].toString());
				DataList.add(objInvoice[2].toString());
				DataList.add(objInvoice[4].toString());
				DataList.add(objInvoice[5].toString()+" ");
				DataList.add(currencyName);
				floatingPoint = Double.parseDouble(objInvoice[3].toString());
				floatingPoint = Double.parseDouble(df.format(floatingPoint).toString());
				DataList.add(floatingPoint);
				
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[3].toString()));
				dblTotalValue = dblTotalValue.add(value);

			}
		}

		totalsList.add(dblTotalValue);
		
		headerList.add("Settlement Name");
		headerList.add("Settlement Type");
		headerList.add("Month Name");
		headerList.add("Year");
		headerList.add("Currency");
		headerList.add("Sales Amt");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("MonthWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/exportRegionWiseInvoiceFlash", method = RequestMethod.GET)
	private ModelAndView funExportRegionWiseInvoiceFlash(@RequestParam("settlementcode") String settlementcode, HttpServletRequest request) {

		List retList = new ArrayList();
		List detailList = new ArrayList();
		List headerList = new ArrayList();
		// Totals at Bottom
		List totalsList = new ArrayList();
		totalsList.add("Total");
		totalsList.add("");
		totalsList.add("");

		String fromDate = request.getParameter("frmDte").toString();
		String toDate = request.getParameter("toDte").toString();
		String locCode = request.getParameter("locCode").toString();
		String custCode = request.getParameter("custCode").toString();
		String currencyCode=request.getParameter("currencyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String currencyName="";
		BigDecimal dblTotalValue = new BigDecimal(0);
		BigDecimal dblTotalQty = new BigDecimal(0);
		
		double currValue = 1.0;
		if(currencyCode.equalsIgnoreCase("All"))
		{
			currencyName=currencyList.get(baseCurrencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(baseCurrencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		else
		{
			currencyName=currencyList.get(currencyCode);
			clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currencyCode, clientCode);
			if (objCurrModel != null) {
				currValue = objCurrModel.getDblConvToBaseCurr();
			}
		}
		
		String sqlInvoiceFlash = "SELECT c.strRegionCode,c.strRegionDesc, SUM(b.dblGrandTotal)/" + currValue + ""
				+ " FROM tblpartymaster a,tblinvoicehd b,tblregionmaster c"
				+ " WHERE b.strCustCode=a.strPCode AND a.strRegion=c.strRegionCode  "
				+ " and b.strLocCode='" + locCode + "' " + " "
				+ " and date(b.dteInvDate) between '" + fromDate + "' and '" + toDate + "' "
				+ " and  b.strClientCode='" + clientCode + "'";
		if (!settlementcode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strSettlementCode='" + settlementcode + "' ";
		}
		if (!custCode.equals("All")) {
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCustCode='" + custCode + "' ";
		}
		if(!currencyCode.equalsIgnoreCase("All"))
		{
			sqlInvoiceFlash = sqlInvoiceFlash + " and  b.strCurrencyCode='" + currencyCode + "' ";
		}
		sqlInvoiceFlash += "group by a.strRegion  order by sum(b.dblGrandTotal) desc ";

		DecimalFormat df = new DecimalFormat("#.##");
		double floatingPoint = 0.0;
		List listOfInvoice = objGlobalService.funGetList(sqlInvoiceFlash, "sql");
		if (!listOfInvoice.isEmpty()) {
		  for (int i = 0; i < listOfInvoice.size(); i++) 
			{
				Object[] objInvoice = (Object[]) listOfInvoice.get(i);
				List DataList = new ArrayList<>();
				DataList.add(objInvoice[0].toString());
				DataList.add(objInvoice[1].toString());
				DataList.add(currencyName);
				DataList.add(Double.valueOf(objInvoice[2].toString()));
				
				detailList.add(DataList);
				BigDecimal value = new BigDecimal(Double.parseDouble(objInvoice[2].toString()));
				dblTotalValue = dblTotalValue.add(value);

		   }
		  
		}

		totalsList.add(dblTotalValue);
		
		headerList.add("Region Code");
		headerList.add("Region Name");
		headerList.add("Currency");
		headerList.add("Sales Amount");

		Object[] objHeader = (Object[]) headerList.toArray();

		String[] ExcelHeader = new String[objHeader.length];
		for (int k = 0; k < objHeader.length; k++) {
			ExcelHeader[k] = objHeader[k].toString();
		}
		
		//detailList.add(listofInvFlash);
		List blankList = new ArrayList();
		detailList.add(blankList);// Blank Row at Bottom
		detailList.add(totalsList);
		
		retList.add("RegionWiseInvoiceData_" + fromDate + "to" + toDate + "_" + userCode);
		retList.add(ExcelHeader);
		retList.add(detailList);
		return new ModelAndView("excelViewWithReportName", "listWithReportName", retList);
	}
	
	

}
