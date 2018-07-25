package com.sanguine.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class clsStockLedgerModel {

	private String strTransType;

	private String strTransDate;

	private String strCostCenter;

	private String strRefNo;

	private double dblReceipt;

	private double dblIssue;

	private double dblBalance;

	private double dblRate;

	private double dblValue;

	private double dblTransNo;

	public String getStrTransType() {
		return strTransType;
	}

	public void setStrTransType(String strTransType) {
		this.strTransType = strTransType;
	}

	public String getStrTransDate() {
		return strTransDate;
	}

	public void setStrTransDate(String strTransDate) {
		this.strTransDate = strTransDate;
	}

	public double getDblReceipt() {
		return dblReceipt;
	}

	public void setDblReceipt(double dblReceipt) {
		this.dblReceipt = dblReceipt;
	}

	public double getDblIssue() {
		return dblIssue;
	}

	public void setDblIssue(double dblIssue) {
		this.dblIssue = dblIssue;
	}

	public double getDblBalance() {
		return dblBalance;
	}

	public void setDblBalance(double dblBalance) {
		this.dblBalance = dblBalance;
	}

	public String getStrCostCenter() {
		return strCostCenter;
	}

	public void setStrCostCenter(String strCostCenter) {
		this.strCostCenter = strCostCenter;
	}

	public String getStrRefNo() {
		return strRefNo;
	}

	public void setStrRefNo(String strRefNo) {
		this.strRefNo = strRefNo;
	}

	public double getDblRate() {
		return dblRate;
	}

	public void setDblRate(double dblRate) {
		this.dblRate = dblRate;
	}

	public double getDblValue() {
		return dblValue;
	}

	public void setDblValue(double dblValue) {
		this.dblValue = dblValue;
	}

	public double getDblTransNo() {
		return dblTransNo;
	}

	public void setDblTransNo(double dblTransNo) {
		this.dblTransNo = dblTransNo;
	}

	public static long funCompareTime(String fromDate, String toDate) {
		long diff = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(fromDate);
			d2 = format.parse(toDate);

			diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			String time = diffHours + ":" + diffMinutes + ":" + diffSeconds;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return diff;
		}
	}

	public static Comparator<clsStockLedgerModel> comparatorStockLedger = new Comparator<clsStockLedgerModel>() {

		public int compare(clsStockLedgerModel s1, clsStockLedgerModel s2) {
			String date1 = s1.getStrTransDate();
			String date2 = s2.getStrTransDate();

			/*
			 * if(s2.getStrTransType().equals("Opening Stk")) { return 1; } else
			 */

			if (funCompareTime(date1, date2) == 0) {
				return 0;
			} else if (funCompareTime(date1, date2) > 1) {
				return -1;
			} else {
				return 1;
			}
		}
	};

	public static Comparator<clsStockLedgerModel> comparatorStockLedger1 = new Comparator<clsStockLedgerModel>() {

		public int compare(clsStockLedgerModel s1, clsStockLedgerModel s2) {
			double transNo1 = s1.getDblTransNo();
			double transNo2 = s2.getDblTransNo();
			if (transNo1 < transNo2) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	/*
	 * @Override public int compareTo(Object o) { double
	 * d1=Double.parseDouble(sequenceNo); double
	 * d2=Double.parseDouble(((clsMakeKotItemDtl)o).sequenceNo);
	 * 
	 * if(d1==d2) { return 0; } else if(d1 > d2) { return 1; } else { return -1;
	 * } }
	 */

}
