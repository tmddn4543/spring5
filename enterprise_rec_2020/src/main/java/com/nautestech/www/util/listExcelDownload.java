package com.nautestech.www.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.nautestech.www.model.Call;
import com.nautestech.www.model.Stat;

public class listExcelDownload extends AbstractXlsxView {
	
	public String authFormat(String auth_cd) {
		if(auth_cd.equals("00")) {
			return "시스템관리자";
		}else if(auth_cd.equals("12")) {
			return "그룹관리자";
		}else if(auth_cd.equals("13")) {
			return "상담원";
		}else if(auth_cd.equals("11")) {
			return "운용사용자";
		}else if(auth_cd.equals("15")) {
			return "알람서비스등록";
		}
		return "";
	}
	
	public String recFormat(String rec_type) {
		if (rec_type.equals("S")) {
			return "인증녹취";
		} else if (rec_type.equals("B")) {
			return "전수녹취";
		} else if (rec_type.equals("N")) {
			return "녹취정지";
		} else {
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

	public String timeFormat(String btime, String etime) throws ParseException {
		btime = btime.substring(11, 19);
		etime = etime.substring(11, 19);

		
		SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);
		Date d1 = f.parse(etime);
		Date d2 = f.parse(btime);
		Date diff = new Date(d1.getTime() - d2.getTime());
		String str_diff = String.valueOf(diff).substring(14,19);
		return str_diff;

	}

	@Override
	protected void buildExcelDocument(Map<String, Object> modelMap, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// String sCurTime = null;
		String sCurTime = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());

		// String excelName = sCurTime + ".xlsx";
		Sheet worksheet = null;
		Row row = null;
		Cell cell = null;
		
		CellStyle style = workbook.createCellStyle(); // 셀 스타일을 위한 변수
		CellStyle style1 = workbook.createCellStyle();
		CellStyle style2 = workbook.createCellStyle();
		Font font = workbook.createFont();
		Font font1 = workbook.createFont();
		
		
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style1.setAlignment(CellStyle.ALIGN_CENTER);
		style1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		font.setFontHeight((short)(20*20));
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style1.setFont(font1);
		// 새로운 sheet를 생성한다.
		worksheet = workbook.createSheet("엑셀 목록");

		// 가장 첫번째 줄에 제목을 만든다.
		row = worksheet.createRow(0);
		
		String excelName="";
		String option = (String)modelMap.get("option");
		
		
		if(option.equals("call")) {
			excelName = "CallHistory_"+sCurTime+".xlsx";
			List<Call> listExcel = (List) modelMap.get("list");
			
			//List<Call> listExcel = modelMap.entrySet().stream().map(e -> new Call()).collect(Collectors.toList());
			
			
			// 칼럼 길이 설정
			int columnIndex = 0;
			while (columnIndex < 9) {
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
					worksheet.setColumnWidth(columnIndex, 5000);
				} else if (columnIndex == 6) {
					worksheet.setColumnWidth(columnIndex, 5000);
				} else if (columnIndex == 7) {
					worksheet.setColumnWidth(columnIndex, 2000);
				} else if (columnIndex == 8) {
					worksheet.setColumnWidth(columnIndex, 2500);
				}
				columnIndex++;
			}

			// 셀 병합 CellRangeAddress(시작 행, 끝 행, 시작 열, 끝 열)
			cell = row.createCell(2);
			worksheet.addMergedRegion( new CellRangeAddress(0, 2, 2, 6));
			cell.setCellStyle(style);
			cell.setCellValue("[ 녹취 이력 ]");
			
			row = worksheet.createRow(2);
			cell = row.createCell(0);
//			worksheet.addMergedRegion( new CellRangeAddress(2, 2, 0, 1));
			cell.setCellValue("생성일자");
			cell = row.createCell(1);
			cell.setCellValue(sCurTime);
			// 헤더 설정
			row = worksheet.createRow(3);
			for(int i=0; i<9; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style1);
				if(i==0) {
					cell.setCellValue("그룹");
				}else if(i==1) {
					cell.setCellValue("사용자ID");
				}else if(i==2) {
					cell.setCellValue("이름");
				}else if(i==3) {
					cell.setCellValue("발신번호");
				}else if(i==4) {
					cell.setCellValue("수신번호");
				}else if(i==5) {
					cell.setCellValue("통화일자");
				}else if(i==6) {
					cell.setCellValue("통화시각");
				}else if(i==7) {
					cell.setCellValue("통화시간");
				}else if(i==8) {
					cell.setCellValue("유형");
				}
			}
			int rowIndex = 4;

			// 각 해당하는 셀에 값과 스타일을 넣음
			for (Call call : listExcel) {
				row = worksheet.createRow(rowIndex);
//				setCellStyle(style)
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getBranch_cd());
				
				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getEmp_id());
				
				cell = row.createCell(2);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getEmp_nm());
				
				cell = row.createCell(3);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getCaller());
				
				cell = row.createCell(4);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getCalled());
				
				cell = row.createCell(5);
				cell.setCellStyle(style2);
				cell.setCellValue(call.getBtime());
				
				cell = row.createCell(6);
				cell.setCellStyle(style2);
				cell.setCellValue(hourFormat(call.getBtime(), call.getEtime()));
				
				cell = row.createCell(7);
				cell.setCellStyle(style2);
				cell.setCellValue(timeFormat(call.getBtime(), call.getEtime()));
				
				cell = row.createCell(8);
				cell.setCellStyle(style2);
				cell.setCellValue(recFormat(call.getRec_type()));
				
				rowIndex++;
			}

			worksheet.createFreezePane(1, 4);
			
			
			
			
			
			
		}else if(option.equals("state")) {
			excelName = "Statistics_"+sCurTime+".xlsx";
			List<Stat> listExcel = (List) modelMap.get("list");
			
			
			// 칼럼 길이 설정
			int columnIndex = 0;
			while (columnIndex < 9) {
				if (columnIndex == 0) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 1) {
					worksheet.setColumnWidth(columnIndex, 3000);
				} else if (columnIndex == 2) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 3) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 4) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 5) {
					worksheet.setColumnWidth(columnIndex, 3000);
				} else if (columnIndex == 6) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 7) {
					worksheet.setColumnWidth(columnIndex, 4000);
				} else if (columnIndex == 8) {
					worksheet.setColumnWidth(columnIndex, 4500);
				}
				columnIndex++;
			}

			// 셀 병합 CellRangeAddress(시작 행, 끝 행, 시작 열, 끝 열)
			cell = row.createCell(2);
			worksheet.addMergedRegion( new CellRangeAddress(0, 2, 2, 6));
			cell.setCellStyle(style);
			cell.setCellValue("[ 통계 이력 ]");
			
			row = worksheet.createRow(2);
			cell = row.createCell(0);
//			worksheet.addMergedRegion( new CellRangeAddress(2, 2, 0, 1));
			cell.setCellValue("생성일자");
			cell = row.createCell(1);
			cell.setCellValue(sCurTime);
			
			// 헤더 설정
			row = worksheet.createRow(3);
			for(int i=0; i<9; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(style1);
				if(i==0) {
					cell.setCellValue("조회기간");
				}else if(i==1) {
					cell.setCellValue("그룹");
				}else if(i==2) {
					cell.setCellValue("사용자");
				}else if(i==3) {
					cell.setCellValue("착신");
				}else if(i==4) {
					cell.setCellValue("발신");
				}else if(i==5) {
					cell.setCellValue("착발신 합계");
				}else if(i==6) {
					cell.setCellValue("착신 통화시간 (초)");
				}else if(i==7) {
					cell.setCellValue("발신 통화시간 (초)");
				}else if(i==8) {
					cell.setCellValue("착발신시간 합계 (초)");
				}
			}
			int rowIndex = 4;
			int total1 = 0;
			int total2 = 0;
			int total3 = 0;
			int total4 = 0;
			int total5 = 0;
			int total6 = 0;
			// 각 해당하는 셀에 값과 스타일을 넣음
			for (Stat stat : listExcel) {
				row = worksheet.createRow(rowIndex);
				
				cell = row.createCell(0);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_date());
				
				cell = row.createCell(1);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getBranch_cd());
				
				cell = row.createCell(2);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getEmp_id());
				
				cell = row.createCell(3);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_called_cnt());
				
				cell = row.createCell(4);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_caller_cnt());
				
				cell = row.createCell(5);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_call_cnt_total());
				
				cell = row.createCell(6);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_called_time());
				
				cell = row.createCell(7);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_caller_time());
				
				cell = row.createCell(8);
				cell.setCellStyle(style2);
				cell.setCellValue(stat.getS_call_time_total());
				
				total1 += stat.getS_called_cnt();
				total2 += stat.getS_caller_cnt();
				total3 += stat.getS_call_cnt_total();
				total4 += stat.getS_called_time();
				total5 += stat.getS_caller_time();
				total6 += stat.getS_call_time_total();
				rowIndex++;
			}
			row = worksheet.createRow(rowIndex+1);
			
			cell = row.createCell(0);
			cell.setCellStyle(style2);
			cell.setCellValue("총소계");
			
			cell = row.createCell(3);
			cell.setCellStyle(style2);
			cell.setCellValue(total1);
			
			cell = row.createCell(4);
			cell.setCellStyle(style2);
			cell.setCellValue(total2);
			
			cell = row.createCell(5);
			cell.setCellStyle(style2);
			cell.setCellValue(total3);
			
			cell = row.createCell(6);
			cell.setCellStyle(style2);
			cell.setCellValue(total4);
			
			cell = row.createCell(7);
			cell.setCellStyle(style2);
			cell.setCellValue(total5);
			
			cell = row.createCell(8);
			cell.setCellStyle(style2);
			cell.setCellValue(total6);
			
			
			worksheet.createFreezePane(1, 4);
			
		}
			
		try {
			response.setHeader("Content-Disposition", "attachement; filename=\""
					+ java.net.URLEncoder.encode(excelName, "UTF-8") + "\";charset=\"UTF-8\"");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
