package com.sanguine.webbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsDebtorLedgerBean;
import com.sanguine.webbooks.model.clsDebtorMaster;
import com.sanguine.webbooks.model.clsJVDebtorDtlModel;
import com.sanguine.webbooks.model.clsLedgerSummaryModel;
import com.sanguine.webbooks.service.clsDebtorLedgerService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class clsDebtorLedgerController {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	@Autowired
	clsDebtorLedgerService objDebtorLedgerService;

	@Autowired
	private clsGlobalFunctions objclsGlobalFunctions;

	@Autowired
	private clsReceiptController objclsReceiptController;

	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	intfBaseService objBaseService;
	
	// Open Debtor Ledger
	@RequestMapping(value = "/frmDebtorLedger", method = RequestMethod.GET)
	public ModelAndView funOpenForm(@ModelAttribute("command") clsDebtorLedgerBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) {
		objDebtorLedgerService.funGetDebtorDetails("M000002", "060.001", "02");
		ModelAndView objModelAndView = new ModelAndView();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		
		String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strDebtor='Yes'  and strPropertyCode = '"+propertyCode+"' ";
		List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
		if (list.size() > 0) {
			objBean.setStrGLCode(list.get(0).toString());
		}		
		
		Map<String, String> hmCurrency = objCurrencyMasterService.funCurrencyListToDisplay(clientCode);
		if (hmCurrency.isEmpty()) {
			hmCurrency.put("", "");
		}
		model.put("currencyList", hmCurrency);

		return objModelAndView;
	}

	// Process and Show Debtor Ledger
	@RequestMapping(value = "/showDebtorLedger", method = RequestMethod.POST)
	public ModelAndView funShowDebtorLedger(@ModelAttribute("command") @Valid clsDebtorLedgerBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			return new ModelAndView("redirect:/frmDebtorLedger.html");
		} else {
			return new ModelAndView("frmDebtorLedger");
		}
	}

	/*
	 * // Get Debtor Details
	 * 
	 * @RequestMapping(value = "/getDebtorDetails", method = RequestMethod.GET)
	 * public @ResponseBody clsDebtorMaster
	 * funLoadMemberDetails(@RequestParam("debtorCode") String
	 * debtorCode,HttpServletRequest req) { String
	 * clientCode=req.getSession().getAttribute("clientCode").toString(); String
	 * propertyCode=req.getSession().getAttribute("propertyCode").toString();
	 * 
	 * String sql="select * from vMemberData " +
	 * "where strMemberCode='"+debtorCode
	 * +"' and strClientCode='"+clientCode+"' " +
	 * "and strPropertyCode='"+propertyCode+"' "; List
	 * list=objGlobalFunctionsService.funGetListModuleWise(sql,"sql");
	 * 
	 * clsDebtorMaster objDebtorMaster=new clsDebtorMaster(); Object[]
	 * arrObj=(Object[]) list.get(0);
	 * objDebtorMaster.setStrDebtorCode(arrObj[0].toString());
	 * objDebtorMaster.setStrDebtorName(arrObj[1].toString());
	 * if(arrObj[2].toString().equals("Y")) {
	 * objDebtorMaster.setStrStatus("Member is Blocked"); } else {
	 * objDebtorMaster.setStrStatus("Member is Active"); }
	 * objDebtorMaster.setStrStopCredit(arrObj[3].toString()); return
	 * objDebtorMaster; }
	 */

	// Get Creditor Details
	@RequestMapping(value = "/getDebtorLedger", method = RequestMethod.GET)
	public @ResponseBody List funLoadMemberDetails(@RequestParam(value = "param1") String param1, @RequestParam(value = "fDate") String fDate, @RequestParam(value = "tDate") String tDate,@RequestParam(value = "currency") String currency, HttpServletRequest req, HttpServletResponse resp) {
		List retList = new ArrayList();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String startDate = req.getSession().getAttribute("startDate").toString();
		// objGlobal=new clsGlobalFunctions();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String[] spParam1 = param1.split(",");
		String glCode = spParam1[0];
		String creditorCode = spParam1[1];
		String prodCode = spParam1[2];
		String fromDate = objGlobal.funGetDate("yyyy-MM-dd", fDate);
		String toDate = objGlobal.funGetDate("yyyy-MM-dd", tDate);

//		double conversionRate=1;
		
		
//		StringBuilder sbSql = new StringBuilder();
//		sbSql.append("select dblConvToBaseCurr from "+webStockDB+".tblcurrencymaster where strCurrencyCode='"+currency+"' and strClientCode='"+clientCode+"' ");
//		try
//		{
//			List list = objBaseService.funGetList(sbSql,"sql");
//			conversionRate=Double.parseDouble(list.get(0).toString());
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		
		startDate = startDate.split("/")[2] + "-" + startDate.split("/")[1] + "-" + startDate.split("/")[0];

		objclsGlobalFunctions.funInvokeWebBookLedger(glCode, creditorCode, startDate, propertyCode, fromDate, toDate, clientCode, userCode, req, resp, "Debtor",currency);

		String hql = "  from clsLedgerSummaryModel a where a.strUserCode='" + userCode + "' and a.strPropertyCode='" + propertyCode + "' and a.strClientCode='" + clientCode + "' order by a.dteVochDate, a.strTransTypeForOrderBy ";

		List listBillLedger = objGlobalFunctionsService.funGetListModuleWise(hql, "hql");

		
		return listBillLedger;
	}
	
	@RequestMapping(value = "/loadCurrencyForDebtor", method = RequestMethod.GET)
	public @ResponseBody String funLoadCurrencyForDebtor(@RequestParam(value = "debtorCode") String debtor, HttpServletRequest req, HttpServletResponse resp) 
	{
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String propertyCode = req.getSession().getAttribute("propertyCode").toString();
		String currency="";
		List list = objclsGlobalFunctions.funGetDebotCreditorCurrency(debtor, clientCode, propertyCode);
		if(list.size()>0)
		{
			currency = list.get(0).toString();
		}
		return currency;
	}
	

}
