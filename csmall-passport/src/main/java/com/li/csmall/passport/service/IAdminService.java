package com.li.csmall.passport.service;

import com.li.csmall.pojo.dto.AdminLoginDTO;
import org.springframework.stereotype.Service;


public interface IAdminService {

    String Login(AdminLoginDTO adminLoginDTO);
}
