package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class Utils {
	public static String format(String dt, String format) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dt);
		} catch(ParseException pe) {
			return "";
		}
		String dateFormat = DateFormatUtils.format(date, format);
		return dateFormat;
	}
	public static String titleFormat(String dt) {
		dt = dt.substring(0,10);
		dt += "...";
		return dt;
	}
}
