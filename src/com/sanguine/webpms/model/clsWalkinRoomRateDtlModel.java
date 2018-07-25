package com.sanguine.webpms.model;

import javax.persistence.Embeddable;

@Embeddable
public class clsWalkinRoomRateDtlModel {
	private static final long serialVersionUID = 1L;
	
	public clsWalkinRoomRateDtlModel(){
		
	}

	private String strRoomType;
	
	private String dtDate;

	private double dblRoomRate;



	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public double getDblRoomRate() {
		return dblRoomRate;
	}

	public void setDblRoomRate(double dblRoomRate) {
		this.dblRoomRate = dblRoomRate;
	}

	public String getStrRoomType() {
		return strRoomType;
	}

	public void setStrRoomType(String strRoomType) {
		this.strRoomType = strRoomType;
	}
	
	
}
