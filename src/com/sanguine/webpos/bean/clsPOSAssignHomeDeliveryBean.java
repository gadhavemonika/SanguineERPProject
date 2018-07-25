package com.sanguine.webpos.bean;

import java.util.List;

public class clsPOSAssignHomeDeliveryBean {
	private List<clsPOSBillHdDtl> listBillNoDtl;
	private List<clsPOSDeliveryPersonMaster> listDelBoyDtl;
	private String strBillNo;
	private String strDPCode;

	private String strClientCode;
	private String strDataPostFlag;
	private String strSettleYN;
	private double dblDBIncentives;
	private String strZone;
	private String strArea;

	public String getStrBillNo() {
		return strBillNo;
	}

	public void setStrBillNo(String strBillNo) {
		this.strBillNo = strBillNo;
	}

	public String getStrDPCode() {
		return strDPCode;
	}

	public void setStrDPCode(String strDPCode) {
		this.strDPCode = strDPCode;
	}

	public String getStrClientCode() {
		return strClientCode;
	}

	public void setStrClientCode(String strClientCode) {
		this.strClientCode = strClientCode;
	}

	public String getStrDataPostFlag() {
		return strDataPostFlag;
	}

	public void setStrDataPostFlag(String strDataPostFlag) {
		this.strDataPostFlag = strDataPostFlag;
	}

	public String getStrSettleYN() {
		return strSettleYN;
	}

	public void setStrSettleYN(String strSettleYN) {
		this.strSettleYN = strSettleYN;
	}

	public double getDblDBIncentives() {
		return dblDBIncentives;
	}

	public void setDblDBIncentives(double dblDBIncentives) {
		this.dblDBIncentives = dblDBIncentives;
	}

	public String getStrZone() {
		return strZone;
	}

	public void setStrZone(String strZone) {
		this.strZone = strZone;
	}

	public String getStrArea() {
		return strArea;
	}

	public void setStrArea(String strArea) {
		this.strArea = strArea;
	}

	public List<clsPOSBillHdDtl> getListBillNoDtl() {
		return listBillNoDtl;
	}

	public void setListBillNoDtl(List<clsPOSBillHdDtl> listBillNoDtl) {
		this.listBillNoDtl = listBillNoDtl;
	}

	public List<clsPOSDeliveryPersonMaster> getListDelBoyDtl() {
		return listDelBoyDtl;
	}

	public void setListDelBoyDtl(List<clsPOSDeliveryPersonMaster> listDelBoyDtl) {
		this.listDelBoyDtl = listDelBoyDtl;
	}

}
