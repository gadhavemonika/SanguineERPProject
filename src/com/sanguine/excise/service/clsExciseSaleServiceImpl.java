package com.sanguine.excise.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.excise.dao.clsExciseSaleDao;
import com.sanguine.excise.model.clsExciseSaleModel;

@Repository("clsExciseSaleService")
public class clsExciseSaleServiceImpl implements clsExciseSaleService {
	@Autowired
	private clsExciseSaleDao objStkAdjDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsExciseSaleModel object) {
		objStkAdjDao.funAddUpdate(object);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Boolean funAddBulkly(ArrayList<clsExciseSaleModel> objList) {
		return objStkAdjDao.funAddBulkly(objList);
	}
}
