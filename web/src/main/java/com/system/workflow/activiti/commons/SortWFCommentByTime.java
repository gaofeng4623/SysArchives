package com.system.workflow.activiti.commons;

import java.util.Comparator;

public class SortWFCommentByTime  implements Comparator<WFComment> {

	@Override
	public int compare(WFComment c1, WFComment c2) {
		return c2.getTime().compareTo(c1.getTime());
	}

}
