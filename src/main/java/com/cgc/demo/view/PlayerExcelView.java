package com.cgc.demo.view;

import java.math.BigDecimal;
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

public class PlayerExcelView extends AbstractExcelView{
	
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		DecimalFormat df2 = new DecimalFormat("0.00");
		
		
		@SuppressWarnings("unchecked")
		List<UserAssociation> userAssociation = (List<UserAssociation>) model.get("userAssociation");
		
		HSSFSheet sheet = workbook.createSheet("Association Members");
		
		HSSFRow header = sheet.createRow(0);
		  header.createCell(0).setCellValue("Member Name");
		  sheet.autoSizeColumn(0);
		  header.createCell(1).setCellValue("Team");
		  sheet.autoSizeColumn(2);
		  header.createCell(2).setCellValue("Player");
		  sheet.autoSizeColumn(2);
		  header.createCell(3).setCellValue("Funds Generated");
		  sheet.autoSizeColumn(3);
		  header.createCell(4).setCellValue("Administration Fees (20%)");
		  sheet.autoSizeColumn(4);
		  header.createCell(5).setCellValue("Total to be disbursed");
		  sheet.autoSizeColumn(5);
		  header.createCell(6).setCellValue("Funding Percent");
		  sheet.autoSizeColumn(6);
		  
		  int count = 1;
		  for(UserAssociation user: userAssociation){
			  BigDecimal total_funds = user.getUserAssociationInfo().getTotal_amount();
			  BigDecimal fees = total_funds.multiply(new BigDecimal("0.20"));
			  BigDecimal after_fees = total_funds.subtract(fees);
			  HSSFRow row = sheet.createRow(count++);
			  row.createCell(0).setCellValue(user.getUser_first_name() +" "+user.getUser_last_name());
			  sheet.autoSizeColumn(0);
			   row.createCell(1).setCellValue(user.getTeam_name());
			   sheet.autoSizeColumn(1);
			   row.createCell(2).setCellValue(user.getPlayer_name());
			   sheet.autoSizeColumn(2);
			   row.createCell(3).setCellValue("$" + df2.format(total_funds));
				sheet.autoSizeColumn(3);
				row.createCell(4).setCellValue("$-" + df2.format(fees));
				sheet.autoSizeColumn(4);
				row.createCell(5).setCellValue("$" + df2.format(after_fees));
				sheet.autoSizeColumn(5);
			   row.createCell(6).setCellValue("%"+user.getDonation_amount().multiply(new BigDecimal("100")));
			   sheet.autoSizeColumn(6);
		  }
	}

}
