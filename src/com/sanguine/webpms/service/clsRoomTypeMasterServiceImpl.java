package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.bean.clsRoomTypeMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

@Service("objRoomTypeMasterService")
public class clsRoomTypeMasterServiceImpl implements clsRoomTypeMasterService {
	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsRoomTypeMasterModel objRoomTypeMasterModel;

	public clsRoomTypeMasterModel funPrepareRoomTypeModel(clsRoomTypeMasterBean objRoomTypeMasterBean, String clientCode, String userCode) {
		objRoomTypeMasterModel = new clsRoomTypeMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objRoomTypeMasterBean.getStrRoomTypeCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblroomtypemaster", "RoomTypeMaster", "strRoomTypeCode", clientCode);
			String roomTypeCode = "RT" + String.format("%06d", lastNo);
			// String deptCode="D0000001";
			objRoomTypeMasterModel.setStrRoomTypeCode(roomTypeCode);
			objRoomTypeMasterModel.setStrUserCreated(userCode);
			objRoomTypeMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objRoomTypeMasterModel.setStrRoomTypeCode(objRoomTypeMasterBean.getStrRoomTypeCode());

		}

		objRoomTypeMasterModel.setStrRoomTypeDesc(objRoomTypeMasterBean.getStrRoomTypeDesc());
		objRoomTypeMasterModel.setDblRoomTerrif(objRoomTypeMasterBean.getDblRoomTerrif());
		objRoomTypeMasterModel.setStrUserEdited(userCode);
		objRoomTypeMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objRoomTypeMasterModel.setStrClientCode(clientCode);

		return objRoomTypeMasterModel;

	}
}
