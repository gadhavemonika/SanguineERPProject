package com.sanguine.bean;

import java.util.ArrayList;
import java.util.List;

import com.sanguine.model.clsVehicleRouteModel;

public class clsVehicleMasterBean {
	// Variable Declaration
	private String strVehCode;

	private String strVehNo;

	private String strDesc;

	private String strRouteCode;

	List<clsVehicleRouteModel> listclsVehicleRouteModel = new ArrayList<clsVehicleRouteModel>();

	// Setter-Getter Methods
	public String getStrVehCode() {
		return strVehCode;
	}

	public void setStrVehCode(String strVehCode) {
		this.strVehCode = strVehCode;
	}

	public String getStrVehNo() {
		return strVehNo;
	}

	public void setStrVehNo(String strVehNo) {
		this.strVehNo = strVehNo;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrRouteCode() {
		return strRouteCode;
	}

	public void setStrRouteCode(String strRouteCode) {
		this.strRouteCode = strRouteCode;
	}

	public List<clsVehicleRouteModel> getListclsVehicleRouteModel() {
		return listclsVehicleRouteModel;
	}

	public void setListclsVehicleRouteModel(List<clsVehicleRouteModel> listclsVehicleRouteModel) {
		this.listclsVehicleRouteModel = listclsVehicleRouteModel;
	}

}
