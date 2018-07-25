package com.sanguine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsDelTransDao;
import com.sanguine.model.clsDeleteTransModel;

@Service("objDelTransService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsDelTransServiceImpl implements clsDelTransService {
	@Autowired
	private clsDelTransDao objDelTransDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funInsertRecord(clsDeleteTransModel objModel) {
		objDelTransDao.funInsertRecord(objModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteRecord(String sql, String queryType) {
		objDelTransDao.funDeleteRecord(sql, queryType);
	}

}
