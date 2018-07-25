package com.sanguine.excise.controller;

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
import com.sanguine.excise.bean.clsSizeMasterBean;
import com.sanguine.excise.model.clsSizeMasterModel;
import com.sanguine.excise.service.clsSizeMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsSizeMasterController {

	@Autowired
	private clsSizeMasterService objSizeMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	// Open SizeMaster
	@RequestMapping(value = "/frmExciseSizeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		List<String> uomList = new ArrayList<String>();
		// uomList=objclsUOMService.funGetUOMList(clientCode);
		uomList.add("ML");
		uomList.add("Peg");
		uomList.add("Ltr");
		model.put("uomList", uomList);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseSizeMaster_1", "command", new clsSizeMasterModel());
		} else {
			return new ModelAndView("frmExciseSizeMaster", "command", new clsSizeMasterModel());
		}
	}

	// Save or Update SizeMaster
	@RequestMapping(value = "/saveExciseSizeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsSizeMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String isSizeGlobal = "Custom";
		try {
			isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		if (!result.hasErrors()) {
			clsSizeMasterModel objclsSizeMasterModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = objSizeMasterService.funAddUpdateSizeMaster(objclsSizeMasterModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Size Name : ".concat(objclsSizeMasterModel.getStrSizeName()));
				return new ModelAndView("redirect:/frmExciseSizeMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmExciseSizeMaster?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("frmExciseSizeMaster?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsSizeMasterModel funPrepareModel(clsSizeMasterBean objBean, String userCode, String clientCode) {

		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsSizeMasterModel objModel = new clsSizeMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrSizeCode().isEmpty())) {
				clsSizeMasterModel objModel1 = objSizeMasterService.funGetObject(objBean.getStrSizeCode(), clientCode);
				if (objModel1 != null) {
					objModel.setStrSizeCode(objModel1.getStrSizeCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblsizemaster", "intId");
				String categoryCode = "S" + String.format("%05d", lastNo);
				objModel.setStrSizeCode(categoryCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrSizeName(objBean.getStrSizeName());
			objModel.setIntQty(objBean.getIntQty());
			objModel.setStrUOM(objBean.getStrUOM());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrNarration(objBean.getStrNarration());
		}
		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseSizeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsSizeMasterModel funAssignFieldsForForm(@RequestParam("sizeCode") String sizeCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSizeGlobal = "Custom";
		try {
			isSizeGlobal = req.getSession().getAttribute("strSizeMaster").toString();
		} catch (Exception e) {
			isSizeGlobal = "Custom";
		}
		if (isSizeGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}
		clsSizeMasterModel objModel = objSizeMasterService.funGetObject(sizeCode, clientCode);
		if (null == objModel) {
			objModel = new clsSizeMasterModel();
			objModel.setStrSizeCode("Invalid Code");
		}
		return objModel;
	}

}
