package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.dao.clsStockFlashDao;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsStockFlashModel;

@Service("objStockFlashService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsStockFlashServiceImpl implements clsStockFlashService {
	@Autowired
	private clsStockFlashDao objStkFlashDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetStockFlashData(String sql, String clientCode, String userCode) {
		return objStkFlashDao.funGetStockFlashData(sql, clientCode, userCode);
	}
}
