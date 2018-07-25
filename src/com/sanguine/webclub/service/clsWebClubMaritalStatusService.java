package com.sanguine.webclub.service;

import com.sanguine.webclub.model.clsWebClubMaritalStatusModel;

public interface clsWebClubMaritalStatusService {

	public void funAddUpdateWebClubMaritalStatus(clsWebClubMaritalStatusModel objMaster);

	public clsWebClubMaritalStatusModel funGetWebClubMaritalStatus(String docCode, String clientCode);

}
