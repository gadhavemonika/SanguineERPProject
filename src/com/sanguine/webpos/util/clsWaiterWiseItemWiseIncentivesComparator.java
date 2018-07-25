package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsPOSWaiterWiseItemWiseIncentivesReportBean;

public class clsWaiterWiseItemWiseIncentivesComparator implements Comparator<clsPOSWaiterWiseItemWiseIncentivesReportBean> {

	private List<Comparator<clsPOSWaiterWiseItemWiseIncentivesReportBean>> listComparators;

	@SafeVarargs
	public clsWaiterWiseItemWiseIncentivesComparator(Comparator<clsPOSWaiterWiseItemWiseIncentivesReportBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsPOSWaiterWiseItemWiseIncentivesReportBean o1, clsPOSWaiterWiseItemWiseIncentivesReportBean o2) {
		for (Comparator<clsPOSWaiterWiseItemWiseIncentivesReportBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}

}
