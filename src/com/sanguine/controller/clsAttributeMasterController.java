package com.sanguine.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsAttributeMasterBean;
import com.sanguine.model.clsAttributeMasterModel;
import com.sanguine.model.clsAttributeMasterModel_ID;
import com.sanguine.service.clsAttributeMasterService;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsAttributeMasterController {
	@Autowired
	private clsAttributeMasterService objAttributeMasterService;
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	private clsGlobalFunctions objGlobal;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/frmAttributeMaster", method = RequestMethod.GET)
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
		model.put("listAttType", listType);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAttributeMaster_1", "command", new clsAttributeMasterModel());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmAttributeMaster", "command", new clsAttributeMasterModel());
		} else {
			return null;
		}

	}

	// Save or Update group master function to save or update record of group
	// master into database and also validates all fields of form.
	@RequestMapping(value = "/saveAttributeMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsAttributeMasterBean objBean, BindingResult result, HttpServletRequest req) {
		objGlobal = new clsGlobalFunctions();
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		if (!result.hasErrors()) {
			clsAttributeMasterModel objModel = funPrepareAttributeModel(objBean, userCode, clientCode);
			objAttributeMasterService.funAddUpdate(objModel);
			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Attribute Code : ".concat(objModel.getStrAttCode()));
			return new ModelAndView("redirect:/frmAttributeMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmAttributeMaster.html?saddr=" + urlHits);
		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/loadAttributeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsAttributeMasterModel funAssignFields(@RequestParam("attCode") String attCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsAttributeMasterModel objModel = objAttributeMasterService.funGetObject(attCode, clientCode);
		if (null == objModel) {
			objModel = new clsAttributeMasterModel();
			objModel.setStrAttCode("Invalid Code");
		}

		return objModel;
	}

	// Returns a single group master record by passing group code as primary
	// key. Also generates next Group Code if transaction is for Save Master
	private clsAttributeMasterModel funPrepareAttributeModel(clsAttributeMasterBean objBean, String userCode, String clientCode) {
		long lastNo = 0;
		clsAttributeMasterModel objModel;

		if (objBean.getStrAttCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetLastNo("tblattributemaster", "AttributeMaster", "intId", clientCode);
			String AttCode = "A" + String.format("%07d", lastNo);
			objModel = new clsAttributeMasterModel(new clsAttributeMasterModel_ID(AttCode, clientCode));
		} else {
			clsAttributeMasterModel objModel1 = objAttributeMasterService.funGetObject(objBean.getStrAttCode(), clientCode);
			if (null == objModel1) {
				lastNo = objGlobalFunctionsService.funGetLastNo("tblattributemaster", "AttributeMaster", "intId", clientCode);
				String AttCode = "A" + String.format("%07d", lastNo);
				objModel = new clsAttributeMasterModel(new clsAttributeMasterModel_ID(AttCode, clientCode));
			} else {
				objModel = new clsAttributeMasterModel(new clsAttributeMasterModel_ID(objBean.getStrAttCode(), clientCode));
			}

		}
		objModel.setStrAttName(objBean.getStrAttName());
		objModel.setStrAttDesc(objBean.getStrAttDesc());
		objModel.setStrAttType(objBean.getStrAttType());
		objModel.setStrPAttCode(objBean.getStrPAttCode());
		objModel.setIntId(lastNo);
		objModel.setStrUserCreated(userCode);
		objModel.setStrUserModified(userCode);
		objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrClientCode(clientCode);

		return objModel;
	}

	@RequestMapping(value = "/frmAttributeList", method = RequestMethod.GET)
	public ModelAndView funAttributeListfrom() throws JRException {
		return new ModelAndView("frmAttributeList", "command", new clsReportBean());
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/rptAttributeList", method = RequestMethod.GET)
	public ModelAndView funShowReport(@ModelAttribute("command") clsReportBean objBean, ModelAndView modelAndView, Map<String, Object> model, HttpServletRequest req) throws JRException {
		String companyName = "DSS";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		String attCode = objBean.getStrDocCode();
		String type = objBean.getStrDocType();
		List listAttributeList = objAttributeMasterService.funGetList(attCode, clientCode);
		List<clsAttributeMasterModel> attributeList = funPrepareAttributeModel(listAttributeList);

		JRDataSource JRdataSource = new JRBeanCollectionDataSource(attributeList);

		model.put("datasource", JRdataSource);
		model.put("strCompanyName", companyName);
		model.put("strUserCode", userCode);
		return new ModelAndView("attributeList" + type.trim().toUpperCase() + "Report", model);

	}

	@SuppressWarnings("rawtypes")
	private List<clsAttributeMasterModel> funPrepareAttributeModel(List listAttribute) {
		List<clsAttributeMasterModel> attributeList = new ArrayList<clsAttributeMasterModel>();
		for (int i = 0; i < listAttribute.size(); i++) {
			// Object[] ob = (Object[])listAttribute.get(i);
			clsAttributeMasterModel AttList = (clsAttributeMasterModel) listAttribute.get(i);
			clsAttributeMasterModel objAttList = new clsAttributeMasterModel();
			objAttList.setStrAttCode(AttList.getStrAttCode());
			objAttList.setStrAttName(AttList.getStrAttName());
			objAttList.setStrAttType(AttList.getStrAttType());
			objAttList.setStrAttDesc(AttList.getStrAttDesc());
			attributeList.add(objAttList);
		}
		return attributeList;
	}
}
