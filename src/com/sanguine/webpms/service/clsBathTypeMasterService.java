package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsBathTypeMasterModel;

public interface clsBathTypeMasterService {
	public clsBathTypeMasterModel funPrepareBathTypeModel(clsBathTypeMasterBean objBathTypeMasterBean, String clientCode, String userCode);
}
