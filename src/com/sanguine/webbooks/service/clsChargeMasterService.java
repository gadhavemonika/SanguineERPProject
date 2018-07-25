package com.sanguine.webbooks.service;

import java.util.List;

import com.sanguine.webbooks.model.clsChargeMasterModel;

public interface clsChargeMasterService {

	public void funAddUpdateChargeMaster(clsChargeMasterModel objMaster);

	public List funGetChargeMaster(String docCode, String clientCode);

	public List funGetDebtoMemberList(String sqlQuery);

	public List<clsChargeMasterModel> funGetAllChargesData(String clientCode, String propertyCode);

}
