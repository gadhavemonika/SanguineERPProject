package com.sanguine.service;

import com.sanguine.model.clsRouteMasterModel;

public interface clsRouteMasterService {

	public void funAddUpdateRouteMaster(clsRouteMasterModel objMaster);

	public clsRouteMasterModel funGetRouteMaster(String docCode, String clientCode);

	public clsRouteMasterModel funGetRouteNameMaster(String strRouteName, String clientCode);

}
