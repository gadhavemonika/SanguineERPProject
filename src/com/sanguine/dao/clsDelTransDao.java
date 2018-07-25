package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsDeleteTransModel;
import com.sanguine.model.clsGroupMasterModel;

public interface clsDelTransDao {
	public void funInsertRecord(clsDeleteTransModel objModel);

	public void funDeleteRecord(String sql, String queryType);
}
