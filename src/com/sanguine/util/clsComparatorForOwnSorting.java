package com.sanguine.util;

import java.util.Comparator;

import com.sanguine.model.clsStkAdjustmentDtlModel;

public class clsComparatorForOwnSorting implements Comparator<clsStkAdjustmentDtlModel> {

	@Override
	public int compare(clsStkAdjustmentDtlModel o1, clsStkAdjustmentDtlModel o2) {
		clsStkAdjustmentDtlModel stkObj1 = (clsStkAdjustmentDtlModel) o1;
		clsStkAdjustmentDtlModel stkObj2 = (clsStkAdjustmentDtlModel) o2;

		return stkObj1.getStrParentName().compareTo(stkObj2.getStrParentName());
	}

}
