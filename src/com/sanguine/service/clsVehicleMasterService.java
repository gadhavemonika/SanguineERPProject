package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsVehicleMasterModel;
import com.sanguine.model.clsVehicleRouteModel;

public interface clsVehicleMasterService {

	public void funAddUpdateVehicleMaster(clsVehicleMasterModel objMaster);

	public clsVehicleMasterModel funGetVehicleMaster(String docCode, String clientCode);

	public boolean funAddUpdateVehicleRouteMaster(clsVehicleRouteModel objVehRouteDtl);

	public List<clsVehicleRouteModel> funGetListVehicleRouteModel(String docCode, String clientCode);

	public clsVehicleMasterModel funGetVehicleCode(String vehNumber, String clientCode);

	public int funDeleteVehRouteDtl(String vehCode, String clientCode);

}
