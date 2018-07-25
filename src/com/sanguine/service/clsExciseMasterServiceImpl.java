package com.sanguine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanguine.dao.clsExciseMasterDao;
import com.sanguine.model.clsExciseMasterModel;

@Repository("clsExciseMasterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class clsExciseMasterServiceImpl implements clsExciseMasterService {

	@Autowired
	private clsExciseMasterDao objexciseMasterDao;

	@Override
	public void funAddExcise(clsExciseMasterModel excise) {
		objexciseMasterDao.funAddExcise(excise);

	}

	@Override
	public List<clsExciseMasterModel> funListExcise(String clientCode) {
		return objexciseMasterDao.funListExcise(clientCode);
	}

	@Override
	public clsExciseMasterModel funGetExcise(String groupCode, String clientCode) {
		return objexciseMasterDao.funGetExcise(groupCode, clientCode);
	}

	@Override
	public List funGetList(String exciseCode, String clientCode) {
		return objexciseMasterDao.funGetList(exciseCode, clientCode);
	}

}
