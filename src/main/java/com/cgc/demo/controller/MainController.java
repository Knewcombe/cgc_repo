package com.cgc.demo.controller;

import java.util.Collections;
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
import com.cgc.demo.model.Json;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.Teams;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public AssociationService associationService;
	
	@Autowired
	BusinessService businessService;

	@RequestMapping({ "/*" })
	public String homePage(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		if (session.getAttribute("userId") != null) {
			System.out.println("Removeing userId");
			session.removeAttribute("userId");
		}
		return "index";
	}
	
	@RequestMapping({"/contact"})
	public String contactPage(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "contact";
	}

	
	@RequestMapping({"/communites/sport"})
	public String sportPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("sports", associationService.getAssociation());
		return "sport_community";
	}
	
	@RequestMapping({"/communites/charity"})
	public String charityPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("charites", associationService.getAllCharities());
		return "charity_community";
	}
	
	@RequestMapping({"/communites/nonprof"})
	public String nonProfPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("nonProfs", associationService.getAllNonProf());
		return "nonProf_community";
	}
	
	@RequestMapping({"/how-it-works"})
	public String howItWorks(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "how-it-works";
	}
	
	@RequestMapping({"/benefits/family"})
	public String benefitsFamily(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "benefits-family";
	}
	
	@RequestMapping({"/benefits/businesses"})
	public String benefitsBusinesses(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "benefits-businesses";
	}
	
	@RequestMapping({"/business/complete"})
	public String messageSent(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "message_sent";
	}
	
	@RequestMapping({"/community/complete"})
	public String messageCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "message_sent";
	}
	
	@RequestMapping({"/register/complete"})
	public String completeRegister(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "register_complete";
	}
	
	@RequestMapping({"/register/select"})
	public String selectRegister(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "register-select";
	}
	
	@RequestMapping( value = "/checkusername", method = RequestMethod.GET)
	@ResponseBody
	public String checkUsername(HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username) {
		Boolean test = userService.checkUsername(username);
		if(test == true){
			test = businessService.checkUserName(username);
			System.out.println("Checking business "+test.toString());
		}
		return test.toString();
	}
	
	@RequestMapping( value = "/teams/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getProvinces(HttpServletResponse response) {
		return associationService.getProvinces();
	}
	
	@RequestMapping(value = "/teams/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getCommunity(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code){
		return associationService.getCommunity(province_code);
	}
	
	@RequestMapping(value = "/teams/getSport", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getSport(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code, @RequestParam(value = "community", required = true) String community){
		return associationService.getSports(province_code, community);
	}
	
	@RequestMapping(value = "/teams/getAssociation", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getAssociation(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code, @RequestParam(value = "community", required = true) String community, @RequestParam(value = "sport", required = true) String sport){
		return associationService.getAssociationName(province_code, community, sport);
	}
	
	@RequestMapping(value = "/teams/getDivision", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getDivision(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index){
		return associationService.getDivision(index);
	}
	
	@RequestMapping(value = "/teams/getGender", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getGender(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index, @RequestParam(value = "division", required = true) String division){
		return associationService.getGender(index, division);
	}
	
	@RequestMapping(value = "/teams/getTeamName", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getTeamName(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index, @RequestParam(value = "division", required = true) String division, @RequestParam(value = "gender", required = true) String gender){
		return associationService.getTeamName(index, division, gender);
	}
	
	@RequestMapping(value = "/teams/getPlayers", method = RequestMethod.GET)
	@ResponseBody
	public List<Player> getPlayerName(HttpServletResponse responce, @RequestParam(value = "team_id", required = true) int team_id){
		System.out.println(team_id);
		return associationService.getPlayers(team_id);
	}
	
	@RequestMapping(value = "/charity/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getCharityProvince(HttpServletResponse responce){
		return associationService.getCharityProvince();
	}
	
	@RequestMapping(value = "/charity/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getChairtyCommunity(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id){
		return associationService.getCharityCommunity(province_id);
	}
	
	@RequestMapping(value = "/charity/getName", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getChairtyName(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id, @RequestParam(value = "community", required = true) String community){
		return associationService.getCharityName(province_id, community);
	}
	
	@RequestMapping(value = "/nonProf/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfProvince(HttpServletResponse responce){
		return associationService.getNonProfProvince();
	}
	
	@RequestMapping(value = "/nonProf/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfCommunity(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id){
		return associationService.getNonProfCommunity(province_id);
	}
	
	@RequestMapping(value = "/nonProf/getName", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfName(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id, @RequestParam(value = "community", required = true) String community){
		return associationService.getNonProfName(province_id, community);
	}

}
