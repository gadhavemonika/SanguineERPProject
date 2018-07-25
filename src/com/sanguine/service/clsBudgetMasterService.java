package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsBudgetMasterHdModel;
import com.sanguine.model.clsGroupMasterModel;

public interface clsBudgetMasterService {

	public void funAddUpdate(clsBudgetMasterHdModel objBudgetMasterModel);

	public clsBudgetMasterHdModel funGetBudget(String budgetCode, String clientCode);

	public void funDeleteBudgetDtl(String budgetCode, String clientCode);

	public List funGetMasterData(String propCode, String ClientCode, String year, String strGroupCode);

}
