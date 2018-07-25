package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsGroupMasterModel;
import com.sanguine.model.clsSessionMasterModel;

public interface clsSessionMasterDao {
	public void funAddSession(clsSessionMasterModel session);

	public clsSessionMasterModel funGetSession(String SessionCode, String clientCode);

	public List<clsSessionMasterModel> funListSession(String clientCode);

}
