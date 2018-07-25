package com.sanguine.excise.dao;

import com.sanguine.excise.model.clsOneDayPassHdModel;

public interface clsOneDayPassDao {

	public boolean funAddOneDayPass(clsOneDayPassHdModel pass);

	public clsOneDayPassHdModel funGetOneDayPassObject(Long id);

	public clsOneDayPassHdModel funGetOneDayPass(Long id, String clientCode);

	public clsOneDayPassHdModel funGetOneDayPassByDate(String strDate, String clientCode);

}
