package telran.time;

import java.time.DayOfWeek;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {
	// returns temporal matching Friday 13
	// if(ChronoField.DAY_OF_WEEK==DayOfWeek.FRIDAY.ordinal()+1) {}
	// 13 day of month ChronoField.DAY_OF_MONTH==13

	private static final int DAY13 = 13;

	@Override
	public Temporal adjustInto(Temporal temporal) {
		boolean isFriday13 = false;

		// find 13 day of month
		int difference = DAY13 - temporal.get(ChronoField.DAY_OF_MONTH);
		temporal = difference > 0 ? temporal.plus(Period.ofDays(difference))
				: temporal.plus(Period.ofMonths(1).plus(Period.ofDays(difference)));

		// find temporal matching Friday
		while (!isFriday13) {
			if (temporal.get(ChronoField.DAY_OF_WEEK) == DayOfWeek.FRIDAY.ordinal() + 1) {
				return temporal;
			}
			temporal = temporal.plus(Period.ofMonths(1));
		}
		return temporal;
	}
}

