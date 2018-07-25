package com.sanguine.webbooks.apgl.service;

import java.util.List;

import com.sanguine.webbooks.apgl.model.clsAPGLBudgetModel;

public interface clsAPGLBudgetMasterService {

	public List funGetBudgetTableData(String strMonth, String strYear, String strClientCode);

	public void funSaveBudgetTableData(clsAPGLBudgetModel objBudgetModel);
}
