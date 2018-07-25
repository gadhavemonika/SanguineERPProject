package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsStkPostingDao;
import com.sanguine.model.clsStkPostingDtlModel;
import com.sanguine.model.clsStkPostingHdModel;

@Repository("clsStkPostingService")
public class clsStkPostingServiceImpl implements clsStkPostingService {
	@Autowired
	private clsStkPostingDao objStkPostingDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsStkPostingHdModel object) {
		objStkPostingDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsStkPostingDtlModel object) {
		objStkPostingDao.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsStkPostingHdModel> funGetList(String clientCode) {
		return objStkPostingDao.funGetList(clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String PSCode, String clientCode) {
		return objStkPostingDao.funGetObject(PSCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsStkPostingDtlModel> funGetDtlList(String PSCode, String clientCode) {
		return objStkPostingDao.funGetDtlList(PSCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String PSCode, String clientCode) {
		objStkPostingDao.funDeleteDtl(PSCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsStkPostingHdModel funGetModelObject(String strphyStkpostCode, String clientCode) {
		return objStkPostingDao.funGetModelObject(strphyStkpostCode, clientCode);

	}
}
