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

import com.sanguine.webpms.bean.clsFloorMasterBean;
import com.sanguine.webpms.dao.clsFloorMasterDao;
import com.sanguine.webpms.model.clsFloorMasterModel;
import com.sanguine.webpms.service.clsFloorMasterService;

@Controller
public class clsFloorMasterController {
	
	@Autowired
	clsFloorMasterService objFloorMasterService;
	
	@Autowired
	clsFloorMasterDao objFloorMasterDao;
	
//Open Market Source Master
	@RequestMapping(value = "/frmFloorMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {

			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmFloorMaster", "command", new clsFloorMasterBean());
		} else {
			return new ModelAndView("frmFloorMaster_1", "command", new clsFloorMasterBean());
		}
	}
	
	@RequestMapping(value = "/saveFloorMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateFloorSourceMaster(@ModelAttribute("command") @Valid clsFloorMasterBean objFloorMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsFloorMasterModel objFloorMasterModel = objFloorMasterService.funPrepareFloorModel(objFloorMasterBean, clientCode, userCode);
		objFloorMasterDao.funAddUpdateFloorMaster(objFloorMasterModel);
		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "Market Code : ".concat(objFloorMasterModel.getStrFloorCode()));

		return new ModelAndView("redirect:/frmFloorMaster.html");

	}
	
	
	@RequestMapping(value = "/loadFloorMasterData", method = RequestMethod.GET)
	public @ResponseBody clsFloorMasterModel funFetchFloorMasterData(@RequestParam("floorCode") String FloorCode, HttpServletRequest req) {
		clsFloorMasterModel objFloorMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listFloorData = objFloorMasterDao.funGetFloorMaster(FloorCode, clientCode);
		if (listFloorData.size() > 0) {
			objFloorMasterModel = (clsFloorMasterModel) listFloorData.get(0);
		} else {
			objFloorMasterModel = new clsFloorMasterModel();
		}
		return objFloorMasterModel;
	}

}
