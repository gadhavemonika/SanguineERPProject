package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsBatchHdModel;

public interface clsProductBatchDao {
	public void funSaveBatch(clsBatchHdModel batchModel);

	public List<clsBatchHdModel> funGetList(String clientCode, String strProdCode);
}
