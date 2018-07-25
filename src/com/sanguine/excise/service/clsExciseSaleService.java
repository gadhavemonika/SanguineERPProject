package com.sanguine.excise.service;

import java.util.ArrayList;

import com.sanguine.excise.model.clsExciseSaleModel;

public interface clsExciseSaleService {
	public void funAddUpdate(clsExciseSaleModel object);

	public Boolean funAddBulkly(ArrayList<clsExciseSaleModel> objList);

}
