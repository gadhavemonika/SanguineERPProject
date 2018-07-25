package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsParameterSetupModel;

public interface clsParameterSetupService {

	public void funAddUpdateParameterSetup(clsParameterSetupModel objMaster);

	public clsParameterSetupModel funGetParameterSetup(String docCode, String clientCode);

}
