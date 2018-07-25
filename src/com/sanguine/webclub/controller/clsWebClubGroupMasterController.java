package com.sanguine.webclub.controller;

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
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webclub.bean.clsWebClubGroupMasterBean;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel;
import com.sanguine.webclub.model.clsWebClubGroupMasterModel_ID;
import com.sanguine.webclub.service.clsWebClubGroupMasterService;

@Controller
public class clsWebClubGroupMasterController {

	clsGlobalFunctions objGlobal;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsWebClubGroupMasterService objGrpMasterService;

	// Open MemberProfile
	@RequestMapping(value = "/frmWebClubGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebClubGroupMaster_1", "command", new clsWebClubGroupMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebClubGroupMaster", "command", new clsWebClubGroupMasterBean());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of master
	// into database and also validates all fields of form.
	@RequestMapping(value = "/savefrmWebClubGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsWebClubGroupMasterBean gpBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			clsWebClubGroupMasterModel obGroup = funPrepareModel(gpBean, req);

			objGrpMasterService.funAddGroup(obGroup);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Group Code : ".concat(obGroup.getStrGroupCode()));
			return new ModelAndView("redirect:/frmWebClubGroupMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmWebClubGroupMaster.html?saddr=" + urlHits);
		}
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsWebClubGroupMasterModel funPrepareModel(clsWebClubGroupMasterBean groupBean, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String propCode = req.getSession().getAttribute("propertyCode").toString();
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsWebClubGroupMasterModel group;
		if (groupBean.getStrGroupCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
			String groupCode = "G" + String.format("%06d", lastNo);
			group = new clsWebClubGroupMasterModel(new clsWebClubGroupMasterModel_ID(groupCode, clientCode));
			group.setIntGId(lastNo);
			group.setStrUserCreated(userCode);
			group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsWebClubGroupMasterModel objGroup = objGrpMasterService.funGetGroup(groupBean.getStrGroupCode(), clientCode);
			if (null == objGroup) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
				String groupCode = "G" + String.format("%06d", lastNo);
				group = new clsWebClubGroupMasterModel(new clsWebClubGroupMasterModel_ID(groupCode, clientCode));
				group.setIntGId(lastNo);
				group.setStrUserCreated(userCode);
				group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				group = new clsWebClubGroupMasterModel(new clsWebClubGroupMasterModel_ID(groupBean.getStrGroupCode(), clientCode));
			}
		}
		group.setStrUserCreated(userCode);
		group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		group.setStrGroupName(groupBean.getStrGroupName().toUpperCase());
		group.setStrUserModified(userCode);
		group.setStrCategory(groupBean.getStrCategory());
		group.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		group.setStrPropertyCode(propCode);
		group.setStrCrDr(groupBean.getStrCrDr());
		group.setStrShortName(groupBean.getStrShortName());
		return group;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadWebClubGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsWebClubGroupMasterModel funAssignFields(@RequestParam("groupCode") String groupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsWebClubGroupMasterModel objGroup = objGrpMasterService.funGetGroup(groupCode, clientCode);
		if (null == objGroup) {
			objGroup = new clsWebClubGroupMasterModel();
			objGroup.setStrGroupCode("Invalid Code");
		}

		return objGroup;
	}

}
