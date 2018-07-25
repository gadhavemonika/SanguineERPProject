package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsOperatorWiseReportBean;

public class clsOperatorWiseComparator implements Comparator<clsOperatorWiseReportBean> {

	private List<Comparator<clsOperatorWiseReportBean>> listComparators;

	@SafeVarargs
	public clsOperatorWiseComparator(Comparator<clsOperatorWiseReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsOperatorWiseReportBean o1, clsOperatorWiseReportBean o2) {
		for (Comparator<clsOperatorWiseReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
