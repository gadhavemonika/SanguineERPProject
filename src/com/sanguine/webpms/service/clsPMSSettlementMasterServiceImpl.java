package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsPMSSettlementMasterBean;
import com.sanguine.webpms.model.clsBaggageMasterModel;
import com.sanguine.webpms.model.clsPMSSettlementMasterHdModel;

@Service("clsSettlementMasterService")
public class clsPMSSettlementMasterServiceImpl implements clsPMSSettlementMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsPMSSettlementMasterHdModel objSettlementMasterModel;

	@Override
	public clsPMSSettlementMasterHdModel funPrepareSettlementModel(clsPMSSettlementMasterBean objSettlementMasterBean, String clientCode) {
		objSettlementMasterModel = new clsPMSSettlementMasterHdModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objSettlementMasterBean.getStrSettlementCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tblsettlementmaster", "SettlementMaster", "strSettlementCode", clientCode);
			// lastNo=1;
			String settlementCode = "ST" + String.format("%06d", lastNo);
			objSettlementMasterModel.setStrSettlementCode(settlementCode);

		} else {
			objSettlementMasterModel.setStrSettlementCode(objSettlementMasterBean.getStrSettlementCode());

		}
		objSettlementMasterModel.setStrSettlementDesc(objSettlementMasterBean.getStrSettlementDesc());
		objSettlementMasterModel.setStrSettlementType(objSettlementMasterBean.getStrSettlementType());
		objSettlementMasterModel.setStrApplicable(objSettlementMasterBean.getStrApplicable());
		objSettlementMasterModel.setStrClientCode(clientCode);
		objSettlementMasterModel.setStrAccountCode(objSettlementMasterBean.getStrAccountCode());

		return objSettlementMasterModel;
	}

}
