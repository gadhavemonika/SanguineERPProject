package com.sanguine.webpms.dao;

import java.util.List;

import com.sanguine.webpms.model.clsChargePostingHdModel;

public interface clsChargePostingDao {

	public void funAddUpdateChargePosting(clsChargePostingHdModel objMaster);

	public List funGetChargePosting(String serviceCode, String clientCode);

}
