package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;

public interface clsSundryDebtorMasterService {

	public void funAddUpdateSundryDebtorMaster(clsSundryDebtorMasterModel objMaster);

	public clsSundryDebtorMasterModel funGetSundryDebtorMaster(String docCode, String clientCode);

	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode);

}
