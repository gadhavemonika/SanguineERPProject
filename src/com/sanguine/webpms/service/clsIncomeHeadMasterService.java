package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsIncomeHeadMasterBean;
import com.sanguine.webpms.bean.clsPlanMasterBean;
import com.sanguine.webpms.model.clsIncomeHeadMasterModel;
import com.sanguine.webpms.model.clsPlanMasterModel;

public interface clsIncomeHeadMasterService {
	public clsIncomeHeadMasterModel funPrepareIncomeHeadModel(clsIncomeHeadMasterBean objIncomeMasterBean, String clientCode, String userCode);

}
