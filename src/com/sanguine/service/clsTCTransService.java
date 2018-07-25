package com.sanguine.service;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsTCTransModel;

public interface clsTCTransService {
	public void funAddTCTrans(clsTCTransModel objTCTrans);

	public List funGetTCTransList(String sql, String transCode, String clientCode, String transType);

	public int funDeleteTCTransList(String sql);
}
