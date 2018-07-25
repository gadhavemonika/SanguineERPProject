package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsBudgetMasterHdModel;

public interface clsBudgetMasterDao {

	public void funAddUpdate(clsBudgetMasterHdModel objBudgetMasterModel);

	public clsBudgetMasterHdModel funGetBudget(String budgetCode, String clientCode);

	public void funDeleteBudgetDtl(String budgetCode, String clientCode);

	public List funGetMasterData(String propCode, String ClientCode, String year, String strGroupCode);

}
