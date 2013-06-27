package com.aldaviva.dreamstats.data.enums;

public enum DayOfWeek {
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;

	/**
	 * @see {@link org.joda.time.DateTimeConstants}
	 * @param jodaDayOfWeek number that Joda uses for day of week (1 = Monday)
	 * @return
	 */
	public static DayOfWeek fromJoda(final int jodaDayOfWeek){
		return values()[jodaDayOfWeek - 1];
	}
}
