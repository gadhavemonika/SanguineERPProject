package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsUOMDao;
import com.sanguine.model.clsUOMModel;

@Service("objclsUOMService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsUOMServiceImpl implements clsUOMService {
	@Autowired
	private clsUOMDao objclsUOMDao;

	@Override
	public void funSaveOrUpdateUOM(clsUOMModel obj) {
		objclsUOMDao.funSaveOrUpdateUOM(obj);

	}

	@Override
	public List funGetUOMObject(String clientCode, String UOM) {
		return objclsUOMDao.funGetUOMObject(clientCode, UOM);

	}

	@Override
	public List<String> funGetUOMList(String clientCode) {
		// TODO Auto-generated method stub
		return objclsUOMDao.funGetUOMList(clientCode);
	}

	@Override
	public void funDeleteUOM(String clientCode, String UOM) {
		objclsUOMDao.funDeleteUOM(clientCode, UOM);
	}

}
