package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.model.clsPlanMasterModel;

public interface clsPlanMasterService {
	public clsPlanMasterModel funPreparePlanModel(clsPlanMasterBean objPlanMasterBean, String clientCode, String userCode);

}
