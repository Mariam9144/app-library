package com.example.gestion_librarie;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		
	    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    String rawPassword = "123456";
	    String encodedPassword = encoder.encode(rawPassword);
	    System.out.println(encodedPassword);
		}


}
