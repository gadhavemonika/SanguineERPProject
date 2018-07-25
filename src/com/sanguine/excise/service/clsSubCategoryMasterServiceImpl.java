package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsSubCategoryMasterDao;
import com.sanguine.excise.model.clsSubCategoryMasterModel;

@Service("clsSubCategoryMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsSubCategoryMasterServiceImpl implements clsSubCategoryMasterService {
	@Autowired
	private clsSubCategoryMasterDao objSubCategoryMasterDao;

	@Override
	public boolean funAddUpdateSubCategoryMaster(clsSubCategoryMasterModel objMaster) {
		return objSubCategoryMasterDao.funAddUpdateSubCategoryMaster(objMaster);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List funGetSubCategoryMaster(String clientCode) {
		return objSubCategoryMasterDao.funGetSubCategoryMaster(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		return objSubCategoryMasterDao.funGetObject(code, clientCode);
	}

}
