package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsSupplierMasterDao;
import com.sanguine.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;

@Service("objSupplierMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsSupplierMasterServiceImpl implements clsSupplierMasterService {
	@Autowired
	private clsSupplierMasterDao objSupplierMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsSupplierMasterModel> funGetList(String clientCode) {
		return objSupplierMasterDao.funGetList(clientCode);
	}

	public clsSupplierMasterModel funGetObject(String code, String clientCode) {
		return objSupplierMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsSupplierMasterModel objModel) {

		objSupplierMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objSupplierMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String pCode, String clientCode) {
		return objSupplierMasterDao.funGetDtlList(pCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator) {
		objSupplierMasterDao.funAddPartyTaxDtl(objPartyTaxIndicator);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeletePartyTaxDtl(String partyCode, String clientCode) {
		objSupplierMasterDao.funDeletePartyTaxDtl(partyCode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funExciseUpdate(String partyCode, String clientCode, String exSuppCode) {
		objSupplierMasterDao.funExciseUpdate(partyCode, clientCode, exSuppCode);
	}

}
