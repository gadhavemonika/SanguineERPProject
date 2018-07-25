package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsDeliveryChallanDao;
import com.sanguine.crm.model.clsDeliveryChallanHdModel;
import com.sanguine.crm.model.clsDeliveryChallanModelDtl;

@Service("clsDeliveryChallanService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsDeliveryChallanHdServiceImpl implements clsDeliveryChallanHdService {
	@Autowired
	private clsDeliveryChallanDao objDeliveryChallanDao;

	@Override
	public boolean funAddUpdateDeliveryChallanHd(clsDeliveryChallanHdModel objHdModel) {
		return objDeliveryChallanDao.funAddUpdateDeliveryChallanHd(objHdModel);
	}

	public void funAddUpdateDeliveryChallanDtl(clsDeliveryChallanModelDtl objDtlModel) {
		objDeliveryChallanDao.funAddUpdateDeliveryChallanDtl(objDtlModel);
	}

	public clsDeliveryChallanHdModel funGetDeliveryChallanHd(String dcCode, String clientCode) {
		return objDeliveryChallanDao.funGetDeliveryChallanHd(dcCode, clientCode);
	}

	public void funDeleteDtl(String soCode, String clientCode) {

		objDeliveryChallanDao.funDeleteDtl(soCode, clientCode);
	}

	public List<Object> funGetDeliveryChallan(String dcCode, String clientCode) {
		return objDeliveryChallanDao.funGetDeliveryChallan(dcCode, clientCode);
	}

	public List<Object> funGetDeliveryChallanDtl(String dcCode, String clientCode) {
		return objDeliveryChallanDao.funGetDeliveryChallanDtl(dcCode, clientCode);

	}

}
