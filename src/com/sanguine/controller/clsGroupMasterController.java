package com.sanguine.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsAttachDocBean;
import com.sanguine.bean.clsGroupMasterBean;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsGroupMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsGroupMasterController {
	@Autowired
	private clsGroupMasterService objGrpMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	clsGlobalFunctions objGlobal;
	@Autowired
	private clsGlobalFunctions objGlobalFunctions;

	Set<String> data = new HashSet<String>();

	@RequestMapping(value = "/AutoCompletGetGroupName", method = RequestMethod.POST)
	public @ResponseBody Set<String> getGroupName(@RequestParam String term, HttpServletResponse response) {
		return simulateSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<String> simulateSearchResult(String GroupName) {
		Set<String> result = new HashSet<String>();
		// iterate a list and filter by ProductName
		for (String Group : data) {
			if (Group.contains(GroupName.toUpperCase())) {
				result.add(Group);
			}
		}
		return result;
	}

	@RequestMapping(value = "/frmGroupMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String sql = "select strGName from tblgroupmaster";
		@SuppressWarnings("rawtypes")
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			String GroupName = list.get(i).toString();
			data.add(GroupName.toUpperCase());
		}
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGroupMaster_1", "command", new clsGroupMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmGroupMaster", "command", new clsGroupMasterModel());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/docs", method = RequestMethod.GET)
	public ModelAndView funAttatchDoc(@RequestParam("groupCode") String groupCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();

		return new ModelAndView("frmAttachDocuments", "document", new clsAttachDocBean());
	}

	// Save or Update group master function to save or update record of master
	// into database and also validates all fields of form.
	@RequestMapping(value = "/saveGroupMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsGroupMasterBean gpBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {
		String strMarathiName = "";
		String urlHits = "1";
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			urlHits = req.getParameter("saddr").toString();
			/*
			 * strMarathiName=req.getParameter("marathiName").toString();
			 * System.out.println(strMarathiName);
			 */

		} catch (Exception e) {
			e.printStackTrace();
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsGroupMasterModel obGroup = funPrepareModel(gpBean, userCode, clientCode);
			objGrpMasterService.funAddGroup(obGroup);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Group Code : ".concat(obGroup.getStrGCode()));
			return new ModelAndView("redirect:/frmGroupMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmGroupMaster.html?saddr=" + urlHits);
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadGroupMasterData", method = RequestMethod.GET)
	public @ResponseBody clsGroupMasterModel funAssignFields(@RequestParam("groupCode") String groupCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsGroupMasterModel objGroup = objGrpMasterService.funGetGroup(groupCode, clientCode);
		if (null == objGroup) {
			objGroup = new clsGroupMasterModel();
			objGroup.setStrGCode("Invalid Code");
		}

		return objGroup;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsGroupMasterModel funPrepareModel(clsGroupMasterBean groupBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsGroupMasterModel group;
		if (groupBean.getStrGCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
			String groupCode = "G" + String.format("%06d", lastNo);
			group = new clsGroupMasterModel(new clsGroupMasterModel_ID(groupCode, clientCode));
			group.setIntGId(lastNo);
			group.setStrUserCreated(userCode);
			group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsGroupMasterModel objGroup = objGrpMasterService.funGetGroup(groupBean.getStrGCode(), clientCode);
			if (null == objGroup) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblgroupmaster", "GroupMaster", "intGId", clientCode);
				String groupCode = "G" + String.format("%06d", lastNo);
				group = new clsGroupMasterModel(new clsGroupMasterModel_ID(groupCode, clientCode));
				group.setIntGId(lastNo);
				group.setStrUserCreated(userCode);
				group.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				group = new clsGroupMasterModel(new clsGroupMasterModel_ID(groupBean.getStrGCode(), clientCode));
			}
		}
		group.setStrGName(groupBean.getStrGName().toUpperCase());
		group.setStrGDesc(groupBean.getStrGDesc());
		group.setStrUserModified(userCode);
		group.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return group;
	}

	@RequestMapping(value = "/rptGroupList", method = RequestMethod.GET)
	public ModelAndView funShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String groupCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		objGlobal = new clsGlobalFunctions();
		List<clsGroupMasterModel> groupMasterList = funPrepareGroupMasterModel(objGrpMasterService.funGetList(groupCode, clientCode));
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(groupMasterList);
		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		return new ModelAndView("groupList" + type.trim().toUpperCase() + "Report", model);

	}

	@SuppressWarnings("rawtypes")
	private List<clsGroupMasterModel> funPrepareGroupMasterModel(List listGroupMaster) {
		List<clsGroupMasterModel> groupMasterList = new ArrayList<clsGroupMasterModel>();

		for (int i = 0; i < listGroupMaster.size(); i++) {
			// Object[] ob = (Object[])listGroupMaster.get(i);
			clsGroupMasterModel groupMaster = (clsGroupMasterModel) listGroupMaster.get(i);
			clsGroupMasterModel objgroupMaster = new clsGroupMasterModel();
			objgroupMaster.setStrGCode(groupMaster.getStrGCode());
			objgroupMaster.setStrGName(groupMaster.getStrGName());
			objgroupMaster.setStrGDesc(groupMaster.getStrGDesc());
			groupMasterList.add(objgroupMaster);
		}
		return groupMasterList;
	}

	@RequestMapping(value = "/frmGroupList", method = RequestMethod.GET)
	public ModelAndView funGroupListfrom() throws JRException {
		return new ModelAndView("frmGroupList", "command", new clsReportBean());
	}

	@RequestMapping(value = "/checkGroupName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("groupName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmGroupMaster");
		return count;

	}

	@RequestMapping(value = "/loadAllGroupData", method = RequestMethod.GET)
	public @ResponseBody List<clsGroupMasterModel> funGetGropList(HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List<clsGroupMasterModel> list = objGrpMasterService.funListGroups(clientCode);
		return list;

	}

}
