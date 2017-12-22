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
		  header.createCell(1).setCellValue("User Id");
		  header.createCell(2).setCellValue("Total Amount");
		  header.createCell(3).setCellValue("Total Funds");
		  header.createCell(4).setCellValue("Date Of Purchase");
		  
		  HSSFRow header2 = sheet2.createRow(0);
		  header2.createCell(0).setCellValue("Transaction Id");
		  header2.createCell(1).setCellValue("Item");
		  header2.createCell(2).setCellValue("Amount");
		  header2.createCell(3).setCellValue("Transaction Rate");
		  header2.createCell(4).setCellValue("Funds Raised");
		  header2.createCell(5).setCellValue("Method of Payment");
		 
		  int tCounter = 1;
		  for (Transaction transaction : transactions) {
		   HSSFRow row = sheet1.createRow(tCounter++);
		   row.createCell(0).setCellValue(transaction.getTransaction_id());
		   row.createCell(1).setCellValue(transaction.getUser_profile_id());
		   row.createCell(2).setCellValue("$"+df2.format(transaction.getTotal()));
		   row.createCell(3).setCellValue("$"+df2.format(transaction.getPrecent_total()));
		   row.createCell(4).setCellValue(transaction.getDate_of_purhase());
		   int tdCounter = 1;
		   for(TransactionDetail details: transaction.getTransactionDetail()){
			   HSSFRow row2 = sheet2.createRow(tdCounter++);
			   row2.createCell(0).setCellValue(details.getTransaction_id());
			   row2.createCell(1).setCellValue(details.getName());
			   row2.createCell(2).setCellValue("$"+df2.format(details.getAmount()));
			   row2.createCell(3).setCellValue("%"+(details.getTransaction_rate() * 100));
			   row2.createCell(4).setCellValue("$"+df2.format(details.getPrecent_amount()));
			   row2.createCell(5).setCellValue(details.getMethod_of_pyment());
		   }
		  }
		
	}

}
