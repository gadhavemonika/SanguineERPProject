package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsBatchHdModel;

public interface clsProductBatchService {

	public void funSaveOrUpdateBatch(clsBatchHdModel BatchModel);

	public List<clsBatchHdModel> funGetList(String clientCode, String strProdCode);
}
