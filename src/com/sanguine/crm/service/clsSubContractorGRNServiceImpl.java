package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSubContractorGRNDao;
import com.sanguine.crm.model.clsSubContractorGRNModelDtl;
import com.sanguine.crm.model.clsSubContractorGRNModelHd;

@Service("clsSubContractorGRNService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSubContractorGRNServiceImpl implements clsSubContractorGRNService {
	@Autowired
	private clsSubContractorGRNDao objSubContractorGRNDao;

	@Override
	public boolean funAddUpdateSubContractorGRNHd(clsSubContractorGRNModelHd objHdModel) {
		return objSubContractorGRNDao.funAddUpdateSubContractorGRNHd(objHdModel);
	}

	public void funAddUpdateSubContractorGRNDtl(clsSubContractorGRNModelDtl objDtlModel) {
		objSubContractorGRNDao.funAddUpdateSubContractorGRNDtl(objDtlModel);
	}

	public clsSubContractorGRNModelHd funGetSubContractorGRNHd(String strSRCode, String clientCode) {
		return objSubContractorGRNDao.funGetSubContractorGRNHd(strSRCode, clientCode);

	}

	public void funDeleteDtl(String strSRCode, String clientCode) {

		objSubContractorGRNDao.funDeleteDtl(strSRCode, clientCode);
	}

	public List<Object> funLoadSubContractorGRNHDData(String docCode, String clientCode) {
		return objSubContractorGRNDao.funLoadSubContractorGRNHDData(docCode, clientCode);
	}

	public List<Object> funLoadSubContractorGRNDtlData(String docCode, String clientCode) {
		return objSubContractorGRNDao.funLoadSubContractorGRNDtlData(docCode, clientCode);
	}

}
