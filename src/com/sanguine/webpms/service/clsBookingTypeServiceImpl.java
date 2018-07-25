package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsBookingTypeBean;
import com.sanguine.webpms.dao.clsBookingTypeDao;
import com.sanguine.webpms.model.clsAgentCommisionHdModel;
import com.sanguine.webpms.model.clsBookingTypeHdModel;

@Service("clsBookingTypeService")
public class clsBookingTypeServiceImpl implements clsBookingTypeService {

	@Autowired
	private clsBookingTypeDao objBookingTypeDao;

	@Autowired
	private clsGlobalFunctions objGlobal = null;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Override
	public void funAddUpdateBookingType(clsBookingTypeHdModel objMaster) {
		objBookingTypeDao.funAddUpdateBookingType(objMaster);
	}

	@Override
	public clsBookingTypeHdModel funGetBookingType(String docCode, String clientCode) {
		return objBookingTypeDao.funGetBookingType(docCode, clientCode);
	}

	// Convert bean to model function
	public clsBookingTypeHdModel funPrepareModel(clsBookingTypeBean objBean, String userCode, String clientCode) {
		objGlobal = new clsGlobalFunctions();
		long lastNo = 0;
		clsBookingTypeHdModel objBookingTypeHdModel = new clsBookingTypeHdModel();

		if (objBean.getStrBookingTypeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblbookingtype", "BookingType", "strBookingTypeCode", clientCode);
			String bookingTypeCode = "BT" + String.format("%06d", lastNo);
			objBookingTypeHdModel.setStrBookingTypeCode(bookingTypeCode);
			objBookingTypeHdModel.setStrUserCreated(userCode);
			objBookingTypeHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objBookingTypeHdModel.setStrBookingTypeCode(objBean.getStrBookingTypeCode());
			objBookingTypeHdModel.setStrUserCreated(userCode);
			objBookingTypeHdModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		}
		objBookingTypeHdModel.setStrBookingTypeDesc(objBean.getStrBookingTypeDesc());
		objBookingTypeHdModel.setStrUserEdited(userCode);
		objBookingTypeHdModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objBookingTypeHdModel.setStrClientCode(clientCode);
		return objBookingTypeHdModel;
	}
}
