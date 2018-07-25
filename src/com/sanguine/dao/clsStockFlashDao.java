package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsStockFlashModel;

public interface clsStockFlashDao {
	public List funGetStockFlashData(String sql, String clientCode, String userCode);
}
