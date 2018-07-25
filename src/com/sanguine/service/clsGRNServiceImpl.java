package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGRNDao;
import com.sanguine.model.clsGRNDtlModel;
import com.sanguine.model.clsGRNHdModel;
import com.sanguine.model.clsGRNTaxDtlModel;

@Repository("clsGRNService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsGRNServiceImpl implements clsGRNService {
	@Autowired
	private clsGRNDao objGRNDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsGRNHdModel object) {
		objGRNDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsGRNDtlModel object) {
		objGRNDao.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funUpdateProductSupplier(String suppCode, String prodCode, String clientCode, String maxQty, String price) {
		objGRNDao.funUpdateProductSupplier(suppCode, prodCode, clientCode, maxQty, price);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProdSupp(String suppCode, String prodCode, String clientCode) {
		return objGRNDao.funGetProdSupp(suppCode, prodCode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsGRNHdModel> funGetList(String clientCode) {
		return objGRNDao.funGetList(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetObject(String GRNCode, String clientCode) {
		return objGRNDao.funGetObject(GRNCode, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String GRNCode, String clientCode) {
		return objGRNDao.funGetDtlList(GRNCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlListAgainst(String code, String clientCode, String againstTableName) {
		return objGRNDao.funGetDtlListAgainst(code, clientCode, againstTableName);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String GRNCode, String clientCode) {
		objGRNDao.funDeleteDtl(GRNCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteProdSupp(String suppCode, String prodCode, String clientCode) {
		return objGRNDao.funDeleteProdSupp(suppCode, prodCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetFixedAmtTaxList(String clientCode) {
		return objGRNDao.funGetFixedAmtTaxList(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funLoadPOforGRN(String strSuppCode, String strPropCode, String StrClientCode) {

		return objGRNDao.funLoadPOforGRN(strSuppCode, strPropCode, StrClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetNonStkData(String strPOCode, String strGrnCode, String strClientCode) {

		return objGRNDao.funGetNonStkData(strPOCode, strGrnCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteGRNTaxDtl(String GRNCode, String clientCode) {
		return objGRNDao.funDeleteGRNTaxDtl(GRNCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateGRNTaxDtl(clsGRNTaxDtlModel objModel) {
		objGRNDao.funAddUpdateGRNTaxDtl(objModel);
	}
}
