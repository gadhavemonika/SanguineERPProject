package com.sanguine.dao;

import java.util.List;
import java.util.Map;

import com.sanguine.model.clsExciseMasterModel;

public interface clsExciseMasterDao {

	public void funAddExcise(clsExciseMasterModel excise);

	public List<clsExciseMasterModel> funListExcise(String clientCode);

	public clsExciseMasterModel funGetExcise(String exciseCode, String clientCode);

	public List<clsExciseMasterModel> funGetList(String exciseCode, String clientCode);

}
