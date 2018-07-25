package com.sanguine.service;

import com.sanguine.model.clsDeleteTransModel;

public interface clsDelTransService {
	public void funInsertRecord(clsDeleteTransModel objModel);

	public void funDeleteRecord(String sql, String queryType);
}
