package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsSizeMasterDao;
import com.sanguine.excise.model.clsSizeMasterModel;

@Service("clsSizeMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsSizeMasterServiceImpl implements clsSizeMasterService {
	@Autowired
	private clsSizeMasterDao objSizeMasterDao;

	@Override
	public boolean funAddUpdateSizeMaster(clsSizeMasterModel objMaster) {
		return objSizeMasterDao.funAddUpdateSizeMaster(objMaster);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetSizeMasterList(String clientCode) {
		return objSizeMasterDao.funGetSizeMasterList(clientCode);
	}

	@Override
	public clsSizeMasterModel funGetObject(String code, String clientCode) {
		return objSizeMasterDao.funGetObject(code, clientCode);
	}

}
