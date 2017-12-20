package com.cgc.demo.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AssociationService associationService;

	@RequestMapping({ "/user/home" })
	public String showUser(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			model.put("name", userAccount.getUsername());
			return "welcome_user";
		} else {
			return "redirect: ../login";
		}
	}
	
	@RequestMapping({ "/user/reports" })
	public String showReports(HttpServletRequest request, HttpSession session, HttpServletResponse response,
		Map<String, Object> model) {
		double total = 0;
		DecimalFormat df2 = new DecimalFormat("0.00");
		List<Transaction> transaction = null;
		if (session.getAttribute("user") != null) {
			UserAccount userAccount = (UserAccount) session.getAttribute("user");
			transaction = userService.getTransaction(userAccount.getUserProfile().getUser_profile_id());
			if(transaction != null){
				total = userService.getTransactionTotal(userAccount.getUserProfile().getUser_profile_id());
				model.put("userAccount", userAccount);
				model.put("total", total);
				model.put("reports", transaction);
				model.put("df2", df2);
				return "user_report";
			}else{
				return "no-report";
			}
		} else {
			return "redirect: ../login";
		}
	}
	
	@RequestMapping({"/user/reports/detail"})
	public String getDetails(@RequestParam(value = "transaction_id", required = true) int id, HttpServletRequest request, HttpSession session, HttpServletResponse response,
		Map<String, Object> model){
		DecimalFormat df2 = new DecimalFormat("0.00");
		Transaction transaction = userService.getOneTransaction(id);
		transaction.setTransactionDetail(userService.getTransactionDetails(id));
		model.put("transaction", transaction);
		model.put("df2", df2);
		return "transaction_details";
	}
	
	@RequestMapping(value = "/user/getSportNames", method = RequestMethod.GET)
	@ResponseBody
	public SportAssociation getSportNames(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int association_id, @RequestParam(value = "team_id", required = false) int team_id, @RequestParam(value = "player_id", required = false) int player_id, Map<String, Object> model){
		return associationService.getSportNames(association_id, team_id, player_id);
	}
	
	@RequestMapping(value = "/user/getChairtyName", method = RequestMethod.GET)
	@ResponseBody
	public CharityAssociation getCharityNam(HttpServletResponse responce, @RequestParam(value = "charity_id", required = true) int charity_id, Map<String, Object> model){
		return associationService.getCharityName(charity_id);
	}
	
	@RequestMapping(value = "/user/getNonProfName", method = RequestMethod.GET)
	@ResponseBody
	public NonProfAssociation getNonProfName(HttpServletResponse responce, @RequestParam(value = "nonProf_id", required = true) int nonProf_id, Map<String, Object> model){
		return associationService.getNonProfName(nonProf_id);
	}

}
