package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsCorporateMasterBean;
import com.sanguine.webpms.model.clsCorporateMasterHdModel;

public interface clsCorporateMasterService {

	public void funAddUpdateCorporateMaster(clsCorporateMasterHdModel objMaster);

	public clsCorporateMasterHdModel funGetCorporateMaster(String docCode, String clientCode);

	public clsCorporateMasterHdModel funPrepareModel(clsCorporateMasterBean objBean, String userCode, String clientCode);

}
