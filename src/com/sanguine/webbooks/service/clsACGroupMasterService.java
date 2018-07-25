package com.sanguine.webbooks.service;

import java.util.List;

import com.sanguine.webbooks.model.clsACGroupMasterModel;

public interface clsACGroupMasterService {

	public void funAddUpdateACGroupMaster(clsACGroupMasterModel objMaster);

	public clsACGroupMasterModel funGetACGroupMaster(String docCode, String clientCode);

	public List<String> funGetGroupCategory(String clientCode);

}
