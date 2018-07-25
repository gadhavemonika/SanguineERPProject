package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsOneDayPassDao;
import com.sanguine.excise.model.clsOneDayPassHdModel;

@Service("clsOneDayPassService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsOneDayPassServiceImpl implements clsOneDayPassService {

	@Autowired
	clsOneDayPassDao objOneDayPassDao;

	public boolean funAddOneDayPass(clsOneDayPassHdModel pass) {
		return objOneDayPassDao.funAddOneDayPass(pass);
	}

	public clsOneDayPassHdModel funGetOneDayPassObject(Long id) {
		return objOneDayPassDao.funGetOneDayPassObject(id);
	}

	public clsOneDayPassHdModel funGetOneDayPass(Long id, String clientCode) {

		return objOneDayPassDao.funGetOneDayPass(id, clientCode);

	}

	@Override
	public clsOneDayPassHdModel funGetOneDayPassByDate(String strDate, String clientCode) {

		return objOneDayPassDao.funGetOneDayPassByDate(strDate, clientCode);
	}

}
