package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsTransactionTimeModel;

public interface clsTransactionTimeService {

	public void funAddUpdate(clsTransactionTimeModel clsTransactionTimeModel);

	public List<clsTransactionTimeModel> funLoadTransactionTime(String strPropertyCode, String strClientCode, String strTransactionName);

	public List<clsTransactionTimeModel> funLoadTransactionTimeLocationWise(String strPropertyCode, String strClientCode, String LocCode);
}
