package com.cgc.demo.controller;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.FamilyMember;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.UserService;
import com.cgc.demo.service.Util;

/**
 * User Controller will handle all user session requests.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociationService associationService;
	
	@Autowired
	Util util;
	
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Check if there is a user in session and sends them to user home page.
	 */
	@RequestMapping({ "/user/home" })
	public String showUser(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			model.put("user", userAccount);
			return "welcome_user";
		} else {
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * User account page, they will be able to view all of the current users information.
	 */
	@RequestMapping({ "/user/account" })
	public String showUserAccount(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
			NumberFormat df2 = NumberFormat.getCurrencyInstance(Locale.CANADA);
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			model.put("user", userAccount);
			model.put("df2", df2);
			return "user_account";
		} else {
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * User will be able to see all transactions that they have made. They will also be able to see the total amount
	 */
	@RequestMapping({ "/user/reports" })
	public String showReports(HttpServletRequest request, HttpSession session, HttpServletResponse response,
		Map<String, Object> model) {
		//Setting all Big Decimal objects
		BigDecimal total;
		BigDecimal fees;
		BigDecimal funds;
		NumberFormat df2 = NumberFormat.getCurrencyInstance(Locale.CANADA);
		List<Transaction> transaction = null;
		//Check for user in session.
		if (session.getAttribute("user") != null) {
			//Getting user info from session.
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			//Getting all transaction using user profile id
			transaction = userService.getTransaction(userAccount.getUserProfile().getUser_profile_id());
			//Setting all Big decimal values from user information.
			total = new BigDecimal(userService.getTransactionTotal(userAccount.getUserProfile().getUser_profile_id()).toPlainString());
			fees = new BigDecimal(userService.getTransactionTotalFees(userAccount.getUserProfile().getUser_profile_id()).toPlainString());
			funds = new BigDecimal(userService.getTransactionTotalFunds(userAccount.getUserProfile().getUser_profile_id()).toPlainString());
			//Setting model objects.
			model.put("userAccount", userAccount);
			model.put("total", total.setScale(2, BigDecimal.ROUND_HALF_UP));
			model.put("fee", fees.setScale(2, BigDecimal.ROUND_HALF_UP));
			model.put("funds", funds.setScale(2, BigDecimal.ROUND_HALF_UP));
			model.put("reports", transaction);
			model.put("df2", df2);
			return "user_report";
		} else {
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Setting up page to change the selected Communites.
	 */
	@RequestMapping({"/user/changeCommunity"})
	public String changeCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			UserProfile tempProfile = new UserProfile();
			//Check the size of userAssociation list object
			if(userAccount.getUserProfile().getUserAssociation().size() < 10){
				//If its less than ten add however many needed to reach 10
				for(int i = 0; i < 10; i++){
					if(i < userAccount.getUserProfile().getUserAssociation().size()){
						tempProfile.setUserAssociation(userAccount.getUserProfile().getUserAssociation().get(i));
					}else{
						tempProfile.setUserAssociation(new UserAssociation());
					}
				}
			}
			model.put("userAccount", userAccount);
			model.put("profile", tempProfile);
			return "user_change";
		}else{
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Once user has selected a new list of communities update data base.
	 */
	@RequestMapping({"/user/updateCommunity"})
	@ResponseBody
	public String updateCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model, @ModelAttribute("profile") UserProfile userProfile){
		//Checking user in session
		if (session.getAttribute("user") != null) {
			//get user information for session
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			//Update user community selection and update object
			userAccount.getUserProfile().setUserAssociation(userService.updateUserAssociation(userProfile.getUserAssociation(), userAccount.getUserProfile().getUser_profile_id()));
			//remove old user from session.
			session.removeAttribute("user");
			//Add new object with new selected Communities.
			session.setAttribute("user", userAccount);
			return "./account";
		}else{
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Getting PDF of user reports.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/user/reports/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> userPdfReport(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			
			// Will also need to work on Search parameters
			
			ByteArrayInputStream bis = util.generateUserReport(userAccount.getUserProfile().getUser_profile_id());
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
	        
			return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
		} else {
			return (ResponseEntity<InputStreamResource>) ResponseEntity.noContent();
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, int id
	 * @return String
	 * Getting transaction details for transaction.
	 */
	@RequestMapping({"/user/reports/detail"})
	public String getDetails(@RequestParam(value = "transaction_id", required = true) int id, HttpServletRequest request, HttpSession session, HttpServletResponse response,
		Map<String, Object> model){
		DecimalFormat df2 = new DecimalFormat("0.00");
		Transaction transaction = userService.getOneTransaction(id);
		transaction.setTransactionDetail(userService.getTransactionDetails(id));
		model.put("transaction", transaction);
		model.put("df2", df2);
		return "user_transaction_details";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String association_id, String team_id, String player_id
	 * @return SportAssociation
	 * AJAX Get the name of the sport
	 */
	@RequestMapping(value = "/user/getSportNames", method = RequestMethod.GET)
	@ResponseBody
	public SportAssociation getSportNames(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int association_id, @RequestParam(value = "team_id", required = false) int team_id, @RequestParam(value = "player_id", required = false) int player_id, Map<String, Object> model){
		return associationService.getSportNames(association_id, team_id, player_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String charity_id
	 * @return CharityAssociation
	 * AJAX Get the name of the charity
	 */
	@RequestMapping(value = "/user/getChairtyName", method = RequestMethod.GET)
	@ResponseBody
	public CharityAssociation getCharityNam(HttpServletResponse responce, @RequestParam(value = "charity_id", required = true) int charity_id, Map<String, Object> model){
		return associationService.getCharityName(charity_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String nonProf_id
	 * @return NonProfAssociation
	 * AJAX Get the name of the nonprof
	 */
	@RequestMapping(value = "/user/getNonProfName", method = RequestMethod.GET)
	@ResponseBody
	public NonProfAssociation getNonProfName(HttpServletResponse responce, @RequestParam(value = "nonProf_id", required = true) int nonProf_id, Map<String, Object> model){
		return associationService.getNonProfName(nonProf_id);
	}

}
