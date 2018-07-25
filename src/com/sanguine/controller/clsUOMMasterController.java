package com.sanguine.controller;

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
import org.springframework.web.servlet.ModelAndView;

import com.sanguine.bean.clsUOMBean;
import com.sanguine.model.clsUOMModel;
import com.sanguine.service.clsUOMService;

@Controller
public class clsUOMMasterController {
	@Autowired
	private clsUOMService objclsUOMService;

	/**
	 * Open UOM Master Form
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/frmUOMMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if ("2".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmUOMMaster_1", "command", new clsUOMBean());
		} else if ("1".equalsIgnoreCase(urlHits)) {
			return new ModelAndView("frmUOMMaster", "command", new clsUOMBean());
		} else {
			return null;
		}
	}

	/**
	 * Save UOM Master Data
	 * 
	 * @param objBean
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/saveUOMMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsUOMBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			List<String> list = null;
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			clsUOMModel objUom = new clsUOMModel();

			if (objBean.getStrhidUom() == null) {
				objUom.setStrUOMName(objBean.getStrUOMName().toUpperCase());
				objUom.setStrClientCode(clientCode);
				objclsUOMService.funSaveOrUpdateUOM(objUom);

			} else {
				objclsUOMService.funDeleteUOM(clientCode, objBean.getStrhidUom());
				objUom.setStrUOMName(objBean.getStrUOMName().toUpperCase());
				objUom.setStrClientCode(clientCode);
				objclsUOMService.funSaveOrUpdateUOM(objUom);

			}

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "");
			return new ModelAndView("redirect:/frmUOMMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("frmUOMMaster_1", "command", new clsUOMBean());
		}

	}

}
