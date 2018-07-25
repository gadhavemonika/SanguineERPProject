package com.sanguine.crm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.model.clsLocationMasterModel;
import com.sanguine.model.clsProductMasterModel;
import com.sanguine.service.clsLocationMasterService;
import com.sanguine.service.clsProductMasterService;
import com.sanguine.util.clsReportBean;

@Controller
public class clsStockLedgerReportController {
	@Autowired
	clsLocationMasterService objLocationMasterService;

	@Autowired
	private clsProductMasterService objProductMasterService;

	@RequestMapping(value = "/frmStockLedgerReportCRM", method = RequestMethod.GET)
	public ModelAndView funIssueListingIndigeous(Map<String, Object> model, HttpServletRequest request) {

		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmStockLedgerReportCRM_1", "command", new clsReportBean());
		} else {
			return new ModelAndView("frmStockLedgerReportCRM", "command", new clsReportBean());
		}

	}

	@RequestMapping(value = "/loadLocationFromCRM", method = RequestMethod.GET)
	public @ResponseBody clsLocationMasterModel funAssignFieldsLocation(@RequestParam("locCode") String locCode, HttpServletRequest request) {
		String clientCode = request.getSession().getAttribute("clientCode").toString();
		clsLocationMasterModel objLocationMasterModel = objLocationMasterService.funGetObject(locCode, clientCode);
		if (null == objLocationMasterModel) {
			objLocationMasterModel = new clsLocationMasterModel();
			objLocationMasterModel.setStrLocCode("Invalid Code");
		}
		return objLocationMasterModel;
	}

	@RequestMapping(value = "/loadProductMasterSockLedger", method = RequestMethod.GET)
	public @ResponseBody clsProductMasterModel funAssignFields(@RequestParam("prodCode") String prodCode, HttpServletRequest req, HttpServletResponse response) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsProductMasterModel objModel = objProductMasterService.funGetObject(prodCode, clientCode);
		if (null == objModel) {
			clsProductMasterModel objModel1 = new clsProductMasterModel();
			objModel1.setStrProdCode("Invalid Code");
			return objModel1;
		} else {
			return objModel;
		}
	}

}
