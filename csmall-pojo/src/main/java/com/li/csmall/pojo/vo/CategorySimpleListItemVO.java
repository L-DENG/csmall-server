package com.li.csmall.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySimpleListItemVO implements Serializable {
    private long id;
    private String name;
    private Integer sort;
    private String icon;
    private Integer isParent;
}
