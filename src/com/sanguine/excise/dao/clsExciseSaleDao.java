package com.sanguine.excise.dao;

import java.util.ArrayList;

import com.sanguine.excise.model.clsExciseSaleModel;

public interface clsExciseSaleDao {

	public void funAddUpdate(clsExciseSaleModel objMaster);

	public Boolean funAddBulkly(ArrayList<clsExciseSaleModel> objList);

}
