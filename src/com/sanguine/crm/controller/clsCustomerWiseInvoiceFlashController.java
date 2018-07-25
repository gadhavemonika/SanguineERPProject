package com.sanguine.crm.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.crm.bean.clsInvoiceBean;
import com.sanguine.crm.service.clsCRMSettlementMasterService;
import com.sanguine.crm.service.clsInvoiceHdService;
import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsCurrencyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsSetupMasterService;

@Controller
public class clsCustomerWiseInvoiceFlashController 
{

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

		@RequestMapping(value = "/frmCustomerWiseInvoiceFlash", method = RequestMethod.GET)
		public ModelAndView funInvoice(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
			String urlHits = "1";
			try {
				urlHits = req.getParameter("saddr").toString();
			} catch (NullPointerException e) {
				urlHits = "1";
			}
			model.put("urlHits", urlHits);
			return funGetModelAndView(req,"Without DrillDown","","","","","");
		}

		
		private ModelAndView funGetModelAndView(HttpServletRequest req,String custCode,String fromDate,String toDate,String locCode,String settleCode,String currencyCode) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
	      
			String urlHits = "1";
			try {
				urlHits = req.getParameter("saddr").toString();
			} catch (NullPointerException e) {
				urlHits = "1";
			}
			ModelAndView objModelView = null;
			if ("2".equalsIgnoreCase(urlHits)) {
				objModelView = new ModelAndView("frmCustomerWiseInvoiceFlash_1");
			} else if ("1".equalsIgnoreCase(urlHits)) {
				objModelView = new ModelAndView("frmCustomerWiseInvoiceFlash");
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
			
			Map<String,String> currencyList=new TreeMap<String, String>();
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

			objModelView.addObject("listProperty", mapProperty);
			objModelView.addObject("LoggedInProp", propertyCode);
			objModelView.addObject("LoggedInLoc", locationCode);
			objModelView.addObject("custCode", custCode);
			objModelView.addObject("fromDate", fromDate);
			objModelView.addObject("toDate", toDate);
			objModelView.addObject("locCode", locCode);
			objModelView.addObject("settleCode", settleCode);
			objModelView.addObject("currencyCode", currencyCode);
			return objModelView;
		}
		
		
		@RequestMapping(value = "/frmCustomerWiseInvoiceFlashForInvoice", method = RequestMethod.GET)
		public ModelAndView funOpenProductWiseFlashForInvoiceForm(@ModelAttribute("command") clsInvoiceBean objBean, BindingResult result, HttpServletRequest req, Map<String, Object> model) {
			String urlHits = "1";
			String custCode=req.getParameter("code").toString();
			String fromDate=req.getParameter("fromDate").toString();
			String toDate=req.getParameter("toDate").toString();
			String locCode=req.getParameter("locCode").toString();
			String settleCode=req.getParameter("settleCode").toString();
			String currencyCode=req.getParameter("currencyCode").toString();


			
			try {
				urlHits = req.getParameter("saddr").toString();
			} catch (NullPointerException e) {
				urlHits = "1";
			}
			model.put("urlHits", urlHits);
			return funGetModelAndView(req,custCode,fromDate,toDate,locCode,settleCode,currencyCode);
		}	
		

}
