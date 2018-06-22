package com.cgc.demo.controller;

import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.FamilyMember;
import com.cgc.demo.model.Questions;
import com.cgc.demo.model.Request;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;
import com.cgc.demo.service.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Register controller to handle all register request.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
@Controller
public class RegisterController {
	@Autowired
	public BusinessService businessService;

	@Autowired
	public UserService userService;
	
	@Autowired
	Util util;

	@Autowired
	AssociationService associationService;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Set up business registration page.
	 */
	@RequestMapping(value = { "/business/register" }, method = { RequestMethod.GET })
	public String showBusinessRegister(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("register", new Request());
		return "business_reg_form";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * set up community register page
	 */
	@RequestMapping(value = { "/community/register" }, method = { RequestMethod.GET })
	public String showCommunityForm(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		model.put("register", new Request());
		return "community_register";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Community response to register. will send a message to admin email and redirect the user
	 */
	@RequestMapping(value = { "/community/registerProcess" }, method = { RequestMethod.POST })
	public String addCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response, @Valid @ModelAttribute("register") Request message, BindingResult result, Map<String, Object> model) {
		util.sendMail(message);
		return "redirect:/community/complete";
	}

	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Business response to register. will send a message to admin email and redirect the user
	 */
	@RequestMapping(value = { "/business/registerProcess" }, method = { RequestMethod.POST })
	public String addBusiness(HttpServletRequest request, HttpSession session, HttpServletResponse response, @Valid @ModelAttribute("register") Request message, BindingResult result, Map<String, Object> model) {
		util.sendMail(message);
		return "redirect:/business/complete";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Set up user register page
	 */
	@RequestMapping(value = { "/user/register" }, method = { RequestMethod.GET })
	public String showUserRegister(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		//creating new User account
		UserAccount userAccount = new UserAccount();
		//Setting new userProfile
		userAccount.setUserProfile(new UserProfile());
		//Adding three new questions for the user account
		for (int f = 0; f < 3; f++) {
			userAccount.setQuestion(new Questions());
		}
		//Adding ten new UserAssociation to map to profile
		for (int i = 0; i < 10; i++) {
			userAccount.getUserProfile().setUserAssociation(new UserAssociation());
		}
		//Adding 5 new Family objects.
		for(int e = 0; e < 5; e++){
			userAccount.getUserProfile().setFamily(new FamilyMember());
		}
		//Add user account to model and send to view.
		model.put("userAccount", userAccount);
		return "register_user";
	}

	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * User register will add the user to the database and send the user to login page.
	 */
	@RequestMapping(value = { "/user/registerProcess" }, method = {RequestMethod.POST })
	@ResponseBody
	public String addUser(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("userAccount") UserAccount userAccount, BindingResult result,
			Map<String, Object> model) {
		//TODO: need to add server side validation. Dosnt have to be clean just enough to stop attacks.
		userService.register(userAccount);
		return "../register/complete";
	}
}
