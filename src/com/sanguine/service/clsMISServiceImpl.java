package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsMISDao;
import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;

@Service("objMISService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsMISServiceImpl implements clsMISService {

	@Autowired
	private clsMISDao objMISDao;

	@Override
	public long funGetLastNo(String tableName, String masterName, String columnName) {
		return objMISDao.funGetLastNo(tableName, masterName, columnName);
	}

	@Override
	public void funAddMISHd(clsMISHdModel MISHd) {

		objMISDao.funAddMISHd(MISHd);
	}

	@Override
	public void funAddUpdateMISDtl(clsMISDtlModel MISDtl) {

		objMISDao.funAddUpdateMISDtl(MISDtl);
	}

	@Override
	public List<clsMISHdModel> funGetList() {

		return objMISDao.funGetList();
	}

	@Override
	public clsMISHdModel funGetObject(String code, String clientCode) {

		return objMISDao.funGetObject(code, clientCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDtlList(String MISCode, String clientCode) {

		return objMISDao.funGetDtlList(MISCode, clientCode);
	}

	@Override
	public void funDeleteDtl(String MISCode, String strClientCode) {

		objMISDao.funDeleteDtl(MISCode, strClientCode);
	}

	@Override
	public List funMISforMRDetails(String strLocFrom, String strLocTo, String strClientCode) {

		return objMISDao.funMISforMRDetails(strLocFrom, strLocTo, strClientCode);
	}

	@Override
	public List<String> funGetProductDtl(String strProdCode, String clientCode) {

		return objMISDao.funGetProductDtl(strProdCode, clientCode);
	}

	@Override
	public int funInsertNonStkItemDirect(String sql) {

		return objMISDao.funInsertNonStkItemDirect(sql);
	}
}
