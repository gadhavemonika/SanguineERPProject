/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpms.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpms.bean.clsPaxReportBean;
import com.sanguine.webpos.bean.clsBillDtl;
import com.sanguine.webpos.bean.clsGroupSubGroupItemBean;

/**
 *
 * @author Manisha
 */
public class clsPaxReportComparator implements Comparator<clsPaxReportBean> {

	private List<Comparator<clsPaxReportBean>> listComparators;

	@SafeVarargs
	public clsPaxReportComparator(Comparator<clsPaxReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsPaxReportBean o1, clsPaxReportBean o2) {
		for (Comparator<clsPaxReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
