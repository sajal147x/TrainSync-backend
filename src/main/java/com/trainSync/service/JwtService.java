package com.trainSync.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * @author sajalgupta
 */
@Service
public class JwtService {

	@Value("${supabase.jwt.secret}")
	private String SupabaseJWTSecret;

	/**
	 * 
	 * @param token
	 * @return
	 */
	public String extractUserId(String token) {
	    Key key = Keys.hmacShaKeyFor(SupabaseJWTSecret.getBytes(StandardCharsets.UTF_8));
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		return claims.get("sub", String.class); // this is the Supabase UUID
	}


}
