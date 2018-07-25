package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsFloorMasterBean;
import com.sanguine.webpms.model.clsFloorMasterModel;

public interface clsFloorMasterService {
	
	public clsFloorMasterModel funPrepareFloorModel(clsFloorMasterBean objFloorBean, String clientCode, String userCode);

}
