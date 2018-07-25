package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsFloorMasterModel;

public interface clsFloorMasterDao {
	
		public void funAddUpdateFloorMaster(clsFloorMasterModel objFloorModel);
	
		public List funGetFloorMaster(String floorCode, String clientCode);
}
