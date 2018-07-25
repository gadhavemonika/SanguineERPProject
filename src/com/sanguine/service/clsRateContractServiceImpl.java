package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsRateContractDao;
import com.sanguine.model.clsRateContractDtlModel;
import com.sanguine.model.clsRateContractHdModel;

@Repository("clsRateContractService")
public class clsRateContractServiceImpl implements clsRateContractService {
	@Autowired
	private clsRateContractDao objRateContDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsRateContractHdModel object) {
		objRateContDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsRateContractDtlModel object) {
		objRateContDao.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsRateContractHdModel> funGetList(String clientCode) {
		return objRateContDao.funGetList(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String rateContractNo, String clientCode) {
		return objRateContDao.funGetObject(rateContractNo, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsRateContractDtlModel> funGetDtlList(String rateContractNo, String clientCode) {
		return objRateContDao.funGetDtlList(rateContractNo, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetSupplierList(String sql) {
		return objRateContDao.funGetSupplierList(sql);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String rateContractNo, String clientCode) {
		objRateContDao.funDeleteDtl(rateContractNo, clientCode);
	}
}
