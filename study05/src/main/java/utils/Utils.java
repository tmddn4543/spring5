package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

public class Utils {
	public static String format(String dt) {
		if(dt!=null) {
		Date date = null;
			try {
				date = new SimpleDateFormat("yyyyMMddHHmmss").parse(dt);
			} catch(ParseException pe) {
				return "";
			}
			String dateFormat = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
			return dateFormat;
		}else {
			return "";
		}
	}
	public static String titleFormat(String dt) {
		if(dt==null) {
			dt = "";
		}else if(dt.length()>10) {
			dt = dt.substring(0,10);
			dt += "...";
		}
		return dt;
	}
	public static String levelFormat(int dt) {
		if(dt==0) {
			return "ADMIN";
		}else if(dt==1) {
			return "USER";
		}
		return "";
	}
	public static String contentsFormat(String dt) {
		if(dt==null) {
			dt = "";
		}else if(dt.length()>20) {
			dt = dt.substring(0,20);
			dt += "...";
		}
		return dt;
	}
}
