package com.li.csmall.passport.security;

import com.li.csmall.passport.mapper.AdminMapper;
import com.li.csmall.pojo.vo.AdminLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername >>> username :" +s);
        AdminLoginVO userInfo = adminMapper.getLoginInfoByUsername(s);
        System.out.println("check the user exist or not ?");
        if (userInfo == null){
            System.out.println("do not have this user!!!");
            throw new BadCredentialsException("login failed, user does not exist");
        }

        System.out.println("the user exist and ready to build the userDetails....");
        UserDetails user = User.builder()
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .accountExpired(false)
                .accountLocked(false)
                .disabled(userInfo.getIsEnable() != 1)
                .credentialsExpired(false)
                .authorities(userInfo.getPermissions().toArray(new String[] {}))
                .build();
        System.out.println("this user is valid");
        return user;
    }
}
