package com.li.csmall.pojo.entity;


import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Permission implements Serializable {
    private long id;
    private String name;
    private String Value;
    private String description;
    private Integer sort;
    private LocalDateTime gmtCreat;
    private LocalDateTime gmtModified;
}
