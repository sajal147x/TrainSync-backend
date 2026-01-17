
package com.trainSync.util;

import java.time.OffsetDateTime;

/**
 * Author: Sajal Gupta
 * Created on: Jan 17, 2026 2:20:10 PM
 */
public class DateUtility {

	/**
	 * 
	 * @param timeFrameMonths
	 * @return
	 */
	public static OffsetDateTime getCutOffDateTimeFromMonthsAgo(String timeFrameMonths) {
		int monthsAgo = Integer.parseInt(timeFrameMonths);
		OffsetDateTime cutOffDateTime = OffsetDateTime.now().minusMonths(monthsAgo);
		return cutOffDateTime;
	}
	

}
