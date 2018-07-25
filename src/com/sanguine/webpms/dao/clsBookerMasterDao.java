package com.sanguine.webpms.dao;

import com.sanguine.webpms.bean.clsBookerMasterBean;
import com.sanguine.webpms.model.clsBookerMasterHdModel;

public interface clsBookerMasterDao {

	public void funAddUpdateBookerMaster(clsBookerMasterHdModel objMaster);

	public clsBookerMasterHdModel funGetBookerMaster(String docCode, String clientCode);

}
