package com.sanguine.webpms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanguine.controller.clsGlobalFunctions;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsGroupMasterModel_ID;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;

@Service("objDeptMasterService")
public class clsDepartmentMasterServiceImpl implements clsDepartmentMasterService {

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;
	clsDepartmentMasterModel objDepartmentMasterModel;

	public clsDepartmentMasterModel funPrepareDeptModel(clsDepartmentMasterBean objDeptMasterBean, String clientCode, String userCode) {
		objDepartmentMasterModel = new clsDepartmentMasterModel();
		clsGlobalFunctions objGlobal = new clsGlobalFunctions();
		long lastNo = 0;

		if (objDeptMasterBean.getStrDeptCode().trim().length() == 0) {
			lastNo = objGlobalFunctionsService.funGetPMSMasterLastNo("tbldepartmentmaster", "DepartmentMaster", "strDeptCode", clientCode);
			String deptCode = "DT" + String.format("%06d", lastNo);
			// String deptCode="D0000001";
			objDepartmentMasterModel.setStrDeptCode(deptCode);
			objDepartmentMasterModel.setStrUserCreated(userCode);
			objDepartmentMasterModel.setDteDateCreated(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		} else {
			objDepartmentMasterModel.setStrDeptCode(objDeptMasterBean.getStrDeptCode());

		}
		objDepartmentMasterModel.setStrDeptDesc(objDeptMasterBean.getStrDeptDesc());

		objDepartmentMasterModel.setStrOperational(objDeptMasterBean.getStrOperational());
		objDepartmentMasterModel.setStrDiscount(objDeptMasterBean.getStrDiscount());
		objDepartmentMasterModel.setStrDeactivate(objDeptMasterBean.getStrDeactivate());
		objDepartmentMasterModel.setStrRevenueProducing(objDeptMasterBean.getStrRevenueProducing());

		objDepartmentMasterModel.setStrType(objDeptMasterBean.getStrType());

		objDepartmentMasterModel.setStrUserEdited(userCode);
		objDepartmentMasterModel.setDteDateEdited(objGlobal.funGetCurrentDateTime("yyyy-MM-dd"));
		objDepartmentMasterModel.setStrClientCode(clientCode);

		return objDepartmentMasterModel;

	}

}
