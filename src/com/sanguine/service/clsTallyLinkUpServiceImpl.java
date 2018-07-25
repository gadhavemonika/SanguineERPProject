package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPOSLinkUpDao;
import com.sanguine.dao.clsTallyLinkUpDao;
import com.sanguine.model.clsTallyLinkUpModel;

@Service("clsTallyLinkUpService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsTallyLinkUpServiceImpl implements clsTallyLinkUpService {

	@Autowired
	private clsTallyLinkUpDao objTallyLinkUpDao;

	@Override
	public int funExecute(String sql) {
		return objTallyLinkUpDao.funExecute(sql);
	}

	@Override
	public boolean funAddUpdate(clsTallyLinkUpModel objModel) {
		return objTallyLinkUpDao.funAddUpdate(objModel);
	}

}
