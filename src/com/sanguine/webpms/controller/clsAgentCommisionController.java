package com.sanguine.webpms.controller;

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
import com.sanguine.webpms.bean.clsAgentCommisionBean;
import com.sanguine.webpms.model.clsAgentCommisionHdModel;
import com.sanguine.webpms.service.clsAgentCommisionService;

@Controller
public class clsAgentCommisionController {

	@Autowired
	private clsAgentCommisionService objAgentCommisionService;

	// Open AgentCommision
	@RequestMapping(value = "/frmAgentCommision", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAgentCommision_1", "command", new clsAgentCommisionBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAgentCommision", "command", new clsAgentCommisionBean());
		} else {
			return null;
		}
	}

	// Save or Update AgentCommision
	@RequestMapping(value = "/saveAgentCommision", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAgentCommisionBean objBean, BindingResult result, HttpServletRequest req) {
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsAgentCommisionHdModel objModel = objAgentCommisionService.funPrepareModel(objBean, userCode, clientCode);
			objAgentCommisionService.funAddUpdateAgentCommision(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Agent Commission Code : ".concat(objModel.getStrAgentCommCode()));

			return new ModelAndView("redirect:/frmAgentCommision.html");
		} else {
			return new ModelAndView("frmAgentCommision");
		}
	}

	// Load Agent Commission Data On Form
	@RequestMapping(value = "/loadAgentCommCode", method = RequestMethod.GET)
	public @ResponseBody clsAgentCommisionHdModel funLoadAgentMaster(@RequestParam("agentCommCode") String agentCommCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsAgentCommisionHdModel objModel = objAgentCommisionService.funGetAgentCommision(agentCommCode, clientCode);
		if (objModel == null) {
			objModel = new clsAgentCommisionHdModel();
			objModel.setStrAgentCommCode("Invalid Code");
		}
		return objModel;
	}

}
