package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsCharacteristicsMasterDao;
import com.sanguine.model.clsCharacteristicsMasterModel;

@Service("objCharacteristicsMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsCharacteristicsMasterServiceImpl implements clsCharacteristicsMasterService {

	@Autowired
	private clsCharacteristicsMasterDao objclsCharacteristicsMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddCharacteristics(clsCharacteristicsMasterModel characteristics) {
		objclsCharacteristicsMasterDao.funAddCharacteristics(characteristics);

	}

	@Override
	public List<clsCharacteristicsMasterModel> funListCharacteristics() {

		return objclsCharacteristicsMasterDao.funListCharacteristics();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsCharacteristicsMasterModel funGetCharacteristics(String characteristicsCode, String clientCode) {

		return objclsCharacteristicsMasterDao.funGetCharacteristics(characteristicsCode, clientCode);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objclsCharacteristicsMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

}
