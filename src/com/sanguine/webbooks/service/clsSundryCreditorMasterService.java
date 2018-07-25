package com.sanguine.webbooks.service;

import java.util.List;

import com.sanguine.webbooks.model.clsSundaryCreditorMasterModel;
import com.sanguine.webbooks.model.clsSundryDebtorMasterModel;

public interface clsSundryCreditorMasterService {

	public void funAddUpdateSundryCreditorMaster(clsSundaryCreditorMasterModel objMaster);

	public clsSundaryCreditorMasterModel funGetSundryCreditorMaster(String docCode, String clientCode);

}
