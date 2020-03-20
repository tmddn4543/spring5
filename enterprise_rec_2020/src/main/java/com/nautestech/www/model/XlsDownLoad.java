package com.nautestech.www.model;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

@Component("Xls")
public class XlsDownLoad extends AbstractXlsView {
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setHeader("Content-Disposition", "attachment; filename=\"call_history.xls\"");
		@SuppressWarnings("unchecked")
		List<Call> xls_file =(List<Call>) request.getAttribute("xls_file");
		System.out.println(xls_file.toString());
		CellStyle numberCellStyle = workbook.createCellStyle();
		DataFormat numberDataFormat = workbook.createDataFormat();
		numberCellStyle.setDataFormat(numberDataFormat.getFormat("#,##0"));
		Sheet sheet = workbook.createSheet("sample_sheet");
		Row row0 = sheet.createRow(0);
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue("그룹");
		Cell cell1 = row0.createCell(1);
		cell1.setCellValue("사용자ID");
		Cell cell2 = row0.createCell(2);
		cell2.setCellValue("이름");
		Cell cell3 = row0.createCell(3);
		cell3.setCellValue("발신번호");
		Cell cell4 = row0.createCell(4);
		cell4.setCellValue("수신번호");
		Cell cell5 = row0.createCell(5);
		cell5.setCellValue("통화일자");
		Cell cell6 = row0.createCell(6);
		cell6.setCellValue("통화시각");
		Cell cell7 = row0.createCell(7);
		cell7.setCellValue("통화시간");
		Cell cell8 = row0.createCell(8);
		cell8.setCellValue("유형");
		
//		Row row1 = sheet.createRow(1);
//		cell0 = row1.createCell(0);
//		cell0.setCellValue("2019-01-01");
//		cell1 = row1.createCell(1);
//		cell1.setCellValue("홍길동");
//		cell2 = row1.createCell(2);
//		cell1.setCellValue("홍길동");
	}
}
