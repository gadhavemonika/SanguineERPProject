package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsACGroupMasterDao;
import com.sanguine.webbooks.model.clsACGroupMasterModel;

@Service("clsACGroupMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsACGroupMasterServiceImpl implements clsACGroupMasterService {
	@Autowired
	private clsACGroupMasterDao objACGroupMasterDao;

	@Override
	public void funAddUpdateACGroupMaster(clsACGroupMasterModel objMaster) {
		objACGroupMasterDao.funAddUpdateACGroupMaster(objMaster);
	}

	@Override
	public clsACGroupMasterModel funGetACGroupMaster(String docCode, String clientCode) {
		return objACGroupMasterDao.funGetACGroupMaster(docCode, clientCode);
	}

	@Override
	public List<String> funGetGroupCategory(String clientCode) {
		return objACGroupMasterDao.funGetGroupCategory(clientCode);
	}
}
