package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;

public interface clsDepartmentMasterService {

	public clsDepartmentMasterModel funPrepareDeptModel(clsDepartmentMasterBean objDeptMasterBean, String clientCode, String userCode);
}
