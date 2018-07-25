package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsPropertyMaster;

public interface clsPropertyMasterService {

	public void funAddProperty(clsPropertyMaster property);

	public List<clsPropertyMaster> funListProperty(String clientCode);

	public clsPropertyMaster funGetProperty(String popertyCode, String clientCode);

}
