package com.sanguine.crm.controller;

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
import com.sanguine.crm.bean.clsExciseChallanBean;
import com.sanguine.crm.model.clsExciseChallanModel;
import com.sanguine.crm.service.clsExciseChallanService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseChallanController {

	@Autowired
	private clsExciseChallanService objExciseChallanService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	private clsGlobalFunctions objGlobal = null;

	// Open ExciseChallan
	@RequestMapping(value = "/frmExciseChallan", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseChallan_1", "command", new clsExciseChallanBean());
		} else {
			return new ModelAndView("frmExciseChallan", "command", new clsExciseChallanBean());
		}
	}

	// Save or Update ExciseChallan
	@RequestMapping(value = "/saveExciseChallan", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseChallanBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if (!result.hasErrors()) {

			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();

			clsExciseChallanModel objHdModel = funPrepareModel(objBean, clientCode, userCode);
			boolean success = objExciseChallanService.funAddUpdateExciseChallan(objHdModel);

			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Excise Challan Code : ".concat(objHdModel.getStrECCode()));
				return new ModelAndView("redirect:/frmExciseChallan.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("redirect:/frmExciseChallan.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("redirect:/frmExciseChallan.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	@SuppressWarnings("rawtypes")
	private clsExciseChallanModel funPrepareModel(clsExciseChallanBean objBean, String clientCode, String userCode) {
		objGlobal = new clsGlobalFunctions();
		Long lastNo = new Long(0);
		objGlobal = new clsGlobalFunctions();

		clsExciseChallanModel objModel = new clsExciseChallanModel();

		if (objBean != null) {
			if (!(objBean.getStrECCode().isEmpty())) {
				List objList = objExciseChallanService.funGetObject(objBean.getStrECCode(), clientCode);
				Object obj[] = (Object[]) objList.get(0);
				clsExciseChallanModel objModel1 = (clsExciseChallanModel) obj[0];
				if (objModel1 != null) {
					objModel.setStrECCode(objModel1.getStrECCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblexcisechallan", "intId");
				String ECCode = "EC" + String.format("%05d", lastNo);
				objModel.setStrECCode(ECCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrAgainst(objBean.getStrAgainst());
			objModel.setStrChallanType(objBean.getStrChallanType());
			objModel.setStrCurrency(objBean.getStrCurrency());
			objModel.setStrDuration(objBean.getStrDuration());
			objModel.setStrGRChallanCode(objBean.getStrGRChallanCode());
			objModel.setStrIdentityMarks(objBean.getStrIdentityMarks());
			objModel.setStrProcessCode(objBean.getStrProcessCode());
			objModel.setStrProdCode(objBean.getStrProdCode());
			objModel.setStrScode(objBean.getStrScode());
			objModel.setStrTariff(objBean.getStrTariff());

			objModel.setDblQty((objBean.getDblQty() != null) ? objBean.getDblQty() : 0);
			objModel.setDblUnitPrice((objBean.getDblUnitPrice() != null) ? objBean.getDblUnitPrice() : 0);
			objModel.setDteDispatchDate(objBean.getDteDispatchDate());
			objModel.setDteECDate(objBean.getDteECDate());
			objModel.setDteDispatchTime(objBean.getDteDispatchTime());
			objModel.setDteGRChallanDate(objBean.getDteGRChallanDate());

			objModel.setStrClientCode(clientCode);
			objModel.setStrUserModified(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		return objModel;
	}

	// Load Data on Form for Edit Transaction
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/loadExciseChallanData", method = RequestMethod.GET)
	public @ResponseBody clsExciseChallanBean funLoadHdData(@RequestParam("ecCode") String ecCode, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String clientCode = req.getSession().getAttribute("clientCode").toString();

		clsExciseChallanBean objBean = null;
		List objList = objExciseChallanService.funGetObject(ecCode, clientCode);
		if (objList.isEmpty()) {
			objBean = new clsExciseChallanBean();
			objBean.setStrECCode("Invalid Code");
		} else {
			Object obj[] = (Object[]) objList.get(0);
			clsExciseChallanModel objModel = (clsExciseChallanModel) obj[0];
			objBean = funPrepareBean(objModel);
		}
		return objBean;
	}

	private clsExciseChallanBean funPrepareBean(clsExciseChallanModel objModel) {

		clsExciseChallanBean objBean = new clsExciseChallanBean();

		objBean.setStrECCode(objModel.getStrECCode());
		objBean.setStrUserCreated(objModel.getStrUserCreated());
		objBean.setDteDateCreated(objModel.getDteDateCreated());
		objBean.setIntId(objModel.getIntId());
		objBean.setStrAgainst(objModel.getStrAgainst());
		objBean.setStrChallanType(objModel.getStrChallanType());
		objBean.setStrCurrency(objModel.getStrCurrency());
		objBean.setStrDuration(objModel.getStrDuration());
		objBean.setStrGRChallanCode(objModel.getStrGRChallanCode());
		objBean.setStrIdentityMarks(objModel.getStrIdentityMarks());
		objBean.setStrProcessCode(objModel.getStrProcessCode());
		objBean.setStrProdCode(objModel.getStrProdCode());
		objBean.setStrScode(objModel.getStrScode());
		objBean.setStrTariff(objModel.getStrTariff());
		objBean.setDblQty((objModel.getDblQty() != null) ? objBean.getDblQty() : 0);
		objBean.setDblUnitPrice((objModel.getDblUnitPrice() != null) ? objBean.getDblUnitPrice() : 0);
		objBean.setDteDispatchDate(objModel.getDteDispatchDate());
		objBean.setDteECDate(objModel.getDteECDate());
		objBean.setDteDispatchTime(objModel.getDteDispatchTime());
		objBean.setDteGRChallanDate(objModel.getDteGRChallanDate());
		objBean.setStrClientCode(objModel.getStrClientCode());
		objBean.setStrUserModified(objModel.getStrUserModified());
		objBean.setDteDateEdited(objModel.getDteDateEdited());

		return objBean;
	}

}
