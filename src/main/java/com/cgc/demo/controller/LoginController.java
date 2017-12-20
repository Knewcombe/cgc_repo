package com.cgc.demo.controller;

import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;

import javax.validation.Valid;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	BusinessService businessService;

	@Autowired
	UserService userService;
	
	@Autowired
	AssociationService associationService;

	@RequestMapping(value = { "/business/login" }, method = { RequestMethod.GET })
	public String showLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		// ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//model.put("dir", "./loginProcess");
		return "login";
	}

	@RequestMapping(value = { "/business/logout" }, method = { RequestMethod.GET })
	public String logoutBusiness(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("business");
		if(session.getAttribute("userId") != null){
			System.out.println("Removeing userId");
			session.removeAttribute("userId");
		}
		return "redirect:/";
	}

	@RequestMapping(value = { "/user/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String logoutUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@RequestMapping(value = { "/association/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String logoutAssociation(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("userAssociation");
		return "redirect:/";
	}

	@RequestMapping(value = { "/business/loginProcess" }, method = { RequestMethod.POST })
	public String bussinessLoginProcess(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("login") Login login, BindingResult result, Map<String, Object> model) {
		// ModelAndView mav = null;
		if (result.hasErrors()) {
			return "login";
		} else {
			System.out.println("No errors found");
			BusinessAccount business = businessService.login(login);
			if (business != null) {
				session.setAttribute("business", business);
				// mav = new ModelAndView("welcome_business");
				// mav.addObject("name", business.getUsername());
				if (session.getAttribute("userId") == null) {
					System.out.println(session.getAttribute("userId"));
					return "redirect:/business/home";
				} else {
					System.out.println(session.getAttribute("userId"));
					return "redirect:/transaction";
					
				}
			} else {
				// mav = new ModelAndView("login");
				model.put("message", "Username or Password is wrong!!");
				return "login";
			}
		}

		// return mav;
	}

	@RequestMapping(value = { "/user/login" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String userLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//mav.addObject("dir", "/loginProcess/user");
		return "login";
	}

	@RequestMapping(value = { "/user/loginProcess" }, method = { RequestMethod.POST })
	public String userLoginProcess(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("login") Login login, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			return "login";
		} else {
			UserAccount user = this.userService.login(login);
			if (user != null) {
				session.setAttribute("user", user);
				// mav = new ModelAndView("welcome_business");
				// mav.addObject("name", business.getUsername());
				return "redirect:/user/home";
			} else {
				// mav = new ModelAndView("login");
				model.put("message", "Username or Password is wrong!!");
				return "login";
			}
		}
	}
	
	@RequestMapping(value = { "/login" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//mav.addObject("dir", "/loginProcess/user");
		return "login";
	}
	
	@RequestMapping(value = { "/loginProcess" }, method = { RequestMethod.POST })
	public String loginProcess(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("login") Login login, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			return "login";
		} else {
			UserAccount user = this.userService.login(login);
			if (user != null) {
				session.setAttribute("user", user);
				// mav = new ModelAndView("welcome_business");
				// mav.addObject("name", business.getUsername());
				return "redirect:/user/home";
			} else {
				BusinessAccount business = businessService.login(login);
				if (business != null) {
					session.setAttribute("business", business);
					// mav = new ModelAndView("welcome_business");
					// mav.addObject("name", business.getUsername());
					if (session.getAttribute("userId") == null) {
						System.out.println(session.getAttribute("userId"));
						return "redirect:/business/home";
					} else {
						System.out.println(session.getAttribute("userId"));
						return "redirect:/transaction";
						
					}
				} else {
					// mav = new ModelAndView("login");
//					model.put("message", "Username or Password is wrong!!");
//					return "login";
					AssociationAccount assoicaiton = associationService.login(login);
					if(assoicaiton != null){
						session.setAttribute("association", assoicaiton);
						return "redirect:/association/home";
					}else{
						model.put("message", "Username or Password is wrong!!");
						return "login";
					}
				}
			}
		}
	}

	// @RequestMapping(value = { "/transaction/login" }, method = {
	// org.springframework.web.bind.annotation.RequestMethod.GET })
	// public ModelAndView notLoggin(HttpServletRequest request, HttpSession
	// session, HttpServletResponse response) {
	// ModelAndView mav = new ModelAndView("login");
	// mav.addObject("login", new Login());
	// mav.addObject("dir", "/transaction/loginProcess");
	// return mav;
	// }
	//
	// @RequestMapping(value = { "/transaction/loginProcess" }, method = {
	// org.springframework.web.bind.annotation.RequestMethod.POST })
	// public ModelAndView transLoginProcess(HttpServletRequest request,
	// HttpSession session, HttpServletResponse response,
	// @ModelAttribute("login") Login login) {
	// ModelAndView mav = null;
	// BusinessAccount business = this.businessService.login(login);
	// if (business != null) {
	// session.setAttribute("business", business);
	//
	// Transaction transaction = new Transaction();
	// if (Integer.parseInt(session.getAttribute("userId").toString()) != 0) {
	// transaction.setUser_profile_id(Integer.parseInt((String)
	// session.getAttribute("userId")));
	// }
	// mav = new ModelAndView("transaction_page");
	// mav.addObject("transaction", transaction);
	// } else {
	// mav = new ModelAndView("login");
	// mav.addObject("message", "Username or Password is wrong!!");
	// }
	// return mav;
	// }
}
