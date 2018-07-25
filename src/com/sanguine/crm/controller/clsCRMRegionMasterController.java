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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsCategoryMasterBean;
import com.sanguine.crm.model.clsRegionMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCRMRegionMasterController {

	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private intfBaseService objBaseService;
	
	
	@RequestMapping(value = "/frmCRMRegionMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMRegionMaster_1", "command", new clsCategoryMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMRegionMaster", "command", new clsCategoryMasterBean());
		} else {
			return null;
		}
	}
	
	
	// Save or Update REgion master function to save or update record of category
	
		@RequestMapping(value = "/saveCRMRegionMaster", method = RequestMethod.POST)
		public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCategoryMasterBean objBean, BindingResult result, HttpServletRequest req) throws Exception {
			String urlHits = "1";
			try {
				urlHits = req.getParameter("saddr").toString();
			} catch (NullPointerException e) {
				urlHits = "1";
			}
			String userCode = req.getSession().getAttribute("usercode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			if (!result.hasErrors()) 
			{
				clsRegionMasterModel objModel =null;
				objModel=funAssignFields(objBean.getStrRegionCode(),req);
				if(objModel!=null && objModel.getStrRegionCode()!=null)
				{
					
				}
				else
				{
					objModel = new clsRegionMasterModel();
					long lastNo = objGlobalFunctionsService.funGetLastNo("tblregionmaster", "RegionMaster", "intId", clientCode);
					String strRegionCode = "R" + String.format("%05d", lastNo);
					objModel.setStrRegionCode(strRegionCode);
					objModel.setIntId(lastNo);
					objModel.setStrUserCreated(userCode);
					objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
					objModel.setStrClientCode(clientCode);
				}
				
				objModel.setStrRegionDesc(objBean.getStrRegionDesc());
				objModel.setStrUserModified(userCode);
				objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objBaseService.funSave(objModel);
				
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Region Code : ".concat(objModel.getStrRegionCode()));
				return new ModelAndView("redirect:/frmCRMRegionMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmCRMRegionMaster?saddr=" + urlHits, "command", new clsCategoryMasterBean());
			}

		}
		
		// Assign filed function to set data onto form for edit transaction.
		@RequestMapping(value = "/loadCRMRegionMasterData", method = RequestMethod.GET)
		public @ResponseBody clsRegionMasterModel funAssignFields(@RequestParam("code") String code, HttpServletRequest req) throws Exception {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsRegionMasterModel objModel = new clsRegionMasterModel();
			StringBuilder sbSql=new StringBuilder();
			sbSql.append("select a.strRegionCode,a.dtCreatedDate,a.intId,a.strUserCreated,a.strRegionDesc from tblregionmaster a where a.strRegionCode='"+code+"' and a.strClientCode='"+clientCode+"' ");
			List listGetRecord = objBaseService.funGetList(sbSql, "sql");
			if(listGetRecord.size()>0)
			{
				for (int j = 0; j < listGetRecord.size(); j++) 
				{
					Object[] obArr = (Object[]) listGetRecord.get(j);
					objModel.setStrRegionCode(obArr[0].toString());
					objModel.setDtCreatedDate(obArr[1].toString());
					objModel.setIntId(Integer.valueOf(obArr[2].toString()));
					objModel.setStrUserCreated(obArr[3].toString());
					objModel.setStrRegionDesc(obArr[4].toString());
					objModel.setStrClientCode(clientCode);
				}	
			}

			return objModel;
		}

		
}
