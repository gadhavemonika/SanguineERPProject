package com.sanguine.excise.service;

import com.sanguine.excise.model.clsExcisePropertySetUpModel;

public interface clsExcisePropertySetUpService {

	public boolean funAddUpdateSetUpMaster(clsExcisePropertySetUpModel objMaster);

	public clsExcisePropertySetUpModel funGetSetUpMaster(String clientCode);

	public clsExcisePropertySetUpModel funGetObject(String clientCode);

}
