package com.sanguine.dao;

import com.sanguine.model.clsRouteMasterModel;

public interface clsRouteMasterDao {

	public void funAddUpdateRouteMaster(clsRouteMasterModel objMaster);

	public clsRouteMasterModel funGetRouteMaster(String docCode, String clientCode);

	public clsRouteMasterModel funGetRouteNameMaster(String strRouteName, String clientCode);

}
