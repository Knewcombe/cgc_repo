package com.cgc.demo.service;


public interface Util {
	
	public String encodePassword(String password);
	
	public Boolean checkPassword(String password, String encodedPassword);
}
