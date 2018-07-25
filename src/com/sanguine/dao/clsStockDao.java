package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsInitialInventoryModel;
import com.sanguine.model.clsOpeningStkDtl;

public interface clsStockDao {
	public void funAddUpdate(clsInitialInventoryModel object);

	public void funAddUpdateDtl(clsOpeningStkDtl object);

	public List<clsInitialInventoryModel> funGetList();

	public clsInitialInventoryModel funGetObject(String code, String clientCode);

	public List funInitialInventoryReport(String clientCode);

	public void funDeleteDtl(String opStkCode, String clientCode);

	public List funGetDtlList(String code, String clientCode);
}
