package com.cgc.demo.controller;

import java.text.DecimalFormat;
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

import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.UserService;

@Controller
public class AssociationController {
	
	@Autowired
	AssociationService associationService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping({ "/association/home" })
	public String showAssociation(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			model.put("name", associtationAccount.getUsername());
			return "welcome_association";
		} else {
			return "redirect:../login";
		}
		
	}
	
	@RequestMapping({ "/association/reports" })
	public String getReports(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			DecimalFormat df2 = new DecimalFormat("0.00");
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			List<UserAssociation> userAssociation = associationService.getUserAsscoiation(associtationAccount);
			model.put("userAssociation", userAssociation);
			model.put("associtationAccount", associtationAccount);
			model.put("df2", df2);
			return "association_reports";
		} else {
			return "redirect:../login";
		}
		
	}
	
	@RequestMapping(value = "/association/getTeamName", method = RequestMethod.GET)
	@ResponseBody
	public Teams getTeamName(HttpServletResponse responce, @RequestParam(value = "team_id", required = true) int team_id){
		return associationService.getTeamName(team_id);
	}
	
	@RequestMapping(value = "/association/getUserName", method = RequestMethod.GET)
	@ResponseBody
	public UserProfile getUserName(HttpServletResponse responce, @RequestParam(value = "user_profile_id", required = true) int user_profile_id){
		return userService.getUserName(user_profile_id);
	}
	
	@RequestMapping(value = "/association/getPlayerName", method = RequestMethod.GET)
	@ResponseBody
	public Player getPlayerName(HttpServletResponse responce, @RequestParam(value = "player_id", required = true) int player_id){
		return associationService.getPlayerName(player_id);
	}
	

}
