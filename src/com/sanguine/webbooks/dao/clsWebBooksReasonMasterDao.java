package com.sanguine.webbooks.dao;

import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel;

public interface clsWebBooksReasonMasterDao {

	public void funAddUpdateWebBooksReasonMaster(clsWebBooksReasonMasterModel objMaster);

	public clsWebBooksReasonMasterModel funGetWebBooksReasonMaster(String docCode, String clientCode);

}
