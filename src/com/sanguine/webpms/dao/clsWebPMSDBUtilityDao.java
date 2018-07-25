package com.sanguine.webpms.dao;

import java.util.List;

public interface clsWebPMSDBUtilityDao {
	public int funExecuteUpdate(String query, String queryType);

	public List funExecuteQuery(String query, String queryType);

}
