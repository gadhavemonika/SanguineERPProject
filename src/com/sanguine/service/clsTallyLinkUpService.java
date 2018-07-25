package com.sanguine.service;

import com.sanguine.model.clsTallyLinkUpModel;

public interface clsTallyLinkUpService {

	public int funExecute(String sql);

	public boolean funAddUpdate(clsTallyLinkUpModel objModel);

}
