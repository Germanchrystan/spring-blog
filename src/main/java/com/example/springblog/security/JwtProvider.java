package com.example.springblog.security;

import com.example.springblog.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Key;

@Service
public class JwtProvider {

    private Key key;

    @PostConstruct
    public void init(){
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }


    public String generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningkey(key).parseClaimsJws(jwt);
        return true;
    }


    public String getusernameFromJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsjws(token)
                .getBody();
        return claims.getSubject();
    }
}
