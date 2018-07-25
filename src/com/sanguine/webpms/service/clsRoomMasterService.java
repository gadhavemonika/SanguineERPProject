package com.sanguine.webpms.service;

import java.util.List;

import com.sanguine.webpms.model.clsRoomMasterModel;

public interface clsRoomMasterService {

	public void funAddUpdateRoomMaster(clsRoomMasterModel objMaster);

	public clsRoomMasterModel funGetRoomMaster(String roomCode, String clientCode);

	public List<String> funGetRoomTypeList(String clientCode);

}
