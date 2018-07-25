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
import com.sanguine.excise.bean.clsExciseSupplierMasterBean;
import com.sanguine.excise.model.clsCityMasterModel;
import com.sanguine.excise.model.clsExciseSupplierMasterModel;
import com.sanguine.excise.service.clsExciseSupplierMasterService;
import com.sanguine.service.clsGlobalFunctionsService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
public class clsExciseSupplierMasterController {

	@Autowired
	private clsExciseSupplierMasterService objExciseSupplierMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal = null;

	// Open ExciseSupplierMaster
	@RequestMapping(value = "/frmExciseSupplierMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		List listType = new ArrayList<>();
		listType.add("Text");
		listType.add("Integer");
		listType.add("List");

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseSupplierMaster_1", "command", new clsExciseSupplierMasterModel());
		} else {
			return new ModelAndView("frmExciseSupplierMaster", "command", new clsExciseSupplierMasterModel());
		}
	}

	// Save or Update ExciseSupplierMaster
	@RequestMapping(value = "/saveExciseSupplierMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseSupplierMasterBean objBean, BindingResult result, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String isSupplierGlobal = "Custom";
		try {
			isSupplierGlobal = req.getSession().getAttribute("strSupplier").toString();
		} catch (Exception e) {
			isSupplierGlobal = "Custom";
		}
		if (isSupplierGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}

		if (!result.hasErrors()) {

			clsExciseSupplierMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = objExciseSupplierMasterService.funAddUpdateExciseSupplierMaster(objModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Supplier Number : ".concat(objModel.getStrSupplierName()));
				return new ModelAndView("redirect:/frmExciseSupplierMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmExciseSupplierMaster?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("frmExciseSupplierMaster?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsExciseSupplierMasterModel funPrepareModel(clsExciseSupplierMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsExciseSupplierMasterModel objModel = new clsExciseSupplierMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrSupplierCode().isEmpty())) {
				List objList = objExciseSupplierMasterService.funGetObject(objBean.getStrSupplierCode(), clientCode);
				Object obj[] = (Object[]) objList.get(0);
				clsExciseSupplierMasterModel objModel1 = (clsExciseSupplierMasterModel) obj[0];
				if (objModel1 != null) {
					objModel.setStrSupplierCode(objModel1.getStrSupplierCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tblsuppliermaster", "intId");
				String licenceCode = "S" + String.format("%05d", lastNo);

				objModel.setStrSupplierCode(licenceCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			// objModel.setStrSupplierNo(objBean.getStrSupplierNo());
			objModel.setStrSupplierName(objBean.getStrSupplierName());
			objModel.setStrVATNo(objBean.getStrVATNo());
			objModel.setStrTINNo(objBean.getStrTINNo());
			objModel.setStrAddress(objBean.getStrAddress());
			objModel.setStrCityCode(objBean.getStrCityCode());
			objModel.setStrPINCode(objBean.getStrPINCode());
			objModel.setStrEmailId(objBean.getStrEmailId());
			objModel.setLongTelephoneNo(objBean.getLongTelephoneNo());
			objModel.setLongMobileNo(objBean.getLongMobileNo());
			objModel.setStrContactPerson(objBean.getStrContactPerson());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}

		return objModel;
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseSupplierMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseSupplierMasterModel funAssignFieldsForForm(@RequestParam("supplierCode") String supplierCode, HttpServletRequest req) {

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String isSupplierGlobal = "Custom";
		try {
			isSupplierGlobal = req.getSession().getAttribute("strSupplier").toString();
		} catch (Exception e) {
			isSupplierGlobal = "Custom";
		}
		if (isSupplierGlobal.equalsIgnoreCase("All")) {
			clientCode = "All";
		}
		clsExciseSupplierMasterModel objModel = null;
		List objList = objExciseSupplierMasterService.funGetObject(supplierCode, clientCode);

		if (objList.isEmpty()) {
			objModel = new clsExciseSupplierMasterModel();
			objModel.setStrSupplierCode("Invalid Code");
		} else {
			Object obj[] = (Object[]) objList.get(0);
			clsExciseSupplierMasterModel objModel1 = (clsExciseSupplierMasterModel) obj[0];
			clsCityMasterModel objclsCityMasterModel = (clsCityMasterModel) obj[1];
			objModel = objModel1;
			objModel.setStrCityName(objclsCityMasterModel.getStrCityName());
		}
		return objModel;
	}

}
