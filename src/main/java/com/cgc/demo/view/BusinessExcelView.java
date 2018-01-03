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

import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;

@SuppressWarnings("deprecation")
public class BusinessExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			   HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		DecimalFormat df2 = new DecimalFormat("0.00");
		
		@SuppressWarnings("unchecked")
		List<Transaction> transactions = (List<Transaction>) model.get("transactions");
		HSSFSheet sheet1 = workbook.createSheet("Transactions");
		HSSFSheet sheet2 = workbook.createSheet("Transactions Details");
		
		HSSFRow header = sheet1.createRow(0);
		  header.createCell(0).setCellValue("Transaction Id");
		  sheet1.autoSizeColumn(0);
		  header.createCell(1).setCellValue("User Id");
		  sheet1.autoSizeColumn(2);
		  header.createCell(2).setCellValue("Total Amount");
		  sheet1.autoSizeColumn(2);
		  header.createCell(3).setCellValue("Total Funds");
		  sheet1.autoSizeColumn(3);
		  header.createCell(4).setCellValue("Date Of Purchase");
		  sheet1.autoSizeColumn(4);
		  
		  HSSFRow header2 = sheet2.createRow(0);
		  header2.createCell(0).setCellValue("Transaction Id");
		  sheet2.autoSizeColumn(0);
		  header2.createCell(1).setCellValue("Item");
		  sheet2.autoSizeColumn(1);
		  header2.createCell(2).setCellValue("Amount");
		  sheet2.autoSizeColumn(2);
		  header2.createCell(3).setCellValue("Transaction Rate");
		  sheet2.autoSizeColumn(3);
		  header2.createCell(4).setCellValue("Funds Raised");
		  sheet2.autoSizeColumn(4);
		  header2.createCell(5).setCellValue("Method of Payment");
		  sheet2.autoSizeColumn(5);
		 
		  int tCounter = 1;
		  int tdCounter = 1;
		  for (Transaction transaction : transactions) {
		   HSSFRow row = sheet1.createRow(tCounter++);
		   row.createCell(0).setCellValue(transaction.getTransaction_id());
		   sheet1.autoSizeColumn(0);
		   row.createCell(1).setCellValue(transaction.getUser_profile_id());
		   sheet1.autoSizeColumn(1);
		   row.createCell(2).setCellValue("$"+df2.format(transaction.getTotal()));
		   sheet1.autoSizeColumn(2);
		   row.createCell(3).setCellValue("$"+df2.format(transaction.getPrecent_total()));
		   sheet1.autoSizeColumn(3);
		   row.createCell(4).setCellValue(transaction.getDate_of_purhase());
		   sheet1.autoSizeColumn(4);
		   for(TransactionDetail details: transaction.getTransactionDetail()){
			   HSSFRow row2 = sheet2.createRow(tdCounter++);
			   row2.createCell(0).setCellValue(details.getTransaction_id());
			   sheet2.autoSizeColumn(0);
			   row2.createCell(1).setCellValue(details.getName());
			   sheet2.autoSizeColumn(1);
			   row2.createCell(2).setCellValue("$"+df2.format(details.getAmount()));
			   sheet2.autoSizeColumn(2);
			   row2.createCell(3).setCellValue("%"+(details.getTransaction_rate() * 100));
			   sheet2.autoSizeColumn(3);
			   row2.createCell(4).setCellValue("$"+df2.format(details.getPrecent_amount()));
			   sheet2.autoSizeColumn(4);
			   row2.createCell(5).setCellValue(details.getMethod_of_pyment());
			   sheet2.autoSizeColumn(5);
		   }
		  }
		
	}

}
