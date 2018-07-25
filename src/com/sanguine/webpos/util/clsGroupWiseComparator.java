package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsGroupWaiseSalesBean;

public class clsGroupWiseComparator implements Comparator<clsGroupWaiseSalesBean> {

	private List<Comparator<clsGroupWaiseSalesBean>> listComparators;

	@SafeVarargs
	public clsGroupWiseComparator(Comparator<clsGroupWaiseSalesBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsGroupWaiseSalesBean o1, clsGroupWaiseSalesBean o2) {
		for (Comparator<clsGroupWaiseSalesBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
