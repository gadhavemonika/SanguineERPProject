package com.sanguine.webbooks.apgl.dao;

import java.util.List;

import com.sanguine.webbooks.apgl.model.clsAPGLBudgetModel;

public interface clsAPGLBudgetMasterDao {

	public List funGetBudgetTableData(String strMonth, String strYear, String strClientCode);

	public void funSaveBudgetTableData(clsAPGLBudgetModel objBudgetModel);

}
