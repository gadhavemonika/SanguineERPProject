package com.sanguine.excise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExcisePOSSaleDao;
import com.sanguine.excise.model.clsExcisePOSSaleModel;

@Service("clsExcisePOSSaleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExcisePOSSaleServiceImpl implements clsExcisePOSSaleService {
	@Autowired
	private clsExcisePOSSaleDao objExcisePOSSalesDtlDao;

	@Override
	public void funAddUpdate(clsExcisePOSSaleModel objMaster) {
		objExcisePOSSalesDtlDao.funAddUpdate(objMaster);
	}
}
