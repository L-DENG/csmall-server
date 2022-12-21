package com.li.csmall.passport.service.impl;

import com.alibaba.fastjson.JSON;
import com.li.csmall.passport.security.JwtConstant;
import com.li.csmall.passport.service.IAdminService;
import com.li.csmall.pojo.dto.AdminLoginDTO;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${csmall.jwt.security-key}")
    String securityKey;

    @Override
    public String Login(AdminLoginDTO adminLoginDTO) {

        //prepare the authentication data
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(
                         adminLoginDTO.getUsername(),adminLoginDTO.getPassword());
        // execute the authentication, throws exception when the information is wrong
        Authentication authenticate = authenticationManager.authenticate(authentication);

        //pass the authentication
        //generate the JWT data

        User user = (User) authenticate.getPrincipal();

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtConstant.KEY_USERNAME,user.getUsername());
        claims.put(JwtConstant.KEY_PERMISSIONS, JSON.toJSONString(user.getAuthorities()));

        System.out.println("ready to write the date into the JWT...");
        System.out.println("data >>> " + claims);


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
        return jwt;
    }
}
