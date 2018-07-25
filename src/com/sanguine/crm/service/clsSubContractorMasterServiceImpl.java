package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSubContractorMasterDao;
import com.sanguine.crm.model.clsPartyTaxIndicatorDtlModel;
import com.sanguine.crm.model.clsSubContractorMasterModel;

@Service("clsSubContractorMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSubContractorMasterServiceImpl implements clsSubContractorMasterService {

	@Autowired
	private clsSubContractorMasterDao objclsSubContractorMasterDao;

	@Override
	public Boolean funAddUpdate(clsSubContractorMasterModel objModel) {
		return objclsSubContractorMasterDao.funAddUpdate(objModel);
	}

	@Override
	public List<clsSubContractorMasterModel> funGetList(String clientCode) {
		return objclsSubContractorMasterDao.funGetList(clientCode);
	}

	@Override
	public clsSubContractorMasterModel funGetObject(String strPCode, String clientCode) {
		return objclsSubContractorMasterDao.funGetObject(strPCode, clientCode);
	}

	@Override
	public Boolean funAddPartyTaxDtl(clsPartyTaxIndicatorDtlModel objPartyTaxIndicator) {
		return objclsSubContractorMasterDao.funAddPartyTaxDtl(objPartyTaxIndicator);
	}

	@Override
	public void funDeletePartyTaxDtl(String partyCode, String clientCode) {
		objclsSubContractorMasterDao.funDeletePartyTaxDtl(partyCode, clientCode);
	}

}
