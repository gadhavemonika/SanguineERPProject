package com.sanguine.webpms.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sanguine.webpms.bean.clsDepartmentMasterBean;
import com.sanguine.webpms.bean.clsWalkinBean;
import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsRoomPackageDtl;
import com.sanguine.webpms.model.clsWalkinDtl;
import com.sanguine.webpms.model.clsWalkinHdModel;

public interface clsWalkinService {

	public void funAddUpdateWalkinHd(clsWalkinHdModel objHdModel);

	public clsWalkinHdModel funPrepareWalkinModel(clsWalkinBean objWalkinBean, String clientCode, HttpServletRequest request, String userCode);

	public void funAddUpdateWalkinDtl(clsWalkinDtl objDtlModel);
	
	public List<clsRoomPackageDtl> funGetWalkinIncomeList(String walkInNo, String clientCode) ;

}
