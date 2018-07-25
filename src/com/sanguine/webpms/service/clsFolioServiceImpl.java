package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsFolioDao;
import com.sanguine.webpms.model.clsFolioDtlModel;
import com.sanguine.webpms.model.clsFolioHdModel;

@Service("clsFolioService")
public class clsFolioServiceImpl implements clsFolioService {
	@Autowired
	private clsFolioDao objFolioDao;

	@Override
	public void funAddUpdateFolioHd(clsFolioHdModel objHdModel) {
		objFolioDao.funAddUpdateFolioHd(objHdModel);
	}

	@Override
	public clsFolioHdModel funGetFolioList(String folioNo, String clientCode, String propertyCode) {
		return objFolioDao.funGetFolioList(folioNo, clientCode, propertyCode);
	}

	@Override
	public List funGetParametersList(String sqlParameters) {
		return objFolioDao.funGetParametersList(sqlParameters);
	}

}
