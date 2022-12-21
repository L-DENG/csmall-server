package com.li.csmall.pojo.dto;

import com.li.csmall.pojo.entity.Permission;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminPermissionSimpleDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Integer isEnable;
    private List<Permission> value;
}
