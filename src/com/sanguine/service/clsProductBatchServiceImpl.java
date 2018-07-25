package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsProductBatchDao;
import com.sanguine.model.clsBatchHdModel;

@Service("objBatchProcessService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class clsProductBatchServiceImpl implements clsProductBatchService {

	@Autowired
	private clsProductBatchDao objBatchProcessDao;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
	public void funSaveOrUpdateBatch(clsBatchHdModel BatchModel) {
		objBatchProcessDao.funSaveBatch(BatchModel);

	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<clsBatchHdModel> funGetList(String clientCode, String strProdCode) {
		return objBatchProcessDao.funGetList(clientCode, strProdCode);
	}

}
