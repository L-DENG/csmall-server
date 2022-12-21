package com.li.csmall.admin.webapi.mapper;

import com.li.csmall.pojo.dto.AdminPermissionSimpleDTO;
import com.li.csmall.pojo.entity.Admin;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminMapper {
    /**
     * insert admin info
     * @param admin
     * @return
     */
    int insert(Admin admin);

    /**
     * get permission information of user by username
     * @param name
     * @return
     */
    AdminPermissionSimpleDTO getByUsername(String name);



}
