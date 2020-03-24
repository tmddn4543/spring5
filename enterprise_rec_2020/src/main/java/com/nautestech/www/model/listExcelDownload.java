package com.nautestech.www.model;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

public class listExcelDownload extends AbstractXlsxView {

	
	public String recFormat(String rec_type) {
		if(rec_type.equals("S")){
			return "인증녹취";
		}else if(rec_type.equals("B")){
			return "전수녹취";
		}else if(rec_type.equals("N")){
			return "녹취정지";
		}else{
			return "";
		}
	}
	public String dateFormat(String btime) {
		btime = btime.substring(0, 10);
		return btime;
	}

	public String hourFormat(String btime, String etime) {
		btime = btime.substring(11, 19);
		etime = etime.substring(11, 19);
		return btime + "~" + etime;
	}

	public String timeFormat(String btime, String etime)throws ParseException {
		btime = btime.substring(11, 19);
		etime = etime.substring(11, 19);
		
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Date d1 = f.parse(etime);
		Date d2 = f.parse(btime);
		Date diff = new Date(d1.getTime() - d2.getTime());
		int sec = diff.getSeconds();
		int min = diff.getMinutes();
		String str_sec = "";
		String str_min = "";
		if(sec<10) {
			str_sec = String.valueOf(sec);
			str_sec = "0"+str_sec;
		}else {
			str_sec = String.valueOf(sec);
		}
		
		if(min<10) {
			str_min = String.valueOf(min);
			str_min = "0"+str_min;
		}else {
			str_min = String.valueOf(min);
		}
		
		return str_min+":"+str_sec;

	}

	@Override
	protected void buildExcelDocument(Map<String, Object> modelMap, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		//String sCurTime = null;
		//sCurTime = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());

		//String excelName = sCurTime + ".xlsx";
		String excelName = "CallHistory.xlsx";
		Sheet worksheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = workbook.createCellStyle(); // 셀 스타일을 위한 변수
		List<Call> listExcel = (List) modelMap.get("list");
		// 새로운 sheet를 생성한다.
		worksheet = workbook.createSheet("엑셀 목록");

		// 가장 첫번째 줄에 제목을 만든다.
		row = worksheet.createRow(0);
		
		// 칼럼 길이 설정
		int columnIndex = 0;
		while (columnIndex < 7) {
			if (columnIndex == 0) {
				worksheet.setColumnWidth(columnIndex, 3000);
			} else if (columnIndex == 1) {
				worksheet.setColumnWidth(columnIndex, 4000);
			} else if (columnIndex == 2) {
				worksheet.setColumnWidth(columnIndex, 4000);
			} else if (columnIndex == 3) {
				worksheet.setColumnWidth(columnIndex, 4000);
			} else if (columnIndex == 4) {
				worksheet.setColumnWidth(columnIndex, 4000);
			} else if (columnIndex == 5) {
				worksheet.setColumnWidth(columnIndex, 3000);
			} else if (columnIndex == 6) {
				worksheet.setColumnWidth(columnIndex, 5000);
			}else if (columnIndex == 7) {
				worksheet.setColumnWidth(columnIndex, 2000);
			}else if (columnIndex == 8) {
				worksheet.setColumnWidth(columnIndex, 2000);
			}
			columnIndex++;
		}

		// 헤더 설정
		row = worksheet.createRow(0);
		row.createCell(0).setCellValue("그룹");
		row.createCell(1).setCellValue("사용자ID");
		row.createCell(2).setCellValue("이름");
		row.createCell(3).setCellValue("발신번호");
		row.createCell(4).setCellValue("수신번호");
		row.createCell(5).setCellValue("통화일자");
		row.createCell(6).setCellValue("통화시각");
		row.createCell(7).setCellValue("통화시간");
		row.createCell(8).setCellValue("유형");

		int rowIndex = 1;

		// 각 해당하는 셀에 값과 스타일을 넣음
		for (Call call : listExcel) {
			row = worksheet.createRow(rowIndex);
			row.createCell(0).setCellValue(call.getBranch_cd());
			row.createCell(1).setCellValue(call.getEmp_id());
			row.createCell(2).setCellValue(call.getEmp_nm());
			row.createCell(3).setCellValue(call.getCaller());
			row.createCell(4).setCellValue(call.getCalled());
			row.createCell(5).setCellValue(dateFormat(call.getBtime()));
			row.createCell(6).setCellValue(hourFormat(call.getBtime(),call.getEtime()));
			row.createCell(7).setCellValue(timeFormat(call.getBtime(),call.getEtime()));
			row.createCell(8).setCellValue(recFormat(call.getRec_type()));
			rowIndex++;
		}

		worksheet.createFreezePane(1, 1);
		try {
			response.setHeader("Content-Disposition", "attachement; filename=\""
					+ java.net.URLEncoder.encode(excelName, "UTF-8") + "\";charset=\"UTF-8\"");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
