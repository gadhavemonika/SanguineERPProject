package com.sanguine.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsManufactureMasterDao;
import com.sanguine.model.clsManufactureMasterModel;

@Service("clsManufactureMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsManufactureMasterServiceImpl implements clsManufactureMasterService {

	@Autowired
	private clsManufactureMasterDao objManufactureMasterDao;

	@Override
	public void funAddUpdate(clsManufactureMasterModel objModel) {
		objManufactureMasterDao.funAddUpdate(objModel);
	}

	@Override
	public clsManufactureMasterModel funGetObject(String strCode, String clientCode) {

		return objManufactureMasterDao.funGetObject(strCode, clientCode);
	}

	@Override
	public Map<String, String> funGetManufacturerComboBox(String clientCode) {
		// TODO Auto-generated method stub
		return objManufactureMasterDao.funGetManufacturerComboBox(clientCode);
	}

}
