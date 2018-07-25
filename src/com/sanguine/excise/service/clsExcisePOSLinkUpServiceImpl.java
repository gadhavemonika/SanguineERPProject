package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExcisePOSLinkUpDao;
import com.sanguine.excise.model.clsExcisePOSLinkUpModel;

@Service("objExcisePOSLinkUpService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsExcisePOSLinkUpServiceImpl implements clsExcisePOSLinkUpService {
	@Autowired
	private clsExcisePOSLinkUpDao objExcisePOSLinkUpDao;

	@Override
	public void funAddUpdatePOSLinkUp(clsExcisePOSLinkUpModel objMaster) {
		objExcisePOSLinkUpDao.funAddUpdatePOSLinkUp(objMaster);
	}

	@Override
	public clsExcisePOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode) {
		return objExcisePOSLinkUpDao.funGetPOSLinkUp(docCode, clientCode);
	}

	@Override
	public int funExecute(String sql) {
		return objExcisePOSLinkUpDao.funExecute(sql);
	}

}
