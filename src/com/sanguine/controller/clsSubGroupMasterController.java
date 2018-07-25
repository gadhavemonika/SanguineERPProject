package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.sanguine.bean.clsSubGroupMasterBean;
import com.sanguine.model.clsSubGroupMasterModel;
import com.sanguine.model.clsSubGroupMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSubGroupMasterService;

@Controller
public class clsSubGroupMasterController {
	@Autowired
	private clsSubGroupMasterService objSubGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	Set<String> data = new HashSet<String>();

	@RequestMapping(value = "/AutoCompletGetSubGroupName", method = RequestMethod.POST)
	public @ResponseBody Set<String> getSGNames(@RequestParam String term, HttpServletResponse response) {
		return simulateSearchResult(term);

	}

	/*
	 * @param SGName
	 * 
	 * @return
	 */
	private Set<String> simulateSearchResult(String SGName) {
		Set<String> result = new HashSet<String>();
		// iterate a list and filter by SubGroupName
		for (String SGNames : data) {
			if (SGNames.contains(SGName.toUpperCase())) {
				result.add(SGNames);
			}
		}
		return result;
	}

	@RequestMapping(value = "/frmSubGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String sql = "select strSGName from tblsubgroupmaster";
		@SuppressWarnings("rawtypes")
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			String LcoationName = list.get(i).toString();
			data.add(LcoationName.toUpperCase());
		}
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubGroupMaster_1", "command", new clsSubGroupMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSubGroupMaster", "command", new clsSubGroupMasterModel());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveSubGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSubGroupMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsSubGroupMasterModel objModel = funPrepareSubGroupModel(objBean, userCode, clientCode);
			objSubGrpMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Subgroup Code : ".concat(objModel.getStrSGCode()));
		}
		// return new ModelAndView("frmSubGroupMaster","command", new
		// clsSubGroupMasterModel());
		return new ModelAndView("redirect:/frmSubGroupMaster.html?saddr=" + urlHits);
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadSubGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSubGroupMasterModel funAssignFields(@RequestParam("subGroupCode") String subgroupCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsSubGroupMasterModel objSubGroup = objSubGrpMasterService.funGetObject(subgroupCode, clientCode);
		if (null == objSubGroup) {
			objSubGroup = new clsSubGroupMasterModel();
			objSubGroup.setStrSGCode("Invalid Code");
		}

		return objSubGroup;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsSubGroupMasterModel funPrepareSubGroupModel(clsSubGroupMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsSubGroupMasterModel subgroup;

		if (objBean.getStrSGCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsubgroupmaster", "SubGroupMaster", "intSGId", clientCode);
			String subGroupCode = "SG" + String.format("%06d", lastNo);
			subgroup = new clsSubGroupMasterModel(new clsSubGroupMasterModel_ID(subGroupCode, clientCode));
			subgroup.setIntSGId(lastNo);
			subgroup.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			subgroup.setStrUserCreated(userCode);
		} else {
			clsSubGroupMasterModel objSubGroup = objSubGrpMasterService.funGetObject(objBean.getStrSGCode(), clientCode);
			if (null == objSubGroup) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblsubgroupmaster", "SubGroupMaster", "intSGId", clientCode);
				String subGroupCode = "SG" + String.format("%06d", lastNo);
				subgroup = new clsSubGroupMasterModel(new clsSubGroupMasterModel_ID(subGroupCode, clientCode));
				subgroup.setIntSGId(lastNo);
				subgroup.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				subgroup.setStrUserCreated(userCode);
			} else {
				subgroup = new clsSubGroupMasterModel(new clsSubGroupMasterModel_ID(objBean.getStrSGCode(), clientCode));
			}
		}
		subgroup.setStrSGName(objBean.getStrSGName().toUpperCase());
		subgroup.setStrExciseChapter(objBean.getStrExciseChapter());
		subgroup.setStrSGDesc(objBean.getStrSGDesc());
		subgroup.setStrGCode(objBean.getStrGCode());
		subgroup.setStrExciseable(objBean.getStrExciseable());
		subgroup.setStrUserModified(userCode);
		subgroup.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		subgroup.setIntSortingNo(objBean.getIntSortingNo());
		subgroup.setStrSGDescHeader(objBean.getStrSGDescHeader());

		return subgroup;
	}

	@RequestMapping(value = "/checksubGroupName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("subgroupName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmSubGroupMaster");
		return count;

	}

	@RequestMapping(value = "/AllloadSubGroup", method = RequestMethod.GET)
	public @ResponseBody List<clsSubGroupMasterModel> funloadSubGroup(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsSubGroupMasterModel> listSubGropModel = objSubGrpMasterService.funGetList();
		return listSubGropModel;

	}

}
