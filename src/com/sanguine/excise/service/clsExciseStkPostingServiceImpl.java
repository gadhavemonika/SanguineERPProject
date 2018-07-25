package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseStkPostingDao;
import com.sanguine.excise.model.clsExciseStkPostingDtlModel;
import com.sanguine.excise.model.clsExciseStkPostingHdModel;

@Repository("clsExciseStkPostingService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExciseStkPostingServiceImpl implements clsExciseStkPostingService {
	@Autowired
	private clsExciseStkPostingDao objExciseStkPostingDao;

	@Override
	public boolean funAddUpdate(clsExciseStkPostingHdModel object) {
		return objExciseStkPostingDao.funAddUpdate(object);
	}

	@Override
	public boolean funAddUpdateDtl(List<clsExciseStkPostingDtlModel> objList) {
		boolean success = false;
		try {
			success = objExciseStkPostingDao.funAddUpdateDtl(objList);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public List<clsExciseStkPostingHdModel> funGetList(String clientCode) {
		return objExciseStkPostingDao.funGetList(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetObject(String strPSPCode, String clientCode) {
		return objExciseStkPostingDao.funGetObject(strPSPCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String strPSPCode, String clientCode, String tempSizeClientCode, String tempBrandClientCode) {
		return objExciseStkPostingDao.funGetDtlList(strPSPCode, clientCode, tempSizeClientCode, tempBrandClientCode);
	}

	public void funDeleteDtl(String strPSPCode, String clientCode) {
		objExciseStkPostingDao.funDeleteDtl(strPSPCode, clientCode);
	}
}
