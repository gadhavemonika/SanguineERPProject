package com.sanguine.excise.service;

import com.sanguine.excise.model.clsOneDayPassHdModel;

public interface clsOneDayPassService {

	public boolean funAddOneDayPass(clsOneDayPassHdModel pass);

	public clsOneDayPassHdModel funGetOneDayPassObject(Long id);

	public clsOneDayPassHdModel funGetOneDayPass(Long id, String clientCode);

	public clsOneDayPassHdModel funGetOneDayPassByDate(String strDate, String clientCode);
}
