package com.edu.sejong.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.edu.sejong.model.Excel;

@Controller
public class ExcelController {
	
	/** 공통으로 사용할 헤더 셀 스타일 */
	public CellStyle commonHeadCellStyle(Workbook wb) {
		// 테이블 헤더
		CellStyle headStyle = wb.createCellStyle(); // 새 셀스타일 만들기
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);
		
		// 배경색 GREY_25
		headStyle.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		// 가운데 정렬
		headStyle.setAlignment(HorizontalAlignment.CENTER);
		
		return headStyle;
	}
	
	
	/** 공통으로 사용할 테두리 셀 스타일 */
	public CellStyle commonBodyCellStyle(Workbook wb) {
		// 테두리
		CellStyle bodyStyle = wb.createCellStyle(); // 새 셀스타일 만들기
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);
		
		return bodyStyle;
	}
	
	@GetMapping("/excelDownloads")
	public void excelDownloads(HttpServletResponse response) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("시트 제목");
		
		Row row = null;
		Cell cell = null;
		int rowNum = 0;
		int index = 0;
		
		// 공통 스타일 설정
		CellStyle headStyle = commonHeadCellStyle(wb);
		CellStyle bodyStyle = commonBodyCellStyle(wb);
		
		String[] headNames = {"테스트제목1", "테스트제목2", "테스트제목3", "테스트제목4"}; 
		// 제목
		row = sheet.createRow(rowNum++);
		for(String headName : headNames) {
			cell = row.createCell(index++);
			cell.setCellStyle(headStyle);
			cell.setCellValue(headName);
		}
		
		// 테스트 내용
		List<Excel> ExcelList = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			Excel excel = new Excel();
			ExcelList.add(excel);
		}
		
		// 내용
		for(Excel vo : ExcelList) {
			index = 0;
			// 내용순서 설정
			String[] strs = {
				 vo.getContents1()
				,vo.getContents2()
				,vo.getContents3()
				,vo.getContents4()
			};
			
			// 공통부분 설정
			row = sheet.createRow(rowNum++);
			for(String str : strs) {
				cell = row.createCell(index++);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(str);
			}
			
		}
		
		// 크기 자동 조절
		for(int i = 0; i <= cell.getColumnIndex(); i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512);
		}
		
		// 엑셀 다운로드 형식
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=ExcelTitle.xls");
		
		try {
			wb.write(response.getOutputStream());
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
