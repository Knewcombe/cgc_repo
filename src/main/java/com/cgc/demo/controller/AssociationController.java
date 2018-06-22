package com.cgc.demo.controller;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cgc.demo.dao.PlayersDAO;
import com.cgc.demo.dao.TeamsDAO;
import com.cgc.demo.dao.UserProfileDAO;
import com.cgc.demo.model.AssociationAccount;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.NonProfAssociation;
import com.cgc.demo.model.Player;
import com.cgc.demo.model.Teams;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.UserAssociation;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.AssociationService;
import com.cgc.demo.service.UserService;
import com.cgc.demo.service.Util;
/**
 * AssociationController.java
 * @since April 16 2018
 * @author Kyle Newcombe
 * Here is the rout controls for any and all pages for the Associations in the site.
 */
@Controller
public class AssociationController {
	//Autowired dependencies for the page
	@Autowired
	AssociationService associationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TeamsDAO teamsDAO;
	
	@Autowired
	PlayersDAO playersDAO;
	
	@Autowired
	Util util;
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce
	 * @return String
	 * Getting the Association information required after logging in.
	 * This will get the session information after the user has logged in and will
	 * add the information to the model 
	 */
	@RequestMapping({ "/association/home" })
	public String showAssociation(HttpServletRequest request, HttpSession session, HttpServletResponse response,Map<String, Object> model) {
		//Checking for the session information. 
		if (session.getAttribute("association") != null) {
			//Casting session information into object to pass into model.
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			model.put("account", associtationAccount);
			//Return String of the page name.
			return "welcome_association";
		} else {
			//If there is no session information, redirect to the login page.
			return "redirect:../login";
		}
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce
	 * @return String
	 * Get Reports for the Association that is logged in.
	 * This will get the information for the session and call the assocaitonService to
	 * gather all information needed for the page.
	 */
	@RequestMapping({ "/association/reports" })
	public String getReports(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model) {
		//Checking for session informaton.
		if (session.getAttribute("association") != null) {
			//setting information for model.
			//creating formatting objects.
			DecimalFormat df2 = new DecimalFormat("0.00");
			BigDecimal total = new BigDecimal("0.00");
			//casting Association account object from session. 
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			//Get the UserAssociation information that is linked to the AssocitatioinAccount.
			List<UserAssociation> userAssociation = associationService.getUserAsscoiation(associtationAccount);
			//If there is no UserAssociation listed, it will redirect to no Report page...
			//This might not be necessary.
			if(userAssociation != null){
				//Looping from the userAssociations to get total amount.
				for (UserAssociation user : userAssociation){
					total = total.add(user.getUserAssociationInfo().getTotal_amount());
				}
				//Setting model objects.
				model.put("userAssociation", userAssociation);
				model.put("associtationAccount", associtationAccount);
				model.put("df2", df2);
				model.put("total", total);
				//returning string of page name.
				return "association_reports";
			}else{
				//Sending user to no report page.
				return "no-report";
			}
		} else {
			//If no session information will redirect to login.
			return "redirect:../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce
	 * @return ResponceEntity
	 * Method will call on the Util service to get a generated PDF.
	 * This will use the session information for the Association account logged in and 
	 * return a PDF to the user.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/association/reports/pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> pdfRepot(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model) {
		//Get session information
		if (session.getAttribute("association") != null) {
			//casting Association account to object
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			//calling Util service to generated PDF.
			ByteArrayInputStream bis = util.generateAssociationReport(associtationAccount.getAssociation_id());
			//Creating header object to return with ResponseEntity
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=associationReport.pdf");
			return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
		} else {
			//If there is no session information return empty ResponceEntity
			return (ResponseEntity<InputStreamResource>) ResponseEntity.noContent();
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce
	 * @return ModelAndView
	 * Method is used to send a XLS file to the user.
	 * This will get the user information from the session and call on the Util service
	 * to generate a XLS and return it to the user.
	 */
	@RequestMapping( value = "/association/reports/excel", method = RequestMethod.GET, produces = "application/vnd.ms-excel")
	public ModelAndView excelAssociationReport(HttpServletRequest request, HttpSession session, HttpServletResponse response, Map<String, Object> model) {
		//Checking for session data.
		if (session.getAttribute("association") != null) {
			//Casting associtionAccount to object from session data.
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			//Gathering data from associationService to get information.
			List<UserAssociation> userAssociation = associationService.getUserAsscoiation(associtationAccount);
			//Setting the ModelAndView object depending on the associationAccount type
			ModelAndView modelAndView = null;
			if(associtationAccount.getSportAssociation() != null){
				modelAndView = new ModelAndView("sportExcelView", "userAssociation", userAssociation);
				//setting the header for XLS file
				response.setHeader("Content-Disposition", "attachment; filename=\"Sport.xls\"");
			}
			if(associtationAccount.getCharityAssociation() != null){
				for(UserAssociation user: userAssociation){
					user.setUserProfile(userService.getUserProfile(user.getUser_profile_id()));
				}
				modelAndView = new ModelAndView("charityExcelView", "userAssociation", userAssociation);
				//setting the header for XLS file
				response.setHeader("Content-Disposition", "attachment; filename=\"Chairty.xls\"");
			}
			if(associtationAccount.getNonProfAssociation() != null){
				modelAndView = new ModelAndView("nonProfExcelView", "userAssociation", userAssociation);
				//setting the header for XLS file
				response.setHeader("Content-Disposition", "attachment; filename=\"NonProf.xls\"");
			}
			//Returning ModelAndView object
			return modelAndView;
		} else {
			//If there is no session information return null;
			return null;
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Method is used only for Sport Associations that have Teams.
	 * Its meant to give report information to the user about the selected team.
	 * Team Id is required in the URL and will return all information about a given team similar to the Association Report method. 
	 * This method will call on the associationService to get any and all information needed for the report page.
	 */
	@RequestMapping({ "/association/reports/team" })
	public String getTeamReports(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "team_id", required = true) int team_id,
			Map<String, Object> model) {
		//Checking for session data.
		if (session.getAttribute("association") != null) {
			BigDecimal total = new BigDecimal("0.00");
			DecimalFormat df2 = new DecimalFormat("0.00");
			//Casting session data to object.
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			//Getting team information with Team ID from URL Params.
			Teams team = teamsDAO.getTeam(team_id);
			//Getting UserAssociation with Team ID.
			List<UserAssociation> userAssociation = associationService.getTeamUserAssociation(team_id);
			if(userAssociation != null){
				for (UserAssociation user : userAssociation){
					//Adding to total from each userAssociation.
					total = total.add(user.getUserAssociationInfo().getTotal_amount());
				}
				//Setting Model information.
				model.put("userAssociation", userAssociation);
				model.put("team", team);
				model.put("associtationAccount", associtationAccount);
				model.put("df2", df2);
				model.put("total", total);
				return "association_reports_teams";
			}else{
				//If no userAssociation found sent to no report page.
				return "no-report2";
			}
		} else {
			//If no session data return to login page.
			return "redirect:../../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return String
	 * Method is used only for Sport Associations that have Players in a Team.
	 * Its meant to give report information to the user about the selected Player.
	 * Player ID is required in the URL and will return all information about a given team similar to the Association Report method. 
	 * This method will call on the associationService to get any and all information needed for the report page.
	 */
	@RequestMapping({ "/association/reports/player" })
	public String getPlayerReports(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "player_id", required = true) int player_id,
			Map<String, Object> model) {
		//Checking the session data
		if (session.getAttribute("association") != null) {
			BigDecimal total = new BigDecimal("0.00");
			DecimalFormat df2 = new DecimalFormat("0.00");
			//Casting session data into object
			AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			//Getting player data
			Player player = playersDAO.getPlayer(player_id);
			//getting userAssociation information
			List<UserAssociation> userAssociation = associationService.getPlayerUserAssociation(player_id);
			if(userAssociation != null){
				//If there is userAssocation information loop through and get total.
				for (UserAssociation user : userAssociation){
					//Adding to total.
					total = total.add(user.getUserAssociationInfo().getTotal_amount());
				}
				//Adding data to the model
				model.put("userAssociation", userAssociation);
				model.put("player", player);
				model.put("associtationAccount", associtationAccount);
				model.put("df2", df2);
				model.put("total", total);
				return "association_reports_player";
			}else{
				return "no-report2";
			}
		} else {
			return "redirect:../../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return ResponceEntity
	 * Method will call on the Util service to get a generated PDF.
	 * This will use the Team id in the URL param and call on the Util service to generate a PDF page of a team's reports. 
	 * return a PDF to the user.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/association/reports/teams/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> pdfTeamRepot(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "team_id", required = true) int team_id,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			
			ByteArrayInputStream bis = util.generateTeamReport(team_id);
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=teams.pdf");
	        
			return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new InputStreamResource(bis));
		} else {
			return (ResponseEntity<InputStreamResource>) ResponseEntity.noContent();
			//return "redirect:../../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return ModelAndView
	 * Method is used to send a XLS file to the user.
	 * This will get the Team ID to gather the information using the assocationService for Teams information
	 * to generate a XLS and return it to the user.
	 */
	@RequestMapping( value = "/association/reports/teams/excel", method = RequestMethod.GET)
	public ModelAndView xlsTeamReport(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "team_id", required = true) int team_id,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			List<UserAssociation> userAssociation = associationService.getTeamUserAssociation(team_id);
			ModelAndView modelAndView = null;
			modelAndView = new ModelAndView("teamExcelView", "userAssociation", userAssociation);
			response.setHeader("Content-Disposition", "attachment; filename=\"Team.xls\"");
			return modelAndView;
		} else {
			return null;
			//return "redirect:../../login";
		}
	}
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return ModelAndView
	 * Method is used to send a XLS file to the user.
	 * This requires Player ID in the URL param to gather the information using the assocationService for Players information
	 * to generate a XLS and return it to the user.
	 */
	@RequestMapping( value = "/association/reports/player/excel", method = RequestMethod.GET)
	public ModelAndView xlsPlayerReport(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "player_id", required = true) int player_id,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			//AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			List<UserAssociation> userAssociation = associationService.getPlayerUserAssociation(player_id);
			ModelAndView modelAndView = null;
			modelAndView = new ModelAndView("playerExcelView", "userAssociation", userAssociation);
			response.setHeader("Content-Disposition", "attachment; filename=\"Player.xls\"");
			return modelAndView;
		} else {
			return null;
			//return "redirect:../../login";
		}
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return ResponceEntity
	 * Method will call on the Util service to get a generated PDF.
	 * This will use the Player id in the URL param and call on the Util service to generate a PDF page of a player's reports. 
	 * return a PDF to the user.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping( value = "/association/reports/player/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> pdfPlayerRepot(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam(value = "player_id", required = true) int player_id,
			Map<String, Object> model) {
		if (session.getAttribute("association") != null) {
			//AssociationAccount associtationAccount  = (AssociationAccount) session.getAttribute("association");
			
			// Will also need to work on Search parameters
			
			ByteArrayInputStream bis = util.generatePlayerReport(player_id);
			
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=players.pdf");
	        
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
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return Teams
	 * Method calls associationService to get Team name from the Team ID URL RequestParam.
	 */
	@RequestMapping(value = "/association/getTeamName", method = RequestMethod.GET)
	@ResponseBody
	public Teams getTeamName(HttpServletResponse responce, @RequestParam(value = "team_id", required = true) int team_id){
		return associationService.getTeamName(team_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return Teams
	 * Method calls associationService to get User name from the User Profile ID URL RequestParam.
	 */
	@RequestMapping(value = "/association/getUserName", method = RequestMethod.GET)
	@ResponseBody
	public UserProfile getUserName(HttpServletResponse responce, @RequestParam(value = "user_profile_id", required = true) int user_profile_id){
		return userService.getUserName(user_profile_id);
	}
	
	/**
	 * @since April 16 2018
	 * @author Kyle Newcombe
	 * @param HttpServlet Request, HttpSession, HttpServletResponce, RequestParam
	 * @return Teams
	 * Method calls associationService to get Player name from the Player ID URL RequestParam.
	 */
	@RequestMapping(value = "/association/getPlayerName", method = RequestMethod.GET)
	@ResponseBody
	public Player getPlayerName(HttpServletResponse responce, @RequestParam(value = "player_id", required = true) int player_id){
		return associationService.getPlayerName(player_id);
	}
	
	

}
