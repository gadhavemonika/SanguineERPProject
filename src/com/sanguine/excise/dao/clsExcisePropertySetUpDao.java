package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsExcisePropertySetUpModel;

public interface clsExcisePropertySetUpDao {

	public boolean funAddUpdateSetUpMaster(clsExcisePropertySetUpModel objMaster);

	public clsExcisePropertySetUpModel funGetSetUpMaster(String clientCode);

	public clsExcisePropertySetUpModel funGetObject(String clientCode);

}
