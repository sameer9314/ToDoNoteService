package com.bridgelabz.tokenutility;

import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Purpose : To provide methods to generate token.
 * 
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    11/07/2018
 */
@Service
public class TokenUtility {

	final static String KEY = "Sameer";
/*
	*//**
	 * Method is written to generate token.
	 * 
	 * @param user
	 * @return String
	 *//*
	@SuppressWarnings("unused")
	public String generator(User user) {
		String email = user.getEmail();
		String passkey = user.getId();
		long time = System.currentTimeMillis();
		long nowMillis = System.currentTimeMillis() + (20 * 60 * 60 * 1000);
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(passkey).setIssuedAt(now).setSubject(email)
				.signWith(SignatureAlgorithm.HS256, KEY);
		return builder.compact();
	}*/

	/**
	 * Method is written to claim token and return back the user email.
	 * 
	 * @param jwt
	 * @return String
	 */
	public Claims parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwt)
				.getBody();
		return claims;
	}
}
