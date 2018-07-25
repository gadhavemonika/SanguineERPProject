package com.sanguine.crm.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsCRMSettlementMasterDao;
import com.sanguine.model.clsSettlementMasterModel;

@Service("clsCRMSettlementMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsCRMSettlementMasterServiceImpl implements clsCRMSettlementMasterService {

	@Autowired
	private clsCRMSettlementMasterDao objSettlementMasterDao;

	@Override
	public void funAddUpdate(clsSettlementMasterModel objModel) {
		objSettlementMasterDao.funAddUpdate(objModel);
	}

	@Override
	public clsSettlementMasterModel funGetObject(String strCode, String clientCode) {

		return objSettlementMasterDao.funGetObject(strCode, clientCode);
	}

	@Override
	public clsSettlementMasterModel funGeSettlementObject(String strCode, String dteInvDate, String clientCode) {
		return objSettlementMasterDao.funGeSettlementObject(strCode, dteInvDate, clientCode);
	}

	@Override
	public Map<String, String> funGetSettlementComboBox(String clientCode) {
		// TODO Auto-generated method stub
		return objSettlementMasterDao.funGetSettlementComboBox(clientCode);
	}

}
