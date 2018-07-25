package com.sanguine.excise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseManualSaleDao;
import com.sanguine.excise.model.clsExciseManualSaleHdModel;
import com.sanguine.excise.model.clsExciseManualSaleDtlModel;

@SuppressWarnings("rawtypes")
@Service("clsExciseManualSaleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "OtherTransactionManager")
public class clsExciseManualSaleServiceImpl implements clsExciseManualSaleService {
	@Autowired
	private clsExciseManualSaleDao objExciseSalesMasterDao;

	@Override
	public boolean funAddUpdateExciseSalesMaster(clsExciseManualSaleHdModel objMaster) {
		return objExciseSalesMasterDao.funAddUpdateExciseSalesMaster(objMaster);
	}

	@Override
	public List funGetObject(long intId, String clientCode) {
		return objExciseSalesMasterDao.funGetObject(intId, clientCode);
	}

	@Override
	public boolean funAddUpdateExciseSalesDtl(clsExciseManualSaleDtlModel objSalesDtl) {
		return objExciseSalesMasterDao.funAddUpdateSalesDtl(objSalesDtl);
	}

	@Override
	public List funGetList(String clientCode) {
		return objExciseSalesMasterDao.funGetList(clientCode);
	}

	@Override
	public List funGetSalesDtlList(long intId, String clientCode) {
		return objExciseSalesMasterDao.funGetSalesDtlList(intId, clientCode);
	}

	@Override
	public boolean funDeleteHd(long intId, String clientCode) {
		return objExciseSalesMasterDao.funDeleteHd(intId, clientCode);
	}

	@Override
	public boolean funDeleteDtl(long intId, String clientCode) {
		return objExciseSalesMasterDao.funDeleteDtl(intId, clientCode);
	}

	@Override
	public boolean funDeleteSaleData(long intId, String clientCode) {
		return objExciseSalesMasterDao.funDeleteSaleData(intId, clientCode);
	}

}
