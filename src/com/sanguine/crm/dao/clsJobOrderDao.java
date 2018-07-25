package com.sanguine.crm.dao;

import java.util.List;

import com.sanguine.crm.model.clsJobOrderModel;

public interface clsJobOrderDao {

	public boolean funAddUpdateJobOrder(clsJobOrderModel objMaster);

	public List<Object> funGetJobOrderUsingSOCode(String strSOcode, String clientCode);

	public List<Object> funGetJobOrder(String strJOcode, String clientCode);

}
