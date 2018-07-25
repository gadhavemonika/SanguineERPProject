package com.sanguine.webclub.controller;

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
import com.sanguine.webclub.bean.clsWebClubLockerMasterBean;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel;
import com.sanguine.webclub.model.clsWebClubCompanyMasterModel_ID;
import com.sanguine.webclub.model.clsWebClubLockerMasterModel;
import com.sanguine.webclub.model.clsWebClubLockerMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubLockerMasterService;

@Controller
public class clsWebClubLockerMasterController {

	@Autowired
	private clsWebClubLockerMasterService objWebClubLockerMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open WebClubLockerMaster
	@RequestMapping(value = "/frmLockerMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLockerMaster_1", "command", new clsWebClubLockerMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmLockerMaster", "command", new clsWebClubLockerMasterModel());
		} else {
			return null;
		}

	}

	// Load Master Data On Form
	@RequestMapping(value = "/frmLockerMaster1", method = RequestMethod.POST)
	public @ResponseBody clsWebClubLockerMasterModel funLoadMasterData(HttpServletRequest request) {
		objGlobal = new clsGlobalFunctions();
		String sql = "";
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("userCode").toString();
		clsWebClubLockerMasterBean objBean = new clsWebClubLockerMasterBean();
		String docCode = request.getParameter("docCode").toString();
		List listModel = objGlobalFunctionsService.funGetList(sql);
		clsWebClubLockerMasterModel objWebClubLockerMaster = new clsWebClubLockerMasterModel();
		return objWebClubLockerMaster;
	}

	// Save or Update WebClubLockerMaster
	@RequestMapping(value = "/saveWebClubLockerMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubLockerMasterBean objBean, BindingResult result, HttpServletRequest req) {

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
			clsWebClubLockerMasterModel objModel = funPrepareModel(objBean, userCode, propCode, clientCode);
			objWebClubLockerMasterService.funAddUpdateWebClubLockerMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Locker Code : ".concat(objModel.getStrLockerCode()));
			return new ModelAndView("redirect:/frmLockerMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmLockerMaster.html?saddr=" + urlHits);
		}

	}

	// Convert bean to model function
	private clsWebClubLockerMasterModel funPrepareModel(clsWebClubLockerMasterBean objBean, String userCode, String propCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubLockerMasterModel objModel;
		if (objBean.getStrLockerCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblLockermaster", "LockerMaster", "intGId", clientCode);
			String lockerCode = "LK" + String.format("%06d", lastNo);
			objModel = new clsWebClubLockerMasterModel(new clsWebClubLockerMasterModel_ID(lockerCode, clientCode));
			objModel.setIntGId(lastNo);
			objModel.setStrPropertyCode(propCode);
			objModel.setStrUserCreated(userCode);
			objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubLockerMasterModel objLockerModel = objWebClubLockerMasterService.funGetWebClubLockerMaster(objBean.getStrLockerCode(), clientCode);
			if (null == objLockerModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblLockermaster", "LockerMaster", "intGId", clientCode);
				String lockerCode = "LK" + String.format("%06d", lastNo);
				objModel = new clsWebClubLockerMasterModel(new clsWebClubLockerMasterModel_ID(lockerCode, clientCode));
				objModel.setIntGId(lastNo);
				objModel.setStrUserCreated(userCode);
				objModel.setStrPropertyCode(propCode);
				objModel.setDteCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {

				objModel = new clsWebClubLockerMasterModel(new clsWebClubLockerMasterModel_ID(objBean.getStrLockerCode(), clientCode));
				objModel.setStrPropertyCode(propCode);
			}
		}
		objModel.setStrUserModified(userCode);
		objModel.setDteLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrLockerName(objBean.getStrLockerName());
		objModel.setStrLockerDesc(objBean.getStrLockerDesc());

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubLockerData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubLockerMasterModel funAssignFields(@RequestParam("docCode") String lockerCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubLockerMasterModel objLockerModel = objWebClubLockerMasterService.funGetWebClubLockerMaster(lockerCode, clientCode);
		if (null == objLockerModel) {
			objLockerModel = new clsWebClubLockerMasterModel();
			objLockerModel.setStrLockerCode("Invalid Code");
		}

		return objLockerModel;
	}

}
