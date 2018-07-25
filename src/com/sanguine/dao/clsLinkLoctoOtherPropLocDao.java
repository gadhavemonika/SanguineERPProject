package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsLinkLoctoOtherPropLocModel;

public interface clsLinkLoctoOtherPropLocDao {

	public void funAddUpdate(clsLinkLoctoOtherPropLocModel objModulehd);

	public void funDeleteData(String propCode, String strClientCode);

	public List funLoadData(String propCode, String strClientCode);
}
