package com.sanguine.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsMISBean;
import com.sanguine.bean.clsRequisitionBean;
import com.sanguine.webclub.model.clsWebClubCompanyTypeMasterModel;

@Controller
public class clsWebStockHelpController {

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/frmWebStockHelpMaterialIssueSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelopMIS(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		
		clsMISBean bean = new clsMISBean();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMIS_1", "command", bean);
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmMIS", "command", bean);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpMaterialRequisition", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpMaterialRequisition(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialRequisition");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialRequisition");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpMaterialReturn", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpMaterialReturn(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialReturn");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialReturn");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpOpeningStock", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpOpeningStock(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpOpeningStock");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpOpeningStock");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpPhysicalStockPosting", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPhysicalStockPosting(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPhysicalStockPosting");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPhysicalStockPosting");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpPurchaseIndent", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPurchaseIndent(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseIndent");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseIndent");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpPurchaseOrder", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPurchaseOrder(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseOrder");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseOrder");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpStockAdjustment", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpStockAdjustment(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpStockAdjustment");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpStockAdjustment");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpStockTransfer", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpStocktransfer(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpStockTransfer");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpStockTransfer");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpBillPassing", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpBillPassing(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpBillPassing");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpBillPassing");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpGRN", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpGRN(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpGRN");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpGRN");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpGRNSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpGRNSlip(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpGRNSlip");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpGRNSlip");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpMaterialReturnSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpMaterialReturnSlip(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialReturnSlip");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMaterialReturnSlip");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpMealPlanning", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpMealPlanning(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMealPlanning");
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpMealPlanning");
		} else {

			return null;
		}

	}

	@RequestMapping(value = "/frmWebStockHelpProductList", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpProductList(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpProductList");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpProductList");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpProductWiseSupplierWise", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpProductWiseSupplierWise(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpProductWiseSupplierWise");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpProductWiseSupplierWise");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpPurchaseOrderSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPurchaseOrderSlip(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseOrderSlip");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpPurchaseOrderSlip");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpPurchaseReturn", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPurchaseReturn(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseReturn");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpPurchaseReturn");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpPurchaseReturnSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpPurchaseReturnSlip(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpPurchaseReturnSlip");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpPurchaseReturnSlip");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpRateContract", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpRateContract(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpRateContract");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpRateContract");
			} else {
				return null;
			}

		}
	}

	@RequestMapping(value = "/frmWebStockHelpRequisitionSlip", method = RequestMethod.GET)
	public ModelAndView funOpenFormHelpRequisitionSlip(Map<String, Object> model, HttpServletRequest req) {
		String urlHits = "1";
		String ClientCode = req.getSession().getAttribute("clientCode").toString();
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";

		}
		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmWebStockHelpRequisitionSlip");
		} else {
			if ("1".equalsIgnoreCase(urlHits)) {
				return new ModelAndView("frmWebStockHelpRequisitionSlip");
			} else {
				return null;
			}

		}
	}

	// Assign filed function to set data onto form for edit transaction.
	@RequestMapping(value = "/getFormName", method = RequestMethod.GET)
	public @ResponseBody String funAssignFields(HttpServletRequest req) {
		String froName = req.getSession().getAttribute("formName").toString();

		return froName;

	}

}
