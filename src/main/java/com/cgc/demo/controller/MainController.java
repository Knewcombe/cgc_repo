package com.cgc.demo.controller;

import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cgc.demo.model.SportAssociation;
import com.cgc.demo.model.CharityAssociation;
import com.cgc.demo.model.Login;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.Questions;
import com.cgc.demo.model.SearchResults;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.UserAccount;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.BusinessService;
import com.cgc.demo.service.UserService;
import com.cgc.demo.service.Util;

/**
 * Main controller handles all non session related requests.
 *
 * @author Kyle Newcombe
 * @since 0.1
 */
@Controller
public class MainController {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public AssociationService associationService;
	
	@Autowired
	BusinessService businessService;
	
	@Autowired
	Util util;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Home will check if there is a user in the session and redirect to where the user needs to be.
	 */
	@RequestMapping({"/"})
	public String home(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			return "redirect:/user/home";
		}else if(session.getAttribute("business") != null){
			return "redirect:/business/home";
		}else if(session.getAttribute("association") != null){
			return "redirect:/association/home";
		}else{
			return "index";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * contact page
	 */
	@RequestMapping({"/contact"})
	public String contactPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		return "contact";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Privacy policy page
	 */
	@RequestMapping({"/privacy"})
	public String privacyPage(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model){
		return "privacy_policy";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Terms of service
	 */
	@RequestMapping({"/terms-of-service"})
	public String termsOfService(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model){
		return "terms-of-service";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * site overview
	 */
	@RequestMapping({"/overview"})
	public String emailTransition(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "site-overview";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Password reset page.
	 */
	@RequestMapping({"/reset"})
	public String resetPassword(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model){
		model.put("user", new UserProfile());
		return "password_reset";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Will gather the question from userProfile email sent from resetPassword page.
	 */
	@RequestMapping(value = { "/security" }, method = { RequestMethod.POST })
	public String secuirty(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model, @ModelAttribute("user") UserProfile userProfile){
		if(userProfile != null){
			Questions question = userService.getUserQuestion(userProfile);
			if(question != null){
				model.put("question", question);
				return "answer_question";	
			}else{
				model.put("user", new UserProfile());
				model.put("message", "No account was found, please try again.");
				return "password_reset";
			}
		}else{
			return "redirect:/login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Will compare the answers from user questions. If all are correct will allow the user to change password.
	 */
	@RequestMapping(value = { "/answer" }, method = { RequestMethod.POST })
	public String answer(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model, @ModelAttribute("question") Questions question){
		if(question != null){
			UserAccount userAccount = userService.matchQuestion(question);
			if(userAccount != null){
				model.put("user", userAccount);
				return "enter-new-password";
			}else{
				model.put("question", question);
				model.put("message", "The answer is wrong, please try again");
				return "answer_question";
			}
		}else{
			return "redirect:/login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Will change the password for requested user.
	 */
	@RequestMapping(value = { "/change_password" }, method = { RequestMethod.POST })
	public String changePass(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model, @ModelAttribute("user") UserAccount userAccount){
		userService.changePassword(userAccount);
		model.put("login", new Login());
		model.put("infomessage", "Password updated, please login to confirm.");
		return "login";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Get all sport community profiles to display.
	 */
	@RequestMapping({"/communites/sport"})
	public String sportPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("sports", associationService.getAssociation());
		return "sport_community";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Get all Charity community profiles to display.
	 */
	@RequestMapping({"/communites/charity"})
	public String charityPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("charites", associationService.getAllCharities());
		return "charity_community";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Get all sport nonProf profiles to display.
	 */
	@RequestMapping({"/communites/nonprof"})
	public String nonProfPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("nonProfs", associationService.getAllNonProf());
		return "nonProf_community";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Get all sport merchants profiles to display.
	 */
	@RequestMapping({"/merchants"})
	public String merchantsPage(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		model.put("business", businessService.getAllBusiness());
		if (session.getAttribute("user") != null) {
			model.put("user_login", true);
		}else{
			model.put("user_login", false);
		}
		return "business_page";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * How it works page.
	 */
	@RequestMapping({"/how-it-works"})
	public String howItWorks(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model){
		return "how-it-works";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * What we were thinking.
	 */
	@RequestMapping({"/what_we_were_thinking"})
	public String thining(HttpServletRequest request, HttpSession session, HttpServletResponse response,  Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			model.put("user_login", true);
		}else{
			model.put("user_login", false);
		}
		return "what_we_were_thinking";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Benefits to families.
	 */
	@RequestMapping({"/benefits/family"})
	public String benefitsFamily(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			model.put("user_login", true);
		}else{
			model.put("user_login", false);
		}
		return "benefits-family";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Benefits to business.
	 */
	@RequestMapping({"/benefits/businesses"})
	public String benefitsBusinesses(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			model.put("user_login", true);
		}else{
			model.put("user_login", false);
		}
		return "benefits-businesses";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, Map<String, Object> model
	 * @return String
	 * Benefits to communities.
	 */
	@RequestMapping({"/benefits/community"})
	public String benefitsCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model){
		if (session.getAttribute("user") != null) {
			model.put("user_login", true);
		}else{
			model.put("user_login", false);
		}
		return "benefits_community";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Registration complete for business.
	 */
	@RequestMapping({"/business/complete"})
	public String messageSent(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "message_sent";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Registration complete for communities.
	 */
	@RequestMapping({"/community/complete"})
	public String messageCommunity(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "message_sent";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Registration complete for user.
	 */
	@RequestMapping({"/register/complete"})
	public String completeRegister(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "register_complete";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Registration select page.
	 */
	@RequestMapping({"/register/select"})
	public String selectRegister(HttpServletRequest request, HttpSession session, HttpServletResponse response){
		return "register-select";
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * AJAX request Check if username already exists.
	 */
	@RequestMapping( value = "/user/checkusername", method = RequestMethod.GET)
	@ResponseBody
	public String checkUsername(HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username) {
		Boolean test = userService.checkUsername(username);
		System.out.println("Getting info "+test.toString());
		if(test == true){
			test = businessService.checkUserName(username);
			System.out.println("Checking business "+test.toString());
			if(test == true){
				test = associationService.checkUsername(username);
				System.out.println("Checking association "+test.toString());
			}
		}
		return test.toString();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * AJAX request to check if email alreay exsists.
	 */
	@RequestMapping( value = "/user/checkemail", method = RequestMethod.GET)
	@ResponseBody
	public String checkEmail(HttpServletResponse response,
			@RequestParam(value = "userProfile.email", required = true) String email) {
		Boolean test = userService.checkEmail(email);
		System.out.println("Checking email "+test.toString());
		if(test == true){
			test = businessService.checkEmail(email);
			System.out.println("Checking business "+test.toString());
			if(test == true){
				test = associationService.checkEmail(email);
				System.out.println("Checking association "+test.toString());
			}
		}
		return test.toString();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * AJAX Get all province of teams for register form
	 */
	@RequestMapping( value = "/teams/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getProvinces(HttpServletResponse response) {
		return associationService.getProvinces();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, province_code
	 * @return String
	 * AJAX Get all communities from province_code for register form
	 */
	@RequestMapping(value = "/teams/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getCommunity(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code){
		return associationService.getCommunity(province_code);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_code, String community
	 * @return String
	 * AJAX Get all sports from province_code and community for register form
	 */
	@RequestMapping(value = "/teams/getSport", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getSport(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code, @RequestParam(value = "community", required = true) String community){
		return associationService.getSports(province_code, community);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_code, String community, String sport
	 * @return String
	 * AJAX Get all Sport associations from province_code, community and sport for register form
	 */
	@RequestMapping(value = "/teams/getAssociation", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getAssociation(HttpServletResponse responce, @RequestParam(value = "province_code", required = true) String province_code, @RequestParam(value = "community", required = true) String community, @RequestParam(value = "sport", required = true) String sport){
		return associationService.getAssociationName(province_code, community, sport);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, int index
	 * @return String
	 * AJAX Get all team divisions from a Sport association using selected index for register form
	 */
	@RequestMapping(value = "/teams/getDivision", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getDivision(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index){
		return associationService.getDivision(index);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, int index, String division
	 * @return String
	 * AJAX Get all team genders for a Sport association
	 */
	@RequestMapping(value = "/teams/getGender", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getGender(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index, @RequestParam(value = "division", required = true) String division){
		return associationService.getGender(index, division);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, int index, String division, String gender
	 * @return String
	 * AJAX Get all the team names from a Sport association using selected index for register form
	 */
	@RequestMapping(value = "/teams/getTeamName", method = RequestMethod.GET)
	@ResponseBody
	public List<Teams> getTeamName(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int index, @RequestParam(value = "division", required = true) String division, @RequestParam(value = "gender", required = true) String gender){
		return associationService.getTeamName(index, division, gender);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, int teams_id
	 * @return String
	 * AJAX Get all the player names from select teams
	 */
	@RequestMapping(value = "/teams/getPlayers", method = RequestMethod.GET)
	@ResponseBody
	public List<Player> getPlayerName(HttpServletResponse responce, @RequestParam(value = "team_id", required = true) int team_id){
		System.out.println(team_id);
		return associationService.getPlayers(team_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * AJAX get all province codes for charities.
	 */
	@RequestMapping(value = "/charity/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getCharityProvince(HttpServletResponse responce){
		return associationService.getCharityProvince();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_id
	 * @return String
	 * AJAX get all city for charities with the selected province_code.
	 */
	@RequestMapping(value = "/charity/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getChairtyCommunity(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id){
		return associationService.getCharityCommunity(province_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_id, String community
	 * @return String
	 * AJAX get all charity names using the province code and community selected
	 */
	@RequestMapping(value = "/charity/getName", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getChairtyName(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id, @RequestParam(value = "community", required = true) String community){
		return associationService.getCharityName(province_id, community);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * AJAX get all province codes for nonprof.
	 */
	@RequestMapping(value = "/nonProf/getProvince", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfProvince(HttpServletResponse responce){
		return associationService.getNonProfProvince();
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_id
	 * @return String
	 * AJAX get all city for nonprof with the selected province_code.
	 */
	@RequestMapping(value = "/nonProf/getCommunity", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfCommunity(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id){
		return associationService.getNonProfCommunity(province_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String province_id, String community
	 * @return String
	 * AJAX get all nonprof names using the province code and community selected
	 */
	@RequestMapping(value = "/nonProf/getName", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfName(HttpServletResponse responce, @RequestParam(value = "province_id", required = true) String province_id, @RequestParam(value = "community", required = true) String community){
		return associationService.getNonProfName(province_id, community);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String search
	 * @return String
	 * AJAX Search for Sport association using a search string
	 */
	@RequestMapping(value = "/user/searchSport", method = RequestMethod.GET)
	@ResponseBody
	public List<SportAssociation> getSportSearch(HttpServletResponse responce, @RequestParam(value = "search", required = true) String search){
		return associationService.searchSport(search);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String search
	 * @return String
	 * AJAX Search for charity using a search string
	 */
	@RequestMapping(value = "/user/searchCharity", method = RequestMethod.GET)
	@ResponseBody
	public List<CharityAssociation> getCharitySearch(HttpServletResponse responce, @RequestParam(value = "search", required = true) String search){
		return associationService.searchChairty(search);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String search
	 * @return String
	 * AJAX Search for nonprof using a search string
	 */
	@RequestMapping(value = "/user/searchNonProf", method = RequestMethod.GET)
	@ResponseBody
	public List<NonProfAssociation> getNonProfSearch(HttpServletResponse responce, @RequestParam(value = "search", required = true) String search){
		return associationService.searchNonProf(search);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String association_id, String team_id, String player_id
	 * @return String
	 * AJAX get information about sport association using the association_id (addiational information for team_id and player_id but not required)
	 */
	@RequestMapping(value = "/user/getSportInfo", method = RequestMethod.GET)
	@ResponseBody
	public SportAssociation getSportInfo(HttpServletResponse responce, @RequestParam(value = "association_id", required = true) int association_id, @RequestParam(value = "team_id", required = true) int team_id, @RequestParam(value = "player_id", required = true) int player_id){
		return associationService.getSportAssociation(association_id, team_id, player_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String charity_id
	 * @return String
	 * AJAX get information about charity using the charity_id
	 */
	@RequestMapping(value = "/user/getCharityInfo", method = RequestMethod.GET)
	@ResponseBody
	public CharityAssociation getCharityInfo(HttpServletResponse responce, @RequestParam(value = "charity_id", required = true) int charity_id){
		return associationService.getCharityAssociation(charity_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam, String nonprof_id
	 * @return String
	 * AJAX get information about nonprof using the nonprof_id
	 */
	@RequestMapping(value = "/user/getNonProfInfo", method = RequestMethod.GET)
	@ResponseBody
	public NonProfAssociation getNonProfInfo(HttpServletResponse responce, @RequestParam(value = "nonprof_id", required = true) int nonprof_id){
		return associationService.getNonProfInfo(nonprof_id);
	}

}
