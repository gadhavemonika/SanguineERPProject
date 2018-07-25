package com.sanguine.crm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.service.clsPartyMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsInwardOutwardRegisterController {
	@Autowired
	clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	clsPartyMasterService objPartyMasterService;

	@RequestMapping(value = "/frmInwardOutwardRegister", method = RequestMethod.GET)
	public ModelAndView funInwardOutwardRegister(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String data = "select strPType from tblpartymaster where strClientCode='" + clientCode + "'  group by strPType ";
		List<String> strDocType = objGlobalFunctionsService.funGetList(data, "sql");
		if (!strDocType.isEmpty()) {
			model.put("typeList", strDocType);
		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmInwardOutwardRegister_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmInwardOutwardRegister", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/loadPartyType", method = RequestMethod.GET)
	public @ResponseBody clsPartyMasterModel funAssignFieldsPType(@RequestParam("partyType") String partyType, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsPartyMasterModel objPartyMasterModel = objPartyMasterService.funGetObject(partyType, clientCode);
		if (null == objPartyMasterModel) {
			objPartyMasterModel = new clsPartyMasterModel();
			objPartyMasterModel.setStrPCode("Invalid Code");
		}
		return objPartyMasterModel;
	}

}
