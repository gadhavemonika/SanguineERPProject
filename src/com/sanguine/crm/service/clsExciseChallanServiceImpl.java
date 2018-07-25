package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsExciseChallanDao;
import com.sanguine.crm.model.clsExciseChallanModel;

@Service("clsExciseChallanService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsExciseChallanServiceImpl implements clsExciseChallanService {
	@Autowired
	private clsExciseChallanDao objExciseChallanDao;

	@Override
	public boolean funAddUpdateExciseChallan(clsExciseChallanModel objMaster) {
		return objExciseChallanDao.funAddUpdateExciseChallan(objMaster);
	}

	@Override
	public List<clsExciseChallanModel> funGetExciseChallanMaster(String clientCode) {
		return objExciseChallanDao.funGetExciseChallan(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String code, String clientCode) {
		return objExciseChallanDao.funGetObject(code, clientCode);
	}

}
