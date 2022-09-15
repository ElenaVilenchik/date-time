package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

//return new temporal matching a date after the given days
public class WorkingDaysAdjuster implements TemporalAdjuster {

	int[] daysOff;
	int nDays;

	public WorkingDaysAdjuster() {
	}

	public WorkingDaysAdjuster(int[] daysOff, int nDays) {
		this.daysOff = daysOff;
		this.nDays = nDays;
	}

	public int[] getDaysOff() {
		return daysOff;
	}

	public void setDaysOff(int[] daysOff) {
		this.daysOff = daysOff;
	}

	public int getnDays() {
		return nDays;
	}

	public void setnDays(int nDays) {
		this.nDays = nDays;
	}

	//version Yuri Granovski
//	@Override
//	public Temporal adjustInto(Temporal temporal) {
//		if (nDays < 0) {
//			throw new IllegalArgumentException("number of working days can not be negative");
//		}
//		int count = 0;
//		if (daysOff.length < DayOfWeek.values().length) {
//			while (count != nDays) {
//				temporal = temporal.plus(1, ChronoUnit.DAYS);
//			//	System.out.println("temporal"+temporal);
//				if (!contains(temporal.get(ChronoField.DAY_OF_WEEK))) {
//			//		System.out.println("count"+count);
//					count++;
//				}
//			}
//		}
//		return temporal;
//	}
//
//	private boolean contains(int day) {
//		for (int di : daysOff) {
//			if (di == day) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	
// my version
	//!!! no working
	

	@Override
	public Temporal adjustInto(Temporal temporal) {
		LocalDate date = LocalDate.from(temporal);
		
		// predicate DaysOff
		Predicate<Temporal> isDaysOff = d -> date.getDayOfWeek().getValue() == daysOff[0];

		// stream date matching a date after the DaysOff
		Stream.iterate(date, d -> date.plusDays(1))
		.filter(isDaysOff.negate())
		.limit(nDays)
		.skip(nDays - 1)
		.findFirst();
		return temporal.with(date);	
	}
}



//version Daniil Zinchin
//	public int[] getDaysOff() {
//		return daysOff;
//	}
//
//	public void setDaysOff(int[] daysOff) {
//		// filters in only valid unique days
//		this.daysOff = Arrays.stream(daysOff).filter(n->n>=1 && n <=7).distinct().toArray();
//	}
//
//	public int getnDays() {
//		return nDays;
//	}
//
//	public void setnDays(int nDays) {
//		this.nDays = nDays;
//	}
//
//	public WorkingDaysAdjuster(int[] daysOff, int nDays) {
//		setDaysOff(daysOff);
//		this.nDays = nDays;
//	}
//
//	public WorkingDaysAdjuster() {


//public Temporal adjustInto(Temporal temporal) {
//	if (daysOff == null || daysOff.length == 0) {	// all days are "working"
//		return temporal.plus(nDays, ChronoUnit.DAYS);
//	}
//	if (daysOff.length == 7) {	// all days are "off"
//		return temporal; // due to test requirement
//	}
//	
//	int workingDaysInWeek = 7 - daysOff.length;
//	Temporal result = temporal.plus(nDays / workingDaysInWeek, ChronoUnit.WEEKS);
//	int remainedDays = nDays % workingDaysInWeek; // no more than 5 days 
//	
//	while(isDayOff(result)) {
//		result = result.plus(1, ChronoUnit.DAYS);
//	}		
//	while (remainedDays-- > 0) {
//		result = result.plus(1, ChronoUnit.DAYS);
//		while(isDayOff(result)) {
//			result = result.plus(1, ChronoUnit.DAYS);
//		}	
//	}
//	return result;
//	
//}
//
//	// Actually performs n=daysOff.length (from 1 to 6) comparisons 6-n times. 
//	// This is 1*5 or 2*4 or 3*3 - no more that 9 comparisons totally.
//	// That's why "lookup table" or "binary search" optimisation is not required here.
//	private boolean isDayOff(Temporal date) {
//		return Arrays.stream(daysOff).anyMatch(n->n==ChronoField.DAY_OF_WEEK.getFrom(date));
//	}
