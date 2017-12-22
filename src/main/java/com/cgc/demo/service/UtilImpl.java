package com.cgc.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cgc.demo.dao.BusinessPreferanceDAO;
import com.cgc.demo.dao.BusinessProfileDAO;
import com.cgc.demo.dao.CharityAssociationDAO;
import com.cgc.demo.dao.NonProfDAO;
import com.cgc.demo.dao.PlayersDAO;
import com.cgc.demo.dao.SportAssociationDAO;
import com.cgc.demo.dao.TeamsDAO;
import com.cgc.demo.dao.TransactionDAO;
import com.cgc.demo.dao.TransactionDetailDAO;
import com.cgc.demo.dao.UserAssociationDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
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

public class UtilImpl implements Util {

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

	@Autowired
	UserAssociationDAO userAssociationDAO;

	@Autowired
	TeamsDAO teamsDAO;

	@Autowired
	PlayersDAO playersDAO;
	
	@Autowired
	UserProfileDAO userProfileDAO;

	public String encodePassword(String password) {
		return bcryptEncoder.encode(password);
	}

	public Boolean checkPassword(String password, String encodedPassword) {
		// System.out.println(bcryptEncoder.encode(password));
		return bcryptEncoder.matches(password, encodedPassword);
	}

	public ByteArrayInputStream generateBusinessReport(int business_id) {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DecimalFormat df2 = new DecimalFormat("0.00");
		// Getting date
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
			table.setWidths(new int[] { 10, 10, 10, 10, 10, 10 });

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

				cell = new PdfPCell(new Phrase("%" + (preferance.getCash_percent() * 100)));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("%" + (preferance.getDebit_percent() * 100)));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("%" + (preferance.getCredit_percent() * 100)));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf("$"
						+ df2.format(transactionDetailDAO.getTotalPreferanceAmount(preferance.getPreference_id())))));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf("$" + df2
						.format(transactionDetailDAO.getTotalPreferancePercentAmount(preferance.getPreference_id())))));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			Paragraph header = new Paragraph(
					new Chunk("Community Game Changer Report", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph businessName = new Paragraph(new Chunk("Business Name: " + business.getBusiness_name(),
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph transactions = new Paragraph(new Chunk("Number of Transactions: " + numberOfTrans,
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph ownerName = new Paragraph(new Chunk("Owner Name: " + business.getMain_contact(),
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph date = new Paragraph(
					new Chunk("As of: " + dtf.format(localDate), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph address = new Paragraph(
					new Chunk(business.getAddress() + ", " + business.getCity() + ", " + business.getProvince_code()
							+ " " + business.getPostal_code(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph totalAmount = new Paragraph(
					new Chunk("Total Raised: $" + df2.format(total), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph beforePercent = new Paragraph(new Chunk("Total Salse (Before tax): $" + df2.format(amount),
					FontFactory.getFont(FontFactory.HELVETICA, 12)));

			PdfWriter.getInstance(document, out);
			document.open();
			document.add(header);
			document.add(Chunk.NEWLINE);
			document.add(businessName);
			document.add(ownerName);
			document.add(address);
			document.add(Chunk.NEWLINE);
			document.add(date);
			document.add(transactions);
			document.add(totalAmount);
			document.add(beforePercent);
			document.add(Chunk.NEWLINE);
			document.add(table);
			document.close();

		} catch (DocumentException ex) {
			System.out.println(ex.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generateAssociationReport(int association_id) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DecimalFormat df2 = new DecimalFormat("0.00");
		// Getting date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		try {
			PdfWriter.getInstance(document, out);
			Paragraph header = new Paragraph(
					new Chunk("Community Game Changer Report", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph date = new Paragraph(
					new Chunk("As of: " + dtf.format(localDate), FontFactory.getFont(FontFactory.HELVETICA, 12)));

			CharityAssociation charityAssociation = charityAssociationDAO.getCharity(association_id);
			SportAssociation sportAssociation = sportAssociationDAO.getSportAssociation(association_id);
			NonProfAssociation nonProfAssociation = nonProfDAO.getNonProf(association_id);

			if (charityAssociation != null) {
				// Get info and create doc here
				double total = 0;
				List<UserAssociation> userAss = userAssociationDAO
						.getChairtyAssociation(charityAssociation.getAssociation_id());
				int sizeOfUser = userAss.size();
				int recites = 0;
				Paragraph charityName = new Paragraph(
						new Chunk(charityAssociation.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph charityAddress = new Paragraph(
						new Chunk(charityAssociation.getCommunity() + ", " + charityAssociation.getProvince_code(),
								FontFactory.getFont(FontFactory.HELVETICA, 12)));
				for (UserAssociation user : userAss) {
					user.setSum_total(
							transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
					total += user.getSum_total();
					if (user.getChairty_recipts() == true) {
						recites++;
					}
				}
				Paragraph sizeOfusersPar = new Paragraph(
						new Chunk("Number of Members: " + sizeOfUser, FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph recitesPar = new Paragraph(new Chunk("Number of Member Recites: " + recites,
						FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph totalFunds = new Paragraph(new Chunk("Total Funding: " + df2.format(total),
						FontFactory.getFont(FontFactory.HELVETICA, 12)));
				document.open();
				document.add(header);
				document.add(Chunk.NEWLINE);
				document.add(charityName);
				document.add(charityAddress);
				document.add(Chunk.NEWLINE);
				document.add(date);
				document.add(sizeOfusersPar);
				document.add(recitesPar);
				document.add(totalFunds);
				document.close();
			}

			if (sportAssociation != null) {
				// get info and put it here
				double total = 0;
				List<UserAssociation> userAss = userAssociationDAO
						.getSportAssociation(sportAssociation.getAssociation_id());
				List<Teams> teams = teamsDAO.getTeams(sportAssociation.getAssociation_id());
				int sizeOfUser = userAss.size();
				Paragraph charityName = new Paragraph(
						new Chunk(sportAssociation.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph charityAddress = new Paragraph(
						new Chunk(sportAssociation.getCommunity() + ", " + sportAssociation.getProvince_code(),
								FontFactory.getFont(FontFactory.HELVETICA, 12)));
				for (UserAssociation user : userAss) {
					user.setSum_total(
							transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
					total += user.getSum_total();
				}

				PdfPTable table = new PdfPTable(4);
				table.setWidthPercentage(90);
				table.setWidths(new int[] { 10, 10, 10, 10 });

				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

				PdfPCell hcell;
				hcell = new PdfPCell(new Phrase("Name", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Divisions", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Members contribution", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Funds raised", headFont));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(hcell);

				for (Teams team : teams) {
					PdfPCell cell;
					int teamContribution = 0;
					double teamTotal = 0.0;
					List<UserAssociation> teamAss = userAssociationDAO.getTeamAssociation(team.getTeam_id());
					if (teamAss != null) {
						teamContribution = teamAss.size();
						for (UserAssociation user : teamAss) {
							user.setSum_total(
									transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
							teamTotal += user.getSum_total();
						}
					}
					cell = new PdfPCell(new Phrase(team.getName()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(team.getDivision()));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + teamContribution));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("$" + df2.format(teamTotal)));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				Paragraph sizeOfusersPar = new Paragraph(
						new Chunk("Number of Members: " + sizeOfUser, FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph totalFunds = new Paragraph(new Chunk("Total Funding: " + df2.format(total),
						FontFactory.getFont(FontFactory.HELVETICA, 12)));
				document.open();
				document.add(header);
				document.add(Chunk.NEWLINE);
				document.add(charityName);
				document.add(charityAddress);
				document.add(Chunk.NEWLINE);
				document.add(date);
				document.add(sizeOfusersPar);
				document.add(totalFunds);
				document.add(Chunk.NEWLINE);
				document.add(table);
				document.close();
			}

			if (nonProfAssociation != null) {
				// get info and put it here
				double total = 0;
				List<UserAssociation> userAss = userAssociationDAO
						.getNonProfAssociation(nonProfAssociation.getAssociation_id());
				int sizeOfUser = userAss.size();
				Paragraph charityName = new Paragraph(
						new Chunk(nonProfAssociation.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph charityAddress = new Paragraph(
						new Chunk(nonProfAssociation.getCommunity() + ", " + nonProfAssociation.getProvince_code(),
								FontFactory.getFont(FontFactory.HELVETICA, 12)));
				for (UserAssociation user : userAss) {
					user.setSum_total(
							transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
					total += user.getSum_total();
				}
				Paragraph sizeOfusersPar = new Paragraph(
						new Chunk("Number of Members: " + sizeOfUser, FontFactory.getFont(FontFactory.HELVETICA, 12)));
				Paragraph totalFunds = new Paragraph(new Chunk("Total Funding: " + df2.format(total),
						FontFactory.getFont(FontFactory.HELVETICA, 12)));
				document.open();
				document.add(header);
				document.add(Chunk.NEWLINE);
				document.add(charityName);
				document.add(charityAddress);
				document.add(Chunk.NEWLINE);
				document.add(date);
				document.add(sizeOfusersPar);
				document.add(totalFunds);
				document.close();
			}

		} catch (DocumentException ex) {
			System.out.println(ex.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generateTeamReport(int team_id) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DecimalFormat df2 = new DecimalFormat("0.00");
		// Getting date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		int teamContribution = 0;
		double teamTotal = 0.0;
		Teams team = teamsDAO.getTeam(team_id);
		SportAssociation assocition = sportAssociationDAO.getAssoication(team.getAssociation_id());
		List<UserAssociation> teamAss = userAssociationDAO.getTeamAssociation(team_id);
		List<Player> players = playersDAO.getPlayers(team_id);
		if (teamAss != null) {
			teamContribution = teamAss.size();
			for (UserAssociation user : teamAss) {
				user.setSum_total(transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
				teamTotal += user.getSum_total();
			}
		}
		try {
			PdfWriter.getInstance(document, out);
			Paragraph header = new Paragraph(
					new Chunk("Community Game Changer Report", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph date = new Paragraph(
					new Chunk("As of: " + dtf.format(localDate), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph chairtyName = new Paragraph(
					new Chunk(team.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph charityAddress = new Paragraph(
					new Chunk(assocition.getCommunity() + ", " + assocition.getProvince_code(),
							FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph sizeOfusersPar = new Paragraph(new Chunk("Number of Members: " + teamContribution,
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph totalFunds = new Paragraph(new Chunk("Total Funding: " + df2.format(teamTotal),
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(90);
			table.setWidths(new int[] { 10, 10, 10, 10 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Team", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Members contribution", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Funds raised", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			if (players != null) {
				for (Player player : players) {
					PdfPCell cell;
					int memberContribution = 0;
					double playerFunding = 0.0;
					List<UserAssociation> playerUserAss = userAssociationDAO
							.getPlayerAssociation(player.getPlayer_id());
					if (playerUserAss != null) {
						memberContribution = playerUserAss.size();
						for (UserAssociation user : playerUserAss) {
							user.setSum_total(
									transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
							playerFunding += user.getSum_total();
						}
					}
					cell = new PdfPCell(new Phrase(player.getName()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(teamsDAO.getTeamName(player.getTeam_id()).getName()));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + memberContribution));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("$" + df2.format(playerFunding)));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
			}

			document.open();
			document.add(header);
			document.add(Chunk.NEWLINE);
			document.add(chairtyName);
			document.add(charityAddress);
			document.add(Chunk.NEWLINE);
			document.add(date);
			document.add(sizeOfusersPar);
			document.add(totalFunds);
			document.add(Chunk.NEWLINE);
			document.add(table);
			document.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generatePlayerReport(int player_id) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DecimalFormat df2 = new DecimalFormat("0.00");
		// Getting date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		int teamContribution = 0;
		double teamTotal = 0.0;
		Player player = playersDAO.getPlayer(player_id);
		Teams team = teamsDAO.getTeam(player.getTeam_id());
		List<UserAssociation> playerAss = userAssociationDAO.getPlayerAssociation(player_id);
		if (playerAss != null) {
			teamContribution = playerAss.size();
			for (UserAssociation user : playerAss) {
				user.setSum_total(transactionDAO.getUserTotal(user.getUser_profile_id()) * user.getDonation_amount());
				teamTotal += user.getSum_total();
			}
		}
		try {
			PdfWriter.getInstance(document, out);
			Paragraph header = new Paragraph(
					new Chunk("Community Game Changer Report", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph date = new Paragraph(
					new Chunk("As of: " + dtf.format(localDate), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph chairtyName = new Paragraph(
					new Chunk(team.getName(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			// Paragraph charityAddress = new Paragraph(
			// new Chunk(player.getCommunity() + ", " +
			// assocition.getProvince_code(),
			// FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph sizeOfusersPar = new Paragraph(new Chunk("Number of Members: " + teamContribution,
					FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph totalFunds = new Paragraph(new Chunk("Total Funding: " + df2.format(teamTotal),
					FontFactory.getFont(FontFactory.HELVETICA, 12)));

			document.open();
			document.add(header);
			document.add(Chunk.NEWLINE);
			document.add(chairtyName);
			// document.add(charityAddress);
			document.add(Chunk.NEWLINE);
			document.add(date);
			document.add(sizeOfusersPar);
			document.add(totalFunds);
			document.close();

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream generateUserReport(int user_profile_id) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DecimalFormat df2 = new DecimalFormat("0.00");
		// Getting date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		UserProfile userProfile = userProfileDAO.getUserProfile(user_profile_id);
		List<UserAssociation> userAssociation = userAssociationDAO.getUserAssociation(user_profile_id);
		double userTotal = transactionDAO.getUserTotal(user_profile_id);
		int userAssNum = userAssociation.size();
		try {			
			PdfWriter.getInstance(document, out);
			Paragraph header = new Paragraph(new Chunk("Community Game Changer Report", FontFactory.getFont(FontFactory.HELVETICA, 30)));
			Paragraph date = new Paragraph(new Chunk("As of: " + dtf.format(localDate), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph userName = new Paragraph(new Chunk(userProfile.getFirst_name() +" "+ userProfile.getLast_name(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph userAddress = new Paragraph(new Chunk(userProfile.getAddress() + ", " + userProfile.getCity() + ", " + userProfile.getProvince_code() + " " + userProfile.getPostal_code(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph numberOfAss = new Paragraph(new Chunk("Selected Community Partners: "+userAssNum, FontFactory.getFont(FontFactory.HELVETICA, 12)));
			Paragraph totalAmount = new Paragraph(new Chunk("Total Amount Raised $: "+userTotal, FontFactory.getFont(FontFactory.HELVETICA, 12)));
			
			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(90);
			table.setWidths(new int[] { 15, 5, 3, 3 });

			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Name", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Type", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Percent", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Funds raised", headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
			
			for(UserAssociation user: userAssociation){
				PdfPCell cell;
				if(user.getCharity_id() != 0){
					cell = new PdfPCell(new Phrase(charityAssociationDAO.getCharityName(user.getCharity_id()).getName()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("Charity"));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				
				if(user.getNonprof_id() != 0){
					cell = new PdfPCell(new Phrase(nonProfDAO.getName(user.getNonprof_id()).getName()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("Non-Profit"));
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				
				if(user.getAssociation_id() != 0){
					if(user.getPlayer_id() != 0){
						cell = new PdfPCell(new Phrase(playersDAO.getPlayerName(user.getPlayer_id()).getName()));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("Sport"));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						
					}else if(user.getTeam_id() != 0){
						cell = new PdfPCell(new Phrase(teamsDAO.getTeamName(user.getTeam_id()).getName()));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("Sport"));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
						
					}else{
						cell = new PdfPCell(new Phrase(sportAssociationDAO.getSportNames(user.getAssociation_id()).getName()));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("Sport"));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);
					}
				}

				cell = new PdfPCell(new Phrase("%" + user.getDonation_amount() * 100));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("$" + df2.format(userTotal * user.getDonation_amount())));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);
			}
			
			document.open();
			document.add(header);
			document.add(Chunk.NEWLINE);
			document.add(userName);
			document.add(userAddress);
			// document.add(charityAddress);
			document.add(Chunk.NEWLINE);
			document.add(date);
			document.add(numberOfAss);
			document.add(totalAmount);
			document.add(Chunk.NEWLINE);
			document.add(table);
			
			document.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		return new ByteArrayInputStream(out.toByteArray());
	}
}
