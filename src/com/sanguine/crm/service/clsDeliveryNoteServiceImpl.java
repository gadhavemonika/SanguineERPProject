package com.sanguine.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.crm.dao.clsDeliveryNoteDao;
import com.sanguine.crm.model.clsDeliveryNoteDtlModel;
import com.sanguine.crm.model.clsDeliveryNoteHdModel;

@Service("clsDeliveryNoteService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "hibernateTransactionManager")
public class clsDeliveryNoteServiceImpl implements clsDeliveryNoteService {
	@Autowired
	private clsDeliveryNoteDao objDeliveryNoteDao;

	@Override
	public Boolean funAddUpdateDeliveryNoteHd(clsDeliveryNoteHdModel objHdModel) {
		return objDeliveryNoteDao.funAddUpdateDeliveryNoteHd(objHdModel);
	}

	public Boolean funAddUpdateDeliveryNoteDtl(clsDeliveryNoteDtlModel objDtlModel) {
		return objDeliveryNoteDao.funAddUpdateDeliveryNoteDtl(objDtlModel);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List funGetDelNoteHdObject(String DNCode, String clientCode) {
		return objDeliveryNoteDao.funGetDelNoteHdObject(DNCode, clientCode);
	}

	@Override
	public List funGetDelNoteDtlList(String DNCode, String clientCode) {
		return objDeliveryNoteDao.funGetDelNoteDtlList(DNCode, clientCode);
	}

	@Override
	public boolean funDeleteDtl(String DNCode, String clientCode) {
		return objDeliveryNoteDao.funDeleteDtl(DNCode, clientCode);
	}

}
