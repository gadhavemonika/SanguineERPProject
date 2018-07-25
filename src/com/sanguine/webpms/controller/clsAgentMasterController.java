package com.sanguine.webpms.controller;

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
import com.sanguine.webpms.bean.clsAgentMasterBean;
import com.sanguine.webpms.model.clsAgentMasterHdModel;
import com.sanguine.webpms.service.clsAgentMasterService;

@Controller
public class clsAgentMasterController {

	@Autowired
	private clsAgentMasterService objAgentMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open AgentMaster
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/frmAgentMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String webStockDB=request.getSession().getAttribute("WebStockDB").toString();
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		ArrayList cityArrList = new ArrayList();
		ArrayList stateArrList = new ArrayList();
		ArrayList countryArrList = new ArrayList();

		String citySql = "select strCityName from "+webStockDB+".tblcitymaster where strClientCode='" + clientCode + "' ";
		List cityList = objGlobalFunctionsService.funGetDataList(citySql, "sql");
		if (cityList.size() > 0) {
			for (int cnt = 0; cnt < cityList.size(); cnt++) {
				cityArrList.add(cityList.get(cnt));
			}
		}

		String stateSql = "select strStateName from "+webStockDB+".tblstatemaster where strClientCode='" + clientCode + "' ";
		List stateList = objGlobalFunctionsService.funGetDataList(stateSql, "sql");
		if (stateList.size() > 0) {
			for (int cnt = 0; cnt < stateList.size(); cnt++) {
				stateArrList.add(stateList.get(cnt));
			}
		}

		String countrySql = "select strCountryName from "+webStockDB+".tblcountrymaster where strClientCode='" + clientCode + "' ";
		List countryList = objGlobalFunctionsService.funGetDataList(countrySql, "sql");
		if (countryList.size() > 0) {
			for (int cnt = 0; cnt < countryList.size(); cnt++) {
				countryArrList.add(countryList.get(cnt));
			}
		}

		model.put("cityArrLsit", cityArrList);
		model.put("stateArrLsit", stateArrList);
		model.put("countryArrLsit", countryArrList);

		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAgentMaster_1", "command", new clsAgentMasterBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAgentMaster", "command", new clsAgentMasterBean());
		} else {
			return null;
		}
	}

	// Save or Update AgentMaster
	@RequestMapping(value = "/saveAgentMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAgentMasterBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsAgentMasterHdModel objModel = objAgentMasterService.funPrepareModel(objBean, userCode, clientCode);
			objAgentMasterService.funAddUpdateAgentMaster(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Agent Code : ".concat(objModel.getStrAgentCode()));

			return new ModelAndView("redirect:/frmAgentMaster.html");
		} else {
			return new ModelAndView("frmAgentMaster");
		}
	}

	// Load Agent Master Data On Form
	@RequestMapping(value = "/loadAgentCode", method = RequestMethod.GET)
	public @ResponseBody clsAgentMasterHdModel funLoadAgentMaster(@RequestParam("agentCode") String agentCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAgentMasterHdModel objModel = objAgentMasterService.funGetAgentMaster(agentCode, clientCode);
		if (objModel == null) {
			objModel = new clsAgentMasterHdModel();
			objModel.setStrAgentCode("Invalid Code");
		}

		return objModel;
	}

}
