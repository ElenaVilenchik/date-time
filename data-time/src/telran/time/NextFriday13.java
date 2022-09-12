package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.stream.Stream;

// returns temporal matching Friday 13
// if(ChronoField.DAY_OF_WEEK==DayOfWeek.FRIDAY.ordinal()+1) {}
// 13 day of month ChronoField.DAY_OF_MONTH==13
public class NextFriday13 implements TemporalAdjuster {

	private static final int DAY13 = 13;

	
	//version Yuri Granovski
		@Override
		public Temporal adjustInto(Temporal temporal) {
			temporal = adjustTemporal(temporal);
			while(temporal.get(ChronoField.DAY_OF_WEEK) != DayOfWeek.FRIDAY.ordinal() + 1) {
				temporal = temporal.plus(1, ChronoUnit.MONTHS);
			}
			return temporal;
		}

		private Temporal adjustTemporal(Temporal temporal) {
			if (temporal.get(ChronoField.DAY_OF_MONTH) >= 13) {
				temporal = temporal.plus(1, ChronoUnit.MONTHS);
			}
			return temporal.with(ChronoField.DAY_OF_MONTH, 13);
		}
	
	
	//My version both
//	@Override
//	public Temporal adjustInto(Temporal temporal) {
//		LocalDate date = LocalDate.from(temporal);
//		if (date.getDayOfMonth() >= DAY13) {
//			date = date.plusMonths(1);
//		}
//		date = date.withDayOfMonth(DAY13);
//
//		while (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
//			date = date.plusMonths(1);
//		}
//		return temporal.with(date);
//	}
	
	//!!!!not work
//	@Override
//	public Temporal adjustInto(Temporal temporal) {
//		LocalDate date = LocalDate.from(temporal); 
//		Stream.iterate(date, d -> date.plusDays(1))
//				.filter(d -> date.getDayOfWeek() != DayOfWeek.FRIDAY 
//				&& date.getDayOfMonth() != DAY13)
//				.limit(366)
//				.findFirst();
//		return temporal.with(date);	
//	}
		

	//version Daniil Zinchin
//	@Override
//	public Temporal adjustInto(Temporal temporal) {
//		LocalDate evilFriday = LocalDate.from(temporal);
//		if (evilFriday.getDayOfMonth() >= 13) {
//			evilFriday = evilFriday.plusMonths(1);
//		}
//	evilFriday = evilFriday.withDayOfMonth(13);
//
//		while (evilFriday.getDayOfWeek() != DayOfWeek.FRIDAY) {
//			evilFriday = evilFriday.plusMonths(1).withDayOfMonth(13);
//		}
//		return temporal.with(evilFriday);
//	}

}

