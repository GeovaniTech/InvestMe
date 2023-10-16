package utils;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
	private static final String SECRET_KEY = "4JbyOe3Sl2N6GzHIBxjK6S05U6g/VWc8w1kmHvrRbxA=";
	private static Number EXPIRATION_TIME = 300000;
	
	public static String generateEmailToken(String email, Number expirationTime) {
		if(expirationTime != null) {
			EXPIRATION_TIME = expirationTime;
		}
		
		Date now = new Date();
		
		Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME.longValue());
		
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	public static String getEmailFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return claims.getBody().getSubject();
	}
}