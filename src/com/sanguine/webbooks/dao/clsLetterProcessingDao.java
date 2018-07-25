package com.sanguine.webbooks.dao;

import java.util.List;

import com.sanguine.webbooks.model.clsLetterProcessingModel;

public interface clsLetterProcessingDao {

	public void funAddUpdateLetterProcessing(clsLetterProcessingModel objMaster);

	public clsLetterProcessingModel funGetLetterProcessing(String docCode, String clientCode);

	public List funGetDebtoMemberList(String sqlQuery);

	public void funClearLetterProcessing(String userCode);

}
