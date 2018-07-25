package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseRecipeMasterDao;
import com.sanguine.excise.model.clsExciseRecipeMasterDtlModel;
import com.sanguine.excise.model.clsExciseRecipeMasterHdModel;

@SuppressWarnings("rawtypes")
@Service("objclsExciseRecipeMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExciseRecipeMasterServiceImpl implements clsExciseRecipeMasterService {
	@Autowired
	private clsExciseRecipeMasterDao objclsExciseReciperMasterDao;

	@Override
	public boolean funAddUpdateRecipeMaster(clsExciseRecipeMasterHdModel objMaster) {
		return objclsExciseReciperMasterDao.funAddUpdateRecipeMaster(objMaster);
	}

	@Override
	public boolean funAddUpdateRecipeDtl(clsExciseRecipeMasterDtlModel objRecipeDtl) {
		return objclsExciseReciperMasterDao.funAddUpdateRecipeDtl(objRecipeDtl);
	}

	@Override
	public List funGetList(String clientCode) {
		return objclsExciseReciperMasterDao.funGetList(clientCode);
	}

	@Override
	public List funGetObject(String RecipeCode, String clientCode) {
		return objclsExciseReciperMasterDao.funGetObject(RecipeCode, clientCode);
	}

	@Override
	public List funGetRecipeDtlList(String RecipeCode, String clientCode) {
		return objclsExciseReciperMasterDao.funGetRecipeDtlList(RecipeCode, clientCode);
	}

	@Override
	public void funDeleteDtl(String RecipeCode, String clientCode) {
		objclsExciseReciperMasterDao.funDeleteDtl(RecipeCode, clientCode);
	}

}
