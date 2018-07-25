package com.sanguine.webpms.service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsChargePostingBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsChargePostingHdModel;

public interface clsChargePostingService {

	public clsChargePostingHdModel funPrepareChargePostingModel(clsChargePostingBean objBean, String userCode, String clientCode);

}