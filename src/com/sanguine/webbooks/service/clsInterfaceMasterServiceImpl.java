package com.sanguine.webbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsInterfaceMasterDao;
import com.sanguine.webbooks.model.clsInterfaceMasterModel;

@Service("clsInterfaceMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsInterfaceMasterServiceImpl implements clsInterfaceMasterService {
	@Autowired
	private clsInterfaceMasterDao objInterfaceMasterDao;

	@Override
	public void funAddUpdateInterfaceMaster(clsInterfaceMasterModel objMaster) {
		objInterfaceMasterDao.funAddUpdateInterfaceMaster(objMaster);
	}

	@Override
	public clsInterfaceMasterModel funGetInterfaceMaster(String docCode, String clientCode) {
		return objInterfaceMasterDao.funGetInterfaceMaster(docCode, clientCode);
	}

}
