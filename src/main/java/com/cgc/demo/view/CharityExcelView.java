package com.cgc.demo.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
		header.createCell(1).setCellValue("Total Funds");
		header.createCell(2).setCellValue("Funding Percent");
		
		HSSFRow header2 = sheet2.createRow(0);
		header2.createCell(0).setCellValue("Member Name");
		header2.createCell(1).setCellValue("Total Funds");
		header2.createCell(2).setCellValue("Funding Percent");
		header2.createCell(3).setCellValue("Province");
		header2.createCell(4).setCellValue("City");
		header2.createCell(5).setCellValue("Address");
		header2.createCell(6).setCellValue("Postal Code");
		header2.createCell(7).setCellValue("Phone");
		header2.createCell(8).setCellValue("Email");

		int count = 1;
		int count2 = 1;
		for (UserAssociation user : userAssociation) {
			HSSFRow row = sheet.createRow(count++);
			row.createCell(0).setCellValue(user.getUser_first_name() + " " + user.getUser_last_name());
			row.createCell(1).setCellValue("$" + df2.format(user.getSum_total()));
			row.createCell(2).setCellValue("%" + user.getDonation_amount() * 100);
			if(user.getChairty_recipts()){
				HSSFRow row2 = sheet2.createRow(count2++);
				row2.createCell(0).setCellValue(user.getUser_first_name() + " " + user.getUser_last_name());
				row2.createCell(1).setCellValue("$" + df2.format(user.getSum_total()));
				row2.createCell(2).setCellValue("%" + user.getDonation_amount() * 100);
				row2.createCell(3).setCellValue(user.getUserProfile().getProvince_code());
				row2.createCell(4).setCellValue(user.getUserProfile().getCity());
				row2.createCell(5).setCellValue(user.getUserProfile().getAddress());
				row2.createCell(6).setCellValue(user.getUserProfile().getPostal_code());
				row2.createCell(7).setCellValue(user.getUserProfile().getPhone());
				row2.createCell(8).setCellValue(user.getUserProfile().getEmail());
			}
		}
	}
}
