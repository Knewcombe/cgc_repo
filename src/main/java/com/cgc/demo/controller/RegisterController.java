package com.cgc.demo.controller;

import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessPreference;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.FamilyMember;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;

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

@Controller
public class RegisterController {
	@Autowired
	public BusinessService businessService;

	@Autowired
	public UserService userService;

	@Autowired
	AssociationService associationService;

	@RequestMapping(value = { "/business/register" }, method = { RequestMethod.GET })
	public String showBusinessRegister(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
//		BusinessAccount businessAccount = new BusinessAccount();
//		businessAccount.setBusinessProfile(new BusinessProfile());
//		for(int i = 0; i < 9; i++){
//			System.out.println("test");
//			businessAccount.getBusinessProfile().setBusinessPreferance(new BusinessPreference());
//		}
//		System.out.println(businessAccount.getBusinessProfile().getBusinessPreferance().size());
//		model.put("businessAccount", businessAccount);
//		return "register_business";
		return "business_reg_form";
	}
	
	@RequestMapping(value = { "/community/register" }, method = { RequestMethod.GET })
	public String showCommunityForm(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
//		BusinessAccount businessAccount = new BusinessAccount();
//		businessAccount.setBusinessProfile(new BusinessProfile());
//		for(int i = 0; i < 9; i++){
//			System.out.println("test");
//			businessAccount.getBusinessProfile().setBusinessPreferance(new BusinessPreference());
//		}
//		System.out.println(businessAccount.getBusinessProfile().getBusinessPreferance().size());
//		model.put("businessAccount", businessAccount);
//		return "register_business";
		return "community_register";
	}

	@RequestMapping(value = { "/business/registerProcess" }, method = { RequestMethod.POST })
	public String addBusiness(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("businessAccount") BusinessAccount businessAccount, BindingResult result,
			Map<String, Object> model) {
		if (result.hasErrors()) {
			return "register_business";
		} else {
			if(businessService.checkUserName(businessAccount.getUsername())){
				model.put("usernameMessage", "Username has already been taken");
				return "register_business";
			}else{
				businessService.register(businessAccount);
				return "redirect:/business/login";
			}
		}
	}

	@RequestMapping(value = { "/user/register" }, method = { RequestMethod.GET })
	public String showUserRegister(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		UserAccount userAccount = new UserAccount();
		userAccount.setUserProfile(new UserProfile());

		for (int i = 0; i < 10; i++) {
			userAccount.getUserProfile().setUserAssociation(new UserAssociation());
		}
		
		for(int e = 0; e < 5; e++){
			userAccount.getUserProfile().setFamily(new FamilyMember());
		}

		model.put("userAccount", userAccount);
		return "register_user";
	}

	@RequestMapping(value = { "/user/registerProcess" }, method = {RequestMethod.POST })
	@ResponseBody
	public String addUser(HttpServletRequest request, HttpServletResponse response,
			@Valid @ModelAttribute("userAccount") UserAccount userAccount, BindingResult result,
			Map<String, Object> model) {
		// this.userService.register(user);
		//String returnString = "register_user";
		userService.register(userAccount);
		System.err.println("Test");
		return "../register/complete";
	}
}
