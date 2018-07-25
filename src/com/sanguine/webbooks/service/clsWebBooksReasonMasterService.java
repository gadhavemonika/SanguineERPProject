package com.sanguine.webbooks.service;

import com.sanguine.webbooks.model.clsWebBooksReasonMasterModel;

public interface clsWebBooksReasonMasterService {

	public void funAddUpdateWebBooksReasonMaster(clsWebBooksReasonMasterModel objMaster);

	public clsWebBooksReasonMasterModel funGetWebBooksReasonMaster(String docCode, String clientCode);

}
