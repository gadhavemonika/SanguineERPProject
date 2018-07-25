package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsCharacteristicsMasterModel;

public interface clsCharacteristicsMasterService {

	public void funAddCharacteristics(clsCharacteristicsMasterModel characteristics);

	public List<clsCharacteristicsMasterModel> funListCharacteristics();

	public clsCharacteristicsMasterModel funGetCharacteristics(String characteristicsCode, String clientCode);

}
