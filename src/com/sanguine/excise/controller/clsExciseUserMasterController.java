package com.sanguine.excise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsUserMasterBean;
import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsUserMasterModel;
import com.sanguine.model.clsUserMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsTreeMenuService;
import com.sanguine.service.clsUserMasterService;

@Controller
public class clsExciseUserMasterController {
	@Autowired
	clsTreeMenuService objclsTreeMenuService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	@Autowired
	private clsUserMasterService objUserMasterService;
	clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmExciseUserMaster", method = RequestMethod.GET)
	public ModelAndView funOpenUserMaster(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);
		ModelAndView ob = null;
		if ("2".equalsIgnoreCase(urlHits)) {
			ob = new ModelAndView("frmExciseUserMaster_1", "command", new clsUserMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			ob = new ModelAndView("frmExciseUserMaster", "command", new clsUserMasterModel());
		} else {
			ob = new ModelAndView("frmExciseUserMaster", "command", new clsUserMasterModel());
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> properties = objUserMasterService.funGetUserProperties(clientCode);

		if (properties.isEmpty()) {
			properties.put("", "");
		}
		ob.addObject("propertyList", properties);
		return ob;
	}

	@RequestMapping(value = "/saveExciseUserMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsUserMasterBean bean, BindingResult result, HttpServletRequest req) {

		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String userCode = req.getSession().getAttribute("usercode").toString();
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		if (!result.hasErrors()) {
			clsUserMasterModel objUserMaster = funPrepareMaster(bean, userCode, clientCode);
			objUserMasterService.funAddUpdateUser(objUserMaster);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "User Code : ".concat(objUserMaster.getStrUserCode1()));
			return new ModelAndView("redirect:/frmExciseUserMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmExciseUserMaster?saddr=" + urlHits);
		}
	}

	@RequestMapping(value = "/loadExciseUserMasterData", method = RequestMethod.GET)
	public @ResponseBody clsUserMasterModel funAssignFields(@RequestParam("userCode") String code, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsUserMasterModel objModel = objUserMasterService.funGetObject(code, clientCode);
		if (null == objModel) {
			objModel = new clsUserMasterModel();
			objModel.setStrUserCode1("Invalid Code");
		}

		return objModel;
	}

	private clsUserMasterModel funPrepareMaster(clsUserMasterBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		lastNo = objGlobalFunctionsService.funGetLastNo("dbwebmms.tbluserhd", "UserMaster", "intid", clientCode);
		clsUserMasterModel objUserMaster = new clsUserMasterModel(new clsUserMasterModel_ID(objBean.getStrUserCode1(), clientCode));
		objUserMaster.setIntid(lastNo);
		objUserMaster.setStrUserCreated(userCode);
		objUserMaster.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		// objUserMaster.setStrClientCode(clientCode);
		// objUserMaster.setStrUserCode1(objBean.getStrUserCode1());
		objUserMaster.setStrUserName1(objBean.getStrUserName1());
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(objBean.getStrPassword1());
		objUserMaster.setStrPassword1(hashedPassword);
		objUserMaster.setStrProperty(objBean.getStrProperty());
		objUserMaster.setStrSuperUser(objBean.getStrSuperUser());
		if (objBean.getStrRetire().equals("Yes")) {
			objUserMaster.setStrRetire("Y");
		} else {
			objUserMaster.setStrRetire("N");
		}
		objUserMaster.setStrType(objGlobal.funIfNull(objBean.getStrType(), "", objBean.getStrType()));
		objUserMaster.setStrLoginStatus(objGlobal.funIfNull(objBean.getStrLoginStatus(), "", objBean.getStrLoginStatus()));
		objUserMaster.setStrSignatureImg(objGlobal.funIfNull(objBean.getStrSignatureImg(), "", objBean.getStrSignatureImg()));
		objUserMaster.setStrUserModified(userCode);
		objUserMaster.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		return objUserMaster;
	}

	@RequestMapping(value = "/frmExciseSystemUsers", method = RequestMethod.GET)
	public ModelAndView funSystemUserfrom() throws JRException {
		return new ModelAndView("redirect:/rptSystemUsers.html");
	}

}