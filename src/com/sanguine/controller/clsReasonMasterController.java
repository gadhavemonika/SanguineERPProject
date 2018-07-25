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

import com.sanguine.bean.clsReasonMasterBean;
import com.sanguine.model.clsReasonMaster;
import com.sanguine.model.clsReasonMaster_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsReasonMasterService;

@Controller
public class clsReasonMasterController {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;
	@Autowired
	private clsReasonMasterService objReasonMasterService;
	@Autowired
	clsGlobalFunctions objGlobalFunctions;

	@RequestMapping(value = "/frmReasonMaster", method = RequestMethod.GET)
	public ModelAndView funOpenReasonMaster(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReasonMaster_1", "command", new clsReasonMaster());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmReasonMaster", "command", new clsReasonMaster());
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/savereasonmaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsReasonMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {
			String userCode = req.getSession().getAttribute("usercode").toString();
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsReasonMaster objReasonMaster = funPrepareModel(objBean, userCode, clientCode);
			objReasonMasterService.funAddUpdateReason(objReasonMaster);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Reason Code : ".concat(objReasonMaster.getStrReasonCode()));
			return new ModelAndView("redirect:/frmReasonMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmReasonMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadReasonMasterData", method = RequestMethod.GET)
	public @ResponseBody clsReasonMaster funAssignFields(@RequestParam("reasonCode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsReasonMaster objModel = objReasonMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsReasonMaster();
			objModel.setStrReasonCode("Invalid Code");
		}
		objModel.setDtExpiryDate(objGlobalFunctions.funGetDate("yyyy/MM/dd", objModel.getDtExpiryDate()));
		return objModel;
	}

	private clsReasonMaster funPrepareModel(clsReasonMasterBean reasonBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsReasonMaster objReasonMaster;
		clsGlobalFunctions ob = new clsGlobalFunctions();
		if (reasonBean.getStrReasonCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblreasonmaster", "ReasonMaster", "intid", clientCode);
			String reasonCode = "R" + String.format("%07d", lastNo);
			objReasonMaster = new clsReasonMaster(new clsReasonMaster_ID(reasonCode, clientCode));

			objReasonMaster.setIntid(lastNo);
			objReasonMaster.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
			objReasonMaster.setStrUserCreated(userCode);
		} else {
			clsReasonMaster objModel = objReasonMasterService.funGetObject(reasonBean.getStrReasonCode(), clientCode);
			if (null == objModel) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblreasonmaster", "ReasonMaster", "intid", clientCode);
				String reasonCode = "R" + String.format("%07d", lastNo);
				objReasonMaster = new clsReasonMaster(new clsReasonMaster_ID(reasonCode, clientCode));

				objReasonMaster.setIntid(lastNo);
				objReasonMaster.setDtCreatedDate(ob.funGetCurrentDateTime("yyyy-MM-dd"));
				objReasonMaster.setStrUserCreated(userCode);
			} else {
				objReasonMaster = new clsReasonMaster(new clsReasonMaster_ID(reasonBean.getStrReasonCode(), clientCode));
			}
		}

		objReasonMaster.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objReasonMaster.setStrCorract(objGlobal.funIfNull(reasonBean.getStrCorract(), "N", "Y"));
		objReasonMaster.setStrDelcha(objGlobal.funIfNull(reasonBean.getStrDelcha(), "N", "Y"));
		objReasonMaster.setStrFollowUps(objGlobal.funIfNull(reasonBean.getStrFollowUps(), "N", "Y"));
		objReasonMaster.setStrLeadMaster(objGlobal.funIfNull(reasonBean.getStrLeadMaster(), "N", "Y"));
		objReasonMaster.setStrNonConf(objGlobal.funIfNull(reasonBean.getStrNonConf(), "N", "Y"));
		objReasonMaster.setStrPrevAct(objGlobal.funIfNull(reasonBean.getStrPrevAct(), "N", "Y"));
		objReasonMaster.setStrReasonDesc(reasonBean.getStrReasonDesc());
		objReasonMaster.setStrReasonName(reasonBean.getStrReasonName());
		objReasonMaster.setStrResAlloc(objGlobal.funIfNull(reasonBean.getStrResAlloc(), "N", "Y"));
		objReasonMaster.setStrStockAdj(objGlobal.funIfNull(reasonBean.getStrStockAdj(), "N", "Y"));
		objReasonMaster.setStrStocktra(objGlobal.funIfNull(reasonBean.getStrStocktra(), "N", "Y"));
		objReasonMaster.setStrUserModified(userCode);
		objReasonMaster.setDtLastModified(ob.funGetCurrentDateTime("yyyy-MM-dd"));
		objReasonMaster.setDtExpiryDate(objGlobal.funGetDate("yyyy-MM-dd", reasonBean.getDtExpiryDate()));

		return objReasonMaster;
	}

	@RequestMapping(value = "/checkReasonName", method = RequestMethod.GET)
	public @ResponseBody boolean funCheckGroupName(@RequestParam("reasonName") String Name, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		boolean count = objGlobalFunctions.funCheckName(Name, clientCode, "frmReasonMaster");
		return count;

	}
}
