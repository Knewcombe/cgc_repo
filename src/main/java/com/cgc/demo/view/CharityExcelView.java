package com.cgc.demo.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cgc.demo.model.UserAssociation;

@SuppressWarnings("deprecation")
public class CharityExcelView extends AbstractExcelView {

	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DecimalFormat df2 = new DecimalFormat("0.00");

		@SuppressWarnings("unchecked")
		List<UserAssociation> userAssociation = (List<UserAssociation>) model.get("userAssociation");

		HSSFSheet sheet = workbook.createSheet("Association Members");
		HSSFSheet sheet2 = workbook.createSheet("Member Recipts");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Member Name");
		sheet.autoSizeColumn(0);
		header.createCell(1).setCellValue("Total Funds");
		sheet.autoSizeColumn(1);
		header.createCell(2).setCellValue("Funding Percent");
		sheet.autoSizeColumn(2);
		
		HSSFRow header2 = sheet2.createRow(0);
		header2.createCell(0).setCellValue("Member Name");		
		sheet2.autoSizeColumn(0);
		header2.createCell(1).setCellValue("Total Funds");
		sheet2.autoSizeColumn(1);
		header2.createCell(2).setCellValue("Funding Percent");
		sheet2.autoSizeColumn(2);
		header2.createCell(3).setCellValue("Province");
		sheet2.autoSizeColumn(3);
		header2.createCell(4).setCellValue("City");
		sheet2.autoSizeColumn(4);
		header2.createCell(5).setCellValue("Address");
		sheet2.autoSizeColumn(5);
		header2.createCell(6).setCellValue("Postal Code");
		sheet2.autoSizeColumn(6);
		header2.createCell(7).setCellValue("Phone");
		sheet2.autoSizeColumn(7);
		header2.createCell(8).setCellValue("Email");
		sheet2.autoSizeColumn(8);

		int count = 1;
		int count2 = 1;
		for (UserAssociation user : userAssociation) {
			HSSFRow row = sheet.createRow(count++);
			row.createCell(0).setCellValue(user.getUser_first_name() + " " + user.getUser_last_name());
			sheet.autoSizeColumn(0);
			row.createCell(1).setCellValue("$" + df2.format(user.getSum_total()));
			sheet.autoSizeColumn(1);
			row.createCell(2).setCellValue("%" + user.getDonation_amount() * 100);
			sheet.autoSizeColumn(1);
			if(user.getChairty_recipts()){
				HSSFRow row2 = sheet2.createRow(count2++);
				row2.createCell(0).setCellValue(user.getUser_first_name() + " " + user.getUser_last_name());
				sheet2.autoSizeColumn(0);
				row2.createCell(1).setCellValue("$" + df2.format(user.getSum_total()));
				sheet2.autoSizeColumn(1);
				row2.createCell(2).setCellValue("%" + user.getDonation_amount() * 100);
				sheet2.autoSizeColumn(2);
				row2.createCell(3).setCellValue(user.getUserProfile().getProvince_code());
				sheet2.autoSizeColumn(3);
				row2.createCell(4).setCellValue(user.getUserProfile().getCity());
				sheet2.autoSizeColumn(4);
				row2.createCell(5).setCellValue(user.getUserProfile().getAddress());
				sheet2.autoSizeColumn(5);
				row2.createCell(6).setCellValue(user.getUserProfile().getPostal_code());
				sheet2.autoSizeColumn(6);
				row2.createCell(7).setCellValue(user.getUserProfile().getPhone());
				sheet2.autoSizeColumn(7);
				row2.createCell(8).setCellValue(user.getUserProfile().getEmail());
				sheet2.autoSizeColumn(8);
			}
		}
	}
}
