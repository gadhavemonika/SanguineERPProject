package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsBrandMasterDao;
import com.sanguine.excise.model.clsBrandMasterModel;
import com.sanguine.excise.model.clsRateMasterModel;

@Service("clsBrandMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsBrandMasterServiceImpl implements clsBrandMasterService {
	@Autowired
	private clsBrandMasterDao objBrandMasterDao;

	@Override
	public boolean funAddUpdateBrandMaster(clsBrandMasterModel objMaster) {
		return objBrandMasterDao.funAddUpdateBrandMaster(objMaster);
	}

	@Override
	public List<clsBrandMasterModel> funGetBrandMaster(String clientCode) {
		return objBrandMasterDao.funGetBrandMaster(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		return objBrandMasterDao.funGetObject(code, clientCode);
	}

	@Override
	public boolean funAddUpdateRateMaster(clsRateMasterModel objMaster) {
		return objBrandMasterDao.funAddUpdateRateMaster(objMaster);
	}

	@Override
	public clsRateMasterModel funGetRateObject(String brandCode, String clientCode) {
		return objBrandMasterDao.funGetRateObject(brandCode, clientCode);
	}

}
