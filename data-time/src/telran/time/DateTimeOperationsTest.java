package telran.time;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;

import org.junit.jupiter.api.Test;

public class DateTimeOperationsTest {
	@Test
	void dateOperationsTest() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		LocalDate barMizvaAS = birthAS.plusYears(13);
		assertEquals(LocalDate.of(1812, 6, 6), barMizvaAS);

	}

	@Test
	void adjusterTest() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		LocalDateTime lt = LocalDateTime.now();
		assertEquals(LocalDate.of(1812, 6, 6), birthAS.with(new BarMizvaAdjuster()));
		System.out.println(lt.with(new BarMizvaAdjuster()));
	}

	@Test
	void nextFriday13Test() {
		TemporalAdjuster nextFiray13 = new NextFriday13();
		assertEquals(LocalDate.of(2023, 1, 13), LocalDate.of(2022, 9, 5).with(nextFiray13));
		assertEquals(LocalDate.of(2023, 10, 13), LocalDate.of(2023, 1, 13).with(nextFiray13));
	}

	@Test
	void workingDaysTest() {
		WorkingDaysAdjuster workingDays = new WorkingDaysAdjuster();
		workingDays.setDaysOff(new int[] { 1, 2, 3, 4, 5, 6, 7 });
		workingDays.setnDays(1000);
		assertEquals(LocalDate.now(), LocalDate.now().with(workingDays));
		workingDays.setDaysOff(new int[] {});
	//	assertEquals(LocalDate.now().plusDays(1000), LocalDate.now().with(workingDays));
		workingDays.setDaysOff(new int[] { 5, 6 });
		workingDays.setnDays(7);
		assertEquals(LocalDate.of(2022, 9, 14), LocalDate.of(2022, 9, 5).with(workingDays));
	}

	@Test
	void getPersonAge() {
		String format = "birthdate is %s, today is %s, age is %d\n";
		LocalDate current = LocalDate.now();
		System.out.printf(format, LocalDate.of(1950, 1, 20), current,
				ChronoUnit.YEARS.between(LocalDate.of(1950, 1, 20), current));
		System.out.printf(format, LocalDate.of(1950, 10, 20), current,
				ChronoUnit.YEARS.between(LocalDate.of(1950, 10, 20), current));
	}
}
