package com.mvalho.study.java.date_api;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateAPITests {
	
	@Test
	public void usingClock() {
		Clock clock = Clock.systemDefaultZone();
		long milis = clock.millis();
		
		assertTrue(milis > 0);
		
		Instant instant = clock.instant();
		Date legacyDate = Date.from(instant);
		
		assertNotNull(legacyDate);
	}
	
	@Test
	public void usingTimezones() {
		ZoneId.getAvailableZoneIds().stream().forEach(zone -> System.out.println(zone));
		assertEquals(595, ZoneId.getAvailableZoneIds().stream().count());
		
		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		
		assertEquals("ZoneRules[currentStandardOffset=+01:00]", zone1.getRules().toString());
		assertEquals("ZoneRules[currentStandardOffset=-03:00]", zone2.getRules().toString());
	}
	
	@Test
	public void usingLocalTime() {
		ZoneId zone1 = ZoneId.of("Europe/Dublin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		
		assertFalse(now1.isBefore(now2));
		
		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
		
		assertEquals(-3, hoursBetween);
		assertEquals(-239, minutesBetween);
		
		LocalTime late = LocalTime.of(23, 59, 59);
		assertEquals("23:59:59", late.toString());
		
		DateTimeFormatter dublinFormater = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);
		LocalTime leetTime = LocalTime.parse("13:37", dublinFormater);
		assertEquals("13:37", leetTime.toString());
	}
	
	@Test
	public void usingLocalDate() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		
		System.out.println(String.format("Today is %s", today));
		System.out.println(String.format("Tomorrow will be %s", tomorrow));
		System.out.println(String.format("Yesterday was %s", yesterday));
		
		LocalDate independeceDay = LocalDate.of(2014, Month.JULY, 4);
		DayOfWeek dayOfWeek = independeceDay.getDayOfWeek();
		assertEquals("FRIDAY", dayOfWeek.toString());
		
		DateTimeFormatter dublinFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		LocalDate xmas = LocalDate.parse("24.12.2014", dublinFormatter);
		assertEquals("2014-12-24", xmas.toString());
	}
	
	@Test
	public void usingLocalDateTime() {
		LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
		DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
		assertEquals(DayOfWeek.WEDNESDAY, dayOfWeek);
		
		Month month = sylvester.getMonth();
		assertEquals(Month.DECEMBER, month);
		
		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		assertEquals(1439, minuteOfDay);
		
		Instant instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();
		Date legacyDate = Date.from(instant);
		assertEquals("Wed Dec 31 23:59:59 BRST 2014", legacyDate.toString());
	}
}
