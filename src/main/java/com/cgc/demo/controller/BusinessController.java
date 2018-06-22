package com.cgc.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.Search;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.TransactionDetail;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.JwtFilter;
import com.cgc.demo.service.UserService;
import com.cgc.demo.service.Util;

/**
 * Business controller meant for all request for business account.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
@Controller
public class BusinessController {

	@Autowired
	BusinessService businessService;
	
	@Autowired
	Util util;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Validates user from session, return home if session exists. Redirect to login otherwise.
	 */
	@RequestMapping({ "/business/home" })
	public String showBusiness(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if(session.getAttribute("userId") != null){
			System.out.println("Removeing userId");
			session.removeAttribute("userId");
		}
		if (session.getAttribute("business") != null) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
			model.put("account", businessAccount);
			return "welcome_business";
		} else {
			return "redirect: ../login";
		}
		
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object>
	 * @return Teams
	 * Show reports in model for business account.
	 */
	@RequestMapping({ "/business/reports" })
	public String showReports(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		//double total = 0;
		BigDecimal total;
		//DecimalFormat df2 = new DecimalFormat("0.00");
		NumberFormat df2 = NumberFormat.getCurrencyInstance(Locale.CANADA);
		List<Transaction> transaction = null;
		if (session.getAttribute("business") != null) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
			transaction = businessService.getTransaction(businessAccount.getBusinessProfile().getBusiness_profile_id());
			// Will also need to work on Search parameters
			if(transaction != null){
				total = new BigDecimal(businessService.getTransactionTotal(businessAccount.getBusinessProfile().getBusiness_profile_id()).toPlainString());
				model.put("businessAccount", businessAccount);
				model.put("total", total);
				model.put("reports", transaction);
				model.put("df2", df2);
				return "business_report";
			}else{
				return "no-report";
			}
		} else {
			return "redirect: ../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return ResponseEntity<InputStreamResource>
	 * Returns PDF for business reports.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/business/reports/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> pdfRepot(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("business") != null) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
			
			// Will also need to work on Search parameters
			
			ByteArrayInputStream bis = util.generateBusinessReport(businessAccount.getBusinessProfile().getBusiness_profile_id());
			
			HttpHeaders headers = new HttpHeaders();
			//Will need to change the file name to the business name in object.
	        headers.add("Content-Disposition", "inline; filename=business.pdf");
	        
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
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return ModelAndView
	 * Return excel document for business reports.
	 */
	@RequestMapping( value = "/business/reports/excel", method = RequestMethod.GET,
            produces = "application/vnd.ms-excel")
	public ModelAndView excelBusinessDoc(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("business") != null) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
	        List<Transaction> transactions = businessService.getTransaction(businessAccount.getBusinessProfile().getBusiness_profile_id());
	        for(Transaction transaction: transactions){
	        	transaction.setTransactionDetail(businessService.getTransactionDetails(transaction.getTransaction_id()));
	        }
	        ModelAndView modelAndView = new ModelAndView("businessExcelView", "transactions", transactions);
	        response.setHeader("Content-Disposition", "attachment; filename=\"transactions.xls\"");
			return modelAndView;
		} else {
			return null;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Shows transaction detail values base on request param (transaction id)
	 */
	@RequestMapping({"/business/reports/detail"})
	public String getDetails(@RequestParam(value = "transaction_id", required = true) int id, HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model){
		DecimalFormat df2 = new DecimalFormat("0.00");
		Transaction transaction = businessService.getOneTransaction(id);
		transaction.setTransactionDetail(businessService.getTransactionDetails(id));
		model.put("transaction", transaction);
		model.put("df2", df2);
		return "business_transaction_details";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Set up the transaction page.
	 */
	@RequestMapping({ "/transaction" })
	public String transaction(@RequestParam(value = "userId", required = false) String id, HttpSession session) {
		//Checking if there us a userId in session
		if (id == null) {
			session.setAttribute("userId", Integer.valueOf(0));
		} else {
			session.setAttribute("userId", id);
		}
		//Checking if session has business account
		if (session.getAttribute("business") != null) {
			return "redirect:transaction/confirm";
		} else {
			return "redirect:../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Setting up transaction page model
	 */
	@RequestMapping(value = { "/transaction/confirm" }, method = {RequestMethod.GET })
	public String showTransaction(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
			if(businessAccount != null){
			Transaction transaction = new Transaction();
			if (Integer.parseInt(session.getAttribute("userId").toString()) != 0) {
				transaction.setUser_profile_id(Integer.parseInt((String) session.getAttribute("userId")));
			}

			for(int i = 0; i < businessAccount.getBusinessProfile().getBusinessPreferance().size(); i++){
				transaction.setTransactionDetail(new TransactionDetail());
				transaction.getTransactionDetail(i).setTransaction_type(businessAccount.getBusinessProfile().getBusinessPreferance(i).getPreference_id());
				transaction.getTransactionDetail(i).setName(businessAccount.getBusinessProfile().getBusinessPreferance(i).getName());
			}
			model.put("businessAccount", businessAccount);
			model.put("transaction", transaction);
			return "transaction_page";
		}else{
			return "redirect:../login";
		}
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Once transaction has been set show the results to the user with the total amount.
	 */
	@RequestMapping(value = { "/transaction/complete" }, method = {RequestMethod.POST })
	public String completeTransaction(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("transaction") Transaction transaction, BindingResult result,
			Map<String, Object> model) {
		if (session.getAttribute("business") != null) {
			BusinessAccount businessAccount = (BusinessAccount) session.getAttribute("business");
			transaction.setBusiness_profile_id(businessAccount.getBusinessProfile().getBusiness_profile_id());
			//TODO: Will need to add further validation.
			if (result.hasErrors()) {
				model.put("businessAccount", businessAccount);
				model.put("transaction", transaction);
				return "transaction_page";
			} else {
				if(businessService.checkForUser(transaction.getUser_profile_id())){
					int transaction_id = businessService.setTransaction(transaction, businessAccount.getBusinessProfile().getBusinessPreferance());
					Transaction test = businessService.getOneTransaction(transaction_id);
					BigDecimal total = test.getTotal();
					BigDecimal funds = test.getPrecent_total();
					
					//DecimalFormat df2 = new DecimalFormat("0.00");
					NumberFormat df2 = NumberFormat.getCurrencyInstance(Locale.CANADA);
					model.put("total", total);
					model.put("funds", funds);
					model.put("df2", df2);
					return "purchase_complete";
				}else{
					model.put("businessAccount", businessAccount);
					model.put("transaction", transaction);
					model.put("message", "No User was found with the provided User Id, please try again");
					return "transaction_page";
				}
			}
		} else {
			return "redirect:../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Set up model to search for user.
	 */
	@RequestMapping(value = { "/search/user" }, method = {RequestMethod.GET })
	public String searchForUser(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model) {
		model.put("search", new Search());
		return "search_user";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return UserProfile
	 * Get the user id with the requet cardId from transaction page.
	 */
	@RequestMapping(value = { "transaction/getUserId" }, method = {RequestMethod.GET })
	@ResponseBody
	public UserProfile getUserId(HttpServletRequest request, @RequestParam(value = "cardId", required = true) String cardId) {
		//Get a user profile with id;
		System.out.println("Card Id " + cardId);
		return businessService.getUserId(cardId);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Once user has been found return the List of userProfiles
	 */
	@RequestMapping(value = { "/found/user" }, method = {RequestMethod.POST })
	public String findUser(HttpServletRequest request, HttpSession session, HttpServletResponse response, @ModelAttribute("search") Search search, Map<String, Object> model) {
		List<UserProfile> userProfile = businessService.searchForUser(search);
		if(userProfile != null){
			model.put("users", userProfile);
			return "found_user";
		}else{
			model.put("message", "No user was found was phone number, please try again");
			return "search_user";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return List<UserProfile>
	 * AJAX response from transaction page. A faster alternative. Will return a list of UserProfiles
	 */
	@RequestMapping(value = { "transaction/searchForUser" }, method = {RequestMethod.GET })
	@ResponseBody
	public List<UserProfile> getUserInfo(HttpServletRequest request, @RequestParam(value = "search", required = true) String search) {
		//Get a user profile with id;
		return businessService.searchForUser(search);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return UserProfile
	 * AJAX to find user id using the card id.
	 */
	@RequestMapping(value = { "transaction/getUserCardId" }, method = {RequestMethod.GET })
	@ResponseBody
	public UserProfile getUserCardId(HttpServletRequest request, @RequestParam(value = "user_profile_id", required = true) int user_profile_id) {
		//Get a user profile with id;
		
		return businessService.getCardId(user_profile_id);
	}

}
