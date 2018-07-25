package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.bean.clsGuestMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsGuestMasterHdModel;

public interface clsGuestMasterService {
	public clsGuestMasterHdModel funPrepareGuestModel(clsGuestMasterBean objGuestMasterBean, String clientCode, String userCode);

	public List funGetGuestMaster(String guestCode, String clientCode);
}
