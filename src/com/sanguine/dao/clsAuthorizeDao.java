package com.sanguine.dao;

import com.sanguine.model.clsAuthorizeUserModel;

public interface clsAuthorizeDao {
	public void funAddUpdate(clsAuthorizeUserModel objModel);

	public void funUpdateTransLevel(String sql);
}
