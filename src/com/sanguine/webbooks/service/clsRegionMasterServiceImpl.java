package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsRegionMasterDao;
import com.sanguine.webbooks.model.clsRegionMasterModel;

@Service("clsRegionMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsRegionMasterServiceImpl implements clsRegionMasterService {
	@Autowired
	private clsRegionMasterDao objRegionMasterDao;

	@Override
	public void funAddUpdateRegionMaster(clsRegionMasterModel objMaster) {
		objRegionMasterDao.funAddUpdateRegionMaster(objMaster);
	}

	@Override
	public clsRegionMasterModel funGetRegionMaster(String docCode, String clientCode) {
		return objRegionMasterDao.funGetRegionMaster(docCode, clientCode);
	}

}
