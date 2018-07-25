package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsTransporterHdModel;
import com.sanguine.model.clsTransporterModelDtl;

public interface clsTransporterMasterService {

	public boolean funAddUpdateHd(clsTransporterHdModel obj);

	public clsTransporterHdModel funGetTransporterMaster(String docCode, String clientCode);

	public void funAddTransporterDtl(clsTransporterModelDtl object);

	public void funDeleteDtl(String transCode, String clientCode);

	public List funListVehicle(String vehCode, String clientCode);

	public List funGetTransporterMasterDtl(String transCode, String clientCode);

}
