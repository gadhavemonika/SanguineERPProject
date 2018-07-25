package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsRequisitionDao;
import com.sanguine.model.clsMRPIDtl;
import com.sanguine.model.clsProductStandardModel;
import com.sanguine.model.clsRequisitionDtlModel;
import com.sanguine.model.clsRequisitionHdModel;

@Service("objReqService")
public class clsRequisitionServiceImpl implements clsRequisitionService {

	@Autowired
	private clsRequisitionDao objReqDao;

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objReqDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddRequisionHd(clsRequisitionHdModel reqhd) {

		objReqDao.funAddRequisionHd(reqhd);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateRequisionDtl(clsRequisitionDtlModel reqdtl) {

		objReqDao.funAddUpdateRequisionDtl(reqdtl);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsRequisitionHdModel> funGetList() {
		return objReqDao.funGetList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsRequisitionHdModel funGetObject(String ReqCode, String clientCode) {

		return objReqDao.funGetObject(ReqCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProductList(String sql) {

		return objReqDao.funGetProductList(sql);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String strReqCode) {

		objReqDao.funDeleteDtl(strReqCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsRequisitionHdModel funGetReqHdData(String reqCode) {

		return objReqDao.funGetReqHdData(reqCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsRequisitionDtlModel> funGetDtlList(String reqCode, String clientCode) {

		return objReqDao.funGetDtlList(reqCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetReqDtlList(String reqCode, String clientCode, String locCode, String userCode) {
		return objReqDao.funGetReqDtlList(reqCode, clientCode, locCode, userCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGenerateAutoReq(String strLocCode, String clientCode, String userCode, String strGCode, String strSGCode, String strSuppCode) {

		return objReqDao.funGenerateAutoReq(strLocCode, clientCode, userCode, strGCode, strSGCode, strSuppCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funSaveMRPIDtl(clsMRPIDtl funSaveMRPIDtl) {
		objReqDao.funSaveMRPIDtl(funSaveMRPIDtl);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddProductStandard(List<clsProductStandardModel> objProdStandard) {
		objReqDao.funAddProductStandard(objProdStandard);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteProductStandard(String strLocCode, String strPropertyCode, String clientCode) {
		objReqDao.funDeleteProductStandard(strLocCode, strPropertyCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsProductStandardModel> funGetProductStandartList(String strPropertyCode, String strLocCode, String clientCode) {
		return objReqDao.funGetProductStandartList(strPropertyCode, strLocCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<String> funGetProductDtl(String strProdCode, String clientCode) {
		return objReqDao.funGetProductDtl(strProdCode, clientCode);
	}

}
