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

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.bean.clsRoomTypeMasterBean;
import com.sanguine.webpms.dao.clsBathTypeMasterDao;
import com.sanguine.webpms.dao.clsRoomTypeMasterDao;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;
import com.sanguine.webpms.service.clsBathTypeMasterService;
import com.sanguine.webpms.service.clsRoomTypeMasterService;

@Controller
public class clsRoomTypeMasterController {
	@Autowired
	private clsRoomTypeMasterService objRoomTypeMasterService;

	@Autowired
	private clsRoomTypeMasterDao objRoomTypeMasterDao;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	// Open RoomTypeMaster
	@RequestMapping(value = "/frmRoomTypeMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		if (urlHits.equalsIgnoreCase("1")) {

			return new ModelAndView("frmRoomTypeMaster", "command", new clsRoomTypeMasterBean());
		} else {
			return new ModelAndView("frmRoomTypeMaster_1", "command", new clsRoomTypeMasterBean());
		}
	}

	@RequestMapping(value = "/saveRoomTypeMaster", method = RequestMethod.GET)
	public ModelAndView funAddUpdateRoomTypeMaster(@ModelAttribute("command") @Valid clsRoomTypeMasterBean objRoomTypeMasterBean, BindingResult result, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		String userCode = req.getSession().getAttribute("usercode").toString();
		clsRoomTypeMasterModel objRoomTypeMasterModel = objRoomTypeMasterService.funPrepareRoomTypeModel(objRoomTypeMasterBean, clientCode, userCode);
		objRoomTypeMasterDao.funAddUpdateRoomMaster(objRoomTypeMasterModel);

		req.getSession().setAttribute("success", true);
		req.getSession().setAttribute("successMessage", "RoomType Code : ".concat(objRoomTypeMasterModel.getStrRoomTypeCode()));

		return new ModelAndView("redirect:/frmRoomTypeMaster.html");

	}

	@RequestMapping(value = "/loadRoomTypeMasterData", method = RequestMethod.GET)
	public @ResponseBody clsRoomTypeMasterModel funFetchRoomTypeMasterData(@RequestParam("roomCode") String roomCode, HttpServletRequest req) {
		clsRoomTypeMasterModel objRoomTypeMasterModel = null;

		String clientCode = req.getSession().getAttribute("clientCode").toString();
		List listRoomData = objRoomTypeMasterDao.funGetRoomTypeMaster(roomCode, clientCode);
		objRoomTypeMasterModel = (clsRoomTypeMasterModel) listRoomData.get(0);
		return objRoomTypeMasterModel;
	}

}
