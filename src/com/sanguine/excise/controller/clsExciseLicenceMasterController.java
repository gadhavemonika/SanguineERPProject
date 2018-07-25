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
import com.sanguine.excise.bean.clsExciseLicenceMasterBean;
import com.sanguine.excise.model.clsExciseLicenceMasterModel;
import com.sanguine.excise.service.clsLicenceMasterService;
import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.service.clsGlobalFunctionsService;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class clsExciseLicenceMasterController {

	@Autowired
	private clsLicenceMasterService objLicenceMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	// Open LicenceMaster

	@RequestMapping(value = "/frmExciseLicenceMaster", method = RequestMethod.GET)
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

		// /model.put("userList", userList) ;
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmExciseLicenceMaster_1", "command", new clsExciseLicenceMasterBean());
		} else {
			return new ModelAndView("frmExciseLicenceMaster", "command", new clsExciseLicenceMasterBean());
		}
	}

	// Save or Update LicenceMaster
	@RequestMapping(value = "/saveExciseLicenceMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsExciseLicenceMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			clsExciseLicenceMasterModel objModel = funPrepareModel(objBean, userCode, clientCode);
			boolean success = objLicenceMasterService.funAddUpdateLicenceMaster(objModel);
			if (success) {
				req.getSession().setAttribute("success", true);
				req.getSession().setAttribute("successMessage", "Licence Number : ".concat(objModel.getStrLicenceNo()));
				return new ModelAndView("redirect:/frmExciseLicenceMaster.html?saddr=" + urlHits);
			} else {
				return new ModelAndView("frmExciseLicenceMaster.html?saddr=" + urlHits);
			}
		} else {
			return new ModelAndView("frmExciseLicenceMaster.html?saddr=" + urlHits);
		}
	}

	// Load UserDetail

	// @RequestMapping(value = "/loadUserDetail", method = RequestMethod.POST)
	// public ArrayList<Object> loadUserDetail(HttpServletRequest request){
	//
	// ArrayList userList=new ArrayList();
	//
	//
	// String
	// clientCode=request.getSession().getAttribute("clientCode").toString();
	// String sql="from cl where strClientCode='"+clientCode+"' ";
	// List<clsLocationMasterModel>
	// list=objGlobalFunctionsService.funGetList(sql, "hql");
	//
	//
	// String
	// userSql="select strUserCode from dbwebmms.tbluserhd a where a.strClientCode='"+clientCode+"' ";
	// List userData=objGlobalFunctionsService.funGetDataList(userSql, "sql");
	// if(userData.size()>0){
	// for(int i=0;i<userData.size();i++){
	// userList.add(userData.get(i).toString());
	//
	// }
	// }
	//
	//
	// return userList;
	// }

	// Convert bean to model function
	private clsExciseLicenceMasterModel funPrepareModel(clsExciseLicenceMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();

		long lastNo = 0;
		clsExciseLicenceMasterModel objModel = new clsExciseLicenceMasterModel();

		if (objBean != null) {
			if (!(objBean.getStrLicenceCode().isEmpty())) {
				clsExciseLicenceMasterModel objModel1 = objLicenceMasterService.funGetObject(objBean.getStrLicenceCode(), objBean.getStrPropertyCode(), clientCode);
				if (objModel1 != null) {
					objModel.setStrLicenceCode(objModel1.getStrLicenceCode());
					objModel.setStrUserCreated(objModel1.getStrUserCreated());
					objModel.setDteDateCreated(objModel1.getDteDateCreated());
					objModel.setIntId(objModel1.getIntId());
				}
			} else {

				lastNo = objGlobalFunctionsService.funGetCount("tbllicencemaster", "intId");
				String licenceCode = "L" + String.format("%03d", lastNo);

				objModel.setStrLicenceCode(licenceCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setIntId(lastNo);
			}

			objModel.setStrLicenceNo(objBean.getStrLicenceNo());
			objModel.setStrLicenceName(objBean.getStrLicenceName());
			objModel.setStrVATNo(objBean.getStrVATNo());
			objModel.setStrTINNo(objBean.getStrTINNo());
			objModel.setStrAddress1(objBean.getStrAddress1());
			objModel.setStrAddress2(objBean.getStrAddress2());
			objModel.setStrAddress3(objBean.getStrAddress3());
			objModel.setStrExternalCode(objBean.getStrExternalCode());
			objModel.setStrCity(objBean.getStrCity());
			objModel.setStrPINCode(objBean.getStrPINCode());
			objModel.setStrEmailId(objBean.getStrEmailId());
			objModel.setLongTelephoneNo(objBean.getLongTelephoneNo());
			objModel.setLongMobileNo(objBean.getLongMobileNo());
			objModel.setStrContactPerson(objBean.getStrContactPerson());
			objModel.setStrBusinessCode(objBean.getStrBusinessCode());
			objModel.setStrClientCode(clientCode);
			objModel.setStrUserEdited(userCode);
			objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
			objModel.setStrPropertyCode(objBean.getStrPropertyCode());
		}

		return objModel;

	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadExciseLicenceMasterData", method = RequestMethod.GET)
	public @ResponseBody clsExciseLicenceMasterModel funAssignFieldsForForm(@RequestParam("licenceCode") String licenceCode, HttpServletRequest request) {
		String propertyCode = request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsExciseLicenceMasterModel objModel = objLicenceMasterService.funGetObject(licenceCode, propertyCode, clientCode);
		if (null == objModel) {
			objModel = new clsExciseLicenceMasterModel();
			objModel.setStrLicenceCode("Invalid Code");
		}

		return objModel;
	}

	/**
	 * Load All Licence for All Property
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/loadAllLicenceForAllProperty", method = RequestMethod.GET)
	public @ResponseBody List<clsExciseLicenceMasterModel> funLoadAllLicenceAllProp(HttpServletRequest request) {
		// String
		// propCode=request.getSession().getAttribute("propertyCode").toString();
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String sql = "from clsExciseLicenceMasterModel where strClientCode='" + clientCode + "' ";
		List<clsExciseLicenceMasterModel> list = objGlobalFunctionsService.funGetListModuleWise(sql, "hql");
		return list;
	}

}
