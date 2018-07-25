package com.sanguine.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsGroupMasterDao;
import com.sanguine.dao.clsTCTransDao;
import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTCTransModel;

@Service("objTCTransService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsTCTransServiceImpl implements clsTCTransService {
	@Autowired
	private clsTCTransDao objTCTrans;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void funAddTCTrans(clsTCTransModel objTCTransModel) {
		objTCTrans.funAddTCTrans(objTCTransModel);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List funGetTCTransList(String sql, String transCode, String clientCode, String transType) {
		return objTCTrans.funGetTCTransList(sql, transCode, clientCode, transType);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int funDeleteTCTransList(String sql) {
		return objTCTrans.funDeleteTCTransList(sql);
	}

}
