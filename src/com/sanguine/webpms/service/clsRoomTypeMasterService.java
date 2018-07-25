package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsRoomTypeMasterBean;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

public interface clsRoomTypeMasterService {
	public clsRoomTypeMasterModel funPrepareRoomTypeModel(clsRoomTypeMasterBean objRoomTypeMasterBean, String clientCode, String userCode);
}
