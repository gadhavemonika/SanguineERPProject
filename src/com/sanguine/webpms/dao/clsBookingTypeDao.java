package com.sanguine.webpms.dao;

import com.sanguine.webpms.model.clsBookingTypeHdModel;

public interface clsBookingTypeDao {

	public void funAddUpdateBookingType(clsBookingTypeHdModel objMaster);

	public clsBookingTypeHdModel funGetBookingType(String docCode, String clientCode);

}
