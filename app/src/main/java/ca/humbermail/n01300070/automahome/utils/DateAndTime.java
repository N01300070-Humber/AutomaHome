package ca.humbermail.n01300070.automahome.utils;

import java.text.DateFormat;
import java.util.Calendar;

public class DateAndTime {
	
	public static String get12HourTime(int hourOfDay, int minute) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
		calendar.set(Calendar.MINUTE, minute);
		
		return DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
	}
}
