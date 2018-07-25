package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;

public interface clsSundryDebtorMasterDao {

	public void funAddUpdateSundryDebtorMaster(clsSundryDebtorMasterModel objMaster);

	public clsSundryDebtorMasterModel funGetSundryDebtorMaster(String docCode, String clientCode);

	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode);

}
