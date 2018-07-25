package com.sanguine.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.sanguine.dao.clsStockDao;
import com.sanguine.model.clsInitialInventoryModel;
import com.sanguine.model.clsOpeningStkDtl;

@Service("objStockService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsStockServiceImpl implements clsStockService {
	@Autowired
	private clsStockDao objStockDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdate(clsInitialInventoryModel object) {
		objStockDao.funAddUpdate(object);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funDeleteDtl(String opStkCode, String clientCode) {
		objStockDao.funDeleteDtl(opStkCode, clientCode);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddUpdateDtl(clsOpeningStkDtl object) {
		objStockDao.funAddUpdateDtl(object);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<clsInitialInventoryModel> funGetList() {
		return objStockDao.funGetList();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public clsInitialInventoryModel funGetObject(String code, String clientCode) {
		return objStockDao.funGetObject(code, clientCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funInitialInventoryReport(String clientCode) {
		return objStockDao.funInitialInventoryReport(clientCode);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetDtlList(String code, String clientCode) {
		return objStockDao.funGetDtlList(code, clientCode);
	}
}