package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsBudgetMasterDao;
import com.sanguine.model.clsBudgetMasterHdModel;
import com.sanguine.model.clsGroupMasterModel;

@Repository("clsBudgetMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsBudgetMasterServiceImpl implements clsBudgetMasterService {

	@Autowired
	private clsBudgetMasterDao objBudgetMasterDao;

	public void funAddUpdate(clsBudgetMasterHdModel objBudgetMasterModel) {
		objBudgetMasterDao.funAddUpdate(objBudgetMasterModel);
	}

	public clsBudgetMasterHdModel funGetBudget(String budgetCode, String clientCode) {
		return objBudgetMasterDao.funGetBudget(budgetCode, clientCode);
	}

	public void funDeleteBudgetDtl(String budgetCode, String clientCode) {

		objBudgetMasterDao.funDeleteBudgetDtl(budgetCode, clientCode);

	}

	public List funGetMasterData(String propCode, String ClientCode, String year, String strGroupCode) {
		return objBudgetMasterDao.funGetMasterData(propCode, ClientCode, year, strGroupCode);
	}
}
