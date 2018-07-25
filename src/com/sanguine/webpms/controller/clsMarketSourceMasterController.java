package com.sanguine.webpms.controller;

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

import com.sanguine.webpms.bean.clsMarketSourceMasterBean;
import com.sanguine.webpms.dao.clsMarketSourceMasterDao;
import com.sanguine.webpms.model.clsMarketSourceMasterModel;
import com.sanguine.webpms.service.clsMarketSourceMasterService;


@Controller
public class clsMarketSourceMasterController {
	
	
	@Autowired
	clsMarketSourceMasterService objMarketSourceMasterService;
	@Autowired
	clsMarketSourceMasterDao objMarketSourceMasterDao;
	
//Open Market Source Master
	@RequestMapping(value = "/frmMarketSource", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {

			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmMarketSource", "command", new clsMarketSourceMasterBean());
		} else {
			return new ModelAndView("frmMarketSource_1", "command", new clsMarketSourceMasterBean());
		}
	}
	
	@RequestMapping(value = "/saveMarketSourceMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateMarketSourceMaster(@ModelAttribute("command") @Valid clsMarketSourceMasterBean objMarketMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsMarketSourceMasterModel objMarketSourceMasterModel = objMarketSourceMasterService.funPrepareMarketModel(objMarketMasterBean, clientCode, userCode);
		objMarketSourceMasterDao.funAddUpdateMarketMaster(objMarketSourceMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Market Code : ".concat(objMarketSourceMasterModel.getStrMarketSourceCode()));

		return new ModelAndView("redirect:/frmMarketSource.html");

	}
	
	@RequestMapping(value = "/loadMarketMasterData", method = RequestMethod.GET)
	public @ResponseBody clsMarketSourceMasterModel funFetchMarketMasterData(@RequestParam("marketCode") String MarketCode, HttpServletRequest req) {
		clsMarketSourceMasterModel objMarketSourceMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listMarketData = objMarketSourceMasterDao.funGetMarketMaster(MarketCode, clientCode);
		if (listMarketData.size() > 0) {
			objMarketSourceMasterModel = (clsMarketSourceMasterModel) listMarketData.get(0);
		} else {
			objMarketSourceMasterModel = new clsMarketSourceMasterModel();
		}
		return objMarketSourceMasterModel;
	}


}
