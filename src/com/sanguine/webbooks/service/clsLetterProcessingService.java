package com.sanguine.webbooks.service;

import java.util.List;

import com.sanguine.webbooks.model.clsLetterProcessingModel;

public interface clsLetterProcessingService {

	public void funAddUpdateLetterProcessing(clsLetterProcessingModel objMaster);

	public clsLetterProcessingModel funGetLetterProcessing(String docCode, String clientCode);

	public List funGetDebtoMemberList(String sqlQuery);

	public void funClearLetterProcessing(String userCode);

}
