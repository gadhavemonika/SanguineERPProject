package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsPMSTaxMasterDao;
import com.sanguine.webpms.model.clsPMSTaxMasterModel;

@Service("clsPMSTaxMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsPMSTaxMasterServiceImpl implements clsPMSTaxMasterService {
	@Autowired
	private clsPMSTaxMasterDao objPMSTaxMasterDao;

	@Override
	public void funAddUpdatePMSTaxMaster(clsPMSTaxMasterModel objMaster) {
		objPMSTaxMasterDao.funAddUpdatePMSTaxMaster(objMaster);
	}

	@Override
	public clsPMSTaxMasterModel funGetPMSTaxMaster(String taxCode, String clientCode) {
		return objPMSTaxMasterDao.funGetPMSTaxMaster(taxCode, clientCode);
	}

	@Override
	public List<String> funGetPMSDepartments(String clientCode) {
		return objPMSTaxMasterDao.funGetPMSDepartments(clientCode);
	}

	@Override
	public List<String> funGetIncomeHead(String clientCode) {
		return objPMSTaxMasterDao.funGetIncomeHead(clientCode);
	}

	@Override
	public List<String> funGetPMSTaxes(String clientCode) {
		return objPMSTaxMasterDao.funGetPMSTaxes(clientCode);
	}

	@Override
	public List<String> funGetPMSTaxGroup(String clientCode) {
		return objPMSTaxMasterDao.funGetPMSTaxGroup(clientCode);
	}

	@Override
	public String funGetCodeFromName(String fieldToBeSeleted, String fieldName, String fromFieldNameValue, String tableName, String clientCode) {
		return objPMSTaxMasterDao.funGetCodeFromName(fieldToBeSeleted, fieldName, fromFieldNameValue, tableName, clientCode);
	}

	@Override
	public String funGetMasterName(String query) {
		return objPMSTaxMasterDao.funGetMasterName(query);
	}

}
