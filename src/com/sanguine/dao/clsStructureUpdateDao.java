package com.sanguine.dao;

import javax.servlet.http.HttpServletRequest;

public interface clsStructureUpdateDao {
	public void funUpdateStructure(String clientCode, HttpServletRequest req);

	public void funClearTransaction(String clientCode, String[] str);

	public void funClearMaster(String clientCode, String[] str);

	public void funClearTransactionByPropertyAndLoction(String clientCode, String[] str, String propName, String locName);

}
