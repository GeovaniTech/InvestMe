package org.acme.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

public class GenerateToken {
    public static void main(String[] args) {
        String token =
           Jwt.issuer("https://www.devpree.com.br/investme/issuer") 
             .upn("investme-api") 
             .groups(new HashSet<>(Arrays.asList("User", "Admin"))) 
             .claim(Claims.birthdate.name(), new Date()) 
             .expiresAt(Date.from(Instant.now().plus(1, ChronoUnit.YEARS)).toInstant())
           .sign();
        System.out.println(token);
    }
}