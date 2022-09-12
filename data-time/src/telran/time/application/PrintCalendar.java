package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class PrintCalendar {

	public static void main(String[] args) {

		//!!! example
//		DayOfWeek weekDay = DayOfWeek.valueOf("THURSDAY");
//		System.out.printf("%s\n", weekDay.getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru")));
		
		
		int dayOfWeekMonthYear[];
		try {
			dayOfWeekMonthYear = getFirstWeekDayMonthYear(args);
			printCalendar(dayOfWeekMonthYear[0], dayOfWeekMonthYear[1], dayOfWeekMonthYear[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printCalendar(int month, int year, int firstWeekDay) {
		printTitle(month, year);
		printWeekDays(firstWeekDay);
		printDates(month, year, firstWeekDay);
	}

	private static void printDates(int month, int year, int firstWeekDay) {
		int column = getFirstColumn(month, year, firstWeekDay);
		printOffset(column);
		int nDays = getMonthDays(month, year);
		int nWeekDays = DayOfWeek.values().length;
		for (int day = 1; day <= nDays; day++) {
			System.out.printf("%4d", day);
			column++;
			if (column == nWeekDays) {
				column = 0;
				System.out.println();
			}
		}
	}

	private static int getMonthDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static void printOffset(int column) {
		System.out.printf("%s", " ".repeat(column * 4));
	}

	private static int getFirstColumn(int month, int year, int firstWeekDay) {
		LocalDate firstMonthDate = LocalDate.of(year, month, 1);
		int weekDay = firstMonthDate.getDayOfWeek().getValue();
		int res = weekDay - firstWeekDay;
		return res >= 0 ? res : res + DayOfWeek.values().length;
	}

	private static DayOfWeek[] getArrayWeekDays(int firstWeekDay) {
		DayOfWeek dayWeeks[] = DayOfWeek.values();
		int nDay = firstWeekDay;
		for (int i = 0; i < dayWeeks.length; i++) {
			dayWeeks[i] = DayOfWeek.of(nDay);
			nDay = nDay < dayWeeks.length ? ++nDay : 1;
		}
		return dayWeeks;
	}

	private static void printWeekDays(int firstWeekDay) {
		DayOfWeek dayWeeks[] = getArrayWeekDays(firstWeekDay);
		System.out.print("  ");
		for (DayOfWeek weekDay : dayWeeks) {
			System.out.printf("%s ", weekDay.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
		}
		System.out.println();
	}

	private static void printTitle(int month, int year) {
		Month monthEn = Month.of(month);
		System.out.printf("%s, %d\n", monthEn.getDisplayName(TextStyle.FULL, Locale.getDefault()), year);
	}

	private static int[] getFirstWeekDayMonthYear(String[] args) throws Exception {
		LocalDate current = LocalDate.now();
		int[] res = { current.getMonthValue(), current.getYear(), 6 };
		if (args.length > 0) {
			res[0] = getMonth(args[0]);
			if (args.length > 1) {
				res[1] = getYear(args[1]);
				if (args.length > 2) {
					res[2] = getFirstDay(args[2]);
				}
			}
		}
		return res;
	}

	private static int getFirstDay(String firstDay) throws Exception {
		try {
			DayOfWeek day = DayOfWeek.valueOf(firstDay.toUpperCase());
			return day.getValue();
		} catch (IllegalArgumentException e) {
			throw new Exception("day of week should be in the range [monday...sunday]");
		}
	}

	private static int getYear(String yearStr) throws Exception {
		try {
			int res = Integer.parseInt(yearStr);
			if (res <= 0) {
				throw new Exception("year should be a positive number");
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("year should be a number");
		}
	}

	private static int getMonth(String monthStr) throws Exception {
		try {
			int res = Integer.parseInt(monthStr);
			int nMonths = Month.values().length;
			if (res < 1 || res > nMonths) {
				throw new Exception(
						String.format("month %d is wrong value; should be in the range [1, %d]", res, nMonths));
			}
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("month should be a number");
		}
	}
}