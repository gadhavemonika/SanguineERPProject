package com.sanguine.webclub.dao;

import com.sanguine.webclub.model.clsWebClubMaritalStatusModel;

public interface clsWebClubMaritalStatusDao {

	public void funAddUpdateWebClubMaritalStatus(clsWebClubMaritalStatusModel objMaster);

	public clsWebClubMaritalStatusModel funGetWebClubMaritalStatus(String docCode, String clientCode);

}
