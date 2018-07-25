package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsLetterMasterModel;

public interface clsLetterMasterService {

	public void funAddUpdateLetterMaster(clsLetterMasterModel objMaster);

	public clsLetterMasterModel funGetLetterMaster(String docCode, String clientCode);

}
