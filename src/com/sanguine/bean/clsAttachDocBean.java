package com.sanguine.bean;

import java.sql.Blob;
import java.util.List;

import com.sanguine.model.clsAttachDocModel;

public class clsAttachDocBean {
	private String strTrans;
	private String strCode;
	private String strActualFileName;
	private String strChangedFileName;
	private long intFileNo;
	private Blob binContent;
	private String strContentType;
	private String strModuleName;
	private String dtFromInvDate;
	private String dtToInvDate;
	private int intNoDays;

	private List<clsAttachDocModel> listAttachDoc;

	public String getStrTrans() {
		return strTrans;
	}

	public void setStrTrans(String strTrans) {
		this.strTrans = strTrans;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrActualFileName() {
		return strActualFileName;
	}

	public void setStrActualFileName(String strActualFileName) {
		this.strActualFileName = strActualFileName;
	}

	public String getStrChangedFileName() {
		return strChangedFileName;
	}

	public void setStrChangedFileName(String strChangedFileName) {
		this.strChangedFileName = strChangedFileName;
	}

	public long getIntFileNo() {
		return intFileNo;
	}

	public void setIntFileNo(long intFileNo) {
		this.intFileNo = intFileNo;
	}

	public Blob getBinContent() {
		return binContent;
	}

	public void setBinContent(Blob binContent) {
		this.binContent = binContent;
	}

	public String getStrContentType() {
		return strContentType;
	}

	public void setStrContentType(String strContentType) {
		this.strContentType = strContentType;
	}

	public List<clsAttachDocModel> getListAttachDoc() {
		return listAttachDoc;
	}

	public void setListAttachDoc(List<clsAttachDocModel> listAttachDoc) {
		this.listAttachDoc = listAttachDoc;
	}

	public String getStrModuleName() {
		return strModuleName;
	}

	public void setStrModuleName(String strModuleName) {
		this.strModuleName = strModuleName;
	}

	public int getIntNoDays() {
		return intNoDays;
	}

	public void setIntNoDays(int intNoDays) {
		this.intNoDays = intNoDays;
	}

	public String getDtFromInvDate() {
		return dtFromInvDate;
	}

	public void setDtFromInvDate(String dtFromInvDate) {
		this.dtFromInvDate = dtFromInvDate;
	}

	public String getDtToInvDate() {
		return dtToInvDate;
	}

	public void setDtToInvDate(String dtToInvDate) {
		this.dtToInvDate = dtToInvDate;
	}

}
