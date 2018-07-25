package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBaggageMasterBean;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;

public interface clsBaggageMasterService {
	public clsBaggageMasterModel funPrepareBaggageModel(clsBaggageMasterBean objBaggageMasterBean, String clientCode, String userCode);
}
