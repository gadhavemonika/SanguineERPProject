package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseStructureUpdateDao;

@Service("clsExciseStructureUpdateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsExciseStructureUpdateServiceImpl implements clsExciseStructureUpdateService {

	@Autowired
	private clsExciseStructureUpdateDao objExciseStructureUpdateDao;

	public void funExciseUpdateStructure(String clientCode) {
		objExciseStructureUpdateDao.funExciseUpdateStructure(clientCode);

	}

	@Override
	public void funExciseClearTransaction(String clientCode, String[] str) {
		objExciseStructureUpdateDao.funExciseClearTransaction(clientCode, str);

	}

	@Override
	public void funExciseClearMaster(String clientCode, String[] str) {
		objExciseStructureUpdateDao.funExciseClearMaster(clientCode, str);

	}
	// @Override
	// public void funExciseClearTransactionByPropertyAndLoction(String
	// clientCode,String[] str,String propName,String locName)
	// {
	// objExciseStructureUpdateDao.funExciseClearTransactionByPropertyAndLoction(clientCode,str,propName,locName);
	// }

}
