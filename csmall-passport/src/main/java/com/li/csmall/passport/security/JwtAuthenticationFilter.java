package com.li.csmall.passport.security;

import com.alibaba.fastjson.JSON;
import com.li.csmall.common.web.JsonResult;
import com.li.csmall.common.web.State;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${csmall.jwt.security-key}")
    String securityKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter.doFilterInternal()");
        // clear the context in the SecurityContextHolder
        //  SecurityContextHolder will keep the context all the time, which means always has the JWT data in the header.
        SecurityContextHolder.clearContext();
        //client must add JWT data in the header
        String Authorization = request.getHeader("Authorization");
        System.out.println("the authorization in the header >>> " +Authorization);
        // check does the JWT exist or not
        if (!StringUtils.hasText(Authorization)) {
            // the JWT does not exit
            System.out.println("the JWT does not exit, continue do other precess");
            filterChain.doFilter(request,response);
            return;
        }
        System.out.println("header contains JWT data, ready to parse the data...");

        String username=null;
        String stringPermissions = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(securityKey).parseClaimsJws(Authorization).getBody();
            username = claims.get(JwtConstant.KEY_USERNAME).toString();
            stringPermissions = claims.get(JwtConstant.KEY_PERMISSIONS).toString();
            System.out.println("permissions: " + stringPermissions );
            System.out.println("name: " + username);
        }catch (ExpiredJwtException e){
            System.out.println("fail to parse the JWT, due to the data has been expired: " + e.getMessage());
            //since we need to stop the process and return the json to front end.
            JsonResult<Void> jsonResult = JsonResult.fail(State.ERR_JWT_EXPIRED,"Login information has been expired, please login again");
            String jsonString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(jsonString);
            return;
        }catch (MalformedJwtException e){
            System.out.println("fail to parse the JWT, due to invalid format of the data: " + e.getMessage());
            //since we need to stop the process and return the json to front end.
            JsonResult<Void> jsonResult = JsonResult.fail(State.ERR_JWT_MALFORMED,"Failed to get information, please login again");
            String jsonString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(jsonString);
            return;
        }catch (SignatureException e){
            System.out.println("fail to parse the JWT, due to signature error: " + e.getMessage() );
            //since we need to stop the process and return the json to front end.
            JsonResult<Void> jsonResult = JsonResult.fail(State.ERR_JWT_SIGNATURE,"Failed to get information, please login again");
            String jsonString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(jsonString);
            return;
        }catch (Throwable e){
            System.out.println("fail to parse the JWT,exception class: "+ e.getClass().getName());
            e.printStackTrace();
            //since we need to stop the process and return the json to front end.
            JsonResult<Void> jsonResult = JsonResult.fail(State.ERR_INTERNAL_SERVER,"Failed to get information, please login again");
            String jsonString = JSON.toJSONString(jsonResult);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(jsonString);
            return;
        }

        //
        List<SimpleGrantedAuthority> permissions = JSON.parseArray(stringPermissions,SimpleGrantedAuthority.class);
        Authentication authentication1= new UsernamePasswordAuthenticationToken(username,null,permissions);
        SecurityContextHolder.getContext().setAuthentication(authentication1);
        filterChain.doFilter(request,response);
    }
}
