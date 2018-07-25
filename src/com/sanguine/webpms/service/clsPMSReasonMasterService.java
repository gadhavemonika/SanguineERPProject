package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsPMSReasonMasterBean;
import com.sanguine.webpms.bean.clsRoomTypeMasterBean;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

public interface clsPMSReasonMasterService {

	public clsPMSReasonMasterModel funPrepareReasonModel(clsPMSReasonMasterBean objReasonMasterBean, String clientCode, String userCode);

}
