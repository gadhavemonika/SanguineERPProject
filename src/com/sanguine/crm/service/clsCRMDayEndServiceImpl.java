package com.sanguine.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsCRMDayEndDao;
import com.sanguine.crm.model.clsCRMDayEndModel;

@Service("objclsCRMDayEndService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, value = "hibernateTransactionManager")
public class clsCRMDayEndServiceImpl implements clsCRMDayEndService {

	@Autowired
	private clsCRMDayEndDao objCRMDayEndDao;

	@Override
	public void funAddUpdadte(clsCRMDayEndModel objModel) {
		objCRMDayEndDao.funAddUpdadte(objModel);

	}

	@Override
	public String funGetCRMDayEndLocationDate(String strLocCode, String strClientCode) {

		return objCRMDayEndDao.funGetCRMDayEndLocationDate(strLocCode, strClientCode);
	}

}
