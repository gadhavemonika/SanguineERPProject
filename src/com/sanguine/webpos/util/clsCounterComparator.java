/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsCounterDtlBean;

/**
 *
 * @author ajjim
 */
public class clsCounterComparator implements Comparator<clsCounterDtlBean> {

	private List<Comparator<clsCounterDtlBean>> listComparators;

	@SafeVarargs
	public clsCounterComparator(Comparator<clsCounterDtlBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsCounterDtlBean o1, clsCounterDtlBean o2) {
		for (Comparator<clsCounterDtlBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}