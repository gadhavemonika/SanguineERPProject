package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBathTypeMasterBean;
import com.sanguine.webpms.bean.clsBusinessSourceMasterBean;
import com.sanguine.webpms.model.clsBathTypeMasterModel;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;

public interface clsBusinessSourceMasterService {
	public clsBusinessSourceMasterModel funPrepareBusinessModel(clsBusinessSourceMasterBean objBusinessMasterBean, String clientCode, String userCode);
}
