package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsSalesOrderBOMDao;
import com.sanguine.crm.model.clsSalesOrderBOMModel;

@Service("clsSalesOrderBOMService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsSalesOrderBOMServiceImpl implements clsSalesOrderBOMService {
	@Autowired
	private clsSalesOrderBOMDao objSoBomDao;

	@Override
	public boolean funAddUpdateSoBomHd(clsSalesOrderBOMModel objHdModel) {
		return objSoBomDao.funAddUpdateSoBomHd(objHdModel);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List funGetListOfMainParent(String soCode, String clientCode) {
		return objSoBomDao.funGetListOfMainParent(soCode, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetListOnProdCode(String soCode, String prodCode, String clientCode) {
		return objSoBomDao.funGetListOnProdCode(soCode, prodCode, clientCode);
	}

	public void funDeleteSalesOrderBom(String soCode, String parentCode, String clientCode) {
		objSoBomDao.funDeleteSalesOrderBom(soCode, parentCode, clientCode);
	}

	public void funDeleteSOBomOnParent(String soCode, String strParentCode, String clientCode) {
		objSoBomDao.funDeleteSOBomOnParent(soCode, strParentCode, clientCode);
	}

	public List funGetListOfSOBom(String soCode, String clientCode) {
		return objSoBomDao.funGetListOfSOBom(soCode, clientCode);
	}

}
