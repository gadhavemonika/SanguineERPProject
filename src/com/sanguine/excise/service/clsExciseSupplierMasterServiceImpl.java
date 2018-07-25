package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseSupplierMasterDao;
import com.sanguine.excise.model.clsExciseSupplierMasterModel;

@Service("clsExciseSupplierMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExciseSupplierMasterServiceImpl implements clsExciseSupplierMasterService {
	@Autowired
	private clsExciseSupplierMasterDao objExciseSupplierMasterDao;

	@Override
	public boolean funAddUpdateExciseSupplierMaster(clsExciseSupplierMasterModel objMaster) {
		return objExciseSupplierMasterDao.funAddUpdateExciseSupplierMaster(objMaster);
	}

	@Override
	public List<clsExciseSupplierMasterModel> funGetExciseSupplierMaster(String clientCode) {
		return objExciseSupplierMasterDao.funGetExciseSupplierMaster(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		return objExciseSupplierMasterDao.funGetObject(code, clientCode);
	}

}
