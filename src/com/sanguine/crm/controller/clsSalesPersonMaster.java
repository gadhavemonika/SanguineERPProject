package com.sanguine.crm.controller;

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

import com.sanguine.crm.bean.clsSalesPersonBean;
import com.sanguine.crm.model.clsSalesPersonMasterModel;
import com.sanguine.crm.model.clsSalesPersonMasterModel_ID;
import com.sanguine.crm.service.clsSalesPersonMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsSalesPersonMaster {
	
	@Autowired
	clsSalesPersonMasterService objSalesPersonMasterService;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmSalesPersonMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesPersonMaster_1", "command", new clsSalesPersonBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSalesPersonMaster", "command", new clsSalesPersonBean());
		} else {
			return null;
		}
	}
	
	//Load Master Data On Form
		@RequestMapping(value = "/loadSalesPerson", method = RequestMethod.POST)
		public @ResponseBody clsSalesPersonMasterModel funLoadMasterData(HttpServletRequest request){
			String sql="";
			String clientCode=request.getSession().getAttribute("clientCode").toString();
			String userCode=request.getSession().getAttribute("usercode").toString();
			clsSalesPersonBean objBean=new clsSalesPersonBean();
			String docCode=request.getParameter("docCode").toString();
			
			clsSalesPersonMasterModel objclsSalesPersonMaster = new clsSalesPersonMasterModel();
			
			List listModel= objSalesPersonMasterService.funGetclsSalesPersonMaster(docCode, clientCode);
			if(listModel.size()>0){
					clsSalesPersonMasterModel obModel=(clsSalesPersonMasterModel) listModel.get(0);
				objclsSalesPersonMaster.setStrClientCode(clientCode);
				objclsSalesPersonMaster.setStrSalesPersonCode(obModel.getStrSalesPersonCode());
				objclsSalesPersonMaster.setStrSalesPersonName(obModel.getStrSalesPersonName());
				
			}else{
				objclsSalesPersonMaster = new clsSalesPersonMasterModel();
				objclsSalesPersonMaster.setStrSalesPersonCode("Invalid Code");
			}
			return objclsSalesPersonMaster;
		}

	//Save or Update clsSalesPersonMaster
		@RequestMapping(value = "/saveSalesPerson", method = RequestMethod.POST)
		public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSalesPersonBean objBean ,BindingResult result,HttpServletRequest req){
			if(!result.hasErrors()){
				String clientCode=req.getSession().getAttribute("clientCode").toString();
				String userCode=req.getSession().getAttribute("usercode").toString();
				clsSalesPersonMasterModel objModel = new clsSalesPersonMasterModel();
				Long lastNo=(long) 0;
				if(null!=objBean){
					
					if (objBean.getStrSalesPersonCode().trim().length() == 0) {
						lastNo = objGlobalFunctionsService.funGetLastNo("tblsalesperson", "SalesPerson", "intID", clientCode);
						String groupCode = "SP" + String.format("%05d", lastNo);
						objModel = new clsSalesPersonMasterModel(new clsSalesPersonMasterModel_ID(groupCode, clientCode));
						objModel.setIntID(lastNo);
					} else {
						List listModel= objSalesPersonMasterService.funGetclsSalesPersonMaster(objBean.getStrSalesPersonCode(), clientCode);
						if(listModel.size()>0){
							clsSalesPersonMasterModel obModel=(clsSalesPersonMasterModel) listModel.get(0);
							
							objModel.setIntID(obModel.getIntID());
							objModel.setStrClientCode(clientCode);
							objModel.setStrSalesPersonCode(obModel.getStrSalesPersonCode());
							objModel.setStrSalesPersonName(obModel.getStrSalesPersonName());
							
						}else{
							if (listModel.size()==0) {
								lastNo = objGlobalFunctionsService.funGetLastNo("tblsalesperson", "SalesPerson", "intID", clientCode);
								String groupCode = "SP" + String.format("%05d", lastNo);
								objModel = new clsSalesPersonMasterModel(new clsSalesPersonMasterModel_ID(groupCode, clientCode));
								
							} else {
								objModel = new clsSalesPersonMasterModel(new clsSalesPersonMasterModel_ID(objBean.getStrSalesPersonCode(), clientCode));
							}
						}
						//objModel = objSalesPersonMasterService.funGetclsSalesPersonMaster(objBean.getStrSalesPersonCode(), clientCode);
						
					}
					objModel.setStrClientCode(clientCode);
					objModel.setStrSalesPersonName(objBean.getStrSalesPersonName());
				}
				objSalesPersonMasterService.funAddUpdateclsSalesPersonMaster(objModel);
				return new ModelAndView("redirect:/frmSalesPersonMaster.html");
			}
			else{
				return new ModelAndView("frmSalesPersonMaster");
			}
		}

	
}
