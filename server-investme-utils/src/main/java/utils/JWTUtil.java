package utils;

import java.util.Date;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import security.wildfly.WildflyConfigs;;

public class JWTUtil {
	private final static Algorithm algorithm = Algorithm.HMAC256(WildflyConfigs.getConfig("jwt_key"));
	
	public static String generateToken(String issuer,
								String subject) {
		
		
		try {
			String token = JWT.create()
					.withIssuer(issuer)
					.withSubject(subject)
					.withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + 300000L))
					.withJWTId(UUID.randomUUID().toString())
					.sign(algorithm);
			
			return token;
		} catch (Exception e) {
			System.err.println(" ############## FAILED TO CREATE JWT TOKEN ############## ");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getValueFromToken(String issuer, String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(issuer)
					.build();
			
			DecodedJWT decodedJWT = verifier.verify(token);
			
			return decodedJWT.getSubject();
		} catch (Exception e) {
			System.err.println(" ############## FAILED TO GET JWT TOKEN VALUE ############## ");
			e.printStackTrace();
		}
		
		return null;
	}
}
