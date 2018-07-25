package com.sanguine.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.bean.clsParentDataForBOM;
import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.dao.clsRecipeMasterDao;
import com.sanguine.dao.clsTaxMasterDao;
import com.sanguine.model.clsBomDtlModel;
import com.sanguine.model.clsBomHdModel;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTaxHdModel;
import com.sanguine.model.clsTaxSettlementMasterModel;

@Service("objRecipeMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsRecipeMasterServiceImpl implements clsRecipeMasterService {
	@Autowired
	private clsRecipeMasterDao objRecipeMasterDao;

	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objRecipeMasterDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsBomHdModel object) {
		// TODO Auto-generated method stub
		objRecipeMasterDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsBomDtlModel object) {
		// TODO Auto-generated method stub
		objRecipeMasterDao.funAddUpdateDtl(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsBomHdModel> funGetList(String bomCode, String clientCode) {
		return objRecipeMasterDao.funGetList(bomCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String bomCode, String clientCode) {
		return objRecipeMasterDao.funGetDtlList(bomCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String bomCode, String clientCode) {
		objRecipeMasterDao.funDeleteDtl(bomCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsBomHdModel funGetObject(String bomCode, String clientCode) {
		// TODO Auto-generated method stub
		return objRecipeMasterDao.funGetObject(bomCode, clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetProductList(String sql) {
		// TODO Auto-generated method stub
		return objRecipeMasterDao.funGetProductList(sql);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetBOMCode(String strParentCode, String strClientCode) {
		return objRecipeMasterDao.funGetBOMCode(strParentCode, strClientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetBOMDtl(String strClientCode, String BOMCode) {
		return objRecipeMasterDao.funGetBOMDtl(strClientCode, BOMCode);
	}

}
