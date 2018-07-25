package com.sanguine.webpos.bean;

import java.util.Map;

public class clsCashManagementDtlBean {
	private double saleAmt;

	private double totSaleAmt;

	private double advanceAmt;

	private double saleAfterRolling;

	private double floatAmt;

	private double totalBalanceAmt;

	private double withdrawlAmt;

	private double transferInAmt;

	private double transferOutAmt;

	private double refundAmt;

	private double paymentAmt;

	private double totPaymentAmt;

	private double rollingAmt;

	private double balanceAmt;

	private String userCode;

	private String userName;

	private String fromDate;

	private String toDate;

	private String posCode;

	private String posName;

	private double totalPostRollingSalesAmt;

	private double totalRollingAmt;

	private Map<String, Double> hmPostRollingSalesAmt;

	public double getFloatAmt() {
		return floatAmt;
	}

	public void setFloatAmt(double floatAmt) {
		this.floatAmt = floatAmt;
	}

	public double getWithdrawlAmt() {
		return withdrawlAmt;
	}

	public void setWithdrawlAmt(double withdrawlAmt) {
		this.withdrawlAmt = withdrawlAmt;
	}

	public double getTransferInAmt() {
		return transferInAmt;
	}

	public void setTransferInAmt(double transferInAmt) {
		this.transferInAmt = transferInAmt;
	}

	public double getTransferOutAmt() {
		return transferOutAmt;
	}

	public void setTransferOutAmt(double transferOutAmt) {
		this.transferOutAmt = transferOutAmt;
	}

	public double getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(double refundAmt) {
		this.refundAmt = refundAmt;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public double getSaleAmt() {
		return saleAmt;
	}

	public void setSaleAmt(double saleAmt) {
		this.saleAmt = saleAmt;
	}

	public double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public double getAdvanceAmt() {
		return advanceAmt;
	}

	public void setAdvanceAmt(double advanceAmt) {
		this.advanceAmt = advanceAmt;
	}

	public double getRollingAmt() {
		return rollingAmt;
	}

	public void setRollingAmt(double rollingAmt) {
		this.rollingAmt = rollingAmt;
	}

	public Map<String, Double> getHmPostRollingSalesAmt() {
		return hmPostRollingSalesAmt;
	}

	public void setHmPostRollingSalesAmt(Map<String, Double> hmPostRollingSalesAmt) {
		this.hmPostRollingSalesAmt = hmPostRollingSalesAmt;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getTotalBalanceAmt() {
		return totalBalanceAmt;
	}

	public void setTotalBalanceAmt(double totalBalanceAmt) {
		this.totalBalanceAmt = totalBalanceAmt;
	}

	public double getTotalPostRollingSalesAmt() {
		return totalPostRollingSalesAmt;
	}

	public void setTotalPostRollingSalesAmt(double totalPostRollingSalesAmt) {
		this.totalPostRollingSalesAmt = totalPostRollingSalesAmt;
	}

	public double getTotalRollingAmt() {
		return totalRollingAmt;
	}

	public void setTotalRollingAmt(double totalRollingAmt) {
		this.totalRollingAmt = totalRollingAmt;
	}

	public double getSaleAfterRolling() {
		return saleAfterRolling;
	}

	public void setSaleAfterRolling(double saleAfterRolling) {
		this.saleAfterRolling = saleAfterRolling;
	}

	public double getTotPaymentAmt() {
		return totPaymentAmt;
	}

	public void setTotPaymentAmt(double totPaymentAmt) {
		this.totPaymentAmt = totPaymentAmt;
	}

	public double getTotSaleAmt() {
		return totSaleAmt;
	}

	public void setTotSaleAmt(double totSaleAmt) {
		this.totSaleAmt = totSaleAmt;
	}

}
