package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsProductionOrderDao;
import com.sanguine.model.clsProductionOrderDtlModel;
import com.sanguine.model.clsProductionOrderHdModel;

@Service("objProductionOrderService")
public class clsProductionOrderServiceImpl implements clsProductionOrderService {

	@Autowired
	clsProductionOrderDao objclsProductionOrderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProductionHd(clsProductionOrderHdModel ProductionOrderHdModel) {

		objclsProductionOrderDao.funAddUpdateProductionHd(ProductionOrderHdModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateProductionDtl(clsProductionOrderDtlModel ProductionOrderDtlModel) {
		objclsProductionOrderDao.funAddUpdateProductionDtl(ProductionOrderDtlModel);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteProductionOrderDtl(String OPCode) {
		objclsProductionOrderDao.funDeleteProductionOrderDtl(OPCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsProductionOrderHdModel funGetObject(String OPCode, String clientCode) {

		return objclsProductionOrderDao.funGetObject(OPCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProductionOrderDtlModel> funGetDtlList(String OPCode, String clientCode) {

		return objclsProductionOrderDao.funGetDtlList(OPCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funListSOforProductionOrder(String strlocCode, String dtFullFilled, String clientCode, String orderType) {
		return objclsProductionOrderDao.funListSOforProductionOrder(strlocCode, dtFullFilled, clientCode, orderType);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean funUpdateProductionOrderAginstMaterialProcution(String strOPCode, String clientCode) {
		return objclsProductionOrderDao.funUpdateProductionOrderAginstMaterialProcution(strOPCode, clientCode);
	}
}
