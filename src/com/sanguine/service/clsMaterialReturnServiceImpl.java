package com.sanguine.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsMISDao;
import com.sanguine.dao.clsMaterialReturnDao;
import com.sanguine.model.clsMISDtlModel;
import com.sanguine.model.clsMISHdModel;
import com.sanguine.model.clsMaterialReturnDtlModel;
import com.sanguine.model.clsMaterialReturnHdModel;

@Service("clsMaterialReturnService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class clsMaterialReturnServiceImpl implements clsMaterialReturnService {
	@Autowired
	private clsMaterialReturnDao objMaterialReturnDao;

	@Override
	public void funDeleteDtl(String MRCode, String clientCode) {
		objMaterialReturnDao.funDeleteDtl(MRCode, clientCode);
	}

	@Override
	public void funAddUpdateMaterialReturnHd(clsMaterialReturnHdModel objMaterialReturnHd) {
		objMaterialReturnDao.funAddUpdateMaterialReturnHd(objMaterialReturnHd);
	}

	@Override
	public void funAddUpdateMaterialReturnDtl(clsMaterialReturnDtlModel objMaterialReturnDtl) {
		objMaterialReturnDao.funAddUpdateMaterialReturnDtl(objMaterialReturnDtl);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<clsMaterialReturnHdModel> funGetList(String clientCode) {
		return objMaterialReturnDao.funGetList(clientCode);
	}

	@Override
	public List funGetObject(String MRCode, String clientCode) {
		return objMaterialReturnDao.funGetObject(MRCode, clientCode);
	}

	@Override
	public List funGetMRDtlList(String MRCode, String clientCode) {
		return objMaterialReturnDao.funGetMRDtlList(MRCode, clientCode);
	}
}
