package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.webpms.dao.clsPropertySetupDao;
import com.sanguine.webpms.model.clsPropertySetupHdModel;

@Service("clsPropertySetupService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsPropertySetupServiceImpl implements clsPropertySetupService {
	@Autowired
	private clsPropertySetupDao objPropertySetupDao;

	@Override
	public void funAddUpdatePropertySetup(clsPropertySetupHdModel objMaster) {
		objPropertySetupDao.funAddUpdatePropertySetup(objMaster);
	}

	@Override
	public clsPropertySetupHdModel funGetPropertySetup(String docCode, String clientCode) {
		return objPropertySetupDao.funGetPropertySetup(docCode, clientCode);
	}

	@Override
	public List<clsCompanyMasterModel> funGetListCompanyMasterModel(String clientCode) {
		return objPropertySetupDao.funGetListCompanyMasterModel(clientCode);

	}
}
