package com.sanguine.webpos.bean;

public class clsPrinterSetupBean {
	// Variable Declaration
	private String strCostCenterCode;

	private String strCostCenterName;

	private String strPrimaryPrinterPort;

	private String strSecondaryPrinterPort;

	private String strPrintOnBothPrintersYN;

	// Setter-Getter Methods
	public String getStrCostCenterCode() {
		return strCostCenterCode;
	}

	public void setStrCostCenterCode(String strCostCenterCode) {
		this.strCostCenterCode = strCostCenterCode;
	}

	public String getStrCostCenterName() {
		return strCostCenterName;
	}

	public void setStrCostCenterName(String strCostCenterName) {
		this.strCostCenterName = strCostCenterName;
	}

	public String getStrPrimaryPrinterPort() {
		return strPrimaryPrinterPort;
	}

	public void setStrPrimaryPrinterPort(String strPrimaryPrinterPort) {
		this.strPrimaryPrinterPort = strPrimaryPrinterPort;
	}

	public String getStrSecondaryPrinterPort() {
		return strSecondaryPrinterPort;
	}

	public void setStrSecondaryPrinterPort(String strSecondaryPrinterPort) {
		this.strSecondaryPrinterPort = strSecondaryPrinterPort;
	}

	public String getStrPrintOnBothPrintersYN() {
		return strPrintOnBothPrintersYN;
	}

	public void setStrPrintOnBothPrintersYN(String strPrintOnBothPrintersYN) {
		this.strPrintOnBothPrintersYN = strPrintOnBothPrintersYN;
	}

}
