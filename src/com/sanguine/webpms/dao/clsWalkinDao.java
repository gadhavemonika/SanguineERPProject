package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;

public interface clsWalkinDao {

	public void funAddUpdateWalkinHd(clsWalkinHdModel objHdModel);

	public void funAddUpdateWalkinDtl(clsWalkinDtl objDtlModel);

	public List funGetWalkinDataDtl(String walkinNo, String clientCode);
	
	public List<clsRoomPackageDtl> funGetWalkinIncomeList(String walkInNo, String clientCode) ;

}
