package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsPMSReasonMasterBean;
import com.sanguine.webpms.bean.clsPMSSettlementMasterBean;
import com.sanguine.webpms.model.clsPMSReasonMasterModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;

public interface clsPMSSettlementMasterService {
	public clsPMSSettlementMasterHdModel funPrepareSettlementModel(clsPMSSettlementMasterBean objSettlementMasterBean, String clientCode);

}
