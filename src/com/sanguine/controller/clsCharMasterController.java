package com.sanguine.controller;

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

import com.sanguine.bean.clsCharacteristicsMasterBean;
import com.sanguine.model.clsCharacteristicsMasterModel;
import com.sanguine.model.clsCharacteristicsMasterModel_ID;
import com.sanguine.service.clsCharacteristicsMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsCharMasterController {
	@Autowired
	private clsCharacteristicsMasterService objCharService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@RequestMapping(value = "/frmCharMaster", method = RequestMethod.GET)
	public ModelAndView funOpenCharacteristicsMasterForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCharacteristicsMaster_1", "command", new clsCharacteristicsMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmCharacteristicsMaster", "command", new clsCharacteristicsMasterModel());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/saveCharMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsCharacteristicsMasterBean characterisicsBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsCharacteristicsMasterModel obCharacterisics = funPrepareModel(characterisicsBean, clientCode, userCode);
			objCharService.funAddCharacteristics(obCharacterisics);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Chracteristics Code : ".concat(obCharacterisics.getStrCharCode()));
			return new ModelAndView("redirect:/frmCharMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmCharMaster.html?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadCharData", method = RequestMethod.GET)
	public @ResponseBody clsCharacteristicsMasterModel funAssignFields(@RequestParam("charCode") String Code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsCharacteristicsMasterModel obCharacteristics = objCharService.funGetCharacteristics(Code, clientCode);
		return obCharacteristics;
	}

	private clsCharacteristicsMasterModel funPrepareModel(clsCharacteristicsMasterBean characterisicsBean, String clientCode, String userCode) {
		long lastNo = 0;
		clsCharacteristicsMasterModel characterisics;
		clsGlobalFunctions ob = new clsGlobalFunctions();
		if (characterisicsBean.getStrCharCode().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblcharacteristics", "CharacteristicsMaster", "intid", clientCode);
			String CharCode = "C" + String.format("%07d", lastNo);
			characterisics = new clsCharacteristicsMasterModel(new clsCharacteristicsMasterModel_ID(CharCode, clientCode));

		} else {

			characterisics = new clsCharacteristicsMasterModel(new clsCharacteristicsMasterModel_ID(characterisicsBean.getStrCharCode(), clientCode));
		}
		characterisics.setStrCharName(characterisicsBean.getStrCharName());
		characterisics.setStrCharType(characterisicsBean.getStrCharType());
		characterisics.setStrCharDesc(characterisicsBean.getStrCharDesc());
		characterisics.setIntId(lastNo);
		characterisics.setStrUserCreated(userCode);
		characterisics.setStrUserModified(userCode);
		characterisics.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		characterisics.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));

		return characterisics;
	}

}
