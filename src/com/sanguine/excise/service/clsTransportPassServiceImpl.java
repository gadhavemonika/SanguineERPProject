package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsTransportPassDao;
import com.sanguine.excise.model.clsTransportPassModel;
import com.sanguine.excise.model.clsTransportPassDtlModel;

@SuppressWarnings("rawtypes")
@Service("clsTransportPassService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsTransportPassServiceImpl implements clsTransportPassService {
	@Autowired
	private clsTransportPassDao objTPMasterDao;

	@Override
	public boolean funAddUpdateTPMaster(clsTransportPassModel objMaster) {
		return objTPMasterDao.funAddUpdateTPMaster(objMaster);
	}

	@Override
	public boolean funAddUpdateTPDtl(clsTransportPassDtlModel objTPDtl) {
		return objTPMasterDao.funAddUpdateTPDtl(objTPDtl);
	}

	@Override
	public List<clsTransportPassModel> funGetList(String clientCode) {
		return objTPMasterDao.funGetList(clientCode);
	}

	@Override
	public List funGetObject(String tpCode, String clientCode) {
		return objTPMasterDao.funGetObject(tpCode, clientCode);
	}

	@Override
	public List funGetTPDtlList(String tpCode, String clientCode) {
		return objTPMasterDao.funGetTPDtlList(tpCode, clientCode);
	}

	public boolean funDeleteHd(String tpCode, String clientCode) {
		return objTPMasterDao.funDeleteHd(tpCode, clientCode);
	}

	@Override
	public boolean funDeleteDtl(String tpCode, String clientCode) {
		return objTPMasterDao.funDeleteDtl(tpCode, clientCode);
	}

	@Override
	public List funGetTpNOObject(String tpNo, String clientCode) {
		return objTPMasterDao.funGetTpNOObject(tpNo, clientCode);
	}

}
