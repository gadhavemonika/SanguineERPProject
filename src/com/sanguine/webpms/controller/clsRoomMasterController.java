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
import com.sanguine.webpms.bean.clsRoomMasterBean;
import com.sanguine.webpms.model.clsRoomMasterModel;
import com.sanguine.webpms.model.clsRoomMasterModel_ID;
import com.sanguine.webpms.service.clsRoomMasterService;

@Controller
public class clsRoomMasterController {

	@Autowired
	private clsRoomMasterService objRoomMasterService;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsGlobalFunctions objGlobal;

	// Open RoomMaster
	@RequestMapping(value = "/frmRoomMaster", method = RequestMethod.GET)
	public ModelAndView funOpenForm(Map<String, Object> model, HttpServletRequest request) {
		String urlHits = "1";
		try {
			urlHits = request.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		model.put("urlHits", urlHits);

		String clientCode = request.getSession().getAttribute("clientCode").toString();
		List<String> listRoomType = objRoomMasterService.funGetRoomTypeList(clientCode);
		model.put("listRoomType", listRoomType);

		if (urlHits.equalsIgnoreCase("1")) {
			return new ModelAndView("frmRoomMaster", "command", new clsRoomMasterBean());
		} else {
			return new ModelAndView("frmRoomMaster_1", "command", new clsRoomMasterBean());
		}
	}

	// Load account Master Data On Form
	@RequestMapping(value = "/loadRoomMasterData", method = RequestMethod.GET)
	public @ResponseBody clsRoomMasterModel funLoadMasterData(@RequestParam("roomCode") String roomCode, HttpServletRequest req) {
		String clientCode = req.getSession().getAttribute("clientCode").toString();
		clsRoomMasterModel objModel = objRoomMasterService.funGetRoomMaster(roomCode, clientCode);
		if (objModel == null) {
			objModel = new clsRoomMasterModel();
			objModel.setStrRoomCode("Invalid Code");
		}

		return objModel;
	}

	// Save or Update RoomMaster
	@RequestMapping(value = "/saveRoomMaster", method = RequestMethod.POST)
	public ModelAndView funAddUpdate(@ModelAttribute("command") @Valid clsRoomMasterBean objBean, BindingResult result, HttpServletRequest req) {
		String urlHits = "1";
		try {
			urlHits = req.getParameter("saddr").toString();
		} catch (NullPointerException e) {
			urlHits = "1";
		}
		if (!result.hasErrors()) {
			String clientCode = req.getSession().getAttribute("clientCode").toString();
			String userCode = req.getSession().getAttribute("usercode").toString();
			String propertyCode = req.getSession().getAttribute("propertyCode").toString();
			clsRoomMasterModel objModel = funPrepareModel(objBean, userCode, clientCode, propertyCode);
			objRoomMasterService.funAddUpdateRoomMaster(objModel);

			req.getSession().setAttribute("success", true);
			req.getSession().setAttribute("successMessage", "Room Code : ".concat(objModel.getStrRoomCode()));

			return new ModelAndView("redirect:/frmRoomMaster.html?saddr=" + urlHits);
		} else {
			return new ModelAndView("redirect:/frmRoomMaster.html?saddr=" + urlHits);
		}
	}

	// Convert bean to model function
	private clsRoomMasterModel funPrepareModel(clsRoomMasterBean objBean, String userCode, String clientCode, String propertyCode) {
		long lastNo = 0;
		clsRoomMasterModel objModel;
		if (objBean.getStrRoomCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblroom", "RoomMaster", "strRoomCode", clientCode);
			String roomCode = "RC" + String.format("%06d", lastNo);
			objModel = new clsRoomMasterModel(new clsRoomMasterModel_ID(roomCode, clientCode));
		} else {
			objModel = new clsRoomMasterModel(new clsRoomMasterModel_ID(objBean.getStrRoomCode(), clientCode));
		}
		objModel.setStrRoomDesc(objBean.getStrRoomDesc());
		objModel.setStrRoomTypeCode(objBean.getStrRoomType());
		objModel.setStrFloorCode(objGlobal.funIfNull(objBean.getStrFloorCode(), "", objBean.getStrFloorCode()));
		objModel.setStrBedType(objGlobal.funIfNull(objBean.getStrBedType(), "", objBean.getStrBedType()));
		objModel.setStrFurniture(objGlobal.funIfNull(objBean.getStrFurniture(), "", objBean.getStrFurniture()));
		objModel.setStrExtraBedCode(objGlobal.funIfNull(objBean.getStrExtraBed(), "", objBean.getStrExtraBed()));
		objModel.setStrUpholstery(objGlobal.funIfNull(objBean.getStrUpholstery(), "", objBean.getStrUpholstery()));
		objModel.setStrLocation(objGlobal.funIfNull(objBean.getStrLocation(), "", objBean.getStrLocation()));
		objModel.setStrBathTypeCode(objGlobal.funIfNull(objBean.getStrBathTypeCode(), "", objBean.getStrBathTypeCode()));
		objModel.setStrColorScheme(objGlobal.funIfNull(objBean.getStrColourScheme(), "", objBean.getStrColourScheme()));
		objModel.setStrPolishType(objGlobal.funIfNull(objBean.getStrPolishType(), "", objBean.getStrPolishType()));
		objModel.setStrGuestAmenities(objGlobal.funIfNull(objBean.getStrGuestAmenities(), "", objBean.getStrGuestAmenities()));
		objModel.setStrInterConnectRooms(objGlobal.funIfNull(objBean.getStrInterConnectRooms(), "", objBean.getStrInterConnectRooms()));
		objModel.setStrProvisionForSmokingYN(objBean.getStrProvisionForSmokingYN());
		objModel.setStrDeactiveYN(objBean.getStrDeactiveYN());
		objModel.setStrUserCreated(userCode);
		objModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrUserEdited(userCode);
		objModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objModel.setStrStatus("Free");
		objModel.setStrAccountCode(objBean.getStrAccountCode());

		return objModel;
	}

}
