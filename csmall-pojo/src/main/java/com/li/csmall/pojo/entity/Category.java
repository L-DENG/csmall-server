package com.li.csmall.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {
    private long id;
    private String name;
    private long parentId;
    private Integer depth;
    private String keywords;
    private Integer sort;
    private String icon;
    private Integer enable;
    private Integer isParent;
    private Integer isDisplay;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
