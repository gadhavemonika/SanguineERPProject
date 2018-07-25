/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsGroupSubGroupItemBean;

/**
 *
 * @author ajjim
 */
public class clsGroupSubGroupComparator implements Comparator<clsGroupSubGroupItemBean> {

	private List<Comparator<clsGroupSubGroupItemBean>> listComparators;

	@SafeVarargs
	public clsGroupSubGroupComparator(Comparator<clsGroupSubGroupItemBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsGroupSubGroupItemBean o1, clsGroupSubGroupItemBean o2) {
		for (Comparator<clsGroupSubGroupItemBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}