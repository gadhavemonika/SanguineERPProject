package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsTCTransModel;

public interface clsTCTransDao {
	public void funAddTCTrans(clsTCTransModel objTCTrans);

	public List funGetTCTransList(String sql, String transCode, String clientCode, String transType);

	public int funDeleteTCTransList(String sql);
}
