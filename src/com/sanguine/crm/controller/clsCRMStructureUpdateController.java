package com.sanguine.crm.controller;

import java.util.ArrayList;
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

import com.sanguine.bean.clsDeleteModuleListBean;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSecurityShellService;
import com.sanguine.service.clsStructureUpdateService;

@Controller
public class clsCRMStructureUpdateController {

	@Autowired
	private clsStructureUpdateService objStructureUpdateService;

	@Autowired
	private clsSecurityShellService objSecurityShellService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	/**
	 * Open Structure Update Form
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/frmCRMStructureUpdate", method = RequestMethod.GET)
	public ModelAndView funOpenStructureUpdateForm(Map<String, Object> model, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMStructureUpdate_1");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCRMStructureUpdate");
		} else {
			return null;
		}

	}

}
