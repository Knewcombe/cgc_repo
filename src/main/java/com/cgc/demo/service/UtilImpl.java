package com.cgc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cgc.demo.dao.AssociationAccountDAO;
import com.cgc.demo.dao.BusinessPreferanceDAO;
import com.cgc.demo.dao.BusinessProfileDAO;
import com.cgc.demo.dao.CharityAssociationDAO;
import com.cgc.demo.dao.NonProfDAO;
import com.cgc.demo.dao.SportAssociationDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.SportAssociation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class UtilImpl implements Util{
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	
	@Autowired
	BusinessProfileDAO businessProfileDAO;
	
	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	BusinessPreferanceDAO businessPreferanceDAO;
	
	@Autowired
	TransactionDetailDAO transactionDetailDAO;
	
	@Autowired
	CharityAssociationDAO charityAssociationDAO;
	
	@Autowired
	SportAssociationDAO sportAssociationDAO;
	
	@Autowired
	NonProfDAO nonProfDAO;
	
	public String encodePassword(String password){
		return bcryptEncoder.encode(password);
	}
	
	public Boolean checkPassword(String password, String encodedPassword){
		//System.out.println(bcryptEncoder.encode(password));
		return bcryptEncoder.matches(password, encodedPassword);
	}
	
	public ByteArrayInputStream generateBusinessReport(int business_id){
		
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DecimalFormat df2 = new DecimalFormat("0.00");
        //Getting date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        
        BusinessProfile business = businessProfileDAO.getBusinessProfile(business_id);
        List<BusinessPreference> businessPref = businessPreferanceDAO.getAllBusinessPreference(business_id);
        
        double total = transactionDAO.getBusinessTotal(business_id);
        double amount = transactionDAO.getBusinessAmount(business_id);
        int numberOfTrans = transactionDAO.getTotalBusinessTransactions(business_id);
        
        try {
        	PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{10, 10, 10, 10, 10, 10});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Cash Precent", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Debit Precent", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Credit Precent", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Total Purchase", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            hcell = new PdfPCell(new Phrase("Amount Rased", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
            
            for (BusinessPreference preferance : businessPref) {
            	PdfPCell cell;
            	
            	cell = new PdfPCell(new Phrase(preferance.getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("%"+(preferance.getCash_percent() * 100)));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("%"+(preferance.getDebit_percent() * 100)));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase("%"+(preferance.getCredit_percent() * 100)));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf("$"+df2.format(transactionDetailDAO.getTotalPreferanceAmount(preferance.getPreference_id())))));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
                
                cell = new PdfPCell(new Phrase(String.valueOf("$"+df2.format(transactionDetailDAO.getTotalPreferancePercentAmount(preferance.getPreference_id())))));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }
            
		Paragraph header = new Paragraph(new Chunk("Community Game Changer Report",FontFactory.getFont(FontFactory.HELVETICA, 30)));
		Paragraph businessName = new Paragraph(new Chunk("Business Name: " + business.getBusiness_name() ,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph transactions = new Paragraph(new Chunk("Number of Transactions: " + numberOfTrans,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph ownerName = new Paragraph(new Chunk("Owner Name: " + business.getMain_contact() ,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph date = new Paragraph(new Chunk("As of: " + dtf.format(localDate),FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph address = new Paragraph(new Chunk(business.getAddress() +", "+ business.getCity() +", "+ business.getProvince_code() +" "+ business.getPostal_code() ,FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph totalAmount = new Paragraph(new Chunk("Total Raised: $"+ df2.format(total),FontFactory.getFont(FontFactory.HELVETICA, 12)));
		Paragraph beforePercent = new Paragraph(new Chunk("Total Salse (Before tax): $"+ df2.format(amount),FontFactory.getFont(FontFactory.HELVETICA, 12)));
		
		PdfWriter.getInstance(document, out);
        document.open();
        document.add(header);
        document.add( Chunk.NEWLINE );
        document.add(businessName);
        document.add(ownerName);
        document.add(address);
        document.add( Chunk.NEWLINE );
        document.add(date);
        document.add(transactions);
        document.add(totalAmount);
        document.add(beforePercent);
        document.add( Chunk.NEWLINE );
        document.add(table);
        document.close();
        
		} catch (DocumentException ex) {
	        System.out.println(ex.toString());
	    }
		
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	public ByteArrayInputStream generateAssociationReport(int association_id){
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DecimalFormat df2 = new DecimalFormat("0.00");
        //Getting date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();
        try{
        	PdfWriter.getInstance(document, out);
        	 Paragraph header = new Paragraph(new Chunk("Community Game Changer Report",FontFactory.getFont(FontFactory.HELVETICA, 30)));
             Paragraph date = new Paragraph(new Chunk("As of: " + dtf.format(localDate),FontFactory.getFont(FontFactory.HELVETICA, 12)));
             
             CharityAssociation charityAssociation = charityAssociationDAO.getCharity(association_id);
             SportAssociation sportAssociation = sportAssociationDAO.getSportAssociation(association_id);
             NonProfAssociation nonProfAssociation = nonProfDAO.getNonProf(association_id);
             
             if(charityAssociation != null){
             	//Get info and create doc here
             }
             
             if(sportAssociation != null){
             	//get info and put it here
             }
             
             if(nonProfAssociation != null){
             	//get info and put it here
             }
             
        }catch (DocumentException ex) {
	        System.out.println(ex.toString());
        }
        
		return null;
	}
}
