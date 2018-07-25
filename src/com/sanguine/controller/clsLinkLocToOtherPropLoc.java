package com.sanguine.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
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

import com.sanguine.bean.clsLinkLocToOtherPropLocBean;
import com.sanguine.model.clsLinkLoctoOtherPropLocModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsLinkLoctoOtherPropLocService;

@Controller
public class clsLinkLocToOtherPropLoc {

	@Autowired
	private clsGlobalFunctionsService objGlobalService;

	@Autowired
	private clsLinkLoctoOtherPropLocService objLinkLoctoOtherPropLocService;
	@Autowired
	private clsGlobalFunctions objGlobal;

	@RequestMapping(value = "/frmLinkLocToOtherPropLoc", method = RequestMethod.GET)
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
			ob = new ModelAndView("frmLinkLocToOtherPropLoc_1", "command", new clsLinkLocToOtherPropLocBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			ob = new ModelAndView("frmLinkLocToOtherPropLoc", "command", new clsLinkLocToOtherPropLocBean());
		} else {
			ob = new ModelAndView("frmLinkLocToOtherPropLoc", "command", new clsLinkLocToOtherPropLocBean());
		}
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		Map<String, String> mapProperties = objGlobalService.funGetPropertyList(clientCode);

		String logInProperty = request.getSession().getAttribute("propertyCode").toString();

		Map<String, String> hmFromLoc = new HashMap<String, String>();
		List listLoc = funLoadByLocation(logInProperty, request);
		if (listLoc.size() > 0) {
			for (int i = 0; i < listLoc.size(); i++) {
				Object[] obj = (Object[]) listLoc.get(i);
				hmFromLoc.put(obj[0].toString(), obj[1].toString());
			}
		}
		if (hmFromLoc.isEmpty()) {
			hmFromLoc.put("", "");
		}
		// /To Location MapFill
		HashMap<String, String> mapToLocation = new HashMap<String, String>();

		List list = funLoadToLocation(logInProperty, request);
		if (list.size() > 0) {
			for (int cnt = 0; cnt < list.size(); cnt++) {
				Object[] obj = (Object[]) list.get(cnt);
				mapToLocation.put(obj[0].toString(), obj[1].toString());
			}

		}

		if (mapToLocation.isEmpty()) {
			mapToLocation.put("", "");
		}
		mapToLocation = clsGlobalFunctions.funSortByValues(mapToLocation);
		if (hmFromLoc.isEmpty()) {
			hmFromLoc.put("", "");
		}

		if (mapProperties.isEmpty()) {
			mapProperties.put("", "");
		}
		ob.addObject("propertyList", mapProperties);
		ob.addObject("logInProperty", logInProperty);
		ob.addObject("listToLocation", mapToLocation);
		ob.addObject("listFromLocc", hmFromLoc);
		// ob.addObject("moduleList",moduleList);
		return ob;
	}

	@RequestMapping(value = "/loadByLocation", method = RequestMethod.GET)
	private @ResponseBody List funLoadByLocation(@RequestParam("propCode") String propCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strLocCode,a.strLocName from tbllocationmaster a where a.strPropertyCode='" + propCode + "' and a.strType='Cost Center'  and a.strClientCode='" + clientCode + "' ";
		List listLoc = objGlobalService.funGetList(sql, "sql");
		return listLoc;
	}

	@RequestMapping(value = "/loadToLocation", method = RequestMethod.GET)
	private @ResponseBody List funLoadToLocation(@RequestParam("propCode") String propCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select a.strLocCode,a.strLocName from tbllocationmaster a where a.strPropertyCode<>'" + propCode + "' and   a.strType='Stores'  and a.strClientCode='" + clientCode + "' ";
		List listLoc = objGlobalService.funGetList(sql, "sql");
		return listLoc;
	}

	@RequestMapping(value = "/saveLinkLocation", method = RequestMethod.POST)
	public String funAddUpdate(@ModelAttribute("command") @Valid clsLinkLocToOtherPropLocBean objBean, BindingResult result, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		String userCode = request.getSession().getAttribute("usercode").toString();
		String startDate = request.getSession().getAttribute("startDate").toString();
		String propCode = request.getSession().getAttribute("propertyCode").toString();
		Double stock, weightedAvg, weightedStk, weigthedvalue = 0.00;

		objLinkLoctoOtherPropLocService.funDeleteData(objBean.getStrPropertyCode(), clientCode);
		for (clsLinkLoctoOtherPropLocModel objModel : objBean.getListLinkLocationModel()) {
			if (!(objModel.getStrPropertyCode() == null)) {
				objModel.setDtCreatedDate(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrUserModified(userCode);
				objModel.setStrUserCreated(userCode);
				objModel.setDtLastModified(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
				objModel.setStrClientCode(clientCode);
				objLinkLoctoOtherPropLocService.funAddUpdate(objModel);
			}
		}

		return ("redirect:/frmLinkLocToOtherPropLoc.html?saddr=" + urlHits);

	}

	@RequestMapping(value = "/loadLinkLocation", method = RequestMethod.GET)
	private @ResponseBody List funLoadLinkLocation(@RequestParam("propCode") String propCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		return objLinkLoctoOtherPropLocService.funLoadData(propCode, clientCode);

	}

	@RequestMapping(value = "/loadPropByLocAndToLocName", method = RequestMethod.GET)
	public @ResponseBody String fun(@RequestParam("propCode") String propCode, @RequestParam("byLocCode") String byLocCode, @RequestParam("toLocCode") String toLocCode, HttpServletRequest req) {
		String data = "";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String sql = "select strPropertyName from tblpropertymaster where strPropertyCode='" + propCode + "' and strClientCode='" + clientCode + "' ";
		List listProp = objGlobalService.funGetList(sql, "sql");
		if (listProp.size() > 0) {
			data = listProp.get(0).toString();
		}

		sql = "select strLocName from tbllocationmaster where strLocCode='" + byLocCode + "' and strClientCode='" + clientCode + "' ";
		List listLoc = objGlobalService.funGetList(sql, "sql");
		if (listLoc.size() > 0) {
			data = data + "#" + listLoc.get(0).toString();
		}

		sql = "select strLocName from tbllocationmaster where strLocCode='" + toLocCode + "' and strClientCode='" + clientCode + "' ";
		List listToLocCode = objGlobalService.funGetList(sql, "sql");
		if (listToLocCode.size() > 0) {
			data = data + "#" + listToLocCode.get(0).toString();
		}
		return data;
	}

}
