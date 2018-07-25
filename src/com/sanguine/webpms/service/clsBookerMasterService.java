package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBookerMasterBean;
import com.sanguine.webpms.model.clsBookerMasterHdModel;

public interface clsBookerMasterService {

	public void funAddUpdateBookerMaster(clsBookerMasterHdModel objMaster);

	public clsBookerMasterHdModel funGetBookerMaster(String docCode, String clientCode);

	public clsBookerMasterHdModel funPrepareModel(clsBookerMasterBean objBean, String userCode, String clientCode);

}
