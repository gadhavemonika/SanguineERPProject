package com.sanguine.excise.service;

import com.sanguine.excise.model.clsExcisePOSLinkUpModel;

public interface clsExcisePOSLinkUpService {

	public void funAddUpdatePOSLinkUp(clsExcisePOSLinkUpModel objMaster);

	public clsExcisePOSLinkUpModel funGetPOSLinkUp(String docCode, String clientCode);

	public int funExecute(String sql);
}
