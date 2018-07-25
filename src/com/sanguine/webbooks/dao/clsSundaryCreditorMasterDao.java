package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;

public interface clsSundaryCreditorMasterDao {

	public void funAddUpdateSundryCreditorMaster(clsSundaryCreditorMasterModel objMaster);

	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode);

}
