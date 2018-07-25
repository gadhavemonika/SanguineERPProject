package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsProdSuppMasterModel;

public interface clsProdSuppMasterDao {
	public void funAddUpdate(clsProdSuppMasterModel objModel);

	public List<clsProdSuppMasterModel> funGetList();

	public clsProdSuppMasterModel funGetObject(String code);
}
