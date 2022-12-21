package com.li.csmall.passport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    String securityKey = "csmall-passport";

    @Test
    public void testGenerateJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",9999);
        claims.put("name","admin-001");



        //jwt must have header, payload,signature
        String jwt = Jwts.builder()
                // header: assign the algorithm and data type
                .setHeaderParam(Header.CONTENT_TYPE,"HS256")
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                //payload: self-defining data and expiration time
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ 5*60*1000))
                //signature: be composed of two parts: algorithm and security key.
                .signWith(SignatureAlgorithm.HS256,securityKey)
                .compact();

        System.out.println(jwt);
        //eyJjdHkiOiJIUzI1NiIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJuYW1lIjoiYWRtaW4tMDAxIiwiaWQiOjk5OTksImV4cCI6MTY3MTQxMTg0OX0.CMz5rZMIyTCvX8m4I2RdYWMUdoss7YQvcV4EhrsR4Zg
    }


    @Test
    public void testParseJwt(){
        String jwt ="eyJjdHkiOiJIUzI1NiIsInR5cCI6IkpXVCIsImFsZyI6IkhTMjU2In0.eyJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiL3Btcy9wcm9kdWN0L2RlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoiL3Btcy9wcm9kdWN0L3JlYWQifSx7ImF1dGhvcml0eSI6Ii9wbXMvcHJvZHVjdC91cGRhdGUifV0sIm5hbWUiOiJub2JvZHkiLCJleHAiOjE2NzE0MzgyMzZ9.OUu_ygSsA-kmmN20ZzhLssUte9YHd3F5hpUTG636Z2Y";
        Claims claims = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(jwt).getBody();
        String name = claims.get("name").toString();
        Collection<GrantedAuthority> permissions = (Collection<GrantedAuthority>) claims.get("permissions");
        System.out.println("permissions: " + permissions );
        System.out.println("name: " + name);
    }
}
