package com.cgc.demo.controller;

import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.LoginResponse;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;

import javax.validation.Valid;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Login controller will handle all login request to the server.
 * It will handle user, business and associations logins.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
@Controller
public class LoginController {

	@Autowired
	BusinessService businessService;

	@Autowired
	UserService userService;
	
	@Autowired
	AssociationService associationService;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Set up business login
	 */
	@RequestMapping(value = { "/business/login" }, method = { RequestMethod.GET })
	public String showLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		// ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//model.put("dir", "./loginProcess");
		return "login";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Logout business account. Removes the business from session.
	 */
	@RequestMapping(value = { "/business/logout" }, method = { RequestMethod.GET })
	public String logoutBusiness(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("business");
		if(session.getAttribute("userId") != null){
			session.removeAttribute("userId");
		}
		return "redirect:/";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Logout user account. Removes the user from session.
	 */
	@RequestMapping(value = { "/user/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String logoutUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Logout association account. Removes the association from session.
	 */
	@RequestMapping(value = { "/association/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String logoutAssociation(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		session.removeAttribute("association");
		return "redirect:/";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Login login, BindingResult result, Map<String, Object> model
	 * @return String
	 * Login process for business account.
	 */
	@RequestMapping(value = { "/business/loginProcess" }, method = { RequestMethod.POST })
	public String bussinessLoginProcess(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("login") Login login, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			return "login";
		} else {
			BusinessAccount business = businessService.login(login);
			if (business != null) {
				session.setAttribute("business", business);
				if (session.getAttribute("userId") == null) {
					return "redirect:/business/home";
				} else {
					return "redirect:/transaction";
					
				}
			} else {
				model.put("message", "Username or Password is wrong!!");
				return "login";
			}
		}

	}
	private final Map<String, List<String>> userDb = new HashMap<>();
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam,  @RequestBody Login login
	 * @return LoginResponse
	 * AJAX login for CGC_APP. will generate a token and return the response to the user as a JSON string.
	 * This is for Business accounts only.
	 */
	@RequestMapping(value = "/business/loginTest" , method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LoginResponse bussinessLoginMobile(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestBody Login login) {
		BusinessAccount business = businessService.login(login);
		if(business != null){
			Date setDate = new Date();
			Date expireDate = new Date(setDate.getTime() + 24*60*60*1000); //Expires in 25 hours
			return new LoginResponse(Jwts.builder()
					.setSubject(login.getUsername())
					.setId(Integer.toString(business.getBusinessProfile().getBusiness_profile_id()))
					.claim("roles", userDb.get(login.getUsername()))
					.setIssuedAt(setDate)
					.setExpiration(expireDate)
					.signWith(SignatureAlgorithm.HS256, "secretkey")
					.compact(), business);
		}else{
			return null;
		}
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * set up user login page
	 */
	@RequestMapping(value = { "/user/login" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String userLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//mav.addObject("dir", "/loginProcess/user");
		return "login";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Login process for user account.
	 */
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
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Setup for generic login.
	 */
	@RequestMapping(value = { "/login" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//ModelAndView mav = new ModelAndView("login");
		model.put("login", new Login());
		//mav.addObject("dir", "/loginProcess/user");
		return "login";
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * New Login process for all account types. This will check through each until username and password match.
	 * If no user does not exsist.
	 */
	@RequestMapping(value = { "/loginProcess" }, method = { RequestMethod.POST })
	public String loginProcess(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			@Valid @ModelAttribute("login") Login login, BindingResult result, Map<String, Object> model) {
		if (result.hasErrors()) {
			return "login";
		} else {
			UserAccount user = this.userService.login(login);
			if (user != null) {
				session.setAttribute("user", user);
				return "redirect:/user/home";
			} else {
				BusinessAccount business = businessService.login(login);
				if (business != null) {
					session.setAttribute("business", business);
					if (session.getAttribute("userId") == null) {
						return "redirect:/business/home";
					} else {
						return "redirect:/transaction";
						
					}
				} else {
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
}
