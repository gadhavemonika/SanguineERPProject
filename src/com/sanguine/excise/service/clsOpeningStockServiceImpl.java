package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsOpeningStockDao;
import com.sanguine.excise.model.clsOpeningStockModel;

@Service("clsOpeningStockService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsOpeningStockServiceImpl implements clsOpeningStockService {
	@Autowired
	private clsOpeningStockDao objExBrandOpMasterDao;

	@Override
	public Boolean funAddUpdateExBrandOpMaster(clsOpeningStockModel objMaster) {
		return objExBrandOpMasterDao.funAddUpdateExBrandOpMaster(objMaster);
	}

	@Override
	public clsOpeningStockModel funGetExBrandOpMaster(Long intId, String clientCode) {
		return objExBrandOpMasterDao.funGetExBrandOpMaster(intId, clientCode);
	}

	@SuppressWarnings("rawtypes")
	public List funGetMasterObject(Long intId, String clientCode) {
		return objExBrandOpMasterDao.funGetMasterObject(intId, clientCode);
	}

	public Boolean funDeleteDtl(Long intId, String clientCode) {
		return objExBrandOpMasterDao.funDeleteBrandOpening(intId, clientCode);
	}

	@Override
	public clsOpeningStockModel funGetOpenedEXBrandMaster(String brandCode, String licenceCode, String clientCode) {
		return objExBrandOpMasterDao.funGetOpenedEXBrandMaster(brandCode, licenceCode, clientCode);
	}

}
