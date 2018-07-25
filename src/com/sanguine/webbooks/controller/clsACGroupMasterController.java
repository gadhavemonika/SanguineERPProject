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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webbooks.bean.clsACGroupMasterBean;
import com.sanguine.webbooks.model.clsACGroupMasterModel;
import com.sanguine.webbooks.model.clsACGroupMasterModel_ID;
import com.sanguine.webbooks.model.clsBankMasterModel;
import com.sanguine.webbooks.model.clsBankMasterModel_ID;
import com.sanguine.webbooks.service.clsACGroupMasterService;

@Controller
public class clsACGroupMasterController {

	@Autowired
	private clsACGroupMasterService objACGroupMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ACGroupMaster
	@RequestMapping(value = "/frmACGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();

		ArrayList<String> listCategory = new ArrayList<String>();

		/*
		 * List<String>
		 * categoryList=objACGroupMasterService.funGetGroupCategory(clientCode);
		 * for(int i=0;i<categoryList.size();i++) {
		 * listCategory.add(categoryList.get(i).toString()); }
		 */

		listCategory.add("SHARE CAPITAL");
		listCategory.add("ASSETS");				
		listCategory.add("BANK BALANCE");
		listCategory.add("CASH BALANCE");		
		listCategory.add("EXPENSE");
		listCategory.add("INCOME");
		listCategory.add("LIABILITY");
		listCategory.add("SUNDRY DEBTOR");
		listCategory.add("SUNDRY CREDITOR");
		listCategory.add("OTHER EXPENSES");
		listCategory.add("OTHER INCOME");
		
		
		

		ArrayList<String> listDefaultType = new ArrayList<String>();
		listDefaultType.add("Debit");
		listDefaultType.add("Credit");

		model.put("urlHits", urlHits);
		model.put("listCategory", listCategory);
		model.put("listDefaultType", listDefaultType);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmACGroupMaster", "command", new clsACGroupMasterModel());
		} else {
			return new ModelAndView("frmACGroupMaster_1", "command", new clsACGroupMasterModel());
		}
	}

	// Load Master Data On Form
	@RequestMapping(value = "/loadACGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsACGroupMasterModel funAssignFields(@RequestParam("acGroupCode") String acGroupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsACGroupMasterModel acGroupMasterModel = objACGroupMasterService.funGetACGroupMaster(acGroupCode, clientCode);
		if (null == acGroupMasterModel) {
			acGroupMasterModel = new clsACGroupMasterModel();
			acGroupMasterModel.setStrGroupCode("Invalid Code");
		}

		return acGroupMasterModel;
	}

	// Save or Update ACGroupMaster
	@RequestMapping(value = "/saveACGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsACGroupMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propCode = req.getSession().getAttribute("propertyCode").toString();
			clsACGroupMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propCode);
			objACGroupMasterService.funAddUpdateACGroupMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Group Code : ".concat(objModel.getStrGroupCode()));

			return new ModelAndView("redirect:/frmACGroupMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmACGroupMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsACGroupMasterModel funPrepareModel(clsACGroupMasterBean objBean, String userCode, String clientCode, String propCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsACGroupMasterModel objModel;
		if (objBean.getStrGroupCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblacgroupmaster", "ACGroupMaster", "intGId", clientCode);
			String acGroupCode = String.format("%04d", lastNo);
			objModel = new clsACGroupMasterModel(new clsACGroupMasterModel_ID(acGroupCode, clientCode));
			objModel.setIntGId(lastNo);

		} else {
			objModel = new clsACGroupMasterModel(new clsACGroupMasterModel_ID(objBean.getStrGroupCode(), clientCode));
		}
		objModel.setStrGroupName(objBean.getStrGroupName().toUpperCase());
		objModel.setStrShortName(objBean.getStrShortName());
		objModel.setStrCategory(objBean.getStrCategory());
		objModel.setStrDefaultType(objBean.getStrDefaultType());

		objModel.setStrUserCreated(userCode);
		objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);
		objModel.setStrPropertyCode(propCode);

		return objModel;

	}

}
