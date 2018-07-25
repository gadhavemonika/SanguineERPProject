package com.sanguine.webpms.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
@Embeddable
public class clsReservationRoomRateModelDtl {

	
		private static final long serialVersionUID = 1L;

		public clsReservationRoomRateModelDtl() {
		}
		
		private String strRoomType;
		
		private String dtDate;

		private double dblRoomRate;
		
		private String strRoomNo;


		public String getStrRoomNo() {
			return strRoomNo;
		}

		public void setStrRoomNo(String strRoomNo) {
			this.strRoomNo = strRoomNo;
		}

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
