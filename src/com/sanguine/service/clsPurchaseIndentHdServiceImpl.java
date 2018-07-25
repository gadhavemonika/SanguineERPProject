package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsPurchaseIndentHdDao;
import com.sanguine.model.clsProductReOrderLevelModel;
import com.sanguine.model.clsPurchaseIndentDtlModel;
import com.sanguine.model.clsPurchaseIndentHdModel;

@Service("objClsPurchaseIndentHdService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsPurchaseIndentHdServiceImpl implements clsPurchaseIndentHdService {

	@Autowired
	private clsPurchaseIndentHdDao objClsPurchaseIndentHdDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePurchaseIndent(clsPurchaseIndentHdModel PurchaseIndentHdModel) {
		objClsPurchaseIndentHdDao.funAddUpdatePurchaseIndent(PurchaseIndentHdModel);
	}

	@Override
	public List<clsPurchaseIndentHdModel> funListPurchaseIndentHdModel() {
		return null;
	}

	@Override
	public clsPurchaseIndentHdModel funGetPurchaseIndent(String PurchaseIndentCode) {
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdatePurchaseIndentDtl(clsPurchaseIndentDtlModel PurchaseIndentDtlModel) {
		objClsPurchaseIndentHdDao.funAddUpdatePurchaseIndentDtl(PurchaseIndentDtlModel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String PICode) {
		objClsPurchaseIndentHdDao.funDeleteDtl(PICode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String PICode, String clientCode, String strLocCode) {
		return objClsPurchaseIndentHdDao.funGetDtlList(PICode, clientCode, strLocCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public clsPurchaseIndentHdModel funGetObject(String POCode, String clientCode) {
		return objClsPurchaseIndentHdDao.funGetObject(POCode, clientCode);
	}

	@Override
	public clsProductReOrderLevelModel funGetReOrderLevel(String strProdCode, String strLocCode, String clientCode) {

		return objClsPurchaseIndentHdDao.funGetReOrderLevel(strProdCode, strLocCode, clientCode);
	}

}
