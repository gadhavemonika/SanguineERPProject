package com.sanguine.webbooks.apgl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.apgl.dao.clsAPGLBudgetMasterDao;
import com.sanguine.webbooks.apgl.model.clsAPGLBudgetModel;

@Service("clsAPGLBudgetMasterServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsAPGLBudgetMasterServiceImpl implements clsAPGLBudgetMasterService {
	@Autowired
	clsAPGLBudgetMasterDao objDao;

	public List funGetBudgetTableData(String strMonth, String strYear, String strClientCode) {
		return objDao.funGetBudgetTableData(strMonth, strYear, strClientCode);
	}

	public void funSaveBudgetTableData(clsAPGLBudgetModel objBudgetModel) {
		objDao.funSaveBudgetTableData(objBudgetModel);
	}
}
