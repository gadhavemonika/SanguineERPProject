package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBusinessSourceMasterBean;
import com.sanguine.webpms.bean.clsExtraBedMasterBean;
import com.sanguine.webpms.model.clsBusinessSourceMasterModel;
import com.sanguine.webpms.model.clsExtraBedMasterModel;

public interface clsExtraBedMasterService {
	public clsExtraBedMasterModel funPrepareExtraBedModel(clsExtraBedMasterBean objExtraBedMasterBean, String clientCode, String userCode);
}
