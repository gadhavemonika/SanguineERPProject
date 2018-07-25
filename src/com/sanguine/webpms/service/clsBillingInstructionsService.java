package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBillingInstructionsBean;
import com.sanguine.webpms.model.clsBillingInstructionsHdModel;

public interface clsBillingInstructionsService {

	public void funAddUpdateBillingInstructions(clsBillingInstructionsHdModel objMaster);

	public clsBillingInstructionsHdModel funGetBillingInstructions(String docCode, String clientCode);

	public clsBillingInstructionsHdModel funPrepareModel(clsBillingInstructionsBean objBean, String userCode, String clientCode);

}
