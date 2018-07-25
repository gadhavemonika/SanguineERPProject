package com.sanguine.webpms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.webpms.dao.clsRoomMasterDao;
import com.sanguine.webpms.model.clsRoomMasterModel;

@Service("clsRoomMasterService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "WebPMSTransactionManager")
public class clsRoomMasterServiceImpl implements clsRoomMasterService {
	@Autowired
	private clsRoomMasterDao objRoomMasterDao;

	@Override
	public void funAddUpdateRoomMaster(clsRoomMasterModel objMaster) {
		objRoomMasterDao.funAddUpdateRoomMaster(objMaster);
	}

	@Override
	public clsRoomMasterModel funGetRoomMaster(String roomCode, String clientCode) {
		return objRoomMasterDao.funGetRoomMaster(roomCode, clientCode);
	}

	@Override
	public List<String> funGetRoomTypeList(String clientCode) {
		return objRoomMasterDao.funGetRoomTypeList(clientCode);
	}

}
