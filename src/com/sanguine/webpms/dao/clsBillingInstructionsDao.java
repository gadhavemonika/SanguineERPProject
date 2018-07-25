package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsBillingInstructionsHdModel;

public interface clsBillingInstructionsDao {

	public void funAddUpdateBillingInstructions(clsBillingInstructionsHdModel objMaster);

	public clsBillingInstructionsHdModel funGetBillingInstructions(String docCode, String clientCode);

}
