package com.sanguine.webbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webbooks.dao.clsChargeMasterDao;
import com.sanguine.webbooks.model.clsChargeMasterModel;

@Service("clsChargeMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebBooksTransactionManager")
public class clsChargeMasterServiceImpl implements clsChargeMasterService {
	@Autowired
	private clsChargeMasterDao objChargeMasterDao;

	@Override
	public void funAddUpdateChargeMaster(clsChargeMasterModel objMaster) {
		objChargeMasterDao.funAddUpdateChargeMaster(objMaster);
	}

	@Override
	public List funGetChargeMaster(String docCode, String clientCode) {
		return objChargeMasterDao.funGetChargeMaster(docCode, clientCode);
	}

	@Override
	public List funGetDebtoMemberList(String sqlQuery) {
		return objChargeMasterDao.funGetDebtoMemberList(sqlQuery);
	}

	@Override
	public List<clsChargeMasterModel> funGetAllChargesData(String clientCode, String propertyCode) {
		return objChargeMasterDao.funGetAllChargesData(clientCode, propertyCode);
	}

}
