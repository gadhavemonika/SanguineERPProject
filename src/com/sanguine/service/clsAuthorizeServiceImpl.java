package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsAuthorizeDao;
import com.sanguine.model.clsAuthorizeUserModel;

@Service("objAuthorizeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsAuthorizeServiceImpl implements clsAuthorizeService {
	@Autowired
	private clsAuthorizeDao objDao;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void funAddUpdate(clsAuthorizeUserModel objModel) {
		objDao.funAddUpdate(objModel);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public void funUpdateTransLevel(String sql) {
		objDao.funUpdateTransLevel(sql);
	}

}
