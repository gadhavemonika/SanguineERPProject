package com.sanguine.bean;

import java.util.List;

import com.sanguine.model.clsPOSLinkUpModel;
import com.sanguine.model.clsTallyLinkUpModel;

public class clsTallyLinkUpBean {

	public String strGCode;

	public String strGName;

	public String strGDes;

	public String strTallyCode;

	public String strLinkup;

	private List<clsTallyLinkUpModel> listTallyLinkUp;

	public String getStrGCode() {
		return strGCode;
	}

	public void setStrGCode(String strGCode) {
		this.strGCode = strGCode;
	}

	public String getStrGName() {
		return strGName;
	}

	public void setStrGName(String strGName) {
		this.strGName = strGName;
	}

	public String getStrGDes() {
		return strGDes;
	}

	public void setStrGDes(String strGDes) {
		this.strGDes = strGDes;
	}

	public String getStrTallyCode() {
		return strTallyCode;
	}

	public void setStrTallyCode(String strTallyCode) {
		this.strTallyCode = strTallyCode;
	}

	public List<clsTallyLinkUpModel> getListTallyLinkUp() {
		return listTallyLinkUp;
	}

	public void setListTallyLinkUp(List<clsTallyLinkUpModel> listTallyLinkUp) {
		this.listTallyLinkUp = listTallyLinkUp;
	}

	public String getStrLinkup() {
		return strLinkup;
	}

	public void setStrLinkup(String strLinkup) {
		this.strLinkup = strLinkup;
	}

}
