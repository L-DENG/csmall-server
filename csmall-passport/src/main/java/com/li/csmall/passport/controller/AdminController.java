package com.li.csmall.passport.controller;

import com.li.csmall.common.web.JsonResult;
import com.li.csmall.passport.service.IAdminService;
import com.li.csmall.pojo.dto.AdminLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admins",produces = "application/json;charset=utf-8")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('/ams/admin/read')")
    public String hello(){
        return "hello~~~";
    }

    @RequestMapping("/login")
    public JsonResult<String> login(AdminLoginDTO adminLoginDTO){
        String jwt = adminService.Login(adminLoginDTO);
        return JsonResult.ok(jwt);
    }

}
