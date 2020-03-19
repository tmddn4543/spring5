package utils;

public class Utils {
	public String btime;
	public String etime;

	public static String authFormat(String param) {
		if (param.equals("시스템관리자")) {
			return "00";
		} else if (param.equals("그룹관리자")) {
			return "12";
		} else if (param.equals("상담원")) {
			return "13";
		} else if (param.equals("운용사용자")) {
			return "11";
		} else if (param.equals("알람서비스등록")) {
			return "15";
		}
		return "";
	}

	public static String recFormat(String param) {
		if (param.equals("인증녹취")) {
			return "S";
		} else if (param.equals("전수녹취")) {
			return "B";
		} else if (param.equals("녹취정지")) {
			return "N";
		}
		return "";
	}

	public String getBtime() {
		return btime;
	}

	public void setBtime(String btime) {
		this.btime = btime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}
	

}
