package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
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

	@Override
	public Temporal adjustInto(Temporal temporal) {
		if (getDaysOff().length == 0)
			return temporal.plus(Period.ofDays(getnDays()));
		else {

			// predicate DaysOff
			Predicate<LocalDate> isDaysOff = date -> date.getDayOfWeek() == DayOfWeek.FRIDAY
					|| date.getDayOfWeek() == DayOfWeek.SATURDAY;

			//stream date matching a date after the given days
			Stream.iterate(LocalDate.now(), date -> date.plusDays(1))
			.filter(isDaysOff.negate())
			.limit(getnDays());

			return temporal;
		}
	}
}
