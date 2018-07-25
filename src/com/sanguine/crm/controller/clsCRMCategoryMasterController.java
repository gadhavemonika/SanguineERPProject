package com.sanguine.crm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.base.model.clsBaseModel;
import com.sanguine.base.service.intfBaseService;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.crm.bean.clsCategoryMasterBean;
import com.sanguine.crm.model.clsCategoryMasterModel;
import com.sanguine.crm.model.clsCategoryMasterModel_ID;
import com.sanguine.model.clsSettlementMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsChequeReportBean;

@Transactional
@Controller
public class clsCRMCategoryMasterController 
{
	@Autowired
	private clsGlobalFunctions objGlobal;
	
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private intfBaseService objBaseService;
	

	@RequestMapping(value = "/frmCRMCategoryMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMCategoryMaster_1", "command", new clsCategoryMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMCategoryMaster", "command", new clsCategoryMasterBean());
		} else {
			return null;
		}
	}
	
	
	// Save or Update category master function to save or update record of category
	
	@RequestMapping(value = "/saveCRMCategoryMaster", method = RequestMethod.POST)
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
			clsCategoryMasterModel objModel =null;
			objModel=funAssignFields(objBean.getStrCategoryCode(),req);
			if(objModel!=null && objModel.getStrCategoryCode()!=null)
			{
				
			}
			else
			{
				objModel = new clsCategoryMasterModel();
				long lastNo = objGlobalFunctionsService.funGetLastNo("tblcategorymaster", "CategoryMaster", "intId", clientCode);
				String strCategoryCode = "CT" + String.format("%05d", lastNo);
				objModel.setStrCategoryCode(strCategoryCode);
				objModel.setIntId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrClientCode(clientCode);
			}
			
			objModel.setStrCategoryDesc(objBean.getStrCategoryDesc());
			objModel.setStrUserModified(userCode);
			objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objBaseService.funSave(objModel);
			
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Category Code : ".concat(objModel.getStrCategoryCode()));
			return new ModelAndView("redirect:/frmCRMCategoryMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmCRMCategoryMaster?saddr=" + urlHits, "command", new clsCategoryMasterBean());
		}

	}
	
	
	// Assign filed function to set data onto form for edit transaction.
		@RequestMapping(value = "/loadCategoryMasterData", method = RequestMethod.GET)
		public @ResponseBody clsCategoryMasterModel funAssignFields(@RequestParam("code") String code, HttpServletRequest req) throws Exception {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsCategoryMasterModel objModel = new clsCategoryMasterModel();
			StringBuilder sbSql=new StringBuilder();
			sbSql.append("select a.strCategoryCode,a.dtCreatedDate,a.intId,a.strUserCreated,a.strCategoryDesc from tblcategorymaster a where a.strCategoryCode='"+code+"' and a.strClientCode='"+clientCode+"' ");
			List listGetRecord = objBaseService.funGetList(sbSql, "sql");
			if(listGetRecord.size()>0)
			{
				for (int j = 0; j < listGetRecord.size(); j++) 
				{
					Object[] obArr = (Object[]) listGetRecord.get(j);
					objModel.setStrCategoryCode(obArr[0].toString());
					objModel.setDtCreatedDate(obArr[1].toString());
					objModel.setIntId(Integer.valueOf(obArr[2].toString()));
					objModel.setStrUserCreated(obArr[3].toString());
					objModel.setStrCategoryDesc(obArr[4].toString());
					objModel.setStrClientCode(clientCode);
				}	
			}

			return objModel;
		}
}
