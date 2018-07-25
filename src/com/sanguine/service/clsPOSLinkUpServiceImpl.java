package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPOSLinkUpDao;
import com.sanguine.model.clsPOSLinkUpModel;

@Service("clsPOSLinkUpService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsPOSLinkUpServiceImpl implements clsPOSLinkUpService {
	@Autowired
	private clsPOSLinkUpDao objPOSLinkUpDao;

	@Override
	public void funAddUpdatePOSLinkUp(clsPOSLinkUpModel objMaster) {
		objPOSLinkUpDao.funAddUpdatePOSLinkUp(objMaster);
	}

	@Override
	public clsPOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode) {
		return objPOSLinkUpDao.funGetPOSLinkUp(docCode, clientCode);
	}

	@Override
	public int funExecute(String sql) {
		return objPOSLinkUpDao.funExecute(sql);
	}

}
