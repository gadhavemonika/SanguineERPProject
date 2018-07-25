package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsAttributeValueMasterModel;

public interface clsAttributeValueMasterService {
	public void funAddUpdate(clsAttributeValueMasterModel object);

	public List<clsAttributeValueMasterModel> funGetList();

	public clsAttributeValueMasterModel funGetObject(String code, String clientCode);
}
