package com.sanguine.dao;

import com.sanguine.model.clsTallyLinkUpModel;

public interface clsTallyLinkUpDao {

	public int funExecute(String sql);

	public boolean funAddUpdate(clsTallyLinkUpModel objModel);

}
