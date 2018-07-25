package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsSubGroupWaiseSalesBean;

public class clsSubGroupWiseComparator implements Comparator<clsSubGroupWaiseSalesBean> {

	private List<Comparator<clsSubGroupWaiseSalesBean>> listComparators;

	@SafeVarargs
	public clsSubGroupWiseComparator(Comparator<clsSubGroupWaiseSalesBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsSubGroupWaiseSalesBean o1, clsSubGroupWaiseSalesBean o2) {
		for (Comparator<clsSubGroupWaiseSalesBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
