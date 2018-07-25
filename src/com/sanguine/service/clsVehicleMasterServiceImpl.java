package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsVehicleMasterDao;
import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleRouteModel;

import org.springframework.transaction.annotation.Propagation;

@Service("clsVehicleMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsVehicleMasterServiceImpl implements clsVehicleMasterService {
	@Autowired
	private clsVehicleMasterDao objVehicleMasterDao;

	@Override
	public void funAddUpdateVehicleMaster(clsVehicleMasterModel objMaster) {
		objVehicleMasterDao.funAddUpdateVehicleMaster(objMaster);
	}

	@Override
	public clsVehicleMasterModel funGetVehicleMaster(String docCode, String clientCode) {
		return objVehicleMasterDao.funGetVehicleMaster(docCode, clientCode);
	}

	@Override
	public boolean funAddUpdateVehicleRouteMaster(clsVehicleRouteModel objVehRouteDtl) {
		return objVehicleMasterDao.funAddUpdateVehicleRouteMaster(objVehRouteDtl);
	}

	@Override
	public List<clsVehicleRouteModel> funGetListVehicleRouteModel(String docCode, String clientCode) {

		return objVehicleMasterDao.funGetListVehicleRouteModel(docCode, clientCode);
	}

	@Override
	public clsVehicleMasterModel funGetVehicleCode(String vehNumber, String clientCode) {
		return objVehicleMasterDao.funGetVehicleCode(vehNumber, clientCode);
	}

	@Override
	public int funDeleteVehRouteDtl(String vehCode, String clientCode) {
		return objVehicleMasterDao.funDeleteVehRouteDtl(vehCode, clientCode);
	}

}
