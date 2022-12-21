package com.li.csmall.passport.mapper;


import com.li.csmall.pojo.vo.AdminLoginVO;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    /**
     * get the login information by the username
     * @param username
     * @return
     */
    AdminLoginVO getLoginInfoByUsername(String username);
}
