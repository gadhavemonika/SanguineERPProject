package com.sanguine.webbooks.dao;

import java.util.List;

import com.sanguine.webbooks.model.clsACGroupMasterModel;

public interface clsACGroupMasterDao {

	public void funAddUpdateACGroupMaster(clsACGroupMasterModel objMaster);

	public clsACGroupMasterModel funGetACGroupMaster(String docCode, String clientCode);

	public List<String> funGetGroupCategory(String clientCode);

}
