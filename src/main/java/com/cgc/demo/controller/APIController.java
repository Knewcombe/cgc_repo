package com.cgc.demo.controller;

import java.io.IOException;

import java.math.BigDecimal;
import java.net.ResponseCache;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgc.demo.model.LoginResponse;
import com.cgc.demo.model.BusinessAccount;
import com.cgc.demo.model.BusinessProfile;
import com.cgc.demo.model.MobileResponce;
import com.cgc.demo.model.Search;
import com.cgc.demo.model.Transaction;
import com.cgc.demo.model.UserProfile;
import com.cgc.demo.service.BusinessService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * API controller meant for all http requests from CGC_APP.
 * All functionality is focused around the Business model.
 *
 * @author Kyle Newcombe
 * @since 0.82
 */
@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	BusinessService businessService;
	
	private final Map<String, List<String>> userDb = new HashMap<>();
	
	/**
	 * Login is meant for quick login using token authentication, this will allow the user to log into the cgc_app
	 * without having to give their username or password.
	 * returns loginResponce to CGC_App to allow Business user to login.
	 * logResponce will hold the business account model and a token.
	 *
	 * @param  request {@code HttpServletRequest}
	 * @param  response {@code HttpServletResponse}
	 * @param  headers {@code HttpHeaders}
	 * @return LoginResponse once user has been validated
	 * 		throws ServletException if header has no token data
	 * @author Kyle Newcombe
	 * @since 0.82
	 */
	@RequestMapping(value = "checkToken", method = RequestMethod.GET)
	@ResponseBody
	public LoginResponse login(final HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers) throws ServletException, ExpiredJwtException {
		//Get authorization from header
		final String authHeader = request.getHeader("Authorization");
		//Check authorization from header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        	//Set response status and throw error.
        	response.setStatus(403);
            throw new ServletException("Missing or invalid Authorization header.");
        }
        //Find the token
        final String token = authHeader.substring(7); // The part after "Bearer "
        //try catch for checking token validation.
        try {
        	//parse token body
            final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            //set claims in request
            request.setAttribute("claims", claims);
            //get the business profile from id set in token.
            BusinessAccount business = new BusinessAccount();
            business.setBusinessProfile(businessService.getProfile(Integer.parseInt(claims.getId())));
            business.setBusiness_account_id(business.getBusinessProfile().getBusiness_account_id());
            business.setUsername(claims.getSubject());
            //setting new expire time
            Date setDate = new Date();
			Date expireDate = new Date(setDate.getTime() + 24*60*60*1000);
            //set status of responce
            response.setStatus(200);
            //return login response with new token
            return new LoginResponse(Jwts.builder()
					.setSubject(business.getUsername())
					.setId(Integer.toString(business.getBusinessProfile().getBusiness_profile_id()))
					.claim("roles", userDb.get(business.getUsername()))
					.setIssuedAt(setDate)
					.setExpiration(expireDate)
					.signWith(SignatureAlgorithm.HS256, "secretkey")
					.compact(), business);
        }
        //throw invalid token
        catch (SignatureException | ExpiredJwtException e) {
        	response.setStatus(403);
            throw new ServletException("Invalid token.");
        }
	}
	/**
	 * Method allows business user to find a Member user with a search string.
	 * Method will return a valid user if found using the Business service model.
	 *
	 * @param  request {@code HttpServletRequest}
	 * @param  response {@code HttpServletResponse}
	 * @param  search {@code String} string used to find the user.
	 * @return List<UserProfile> throws ServletException if token is not valid.
	 * @author Kyle Newcombe
	 * @since 0.82
	 */
	@RequestMapping(value = { "searchForUser" }, method = {RequestMethod.POST })
	@ResponseBody
	public List<UserProfile> getUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String search) throws ServletException, ExpiredJwtException{
		//get token from header
		final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }
        //get token from authorization
        final String token = authHeader.substring(7); // The part after "Bearer "
        //try to parse token body to validate token
        try {
            final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            //set request status.
            request.setAttribute("claims", claims);
            response.setStatus(200);
            //Find the user using the search string
            return businessService.searchForUser(search);
        }
        //Throw error invalid token
        catch (SignatureException | ExpiredJwtException e) {
        	response.setStatus(403);
            throw new ServletException("Invalid token.");
        }
	}
	
	/**
	 * Method will return a userId using the cardId, This will insure the transaction made has a valid user for
	 * front end.
	 *
	 * @param  request {@code HttpServletRequest}
	 * @param  response {@code HttpServletResponse}
	 * @param  cardId {@code String} string used to find the user cardId.
	 * @return UserProfile throws ServletException if token is not valid.
	 * @author Kyle Newcombe
	 * @since 0.82
	 */
	@RequestMapping(value = { "getUserId" }, method = {RequestMethod.POST })
	@ResponseBody
	public UserProfile getUserId(HttpServletRequest request, HttpServletResponse response, @RequestBody String cardId) throws ServletException, ExpiredJwtException{
		//Get header
		final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }
        //get token from authorization
        final String token = authHeader.substring(7); // The part after "Bearer "
        //Try to parse token body to validate token
        try {
            final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
            response.setStatus(200);
            return businessService.getUserId(cardId);
        }
        catch (SignatureException | ExpiredJwtException e) {
        	response.setStatus(403);
            throw new ServletException("Invalid token.");
        }
	}
	
	/**
	 * Method will store a transaction once sent, and will return the results to the user.
	 *
	 * @param  request {@code HttpServletRequest}
	 * @param  response {@code HttpServletResponse}
	 * @param  transactionJson {@code String} json string is sent from app to be mapped to transaction object
	 * @return MobileResponce throws ServletException if token is not valid.
	 * @since 0.82
	 */
	@RequestMapping(value = { "transaction" }, method = {RequestMethod.POST }, consumes = "application/json")
	@ResponseBody
	public MobileResponce sendTransaction(HttpServletRequest request, HttpServletResponse response, @RequestBody String transactionJson) throws ServletException, ExpiredJwtException, JsonParseException, JsonMappingException, IOException{
		//Get header
		final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }
        //get token from authorization
        final String token = authHeader.substring(7); // The part after "Bearer "
        //try to parse token body to validate token
        try {
            final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
            //set response status
            request.setAttribute("claims", claims);
            response.setStatus(200);
            ObjectMapper objectMapper = new ObjectMapper();
            //try to map JSON string to object
        	try {
        		
				Transaction transaction = objectMapper.readValue(transactionJson, Transaction.class);
				BusinessProfile business = businessService.getProfile(Integer.parseInt(claims.getId()));
				//Send transaction to database
                int transaction_id = businessService.setTransaction(transaction, business.getBusinessPreferance());
                //get transaction once sent
                Transaction returnTransaction = businessService.getOneTransaction(transaction_id);
                //set up return object
                MobileResponce res = new MobileResponce();
                //Set total and funds generated for app to display.
                res.setTotal(returnTransaction.getTotal());
                res.setFunds(returnTransaction.getPrecent_total());
                return res;
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw e;
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
        }
        catch (SignatureException | ExpiredJwtException e) {
        	response.setStatus(403);
            throw new ServletException("Invalid token.");
        }
	}

}
