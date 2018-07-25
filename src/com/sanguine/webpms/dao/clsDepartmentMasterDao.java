package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsDepartmentMasterModel;

public interface clsDepartmentMasterDao {

	public void funAddUpdateDeptMaster(clsDepartmentMasterModel objDeptMasterModel);

	public List funGetDepartmentMaster(String deptCode, String clientCode);
}
