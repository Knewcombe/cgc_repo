package com.cgc.demo.view;

import java.util.Map;

import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cgc.demo.model.UserAssociation;

public class NonProfExcelView extends AbstractExcelView{
	
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		DecimalFormat df2 = new DecimalFormat("0.00");
		HSSFSheet sheet = workbook.createSheet("Association Members");
		
		@SuppressWarnings("unchecked")
		List<UserAssociation> userAssociation = (List<UserAssociation>) model.get("userAssociation");
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Member Name");
		sheet.autoSizeColumn(0);
		header.createCell(1).setCellValue("Total Funds");
		sheet.autoSizeColumn(1);
		header.createCell(2).setCellValue("Funding Percent");
		sheet.autoSizeColumn(2);
		
		int count = 1;
		for (UserAssociation user : userAssociation) {
			HSSFRow row = sheet.createRow(count++);
			row.createCell(0).setCellValue(user.getUser_first_name() + " " + user.getUser_last_name());
			sheet.autoSizeColumn(0);
			row.createCell(1).setCellValue("$" + df2.format(user.getSum_total()));
			sheet.autoSizeColumn(1);
			row.createCell(2).setCellValue("%" + user.getDonation_amount() * 100);
			sheet.autoSizeColumn(2);
		}
	}

}
