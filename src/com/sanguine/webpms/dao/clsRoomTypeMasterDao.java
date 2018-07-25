package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsDepartmentMasterModel;
import com.sanguine.webpms.model.clsRoomTypeMasterModel;

public interface clsRoomTypeMasterDao {
	public void funAddUpdateRoomMaster(clsRoomTypeMasterModel objRoomMasterModel);

	public List funGetRoomTypeMaster(String roomCode, String clientCode);
}
