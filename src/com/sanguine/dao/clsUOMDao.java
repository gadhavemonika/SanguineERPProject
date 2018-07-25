package com.sanguine.dao;

import java.util.List;

import com.sanguine.model.clsUOMModel;

public interface clsUOMDao {

	public void funSaveOrUpdateUOM(clsUOMModel obj);

	public List funGetUOMObject(String clientCode, String UOM);

	public List<String> funGetUOMList(String clientCode);

	public void funDeleteUOM(String clientCode, String UOM);
}
