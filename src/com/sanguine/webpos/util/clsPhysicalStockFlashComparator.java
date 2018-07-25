package com.sanguine.webpos.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.sanguine.webpos.bean.clsPhysicalStockFlashBean;

public class clsPhysicalStockFlashComparator implements Comparator<clsPhysicalStockFlashBean> {

	private List<Comparator<clsPhysicalStockFlashBean>> listComparators;

	@SafeVarargs
	public clsPhysicalStockFlashComparator(Comparator<clsPhysicalStockFlashBean>... comparators) {
		this.listComparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(clsPhysicalStockFlashBean o1, clsPhysicalStockFlashBean o2) {
		for (Comparator<clsPhysicalStockFlashBean> comparator : listComparators) {
			int result = comparator.compare(o1, o2);
			if (result != 0) {
				return result;
			}
		}
		return 0;
	}
}
