package com.sanguine.controller;

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

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsSessionMasterModel;
import com.sanguine.model.clsSessionMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSessionMasterService;

@Controller
public class clsSessionMasterController {

	Set<String> data = new HashSet<String>();
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSessionMasterService objSessionMasterService;

	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/AutoCompletGetSessionName", method = RequestMethod.POST)
	public @ResponseBody Set<String> getGroupName(@RequestParam String term, HttpServletResponse response) {
		return simulateSearchResult(term);

	}

	/*
	 * @param empName
	 * 
	 * @return
	 */
	private Set<String> simulateSearchResult(String SessionName) {
		Set<String> result = new HashSet<String>();
		// iterate a list and filter by ProductName
		for (String Group : data) {
			if (Group.contains(SessionName.toUpperCase())) {
				result.add(Group);
			}
		}
		return result;
	}

	@RequestMapping(value = "/frmSessionMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String sql = "select strSessionName from tblsessionmaster";
		@SuppressWarnings("rawtypes")
		List list = objGlobalFunctionsService.funGetList(sql, "sql");
		for (int i = 0; i < list.size(); i++) {
			String strSessionName = list.get(i).toString();
			data.add(strSessionName.toUpperCase());
		}
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSessionMaster_1", "command", new clsSessionMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmSessionMaster", "command", new clsSessionMasterModel());
		} else {
			return null;
		}

	}

	// Save Logic

	@RequestMapping(value = "/saveSessionMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSessionMasterModel gpBean, BindingResult result, HttpServletRequest req, HttpServletResponse resp) {
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
			clsSessionMasterModel obModel = funPrepareModel(gpBean, userCode, clientCode);
			objSessionMasterService.funAddSession(obModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Session Code : ".concat(obModel.getStrSessionCode()));
			return new ModelAndView("redirect:/frmSessionMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmSessionMaster.html?saddr=" + urlHits);
		}
	}

	private clsSessionMasterModel funPrepareModel(clsSessionMasterModel bean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSessionMasterModel session;
		if (bean.getStrSessionCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblsessionmaster", "SessionMaster", "intSId", clientCode);
			String groupCode = "SE" + String.format("%06d", lastNo);
			session = new clsSessionMasterModel(new clsSessionMasterModel_ID(groupCode, clientCode));
			session.setIntSId(lastNo);
			session.setStrUserCreated(userCode);
			session.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			clsSessionMasterModel objGroup = objSessionMasterService.funGetSession(bean.getStrSessionCode(), clientCode);
			if (null == objGroup) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblsessionmaster", "SessionMaster", "intSId", clientCode);
				String sessionCode = "SE" + String.format("%06d", lastNo);
				session = new clsSessionMasterModel(new clsSessionMasterModel_ID(sessionCode, clientCode));
				session.setIntSId(lastNo);
				session.setStrUserCreated(userCode);
				session.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			} else {
				session = new clsSessionMasterModel(new clsSessionMasterModel_ID(bean.getStrSessionCode(), clientCode));
			}
		}
		session.setStrSessionName(bean.getStrSessionName().toUpperCase());
		session.setStrSDesc(bean.getStrSDesc());
		session.setStrUserModified(userCode);
		session.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));

		return session;
	}

	@RequestMapping(value = "/loadSessionMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSessionMasterModel funAssignFields(@RequestParam("sessionCode") String sessionCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsSessionMasterModel objsession = objSessionMasterService.funGetSession(sessionCode, clientCode);
		if (null == objsession) {
			objsession = new clsSessionMasterModel();
			objsession.setStrSessionCode("Invalid Code");
		}

		return objsession;
	}
}
