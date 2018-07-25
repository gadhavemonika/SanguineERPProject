package com.sanguine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.SQLGrammarException;

import com.sanguine.model.clsUserMasterModel;

public interface clsUserMasterService {
	public void funAddUpdateUser(clsUserMasterModel userMaster);

	public List<clsUserMasterModel> funListUserMaster();

	public clsUserMasterModel funGetUser(String userCode, String clientCode) throws SQLGrammarException;

	public clsUserMasterModel funGetObject(String userCode, String clientCode);

	public Map<String, String> funProperties(String strClientCode);

	public Map<String, String> funGetUsers();

	public List funGetDtlList(String clientCode);

	public Map<String, String> funGetUserProperties(String strClientCode);

	public Map<String, String> funGetLocMapPropertyNUserWise(String propertyCode, String clientCode, String usercode, HttpServletRequest req);

	public Map<String, String> funGetUserWiseProperties(List<String> propCodes, String strClientCode);

	public Map<String, String> funGetUserBasedProperty(String strUserCode, String strClientCode);

	public HashMap<String, String> funGetUserBasedPropertyLocation(String strPropertyCode, String strUserCode, String strClientCode);

	public List<String> funGetUserWiseModules(String userCode, String clientCode);
}
