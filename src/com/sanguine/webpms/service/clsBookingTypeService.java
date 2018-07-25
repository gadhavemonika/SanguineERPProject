package com.sanguine.webpms.service;

import com.sanguine.webpms.bean.clsBookingTypeBean;
import com.sanguine.webpms.model.clsBookingTypeHdModel;

public interface clsBookingTypeService {

	public void funAddUpdateBookingType(clsBookingTypeHdModel objMaster);

	public clsBookingTypeHdModel funGetBookingType(String docCode, String clientCode);

	public clsBookingTypeHdModel funPrepareModel(clsBookingTypeBean objBean, String userCode, String clientCode);
}
