package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.sanguine.webpos.bean.clsVoidBillReportBean;

public class clsVoidBillReportBeanComparator implements Comparator<clsVoidBillReportBean> {

	private List<Comparator<clsVoidBillReportBean>> listComparators;

	@SafeVarargs
	public clsVoidBillReportBeanComparator(Comparator<clsVoidBillReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsVoidBillReportBean o1, clsVoidBillReportBean o2) {
		for (Comparator<clsVoidBillReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
