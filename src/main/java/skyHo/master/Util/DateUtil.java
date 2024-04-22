package skyHo.Master.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import io.quarkus.logging.Log;

public class DateUtil {
	public static final String DEFAULT_SQL_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DEFAULT_SQL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

	private DateUtil() {
		throw new IllegalStateException("DateUtil");
	}

	public static Date convertJDEDateToDate(int jdeDate) {
		int thisYear = (jdeDate / 1000) + 1900;
		int thisDays = jdeDate % 1000;
		Calendar calendar = new GregorianCalendar();
		calendar.clear();
		calendar.set(Calendar.YEAR, thisYear);
		calendar.set(Calendar.DAY_OF_YEAR, thisDays);
		return calendar.getTime();
	}

	public static Date getSystemDateOnDayBeginning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static Date convertStringToDate(String date, String format) {
		if (date == null || date.isEmpty())
			return null;

		Date convertedDate = null;
		if (format == null || format.isEmpty()) {
			format = DEFAULT_SQL_DATETIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			convertedDate = sdf.parse(date);
		} catch (ParseException e) {
			Log.error(e);
		}
		return convertedDate;
	}

	public static String convertDateToString(Date date, String format) {
		if (date == null)
			return "";

		if (format == null || format.isEmpty()) {
			format = DEFAULT_SQL_DATETIME_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		String strDate = sdf.format(date);

		return strDate;
	}

	public static Date addDays(Date date, Integer number) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, number);
		return c.getTime();
	}

	public static Date parseDate(String inputDate) {
		Date outputDate = DateUtil.convertStringToDate(inputDate, null);
		if (inputDate.contains("/") || inputDate.contains("-")) {
			outputDate = DateUtil.convertStringToDate(inputDate,
					(inputDate.contains("/")) ? DEFAULT_DATE_FORMAT : DEFAULT_SQL_DATE_FORMAT);
		}
		return outputDate;
	}

}