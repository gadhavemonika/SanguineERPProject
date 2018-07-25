package com.sanguine.webclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webclub.dao.clsWebClubEditOtherInfoDao;
import com.sanguine.webclub.model.clsWebClubEditOtherInfoModel;

@Service("clsWebClubEditOtherInfoService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebClubTransactionManager")
public class clsWebClubEditOtherInfoServiceImpl implements clsWebClubEditOtherInfoService {
	@Autowired
	private clsWebClubEditOtherInfoDao objWebClubEditOtherInfoDao;

	@Override
	public void funAddUpdateWebClubEditOtherInfo(clsWebClubEditOtherInfoModel objMaster) {
		objWebClubEditOtherInfoDao.funAddUpdateWebClubEditOtherInfo(objMaster);
	}

	@Override
	public clsWebClubEditOtherInfoModel funGetWebClubEditOtherInfo(String docCode, String clientCode) {
		return objWebClubEditOtherInfoDao.funGetWebClubEditOtherInfo(docCode, clientCode);
	}

}
