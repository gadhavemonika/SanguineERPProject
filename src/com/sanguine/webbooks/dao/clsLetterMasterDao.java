package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsLetterMasterModel;

public interface clsLetterMasterDao {

	public void funAddUpdateLetterMaster(clsLetterMasterModel objMaster);

	public clsLetterMasterModel funGetLetterMaster(String docCode, String clientCode);

}
