package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsPartyMasterDao;
import com.sanguine.crm.model.clsPartyMasterModel;
import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.model.clsSupplierMasterModel;

@Service("clsPartyMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsPartyMasterServiceImpl implements clsPartyMasterService {

	@Autowired
	private clsPartyMasterDao objPartyMasterDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPartyMasterModel> funGetList() {
		return objPartyMasterDao.funGetList();
	}

	public clsPartyMasterModel funGetObject(String code, String clientCode) {
		return objPartyMasterDao.funGetObject(code, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsPartyMasterModel objModel) {

		objPartyMasterDao.funAddUpdate(objModel);
	}

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objPartyMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String pCode, String clientCode) {
		return objPartyMasterDao.funGetDtlList(pCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator) {
		objPartyMasterDao.funAddPartyTaxDtl(objPartyTaxIndicator);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeletePartyTaxDtl(String partyCode, String clientCode) {
		objPartyMasterDao.funDeletePartyTaxDtl(partyCode, clientCode);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsPartyMasterModel funGetPartyDtl(String pCode, String clientCode) {
		return objPartyMasterDao.funGetPartyDtl(pCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPartyMasterModel> funGetListCustomer(String clientCode) {
		return objPartyMasterDao.funGetListCustomer(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsPartyMasterModel> funGetLinkLocCustomer(String locCode, String clientCode) {
		return objPartyMasterDao.funGetLinkLocCustomer(locCode, clientCode);
	}

}
