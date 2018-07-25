package com.sanguine.service;

import com.sanguine.model.clsAuthorizeUserModel;

public interface clsAuthorizeService {
	public void funAddUpdate(clsAuthorizeUserModel objModel);

	public void funUpdateTransLevel(String sql);
}
