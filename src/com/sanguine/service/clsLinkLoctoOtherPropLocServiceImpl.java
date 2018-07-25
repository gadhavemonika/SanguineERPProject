package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsLinkLoctoOtherPropLocDao;
import com.sanguine.model.clsLinkLoctoOtherPropLocModel;

@Repository("clsLinkLoctoOtherPropLocService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsLinkLoctoOtherPropLocServiceImpl implements clsLinkLoctoOtherPropLocService {

	@Autowired
	private clsLinkLoctoOtherPropLocDao objLinkLoctoOtherPropLocDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsLinkLoctoOtherPropLocModel objModulehd) {
		objLinkLoctoOtherPropLocDao.funAddUpdate(objModulehd);
	}

	public void funDeleteData(String propCode, String strClientCode) {
		objLinkLoctoOtherPropLocDao.funDeleteData(propCode, strClientCode);
	}

	public List funLoadData(String propCode, String strClientCode) {
		return objLinkLoctoOtherPropLocDao.funLoadData(propCode, strClientCode);

	}
}
