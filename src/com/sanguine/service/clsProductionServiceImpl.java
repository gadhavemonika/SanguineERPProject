package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsProductionDao;
import com.sanguine.model.clsProductionDtlModel;
import com.sanguine.model.clsProductionHdModel;

@Service("objPDService")
public class clsProductionServiceImpl implements clsProductionService {
	@Autowired
	private clsProductionDao objPDDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objPDDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean funAddPDHd(clsProductionHdModel PDHd) {
		return objPDDao.funAddPDHd(PDHd);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePDDtl(clsProductionDtlModel PDDtl) {
		objPDDao.funAddUpdatePDDtl(PDDtl);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProductionHdModel> funGetList() {

		return objPDDao.funGetList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsProductionHdModel funGetObject(String code, String clientCode) {

		return objPDDao.funGetObject(code, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String PDCode, String clientCode) {

		return objPDDao.funGetDtlList(PDCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String PDCode, String clientCode) {
		objPDDao.funDeleteDtl(PDCode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetWOHdData(String workOrderCode, String clientCode) {

		return objPDDao.funGetWOHdData(workOrderCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funUpdateWorkOrderStatus(String strWOCode, String strStatus, String strUser, String dteModifieddate, String strClientCode) {

		return objPDDao.funUpdateWorkOrderStatus(strWOCode, strStatus, strUser, dteModifieddate, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Double funGetPdProdQty(String workOrderCode, String ProdCode, String clientCode) {

		return objPDDao.funGetPdProdQty(workOrderCode, ProdCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetWorkOrdersComplete(String[] woCodes, String clientCode) {
		return objPDDao.funGetWorkOrdersComplete(woCodes, clientCode);
	}

}
