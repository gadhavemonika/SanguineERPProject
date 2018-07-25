package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsWorkOrderDao;
import com.sanguine.model.clsWorkOrderDtlModel;
import com.sanguine.model.clsWorkOrderHdModel;

@Service("objWorkOrderService")
public class clsWorkOrderServiceImpl implements clsWorkOrderService {
	@Autowired
	private clsWorkOrderDao objWorkOrderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public long funGetLastNo(String tableName, String masterName, String columnName) {

		return objWorkOrderDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddWorkOrderHd(clsWorkOrderHdModel WoHd) {

		objWorkOrderDao.funAddWorkOrderHd(WoHd);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateWorkOrderDtl(clsWorkOrderDtlModel WoDtl) {
		objWorkOrderDao.funAddUpdateWorkOrderDtl(WoDtl);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsWorkOrderHdModel> funGetList() {

		return objWorkOrderDao.funGetList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsWorkOrderDtlModel> funGetDtlList(String WoCode, String prodCode, String clientCode) {

		return objWorkOrderDao.funGetDtlList(WoCode, prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String WoCode, String clientCode) {

		objWorkOrderDao.funDeleteDtl(WoCode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsWorkOrderHdModel> funGetWOHdData(String WoCode, String clientCode) {

		return objWorkOrderDao.funGetWOHdData(WoCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsWorkOrderHdModel funGetWOHd(String WoCode, String clientCode) {

		return objWorkOrderDao.funGetWOHd(WoCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String funGetProdProcessStatus(String strProdCode, String strProcessCode, String strWoCode, String strClientCode) {

		return objWorkOrderDao.funGetProdProcessStatus(strProdCode, strProcessCode, strWoCode, strClientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProdProcess(String prodCode, String strClientCode) {

		return objWorkOrderDao.funGetProdProcess(prodCode, strClientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProcessDet(String strWOCode, String strClientCode) {
		return objWorkOrderDao.funGetProcessDet(strWOCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String funGetWOStatusforProduct(String strWOCode, String strClientCode) {

		return objWorkOrderDao.funGetWOStatusforProduct(strWOCode, strClientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGenearteWOAgainstOPData(String oPCode, String strSOCode, String strClientCode, String against) {

		return objWorkOrderDao.funGenearteWOAgainstOPData(oPCode, strSOCode, strClientCode, against);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetRecipeList(String strParentCode, String strClientCode) {

		return objWorkOrderDao.funGetRecipeList(strParentCode, strClientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetGeneratedWOAgainstOPData(String oPCode, String clientCode) {

		return objWorkOrderDao.funGetGeneratedWOAgainstOPData(oPCode, clientCode);
	}

}
