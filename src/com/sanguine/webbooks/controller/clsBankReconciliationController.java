package com.sanguine.webbooks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsBankReconciliationBean;
import com.sanguine.webbooks.bean.clsBankReconciliationDetailBean;
import com.sanguine.webbooks.bean.clsPaymentBean;

@Controller
public class clsBankReconciliationController {

	
	@Autowired
	clsCurrencyMasterService objCurrencyMasterService;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	
	@Autowired
	private clsGlobalFunctions objGlobal;
	
	// Open Debtor Ledger
		@RequestMapping(value = "/frmBankReconciliation", method = RequestMethod.GET)
		public ModelAndView funOpenForm(@ModelAttribute("command") clsBankReconciliationBean objBean, BindingResult result, Map<String, Object> model, HttpServletRequest req) 
		{
			ModelAndView objModelAndView = new ModelAndView();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			
			String sql="select strAccountCode from tblacmaster where strClientCode='"+clientCode+"' and strType='Bank'  and strPropertyCode = '"+propertyCode+"' ";
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
		
		
		@RequestMapping(value = "/loadPaymentReceiptBankData", method = RequestMethod.GET)
		public @ResponseBody List<clsPaymentBean> funLoadPaymentReceiptBankData( HttpServletRequest req) 
		{
			ModelAndView objModelAndView = new ModelAndView();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			String accCode=req.getParameter("accountCode").toString();
			String fDate=req.getParameter("frmDate").toString();
			String tDate=req.getParameter("toDate").toString();
			String fromDate=objGlobal.funGetDate("yyyy/MM/dd", fDate);
			String toDate=objGlobal.funGetDate("yyyy/MM/dd",tDate);
			String currency=req.getParameter("currency").toString();
			
			double currConversion = 1.0;
			
				clsCurrencyMasterModel objCurrModel = objCurrencyMasterService.funGetCurrencyMaster(currency, clientCode);
				if (objCurrModel != null) {
					currConversion = objCurrModel.getDblConvToBaseCurr();
				}

				List<clsPaymentBean>listBankRec=new ArrayList<clsPaymentBean>();
			String sql="SELECT re.TransType,re.strVouchNo,re.strChequeNo, re.dteChequeDate, re.bal, re.dteClearence,re.strDrawnOn,re.dteVouchDate from "
					  +" (SELECT 'Payment' as TransType,a.strVouchNo as strVouchNo ,a.strChequeNo as strChequeNo , DATE_FORMAT(a.dteChequeDate,'%d-%m-%Y') as dteChequeDate, (SUM(b.dblDrAmt)- SUM(b.dblCrAmt)) as bal, DATE_FORMAT(a.dteClearence,'%m/%d/%Y') as dteClearence,a.strDrawnOn as strDrawnOn, DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') as dteVouchDate "
					  + " from tblpaymenthd a,tblpaymentdtl b " 
					  +" where a.strVouchNo=b.strVouchNo  and b.strAccountCode='"+accCode+"' and a.strType='Cheque' and " 
					  +" a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' "
					  +" and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"' group by a.strVouchNo "
					
					  +" union All " 
					  +"SELECT 'Receipt' as TransType,a.strVouchNo as strVouchNo ,a.strChequeNo asstrChequeNo , DATE_FORMAT(a.dteChequeDate,'%d-%m-%Y') as dteChequeDate, (SUM(b.dblDrAmt)- SUM(b.dblCrAmt)) as bal, DATE_FORMAT(a.dteClearence,'%m/%d/%Y') as dteClearence,a.strDrawnOn as strDrawnOn, DATE_FORMAT(a.dteVouchDate,'%d-%m-%Y') as dteVouchDate from tblreceipthd a,tblreceiptdtl b where a.strVouchNo=b.strVouchNo and " 
					  +" a.strPropertyCode='"+propertyCode+"' and a.strClientCode='"+clientCode+"' and b.strAccountCode='"+accCode+"' "
					  +" and a.strType='Cheque' and date(a.dteVouchDate) between '"+fromDate+"' and '"+toDate+"'  group by a.strVouchNo  "
					  + " ) as re ORDER BY re.dteClearence,re.strChequeNo ";
			
			List list = objGlobalFunctionsService.funGetListModuleWise(sql, "sql");
			if (list.size() > 0) {
				  
				for(int i=0;i<list.size();i++)
				{
					clsPaymentBean objRecPayBean=new clsPaymentBean();
					Object obj[]=(Object[])list.get(i);
					objRecPayBean.setStrTransMode(obj[0].toString());
					objRecPayBean.setStrVouchNo(obj[1].toString());
					objRecPayBean.setStrChequeNo(obj[2].toString());
					objRecPayBean.setDteChequeDate(obj[3].toString());
					objRecPayBean.setDblAmt(Double.parseDouble(obj[4].toString()));
					objRecPayBean.setDteClearence(obj[5].toString());
					objRecPayBean.setStrDrawnOn(obj[6].toString());
					objRecPayBean.setDteVouchDate(obj[7].toString());
					listBankRec.add(objRecPayBean);
				}
				
				
			}		
			
			
			
			return listBankRec;
		}
		
		@RequestMapping(value = "/rptBankReconciliation", method = RequestMethod.GET)
		public ModelAndView funRptBankReconciliation(@ModelAttribute("command") @Valid clsBankReconciliationBean objBean, HttpServletRequest req) 
		{
			
			for(clsBankReconciliationDetailBean obj:objBean.getListBankReconciliationDtl())
			{
				if(obj.getDteClearing()!=""){
					
					String toDate[]=obj.getDteClearing().split("/");//06/13/2018
					String chequedt= toDate[2]+"-"+toDate[0]+"-"+toDate[1];
					String sql="update tblpaymenthd set dteClearence='"+chequedt+"' where strVouchNo='"+obj.getStrVouchNo()+"' ";
					objGlobalFunctionsService.funUpdateAllModule(sql, "sql");
	                String sql1="update tblreceipthd set dteClearence='"+chequedt+"' where strVouchNo='"+obj.getStrVouchNo()+"' ";
	                objGlobalFunctionsService.funUpdateAllModule(sql1, "sql");
				}
				
				
			}
		
			return new ModelAndView("redirect:/frmBankReconciliation.html?saddr=" + 1);
		}
		
}
