package com.sanguine.service;

import java.util.List;

import com.sanguine.model.clsTreeMasterModel;
import com.sanguine.model.clsUserDesktopModel;
import com.sanguine.util.clsTreeRootNodeItemUtil;
import com.sanguine.util.clsUserDesktopUtil;

public interface clsTreeMenuService {

	public List<clsTreeMasterModel> funGetMenuForm();

	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String userCode, String clientCode, String rootNode);

	public List<clsTreeRootNodeItemUtil> getRootNodeItems(String rootNode);

	public List<clsUserDesktopUtil> funGetForms();

	public List<clsUserDesktopUtil> funGetForms(String userCode, String clientCode);

	public List<clsUserDesktopModel> getUserDesktopForm(String userCode);

	public void funDeleteDesktopForm(String userCode);

	public void funInsertDesktopForm(String strformname, String userCode);

	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode);

	public List<clsUserDesktopUtil> funGetDesktopForms(String userCode, String clientCode);
}
