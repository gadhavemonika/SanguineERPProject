package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsTransporterMasterDao;
import com.sanguine.model.clsTransporterHdModel;
import com.sanguine.model.clsTransporterModelDtl;

@Service("clsTransporterMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsTransporterMasterServiceImpl implements clsTransporterMasterService {
	@Autowired
	private clsTransporterMasterDao objDao;

	@Override
	public boolean funAddUpdateHd(clsTransporterHdModel objHdModel) {
		return objDao.funAddUpdateHd(objHdModel);
	}

	@Override
	public clsTransporterHdModel funGetTransporterMaster(String docCode, String clientCode) {
		return objDao.funGetTransporterMaster(docCode, clientCode);
	}

	@Override
	public void funAddTransporterDtl(clsTransporterModelDtl object) {
		objDao.funAddTransporterDtl(object);

	}

	public void funDeleteDtl(String transCode, String clientCode) {
		objDao.funDeleteDtl(transCode, clientCode);
	}

	public List funListVehicle(String vehCode, String clientCode) {
		return objDao.funListVehicle(vehCode, clientCode);
	}

	public List funGetTransporterMasterDtl(String transCode, String clientCode) {
		return objDao.funGetTransporterMasterDtl(transCode, clientCode);
	}
}
