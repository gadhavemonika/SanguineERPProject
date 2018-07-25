package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsLinkLoctoOtherPropLocModel;

public interface clsLinkLoctoOtherPropLocService {

	public void funAddUpdate(clsLinkLoctoOtherPropLocModel objModulehd);

	public void funDeleteData(String propCode, String strByLoc);

	public List funLoadData(String propCode, String strByLoc);

}
