/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsCostCenterBean;
import com.sanguine.webpos.bean.clsCostCenterWiseSalesReportBean;

/**
 *
 * @author ajjim
 */
public class clsCostCenterComparator implements Comparator<clsCostCenterWiseSalesReportBean> {

	private List<Comparator<clsCostCenterWiseSalesReportBean>> listComparators;

	@SafeVarargs
	public clsCostCenterComparator(Comparator<clsCostCenterWiseSalesReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsCostCenterWiseSalesReportBean o1, clsCostCenterWiseSalesReportBean o2) {
		for (Comparator<clsCostCenterWiseSalesReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}